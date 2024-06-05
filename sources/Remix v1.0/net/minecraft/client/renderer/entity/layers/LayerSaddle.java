package net.minecraft.client.renderer.entity.layers;

import net.minecraft.util.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.model.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.*;

public class LayerSaddle implements LayerRenderer
{
    private static final ResourceLocation TEXTURE;
    private final RenderPig pigRenderer;
    private final ModelPig pigModel;
    
    public LayerSaddle(final RenderPig p_i46113_1_) {
        this.pigModel = new ModelPig(0.5f);
        this.pigRenderer = p_i46113_1_;
    }
    
    public void doRenderLayer(final EntityPig p_177155_1_, final float p_177155_2_, final float p_177155_3_, final float p_177155_4_, final float p_177155_5_, final float p_177155_6_, final float p_177155_7_, final float p_177155_8_) {
        if (p_177155_1_.getSaddled()) {
            this.pigRenderer.bindTexture(LayerSaddle.TEXTURE);
            this.pigModel.setModelAttributes(this.pigRenderer.getMainModel());
            this.pigModel.render(p_177155_1_, p_177155_2_, p_177155_3_, p_177155_5_, p_177155_6_, p_177155_7_, p_177155_8_);
        }
    }
    
    @Override
    public boolean shouldCombineTextures() {
        return false;
    }
    
    @Override
    public void doRenderLayer(final EntityLivingBase p_177141_1_, final float p_177141_2_, final float p_177141_3_, final float p_177141_4_, final float p_177141_5_, final float p_177141_6_, final float p_177141_7_, final float p_177141_8_) {
        this.doRenderLayer((EntityPig)p_177141_1_, p_177141_2_, p_177141_3_, p_177141_4_, p_177141_5_, p_177141_6_, p_177141_7_, p_177141_8_);
    }
    
    static {
        TEXTURE = new ResourceLocation("textures/entity/pig/pig_saddle.png");
    }
}
