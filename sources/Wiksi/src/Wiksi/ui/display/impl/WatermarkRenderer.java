/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package src.Wiksi.ui.display.impl;

import com.mojang.blaze3d.matrix.MatrixStack;
import java.text.SimpleDateFormat;
import java.util.Date;
import src.Wiksi.events.EventDisplay;
import src.Wiksi.ui.display.ElementRenderer;
import src.Wiksi.ui.styles.Style;
import src.Wiksi.utils.client.PingUtil;
import src.Wiksi.utils.render.ColorUtils;
import src.Wiksi.utils.render.DisplayUtils;
import src.Wiksi.utils.render.font.Fonts;
import src.Wiksi.Wiksi;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

public class WatermarkRenderer
implements ElementRenderer {
    private final Minecraft mc = Minecraft.getInstance();
    private final ResourceLocation logo = new ResourceLocation("Wiksi/images/watermark/logo.png");
    private final ResourceLocation fpsIcon = new ResourceLocation("Wiksi/images/watermark/fps.png");
    private final ResourceLocation timeIcon = new ResourceLocation("Wiksi/images/watermark/time.png");
    private final ResourceLocation userIcon = new ResourceLocation("Wiksi/images/watermark/user.png");
    private final ResourceLocation compassIcon = new ResourceLocation("Wiksi/images/watermark/compass.png");
    private final ResourceLocation internetIcon = new ResourceLocation("Wiksi/images/watermark/internet.png");
    private final ResourceLocation bpsIcon = new ResourceLocation("Wiksi/images/watermark/bps.png");

    @Override
    public void render(EventDisplay eventDisplay) {
        MatrixStack matrixStack = eventDisplay.getMatrixStack();
        float f2 = 4.0f;
        float f3 = 5.0f;
        float f4 = 6.5f;
        float f5 = 10.0f;
        int n = this.mc.getDebugFPS();
        float f6 = 80.0f;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        String string = "WIKSI SRC | BY KNOWQE FURIOUS CLIENT";
        String string2 = String.valueOf(n) + " Fps";
        float f7 = Fonts.sfui.getWidth(string, f4);
        String string3 = simpleDateFormat.format(new Date());
        String string4 = this.mc.player.getName().getString();
        String string5 = (int)this.mc.player.getPosX() + " " + (int)this.mc.player.getPosY() + " " + (int)this.mc.player.getPosZ();
        String string6 = String.valueOf(PingUtil.calculatePing()) + " ms";
        String string7 = String.valueOf(PingUtil.calculateBPS()) + " BPS";
        float f8 = Fonts.sfui.getWidth(string2, f4);
        float f = 0;
        float f9 = f + f5 + f3 * 2.5f + f7 + f3;
        float f10 = Fonts.sfui.getWidth(string3, f4);
        float f11 = f9 + f5 + f3 * 2.5f + f8 + f3;
        float f12 = Fonts.sfui.getWidth(string4, f4);
        float f13 = f11 + f5 + f3 * 2.5f + f10 + f3;
        float f14 = f2;
        float f15 = f2 + f5 + f3 * 2.0f + f3;
        float f16 = Fonts.sfui.getWidth(string5, f4);
        float f18 = f15;
        float f19 = Fonts.sfui.getWidth(string6, f4);
        float f20 = f + f5 + f3 * 2.5f + f16 + f3;
        float f21 = f18;
        float f22 = Fonts.sfui.getWidth(string7, f4);
        float f23 = f20 + f5 + f3 * 2.5f + f19 + f3;
        float f24 = f21;
        int n2 = "\u54dc\u63c3".length();
        int n3 = "\u5a02\u6ad9\u5fd2\u553c\u5707".length();
        int n4 = "\u5471\u6a8d\u5691".length();
        DisplayUtils.drawShadow(f, f2, f5 + f3 * 2.5f + f7, f5 + f3 * 2.0f, 15, ColorUtils.rgba(21, 24, 40, 165));
        DisplayUtils.drawRoundedRect(f, f2, f5 + f3 * 2.5f + f7, f5 + f3 * 2.0f, 4.0f, ColorUtils.rgba(21, 24, 40, 165));
        DisplayUtils.drawImage(this.logo, f + f3, f2 + f3, f5, f5, ColorUtils.getColor(1));
        Fonts.sfui.drawText(matrixStack, string, f + f5 + f3 * 1.5f, f2 + f5 / 2.0f + 1.5f, ColorUtils.rgb(255, 255, 255), f4);
        Fonts.sfui.drawText(matrixStack, string4, f13 + f3 * 1.5f + f5, f2 + f5 / 2.0f + 1.5f, ColorUtils.rgb(255, 255, 255), f4);
        Fonts.sfui.drawText(matrixStack, string2, f9 + f5 + f3 * 1.5f, f2 + f5 / 2.0f + 1.5f, ColorUtils.rgb(255, 255, 255), f4);
        DisplayUtils.drawShadow(f9, f2, f5 + f3 * 2.5f + f8, f5 + f3 * 2.0f, 15, ColorUtils.rgba(21, 24, 40, 165));
        DisplayUtils.drawRoundedRect(f9, f2, f5 + f3 * 2.5f + f8, f5 + f3 * 2.0f, 4.0f, ColorUtils.rgba(21, 24, 40, 165));
        DisplayUtils.drawImage(this.fpsIcon, f9 + f3, f2 + f3, f5, f5, ColorUtils.getColor(1));
        Fonts.sfui.drawText(matrixStack, string2, f9 + f5 + f3 * 1.5f, f2 + f5 / 2.0f + 1.5f, ColorUtils.rgb(255, 255, 255), f4);
        DisplayUtils.drawShadow(f11, f2, f5 + f3 * 2.5f + f10, f5 + f3 * 2.0f, 15, ColorUtils.rgba(21, 24, 40, 165));
        DisplayUtils.drawRoundedRect(f11, f2, f5 + f3 * 2.5f + f10, f5 + f3 * 2.0f, 4.0f, ColorUtils.rgba(21, 24, 40, 165));
        DisplayUtils.drawImage(this.timeIcon, f11 + f3, f2 + f3, f5, f5, ColorUtils.getColor(1));
        Fonts.sfui.drawText(matrixStack, string3, f11 + f5 + f3 * 1.5f, f2 + f5 / 2.0f + 1.5f, ColorUtils.rgb(255, 255, 255), f4);
        DisplayUtils.drawShadow(f13, f14, f5 + f3 * 2.5f + f12, f5 + f3 * 2.0f, 15, ColorUtils.rgba(21, 24, 40, 165));
        DisplayUtils.drawRoundedRect(f13, f14, f5 + f3 * 2.5f + f12, f5 + f3 * 2.0f, 4.0f, ColorUtils.rgba(21, 24, 40, 165));
        DisplayUtils.drawImage(this.userIcon, f13 + f3, f2 + f3, f5, f5, ColorUtils.getColor(1));
        Fonts.sfui.drawText(matrixStack, string4, f13 + f3 * 1.5f + f5, f2 + f5 / 2.0f + 1.5f, ColorUtils.rgb(255, 255, 255), f4);
        DisplayUtils.drawShadow(f, f18, f5 + f3 * 2.5f + f16, f5 + f3 * 2.0f, 15, ColorUtils.rgba(21, 24, 40, 165));
        DisplayUtils.drawRoundedRect(f, f18, f5 + f3 * 2.5f + f16, f5 + f3 * 2.0f, 4.0f, ColorUtils.rgba(21, 24, 40, 165));
        DisplayUtils.drawImage(this.compassIcon, f + f3, f15 + f3, f5, f5, ColorUtils.getColor(1));
        Fonts.sfui.drawText(matrixStack, string5, f + f3 * 1.5f + f5, f15 + f5 / 2.0f + 1.5f, ColorUtils.rgb(255, 255, 255), f4);
        DisplayUtils.drawShadow(f20, f21, f5 + f3 * 2.5f + f19, f5 + f3 * 2.0f, 15, ColorUtils.rgba(21, 24, 40, 165));
        DisplayUtils.drawRoundedRect(f20, f21, f5 + f3 * 2.5f + f19, f5 + f3 * 2.0f, 4.0f, ColorUtils.rgba(21, 24, 40, 165));
        DisplayUtils.drawImage(this.internetIcon, f20 + f3, f21 + f3, f5, f5, ColorUtils.getColor(1));
        Fonts.sfui.drawText(matrixStack, string6, f20 + f3 * 1.5f + f5, f21 + f5 / 2.0f + 1.5f, ColorUtils.rgb(255, 255, 255), f4);
        DisplayUtils.drawShadow(f23, f24, f5 + f3 * 2.5f + f22, f5 + f3 * 2.0f, 15, ColorUtils.rgba(21, 24, 40, 165));
        DisplayUtils.drawRoundedRect(f23, f24, f5 + f3 * 2.5f + f22, f5 + f3 * 2.0f, 4.0f, ColorUtils.rgba(21, 24, 40, 165));
        DisplayUtils.drawImage(this.bpsIcon, f23 + f3, f24 + f3, f5, f5, ColorUtils.getColor(1));
        Fonts.sfui.drawText(matrixStack, string7, f23 + f3 * 1.5f + f5, f24 + f5 / 2.0f + 1.5f, ColorUtils.rgb(255, 255, 255), f4);
    }

    private void drawStyledRect(float f, float f2, float f3, float f4, float f5) {
        int n = "\u574b".length();
        int n2 = "\u6a23\u5267\u51ba\u64f0\u6e66".length();
        int n3 = "\u5a46\u4fec\u6dfc\u66b1".length();
        int n4 = "\u63cb\u6e98\u647b\u6097\u6d6b".length();
        int n5 = "\u595f\u6469\u5988\u60e4".length();
        int n6 = "\u4e2a\u58d0".length();
        int n7 = "\u6a47\u6e88\u6728".length();
        int n8 = "\u5742\u6cfa".length();
        DisplayUtils.drawRoundedRect(f - 0.5f, f2 - 0.5f, f3 + 1.0f, f4 + 1.0f, f5 + 0.5f, ColorUtils.getColor(0));
        DisplayUtils.drawRoundedRect(f, f2, f3, f4, f5, ColorUtils.rgba(21, 21, 21, 255));
    }
}

