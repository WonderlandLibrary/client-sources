package net.minecraft.client.renderer.entity;

import net.minecraft.entity.item.*;
import net.minecraft.client.*;
import net.minecraft.init.*;
import net.minecraft.client.renderer.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.entity.*;

public class RenderTNTPrimed extends Render<EntityTNTPrimed>
{
    @Override
    public void doRender(final EntityTNTPrimed entityTNTPrimed, final double n, final double n2, final double n3, final float n4, final float n5) {
        final BlockRendererDispatcher blockRendererDispatcher = Minecraft.getMinecraft().getBlockRendererDispatcher();
        GlStateManager.pushMatrix();
        GlStateManager.translate((float)n, (float)n2 + 0.5f, (float)n3);
        if (entityTNTPrimed.fuse - n5 + 1.0f < 10.0f) {
            final float clamp_float = MathHelper.clamp_float(1.0f - (entityTNTPrimed.fuse - n5 + 1.0f) / 10.0f, 0.0f, 1.0f);
            final float n6 = clamp_float * clamp_float;
            final float n7 = 1.0f + n6 * n6 * 0.3f;
            GlStateManager.scale(n7, n7, n7);
        }
        final float n8 = (1.0f - (entityTNTPrimed.fuse - n5 + 1.0f) / 100.0f) * 0.8f;
        this.bindEntityTexture(entityTNTPrimed);
        GlStateManager.translate(-0.5f, -0.5f, 0.5f);
        blockRendererDispatcher.renderBlockBrightness(Blocks.tnt.getDefaultState(), entityTNTPrimed.getBrightness(n5));
        GlStateManager.translate(0.0f, 0.0f, 1.0f);
        if (entityTNTPrimed.fuse / (0xC1 ^ 0xC4) % "  ".length() == 0) {
            GlStateManager.disableTexture2D();
            GlStateManager.disableLighting();
            GlStateManager.enableBlend();
            GlStateManager.blendFunc(678 + 561 - 611 + 142, 610 + 655 - 685 + 192);
            GlStateManager.color(1.0f, 1.0f, 1.0f, n8);
            GlStateManager.doPolygonOffset(-3.0f, -3.0f);
            GlStateManager.enablePolygonOffset();
            blockRendererDispatcher.renderBlockBrightness(Blocks.tnt.getDefaultState(), 1.0f);
            GlStateManager.doPolygonOffset(0.0f, 0.0f);
            GlStateManager.disablePolygonOffset();
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            GlStateManager.disableBlend();
            GlStateManager.enableLighting();
            GlStateManager.enableTexture2D();
        }
        GlStateManager.popMatrix();
        super.doRender(entityTNTPrimed, n, n2, n3, n4, n5);
    }
    
    public RenderTNTPrimed(final RenderManager renderManager) {
        super(renderManager);
        this.shadowSize = 0.5f;
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final EntityTNTPrimed entityTNTPrimed) {
        return TextureMap.locationBlocksTexture;
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return this.getEntityTexture((EntityTNTPrimed)entity);
    }
    
    @Override
    public void doRender(final Entity entity, final double n, final double n2, final double n3, final float n4, final float n5) {
        this.doRender((EntityTNTPrimed)entity, n, n2, n3, n4, n5);
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
            if (0 < 0) {
                throw null;
            }
        }
        return sb.toString();
    }
}
