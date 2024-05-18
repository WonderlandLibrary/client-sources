package net.minecraft.client.renderer.entity.layers;

import net.minecraft.entity.monster.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.block.material.*;
import net.minecraft.client.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.block.state.*;
import net.minecraft.client.renderer.*;
import net.minecraft.entity.*;

public class LayerHeldBlock implements LayerRenderer<EntityEnderman>
{
    private final RenderEnderman endermanRenderer;
    
    public LayerHeldBlock(final RenderEnderman endermanRenderer) {
        this.endermanRenderer = endermanRenderer;
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
            if (2 == 0) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public boolean shouldCombineTextures() {
        return "".length() != 0;
    }
    
    @Override
    public void doRenderLayer(final EntityEnderman entityEnderman, final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final float n7) {
        final IBlockState heldBlockState = entityEnderman.getHeldBlockState();
        if (heldBlockState.getBlock().getMaterial() != Material.air) {
            final BlockRendererDispatcher blockRendererDispatcher = Minecraft.getMinecraft().getBlockRendererDispatcher();
            GlStateManager.enableRescaleNormal();
            GlStateManager.pushMatrix();
            GlStateManager.translate(0.0f, 0.6875f, -0.75f);
            GlStateManager.rotate(20.0f, 1.0f, 0.0f, 0.0f);
            GlStateManager.rotate(45.0f, 0.0f, 1.0f, 0.0f);
            GlStateManager.translate(0.25f, 0.1875f, 0.25f);
            final float n8 = 0.5f;
            GlStateManager.scale(-n8, -n8, n8);
            final int brightnessForRender = entityEnderman.getBrightnessForRender(n3);
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, brightnessForRender % (63356 + 32561 - 43424 + 13043) / 1.0f, brightnessForRender / (27672 + 32407 - 4120 + 9577) / 1.0f);
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            this.endermanRenderer.bindTexture(TextureMap.locationBlocksTexture);
            blockRendererDispatcher.renderBlockBrightness(heldBlockState, 1.0f);
            GlStateManager.popMatrix();
            GlStateManager.disableRescaleNormal();
        }
    }
    
    @Override
    public void doRenderLayer(final EntityLivingBase entityLivingBase, final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final float n7) {
        this.doRenderLayer((EntityEnderman)entityLivingBase, n, n2, n3, n4, n5, n6, n7);
    }
}
