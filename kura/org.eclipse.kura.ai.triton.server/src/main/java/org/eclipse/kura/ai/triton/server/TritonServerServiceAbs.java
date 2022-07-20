package org.eclipse.kura.ai.triton.server;

import java.util.Map;

import org.eclipse.kura.configuration.ConfigurableComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class TritonServerServiceAbs implements ConfigurableComponent {

    protected static final Logger logger = LoggerFactory.getLogger(TritonServerServiceAbs.class);
    protected String shared = "default";

    protected void activate(Map<String, Object> properties) {
        logger.info("Activate TritonServerServiceAbs...");
        updated(properties);
    }

    public void updated(Map<String, Object> properties) {
        logger.info("Update TritonServerServiceAbs...");
        TritonServerServiceOptions newOptions = new TritonServerServiceOptions(properties);

        logger.info(newOptions.toString());

        this.shared = buildSharedString();

        logger.info("Shared string: ");
        logger.info(shared);
    }

    abstract String buildSharedString();

    protected void deactivate() {
        logger.info("Deactivate TritonServerService...");
    }
}
