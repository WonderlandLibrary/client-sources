package net.java.games.input;

import java.io.*;

final class OSXAbstractController extends AbstractController
{
    private final Controller.PortType port;
    private final OSXHIDQueue queue;
    private final Controller.Type type;
    
    protected OSXAbstractController(final OSXHIDDevice device, final OSXHIDQueue queue, final Component[] components, final Controller[] children, final Rumbler[] rumblers, final Controller.Type type) {
        super(device.getProductName(), components, children, rumblers);
        this.queue = queue;
        this.type = type;
        this.port = device.getPortType();
    }
    
    @Override
    protected final boolean getNextDeviceEvent(final Event event) throws IOException {
        return OSXControllers.getNextDeviceEvent(event, this.queue);
    }
    
    @Override
    protected final void setDeviceEventQueueSize(final int size) throws IOException {
        this.queue.setQueueDepth(size);
    }
    
    @Override
    public Controller.Type getType() {
        return this.type;
    }
    
    @Override
    public final Controller.PortType getPortType() {
        return this.port;
    }
}
