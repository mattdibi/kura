package org.eclipse.kura.example.itxpt.translator;

import java.io.IOException;
import java.nio.charset.Charset;
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

    private static final int AVAHI_IF_UNSPEC = -1;
    private static final int AVAHI_PROTO_UNSPEC = -1;

    private EntryGroup entryGroup = null;

    protected void activate(ComponentContext componentContext) {
        s_logger.info("Bundle " + APP_ID + " has started!");

        try (DBusConnection dbusConn = DBusConnection.getConnection(DBusConnection.DEFAULT_SYSTEM_BUS_ADDRESS)) {

            // Retrieve Avahi version
            Server2 server = dbusConn.getRemoteObject("org.freedesktop.Avahi", "/", Server2.class);
            String version = server.GetVersionString();
            s_logger.info("Detected Avahi Daemon version: {}", version);

            // Register a new service
            DBusPath entryGroupPath = server.EntryGroupNew();
            this.entryGroup = dbusConn.getRemoteObject("org.freedesktop.Avahi", entryGroupPath.getPath(),
                    EntryGroup.class);

            Charset charset = Charset.forName("ASCII");

            List<String> list = new ArrayList<>();
            list.add("txtvers=1");
            list.add("version=2.1.1");
            list.add("system_type=Gateway");
            list.add("system_model=Raspberry");
            list.add("manufacturer=Eurotech");
            list.add("serial_number=AAAABBB0000");
            list.add("software_version=2.7.0");
            list.add("hardware_version=2.7.0");
            list.add("mac_address=dc:a6:32:e0:54:f0");
            list.add("status=0");
            list.add("xml_path=./");
            list.add("services=inventory");

            List<List<Byte>> txt = new ArrayList<>();
            for (String element : list) {
                txt.add(convertToListOfBytes(element.getBytes(charset)));
            }

            this.entryGroup.AddService(AVAHI_IF_UNSPEC,         // interface
                    AVAHI_PROTO_UNSPEC,                         // protocol
                    new UInt32(0),                              // flags
                    "RaspberryPi-dc:a6:32:e0:54:f0_inventory",  // name
                    "_itxpt_http._tcp",  // type
                    "local",             // domain
                    "raspberrypi.local", // host
                    new UInt16(80),      // port
                    txt                  // txt
            );
            this.entryGroup.Commit();

            s_logger.info("Registered DNS service");

        } catch (IOException | DBusException | DBusExecutionException ex) {
            s_logger.error("{} activate failed due to: ", APP_ID, ex);
        }

    }

    private List<Byte> convertToListOfBytes(byte[] bytes) {
        List<Byte> output = new ArrayList<>();
        for (byte el : bytes) {
            output.add(el);
        }

        return output;
    }

    protected void deactivate(ComponentContext componentContext) {
        s_logger.info("Bundle " + APP_ID + " has stopped!");

        try {
            this.entryGroup.Free();
        } catch (DBusExecutionException ex) {
            s_logger.error("{} deactivate failed due to: ", APP_ID, ex);
        }

        s_logger.info("Unregistered all DNS services");

    }

}
