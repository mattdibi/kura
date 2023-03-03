package org.eclipse.kura.nm;

import java.util.concurrent.CountDownLatch;

import org.freedesktop.dbus.interfaces.DBusSigHandler;
import org.freedesktop.networkmanager.Device;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NMDeviceStateChangeHandler implements DBusSigHandler<Device.StateChanged> {

    private static final Logger logger = LoggerFactory.getLogger(NMDeviceStateChangeHandler.class);

    private CountDownLatch latch;
    private String path;

    public NMDeviceStateChangeHandler(CountDownLatch latch, String path) {
        this.latch = latch;
        this.path = path;
    }

    @Override
    public void handle(Device.StateChanged s) {

        NMDeviceState oldState = NMDeviceState.fromUInt32(s.getOldState());
        NMDeviceState newState = NMDeviceState.fromUInt32(s.getNewState());

        logger.info("Device state change detected: {} -> {}, for {}", oldState, newState, s.getPath());
        if (s.getPath().equals(path) && (newState == NMDeviceState.NM_DEVICE_STATE_ACTIVATED
                || newState == NMDeviceState.NM_DEVICE_STATE_FAILED)) {
            logger.info("Notify waiting thread");
            latch.countDown();
        }
    }
}