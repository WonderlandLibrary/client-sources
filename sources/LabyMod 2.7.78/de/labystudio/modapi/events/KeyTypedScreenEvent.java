package de.labystudio.modapi.events;

import de.labystudio.modapi.Event;
import de.labystudio.modapi.Listener;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.client.gui.GuiScreen;

public class KeyTypedScreenEvent extends Event
{
    public static final Map<Listener, List<Method>> listenerMethods = new HashMap();
    private GuiScreen screen;
    private char typedChar;
    private int keyCode;

    public KeyTypedScreenEvent(GuiScreen screen, char typedChar, int keyCode)
    {
        this.screen = screen;
        this.typedChar = typedChar;
        this.keyCode = keyCode;
    }

    public GuiScreen getScreen()
    {
        return this.screen;
    }

    public int getKeyCode()
    {
        return this.keyCode;
    }

    public char getTypedChar()
    {
        return this.typedChar;
    }

    public Map<Listener, List<Method>> getListenerMethods()
    {
        return listenerMethods;
    }
}
