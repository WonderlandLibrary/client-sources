package net.minecraft.client.renderer.entity;

import net.minecraft.entity.item.*;
import net.minecraft.block.state.*;
import net.minecraft.util.*;
import net.minecraft.client.*;
import net.minecraft.init.*;
import net.minecraft.client.renderer.*;

public class RenderTntMinecart extends RenderMinecart<EntityMinecartTNT>
{
    public RenderTntMinecart(final RenderManager renderManager) {
        super(renderManager);
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
    protected void func_180560_a(final EntityMinecart entityMinecart, final float n, final IBlockState blockState) {
        this.func_180560_a((EntityMinecartTNT)entityMinecart, n, blockState);
    }
    
    @Override
    protected void func_180560_a(final EntityMinecartTNT entityMinecartTNT, final float n, final IBlockState blockState) {
        final int fuseTicks = entityMinecartTNT.getFuseTicks();
        if (fuseTicks > -" ".length() && fuseTicks - n + 1.0f < 10.0f) {
            final float clamp_float = MathHelper.clamp_float(1.0f - (fuseTicks - n + 1.0f) / 10.0f, 0.0f, 1.0f);
            final float n2 = clamp_float * clamp_float;
            final float n3 = 1.0f + n2 * n2 * 0.3f;
            GlStateManager.scale(n3, n3, n3);
        }
        super.func_180560_a(entityMinecartTNT, n, blockState);
        if (fuseTicks > -" ".length() && fuseTicks / (0x1 ^ 0x4) % "  ".length() == 0) {
            final BlockRendererDispatcher blockRendererDispatcher = Minecraft.getMinecraft().getBlockRendererDispatcher();
            GlStateManager.disableTexture2D();
            GlStateManager.disableLighting();
            GlStateManager.enableBlend();
            GlStateManager.blendFunc(518 + 450 - 505 + 307, 70 + 249 + 81 + 372);
            GlStateManager.color(1.0f, 1.0f, 1.0f, (1.0f - (fuseTicks - n + 1.0f) / 100.0f) * 0.8f);
            GlStateManager.pushMatrix();
            blockRendererDispatcher.renderBlockBrightness(Blocks.tnt.getDefaultState(), 1.0f);
            GlStateManager.popMatrix();
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            GlStateManager.disableBlend();
            GlStateManager.enableLighting();
            GlStateManager.enableTexture2D();
        }
    }
}
