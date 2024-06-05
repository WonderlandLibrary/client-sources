package net.minecraft.client.renderer.entity;

import net.minecraft.util.*;
import net.minecraft.client.model.*;
import net.minecraft.client.renderer.entity.layers.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.*;

public class RenderMooshroom extends RenderLiving
{
    private static final ResourceLocation mooshroomTextures;
    
    public RenderMooshroom(final RenderManager p_i46152_1_, final ModelBase p_i46152_2_, final float p_i46152_3_) {
        super(p_i46152_1_, p_i46152_2_, p_i46152_3_);
        this.addLayer(new LayerMooshroomMushroom(this));
    }
    
    protected ResourceLocation func_180582_a(final EntityMooshroom p_180582_1_) {
        return RenderMooshroom.mooshroomTextures;
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final Entity p_110775_1_) {
        return this.func_180582_a((EntityMooshroom)p_110775_1_);
    }
    
    static {
        mooshroomTextures = new ResourceLocation("textures/entity/cow/mooshroom.png");
    }
}
