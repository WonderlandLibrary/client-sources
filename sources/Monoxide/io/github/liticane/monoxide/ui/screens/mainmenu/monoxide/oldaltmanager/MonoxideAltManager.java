package io.github.liticane.monoxide.ui.screens.mainmenu.monoxide.oldaltmanager;

import fr.litarvan.openauth.microsoft.MicrosoftAuthResult;
import fr.litarvan.openauth.microsoft.MicrosoftAuthenticator;
import io.github.liticane.monoxide.microsoft.MicrosoftLogin;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.Session;
import org.lwjglx.input.Keyboard;

import java.io.IOException;

public class MonoxideAltManager extends GuiScreen {
    private GuiTextField textField;
    private String text = "Not pressed";

    public void updateScreen() {
        this.textField.updateCursorCounter();
    }

    public void initGui() {
        Keyboard.enableRepeatEvents(true);
        this.buttonList.clear();

        this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 96 + 12, I18n.format("Log In")));
        this.buttonList.add(new GuiButton(2, this.width / 2 - 100, this.height / 4 + 120 + 12, I18n.format("Browser Login")));
        this.buttonList.add(new GuiButton(3, this.width / 2 - 100, this.height / 4 + 144 + 12, I18n.format("Random Username")));
        this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height / 4 + 168 + 12, I18n.format("Back")));

        this.textField = new GuiTextField(2, this.fontRendererObj, this.width / 2 - 100, 116, 200, 20);
        this.textField.setMaxStringLength(128);
        this.textField.setFocused(true);

        this.buttonList.get(0).enabled = !this.textField.getText().isEmpty() && this.textField.getText().split(":").length > 0;
    }

    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
    }

    protected void actionPerformed(GuiButton button) throws IOException {
        if (button.enabled) {
            if (button.id == 1) {
                this.onGuiClosed();
                this.mc.displayGuiScreen(null);
            }
            if (button.id == 2)
            {
                text = "Copied link to your keyboard";
                MicrosoftLogin.getRefreshToken(refreshToken -> {
                    if (refreshToken != null) {
                        new Thread(() -> {
                            MicrosoftLogin.LoginData loginData = loginWithRefreshToken(refreshToken);
                        }).start();
                    }
                });
            } else {
                text = "Not pressed";
            }
            if (button.id == 3)
            {
                mc.session = new Session("Monoxide" + + Minecraft.getSystemTime() % 1000L, "SukuEnjoyer" + + Minecraft.getSystemTime() % 1000L, "0", "legacy");
            }

            else if (button.id == 0)
            {
                if (this.textField.getText().contains(":")){
                    try {
                        MicrosoftAuthenticator authenticator = new MicrosoftAuthenticator();
                        String[] splitString = this.textField.getText().split(":");

                        MicrosoftAuthResult result = authenticator.loginWithCredentials(splitString[0], splitString[1]);
                        mc.session = new Session(result.getProfile().getName(), result.getProfile().getId(), result.getAccessToken(), "legacy");

                    } catch (Exception e ){
                        e.printStackTrace();
                    }
                } else {
                    mc.session = new Session(textField.getText(), "", "", "mojang");
                }
            }
        }
    }
    private MicrosoftLogin.LoginData loginWithRefreshToken(String refreshToken) {
        final MicrosoftLogin.LoginData loginData = MicrosoftLogin.login(refreshToken);
        mc.session = new Session(loginData.username, loginData.uuid, loginData.mcToken, "microsoft");
        return loginData;
    }

    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if (this.textField.textboxKeyTyped(typedChar, keyCode))
        {
            this.buttonList.get(0).enabled = this.textField.getText().length() > 0 && this.textField.getText().split(":").length > 0;
        }
        else if (keyCode == 28 || keyCode == 156)
        {
            this.actionPerformed(this.buttonList.get(0));
        }
    }

    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException
    {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        this.textField.mouseClicked(mouseX, mouseY, mouseButton);
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRendererObj, I18n.format("Log in to an alt | Status " + text), this.width / 2, 20, 16777215);
        this.drawString(this.fontRendererObj, I18n.format("Username / Combo"), this.width / 2 - 100, 100, 10526880);
        this.drawString(this.fontRendererObj, I18n.format("Ign: " + mc.session.getUsername()), this.width / 2 - 50, 145, 10526880);
        this.textField.drawTextBox();
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
}