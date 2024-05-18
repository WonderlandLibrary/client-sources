package net.minecraft.client.gui;

import de.labystudio.gui.GuiGommeHDSearch;
import de.labystudio.gui.GuiMenuScreen;
import de.labystudio.labymod.ConfigManager;
import de.labystudio.labymod.LabyMod;
import de.labystudio.listener.GommeHD;
import de.labystudio.utils.Color;
import de.labystudio.utils.DrawUtils;
import de.zockermaus.serverpinger.ServerPinger;
import java.io.IOException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.achievement.GuiAchievements;
import net.minecraft.client.gui.achievement.GuiStats;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.resources.I18n;
import net.minecraft.realms.RealmsBridge;

public class GuiIngameMenu extends GuiMenuScreen
{
    private int field_146445_a;
    private int field_146444_f;
    int confirmDisconnect = 0;
    GuiButton returnToMenu;
    ServerPinger pinger;

    public GuiIngameMenu()
    {
        super((GuiScreen)null);
        this.childScreen = this;
        this.id = "Menu";
    }

    /**
     * Adds the buttons (and other controls) to the screen in question. Called when the GUI is displayed and when the
     * window resizes, the buttonList is cleared beforehand.
     */
    public void initGui()
    {
        this.confirmDisconnect = 0;
        this.field_146445_a = 0;
        this.buttonList.clear();
        int i = -16;
        int j = 98;
        this.buttonList.add(this.returnToMenu = new GuiButton(1, this.width / 2 - 100, this.height / 4 + 120 + i, I18n.format("menu.returnToMenu", new Object[0])));

        if (!this.mc.isIntegratedServerRunning())
        {
            ((GuiButton)this.buttonList.get(0)).displayString = I18n.format("menu.disconnect", new Object[0]);
        }

        this.buttonList.add(new GuiButton(4, this.width / 2 - 100, this.height / 4 + 24 + i, I18n.format("menu.returnToGame", new Object[0])));
        this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 96 + i, 98, 20, I18n.format("menu.options", new Object[0])));
        GuiButton guibutton;
        this.buttonList.add(guibutton = new GuiButton(7, this.width / 2 + 2, this.height / 4 + 96 + i, 98, 20, I18n.format("menu.shareToLan", new Object[0])));
        this.buttonList.add(new GuiButton(5, this.width / 2 - 100, this.height / 4 + 48 + i, 98, 20, I18n.format("gui.achievements", new Object[0])));
        this.buttonList.add(new GuiButton(6, this.width / 2 + 2, this.height / 4 + 48 + i, 98, 20, I18n.format("gui.stats", new Object[0])));
        guibutton.enabled = this.mc.isSingleplayer() && !this.mc.getIntegratedServer().getPublic();

        if (GommeHD.isGommeHD())
        {
            this.buttonList.add(new GuiButton(8, this.width - 53, this.height - 23, 50, 20, "Search"));
        }

        String s = Color.cl("a") + "Gui enabled";

        if (ConfigManager.settings.hideMod)
        {
            s = Color.cl("c") + "Gui disabled";
        }

        this.buttonList.add(new GuiButton(9, this.width - 70, 4, 67, 20, s));
        super.initGui();
        ServerPinger serverpinger = new ServerPinger(LabyMod.getInstance().ip, LabyMod.getInstance().port);
        serverpinger.start();
        this.pinger = serverpinger;
    }

    /**
     * Called by the controls from the buttonList when activated. (Mouse pressed for buttons)
     */
    protected void actionPerformed(GuiButton button) throws IOException
    {
        switch (button.id)
        {
            case 0:
                this.mc.displayGuiScreen(new GuiOptions(this, this.mc.gameSettings));
                break;

            case 1:
                if (ConfigManager.settings.confirmDisconnect && !Minecraft.getMinecraft().isSingleplayer() && this.confirmDisconnect < 1)
                {
                    ++this.confirmDisconnect;
                    this.returnToMenu.displayString = Color.cl("c") + "Press again to confirm disconnect";
                    return;
                }

                button.enabled = false;
                boolean flag = this.mc.isIntegratedServerRunning();
                boolean flag1 = this.mc.func_181540_al();
                button.enabled = false;
                this.mc.theWorld.sendQuittingDisconnectingPacket();
                this.mc.loadWorld((WorldClient)null);

                if (flag)
                {
                    this.mc.displayGuiScreen(new GuiMainMenu());
                }
                else if (flag1)
                {
                    RealmsBridge realmsbridge = new RealmsBridge();
                    realmsbridge.switchToRealms(new GuiMainMenu());
                }
                else
                {
                    this.mc.displayGuiScreen(new GuiMultiplayer(new GuiMainMenu()));
                }

            case 2:
            case 3:
            default:
                break;

            case 4:
                this.mc.displayGuiScreen((GuiScreen)null);
                this.mc.setIngameFocus();
                break;

            case 5:
                this.mc.displayGuiScreen(new GuiAchievements(this, this.mc.thePlayer.getStatFileWriter()));
                break;

            case 6:
                this.mc.displayGuiScreen(new GuiStats(this, this.mc.thePlayer.getStatFileWriter()));
                break;

            case 7:
                this.mc.displayGuiScreen(new GuiShareToLan(this));
                break;

            case 8:
                this.mc.displayGuiScreen(new GuiGommeHDSearch());
                break;

            case 9:
                ConfigManager.settings.hideMod = !ConfigManager.settings.hideMod;
                this.initGui();
                ConfigManager.save();
        }

        super.actionPermformed(button);
    }

    public void drawServerInfo()
    {
        if (ConfigManager.settings.infoInMenu)
        {
            if (!LabyMod.getInstance().ip.isEmpty())
            {
                String s = LabyMod.getInstance().ip;

                if (this.pinger != null && this.pinger.getCurrentData() != null && this.pinger.getCurrentData().motd != null)
                {
                    try
                    {
                        int i = LabyMod.getInstance().draw.getHeight() - 32;
                        String s1 = Color.c + "c" + LabyMod.getInstance().ip.toUpperCase() + " " + Color.c + "5Players: " + Color.c + "f" + this.pinger.getCurrentData().players + "/" + this.pinger.getCurrentData().maxPlayers + " " + Color.c + "5Ping:" + Color.c + "f " + Color.c + "a" + this.pinger.getCurrentData().ms + "ms";
                        DrawUtils drawutils = LabyMod.getInstance().draw;
                        DrawUtils.drawRect(1, i - 2, LabyMod.getInstance().draw.getStringWidth(s1) + 5, this.height - 1, Integer.MIN_VALUE);
                        LabyMod.getInstance().draw.drawString(s1, 3.0D, (double)i);
                        i = i + 10;

                        if (this.pinger.getCurrentData().motd.contains("\n"))
                        {
                            String[] astring = this.pinger.getCurrentData().motd.split("\n");
                            LabyMod.getInstance().draw.drawString(astring[0], 3.0D, (double)i);
                            i = i + 10;
                            LabyMod.getInstance().draw.drawString(astring[1], 3.0D, (double)i);
                        }
                        else
                        {
                            int j = 45;

                            if (this.pinger.getCurrentData().motd.length() > j)
                            {
                                LabyMod.getInstance().draw.drawString(this.pinger.getCurrentData().motd.substring(0, j), 3.0D, (double)i);
                                i = i + 10;
                                LabyMod.getInstance().draw.drawString(this.pinger.getCurrentData().motd.substring(j, this.pinger.getCurrentData().motd.length()), 3.0D, (double)i);
                            }
                            else
                            {
                                LabyMod.getInstance().draw.drawString(this.pinger.getCurrentData().motd, 3.0D, (double)i);
                            }
                        }

                        LabyMod.getInstance().playerPing = this.pinger.getCurrentData().ms;
                    }
                    catch (Exception exception)
                    {
                        exception.printStackTrace();
                    }
                }
                else if (!this.mc.isSingleplayer())
                {
                    LabyMod.getInstance().draw.drawString(Color.cl("c") + "Loading server information..", 2.0D, (double)(LabyMod.getInstance().draw.getHeight() - 10));
                }
            }
        }
    }

    /**
     * Called from the main game loop to update the screen.
     */
    public void updateScreen()
    {
        super.updateScreen();
        ++this.field_146444_f;
    }

    /**
     * Draws the screen and all the components in it. Args : mouseX, mouseY, renderPartialTicks
     */
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRendererObj, I18n.format("menu.game", new Object[0]), this.width / 2, 40, 16777215);
        this.drawServerInfo();
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
}
