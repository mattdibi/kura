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
@DBusInterfaceName("org.freedesktop.Avahi.EntryGroup")
public interface EntryGroup extends DBusInterface {


    public void Free();
    public void Commit();
    public void Reset();
    public int GetState();
    public boolean IsEmpty();
    public void AddService(int interfaceparam, int protocol, UInt32 flags, String name, String type, String domain, String host, UInt16 port, List<List<Byte>> txt);
    public void AddServiceSubtype(int interfaceparam, int protocol, UInt32 flags, String name, String type, String domain, String subtype);
    public void UpdateServiceTxt(int interfaceparam, int protocol, UInt32 flags, String name, String type, String domain, List<List<Byte>> txt);
    public void AddAddress(int interfaceparam, int protocol, UInt32 flags, String name, String address);
    public void AddRecord(int interfaceparam, int protocol, UInt32 flags, String name, UInt16 clazz, UInt16 type, UInt32 ttl, List<Byte> rdata);


    public static class StateChanged extends DBusSignal {

        private final int state;
        private final String error;

        public StateChanged(String _path, int _state, String _error) throws DBusException {
            super(_path, _state, _error);
            this.state = _state;
            this.error = _error;
        }


        public int getState() {
            return state;
        }

        public String getError() {
            return error;
        }


    }
}