package me.mees.remix.ui.gui;

import net.minecraft.client.gui.*;
import me.satisfactory.base.module.*;
import org.lwjgl.input.*;

public class GuiBinding extends GuiScreen implements GuiYesNoCallback
{
    private Module mod;
    private int keyBind;
    private GuiScreen prevScreen;
    
    public GuiBinding(final Module m, final CSGOGuiScreen prev) {
        this.keyBind = 0;
        this.mod = m;
        this.keyBind = this.mod.getKeybind();
        this.prevScreen = prev;
    }
    
    @Override
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        GuiBinding.mc.fontRendererObj.drawString(this.mod.getName(), GuiBinding.width / 2 - GuiBinding.mc.fontRendererObj.getStringWidth(this.mod.getName()) / 2.0f, (float)(GuiBinding.height / 2 - 30), -16777216);
        GuiBinding.mc.fontRendererObj.drawString("Current Bind: " + Keyboard.getKeyName(this.keyBind), GuiBinding.width / 2 - GuiBinding.mc.fontRendererObj.getStringWidth("Current Bind: " + Keyboard.getKeyName(this.keyBind)) / 2.0f, (float)(GuiBinding.height / 2 - 15 - 1), -16777216);
        GuiBinding.mc.fontRendererObj.drawString("Press Space to unbind " + this.mod.getName() + "!", GuiBinding.width / 2 - GuiBinding.mc.fontRendererObj.getStringWidth("Press Space to unbind " + this.mod.getName() + "!") / 2.0f, (float)(GuiBinding.height / 2 - 1), -16777216);
        GuiBinding.mc.fontRendererObj.drawString("Press Escape to exit!", GuiBinding.width / 2 - GuiBinding.mc.fontRendererObj.getStringWidth("Press Escape to exit!") / 2.0f, (float)(GuiBinding.height / 2 + 15), -1);
    }
    
    @Override
    protected void keyTyped(final char typedChar, final int keyCode) {
        this.keyBind = keyCode;
        GuiBinding.mc.displayGuiScreen(this.prevScreen);
    }
}
