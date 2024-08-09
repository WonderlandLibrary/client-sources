package src.Wiksi.ui.display.impl;

import src.Wiksi.events.EventDisplay;
import src.Wiksi.ui.display.ElementRenderer;
import src.Wiksi.utils.render.ColorUtils;
import src.Wiksi.utils.render.DisplayUtils;
import src.Wiksi.utils.render.KawaseBlur;
import src.Wiksi.utils.render.font.Fonts;
import src.Wiksi.utils.text.GradientUtil;
import lombok.RequiredArgsConstructor;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

@RequiredArgsConstructor
public class Coords2Renderer implements ElementRenderer {


    final ResourceLocation run = new ResourceLocation("Wiksi/images/hud/bps1.png");
    final ResourceLocation gps = new ResourceLocation("Wiksi/images/hud/gps1.png");
    float iconSize = 10;

    @Override
    public void render(EventDisplay eventDisplay) {
        float offset = 3;
        float fontSize = 7;
        float fontHeight = Fonts.sfui.getHeight(fontSize);

        final ITextComponent xyz = GradientUtil.gradient("XYZ:");
        final ITextComponent bps = GradientUtil.gradient("BPS:");

        float posX = offset;
        float posY = window.getScaledHeight() - offset - fontHeight;
        float posX1 = window.getScaledWidth();

        float stringWidth = Fonts.sfui.getWidth("XYZ:", fontSize);
        float stringWidth2 = Fonts.sfui.getWidth((int) mc.player.getPosX() + " | " + (int) mc.player.getPosY() + " | " + (int) mc.player.getPosZ(), fontSize);
        DisplayUtils.drawRoundedRect(posX - 2 , posY - 10 , 44.0f + stringWidth2, 18.0f, 3, ColorUtils.rgba(21, 21, 21, 215));
        DisplayUtils.drawRoundedRect(posX1 - 65  , posY - 10 , 65.0f, 18.0f, 3, ColorUtils.rgba(21, 21, 21, 215));


        Fonts.sfui.drawText(eventDisplay.getMatrixStack(), "XYZ:", posX + 17, posY - 4,ColorUtils.rgb(255,255,255), fontSize);

        Fonts.sfui.drawText(eventDisplay.getMatrixStack(), (int) mc.player.getPosX() + " | " + (int) mc.player.getPosY() + " | " + (int) mc.player.getPosZ(), posX + 18 + stringWidth, posY - 4, ColorUtils.rgb(1, 209, 35), fontSize, 0.05f);

        posY -= 12;
        stringWidth = Fonts.sfui.getWidth("BPS:", fontSize);
        stringWidth2 = Fonts.sfui.getWidth((int) mc.player.getPosX() + " | " + (int) mc.player.getPosY() + " | " + (int) mc.player.getPosZ(), fontSize);

        Fonts.sfui.drawText(eventDisplay.getMatrixStack(), "BPS:", posX1 - 45, posY + 8,ColorUtils.rgb(255,255,255), fontSize);

        Fonts.sfui.drawText(eventDisplay.getMatrixStack(), String.format("%.2f", Math.hypot(mc.player.prevPosX - mc.player.getPosX(), mc.player.prevPosZ - mc.player.getPosZ()) * 20),posX1 - 44 + stringWidth, posY + 8, ColorUtils.rgb(1, 209, 35), fontSize, 0.05f);
        DisplayUtils.drawRectVerticalW(posX + 15.0f, posY + 4.0f, 1, 14.0f, 3, ColorUtils.rgba(255, 255, 255, (int) (255 * 0.75f)));
        DisplayUtils.drawImage(gps, posX + 2.5f, posY + 6.5f, iconSize, iconSize, ColorUtils.rgb(255, 255, 255));
        DisplayUtils.drawRectVerticalW(posX1 - 47, posY + 4.0f, 1, 14.0f, 3, ColorUtils.rgba(255, 255, 255, (int) (255 * 0.75f)));
        DisplayUtils.drawImage(run, posX1 - 60.0f, posY + 6.5f, iconSize, iconSize, ColorUtils.rgb(255, 255, 255));
    }
}
