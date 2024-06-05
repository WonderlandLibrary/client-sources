package net.java.games.input;

import java.io.*;

static final class Key extends AbstractComponent
{
    private final RawDevice device;
    private final int vkey_code;
    
    public Key(final RawDevice device, final int vkey_code, final Component.Identifier.Key key_id) {
        super(key_id.getName(), key_id);
        this.device = device;
        this.vkey_code = vkey_code;
    }
    
    protected final float poll() throws IOException {
        return this.device.isKeyDown(this.vkey_code) ? 1.0f : 0.0f;
    }
    
    public final boolean isAnalog() {
        return false;
    }
    
    public final boolean isRelative() {
        return false;
    }
}
