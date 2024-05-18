package de.labystudio.modapi.events;

import de.labystudio.modapi.Event;
import de.labystudio.modapi.Listener;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.client.gui.GuiScreen;

public class MouseClickedScreenEvent extends Event
{
    public static final Map<Listener, List<Method>> listenerMethods = new HashMap();
    private GuiScreen screen;
    private int mouseX;
    private int mouseY;

    public MouseClickedScreenEvent(GuiScreen screen, int mouseX, int mouseY)
    {
        this.screen = screen;
        this.mouseX = mouseX;
        this.mouseY = mouseY;
    }

    public GuiScreen getScreen()
    {
        return this.screen;
    }

    public int getMouseX()
    {
        return this.mouseX;
    }

    public int getMouseY()
    {
        return this.mouseY;
    }

    public Map<Listener, List<Method>> getListenerMethods()
    {
        return listenerMethods;
    }
}
