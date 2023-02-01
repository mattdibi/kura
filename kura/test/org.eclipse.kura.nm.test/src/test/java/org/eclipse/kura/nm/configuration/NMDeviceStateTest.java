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
 ******************************************************************************/
package org.eclipse.kura.nm.configuration;

import static org.junit.Assert.assertEquals;

import org.eclipse.kura.KuraException;
import org.freedesktop.dbus.types.UInt32;
import org.junit.Test;

public class NMDeviceStateTest {

	NMDeviceState state;

	@Test
	public void conversionWorksForStateUnknown() {
		whenInt32StateIsPassed(new UInt32(0));
		thenStateShouldBeEqualTo(NMDeviceState.NM_DEVICE_STATE_UNKNOWN);
	}

	@Test
	public void conversionWorksForStateUnmanaged() {
		whenInt32StateIsPassed(new UInt32(10));
		thenStateShouldBeEqualTo(NMDeviceState.NM_DEVICE_STATE_UNMANAGED);
	}

	@Test
	public void conversionWorksForStateUnavailable() {
		whenInt32StateIsPassed(new UInt32(20));
		thenStateShouldBeEqualTo(NMDeviceState.NM_DEVICE_STATE_UNAVAILABLE);
	}

	@Test
	public void conversionWorksForStateDisconnected() {
		whenInt32StateIsPassed(new UInt32(30));
		thenStateShouldBeEqualTo(NMDeviceState.NM_DEVICE_STATE_DISCONNECTED);
	}

	@Test
	public void conversionWorksForStatePrepare() {
		whenInt32StateIsPassed(new UInt32(40));
		thenStateShouldBeEqualTo(NMDeviceState.NM_DEVICE_STATE_PREPARE);
	}

	@Test
	public void conversionWorksForStateConfig() {
		whenInt32StateIsPassed(new UInt32(50));
		thenStateShouldBeEqualTo(NMDeviceState.NM_DEVICE_STATE_CONFIG);
	}

	@Test
	public void conversionWorksForStateNeedAuth() {
		whenInt32StateIsPassed(new UInt32(60));
		thenStateShouldBeEqualTo(NMDeviceState.NM_DEVICE_STATE_NEED_AUTH);
	}

	@Test
	public void conversionWorksForStateIpConfig() {
		whenInt32StateIsPassed(new UInt32(70));
		thenStateShouldBeEqualTo(NMDeviceState.NM_DEVICE_STATE_IP_CONFIG);
	}

	@Test
	public void conversionWorksForStateIpCheck() {
		whenInt32StateIsPassed(new UInt32(80));
		thenStateShouldBeEqualTo(NMDeviceState.NM_DEVICE_STATE_IP_CHECK);
	}

	@Test
	public void conversionWorksForStateSecondaries() {
		whenInt32StateIsPassed(new UInt32(90));
		thenStateShouldBeEqualTo(NMDeviceState.NM_DEVICE_STATE_SECONDARIES);
	}

	@Test
	public void conversionWorksForStateActivated() {
		whenInt32StateIsPassed(new UInt32(100));
		thenStateShouldBeEqualTo(NMDeviceState.NM_DEVICE_STATE_ACTIVATED);
	}

	@Test
	public void conversionWorksForStateDeactivating() {
		whenInt32StateIsPassed(new UInt32(110));
		thenStateShouldBeEqualTo(NMDeviceState.NM_DEVICE_STATE_DEACTIVATING);
	}

	@Test
	public void conversionWorksForStateFailed() {
		whenInt32StateIsPassed(new UInt32(120));
		thenStateShouldBeEqualTo(NMDeviceState.NM_DEVICE_STATE_FAILED);
	}

	@Test
	public void conversionWorksForStateUnknownDefault() {
		whenInt32StateIsPassed(new UInt32(121));
		thenStateShouldBeEqualTo(NMDeviceState.NM_DEVICE_STATE_UNKNOWN);
	}

	@Test
	public void shouldReturnConnectionFalseUnknown() {
		whenStateIsSetTo(NMDeviceState.NM_DEVICE_STATE_UNKNOWN);
		thenStateShouldBeEqualTo(false);
	}

	@Test
	public void shouldReturnConnectionFalseUnmanaged() {
		whenStateIsSetTo(NMDeviceState.NM_DEVICE_STATE_UNMANAGED);
		thenStateShouldBeEqualTo(false);
	}

	@Test
	public void shouldReturnConnectionFalseUnavailable() {
		whenStateIsSetTo(NMDeviceState.NM_DEVICE_STATE_UNAVAILABLE);
		thenStateShouldBeEqualTo(false);
	}

	@Test
	public void shouldReturnConnectionFalseDisconnected() {
		whenStateIsSetTo(NMDeviceState.NM_DEVICE_STATE_DISCONNECTED);
		thenStateShouldBeEqualTo(false);
	}

	@Test
	public void shouldReturnConnectionTruePrepare() {
		whenStateIsSetTo(NMDeviceState.NM_DEVICE_STATE_PREPARE);
		thenStateShouldBeEqualTo(true);
	}

	@Test
	public void shouldReturnConnectionTrueConfig() {
		whenStateIsSetTo(NMDeviceState.NM_DEVICE_STATE_CONFIG);
		thenStateShouldBeEqualTo(true);
	}

	@Test
	public void shouldReturnConnectionTrueNeedAuth() {
		whenStateIsSetTo(NMDeviceState.NM_DEVICE_STATE_NEED_AUTH);
		thenStateShouldBeEqualTo(true);
	}

	@Test
	public void shouldReturnConnectionTrueIpConfig() {
		whenStateIsSetTo(NMDeviceState.NM_DEVICE_STATE_IP_CONFIG);
		thenStateShouldBeEqualTo(true);
	}

	@Test
	public void shouldReturnConnectionTrueIpCheck() {
		whenStateIsSetTo(NMDeviceState.NM_DEVICE_STATE_IP_CHECK);
		thenStateShouldBeEqualTo(true);
	}

	@Test
	public void shouldReturnConnectionTrueSecondaries() {
		whenStateIsSetTo(NMDeviceState.NM_DEVICE_STATE_SECONDARIES);
		thenStateShouldBeEqualTo(true);
	}

	@Test
	public void shouldReturnConnectionTrueActivated() {
		whenStateIsSetTo(NMDeviceState.NM_DEVICE_STATE_ACTIVATED);
		thenStateShouldBeEqualTo(true);
	}

	@Test
	public void shouldReturnConnectionTrueDeactivating() {
		whenStateIsSetTo(NMDeviceState.NM_DEVICE_STATE_DEACTIVATING);
		thenStateShouldBeEqualTo(true);
	}

	@Test
	public void shouldReturnConnectionTrueFailed() {
		whenStateIsSetTo(NMDeviceState.NM_DEVICE_STATE_FAILED);
		thenStateShouldBeEqualTo(true);
	}

	public void whenInt32StateIsPassed(UInt32 type) {
		state = NMDeviceState.fromUInt32(type);
	}

	public void whenStateIsSetTo(NMDeviceState type) {
		state = type;
	}

	public void thenStateShouldBeEqualTo(NMDeviceState type) {
		assertEquals(state, type);
	}

	public void thenStateShouldBeEqualTo(Boolean bool) {
		assertEquals(NMDeviceState.isConnected(state), bool);
	}

}
