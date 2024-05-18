package net.minecraft.client.renderer.entity.layers;

import net.minecraft.entity.monster.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.entity.*;
import net.minecraft.client.*;
import net.minecraft.client.model.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.init.*;
import net.minecraft.client.renderer.*;

public class LayerIronGolemFlower implements LayerRenderer<EntityIronGolem>
{
    private final RenderIronGolem ironGolemRenderer;
    
    @Override
    public void doRenderLayer(final EntityLivingBase entityLivingBase, final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final float n7) {
        this.doRenderLayer((EntityIronGolem)entityLivingBase, n, n2, n3, n4, n5, n6, n7);
    }
    
    public LayerIronGolemFlower(final RenderIronGolem ironGolemRenderer) {
        this.ironGolemRenderer = ironGolemRenderer;
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
            if (-1 >= 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public void doRenderLayer(final EntityIronGolem entityIronGolem, final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final float n7) {
        if (entityIronGolem.getHoldRoseTick() != 0) {
            final BlockRendererDispatcher blockRendererDispatcher = Minecraft.getMinecraft().getBlockRendererDispatcher();
            GlStateManager.enableRescaleNormal();
            GlStateManager.pushMatrix();
            GlStateManager.rotate(5.0f + 180.0f * ((ModelIronGolem)this.ironGolemRenderer.getMainModel()).ironGolemRightArm.rotateAngleX / 3.1415927f, 1.0f, 0.0f, 0.0f);
            GlStateManager.rotate(90.0f, 1.0f, 0.0f, 0.0f);
            GlStateManager.translate(-0.9375f, -0.625f, -0.9375f);
            final float n8 = 0.5f;
            GlStateManager.scale(n8, -n8, n8);
            final int brightnessForRender = entityIronGolem.getBrightnessForRender(n3);
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, brightnessForRender % (23100 + 37748 - 11359 + 16047) / 1.0f, brightnessForRender / (13314 + 14824 + 30736 + 6662) / 1.0f);
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            this.ironGolemRenderer.bindTexture(TextureMap.locationBlocksTexture);
            blockRendererDispatcher.renderBlockBrightness(Blocks.red_flower.getDefaultState(), 1.0f);
            GlStateManager.popMatrix();
            GlStateManager.disableRescaleNormal();
        }
    }
    
    @Override
    public boolean shouldCombineTextures() {
        return "".length() != 0;
    }
}
