package de.labystudio.modapi.events;

import de.labystudio.cosmetic.CosmeticUser;
import de.labystudio.modapi.Event;
import de.labystudio.modapi.Listener;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.entity.Entity;

public class RenderCosmeticEvent extends Event
{
    public static final Map<Listener, List<Method>> listenerMethods = new HashMap();
    private Entity entityIn;
    private CosmeticUser cosmeticUser;
    private float var2;
    private float var3;
    private float var4;
    private float var5;
    private float var6;
    private float scale;

    public RenderCosmeticEvent(Entity entityIn, CosmeticUser cosmeticUser, float var2, float var3, float var4, float var5, float var6, float scale)
    {
        this.entityIn = entityIn;
        this.cosmeticUser = cosmeticUser;
        this.var2 = var2;
        this.var3 = var3;
        this.var4 = var4;
        this.var5 = var5;
        this.var6 = var6;
        this.scale = scale;
    }

    public Map<Listener, List<Method>> getListenerMethods()
    {
        return listenerMethods;
    }

    public Entity getEntityIn()
    {
        return this.entityIn;
    }

    public CosmeticUser getCosmeticUser()
    {
        return this.cosmeticUser;
    }

    public float getVar2()
    {
        return this.var2;
    }

    public float getVar3()
    {
        return this.var3;
    }

    public float getVar4()
    {
        return this.var4;
    }

    public float getVar5()
    {
        return this.var5;
    }

    public float getVar6()
    {
        return this.var6;
    }

    public float getScale()
    {
        return this.scale;
    }
}
