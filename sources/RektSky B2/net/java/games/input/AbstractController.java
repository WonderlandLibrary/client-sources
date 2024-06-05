package net.java.games.input;

import java.util.*;
import java.io.*;

public abstract class AbstractController implements Controller
{
    static final int EVENT_QUEUE_DEPTH = 32;
    private static final Event event;
    private final String name;
    private final Component[] components;
    private final Controller[] children;
    private final Rumbler[] rumblers;
    private final Map<Component.Identifier, Component> id_to_components;
    private EventQueue event_queue;
    
    protected AbstractController(final String name, final Component[] components, final Controller[] children, final Rumbler[] rumblers) {
        this.id_to_components = new HashMap<Component.Identifier, Component>();
        this.event_queue = new EventQueue(32);
        this.name = name;
        this.components = components;
        this.children = children;
        this.rumblers = rumblers;
        for (int i = components.length - 1; i >= 0; --i) {
            this.id_to_components.put(components[i].getIdentifier(), components[i]);
        }
    }
    
    @Override
    public final Controller[] getControllers() {
        return this.children;
    }
    
    @Override
    public final Component[] getComponents() {
        return this.components;
    }
    
    @Override
    public final Component getComponent(final Component.Identifier id) {
        return this.id_to_components.get(id);
    }
    
    @Override
    public final Rumbler[] getRumblers() {
        return this.rumblers;
    }
    
    @Override
    public PortType getPortType() {
        return PortType.UNKNOWN;
    }
    
    @Override
    public int getPortNumber() {
        return 0;
    }
    
    @Override
    public final String getName() {
        return this.name;
    }
    
    @Override
    public String toString() {
        return this.name;
    }
    
    @Override
    public Type getType() {
        return Type.UNKNOWN;
    }
    
    @Override
    public final void setEventQueueSize(final int size) {
        try {
            this.setDeviceEventQueueSize(size);
            this.event_queue = new EventQueue(size);
        }
        catch (IOException e) {
            ControllerEnvironment.log("Failed to create new event queue of size " + size + ": " + e);
        }
    }
    
    protected void setDeviceEventQueueSize(final int size) throws IOException {
    }
    
    @Override
    public final EventQueue getEventQueue() {
        return this.event_queue;
    }
    
    protected abstract boolean getNextDeviceEvent(final Event p0) throws IOException;
    
    protected void pollDevice() throws IOException {
    }
    
    @Override
    public synchronized boolean poll() {
        final Component[] components = this.getComponents();
        try {
            this.pollDevice();
            for (int i = 0; i < components.length; ++i) {
                final AbstractComponent component = (AbstractComponent)components[i];
                if (component.isRelative()) {
                    component.setPollData(0.0f);
                }
                else {
                    component.resetHasPolled();
                }
            }
            while (this.getNextDeviceEvent(AbstractController.event)) {
                final AbstractComponent component2 = (AbstractComponent)AbstractController.event.getComponent();
                final float value = AbstractController.event.getValue();
                if (component2.isRelative()) {
                    if (value == 0.0f) {
                        continue;
                    }
                    component2.setPollData(component2.getPollData() + value);
                }
                else {
                    if (value == component2.getEventValue()) {
                        continue;
                    }
                    component2.setEventValue(value);
                }
                if (!this.event_queue.isFull()) {
                    this.event_queue.add(AbstractController.event);
                }
            }
            return true;
        }
        catch (IOException e) {
            ControllerEnvironment.log("Failed to poll device: " + e.getMessage());
            return false;
        }
    }
    
    static {
        event = new Event();
    }
}
