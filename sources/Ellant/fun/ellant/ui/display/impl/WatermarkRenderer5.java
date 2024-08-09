package fun.ellant.ui.display.impl;

import com.mojang.blaze3d.matrix.MatrixStack;
import fun.ellant.Ellant;
import fun.ellant.events.EventDisplay;
import fun.ellant.ui.display.ElementRenderer;
import fun.ellant.ui.styles.Style;
import fun.ellant.utils.render.ColorUtils;
import fun.ellant.utils.render.DisplayUtils;
import fun.ellant.utils.render.KawaseBlur;
import fun.ellant.utils.render.font.Fonts;
import fun.ellant.utils.text.GradientUtil;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class WatermarkRenderer5 implements ElementRenderer {

    @Override
    public void render(EventDisplay eventDisplay) {
        MatrixStack ms = eventDisplay.getMatrixStack();
        float posX = 3;
        float posY = 4;
        float padding = 5;
        float fontSize = 6 + 0.8f;
        float iconSize = 8;

        Style style = Ellant.getInstance().getStyleManager().getCurrentStyle();

        drawStyledRect(posX - 1, posY, iconSize + padding * 1, iconSize + padding * 1, 1);


        ITextComponent iconText = GradientUtil.white("E");
        // Adjust the coordinates to center the "E"
        Fonts.sfbold.drawText(ms, iconText, posX + 3.0f, posY + 3.0f, fontSize, ColorUtils.rgb(255, 255, 255));

        ITextComponent text = GradientUtil.white("Ellant Client");

        float textWidth = Fonts.sfui.getWidth(text, fontSize);

        float localPosX = 17;

        KawaseBlur.blur.updateBlur(1, 2);
        KawaseBlur.blur.render(() -> {
            DisplayUtils.drawRoundedRect(localPosX, posY, iconSize + 1 + textWidth, iconSize + padding * 0, 2, ColorUtils.rgba(21, 21, 21, 155));
        });
        DisplayUtils.drawRoundedRect(localPosX - 0, posY, textWidth + 5, iconSize + 5, 2, ColorUtils.rgba(30, 30, 30, 155));

        Fonts.sfui.drawText(ms, text, localPosX + 2, posY + 3 + 0.5f, fontSize, 255);
    }

    private void drawStyledRect(float posX,
                                float posY,
                                float x,
                                float y,
                                float width) {
        float textWidth = 8;
        float iconSize = 8;
        float localPosX = 2;
        float padding = 1;
        float height = 1;
        float radius = 2;

        KawaseBlur.blur.updateBlur(1, 2);
        KawaseBlur.blur.render(() -> {
            DisplayUtils.drawRoundedRect(localPosX, posY, iconSize + 1 + textWidth, iconSize + padding * 0, 2, ColorUtils.rgba(21, 21, 21, 155));
        });
        DisplayUtils.drawRoundedRect(localPosX - 0, posY, textWidth + 5, iconSize + 5, 2, ColorUtils.rgba(30, 30, 30, 155));
        DisplayUtils.drawRoundedRect(x, y, width, height, radius, ColorUtils.rgba(21, 21, 21, 255));
    }
}
