package de.labystudio.modapi.events;

import de.labystudio.modapi.Cancellable;
import de.labystudio.modapi.Event;
import de.labystudio.modapi.Listener;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class RenderModelBipedEvent extends Event implements Cancellable
{
    public static final Map<Listener, List<Method>> listenerMethods = new HashMap();
    private ModelRenderer bipedHead;
    private ModelRenderer bipedHeadwear;
    private ModelRenderer bipedBody;
    private ModelRenderer bipedRightArm;
    private ModelRenderer bipedLeftArm;
    private ModelRenderer bipedRightLeg;
    private ModelRenderer bipedLeftLeg;
    private ModelRenderer modifiedBipedHead;
    private ModelRenderer modifiedBipedHeadwear;
    private ModelRenderer modifiedBipedBody;
    private ModelRenderer modifiedBipedRightArm;
    private ModelRenderer modifiedBipedLeftArm;
    private ModelRenderer modifiedBipedRightLeg;
    private ModelRenderer modifiedBipedLeftLeg;
    private ModelBiped modelBiped;
    private float var1;
    private float var2;
    private float var3;
    private float var4;
    private float var5;
    private Entity entity;
    private boolean cancelled;
    private float scale;

    public RenderModelBipedEvent(ModelRenderer bipedHead, ModelRenderer bipedHeadwear, ModelRenderer bipedBody, ModelRenderer bipedRightArm, ModelRenderer bipedLeftArm, ModelRenderer bipedRightLeg, ModelRenderer bipedLeftLeg, float scale, Entity entityIn, float var1, float var2, float var3, float var4, float var5, ModelBiped modelBiped)
    {
        this.bipedHead = bipedHead;
        this.bipedHeadwear = bipedHeadwear;
        this.bipedBody = bipedBody;
        this.bipedRightArm = bipedRightArm;
        this.bipedLeftArm = bipedLeftArm;
        this.bipedRightLeg = bipedRightLeg;
        this.bipedLeftLeg = bipedLeftLeg;
        this.scale = scale;
        this.modelBiped = modelBiped;
        this.var1 = var1;
        this.var2 = var2;
        this.var3 = var3;
        this.var4 = var4;
        this.var5 = var5;
        this.entity = entityIn;
    }

    public Entity getEntity()
    {
        return this.entity;
    }

    public void setCancelled(boolean cancel)
    {
        this.cancelled = cancel;
    }

    public boolean isCancelled()
    {
        return this.cancelled;
    }

    public ModelRenderer getBipedHead()
    {
        return this.bipedHead;
    }

    public ModelRenderer getBipedHeadwear()
    {
        return this.bipedHeadwear;
    }

    public ModelRenderer getBipedBody()
    {
        return this.bipedBody;
    }

    public ModelRenderer getBipedRightArm()
    {
        return this.bipedRightArm;
    }

    public ModelRenderer getBipedLeftArm()
    {
        return this.bipedLeftArm;
    }

    public ModelRenderer getBipedRightLeg()
    {
        return this.bipedRightLeg;
    }

    public ModelRenderer getBipedLeftLeg()
    {
        return this.bipedLeftLeg;
    }

    public void setBipedHead(ModelRenderer bipedHead)
    {
        this.modifiedBipedHead = bipedHead;
    }

    public void setBipedHeadwear(ModelRenderer bipedHeadwear)
    {
        this.modifiedBipedHeadwear = bipedHeadwear;
    }

    public void setBipedBody(ModelRenderer bipedBody)
    {
        this.modifiedBipedBody = bipedBody;
    }

    public void setBipedRightArm(ModelRenderer bipedRightArm)
    {
        this.modifiedBipedRightArm = bipedRightArm;
    }

    public void setBipedLeftArm(ModelRenderer bipedLeftArm)
    {
        this.modifiedBipedLeftArm = bipedLeftArm;
    }

    public void setBipedRightLeg(ModelRenderer bipedRightLeg)
    {
        this.modifiedBipedRightLeg = bipedRightLeg;
    }

    public void setBipedLeftLeg(ModelRenderer bipedLeftLeg)
    {
        this.modifiedBipedLeftLeg = bipedLeftLeg;
    }

    public ModelRenderer getModifiedBipedBody()
    {
        return this.modifiedBipedBody;
    }

    public ModelRenderer getModifiedBipedHead()
    {
        return this.modifiedBipedHead;
    }

    public ModelRenderer getModifiedBipedHeadwear()
    {
        return this.modifiedBipedHeadwear;
    }

    public ModelRenderer getModifiedBipedLeftArm()
    {
        return this.modifiedBipedLeftArm;
    }

    public ModelRenderer getModifiedBipedLeftLeg()
    {
        return this.modifiedBipedLeftLeg;
    }

    public ModelRenderer getModifiedBipedRightArm()
    {
        return this.modifiedBipedRightArm;
    }

    public ModelRenderer getModifiedBipedRightLeg()
    {
        return this.modifiedBipedRightLeg;
    }

    public void removeBipedHead()
    {
        this.modifiedBipedHead = this.bipedHead;
    }

    public void removeBipedHeadwear()
    {
        this.modifiedBipedHeadwear = this.bipedHeadwear;
    }

    public void removeBipedBody()
    {
        this.modifiedBipedBody = this.bipedBody;
    }

    public void removeBipedRightArm()
    {
        this.modifiedBipedRightArm = this.bipedRightArm;
    }

    public void removeBipedLeftArm()
    {
        this.modifiedBipedLeftArm = this.bipedLeftArm;
    }

    public void removeBipedRightLeg()
    {
        this.modifiedBipedRightLeg = this.bipedRightLeg;
    }

    public void removeBipedLeftLeg()
    {
        this.modifiedBipedLeftLeg = this.bipedLeftLeg;
    }

    public void setModelBiped(ModelBiped modelBiped)
    {
        this.modelBiped = modelBiped;
    }

    public float getScale()
    {
        return this.scale;
    }

    public ModelBiped getModelBiped()
    {
        return this.modelBiped;
    }

    public float getVar1()
    {
        return this.var1;
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

    public Map<Listener, List<Method>> getListenerMethods()
    {
        return listenerMethods;
    }
}
