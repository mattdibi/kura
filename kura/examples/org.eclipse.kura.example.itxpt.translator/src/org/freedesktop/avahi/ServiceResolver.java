package org.freedesktop.avahi;

import java.util.List;
import org.freedesktop.dbus.annotations.DBusInterfaceName;
import org.freedesktop.dbus.exceptions.DBusException;
import org.freedesktop.dbus.interfaces.DBusInterface;
import org.freedesktop.dbus.messages.DBusSignal;
import org.freedesktop.dbus.types.UInt16;
import org.freedesktop.dbus.types.UInt32;

/**
 * Auto-generated class.
 */
@DBusInterfaceName("org.freedesktop.Avahi.ServiceResolver")
public interface ServiceResolver extends DBusInterface {


    public void Free();
    public void Start();


    public static class Found extends DBusSignal {

        private final int interfaceparam;
        private final int protocol;
        private final String name;
        private final String type;
        private final String domain;
        private final String host;
        private final int aprotocol;
        private final String address;
        private final UInt16 port;
        private final List<List<Byte>> txt;
        private final UInt32 flags;

        public Found(String _path, int _interface, int _protocol, String _name, String _type, String _domain, String _host, int _aprotocol, String _address, UInt16 _port, List<List<Byte>> _txt, UInt32 _flags) throws DBusException {
            super(_path, _interface, _protocol, _name, _type, _domain, _host, _aprotocol, _address, _port, _txt, _flags);
            this.interface = _interface;
            this.protocol = _protocol;
            this.name = _name;
            this.type = _type;
            this.domain = _domain;
            this.host = _host;
            this.aprotocol = _aprotocol;
            this.address = _address;
            this.port = _port;
            this.txt = _txt;
            this.flags = _flags;
        }


        public int getInterfaceparam() {
            return interfaceparam;
        }

        public int getProtocol() {
            return protocol;
        }

        public String getName() {
            return name;
        }

        public String getType() {
            return type;
        }

        public String getDomain() {
            return domain;
        }

        public String getHost() {
            return host;
        }

        public int getAprotocol() {
            return aprotocol;
        }

        public String getAddress() {
            return address;
        }

        public UInt16 getPort() {
            return port;
        }

        public List<List<Byte>> getTxt() {
            return txt;
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