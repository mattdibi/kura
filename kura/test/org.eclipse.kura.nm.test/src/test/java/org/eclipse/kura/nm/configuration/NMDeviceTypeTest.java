package org.eclipse.kura.nm.configuration;

import static org.junit.Assert.assertEquals;

import org.eclipse.kura.KuraException;
import org.freedesktop.dbus.types.UInt32;
import org.junit.Test;

public class NMDeviceTypeTest {
	
	NMDeviceType type;

    @Test
    public void shouldReturnCorrectTypeFromUIntZero() throws InterruptedException, KuraException {
    	whenInt32StateIsPassed(new UInt32(0));
    	thenCorrectTypeIsReturned(NMDeviceType.NM_DEVICE_TYPE_UNKNOWN);
    }
    @Test
    public void shouldReturnCorrectTypeFromUIntOne() throws InterruptedException, KuraException {
    	whenInt32StateIsPassed(new UInt32(1));
    	thenCorrectTypeIsReturned(NMDeviceType.NM_DEVICE_TYPE_ETHERNET);
    }
    @Test
    public void shouldReturnCorrectTypeFromUIntTwo() throws InterruptedException, KuraException {
    	whenInt32StateIsPassed(new UInt32(2));
    	thenCorrectTypeIsReturned(NMDeviceType.NM_DEVICE_TYPE_WIFI);
    }
    @Test
    public void shouldReturnCorrectTypeFromUIntThree() throws InterruptedException, KuraException {
    	whenInt32StateIsPassed(new UInt32(3));
    	thenCorrectTypeIsReturned(NMDeviceType.NM_DEVICE_TYPE_UNUSED1);
    }
    @Test
    public void shouldReturnCorrectTypeFromUIntFour() throws InterruptedException, KuraException {
    	whenInt32StateIsPassed(new UInt32(4));
    	thenCorrectTypeIsReturned(NMDeviceType.NM_DEVICE_TYPE_UNUSED2);
    }
    @Test
    public void shouldReturnCorrectTypeFromUIntFive() throws InterruptedException, KuraException {
    	whenInt32StateIsPassed(new UInt32(5));
    	thenCorrectTypeIsReturned(NMDeviceType.NM_DEVICE_TYPE_BT);
    }
    @Test
    public void shouldReturnCorrectTypeFromUIntSix() throws InterruptedException, KuraException {
    	whenInt32StateIsPassed(new UInt32(6));
    	thenCorrectTypeIsReturned(NMDeviceType.NM_DEVICE_TYPE_OLPC_MESH);
    }
    @Test
    public void shouldReturnCorrectTypeFromUIntSeven() throws InterruptedException, KuraException {
    	whenInt32StateIsPassed(new UInt32(7));
    	thenCorrectTypeIsReturned(NMDeviceType.NM_DEVICE_TYPE_WIMAX);
    }
    @Test
    public void shouldReturnCorrectTypeFromUIntEight() throws InterruptedException, KuraException {
    	whenInt32StateIsPassed(new UInt32(8));
    	thenCorrectTypeIsReturned(NMDeviceType.NM_DEVICE_TYPE_MODEM);
    }
    @Test
    public void shouldReturnCorrectTypeFromUIntNine() throws InterruptedException, KuraException {
    	whenInt32StateIsPassed(new UInt32(9));
    	thenCorrectTypeIsReturned(NMDeviceType.NM_DEVICE_TYPE_INFINIBAND);
    }
    @Test
    public void shouldReturnCorrectTypeFromUIntTen() throws InterruptedException, KuraException {
    	whenInt32StateIsPassed(new UInt32(10));
    	thenCorrectTypeIsReturned(NMDeviceType.NM_DEVICE_TYPE_BOND);
    }
    @Test
    public void shouldReturnCorrectTypeFromUIntEleven() throws InterruptedException, KuraException {
    	whenInt32StateIsPassed(new UInt32(11));
    	thenCorrectTypeIsReturned(NMDeviceType.NM_DEVICE_TYPE_VLAN);
    }
    @Test
    public void shouldReturnCorrectTypeFromUIntTwelve() throws InterruptedException, KuraException {
    	whenInt32StateIsPassed(new UInt32(12));
    	thenCorrectTypeIsReturned(NMDeviceType.NM_DEVICE_TYPE_ADSL);
    }
    @Test
    public void shouldReturnCorrectTypeFromUIntThirteen() throws InterruptedException, KuraException {
    	whenInt32StateIsPassed(new UInt32(13));
    	thenCorrectTypeIsReturned(NMDeviceType.NM_DEVICE_TYPE_BRIDGE);
    }
    @Test
    public void shouldReturnCorrectTypeFromUIntFourteen() throws InterruptedException, KuraException {
    	whenInt32StateIsPassed(new UInt32(14));
    	thenCorrectTypeIsReturned(NMDeviceType.NM_DEVICE_TYPE_GENERIC);
    }
    @Test
    public void shouldReturnCorrectTypeFromUIntFifteen() throws InterruptedException, KuraException {
    	whenInt32StateIsPassed(new UInt32(15));
    	thenCorrectTypeIsReturned(NMDeviceType.NM_DEVICE_TYPE_TEAM);
    }
    @Test
    public void shouldReturnCorrectTypeFromUIntSixteen() throws InterruptedException, KuraException {
    	whenInt32StateIsPassed(new UInt32(16));
    	thenCorrectTypeIsReturned(NMDeviceType.NM_DEVICE_TYPE_TUN);
    }
    @Test
    public void shouldReturnCorrectTypeFromUIntSeventeen() throws InterruptedException, KuraException {
    	whenInt32StateIsPassed(new UInt32(17));
    	thenCorrectTypeIsReturned(NMDeviceType.NM_DEVICE_TYPE_IP_TUNNEL);
    }
    @Test
    public void shouldReturnCorrectTypeFromUIntEightteen() throws InterruptedException, KuraException {
    	whenInt32StateIsPassed(new UInt32(18));
    	thenCorrectTypeIsReturned(NMDeviceType.NM_DEVICE_TYPE_MACVLAN);
    }
    @Test
    public void shouldReturnCorrectTypeFromUIntNineteen() throws InterruptedException, KuraException {
    	whenInt32StateIsPassed(new UInt32(19));
    	thenCorrectTypeIsReturned(NMDeviceType.NM_DEVICE_TYPE_VXLAN);
    }
    @Test
    public void shouldReturnCorrectTypeFromUIntTwenty() throws InterruptedException, KuraException {
    	whenInt32StateIsPassed(new UInt32(20));
    	thenCorrectTypeIsReturned(NMDeviceType.NM_DEVICE_TYPE_VETH);
    }
    @Test
    public void shouldReturnCorrectTypeFromUIntTwentyOne() throws InterruptedException, KuraException {
    	whenInt32StateIsPassed(new UInt32(21));
    	thenCorrectTypeIsReturned(NMDeviceType.NM_DEVICE_TYPE_MACSEC);
    }
    @Test
    public void shouldReturnCorrectTypeFromUIntTwentyTwo() throws InterruptedException, KuraException {
    	whenInt32StateIsPassed(new UInt32(22));
    	thenCorrectTypeIsReturned(NMDeviceType.NM_DEVICE_TYPE_DUMMY);
    }
    @Test
    public void shouldReturnCorrectTypeFromUIntTwentyThree() throws InterruptedException, KuraException {
    	whenInt32StateIsPassed(new UInt32(23));
    	thenCorrectTypeIsReturned(NMDeviceType.NM_DEVICE_TYPE_PPP);
    }
    @Test
    public void shouldReturnCorrectTypeFromUIntTwentyFour() throws InterruptedException, KuraException {
    	whenInt32StateIsPassed(new UInt32(24));
    	thenCorrectTypeIsReturned(NMDeviceType.NM_DEVICE_TYPE_OVS_INTERFACE);
    }
    @Test
    public void shouldReturnCorrectTypeFromUIntTwentyFive() throws InterruptedException, KuraException {
    	whenInt32StateIsPassed(new UInt32(25));
    	thenCorrectTypeIsReturned(NMDeviceType.NM_DEVICE_TYPE_OVS_PORT);
    }
    @Test
    public void shouldReturnCorrectTypeFromUIntTwentySix() throws InterruptedException, KuraException {
    	whenInt32StateIsPassed(new UInt32(26));
    	thenCorrectTypeIsReturned(NMDeviceType.NM_DEVICE_TYPE_OVS_BRIDGE);
    }
    @Test
    public void shouldReturnCorrectTypeFromUIntTwentySeven() throws InterruptedException, KuraException {
    	whenInt32StateIsPassed(new UInt32(27));
    	thenCorrectTypeIsReturned(NMDeviceType.NM_DEVICE_TYPE_WPAN);
    }
    @Test
    public void shouldReturnCorrectTypeFromUIntTwentyEight() throws InterruptedException, KuraException {
    	whenInt32StateIsPassed(new UInt32(28));
    	thenCorrectTypeIsReturned(NMDeviceType.NM_DEVICE_TYPE_6LOWPAN);
    }
    @Test
    public void shouldReturnCorrectTypeFromUIntTwentyNine() throws InterruptedException, KuraException {
    	whenInt32StateIsPassed(new UInt32(29));
    	thenCorrectTypeIsReturned(NMDeviceType.NM_DEVICE_TYPE_WIREGUARD);
    }
    @Test
    public void shouldReturnCorrectTypeFromUIntThirty() throws InterruptedException, KuraException {
    	whenInt32StateIsPassed(new UInt32(30));
    	thenCorrectTypeIsReturned(NMDeviceType.NM_DEVICE_TYPE_WIFI_P2P);
    }
    @Test
    public void shouldReturnCorrectTypeFromUIntThirtyOne() throws InterruptedException, KuraException {
    	whenInt32StateIsPassed(new UInt32(31));
    	thenCorrectTypeIsReturned(NMDeviceType.NM_DEVICE_TYPE_VRF);
    }
    
    @Test
    public void shouldReturnCorrectTypeFromUIntThirtydefault() throws InterruptedException, KuraException {
    	whenInt32StateIsPassed(new UInt32(32));
    	thenCorrectTypeIsReturned(null);
    }

	private void whenInt32StateIsPassed(UInt32 type) {
		this.type = NMDeviceType.fromUInt32(type);
	}
	
	private void thenCorrectTypeIsReturned(NMDeviceType type) {
		assertEquals(this.type, type);
		
	}
    
}
