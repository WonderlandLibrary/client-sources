package net.minecraft.client.gui;

import net.minecraft.util.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.client.renderer.*;

public class Gui
{
    public static final ResourceLocation statIcons;
    private static final String[] I;
    public static final ResourceLocation icons;
    protected float zLevel;
    public static final ResourceLocation optionsBackground;
    
    public void drawTexturedModalRect(final float n, final float n2, final int n3, final int n4, final int n5, final int n6) {
        final float n7 = 0.00390625f;
        final float n8 = 0.00390625f;
        final Tessellator instance = Tessellator.getInstance();
        final WorldRenderer worldRenderer = instance.getWorldRenderer();
        worldRenderer.begin(0xA9 ^ 0xAE, DefaultVertexFormats.POSITION_TEX);
        worldRenderer.pos(n + 0.0f, n2 + n6, this.zLevel).tex((n3 + "".length()) * n7, (n4 + n6) * n8).endVertex();
        worldRenderer.pos(n + n5, n2 + n6, this.zLevel).tex((n3 + n5) * n7, (n4 + n6) * n8).endVertex();
        worldRenderer.pos(n + n5, n2 + 0.0f, this.zLevel).tex((n3 + n5) * n7, (n4 + "".length()) * n8).endVertex();
        worldRenderer.pos(n + 0.0f, n2 + 0.0f, this.zLevel).tex((n3 + "".length()) * n7, (n4 + "".length()) * n8).endVertex();
        instance.draw();
    }
    
    protected void drawHorizontalLine(int n, int n2, final int n3, final int n4) {
        if (n2 < n) {
            final int n5 = n;
            n = n2;
            n2 = n5;
        }
        drawRect(n, n3, n2 + " ".length(), n3 + " ".length(), n4);
    }
    
    private static void I() {
        (I = new String["   ".length()])["".length()] = I("9\"\u001c<=?\"\u0017g/8.K'89.\u000b&;\u0012%\u0005+#*5\u000b=&)i\u0014&/", "MGdHH");
        Gui.I[" ".length()] = I("\"3\u0017\u001f\u0014$3\u001cD\u0006#?@\b\u000e8\"\u000e\u0002\u000f3$@\u0018\u00157\"\u001c4\b59\u0001\u0018O&8\b", "VVoka");
        Gui.I["  ".length()] = I("85>-9>55v+99i0/#>5w<\"7", "LPFYL");
    }
    
    public void drawTexturedModalRect(final int n, final int n2, final TextureAtlasSprite textureAtlasSprite, final int n3, final int n4) {
        final Tessellator instance = Tessellator.getInstance();
        final WorldRenderer worldRenderer = instance.getWorldRenderer();
        worldRenderer.begin(0xA7 ^ 0xA0, DefaultVertexFormats.POSITION_TEX);
        worldRenderer.pos(n + "".length(), n2 + n4, this.zLevel).tex(textureAtlasSprite.getMinU(), textureAtlasSprite.getMaxV()).endVertex();
        worldRenderer.pos(n + n3, n2 + n4, this.zLevel).tex(textureAtlasSprite.getMaxU(), textureAtlasSprite.getMaxV()).endVertex();
        worldRenderer.pos(n + n3, n2 + "".length(), this.zLevel).tex(textureAtlasSprite.getMaxU(), textureAtlasSprite.getMinV()).endVertex();
        worldRenderer.pos(n + "".length(), n2 + "".length(), this.zLevel).tex(textureAtlasSprite.getMinU(), textureAtlasSprite.getMinV()).endVertex();
        instance.draw();
    }
    
    protected void drawVerticalLine(final int n, int n2, int n3, final int n4) {
        if (n3 < n2) {
            final int n5 = n2;
            n2 = n3;
            n3 = n5;
        }
        drawRect(n, n2 + " ".length(), n + " ".length(), n3, n4);
    }
    
    static {
        I();
        optionsBackground = new ResourceLocation(Gui.I["".length()]);
        statIcons = new ResourceLocation(Gui.I[" ".length()]);
        icons = new ResourceLocation(Gui.I["  ".length()]);
    }
    
    protected void drawGradientRect(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        final float n7 = (n5 >> (0x45 ^ 0x5D) & 11 + 170 - 180 + 254) / 255.0f;
        final float n8 = (n5 >> (0x70 ^ 0x60) & 88 + 23 - 24 + 168) / 255.0f;
        final float n9 = (n5 >> (0x9C ^ 0x94) & 243 + 90 - 212 + 134) / 255.0f;
        final float n10 = (n5 & 9 + 218 - 100 + 128) / 255.0f;
        final float n11 = (n6 >> (0x6F ^ 0x77) & 138 + 82 + 6 + 29) / 255.0f;
        final float n12 = (n6 >> (0x68 ^ 0x78) & 160 + 77 - 206 + 224) / 255.0f;
        final float n13 = (n6 >> (0x4C ^ 0x44) & 44 + 241 - 137 + 107) / 255.0f;
        final float n14 = (n6 & 231 + 91 - 110 + 43) / 255.0f;
        GlStateManager.disableTexture2D();
        GlStateManager.enableBlend();
        GlStateManager.disableAlpha();
        GlStateManager.tryBlendFuncSeparate(91 + 449 - 227 + 457, 560 + 131 - 604 + 684, " ".length(), "".length());
        GlStateManager.shadeModel(5421 + 5676 - 5703 + 2031);
        final Tessellator instance = Tessellator.getInstance();
        final WorldRenderer worldRenderer = instance.getWorldRenderer();
        worldRenderer.begin(0xA8 ^ 0xAF, DefaultVertexFormats.POSITION_COLOR);
        worldRenderer.pos(n3, n2, this.zLevel).color(n8, n9, n10, n7).endVertex();
        worldRenderer.pos(n, n2, this.zLevel).color(n8, n9, n10, n7).endVertex();
        worldRenderer.pos(n, n4, this.zLevel).color(n12, n13, n14, n11).endVertex();
        worldRenderer.pos(n3, n4, this.zLevel).color(n12, n13, n14, n11).endVertex();
        instance.draw();
        GlStateManager.shadeModel(1267 + 1437 + 4548 + 172);
        GlStateManager.disableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.enableTexture2D();
    }
    
