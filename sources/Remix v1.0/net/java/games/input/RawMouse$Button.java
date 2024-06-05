package net.java.games.input;

import java.io.*;

static final class Button extends AbstractComponent
{
    private final RawDevice device;
    private final int button_id;
    
    public Button(final RawDevice device, final Component.Identifier.Button id, final int button_id) {
        super(id.getName(), id);
        this.device = device;
        this.button_id = button_id;
    }
    
    protected final float poll() throws IOException {
        return this.device.getButtonState(this.button_id) ? 1.0f : 0.0f;
    }
    
    public final boolean isAnalog() {
        return false;
    }
    
    public final boolean isRelative() {
        return false;
    }
}
