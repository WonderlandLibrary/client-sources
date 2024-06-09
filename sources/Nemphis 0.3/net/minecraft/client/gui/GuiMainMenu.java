/*
 * Decompiled with CFR 0_118.
 */
package net.minecraft.client.gui;

import java.awt.Color;
import java.io.IOException;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiSelectWorld;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.ResourceLocation;
import pw.vertexcode.nemphis.Nemphis;
import pw.vertexcode.nemphis.ui.guis.GuiDirectLogin;
import pw.vertexcode.util.font.FontManager;
import pw.vertexcode.util.lwjgl.LWJGLUtil;

public class GuiMainMenu
extends GuiScreen {
    public boolean buttonClicked = false;
    public float alpha = 0.0f;

    @Override
    public void initGui() {
        this.addButtons();
        super.initGui();
    }

    public void addButtons() {
        ScaledResolution sr = new ScaledResolution(this.mc);
        int y = sr.getScaledHeight() / 2;
        int x = sr.getScaledWidth() / 2 - 104;
        this.buttonList.add(new GuiButton(0, x, y - 21, "Singleplayer"));
        this.buttonList.add(new GuiButton(1, x, y, "Mutliplayer"));
        this.buttonList.add(new GuiButton(2, x, y + 21, "Direct Login"));
        this.buttonList.add(new GuiButton(3, x, y + 42, "Nemphis"));
        this.buttonList.add(new GuiButton(4, x, y + 63, "Options"));
        this.buttonList.add(new GuiButton(5, x, y + 84, "Quit Game"));
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        if (button.id == 0) {
            this.mc.displayGuiScreen(new GuiSelectWorld(this));
        }
        if (button.id == 1) {
            this.mc.displayGuiScreen(new GuiMultiplayer(this));
        }
        if (button.id == 2) {
            this.mc.displayGuiScreen(new GuiDirectLogin());
        }
        if (button.id == 4) {
            this.mc.displayGuiScreen(new GuiOptions(this, this.mc.gameSettings));
        }
        if (button.id == 5) {
            this.mc.shutdown();
        }
        if (button.id <= 5) {
            this.buttonClicked = true;
        }
        super.actionPerformed(button);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        LWJGLUtil.drawFullscreenImage(Nemphis.instance.background);
        LWJGLUtil.drawRect(0.0f, 0.0f, 1920.0f, 1080.0f, Integer.MIN_VALUE);
        ScaledResolution sr = new ScaledResolution(this.mc);
        int x = sr.getScaledWidth() / 2;
        int y = sr.getScaledHeight() / 2;
        FontRenderer fr = this.mc.fontRendererObj;
        int x2 = sr.getScaledWidth();
        int y2 = sr.getScaledHeight();
        fr.drawString("Nemphis \u00a74v" + Nemphis.instance.clientVersion, 2, y2 - fr.FONT_HEIGHT, -1);
        fr.drawString("Client made by \u00a74Razex", x2 - fr.getStringWidth("Client made by Razex"), y2 - fr.FONT_HEIGHT, -1);
        FontManager.getFont("jslight", 60).drawString("Nemphis", x - FontManager.getFont("jslight", 60).getStringWidth("Nemphis") / 2 - 6, y - 100, -1);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    public void fade() {
        Color c = new Color(0.0f, 0.0f, 0.0f, this.alpha / 255.0f);
        LWJGLUtil.drawRect(0.0f, 0.0f, 1000000.0f, 1000000.0f, c.getRGB());
        if (this.alpha != 255.0f) {
            this.alpha += 5.0f;
        }
        if (this.alpha >= 255.0f) {
            this.alpha = 255.0f;
        }
    }
}

