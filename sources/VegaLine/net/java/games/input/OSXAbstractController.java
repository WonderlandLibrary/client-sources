/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.java.games.input;

import java.io.IOException;
import net.java.games.input.AbstractController;
import net.java.games.input.Component;
import net.java.games.input.Controller;
import net.java.games.input.Event;
import net.java.games.input.OSXControllers;
import net.java.games.input.OSXHIDDevice;
import net.java.games.input.OSXHIDQueue;
import net.java.games.input.Rumbler;

final class OSXAbstractController
extends AbstractController {
    private final Controller.PortType port;
    private final OSXHIDQueue queue;
    private final Controller.Type type;

    protected OSXAbstractController(OSXHIDDevice device, OSXHIDQueue queue, Component[] components, Controller[] children, Rumbler[] rumblers, Controller.Type type2) {
        super(device.getProductName(), components, children, rumblers);
        this.queue = queue;
        this.type = type2;
        this.port = device.getPortType();
    }

    protected final boolean getNextDeviceEvent(Event event) throws IOException {
        return OSXControllers.getNextDeviceEvent(event, this.queue);
    }

    protected final void setDeviceEventQueueSize(int size) throws IOException {
        this.queue.setQueueDepth(size);
    }

    public Controller.Type getType() {
        return this.type;
    }

    public final Controller.PortType getPortType() {
        return this.port;
    }
}

