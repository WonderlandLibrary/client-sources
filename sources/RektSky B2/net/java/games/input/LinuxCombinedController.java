package net.java.games.input;

import java.io.*;

public class LinuxCombinedController extends AbstractController
{
    private LinuxAbstractController eventController;
    private LinuxJoystickAbstractController joystickController;
    
    LinuxCombinedController(final LinuxAbstractController eventController, final LinuxJoystickAbstractController joystickController) {
        super(eventController.getName(), joystickController.getComponents(), eventController.getControllers(), eventController.getRumblers());
        this.eventController = eventController;
        this.joystickController = joystickController;
    }
    
    @Override
    protected boolean getNextDeviceEvent(final Event event) throws IOException {
        return this.joystickController.getNextDeviceEvent(event);
    }
    
    @Override
    public final Controller.PortType getPortType() {
        return this.eventController.getPortType();
    }
    
    public final void pollDevice() throws IOException {
        this.eventController.pollDevice();
        this.joystickController.pollDevice();
    }
    
    @Override
    public Controller.Type getType() {
        return this.eventController.getType();
    }
}
