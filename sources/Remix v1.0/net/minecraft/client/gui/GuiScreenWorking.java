package net.minecraft.client.gui;

import net.minecraft.util.*;

public class GuiScreenWorking extends GuiScreen implements IProgressUpdate
{
    private String field_146591_a;
    private String field_146589_f;
    private int field_146590_g;
    private boolean field_146592_h;
    
    public GuiScreenWorking() {
        this.field_146591_a = "";
        this.field_146589_f = "";
    }
    
    @Override
    public void displaySavingString(final String message) {
        this.resetProgressAndMessage(message);
    }
    
    @Override
    public void resetProgressAndMessage(final String p_73721_1_) {
        this.field_146591_a = p_73721_1_;
        this.displayLoadingString("Working...");
    }
    
    @Override
    public void displayLoadingString(final String message) {
        this.field_146589_f = message;
        this.setLoadingProgress(0);
    }
    
    @Override
    public void setLoadingProgress(final int progress) {
        this.field_146590_g = progress;
    }
    
    @Override
    public void setDoneWorking() {
        this.field_146592_h = true;
    }
    
    @Override
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        if (this.field_146592_h) {
            GuiScreenWorking.mc.displayGuiScreen(null);
        }
        else {
            this.drawDefaultBackground();
            Gui.drawCenteredString(this.fontRendererObj, this.field_146591_a, GuiScreenWorking.width / 2, 70, 16777215);
            Gui.drawCenteredString(this.fontRendererObj, this.field_146589_f + " " + this.field_146590_g + "%", GuiScreenWorking.width / 2, 90, 16777215);
            super.drawScreen(mouseX, mouseY, partialTicks);
        }
    }
}
