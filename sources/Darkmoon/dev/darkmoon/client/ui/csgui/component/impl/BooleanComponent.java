package dev.darkmoon.client.ui.csgui.component.impl;

import dev.darkmoon.client.manager.theme.Themes;
import dev.darkmoon.client.module.setting.impl.BooleanSetting;
import dev.darkmoon.client.ui.csgui.component.Component;
import dev.darkmoon.client.DarkMoon;
import dev.darkmoon.client.utility.render.ColorUtility;
import dev.darkmoon.client.utility.render.RenderUtility;
import dev.darkmoon.client.utility.render.animation.AnimationMath;
import dev.darkmoon.client.utility.render.font.Fonts;

import java.awt.*;

public class BooleanComponent extends Component {
    public ModuleComponent moduleComponent;
    public BooleanSetting setting;
    public float animation = 0;

    public BooleanComponent(ModuleComponent moduleComponent, BooleanSetting setting) {
        super(0, 0, 0, 14);
        this.moduleComponent = moduleComponent;
        this.setting = setting;
    }

    @Override
    public void render(int mouseX, int mouseY) {
        super.render(mouseX, mouseY);
        animation = AnimationMath.fast(animation, setting.state ? -1 : 0, 15f);

        Fonts.nunitoBold14.drawString(setting.getName(), x + 5, y + 5f, Color.WHITE.getRGB());
        RenderUtility.drawRoundedRect(x + width - 25, y + 2, 20, 10, 6, new Color(25, 25, 25).getRGB());
        Color c = ColorUtility.interpolateColorC(new Color(34, 34, 34).getRGB(), Color.WHITE.getRGB(), Math.abs(animation));
        RenderUtility.drawRoundedRect(x + width - 23.5f - animation * 10, y + 3.5f, 7, 7, 6, c.getRGB());
    }

    @Override
    public void mouseClicked(double mouseX, double mouseY, int mouseButton) {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        boolean isHovered = RenderUtility.isHovered(mouseX, mouseY, x, y, width, height);
        if (isHovered && mouseButton == 0) {
            setting.state = (!setting.get());
        }
    }

    @Override
    public boolean isVisible() {
        return setting.getVisible().get();
    }
}
