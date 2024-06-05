package net.java.games.input;

import java.io.*;

final class OSXKeyboard extends Keyboard
{
    private final Controller.PortType port;
    private final OSXHIDQueue queue;
    
    protected OSXKeyboard(final OSXHIDDevice device, final OSXHIDQueue queue, final Component[] components, final Controller[] children, final Rumbler[] rumblers) {
        super(device.getProductName(), components, children, rumblers);
        this.queue = queue;
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
    public final Controller.PortType getPortType() {
        return this.port;
    }
}
