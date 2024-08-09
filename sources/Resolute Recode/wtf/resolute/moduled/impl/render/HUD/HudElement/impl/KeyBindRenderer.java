package wtf.resolute.moduled.impl.render.HUD.HudElement.impl;

import com.mojang.blaze3d.matrix.MatrixStack;
import wtf.resolute.ResoluteInfo;
import wtf.resolute.evented.EventDisplay;
import wtf.resolute.moduled.Module;
import wtf.resolute.moduled.impl.render.HUD.HudElement.ElementRenderer;
import wtf.resolute.utiled.client.KeyStorage;
import wtf.resolute.manage.drag.Dragging;
import wtf.resolute.utiled.font.Fonted;
import wtf.resolute.utiled.render.ColorUtils;
import wtf.resolute.utiled.render.DisplayUtils;
import wtf.resolute.utiled.render.Scissor;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import wtf.resolute.utiled.render.font.Fonts;

import java.awt.*;

@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class KeyBindRenderer implements ElementRenderer {

    final Dragging dragging;


    float width;
    float height;

    @Override
    public void render(EventDisplay eventDisplay) {
        MatrixStack ms = eventDisplay.getMatrixStack();

        float posX = dragging.getX();
        float posY = dragging.getY();
        float fontSize = 6.5f;
        float padding = 5;
        int firstColor = ColorUtils.getColorStyle(0);
        int secondColor = ColorUtils.getColorStyle(100);
        DisplayUtils.drawShadow(posX, posY, width, height, 8, firstColor,secondColor);
        DisplayUtils.drawRoundedRect(posX, posY, width, height, 3, DisplayUtils.reAlphaInt(new Color(33, 32, 34).getRGB(), 210));
        Scissor.push();
        Scissor.setFromComponentCoordinates(posX, posY, width, height);
        Fonts.sfMedium.drawCenteredText(ms, "KeyBinds", posX + width / 2, posY + padding + 0.5f,-1, fontSize);

        posY += fontSize + padding * 2;

        float maxWidth = Fonts.sfMedium.getWidth("KeyBinds", fontSize) + padding ;
        float localHeight = fontSize + padding * 2;

        DisplayUtils.drawRectHorizontalW(posX + 0.5f, posY, width - 1, 2.5f, 3, ColorUtils.rgba(0, 0, 0, (int) (255 * 0.25f)));
        posY += 3f;

        for (Module f : ResoluteInfo.getInstance().getFunctionRegistry().getFunctions()) {
            f.getAnimation().update();
            if (!(f.getAnimation().getValue() > 0) || f.getBind() == 0) continue;
            String nameText = f.getName();
            float nameWidth = Fonts.sfMedium.getWidth(nameText, fontSize);

            String bindText = "(" + KeyStorage.getKey(f.getBind()) + ")";
            float bindWidth = Fonts.sfMedium.getWidth(bindText, fontSize);

            float localWidth = nameWidth + bindWidth + padding * 3;

            Fonts.sfMedium.drawText(ms, nameText, posX + padding, posY + 0.5f, ColorUtils.rgba(210, 210, 210, (int) (255 * f.getAnimation().getValue())), fontSize);
            Fonts.sfMedium.drawText(ms, bindText, posX + width - padding - bindWidth, posY + 0.5f, ColorUtils.rgba(210, 210, 210, (int) (255 * f.getAnimation().getValue())), fontSize);

            if (localWidth > maxWidth) {
                maxWidth = localWidth;
            }

            posY += (float) ((fontSize + padding) * f.getAnimation().getValue());
            localHeight += (float) ((fontSize + padding) * f.getAnimation().getValue());
        }
        Scissor.unset();
        Scissor.pop();
        width = Math.max(maxWidth, 80);
        height = localHeight + 2.5f;
        dragging.setWidth(width);
        dragging.setHeight(height);
    }

    private void drawStyledRect(float x,
                                float y,
                                float width,
                                float height,
                                float radius) {

        DisplayUtils.drawRoundedRect(x - 0.5f, y - 0.5f, width + 1, height + 1, radius + 0.5f, ColorUtils.getColor(0)); // outline
        DisplayUtils.drawRoundedRect(x, y, width, height, radius, ColorUtils.rgba(21, 21, 21, 255));
    }
}
