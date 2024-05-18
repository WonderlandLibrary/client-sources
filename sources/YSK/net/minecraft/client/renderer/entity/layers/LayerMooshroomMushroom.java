package net.minecraft.client.renderer.entity.layers;

import net.minecraft.entity.passive.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.entity.*;
import net.minecraft.client.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.init.*;
import net.minecraft.client.model.*;
import net.minecraft.client.renderer.*;

public class LayerMooshroomMushroom implements LayerRenderer<EntityMooshroom>
{
    private final RenderMooshroom mooshroomRenderer;
    
    @Override
    public boolean shouldCombineTextures() {
        return " ".length() != 0;
    }
    
    public LayerMooshroomMushroom(final RenderMooshroom mooshroomRenderer) {
        this.mooshroomRenderer = mooshroomRenderer;
    }
    
    @Override
    public void doRenderLayer(final EntityLivingBase entityLivingBase, final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final float n7) {
        this.doRenderLayer((EntityMooshroom)entityLivingBase, n, n2, n3, n4, n5, n6, n7);
    }
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (-1 != -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public void doRenderLayer(final EntityMooshroom entityMooshroom, final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final float n7) {
        if (!entityMooshroom.isChild() && !entityMooshroom.isInvisible()) {
            final BlockRendererDispatcher blockRendererDispatcher = Minecraft.getMinecraft().getBlockRendererDispatcher();
            this.mooshroomRenderer.bindTexture(TextureMap.locationBlocksTexture);
            GlStateManager.enableCull();
            GlStateManager.cullFace(1002 + 899 - 1579 + 706);
            GlStateManager.pushMatrix();
            GlStateManager.scale(1.0f, -1.0f, 1.0f);
            GlStateManager.translate(0.2f, 0.35f, 0.5f);
            GlStateManager.rotate(42.0f, 0.0f, 1.0f, 0.0f);
            GlStateManager.pushMatrix();
            GlStateManager.translate(-0.5f, -0.5f, 0.5f);
            blockRendererDispatcher.renderBlockBrightness(Blocks.red_mushroom.getDefaultState(), 1.0f);
            GlStateManager.popMatrix();
            GlStateManager.pushMatrix();
            GlStateManager.translate(0.1f, 0.0f, -0.6f);
            GlStateManager.rotate(42.0f, 0.0f, 1.0f, 0.0f);
            GlStateManager.translate(-0.5f, -0.5f, 0.5f);
            blockRendererDispatcher.renderBlockBrightness(Blocks.red_mushroom.getDefaultState(), 1.0f);
            GlStateManager.popMatrix();
            GlStateManager.popMatrix();
            GlStateManager.pushMatrix();
            ((ModelQuadruped)this.mooshroomRenderer.getMainModel()).head.postRender(0.0625f);
            GlStateManager.scale(1.0f, -1.0f, 1.0f);
            GlStateManager.translate(0.0f, 0.7f, -0.2f);
            GlStateManager.rotate(12.0f, 0.0f, 1.0f, 0.0f);
            GlStateManager.translate(-0.5f, -0.5f, 0.5f);
            blockRendererDispatcher.renderBlockBrightness(Blocks.red_mushroom.getDefaultState(), 1.0f);
            GlStateManager.popMatrix();
            GlStateManager.cullFace(904 + 1 - 612 + 736);
            GlStateManager.disableCull();
        }
    }
}
