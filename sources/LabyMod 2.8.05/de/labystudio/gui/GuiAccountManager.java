package de.labystudio.gui;

import de.labystudio.account.AccountManager;
import de.labystudio.gui.extras.ModGuiTextField;
import de.labystudio.labymod.LabyMod;
import de.labystudio.utils.Color;
import de.labystudio.utils.DrawUtils;
import java.io.IOException;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import org.lwjgl.input.Keyboard;

public class GuiAccountManager extends GuiScreen
{
    DrawUtils draw;
    GuiScreen lastScreen;
    String selectedFriend = "";
    boolean allowScroll = false;
    ModGuiTextField username;
    ModGuiTextField password;
    GuiButton done;
    GuiButton cancel;
    String badLogin = "";
    long time = 0L;
    boolean flash = false;
    boolean login = false;

    public GuiAccountManager(GuiScreen lastScreen)
    {
        this.draw = LabyMod.getInstance().draw;
        this.lastScreen = lastScreen;
    }

    /**
     * Adds the buttons (and other controls) to the screen in question. Called when the GUI is displayed and when the
     * window resizes, the buttonList is cleared beforehand.
     */
    public void initGui()
    {
        Keyboard.enableRepeatEvents(true);
        this.buttonList.clear();
        this.username = new ModGuiTextField(-1, this.draw.fontRenderer, this.width / 2 - 100, this.height / 2 - 50, 200, 20);
        this.username.setBlacklistWord(" ");
        this.username.setText(AccountManager.accountManagerUsername);
        this.username.setMaxStringLength(64);
        this.password = new ModGuiTextField(-1, this.draw.fontRenderer, this.width / 2 - 100, this.height / 2, 200, 20);
        this.password.setBlacklistWord(" ");
        this.password.setText(AccountManager.accountManagerPassword);
        this.password.setPasswordBox(true);
        this.password.setMaxStringLength(64);
        this.done = new GuiButton(0, this.width / 2 - 100, this.height / 2 + 28, "Login");
        this.buttonList.add(this.done);
        this.cancel = new GuiButton(1, this.width / 2 - 100, this.height / 2 + 53, "Cancel");
        this.buttonList.add(this.cancel);
        super.initGui();
    }

    /**
     * Fired when a key is typed (except F11 which toggles full screen). This is the equivalent of
     * KeyListener.keyTyped(KeyEvent e). Args : character (character on the key), keyCode (lwjgl Keyboard key code)
     */
    protected void keyTyped(char typedChar, int keyCode) throws IOException
    {
        if (!this.login)
        {
            if (this.username.textboxKeyTyped(typedChar, keyCode))
            {
                AccountManager.accountManagerUsername = this.username.getText();
            }

            if (this.password.textboxKeyTyped(typedChar, keyCode))
            {
                AccountManager.accountManagerPassword = this.password.getText();
            }
        }

        if (this.done.enabled && (keyCode == 28 || keyCode == 156))
        {
            this.actionPerformed(this.done);
        }

        if (keyCode == 15)
        {
            if (!this.password.isFocused())
            {
                this.username.setFocused(false);
                this.password.setFocused(true);
            }
            else
            {
                this.username.setFocused(true);
                this.password.setFocused(false);
            }
        }

        if (keyCode == 1)
        {
            if (this.login)
            {
                this.mc.displayGuiScreen(this.lastScreen);
            }
        }
        else
        {
            super.keyTyped(typedChar, keyCode);
        }
    }

    /**
     * Called when the mouse is clicked. Args : mouseX, mouseY, clickedButton
     */
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException
    {
        this.username.mouseClicked(mouseX, mouseY, mouseButton);
        this.password.mouseClicked(mouseX, mouseY, mouseButton);
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    /**
     * Called by the controls from the buttonList when activated. (Mouse pressed for buttons)
     */
    protected void actionPerformed(GuiButton button) throws IOException
    {
        super.actionPerformed(button);

        if (button.id == 0)
        {
            this.login = true;
            (new GuiAccountManager.Login()).start();
        }

        if (button.id == 1)
        {
            this.mc.displayGuiScreen(this.lastScreen);
        }
    }

    /**
     * Draws the screen and all the components in it. Args : mouseX, mouseY, renderPartialTicks
     */
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        this.drawDefaultBackground();
        this.done.enabled = !this.login;
        this.cancel.enabled = !this.login;
        this.draw.drawString("Username/Email:", (double)(this.width / 2 - 100), (double)(this.height / 2 - 63));
        this.draw.drawString("Password:", (double)(this.width / 2 - 100), (double)(this.height / 2 - 13));

        if (!this.badLogin.isEmpty())
        {
            String s = Color.cl("f");
            drawRect(0, 10, this.width, 30, java.awt.Color.RED.getRGB());

            if (this.time + 1000L > System.currentTimeMillis() && this.flash)
            {
                this.draw.drawCenteredString(s + "Error: " + this.badLogin, this.width / 2 - 1, 16);
            }
            else
            {
                this.draw.drawCenteredString(s + "Error: " + this.badLogin, this.width / 2, 16);
            }

            this.flash = !this.flash;
        }

        if (this.login)
        {
            drawRect(0, 10, this.width, 30, java.awt.Color.BLUE.getRGB());
            this.draw.drawCenteredString("Logging in..", this.width / 2 - 1, 16);
        }

        this.username.drawTextBox();
        this.password.drawTextBox();
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    class Login extends Thread
    {
        public void run()
        {
            String s = AccountManager.login(GuiAccountManager.this.username.getText(), GuiAccountManager.this.password.getText());

            if (s.isEmpty())
            {
                GuiAccountManager.this.badLogin = "";
                GuiAccountManager.this.mc.displayGuiScreen(GuiAccountManager.this.lastScreen);
            }
            else
            {
                GuiAccountManager.this.badLogin = s;
                GuiAccountManager.this.time = System.currentTimeMillis();
            }

            GuiAccountManager.this.login = false;
        }
    }
}
