package org.eclipse.kura.example.itxpt.translator;

import java.io.IOException;

import org.freedesktop.avahi.Server2;
import org.freedesktop.dbus.connections.impl.DBusConnection;
import org.freedesktop.dbus.exceptions.DBusException;
import org.osgi.service.component.ComponentContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ITxPTTranslator {

    private static final Logger s_logger = LoggerFactory.getLogger(ITxPTTranslator.class);

    private static final String APP_ID = "org.eclipse.kura.example.itxpt.translator";

    protected void activate(ComponentContext componentContext) {
        s_logger.info("Bundle " + APP_ID + " has started!");

        try (DBusConnection dbusConn = DBusConnection.getConnection(DBusConnection.DEFAULT_SYSTEM_BUS_ADDRESS)) {
            Server2 server = dbusConn.getRemoteObject("org.freedesktop.Avahi", "/", Server2.class);

            String version = server.GetVersionString();

            s_logger.info("Detected Avahi Daemon version: {}", version);
        } catch (IOException | DBusException _ex) {
            _ex.printStackTrace();
        }

        s_logger.info("Registered DNS service");
    }

    protected void deactivate(ComponentContext componentContext) {
        s_logger.info("Bundle " + APP_ID + " has stopped!");

        s_logger.info("Unregistered all DNS services");

    }

}
