package net.minecraft.client.renderer.entity;

import net.minecraft.util.*;
import net.minecraft.client.model.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.*;

public class RenderBlaze extends RenderLiving
{
    private static final ResourceLocation blazeTextures;
    
    public RenderBlaze(final RenderManager p_i46191_1_) {
        super(p_i46191_1_, new ModelBlaze(), 0.5f);
    }
    
    protected ResourceLocation getEntityTexture(final EntityBlaze p_110775_1_) {
        return RenderBlaze.blazeTextures;
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final Entity p_110775_1_) {
        return this.getEntityTexture((EntityBlaze)p_110775_1_);
    }
    
    static {
        blazeTextures = new ResourceLocation("textures/entity/blaze.png");
    }
}
