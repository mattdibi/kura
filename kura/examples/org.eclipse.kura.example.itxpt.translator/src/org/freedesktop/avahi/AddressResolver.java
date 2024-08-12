package org.freedesktop.avahi;

import org.freedesktop.dbus.annotations.DBusInterfaceName;
import org.freedesktop.dbus.exceptions.DBusException;
import org.freedesktop.dbus.interfaces.DBusInterface;
import org.freedesktop.dbus.messages.DBusSignal;
import org.freedesktop.dbus.types.UInt32;

/**
 * Auto-generated class.
 */
@DBusInterfaceName("org.freedesktop.Avahi.AddressResolver")
public interface AddressResolver extends DBusInterface {


    public void Free();
    public void Start();


    public static class Found extends DBusSignal {

        private final int interfaceparam;
        private final int protocol;
        private final int aprotocol;
        private final String address;
        private final String name;
        private final UInt32 flags;

        public Found(String _path, int _interface, int _protocol, int _aprotocol, String _address, String _name, UInt32 _flags) throws DBusException {
            super(_path, _interface, _protocol, _aprotocol, _address, _name, _flags);
            this.interface = _interface;
            this.protocol = _protocol;
            this.aprotocol = _aprotocol;
            this.address = _address;
            this.name = _name;
            this.flags = _flags;
        }


        public int getInterfaceparam() {
            return interfaceparam;
        }

        public int getProtocol() {
            return protocol;
        }

        public int getAprotocol() {
            return aprotocol;
        }

        public String getAddress() {
            return address;
        }

        public String getName() {
            return name;
        }

        public UInt32 getFlags() {
            return flags;
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