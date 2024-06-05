package net.java.games.input;

import java.io.*;

final class DIComponent extends AbstractComponent
{
    private final DIDeviceObject object;
    
    public DIComponent(final Component.Identifier identifier, final DIDeviceObject object) {
        super(object.getName(), identifier);
        this.object = object;
    }
    
    @Override
    public final boolean isRelative() {
        return this.object.isRelative();
    }
    
    @Override
    public final boolean isAnalog() {
        return this.object.isAnalog();
    }
    
    @Override
    public final float getDeadZone() {
        return this.object.getDeadzone();
    }
    
    public final DIDeviceObject getDeviceObject() {
        return this.object;
    }
    
    @Override
    protected final float poll() throws IOException {
        return DIControllers.poll(this, this.object);
    }
}
