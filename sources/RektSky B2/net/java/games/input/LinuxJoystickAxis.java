package net.java.games.input;

import java.io.*;

class LinuxJoystickAxis extends AbstractComponent
{
    private float value;
    private boolean analog;
    
    public LinuxJoystickAxis(final Component.Identifier.Axis axis_id) {
        this(axis_id, true);
    }
    
    public LinuxJoystickAxis(final Component.Identifier.Axis axis_id, final boolean analog) {
        super(axis_id.getName(), axis_id);
        this.analog = analog;
    }
    
    @Override
    public final boolean isRelative() {
        return false;
    }
    
    @Override
    public final boolean isAnalog() {
        return this.analog;
    }
    
    final void setValue(final float value) {
        this.value = value;
        this.resetHasPolled();
    }
    
    @Override
    protected final float poll() throws IOException {
        return this.value;
    }
}
