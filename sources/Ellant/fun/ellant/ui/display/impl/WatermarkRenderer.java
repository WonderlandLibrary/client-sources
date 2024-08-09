package fun.ellant.ui.display.impl;

import com.jagrosh.discordipc.entities.User;
import com.mojang.blaze3d.matrix.MatrixStack;
import fun.ellant.Ellant;
import fun.ellant.events.EventDisplay;
import fun.ellant.functions.impl.hud.HUD;
import fun.ellant.ui.display.ElementRenderer;
import fun.ellant.ui.styles.Style;
import fun.ellant.utils.math.Vector4i;
import fun.ellant.utils.render.BetterText;
import fun.ellant.utils.render.ColorUtils;
import fun.ellant.utils.render.DisplayUtils;
import fun.ellant.utils.render.font.Fonts;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.vector.Vector4f;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class WatermarkRenderer implements ElementRenderer {
    final Minecraft mc = Minecraft.getInstance();

    LocalTime currentTime = LocalTime.now();
    String formattedTime = currentTime.format(DateTimeFormatter.ofPattern("HH:mm"));
    int fps = mc.getDebugFPS();
    private static HUD hud;
    BetterText betterText = new BetterText(List.of(
            "Пенит всё!",
            "Ellant Client :3",
            "Лучший бесплатный чит!",
            formattedTime
    ), 2000);

    @Override
    public void render(EventDisplay eventDisplay) {
        MatrixStack ms = eventDisplay.getMatrixStack();
        float posX = -2.5f;
        float posY = 3;
        float padding = 5;
        float fontSize = 7f;
        Style style = Ellant.getInstance().getStyleManager().getCurrentStyle();

        float textWidth = Fonts.sfMedium.getWidth(betterText.output, fontSize);
        float textHeight = fontSize;

        float localPosX = posX + padding * 1.5f;
        float localPosY = posY + padding;

        float boxWidth = fontSize + padding * 3.1f + textWidth;
        float boxHeight = fontSize + padding * 3;

        float borderRadius = 6;
        float borderWidth = 1.1f;

        DisplayUtils.drawShadow(localPosX, localPosY, boxWidth, boxHeight, 10, style.getFirstColor().getRGB(), style.getSecondColor().getRGB());
        drawStyledRect(localPosX, localPosY, boxWidth, boxHeight, borderRadius, borderWidth);

// Рисуем текст с использованием вашего шрифта и цвета вашего стиля
        Fonts.sfMedium.drawText(ms, betterText.output, localPosX + (boxWidth - textWidth) / 2, localPosY + (boxHeight - textHeight) / 2, style.getFirstColor().getRGB(), fontSize);
    }

    private void drawStyledRect(float x, float y, float width, float height, float radius, float borderWidth) {
        Vector4i colors = new Vector4i(HUD.getColor(0, 1), HUD.getColor(90, 1), HUD.getColor(180, 1), HUD.getColor(270, 1));
        DisplayUtils.drawRoundedRect(x - borderWidth, y - borderWidth, width + borderWidth * 2, height + borderWidth * 2, new Vector4f(7,7,7,7),colors);
        DisplayUtils.drawRoundedRect(x, y, width, height, radius, ColorUtils.rgba(21, 21, 21, 255));
    }
}