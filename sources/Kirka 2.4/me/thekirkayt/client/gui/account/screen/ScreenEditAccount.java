/*
 * Decompiled with CFR 0.143.
 */
package me.thekirkayt.client.gui.account.screen;

import java.util.List;
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

public class ScreenEditAccount
extends Screen {
    private EditButton editButton;
    private GuiTextField emailText;
    private GuiTextField passText;
    private GuiTextField nameText;
    private Alt alt;

    public ScreenEditAccount(Alt alt) {
        this.alt = alt;
        this.editButton = new EditButton();
        ClientUtils.mc();
        ClientUtils.mc();
        this.emailText = new GuiTextField(-10, ClientUtils.clientFont(), GuiScreen.width / 2 - 60, GuiScreen.height / 2 - 13 - 80, 120, 26);
        ClientUtils.mc();
        ClientUtils.mc();
        this.passText = new GuiTextField(-9, ClientUtils.clientFont(), GuiScreen.width / 2 - 60, GuiScreen.height / 2 - 13 - 40, 120, 26);
        ClientUtils.mc();
        ClientUtils.mc();
        this.nameText = new GuiTextField(-8, ClientUtils.clientFont(), GuiScreen.width / 2 - 60, GuiScreen.height / 2 - 13, 120, 26);
        this.emailText.setText(alt.getEmail());
        this.passText.setText(alt.getPassword());
        this.nameText.setText(alt.getUsername());
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
        RenderUtils.rectangle(0.0, 0.0, GuiScreen.width, GuiScreen.height, -1072689136);
        this.editButton.draw(mouseX, mouseY);
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
        if (this.editButton.isOver()) {
            this.editButton.onClick(mouseButton);
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

    public int getPositionInAltList(Alt alt) {
        for (int i = 0; i < AccountManager.accountList.size() - 1; ++i) {
            if (!AccountManager.accountList.get(i).equals(alt)) continue;
            return i;
        }
        return 0;
    }

    private class EditButton
    extends Button {
        public EditButton() {
            ClientUtils.mc();
            ClientUtils.mc();
            ClientUtils.mc();
            ClientUtils.mc();
            super("Edit Account", GuiScreen.width / 2 - 40, GuiScreen.width / 2 + 40, GuiScreen.height / 2 - 13 + 40, GuiScreen.height / 2 + 13 + 40, -15921907, -16777216);
        }

        @Override
        public void onClick(int button) {
            if (ScreenEditAccount.this.emailText.getText().length() == 0 || ScreenEditAccount.this.emailText.getText().equals("\u00a77Username") || ScreenEditAccount.this.emailText.getText().equals("\u00a7cCannot Be Blank")) {
                ScreenEditAccount.this.emailText.setText("\u00a7cCannot Be Blank");
                return;
            }
            AccountManager.addAlt(ScreenEditAccount.this.getPositionInAltList(ScreenEditAccount.this.alt), new Alt(ScreenEditAccount.this.emailText.getText(), ScreenEditAccount.this.nameText.getText(), ScreenEditAccount.this.passText.getText()));
            AccountManager.removeAlt(ScreenEditAccount.this.alt);
            AccountScreen.getInstance().currentScreen = null;
            AccountManager.save();
            AccountScreen.getInstance().initGui();
            AccountScreen.getInstance().info = "\u00a7aAlt Edited";
        }
    }

}

