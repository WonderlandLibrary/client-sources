package de.labystudio.labymod;

import de.labystudio.capes.CapeManager;
import de.labystudio.chat.ChatHandler;
import de.labystudio.chat.Client;
import de.labystudio.chat.ClientConnection;
import de.labystudio.chat.EnumAlertType;
import de.labystudio.chat.LabyModPlayer;
import de.labystudio.cosmetic.CosmeticManager;
import de.labystudio.cosmetic.CosmeticTick;
import de.labystudio.downloader.ModInfoDownloader;
import de.labystudio.downloader.UserCapesDownloader;
import de.labystudio.downloader.UserCosmeticsDownloader;
import de.labystudio.gui.GuiAchievementMod;
import de.labystudio.hologram.Manager;
import de.labystudio.language.L;
import de.labystudio.listener.Games;
import de.labystudio.listener.GommeHD;
import de.labystudio.listener.HiveMC;
import de.labystudio.listener.JumpLeague;
import de.labystudio.listener.KeyListener;
import de.labystudio.listener.Revayd;
import de.labystudio.listener.Timolia;
import de.labystudio.minecraft.GuiIngameMod;
import de.labystudio.modapi.ModAPI;
import de.labystudio.modapi.ModManager;
import de.labystudio.modapi.events.GameTickEvent;
import de.labystudio.modapi.events.JoinedServerEvent;
import de.labystudio.modapi.events.PluginMessageReceivedEvent;
import de.labystudio.packets.EnumConnectionState;
import de.labystudio.packets.PacketPlayServerStatus;
import de.labystudio.spotify.SpotifyManager;
import de.labystudio.utils.Allowed;
import de.labystudio.utils.AutoTextLoader;
import de.labystudio.utils.Color;
import de.labystudio.utils.ControllerInput;
import de.labystudio.utils.CrashFix;
import de.labystudio.utils.Debug;
import de.labystudio.utils.DrawUtils;
import de.labystudio.utils.FilterLoader;
import de.labystudio.utils.FriendsLoader;
import de.labystudio.utils.LOGO;
import de.labystudio.utils.ModGui;
import de.labystudio.utils.ServerBroadcast;
import de.labystudio.utils.ServiceStatus;
import de.labystudio.utils.StatsLoader;
import de.labystudio.utils.SupportLog;
import de.labystudio.utils.TextureManager;
import de.labystudio.utils.Utils;
import de.zockermaus.ts3.TeamSpeak;
import de.zockermaus.ts3.TeamSpeakController;
import io.netty.buffer.Unpooled;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiIngameMenu;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.multiplayer.GuiConnecting;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.client.C17PacketCustomPayload;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.lang3.SystemUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.Display;

public class LabyMod extends Gui
{
    public Logger logger = LogManager.getLogger();
    public static Random random = new Random();
    public String ip = "";
    public int port = 25565;
    public String gameMode = "";
    public Minecraft mc = null;
    public boolean refresh = false;
    public int scroll = 0;
    public int playerPing = 0;
    public int lavaTime = 0;
    public String nickname = "";
    public IChatComponent footer;
    public IChatComponent header;
    public float foodSaturationLevel = 0.0F;
    public ArrayList<String> onlinePlayerList = new ArrayList();
    public ArrayList<String> gameTypes = new ArrayList();
    public ArrayList<String> serverMSG = new ArrayList();
    public HashMap<String, String> serverPing = new HashMap();
    public ArrayList<String> dumb = new ArrayList();
    public String dumb_str = null;
    public HashMap<String, ServiceStatus> mojangStatus = new HashMap();
    public ArrayList<String> commandQueue = new ArrayList();
    public ArrayList<NetworkPlayerInfo> onlinePlayers = new ArrayList();
    public boolean chat = true;
    public GuiAchievementMod achievementGui;
    public String line1 = "";
    public String line2 = "";
    public int animation = 0;
    public GuiScreen lastScreen;
    public boolean joined = false;
    public boolean intentionally;
    private int min;
    private long secondLoop;
    public int removeChallenge = 0;
    private long lastReport = 0L;
    public boolean out = false;
    public GuiScreen onlineChat;
    public DrawUtils draw;
    public Client client;
    public boolean newMessage = false;
    public long lastRecon = 0L;
    public String lastKickReason = "";
    public LabyModPlayer selectedPlayer = null;
    public ResourceLocation texture_img = new ResourceLocation("img.png");
    public ResourceLocation texture_status = new ResourceLocation("status.png");
    public ResourceLocation texture_mic = new ResourceLocation("mic.png");
    public ResourceLocation texture_box = new ResourceLocation("box.png");
    public ResourceLocation texture_teamSpeak = new ResourceLocation("teamspeak.png");
    public ChatHandler handler;
    public TextureManager textureManager;
    public boolean chatVisibility = false;
    public boolean chatChange = false;
    public GuiScreen openMenu = null;
    public boolean isAFK = false;
    public long lastTimeAFK = 0L;
    public long keepTime = 0L;
    public long lastMove = 0L;
    public int autoUpdaterLatestVersionId = 0;
    public int autoUpdaterCurrentVersionId = 0;
    public String latestVersionName = "?";
    public boolean chatPacketUpdate = false;
    private float partialTicks;
    private ServerBroadcast serverBroadcast;
    private CapeManager capeManager;
    private CosmeticManager cosmeticManager;
    private SpotifyManager spotifyManager;
    public String LIVETICKER = "";
    public boolean supportApply;
    private static LabyMod instance;
    private static boolean overwrite = false;

