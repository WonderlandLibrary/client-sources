package de.labystudio.modapi.events;

import de.labystudio.modapi.Event;
import de.labystudio.modapi.Listener;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GameTickEvent extends Event
{
    public static final Map<Listener, List<Method>> listenerMethods = new HashMap();

    public Map<Listener, List<Method>> getListenerMethods()
    {
        return listenerMethods;
    }
}
