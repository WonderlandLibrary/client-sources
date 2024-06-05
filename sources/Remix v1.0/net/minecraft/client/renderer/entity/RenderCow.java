package net.minecraft.client.renderer.entity;

import net.minecraft.util.*;
import net.minecraft.client.model.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.*;

public class RenderCow extends RenderLiving
{
    private static final ResourceLocation cowTextures;
    
    public RenderCow(final RenderManager p_i46187_1_, final ModelBase p_i46187_2_, final float p_i46187_3_) {
        super(p_i46187_1_, p_i46187_2_, p_i46187_3_);
    }
    
    protected ResourceLocation func_180572_a(final EntityCow p_180572_1_) {
        return RenderCow.cowTextures;
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final Entity p_110775_1_) {
        return this.func_180572_a((EntityCow)p_110775_1_);
    }
    
    static {
        cowTextures = new ResourceLocation("textures/entity/cow/cow.png");
    }
}
