// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.client.triton.ui.account.screen;

import net.minecraft.client.Minecraft;
import net.minecraft.client.triton.Triton;
import net.minecraft.client.triton.management.account.AccountManager;
import net.minecraft.client.triton.management.account.Alt;
import net.minecraft.client.triton.ui.account.AccountScreen;
import net.minecraft.client.triton.ui.account.component.Button;
import net.minecraft.client.triton.utils.ClientUtils;
import net.minecraft.client.triton.utils.RenderUtils;
import net.minecraft.client.triton.utils.RenderingUtils;
import net.minecraft.client.triton.utils.minecraft.GuiTextField;

public class ScreenAddAccount
extends Screen {
    private AddButton addButton;
    private GuiTextField emailText;
    private GuiTextField passText;
    private GuiTextField nameText;

    public ScreenAddAccount() {
        this.addButton = new AddButton();
        this.emailText = new GuiTextField(-5, ClientUtils.clientFont(), Minecraft.getMinecraft().currentScreen.width / 2 - 60, Minecraft.getMinecraft().currentScreen.height / 2 - 13 - 80, 120, 26);
        this.passText = new GuiTextField(-4, ClientUtils.clientFont(), Minecraft.getMinecraft().currentScreen.width / 2 - 60, Minecraft.getMinecraft().currentScreen.height / 2 - 13 - 40, 120, 26);
        this.nameText = new GuiTextField(-3, ClientUtils.clientFont(), Minecraft.getMinecraft().currentScreen.width / 2 - 60, Minecraft.getMinecraft().currentScreen.height / 2 - 13, 120, 26);
    }

    @Override
    public void draw(int mouseX, int mouseY) {
        if (!(this.emailText.isFocused() || this.emailText.getText().equals("\u00a77Username") || this.emailText.getText().equals("\u00a7cCannot Be Blank") || this.emailText.getText().length() != 0)) {
            this.emailText.setText("\u00a77Username");
        }
        if (!this.passText.isFocused() && !this.passText.getText().equals("\u00a77Password") && this.passText.getText().length() == 0) {
            this.passText.setText("\u00a77Password");
        }
        if (!this.nameText.isFocused() && !this.nameText.getText().equals("\u00a77Name") && this.nameText.getText().length() == 0) {
            this.nameText.setText("\u00a77Name");
        }
        RenderingUtils.drawGradientRect(0.0f, 0.0f, Minecraft.getMinecraft().currentScreen.width, Minecraft.getMinecraft().currentScreen.height, -1072689136, -804253680);
        this.addButton.draw(mouseX, mouseY);
        this.emailText.drawTextBox();
        this.passText.drawTextBox();
        this.nameText.drawTextBox();
    }

    @Override
    public void onClick(int x, int y, int mouseButton) {
        if (this.emailText.getText().equals("\u00a77Username") || this.emailText.getText().equals("\u00a7cCannot Be Blank")) {
            this.emailText.setText("");
        }
        if (this.passText.getText().equals("\u00a77Password")) {
            this.passText.setText("");
        }
        if (this.nameText.getText().equals("\u00a77Name")) {
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
    public void onKeyPress(char c, int key) {
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

    private class AddButton
    extends Button {
        public AddButton() {
            super("Add Account", Minecraft.getMinecraft().currentScreen.width / 2 - 40, Minecraft.getMinecraft().currentScreen.width / 2 + 40, Minecraft.getMinecraft().currentScreen.height / 2 - 13 + 40, Minecraft.getMinecraft().currentScreen.height / 2 + 13 + 40, -15921907, -16777216);
        }

        @Override
        public void onClick(int button) {
            if (ScreenAddAccount.this.emailText.getText().length() == 0 || ScreenAddAccount.this.emailText.getText().equals("\u00a77Username") || ScreenAddAccount.this.emailText.getText().equals("\u00a7cCannot Be Blank")) {
                ScreenAddAccount.this.emailText.setText("\u00a7cCannot Be Blank");
                return;
            }
            AccountManager.addAlt(0, new Alt(ScreenAddAccount.this.emailText.getText(), ScreenAddAccount.this.nameText.getText(), ScreenAddAccount.this.passText.getText()));
            Triton.accountScreen.currentScreen = null;
            AccountManager.saveAccounts();
            Triton.accountScreen.initGui();
            Triton.accountScreen.info = "\u00a7aAlt Added";
        }
    }

}

