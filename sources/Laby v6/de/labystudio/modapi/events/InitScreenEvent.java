package de.labystudio.modapi.events;

import de.labystudio.modapi.Event;
import de.labystudio.modapi.Listener;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

public class InitScreenEvent extends Event
{
    public static final Map<Listener, List<Method>> listenerMethods = new HashMap();
    private GuiScreen screen;
    private List<GuiButton> buttonList;

    public InitScreenEvent(GuiScreen screen, List<GuiButton> buttonList)
    {
        this.screen = screen;
        this.buttonList = buttonList;
    }

    public GuiScreen getScreen()
    {
        return this.screen;
    }

    public List<GuiButton> getButtonList()
    {
        return this.buttonList;
    }

    public Map<Listener, List<Method>> getListenerMethods()
    {
        return listenerMethods;
    }
}
