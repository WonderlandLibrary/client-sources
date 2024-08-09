/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.java.games.input;

import java.io.IOException;
import net.java.games.input.AbstractController;
import net.java.games.input.Component;
import net.java.games.input.Controller;
import net.java.games.input.Event;
import net.java.games.input.LinuxJoystickDevice;
import net.java.games.input.Rumbler;

final class LinuxJoystickAbstractController
extends AbstractController {
    private final LinuxJoystickDevice device;

    protected LinuxJoystickAbstractController(LinuxJoystickDevice linuxJoystickDevice, Component[] componentArray, Controller[] controllerArray, Rumbler[] rumblerArray) {
        super(linuxJoystickDevice.getName(), componentArray, controllerArray, rumblerArray);
        this.device = linuxJoystickDevice;
    }

    protected final void setDeviceEventQueueSize(int n) throws IOException {
        this.device.setBufferSize(n);
    }

    public final void pollDevice() throws IOException {
        this.device.poll();
    }

    protected final boolean getNextDeviceEvent(Event event) throws IOException {
        return this.device.getNextEvent(event);
    }

    public Controller.Type getType() {
        return Controller.Type.STICK;
    }
}

