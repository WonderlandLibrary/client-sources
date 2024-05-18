package de.labystudio.gui;

import de.labystudio.chat.BroadcastType;
import de.labystudio.chat.ChatHandler;
import de.labystudio.chat.ClientConnection;
import de.labystudio.chat.EnumAlertType;
import de.labystudio.chat.LabyModPlayer;
import de.labystudio.chat.LabyModPlayerRequester;
import de.labystudio.chat.MessageChatComponent;
import de.labystudio.chat.ServerInfo;
import de.labystudio.chat.SingleChat;
import de.labystudio.chat.TitleChatComponent;
import de.labystudio.gui.extras.ModGuiTextField;
import de.labystudio.labymod.ConfigManager;
import de.labystudio.labymod.LabyMod;
import de.labystudio.labymod.Source;
import de.labystudio.labymod.Timings;
import de.labystudio.language.L;
import de.labystudio.packets.EnumConnectionState;
import de.labystudio.packets.PacketPlayChangeOptions;
import de.labystudio.packets.PacketPlayDenyFriendRequest;
import de.labystudio.packets.PacketPlayFriendRemove;
import de.labystudio.packets.PacketPlayRequestAddFriend;
import de.labystudio.packets.PacketPlayServerStatus;
import de.labystudio.utils.Color;
import de.labystudio.utils.DrawUtils;
import de.labystudio.utils.LOGO;
import de.labystudio.utils.ModGui;
import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.TargetDataLine;
import javax.sound.sampled.AudioFileFormat.Type;
import javax.sound.sampled.DataLine.Info;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.GlStateManager;
import org.apache.commons.lang3.ArrayUtils;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class GuiOnlineChat extends GuiMenuScreen
{
    DateFormat df = new SimpleDateFormat("HH:mm");
    DrawUtils draw;
    int copyLine = 0;
    long micCooldown = 0L;
    long switchScreen = 0L;
    boolean recording = false;
    GuiButton reconButton;
    GuiButton showSettingsButton;
    GuiButton showFileSharingButton;
    GuiButton micButton;
    GuiButton sendButton;
    GuiButton screenSelectButton;
    GuiButton friendSelectButton;
    GuiButton addFriendScreenButton;
    GuiButton playerStatusButton;
    boolean showSettingsBox = false;
    int showSettingsX = 0;
    int showSettingsY = 0;
    boolean showStatusBox = false;
    int showStatusX = 0;
    int showStatusY = 0;
    boolean showFileSharing = false;
    int showFileSharingX = 0;
    int showFileSharingY = 0;
    boolean showPlayerSettingsBox = false;
    int showPlayerSettingsX = 0;
    int showPlayerSettingsY = 0;
    ModGuiTextField searchArea;
    GuiTextField chatArea;
    GuiTextField motdEditor;
    ModGuiTextField searchFriendsArea;
    boolean YesNoBox = false;
    boolean stopSpam = false;
    boolean playerInfo = false;
    int scrollChatlog = 0;
    int chatLastY = 0;
    List<MessageChatComponent> chatlogList = new ArrayList();
    TargetDataLine micLine;
    File currentAudioFile;
    int micLevel = 0;
    int micLastLevel = 0;
    int changeCount = 0;
    int smoothLevel = 0;
    double result = 0.0D;
    int micTimer = 0;
    private static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss");
    int PSSizeX = 120;
    int PSSizeY = 55;
    int SSSizeX = 70;
    int SSSizeY = 42;
    int FSSizeX = 100;
    int FSSizeY = 18;
    int SBSizeX = 70;
    int SBSizeY = 54;
    LabyModPlayer showPlayerSettingsPlayer;
    boolean friendFinder = false;
    boolean playerExist = false;
    boolean screenSelector = false;
    int scrollScreenBrowser = 0;
    boolean endOfScreens = false;
    File screenSelected = null;
    File[] screenList;
    int friendListY;
    int scrollFriendList;
    int listPositionY = 20;
    int renderedFriends;
    int failedRender;
    List<LabyModPlayer> renderedPlayers = new ArrayList();
    int cacheMouseX = 0;

    GuiOnlineChat()
    {
        super((GuiScreen)null);
        this.childScreen = this;
        this.id = "Chat";
        this.draw = LabyMod.getInstance().draw;
    }

    /**
     * Adds the buttons (and other controls) to the screen in question. Called when the GUI is displayed and when the
     * window resizes, the buttonList is cleared beforehand.
     */
    public void initGui()
    {
        Timings.start("LabyMod Chat init");
        Keyboard.enableRepeatEvents(true);
        this.scrollFriendList = 0;
        this.scrollScreenBrowser = 0;
        this.scrollChatlog = 0;
        this.searchArea = new ModGuiTextField(-1, this.fontRendererObj, 29, 74, 100, 20);
        this.searchArea.setBlacklistWord(" ");
        this.searchArea.setMaxStringLength(16);
        this.searchArea.setEnableBackgroundDrawing(false);
        this.searchFriendsArea = new ModGuiTextField(-1, this.fontRendererObj, 0, 0, 180, 20);
        this.searchFriendsArea.setBlacklistWord(" ");
        this.searchFriendsArea.setMaxStringLength(16);

        if (this.friendFinder)
        {
            this.searchFriendsArea.setFocused(true);
        }

        this.chatArea = new GuiTextField(-1, this.fontRendererObj, 185, this.height - 24, this.width - 224, 18);
        this.chatArea.setMaxStringLength(120);
        this.motdEditor = new GuiTextField(-1, this.fontRendererObj, 145, 111, 218, 20);
        this.motdEditor.setMaxStringLength(70);
        this.motdEditor.setText(ConfigManager.settings.motd);
        this.refreshButtons();
        this.updateChatlog();
        Timings.stop("LabyMod Chat init");
    }

    private void refreshButtons()
    {
        this.buttonList.clear();

        if (this.screenSelector)
        {
            this.screenSelectButton = new GuiButton(5, 70, this.height - 25, 120, 20, "Send screenshot");
            this.buttonList.add(this.screenSelectButton);
            GuiButton guibutton4 = new GuiButton(6, 2, this.height - 25, 60, 20, "Cancel");
            this.buttonList.add(guibutton4);
        }
        else if (this.friendFinder)
        {
            this.friendSelectButton = new GuiButton(8, this.width / 2 + 5, this.height / 2 + 15, 87, 20, L._("gui_chat_sendrequest", new Object[0]));
            this.buttonList.add(this.friendSelectButton);
            GuiButton guibutton3 = new GuiButton(6, this.width / 2 - 90 - 1, this.height / 2 + 15, 90, 20, L._("button_cancel", new Object[0]));
            this.buttonList.add(guibutton3);
        }
        else
        {
            if (LabyMod.getInstance().selectedPlayer != null)
            {
                this.showFileSharingButton = new GuiButton(1, 140, this.height - 25, 20, 20, "+");
                this.showFileSharingButton.enabled = false;
                this.buttonList.add(this.showFileSharingButton);
                this.micButton = new GuiButton(2, 161, this.height - 25, 21, 20, "");
                this.micButton.enabled = false;
                this.buttonList.add(this.micButton);
                this.sendButton = new GuiButton(3, this.width - 35, this.height - 25, 30, 20, L._("gui_chat_send", new Object[0]));
                this.buttonList.add(this.sendButton);

                if (LabyMod.getInstance().selectedPlayer instanceof LabyModPlayerRequester)
                {
                    GuiButton guibutton = new GuiButton(10, (this.width - 140) / 2 + 2 + 140, this.height / 2 + 15, 90, 20, Color.cl("a") + L._("gui_chat_accept", new Object[0]));

                    if (this.stopSpam)
                    {
                        guibutton.enabled = false;
                    }

                    this.buttonList.add(guibutton);
                    guibutton = new GuiButton(9, (this.width - 140) / 2 - 92 + 140, this.height / 2 + 15, 90, 20, Color.cl("c") + L._("gui_chat_deny", new Object[0]));

                    if (this.stopSpam)
                    {
                        guibutton.enabled = false;
                    }

                    this.buttonList.add(guibutton);
                }

                if (this.YesNoBox)
                {
                    GuiButton guibutton1 = new GuiButton(12, this.width / 2 - 95, this.height / 2 + 25, 90, 20, Color.cl("c") + L._("status_no", new Object[0]));
                    this.buttonList.add(guibutton1);
                    guibutton1 = new GuiButton(13, this.width / 2 + 5, this.height / 2 + 25, 90, 20, Color.cl("a") + L._("status_yes", new Object[0]));
                    this.buttonList.add(guibutton1);
                }
            }
            else
            {
                GuiButton guibutton2 = new GuiButton(11, 143, 135, 130, 20, L._("gui_chat_showmyip", new Object[0]) + ": " + this.colorBoolean(ConfigManager.settings.showConntectedIP));
                this.buttonList.add(guibutton2);
                guibutton2 = new GuiButton(14, 275, 135, 90, 20, L._("gui_chat_showalerts", new Object[0]) + ": " + this.colorBoolean(ConfigManager.settings.alertsChat));
                this.buttonList.add(guibutton2);
                guibutton2 = new GuiButton(15, 143, 157, 130, 20, L._("gui_chat_showplayingalerts", new Object[0]) + ": " + this.colorBoolean(ConfigManager.settings.alertsPlayingOn));
                guibutton2.enabled = ConfigManager.settings.alertsChat;
                this.buttonList.add(guibutton2);
                guibutton2 = new GuiButton(17, 275, 157, 90, 20, L._("gui_chat_playsounds", new Object[0]) + ": " + this.colorBoolean(ConfigManager.settings.playSounds));
                this.buttonList.add(guibutton2);
                guibutton2 = new GuiButton(19, 143, 179, 130, 20, L._("gui_chat_ignorerequests", new Object[0]) + ": " + this.colorBoolean(ConfigManager.settings.ignoreRequests));
                this.buttonList.add(guibutton2);

                if (LOGO.isLogo(LabyMod.getInstance().getPlayerName()))
                {
                    guibutton2 = new GuiButton(1337, 275, 179, 90, 20, "Logomode: " + this.colorBoolean(ConfigManager.settings.logomode));
                    this.buttonList.add(guibutton2);
                }
            }

            this.showSettingsButton = new GuiButton(0, 7, 68, 20, 20, "!");
            this.buttonList.add(this.showSettingsButton);
            this.addFriendScreenButton = new GuiButton(7, this.width - 66 - 4, 3, 67, 20, L._("gui_chat_addfriend", new Object[0]));
            this.addFriendScreenButton.width = this.draw.getStringWidth(this.addFriendScreenButton.displayString) + 10;
            this.addFriendScreenButton.xPosition = this.width - this.draw.getStringWidth(this.addFriendScreenButton.displayString) - 10 - 4;
            this.buttonList.add(this.addFriendScreenButton);
            this.playerStatusButton = new GuiButton(16, this.width - this.draw.getStringWidth(this.addFriendScreenButton.displayString) - 45 - 4, 3, 35, 20, "?");
            this.buttonList.add(this.playerStatusButton);
            this.reconButton = new GuiButton(4, 6, this.height - 25, 130, 20, "?");
            this.reconButton.enabled = false;
            this.buttonList.add(this.reconButton);
            super.initGui();
            ConfigManager.save();
            this.stopSpam = false;
        }
    }

    private String colorBoolean(boolean b)
    {
        return b ? Color.cl("a") + L._("status_yes", new Object[0]) : Color.cl("c") + L._("status_no", new Object[0]);
    }

    /**
     * Draws the screen and all the components in it. Args : mouseX, mouseY, renderPartialTicks
     */
    public void drawScreen(int mouseX, int mouseY, float partialTicks)
    {
        this.cacheMouseX = mouseX;

        if (LabyMod.getInstance().isInGame())
        {
            GlStateManager.enableBlend();
            int i1 = this.width - 5;
            LabyMod.getInstance().draw.drawTransparentBackground(140, 25, i1, this.height - 30);
            int l = 5 + this.listPositionY;
            LabyMod.getInstance().draw.drawTransparentBackground(6, l, 138, this.height - 30);
        }
        else
        {
            this.drawDefaultBackground();
            int j = this.width - 5;
            LabyMod.getInstance().draw.drawChatBackground(140, 25, j, this.height - 30);
            int i = 5 + this.listPositionY;
            LabyMod.getInstance().draw.drawChatBackground(6, i, 138, this.height - 30);
        }

        GlStateManager.disableBlend();
        this.drawOpenScreenshots();

        if (LabyMod.getInstance().selectedPlayer != null && !ChatHandler.getMyFriends().contains(LabyMod.getInstance().selectedPlayer))
        {
            LabyMod.getInstance().selectedPlayer = null;
            this.initGui();
        }

        this.updateButtons();

        if (this.screenSelector)
        {
            this.drawScreenSelector();
            super.drawScreen(mouseX, mouseY, partialTicks);
        }
        else if (this.friendFinder)
        {
            this.drawFriendFinder();
            super.drawScreen(mouseX, mouseY, partialTicks);
        }
        else
        {
            this.drawChatlog();
            LabyMod.getInstance().draw.overlayBackground(0, 25);
            int k = this.height - 30;
            LabyMod.getInstance().draw.overlayBackground(k, this.height);
            this.drawChatArea();
            this.updateButtons();
            this.drawServerStatus();
            this.drawMyProfile();
            this.drawMyFriends();
            this.drawSearchArea();
            this.drawSettings();
            this.drawTitle();
            this.drawRequestScreen();
            this.drawYesNoBox();
            super.drawScreen(mouseX, mouseY, partialTicks);
            this.drawMic();
            this.drawSettingsBox();
            this.drawStatusBox();
            this.drawFileSharingBox();
            this.drawPlayerSettingsBox();
            this.drawPlayerInfo();

            if (LabyMod.getInstance().newMessage)
            {
                LabyMod.getInstance().newMessage = false;
                this.updateChatlog();
            }

            ChatHandler.updateIsWriting(LabyMod.getInstance().selectedPlayer, this.chatArea.getText());
        }
    }

    private void updateButtons()
    {
        Timings.start("LabyMod Chat update buttons");
        this.updateReconnectButton();
        this.updateShowSettingsButton();
        this.updateShowFileSharingButton();
        this.updateMicButton();
        this.updateSendButton();
        this.updateSelectButton();
        this.updateAddFriendScreenButton();
        this.updateSettingsButton();
        this.updateStatusButton();
        Timings.stop("LabyMod Chat update buttons");
    }

    private void updateStatusButton()
    {
        if (this.playerStatusButton != null)
        {
            if (ChatHandler.playerStatus == 0)
            {
                this.playerStatusButton.displayString = Color.cl("a") + L._("gui_chat_status_online", new Object[0]);
            }

            if (ChatHandler.playerStatus == 1)
            {
                this.playerStatusButton.displayString = Color.cl("b") + L._("gui_chat_status_away", new Object[0]);
            }

            if (ChatHandler.playerStatus == 2)
            {
                this.playerStatusButton.displayString = Color.cl("d") + L._("gui_chat_status_busy", new Object[0]);
            }

            this.playerStatusButton.enabled = !this.showStatusBox;

            if (LabyMod.getInstance().getClient().getClientConnection().getState() != EnumConnectionState.PLAY)
            {
                this.showStatusBox = false;
                this.playerStatusButton.enabled = false;
                this.playerStatusButton.visible = false;
            }
            else
            {
                this.playerStatusButton.visible = true;
            }
        }
    }

    private void updateAddFriendScreenButton()
    {
        if (this.addFriendScreenButton != null)
        {
            this.addFriendScreenButton.enabled = LabyMod.getInstance().client.getClientConnection().getState() == EnumConnectionState.PLAY;
        }
    }

    private void updateSelectButton()
    {
        if (this.friendSelectButton != null && this.friendFinder)
        {
            this.friendSelectButton.enabled = !this.searchFriendsArea.getText().isEmpty();

            if (this.searchFriendsArea.getText().equalsIgnoreCase(LabyMod.getInstance().getPlayerName()))
            {
                this.friendSelectButton.enabled = false;
            }

            for (LabyModPlayer labymodplayer : LabyMod.getInstance().client.getFriends())
            {
                if (labymodplayer.getName().equalsIgnoreCase(this.searchFriendsArea.getText()))
                {
                    this.friendSelectButton.enabled = false;
                }
            }

            for (LabyModPlayer labymodplayer1 : LabyMod.getInstance().client.getRequests())
            {
                if (labymodplayer1.getName().equalsIgnoreCase(this.searchFriendsArea.getText()))
                {
                    this.friendSelectButton.enabled = false;
                }
            }
        }

        if (this.screenSelectButton != null)
        {
            this.screenSelectButton.enabled = this.screenSelected != null;
        }
    }

    private void updateSendButton()
    {
        if (this.sendButton != null)
        {
            if (LabyMod.getInstance().selectedPlayer != null)
            {
                this.sendButton.enabled = !LabyMod.getInstance().selectedPlayer.isRequest() && !this.chatArea.getText().replace(" ", "").isEmpty() && LabyMod.getInstance().getClient().getClientConnection().getState() == EnumConnectionState.PLAY;
            }
            else
            {
                this.sendButton.enabled = false;
            }
        }
    }

    private void updateMicButton()
    {
        if (this.micButton != null)
        {
            this.micButton.enabled = false;
        }
    }

    private void updateSettingsButton()
    {
        if (!ConfigManager.settings.alertsChat && ConfigManager.settings.alertsPlayingOn)
        {
            ConfigManager.settings.alertsPlayingOn = false;
            this.initGui();
        }
    }

    private String getConnectionStatus()
    {
        EnumConnectionState enumconnectionstate = LabyMod.getInstance().client.getClientConnection().getState();
        return enumconnectionstate == EnumConnectionState.PLAY ? Color.cl("a") + L._("gui_chat_connection_connected", new Object[0]) + Color.cl("f") : (enumconnectionstate == EnumConnectionState.HELLO ? Color.cl("c") + L._("gui_chat_connection_connecting", new Object[0]) + Color.cl("f") : (enumconnectionstate == EnumConnectionState.LOGIN ? Color.cl("c") + L._("gui_chat_connection_loggingin", new Object[0]) + Color.cl("f") : Color.cl("4") + L._("gui_chat_connection_notconnected", new Object[0]) + Color.cl("f")));
    }

    private void updateShowSettingsButton()
    {
        if (ConfigManager.settings.showSettingsFriend == 0)
        {
            this.showSettingsButton.displayString = Color.cl("b") + "All";
        }

        if (ConfigManager.settings.showSettingsFriend == 1)
        {
            this.showSettingsButton.displayString = Color.cl("a") + "On";
        }

        if (ConfigManager.settings.showSettingsFriend == 2)
        {
            this.showSettingsButton.displayString = Color.cl("e") + "Rq";
        }

        if (ConfigManager.settings.showSettingsFriend == 3)
        {
            this.showSettingsButton.displayString = Color.cl("6") + "<-";
        }

        this.showSettingsButton.enabled = !this.showSettingsBox;
    }

    private void updateShowFileSharingButton()
    {
        if (this.showFileSharingButton != null)
        {
            this.showFileSharingButton.enabled = false;
        }
    }

    private void updateReconnectButton()
    {
        if (LabyMod.getInstance().client.getClientConnection().getState() == EnumConnectionState.PLAY)
        {
            this.reconButton.displayString = L._("gui_chat_logout", new Object[0]);
        }
        else
        {
            this.reconButton.displayString = L._("gui_chat_login", new Object[0]);
        }

        this.reconButton.enabled = LabyMod.getInstance().lastRecon + 5000L < System.currentTimeMillis();

        if (!this.reconButton.enabled)
        {
            if (LabyMod.getInstance().client.getClientConnection().getState() == EnumConnectionState.PLAY)
            {
                this.reconButton.displayString = L._("gui_chat_loggedin", new Object[0]);
            }
            else
            {
                this.reconButton.displayString = L._("gui_chat_loggedout", new Object[0]);
            }
        }

        if (LabyMod.getInstance().client.getClientConnection().getState() == EnumConnectionState.LOGIN)
        {
            this.reconButton.enabled = false;
            this.reconButton.displayString = L._("gui_chat_pleasewait", new Object[0]);
        }
    }

    private void drawYesNoBox()
    {
        if (this.YesNoBox)
        {
            this.draw.drawBox(this.width / 2 - 100, this.height / 2 - 20, this.width / 2 + 100, this.height / 2 + 50);
            this.draw.drawCenteredString(Color.cl("c") + L._("gui_chat_warning", new Object[0]), this.width / 2, this.height / 2 - 13);
            this.draw.drawCenteredString(L._("gui_chat_removequestion_line1", new Object[0]), this.width / 2, this.height / 2 - 2);
            this.draw.drawCenteredString(L._("gui_chat_removequestion_line2", new Object[] {this.showPlayerSettingsPlayer.getName()}), this.width / 2, this.height / 2 + 8);
        }
    }

    private void drawSettings()
    {
        if (LabyMod.getInstance().selectedPlayer == null)
        {
            GL11.glColor3d(1.0D, 1.0D, 1.0D);
            GL11.glPushMatrix();
            GL11.glScaled(2.0D, 2.0D, 2.0D);
            GL11.glTranslated(-78.2D, -8.0D, 0.0D);
            LabyMod.getInstance().textureManager.drawPlayerHead(LabyMod.getInstance().getPlayerName(), 150.0D, 25.0D, 1.0D);
            GL11.glScaled(0.7D, 0.7D, 0.7D);
            GL11.glTranslated(79.2D, 10.0D, 0.0D);
            this.draw.drawString(Color.cl("b") + Color.cl("l") + LabyMod.getInstance().getPlayerName(), 183.0D, 28.0D);
            GL11.glPopMatrix();
            this.draw.drawString(L._("gui_chat_personalmessage", new Object[0]) + ":", 145.0D, 98.0D);
            this.motdEditor.drawTextBox();

            if (this.isConnected())
            {
                this.draw.drawString(L._("gui_chat_contacts", new Object[0]) + ": " + Color.cl("7") + LabyMod.getInstance().client.build().getContactsAmount(), 210.0D, 53.0D);

                if (LabyMod.getInstance().draw.getHeight() > 260)
                {
                    this.draw.drawString(L._("gui_chat_datejoined", new Object[0]) + ":", 150.0D, 208.0D);
                    this.draw.drawString(this.buildDate(LabyMod.getInstance().client.build().getJoinedFirst()), 150.0D, 219.0D);
                    this.draw.drawString(L._("gui_chat_lasttimeonline", new Object[0]) + ":", 270.0D, 208.0D);
                    this.draw.drawString(this.buildDate(LabyMod.getInstance().client.build().getLastOnline()), 270.0D, 219.0D);
                }
            }
        }
    }

    public boolean isConnected()
    {
        return LabyMod.getInstance().client.getClientConnection().getState() == EnumConnectionState.PLAY;
    }

    public String buildDate(long time)
    {
        Date date = new Date(time);
        DateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        return Color.cl("7") + dateformat.format(date);
    }

    private void drawPlayerInfo()
    {
        if (this.playerInfo && LabyMod.getInstance().selectedPlayer != null)
        {
            this.draw.drawBox(this.width / 2 - 180, this.height / 2 - 70, this.width / 2 + 180, this.height / 2 + 70);
            LabyMod.getInstance().textureManager.drawPlayerHead(LabyMod.getInstance().selectedPlayer.getName(), (double)(this.width / 2 - 175), (double)(this.height / 2 - 60), 4.1D);
            double d0 = 1.7D;
            GL11.glPushMatrix();
            GL11.glScaled(d0, d0, d0);
            this.draw.drawString(LabyMod.getInstance().selectedPlayer.getName(), (double)((int)((double)(this.width / 2 - 40) / d0)), (double)((int)((double)(this.height / 2) / d0 - 35.0D)));
            GL11.glPopMatrix();
            GL11.glPushMatrix();
            d0 = 1.2D;
            GL11.glScaled(d0, d0, d0);
            this.drawStatusSymbol(LabyMod.getInstance().selectedPlayer.getStatus(), LabyMod.getInstance().selectedPlayer.isRequest(), (int)((double)(this.width / 2 - 40) / d0), (int)((double)(this.height / 2) / d0 - 34.0D));
            String s = Color.cl("e") + L._("gui_chat_status_request", new Object[0]);

            if (!LabyMod.getInstance().selectedPlayer.isRequest())
            {
                if (LabyMod.getInstance().selectedPlayer.getStatus() == LabyModPlayer.OnlineStatus.ONLINE)
                {
                    s = Color.cl("2") + L._("gui_chat_status_online", new Object[0]);
                }

                if (LabyMod.getInstance().selectedPlayer.getStatus() == LabyModPlayer.OnlineStatus.AWAY)
                {
                    s = Color.cl("b") + L._("gui_chat_status_away", new Object[0]);
                }

                if (LabyMod.getInstance().selectedPlayer.getStatus() == LabyModPlayer.OnlineStatus.BUSY)
                {
                    s = Color.cl("d") + L._("gui_chat_status_busy", new Object[0]);
                }

                if (LabyMod.getInstance().selectedPlayer.getStatus() == LabyModPlayer.OnlineStatus.OFFLINE)
                {
                    s = Color.cl("4") + L._("gui_chat_status_offline", new Object[0]);
                }
            }

            this.draw.drawString(s, (double)((int)((double)(this.width / 2 - 25) / d0)), (double)((int)((double)(this.height / 2) / d0 - 34.0D)));
            GL11.glPopMatrix();

            if (LabyMod.getInstance().selectedPlayer.isOnline() && !LabyMod.getInstance().selectedPlayer.getTimeZone().isEmpty())
            {
                this.df.setCalendar(Calendar.getInstance());
                this.df.setTimeZone(TimeZone.getTimeZone(LabyMod.getInstance().selectedPlayer.getTimeZone()));
                this.draw.drawString(this.df.format(this.df.getCalendar().getTime()) + " " + this.df.getTimeZone().getDisplayName(), (double)(this.width / 2 - 40), (double)(this.height / 2 - 25));
            }

            if (LabyMod.getInstance().selectedPlayer.isOnline() && LabyMod.getInstance().selectedPlayer.getContactsAmount() != 0)
            {
                this.draw.drawString(L._("gui_chat_contacts", new Object[0]) + ": " + Color.cl("7") + LabyMod.getInstance().selectedPlayer.getContactsAmount(), (double)(this.width / 2 - 40), (double)(this.height / 2 - 13));
            }

            if (!LabyMod.getInstance().selectedPlayer.getServerInfo().getServerIp().replace(" ", "").isEmpty() && LabyMod.getInstance().selectedPlayer.isOnline())
            {
                if (LabyMod.getInstance().selectedPlayer.getServerInfo().getSpecifiedServerName() != null)
                {
                    this.draw.drawString(L._("gui_chat_server", new Object[0]) + ": " + Color.cl("7") + LabyMod.getInstance().selectedPlayer.getServerInfo().getServerIp() + " (" + LabyMod.getInstance().selectedPlayer.getServerInfo().getSpecifiedServerName() + ")", (double)(this.width / 2 - 40), (double)(this.height / 2 - 3));
                }
                else
                {
                    this.draw.drawString(L._("gui_chat_server", new Object[0]) + ": " + Color.cl("7") + LabyMod.getInstance().selectedPlayer.getServerInfo().getServerIp(), (double)(this.width / 2 - 40), (double)(this.height / 2 - 3));
                }
            }

            if (!LabyMod.getInstance().selectedPlayer.isRequest())
            {
                this.draw.drawString(L._("gui_chat_datejoined", new Object[0]) + ":", (double)(this.width / 2 - 40), (double)(this.height / 2 + 12));
                this.draw.drawString(this.buildDate(LabyMod.getInstance().selectedPlayer.getJoinedFirst()), (double)(this.width / 2 - 40), (double)(this.height / 2 + 23));
                this.draw.drawString(L._("gui_chat_lasttimeonline", new Object[0]) + ":", (double)(this.width / 2 - 40), (double)(this.height / 2 + 37));
                this.draw.drawString(this.buildDate(LabyMod.getInstance().selectedPlayer.getLastOnline()), (double)(this.width / 2 - 40), (double)(this.height / 2 + 48));
            }
        }
    }

    private void drawRequestScreen()
    {
        if (LabyMod.getInstance().selectedPlayer != null)
        {
            if (LabyMod.getInstance().selectedPlayer instanceof LabyModPlayerRequester)
            {
                GL11.glColor3d(1.0D, 1.0D, 1.0D);
                LabyMod.getInstance().textureManager.drawPlayerHead(LabyMod.getInstance().selectedPlayer.getName(), (double)((this.width - 140) / 2 + 5 + 100), (double)(this.height / 2 - 75), 2.0D);
                double d0 = 2.0D;
                GL11.glPushMatrix();
                GL11.glScaled(d0, d0, d0);
                this.draw.drawCenteredString(Color.cl("b") + Color.cl("l") + LabyMod.getInstance().selectedPlayer.getName(), ((this.width - 140) / 2 + 5 + 132) / (int)d0, this.height / 2 / (int)d0 - 3);
                GL11.glPopMatrix();
            }
        }
    }

    private String fixLine(String i)
    {
        while (i.contains("  "))
        {
            i = i.replace("  ", "");
        }

        return i;
    }

    private void sendMessage()
    {
        if (this.chatArea != null)
        {
            if (LabyMod.getInstance().selectedPlayer != null)
            {
                if (!LabyMod.getInstance().selectedPlayer.isRequest())
                {
                    if (!this.chatArea.getText().replace(" ", "").isEmpty())
                    {
                        if (LabyMod.getInstance().getClient().getClientConnection().getState() == EnumConnectionState.PLAY)
                        {
                            String s = this.fixLine(this.chatArea.getText());
                            s = s.replace("\'", "\u00b4");
                            this.chatArea.setText("");
                            SingleChat singlechat = ChatHandler.getHandler().getChat(LabyMod.getInstance().selectedPlayer);

                            if (LabyMod.getInstance().client.getClientConnection().isNextDay(singlechat.getMessages()))
                            {
                                singlechat.addMessage(new TitleChatComponent(LabyMod.getInstance().getPlayerName(), System.currentTimeMillis(), LabyMod.getInstance().client.getClientConnection().getThisDay()));
                            }

                            singlechat.addMessage(new MessageChatComponent(LabyMod.getInstance().getPlayerName(), System.currentTimeMillis(), s));
                            LabyMod.getInstance().selectedPlayer.setLastMessage(System.currentTimeMillis());
                            this.updateChatlog();
                        }
                    }
                }
            }
        }
    }

    private void drawChatlog()
    {
        if (LabyMod.getInstance().selectedPlayer != null)
        {
            if (!(LabyMod.getInstance().selectedPlayer instanceof LabyModPlayerRequester))
            {
                int i = 145;
                int j = 25;
                int k = this.height - 43 - this.scrollChatlog;
                int l = k;
                List<MessageChatComponent> list = this.getCurrentChatlog();

                if (list != null)
                {
                    if (LabyMod.getInstance().getClient().isTyping(LabyMod.getInstance().selectedPlayer) && LabyMod.getInstance().selectedPlayer.isOnline())
                    {
                        l = k - 20;
                        this.chatLastY = l - 20;
                        this.draw.drawString(Color.cl("3") + L._("gui_chat_typing", new Object[] {LabyMod.getInstance().selectedPlayer.getName()}), (double)i, (double)(this.chatLastY + 37));
                    }

                    for (MessageChatComponent messagechatcomponent : list)
                    {
                        messagechatcomponent.draw(i, l);
                        l -= messagechatcomponent.getYSize();
                        this.chatLastY = l - 20;
                    }
                }

                DrawUtils drawutils = this.draw;
                DrawUtils.drawRect(i - 5, j, this.draw.getWidth() - 5, j + 31, Color.toRGB(0, 0, 0, 240));
                this.draw.overlayBackground(i - 4, j + 1, this.draw.getWidth() - i - 2, j + 30);
                String s = Color.cl("4") + "OFFLINE";

                if (LabyMod.getInstance().selectedPlayer.getStatus() == LabyModPlayer.OnlineStatus.ONLINE)
                {
                    s = Color.cl("a") + "ONLINE";
                }

                if (LabyMod.getInstance().selectedPlayer.getStatus() == LabyModPlayer.OnlineStatus.BUSY)
                {
                    s = Color.cl("5") + "BUSY";
                }

                if (LabyMod.getInstance().selectedPlayer.getStatus() == LabyModPlayer.OnlineStatus.AWAY)
                {
                    s = Color.cl("b") + "AWAY";
                }

                LabyMod.getInstance().textureManager.drawPlayerHead(LabyMod.getInstance().selectedPlayer.getName(), (double)i, (double)(j + 8), 0.7D);
                LabyMod.getInstance().draw.drawString(LabyMod.getInstance().selectedPlayer.getName(), (double)(i + 26), (double)(j + 6));
                LabyMod.getInstance().draw.drawString(Color.cl("7") + LabyMod.getInstance().selectedPlayer.getMotd(), (double)(i + 26), (double)(j + 17));
                LabyMod.getInstance().draw.drawRightString(s, (double)(this.draw.getWidth() - 8), (double)(j + 3));

                if (LabyMod.getInstance().selectedPlayer.isOnline() && !LabyMod.getInstance().selectedPlayer.getTimeZone().isEmpty())
                {
                    this.df.setCalendar(Calendar.getInstance());
                    this.df.setTimeZone(TimeZone.getTimeZone(LabyMod.getInstance().selectedPlayer.getTimeZone()));
                    LabyMod.getInstance().draw.drawRightString(this.df.format(this.df.getCalendar().getTime()) + " Uhr", (double)(this.draw.getWidth() - 8), (double)(j + 15));
                }

                ServerInfo serverinfo = LabyMod.getInstance().selectedPlayer.getServerInfo();

                if (serverinfo != null)
                {
                    if (serverinfo.getServerIp() != null && !serverinfo.getServerIp().replace(" ", "").isEmpty())
                    {
                        if (serverinfo.getServerIp().equals("Hidden"))
                        {
                            LabyMod.getInstance().draw.drawRightString(Color.cl("4") + "Hidden serverip " + Color.cl("7") + "| ", (double)(this.draw.getWidth() - 10 - this.draw.getStringWidth(s)), (double)(j + 3));
                        }
                        else
                        {
                            LabyMod.getInstance().draw.drawRightString(Color.cl("e") + serverinfo.getServerIp() + " " + Color.cl("7") + "| ", (double)(this.draw.getWidth() - 10 - this.draw.getStringWidth(s)), (double)(j + 3));
                        }
                    }
                    else
                    {
                        LabyMod.getInstance().draw.drawRightString(Color.cl("c") + "Not playing " + Color.cl("7") + "| ", (double)(this.draw.getWidth() - 10 - this.draw.getStringWidth(s)), (double)(j + 3));
                    }
                }
            }
        }
    }

    private void drawOpenScreenshots()
    {
        List<MessageChatComponent> list = this.getCurrentChatlog();

        if (list != null)
        {
            for (MessageChatComponent messagechatcomponent : list)
            {
                messagechatcomponent.drawOpen();
            }
        }
    }

    private List<MessageChatComponent> getCurrentChatlog()
    {
        List<MessageChatComponent> list = new ArrayList();
        list.clear();
        list.addAll(this.chatlogList);
        return list;
    }

    public void updateChatlog()
    {
        new ArrayList();
        Object list;

        if (LabyMod.getInstance().selectedPlayer != null && LabyMod.getInstance().selectedPlayer instanceof LabyModPlayer && !LabyMod.getInstance().selectedPlayer.isRequest())
        {
            list = ChatHandler.getHandler().getChat(LabyMod.getInstance().selectedPlayer).getMessages();
        }
        else
        {
            list = new ArrayList();
        }

        this.chatlogList.clear();
        this.chatlogList.addAll((Collection<MessageChatComponent>)list);
    }

    public double avg(double... d)
    {
        double d0 = 0.0D;

        for (int i = 0; i < d.length; ++i)
        {
            d0 += d[i];
        }

        return d0 / (double)d.length;
    }

    private void enableMic()
    {
        try
        {
            AudioFormat audioformat = new AudioFormat(20000.0F, 16, 1, true, true);
            TargetDataLine targetdataline = AudioSystem.getTargetDataLine(audioformat);
            Info info = new Info(TargetDataLine.class, audioformat);
            this.micLine = (TargetDataLine)AudioSystem.getLine(info);
            this.micLine.open();
            this.micLine.start();
            this.currentAudioFile = this.createNewAudioFile();
            (new GuiOnlineChat.rec()).start();
        }
        catch (Exception var4)
        {
            ;
        }
    }

    private void drawInfo(String title, String message)
    {
        this.draw.drawBox(this.width / 3, this.height / 2 - 20, this.width / 3 * 2, this.height / 2 + 20);
        this.draw.drawCenteredString(Color.cl("c") + title, this.width / 2, this.height / 2 - 14);
        this.draw.drawCenteredString(message, this.width / 2, this.height / 2);
    }

    private void drawMic()
    {
        try
        {
            if (this.micButton == null)
            {
                return;
            }

            if (LabyMod.getInstance().selectedPlayer != null)
            {
                GlStateManager.color(1.0F, 1.0F, 1.0F);
                GL11.glPushMatrix();
                GL11.glScaled(0.92D, 0.92D, 1.1D);
                GL11.glTranslated(14.9D, (double)(this.height / 10 - 3), 0.0D);

                if (this.recording)
                {
                    GL11.glColor4d(2.0D, 0.3D, 0.3D, 1.0D);
                }

                if (!this.micButton.enabled)
                {
                    GL11.glColor4d(1.3D, 1.0D, 1.1D, 0.5D);
                }

                this.mc.getTextureManager().bindTexture(LabyMod.getInstance().texture_mic);
                this.drawTexturedModalRect(166, this.height - 23, 0, 0, 13, 13);
                GL11.glPopMatrix();
            }

            if (this.micLine != null)
            {
                int i = this.calcMicLevel();
                int j = java.awt.Color.GREEN.getRGB();

                if (i > 7)
                {
                    j = java.awt.Color.ORANGE.getRGB();
                }

                if (i > 10)
                {
                    j = java.awt.Color.RED.getRGB();
                }

                DrawUtils drawutils = this.draw;
                DrawUtils.drawRect(164, this.height - 145, 250, this.height - 39, Integer.MIN_VALUE);
                this.draw.drawBox(164, this.height - 145, 176, this.height - 39);
                this.drawGradientRect(165, this.height - 40 - i * 5, 175, this.height - 40, j, java.awt.Color.GREEN.getRGB());
                this.draw.drawString(Color.cl("c") + "Recording.. ", 180.0D, (double)(this.height - 140));
                this.draw.drawString("" + ModGui.truncateTo(((double)this.micLine.getFramePosition() + 0.0D) / (double)this.micLine.getFormat().getFrameRate(), 1) + " sec", 180.0D, (double)(this.height - 140 + 10));

                if (this.micLine.getFramePosition() / 10000 / 2 > 60)
                {
                    this.recording = false;
                }

                if (!this.recording && this.micLine != null)
                {
                    this.micCooldown = System.currentTimeMillis();

                    if (this.micLine.getFramePosition() / 10000 / 2 < 1)
                    {
                        this.micLine.stop();
                        this.micLine.close();

                        if (ConfigManager.settings.chatAlertType)
                        {
                            if (ConfigManager.settings.alertsChat)
                            {
                                LabyMod.getInstance().displayMessageInChat(ClientConnection.chatPrefix + Color.cl("c") + "Recording is too short!");
                            }
                        }
                        else
                        {
                            LabyMod.getInstance().achievementGui.displayBroadcast(BroadcastType.ERROR, "Recording is too short!", EnumAlertType.CHAT);
                        }
                    }
                    else
                    {
                        this.micLine.stop();
                        this.micLine.close();
                        LabyModPlayer labymodplayer = LabyMod.getInstance().selectedPlayer;

                        if (labymodplayer == null)
                        {
                            this.currentAudioFile.delete();
                        }
                    }

                    this.micLine = null;
                    this.currentAudioFile = null;
                }
            }
        }
        catch (Exception var4)
        {
            ;
        }
    }

    private int calcMicLevel()
    {
        (new GuiOnlineChat.level()).start();
        int i = this.micLevel - this.micLastLevel;

        if (i < 0)
        {
            i *= -1;
        }

        if (i > 5)
        {
            ++this.changeCount;
        }

        if (this.micTimer > 3)
        {
            this.micTimer = 0;
            this.result = (double)this.changeCount;
            this.changeCount = 0;
            --this.smoothLevel;
        }

        ++this.micTimer;
        this.micLastLevel = this.micLevel;

        if ((double)this.smoothLevel > this.result * 10.0D)
        {
            --this.smoothLevel;
        }

        if ((double)this.smoothLevel < this.result * 5.0D)
        {
            ++this.smoothLevel;
        }

        if (this.smoothLevel > 18)
        {
            this.smoothLevel = 18;
        }

        return this.smoothLevel;
    }

    private int calculateRMSLevel(byte[] audioData)
    {
        long i = 0L;

        for (int j = 0; j < audioData.length; ++j)
        {
            i += (long)audioData[j];
        }

        double d1 = (double)(i / (long)audioData.length);
        double d0 = 0.0D;

        for (int k = 0; k < audioData.length; ++k)
        {
            d0 += Math.pow((double)audioData[k] - d1, 2.0D);
        }

        double d2 = d0 / (double)audioData.length;
        return (int)(Math.pow(d2, 0.5D) + 0.5D);
    }

    private static File getTimestampedPNGFileForDirectory(File gameDirectory)
    {
        String s = dateFormat.format(new Date()).toString();
        int i = 1;

        while (true)
        {
            File file1 = new File(gameDirectory, s + (i == 1 ? "" : "_" + i) + ".wav");

            if (!file1.exists())
            {
                return file1;
            }

            ++i;
        }
    }

    public File createNewAudioFile()
    {
        File file1 = new File(Source.file_Chatlog + "/media/");

        if (!file1.exists())
        {
            file1.mkdirs();
        }

        return getTimestampedPNGFileForDirectory(file1);
    }

    /**
     * Called when a mouse button is released.  Args : mouseX, mouseY, releaseButton
     */
    protected void mouseReleased(int mouseX, int mouseY, int state)
    {
        this.recording = false;
        this.comRelease(mouseX, mouseY, state);
        super.mouseReleased(mouseX, mouseY, state);
    }

    private void drawChatArea()
    {
        if (LabyMod.getInstance().selectedPlayer != null)
        {
            this.chatArea.drawTextBox();
        }
    }

    /**
     * Called by the controls from the buttonList when activated. (Mouse pressed for buttons)
     */
    public void actionPerformed(GuiButton button) throws IOException
    {
        if (this.switchScreen + 100L <= System.currentTimeMillis())
        {
            if (button.id == 4 && LabyMod.getInstance().lastRecon + 5000L < System.currentTimeMillis())
            {
                button.enabled = false;
                LabyMod.getInstance().selectedPlayer = null;
                LabyMod.getInstance().lastRecon = System.currentTimeMillis();

                if (LabyMod.getInstance().client.getClientConnection().getState() == EnumConnectionState.PLAY)
                {
                    LabyMod.getInstance().getClient().getClientConnection().setIntentionally(true);
                    LabyMod.getInstance().client.disconnect();
                }
                else
                {
                    LabyMod.getInstance().client.connect();
                }

                this.refreshButtons();
            }

            if (button.id == 0 && !this.showSettingsBox)
            {
                this.showSettingsBox = true;
            }

            if (button.id == 1 && !this.showFileSharing)
            {
                this.showFileSharing = true;
                this.showFileSharingButton.enabled = false;
            }

            if (button.id == 3)
            {
                this.sendMessage();
            }

            if (button.id == 2)
            {
                this.recording = true;
                this.enableMic();
            }

            if (button.id == 5)
            {
                SingleChat singlechat = ChatHandler.getHandler().getChat(LabyMod.getInstance().selectedPlayer);
                this.screenSelector = false;
                this.switchScreen = System.currentTimeMillis();
                this.initGui();
            }

            if (button.id == 6)
            {
                this.screenSelector = false;
                this.friendFinder = false;
                this.switchScreen = System.currentTimeMillis();
                this.initGui();
            }

            if (button.id == 7)
            {
                this.openFriendFinder();
            }

            if (button.id == 8)
            {
                this.sendRequest(this.searchFriendsArea.getText());
                this.initGui();
            }

            if (button.id == 9 && LabyMod.getInstance().selectedPlayer != null && LabyMod.getInstance().selectedPlayer instanceof LabyModPlayerRequester)
            {
                LabyMod.getInstance().client.getClientConnection().sendPacket(new PacketPlayDenyFriendRequest((LabyModPlayerRequester)LabyMod.getInstance().selectedPlayer));
                LabyMod.getInstance().selectedPlayer = null;
                this.initGui();
            }

            if (button.id == 10 && LabyMod.getInstance().selectedPlayer != null)
            {
                this.sendRequest(LabyMod.getInstance().selectedPlayer.getName());
                this.stopSpam = true;
                button.enabled = false;
                this.initGui();
            }

            if (button.id == 11)
            {
                ConfigManager.settings.showConntectedIP = !ConfigManager.settings.showConntectedIP;
                LabyMod.getInstance().client.getClientConnection().sendPacket(new PacketPlayChangeOptions(LabyMod.getInstance().getClient().getOptions()));

                if (!ConfigManager.settings.showConntectedIP && LabyMod.getInstance().getClient().getClientConnection().getState() != EnumConnectionState.OFFLINE)
                {
                    LabyMod.getInstance().getClient().getClientConnection().sendPacket(new PacketPlayServerStatus(" ", 0));
                }

                this.initGui();
            }

            if (button.id == 19)
            {
                ConfigManager.settings.ignoreRequests = !ConfigManager.settings.ignoreRequests;
                this.initGui();
            }

            if (button.id == 12)
            {
                this.YesNoBox = false;
                this.initGui();
            }

            if (button.id == 13)
            {
                if (LabyMod.getInstance().client.getClientConnection().getState() == EnumConnectionState.PLAY)
                {
                    if (this.showPlayerSettingsPlayer.isRequest())
                    {
                        LabyMod.getInstance().client.getClientConnection().sendPacket(new PacketPlayDenyFriendRequest((LabyModPlayerRequester)LabyMod.getInstance().selectedPlayer));
                    }
                    else
                    {
                        LabyMod.getInstance().client.getClientConnection().sendPacket(new PacketPlayFriendRemove(this.showPlayerSettingsPlayer));
                    }

                    if (ConfigManager.settings.chatAlertType)
                    {
                        if (ConfigManager.settings.alertsChat)
                        {
                            LabyMod.getInstance().displayMessageInChat(ClientConnection.chatPrefix + Color.cl("e") + L._("", new Object[] {LabyMod.getInstance().selectedPlayer.getName()}));
                        }
                    }
                    else
                    {
                        LabyMod.getInstance().achievementGui.displayBroadcast(BroadcastType.INFO, L._("", new Object[] {LabyMod.getInstance().selectedPlayer.getName()}), EnumAlertType.CHAT);
                    }
                }
                else if (ConfigManager.settings.chatAlertType)
                {
                    if (ConfigManager.settings.alertsChat)
                    {
                        LabyMod.getInstance().displayMessageInChat(ClientConnection.chatPrefix + Color.cl("c") + L._("gui_chat_message_offline", new Object[0]));
                    }
                }
                else
                {
                    LabyMod.getInstance().achievementGui.displayBroadcast(BroadcastType.ERROR, L._("gui_chat_message_offline", new Object[0]), EnumAlertType.CHAT);
                }

                this.YesNoBox = false;
                this.initGui();
            }

            if (button.id == 14)
            {
                ConfigManager.settings.alertsChat = !ConfigManager.settings.alertsChat;
                this.initGui();
            }

            if (button.id == 15)
            {
                ConfigManager.settings.alertsPlayingOn = !ConfigManager.settings.alertsPlayingOn;
                this.initGui();
            }

            if (button.id == 16 && !this.showStatusBox)
            {
                this.showStatusBox = true;
            }

            if (button.id == 17)
            {
                ConfigManager.settings.playSounds = !ConfigManager.settings.playSounds;
                this.initGui();
            }

            if (button.id == 1337)
            {
                ConfigManager.settings.logomode = !ConfigManager.settings.logomode;
                this.initGui();
            }

            super.actionPermformed(button);
        }
    }

    private String getCurrentPlayerName()
    {
        return LabyMod.getInstance().selectedPlayer == null ? "" : LabyMod.getInstance().selectedPlayer.getName();
    }

    private void drawPlayerSettingsBox()
    {
        if (this.showPlayerSettingsBox)
        {
            this.PSSizeX = this.draw.getStringWidth(this.followString(LabyMod.getInstance().selectedPlayer)) + 75;
            this.draw.drawBox(this.showPlayerSettingsX, this.showPlayerSettingsY, this.showPlayerSettingsX + this.PSSizeX, this.showPlayerSettingsY + this.PSSizeY);
            this.draw.drawString("Info", (double)(this.showPlayerSettingsX + 5), (double)(this.showPlayerSettingsY + 5));
            this.draw.drawString(this.followString(LabyMod.getInstance().selectedPlayer), (double)(this.showPlayerSettingsX + 5), (double)(this.showPlayerSettingsY + 17));
            this.draw.drawString(L._("gui_chat_togglenotification", new Object[0]) + " " + this.notifyString(LabyMod.getInstance().selectedPlayer), (double)(this.showPlayerSettingsX + 5), (double)(this.showPlayerSettingsY + 29));
            this.draw.drawString(L._("gui_chat_removecontact", new Object[0]), (double)(this.showPlayerSettingsX + 5), (double)(this.showPlayerSettingsY + 41));
        }
    }

    private String notifyString(LabyModPlayer p)
    {
        String s = Color.cl("c") + "(" + L._("status_disabled", new Object[0]) + ")";

        if (p != null && p.isNotify())
        {
            s = Color.cl("a") + "(" + L._("status_enabled", new Object[0]) + ")";
        }

        return s;
    }

    private String followString(LabyModPlayer p)
    {
        String s = L._("gui_chat_joinserver", new Object[0]) + " " + Color.cl("c") + "(" + L._("gui_chat_notplaying", new Object[0]) + ")";

        if (p != null && !p.getServerInfo().getServerIp().replace(" ", "").isEmpty() && p.isOnline())
        {
            s = L._("gui_chat_joinserver", new Object[0]) + " " + Color.cl("a") + "(" + p.getServerInfo().getServerIp() + ")";

            if (p.getServerInfo().getServerIp().equalsIgnoreCase("Hidden"))
            {
                s = L._("gui_chat_joinserver", new Object[0]) + " " + Color.cl("c") + "(" + L._("gui_chat_serverhidden", new Object[0]) + ")";
            }
        }

        return s;
    }

    private void drawStatusBox()
    {
        if (this.showStatusBox)
        {
            this.draw.drawBox(this.showStatusX, this.showStatusY, this.showStatusX - this.SSSizeX, this.showStatusY + this.SSSizeY);
            this.draw.drawString(Color.cl("a") + this.isStatus(0) + L._("gui_chat_status_online", new Object[0]), (double)(this.showStatusX - this.SSSizeX + 5), (double)(this.showStatusY + 5));
            this.draw.drawString(Color.cl("b") + this.isStatus(1) + L._("gui_chat_status_away", new Object[0]), (double)(this.showStatusX - this.SSSizeX + 5), (double)(this.showStatusY + 17));
            this.draw.drawString(Color.cl("d") + this.isStatus(2) + L._("gui_chat_status_busy", new Object[0]), (double)(this.showStatusX - this.SSSizeX + 5), (double)(this.showStatusY + 29));
        }
    }

    private String isStatus(int i)
    {
        return ChatHandler.playerStatus == i ? "> " + Color.cl("f") : Color.cl("0") + "> " + Color.cl("f");
    }

    private void drawFileSharingBox()
    {
        if (this.showFileSharing)
        {
            this.draw.drawBox(this.showFileSharingX, this.showFileSharingY, this.showFileSharingX + this.FSSizeX, this.showFileSharingY - this.FSSizeY);
            this.draw.drawString("Send screenshot", (double)(this.showFileSharingX + 5), (double)(this.showFileSharingY - this.FSSizeY + 5));
        }
    }

    private void drawSettingsBox()
    {
        if (this.showSettingsBox)
        {
            this.draw.drawBox(this.showSettingsX, this.showSettingsY, this.showSettingsX + this.SBSizeX, this.showSettingsY + this.SBSizeY);
            this.draw.drawString(Color.cl("b") + this.isSettings(0) + L._("gui_chat_filter_all", new Object[0]), (double)(this.showSettingsX + 5), (double)(this.showSettingsY + 5));
            this.draw.drawString(Color.cl("a") + this.isSettings(1) + L._("gui_chat_filter_online", new Object[0]), (double)(this.showSettingsX + 5), (double)(this.showSettingsY + 17));
            this.draw.drawString(Color.cl("e") + this.isSettings(2) + L._("gui_chat_filter_requests", new Object[0]), (double)(this.showSettingsX + 5), (double)(this.showSettingsY + 29));
            this.draw.drawString(Color.cl("6") + this.isSettings(3) + L._("gui_chat_filter_recent", new Object[0]), (double)(this.showSettingsX + 5), (double)(this.showSettingsY + 41));
        }
    }

    private String isSettings(int i)
    {
        return ConfigManager.settings.showSettingsFriend == i ? "> " + Color.cl("f") : Color.cl("0") + "> " + Color.cl("f");
    }

    private boolean playerSettingsBoxMouseClicked(int mouseX, int mouseY, int mouseButton)
    {
        if (!this.showPlayerSettingsBox)
        {
            this.showPlayerSettingsX = mouseX;
            this.showPlayerSettingsY = mouseY;
        }

        if (mouseX > this.showPlayerSettingsX && mouseX < this.showPlayerSettingsX + this.PSSizeX && mouseY > this.showPlayerSettingsY && mouseY < this.showPlayerSettingsY + this.PSSizeY)
        {
            if (mouseY > this.showPlayerSettingsY && mouseY < this.showPlayerSettingsY + 14)
            {
                this.playerInfo = true;
                this.showPlayerSettingsBox = false;
            }

            if (mouseY > this.showPlayerSettingsY + 14 && mouseY < this.showPlayerSettingsY + 29)
            {
                if (LabyMod.getInstance().selectedPlayer != null)
                {
                    if (LabyMod.getInstance().selectedPlayer.isRequest())
                    {
                        if (ConfigManager.settings.chatAlertType)
                        {
                            if (ConfigManager.settings.alertsChat)
                            {
                                LabyMod.getInstance().displayMessageInChat(ClientConnection.chatPrefix + Color.cl("c") + L._("gui_chat_message_nofriends", new Object[] {LabyMod.getInstance().selectedPlayer.getName()}));
                            }
                        }
                        else
                        {
                            LabyMod.getInstance().achievementGui.displayBroadcast(BroadcastType.ERROR, L._("gui_chat_message_nofriends", new Object[] {LabyMod.getInstance().selectedPlayer.getName()}), EnumAlertType.CHAT);
                        }
                    }
                    else if (!LabyMod.getInstance().selectedPlayer.isOnline())
                    {
                        if (ConfigManager.settings.chatAlertType)
                        {
                            if (ConfigManager.settings.alertsChat)
                            {
                                LabyMod.getInstance().displayMessageInChat(ClientConnection.chatPrefix + Color.cl("c") + L._("gui_chat_message_isoffline", new Object[0]));
                            }
                        }
                        else
                        {
                            LabyMod.getInstance().achievementGui.displayBroadcast(BroadcastType.ERROR, L._("gui_chat_message_isoffline", new Object[0]), EnumAlertType.CHAT);
                        }
                    }
                    else if (LabyMod.getInstance().selectedPlayer.getServerInfo().getServerIp() != null && !LabyMod.getInstance().selectedPlayer.getServerInfo().getServerIp().replace(" ", "").isEmpty())
                    {
                        if (LabyMod.getInstance().selectedPlayer.getServerInfo().getServerIp().equalsIgnoreCase("Hidden"))
                        {
                            if (ConfigManager.settings.chatAlertType)
                            {
                                if (ConfigManager.settings.alertsChat)
                                {
                                    LabyMod.getInstance().displayMessageInChat(ClientConnection.chatPrefix + Color.cl("c") + L._("gui_chat_message_cantfollow", new Object[0]));
                                }
                            }
                            else
                            {
                                LabyMod.getInstance().achievementGui.displayBroadcast(BroadcastType.ERROR, L._("gui_chat_message_cantfollow", new Object[0]), EnumAlertType.CHAT);
                            }
                        }
                        else
                        {
                            LabyMod.getInstance().connectToServer(LabyMod.getInstance().selectedPlayer.getServerInfo().getServerIp());
                        }
                    }
                    else if (ConfigManager.settings.chatAlertType)
                    {
                        if (ConfigManager.settings.alertsChat)
                        {
                            LabyMod.getInstance().displayMessageInChat(Color.cl("c") + L._("gui_chat_message_notplaying", new Object[0]));
                        }
                    }
                    else
                    {
                        LabyMod.getInstance().achievementGui.displayBroadcast(BroadcastType.ERROR, L._("gui_chat_message_notplaying", new Object[0]), EnumAlertType.CHAT);
                    }
                }

                this.showPlayerSettingsBox = false;
            }

            if (mouseY > this.showPlayerSettingsY + 29 && mouseY < this.showPlayerSettingsY + 41 && LabyMod.getInstance().selectedPlayer != null)
            {
                LabyMod.getInstance().getClient().setNotifecationStatus(LabyMod.getInstance().selectedPlayer, !LabyMod.getInstance().selectedPlayer.isNotify());
            }

            if (mouseY > this.showPlayerSettingsY + 41 && mouseY < this.showPlayerSettingsY + this.PSSizeY && this.showPlayerSettingsPlayer != null)
            {
                this.YesNoBox = true;
                this.showPlayerSettingsBox = false;
            }

            ConfigManager.save();
            this.initGui();
            return true;
        }
        else
        {
            if (this.showPlayerSettingsBox)
            {
                this.showPlayerSettingsBox = false;
                this.showPlayerSettingsPlayer = null;
            }

            return false;
        }
    }

    private boolean statusBoxMouseClicked(int mouseX, int mouseY, int mouseButton)
    {
        if (!this.showStatusBox)
        {
            this.showStatusX = mouseX;
            this.showStatusY = mouseY;
        }

        if (mouseX < this.showStatusX && mouseX > this.showStatusX - this.SSSizeX && mouseY > this.showStatusY && mouseY < this.showStatusY + this.SSSizeY)
        {
            if (mouseY > this.showStatusY && mouseY < this.showStatusY + 17)
            {
                ChatHandler.setStatus(0);
            }

            if (mouseY > this.showStatusY + 17 && mouseY < this.showStatusY + 29)
            {
                ChatHandler.setStatus(1);
            }

            if (mouseY > this.showStatusY + 29 && mouseY < this.showStatusY + this.SSSizeY)
            {
                ChatHandler.setStatus(2);
            }

            ConfigManager.save();
            this.initGui();
            return true;
        }
        else
        {
            if (this.showStatusBox)
            {
                this.showStatusBox = false;
            }

            return false;
        }
    }

    private boolean settingsBoxMouseClicked(int mouseX, int mouseY, int mouseButton)
    {
        if (!this.showSettingsBox)
        {
            this.showSettingsX = mouseX;
            this.showSettingsY = mouseY;
        }

        if (mouseX > this.showSettingsX && mouseX < this.showSettingsX + this.SBSizeX && mouseY > this.showSettingsY && mouseY < this.showSettingsY + this.SBSizeY)
        {
            if (mouseY > this.showSettingsY && mouseY < this.showSettingsY + 17)
            {
                ConfigManager.settings.showSettingsFriend = 0;
            }

            if (mouseY > this.showSettingsY + 17 && mouseY < this.showSettingsY + 29)
            {
                ConfigManager.settings.showSettingsFriend = 1;
            }

            if (mouseY > this.showSettingsY + 29 && mouseY < this.showSettingsY + 41)
            {
                ConfigManager.settings.showSettingsFriend = 2;
            }

            if (mouseY > this.showSettingsY + 41 && mouseY < this.showSettingsY + this.SBSizeY)
            {
                ConfigManager.settings.showSettingsFriend = 3;
            }

            ConfigManager.save();
            this.initGui();
            return true;
        }
        else
        {
            if (this.showSettingsBox)
            {
                this.showSettingsBox = false;
            }

            return false;
        }
    }

    private boolean fileSharingBoxMouseClicked(int mouseX, int mouseY, int mouseButton)
    {
        if (!this.showFileSharing)
        {
            this.showFileSharingX = mouseX;
            this.showFileSharingY = mouseY;
        }

        if (mouseX > this.showFileSharingX && mouseX < this.showFileSharingX + this.FSSizeX && mouseY < this.showFileSharingY && mouseY > this.showFileSharingY - this.FSSizeY)
        {
            if (mouseY < this.showFileSharingY && mouseY > this.showFileSharingY - 30)
            {
                this.openScreenshotSelector();
            }

            return true;
        }
        else
        {
            if (this.showFileSharing)
            {
                this.showFileSharing = false;
            }

            return false;
        }
    }

    private void openFriendFinder()
    {
        this.searchFriendsArea.setText("");
        this.friendFinder = true;
        this.initGui();
    }

    private void drawFriendFinder()
    {
        this.drawBackground(0);
        this.draw.drawCenteredString(L._("gui_chat_addfriend", new Object[0]), this.width / 2, this.height / 2 - 25);
        this.searchFriendsArea.xPosition = this.width / 2 - 90;
        this.searchFriendsArea.yPosition = this.height / 2 - 10;
        this.searchFriendsArea.drawTextBox();
    }

    private void sendRequest(String playerName)
    {
        LabyMod.getInstance().client.getClientConnection().sendPacket(new PacketPlayRequestAddFriend(playerName));
        this.friendFinder = false;
        this.searchFriendsArea.setText("");
        this.initGui();
    }

    private void openScreenshotSelector()
    {
        File file1 = new File("screenshots");

        if (file1.exists())
        {
            this.screenSelected = null;
            this.screenSelector = true;
            this.refreshButtons();
            File[] afile = file1.listFiles();
            ArrayUtils.reverse((Object[])afile);
            this.screenList = afile;
        }
    }

    private void drawScreenSelector()
    {
        if (this.screenSelector)
        {
            this.drawBackground(0);
            LabyMod.getInstance().draw.drawChatBackground(0, 20, 190, this.height - 30);
            int i = 13;
            int j = 27 + this.scrollScreenBrowser * 20;
            this.endOfScreens = true;

            if (this.screenList != null)
            {
                for (File file1 : this.screenList)
                {
                    if (j > this.width)
                    {
                        this.endOfScreens = false;
                    }
                    else if (!file1.exists())
                    {
                        if (file1 == this.screenSelected)
                        {
                            this.screenSelected = null;
                        }
                    }
                    else if (file1.getName().toLowerCase().endsWith(".png"))
                    {
                        if (file1 == this.screenSelected)
                        {
                            DrawUtils drawutils = LabyMod.getInstance().draw;
                            DrawUtils.drawRect(0, j - 2, i + 176, j + 10, 1023000000);
                            GlStateManager.color(1.0F, 1.0F, 1.0F);
                        }

                        this.mc.getTextureManager().bindTexture(LabyMod.getInstance().texture_img);

                        if (file1.isDirectory())
                        {
                            this.drawTexturedModalRect(1, j - 1, 10, 0, 10, 10);
                        }
                        else
                        {
                            this.drawTexturedModalRect(1, j - 1, 0, 0, 10, 10);
                        }

                        this.draw.drawString(file1.getName(), (double)i, (double)j);
                        j += 12;

                        if (j > this.draw.getHeight() - 40)
                        {
                            this.endOfScreens = false;
                        }
                    }
                }
            }

            int k = this.height - 30;
            LabyMod.getInstance().draw.overlayBackground(k, this.height);
            LabyMod.getInstance().draw.overlayBackground(0, 20);

            if (this.screenSelected != null)
            {
                if (this.screenSelected.isDirectory())
                {
                    this.draw.drawString(Color.cl("c") + "Only PNG files!", 195.0D, 23.0D);
                }
                else
                {
                    GlStateManager.color(1.0F, 1.0F, 1.0F);
                    GL11.glPushMatrix();
                    double d0 = (double)(this.width / 39) * 0.1D;
                    GL11.glScaled(d0, d0, d0);
                    double d1 = 195.0D / d0;
                    double d2 = 35.0D / d0;
                    double d3 = (double)this.width / d0;
                    double d4 = (double)this.height;
                    LabyMod.getInstance().textureManager.drawFileImage(this.screenSelected, d1, d2, d3, d4 / d0);
                    this.draw.drawString("" + this.screenSelected.getName(), (double)((int)(190.0D / d0) + 3), (double)((int)(20.0D / d0)));
                    GL11.glPopMatrix();
                }
            }

            this.draw.drawString("Select screenshot", 5.0D, 7.0D);
            this.draw.drawString(this.getCurrentPlayerName(), (double)(this.width - 10 - this.draw.getStringWidth(this.getCurrentPlayerName())), 7.0D);
        }
    }

    private void screenClick(int mouseX, int mouseY, int mouseButton)
    {
        if (this.screenSelector)
        {
            int i = 13;

            if (mouseX > 13 && mouseX < 190)
            {
                int j = 27 + this.scrollScreenBrowser * 20;

                for (File file1 : this.screenList)
                {
                    if (file1.exists() && file1.getName().toLowerCase().endsWith(".png") && mouseY >= 25 && mouseY <= this.height - 35)
                    {
                        if (mouseY > j && mouseY < j + 12)
                        {
                            this.screenSelected = file1;
                            this.refreshButtons();
                            return;
                        }

                        j += 12;
                    }
                }
            }
        }
    }

    private void finderClick(int mouseX, int mouseY, int mouseButton)
    {
        this.searchFriendsArea.mouseClicked(mouseX, mouseY, mouseButton);
    }

    /**
     * Called when the mouse is clicked. Args : mouseX, mouseY, clickedButton
     */
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException
    {
        if (this.playerInfo)
        {
            this.playerInfo = false;
        }

        if (this.YesNoBox)
        {
            if (mouseX <= this.width / 2 - 100 || mouseX >= this.width / 2 + 100 || mouseY <= this.height / 2 - 50 || mouseY >= this.height / 2 + 50)
            {
                this.YesNoBox = false;
                this.initGui();
            }

            super.mouseClicked(mouseX, mouseY, mouseButton);
        }
        else if (!this.settingsBoxMouseClicked(mouseX, mouseY, mouseButton) && !this.fileSharingBoxMouseClicked(mouseX, mouseY, mouseButton) && !this.playerSettingsBoxMouseClicked(mouseX, mouseY, mouseButton) && !this.statusBoxMouseClicked(mouseX, mouseY, mouseButton))
        {
            List<MessageChatComponent> list = this.getCurrentChatlog();

            if (list != null)
            {
                for (MessageChatComponent messagechatcomponent : list)
                {
                    messagechatcomponent.click(mouseX, mouseY, mouseButton);
                }
            }

            if (this.screenSelector)
            {
                this.screenClick(mouseX, mouseY, mouseButton);
                super.mouseClicked(mouseX, mouseY, mouseButton);
            }
            else if (this.friendFinder)
            {
                this.finderClick(mouseX, mouseY, mouseButton);
                super.mouseClicked(mouseX, mouseY, mouseButton);
            }
            else
            {
                this.searchArea.mouseClicked(mouseX, mouseY, mouseButton);
                this.chatArea.mouseClicked(mouseX, mouseY, mouseButton);

                if (LabyMod.getInstance().selectedPlayer == null)
                {
                    this.motdEditor.mouseClicked(mouseX, mouseY, mouseButton);
                }

                int i = this.listPositionY + 72;

                for (LabyModPlayer labymodplayer : this.renderedPlayers)
                {
                    if (mouseX > 7 && mouseX < 140 && mouseY > i && mouseY < i + 32)
                    {
                        LabyMod.getInstance().selectedPlayer = labymodplayer;
                        this.refreshButtons();
                        this.updateChatlog();
                        this.scrollChatlog = 0;

                        if (mouseButton == 1)
                        {
                            this.showPlayerSettingsX = mouseX;
                            this.showPlayerSettingsY = mouseY;
                            this.showPlayerSettingsPlayer = labymodplayer;
                            this.showPlayerSettingsBox = true;
                        }

                        ChatHandler.resetTyping();
                        break;
                    }

                    i += 36;
                }

                if (mouseX > 7 && mouseX < 140 && mouseY > 25 && mouseY < 57)
                {
                    LabyMod.getInstance().selectedPlayer = null;
                    this.refreshButtons();
                }

                if (mouseX > 145 && LabyMod.getInstance().selectedPlayer != null)
                {
                    this.chatArea.setFocused(true);
                }

                super.mouseClicked(mouseX, mouseY, mouseButton);
            }
        }
    }

    /**
     * Fired when a key is typed (except F11 which toggles full screen). This is the equivalent of
     * KeyListener.keyTyped(KeyEvent e). Args : character (character on the key), keyCode (lwjgl Keyboard key code)
     */
    protected void keyTyped(char typedChar, int keyCode) throws IOException
    {
        if (this.searchArea.textboxKeyTyped(typedChar, keyCode))
        {
            this.scrollFriendList = 0;
        }

        if (this.friendFinder)
        {
            this.searchFriendsArea.textboxKeyTyped(typedChar, keyCode);
        }

        if (this.chatArea.textboxKeyTyped(typedChar, keyCode))
        {
            this.copyLine = 0;
        }

        if (this.motdEditor.textboxKeyTyped(typedChar, keyCode))
        {
            ConfigManager.settings.motd = this.motdEditor.getText();
        }

        if (keyCode == 28 || keyCode == 156)
        {
            if (this.friendFinder)
            {
                if (this.friendSelectButton.enabled)
                {
                    this.actionPerformed(this.friendSelectButton);
                }
            }
            else if (!this.screenSelector && this.sendButton != null && this.sendButton.enabled)
            {
                this.actionPerformed(this.sendButton);
            }
        }

        ArrayList<MessageChatComponent> arraylist = this.filterList(this.chatlogList, LabyMod.getInstance().getPlayerName());

        if (keyCode == 200 && this.copyLine < arraylist.size())
        {
            ++this.copyLine;

            if (this.copyLine <= arraylist.size() && this.copyLine != 0)
            {
                this.chatArea.setText(((MessageChatComponent)arraylist.get(this.copyLine - 1)).getMessage());
            }
        }

        if (keyCode == 208)
        {
            if (this.copyLine > 0)
            {
                --this.copyLine;

                if (this.copyLine <= arraylist.size() && this.copyLine != 0)
                {
                    this.chatArea.setText(((MessageChatComponent)arraylist.get(this.copyLine - 1)).getMessage());
                }
            }

            if (this.copyLine == 0)
            {
                this.chatArea.setText("");
            }
        }

        if (keyCode == 15 && !this.chatArea.getText().isEmpty())
        {
            String[] astring = this.chatArea.getText().split(" ");
            String s = astring[astring.length - 1];

            if (this.chatArea.getText().contains(" "))
            {
                this.complete(astring, LabyMod.getInstance().getPlayerName(), s);

                for (LabyModPlayer labymodplayer : LabyMod.getInstance().client.getFriends())
                {
                    this.complete(astring, labymodplayer.getName(), s);
                }
            }
            else
            {
                this.complete(astring, LabyMod.getInstance().getPlayerName(), this.chatArea.getText());

                for (LabyModPlayer labymodplayer1 : LabyMod.getInstance().client.getFriends())
                {
                    this.complete(astring, labymodplayer1.getName(), this.chatArea.getText());
                }
            }
        }

        super.keyTyped(typedChar, keyCode);
    }

    private void complete(String[] split, String completed, String toComplete)
    {
        if (!toComplete.equals(completed) && completed.startsWith(toComplete))
        {
            String s = "";

            if (split.length > 1)
            {
                for (int i = 0; i <= split.length - 2; ++i)
                {
                    s = s + split[i] + " ";
                }
            }

            this.chatArea.setText(s + completed);
        }
    }

    private ArrayList<MessageChatComponent> filterList(List<MessageChatComponent> chatlogList2, String onlyPlayer)
    {
        ArrayList<MessageChatComponent> arraylist = new ArrayList();

        for (MessageChatComponent messagechatcomponent : chatlogList2)
        {
            if (messagechatcomponent.getSender().equalsIgnoreCase(onlyPlayer))
            {
                arraylist.add(messagechatcomponent);
            }
        }

        return arraylist;
    }

    private void drawSearchArea()
    {
        int i = this.searchArea.xPosition - 1 - 22;
        int j = this.searchArea.yPosition - 7;
        int k = this.searchArea.xPosition + 108;
        LabyMod.getInstance().draw.drawBox(i, j, k, this.searchArea.yPosition + 15);
        this.searchArea.drawTextBox();
    }

    private void drawMotd(String motd, int x, int y)
    {
        int i = 0;
        boolean flag = false;

        for (int j = 0; j <= motd.length() - 1; ++j)
        {
            char c0 = motd.charAt(j);

            if (43 + i > 130 && !flag)
            {
                flag = true;
                i = 0;
            }

            if (!flag)
            {
                this.draw.drawString(Color.cl("o") + c0 + "", (double)(x + i), (double)y);
            }
            else if (43 + i < 130)
            {
                this.draw.drawString(Color.cl("o") + c0 + "", (double)(x + i), (double)(y + 10));
            }

            i += this.draw.getStringWidth(Color.cl("o") + c0 + "");
        }
    }

    private void drawNextEntry(LabyModPlayer p)
    {
        if (this.friendListY + this.scrollFriendList > this.listPositionY + 20)
        {
            if (this.friendListY + this.scrollFriendList < this.height - 90)
            {
                GlStateManager.color(1.0F, 1.0F, 1.0F);

                if (this.getCurrentPlayerName().equals(p.getName()))
                {
                    DrawUtils drawutils = LabyMod.getInstance().draw;
                    DrawUtils.drawRect(7, 26 + this.friendListY + this.scrollFriendList, 137, 26 + this.friendListY + this.scrollFriendList + 34, 1154650990);
                    GL11.glColor3d(1.0D, 1.0D, 1.0D);
                    p.messages = 0;
                }

                if (!p.isOnline())
                {
                    GL11.glColor3d(0.30000001192092896D, 0.30000001192092896D, 0.30000001192092896D);
                }

                LabyMod.getInstance().textureManager.drawPlayerHead(p.getName(), 8.0D, (double)(30 + this.friendListY + this.scrollFriendList), 1.0D);
                String s = "";
                double d0 = 1.0D;

                if (p.messages != 0)
                {
                    if (p.getName().length() > 11)
                    {
                        d0 = 0.88D;
                    }

                    int i = p.messages;

                    if (i > 99)
                    {
                        i = 99;
                    }

                    s = Color.cl("c") + " (" + i + ")";
                }

                GL11.glPushMatrix();
                GL11.glScaled(d0, d0, d0);
                this.draw.drawString(Color.cl("b") + p.getName() + s, (double)((int)(43.0D / d0)), (double)((int)((double)(28 + this.friendListY + this.scrollFriendList) / d0)));
                GL11.glPopMatrix();

                if (p.isRequest())
                {
                    this.draw.drawString(Color.cl("e") + Color.cl("l") + L._("gui_chat_status_request", new Object[0]), 43.0D, (double)(38 + this.friendListY + this.scrollFriendList));
                }
                else
                {
                    this.drawMotd(p.getMotd(), 43, 38 + this.friendListY + this.scrollFriendList);
                }

                this.drawStatusSymbol(p.getStatus(), p.isRequest(), 9, 50 + this.friendListY + this.scrollFriendList);
                ++this.renderedFriends;
                this.renderedPlayers.add(p);
            }
            else
            {
                ++this.failedRender;
            }
        }

        this.friendListY += 36;
    }

    private void drawStatusSymbol(LabyModPlayer.OnlineStatus onlineStatus, boolean isRequest, int x, int y)
    {
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GlStateManager.enableAlpha();
        GlStateManager.enableBlend();
        GlStateManager.color(1.0F, 1.0F, 1.0F);
        String s = Color.cl("f") + Color.cl("l") + "\u2718";
        GL11.glColor4f(1.0F, 0.0F, 0.0F, 0.7F);

        if (onlineStatus == LabyModPlayer.OnlineStatus.ONLINE)
        {
            s = Color.cl("f") + Color.cl("l") + "\u2714";
            GL11.glColor4f(0.0F, 1.0F, 0.0F, 0.7F);
        }

        if (onlineStatus == LabyModPlayer.OnlineStatus.AWAY)
        {
            s = Color.cl("f") + Color.cl("l") + "/";
            GL11.glColor4f(0.0F, 1.0F, 1.0F, 0.7F);
        }

        if (onlineStatus == LabyModPlayer.OnlineStatus.BUSY)
        {
            s = Color.cl("f") + Color.cl("l") + "-";
            GL11.glColor4f(0.8F, 0.3F, 0.9F, 0.9F);
        }

        if (isRequest)
        {
            s = Color.cl("f") + Color.cl("l") + "?";
            GL11.glColor4f(1.0F, 1.0F, 0.0F, 0.7F);
        }

        this.mc.getTextureManager().bindTexture(LabyMod.getInstance().texture_status);
        this.drawTexturedModalRect(x - 1, y - 1, 0, 0, 64, 64);
        GL11.glPushMatrix();
        GL11.glScaled(0.75D, 0.75D, 0.75D);
        this.draw.drawString(s, (double)((int)((double)x * 1.33D) + 3), (double)((int)((double)y * 1.33D) + 3));
        GL11.glPopMatrix();
        GlStateManager.color(1.0F, 1.0F, 1.0F);
    }

    private void drawMyFriends()
    {
        this.friendListY = this.listPositionY + 47;
        this.renderedFriends = 0;
        this.failedRender = 0;
        this.renderedPlayers.clear();
        List<LabyModPlayer> list = new ArrayList();
        list.addAll(ChatHandler.getMyFriends());

        if (list.isEmpty())
        {
            if (LabyMod.getInstance().getClient().getClientConnection().getState() == EnumConnectionState.PLAY)
            {
                this.draw.drawCenteredString(Color.cl("c") + L._("gui_chat_nofriends_title", new Object[0]), 70, this.listPositionY + 75);
                this.draw.drawCenteredString(Color.cl("c") + L._("gui_chat_nofriends_all", new Object[0]), 70, this.listPositionY + 85);
            }
        }
        else
        {
            int i = 0;

            for (LabyModPlayer labymodplayer : list)
            {
                if (!labymodplayer.getName().isEmpty() && labymodplayer.getName().toLowerCase().contains(this.searchArea.getText().replace(" ", "").toLowerCase()) && (ConfigManager.settings.showSettingsFriend != 1 || labymodplayer.isOnline()) && (ConfigManager.settings.showSettingsFriend != 2 || labymodplayer instanceof LabyModPlayerRequester))
                {
                    ++i;
                    this.drawNextEntry(labymodplayer);
                }
            }

            if (i == 0 && !list.isEmpty())
            {
                if (this.searchArea.getText().replace(" ", "").isEmpty())
                {
                    this.draw.drawCenteredString(Color.cl("c") + L._("gui_chat_nofriends_title", new Object[0]), 70, this.listPositionY + 85);
                    this.draw.drawCenteredString(Color.cl("c") + this.trlS(ConfigManager.settings.showSettingsFriend), 70, this.listPositionY + 95);
                }
                else
                {
                    this.draw.drawCenteredString(Color.cl("c") + L._("gui_chat_nofriends_error", new Object[0]), 70, this.listPositionY + 75);
                    this.draw.drawCenteredString(Color.cl("c") + this.searchArea.getText(), 70, this.listPositionY + 85);
                }
            }
        }
    }

    private String trlS(int i)
    {
        return i != 0 && i != 3 ? (i == 1 ? L._("gui_chat_nofriends_online", new Object[0]) : (i == 2 ? L._("gui_chat_nofriends_request", new Object[0]) : null)) : L._("gui_chat_nofriends_all", new Object[0]);
    }

    private void drawMyProfile()
    {
        GL11.glColor3d(1.0D, 1.0D, 1.0D);

        if (LabyMod.getInstance().selectedPlayer == null)
        {
            DrawUtils drawutils = LabyMod.getInstance().draw;
            DrawUtils.drawRect(7, 26, 137, 60, 1154650990);
            GL11.glColor3d(1.0D, 1.0D, 1.0D);
        }

        this.draw.drawString(Color.cl("b") + LabyMod.getInstance().getPlayerName(), 43.0D, 28.0D);
        this.drawMotd(ChatHandler.getInfo(LabyMod.getInstance().getPlayerName()).getMotd(), 43, 38);
        GlStateManager.color(1.0F, 1.0F, 1.0F);
        LabyMod.getInstance().textureManager.drawPlayerHead(LabyMod.getInstance().getPlayerName(), 8.0D, 30.0D, 1.0D);
    }

    private void drawServerStatus()
    {
        ChatHandler.getServerStatus();
    }

    private void drawTitle()
    {
        int i = 0;

        if (LabyMod.getInstance().getClient().getClientConnection().getState() == EnumConnectionState.PLAY)
        {
            i = 30;
        }

        this.draw.drawRightString(this.getConnectionStatus(), (double)(this.width - this.draw.getStringWidth(this.addFriendScreenButton.displayString) - 15 - 6 - i), 10.0D);

        if (LabyMod.getInstance().lastKickReason != null && !LabyMod.getInstance().lastKickReason.isEmpty() && LabyMod.getInstance().getClient().getClientConnection().getState() != EnumConnectionState.PLAY)
        {
            this.draw.drawString("\u00a74" + L._("error_error", new Object[0]) + ": \u00a7c" + LabyMod.getInstance().lastKickReason, 145.0D, (double)(LabyMod.getInstance().draw.getHeight() - 18));
        }
    }

    /**
     * Handles mouse input.
     */
    public void handleMouseInput() throws IOException
    {
        super.handleMouseInput();
        List<MessageChatComponent> list = this.getCurrentChatlog();

        if (list != null)
        {
            for (MessageChatComponent messagechatcomponent : list)
            {
                messagechatcomponent.handleMouseInput();
            }
        }

        int i = Mouse.getEventDWheel();

        if (i != 0)
        {
            if (i > 1)
            {
                i = 1;
            }

            if (i < -1)
            {
                i = -1;
            }

            if (this.screenSelector)
            {
                if (i > 0)
                {
                    if (this.scrollScreenBrowser < 0)
                    {
                        ++this.scrollScreenBrowser;
                    }
                }
                else if (!this.endOfScreens)
                {
                    --this.scrollScreenBrowser;
                }
            }
            else if (!this.friendFinder)
            {
                if (this.cacheMouseX < 140)
                {
                    if (i > 0)
                    {
                        if (this.scrollFriendList < 0)
                        {
                            this.scrollFriendList += 36;
                        }
                    }
                    else if (this.failedRender != 0)
                    {
                        this.scrollFriendList -= 36;
                    }
                }
                else if (LabyMod.getInstance().selectedPlayer != null)
                {
                    if (i > 0)
                    {
                        if (this.chatLastY < 0)
                        {
                            this.scrollChatlog -= 20;
                        }
                    }
                    else if (this.scrollChatlog < 0)
                    {
                        this.scrollChatlog += 20;
                    }
                }
            }
        }
    }

    /**
     * Called when a mouse button is pressed and the mouse is moved around. Parameters are : mouseX, mouseY,
     * lastButtonClicked & timeSinceMouseClick.
     */
    protected void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick)
    {
        List<MessageChatComponent> list = this.getCurrentChatlog();

        if (list != null)
        {
            for (MessageChatComponent messagechatcomponent : list)
            {
                messagechatcomponent.mouseClickMove(mouseX, mouseY, clickedMouseButton, timeSinceLastClick);
            }
        }
    }

    private void comRelease(int mouseX, int mouseY, int state)
    {
        List<MessageChatComponent> list = this.getCurrentChatlog();

        if (list != null)
        {
            for (MessageChatComponent messagechatcomponent : list)
            {
                messagechatcomponent.mouseRelease(mouseX, mouseY, state);
            }
        }
    }

    class level extends Thread
    {
        public void run()
        {
            if (GuiOnlineChat.this.micLine != null)
            {
                byte[] abyte = new byte[GuiOnlineChat.this.micLine.getFormat().getFrameSize()];
                GuiOnlineChat.this.micLine.read(abyte, 0, abyte.length);
                GuiOnlineChat.this.micLevel = GuiOnlineChat.this.calculateRMSLevel(abyte);
            }
        }
    }

    class rec extends Thread
    {
        public void run()
        {
            Type type = Type.WAVE;
            AudioInputStream audioinputstream = new AudioInputStream(GuiOnlineChat.this.micLine);

            try
            {
                AudioSystem.write(audioinputstream, type, GuiOnlineChat.this.currentAudioFile);
            }
            catch (Exception var4)
            {
                ;
            }
        }
    }
}
