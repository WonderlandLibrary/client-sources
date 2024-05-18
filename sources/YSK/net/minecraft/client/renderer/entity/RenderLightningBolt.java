package net.minecraft.client.renderer.entity;

import net.minecraft.entity.effect.*;
import java.util.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.client.renderer.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;

public class RenderLightningBolt extends Render<EntityLightningBolt>
{
    public RenderLightningBolt(final RenderManager renderManager) {
        super(renderManager);
    }
    
    @Override
    public void doRender(final EntityLightningBolt entityLightningBolt, final double n, final double n2, final double n3, final float n4, final float n5) {
        final Tessellator instance = Tessellator.getInstance();
        final WorldRenderer worldRenderer = instance.getWorldRenderer();
        GlStateManager.disableTexture2D();
        GlStateManager.disableLighting();
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(723 + 402 - 618 + 263, " ".length());
        final double[] array = new double[0xD ^ 0x5];
        final double[] array2 = new double[0x5 ^ 0xD];
        double n6 = 0.0;
        double n7 = 0.0;
        final Random random = new Random(entityLightningBolt.boltVertex);
        int i = 0x38 ^ 0x3F;
        "".length();
        if (2 >= 4) {
            throw null;
        }
        while (i >= 0) {
            array[i] = n6;
            array2[i] = n7;
            n6 += random.nextInt(0x3F ^ 0x34) - (0xBD ^ 0xB8);
            n7 += random.nextInt(0x10 ^ 0x1B) - (0xBC ^ 0xB9);
            --i;
        }
        int j = "".length();
        "".length();
        if (0 == 2) {
            throw null;
        }
        while (j < (0x55 ^ 0x51)) {
            final Random random2 = new Random(entityLightningBolt.boltVertex);
            int k = "".length();
            "".length();
            if (2 <= 0) {
                throw null;
            }
            while (k < "   ".length()) {
                int n8 = 0x6F ^ 0x68;
                int length = "".length();
                if (k > 0) {
                    n8 = (0x90 ^ 0x97) - k;
                }
                if (k > 0) {
                    length = n8 - "  ".length();
                }
                double n9 = array[n8] - n6;
                double n10 = array2[n8] - n7;
                int l = n8;
                "".length();
                if (-1 >= 0) {
                    throw null;
                }
                while (l >= length) {
                    final double n11 = n9;
                    final double n12 = n10;
                    if (k == 0) {
                        n9 += random2.nextInt(0x55 ^ 0x5E) - (0xA0 ^ 0xA5);
                        n10 += random2.nextInt(0x54 ^ 0x5F) - (0x32 ^ 0x37);
                        "".length();
                        if (3 < 0) {
                            throw null;
                        }
                    }
                    else {
                        n9 += random2.nextInt(0x2A ^ 0x35) - (0x49 ^ 0x46);
                        n10 += random2.nextInt(0xBF ^ 0xA0) - (0x65 ^ 0x6A);
                    }
                    worldRenderer.begin(0xAA ^ 0xAF, DefaultVertexFormats.POSITION_COLOR);
                    double n13 = 0.1 + j * 0.2;
                    if (k == 0) {
                        n13 *= l * 0.1 + 1.0;
                    }
                    double n14 = 0.1 + j * 0.2;
                    if (k == 0) {
                        n14 *= (l - " ".length()) * 0.1 + 1.0;
                    }
                    int length2 = "".length();
                    "".length();
                    if (3 < -1) {
                        throw null;
                    }
                    while (length2 < (0x93 ^ 0x96)) {
                        double n15 = n + 0.5 - n13;
                        double n16 = n3 + 0.5 - n13;
                        if (length2 == " ".length() || length2 == "  ".length()) {
                            n15 += n13 * 2.0;
                        }
                        if (length2 == "  ".length() || length2 == "   ".length()) {
                            n16 += n13 * 2.0;
                        }
                        double n17 = n + 0.5 - n14;
                        double n18 = n3 + 0.5 - n14;
                        if (length2 == " ".length() || length2 == "  ".length()) {
                            n17 += n14 * 2.0;
                        }
                        if (length2 == "  ".length() || length2 == "   ".length()) {
                            n18 += n14 * 2.0;
                        }
                        worldRenderer.pos(n17 + n9, n2 + l * (0xB4 ^ 0xA4), n18 + n10).color(0.45f, 0.45f, 0.5f, 0.3f).endVertex();
                        worldRenderer.pos(n15 + n11, n2 + (l + " ".length()) * (0x33 ^ 0x23), n16 + n12).color(0.45f, 0.45f, 0.5f, 0.3f).endVertex();
                        ++length2;
                    }
                    instance.draw();
                    --l;
                }
                ++k;
            }
            ++j;
        }
        GlStateManager.disableBlend();
        GlStateManager.enableLighting();
        GlStateManager.enableTexture2D();
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final EntityLightningBolt entityLightningBolt) {
        return null;
    }
    
    @Override
    public void doRender(final Entity entity, final double n, final double n2, final double n3, final float n4, final float n5) {
        this.doRender((EntityLightningBolt)entity, n, n2, n3, n4, n5);
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
            if (-1 < -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    protected ResourceLocation getEntityTexture(final Entity entity) {
        return this.getEntityTexture((EntityLightningBolt)entity);
    }
}
