package net.minecraft.client.renderer.tileentity;

import net.minecraft.util.*;
import java.nio.*;
import java.util.*;
import net.minecraft.client.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.client.renderer.*;
import net.minecraft.tileentity.*;

public class TileEntityEndPortalRenderer extends TileEntitySpecialRenderer<TileEntityEndPortal>
{
    private static final ResourceLocation END_PORTAL_TEXTURE;
    private static final String[] I;
    FloatBuffer field_147528_b;
    private static final ResourceLocation END_SKY_TEXTURE;
    private static final Random field_147527_e;
    
    private FloatBuffer func_147525_a(final float n, final float n2, final float n3, final float n4) {
        this.field_147528_b.clear();
        this.field_147528_b.put(n).put(n2).put(n3).put(n4);
        this.field_147528_b.flip();
        return this.field_147528_b;
    }
    
    @Override
    public void renderTileEntityAt(final TileEntityEndPortal tileEntityEndPortal, final double n, final double n2, final double n3, final float n4, final int n5) {
        final float n6 = (float)this.rendererDispatcher.entityX;
        final float n7 = (float)this.rendererDispatcher.entityY;
        final float n8 = (float)this.rendererDispatcher.entityZ;
        GlStateManager.disableLighting();
        TileEntityEndPortalRenderer.field_147527_e.setSeed(31100L);
        final float n9 = 0.75f;
        int i = "".length();
        "".length();
        if (2 < 1) {
            throw null;
        }
        while (i < (0x19 ^ 0x9)) {
            GlStateManager.pushMatrix();
            float n10 = (0x5C ^ 0x4C) - i;
            float n11 = 0.0625f;
            float n12 = 1.0f / (n10 + 1.0f);
            if (i == 0) {
                this.bindTexture(TileEntityEndPortalRenderer.END_SKY_TEXTURE);
                n12 = 0.1f;
                n10 = 65.0f;
                n11 = 0.125f;
                GlStateManager.enableBlend();
                GlStateManager.blendFunc(0 + 495 - 278 + 553, 409 + 225 - 249 + 386);
            }
            if (i >= " ".length()) {
                this.bindTexture(TileEntityEndPortalRenderer.END_PORTAL_TEXTURE);
            }
            if (i == " ".length()) {
                GlStateManager.enableBlend();
                GlStateManager.blendFunc(" ".length(), " ".length());
                n11 = 0.5f;
            }
            final float n13 = (float)(-(n2 + n9));
            GlStateManager.translate(n6, (float)(n2 + n9) + (n13 + (float)ActiveRenderInfo.getPosition().yCoord) / (n13 + n10 + (float)ActiveRenderInfo.getPosition().yCoord), n8);
            GlStateManager.texGen(GlStateManager.TexGen.S, 1800 + 2600 - 164 + 4981);
            GlStateManager.texGen(GlStateManager.TexGen.T, 3666 + 5548 - 6966 + 6969);
            GlStateManager.texGen(GlStateManager.TexGen.R, 3392 + 5102 + 358 + 365);
            GlStateManager.texGen(GlStateManager.TexGen.Q, 3463 + 6009 - 7364 + 7108);
            GlStateManager.func_179105_a(GlStateManager.TexGen.S, 7824 + 7223 - 11607 + 6033, this.func_147525_a(1.0f, 0.0f, 0.0f, 0.0f));
            GlStateManager.func_179105_a(GlStateManager.TexGen.T, 9453 + 6568 - 14692 + 8144, this.func_147525_a(0.0f, 0.0f, 1.0f, 0.0f));
            GlStateManager.func_179105_a(GlStateManager.TexGen.R, 677 + 124 + 1947 + 6725, this.func_147525_a(0.0f, 0.0f, 0.0f, 1.0f));
            GlStateManager.func_179105_a(GlStateManager.TexGen.Q, 3585 + 8021 - 9185 + 7053, this.func_147525_a(0.0f, 1.0f, 0.0f, 0.0f));
            GlStateManager.enableTexGenCoord(GlStateManager.TexGen.S);
            GlStateManager.enableTexGenCoord(GlStateManager.TexGen.T);
            GlStateManager.enableTexGenCoord(GlStateManager.TexGen.R);
            GlStateManager.enableTexGenCoord(GlStateManager.TexGen.Q);
            GlStateManager.popMatrix();
            GlStateManager.matrixMode(912 + 3883 + 120 + 975);
            GlStateManager.pushMatrix();
            GlStateManager.loadIdentity();
            GlStateManager.translate(0.0f, Minecraft.getSystemTime() % 700000L / 700000.0f, 0.0f);
            GlStateManager.scale(n11, n11, n11);
            GlStateManager.translate(0.5f, 0.5f, 0.0f);
            GlStateManager.rotate((i * i * (713 + 1748 + 376 + 1484) + i * (0x6 ^ 0xF)) * 2.0f, 0.0f, 0.0f, 1.0f);
            GlStateManager.translate(-0.5f, -0.5f, 0.0f);
            GlStateManager.translate(-n6, -n8, -n7);
            final float n14 = n13 + (float)ActiveRenderInfo.getPosition().yCoord;
            GlStateManager.translate((float)ActiveRenderInfo.getPosition().xCoord * n10 / n14, (float)ActiveRenderInfo.getPosition().zCoord * n10 / n14, -n7);
            final Tessellator instance = Tessellator.getInstance();
            final WorldRenderer worldRenderer = instance.getWorldRenderer();
            worldRenderer.begin(0x29 ^ 0x2E, DefaultVertexFormats.POSITION_COLOR);
            float n15 = (TileEntityEndPortalRenderer.field_147527_e.nextFloat() * 0.5f + 0.1f) * n12;
            float n16 = (TileEntityEndPortalRenderer.field_147527_e.nextFloat() * 0.5f + 0.4f) * n12;
            float n17 = (TileEntityEndPortalRenderer.field_147527_e.nextFloat() * 0.5f + 0.5f) * n12;
            if (i == 0) {
                n16 = (n15 = (n17 = 1.0f * n12));
            }
            worldRenderer.pos(n, n2 + n9, n3).color(n15, n16, n17, 1.0f).endVertex();
            worldRenderer.pos(n, n2 + n9, n3 + 1.0).color(n15, n16, n17, 1.0f).endVertex();
            worldRenderer.pos(n + 1.0, n2 + n9, n3 + 1.0).color(n15, n16, n17, 1.0f).endVertex();
            worldRenderer.pos(n + 1.0, n2 + n9, n3).color(n15, n16, n17, 1.0f).endVertex();
            instance.draw();
            GlStateManager.popMatrix();
            GlStateManager.matrixMode(1245 + 3174 - 1631 + 3100);
            this.bindTexture(TileEntityEndPortalRenderer.END_SKY_TEXTURE);
            ++i;
        }
        GlStateManager.disableBlend();
        GlStateManager.disableTexGenCoord(GlStateManager.TexGen.S);
        GlStateManager.disableTexGenCoord(GlStateManager.TexGen.T);
        GlStateManager.disableTexGenCoord(GlStateManager.TexGen.R);
        GlStateManager.disableTexGenCoord(GlStateManager.TexGen.Q);
        GlStateManager.enableLighting();
    }
    
