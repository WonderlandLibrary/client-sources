package net.java.games.input;

import java.io.*;

static final class Axis extends AbstractComponent
{
    private final RawDevice device;
    
    public Axis(final RawDevice device, final Component.Identifier.Axis axis) {
        super(axis.getName(), axis);
        this.device = device;
    }
    
    public final boolean isRelative() {
        return true;
    }
    
    public final boolean isAnalog() {
        return true;
    }
    
    protected final float poll() throws IOException {
        if (this.getIdentifier() == Component.Identifier.Axis.X) {
            return (float)this.device.getRelativeX();
        }
        if (this.getIdentifier() == Component.Identifier.Axis.Y) {
            return (float)this.device.getRelativeY();
        }
        if (this.getIdentifier() == Component.Identifier.Axis.Z) {
            return (float)this.device.getWheel();
        }
        throw new RuntimeException("Unknown raw axis: " + this.getIdentifier());
    }
}
