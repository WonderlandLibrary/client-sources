package net.minecraft.client.renderer.entity.layers;

import net.minecraft.util.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.item.*;
import net.minecraft.entity.passive.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.*;

public class LayerWolfCollar implements LayerRenderer<EntityWolf>
{
    private static final ResourceLocation WOLF_COLLAR;
    private final RenderWolf wolfRenderer;
    
    public LayerWolfCollar(final RenderWolf wolfRendererIn) {
        this.wolfRenderer = wolfRendererIn;
    }
    
    @Override
    public void doRenderLayer(final EntityWolf entitylivingbaseIn, final float p_177141_2_, final float p_177141_3_, final float partialTicks, final float p_177141_5_, final float p_177141_6_, final float p_177141_7_, final float scale) {
        if (entitylivingbaseIn.isTamed() && !entitylivingbaseIn.isInvisible()) {
            this.wolfRenderer.bindTexture(LayerWolfCollar.WOLF_COLLAR);
            final EnumDyeColor enumdyecolor = EnumDyeColor.byMetadata(entitylivingbaseIn.getCollarColor().getMetadata());
            final float[] afloat = EntitySheep.func_175513_a(enumdyecolor);
            GlStateManager.color(afloat[0], afloat[1], afloat[2]);
            this.wolfRenderer.getMainModel().render(entitylivingbaseIn, p_177141_2_, p_177141_3_, p_177141_5_, p_177141_6_, p_177141_7_, scale);
        }
    }
    
    @Override
    public boolean shouldCombineTextures() {
        return true;
    }
    
    static {
        WOLF_COLLAR = new ResourceLocation("textures/entity/wolf/wolf_collar.png");
    }
}
