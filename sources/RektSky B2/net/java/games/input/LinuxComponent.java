package net.java.games.input;

import java.io.*;

class LinuxComponent extends AbstractComponent
{
    private final LinuxEventComponent component;
    
    public LinuxComponent(final LinuxEventComponent component) {
        super(component.getIdentifier().getName(), component.getIdentifier());
        this.component = component;
    }
    
    @Override
    public final boolean isRelative() {
        return this.component.isRelative();
    }
    
    @Override
    public final boolean isAnalog() {
        return this.component.isAnalog();
    }
    
    @Override
    protected float poll() throws IOException {
        return this.convertValue(LinuxControllers.poll(this.component), this.component.getDescriptor());
    }
    
    float convertValue(final float value, final LinuxAxisDescriptor descriptor) {
        return this.getComponent().convertValue(value);
    }
    
    @Override
    public final float getDeadZone() {
        return this.component.getDeadZone();
    }
    
    public final LinuxEventComponent getComponent() {
        return this.component;
    }
}
