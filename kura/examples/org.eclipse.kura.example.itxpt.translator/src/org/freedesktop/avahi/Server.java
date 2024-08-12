package org.freedesktop.avahi;

import java.util.List;
import org.freedesktop.dbus.DBusPath;
import org.freedesktop.dbus.annotations.DBusInterfaceName;
import org.freedesktop.dbus.exceptions.DBusException;
import org.freedesktop.dbus.interfaces.DBusInterface;
import org.freedesktop.dbus.messages.DBusSignal;
import org.freedesktop.dbus.types.UInt16;
import org.freedesktop.dbus.types.UInt32;

/**
 * Auto-generated class.
 */
@DBusInterfaceName("org.freedesktop.Avahi.Server")
public interface Server extends DBusInterface {


    public String GetVersionString();
    public UInt32 GetAPIVersion();
    public String GetHostName();
    public void SetHostName(String name);
    public String GetHostNameFqdn();
    public String GetDomainName();
    public boolean IsNSSSupportAvailable();
    public int GetState();
    public UInt32 GetLocalServiceCookie();
    public String GetAlternativeHostName(String name);
    public String GetAlternativeServiceName(String name);
    public String GetNetworkInterfaceNameByIndex(int index);
    public int GetNetworkInterfaceIndexByName(String name);
    public ResolveHostNameTuple ResolveHostName(int interfaceparam, int protocol, String name, int aprotocol, UInt32 flags);
    public ResolveAddressTuple ResolveAddress(int interfaceparam, int protocol, String address, UInt32 flags);
    public ResolveServiceTuple ResolveService(int interfaceparam, int protocol, String name, String type, String domain, int aprotocol, UInt32 flags);
    public DBusPath EntryGroupNew();
    public DBusPath DomainBrowserNew(int interfaceparam, int protocol, String domain, int btype, UInt32 flags);
    public DBusPath ServiceTypeBrowserNew(int interfaceparam, int protocol, String domain, UInt32 flags);
    public DBusPath ServiceBrowserNew(int interfaceparam, int protocol, String type, String domain, UInt32 flags);
    public DBusPath ServiceResolverNew(int interfaceparam, int protocol, String name, String type, String domain, int aprotocol, UInt32 flags);
    public DBusPath HostNameResolverNew(int interfaceparam, int protocol, String name, int aprotocol, UInt32 flags);
    public DBusPath AddressResolverNew(int interfaceparam, int protocol, String address, UInt32 flags);
    public DBusPath RecordBrowserNew(int interfaceparam, int protocol, String name, UInt16 clazz, UInt16 type, UInt32 flags);


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