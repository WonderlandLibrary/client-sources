/*

    Code was written by MarkGG
    Any illegal distribution of this code will
    have consequences

    Vectus Client @ 2024-2025

 */
package lol.point.returnclient.ui.cgui.dropdown.comp.impl;

import lol.point.returnclient.settings.Setting;
import lol.point.returnclient.settings.impl.*;
import lol.point.returnclient.ui.cgui.dropdown.comp.Component;
import lol.point.returnclient.util.render.shaders.ShaderUtil;
import lol.point.returnclient.util.system.MathUtil;
import org.lwjgl.input.Mouse;

import java.awt.*;

public class SettingComponent extends Component {

    private final Setting setting;

    public SettingComponent(Setting setting) {
        this.setting = setting;
    }

    public void drawScreen(int mouseX, int mouseY) {
        boolean hovered = RenderUtil.hovered(mouseX, mouseY, getX(), getY(), getWidth(), getHeight());
        RenderUtil.rectangle(getX(), getY(), getWidth(), getHeight(), hovered ? new Color(37, 37, 37).darker() : new Color(37, 37, 37));
        if (setting instanceof BooleanSetting) {
            getFont().drawString(setting.name, getX() + 2, getY() + 5, -1);
            RenderUtil.rectangle(getX() + getWidth() - 8 - 4, getY() + 4, 8, 8, false, getTheme().gradient1);
            if (((BooleanSetting) setting).value) {
                RenderUtil.rectangle(getX() + getWidth() - 8 - 2.5f, getY() + 5.5, 5, 5, getTheme().gradient1);
            }

            ShaderUtil.renderGlow(() -> {
                RenderUtil.rectangle(getX() + getWidth() - 8 - 4, getY() + 4, 8, 8, false, getTheme().gradient1);
                if (((BooleanSetting) setting).value) {
                    RenderUtil.rectangle(getX() + getWidth() - 8 - 2.5f, getY() + 5.5, 5, 5, getTheme().gradient1);
                }
            }, 2, 2, 0.86f);
        } else if (setting instanceof StringSetting) {
            String value = ((StringSetting) setting).value;
            String fullText = setting.name + " " + value.toUpperCase();
            String displayText = getFont().getWidth(fullText) > 111 ? truncateText(fullText, 111) : fullText;
            getFont().drawString(displayText, getX() + 2, getY() + 5, -1);
        } else if (setting instanceof NumberSetting) {
            boolean hovered2 = RenderUtil.hovered(mouseX, mouseY, getX(), getY(), getWidth() + 5, getHeight());
            float value = ((NumberSetting) setting).value.floatValue();
            float min = ((NumberSetting) setting).min.floatValue();
            float max = ((NumberSetting) setting).max.floatValue();
            float width = Math.min(Math.max((value - min) / (max - min), 0), 1) * getWidth();
            RenderUtil.rectangle(getX(), getY(), getWidth(), getHeight(), new Color(37, 37, 37));
            RenderUtil.rectangle(getX(), getY(), width, getHeight(), getTheme().gradient1.darker());

            ShaderUtil.renderGlow(() -> RenderUtil.rectangle(getX(), getY(), width, getHeight(), getTheme().gradient1), 2, 2, 0.86f);
            getFont().drawString(setting.name + " " + value, getX() + 2, getY() + 5, -1);

            if (hovered2 && Mouse.isButtonDown(0)) {
                double normalizedX = (mouseX - getX()) / getWidth();
                double newValue = min + normalizedX * (max - min);
                int decimalPoints = ((NumberSetting) setting).decimalPoints;
                newValue = MathUtil.round(newValue, decimalPoints);
                newValue = Math.min(Math.max(newValue, min), max);
                ((NumberSetting) setting).value = newValue;
            }
        } else if (setting instanceof ColorSetting clr) {
            getFont().drawString(setting.name, getX() + 2, getY() + 5, -1);
            RenderUtil.rectangle(getX() + getWidth() - 8 - 4, getY() + 4, 8, 8, clr.color);
            ShaderUtil.renderGlow(() -> {
                RenderUtil.rectangle(getX() + getWidth() - 8 - 4, getY() + 4, 8, 8, clr.color);
            }, 2, 2, 0.86f);
            if (clr.expanded) {
                int r = clr.color.getRed(), g = clr.color.getGreen(), b = clr.color.getBlue(), a = clr.color.getAlpha();
                for (String color : new String[]{"Red", "Green", "Blue", "Alpha"}) {
                    switch (color) {
                        case "Red" -> {
                            boolean hoverRed = RenderUtil.hovered(mouseX, mouseY, getX(), getY() + 15, getWidth() + 5, getHeight());
                            float min = 0;
                            float max = 255;
                            float width = Math.min(Math.max(((float) r - min) / (max - min), 0), 1) * getWidth();
                            RenderUtil.rectangle(getX(), getY() + 15, getWidth(), getHeight(), new Color(37, 37, 37));
                            RenderUtil.rectangle(getX(), getY() + 15, width, getHeight(), new Color(r, 0, 0));

                            ShaderUtil.renderGlow(() -> RenderUtil.rectangle(getX(), getY() + 15, width, getHeight(), new Color(r, 0, 0)), 2, 2, 0.86f);
                            getFont().drawString("Red: " + r, getX() + 2, getY() + 5 + 15, -1);

                            if (hoverRed && Mouse.isButtonDown(0)) {
                                float normalizedX = (mouseX - getX()) / getWidth();
                                int newValue = (int) (min + normalizedX * (max - min));
                                newValue = (int) MathUtil.round(newValue, 0);
                                newValue = (int) Math.min(Math.max(newValue, min), max);
                                clr.color = new Color(newValue, g, b, a);
                            }
                        }
                        case "Green" -> {
                            boolean hoverGreen = RenderUtil.hovered(mouseX, mouseY, getX(), getY() + 30, getWidth() + 5, getHeight());
                            float min = 0;
                            float max = 255;
                            float width = Math.min(Math.max(((float) g - min) / (max - min), 0), 1) * getWidth();
                            RenderUtil.rectangle(getX(), getY() + 30, getWidth(), getHeight(), new Color(37, 37, 37));
                            RenderUtil.rectangle(getX(), getY() + 30, width, getHeight(), new Color(0, g, 0));

                            ShaderUtil.renderGlow(() -> RenderUtil.rectangle(getX(), getY() + 30, width, getHeight(), new Color(0, g, 0)), 2, 2, 0.86f);

                            getFont().drawString("Green: " + g, getX() + 2, getY() + 5 + 30, -1);

                            if (hoverGreen && Mouse.isButtonDown(0)) {
                                float normalizedX = (mouseX - getX()) / getWidth();
                                int newValue = (int) (min + normalizedX * (max - min));
                                newValue = (int) MathUtil.round(newValue, 0);
                                newValue = (int) Math.min(Math.max(newValue, min), max);
                                clr.color = new Color(r, newValue, b, a);
                            }
                        }
                        case "Blue" -> {
                            boolean hoverBlue = RenderUtil.hovered(mouseX, mouseY, getX(), getY() + 45, getWidth() + 5, getHeight());
                            float min = 0;
                            float max = 255;
                            float width = Math.min(Math.max(((float) b - min) / (max - min), 0), 1) * getWidth();
                            RenderUtil.rectangle(getX(), getY() + 45, getWidth(), getHeight(), new Color(37, 37, 37));
                            RenderUtil.rectangle(getX(), getY() + 45, width, getHeight(), new Color(0, 0, b));

                            ShaderUtil.renderGlow(() -> RenderUtil.rectangle(getX(), getY() + 45, width, getHeight(), new Color(0, 0, b)), 2, 2, 0.86f);

                            getFont().drawString("Blue: " + b, getX() + 2, getY() + 5 + 45, -1);

                            if (hoverBlue && Mouse.isButtonDown(0)) {
                                float normalizedX = (mouseX - getX()) / getWidth();
                                int newValue = (int) (min + normalizedX * (max - min));
                                newValue = (int) MathUtil.round(newValue, 0);
                                newValue = (int) Math.min(Math.max(newValue, min), max);
                                clr.color = new Color(r, g, newValue, a);
                            }
                        }
                        case "Alpha" -> {
                            boolean hoverAlpha = RenderUtil.hovered(mouseX, mouseY, getX(), getY() + 60, getWidth() + 5, getHeight());
                            float min = 0;
                            float max = 255;
                            float width = Math.min(Math.max(((float) a - min) / (max - min), 0), 1) * getWidth();
                            RenderUtil.rectangle(getX(), getY() + 60, getWidth(), getHeight(), new Color(37, 37, 37));
                            RenderUtil.rectangle(getX(), getY() + 60, width, getHeight(), new Color(0, 0, 0, a));

                            ShaderUtil.renderGlow(() -> RenderUtil.rectangle(getX(), getY() + 60, width, getHeight(), new Color(0, 0, 0, a)), 2, 2, 0.86f);

                            getFont().drawString("Alpha: " + a, getX() + 2, getY() + 5 + 60, -1);

                            if (hoverAlpha && Mouse.isButtonDown(0)) {
                                float normalizedX = (mouseX - getX()) / getWidth();
                                int newValue = (int) (min + normalizedX * (max - min));
                                newValue = (int) MathUtil.round(newValue, 0);
                                newValue = (int) Math.min(Math.max(newValue, min), max);
                                clr.color = new Color(r, g, b, newValue);
                            }
                        }
                    }
                }
            }
        }
    }

    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        boolean hovered = RenderUtil.hovered(mouseX, mouseY, getX(), getY(), getWidth(), getHeight());
        if (hovered) {
            switch (mouseButton) {
                case 0:
                    if (setting instanceof BooleanSetting) {
                        ((BooleanSetting) setting).toggle();
                    } else if (setting instanceof StringSetting) {
                        ((StringSetting) setting).cycleForwards();
                    } else if (setting instanceof ColorSetting) {
                        ((ColorSetting) setting).toggle();
                    }
                    break;
                case 1:
                    if (setting instanceof StringSetting) {
                        ((StringSetting) setting).cycleBackwards();
                    }
                    break;
            }
        } else {
            SettingListener.setCurrentSetting(null, false);
        }
    }

    public void keyTyped(char typedChar, int keyCode) {
    }

    public static class SettingListener {

        public static Setting currentSetting = null;
        public static boolean listening = false;

        public static void setCurrentSetting(Setting currentSetting, boolean listening) {
            SettingListener.currentSetting = currentSetting;
            SettingListener.listening = listening;
        }

    }

    private String truncateText(String text, int maxWidth) {
        while (getFont().getWidth(text) > maxWidth && text.length() > 3) {
            text = text.substring(0, text.length() - 4) + "...";
        }
        return text;
    }

}
