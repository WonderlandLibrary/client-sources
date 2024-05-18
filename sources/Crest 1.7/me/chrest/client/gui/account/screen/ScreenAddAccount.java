// 
// Decompiled by Procyon v0.5.30
// 

package me.chrest.client.gui.account.screen;

import me.chrest.client.gui.account.AccountScreen;
import me.chrest.client.Client;
import me.chrest.client.account.AccountManager;
import me.chrest.client.account.Alt;
import me.chrest.client.gui.account.component.Button;
import me.chrest.utils.RenderingUtils;
import me.chrest.utils.minecraft.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.Minecraft;
import me.chrest.utils.ClientUtils;
import me.chrest.utils.minecraft.GuiTextField;

public class ScreenAddAccount extends Screen
{
    private AddButton addButton;
    private GuiTextField emailText;
    private GuiTextField passText;
    private GuiTextField nameText;
    
    public ScreenAddAccount() {
        this.addButton = new AddButton();
        final int p_i45542_1_ = -5;
        final FontRenderer clientFont = ClientUtils.clientFont();
        final int p_i45542_3_ = Minecraft.getMinecraft().currentScreen.width / 2 - 60;
        final GuiScreen currentScreen = Minecraft.getMinecraft().currentScreen;
        this.emailText = new GuiTextField(p_i45542_1_, clientFont, p_i45542_3_, GuiScreen.height / 2 - 13 - 80, 120, 26);
        final int p_i45542_1_2 = -4;
        final FontRenderer clientFont2 = ClientUtils.clientFont();
        final int p_i45542_3_2 = Minecraft.getMinecraft().currentScreen.width / 2 - 60;
        final GuiScreen currentScreen2 = Minecraft.getMinecraft().currentScreen;
        this.passText = new GuiTextField(p_i45542_1_2, clientFont2, p_i45542_3_2, GuiScreen.height / 2 - 13 - 40, 120, 26);
        final int p_i45542_1_3 = -3;
        final FontRenderer clientFont3 = ClientUtils.clientFont();
        final int p_i45542_3_3 = Minecraft.getMinecraft().currentScreen.width / 2 - 60;
        final GuiScreen currentScreen3 = Minecraft.getMinecraft().currentScreen;
        this.nameText = new GuiTextField(p_i45542_1_3, clientFont3, p_i45542_3_3, GuiScreen.height / 2 - 13, 120, 26);
    }
    
    @Override
    public void draw(final int mouseX, final int mouseY) {
        if (!this.emailText.isFocused() && !this.emailText.getText().equals("§7Username") && !this.emailText.getText().equals("§cCannot Be Blank") && this.emailText.getText().length() == 0) {
            this.emailText.setText("§7Username");
        }
        if (!this.passText.isFocused() && !this.passText.getText().equals("§7Password") && this.passText.getText().length() == 0) {
            this.passText.setText("§7Password");
        }
        if (!this.nameText.isFocused() && !this.nameText.getText().equals("§7Name") && this.nameText.getText().length() == 0) {
            this.nameText.setText("§7Name");
        }
        final float x = 0.0f;
        final float y = 0.0f;
        final float x2 = Minecraft.getMinecraft().currentScreen.width;
        final GuiScreen currentScreen = Minecraft.getMinecraft().currentScreen;
        RenderingUtils.drawGradientRect(x, y, x2, GuiScreen.height, -1072689136, -804253680);
        this.addButton.draw(mouseX, mouseY);
        this.emailText.drawTextBox();
        this.passText.drawTextBox();
        this.nameText.drawTextBox();
    }
    
    @Override
    public void onClick(final int x, final int y, final int mouseButton) {
        if (this.emailText.getText().equals("§7Username") || this.emailText.getText().equals("§cCannot Be Blank")) {
            this.emailText.setText("");
        }
        if (this.passText.getText().equals("§7Password")) {
            this.passText.setText("");
        }
        if (this.nameText.getText().equals("§7Name")) {
            this.nameText.setText("");
        }
        if (this.addButton.isOver()) {
            this.addButton.onClick(mouseButton);
        }
        this.emailText.mouseClicked(x, y, mouseButton);
        this.passText.mouseClicked(x, y, mouseButton);
        this.nameText.mouseClicked(x, y, mouseButton);
    }
    
    @Override
    public void onKeyPress(final char c, final int key) {
        this.emailText.textboxKeyTyped(c, key);
        this.passText.textboxKeyTyped(c, key);
        this.nameText.textboxKeyTyped(c, key);
    }
    
    @Override
    public void update() {
        this.emailText.updateCursorCounter();
        this.passText.updateCursorCounter();
        this.nameText.updateCursorCounter();
    }
    
    private class AddButton extends Button
    {
        public AddButton() {
            final String text = "Add Account";
            final int x1 = Minecraft.getMinecraft().currentScreen.width / 2 - 40;
            final int x2 = Minecraft.getMinecraft().currentScreen.width / 2 + 40;
            final GuiScreen currentScreen = Minecraft.getMinecraft().currentScreen;
            final int y1 = GuiScreen.height / 2 - 13 + 40;
            final GuiScreen currentScreen2 = Minecraft.getMinecraft().currentScreen;
            super(text, x1, x2, y1, GuiScreen.height / 2 + 13 + 40, -15921907, -16777216);
        }
        
        @Override
        public void onClick(final int button) {
            if (ScreenAddAccount.this.emailText.getText().length() == 0 || ScreenAddAccount.this.emailText.getText().equals("§7Username") || ScreenAddAccount.this.emailText.getText().equals("§cCannot Be Blank")) {
                ScreenAddAccount.this.emailText.setText("§cCannot Be Blank");
                return;
            }
            AccountManager.addAlt(0, new Alt(ScreenAddAccount.this.emailText.getText(), ScreenAddAccount.this.nameText.getText(), ScreenAddAccount.this.passText.getText()));
            Client.accountScreen.currentScreen = null;
            AccountManager.saveAccounts();
            Client.accountScreen.initGui();
            AccountScreen.info = "§aAlt Added";
        }
    }
}
