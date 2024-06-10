package me.kaimson.melonclient.event.events;

import me.kaimson.melonclient.event.*;

public class GuiScreenEvent extends Event
{
    public final axu screen;
    
    public GuiScreenEvent(final axu screen) {
        this.screen = screen;
    }
    
    public static class Open extends GuiScreenEvent
    {
        public Open(final axu screen) {
            super(screen);
        }
    }
}
