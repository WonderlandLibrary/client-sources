package im.expensive.ui.display.impl;

import com.mojang.blaze3d.matrix.MatrixStack;
import im.expensive.events.EventDisplay;
import im.expensive.ui.display.ElementRenderer;
import im.expensive.utils.render.font.Fonts;
import im.expensive.utils.text.GradientUtil;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.text.ITextComponent;

@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class WatermarkRenderer implements ElementRenderer {

    final Minecraft mc = Minecraft.getInstance();
    private EventDisplay eventDisplay;


    @Override
    public void render(EventDisplay eventDisplay) {
        MatrixStack ms = eventDisplay.getMatrixStack();
        float posX = -5;
        float posY = -15;
        float paddingX = 1;
        float paddingY = 1;
        float fontSize = 80f;

        PlayerEntity player = mc.player;


        if (player != null) {
            String playerName = player.getName().getString();
            int fps = mc.getDebugFPS();

            ITextComponent text = GradientUtil.gradient("X");

            float textWidth = Fonts.sfbold.getWidth(text, fontSize);

            float rectWidth = textWidth + paddingX * 20f;
            float localPosX = posX + paddingX;

            // drawStyledRect(localPosX, posY, rectWidth, fontSize + paddingY * 2, 3);

            Fonts.sfbold.drawText(ms, text, localPosX, posY + paddingY, fontSize + 5, 0xFFFFFF);
        }
        if (player != null) {
            String playerName = player.getName().getString();
            int fps = mc.getDebugFPS();

            ITextComponent text = GradientUtil.gradient(" ");

            float textWidth = Fonts.sfui.getWidth(text, fontSize);

            float rectWidth = textWidth + paddingX * 6f;
            float localPosX = posX + 76;

            // drawStyledRect(localPosX, posY, rectWidth, fontSize + paddingY * 2, 3);

            Fonts.sfbold.drawText(ms, text, localPosX, posY + 50, fontSize + 0, 0xFFFFFF);
        }
    }



    //private void drawStyledRect(float x,
    // float y,
    // float width,
    // float height,
    //   float radius) {
    //   Style style = Expensive.getInstance().getStyleManager().getCurrentStyle();

    //  DisplayUtils.drawRoundedRect(x - 0.5f, y - 0.5f, width + 1, height + 1, radius + 2.5f, ColorUtils.getColor(0)); // outline
    //  DisplayUtils.drawRoundedRect(x, y, width, height, radius + 2.5f, ColorUtils.rgba(21, 21, 21, 255));
    //    DisplayUtils.drawShadow(x, y, width, height, 80, style.getFirstColor().getRGB(), style.getSecondColor().getRGB());
    //  }
}