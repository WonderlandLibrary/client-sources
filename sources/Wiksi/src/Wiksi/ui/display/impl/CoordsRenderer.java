/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package src.Wiksi.ui.display.impl;

import src.Wiksi.events.EventDisplay;
import src.Wiksi.ui.display.ElementRenderer;
import src.Wiksi.utils.render.ColorUtils;
import src.Wiksi.utils.render.DisplayUtils;
import src.Wiksi.utils.render.font.Fonts;
import src.Wiksi.utils.text.GradientUtil;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.StringTextComponent;

public class CoordsRenderer
        implements ElementRenderer {
    final ResourceLocation run = new ResourceLocation("Wiksi/images/run.png");
    final ResourceLocation gps = new ResourceLocation("Wiksi/images/gps.png");
    float iconSize = 8.0f;

    @Override
    public void render(EventDisplay eventDisplay) {
        float offset = 3.0f;
        float fontSize = 7.0f;
        float fontHeight = Fonts.sfui.getHeight(fontSize);
        StringTextComponent xyz = GradientUtil.gradient("XYZ:");
        StringTextComponent bps = GradientUtil.gradient("BPS:");
        float posX = offset;
        float posY = (float)window.getScaledHeight() - offset - fontHeight;
        float posX1 = window.getScaledWidth();
        float stringWidth = Fonts.sfui.getWidth("XYZ:", fontSize);
        float stringWidth2 = Fonts.sfui.getWidth((int)CoordsRenderer.mc.player.getPosX() + ", " + (int)CoordsRenderer.mc.player.getPosY() + ", " + (int)CoordsRenderer.mc.player.getPosZ(), fontSize);
        DisplayUtils.drawShadow(posX - 0.5f, posY - 7.0f, 40.0f + stringWidth2, 15.0f, 15, ColorUtils.rgba(21, 24, 40, 165));
        DisplayUtils.drawShadow(posX1 - 60.0f, posY - 7.0f, posX + 55.0f, 15.0f, 15, ColorUtils.rgba(21, 24, 40, 165));
        DisplayUtils.drawRoundedRect(posX - 0.5f, posY - 7.0f, 40.0f + stringWidth2, 15.0f, 4.0f, ColorUtils.setAlpha(ColorUtils.rgb(21, 24, 40), 165));
        DisplayUtils.drawRoundedRect(posX1 - 60.0f, posY - 7.0f, posX + 55.0f, 15.0f, 4.0f, ColorUtils.rgba(21, 24, 40, 165));
        Fonts.sfui.drawText(eventDisplay.getMatrixStack(), xyz, posX + 13.0f, posY - 3.0f, fontSize, 255);
        Fonts.sfui.drawText(eventDisplay.getMatrixStack(), (int)CoordsRenderer.mc.player.getPosX() + " | " + (int)CoordsRenderer.mc.player.getPosY() + " | " + (int)CoordsRenderer.mc.player.getPosZ(), posX + 13.0f + stringWidth, posY - 3.0f, ColorUtils.rgb(255, 255, 255), fontSize, 0.0f);
        stringWidth = Fonts.sfui.getWidth("BPS:", fontSize);
        stringWidth2 = Fonts.sfui.getWidth((int)CoordsRenderer.mc.player.getPosX() + ", " + (int)CoordsRenderer.mc.player.getPosY() + ", " + (int)CoordsRenderer.mc.player.getPosZ(), fontSize);
        Fonts.sfui.drawText(eventDisplay.getMatrixStack(), bps, posX1 - 45.0f, (posY -= 12.0f) + 9.0f, fontSize, 255);
        Fonts.sfui.drawText(eventDisplay.getMatrixStack(), String.format("%.2f", Math.hypot(CoordsRenderer.mc.player.prevPosX - CoordsRenderer.mc.player.getPosX(), CoordsRenderer.mc.player.prevPosZ - CoordsRenderer.mc.player.getPosZ()) * 20.0), posX1 - 44.0f + stringWidth, posY + 9.0f, ColorUtils.rgb(255, 255, 255), fontSize, 0.0f);
        DisplayUtils.drawRectVerticalW(posX + 15.0f, posY + 4.0f, 1.0, 14.0, 3, ColorUtils.rgba(21, 21, 21, 0));
        DisplayUtils.drawImage(this.gps, posX + 2.5f, posY + 8.5f, this.iconSize, this.iconSize, ColorUtils.rgb(40, 212, 163));
        DisplayUtils.drawRectVerticalW(posX1 - 47.0f, posY + 4.0f, 1.0, 14.0, 3, ColorUtils.rgba(21, 21, 21, 0));
        DisplayUtils.drawImage(this.run, posX1 - 55.5f, posY + 8.5f, this.iconSize, this.iconSize, ColorUtils.rgb(40, 212, 163));
    }
}

