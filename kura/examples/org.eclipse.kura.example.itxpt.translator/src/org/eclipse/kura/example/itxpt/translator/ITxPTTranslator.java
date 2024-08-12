package org.eclipse.kura.example.itxpt.translator;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.freedesktop.avahi.EntryGroup;
import org.freedesktop.avahi.Server2;
import org.freedesktop.dbus.DBusPath;
import org.freedesktop.dbus.connections.impl.DBusConnection;
import org.freedesktop.dbus.exceptions.DBusException;
import org.freedesktop.dbus.exceptions.DBusExecutionException;
import org.freedesktop.dbus.types.UInt16;
import org.freedesktop.dbus.types.UInt32;
import org.osgi.service.component.ComponentContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ITxPTTranslator {

    private static final Logger s_logger = LoggerFactory.getLogger(ITxPTTranslator.class);

    private static final String APP_ID = "org.eclipse.kura.example.itxpt.translator";

    private static final int AVAHI_IF_UNSPEC = -1; // Unspecified interface, publish on all interfaces
    private static final int AVAHI_PROTO_UNSPEC = -1; // Unspecified protocol, publish on all protocols

    private EntryGroup entryGroup = null;
    private DBusConnection dbusConn = null;

    protected void activate(ComponentContext componentContext) {
        s_logger.info("Bundle " + APP_ID + " has started!");

        try {
            this.dbusConn = DBusConnection.getConnection(DBusConnection.DEFAULT_SYSTEM_BUS_ADDRESS);
        } catch (DBusException | DBusExecutionException ex) {
            s_logger.error("{} dbus connection failed due to: ", APP_ID, ex);
        }

        try {
            // Retrieve Avahi version
            Server2 server = this.dbusConn.getRemoteObject("org.freedesktop.Avahi", "/", Server2.class);
            String version = server.GetVersionString();
            s_logger.info("Detected Avahi Daemon version: {}", version);

            // Register a new service
            DBusPath entryGroupPath = server.EntryGroupNew();
            this.entryGroup = this.dbusConn.getRemoteObject("org.freedesktop.Avahi", entryGroupPath.getPath(),
                    EntryGroup.class);

            List<String> txt = new ArrayList<>();
            txt.add("txtvers=1");
            txt.add("version=2.1.1");
            txt.add("system_type=Gateway");
            txt.add("system_model=Raspberry");
            txt.add("manufacturer=Eurotech");
            txt.add("serial_number=AAAABBB0000");
            txt.add("software_version=2.7.0");
            txt.add("hardware_version=2.7.0");
            txt.add("mac_address=dc:a6:32:e0:54:f0");
            txt.add("status=0");
            txt.add("xml_path=./");
            txt.add("services=inventory");

            this.entryGroup.AddService(AVAHI_IF_UNSPEC,         // interface
                    AVAHI_PROTO_UNSPEC,                         // protocol
                    new UInt32(0),                              // flags
                    "RaspberryPi-dc:a6:32:e0:54:f0_inventory",  // name
                    "_itxpt_http._tcp",   // type
                    "",                   // domain
                    "",                   // host
                    new UInt16(80),       // port
                    convertToTxtList(txt) // txt
            );
            this.entryGroup.Commit();

            s_logger.info("Registered DNS service");

        } catch (DBusException | DBusExecutionException ex) {
            s_logger.error("{} activate failed due to: ", APP_ID, ex);
        }

    }

    private List<List<Byte>> convertToTxtList(List<String> stringArray) {
        List<List<Byte>> output = new ArrayList<>();
        Charset charset = StandardCharsets.US_ASCII;
        for (String element : stringArray) {
            output.add(convertToListOfBytes(element.getBytes(charset)));
        }

        return output;
    }

    private List<Byte> convertToListOfBytes(byte[] bytes) {
        List<Byte> output = new ArrayList<>();
        for (byte elem : bytes) {
            output.add(elem);
        }

        return output;
    }

    protected void deactivate(ComponentContext componentContext) {
        s_logger.info("Bundle " + APP_ID + " has stopped!");

        try {
            this.entryGroup.Reset();
        } catch (DBusExecutionException ex) {
            s_logger.error("{} deactivate failed due to: ", APP_ID, ex);
        }

        s_logger.info("Unregistered all DNS services");
    }

}
