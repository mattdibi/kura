package org.eclipse.kura.nm;

import org.freedesktop.ModemManager1;
import org.freedesktop.dbus.connections.impl.DBusConnection;
import org.freedesktop.dbus.exceptions.DBusException;

public class MMDbusWrapper {

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

}
