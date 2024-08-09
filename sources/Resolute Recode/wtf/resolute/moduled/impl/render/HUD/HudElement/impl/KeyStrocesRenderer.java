package wtf.resolute.moduled.impl.render.HUD.HudElement.impl;

import com.mojang.blaze3d.matrix.MatrixStack;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import wtf.resolute.evented.EventDisplay;
import wtf.resolute.moduled.impl.render.HUD.HudElement.ElementRenderer;
import wtf.resolute.manage.drag.Dragging;
import wtf.resolute.utiled.render.ColorUtils;
import wtf.resolute.utiled.render.DisplayUtils;
import wtf.resolute.utiled.render.font.Fonts;

import java.awt.*;

@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class KeyStrocesRenderer implements ElementRenderer {
    final Dragging dragging;


    float width;
    float height;

    @Override
    public void render(EventDisplay eventDisplay) {
        MatrixStack ms = eventDisplay.getMatrixStack();
        int firstColor = ColorUtils.getColorStyle(0);
        int secondColor = ColorUtils.getColorStyle(100);
        float posX = dragging.getX();
        float posY = dragging.getY();
        float fontSize = 6.5f;
        float padding = 5;
            float width = 20.0F;
            float height = 20.0F;
            String w = "W";
            String a = "A";
            String s = "S";
            String d = "D";
        DisplayUtils.drawShadow(posX, posY, width, height, 8, firstColor,secondColor);
            DisplayUtils.drawRoundedRect(posX, posY, width, height, 4.0F, DisplayUtils.reAlphaInt(new Color(33, 32, 34).getRGB(), 210));
            Fonts.sfMedium.drawText(ms, w,posX + 6.0F,posY + 8.0F, new Color(33, 32, 34).getRGB(),fontSize);
        DisplayUtils.drawShadow(posX, posY + 23.0F, width, height, 8, firstColor,secondColor);
            DisplayUtils.drawRoundedRect(posX, posY + 23.0F, width, height, 4.0F, DisplayUtils.reAlphaInt(new Color(33, 32, 34).getRGB(), 210));
            Fonts.sfMedium.drawText(ms, s,posX + 7.5F,posY + 30.0F, new Color(33, 32, 34).getRGB(),fontSize);
        DisplayUtils.drawShadow(posX - 23.0F, posY + 23.0F, width, height, 8, firstColor,secondColor);
            DisplayUtils.drawRoundedRect(posX - 23.0F, posY + 23.0F, width, height, 4.0F, DisplayUtils.reAlphaInt(new Color(33, 32, 34).getRGB(), 210));
            Fonts.sfMedium.drawText(ms, a,posX - 16.5F,posY + 30.0F, new Color(33, 32, 34).getRGB(),fontSize);
        DisplayUtils.drawShadow(posX + 23.0F, posY + 23.0F, width, height, 8, firstColor,secondColor);
            DisplayUtils.drawRoundedRect(posX + 23.0F, posY + 23.0F, width, height, 4.0F, DisplayUtils.reAlphaInt(new Color(33, 32, 34).getRGB(), 210));
            Fonts.sfMedium.drawText(ms, d,posX + 30.0F,posY + 30.0F, new Color(33, 32, 34).getRGB(),fontSize);
        if (mc.gameSettings.keyBindForward.isKeyDown()) {
                Fonts.sfMedium.drawText(ms,w, (posX + 6.0F), (posY + 8.0F), -1,fontSize);
            }

            if (mc.gameSettings.keyBindBack.isKeyDown()) {
                Fonts.sfMedium.drawText(ms,s, posX + 7.5F,posY + 30.0F, -1,fontSize);
            }

            if (mc.gameSettings.keyBindLeft.isKeyDown()) {
                Fonts.sfMedium.drawText(ms,a, (posX - 16.0F), (posY + 30.0F), -1,fontSize);
            }

            if (mc.gameSettings.keyBindRight.isKeyDown()) {
                Fonts.sfMedium.drawText(ms,d, (posX + 30.0F),(posY + 30.0F), -1,fontSize);
            }
        dragging.setWidth(width);
        dragging.setHeight(height);
        }
    }
