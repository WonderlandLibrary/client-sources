package fun.ellant.ui.display.impl;

import com.mojang.blaze3d.matrix.MatrixStack;
import fun.ellant.events.EventDisplay;
import fun.ellant.functions.impl.hud.HUD;
import fun.ellant.ui.display.ElementRenderer;
import fun.ellant.utils.client.PingUtil;
import fun.ellant.utils.client.ServerTPS;
import fun.ellant.utils.render.ColorUtils;
import fun.ellant.utils.render.DisplayUtils;
import fun.ellant.utils.render.font.Fonts;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;

@FieldDefaults(level = AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class WatermarkRenderer7 implements ElementRenderer {
    final Minecraft mc = Minecraft.getInstance();

    final ResourceLocation alphaIcon = new ResourceLocation("expensive/images/hud/E.png");
    final ResourceLocation wifiicon = new ResourceLocation("expensive/images/hud/Ping.png");

    @Override
    public void render(EventDisplay eventDisplay) {
        MatrixStack ms = eventDisplay.getMatrixStack();
        // Хуячим ник игрока
        PlayerEntity player = mc.player;
        String playerName = player.getName().getString();

        float posY = 4;
        float padding = 2;
        int iconSize = 12; // размер иконок
        float fontSize = 7.0f;

        // Данные
        String headerText = playerName;
        String timeText = java.time.LocalTime.now().withNano(0).toString();
        String serverText = mc.isSingleplayer() ? "Singleplayer" : PingUtil.serverIP();
        String ticksText = String.valueOf(PingUtil.calculatePing()) + " ms";
        String fpsText = mc.getDebugFPS() + " fps";

        float headerTextWidth = Fonts.sfui.getWidth(headerText, fontSize);
        float timeTextWidth = Fonts.sfui.getWidth(timeText, fontSize);
        float serverTextWidth = Fonts.sfui.getWidth(serverText, fontSize);
        float ticksTextWidth = Fonts.sfui.getWidth(ticksText, fontSize);
        float fpsTextWidth = Fonts.sfui.getWidth(fpsText, fontSize);

        float rectHeight = iconSize + padding * 2;

        float rectWidthLogo = iconSize + padding * 2;
        float rectWidthHeader = headerTextWidth + timeTextWidth + padding * 4;
        float rectWidthServerAndTicks = iconSize + serverTextWidth + ticksTextWidth + padding * 6;
        float rectWidthFps = fpsTextWidth + padding * 4;

        float posX = 5;
        float currentX = posX;

        float imageOffsetY = (rectHeight - iconSize) / 2;

        // отрисовка секции иконки
        drawStyledRect(currentX, posY, rectWidthLogo, rectHeight, 3);
        DisplayUtils.drawImage(alphaIcon, currentX + padding, posY + imageOffsetY, iconSize, iconSize, HUD.getColor(0));

        // корректировка позиция для текста и время вроде
        currentX += rectWidthLogo + padding;

        // отрисовка секции текста и время
        drawStyledRect(currentX, posY, rectWidthHeader, rectHeight, 3);
        Fonts.sfMedium.drawText(ms, headerText, currentX + padding * 2, posY + (rectHeight - fontSize) / 2, ColorUtils.rgb(255, 255, 255), fontSize);
        Fonts.sfMedium.drawText(ms, timeText, currentX + padding * 2 + headerTextWidth + padding, posY + (rectHeight - fontSize) / 2, ColorUtils.rgb(255, 255, 255), fontSize);

        float lowerPosY = posY + rectHeight + padding;

        // отрисовка секции айпи сервера и пинга
        drawStyledRect(posX, lowerPosY, rectWidthServerAndTicks, rectHeight, 3);
        Fonts.miscelasibility.drawText(ms, "B", posX + padding - 2f, lowerPosY + imageOffsetY - 1f, HUD.getColor(0), iconSize + 2);
        Fonts.sfMedium.drawText(ms, serverText, posX + iconSize + padding * 2, lowerPosY + (rectHeight - fontSize) / 2, ColorUtils.rgb(255, 255, 255), fontSize);
        float serverTextWidthWithPadding = posX + iconSize + padding * 2 + serverTextWidth;
        drawStyledSeparator(ms, serverTextWidthWithPadding + padding, lowerPosY + padding / 2, 1.0f, rectHeight - padding, 0.4f);
        Fonts.sfMedium.drawText(ms, ticksText, serverTextWidthWithPadding + padding * 3, lowerPosY + (rectHeight - fontSize) / 2, ColorUtils.rgb(255, 255, 255), fontSize);

        // отрисовка секции фпс
        float fpsX = posX + rectWidthServerAndTicks + padding;
        drawStyledRect(fpsX, lowerPosY, rectWidthFps, rectHeight, 3);
        Fonts.sfMedium.drawText(ms, fpsText, fpsX + padding * 2, lowerPosY + (rectHeight - fontSize) / 2, ColorUtils.rgb(255, 255, 255), fontSize);
    }

    private void drawStyledSeparator(MatrixStack ms, float x, float y, float width, float height, float alpha) {
        // тень полосочек пон?
        DisplayUtils.drawRoundedRect(x + 1.0f, y + 0.9f, width, height, 1.0f, ColorUtils.setAlpha(ColorUtils.rgb(0, 0, 0), 0));
        // основа полосочек пон?
        DisplayUtils.drawRoundedRect(x, y, width, height, 1.0f, ColorUtils.setAlpha(ColorUtils.rgb(255, 255, 255), (int) (alpha * 75)));
    }

    private void drawStyledRect(float x, float y, float width, float height, float radius) {
        // рисует тень
        DisplayUtils.drawRoundedRect(x + 1.0f, y + 0.9f, width, height, radius, ColorUtils.setAlpha(ColorUtils.rgb(0, 0, 0), 15));
        // рисует прямоугольники
        DisplayUtils.drawRoundedRect(x, y, width, height, radius, ColorUtils.rgba(21, 21, 21, 160));
    }
}