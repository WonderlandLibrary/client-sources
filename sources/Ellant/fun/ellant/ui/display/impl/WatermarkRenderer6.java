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
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

import java.text.SimpleDateFormat;
import java.util.Date;

@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class WatermarkRenderer6 implements ElementRenderer {

    final Minecraft mc = Minecraft.getInstance();
    final ResourceLocation logo = new ResourceLocation("expensive/images/hud/rhomb.png");
    final ResourceLocation fpsIcon = new ResourceLocation("expensive/images/hud/fps1.png");
    final ResourceLocation timeIcon = new ResourceLocation("expensive/images/hud/time1.png");

    @Override
    public void render(EventDisplay eventDisplay) {
        MatrixStack ms = eventDisplay.getMatrixStack();
        float posX = 5;
        float posY = 4;
        float padding = 5;
        float fontSize = 6.5f;
        float iconSize = 10;
        float posX1 = 65;
        float posY1 = 4;
        PlayerEntity player = mc.player;
        String playerName = player.getName().getString();
        final ITextComponent text = GradientUtil.gradient( playerName, ColorUtils.rgba(255, 0, 0, 255), ColorUtils.rgba(0, 255, 0, 255));
        Style style = Ellant.getInstance().getStyleManager().getCurrentStyle();

        drawStyledRect(posX, posY, iconSize + padding * 9, iconSize + padding * 2, 4);
        DisplayUtils.drawImage(logo, posX + padding, posY + padding, iconSize, iconSize, ColorUtils.rgb(255, 255, 255));
        DisplayUtils.drawRectVerticalW(posX + 18.0f, posY + 3.0f,  1, 14.0f, 3, ColorUtils.rgba(0, 0, 0, (int) (255 * 0.75f)));
        Fonts.sfui.drawCenteredText(ms, text, posX + 35.0f, posY + 7.0f, fontSize);

        int fps = mc.getDebugFPS();
        ITextComponent fpsText = GradientUtil.gradient(String.valueOf(fps), ColorUtils.rgba(255, 0, 0, 255), ColorUtils.rgba(0, 255, 0, 255));
        float fpsTextWidth = Fonts.sfui.getWidth(fpsText, fontSize);
        float fpsPosX = posX + iconSize + padding * 3;
        if (fps >= 100 && fps <= 999) {
            posX1 += 5;
        }

        drawStyledRect(fpsPosX * 2.2f, posY, iconSize + padding * 3.0f + fpsTextWidth, iconSize + padding * 2, 4);
        DisplayUtils.drawImage(fpsIcon, fpsPosX + 40, posY + padding, iconSize, iconSize, ColorUtils.rgb(255, 255, 255));
        Fonts.sfui.drawText(ms, fpsText, fpsPosX * 2.25f + iconSize + padding * 1.5f, posY + iconSize / 2 + 1.5f, fontSize, 255);
        DisplayUtils.drawRectVerticalW(fpsPosX * 2.32f + iconSize + padding * 1.5f -3, posY + iconSize - 23.5f / 2 + 4.5f,  1, 14.0f, 3, ColorUtils.rgba(0, 0, 0, (int) (255 * 0.75f)));

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String timeString = sdf.format(new Date());

        ITextComponent timeText = GradientUtil.gradient(timeString, ColorUtils.rgba(255, 0, 0, 255), ColorUtils.rgba(0, 255, 0, 255));
        float timeTextWidth = Fonts.sfui.getWidth(timeText, fontSize);
        float timePosX = posX1;

        drawStyledRect(timePosX * 1.6f, posY1, iconSize + padding * 2.9f + timeTextWidth, iconSize + padding * 2, 4);
        DisplayUtils.drawImage(timeIcon, timePosX + 46 , posY1 + padding, iconSize, iconSize, ColorUtils.rgb(255, 255, 255));
        Fonts.sfui.drawText(ms, timeText, timePosX * 1.65f + iconSize + padding * 1.5f, posY1 + iconSize / 2 + 1.5f, fontSize, 255);
        DisplayUtils.drawRectVerticalW(timePosX * 1.6f + iconSize + padding * 1.5f, posY + iconSize - 23.5f / 2 + 4.5f,  1, 14.0f, 3, ColorUtils.rgba(0, 0, 0, (int) (255 * 0.75f)));
    }

    private void drawStyledRect(float x, float y, float width, float height, float radius) {
        // DisplayUtils.drawRoundedRect(x - 0.5f, y - 0.5f, width + 1, height + 1, radius + 0.5f, ColorUtils.getColor(0)); // outline
        DisplayUtils.drawRoundedRect(x, y, width, height, radius, ColorUtils.rgba(255, 255, 255, 255));
    }
}