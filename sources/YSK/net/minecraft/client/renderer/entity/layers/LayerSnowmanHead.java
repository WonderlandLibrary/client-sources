package net.minecraft.client.renderer.entity.layers;

import net.minecraft.entity.monster.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.entity.*;

public class LayerSnowmanHead implements LayerRenderer<EntitySnowman>
{
    private final RenderSnowMan snowManRenderer;
    
    public LayerSnowmanHead(final RenderSnowMan snowManRenderer) {
        this.snowManRenderer = snowManRenderer;
    }
    
    @Override
    public void doRenderLayer(final EntitySnowman entitySnowman, final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final float n7) {
        if (!entitySnowman.isInvisible()) {
            GlStateManager.pushMatrix();
            this.snowManRenderer.getMainModel().head.postRender(0.0625f);
            final float n8 = 0.625f;
            GlStateManager.translate(0.0f, -0.34375f, 0.0f);
            GlStateManager.rotate(180.0f, 0.0f, 1.0f, 0.0f);
            GlStateManager.scale(n8, -n8, -n8);
            Minecraft.getMinecraft().getItemRenderer().renderItem(entitySnowman, new ItemStack(Blocks.pumpkin, " ".length()), ItemCameraTransforms.TransformType.HEAD);
            GlStateManager.popMatrix();
        }
    }
    
    @Override
    public void doRenderLayer(final EntityLivingBase entityLivingBase, final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final float n7) {
        this.doRenderLayer((EntitySnowman)entityLivingBase, n, n2, n3, n4, n5, n6, n7);
    }
    
    @Override
    public boolean shouldCombineTextures() {
        return " ".length() != 0;
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
            if (4 <= 0) {
                throw null;
            }
        }
        return sb.toString();
    }
}
