package mods.accountmanager.gui;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import de.labystudio.account.AccountManager;
import de.labystudio.labymod.LabyMod;
import de.labystudio.utils.DrawUtils;
import java.awt.Color;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.UUID;
import mods.accountmanager.Account;
import mods.accountmanager.utils.FancyTextField;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.Session;
import org.apache.commons.io.IOUtils;
import org.lwjgl.input.Keyboard;

public class LoginGui extends GuiScreen
{
    DrawUtils draw;
    public static String openNewAccountManagerGui = null;
    private String selectedFriend = "";
    private boolean allowScroll = false;
    private FancyTextField username;
    private FancyTextField password;
    private GuiButton done;
    private GuiButton cancel;
    String badLogin = "";
    long time = 0L;
    boolean flash = false;
    boolean login = false;

    public LoginGui()
    {
        this.draw = LabyMod.getInstance().draw;
    }

    /**
     * Adds the buttons (and other controls) to the screen in question. Called when the GUI is displayed and when the
     * window resizes, the buttonList is cleared beforehand.
     */
    public void initGui()
    {
        Keyboard.enableRepeatEvents(true);
        this.buttonList.clear();
        int i = this.width / 2 - 100;
        this.username = new FancyTextField(-1, Minecraft.getMinecraft().fontRendererObj, i, this.height / 2 - 50, 200, 20);
        this.username.setMaxStringLength(32);
        i = this.width / 2 - 100;
        int j = this.height / 2;
        this.password = new FancyTextField(-1, Minecraft.getMinecraft().fontRendererObj, i, j, 200, 20);
        this.password.setPassword(true);
        this.password.setMaxStringLength(32);
        this.done = new GuiButton(0, this.width / 2 - 100, this.height / 2 + 28, "Login");
        this.buttonList.add(this.done);
        this.cancel = new GuiButton(1, this.width / 2 - 100, this.height / 2 + 53, "Back");
        this.buttonList.add(this.cancel);
        super.initGui();
    }

    /**
     * Fired when a key is typed (except F11 which toggles full screen). This is the equivalent of
     * KeyListener.keyTyped(KeyEvent e). Args : character (character on the key), keyCode (lwjgl Keyboard key code)
     */
    protected void keyTyped(char typedChar, int keyCode) throws IOException
    {
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

        if (keyCode == 1 && this.login)
        {
            this.mc.displayGuiScreen(new AccountManagerGUI());
        }
        else
        {
            this.username.textboxKeyTyped(typedChar, keyCode);
            this.password.textboxKeyTyped(typedChar, keyCode);
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
            (new LoginGui.Login()).start();
        }

        if (button.id == 1)
        {
            this.mc.displayGuiScreen(new AccountManagerGUI());
        }
    }

    private Session getCurrentSession()
    {
        try
        {
            Field field = this.mc.getClass().getDeclaredField("ae");
            field.setAccessible(true);
            return (Session)field.get(this.mc);
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            return null;
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
        FontRenderer fontrenderer = Minecraft.getMinecraft().fontRendererObj;
        fontrenderer.drawString("Username/Email:", this.width / 2 - 100, this.height / 2 - 63, 16777215);
        fontrenderer.drawString("Password:", this.width / 2 - 100, this.height / 2 - 13, 16777215);

        if (!this.badLogin.isEmpty())
        {
            String s = "\u00a7f\u00a7l";
            drawRect(0, 10, this.width, 30, Color.RED.getRGB());

            if (this.time + 1000L > System.currentTimeMillis() && this.flash)
            {
                fontrenderer.drawString(s + "Error: " + this.badLogin, this.width / 2 - 1 - fontrenderer.getStringWidth("Error: " + this.badLogin) / 2, 16, 16777215);
            }
            else
            {
                fontrenderer.drawString(s + "Error: " + this.badLogin, this.width / 2 - fontrenderer.getStringWidth("Error: " + this.badLogin) / 2, 16, 16777215);
            }

            this.flash = !this.flash;
        }

        if (this.login)
        {
            drawRect(0, 10, this.width, 30, Color.BLUE.getRGB());
            fontrenderer.drawString("Logging in..", this.width / 2 - 1 - fontrenderer.getStringWidth("Logging in..") / 2, 16, 16777215);
        }

        this.username.drawTextBox();
        this.password.drawTextBox();
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    class Login extends Thread
    {
        public void run()
        {
            String s = AccountManager.login(LoginGui.this.username.getText(), LoginGui.this.password.getText());

            if (s.isEmpty())
            {
                Session session = LoginGui.this.getCurrentSession();

                if (session != null && !mods.accountmanager.AccountManager.containsAccount(session.getUsername()))
                {
                    JsonParser jsonparser = new JsonParser();
                    String s1 = null;

                    try
                    {
                        s1 = IOUtils.toString((InputStream)(new FileInputStream("launcher_profiles.json")));
                    }
                    catch (FileNotFoundException filenotfoundexception)
                    {
                        filenotfoundexception.printStackTrace();
                    }
                    catch (IOException ioexception)
                    {
                        ioexception.printStackTrace();
                    }

                    if (s1 != null)
                    {
                        JsonObject jsonobject = jsonparser.parse(s1).getAsJsonObject();
                        JsonObject jsonobject1 = jsonobject.get("authenticationDatabase").getAsJsonObject();
                        JsonObject jsonobject2 = new JsonObject();
                        jsonobject2.addProperty("displayName", session.getUsername());
                        jsonobject2.addProperty("accessToken", session.getToken());
                        jsonobject2.addProperty("uuid", session.getPlayerID());
                        jsonobject2.addProperty("username", LoginGui.this.username.getText());
                        jsonobject1.add(session.getPlayerID().replace("-", ""), jsonobject2);

                        try
                        {
                            Gson gson = (new GsonBuilder()).setPrettyPrinting().create();
                            FileWriter filewriter = new FileWriter("launcher_profiles.json");
                            filewriter.write(gson.toJson((JsonElement)jsonobject));
                            filewriter.flush();
                            filewriter.close();
                            System.out.println("Logged in into account " + session.getUsername());
                        }
                        catch (Exception exception)
                        {
                            exception.printStackTrace();
                        }
                    }

                    mods.accountmanager.AccountManager.accounts.add(new Account(session.getUsername(), session.getToken(), UUID.fromString(session.getPlayerID())));
                }

                LoginGui.this.badLogin = "";
                LoginGui.openNewAccountManagerGui = session.getUsername();
            }
            else
            {
                LoginGui.this.badLogin = s;
                LoginGui.this.time = System.currentTimeMillis();
            }

            LoginGui.this.login = false;
        }
    }
}
