package dev.darkmoon.client.ui.csgui.component.impl;

import dev.darkmoon.client.manager.theme.Themes;
import dev.darkmoon.client.module.Module;
import dev.darkmoon.client.module.setting.Setting;
import dev.darkmoon.client.ui.csgui.CsGui;
import dev.darkmoon.client.ui.csgui.component.Component;
import dev.darkmoon.client.DarkMoon;
import dev.darkmoon.client.module.setting.impl.*;
import dev.darkmoon.client.utility.render.ColorUtility;
import dev.darkmoon.client.utility.render.RenderUtility;
import dev.darkmoon.client.utility.render.animation.AnimationMath;
import dev.darkmoon.client.utility.render.font.Fonts;
import lombok.Getter;
import org.lwjgl.input.Keyboard;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ModuleComponent extends dev.darkmoon.client.ui.csgui.component.Component {
    @Getter
    private final Module module;
    public boolean binding;
    public List<dev.darkmoon.client.ui.csgui.component.Component> elements = new ArrayList<>();
    public float enableAnimation = 0;

    public ModuleComponent(Module module, float width, float height) {
        super(0, 0, width, height);
        this.module = module;

        for (Setting setting : module.getSettings()) {
            if (setting instanceof BooleanSetting) {
                elements.add(new BooleanComponent(this, (BooleanSetting) setting));
            } else if (setting instanceof NumberSetting) {
                elements.add(new SliderComponent(this, (NumberSetting) setting));
            } else if (setting instanceof ModeSetting) {
                elements.add(new ModeComponent(this, (ModeSetting) setting));
            } else if (setting instanceof MultiBooleanSetting) {
                elements.add(new MultiBoolComponent(this, (MultiBooleanSetting) setting));
            } else if (setting instanceof ColorSetting) {
                elements.add(new ColorComponent(this, (ColorSetting) setting));
            }
        }
    }

    @Override
    public void render(int mouseX, int mouseY) {
        int offset = 0;
        for (dev.darkmoon.client.ui.csgui.component.Component element : elements) {
            if (!element.isVisible()) continue;
            offset += element.height;
        }

        float normalHeight = height + offset;

        Color color = DarkMoon.getInstance().getThemeManager().getCurrentStyleTheme().getColors()[0];
        Color color2 = DarkMoon.getInstance().getThemeManager().getCurrentStyleTheme().getColors()[1];
        Color moduleColor = new Color(17, 15, 15);

        RenderUtility.drawGradientGlow(x + 1, y + normalHeight - 1, (int) width - 2, 2, 4, color, color2);
        RenderUtility.drawRoundedGradientRect(x + 0.5f, y + normalHeight - 8, width - 1, 9, 5, 1, color.getRGB(), color.getRGB(), color2.getRGB(), color2.getRGB());

        RenderUtility.drawRoundedRect(x, y, width, normalHeight, 5, moduleColor.getRGB());

        Fonts.nunitoBold14.drawString(binding ? "Press a key... " : module.getName(), x + 5, y + 6, Color.WHITE.getRGB());

        // Bind
        if (binding) {
            String bindText = (module.bind < 0 ? "MOUSE " + module.getMouseBind() : Keyboard.getKeyName(module.getBind()));
            RenderUtility.drawRoundedRect(x + width - 10 - Fonts.nunitoBold14.getStringWidth(module.bind == 0 ? "R" : bindText), y + 2, module.bind == 0 ? Fonts.nunitoBold14.getStringWidth("R") + 5 : Fonts.nunitoBold14.getStringWidth(bindText) + 5, 10, 4, new Color(0, 0, 0).getRGB());
            if (module.bind != 0) {
                Fonts.nunitoBold14.drawString(bindText, x + width - 10 - Fonts.nunitoBold14.getStringWidth(bindText) + 2.4f, y + 5, Color.WHITE.getRGB());
            }
        }
        //

        RenderUtility.drawRect(x + 5, y + 5 + Fonts.nunitoBold14.getStringHeight(module.getName()) + 3, width - 10, 1, new Color(35, 29, 29).getRGB());

        Fonts.nunitoBold14.drawString(module.enabled ? "Enabled" : "Disabled", x + 5, y + 5 + Fonts.nunitoBold14.getStringHeight(module.getName()) + 9,Color.WHITE.getRGB());
        enableAnimation = AnimationMath.fast(enableAnimation, module.enabled ? -1 : 0, 15f);
        RenderUtility.drawRoundedRect(x + width - 25, y + 5 + Fonts.nunitoBold14.getStringHeight(module.getName()) + 6, 20, 10, 6, new Color(25, 25, 25).getRGB());

        Color c = ColorUtility.interpolateColorC(new Color(34, 34, 34).getRGB(), Color.WHITE.getRGB(), Math.abs(enableAnimation));
        RenderUtility.drawRoundedRect(x + width - 23.5f - enableAnimation * 10, y + 6.5f + Fonts.nunitoBold14.getStringHeight(module.getName()) + 6, 7, 7, 6, c.getRGB());

        offset = 0;

        for (dev.darkmoon.client.ui.csgui.component.Component element : elements) {
            if (!element.isVisible()) continue;
            element.x = x;
            element.y = y + 29 + offset;
            element.width = width;
            element.render(mouseX, mouseY);
            offset += element.height;
        }
    }

    @Override
    public void mouseClicked(double mouseX, double mouseY, int mouseButton) {
        super.mouseClicked(mouseX, mouseY, mouseButton);

        if (binding && mouseButton > 2) {
            module.bind = mouseButton - 100;
            binding = false;
        }

        boolean enableButtonHovered = RenderUtility.isHovered(mouseX, mouseY, x + width - 25, y + 5 + Fonts.nunitoBold14.getStringHeight(module.getName()) + 6, 20, 10);
        boolean isTitleHovered = RenderUtility.isHovered(mouseX, mouseY, x, y, width, Fonts.nunitoBold14.getStringHeight(module.getName()) + 8);

        if (enableButtonHovered && mouseButton == 0) {
            module.toggle();
        } else if (isTitleHovered && mouseButton == 2) {
            binding = !binding;
        }

        for (Component element : elements) {
            element.mouseClicked(mouseX, mouseY, mouseButton);
        }
    }

    @Override
    public void keyTyped(int keyCode) {
        super.keyTyped(keyCode);
        if (binding) {
            if (keyCode == Keyboard.KEY_ESCAPE) {
                CsGui.escapeInUse = true;
                binding = false;
                return;
            } else if (keyCode == Keyboard.KEY_DELETE) {
                module.bind = Keyboard.KEY_NONE;
            } else {
                module.bind = keyCode;
            }
            binding = false;
        }
    }
}
