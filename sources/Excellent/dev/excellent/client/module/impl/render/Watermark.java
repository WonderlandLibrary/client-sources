package dev.excellent.client.module.impl.render;

import dev.excellent.Constants;
import dev.excellent.api.event.impl.render.Render2DEvent;
import dev.excellent.api.interfaces.event.Listener;
import dev.excellent.client.module.api.Category;
import dev.excellent.client.module.api.Module;
import dev.excellent.client.module.api.ModuleInfo;
import dev.excellent.impl.font.Font;
import dev.excellent.impl.font.Fonts;
import dev.excellent.impl.font.Icon;
import dev.excellent.impl.util.render.RectUtil;
import dev.excellent.impl.util.render.RenderUtil;
import dev.excellent.impl.util.render.color.ColorUtil;
import dev.excellent.impl.util.render.text.AnimatedText;
import dev.excellent.impl.value.impl.*;
import dev.excellent.impl.value.mode.SubMode;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.TextFormatting;
import org.joml.Vector2d;

import java.util.List;

@ModuleInfo(name = "Watermark", description = "Отображает водяной знак клиента.", category = Category.RENDER)
public class Watermark extends Module {

    private final DragValue position = new DragValue("Position", this, new Vector2d(200, 200));
    private final ModeValue mode = new ModeValue("Режим", this)
            .add(
                    new SubMode("Старый"),
                    new SubMode("Новый")
            ).setDefault("Новый");

    @Override
    public String getSuffix() {
        return mode.getValue().getName();
    }

    private final StringValue watermark = new StringValue("Своя надпись", this, Constants.name, () -> !mode.is("Старый"));
    private final MultiBooleanValue elements = new MultiBooleanValue("Элементы", this, () -> !mode.is("Старый"))
            .add(
                    new BooleanValue("Фпс", true),
                    new BooleanValue("Сервер", true),
                    new BooleanValue("Юзер", true)
            );
    private final Font font = Fonts.INTER_BOLD.get(16);
    private final AnimatedText text = new AnimatedText(List.of(Constants.name, watermark.getValue()), 3000);
    private final AnimatedText watermark2Text = new AnimatedText(List.of(
            "Всегда на высоте",
            "Лучший из лучших",
            "Пример для подражания",
            "Лидер среди лидеров",
            "Всегда в центре внимания",
            "Наивысший стандарт качества",
            "Лучший выбор",
            "Всегда превосходно",
            "Надежно и качественно",
            "Безупречно",
            "Fusurt пися <3"
    ), 2000);

