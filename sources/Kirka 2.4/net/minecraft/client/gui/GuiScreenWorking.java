/*
 * Decompiled with CFR 0.143.
 */
package net.minecraft.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.IProgressUpdate;

public class GuiScreenWorking
extends GuiScreen
implements IProgressUpdate {
    private String field_146591_a = "";
    private String field_146589_f = "";
    private int field_146590_g;
    private boolean field_146592_h;
    private static final String __OBFID = "CL_00000707";

    @Override
    public void displaySavingString(String message) {
        this.resetProgressAndMessage(message);
    }

    @Override
    public void resetProgressAndMessage(String p_73721_1_) {
        this.field_146591_a = p_73721_1_;
        this.displayLoadingString("Working...");
    }

    @Override
    public void displayLoadingString(String message) {
        this.field_146589_f = message;
        this.setLoadingProgress(0);
    }

    @Override
    public void setLoadingProgress(int progress) {
        this.field_146590_g = progress;
    }

    @Override
    public void setDoneWorking() {
        this.field_146592_h = true;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        if (this.field_146592_h) {
            this.mc.displayGuiScreen(null);
        } else {
            this.drawDefaultBackground();
            this.drawCenteredString(this.fontRendererObj, this.field_146591_a, width / 2, 70, 16777215);
            this.drawCenteredString(this.fontRendererObj, String.valueOf(this.field_146589_f) + " " + this.field_146590_g + "%", width / 2, 90, 16777215);
            super.drawScreen(mouseX, mouseY, partialTicks);
        }
    }
}

