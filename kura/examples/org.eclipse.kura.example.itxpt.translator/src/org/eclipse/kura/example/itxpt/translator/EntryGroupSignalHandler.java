package org.eclipse.kura.example.itxpt.translator;

import org.freedesktop.avahi.EntryGroup;
import org.freedesktop.avahi.EntryGroup.StateChanged;
import org.freedesktop.dbus.interfaces.DBusSigHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EntryGroupSignalHandler implements DBusSigHandler<EntryGroup.StateChanged> {

    private static final Logger logger = LoggerFactory.getLogger(EntryGroupSignalHandler.class);

    public EntryGroupSignalHandler() {
    }

    @Override
    public void handle(StateChanged arg0) {
        logger.info("Entry group state changed: {} (err: {})", new Integer(arg0.getState()).toString(),
                arg0.getError());
    }

}
