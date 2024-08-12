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
@DBusInterfaceName("org.freedesktop.Avahi.RecordBrowser")
public interface RecordBrowser extends DBusInterface {


    public void Free();
    public void Start();


    public static class ItemNew extends DBusSignal {

        private final int interfaceparam;
        private final int protocol;
        private final String name;
        private final UInt16 clazz;
        private final UInt16 type;
        private final List<Byte> rdata;
        private final UInt32 flags;

        public ItemNew(String _path, int _interface, int _protocol, String _name, UInt16 _clazz, UInt16 _type, List<Byte> _rdata, UInt32 _flags) throws DBusException {
            super(_path, _interface, _protocol, _name, _clazz, _type, _rdata, _flags);
            this.interface = _interface;
            this.protocol = _protocol;
            this.name = _name;
            this.clazz = _clazz;
            this.type = _type;
            this.rdata = _rdata;
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

        public UInt16 getClazz() {
            return clazz;
        }

        public UInt16 getType() {
            return type;
        }

        public List<Byte> getRdata() {
            return rdata;
        }

        public UInt32 getFlags() {
            return flags;
        }


    }

    public static class ItemRemove extends DBusSignal {

        private final int interfaceparam;
        private final int protocol;
        private final String name;
        private final UInt16 clazz;
        private final UInt16 type;
        private final List<Byte> rdata;
        private final UInt32 flags;

        public ItemRemove(String _path, int _interface, int _protocol, String _name, UInt16 _clazz, UInt16 _type, List<Byte> _rdata, UInt32 _flags) throws DBusException {
            super(_path, _interface, _protocol, _name, _clazz, _type, _rdata, _flags);
            this.interface = _interface;
            this.protocol = _protocol;
            this.name = _name;
            this.clazz = _clazz;
            this.type = _type;
            this.rdata = _rdata;
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

        public UInt16 getClazz() {
            return clazz;
        }

        public UInt16 getType() {
            return type;
        }

        public List<Byte> getRdata() {
            return rdata;
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