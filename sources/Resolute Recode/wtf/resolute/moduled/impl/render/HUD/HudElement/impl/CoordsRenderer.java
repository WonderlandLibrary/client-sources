package wtf.resolute.moduled.impl.render.HUD.HudElement.impl;

import com.mojang.blaze3d.matrix.MatrixStack;
import wtf.resolute.moduled.impl.render.HUD.HudElement.ElementRenderer;
import wtf.resolute.evented.EventDisplay;
import wtf.resolute.utiled.render.ColorUtils;
import wtf.resolute.utiled.render.DisplayUtils;
import wtf.resolute.utiled.render.font.Fonts;
import lombok.RequiredArgsConstructor;

import java.awt.*;

@RequiredArgsConstructor
public class CoordsRenderer implements ElementRenderer {


    @Override
    public void render(EventDisplay eventDisplay) {
        MatrixStack ms = eventDisplay.getMatrixStack();
        float offset = 3;
        float fontSize = 7;
        float fontHeight = Fonts.sfMedium.getHeight(fontSize);
        float posX = offset;
       // float posY = window.getScaledHeight() - offset - fontHeight;
        float posY = 19;
        String pos = (int) mc.player.getPosX() + " | " + (int) mc.player.getPosY() + " | " + (int) mc.player.getPosZ();
        float stringWidth2 = Fonts.sfMedium.getWidth("XYZ: " + pos, fontSize) + 6;
        float stringWidth = Fonts.sfMedium.getWidth("XYZ: ", fontSize);
        int firstColor = ColorUtils.getColorStyle(0);
        int secondColor = ColorUtils.getColorStyle(100);
        DisplayUtils.drawShadow(4, posY, stringWidth2, 13, 8, firstColor,secondColor);
        DisplayUtils.drawRoundedRect(4, posY, stringWidth2, 13, 3, DisplayUtils.reAlphaInt(new Color(33, 32, 34).getRGB(), 210));
        Fonts.sfMedium.drawText(ms, "XYZ: ", 7, posY+3, -1, fontSize, 0.05f);
        Fonts.sfMedium.drawText(ms, (int) mc.player.getPosX() + ", "
                + (int) mc.player.getPosY() + ", " + (int) mc.player.getPosZ(), 7 + stringWidth, posY+3, -1, fontSize, 0.05f);
        posY -= 12;
    }
}