    public void drawString(final FontRenderer fontRenderer, final String s, final int n, final int n2, final int n3) {
        fontRenderer.drawStringWithShadow(s, n, n2, n3);
    }
    
    public void drawTexturedModalRect(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        final float n7 = 0.00390625f;
        final float n8 = 0.00390625f;
        final Tessellator instance = Tessellator.getInstance();
        final WorldRenderer worldRenderer = instance.getWorldRenderer();
        worldRenderer.begin(0x82 ^ 0x85, DefaultVertexFormats.POSITION_TEX);
        worldRenderer.pos(n + "".length(), n2 + n6, this.zLevel).tex((n3 + "".length()) * n7, (n4 + n6) * n8).endVertex();
        worldRenderer.pos(n + n5, n2 + n6, this.zLevel).tex((n3 + n5) * n7, (n4 + n6) * n8).endVertex();
        worldRenderer.pos(n + n5, n2 + "".length(), this.zLevel).tex((n3 + n5) * n7, (n4 + "".length()) * n8).endVertex();
        worldRenderer.pos(n + "".length(), n2 + "".length(), this.zLevel).tex((n3 + "".length()) * n7, (n4 + "".length()) * n8).endVertex();
        instance.draw();
    }
    
    public void drawCenteredString(final FontRenderer fontRenderer, final String s, final int n, final int n2, final int n3) {
        fontRenderer.drawStringWithShadow(s, n - fontRenderer.getStringWidth(s) / "  ".length(), n2, n3);
    }
    
    public static void drawModalRectWithCustomSizedTexture(final int n, final int n2, final float n3, final float n4, final int n5, final int n6, final float n7, final float n8) {
        final float n9 = 1.0f / n7;
        final float n10 = 1.0f / n8;
        final Tessellator instance = Tessellator.getInstance();
        final WorldRenderer worldRenderer = instance.getWorldRenderer();
        worldRenderer.begin(0x27 ^ 0x20, DefaultVertexFormats.POSITION_TEX);
        worldRenderer.pos(n, n2 + n6, 0.0).tex(n3 * n9, (n4 + n6) * n10).endVertex();
        worldRenderer.pos(n + n5, n2 + n6, 0.0).tex((n3 + n5) * n9, (n4 + n6) * n10).endVertex();
        worldRenderer.pos(n + n5, n2, 0.0).tex((n3 + n5) * n9, n4 * n10).endVertex();
        worldRenderer.pos(n, n2, 0.0).tex(n3 * n9, n4 * n10).endVertex();
        instance.draw();
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
            if (3 <= 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public static void drawRect(int n, int n2, int n3, int n4, final int n5) {
        if (n < n3) {
            final int n6 = n;
            n = n3;
            n3 = n6;
        }
        if (n2 < n4) {
            final int n7 = n2;
            n2 = n4;
            n4 = n7;
        }
        final float n8 = (n5 >> (0x6B ^ 0x73) & 234 + 65 - 86 + 42) / 255.0f;
        final float n9 = (n5 >> (0x8C ^ 0x9C) & 223 + 68 - 194 + 158) / 255.0f;
        final float n10 = (n5 >> (0x37 ^ 0x3F) & 240 + 35 - 229 + 209) / 255.0f;
        final float n11 = (n5 & 77 + 12 + 7 + 159) / 255.0f;
        final Tessellator instance = Tessellator.getInstance();
        final WorldRenderer worldRenderer = instance.getWorldRenderer();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture2D();
        GlStateManager.tryBlendFuncSeparate(0 + 340 - 327 + 757, 310 + 560 - 290 + 191, " ".length(), "".length());
        GlStateManager.color(n9, n10, n11, n8);
        worldRenderer.begin(0x0 ^ 0x7, DefaultVertexFormats.POSITION);
        worldRenderer.pos(n, n4, 0.0).endVertex();
        worldRenderer.pos(n3, n4, 0.0).endVertex();
        worldRenderer.pos(n3, n2, 0.0).endVertex();
        worldRenderer.pos(n, n2, 0.0).endVertex();
        instance.draw();
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
    }
    
    public static void drawScaledCustomSizeModalRect(final int n, final int n2, final float n3, final float n4, final int n5, final int n6, final int n7, final int n8, final float n9, final float n10) {
        final float n11 = 1.0f / n9;
        final float n12 = 1.0f / n10;
        final Tessellator instance = Tessellator.getInstance();
        final WorldRenderer worldRenderer = instance.getWorldRenderer();
        worldRenderer.begin(0x8A ^ 0x8D, DefaultVertexFormats.POSITION_TEX);
        worldRenderer.pos(n, n2 + n8, 0.0).tex(n3 * n11, (n4 + n6) * n12).endVertex();
        worldRenderer.pos(n + n7, n2 + n8, 0.0).tex((n3 + n5) * n11, (n4 + n6) * n12).endVertex();
        worldRenderer.pos(n + n7, n2, 0.0).tex((n3 + n5) * n11, n4 * n12).endVertex();
        worldRenderer.pos(n, n2, 0.0).tex(n3 * n11, n4 * n12).endVertex();
        instance.draw();
    }
}
