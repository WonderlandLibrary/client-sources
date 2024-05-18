package de.labystudio.modapi.events;

import de.labystudio.modapi.Event;
import de.labystudio.modapi.Listener;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.network.PacketBuffer;

public class PluginMessageReceivedEvent extends Event
{
    public static final Map<Listener, List<Method>> listenerMethods = new HashMap();
    private String channel;
    private PacketBuffer data;

    public PluginMessageReceivedEvent(String channel, PacketBuffer data)
    {
        this.channel = channel;
        this.data = data;
    }

    public Map<Listener, List<Method>> getListenerMethods()
    {
        return listenerMethods;
    }

    public String getChannel()
    {
        return this.channel;
    }

    public PacketBuffer getData()
    {
        return this.data;
    }
}
