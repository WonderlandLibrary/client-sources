package de.labystudio.modapi;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

public abstract class Event
{
    public abstract Map<Listener, List<Method>> getListenerMethods();
}