    private final Listener<Render2DEvent> onRender2D = event -> {
        if (mc.player == null) return;

        float x = (float) position.position.x;
        float y = (float) position.position.y;
        if (mc.gameSettings.showDebugInfo) return;

        if (mode.is("Старый")) {
            text.update();
            text.setTexts(List.of(Constants.name, watermark.getValue()));

            double margin = 4;
            double height = 15;

            String watermarkText = getWatermarkText().toString();

            double width = font.getWidth(watermarkText) + (margin * 2F) + 8 + margin;

            position.size.set(width, height);

            RenderUtil.renderClientRect(event.getMatrix(), x, y, (float) width, (float) height, false, height);

            Fonts.ICON.get(12).draw(event.getMatrix(), Icon.STAR.getCharacter(), x + 3, (int) y + 2.5, -1);
            Fonts.ICON.get(10).draw(event.getMatrix(), Icon.STAR.getCharacter(), x + margin + 4, (int) y + margin + 1, -1);
            Fonts.ICON.get(8).draw(event.getMatrix(), Icon.STAR.getCharacter(), x + margin + 0.5, (int) y + margin + 4.5, -1);

            font.draw(event.getMatrix(), watermarkText, x + margin + 8 + margin, (int) y + ((height / 2F) - (font.getHeight() / 2F)), -1);
        }
        if (mode.is("Новый")) {
            watermark2Text.update();

            int bgcolor1 = getTheme().getClientColor(0);
            int bgcolor2 = getTheme().getClientColor(90);
            int bgcolor3 = getTheme().getClientColor(180);
            int bgcolor4 = getTheme().getClientColor(270);

            float pc = 0.4F;
            int white = ColorUtil.getColor(255, 255, 255, 255);

            int color1 = ColorUtil.overCol(white, getTheme().getClientColor(0), pc);
            int color2 = ColorUtil.overCol(white, getTheme().getClientColor(90), pc);
            int color3 = ColorUtil.overCol(white, getTheme().getClientColor(180), pc);
            int color4 = ColorUtil.overCol(white, getTheme().getClientColor(270), pc);

            float margin = 5;
            Font pixelFont = Fonts.SMALL_PIXEL.get(24);
            Font pixelFontSmall = Fonts.SMALL_PIXEL.get(18);
            String watermarkText = Constants.name + " (Ушли на рекод)" + " " + Minecraft.getDebugFPS() + "ФПС";

            float width = Math.max(pixelFontSmall.getWidth(watermark2Text.getOutput().toString()), pixelFont.getWidth(watermarkText)) + (margin * 1.5F), height = 22;
            position.size.set(width, height);

            float off = 2;

            RectUtil.drawRoundedRectShadowed(event.getMatrix(), x, y + off, x + width - off, y + height, getTheme().round() + 1, 1, bgcolor1, bgcolor2, bgcolor3, bgcolor4, false, true, true, true);
            RectUtil.drawRoundedRectShadowed(event.getMatrix(), x + off, y, x + width, y + height - off, getTheme().round(), 1, color1, color2, color3, color4, false, true, true, true);

            pixelFont.draw(event.getMatrix(), watermarkText, x + margin + 0.5, (int) y + 0.5, ColorUtil.multDark(getTheme().getClientColor(0, 0.5F), 0.5F));
            pixelFont.draw(event.getMatrix(), watermarkText, x + margin, (int) y, ColorUtil.getColor(0, 0, 0, 255));
            pixelFontSmall.draw(event.getMatrix(), watermark2Text.getOutput().toString(), x + margin + 0.25, (int) y + height - pixelFontSmall.getHeight() - off + 0.25, ColorUtil.multDark(getTheme().getClientColor(0, 0.5F), 0.5F));
            pixelFontSmall.draw(event.getMatrix(), watermark2Text.getOutput().toString(), x + margin, (int) y + height - pixelFontSmall.getHeight() - off, ColorUtil.getColor(20, 20, 20, 255));
        }
    };

    private StringBuilder getWatermarkText() {
        StringBuilder watermarkText = new StringBuilder();
        String wmtext = text.getOutput().toString();
        watermarkText.append(watermark.getValue().equals(Constants.name) ? Constants.name + " (Ушли на рекод, ожидайте!)" : wmtext);

        if (elements.isEnabled("Фпс") || elements.isEnabled("Сервер") || elements.isEnabled("Юзер")) {
            watermarkText.append(TextFormatting.GRAY).append(" - ").append(TextFormatting.RESET);
        }

        if (elements.isEnabled("Фпс")) {
            watermarkText.append(Minecraft.getDebugFPS()).append("fps");
            if (elements.isEnabled("Сервер") || elements.isEnabled("Юзер")) {
                watermarkText.append(TextFormatting.GRAY).append(" - ").append(TextFormatting.RESET);
            }
        }
        if (elements.isEnabled("Сервер")) {
            if (mc.getCurrentServerData() != null && mc.getCurrentServerData().serverIP != null && !mc.getCurrentServerData().serverIP.equals("45.93.200.8:25610")) {
                watermarkText.append(mc.getCurrentServerData().serverIP);
            } else {
                watermarkText.append("local");
            }
            if (elements.isEnabled("Юзер")) {
                watermarkText.append(TextFormatting.GRAY).append(" - ").append(TextFormatting.RESET);
            }
        }
        if (elements.isEnabled("Юзер")) {
            watermarkText.append(mc.player.getName().getString());
        }
        return watermarkText;
    }
}
