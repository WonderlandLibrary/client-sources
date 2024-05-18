package net.minecraft.client.renderer.entity.layers;

import net.minecraft.entity.boss.*;
import net.minecraft.entity.*;
import java.util.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.client.renderer.*;

public class LayerEnderDragonDeath implements LayerRenderer<EntityDragon>
{
    @Override
    public void doRenderLayer(final EntityLivingBase entityLivingBase, final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final float n7) {
        this.doRenderLayer((EntityDragon)entityLivingBase, n, n2, n3, n4, n5, n6, n7);
    }
    
    @Override
    public boolean shouldCombineTextures() {
        return "".length() != 0;
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
            if (true != true) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public void doRenderLayer(final EntityDragon entityDragon, final float n, final float n2, final float n3, final float n4, final float n5, final float n6, final float n7) {
        if (entityDragon.deathTicks > 0) {
            final Tessellator instance = Tessellator.getInstance();
            final WorldRenderer worldRenderer = instance.getWorldRenderer();
            RenderHelper.disableStandardItemLighting();
            final float n8 = (entityDragon.deathTicks + n3) / 200.0f;
            float n9 = 0.0f;
            if (n8 > 0.8f) {
                n9 = (n8 - 0.8f) / 0.2f;
            }
            final Random random = new Random(432L);
            GlStateManager.disableTexture2D();
            GlStateManager.shadeModel(4576 + 7051 - 4761 + 559);
            GlStateManager.enableBlend();
            GlStateManager.blendFunc(32 + 653 - 628 + 713, " ".length());
            GlStateManager.disableAlpha();
            GlStateManager.enableCull();
            GlStateManager.depthMask("".length() != 0);
            GlStateManager.pushMatrix();
            GlStateManager.translate(0.0f, -1.0f, -2.0f);
            int length = "".length();
            "".length();
            if (0 >= 1) {
                throw null;
            }
            while (length < (n8 + n8 * n8) / 2.0f * 60.0f) {
                GlStateManager.rotate(random.nextFloat() * 360.0f, 1.0f, 0.0f, 0.0f);
                GlStateManager.rotate(random.nextFloat() * 360.0f, 0.0f, 1.0f, 0.0f);
                GlStateManager.rotate(random.nextFloat() * 360.0f, 0.0f, 0.0f, 1.0f);
                GlStateManager.rotate(random.nextFloat() * 360.0f, 1.0f, 0.0f, 0.0f);
                GlStateManager.rotate(random.nextFloat() * 360.0f, 0.0f, 1.0f, 0.0f);
                GlStateManager.rotate(random.nextFloat() * 360.0f + n8 * 90.0f, 0.0f, 0.0f, 1.0f);
                final float n10 = random.nextFloat() * 20.0f + 5.0f + n9 * 10.0f;
                final float n11 = random.nextFloat() * 2.0f + 1.0f + n9 * 2.0f;
                worldRenderer.begin(0xB3 ^ 0xB5, DefaultVertexFormats.POSITION_COLOR);
                worldRenderer.pos(0.0, 0.0, 0.0).color(197 + 8 + 33 + 17, 135 + 66 - 196 + 250, 140 + 53 + 43 + 19, (int)(255.0f * (1.0f - n9))).endVertex();
                worldRenderer.pos(-0.866 * n11, n10, -0.5f * n11).color(13 + 61 + 167 + 14, "".length(), 27 + 173 - 38 + 93, "".length()).endVertex();
                worldRenderer.pos(0.866 * n11, n10, -0.5f * n11).color(144 + 222 - 333 + 222, "".length(), 196 + 34 - 121 + 146, "".length()).endVertex();
                worldRenderer.pos(0.0, n10, 1.0f * n11).color(2 + 8 + 30 + 215, "".length(), 112 + 220 - 309 + 232, "".length()).endVertex();
                worldRenderer.pos(-0.866 * n11, n10, -0.5f * n11).color(98 + 214 - 128 + 71, "".length(), 26 + 141 + 65 + 23, "".length()).endVertex();
                instance.draw();
                ++length;
            }
            GlStateManager.popMatrix();
            GlStateManager.depthMask(" ".length() != 0);
            GlStateManager.disableCull();
            GlStateManager.disableBlend();
            GlStateManager.shadeModel(618 + 6816 - 4722 + 4712);
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            GlStateManager.enableTexture2D();
            GlStateManager.enableAlpha();
            RenderHelper.enableStandardItemLighting();
        }
    }
}
