package net.java.games.input;

import java.io.*;

class OSXComponent extends AbstractComponent
{
    private final OSXHIDElement element;
    
    public OSXComponent(final Component.Identifier id, final OSXHIDElement element) {
        super(id.getName(), id);
        this.element = element;
    }
    
    @Override
    public final boolean isRelative() {
        return this.element.isRelative();
    }
    
    @Override
    public boolean isAnalog() {
        return this.element.isAnalog();
    }
    
    public final OSXHIDElement getElement() {
        return this.element;
    }
    
    @Override
    protected float poll() throws IOException {
        return OSXControllers.poll(this.element);
    }
}
