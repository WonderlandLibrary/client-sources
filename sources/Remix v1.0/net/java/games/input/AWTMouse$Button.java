package net.java.games.input;

import java.io.*;

static final class Button extends AbstractComponent
{
    private float value;
    
    public Button(final Component.Identifier.Button button_id) {
        super(button_id.getName(), button_id);
    }
    
    protected final void setValue(final float value) {
        this.value = value;
    }
    
    protected final float poll() throws IOException {
        return this.value;
    }
    
    public final boolean isAnalog() {
        return false;
    }
    
    public final boolean isRelative() {
        return false;
    }
}
