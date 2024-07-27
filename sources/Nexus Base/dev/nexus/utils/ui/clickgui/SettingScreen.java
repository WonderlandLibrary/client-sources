package dev.nexus.utils.ui.clickgui;

import dev.nexus.modules.Module;
import dev.nexus.modules.setting.BooleanSetting;
import dev.nexus.modules.setting.ModeSetting;
import dev.nexus.modules.setting.NumberSetting;
import dev.nexus.modules.setting.Setting;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SettingScreen extends GuiScreen {
    private final Module module;
    public List<Setting> settings = new ArrayList<>();

    private NumberSetting draggingSlider = null;

    public SettingScreen(Module module) {
        this.module = module;
    }

    @Override
    public void initGui() {
        settings = module.getSettings();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        drawDefaultBackground();

        int rectWidth = width * 3 / 4;
        int rectHeight = height * 3 / 4;
        int x1 = (width - rectWidth) / 2;
        int y1 = (height - rectHeight) / 2;
        int x2 = x1 + rectWidth;
        int y2 = y1 + rectHeight;

        Gui.drawRect(x1, y1, x2, y2, new Color(26, 26, 26).getRGB());

        int yOffset = y1 + 10;
        int xOffset = x1 + 10;
        for (Setting setting : settings) {
            if (setting.getDependencyBoolSetting() != null) {
                Setting dependency = setting.getDependencyBoolSetting();
                if (dependency instanceof BooleanSetting && ((BooleanSetting) dependency).getValue() != setting.getDependencyBool()) {
                    continue;
                }
            }

            if (setting.dependencyModeSetting() != null) {
                Setting dependency = setting.dependencyModeSetting();
                if (!(dependency instanceof ModeSetting && ((ModeSetting) dependency).isMode(setting.dependencyMode()))) {
                    continue;
                }
            }

            drawString(mc.fontRendererObj, setting.getName() + " - ", xOffset, yOffset, Color.WHITE.getRGB());
            xOffset += mc.fontRendererObj.getStringWidth(setting.getName() + " - ");

            if (setting instanceof BooleanSetting booleanSetting) {
                drawCheckbox(booleanSetting, xOffset, yOffset, mouseX, mouseY);
                xOffset += 20;
            } else if (setting instanceof ModeSetting modeSetting) {
                drawMode(modeSetting, xOffset, yOffset, mouseX, mouseY);
                xOffset += 110;
            } else if (setting instanceof NumberSetting numberSetting) {
                drawSlider(numberSetting, xOffset, yOffset, mouseX, mouseY);
                xOffset += 110;
            }

            xOffset = x1 + 10;
            yOffset += 20;
        }

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    private void drawCheckbox(BooleanSetting setting, int x, int y, int mouseX, int mouseY) {
        int size = 10;
        int boxColor = setting.getValue() ? Color.GREEN.getRGB() : Color.RED.getRGB();

        Gui.drawRect(x, y, x + size, y + size, boxColor);
    }

    private void drawMode(ModeSetting setting, int x, int y, int mouseX, int mouseY) {
        drawString(mc.fontRendererObj, setting.getMode(), x, y, -1);
    }

    private void drawSlider(NumberSetting setting, int x, int y, int mouseX, int mouseY) {
        int width = 100;
        int height = 10;
        double percent = (setting.getValue() - setting.getMin()) / (setting.getMax() - setting.getMin());
        int sliderPos = (int) (percent * width);

        Gui.drawRect(x, y, x + width, y + height, Color.DARK_GRAY.getRGB());
        Gui.drawRect(x, y, x + sliderPos, y + height, Color.BLUE.getRGB());
        drawString(mc.fontRendererObj, String.valueOf(setting.getValue()), x + width + 5, y, Color.WHITE.getRGB());
    }

    private boolean isMouseOver(int mouseX, int mouseY, int x, int y, int width, int height) {
        return mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);

        int yOffset = (height - (height * 3 / 4)) / 2 + 10;
        for (Setting setting : settings) {
            if (setting.getDependencyBoolSetting() != null) {
                Setting dependency = setting.getDependencyBoolSetting();
                if (dependency instanceof BooleanSetting && ((BooleanSetting) dependency).getValue() != setting.getDependencyBool()) {
                    continue;
                }
            }

            if (setting.dependencyModeSetting() != null) {
                Setting dependency = setting.dependencyModeSetting();
                if (!(dependency instanceof ModeSetting && ((ModeSetting) dependency).isMode(setting.dependencyMode()))) {
                    continue;
                }
            }
            int xOffset = (width - (width * 3 / 4)) / 2 + 10;

            xOffset += mc.fontRendererObj.getStringWidth(setting.getName() + " - ");

            if (setting instanceof BooleanSetting booleanSetting) {
                if (isMouseOver(mouseX, mouseY, xOffset, yOffset, 10, 10)) {
                    booleanSetting.setValue(!booleanSetting.getValue());
                }
            } else if (setting instanceof ModeSetting modeSetting) {
                if (isMouseOver(mouseX, mouseY, xOffset, yOffset, mc.fontRendererObj.getStringWidth(modeSetting.getMode()), 10)) {
                    modeSetting.cycle();
                }
            } else if (setting instanceof NumberSetting numberSetting) {
                int sliderWidth = 100;
                int sliderHeight = 10;
                if (isMouseOver(mouseX, mouseY, xOffset, yOffset, sliderWidth, sliderHeight)) {
                    double percent = (double) (mouseX - xOffset) / sliderWidth;
                    double value = numberSetting.getMin() + percent * (numberSetting.getMax() - numberSetting.getMin());
                    value = Math.round(value / numberSetting.getIncrement()) * numberSetting.getIncrement();
                    numberSetting.setValue(value);
                    draggingSlider = numberSetting;
                }
            }

            yOffset += 20;
        }
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        super.mouseReleased(mouseX, mouseY, state);
        draggingSlider = null;
    }

    @Override
    protected void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick) {
        super.mouseClickMove(mouseX, mouseY, clickedMouseButton, timeSinceLastClick);

        if (draggingSlider != null) {
            int xOffset = (width - (width * 3 / 4)) / 2 + 10;
            xOffset += mc.fontRendererObj.getStringWidth(draggingSlider.getName() + " - ") + 10;

            int sliderWidth = 100;
            double percent = (double) (mouseX - xOffset) / sliderWidth;
            double value = draggingSlider.getMin() + percent * (draggingSlider.getMax() - draggingSlider.getMin());
            value = Math.round(value / draggingSlider.getIncrement()) * draggingSlider.getIncrement();
            draggingSlider.setValue(value);
        }
    }

    @Override
    public void onGuiClosed() {
        mc.currentScreen = new ClickGuiScreen();
    }
}
