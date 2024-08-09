package dev.excellent.client.module.impl.misc.autobuy.pages;

import dev.excellent.client.module.impl.misc.AutoBuy;
import dev.excellent.client.module.impl.misc.autobuy.screen.AutoBuyScreen;
import dev.excellent.client.module.impl.misc.autobuy.screen.IAutoBuyPage;
import dev.excellent.impl.font.Font;
import dev.excellent.impl.font.Fonts;
import dev.excellent.impl.util.math.Mathf;
import dev.excellent.impl.util.render.ScrollUtil;
import dev.excellent.impl.util.render.color.ColorUtil;
import lombok.Getter;
import net.minecraft.util.math.vector.Vector4f;
import net.mojang.blaze3d.matrix.MatrixStack;

import java.awt.*;

@Getter
public class AutoBuyMainPage implements IAutoBuyPage {
    private final Vector4f vec = new Vector4f();
    private final ScrollUtil scrollUtil = new ScrollUtil();

    @Override
    public Vector4f position() {
        return vec;
    }

    @Override
    public void init() {
        excellent.getAutoBuyManager().set();
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        float x, y, width, height;
        x = position().getX();
        y = position().getY();
        width = position().getZ();
        height = position().getW();
        Font font = Fonts.INTER_MEDIUM.get(40);

        double angle = (float) Mathf.clamp(0, 8, ((Math.sin(System.currentTimeMillis() / 500D) + 1F) / 2F) * 8);
        double yPos = y + (height / 4F) - (font.getHeight() / 2F) - 4 + angle;

        int color = ColorUtil.getColor(255, 255, 255, (int) Mathf.clamp(5, 255, parent().getAlphaAnimation().getValue() * 255));

        font.drawCenter(matrixStack, "Добро пожаловать!", x + width / 2F, yPos, color);
        yPos += 30;

        color = ColorUtil.getColor(200, 200, 200, (int) Mathf.clamp(5, 255, parent().getAlphaAnimation().getValue() * 255));

        font = Fonts.INTER_REGULAR.get(16);
        yPos += font.getHeight();
        font.drawCenter(matrixStack, "Вы попали в меню функции \247cAutoBuy\247r", x + width / 2F, yPos, color);
        yPos += font.getHeight();
        font.drawCenter(matrixStack, "Пока что это тестовый модуль и в дальнейшем он будет дорабатываться, пока что давайте его настроим.", x + width / 2F, yPos, color);
        yPos += font.getHeight();
        font.drawCenter(matrixStack, "Для этого, нажмите кнопочку \247cДОБАВИТЬ\247r, и выберите необходимые предметы для скупки.", x + width / 2F, yPos, color);
        yPos += font.getHeight();
        font.drawCenter(matrixStack, "Если же вы хотите выставить каждому предмету свою цену, вы можете это сделать нажав \247cИЗМЕНИТЬ.\247r", x + width / 2F, yPos, color);
        yPos += 20;

        yPos += font.getHeight();
        font.drawCenter(matrixStack, "Как активировать \247cAutoBuy\247r", x + width / 2F, yPos, color);
        yPos += font.getHeight();
        font.drawCenter(matrixStack, "Всё просто, зайдите на \247cFunTime\247r, откройте \247c/ah\247r и нажмите на кнопку которая будет над страницей аукциона.", x + width / 2F, yPos, color);
        yPos += font.getHeight();
        font.drawCenter(matrixStack, "И всё! Вы можете легко и просто заполучать ресурсы при этом ничего не делая.", x + width / 2F, yPos, color);

        yPos += 20;
        yPos += font.getHeight();
        String text = "Приятной игры с ";
        String gradientText = "Excellent Client";
        font.drawCenter(matrixStack, text, (x + width / 2F) - (font.getWidth(gradientText) / 2F), yPos, color);
        Color[] colors = ColorUtil.genGradientForText(getTheme().getFirstColor(), getTheme().getSecondColor(), gradientText.length());
        float xOffset = 0;
        for (int i = 0; i < gradientText.length(); i++) {
            char character = gradientText.charAt(i);
            int charcolor = colors[i].hashCode();
            font.draw(matrixStack, String.valueOf(character), (x + width / 2F) + (font.getWidth(text) / 2F) - (font.getWidth(gradientText) / 2F) + xOffset, yPos, charcolor);
            xOffset += font.getWidth(String.valueOf(character));
        }

        yPos += font.getHeight();
        font.drawCenter(matrixStack, "С уважением: \247cАдминистрация клиента", x + width / 2F, yPos, color);
    }

    @Override
    public boolean mouseClicked(double mouseX, double mouseY, int button) {
        return false;
    }

    @Override
    public boolean mouseReleased(double mouseX, double mouseY, int button) {
        return false;
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        return false;
    }

    @Override
    public boolean keyReleased(int keyCode, int scanCode, int modifiers) {
        return false;
    }

    @Override
    public boolean charTyped(char codePoint, int modifiers) {
        return false;
    }

    @Override
    public void onClose() {
        excellent.getAutoBuyManager().set();
    }

    private AutoBuyScreen parent() {
        return AutoBuy.singleton.get().getAutoBuyScreen();
    }
}
