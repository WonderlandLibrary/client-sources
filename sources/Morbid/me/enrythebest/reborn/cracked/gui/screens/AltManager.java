package me.enrythebest.reborn.cracked.gui.screens;

import net.minecraft.src.*;
import org.lwjgl.opengl.*;

public class AltManager extends GuiScreen
{
    private GuiScreen parentScreen;
    
    public AltManager(final GuiScreen guiscreen) {
        this.parentScreen = guiscreen;
    }
    
    @Override
    public void initGui() {
        this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height - 24, "Back"));
    }
    
    public void actionPerformed(final GuiButton g) {
        if (g.id == 1) {
            this.mc.displayGuiScreen(this.parentScreen);
        }
    }
    
    @Override
    public void drawScreen(final int par1, final int par2, final float par3) {
        this.drawDefaultBackground();
        Gui.drawRect(2, 34, this.width - 2, this.height - 36, -1895825408);
        GL11.glPushMatrix();
        GL11.glScaled(4.0, 4.0, 4.0);
        this.drawCenteredString(this.fontRenderer, "§fCracked By:", 119, 30, 16777215);
        this.drawCenteredString(this.fontRenderer, "§f§nWolve189", 119, 60, 16777215);
        GL11.glPopMatrix();
        super.drawScreen(par1, par2, par3);
    }
}
