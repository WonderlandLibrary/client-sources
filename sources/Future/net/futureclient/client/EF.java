package net.futureclient.client;

import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.client.renderer.entity.RenderLivingBase;

public static class EF extends qF {
    private float K = i;
    private float M = m;
    private float d = d2;
    private float a = a;
    private float D = d;
    private float k = k;
    
    public EF(final RenderLivingBase<?> renderLivingBase, final EntityLivingBase entityLivingBase, final ModelBase modelBase, final float m, final float d, final float d2, final float a, final float k, final float i) {
        super(renderLivingBase, entityLivingBase, modelBase);
    }
    
    public float B() {
        return this.k;
    }
    
    public float C() {
        return this.d;
    }
    
    public float b() {
        return this.a;
    }
    
    public float e() {
        return this.K;
    }
    
    public float i() {
        return this.M;
    }
    
    public float M() {
        return this.D;
    }
}