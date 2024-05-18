package mods.accountmanager.gui;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import de.labystudio.modapi.ModAPI;
import java.awt.Color;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import mods.accountmanager.Account;
import mods.accountmanager.AccountManager;
import mods.accountmanager.utils.FancyButton;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import org.apache.commons.codec.Charsets;
import org.apache.commons.io.IOUtils;

public class AccountManagerGUI extends GuiScreen
{
    public static final FontRenderer fontRendererObj;
    private AccountList list;
    private static ScaledResolution resolution;
    private String statusMessage = "";
    private long lastStatusMessageChanged = -1L;

    public AccountList getList()
    {
        return this.list;
    }

    public AccountManagerGUI()
    {
    }

    public AccountManagerGUI(String userName)
    {
        AccountManager.loadPlayerHead(userName);
        this.statusMessage = "\u00a7aAdded account: " + userName;
        this.lastStatusMessageChanged = System.currentTimeMillis();
    }

    /**
     * Adds the buttons (and other controls) to the screen in question. Called when the GUI is displayed and when the
     * window resizes, the buttonList is cleared beforehand.
     */
    public void initGui()
    {
        resolution = new ScaledResolution(Minecraft.getMinecraft());
        this.buttonList.clear();

        if (AccountManager.accounts.size() != 0)
        {
            this.list = new AccountList(Minecraft.getMinecraft(), resolution.getScaledWidth() - 20, resolution.getScaledHeight() - 50, 25, resolution.getScaledHeight() - 25, 20, 10);
            List<String> list = new ArrayList();

            for (Account account : AccountManager.accounts)
            {
                list.add(account.getUserName());
            }

            this.list.setAccounts(this, list);
        }
        else
        {
            this.list = null;
        }

        GuiButton guibutton = new FancyButton(3, 5, resolution.getScaledHeight() - 22, 60, 20, "Login");
        guibutton.enabled = false;
        GuiButton guibutton1 = new FancyButton(4, 70, resolution.getScaledHeight() - 22, 60, 20, "Logout");
        guibutton1.enabled = false;
        this.buttonList.add(new FancyButton(5, 135, resolution.getScaledHeight() - 22, 120, 20, "Add Account"));
        this.buttonList.add(guibutton);
        this.buttonList.add(guibutton1);
        this.buttonList.add(new FancyButton(6, 5, 3, 20, 20, "\u00a7c\u00ab"));
        super.initGui();
    }

    /**
     * Draws the screen and all the components in it. Args : mouseX, mouseY, renderPartialTicks
     */
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        this.drawDefaultBackground();

        if (this.list == null)
        {
            String s = "Something went wrong!";
            fontRendererObj.drawString(s, resolution.getScaledWidth() / 2 - fontRendererObj.getStringWidth(s) / 2, resolution.getScaledHeight() / 2 - 2, 16777215);
        }
        else
        {
            this.list.drawScreen(mouseX, mouseY, partialTicks);
        }

        if (!this.statusMessage.equals("") && (this.lastStatusMessageChanged == -1L || System.currentTimeMillis() - this.lastStatusMessageChanged < 2000L))
        {
            drawRect(0, 10, this.width, 30, Color.BLACK.getRGB());
            fontRendererObj.drawString(this.statusMessage, this.width / 2 - fontRendererObj.getStringWidth(this.statusMessage) / 2, 16, 16777215);
        }

        String s1 = "AccountManager v1.0";
        fontRendererObj.drawString(s1, this.width - fontRendererObj.getStringWidth(s1) - 5, 5, 16777215);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    /**
     * Called by the controls from the buttonList when activated. (Mouse pressed for buttons)
     */
    protected void actionPerformed(GuiButton button) throws IOException
    {
        super.actionPerformed(button);

        if (button.id == 3)
        {
            for (final Account account : AccountManager.accounts)
            {
                if (account.getUserName().equals(this.list.getAccountEntry().getName()))
                {
                    this.statusMessage = "\u00a7eLogging in...";
                    this.lastStatusMessageChanged = -1L;
                    System.out.println("[AccountManager] Logging in... (" + account.getUserName() + ")");
                    (new Thread(new Runnable()
                    {
                        public void run()
                        {
                            try
                            {
                                String s = AccountManagerGUI.this.performPostRequest(new URL("https://authserver.mojang.com/validate"), "{ \"accessToken\": \"" + account.getAccessToken() + "\" }");

                                if (s.equals(""))
                                {
                                    account.setToCurrentUser();
                                    AccountManagerGUI.this.statusMessage = "\u00a72Logged in! Enjoy playing!";
                                    AccountManagerGUI.this.lastStatusMessageChanged = System.currentTimeMillis();
                                    System.out.println("[AccountManager] Logged in (" + account.getUserName() + ")");
                                }
                                else
                                {
                                    AccountManagerGUI.this.statusMessage = "\u00a74Failed to login! Invalid accessToken!";
                                    AccountManagerGUI.this.lastStatusMessageChanged = System.currentTimeMillis();
                                    AccountManagerGUI.this.logOut(account.getUuid().toString());
                                }
                            }
                            catch (Exception exception)
                            {
                                exception.printStackTrace();
                                AccountManagerGUI.this.statusMessage = "\u00a74Failed login! " + exception.getMessage();
                                AccountManagerGUI.this.lastStatusMessageChanged = System.currentTimeMillis();
                            }
                        }
                    })).start();
                    break;
                }
            }
        }

        if (button.id == 4)
        {
            for (Account account1 : AccountManager.accounts)
            {
                if (account1.getUserName().equals(this.list.getAccountEntry().getName()))
                {
                    this.logOut(account1.getUuid().toString());
                    break;
                }
            }
        }

        if (button.id == 5)
        {
            this.mc.displayGuiScreen(new LoginGui());
        }

        if (button.id == 6)
        {
            this.mc.displayGuiScreen(ModAPI.getLastScreen());
        }

        if (this.list != null)
        {
            this.list.actionPerformed(button);
        }
    }

