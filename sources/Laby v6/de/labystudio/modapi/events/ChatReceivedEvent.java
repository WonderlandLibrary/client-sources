package de.labystudio.modapi.events;

import de.labystudio.modapi.Event;
import de.labystudio.modapi.Listener;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.util.IChatComponent;

public class ChatReceivedEvent extends Event
{
    public static final Map<Listener, List<Method>> listenerMethods = new HashMap();
    private boolean cancelled;
    private IChatComponent component;

    public ChatReceivedEvent(IChatComponent component)
    {
        this.component = component;
    }

    public Map<Listener, List<Method>> getListenerMethods()
    {
        return listenerMethods;
    }

    public boolean isCancelled()
    {
        return this.cancelled;
    }

    public void setCancelled(boolean cancelled)
    {
        this.cancelled = cancelled;
    }

    public IChatComponent getComponent()
    {
        return this.component;
    }

    public String getCleanMsg()
    {
        return this.component.getUnformattedText();
    }

    public String getMsg()
    {
        return this.component.getFormattedText();
    }

    public void setComponent(IChatComponent component)
    {
        this.component = component;
    }
}
