package org.eclipse.kura.example.itxpt.translator;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

import javax.jmdns.JmDNS;
import javax.jmdns.ServiceInfo;

import org.osgi.service.component.ComponentContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ITxPTTranslator {

    private static final Logger s_logger = LoggerFactory.getLogger(ITxPTTranslator.class);

    private static final String APP_ID = "org.eclipse.kura.example.itxpt.translator";

    private JmDNS jmdns;

    protected void activate(ComponentContext componentContext) {
        s_logger.info("Bundle " + APP_ID + " has started!");

        // Create a JmDNS instance
        try {
            InetAddress addr = InetAddress.getLocalHost();
            String hostname = InetAddress.getByName(addr.getHostName()).toString();
            this.jmdns = JmDNS.create(addr, hostname);
            ServiceInfo serviceInfo = ServiceInfo.create("_itxpt_http._tcp.local.", "example", 80,
                    "services=inventory");
            this.jmdns.registerService(serviceInfo);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        s_logger.info("Registered DNS service");
    }

    protected void deactivate(ComponentContext componentContext) {
        s_logger.info("Bundle " + APP_ID + " has stopped!");

        this.jmdns.unregisterAllServices();

        s_logger.info("Unregistered all DNS services");

    }

}
