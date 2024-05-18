package net.minecraft.client.gui;

import net.minecraft.client.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.client.renderer.*;

public class GuiStreamIndicator
{
    private final Minecraft mc;
    private int field_152444_d;
    private static final ResourceLocation locationStreamIndicator;
    private float field_152443_c;
    private static final String[] I;
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("\u0000+3 7\u0006+8{%\u0001'd'6\u0006+*9\u001d\u001d /=!\u0015:$&l\u0004 ,", "tNKTB");
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
            if (2 != 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public GuiStreamIndicator(final Minecraft mc) {
        this.field_152443_c = 1.0f;
        this.field_152444_d = " ".length();
        this.mc = mc;
    }
    
    private void render(final int n, final int n2, final int n3, final int n4) {
        GlStateManager.color(1.0f, 1.0f, 1.0f, 0.65f + 0.35000002f * this.field_152443_c);
        this.mc.getTextureManager().bindTexture(GuiStreamIndicator.locationStreamIndicator);
        final float n5 = 150.0f;
        final float n6 = 0.0f;
        final float n7 = n3 * 0.015625f;
        final float n8 = 1.0f;
        final float n9 = (n3 + (0x72 ^ 0x62)) * 0.015625f;
        final Tessellator instance = Tessellator.getInstance();
        final WorldRenderer worldRenderer = instance.getWorldRenderer();
        worldRenderer.begin(0x53 ^ 0x54, DefaultVertexFormats.POSITION_TEX);
        worldRenderer.pos(n - (0x4C ^ 0x5C) - n4, n2 + (0x21 ^ 0x31), n5).tex(n6, n9).endVertex();
        worldRenderer.pos(n - n4, n2 + (0xAA ^ 0xBA), n5).tex(n8, n9).endVertex();
        worldRenderer.pos(n - n4, n2 + "".length(), n5).tex(n8, n7).endVertex();
        worldRenderer.pos(n - (0x70 ^ 0x60) - n4, n2 + "".length(), n5).tex(n6, n7).endVertex();
        instance.draw();
        GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
    }
    
    private int func_152438_c() {
        int n;
        if (this.mc.getTwitchStream().func_152929_G()) {
            n = (0x6E ^ 0x5E);
            "".length();
            if (1 >= 3) {
                throw null;
            }
        }
        else {
            n = (0xE7 ^ 0xC7);
        }
        return n;
    }
    
    private int func_152440_b() {
        int length;
        if (this.mc.getTwitchStream().isPaused()) {
            length = (0x13 ^ 0x3);
            "".length();
            if (-1 >= 2) {
                throw null;
            }
        }
        else {
            length = "".length();
        }
        return length;
    }
    
    public void render(final int n, final int n2) {
        if (this.mc.getTwitchStream().isBroadcasting()) {
            GlStateManager.enableBlend();
            final int func_152920_A = this.mc.getTwitchStream().func_152920_A();
            if (func_152920_A > 0) {
                final String string = new StringBuilder().append(func_152920_A).toString();
                final int stringWidth = this.mc.fontRendererObj.getStringWidth(string);
                final int n3 = n - stringWidth - " ".length();
                final int n4 = n2 + (0x97 ^ 0x83) - " ".length();
                final int n5 = n2 + (0xBB ^ 0xAF) + this.mc.fontRendererObj.FONT_HEIGHT - " ".length();
                GlStateManager.disableTexture2D();
                final Tessellator instance = Tessellator.getInstance();
                final WorldRenderer worldRenderer = instance.getWorldRenderer();
                GlStateManager.color(0.0f, 0.0f, 0.0f, (0.65f + 0.35000002f * this.field_152443_c) / 2.0f);
                worldRenderer.begin(0x9F ^ 0x98, DefaultVertexFormats.POSITION);
                worldRenderer.pos(n3, n5, 0.0).endVertex();
                worldRenderer.pos(n, n5, 0.0).endVertex();
                worldRenderer.pos(n, n4, 0.0).endVertex();
                worldRenderer.pos(n3, n4, 0.0).endVertex();
                instance.draw();
                GlStateManager.enableTexture2D();
                this.mc.fontRendererObj.drawString(string, n - stringWidth, n2 + (0x1C ^ 0x8), 5927673 + 14498797 - 15040925 + 11391670);
            }
            this.render(n, n2, this.func_152440_b(), "".length());
            this.render(n, n2, this.func_152438_c(), 0x1E ^ 0xF);
        }
    }
    
    static {
        I();
        locationStreamIndicator = new ResourceLocation(GuiStreamIndicator.I["".length()]);
    }
    
    public void func_152439_a() {
        if (this.mc.getTwitchStream().isBroadcasting()) {
            this.field_152443_c += 0.025f * this.field_152444_d;
            if (this.field_152443_c < 0.0f) {
                this.field_152444_d *= -" ".length();
                this.field_152443_c = 0.0f;
                "".length();
                if (2 <= 1) {
                    throw null;
                }
            }
            else if (this.field_152443_c > 1.0f) {
                this.field_152444_d *= -" ".length();
                this.field_152443_c = 1.0f;
                "".length();
                if (4 == 1) {
                    throw null;
                }
            }
        }
        else {
            this.field_152443_c = 1.0f;
            this.field_152444_d = " ".length();
        }
    }
}
