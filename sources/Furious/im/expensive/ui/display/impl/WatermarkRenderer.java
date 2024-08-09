package im.expensive.ui.display.impl;

import com.mojang.blaze3d.matrix.MatrixStack;
import im.expensive.Furious;
import im.expensive.events.EventDisplay;
import im.expensive.ui.display.ElementRenderer;
import im.expensive.ui.styles.Style;
import im.expensive.utils.render.ColorUtils;
import im.expensive.utils.render.DisplayUtils;
import im.expensive.utils.render.font.Fonts;
import im.expensive.utils.text.GradientUtil;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class WatermarkRenderer implements ElementRenderer {

    @Override
    public void render(EventDisplay eventDisplay) {
        MatrixStack ms = eventDisplay.getMatrixStack();
        float posX = 4;
        float posY = 4;
        float padding = 5;
        float fontSize = 6.5f;
        float iconSize = 10;
        Style style = Furious.getInstance().getStyleManager().getCurrentStyle();

        ITextComponent text = GradientUtil.white("Furious.cc | " + mc.getDebugFPS());

        float textWidth = Fonts.sfui.getWidth(text, fontSize);

        float localPosX = posX + iconSize + padding * 3;

        DisplayUtils.drawShadow(posX, posY, 2 + textWidth + 2, 10, 10, style.getFirstColor().getRGB(), style.getSecondColor().getRGB());
        drawStyledRect(posX, posY, 3 + textWidth + 3, 10, 3);

        Fonts.sfui.drawText(ms, text, posX + 3, posY + 1.5f, fontSize, 255);
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
