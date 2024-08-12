package org.freedesktop.avahi;

import org.freedesktop.dbus.Tuple;
import org.freedesktop.dbus.annotations.Position;
import org.freedesktop.dbus.types.UInt32;

/**
 * Auto-generated class.
 */
public class ResolveHostNameTuple extends Tuple {

    @Position(0)
    private int interfaceparam;
    @Position(1)
    private int protocol;
    @Position(2)
    private String name;
    @Position(3)
    private int aprotocol;
    @Position(4)
    private String address;
    @Position(5)
    private UInt32 flags;

    public ResolveHostNameTuple(int interfaceparam, int protocol, String name, int aprotocol, String address,
            UInt32 flags) {
        this.interfaceparam = interfaceparam;
        this.protocol = protocol;
        this.name = name;
        this.aprotocol = aprotocol;
        this.address = address;
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

    public void setName(String arg) {
        name = arg;
    }

    public String getName() {
        return name;
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

    public void setFlags(UInt32 arg) {
        flags = arg;
    }

    public UInt32 getFlags() {
        return flags;
    }

}