    public TileEntityEndPortalRenderer() {
        this.field_147528_b = GLAllocation.createDirectFloatBuffer(0xA1 ^ 0xB1);
    }
    
    private static void I() {
        (I = new String["  ".length()])["".length()] = I("\u0003(?\u0010\u0011\u0005(4K\u0001\u0019;.\u0016\u000b\u0019 \"\n\u0010X()\u0000;\u0004&>J\u0014\u0019*", "wMGdd");
        TileEntityEndPortalRenderer.I[" ".length()] = I("02\u001b&962\u0010})*#\n&5k2\r6\u001348\u0011&-(y\u0013<+", "DWcRL");
    }
    
    @Override
    public void renderTileEntityAt(final TileEntity tileEntity, final double n, final double n2, final double n3, final float n4, final int n5) {
        this.renderTileEntityAt((TileEntityEndPortal)tileEntity, n, n2, n3, n4, n5);
    }
    
    static {
        I();
        END_SKY_TEXTURE = new ResourceLocation(TileEntityEndPortalRenderer.I["".length()]);
        END_PORTAL_TEXTURE = new ResourceLocation(TileEntityEndPortalRenderer.I[" ".length()]);
        field_147527_e = new Random(31100L);
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
            if (0 <= -1) {
                throw null;
            }
        }
        return sb.toString();
    }
}
