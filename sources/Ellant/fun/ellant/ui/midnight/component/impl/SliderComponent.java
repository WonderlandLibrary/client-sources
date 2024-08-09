package fun.ellant.ui.midnight.component.impl;

import com.mojang.blaze3d.matrix.MatrixStack;
import fun.ellant.functions.settings.impl.SliderSetting;
import fun.ellant.utils.render.DisplayUtils;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector4f;
import fun.ellant.utils.font.Fonts;
import fun.ellant.utils.math.MathUtil;

import java.awt.*;

public class SliderComponent extends Component {

    public SliderSetting option;

    public SliderComponent(SliderSetting option) {
        this.option = option;
        this.setting = option;

    }

    boolean drag;

    float anim;

    @Override
    public void drawComponent(MatrixStack matrixStack, int mouseX, int mouseY) {
        height += 2;
        float sliderWidth = ((option.get() - option.min) / (option.max - option.min)) * (width - 12);
        anim = MathUtil.lerp(anim, sliderWidth, 10);
        Fonts.gilroy[14].drawString(matrixStack, option.getName(), x + 6, y + 4, -1);
        Fonts.gilroy[14].drawString(matrixStack, String.valueOf(option.get()), x + width - Fonts.gilroy[14].getWidth(String.valueOf(option.get())) - 6, y + 4, -1);
        DisplayUtils.drawRoundedRect(x + 6, y + 13, width - 12, 3, new Vector4f(2, 2, 2, 2), new Color(26, 29, 33).getRGB());

        DisplayUtils.drawShadow(x + 6, y + 14, anim, 3, 8, new Color(74, 166, 218, 50).getRGB());

        DisplayUtils.drawRoundedRect(x + 6, y + 13, anim, 3, new Vector4f(2, 2,
                option.max == option.get() ? 2 : 0, option.max == option.get() ? 2 : 0), new Color(74, 166, 218).getRGB());

        DisplayUtils.drawCircle(x + 5 + anim, y + 14.5f, 10, new Color(17, 18, 21).getRGB());
        DisplayUtils.drawCircle(x + 5 + anim, y + 14.5f, 8, new Color(74, 166, 218).getRGB());
        DisplayUtils.drawCircle(x + 5 + anim, y + 14.5f, 6, new Color(17, 18, 21).getRGB());
        if (drag) {
            float draggingValue = (float) MathHelper.clamp(MathUtil.round((mouseX - x + 6) / (width - 12)
                    * (option.max - option.min) + option.min, option.increment), option.min, option.max);
            option.set(draggingValue);
        }
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (isHovered(mouseX, mouseY)) {
            drag = true;
        }
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
        drag = false;
    }

    @Override
    public void keyTyped(int keyCode, int scanCode, int modifiers) {

    }

    @Override
    public void charTyped(char codePoint, int modifiers) {

    }
}
