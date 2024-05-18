package net.minecraft.client.gui;

import de.labystudio.labymod.ConfigManager;
import de.labystudio.labymod.LabyMod;
import de.labystudio.labymod.Timings;
import de.labystudio.utils.Color;
import de.zockermaus.serverpinger.ServerPinger;
import java.io.IOException;
import net.minecraft.client.multiplayer.ServerAddress;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.resources.I18n;
import org.lwjgl.input.Keyboard;

public class GuiScreenServerList extends GuiScreen
{
    private final GuiScreen field_146303_a;
    private final ServerData field_146301_f;
    private GuiTextField field_146302_g;
    private static final String __OBFID = "CL_00000692";
    ServerPinger pinger;
    int online = 0;
    int max = 0;
    long update = 0L;

    public GuiScreenServerList(GuiScreen p_i1031_1_, ServerData p_i1031_2_)
    {
        this.field_146303_a = p_i1031_1_;
        this.field_146301_f = p_i1031_2_;
    }

    /**
     * Called from the main game loop to update the screen.
     */
    public void updateScreen()
    {
        this.field_146302_g.updateCursorCounter();

        if (ConfigManager.settings.directConnectInfo && !this.field_146302_g.getText().replace(" ", "").isEmpty() && this.update + 5000L < System.currentTimeMillis())
        {
            this.update = System.currentTimeMillis();

            try
            {
                boolean flag = false;
                ServerAddress serveraddress = ServerAddress.func_78860_a(this.field_146302_g.getText());

                if (this.pinger != null && this.pinger.getCurrentData() != null && this.pinger.getCurrentData().maxPlayers == 0 && this.pinger.getCurrentData().players == 0 && this.pinger.getCurrentData().serverName.equals(serveraddress.getIP()))
                {
                    flag = true;
                }

                ServerPinger serverpinger = new ServerPinger(serveraddress.getIP(), serveraddress.getPort());
                serverpinger.start();

                if (flag)
                {
                    this.pinger = null;
                    this.max = 0;
                    this.online = 0;
                }
                else
                {
                    this.pinger = serverpinger;
                }
            }
            catch (Exception exception)
            {
                this.pinger = null;
                exception.printStackTrace();
            }
        }
    }

    /**
     * Adds the buttons (and other controls) to the screen in question. Called when the GUI is displayed and when the
     * window resizes, the buttonList is cleared beforehand.
     */
    public void initGui()
    {
        Keyboard.enableRepeatEvents(true);
        this.buttonList.clear();
        this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 96 + 12, I18n.format("selectServer.select", new Object[0])));
        this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height / 4 + 120 + 12, I18n.format("gui.cancel", new Object[0])));
        this.field_146302_g = new GuiTextField(2, this.fontRendererObj, this.width / 2 - 100, 116, 200, 20);
        this.field_146302_g.setMaxStringLength(128);
        this.field_146302_g.setFocused(true);
        this.field_146302_g.setText(this.mc.gameSettings.lastServer);
        ((GuiButton)this.buttonList.get(0)).enabled = this.field_146302_g.getText().length() > 0 && this.field_146302_g.getText().split(":").length > 0;
    }

    /**
     * Called when the screen is unloaded. Used to disable keyboard repeat events
     */
    public void onGuiClosed()
    {
        Keyboard.enableRepeatEvents(false);
        this.mc.gameSettings.lastServer = this.field_146302_g.getText();
        this.mc.gameSettings.saveOptions();
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
                this.field_146303_a.confirmClicked(false, 0);
            }
            else if (button.id == 0)
            {
                this.field_146301_f.serverIP = this.field_146302_g.getText();
                this.field_146303_a.confirmClicked(true, 0);
            }
        }
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
            this.mc.gameSettings.lastServer = this.field_146302_g.getText();
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
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRendererObj, I18n.format("selectServer.direct", new Object[0]), this.width / 2, 20, 16777215);
        this.drawString(this.fontRendererObj, I18n.format("addServer.enterIp", new Object[0]), this.width / 2 - 100, 100, 10526880);
        this.field_146302_g.drawTextBox();

        if (ConfigManager.settings.directConnectInfo)
        {
            Timings.start("Serverlist pinger");

            if (this.pinger != null && this.pinger.getCurrentData() != null)
            {
                int i = this.pinger.getCurrentData().players;

                if (i > this.online)
                {
                    if (i - this.online > 500 && i != 0)
                    {
                        this.online = i;
                    }

                    ++this.online;
                }

                if (i < this.online)
                {
                    if (i - this.online < 500 && i != 0)
                    {
                        this.online = i;
                    }

                    --this.online;
                }

                if (this.pinger.getCurrentData().maxPlayers != 0 && i == 0)
                {
                    this.online = i;
                }

                i = this.pinger.getCurrentData().maxPlayers;

                if (i != 0)
                {
                    this.max = i;
                }

                boolean flag = this.pinger.getCurrentData().maxPlayers == 0 && this.pinger.getCurrentData().players == 0;

                if (flag)
                {
                    LabyMod.getInstance().draw.drawString(Color.c(1) + "Players: " + Color.cl("c") + this.online + "/" + this.max, (double)(this.width / 2 - 100), 140.0D);
                }
                else
                {
                    LabyMod.getInstance().draw.drawString(Color.c(1) + "Players: " + Color.cl("7") + this.online + "/" + this.max, (double)(this.width / 2 - 100), 140.0D);
                }
            }
            else
            {
                LabyMod.getInstance().draw.drawString(Color.cl("c") + "Pinging..", (double)(this.width / 2 - 100), 140.0D);
            }

            Timings.stop("Serverlist pinger");
        }

        super.drawScreen(mouseX, mouseY, partialTicks);
    }
}
