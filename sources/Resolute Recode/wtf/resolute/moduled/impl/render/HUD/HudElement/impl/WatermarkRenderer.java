package wtf.resolute.moduled.impl.render.HUD.HudElement.impl;

import com.mojang.blaze3d.matrix.MatrixStack;
import wtf.resolute.evented.EventDisplay;
import wtf.resolute.moduled.impl.render.HUD.HudElement.ElementRenderer;
import wtf.resolute.utiled.client.HudUtil;
import wtf.resolute.utiled.font.Fonted;
import wtf.resolute.utiled.render.ColorUtils;
import wtf.resolute.utiled.render.DisplayUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.awt.*;

@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class WatermarkRenderer implements ElementRenderer {
    @Override
    public void render(EventDisplay eventDisplay) {
        MatrixStack ms = eventDisplay.getMatrixStack();
        final String title = "RESOLUTE | " + mc.debugFPS + " fps | " + HudUtil.calculatePing() + " ping";
        float posX = 4;
        float posY = 4;
        float textWidth = Fonted.rubik[14].getWidth(title) + 8;
        int firstColor = ColorUtils.getColorStyle(0);
        int secondColor = ColorUtils.getColorStyle(100);
        DisplayUtils.drawShadow(posX, posY, textWidth + 1, 13, 4, firstColor,secondColor);
        DisplayUtils.drawRoundedRect(posX, posY, textWidth, 13, 1, DisplayUtils.reAlphaInt(new Color(33, 32, 34).getRGB(), 250));
        DisplayUtils.drawRoundedRect(posX, posY, textWidth, 2, 1, ColorUtils.getColor(0));
        Fonted.rubik[14].drawString(ms, title, posX + 3, posY + 5.5f,-1);
    }
}

