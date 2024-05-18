package net.minecraft.client.renderer.tileentity;

import org.lwjgl.opengl.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.client.renderer.*;
import java.util.*;
import net.minecraft.tileentity.*;

public class TileEntityBeaconRenderer extends TileEntitySpecialRenderer<TileEntityBeacon>
{
    private static final ResourceLocation beaconBeam;
    private static final String[] I;
    
    @Override
    public boolean func_181055_a() {
        return " ".length() != 0;
    }
    
    @Override
    public void renderTileEntityAt(final TileEntityBeacon tileEntityBeacon, final double n, final double n2, final double n3, final float n4, final int n5) {
        final float shouldBeamRender = tileEntityBeacon.shouldBeamRender();
        GlStateManager.alphaFunc(278 + 73 - 247 + 412, 0.1f);
        if (shouldBeamRender > 0.0f) {
            final Tessellator instance = Tessellator.getInstance();
            final WorldRenderer worldRenderer = instance.getWorldRenderer();
            GlStateManager.disableFog();
            final List<TileEntityBeacon.BeamSegment> beamSegments = tileEntityBeacon.getBeamSegments();
            int length = "".length();
            int i = "".length();
            "".length();
            if (0 <= -1) {
                throw null;
            }
            while (i < beamSegments.size()) {
                final TileEntityBeacon.BeamSegment beamSegment = beamSegments.get(i);
                final int n6 = length + beamSegment.getHeight();
                this.bindTexture(TileEntityBeaconRenderer.beaconBeam);
                GL11.glTexParameterf(2745 + 2997 - 4372 + 2183, 6502 + 4210 - 2377 + 1907, 10497.0f);
                GL11.glTexParameterf(759 + 2410 - 2575 + 2959, 5844 + 5829 - 1446 + 16, 10497.0f);
                GlStateManager.disableLighting();
                GlStateManager.disableCull();
                GlStateManager.disableBlend();
                GlStateManager.depthMask(" ".length() != 0);
                GlStateManager.tryBlendFuncSeparate(164 + 619 - 307 + 294, " ".length(), " ".length(), "".length());
                final double n7 = tileEntityBeacon.getWorld().getTotalWorldTime() + n4;
                final double func_181162_h = MathHelper.func_181162_h(-n7 * 0.2 - MathHelper.floor_double(-n7 * 0.1));
                final float n8 = beamSegment.getColors()["".length()];
                final float n9 = beamSegment.getColors()[" ".length()];
                final float n10 = beamSegment.getColors()["  ".length()];
                final double n11 = n7 * 0.025 * -1.5;
                final double n12 = 0.5 + Math.cos(n11 + 2.356194490192345) * 0.2;
                final double n13 = 0.5 + Math.sin(n11 + 2.356194490192345) * 0.2;
                final double n14 = 0.5 + Math.cos(n11 + 0.7853981633974483) * 0.2;
                final double n15 = 0.5 + Math.sin(n11 + 0.7853981633974483) * 0.2;
                final double n16 = 0.5 + Math.cos(n11 + 3.9269908169872414) * 0.2;
                final double n17 = 0.5 + Math.sin(n11 + 3.9269908169872414) * 0.2;
                final double n18 = 0.5 + Math.cos(n11 + 5.497787143782138) * 0.2;
                final double n19 = 0.5 + Math.sin(n11 + 5.497787143782138) * 0.2;
                final double n20 = -1.0 + func_181162_h;
                final double n21 = beamSegment.getHeight() * shouldBeamRender * 2.5 + n20;
                worldRenderer.begin(0x7E ^ 0x79, DefaultVertexFormats.POSITION_TEX_COLOR);
                worldRenderer.pos(n + n12, n2 + n6, n3 + n13).tex(1.0, n21).color(n8, n9, n10, 1.0f).endVertex();
                worldRenderer.pos(n + n12, n2 + length, n3 + n13).tex(1.0, n20).color(n8, n9, n10, 1.0f).endVertex();
                worldRenderer.pos(n + n14, n2 + length, n3 + n15).tex(0.0, n20).color(n8, n9, n10, 1.0f).endVertex();
                worldRenderer.pos(n + n14, n2 + n6, n3 + n15).tex(0.0, n21).color(n8, n9, n10, 1.0f).endVertex();
                worldRenderer.pos(n + n18, n2 + n6, n3 + n19).tex(1.0, n21).color(n8, n9, n10, 1.0f).endVertex();
                worldRenderer.pos(n + n18, n2 + length, n3 + n19).tex(1.0, n20).color(n8, n9, n10, 1.0f).endVertex();
                worldRenderer.pos(n + n16, n2 + length, n3 + n17).tex(0.0, n20).color(n8, n9, n10, 1.0f).endVertex();
                worldRenderer.pos(n + n16, n2 + n6, n3 + n17).tex(0.0, n21).color(n8, n9, n10, 1.0f).endVertex();
                worldRenderer.pos(n + n14, n2 + n6, n3 + n15).tex(1.0, n21).color(n8, n9, n10, 1.0f).endVertex();
                worldRenderer.pos(n + n14, n2 + length, n3 + n15).tex(1.0, n20).color(n8, n9, n10, 1.0f).endVertex();
                worldRenderer.pos(n + n18, n2 + length, n3 + n19).tex(0.0, n20).color(n8, n9, n10, 1.0f).endVertex();
                worldRenderer.pos(n + n18, n2 + n6, n3 + n19).tex(0.0, n21).color(n8, n9, n10, 1.0f).endVertex();
                worldRenderer.pos(n + n16, n2 + n6, n3 + n17).tex(1.0, n21).color(n8, n9, n10, 1.0f).endVertex();
                worldRenderer.pos(n + n16, n2 + length, n3 + n17).tex(1.0, n20).color(n8, n9, n10, 1.0f).endVertex();
                worldRenderer.pos(n + n12, n2 + length, n3 + n13).tex(0.0, n20).color(n8, n9, n10, 1.0f).endVertex();
                worldRenderer.pos(n + n12, n2 + n6, n3 + n13).tex(0.0, n21).color(n8, n9, n10, 1.0f).endVertex();
                instance.draw();
                GlStateManager.enableBlend();
                GlStateManager.tryBlendFuncSeparate(762 + 523 - 975 + 460, 730 + 573 - 1148 + 616, " ".length(), "".length());
                GlStateManager.depthMask("".length() != 0);
                final double n22 = -1.0 + func_181162_h;
                final double n23 = beamSegment.getHeight() * shouldBeamRender + n22;
                worldRenderer.begin(0x65 ^ 0x62, DefaultVertexFormats.POSITION_TEX_COLOR);
                worldRenderer.pos(n + 0.2, n2 + n6, n3 + 0.2).tex(1.0, n23).color(n8, n9, n10, 0.125f).endVertex();
                worldRenderer.pos(n + 0.2, n2 + length, n3 + 0.2).tex(1.0, n22).color(n8, n9, n10, 0.125f).endVertex();
                worldRenderer.pos(n + 0.8, n2 + length, n3 + 0.2).tex(0.0, n22).color(n8, n9, n10, 0.125f).endVertex();
                worldRenderer.pos(n + 0.8, n2 + n6, n3 + 0.2).tex(0.0, n23).color(n8, n9, n10, 0.125f).endVertex();
                worldRenderer.pos(n + 0.8, n2 + n6, n3 + 0.8).tex(1.0, n23).color(n8, n9, n10, 0.125f).endVertex();
                worldRenderer.pos(n + 0.8, n2 + length, n3 + 0.8).tex(1.0, n22).color(n8, n9, n10, 0.125f).endVertex();
                worldRenderer.pos(n + 0.2, n2 + length, n3 + 0.8).tex(0.0, n22).color(n8, n9, n10, 0.125f).endVertex();
                worldRenderer.pos(n + 0.2, n2 + n6, n3 + 0.8).tex(0.0, n23).color(n8, n9, n10, 0.125f).endVertex();
                worldRenderer.pos(n + 0.8, n2 + n6, n3 + 0.2).tex(1.0, n23).color(n8, n9, n10, 0.125f).endVertex();
                worldRenderer.pos(n + 0.8, n2 + length, n3 + 0.2).tex(1.0, n22).color(n8, n9, n10, 0.125f).endVertex();
                worldRenderer.pos(n + 0.8, n2 + length, n3 + 0.8).tex(0.0, n22).color(n8, n9, n10, 0.125f).endVertex();
                worldRenderer.pos(n + 0.8, n2 + n6, n3 + 0.8).tex(0.0, n23).color(n8, n9, n10, 0.125f).endVertex();
                worldRenderer.pos(n + 0.2, n2 + n6, n3 + 0.8).tex(1.0, n23).color(n8, n9, n10, 0.125f).endVertex();
                worldRenderer.pos(n + 0.2, n2 + length, n3 + 0.8).tex(1.0, n22).color(n8, n9, n10, 0.125f).endVertex();
                worldRenderer.pos(n + 0.2, n2 + length, n3 + 0.2).tex(0.0, n22).color(n8, n9, n10, 0.125f).endVertex();
                worldRenderer.pos(n + 0.2, n2 + n6, n3 + 0.2).tex(0.0, n23).color(n8, n9, n10, 0.125f).endVertex();
                instance.draw();
                GlStateManager.enableLighting();
                GlStateManager.enableTexture2D();
                GlStateManager.depthMask(" ".length() != 0);
                length = n6;
                ++i;
            }
            GlStateManager.enableFog();
        }
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("\u0001\u001c)\u0007\u0005\u0007\u001c\"\\\u0015\u001b\r8\u0007\tZ\u001b4\u0012\u0013\u001a\u0017\u000e\u0011\u0015\u0014\u0014\u007f\u0003\u001e\u0012", "uyQsp");
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
            if (2 < 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public void renderTileEntityAt(final TileEntity tileEntity, final double n, final double n2, final double n3, final float n4, final int n5) {
        this.renderTileEntityAt((TileEntityBeacon)tileEntity, n, n2, n3, n4, n5);
    }
    
    static {
        I();
        beaconBeam = new ResourceLocation(TileEntityBeaconRenderer.I["".length()]);
    }
}
