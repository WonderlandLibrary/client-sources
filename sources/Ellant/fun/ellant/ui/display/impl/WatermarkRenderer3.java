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
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;

import java.text.SimpleDateFormat;
import java.util.Date;

@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class WatermarkRenderer3 implements ElementRenderer {

    final Minecraft mc = Minecraft.getInstance();
    final ResourceLocation logo = new ResourceLocation("expensive/images/hud/logo.png");
    final ResourceLocation fpsIcon = new ResourceLocation("expensive/images/hud/fps.png");
    final ResourceLocation timeIcon = new ResourceLocation("expensive/images/hud/time.png");

    @Override
    public void render(EventDisplay eventDisplay) {
        MatrixStack ms = eventDisplay.getMatrixStack();
        float posX = 4;
        float posY = 4;
        float padding = 5;
        float fontSize = 6.5f;
        float iconSize = 10;
        float posX1 = 65;
        float posY1 = 4;

        Style style = Ellant.getInstance().getStyleManager().getCurrentStyle();

        drawStyledRect(posX, posY, iconSize + padding * 2, iconSize + padding * 2, 4);
        DisplayUtils.drawImage(logo, posX + padding, posY + padding, iconSize, iconSize, ColorUtils.rgb(255, 255, 255));

        int fps = mc.getDebugFPS();
        ITextComponent fpsText = GradientUtil.gradient(String.valueOf(fps), ColorUtils.rgba(255, 0, 0, 255), ColorUtils.rgba(0, 255, 0, 255));
        float fpsTextWidth = Fonts.sfui.getWidth(fpsText, fontSize);
        float fpsPosX = posX + iconSize + padding * 3;
        if (fps >= 100 && fps <= 999) {
            posX1 += 5;
        }

        drawStyledRect(fpsPosX, posY, iconSize + padding * 2.5f + fpsTextWidth, iconSize + padding * 2, 4);
        DisplayUtils.drawImage(fpsIcon, fpsPosX + padding, posY + padding, iconSize, iconSize, ColorUtils.rgb(255, 255, 255));
        Fonts.sfui.drawText(ms, fpsText, fpsPosX + iconSize + padding * 1.5f, posY + iconSize / 2 + 1.5f, fontSize, 255);

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String timeString = sdf.format(new Date());

        ITextComponent timeText = GradientUtil.gradient(timeString, ColorUtils.rgba(255, 0, 0, 255), ColorUtils.rgba(0, 255, 0, 255));
        float timeTextWidth = Fonts.sfui.getWidth(timeText, fontSize);
        float timePosX = posX1;

        drawStyledRect(timePosX, posY1, iconSize + padding * 2.5f + timeTextWidth, iconSize + padding * 2, 4);
        DisplayUtils.drawImage(timeIcon, timePosX + padding, posY1 + padding, iconSize, iconSize, ColorUtils.rgb(255, 255, 255));
        Fonts.sfui.drawText(ms, timeText, timePosX + iconSize + padding * 1.5f, posY1 + iconSize / 2 + 1.5f, fontSize, 255);
    }

    private void drawStyledRect(float x, float y, float width, float height, float radius) {
        DisplayUtils.drawRoundedRect(x - 0.5f, y - 0.5f, width + 1, height + 1, radius + 0.5f, ColorUtils.getColor(0)); // outline
        DisplayUtils.drawRoundedRect(x, y, width, height, radius, ColorUtils.rgba(21, 21, 21, 255));
    }
}