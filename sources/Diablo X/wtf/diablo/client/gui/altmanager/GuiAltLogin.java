package wtf.diablo.client.gui.altmanager;

import com.mojang.realmsclient.gui.ChatFormatting;
import fr.litarvan.openauth.microsoft.MicrosoftAuthResult;
import fr.litarvan.openauth.microsoft.MicrosoftAuthenticationException;
import fr.litarvan.openauth.microsoft.MicrosoftAuthenticator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.util.Session;
import wtf.diablo.client.util.system.alts.CookieAltsUtil;

import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

public final class GuiAltLogin extends GuiScreen {
    private final GuiScreen parent;
    private GuiTextField emailField;
    private GuiTextField passwordField;
    private String status;

    public GuiAltLogin(final GuiScreen parent) {
        this.parent = parent;
        this.status = "";
    }

    @Override
    public void initGui() {
        this.emailField = new GuiTextField(0, this.fontRendererObj, this.width / 2 - 100, 66, 200, 20);
        this.passwordField = new GuiTextField(1, this.fontRendererObj, this.width / 2 - 100, 106, 200, 20);

        this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 96 + 12, "Login"));
        this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height / 4 + 120 + 12, "Back"));
        this.buttonList.add(new GuiButton(2, this.width / 2 - 100, this.height / 4 + 144 + 12, "Login from clipboard"));
        this.buttonList.add(new GuiButton(3, this.width / 2 - 100, this.height / 4 + 168 + 12, "Paste from Clipboard"));
        this.buttonList.add(new GuiButton(4, this.width / 2 - 100, this.height / 4 + 192 + 12, "Cookie Alts"));

    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRendererObj, "Alt Login", this.width / 2, 20, -1);
        this.drawCenteredString(this.fontRendererObj, this.status, this.width / 2, 35, -1);
        this.drawString(this.fontRendererObj, "Email/Username:", this.width / 2 - 100, 53, -1);
        this.drawString(this.fontRendererObj, "Password:", this.width / 2 - 100, 94, -1);

        this.emailField.drawTextBox();
        this.passwordField.drawTextBox();

        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void actionPerformed(final GuiButton button) {
        switch (button.id) {
            case 0:
                this.login(this.emailField.getText(), this.passwordField.getText());
                break;
            case 1:
                this.mc.displayGuiScreen(this.parent);
                break;
            case 2:
                try {
                    final String[] data = Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor).toString().split(":");
                    this.login(data[0], data[1]);
                } catch (UnsupportedFlavorException | IOException | ArrayIndexOutOfBoundsException e) {
                    e.printStackTrace();
                }
                break;
            case 3:
                try {
                    final String[] data = Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor).toString().split(":");

                    this.emailField.setText(data[0]);
                    this.passwordField.setText(data[1]);
                } catch (UnsupportedFlavorException | IOException | ArrayIndexOutOfBoundsException e) {
                    e.printStackTrace();
                }
                break;
            case 4:
                try {
                    status = ChatFormatting.GREEN + "OAuth link has been copied to your clipboard! Paste it in your browser to login!";
                    CookieAltsUtil.getAccessToken();
                } catch (final Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    private void login(final String username, final String password){
        if(password.isEmpty()) {
            final Session session = new Session(username, "", "", "mojang");

            Minecraft.getMinecraft().setSession(session);
        } else {
            try {
                final MicrosoftAuthenticator authenticator = new MicrosoftAuthenticator();
                final MicrosoftAuthResult result = authenticator.loginWithCredentials(username, password);

                final Session session = new Session(result.getProfile().getName(), result.getProfile().getId(), result.getAccessToken(), "microsoft");

                Minecraft.getMinecraft().setSession(session);
            } catch (final MicrosoftAuthenticationException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException
    {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        this.emailField.mouseClicked(mouseX, mouseY, mouseButton);
        this.passwordField.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException
    {
        super.keyTyped(typedChar, keyCode);
        this.emailField.textboxKeyTyped(typedChar, keyCode);
        this.passwordField.textboxKeyTyped(typedChar, keyCode);
    }
}
