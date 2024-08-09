/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.java.games.input;

import java.io.IOException;
import net.java.games.input.Component;
import net.java.games.input.DIComponent;
import net.java.games.input.DIDeviceObject;
import net.java.games.input.DIDeviceObjectData;
import net.java.games.input.Event;
import net.java.games.input.IDirectInputDevice;

final class DIControllers {
    private static final DIDeviceObjectData di_event = new DIDeviceObjectData();

    DIControllers() {
    }

    public static final synchronized boolean getNextDeviceEvent(Event event, IDirectInputDevice iDirectInputDevice) throws IOException {
        if (!iDirectInputDevice.getNextEvent(di_event)) {
            return true;
        }
        DIDeviceObject dIDeviceObject = iDirectInputDevice.mapEvent(di_event);
        DIComponent dIComponent = iDirectInputDevice.mapObject(dIDeviceObject);
        if (dIComponent == null) {
            return true;
        }
        int n = dIDeviceObject.isRelative() ? dIDeviceObject.getRelativeEventValue(di_event.getData()) : di_event.getData();
        event.set(dIComponent, dIComponent.getDeviceObject().convertValue(n), di_event.getNanos());
        return false;
    }

    public static final float poll(Component component, DIDeviceObject dIDeviceObject) throws IOException {
        int n = dIDeviceObject.getDevice().getPollData(dIDeviceObject);
        float f = dIDeviceObject.isRelative() ? (float)dIDeviceObject.getRelativePollValue(n) : (float)n;
        return dIDeviceObject.convertValue(f);
    }
}

