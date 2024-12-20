/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.java.games.input;

import java.io.IOException;
import net.java.games.input.AbstractController;
import net.java.games.input.Component;
import net.java.games.input.Controller;
import net.java.games.input.DIControllers;
import net.java.games.input.Event;
import net.java.games.input.IDirectInputDevice;
import net.java.games.input.Rumbler;

final class DIAbstractController
extends AbstractController {
    private final IDirectInputDevice device;
    private final Controller.Type type;

    protected DIAbstractController(IDirectInputDevice iDirectInputDevice, Component[] componentArray, Controller[] controllerArray, Rumbler[] rumblerArray, Controller.Type type) {
        super(iDirectInputDevice.getProductName(), componentArray, controllerArray, rumblerArray);
        this.device = iDirectInputDevice;
        this.type = type;
    }

    public final void pollDevice() throws IOException {
        this.device.pollAll();
    }

    protected final boolean getNextDeviceEvent(Event event) throws IOException {
        return DIControllers.getNextDeviceEvent(event, this.device);
    }

    protected final void setDeviceEventQueueSize(int n) throws IOException {
        this.device.setBufferSize(n);
    }

    public final Controller.Type getType() {
        return this.type;
    }
}

