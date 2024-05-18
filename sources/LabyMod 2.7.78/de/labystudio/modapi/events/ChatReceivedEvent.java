package de.labystudio.modapi.events;

import de.labystudio.modapi.Event;
import de.labystudio.modapi.Listener;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChatReceivedEvent extends Event
{
    public static final Map<Listener, List<Method>> listenerMethods = new HashMap();
    private String msg;
    private String cleanMsg;

    public ChatReceivedEvent(String msg, String cleanMsg)
    {
        this.msg = msg;
        this.cleanMsg = cleanMsg;
    }

    public Map<Listener, List<Method>> getListenerMethods()
    {
        return listenerMethods;
    }

    public String getMsg()
    {
        return this.msg;
    }

    public String getCleanMsg()
    {
        return this.cleanMsg;
    }
}
