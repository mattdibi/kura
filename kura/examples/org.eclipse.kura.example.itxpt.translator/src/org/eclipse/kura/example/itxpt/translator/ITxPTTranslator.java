package org.eclipse.kura.example.itxpt.translator;

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
    private EntryGroupSignalHandler entryGroupHandler = new EntryGroupSignalHandler();
    private ServerSignalHandler serverHandler = new ServerSignalHandler();
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

            // Add signal handler
            this.dbusConn.addSigHandler(EntryGroup.StateChanged.class, this.entryGroupHandler);
            this.dbusConn.addSigHandler(Server2.StateChanged.class, this.serverHandler);

            // Register a new service
            DBusPath entryGroupPath = server.EntryGroupNew();
            this.entryGroup = this.dbusConn.getRemoteObject("org.freedesktop.Avahi", entryGroupPath.getPath(),
                    EntryGroup.class);

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
            Charset charset = Charset.forName("ASCII");
            for (String element : list) {
                txt.add(convertToListOfBytes(element.getBytes(charset)));
            }

            this.entryGroup.AddService(AVAHI_IF_UNSPEC,         // interface
                    AVAHI_PROTO_UNSPEC,                         // protocol
                    new UInt32(0),                              // flags
                    "RaspberryPi-dc:a6:32:e0:54:f0_inventory",  // name
                    "_itxpt_http._tcp",  // type
                    "",                  // domain
                    "",                  // host
                    new UInt16(80),      // port
                    txt                  // txt
            );
            this.entryGroup.Commit();

            s_logger.info("Registered DNS service");

        } catch (DBusException | DBusExecutionException ex) {
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
            this.dbusConn.removeSigHandler(EntryGroup.StateChanged.class, this.entryGroupHandler);
            this.dbusConn.removeSigHandler(Server2.StateChanged.class, this.serverHandler);
        } catch (DBusException e) {
            s_logger.error("{} sig handler deactivate failed due to: ", APP_ID, e);
        }

        try {
            this.entryGroup.Reset();
        } catch (DBusExecutionException ex) {
            s_logger.error("{} deactivate failed due to: ", APP_ID, ex);
        }

        s_logger.info("Unregistered all DNS services");

    }

}
