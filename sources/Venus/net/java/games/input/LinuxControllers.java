/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.java.games.input;

import java.io.IOException;
import net.java.games.input.Event;
import net.java.games.input.LinuxAbsInfo;
import net.java.games.input.LinuxAxisDescriptor;
import net.java.games.input.LinuxComponent;
import net.java.games.input.LinuxEvent;
import net.java.games.input.LinuxEventComponent;
import net.java.games.input.LinuxEventDevice;

final class LinuxControllers {
    private static final LinuxEvent linux_event = new LinuxEvent();
    private static final LinuxAbsInfo abs_info = new LinuxAbsInfo();

    LinuxControllers() {
    }

    public static final synchronized boolean getNextDeviceEvent(Event event, LinuxEventDevice linuxEventDevice) throws IOException {
        while (linuxEventDevice.getNextEvent(linux_event)) {
            LinuxAxisDescriptor linuxAxisDescriptor = linux_event.getDescriptor();
            LinuxComponent linuxComponent = linuxEventDevice.mapDescriptor(linuxAxisDescriptor);
            if (linuxComponent == null) continue;
            float f = linuxComponent.convertValue(linux_event.getValue(), linuxAxisDescriptor);
            event.set(linuxComponent, f, linux_event.getNanos());
            return false;
        }
        return true;
    }

    public static final synchronized float poll(LinuxEventComponent linuxEventComponent) throws IOException {
        int n = linuxEventComponent.getDescriptor().getType();
        switch (n) {
            case 1: {
                int n2 = linuxEventComponent.getDescriptor().getCode();
                float f = linuxEventComponent.getDevice().isKeySet(n2) ? 1.0f : 0.0f;
                return f;
            }
            case 3: {
                linuxEventComponent.getAbsInfo(abs_info);
                return abs_info.getValue();
            }
        }
        throw new RuntimeException("Unkown native_type: " + n);
    }
}

