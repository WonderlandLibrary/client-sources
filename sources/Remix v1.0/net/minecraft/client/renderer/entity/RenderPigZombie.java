package net.minecraft.client.renderer.entity;

import net.minecraft.util.*;
import net.minecraft.client.model.*;
import net.minecraft.client.renderer.entity.layers.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.*;

public class RenderPigZombie extends RenderBiped
{
    private static final ResourceLocation field_177120_j;
    
    public RenderPigZombie(final RenderManager p_i46148_1_) {
        super(p_i46148_1_, new ModelZombie(), 0.5f, 1.0f);
        this.addLayer(new LayerHeldItem(this));
        this.addLayer(new LayerBipedArmor(this) {
            @Override
            protected void func_177177_a() {
                this.field_177189_c = new ModelZombie(0.5f, true);
                this.field_177186_d = new ModelZombie(1.0f, true);
            }
        });
    }
    
    protected ResourceLocation func_177119_a(final EntityPigZombie p_177119_1_) {
        return RenderPigZombie.field_177120_j;
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final EntityLiving p_110775_1_) {
        return this.func_177119_a((EntityPigZombie)p_110775_1_);
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final Entity p_110775_1_) {
        return this.func_177119_a((EntityPigZombie)p_110775_1_);
    }
    
    static {
        field_177120_j = new ResourceLocation("textures/entity/zombie_pigman.png");
    }
}
