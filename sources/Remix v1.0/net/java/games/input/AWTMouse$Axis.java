package net.java.games.input;

import java.io.*;

static final class Axis extends AbstractComponent
{
    private float value;
    
    public Axis(final Component.Identifier.Axis axis_id) {
        super(axis_id.getName(), axis_id);
    }
    
    public final boolean isRelative() {
        return false;
    }
    
    public final boolean isAnalog() {
        return true;
    }
    
    protected final void setValue(final float value) {
        this.value = value;
    }
    
    protected final float poll() throws IOException {
        return this.value;
    }
}
