/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.java.games.input;

import java.io.IOException;
import net.java.games.input.Component;
import net.java.games.input.Controller;
import net.java.games.input.DIControllers;
import net.java.games.input.Event;
import net.java.games.input.IDirectInputDevice;
import net.java.games.input.Keyboard;
import net.java.games.input.Rumbler;

final class DIKeyboard
extends Keyboard {
    private final IDirectInputDevice device;

    protected DIKeyboard(IDirectInputDevice iDirectInputDevice, Component[] componentArray, Controller[] controllerArray, Rumbler[] rumblerArray) {
        super(iDirectInputDevice.getProductName(), componentArray, controllerArray, rumblerArray);
        this.device = iDirectInputDevice;
    }

    protected final boolean getNextDeviceEvent(Event event) throws IOException {
        return DIControllers.getNextDeviceEvent(event, this.device);
    }

    public final void pollDevice() throws IOException {
        this.device.pollAll();
    }

    protected final void setDeviceEventQueueSize(int n) throws IOException {
        this.device.setBufferSize(n);
    }
}

