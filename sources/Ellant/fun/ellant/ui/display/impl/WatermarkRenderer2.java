package fun.ellant.ui.display.impl;

import com.mojang.blaze3d.matrix.MatrixStack;
import fun.ellant.Ellant;
import fun.ellant.events.EventDisplay;
import fun.ellant.ui.display.ElementRenderer;
import fun.ellant.ui.styles.Style;
import fun.ellant.utils.render.ColorUtils;
import fun.ellant.utils.render.DisplayUtils;
import fun.ellant.utils.render.font.Fonts;
import fun.ellant.utils.text.GradientUtil;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.text.ITextComponent;

@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class WatermarkRenderer2 implements ElementRenderer {

    final Minecraft mc = Minecraft.getInstance();

    @Override
    public void render(EventDisplay eventDisplay) {
        MatrixStack ms = eventDisplay.getMatrixStack();
        float posX = 1;
        float posY = 3;
        float paddingX = 1;
        float paddingY = 3;
        float fontSize = 7.5f;

        PlayerEntity player = mc.player;


        if (player != null) {
            String playerName = player.getName().getString();
            int fps = mc.getDebugFPS();

            ITextComponent text = GradientUtil.gradient("  Ellant | " + playerName + " | FPS " + fps + " ", ColorUtils.rgba(255, 0, 0, 255), ColorUtils.rgba(0, 255, 0, 255));

            float textWidth = Fonts.sfui.getWidth(text, fontSize);

            float rectWidth = textWidth + paddingX * 6f;
            float localPosX = posX + paddingX;

            drawStyledRect(localPosX, posY, rectWidth, fontSize + paddingY * 2, 3);

            Fonts.sfbold.drawText(ms, text, localPosX, posY + paddingY, fontSize, 0xFFFFFF);
        }
    }

    private void drawStyledRect(float x,
                                float y,
                                float width,
                                float height,
                                float radius) {
        Style style = Ellant.getInstance().getStyleManager().getCurrentStyle();

        DisplayUtils.drawRoundedRect(x - 0.5f, y - 0.5f, width + 1, height + 1, radius + 2.5f, ColorUtils.getColor(0)); // outline
        DisplayUtils.drawRoundedRect(x, y, width, height, radius + 2.5f, ColorUtils.rgba(21, 21, 21, 255));
        DisplayUtils.drawShadow(x, y, width, height, 80, style.getFirstColor().getRGB(), style.getSecondColor().getRGB());
    }
}