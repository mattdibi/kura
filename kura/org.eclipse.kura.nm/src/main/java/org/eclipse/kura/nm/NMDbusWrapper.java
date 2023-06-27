/*******************************************************************************
 * Copyright (c) 2023 Eurotech and/or its affiliates and others
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *  Eurotech
 *******************************************************************************/
package org.eclipse.kura.nm;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.eclipse.kura.nm.enums.NMDeviceState;
import org.eclipse.kura.nm.enums.NMDeviceType;
import org.freedesktop.NetworkManager;
import org.freedesktop.dbus.DBusPath;
import org.freedesktop.dbus.connections.impl.DBusConnection;
import org.freedesktop.dbus.exceptions.DBusException;
import org.freedesktop.dbus.exceptions.DBusExecutionException;
import org.freedesktop.dbus.interfaces.Properties;
import org.freedesktop.dbus.types.UInt32;
import org.freedesktop.dbus.types.Variant;
import org.freedesktop.networkmanager.Device;
import org.freedesktop.networkmanager.Settings;
import org.freedesktop.networkmanager.device.Generic;
import org.freedesktop.networkmanager.device.Wireless;
import org.freedesktop.networkmanager.settings.Connection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NMDbusWrapper {

    private static final Logger logger = LoggerFactory.getLogger(NMDbusWrapper.class);

    private static final String NM_BUS_NAME = "org.freedesktop.NetworkManager";
    private static final String NM_BUS_PATH = "/org/freedesktop/NetworkManager";
    private static final String NM_DEVICE_BUS_NAME = "org.freedesktop.NetworkManager.Device";
    private static final String NM_DEVICE_WIRELESS_BUS_NAME = "org.freedesktop.NetworkManager.Device.Wireless";
    private static final String NM_GENERIC_DEVICE_BUS_NAME = "org.freedesktop.NetworkManager.Device.Generic";
    private static final String NM_SETTINGS_BUS_PATH = "/org/freedesktop/NetworkManager/Settings";

    private static final String NM_PROPERTY_VERSION = "Version";

    private static final String NM_DEVICE_PROPERTY_INTERFACE = "Interface";
    private static final String NM_DEVICE_PROPERTY_MANAGED = "Managed";
    private static final String NM_DEVICE_PROPERTY_DEVICETYPE = "DeviceType";
    private static final String NM_DEVICE_PROPERTY_STATE = "State";
    private static final String NM_DEVICE_PROPERTY_IP4CONFIG = "Ip4Config";
    private static final String NM_SETTING_CONNECTION_KEY = "connection";
    private static final String NM_DEVICE_GENERIC_PROPERTY_TYPEDESCRIPTION = "TypeDescription";

    private DBusConnection dbusConnection;
    private NetworkManager networkManager;

    public NMDbusWrapper(DBusConnection dbusConnection) throws DBusException {
        this.dbusConnection = dbusConnection;
        this.networkManager = this.dbusConnection.getRemoteObject(NM_BUS_NAME, NM_BUS_PATH, NetworkManager.class);
    }

    public Map<String, String> getPermissions() {
        return this.networkManager.GetPermissions();
    }

    public String getVersion() throws DBusException {
        Properties nmProperties = this.dbusConnection.getRemoteObject(NM_BUS_NAME, NM_BUS_PATH, Properties.class);
        return nmProperties.Get(NM_BUS_NAME, NM_PROPERTY_VERSION);
    }

    public String getDeviceInterface(Device device) throws DBusException {
        Properties deviceProperties = this.dbusConnection.getRemoteObject(NM_BUS_NAME, device.getObjectPath(),
                Properties.class);

        return deviceProperties.Get(NM_DEVICE_BUS_NAME, NM_DEVICE_PROPERTY_INTERFACE);
    }

    public NMDeviceState getDeviceState(Device device) throws DBusException {
        Properties deviceProperties = this.dbusConnection.getRemoteObject(NM_BUS_NAME, device.getObjectPath(),
                Properties.class);

        return NMDeviceState.fromUInt32(deviceProperties.Get(NM_DEVICE_BUS_NAME, NM_DEVICE_PROPERTY_STATE));
    }

    public void setDeviceManaged(Device device, Boolean manage) throws DBusException {
        Properties deviceProperties = this.dbusConnection.getRemoteObject(NM_BUS_NAME, device.getObjectPath(),
                Properties.class);

        deviceProperties.Set(NM_DEVICE_BUS_NAME, NM_DEVICE_PROPERTY_MANAGED, manage);
    }

    public Boolean isDeviceManaged(Device device) throws DBusException {
        Properties deviceProperties = this.dbusConnection.getRemoteObject(NM_BUS_NAME, device.getObjectPath(),
                Properties.class);

        return deviceProperties.Get(NM_DEVICE_BUS_NAME, NM_DEVICE_PROPERTY_MANAGED);
    }

    public NMDeviceType getDeviceType(String deviceDbusPath) throws DBusException {
        Properties deviceProperties = this.dbusConnection.getRemoteObject(NM_BUS_NAME, deviceDbusPath,
                Properties.class);

        NMDeviceType deviceType = NMDeviceType
                .fromUInt32(deviceProperties.Get(NM_DEVICE_BUS_NAME, NM_DEVICE_PROPERTY_DEVICETYPE));

        // Workaround to identify Loopback interface for NM versions prior to 1.42
        if (deviceType == NMDeviceType.NM_DEVICE_TYPE_GENERIC) {
            Generic genericDevice = this.dbusConnection.getRemoteObject(NM_BUS_NAME, deviceDbusPath, Generic.class);
            Properties genericDeviceProperties = this.dbusConnection.getRemoteObject(NM_BUS_NAME,
                    genericDevice.getObjectPath(), Properties.class);
            String genericDeviceType = genericDeviceProperties.Get(NM_GENERIC_DEVICE_BUS_NAME,
                    NM_DEVICE_GENERIC_PROPERTY_TYPEDESCRIPTION);
            if (genericDeviceType.equals("loopback")) {
                return NMDeviceType.NM_DEVICE_TYPE_LOOPBACK;
            }
        }

        return deviceType;
    }

    public List<Device> getAllDevices() throws DBusException {
        List<DBusPath> devicePaths = this.networkManager.GetAllDevices();

        List<Device> devices = new ArrayList<>();
        for (DBusPath path : devicePaths) {
            devices.add(this.dbusConnection.getRemoteObject(NM_BUS_NAME, path.getPath(), Device.class));
        }

        return devices;
    }

    public Optional<Connection> getAssociatedConnection(Device dev) throws DBusException {
        Optional<Connection> appliedConnection = getAppliedConnection(dev);
        if (appliedConnection.isPresent()) {
            return appliedConnection;
        } else {
            logger.debug("Active connection not found, looking for avaliable connections.");

            List<Connection> availableConnections = getAvaliableConnections(dev);

            if (!availableConnections.isEmpty()) {
                return Optional.of(availableConnections.get(0));

            } else {
                return Optional.empty();
            }
        }
    }

    public List<Connection> getAvaliableConnections(Device dev) throws DBusException {
        List<Connection> connections = new ArrayList<>();

        try {
            Settings settings = this.dbusConnection.getRemoteObject(NM_BUS_NAME, NM_SETTINGS_BUS_PATH, Settings.class);

            List<DBusPath> connectionPath = settings.ListConnections();

            Properties deviceProperties = this.dbusConnection.getRemoteObject(NM_BUS_NAME, dev.getObjectPath(),
                    Properties.class);
            String interfaceName = deviceProperties.Get(NM_DEVICE_BUS_NAME, NM_DEVICE_PROPERTY_INTERFACE);
            String expectedConnectionName = String.format("kura-%s-connection", interfaceName);

            for (DBusPath path : connectionPath) {

                Connection availableConnection = this.dbusConnection.getRemoteObject(NM_BUS_NAME, path.getPath(),
                        Connection.class);

                Map<String, Map<String, Variant<?>>> availableConnectionSettings = availableConnection.GetSettings();
                String availableConnectionId = (String) availableConnectionSettings.get(NM_SETTING_CONNECTION_KEY)
                        .get("id").getValue();

                if (availableConnectionId.equals(expectedConnectionName)) {
                    connections.add(availableConnection);
                }

            }

        } catch (DBusExecutionException e) {
            logger.debug("Could not find applied connection for {}, caused by", dev.getObjectPath(), e);
        }

        return connections;
    }

    public Optional<Connection> getAppliedConnection(Device dev) throws DBusException {
        try {
            Map<String, Map<String, Variant<?>>> connectionSettings = dev.GetAppliedConnection(new UInt32(0))
                    .getConnection();
            String uuid = String.valueOf(connectionSettings.get(NM_SETTING_CONNECTION_KEY).get("uuid").getValue());

            Settings settings = this.dbusConnection.getRemoteObject(NM_BUS_NAME, NM_SETTINGS_BUS_PATH, Settings.class);

            DBusPath connectionPath = settings.GetConnectionByUuid(uuid);
            return Optional
                    .of(this.dbusConnection.getRemoteObject(NM_BUS_NAME, connectionPath.getPath(), Connection.class));
        } catch (DBusExecutionException e) {
            logger.debug("Could not find applied connection for {}, caused by", dev.getObjectPath(), e);
        }

        return Optional.empty();
    }

    public void activateConnection(Connection connection, Device device) throws DBusException {
        this.networkManager.ActivateConnection(new DBusPath(connection.getObjectPath()),
                new DBusPath(device.getObjectPath()), new DBusPath("/"));
    }

    public List<Properties> getAllAccessPoints(Wireless wirelessDevice) throws DBusException {
        List<DBusPath> accessPointPaths = wirelessDevice.GetAllAccessPoints();

        List<Properties> accessPointProperties = new ArrayList<>();

        for (DBusPath path : accessPointPaths) {
            Properties apProperties = this.dbusConnection.getRemoteObject(NM_BUS_NAME, path.getPath(),
                    Properties.class);
            accessPointProperties.add(apProperties);

        }

        return accessPointProperties;
    }

    public String getDeviceId(String deviceDbusPath) throws DBusException {
        Properties nmModemProperties = this.dbusConnection.getRemoteObject(NM_BUS_NAME, deviceDbusPath,
                Properties.class);
        String deviceId = (String) nmModemProperties.Get(NM_DEVICE_BUS_NAME + ".Modem", "DeviceId");
        logger.debug("Found DeviceId {} for device {}", deviceId, deviceDbusPath);
        return deviceId;
    }

    public Optional<String> getModemManagerDbusPath(String deviceDbusPath) throws DBusException {
        Properties deviceProperties = this.dbusConnection.getRemoteObject(NM_BUS_NAME, deviceDbusPath,
                Properties.class);
        String modemDbusPath = (String) deviceProperties.Get(NM_DEVICE_BUS_NAME, "Udi");

        // Check that modemDbusPath is not null and that it is a modemmanager dbus path
        if (modemDbusPath == null || !modemDbusPath.startsWith("/org/freedesktop/ModemManager1")) {
            logger.debug("Could not find ModemDbusPath for device {}", deviceDbusPath);
            return Optional.empty();
        }

        logger.debug("Found ModemDbusPath {} for device {}", modemDbusPath, deviceDbusPath);
        return Optional.of(modemDbusPath);
    }

}