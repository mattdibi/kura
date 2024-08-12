package org.eclipse.kura.example.itxpt.translator;

import org.freedesktop.avahi.Server2;
import org.freedesktop.avahi.Server2.StateChanged;
import org.freedesktop.dbus.interfaces.DBusSigHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ServerSignalHandler implements DBusSigHandler<Server2.StateChanged> {

    private static final Logger logger = LoggerFactory.getLogger(ServerSignalHandler.class);

    public ServerSignalHandler() {
    }

    @Override
    public void handle(StateChanged arg0) {
        logger.info("Server2 state changed: {} (err: {})", new Integer(arg0.getState()).toString(), arg0.getError());
    }

}
