package net.minecraft.client.renderer.entity.layers;

import net.minecraft.entity.passive.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.model.*;
import net.minecraft.item.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.*;

public class LayerSheepWool implements LayerRenderer<EntitySheep>
{
    private static final ResourceLocation TEXTURE;
    private final RenderSheep sheepRenderer;
    private final ModelSheep1 sheepModel;
    
    public LayerSheepWool(final RenderSheep sheepRendererIn) {
        this.sheepModel = new ModelSheep1();
        this.sheepRenderer = sheepRendererIn;
    }
    
    @Override
    public void doRenderLayer(final EntitySheep entitylivingbaseIn, final float p_177141_2_, final float p_177141_3_, final float partialTicks, final float p_177141_5_, final float p_177141_6_, final float p_177141_7_, final float scale) {
        if (!entitylivingbaseIn.getSheared() && !entitylivingbaseIn.isInvisible()) {
            this.sheepRenderer.bindTexture(LayerSheepWool.TEXTURE);
            if (entitylivingbaseIn.hasCustomName() && "jeb_".equals(entitylivingbaseIn.getCustomNameTag())) {
                final int i1 = 25;
                final int j = entitylivingbaseIn.ticksExisted / 25 + entitylivingbaseIn.getEntityId();
                final int k = EnumDyeColor.values().length;
                final int l = j % k;
                final int m = (j + 1) % k;
                final float f = (entitylivingbaseIn.ticksExisted % 25 + partialTicks) / 25.0f;
                final float[] afloat1 = EntitySheep.func_175513_a(EnumDyeColor.byMetadata(l));
                final float[] afloat2 = EntitySheep.func_175513_a(EnumDyeColor.byMetadata(m));
                GlStateManager.color(afloat1[0] * (1.0f - f) + afloat2[0] * f, afloat1[1] * (1.0f - f) + afloat2[1] * f, afloat1[2] * (1.0f - f) + afloat2[2] * f);
            }
            else {
                final float[] afloat3 = EntitySheep.func_175513_a(entitylivingbaseIn.getFleeceColor());
                GlStateManager.color(afloat3[0], afloat3[1], afloat3[2]);
            }
            this.sheepModel.setModelAttributes(this.sheepRenderer.getMainModel());
            this.sheepModel.setLivingAnimations(entitylivingbaseIn, p_177141_2_, p_177141_3_, partialTicks);
            this.sheepModel.render(entitylivingbaseIn, p_177141_2_, p_177141_3_, p_177141_5_, p_177141_6_, p_177141_7_, scale);
        }
    }
    
    @Override
    public boolean shouldCombineTextures() {
        return true;
    }
    
    static {
        TEXTURE = new ResourceLocation("textures/entity/sheep/sheep_fur.png");
    }
}
