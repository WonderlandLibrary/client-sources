package de.labystudio.modapi.events;

import de.labystudio.modapi.Event;
import de.labystudio.modapi.Listener;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JoinedServerEvent extends Event
{
    public static final Map<Listener, List<Method>> listenerMethods = new HashMap();
    private String ip;
    private int port;

    public JoinedServerEvent(String ip, int port)
    {
        this.ip = ip;
        this.port = port;
    }

    public String getIp()
    {
        return this.ip;
    }

    public int getPort()
    {
        return this.port;
    }

    public Map<Listener, List<Method>> getListenerMethods()
    {
        return listenerMethods;
    }
}
