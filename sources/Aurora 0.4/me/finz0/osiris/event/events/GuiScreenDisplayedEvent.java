package me.finz0.osiris.event.events;

import me.finz0.osiris.event.OsirisEvent;
import net.minecraft.client.gui.GuiScreen;

public class GuiScreenDisplayedEvent extends OsirisEvent {
    private final GuiScreen guiScreen;
    public GuiScreenDisplayedEvent(GuiScreen screen){
        super();
        guiScreen = screen;
    }

    public GuiScreen getScreen(){
        return guiScreen;
    }

}
