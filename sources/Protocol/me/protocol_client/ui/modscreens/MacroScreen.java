/*
 * Decompiled with CFR 0_110.
 * 
 * Could not load the following classes:
 *  org.lwjgl.input.Keyboard
 */
package me.protocol_client.ui.modscreens;

import java.io.IOException;

import me.protocol_client.Wrapper;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;

import org.lwjgl.input.Keyboard;

public class MacroScreen
extends GuiScreen {
    protected GuiScreen prevMenu;
    protected GuiTextField xBox;
    protected GuiTextField yBox;

    @Override
    public void updateScreen() {
        this.xBox.updateCursorCounter();
    }

    @Override
    public void initGui() {
        Keyboard.enableRepeatEvents((boolean)true);
        this.buttonList.clear();
        this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 72 + 12, "Done"));
        this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height / 4 + 96 + 12, "Cancel"));
        this.xBox = new GuiTextField(1, this.fontRendererObj, this.width / 2 - 100, 60, 200, 20);
        this.yBox = new GuiTextField(1, this.fontRendererObj, this.width / 2 - 30, 85, 60, 20);
        this.xBox.setMaxStringLength(65);
        this.xBox.setFocused(true);
        this.yBox.setMaxStringLength(10);
        this.yBox.setFocused(false);
    }

    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents((boolean)false);
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        if (button.enabled) {
            if (button.id == 1) {
                this.mc.displayGuiScreen(this.prevMenu);
            } else if (button.id == 0) {
               // Macro.macro = this.xBox.getText();
                //Macro.key = Keyboard.getKeyIndex((String)this.yBox.getText().toUpperCase());
                this.mc.displayGuiScreen(this.prevMenu);
                Wrapper.tellPlayer("Warning: if you entered an invalid key, it won't work.");
            }
        }
    }

    @Override
    protected void keyTyped(char par1, int par2) {
        this.xBox.textboxKeyTyped(par1, par2);
        this.yBox.textboxKeyTyped(par1, par2);
        if (par2 == 28 || par2 == 156) {
            this.actionPerformed((GuiButton)this.buttonList.get(0));
        }
    }

    @Override
    protected void mouseClicked(int par1, int par2, int par3) throws IOException {
        super.mouseClicked(par1, par2, par3);
        this.xBox.mouseClicked(par1, par2, par3);
        this.yBox.mouseClicked(par1, par2, par3);
    }

    @Override
    public void drawScreen(int par1, int par2, float par3) {
        this.drawDefaultBackground();
        this.xBox.drawTextBox();
        this.yBox.drawTextBox();
        Gui.drawString(Wrapper.mc().fontRendererObj, "Text", this.width / 2 - 100, 51, -1);
        Gui.drawString(Wrapper.mc().fontRendererObj, "Key", this.width / 2 - 10, 110, -1);
        super.drawScreen(par1, par2, par3);
    }
}

