package fun.ellant.ui.display.impl;

import fun.ellant.ui.display.ElementRenderer;
import fun.ellant.events.EventDisplay;
import fun.ellant.utils.render.ColorUtils;
import fun.ellant.utils.render.DisplayUtils;
import fun.ellant.utils.render.font.Fonts;
import fun.ellant.utils.text.GradientUtil;
import lombok.RequiredArgsConstructor;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

@RequiredArgsConstructor
public class CoordsRenderer implements ElementRenderer {


    final ResourceLocation run = new ResourceLocation("expensive/images/hud/run2.png");
    final ResourceLocation gps = new ResourceLocation("expensive/images/hud/gps2.png");
    float iconSize = 10;

    @Override
    public void render(EventDisplay eventDisplay) {
        float offset = 3;
        float fontSize = 7;
        float fontHeight = Fonts.sfui.getHeight(fontSize);

        final ITextComponent xyz = GradientUtil.gradient("", ColorUtils.rgba(255, 0, 0, 255), ColorUtils.rgba(0, 255, 0, 255));
        final ITextComponent bps = GradientUtil.gradient("", ColorUtils.rgba(255, 0, 0, 255), ColorUtils.rgba(0, 255, 0, 255));

        float posX = offset;
        float posY = window.getScaledHeight() - offset - fontHeight;
        float posX1 = window.getScaledWidth();

        float stringWidth = Fonts.sfui.getWidth("", fontSize);
        float stringWidth2 = Fonts.sfui.getWidth((int) mc.player.getPosX() + ", " + (int) mc.player.getPosY() + ", " + (int) mc.player.getPosZ(), fontSize);
        DisplayUtils.drawRoundedRect(posX - 2 , posY - 10 , 42.0f + stringWidth2, 18.0f, 3, ColorUtils.rgba(30, 30, 30, 205));
        DisplayUtils.drawRoundedRect(posX1 - 60  , posY - 10 , 60.0f, 18.0f, 3, ColorUtils.rgba(30, 30, 30, 205));

        Fonts.sfui.drawText(eventDisplay.getMatrixStack(), xyz, posX + 17 , posY - 4 , fontSize, 255);

        Fonts.sfui.drawText(eventDisplay.getMatrixStack(), (int) mc.player.getPosX() + ", " + (int) mc.player.getPosY() + ", " + (int) mc.player.getPosZ(), posX + 18 + stringWidth, posY - 4, ColorUtils.rgb(255, 255, 255), fontSize, 0.05f);

        posY -= 12;
        stringWidth = Fonts.sfui.getWidth("", fontSize);
        stringWidth2 = Fonts.sfui.getWidth((int) mc.player.getPosX() + ", " + (int) mc.player.getPosY() + ", " + (int) mc.player.getPosZ(), fontSize);

        Fonts.sfui.drawText(eventDisplay.getMatrixStack(), bps, posX1 - 45, posY + 8, fontSize, 255);

        Fonts.sfui.drawText(eventDisplay.getMatrixStack(), String.format("%.2f", Math.hypot(mc.player.prevPosX - mc.player.getPosX(), mc.player.prevPosZ - mc.player.getPosZ()) * 20),posX1 - 44 + stringWidth, posY + 8, ColorUtils.rgb(255, 255, 255), fontSize, 0.05f);
        DisplayUtils.drawRectVerticalW(posX + 15.0f, posY + 4.0f, 1, 14.0f, 3, ColorUtils.rgba(0, 0, 0, (int) (205 * 0.75f)));
        DisplayUtils.drawImage(gps, posX + 2.5f, posY + 6.5f, iconSize, iconSize, ColorUtils.rgb(255, 255, 255));
        DisplayUtils.drawRectVerticalW(posX1 - 47, posY + 4.0f, 1, 14.0f, 3, ColorUtils.rgba(0, 0, 0, (int) (205 * 0.75f)));
        DisplayUtils.drawImage(run, posX1 - 58.5f, posY + 6.5f, iconSize, iconSize, ColorUtils.rgb(255, 255, 255));
    }
}
