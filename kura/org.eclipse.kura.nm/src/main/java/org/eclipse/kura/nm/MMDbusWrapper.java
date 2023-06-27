package org.eclipse.kura.nm;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.kura.nm.status.SimProperties;
import org.freedesktop.ModemManager1;
import org.freedesktop.dbus.DBusPath;
import org.freedesktop.dbus.connections.impl.DBusConnection;
import org.freedesktop.dbus.exceptions.DBusException;
import org.freedesktop.dbus.exceptions.DBusExecutionException;
import org.freedesktop.dbus.interfaces.ObjectManager;
import org.freedesktop.dbus.interfaces.Properties;
import org.freedesktop.dbus.types.UInt32;
import org.freedesktop.dbus.types.Variant;
import org.freedesktop.modemmanager1.Modem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MMDbusWrapper {

    private static final Logger logger = LoggerFactory.getLogger(MMDbusWrapper.class);

    private static final String MM_BUS_NAME = "org.freedesktop.ModemManager1";
    private static final String MM_BUS_PATH = "/org/freedesktop/ModemManager1";
    private static final String MM_MODEM_NAME = "org.freedesktop.ModemManager1.Modem";
    private static final String MM_SIM_NAME = "org.freedesktop.ModemManager1.Sim";
    private static final String MM_LOCATION_BUS_NAME = "org.freedesktop.ModemManager1.Modem.Location";

    private DBusConnection dbusConnection;
    private ModemManager1 modemManager;

    public MMDbusWrapper(DBusConnection dbusConnection) throws DBusException {
        this.dbusConnection = dbusConnection;
        this.modemManager = dbusConnection.getRemoteObject(MM_BUS_PATH, MM_BUS_NAME, ModemManager1.class);
    }

    public Map<DBusPath, Map<String, Map<String, Variant<?>>>> getManagedObjects() throws DBusException {
        ObjectManager objectManager = this.dbusConnection.getRemoteObject(MM_BUS_NAME, MM_BUS_PATH,
                ObjectManager.class);
        Map<DBusPath, Map<String, Map<String, Variant<?>>>> managedObjects = objectManager.GetManagedObjects();
        logger.debug("Found Managed Objects {}", managedObjects.keySet());
        return managedObjects;
    }

    public Optional<Properties> getModemProperties(String modemPath) throws DBusException {
        Optional<Properties> modemProperties = Optional.empty();
        Properties properties = this.dbusConnection.getRemoteObject(MM_BUS_NAME, modemPath, Properties.class);
        if (Objects.nonNull(properties)) {
            modemProperties = Optional.of(properties);
        }
        return modemProperties;
    }

    public List<SimProperties> getModemSimProperties(Properties modemProperties) throws DBusException {
        List<SimProperties> simProperties = new ArrayList<>();
        try {
            UInt32 primarySimSlot = modemProperties.Get(MM_MODEM_NAME, "PrimarySimSlot");

            if (primarySimSlot.intValue() == 0) {
                // Multiple SIM slots aren't supported
                DBusPath simPath = modemProperties.Get(MM_MODEM_NAME, "Sim");
                if (!simPath.getPath().equals("/")) {
                    Properties simProp = this.dbusConnection.getRemoteObject(MM_BUS_NAME, simPath.getPath(),
                            Properties.class);
                    simProperties.add(new SimProperties(simProp, true, true));
                }
            } else {
                List<DBusPath> simPaths = modemProperties.Get(MM_MODEM_NAME, "SimSlots");
                for (int index = 0; index < simPaths.size(); index++) {
                    String dbusPath = simPaths.get(index).getPath();

                    if (dbusPath.equals("/")) {
                        // SIM slot doesn't contain a SIM
                        continue;
                    }

                    Properties simProp = this.dbusConnection.getRemoteObject(MM_BUS_NAME, dbusPath, Properties.class);
                    boolean isActive = simProp.Get(MM_SIM_NAME, "Active");
                    boolean isPrimary = index == (primarySimSlot.intValue() - 1);

                    simProperties.add(new SimProperties(simProp, isActive, isPrimary));
                }
            }
        } catch (DBusExecutionException e) {
            // Fallback for ModemManager version prior to 1.16
            DBusPath simPath = modemProperties.Get(MM_MODEM_NAME, "Sim");
            if (!simPath.getPath().equals("/")) {
                Properties simProp = this.dbusConnection.getRemoteObject(MM_BUS_NAME, simPath.getPath(),
                        Properties.class);
                simProperties.add(new SimProperties(simProp, true, true));
            }

        }
        return simProperties;
    }

    public List<Properties> getModemBearersProperties(String modemPath, Properties modemProperties)
            throws DBusException {
        List<Properties> bearerProperties = new ArrayList<>();
        try {
            Modem modem = this.dbusConnection.getRemoteObject(MM_BUS_NAME, modemPath, Modem.class);
            if (Objects.nonNull(modem)) {
                List<DBusPath> bearerPaths = modem.ListBearers();
                bearerProperties = getBearersPropertiesFromPaths(bearerPaths);
            }
        } catch (DBusExecutionException e) {
            try {
                List<DBusPath> bearerPaths = modemProperties.Get(MM_BUS_NAME, "Bearers");
                bearerProperties = getBearersPropertiesFromPaths(bearerPaths);
            } catch (DBusExecutionException e1) {
                logger.warn("Cannot get bearers for modem {}", modemPath, e1);
            }
        }
        return bearerProperties;
    }

    public List<Properties> getBearersPropertiesFromPaths(List<DBusPath> bearerPaths) throws DBusException {
        List<Properties> bearerProperties = new ArrayList<>();
        for (DBusPath bearerPath : bearerPaths) {
            if (!bearerPath.getPath().equals("/")) {
                bearerProperties
                        .add(this.dbusConnection.getRemoteObject(MM_BUS_NAME, bearerPath.getPath(), Properties.class));
            }
        }
        return bearerProperties;

    }

}
