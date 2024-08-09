package dev.darkmoon.client.module.impl.render;

import com.darkmagician6.eventapi.EventTarget;
import dev.darkmoon.client.manager.theme.Themes;
import dev.darkmoon.client.module.setting.impl.BooleanSetting;
import dev.darkmoon.client.module.setting.impl.ColorSetting;
import dev.darkmoon.client.module.setting.impl.ModeSetting;
import dev.darkmoon.client.module.setting.impl.NumberSetting;
import dev.darkmoon.client.DarkMoon;
import dev.darkmoon.client.event.render.EventRender2D;
import dev.darkmoon.client.module.Category;
import dev.darkmoon.client.module.Module;
import dev.darkmoon.client.module.ModuleAnnotation;
import dev.darkmoon.client.utility.render.ColorUtility;
import dev.darkmoon.client.utility.render.RenderUtility;
import dev.darkmoon.client.utility.render.animation.Animation;
import dev.darkmoon.client.utility.render.animation.Direction;
import dev.darkmoon.client.utility.render.font.FontRenderer;
import dev.darkmoon.client.utility.render.font.Fonts;

import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

@ModuleAnnotation(name = "Arraylist", category = Category.RENDER)
public class Arraylist extends Module {
    public ModeSetting fontSetting = new ModeSetting("Font", "Default", "Default", "Ace", "Comfortaa", "Poppin", "Minecraft", "Hack");
    public BooleanSetting hideRender = new BooleanSetting("Hide-Render", false);
    public ModeSetting fontColor = new ModeSetting("Font-Color", "White", "White", "Client", "Custom");
    public ColorSetting customColor = new ColorSetting("Custom-Color", Color.WHITE.getRGB(), () -> fontColor.is("Custom"));
    public BooleanSetting backGround = new BooleanSetting("BackGround", false);
    public BooleanSetting shadow = new BooleanSetting("Shadow", true);
    public static NumberSetting colorSpeed = new NumberSetting("Color-Speed", 6, 1, 9, 1);
    public BooleanSetting lowerCase = new BooleanSetting("Lower-Case", false);

