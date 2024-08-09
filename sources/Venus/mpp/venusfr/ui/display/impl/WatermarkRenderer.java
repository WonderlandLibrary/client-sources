/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.ui.display.impl;

import com.mojang.blaze3d.matrix.MatrixStack;
import java.text.SimpleDateFormat;
import java.util.Date;
import mpp.venusfr.events.EventDisplay;
import mpp.venusfr.ui.display.ElementRenderer;
import mpp.venusfr.ui.styles.Style;
import mpp.venusfr.utils.client.PingUtil;
import mpp.venusfr.utils.render.ColorUtils;
import mpp.venusfr.utils.render.DisplayUtils;
import mpp.venusfr.utils.render.font.Fonts;
import mpp.venusfr.venusfr;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

public class WatermarkRenderer
implements ElementRenderer {
    private final Minecraft mc = Minecraft.getInstance();
    private final ResourceLocation logo = new ResourceLocation("venusfr/images/hud/logo.png");
    private final ResourceLocation fpsIcon = new ResourceLocation("venusfr/images/hud/fps.png");
    private final ResourceLocation timeIcon = new ResourceLocation("venusfr/images/hud/time.png");
    private final ResourceLocation userIcon = new ResourceLocation("venusfr/images/hud/user.png");
    private final ResourceLocation compassIcon = new ResourceLocation("venusfr/images/hud/compass.png");
    private final ResourceLocation internetIcon = new ResourceLocation("venusfr/images/hud/internet.png");
    private final ResourceLocation bpsIcon = new ResourceLocation("venusfr/images/hud/bps.png");

    @Override
    public void render(EventDisplay eventDisplay) {
        MatrixStack matrixStack = eventDisplay.getMatrixStack();
        float f = 4.0f;
        float f2 = 4.0f;
        float f3 = 5.0f;
        float f4 = 6.5f;
        float f5 = 10.0f;
        float f6 = 80.0f;
        Style style = venusfr.getInstance().getStyleManager().getCurrentStyle();
        String string = "VenusFree";
        float f7 = Fonts.sfui.getWidth(string, f4);
        DisplayUtils.drawShadow(f, f2, f5 + f3 * 2.5f + f7, f5 + f3 * 2.0f, 15, ColorUtils.rgba(21, 24, 40, 165));
        DisplayUtils.drawRoundedRect(f, f2, f5 + f3 * 2.5f + f7, f5 + f3 * 2.0f, 4.0f, ColorUtils.rgba(21, 24, 40, 165));
        DisplayUtils.drawImage(this.logo, f + f3, f2 + f3, f5, f5, ColorUtils.getColor(1));
        Fonts.sfui.drawText(matrixStack, string, f + f5 + f3 * 1.5f, f2 + f5 / 2.0f + 1.5f, ColorUtils.rgb(255, 255, 255), f4);
        int n = this.mc.getDebugFPS();
        String string2 = String.valueOf(n) + " Fps";
        float f8 = Fonts.sfui.getWidth(string2, f4);
        float f9 = f + f5 + f3 * 2.5f + f7 + f3;
        DisplayUtils.drawShadow(f9, f2, f5 + f3 * 2.5f + f8, f5 + f3 * 2.0f, 15, ColorUtils.rgba(21, 24, 40, 165));
        DisplayUtils.drawRoundedRect(f9, f2, f5 + f3 * 2.5f + f8, f5 + f3 * 2.0f, 4.0f, ColorUtils.rgba(21, 24, 40, 165));
        DisplayUtils.drawImage(this.fpsIcon, f9 + f3, f2 + f3, f5, f5, ColorUtils.getColor(1));
        Fonts.sfui.drawText(matrixStack, string2, f9 + f5 + f3 * 1.5f, f2 + f5 / 2.0f + 1.5f, ColorUtils.rgb(255, 255, 255), f4);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
        String string3 = simpleDateFormat.format(new Date());
        float f10 = Fonts.sfui.getWidth(string3, f4);
        float f11 = f9 + f5 + f3 * 2.5f + f8 + f3;
        DisplayUtils.drawShadow(f11, f2, f5 + f3 * 2.5f + f10, f5 + f3 * 2.0f, 15, ColorUtils.rgba(21, 24, 40, 165));
        DisplayUtils.drawRoundedRect(f11, f2, f5 + f3 * 2.5f + f10, f5 + f3 * 2.0f, 4.0f, ColorUtils.rgba(21, 24, 40, 165));
        DisplayUtils.drawImage(this.timeIcon, f11 + f3, f2 + f3, f5, f5, ColorUtils.getColor(1));
        Fonts.sfui.drawText(matrixStack, string3, f11 + f5 + f3 * 1.5f, f2 + f5 / 2.0f + 1.5f, ColorUtils.rgb(255, 255, 255), f4);
        String string4 = this.mc.player.getName().getString();
        float f12 = Fonts.sfui.getWidth(string4, f4);
        float f13 = f11 + f5 + f3 * 2.5f + f10 + f3;
        float f14 = f2;
        DisplayUtils.drawShadow(f13, f14, f5 + f3 * 2.5f + f12, f5 + f3 * 2.0f, 15, ColorUtils.rgba(21, 24, 40, 165));
        DisplayUtils.drawRoundedRect(f13, f14, f5 + f3 * 2.5f + f12, f5 + f3 * 2.0f, 4.0f, ColorUtils.rgba(21, 24, 40, 165));
        DisplayUtils.drawImage(this.userIcon, f13 + f3, f2 + f3, f5, f5, ColorUtils.getColor(1));
        Fonts.sfui.drawText(matrixStack, string4, f13 + f3 * 1.5f + f5, f2 + f5 / 2.0f + 1.5f, ColorUtils.rgb(255, 255, 255), f4);
        float f15 = f2 + f5 + f3 * 2.0f + f3;
        String string5 = (int)this.mc.player.getPosX() + " " + (int)this.mc.player.getPosY() + " " + (int)this.mc.player.getPosZ();
        float f16 = Fonts.sfui.getWidth(string5, f4);
        float f17 = f;
        float f18 = f15;
        DisplayUtils.drawShadow(f17, f18, f5 + f3 * 2.5f + f16, f5 + f3 * 2.0f, 15, ColorUtils.rgba(21, 24, 40, 165));
        DisplayUtils.drawRoundedRect(f17, f18, f5 + f3 * 2.5f + f16, f5 + f3 * 2.0f, 4.0f, ColorUtils.rgba(21, 24, 40, 165));
        DisplayUtils.drawImage(this.compassIcon, f17 + f3, f15 + f3, f5, f5, ColorUtils.getColor(1));
        Fonts.sfui.drawText(matrixStack, string5, f17 + f3 * 1.5f + f5, f15 + f5 / 2.0f + 1.5f, ColorUtils.rgb(255, 255, 255), f4);
        String string6 = String.valueOf(PingUtil.calculatePing()) + " ms";
        float f19 = Fonts.sfui.getWidth(string6, f4);
        float f20 = f17 + f5 + f3 * 2.5f + f16 + f3;
        float f21 = f18;
        DisplayUtils.drawShadow(f20, f21, f5 + f3 * 2.5f + f19, f5 + f3 * 2.0f, 15, ColorUtils.rgba(21, 24, 40, 165));
        DisplayUtils.drawRoundedRect(f20, f21, f5 + f3 * 2.5f + f19, f5 + f3 * 2.0f, 4.0f, ColorUtils.rgba(21, 24, 40, 165));
        DisplayUtils.drawImage(this.internetIcon, f20 + f3, f21 + f3, f5, f5, ColorUtils.getColor(1));
        Fonts.sfui.drawText(matrixStack, string6, f20 + f3 * 1.5f + f5, f21 + f5 / 2.0f + 1.5f, ColorUtils.rgb(255, 255, 255), f4);
        String string7 = String.valueOf(PingUtil.calculateBPS()) + " BPS";
        float f22 = Fonts.sfui.getWidth(string7, f4);
        float f23 = f20 + f5 + f3 * 2.5f + f19 + f3;
        float f24 = f21;
        DisplayUtils.drawShadow(f23, f24, f5 + f3 * 2.5f + f22, f5 + f3 * 2.0f, 15, ColorUtils.rgba(21, 24, 40, 165));
        DisplayUtils.drawRoundedRect(f23, f24, f5 + f3 * 2.5f + f22, f5 + f3 * 2.0f, 4.0f, ColorUtils.rgba(21, 24, 40, 165));
        DisplayUtils.drawImage(this.bpsIcon, f23 + f3, f24 + f3, f5, f5, ColorUtils.getColor(1));
        Fonts.sfui.drawText(matrixStack, string7, f23 + f3 * 1.5f + f5, f24 + f5 / 2.0f + 1.5f, ColorUtils.rgb(255, 255, 255), f4);
    }

    private void drawStyledRect(float f, float f2, float f3, float f4, float f5) {
        DisplayUtils.drawRoundedRect(f - 0.5f, f2 - 0.5f, f3 + 1.0f, f4 + 1.0f, f5 + 0.5f, ColorUtils.getColor(0));
        DisplayUtils.drawRoundedRect(f, f2, f3, f4, f5, ColorUtils.rgba(21, 21, 21, 255));
    }
}

