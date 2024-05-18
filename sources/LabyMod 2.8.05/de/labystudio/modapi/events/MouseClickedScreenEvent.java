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
    private int button;

    public MouseClickedScreenEvent(GuiScreen screen, int mouseX, int mouseY, int button)
    {
        this.screen = screen;
        this.mouseX = mouseX;
        this.mouseY = mouseY;
        this.button = button;
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

    public int getButton()
    {
        return this.button;
    }

    public Map<Listener, List<Method>> getListenerMethods()
    {
        return listenerMethods;
    }
}
