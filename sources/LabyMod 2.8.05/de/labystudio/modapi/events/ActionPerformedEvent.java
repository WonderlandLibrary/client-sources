package de.labystudio.modapi.events;

import de.labystudio.modapi.Event;
import de.labystudio.modapi.Listener;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;

public class ActionPerformedEvent extends Event
{
    public static final Map<Listener, List<Method>> listenerMethods = new HashMap();
    private GuiScreen screen;
    private GuiButton button;

    public ActionPerformedEvent(GuiScreen screen, GuiButton button)
    {
        this.screen = screen;
        this.button = button;
    }

    public GuiScreen getScreen()
    {
        return this.screen;
    }

    public GuiButton getButton()
    {
        return this.button;
    }

    public Map<Listener, List<Method>> getListenerMethods()
    {
        return listenerMethods;
    }
}
