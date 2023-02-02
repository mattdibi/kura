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
package org.eclipse.kura.nm.configuration;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.freedesktop.dbus.types.Variant;
import org.freedesktop.networkmanager.settings.Connection;
import org.junit.Test;

public class NMSettingsConverterTest {

	Map<String, Variant<?>> internalComparatorMap;
	Map<String, Variant<?>> resultMap;
	
	Map<String, Map<String, Variant<?>>> internalComparatorAllSettingsMap;
	Map<String, Map<String, Variant<?>>> resultAllSettingsMap;
	
	NetworkProperties props;

	@Test(expected=IllegalArgumentException.class)
	public void shouldThrowErrorWhenBuildSettingsWithEmptyMap() {
		//TODO
		givenTheExpectedMapOutputBuild80211WirelessSecuritySettings();
		givenBlankNetworkProps();
		whenBuildSettingsIsRunWithNetworkPropsAndIfaceString(this.props, Optional.empty(), "wlan0", NMDeviceType.NM_DEVICE_TYPE_WIFI);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void shouldThrowErrorWhenBuildIpv4SettingsWithEmptyMap() {
		//TODO
		givenTheExpectedMapOutputBuild80211WirelessSecuritySettings();
		givenBlankNetworkProps();
		whenBuildIpv4SettingsIsRunWithNetworkPropsAndIfaceString(this.props, "wlan0");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void shouldThrowErrorWhenBuildIpv6SettingsWithEmptyMap() {
		//TODO
		givenTheExpectedMapOutputBuild80211WirelessSecuritySettings();
		givenBlankNetworkProps();
		whenBuildIpv6SettingsIsRunWithNetworkPropsAndIfaceString(this.props, "wlan0");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void shouldThrowErrorWhenBuild80211WirelessSettingsWithEmptyMap() {
		//TODO
		givenTheExpectedMapOutputBuild80211WirelessSecuritySettings();
		givenBlankNetworkProps();
		whenBuild80211WirelessSettingsIsRunWithNetworkPropsAndIfaceString(this.props, "wlan0");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void shouldThrowErrorWhenBuild80211WirelessSecuritySettingsWithEmptyMap() {
		givenTheExpectedMapOutputBuild80211WirelessSecuritySettings();
		givenBlankNetworkProps();
		whenBuild80211WirelessSecuritySettingsIsRunWithNetworkPropsAndIfaceString(this.props, "wlan0");
	}

	// given

	public void givenTheExpectedMapOutputBuild80211WirelessSecuritySettings() {
		internalComparatorMap = new HashMap<>();
	}
	
	public void givenBlankNetworkProps() {
		this.props = new NetworkProperties(new HashMap<String, Object>());
	}
	

	// when

	public void whenBuildSettingsIsRunWithNetworkPropsAndIfaceString(NetworkProperties properties,
            Optional<Connection> oldConnection, String iface, NMDeviceType deviceType) {
		this.resultAllSettingsMap = NMSettingsConverter.buildSettings(properties, oldConnection, iface, deviceType);
	}
	
	public void whenBuildIpv4SettingsIsRunWithNetworkPropsAndIfaceString(NetworkProperties props, String iface) {
		this.resultMap = NMSettingsConverter.buildIpv4Settings(props, iface);
	}
	
	public void whenBuildIpv6SettingsIsRunWithNetworkPropsAndIfaceString(NetworkProperties props, String iface) {
		this.resultMap = NMSettingsConverter.buildIpv6Settings(props, iface);
	}
	
	public void whenBuild80211WirelessSettingsIsRunWithNetworkPropsAndIfaceString(NetworkProperties props, String iface) {
		this.resultMap = NMSettingsConverter.build80211WirelessSettings(props, iface);
	}
	
	public void whenBuild80211WirelessSecuritySettingsIsRunWithNetworkPropsAndIfaceString(NetworkProperties props, String iface) {
		this.resultMap = NMSettingsConverter.build80211WirelessSecuritySettings(props, iface);
	}

	// then

	public void thenMapResultShouldEqualInternalMap() {
		assertEquals(this.resultMap, this.internalComparatorMap);
	}

}