    private void logOut(String uuid)
    {
        Iterator<Account> iterator = AccountManager.accounts.iterator();

        while (iterator.hasNext())
        {
            Account account = (Account)iterator.next();

            if (account.getUuid().toString().equals(uuid))
            {
                iterator.remove();
            }
        }

        List<String> iterator1 = new ArrayList();

        for (Account account1 : AccountManager.accounts)
        {
            iterator1.add(account1.getUserName());
        }

        this.list.setAccounts(this, iterator1);
        JsonParser jsonparser = new JsonParser();
        String s = null;

        try
        {
            s = IOUtils.toString((InputStream)(new FileInputStream("launcher_profiles.json")));
        }
        catch (FileNotFoundException filenotfoundexception)
        {
            filenotfoundexception.printStackTrace();
        }
        catch (IOException ioexception)
        {
            ioexception.printStackTrace();
        }

        if (s != null)
        {
            JsonObject jsonobject = jsonparser.parse(s).getAsJsonObject();
            JsonObject jsonobject1 = jsonobject.get("authenticationDatabase").getAsJsonObject();
            jsonobject1.remove(uuid.replace("-", ""));

            try
            {
                Gson gson = (new GsonBuilder()).setPrettyPrinting().create();
                FileWriter filewriter = new FileWriter("launcher_profiles.json");
                filewriter.write(gson.toJson((JsonElement)jsonobject));
                filewriter.flush();
                filewriter.close();
                System.out.println("Logged out from account " + uuid);
            }
            catch (Exception exception)
            {
                exception.printStackTrace();
                System.out.println("[AccountManager] Failed logging out from " + uuid + "!");
            }
        }
    }

    private String performPostRequest(URL url, String post) throws Exception
    {
        HttpURLConnection httpurlconnection = (HttpURLConnection)url.openConnection();
        httpurlconnection.setConnectTimeout(15000);
        httpurlconnection.setReadTimeout(15000);
        httpurlconnection.setUseCaches(false);
        byte[] abyte = post.getBytes(Charsets.UTF_8);
        httpurlconnection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
        httpurlconnection.setRequestProperty("Content-Length", "" + abyte.length);
        httpurlconnection.setDoOutput(true);
        OutputStream outputstream = null;

        try
        {
            outputstream = httpurlconnection.getOutputStream();
            IOUtils.write(abyte, outputstream);
        }
        finally
        {
            IOUtils.closeQuietly(outputstream);
        }

        InputStream inputStream = null;
        String s2;

        try
        {
            inputStream = httpurlconnection.getInputStream();
            String s = IOUtils.toString(inputStream, Charsets.UTF_8);
            String s3 = s;
            return s3;
        }
        catch (IOException ioexception)
        {
            IOUtils.closeQuietly(inputStream);
            inputStream = httpurlconnection.getErrorStream();

            if (inputStream == null)
            {
                throw ioexception;
            }

            String s1 = IOUtils.toString(inputStream, Charsets.UTF_8);
            s2 = s1;
        }
        finally
        {
            IOUtils.closeQuietly(inputStream);
        }

        return s2;
    }

    /**
     * Handles mouse input.
     */
    public void handleMouseInput() throws IOException
    {
        super.handleMouseInput();

        if (this.list != null)
        {
            this.list.handleMouseInput();
        }
    }

    /**
     * Called when the mouse is clicked. Args : mouseX, mouseY, clickedButton
     */
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException
    {
        super.mouseClicked(mouseX, mouseY, mouseButton);

        if (this.list != null)
        {
            this.list.mouseClicked(mouseX, mouseY, mouseButton);
        }
    }

    public List<GuiButton> getButtonList()
    {
        return this.buttonList;
    }

    static
    {
        fontRendererObj = Minecraft.getMinecraft().fontRendererObj;
    }
}
