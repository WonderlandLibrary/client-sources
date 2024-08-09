package im.expensive.ui.display.impl;

import com.mojang.blaze3d.matrix.MatrixStack;
import im.expensive.ui.display.ElementRenderer;
import im.expensive.events.EventDisplay;
import im.expensive.utils.render.ColorUtils;
import im.expensive.utils.render.DisplayUtils;
import im.expensive.utils.render.font.Fonts;
import net.minecraft.client.Minecraft;
import net.minecraft.client.MainWindow;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CoordsRenderer implements ElementRenderer {

    private final Minecraft mc = Minecraft.getInstance();

    @Override
    public void render(EventDisplay eventDisplay) {
        MainWindow window = mc.getMainWindow();
        if (window == null) {
            return;
        }

        MatrixStack ms = eventDisplay.getMatrixStack();
        float offset = 3;
        float fontSize = 7;
        float fontHeight = Fonts.sfbold.getHeight(fontSize);

        float posX = offset;
        float posY = window.getScaledHeight() - offset - fontHeight - 24;

        String xyzText = "XYZ: " + (int) mc.player.getPosX() + ", " + (int) mc.player.getPosY() + ", " + (int) mc.player.getPosZ();
        float xyzWidth = Fonts.sfbold.getWidth(xyzText, fontSize) + offset * 2;
        float boxHeight = fontHeight + offset * 2;
        drawStyledRectWithShadow(posX, posY, xyzWidth, boxHeight, 0);

        Fonts.sfbold.drawTextWithOutline(ms, "XYZ: ", posX + offset, posY + offset, -1, fontSize, 0.05f);
        Fonts.sfbold.drawTextWithOutline(ms, (int) mc.player.getPosX() + ", " + (int) mc.player.getPosY() + ", " + (int) mc.player.getPosZ(), posX + offset + Fonts.sfui.getWidth("XYZ: ", fontSize), posY + offset, ColorUtils.rgb(91, 101, 137), fontSize, 0.05f);

    }

    private void drawStyledRectWithShadow(float x, float y, float width, float height, float radius) {
        DisplayUtils.drawShadow(x, y, width, height, 0, ColorUtils.rgba(0, 0, 0, 100), ColorUtils.rgba(0, 0, 0, 0));
        DisplayUtils.drawRoundedRect(x, y, width, height, radius, ColorUtils.rgba(21, 21, 21, 255));


    }
}