package koks.gui.configs;

import koks.Koks;
import koks.files.Files;
import koks.gui.configs.elements.DrawConfigs;
import koks.modules.Module;
import koks.modules.impl.visuals.ClearTag;
import koks.utilities.RenderUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;

/**
 * @author avox | lmao | kroko
 * @created on 04.09.2020 : 22:22
 */
public class ConfigScreen extends GuiScreen {

    private final Minecraft mc = Minecraft.getMinecraft();
    private final FontRenderer fr = Minecraft.getMinecraft().fontRendererObj;
    private final RenderUtils renderUtils = new RenderUtils();
    private ArrayList<DrawConfigs> configs = new ArrayList<>();
    private ArrayList<File> configAdded = new ArrayList<>();
    public int x, y, width, height, configHeight, panelHeight, dragX, dragY;
    private ScaledResolution sr;
    public boolean dragging;


    public ConfigScreen() {
        ScaledResolution sr = new ScaledResolution(mc);
        this.x = sr.getScaledWidth() / 2 - 150;
        this.y = 5;
        this.width = 300;
        this.height = 400;
        this.configHeight = 20;
        this.panelHeight = 20;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {


            configAdded.removeIf(file -> !file.exists());
        configs.removeIf(drawConfigs -> !drawConfigs.file.exists());

        for (File file : Koks.getKoks().configManager.getConfigs()) {
            if(!configAdded.contains(file)) {
                configs.add(new DrawConfigs(file));
                configAdded.add(file);
            }
        }

        configs.sort(Comparator.comparing(DrawConfigs::getName));

        configAdded.sort(Comparator.comparing(File::getName));

        sr = new ScaledResolution(mc);

        if (dragging) {
            x = dragX + mouseX;
            y = dragY + mouseY;
        }

        Gui.drawGradientRect(x, y, x + width, y + panelHeight, new Color(32, 32, 32, 255).getRGB(), new Color(16, 16, 16, 255).getRGB());
        Gui.drawRect(x, y + panelHeight, x + width, y + height, new Color(22, 22, 22, 255).getRGB());
        renderUtils.drawOutlineRect(x, y, x + width, y + height, 2, Koks.getKoks().client_color);
        renderUtils.drawOutlineRect(x - 0.5F, y - 1, x + width + 1, y + height + 0.5F, 1, Color.BLACK);
        fr.drawString("Config Manager", x + width / 2 - fr.getStringWidth("Config Manager") / 2, y + panelHeight / 2 - fr.FONT_HEIGHT / 2, 0xFFFFFFFF);

        int y = this.y + panelHeight;
        for (DrawConfigs drawConfigs : configs) {
            drawConfigs.updateValues(x, y, width, configHeight);
            drawConfigs.drawScreen(mouseX, mouseY, partialTicks);
            y += configHeight + 2;
        }
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        for (DrawConfigs drawConfigs : configs) {
            drawConfigs.keyTyped(typedChar, keyCode);
        }
        super.keyTyped(typedChar, keyCode);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        if (mouseButton == 0 && mouseX > x && mouseX < x + width && mouseY > y && mouseY < y + panelHeight) {
            dragX = x - mouseX;
            dragY = y - mouseY;
            dragging = true;
        }

        for (DrawConfigs drawConfigs : configs) {
            drawConfigs.mouseClicked(mouseX, mouseY, mouseButton);
        }
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        dragging = false;

        for (DrawConfigs drawConfigs : configs) {
            drawConfigs.mouseReleased(mouseX, mouseY, state);

        }
        super.mouseReleased(mouseX, mouseY, state);
    }

    @Override
    public void initGui() {

        super.initGui();
    }

    @Override
    public void onGuiClosed() {
        super.onGuiClosed();
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

}