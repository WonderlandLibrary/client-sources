package net.futureclient.client.events;

import net.minecraft.client.gui.GuiScreen;

public class EventGuiScreen extends Event
{
    private GuiScreen k;
    
    public EventGuiScreen(final GuiScreen k) {
        super();
        this.k = k;
    }
    
    public GuiScreen M() {
        return this.k;
    }
}
