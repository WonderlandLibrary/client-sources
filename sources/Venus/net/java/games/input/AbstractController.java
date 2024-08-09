/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.java.games.input;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import net.java.games.input.AbstractComponent;
import net.java.games.input.Component;
import net.java.games.input.Controller;
import net.java.games.input.ControllerEnvironment;
import net.java.games.input.Event;
import net.java.games.input.EventQueue;
import net.java.games.input.Rumbler;

public abstract class AbstractController
implements Controller {
    static final int EVENT_QUEUE_DEPTH = 32;
    private static final Event event = new Event();
    private final String name;
    private final Component[] components;
    private final Controller[] children;
    private final Rumbler[] rumblers;
    private final Map id_to_components = new HashMap();
    private EventQueue event_queue = new EventQueue(32);

    protected AbstractController(String string, Component[] componentArray, Controller[] controllerArray, Rumbler[] rumblerArray) {
        this.name = string;
        this.components = componentArray;
        this.children = controllerArray;
        this.rumblers = rumblerArray;
        for (int i = componentArray.length - 1; i >= 0; --i) {
            this.id_to_components.put(componentArray[i].getIdentifier(), componentArray[i]);
        }
    }

    public final Controller[] getControllers() {
        return this.children;
    }

    public final Component[] getComponents() {
        return this.components;
    }

    public final Component getComponent(Component.Identifier identifier) {
        return (Component)this.id_to_components.get(identifier);
    }

    public final Rumbler[] getRumblers() {
        return this.rumblers;
    }

    public Controller.PortType getPortType() {
        return Controller.PortType.UNKNOWN;
    }

    public int getPortNumber() {
        return 1;
    }

    public final String getName() {
        return this.name;
    }

    public String toString() {
        return this.name;
    }

    public Controller.Type getType() {
        return Controller.Type.UNKNOWN;
    }

    public final void setEventQueueSize(int n) {
        try {
            this.setDeviceEventQueueSize(n);
            this.event_queue = new EventQueue(n);
        } catch (IOException iOException) {
            ControllerEnvironment.logln("Failed to create new event queue of size " + n + ": " + iOException);
        }
    }

    protected void setDeviceEventQueueSize(int n) throws IOException {
    }

    public final EventQueue getEventQueue() {
        return this.event_queue;
    }

    protected abstract boolean getNextDeviceEvent(Event var1) throws IOException;

    protected void pollDevice() throws IOException {
    }

    public synchronized boolean poll() {
        Component[] componentArray = this.getComponents();
        try {
            this.pollDevice();
            for (int i = 0; i < componentArray.length; ++i) {
                AbstractComponent abstractComponent = (AbstractComponent)componentArray[i];
                if (abstractComponent.isRelative()) {
                    abstractComponent.setPollData(0.0f);
                    continue;
                }
                abstractComponent.resetHasPolled();
            }
            while (this.getNextDeviceEvent(event)) {
                AbstractComponent abstractComponent = (AbstractComponent)event.getComponent();
                float f = event.getValue();
                if (abstractComponent.isRelative()) {
                    if (f == 0.0f) continue;
                    abstractComponent.setPollData(abstractComponent.getPollData() + f);
                } else {
                    if (f == abstractComponent.getEventValue()) continue;
                    abstractComponent.setEventValue(f);
                }
                if (this.event_queue.isFull()) continue;
                this.event_queue.add(event);
            }
            return true;
        } catch (IOException iOException) {
            ControllerEnvironment.logln("Failed to poll device: " + iOException.getMessage());
            return true;
        }
    }
}

