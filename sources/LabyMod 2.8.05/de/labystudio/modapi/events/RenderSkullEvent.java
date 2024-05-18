package de.labystudio.modapi.events;

import de.labystudio.modapi.Event;
import de.labystudio.modapi.Listener;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.tileentity.TileEntitySkull;

public class RenderSkullEvent extends Event
{
    public static final Map<Listener, List<Method>> listenerMethods = new HashMap();
    private TileEntitySkull skull;
    private double x;
    private double y;
    private double z;

    public RenderSkullEvent(TileEntitySkull skull, double x, double y, double z)
    {
        this.skull = skull;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public TileEntitySkull getSkull()
    {
        return this.skull;
    }

    public double getX()
    {
        return this.x;
    }

    public double getY()
    {
        return this.y;
    }

    public double getZ()
    {
        return this.z;
    }

    public Map<Listener, List<Method>> getListenerMethods()
    {
        return listenerMethods;
    }
}
