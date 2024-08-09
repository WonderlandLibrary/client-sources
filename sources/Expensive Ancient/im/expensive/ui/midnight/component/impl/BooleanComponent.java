package im.expensive.ui.midnight.component.impl;

import com.mojang.blaze3d.matrix.MatrixStack;
import im.expensive.functions.settings.impl.BooleanSetting;
import im.expensive.utils.font.Fonts;
import im.expensive.utils.math.MathUtil;
import im.expensive.utils.render.ColorUtils;
import im.expensive.utils.render.DisplayUtils;
import im.expensive.utils.render.Scissor;

import java.awt.*;

import static im.expensive.utils.render.DisplayUtils.reAlphaInt;


public class BooleanComponent extends Component {

    public BooleanSetting option;

    public BooleanComponent(BooleanSetting option) {
        this.option = option;
        this.setting = option;
    }

    public float animationToggle;

    @Override
    public void drawComponent(MatrixStack matrixStack, int mouseX, int mouseY) {
        height = 15;
        float off = 0.5f;
        animationToggle = MathUtil.lerp(animationToggle, option.get() ? 1 : 0, 10);

        int color = ColorUtils.interpolateColor(ColorUtils.IntColor.rgba(26, 29, 33, 255),
                ColorUtils.IntColor.rgba(74, 166, 218, 255), animationToggle);

        DisplayUtils.drawShadow(x + 5, y + 1 + off, 10, 10, 8, reAlphaInt(color, 50));
        DisplayUtils.drawRoundedRect(x + 5, y + 1 + off, 10, 10, 2f, color);
        Scissor.push();

        Scissor.setFromComponentCoordinates(x + 5, y + 1 + off, 10 * animationToggle, 10);
        Fonts.icons[12].drawString(matrixStack, "A", x + 7, y + 6 + off, -1);
        Scissor.unset();
        Scissor.pop();

        Fonts.gilroy[14].drawString(matrixStack, option.getName(), x + 18f, y + 4.5f + off, -1);
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (MathUtil.isInRegion(mouseX, mouseY, x, y, width - 10, 15)) {

            option.set(!option.get());
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int mouseButton) {

    }

    @Override
    public void keyTyped(int keyCode, int scanCode, int modifiers) {

    }

    @Override
    public void charTyped(char codePoint, int modifiers) {

    }
}
