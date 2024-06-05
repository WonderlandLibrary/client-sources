package xyz.vergoclient.ui.guis.altManager.pages;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import xyz.vergoclient.files.impl.FileAlts;
import xyz.vergoclient.ui.guis.altManager.addOns.Account;
import xyz.vergoclient.util.main.SessionChanger;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.io.IOException;

public final class DirectLogin extends GuiScreen {

    private final GuiScreen parent;

    private GuiTextField userField;
    private GuiTextField passwordField;

    public static FileAlts altsFile1 = new FileAlts();

    public DirectLogin(GuiScreen parent) {
        this.parent = parent;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        // Background
        //ColorUtils.glDrawFilledQuad(0, 0, this.width, this.height, 0xFF080808, 0xFF0F0F0F);

        this.drawDefaultBackground();

        // Title
        this.mc.fontRendererObj.drawStringWithShadow("Add Account", (float) (this.width / 2.0 - this.mc.fontRendererObj.getStringWidth("Add Account") / 2.0),
                (float) (this.height / 5.0 - 9 / 2.0), 0xFFFFFFFF);

        this.userField.drawTextBox();
        this.passwordField.drawTextBox();

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public void initGui() {
        final int yBuffer = 2;
        final int xBuffer = 2;
        final int fullWidth = this.width / 4;
        final int width = fullWidth - xBuffer * 2;
        final int left = this.width / 2 - fullWidth / 2;
        final int top = this.height / 3;
        final int height = 20 + yBuffer * 2;

        this.userField = new GuiTextField(0, this.mc.fontRendererObj, left, top, width, 20);
        this.passwordField = new GuiTextField(1, this.mc.fontRendererObj, left, top + height, width, 20);

        final int buttonWidth = this.width / 6 - 2 * 2;
        final int buttonLeft = left + (fullWidth - buttonWidth) / 2;

        //this.buttonList.add(new GuiButton(2, buttonLeft, top + xBuffer * 2 + height * 2, buttonWidth, 20, "Add"));
        this.buttonList.add(new GuiButton(0, buttonLeft, top + xBuffer * 2 + height * 3, buttonWidth, 20, "Paste Clipboard"));
        this.buttonList.add(new GuiButton(1, buttonLeft, top + xBuffer * 2 + height * 4, buttonWidth, 20, "Direct Login"));
        this.buttonList.add(new GuiButton(3, buttonLeft, top + xBuffer * 2 + height * 5, buttonWidth, 20, "Done"));
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        Account account = null;

        switch (button.id) {
            case 0:
                final String clipboardContents = getClipboard();
                if (clipboardContents == null || clipboardContents.length() < 3) return;
                final String[] split = clipboardContents.split(":", 2);
                if (split.length < 2) return;
                this.userField.setText(split[0]);
                this.passwordField.setText(split[1]);
                break;
            case 1:
                SessionChanger.login(this.userField.getText(), this.passwordField.getText(), true);
            case 2:
            case 3:
                this.mc.displayGuiScreen(this.parent);
                break;
        }
    }

    private Account createAccountFromFields() {
        final String email = this.userField.getText();
        final String password = this.passwordField.getText();

        if (email.length() == 0) return null;

        return Account.builder()
                .email(email)
                .password(password)
                .build();
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        this.userField.mouseClicked(mouseX, mouseY, mouseButton);
        this.passwordField.mouseClicked(mouseX, mouseY, mouseButton);

        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    public void updateScreen() {
        this.userField.updateCursorCounter();
        this.passwordField.updateCursorCounter();
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        super.mouseReleased(mouseX, mouseY, state);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if (keyCode == 1) {
            this.mc.displayGuiScreen(this.parent);
            return;
        }

        this.userField.textboxKeyTyped(typedChar, keyCode);
        this.passwordField.textboxKeyTyped(typedChar, keyCode);
    }

    public static String getClipboard() {
        final Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();

        try {
            return (String) clipboard.getData(DataFlavor.stringFlavor);
        } catch (Exception e) {
            // no error, boring
            return null;
        }
    }
}
