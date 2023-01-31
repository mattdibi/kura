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
    public void shouldReturnCorrectStateTypeZero() throws InterruptedException, KuraException {
    	whenInt32StateIsPassed(new UInt32(0));
    	thenCorrectStateIsReturned(NMDeviceState.NM_DEVICE_STATE_UNKNOWN);
    }
    
    @Test
    public void shouldReturnCorrectStateTypeTen() throws InterruptedException, KuraException {
    	whenInt32StateIsPassed(new UInt32(10));
    	thenCorrectStateIsReturned(NMDeviceState.NM_DEVICE_STATE_UNMANAGED);
    }
    @Test
    public void shouldReturnCorrectStateTypeTwenty() throws InterruptedException, KuraException {
    	whenInt32StateIsPassed(new UInt32(20));
    	thenCorrectStateIsReturned(NMDeviceState.NM_DEVICE_STATE_UNAVAILABLE);
    }
    @Test
    public void shouldReturnCorrectStateTypeThirty() throws InterruptedException, KuraException {
    	whenInt32StateIsPassed(new UInt32(30));
    	thenCorrectStateIsReturned(NMDeviceState.NM_DEVICE_STATE_DISCONNECTED);
    }
    @Test
    public void shouldReturnCorrectStateTypeForty() throws InterruptedException, KuraException {
    	whenInt32StateIsPassed(new UInt32(40));
    	thenCorrectStateIsReturned(NMDeviceState.NM_DEVICE_STATE_PREPARE);
    }
    @Test
    public void shouldReturnCorrectStateTypeFifty() throws InterruptedException, KuraException {
    	whenInt32StateIsPassed(new UInt32(50));
    	thenCorrectStateIsReturned(NMDeviceState.NM_DEVICE_STATE_CONFIG);
    }
    @Test
    public void shouldReturnCorrectStateTypeSixty() throws InterruptedException, KuraException {
    	whenInt32StateIsPassed(new UInt32(60));
    	thenCorrectStateIsReturned(NMDeviceState.NM_DEVICE_STATE_NEED_AUTH);
    }
    @Test
    public void shouldReturnCorrectStateTypeSeventy() throws InterruptedException, KuraException {
    	whenInt32StateIsPassed(new UInt32(70));
    	thenCorrectStateIsReturned(NMDeviceState.NM_DEVICE_STATE_IP_CONFIG);
    }
    @Test
    public void shouldReturnCorrectStateTypeEighty() throws InterruptedException, KuraException {
    	whenInt32StateIsPassed(new UInt32(80));
    	thenCorrectStateIsReturned(NMDeviceState.NM_DEVICE_STATE_IP_CHECK);
    }
    @Test
    public void shouldReturnCorrectStateTypeNinety() throws InterruptedException, KuraException {
    	whenInt32StateIsPassed(new UInt32(90));
    	thenCorrectStateIsReturned(NMDeviceState.NM_DEVICE_STATE_SECONDARIES);
    }
    @Test
    public void shouldReturnCorrectStateTypeHundred() throws InterruptedException, KuraException {
    	whenInt32StateIsPassed(new UInt32(100));
    	thenCorrectStateIsReturned(NMDeviceState.NM_DEVICE_STATE_ACTIVATED);
    }
    @Test
    public void shouldReturnCorrectStateTypeHundredTen() throws InterruptedException, KuraException {
    	whenInt32StateIsPassed(new UInt32(110));
    	thenCorrectStateIsReturned(NMDeviceState.NM_DEVICE_STATE_DEACTIVATING);
    }
    @Test
    public void shouldReturnCorrectStateTypeHundredTwenty() throws InterruptedException, KuraException {
    	whenInt32StateIsPassed(new UInt32(120));
    	thenCorrectStateIsReturned(NMDeviceState.NM_DEVICE_STATE_FAILED);
    }
    
    @Test
    public void shouldReturnCorrectStateTypeHundredOther() throws InterruptedException, KuraException {
    	whenInt32StateIsPassed(new UInt32(121));
    	thenCorrectStateIsReturned(NMDeviceState.NM_DEVICE_STATE_UNKNOWN);
    }
    
    @Test
    public void shouldReturnConnectionFalseUnknown() throws InterruptedException, KuraException {
    	whenStateIsSetTo(NMDeviceState.NM_DEVICE_STATE_UNKNOWN);
    	thenIsConnectShouldReturn(false);
    }
    @Test
    public void shouldReturnConnectionFalseUnmanaged() throws InterruptedException, KuraException {
    	whenStateIsSetTo(NMDeviceState.NM_DEVICE_STATE_UNMANAGED);
    	thenIsConnectShouldReturn(false);
    }
    @Test
    public void shouldReturnConnectionFalseUnavailable() throws InterruptedException, KuraException {
    	whenStateIsSetTo(NMDeviceState.NM_DEVICE_STATE_UNAVAILABLE);
    	thenIsConnectShouldReturn(false);
    }
    
    @Test
    public void shouldReturnConnectionFalseDisconnected() throws InterruptedException, KuraException {
    	whenStateIsSetTo(NMDeviceState.NM_DEVICE_STATE_DISCONNECTED);
    	thenIsConnectShouldReturn(false);
    }
    @Test
    public void shouldReturnConnectionTruePrepare() throws InterruptedException, KuraException {
    	whenStateIsSetTo(NMDeviceState.NM_DEVICE_STATE_PREPARE);
    	thenIsConnectShouldReturn(true);
    }
    @Test
    public void shouldReturnConnectionTrueConfig() throws InterruptedException, KuraException {
    	whenStateIsSetTo(NMDeviceState.NM_DEVICE_STATE_CONFIG);
    	thenIsConnectShouldReturn(true);
    }
    @Test
    public void shouldReturnConnectionTrueNeedAuth() throws InterruptedException, KuraException {
    	whenStateIsSetTo(NMDeviceState.NM_DEVICE_STATE_NEED_AUTH);
    	thenIsConnectShouldReturn(true);
    }
    @Test
    public void shouldReturnConnectionTrueIpConfig() throws InterruptedException, KuraException {
    	whenStateIsSetTo(NMDeviceState.NM_DEVICE_STATE_IP_CONFIG);
    	thenIsConnectShouldReturn(true);
    }
    @Test
    public void shouldReturnConnectionTrueIpCheck() throws InterruptedException, KuraException {
    	whenStateIsSetTo(NMDeviceState.NM_DEVICE_STATE_IP_CHECK);
    	thenIsConnectShouldReturn(true);
    }
    @Test
    public void shouldReturnConnectionTrueSecondaries() throws InterruptedException, KuraException {
    	whenStateIsSetTo(NMDeviceState.NM_DEVICE_STATE_SECONDARIES);
    	thenIsConnectShouldReturn(true);
    }
    @Test
    public void shouldReturnConnectionTrueActivated() throws InterruptedException, KuraException {
    	whenStateIsSetTo(NMDeviceState.NM_DEVICE_STATE_ACTIVATED);
    	thenIsConnectShouldReturn(true);
    }
    @Test
    public void shouldReturnConnectionTrueDeactivating() throws InterruptedException, KuraException {
    	whenStateIsSetTo(NMDeviceState.NM_DEVICE_STATE_DEACTIVATING);
    	thenIsConnectShouldReturn(true);
    }
    @Test
    public void shouldReturnConnectionTrueFailed() throws InterruptedException, KuraException {
    	whenStateIsSetTo(NMDeviceState.NM_DEVICE_STATE_FAILED);
    	thenIsConnectShouldReturn(true);
    }

    public void whenInt32StateIsPassed(UInt32 type){
    	state = NMDeviceState.fromUInt32(type);
    }
    
    public void whenStateIsSetTo(NMDeviceState type){
    	state = type;
    }
    
    public void thenCorrectStateIsReturned(NMDeviceState type){
    	assertEquals(state, type);
    }
    
    public void thenIsConnectShouldReturn(Boolean bool){
    	assertEquals(NMDeviceState.isConnected(state), bool);
    }

}
