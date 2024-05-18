package de.labystudio.modapi.events;

import de.labystudio.modapi.Event;
import de.labystudio.modapi.Listener;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;

public class ModelBipedRotationAnglesEvent extends Event
{
    public static final Map<Listener, List<Method>> listenerMethods = new HashMap();
    ModelBiped modelBiped;
    Entity entity;
    private boolean cancelled;

    public ModelBipedRotationAnglesEvent(ModelBiped modelBiped, Entity entityIn)
    {
        this.modelBiped = modelBiped;
        this.entity = entityIn;
    }

    public ModelBiped getModelBiped()
    {
        return this.modelBiped;
    }

    public Entity getEntity()
    {
        return this.entity;
    }

    public void setCancelled(boolean cancelled)
    {
        this.cancelled = cancelled;
    }

    public boolean isCancelled()
    {
        return this.cancelled;
    }

    public Map<Listener, List<Method>> getListenerMethods()
    {
        return listenerMethods;
    }
}
