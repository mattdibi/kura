package org.freedesktop.avahi;

import org.freedesktop.dbus.annotations.DBusInterfaceName;
import org.freedesktop.dbus.exceptions.DBusException;
import org.freedesktop.dbus.interfaces.DBusInterface;
import org.freedesktop.dbus.messages.DBusSignal;
import org.freedesktop.dbus.types.UInt32;

/**
 * Auto-generated class.
 */
@DBusInterfaceName("org.freedesktop.Avahi.DomainBrowser")
public interface DomainBrowser extends DBusInterface {

    public void Free();

    public void Start();

    public static class ItemNew extends DBusSignal {

        private final int iface;
        private final int protocol;
        private final String domain;
        private final UInt32 flags;

        public ItemNew(String _path, int _interface, int _protocol, String _domain, UInt32 _flags)
                throws DBusException {
            super(_path, _interface, _protocol, _domain, _flags);
            this.iface = _interface;
            this.protocol = _protocol;
            this.domain = _domain;
            this.flags = _flags;
        }

        public int getIface() {
            return this.iface;
        }

        public int getProtocol() {
            return this.protocol;
        }

        public String getDomain() {
            return this.domain;
        }

        public UInt32 getSignalFlags() {
            return this.flags;
        }

    }

    public static class ItemRemove extends DBusSignal {

        private final int iface;
        private final int protocol;
        private final String domain;
        private final UInt32 flags;

        public ItemRemove(String _path, int _interface, int _protocol, String _domain, UInt32 _flags)
                throws DBusException {
            super(_path, _interface, _protocol, _domain, _flags);
            this.iface = _interface;
            this.protocol = _protocol;
            this.domain = _domain;
            this.flags = _flags;
        }

        public int getIface() {
            return this.iface;
        }

        public int getProtocol() {
            return this.protocol;
        }

        public String getDomain() {
            return this.domain;
        }

        public UInt32 getSignalFlags() {
            return this.flags;
        }

    }

    public static class Failure extends DBusSignal {

        private final String error;

        public Failure(String _path, String _error) throws DBusException {
            super(_path, _error);
            this.error = _error;
        }

        public String getError() {
            return error;
        }

    }
}