    @EventTarget
    public void onRender2D(EventRender2D event) {
        if (mc.gameSettings.showDebugInfo) return;

        float x = 3;
        float y = 30;
        int offset = 0;
        int count = 0;
        DarkMoon.getInstance().getScaleMath().pushScale();

        List<Module> sortedModules = getSortedModules();

        if (shadow.get()) {
            for (Module module : sortedModules) {
                if (hideRender.get() && module.getCategory() == Category.RENDER) continue;
                String moduleName = lowerCase.get() ? module.getName().toLowerCase() : module.getName();
                Animation moduleAnimation = module.getAnimation();
                if (!module.isEnabled() && moduleAnimation.finished(Direction.BACKWARDS)) continue;

                if (!moduleAnimation.isDone()) {
                    RenderUtility.scaleStart(x - 1 + (getFont().getStringWidth(moduleName) + 8 + (getFont().equals(Fonts.minecraft13) ? 1 : 0)) / 2f, y + offset - 1 + 6.5f, moduleAnimation.getOutput());
                }
                float alphaAnimation = moduleAnimation.getOutput();

                RenderUtility.drawGlow(x - 1, y + offset - 1, getFont().getStringWidth(moduleName) + 8 + (getFont().equals(Fonts.minecraft13) ? 1 : 0), 13, 7, ColorUtility.applyOpacity(getArrayColor(count), alphaAnimation));

                if (!moduleAnimation.isDone()) {
                    RenderUtility.scaleEnd();
                }

                offset += moduleAnimation.getOutput() * 10;
                count += 1;
            }
        }

        offset = 0;
        count = 0;
        for (Module module : sortedModules) {
            if (hideRender.get() && module.getCategory() == Category.RENDER) continue;
            String moduleName = lowerCase.get() ? module.getName().toLowerCase() : module.getName();
            float stringWidth = getFont().getStringWidth(moduleName) + 4.5f + (getFont().equals(Fonts.minecraft13) ? 1 : 0);

            Animation moduleAnimation = module.getAnimation();
            moduleAnimation.setDirection(module.isEnabled() ? Direction.FORWARDS : Direction.BACKWARDS);
            if (!module.isEnabled() && moduleAnimation.finished(Direction.BACKWARDS)) continue;

            if (!moduleAnimation.isDone()) {
                RenderUtility.scaleStart(x + 2 + stringWidth / 2f, y + offset + 5.5f, moduleAnimation.getOutput());
            }
            float alphaAnimation = moduleAnimation.getOutput();
            if (backGround.get()) {
                RenderUtility.drawCRoundedRect(x + 2, y + offset, stringWidth, 11, 0, 0, 0, 0, ColorUtility.applyOpacity(new Color(24, 22, 22), alphaAnimation).getRGB());
            }
            if (fontColor.is("White")) {
                getFont().drawString(moduleName, x + 4 + (getFont().equals(Fonts.minecraft13) ? 1 : 0), y + offset + (11f - getFont().getStringHeight(moduleName)) / 2 + 1, Color.WHITE.getRGB());
            } else if (fontColor.is("Client")) {
                getFont().drawString(moduleName, x + 4 + (getFont().equals(Fonts.minecraft13) ? 1 : 0), y + offset + (11f - getFont().getStringHeight(moduleName)) / 2 + 1, getArrayColor(count).getRGB());
            } else if (fontColor.is("Custom")) {
                getFont().drawString(moduleName, x + 4 + (getFont().equals(Fonts.minecraft13) ? 1 : 0), y + offset + (11f - getFont().getStringHeight(moduleName)) / 2 + 1, customColor.getColor().getRGB());
            }
            if (!moduleAnimation.isDone()) {
                RenderUtility.scaleEnd();
            }

            offset += moduleAnimation.getOutput() * 10;
            count += 1;
        }

        offset = 0;
        count = 0;
        for (Module module : sortedModules) {
            if (hideRender.get() && module.getCategory() == Category.RENDER) continue;
            Animation moduleAnimation = module.getAnimation();

            moduleAnimation.setDirection(module.isEnabled() ? Direction.FORWARDS : Direction.BACKWARDS);
            if (!module.isEnabled() && moduleAnimation.finished(Direction.BACKWARDS)) continue;

            float alphaAnimation = moduleAnimation.getOutput();

            RenderUtility.drawRect(x, y + offset, 2, 11, ColorUtility.applyOpacity(getArrayColor(count), alphaAnimation).getRGB());

            offset += moduleAnimation.getOutput() * 10;
            count += 1;
        }

        DarkMoon.getInstance().getScaleMath().popScale();
    }

    public List<Module> getSortedModules() {
        return DarkMoon.getInstance().getModuleManager().getModules().stream().sorted((module1, module2) -> {
            float width1 = getFont().getStringWidth(lowerCase.get() ? module1.getName().toLowerCase() : module1.getName());
            float width2 = getFont().getStringWidth(lowerCase.get() ? module2.getName().toLowerCase() : module2.getName());
            return Float.compare(width2, width1);
        }).collect(Collectors.toList());
    }

    public FontRenderer getFont() {
        FontRenderer font = Fonts.ace16;
        switch (fontSetting.get()) {
            case "Default":
                font = Fonts.nunitoBold16;
                break;
            case "Ace":
                font = Fonts.ace16;
                break;
            case "Poppin":
                font = Fonts.poppin16;
                break;
            case "Comfortaa":
                font = Fonts.comfortaa16;
                break;
            case "Minecraft":
                font = Fonts.minecraft13;
                break;
            case "Hack":
                font = Fonts.hack16;
                break;
        }
        return font;
    }

    public static Color getColor(int index) {
        Color color = DarkMoon.getInstance().getThemeManager().getCurrentStyleTheme().getColors()[0];
        Color color2 = DarkMoon.getInstance().getThemeManager().getCurrentStyleTheme().getColors()[1];
        return ColorUtility.gradient((int) (10 - colorSpeed.get()),
                index * 2, color, color2);
    }

    public static Color getArrayColor(int index) {
        Color color = DarkMoon.getInstance().getThemeManager().getCurrentStyleTheme().getColors()[0];
        Color color2 = DarkMoon.getInstance().getThemeManager().getCurrentStyleTheme().getColors()[1];
        return ColorUtility.gradient((int) (10 - colorSpeed.get()),
                index * 20, color, color2);
    }
}
