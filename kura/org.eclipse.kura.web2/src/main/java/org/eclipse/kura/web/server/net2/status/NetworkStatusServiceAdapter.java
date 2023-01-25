/*******************************************************************************
 * Copyright (c) 2023 Eurotech and/or its affiliates and others
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 * 
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *  Eurotech
 *******************************************************************************/
package org.eclipse.kura.web.server.net2.status;

import java.util.Arrays;

import org.eclipse.kura.net.NetInterfaceType;
import org.eclipse.kura.web.shared.model.GwtModemInterfaceConfig;
import org.eclipse.kura.web.shared.model.GwtNetIfConfigMode;
import org.eclipse.kura.web.shared.model.GwtNetInterfaceConfig;
import org.eclipse.kura.web.shared.model.GwtWifiNetInterfaceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Adapter to convert status-related properties to a
 * {@link GwtNetInterfaceConfig} object.
 *
 */
public class NetworkStatusServiceAdapter {

    private static final String NA = "N/A";
    private static final Logger logger = LoggerFactory.getLogger(NetworkStatusServiceAdapter.class);

    public NetworkStatusServiceAdapter() {
        // TODO init status services here
    }

    public GwtNetInterfaceConfig getGwtNetInterfaceConfig(String ifname, String iftype) {
        GwtNetInterfaceConfig gwtConfig = createGwtNetInterfaceConfig(iftype);

        setCommonStateProperties(gwtConfig, ifname);
        if (gwtConfig.getConfigMode().equals(GwtNetIfConfigMode.netIPv4ConfigModeDHCP.name())) {
            setIpv4StateProperties(gwtConfig, ifname);
        }
        setModemStateProperties(gwtConfig, ifname);

        return gwtConfig;
    }

    private GwtNetInterfaceConfig createGwtNetInterfaceConfig(String iftype) {
        if (iftype.equals(NetInterfaceType.WIFI.name())) {
            return new GwtWifiNetInterfaceConfig();
        }

        if (iftype.equals(NetInterfaceType.MODEM.name())) {
            return new GwtModemInterfaceConfig();
        }

        return new GwtNetInterfaceConfig();
    }

    private void setCommonStateProperties(GwtNetInterfaceConfig gwtConfig, String ifname) {
        gwtConfig.setHwState(NA);
        gwtConfig.setHwAddress(NA); // MAC address
        gwtConfig.setHwDriver(NA);
        gwtConfig.setHwDriverVersion(NA);
        gwtConfig.setHwFirmware(NA);
        gwtConfig.setHwMTU(99);
        gwtConfig.setHwUsbDevice(NA);
        gwtConfig.setHwSerial(NA);
        gwtConfig.setHwRssi(NA);
    }

    private void setIpv4StateProperties(GwtNetInterfaceConfig gwtConfig, String ifname) {
        // fetch ip address, mask, gateway, dns
        // set readonly dns servers if in dhcp client mode (if here, then last is true)
    }

    private void setModemStateProperties(GwtNetInterfaceConfig gwtConfig, String ifname) {
        if (gwtConfig instanceof GwtModemInterfaceConfig) {
            GwtModemInterfaceConfig gwtModemConfig = (GwtModemInterfaceConfig) gwtConfig;

            gwtModemConfig.setHwSerial(NA); // imei
            gwtModemConfig.setHwRssi(NA); // Integer.toString(rssi)
            gwtModemConfig.setHwICCID(NA);
            gwtModemConfig.setHwIMSI(NA);
            gwtModemConfig.setHwRegistration(NA);
            gwtModemConfig.setHwPLMNID(NA);
            gwtModemConfig.setHwNetwork(NA);
            gwtModemConfig.setHwRadio(NA);
            gwtModemConfig.setHwBand(NA);
            gwtModemConfig.setHwLAC(NA);
            gwtModemConfig.setHwCI(NA);
            gwtModemConfig.setGpsSupported(false);
            gwtModemConfig.setHwFirmware(NA); // firmware version
            gwtModemConfig.setConnectionType("PPP"); // PPP or DirectIP
            gwtModemConfig.setNetworkTechnology(Arrays.asList(NA)); // HSPDA/EVMO/...

            gwtModemConfig.setIpAddress("10.10.10.10");
            gwtModemConfig.setSubnetMask("255.255.255.0");
            gwtModemConfig.setGateway("10.10.10.1");
        }
    }

}
