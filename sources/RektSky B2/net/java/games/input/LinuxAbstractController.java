package net.java.games.input;

import java.io.*;

final class LinuxAbstractController extends AbstractController
{
    private final Controller.PortType port;
    private final LinuxEventDevice device;
    private final Controller.Type type;
    
    protected LinuxAbstractController(final LinuxEventDevice device, final Component[] components, final Controller[] children, final Rumbler[] rumblers, final Controller.Type type) throws IOException {
        super(device.getName(), components, children, rumblers);
        this.device = device;
        this.port = device.getPortType();
        this.type = type;
    }
    
    @Override
    public final Controller.PortType getPortType() {
        return this.port;
    }
    
    public final void pollDevice() throws IOException {
        this.device.pollKeyStates();
    }
    
    @Override
    protected final boolean getNextDeviceEvent(final Event event) throws IOException {
        return LinuxControllers.getNextDeviceEvent(event, this.device);
    }
    
    @Override
    public Controller.Type getType() {
        return this.type;
    }
}
