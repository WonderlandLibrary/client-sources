/*
 * Decompiled with CFR 0_122.
 */
package winter.utils.other.account;

import java.io.IOException;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.Session;
import net.minecraft.util.StringUtils;
import org.lwjgl.input.Keyboard;
import winter.utils.other.account.LoginUtils;
import winter.utils.other.account.component.GuiPasswordField;

public class AccountManager
extends GuiScreen {
    private GuiScreen prevMenu;
    private GuiTextField theName;
    private GuiPasswordField thePassword;

    public AccountManager(GuiScreen parent) {
        this.prevMenu = parent;
    }

    @Override
    public void initGui() {
        this.buttonList.clear();
        boolean var2 = true;
        this.theName = new GuiTextField(0, Minecraft.getMinecraft().fontRendererObj, this.width / 2 - 100, this.height / 4 + 20, 200, 20);
        this.theName.setMaxStringLength(250);
        this.thePassword = new GuiPasswordField(0, this.fontRendererObj, this.width / 2 - 100, this.height / 4 + 60, 200, 20);
        this.thePassword.setMaxStringLength(250);
        this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 96, "Login/Change name?"));
        this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height / 4 + 120, I18n.format("menu.returnToMenu", new Object[0])));
        Keyboard.enableRepeatEvents(true);
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        switch (button.id) {
            case 0: {
                if (!StringUtils.isNullOrEmpty(this.theName.getText()) && !StringUtils.isNullOrEmpty(this.thePassword.getText())) {
                    LoginUtils.login(this.theName.getText(), this.thePassword.getText());
                    break;
                }
                if (StringUtils.isNullOrEmpty(this.theName.getText())) break;
                LoginUtils.changeCrackedName(this.theName.getText());
                break;
            }
            case 1: {
                this.mc.displayGuiScreen(this.prevMenu);
            }
        }
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        this.theName.textboxKeyTyped(typedChar, keyCode);
        this.thePassword.textboxKeyTyped(typedChar, keyCode);
        if (keyCode == 1) {
            this.mc.displayGuiScreen(this.prevMenu);
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        this.theName.mouseClicked(mouseX, mouseY, mouseButton);
        this.thePassword.mouseClicked(mouseX, mouseY, mouseButton);
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    public void updateScreen() {
        this.theName.updateCursorCounter();
        this.thePassword.updateCursorCounter();
        super.updateScreen();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        this.drawCenteredString(Minecraft.getMinecraft().fontRendererObj, "Login with a different account/use a cracked name", this.width / 2, 48, -1);
        this.theName.drawTextBox();
        this.thePassword.drawTextBox();
        this.drawString(Minecraft.getMinecraft().fontRendererObj, "Username", this.width / 2 - 100, this.height / 4 + 8, -1);
        this.drawString(Minecraft.getMinecraft().fontRendererObj, "Password", this.width / 2 - 100, this.height / 4 + 48, -1);
        this.drawCenteredString(Minecraft.getMinecraft().fontRendererObj, "Current name: \u00a7a" + Minecraft.getMinecraft().getSession().getUsername(), this.width / 2, 12, -1);
        if (LoginUtils.getMessage() != "") {
            this.drawCenteredString(Minecraft.getMinecraft().fontRendererObj, LoginUtils.getMessage(), this.width / 2, 24, 16711680);
        }
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
}