    public static LabyMod getInstance()
    {
        if (instance == null)
        {
            new LabyMod();
        }

        return instance;
    }

    public static void overWrite()
    {
        if (!overwrite)
        {
            overwrite = true;
            Minecraft.getMinecraft().ingameGUI = new GuiIngameMod(Minecraft.getMinecraft());
        }

        try
        {
            Display.setTitle("Minecraft 1.8.8 | LabyMod 2.7.99");
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }

    public LabyMod()
    {
        instance = this;
        SupportLog.overwrite();
        System.out.println("[LabyMod] Loading labymod..");
        L.load();
        this.mc = Minecraft.getMinecraft();
        this.textureManager = new TextureManager();
        this.draw = new DrawUtils();
        ConfigManager.loadProperties(true);
        this.achievementGui = new GuiAchievementMod(this.mc);
        this.client = new Client();
        this.handler = new ChatHandler();
        this.client.init();
        FriendsLoader.loadFriends();
        FilterLoader.loadFilters();
        AutoTextLoader.load();
        this.capeManager = new CapeManager();
        this.cosmeticManager = new CosmeticManager();

        if (SystemUtils.IS_OS_WINDOWS)
        {
            this.spotifyManager = new SpotifyManager();
        }

        if (ConfigManager.settings != null)
        {
            if (ConfigManager.settings.teamSpeak)
            {
                TeamSpeak.enable();
            }

            Debug.debug("[LabyMod] Download all cape and cosmetic infos..");
            new ModInfoDownloader();
            new UserCosmeticsDownloader();
            new UserCapesDownloader();
            Debug.debug("[LabyMod] Loaded " + this.getCosmeticManager().getOnlineCosmetics().size() + " cosmetics!");
            Debug.debug("[LabyMod] Loaded " + this.getCapeManager().countUserCapes() + " capes!");
            ModManager.loadMods();

            if (!LOGO.isLogo(this.getPlayerName()))
            {
                ConfigManager.settings.logomode = false;
            }

            this.updaterHook();
            StatsLoader.loadstats();

            if (ConfigManager.settings.controller)
            {
                ControllerInput.init();
            }

            System.out.println("[LabyMod] LabyMod Version 2.7.99 for Minecraft 1.8.8 loaded!");
        }
    }

    private void updaterHook()
    {
        Runtime.getRuntime().addShutdownHook(new Thread()
        {
            public void run()
            {
                try
                {
                    CrashFix.fixOptifineCrash();
                    System.out.println("[LabyMod] Checking if you are using an outdated LabyMod version..");

                    if (LabyMod.getInstance().autoUpdaterLatestVersionId > LabyMod.getInstance().autoUpdaterCurrentVersionId)
                    {
                        System.out.println("[LabyMod] You are outdated! You are still on Version v2.7.99, the latest version v" + LabyMod.this.latestVersionName + " will now be installed..");
                        Runtime.getRuntime().exec("java -jar LabyMod/Updater-1.8.8.jar");
                    }
                    else
                    {
                        System.out.println("[LabyMod] You are using the latest LabyMod version v2.7.99");
                    }
                }
                catch (Exception exception)
                {
                    exception.printStackTrace();
                }
            }
        });
    }

    public SpotifyManager getSpotifyManager()
    {
        return this.spotifyManager;
    }

    public CapeManager getCapeManager()
    {
        return this.capeManager;
    }

    public CosmeticManager getCosmeticManager()
    {
        return this.cosmeticManager;
    }

    public Client getClient()
    {
        return this.client;
    }

    public float getPartialTicks()
    {
        return this.partialTicks;
    }

    public void setPartialTicks(float partialTicks)
    {
        this.partialTicks = partialTicks;
    }

    public ServerBroadcast getServerBroadcast()
    {
        return this.serverBroadcast;
    }

    public void setServerBroadcast(ServerBroadcast serverBroadcast)
    {
        this.serverBroadcast = serverBroadcast;
    }

    public boolean isUpdateAvailable()
    {
        return getInstance().autoUpdaterLatestVersionId == 0 ? false : getInstance().autoUpdaterLatestVersionId > getInstance().autoUpdaterCurrentVersionId;
    }

    public void resetIP()
    {
        if ((this.ip == null || !this.ip.replace(" ", "").isEmpty()) && this.client.getClientConnection().getState() == EnumConnectionState.PLAY)
        {
            this.client.getClientConnection().sendPacket(new PacketPlayServerStatus(" ", 0));
        }

        this.ip = "";
        this.gameMode = "";
        this.joined = false;
    }

    public void resetMod()
    {
        this.scroll = 0;
        this.lavaTime = 0;
        this.playerPing = 0;
        this.lavaTime = 0;
        this.nickname = "";
        Manager.holograms.clear();
        ChatHandler.resetTyping();

        if (this.gameMode == null)
        {
            this.gameMode = "";
        }

        if (!this.gameMode.isEmpty())
        {
            this.gameMode = "";
            ChatHandler.updateGameMode("");
        }

        this.header = null;
        this.footer = null;
        JumpLeague.resetJumpLeague();
        GommeHD.resetGommeHD();
        Timolia.resetTimolia();
        ModGui.reset();
        Games.reset();
        Revayd.reset();
        HiveMC.reset();
    }

    public String getHeader()
    {
        return this.header != null && this.header.getUnformattedText() != null ? this.header.getUnformattedText() : "";
    }

    public String getFooter()
    {
        return this.footer != null && this.footer.getUnformattedText() != null ? this.footer.getUnformattedText() : "";
    }

    public void sendCommand(String send)
    {
        if (this.isInGame())
        {
            this.mc.thePlayer.sendChatMessage("/" + send);
        }
    }

    public void sendChatMessage(String message)
    {
        if (this.isInGame())
        {
            this.mc.thePlayer.sendChatMessage(message);
        }
    }

    public void displayMessageInChat(String message)
    {
        if (Minecraft.getMinecraft().ingameGUI != null)
        {
            Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage(new ChatComponentText(message));
        }
    }

    public void sendMessage(String prefix, LabyModPlayer player, String message)
    {
        this.getClient();

        if (!Client.isBusy())
        {
            if (this.client.hasNotifications(player))
            {
                if (ConfigManager.settings.chatAlertType)
                {
                    if (ConfigManager.settings.alertsChat)
                    {
                        getInstance().displayMessageInChat(ClientConnection.chatPrefix + Color.cl("e") + prefix + player.getName() + Color.cl("7") + " " + message);
                    }
                }
                else
                {
                    this.achievementGui.displayMessage(prefix + player.getName(), message, EnumAlertType.CHAT);
                }
            }
        }
    }

    public String getPlayerName()
    {
        return this.mc.getSession().getUsername();
    }

    public UUID getPlayerUUID()
    {
        UUID uuid = this.mc.getSession().getProfile().getId();
        return uuid == null ? UUID.randomUUID() : uuid;
    }

    public boolean isInGame()
    {
        try
        {
            return this.mc != null && this.mc.thePlayer != null && this.mc.thePlayer != null;
        }
        catch (Exception var2)
        {
            return false;
        }
    }

    public boolean isChatGUI()
    {
        return this.mc.currentScreen != null ? this.mc.currentScreen.toString().contains("GuiChat") : false;
    }

    public void gameTick()
    {
        if (ModAPI.enabled())
        {
            ModAPI.callEvent(new GameTickEvent());
        }

        ClickCounter.tick();
        SupportLog.listenKey();
        CosmeticTick.tickAllCosmetics();

        if (ConfigManager.settings.controller)
        {
            ControllerInput.tick();
        }

        ++this.secondLoop;

        if (this.secondLoop >= 20L)
        {
            this.secondLoop = 0L;
            this.secondLoop();
        }
    }

    public void secondLoop()
    {
        ++this.min;

        if (this.getSpotifyManager() != null && ConfigManager.settings.spotfiyTrack)
        {
            this.getSpotifyManager().updateTitle();
        }

        if (this.isInGame())
        {
            this.onlinePlayers.clear();
            this.onlinePlayers.addAll(this.mc.thePlayer.sendQueue.getPlayerInfoMap());

            if (ConfigManager.settings.lavaTime)
            {
                if (this.mc.theWorld.handleMaterialAcceleration(this.mc.thePlayer.getEntityBoundingBox().expand(0.0D, -0.4000000059604645D, 0.0D).contract(0.001D, 0.001D, 0.001D), Material.lava, this.mc.thePlayer) && this.mc.thePlayer.isBurning())
                {
                    ++this.lavaTime;
                    this.removeChallenge = 0;
                }
                else
                {
                    ++this.removeChallenge;

                    if (this.removeChallenge > 2)
                    {
                        this.lavaTime = 0;
                        this.removeChallenge = 0;
                    }
                }
            }
        }
        else
        {
            this.isAFK = false;
        }

        if (this.mc.currentScreen == null)
        {
            ChatHandler.updateIsWriting((LabyModPlayer)null, "");
        }

        if (this.min >= 60)
        {
            this.min = 0;
            this.minutesLoop();
        }

        GommeHD.loop();

        if (ConfigManager.settings.teamSpeak && TeamSpeakController.getInstance() != null)
        {
            TeamSpeakController.getInstance().tick();
        }
    }

    public void minutesLoop()
    {
        if (random.nextInt(6) == 0)
        {
            this.getClient().getClientConnection().reconnect();
        }
    }

    public boolean openWebpage(String urlString, boolean request)
    {
        try
        {
            if (!urlString.toLowerCase().startsWith("https://") && !urlString.toLowerCase().startsWith("http://"))
            {
                urlString = "http://" + urlString;
            }

            Utils.openWebpage((new URL(urlString)).toURI(), request);
            return true;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
            return false;
        }
    }

    public void connectToServer(String address)
    {
        if (Minecraft.getMinecraft().theWorld != null)
        {
            Minecraft.getMinecraft().theWorld.sendQuittingDisconnectingPacket();
            Minecraft.getMinecraft().loadWorld((WorldClient)null);
        }

        Minecraft.getMinecraft().displayGuiScreen(new GuiConnecting(new GuiMainMenu(), this.mc, new ServerData("Server", address, false)));
    }

    public Boolean hasFocus()
    {
        return this.isInGame() ? Boolean.valueOf(this.mc.inGameHasFocus) : Boolean.valueOf(false);
    }

    public ArrayList<String> toLowerCaseList(ArrayList<String> list)
    {
        ArrayList<String> arraylist = new ArrayList();

        for (String s : list)
        {
            arraylist.add(s.toLowerCase());
        }

        return arraylist;
    }

    public void back()
    {
        if (this.isInGame())
        {
            this.mc.displayGuiScreen(new GuiIngameMenu());
        }
        else
        {
            this.mc.displayGuiScreen(new GuiMultiplayer((GuiScreen)null));
        }
    }

    public void updateServerIP(String address, int port)
    {
        this.resetMod();

        if (address == null)
        {
            this.ip = "";
        }
        else
        {
            this.ip = address;
        }

        this.port = port;
        ConfigManager.settings.last_Server = address;
        Allowed.update(address);

        if (this.client.getClientConnection().getState() != EnumConnectionState.OFFLINE)
        {
            this.client.getClientConnection().sendPacket(new PacketPlayServerStatus(address, port));
        }
    }

    public boolean createPath(File file)
    {
        if (file.exists())
        {
            return false;
        }
        else
        {
            file.getParentFile().mkdirs();

            try
            {
                file.createNewFile();
                return true;
            }
            catch (IOException ioexception)
            {
                ioexception.printStackTrace();
                return false;
            }
        }
    }

    public void overlay(int mouseX, int mouseY)
    {
        if (this.achievementGui != null && (!ConfigManager.settings.chatAlertType || !ConfigManager.settings.teamSpeakAlertTypeChat || !ConfigManager.settings.mojangStatusChat))
        {
            this.achievementGui.updateAchievementWindow();
        }

        DrawUtils drawutils = this.draw;
        DrawUtils.updateMouse(mouseX, mouseY);
        KeyListener.handle();
    }

    public void onRender()
    {
        if (getInstance().isInGame())
        {
            JumpLeague.isFallingDown();
            ModGui.smoothFPS();

            if (!this.joined)
            {
                this.joined = true;
                this.onJoin();
            }
        }

        if (ConfigManager.settings.controller)
        {
            ControllerInput.mouseTick();
        }
    }

    public void onJoin()
    {
        if (ModAPI.enabled())
        {
            ModAPI.callEvent(new JoinedServerEvent(this.ip, this.port));
        }

        if (!this.mc.isSingleplayer() && !getInstance().commandQueue.isEmpty())
        {
            getInstance().sendCommand((String)getInstance().commandQueue.get(0));
        }

        PacketBuffer packetbuffer = new PacketBuffer(Unpooled.buffer());
        packetbuffer.writeString("LabyMod v2.7.99");
        this.mc.getNetHandler().addToSendQueue(new C17PacketCustomPayload("LABYMOD", packetbuffer));

        if (this.chatPacketUpdate)
        {
            this.displayMessageInChat(Color.cl("c") + "LabyMod is outdated!" + Color.cl("7") + " Download the latest version " + Color.cl("e") + "v" + this.latestVersionName + Color.cl("7") + " at " + Color.cl("9") + "https://www.LabyMod.net" + "");
        }

        if (this.ip.toLowerCase().contains("bessererange.tk"))
        {
            this.displayMessageInChat(Color.cl("4") + Color.cl("l") + "Du glaubst doch nicht wirklich,");
            this.displayMessageInChat(Color.cl("4") + Color.cl("l") + "dass dir diese IP einen Vorteil bringt, oder?");
        }
    }

    public void pluginMessage(String channel, PacketBuffer data)
    {
        if (ModAPI.enabled())
        {
            ModAPI.callEvent(new PluginMessageReceivedEvent(channel, data));
        }

        try
        {
            if (data != null && channel != null && channel.equals("LABYMOD"))
            {
                ByteArrayInputStream bytearrayinputstream = new ByteArrayInputStream(data.array());
                ObjectInputStream objectinputstream = new ObjectInputStream(bytearrayinputstream);
                Map<String, Boolean> map = (Map)objectinputstream.readObject();

                for (String s : map.keySet())
                {
                    Allowed.set(s, ((Boolean)map.get(s)).booleanValue());
                    Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage(new ChatComponentText(Color.cl("c") + "The " + Color.cl("e") + s + Color.cl("c") + " function was " + ((Boolean)map.get(s)).toString().replace("true", Color.cl("a") + "enabled").replace("false", Color.cl("4") + "disabled") + Color.cl("c") + " by the server."));
                }
            }
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }

    public boolean onSendChatMessage(String msg)
    {
        String s = msg.toLowerCase();

        if (!s.startsWith("/capereport") && !s.startsWith("/reportcape"))
        {
            return true;
        }
        else
        {
            if (msg.contains(" "))
            {
                if (this.lastReport < System.currentTimeMillis())
                {
                    final String s1 = msg.split(" ")[1];
                    (new Thread(new Runnable()
                    {
                        public void run()
                        {
                            try
                            {
                                LabyMod.this.lastReport = System.currentTimeMillis() + 20000L;
                                LabyMod.this.displayMessageInChat(Utils.jsonPost("http://api.labymod.net/php/capeReport.php", "reporter=" + LabyMod.this.getPlayerName() + "&capeowner=" + s1));
                            }
                            catch (Exception exception)
                            {
                                exception.printStackTrace();
                            }
                        }
                    })).start();
                }
                else
                {
                    this.displayMessageInChat(Color.cl("c") + "You\'ve just reported a cape, please wait for a short while..");
                }
            }
            else
            {
                this.displayMessageInChat(Color.cl("c") + msg + " <player>");
            }

            return false;
        }
    }
}
