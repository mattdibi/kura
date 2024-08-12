package org.eclipse.kura.example.itxpt.translator;

import org.osgi.service.component.ComponentContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ITxPTTranslator {

    private static final Logger s_logger = LoggerFactory.getLogger(ITxPTTranslator.class);

    private static final String APP_ID = "org.eclipse.kura.example.itxpt.translator";

    protected void activate(ComponentContext componentContext) {
        s_logger.info("Bundle " + APP_ID + " has started!");

        s_logger.info("Registered DNS service");
    }

    protected void deactivate(ComponentContext componentContext) {
        s_logger.info("Bundle " + APP_ID + " has stopped!");

        s_logger.info("Unregistered all DNS services");

    }

}
