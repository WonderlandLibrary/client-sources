package me.gishreload.yukon.gui;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Proxy;
import java.net.URL;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.util.Session;
import org.lwjgl.input.Keyboard;

import com.mojang.authlib.Agent;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;

public class AccountChange extends GuiScreen
{
    private GuiScreen parentScreen;
    private GuiTextField usernameTextField;
    private PasswordField passwordTextField;
    private String error;

    public AccountChange(GuiScreen guiscreen)
    {
        this.parentScreen = guiscreen;
    }

    /**
     * Called from the main game loop to update the screen.
     */
    public void updateScreen()
    {
        this.usernameTextField.updateCursorCounter();
        //this.passwordTextField.updateCursorCounter();
    }

    /**
     * "Called when the screen is unloaded. Used to disable keyboard repeat events."
     */
    public void onGuiClosed()
    {
        Keyboard.enableRepeatEvents(false);
    }

    protected void actionPerformed(GuiButton guibutton)
    {
        if (guibutton.enabled)
        {
            if (guibutton.id == 1)
            {
                this.mc.displayGuiScreen(this.parentScreen);
            }
            else if (guibutton.id == 0)
            {
                if (this.passwordTextField.getText().length() > 0)
                {
                    String s = this.usernameTextField.getText();
                    //String s1 = this.passwordTextField.getText();

                    try
                    {
                        String e = this.Login(s, s).trim();

                        if (e == null || !e.contains(":"))
                        {
                            this.error = e;
                            return;
                        }

                        String[] values = e.split(":");

                        if (values.length > 1)
                        {
                            this.mc.session = new Session(values[2], values[3], values[4], e);
                        }

                        this.mc.displayGuiScreen(this.parentScreen);
                    }
                    catch (Exception var6)
                    {
                        var6.printStackTrace();
                    }
                }
                else
                {
                    this.mc.session = new Session(this.usernameTextField.getText(), "", this.error, "");
                }

                this.mc.displayGuiScreen(this.parentScreen);
            }
        }
    }

    /**
     * Fired when a key is typed. This is the equivalent of KeyListener.keyTyped(KeyEvent e).
     */
    protected void keyTyped(char c, int i)
    {
        this.usernameTextField.textboxKeyTyped(c, i);
        //this.passwordTextField.textboxKeyTyped(c, i);

        if (c == 9)
        {
            if (this.usernameTextField.isFocused)
            {
                this.usernameTextField.isFocused = false;
                //this.passwordTextField.isFocused = true;
            }
            else
            {
                this.usernameTextField.isFocused = true;
                //this.passwordTextField.isFocused = false;
            }
        }

        if (c == 13)
        {
            this.actionPerformed((GuiButton)this.buttonList.get(0));
        }
    }

    /**
     * Called when the mouse is clicked.
     * @throws IOException 
     */
    protected void mouseClicked(int i, int j, int k) throws IOException
    {
        super.mouseClicked(i, j, k);
        this.usernameTextField.mouseClicked(i, j, k);
        //this.passwordTextField.mouseClicked(i, j, k);
    }

    /**
     * Adds the buttons (and other controls) to the screen in question.
     */
    public void initGui()
    {
        Keyboard.enableRepeatEvents(true);
        this.buttonList.clear();
        this.buttonList.add(new DarkButtons(0, this.width / 2 - 100, this.height / 4 + 96 + 12, "Done"));
        this.buttonList.add(new DarkButtons(1, this.width / 2 - 100, this.height / 4 + 120 + 12, "Cancel"));
        this.usernameTextField = new GuiTextField(eventButton, this.fontRendererObj, this.width / 2 - 100, 76, 200, 20);
        this.passwordTextField = new PasswordField(this, this.fontRendererObj, this.width / 2 - 100, 116, 200, 20, "");
    }

    /**
     * Draws the screen and all the components in it.
     */
    public void drawScreen(int i, int j, float f)
    {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRendererObj, "\u00a7fNick change - offline", this.width / 2, this.height / 4 - 60 + 20, 16777215);
        this.drawString(this.fontRendererObj, "\u00a77Nick name", this.width / 2 - 100, 63, 10526880);
        //this.drawString(this.fontRendererObj, "\u00a74Password", this.width / 2 - 100, 104, 10526880);
        this.usernameTextField.drawTextBox();
        //this.passwordTextField.drawTextBox();

        if (this.error != null)
        {
            this.drawCenteredString(this.fontRendererObj, "\u00a74 Login Failed:" + this.error, this.width / 2, this.height / 4 + 72 + 12, 16777215);
        }

        super.drawScreen(i, j, f);
    }

    public String Login(String username, String password)
    {
        String resultText = "";
        String loginpage = "http://login.minecraft.net/?user=" + username + "&password=" + password + "&version=13";

        try
        {
            BufferedReader e = this.read(loginpage);

            for (String s = ""; (s = e.readLine()) != null; resultText = s)
            {
                ;
            }
        }
        catch (Exception var7)
        {
            var7.printStackTrace();
        }

        return resultText;
    }

    private BufferedReader read(String url) throws Exception, FileNotFoundException
    {
        return new BufferedReader(new InputStreamReader((new URL(url)).openStream()));
    }
}
