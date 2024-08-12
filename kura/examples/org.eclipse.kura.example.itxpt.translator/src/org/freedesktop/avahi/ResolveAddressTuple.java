package org.freedesktop.avahi;

import org.freedesktop.dbus.Tuple;
import org.freedesktop.dbus.annotations.Position;

/**
 * Auto-generated class.
 */
public class ResolveAddressTuple extends Tuple {
    @Position(0)
    private int interfaceparam;
    @Position(1)
    private int protocol;
    @Position(2)
    private int aprotocol;
    @Position(3)
    private String address;
    @Position(4)
    private String name;
    @Position(5)
    private UInt32 flags;

    public ResolveAddressTuple(int interfaceparam, int protocol, int aprotocol, String address, String name, UInt32 flags) {
        this.interfaceparam = interfaceparam;
        this.protocol = protocol;
        this.aprotocol = aprotocol;
        this.address = address;
        this.name = name;
        this.flags = flags;
    }

    public void setInterfaceparam(int arg) {
        interfaceparam = arg;
    }

    public int getInterfaceparam() {
        return interfaceparam;
    }
    public void setProtocol(int arg) {
        protocol = arg;
    }

    public int getProtocol() {
        return protocol;
    }
    public void setAprotocol(int arg) {
        aprotocol = arg;
    }

    public int getAprotocol() {
        return aprotocol;
    }
    public void setAddress(String arg) {
        address = arg;
    }

    public String getAddress() {
        return address;
    }
    public void setName(String arg) {
        name = arg;
    }

    public String getName() {
        return name;
    }
    public void setFlags(UInt32 arg) {
        flags = arg;
    }

    public UInt32 getFlags() {
        return flags;
    }


}