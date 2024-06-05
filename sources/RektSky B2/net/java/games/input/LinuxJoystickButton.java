package net.java.games.input;

import java.io.*;

final class LinuxJoystickButton extends AbstractComponent
{
    private float value;
    
    public LinuxJoystickButton(final Component.Identifier button_id) {
        super(button_id.getName(), button_id);
    }
    
    @Override
    public final boolean isRelative() {
        return false;
    }
    
    final void setValue(final float value) {
        this.value = value;
    }
    
    @Override
    protected final float poll() throws IOException {
        return this.value;
    }
}
