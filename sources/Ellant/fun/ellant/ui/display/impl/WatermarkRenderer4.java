package fun.ellant.ui.display.impl;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.text.StringTextComponent;
import fun.ellant.events.EventDisplay;
import fun.ellant.ui.display.ElementRenderer;
import fun.ellant.utils.render.ColorUtils;
import fun.ellant.utils.render.DisplayUtils;
import fun.ellant.utils.render.font.Fonts;
import fun.ellant.utils.text.GradientUtil;

import java.util.Random;

public class WatermarkRenderer4 implements ElementRenderer {

    private final Minecraft mc = Minecraft.getInstance();

    @Override
    public void render(EventDisplay eventDisplay) {
        MatrixStack ms = eventDisplay.getMatrixStack();
        float posX = 4.0f;
        float posY = 4.0f;
        float padding = 5.0f;
        float fontSize = 6.5f;
        float iconSize = 10.0f;

        PlayerEntity player = mc.player;
        int fps = mc.debugFPS;
        Random rand = new Random();
        int uidRand = rand.nextInt();

        // Отрисовка текста "E | Alpha"
        StringTextComponent ellant = (StringTextComponent) GradientUtil.white("E | Alpha");
        float ellantWidth = Fonts.montserrat.getWidth(ellant, fontSize);
        float ellantBackgroundWidth = ellantWidth + padding * 2;
        float ellantBackgroundHeight = iconSize + padding * 2;

        Fonts.montserrat.drawText(ms, ellant, posX + padding, posY + padding, fontSize, ColorUtils.rgb(255, 255, 255));

        // Отрисовка заднего фона для текста "E | Alpha"
        drawStyledRect(posX, posY, ellantBackgroundWidth, ellantBackgroundHeight, 4);

        // Отрисовка текста "ФПС: [число]"
        StringTextComponent user = (StringTextComponent) GradientUtil.white("User: " + fps);
        float userWidth = Fonts.sfbold.getWidth(user, fontSize);
        float userBackgroundWidth = userWidth + padding * 2;
        float userBackgroundHeight = iconSize + padding * 2;

        float userPosX = posX + ellantBackgroundWidth + padding; // Расположение текста "ФПС: [число]"
        Fonts.sfbold.drawText(ms, user, userPosX, posY + padding, fontSize, ColorUtils.rgb(255, 255, 255));

        // Отрисовка заднего фона для текста "ФПС: [число]"
        drawStyledRect(userPosX, posY, userBackgroundWidth, userBackgroundHeight, 4);
    }

    private void drawStyledRect(float x, float y, float width, float height, float radius) {
        DisplayUtils.drawRoundedRect(x, y, width, height, radius, ColorUtils.rgba(21, 21, 21, 155));
    }
}
