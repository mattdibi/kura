package org.freedesktop.avahi;

import org.freedesktop.dbus.Tuple;
import org.freedesktop.dbus.annotations.Position;

/**
 * Auto-generated class.
 */
public class ResolveServiceTuple extends Tuple {
    @Position(0)
    private int interfaceparam;
    @Position(1)
    private int protocol;
    @Position(2)
    private String name;
    @Position(3)
    private String type;
    @Position(4)
    private String domain;
    @Position(5)
    private String host;
    @Position(6)
    private int aprotocol;
    @Position(7)
    private String address;
    @Position(8)
    private UInt16 port;
    @Position(9)
    private List<List<Byte>> txt;
    @Position(10)
    private UInt32 flags;

    public ResolveServiceTuple(int interfaceparam, int protocol, String name, String type, String domain, String host, int aprotocol, String address, UInt16 port, List<List<Byte>> txt, UInt32 flags) {
        this.interfaceparam = interfaceparam;
        this.protocol = protocol;
        this.name = name;
        this.type = type;
        this.domain = domain;
        this.host = host;
        this.aprotocol = aprotocol;
        this.address = address;
        this.port = port;
        this.txt = txt;
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
    public void setType(String arg) {
        type = arg;
    }

    public String getType() {
        return type;
    }
    public void setDomain(String arg) {
        domain = arg;
    }

    public String getDomain() {
        return domain;
    }
    public void setHost(String arg) {
        host = arg;
    }

    public String getHost() {
        return host;
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
    public void setPort(UInt16 arg) {
        port = arg;
    }

    public UInt16 getPort() {
        return port;
    }
    public void setTxt(List<List<Byte>> arg) {
        txt = arg;
    }

    public List<List<Byte>> getTxt() {
        return txt;
    }
    public void setFlags(UInt32 arg) {
        flags = arg;
    }

    public UInt32 getFlags() {
        return flags;
    }


}