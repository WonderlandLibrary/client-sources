/*
 * Decompiled with CFR 0_118.
 * 
 * Could not load the following classes:
 *  org.lwjgl.input.Keyboard
 */
package net.minecraft.client.gui;

import java.io.IOException;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.resources.I18n;
import net.minecraft.world.storage.ISaveFormat;
import net.minecraft.world.storage.WorldInfo;
import org.lwjgl.input.Keyboard;

public class GuiRenameWorld
extends GuiScreen {
    private GuiScreen field_146585_a;
    private GuiTextField field_146583_f;
    private final String field_146584_g;
    private static final String __OBFID = "CL_00000709";

    public GuiRenameWorld(GuiScreen p_i46317_1_, String p_i46317_2_) {
        this.field_146585_a = p_i46317_1_;
        this.field_146584_g = p_i46317_2_;
    }

    @Override
    public void updateScreen() {
        this.field_146583_f.updateCursorCounter();
    }

    @Override
    public void initGui() {
        Keyboard.enableRepeatEvents((boolean)true);
        this.buttonList.clear();
        this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 96 + 12, I18n.format("selectWorld.renameButton", new Object[0])));
        this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height / 4 + 120 + 12, I18n.format("gui.cancel", new Object[0])));
        ISaveFormat var1 = this.mc.getSaveLoader();
        WorldInfo var2 = var1.getWorldInfo(this.field_146584_g);
        String var3 = var2.getWorldName();
        this.field_146583_f = new GuiTextField(2, this.fontRendererObj, this.width / 2 - 100, 60, 200, 20);
        this.field_146583_f.setFocused(true);
        this.field_146583_f.setText(var3);
    }

    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents((boolean)false);
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        if (button.enabled) {
            if (button.id == 1) {
                this.mc.displayGuiScreen(this.field_146585_a);
            } else if (button.id == 0) {
                ISaveFormat var2 = this.mc.getSaveLoader();
                var2.renameWorld(this.field_146584_g, this.field_146583_f.getText().trim());
                this.mc.displayGuiScreen(this.field_146585_a);
            }
        }
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        this.field_146583_f.textboxKeyTyped(typedChar, keyCode);
        boolean bl = ((GuiButton)this.buttonList.get((int)0)).enabled = this.field_146583_f.getText().trim().length() > 0;
        if (keyCode == 28 || keyCode == 156) {
            this.actionPerformed((GuiButton)this.buttonList.get(0));
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        this.field_146583_f.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRendererObj, I18n.format("selectWorld.renameTitle", new Object[0]), this.width / 2, 20, 16777215);
        GuiRenameWorld.drawString(this.fontRendererObj, I18n.format("selectWorld.enterName", new Object[0]), this.width / 2 - 100, 47, 10526880);
        this.field_146583_f.drawTextBox();
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
}

