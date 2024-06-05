package net.minecraft.client.renderer.entity;

import net.minecraft.util.*;
import net.minecraft.client.model.*;
import net.minecraft.entity.passive.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.*;

public class RenderOcelot extends RenderLiving
{
    private static final ResourceLocation blackOcelotTextures;
    private static final ResourceLocation ocelotTextures;
    private static final ResourceLocation redOcelotTextures;
    private static final ResourceLocation siameseOcelotTextures;
    
    public RenderOcelot(final RenderManager p_i46151_1_, final ModelBase p_i46151_2_, final float p_i46151_3_) {
        super(p_i46151_1_, p_i46151_2_, p_i46151_3_);
    }
    
    protected ResourceLocation getEntityTexture(final EntityOcelot p_110775_1_) {
        switch (p_110775_1_.getTameSkin()) {
            default: {
                return RenderOcelot.ocelotTextures;
            }
            case 1: {
                return RenderOcelot.blackOcelotTextures;
            }
            case 2: {
                return RenderOcelot.redOcelotTextures;
            }
            case 3: {
                return RenderOcelot.siameseOcelotTextures;
            }
        }
    }
    
    protected void preRenderCallback(final EntityOcelot p_77041_1_, final float p_77041_2_) {
        super.preRenderCallback(p_77041_1_, p_77041_2_);
        if (p_77041_1_.isTamed()) {
            GlStateManager.scale(0.8f, 0.8f, 0.8f);
        }
    }
    
    @Override
    protected void preRenderCallback(final EntityLivingBase p_77041_1_, final float p_77041_2_) {
        this.preRenderCallback((EntityOcelot)p_77041_1_, p_77041_2_);
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final Entity p_110775_1_) {
        return this.getEntityTexture((EntityOcelot)p_110775_1_);
    }
    
    static {
        blackOcelotTextures = new ResourceLocation("textures/entity/cat/black.png");
        ocelotTextures = new ResourceLocation("textures/entity/cat/ocelot.png");
        redOcelotTextures = new ResourceLocation("textures/entity/cat/red.png");
        siameseOcelotTextures = new ResourceLocation("textures/entity/cat/siamese.png");
    }
}
