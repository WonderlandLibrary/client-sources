package dev.darkmoon.client.ui.csgui.component.impl;

import dev.darkmoon.client.manager.theme.Themes;
import dev.darkmoon.client.module.setting.impl.NumberSetting;
import dev.darkmoon.client.ui.csgui.component.Component;
import dev.darkmoon.client.DarkMoon;
import dev.darkmoon.client.utility.math.MathUtility;
import dev.darkmoon.client.utility.render.RenderUtility;
import dev.darkmoon.client.utility.render.animation.AnimationMath;
import dev.darkmoon.client.utility.render.font.Fonts;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.input.Mouse;

import java.awt.*;

public class SliderComponent extends Component {
    public ModuleComponent moduleComponent;
    public NumberSetting setting;
    public float animation = 0;

    public boolean isDragging;

    public SliderComponent(ModuleComponent moduleComponent, NumberSetting setting) {
        super(0, 0, 0, 19);
        this.moduleComponent = moduleComponent;
        this.setting = setting;
    }

    @Override
    public void render(int mouseX, int mouseY) {
        super.render(mouseX, mouseY);

        int color = DarkMoon.getInstance().getThemeManager().getCurrentStyleTheme().getColors()[0].getRGB();
        int color2 = DarkMoon.getInstance().getThemeManager().getCurrentStyleTheme().getColors()[1].getRGB();
        Fonts.nunitoBold14.drawString(setting.getName(), x + 5, y + 3, Color.WHITE.getRGB());

        RenderUtility.drawRect(x + 5, y + 12, width - 10, 3.5f, new Color(25, 25, 25).getRGB());

        float sliderWidth = (float) ((setting.get() - setting.getMinValue())
                / (setting.getMaxValue() - setting.getMinValue()) * (width - 10));

        animation = AnimationMath.fast(animation, sliderWidth, 15f);
        RenderUtility.drawGradientRect(x + 5, y + 12, animation, 3.5f, color, color2, color2, color);

        RenderUtility.drawGlow(x + animation + 3.5f, y + 10.5f, 2, 7, 3, Color.BLACK);
        RenderUtility.drawRect(x + animation + 3, y + 10, 2.5f, 7.5f, -1);

        if (isDragging) {
            if (!Mouse.isButtonDown(0)) {
                this.isDragging = false;
            }
            float sliderValue = (float) MathHelper.clamp(MathUtility.round((float) ((mouseX - x - 5) / (width - 10) * (setting.getMaxValue() - setting.getMinValue()) + setting.getMinValue()), setting.getIncrement()), setting.getMinValue(), setting.getMaxValue());
            setting.set(sliderValue);
        }

        Fonts.nunitoBold14.drawString(String.valueOf(setting.get()), x + width - 7 - Fonts.nunitoBold12.getStringWidth(String.valueOf(setting.get())), y + 3, Color.WHITE.getRGB());
    }

    @Override
    public void mouseClicked(double mouseX, double mouseY, int mouseButton) {
        super.mouseClicked(mouseX, mouseY, mouseButton);

        boolean isHovered = RenderUtility.isHovered(mouseX, mouseY, x, y, width, height);
        if (isHovered && mouseButton == 0) {
            isDragging = true;
        }
    }

    @Override
    public boolean isVisible() {
        return setting.getVisible().get();
    }
}
