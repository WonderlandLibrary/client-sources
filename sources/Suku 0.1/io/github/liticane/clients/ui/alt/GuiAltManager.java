package io.github.liticane.clients.ui.alt;

import fr.litarvan.openauth.microsoft.MicrosoftAuthResult;
import fr.litarvan.openauth.microsoft.MicrosoftAuthenticator;
import io.github.liticane.clients.ms.MicroshitLogin;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.Session;
import org.lwjglx.input.Keyboard;

import java.io.IOException;

public class GuiAltManager extends GuiScreen {
    private final GuiScreen field_146303_a;
    private GuiTextField field_146302_g;
    private String serverIP;
    private String text = "Not pressed";
    public GuiAltManager(GuiScreen p_i1031_1_)
    {
        this.field_146303_a = p_i1031_1_;
    }

    /**
     * Called from the main game loop to update the screen.
     */
    public void updateScreen()
    {
        this.field_146302_g.updateCursorCounter();
    }

    /**
     * Adds the buttons (and other controls) to the screen in question. Called when the GUI is displayed and when the
     * window resizes, the buttonList is cleared beforehand.
     */
    public void initGui()
    {
        Keyboard.enableRepeatEvents(true);
        this.buttonList.clear();
        this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 96 + 12, I18n.format("Log In", new Object[0])));
        this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height / 4 + 120 + 12, I18n.format("Back", new Object[0])));
        this.buttonList.add(new GuiButton(2, this.width / 2 - 100, this.height / 4 + 144 + 12, I18n.format("Broswer Login", new Object[0])));
        this.buttonList.add(new GuiButton(3, this.width / 2 - 100, this.height / 4 + 168 + 12, I18n.format("Random Username", new Object[0])));

        this.field_146302_g = new GuiTextField(2, this.fontRendererObj, this.width / 2 - 100, 116, 200, 20);
        this.field_146302_g.setMaxStringLength(128);
        this.field_146302_g.setFocused(true);

        ((GuiButton)this.buttonList.get(0)).enabled = this.field_146302_g.getText().length() > 0 && this.field_146302_g.getText().split(":").length > 0;
    }

    /**
     * Called when the screen is unloaded. Used to disable keyboard repeat events
     */
    public void onGuiClosed()
    {
        Keyboard.enableRepeatEvents(false);
    }

    /**
     * Called by the controls from the buttonList when activated. (Mouse pressed for buttons)
     */
    protected void actionPerformed(GuiButton button) throws IOException
    {
        if (button.enabled)
        {
            if (button.id == 1)
            {
                this.onGuiClosed();
                this.mc.display(null);
            }
            if (button.id == 2)
            {
                text = "Copied link to your keyboard";
                MicroshitLogin.getRefreshToken(refreshToken -> {
                    if (refreshToken != null) {
                        new Thread(() -> {
                            MicroshitLogin.LoginData loginData = loginWithRefreshToken(refreshToken);
                        }).start();
                    }
                });
            } else {
                text = "Not pressed";
            }
            if (button.id == 3)
            {
                mc.session = new Session("SukuEnjoyer" + + Minecraft.getSystemTime() % 1000L, "SukuEnjoyer" + + Minecraft.getSystemTime() % 1000L, "0", "legacy");
            }

            else if (button.id == 0)
            {
                if (this.field_146302_g.getText().contains(":")){
                    try {
                        MicrosoftAuthenticator authenticator = new MicrosoftAuthenticator();
                        String[] splitString = this.field_146302_g.getText().split(":");

                        MicrosoftAuthResult result = authenticator.loginWithCredentials(splitString[0], splitString[1]);
                        mc.session = new Session(result.getProfile().getName(), result.getProfile().getId(), result.getAccessToken(), "legacy");

                    } catch (Exception e ){
                        e.printStackTrace();
                    }
                } else {
                    mc.session = new Session(field_146302_g.getText(), "", "", "mojang");
                    // mc.session = new Session(this.field_146302_g.getText(), "", "", "legacy");
                }
            }
        }
    }
    private MicroshitLogin.LoginData loginWithRefreshToken(String refreshToken) {
        final MicroshitLogin.LoginData loginData = MicroshitLogin.login(refreshToken);
        mc.session = new Session(loginData.username, loginData.uuid, loginData.mcToken, "microsoft");
        return loginData;
    }

    /**
     * Fired when a key is typed (except F11 which toggles full screen). This is the equivalent of
     * KeyListener.keyTyped(KeyEvent e). Args : character (character on the key), keyCode (lwjgl Keyboard key code)
     */
    protected void keyTyped(char typedChar, int keyCode) throws IOException
    {
        if (this.field_146302_g.textboxKeyTyped(typedChar, keyCode))
        {
            ((GuiButton)this.buttonList.get(0)).enabled = this.field_146302_g.getText().length() > 0 && this.field_146302_g.getText().split(":").length > 0;
        }
        else if (keyCode == 28 || keyCode == 156)
        {
            this.actionPerformed((GuiButton)this.buttonList.get(0));
        }
    }

    /**
     * Called when the mouse is clicked. Args : mouseX, mouseY, clickedButton
     */
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException
    {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        this.field_146302_g.mouseClicked(mouseX, mouseY, mouseButton);
    }

    /**
     * Draws the screen and all the components in it. Args : mouseX, mouseY, renderPartialTicks
     */
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        //mc.session.getUsername()
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRendererObj, I18n.format("Log in to an alt | Status " + text, new Object[0]), this.width / 2, 20, 16777215);
        this.drawString(this.fontRendererObj, I18n.format("Username / Combo", new Object[0]), this.width / 2 - 100, 100, 10526880);
        this.drawString(this.fontRendererObj, I18n.format("Ign: " + mc.session.getUsername(), new Object[0]), this.width / 2 - 50, 145, 10526880);
        this.field_146302_g.drawTextBox();
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
}