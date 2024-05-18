/*
 * Decompiled with CFR 0.143.
 */
package me.thekirkayt.client.gui.account.screen;

import me.thekirkayt.client.account.AccountManager;
import me.thekirkayt.client.account.Alt;
import me.thekirkayt.client.gui.account.AccountScreen;
import me.thekirkayt.client.gui.account.component.Button;
import me.thekirkayt.client.gui.account.screen.Screen;
import me.thekirkayt.utils.ClientUtils;
import me.thekirkayt.utils.RenderUtils;
import me.thekirkayt.utils.minecraft.FontRenderer;
import me.thekirkayt.utils.minecraft.GuiTextField;
import net.minecraft.client.gui.GuiScreen;

public class ScreenAddAccount
extends Screen {
    private AddButton addButton = new AddButton();
    private GuiTextField emailText;
    private GuiTextField passText;
    private GuiTextField nameText;

    public ScreenAddAccount() {
        ClientUtils.mc();
        ClientUtils.mc();
        this.emailText = new GuiTextField(-5, ClientUtils.clientFont(), GuiScreen.width / 2 - 60, GuiScreen.height / 2 - 13 - 80, 120, 26);
        ClientUtils.mc();
        ClientUtils.mc();
        this.passText = new GuiTextField(-4, ClientUtils.clientFont(), GuiScreen.width / 2 - 60, GuiScreen.height / 2 - 13 - 40, 120, 26);
        ClientUtils.mc();
        ClientUtils.mc();
        this.nameText = new GuiTextField(-3, ClientUtils.clientFont(), GuiScreen.width / 2 - 60, GuiScreen.height / 2 - 13, 120, 26);
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
        ClientUtils.mc();
        ClientUtils.mc();
        RenderUtils.rectangle(0.0, 0.0, GuiScreen.width, GuiScreen.height, -804253680);
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
            ClientUtils.mc();
            ClientUtils.mc();
            ClientUtils.mc();
            ClientUtils.mc();
            super("Add Account", GuiScreen.width / 2 - 40, GuiScreen.width / 2 + 40, GuiScreen.height / 2 - 13 + 40, GuiScreen.height / 2 + 13 + 40, -15921907, -16777216);
        }

        @Override
        public void onClick(int button) {
            if (ScreenAddAccount.this.emailText.getText().length() == 0 || ScreenAddAccount.this.emailText.getText().equals("\u00a77Username") || ScreenAddAccount.this.emailText.getText().equals("\u00a7cCannot Be Blank")) {
                ScreenAddAccount.this.emailText.setText("\u00a7cCannot Be Blank");
                return;
            }
            AccountManager.addAlt(0, new Alt(ScreenAddAccount.this.emailText.getText(), ScreenAddAccount.this.nameText.getText(), ScreenAddAccount.this.passText.getText()));
            AccountScreen.getInstance().currentScreen = null;
            AccountManager.save();
            AccountScreen.getInstance().initGui();
            AccountScreen.getInstance().info = "\u00a7aAlt Added";
        }
    }

}

