package src.Wiksi.ui.display.impl;

import com.google.common.eventbus.Subscribe;
import com.mojang.blaze3d.matrix.MatrixStack;
import src.Wiksi.Wiksi;
import src.Wiksi.events.EventDisplay;
import src.Wiksi.ui.display.ElementRenderer;
import src.Wiksi.ui.styles.Style;
import src.Wiksi.utils.drag.Dragging;
import src.Wiksi.utils.render.ColorUtils;
import src.Wiksi.utils.render.DisplayUtils;
import src.Wiksi.utils.render.RenderUtils;
import src.Wiksi.utils.render.Scissor;
import src.Wiksi.utils.render.font.Fonts;
import src.Wiksi.utils.text.GradientUtil;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.minecraft.util.text.ITextComponent;

import java.awt.*;
import java.time.LocalTime;

@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class TimeRenderer implements ElementRenderer {
    final Dragging dragging;
    float x;
    float y;
    float width;
    float height;

    @Subscribe
    public void render (EventDisplay e){
        MatrixStack stack = e.getMatrixStack();
        float posX = dragging.getX();
        float posY = dragging.getY();
        float padding = 3;
        float fontSize = 8;

        LocalTime localTime = LocalTime.now();
        String time = String.format("%02d:%02d:%02d", localTime.getHour(), localTime.getMinute(), localTime.getSecond());
        ITextComponent ss = GradientUtil.gradient(time);
        RenderUtils.renderClientRect(stack,  posX, posY, width, height, false, 0);
        Scissor.push();
        Scissor.setFromComponentCoordinates(posX, posY, width, height);
        Fonts.consolas.drawCenteredText(stack, ss, posX + 26.5f, posY + 3, 10f);

        posY += fontSize + padding * 2;

        float maxWidth = Fonts.consolas.getWidth(time, 4) + padding * 2;
        float localHeight = fontSize + padding * 2;
        posY += 3f;
        Scissor.unset();
        Scissor.pop();
        width = Math.max(maxWidth, 54);
        height = localHeight + 2.5f;
        dragging.setWidth(width);
        dragging.setHeight(height);
    }


    private void drawStyledRect(float x,
                                float y,
                                float width,
                                float height,
                                float radius) {
        DisplayUtils.drawRoundedRect(x - 0.5f, y - 0.5f, width + 1, height + 1, radius + 0.5f, ColorUtils.setAlpha(ColorUtils.rgb(21,24,40), 255));
        DisplayUtils.drawRoundedRect(x, y, width, height, radius, ColorUtils.rgba(21, 24, 40, 90));
    }
}