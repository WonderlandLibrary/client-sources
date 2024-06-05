package koks.gui.configsnew;

import koks.Koks;
import koks.gui.configs.elements.DrawConfigs;
import koks.utilities.RenderUtils;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;

/**
 * @author avox | lmao | kroko
 * @created on 09.09.2020 : 15:09
 */
public class DrawConfigManager extends GuiScreen {

    private final RenderUtils renderUtils = new RenderUtils();
    private int x = 200, y = 50, width = 500, height = 300, dragX, dragY, iconSize;
    private boolean dragging;
    private ArrayList<DrawConfig> configs = new ArrayList<>();
    private ArrayList<DrawCurrentInfo> drawCurrentInfos = new ArrayList<>();
    private ArrayList<File> configAdded = new ArrayList<>();

    public DrawConfigManager() {

    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        updatePosition(mouseX, mouseY);

        configAdded.removeIf(file -> !file.exists());
        configs.removeIf(drawConfigs -> !drawConfigs.file.exists());
        drawCurrentInfos.removeIf(drawConfigs -> !drawConfigs.file.exists());

        for (File file : Koks.getKoks().configManager.getConfigs()) {
            if(!configAdded.contains(file)) {
                configs.add(new DrawConfig(file));
                drawCurrentInfos.add(new DrawCurrentInfo(file));
                configAdded.add(file);
            }
        }

        configs.sort(Comparator.comparing(DrawConfig::getName));
        configAdded.sort(Comparator.comparing(File::getName));

        Gui.drawRect(x, y, x + width, y + height, 0xFFFFFFFF);
        renderUtils.drawShadow(x, y, width, height);

        int expandLeftSide = 50;

        Gui.drawRect(x + 20, y + 20, x + width / 2 - 10 + expandLeftSide, y + height - 20, 0xFFFFFFFF);
        renderUtils.drawShadow(x + 20, y + 20, width / 2 + expandLeftSide - 30, height - 40);

        Gui.drawRect(x + width - 20, y + 20, x + width / 2 + 10 + expandLeftSide, y + height - 20, 0xFFFFFFFF);
        renderUtils.drawShadow(x + width / 2 + 10 + expandLeftSide, y + 20, width / 2 - expandLeftSide - 30, height - 40);

        int y = this.y + 20;
        for (DrawConfig drawConfigs : configs) {
            drawConfigs.updateValues(x + 20, y, width / 2 - 30 + expandLeftSide, 20);
            drawConfigs.drawScreen(mouseX, mouseY, partialTicks);
            y += 20;
        }

        if (Koks.getKoks().configManagerFromScreen.currentConfig != null) {
            for (DrawCurrentInfo drawCurrentInfo : drawCurrentInfos) {
                if (drawCurrentInfo.file == Koks.getKoks().configManagerFromScreen.currentConfig) {
                    drawCurrentInfo.updateValues(x + width / 2 + 10 + expandLeftSide, this.y + 20, width / 2 - expandLeftSide - 30);
                    drawCurrentInfo.drawScreen(mouseX, mouseY, partialTicks);
                }
            }
        }

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    public void updatePosition(int mouseX, int mouseY) {
        if (!dragging)
            return;

        x = dragX + mouseX;
        y = dragY + mouseY;
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        for (DrawConfig drawConfigs : configs) {
            drawConfigs.keyTyped(typedChar, keyCode);
        }

        if (Koks.getKoks().configManagerFromScreen.currentConfig != null) {
            for (DrawCurrentInfo drawCurrentInfo : drawCurrentInfos) {
                if (drawCurrentInfo.file == Koks.getKoks().configManagerFromScreen.currentConfig) {
                    drawCurrentInfo.keyTyped(typedChar, keyCode);
                }
            }
        }
        super.keyTyped(typedChar, keyCode);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        if (mouseButton == 0 && mouseX > x && mouseX < x + width && mouseY > y && mouseY < y + 20) {
            dragX = x - mouseX;
            dragY = y - mouseY;
            dragging = true;
        }

        for (DrawConfig drawConfigs : configs) {
            drawConfigs.mouseClicked(mouseX, mouseY, mouseButton);
        }

        if (Koks.getKoks().configManagerFromScreen.currentConfig != null) {
            for (DrawCurrentInfo drawCurrentInfo : drawCurrentInfos) {
                if (drawCurrentInfo.file == Koks.getKoks().configManagerFromScreen.currentConfig) {
                    drawCurrentInfo.mouseClicked(mouseX, mouseY, mouseButton);
                }
            }
        }
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        dragging = false;

        for (DrawConfig drawConfigs : configs) {
            drawConfigs.mouseReleased(mouseX, mouseY, state);
        }

        if (Koks.getKoks().configManagerFromScreen.currentConfig != null) {
            for (DrawCurrentInfo drawCurrentInfo : drawCurrentInfos) {
                if (drawCurrentInfo.file == Koks.getKoks().configManagerFromScreen.currentConfig) {
                    drawCurrentInfo.mouseReleased(mouseX, mouseY, state);
                }
            }
        }
        super.mouseReleased(mouseX, mouseY, state);
    }
}