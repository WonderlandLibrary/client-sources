package net.silentclient.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.multiplayer.ServerList;
import net.minecraft.client.resources.data.IMetadataSerializer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Session;
import net.silentclient.client.config.ConfigManager;
import net.silentclient.client.cosmetics.Cosmetics;
import net.silentclient.client.emotes.EmotesMod;
import net.silentclient.client.event.EventManager;
import net.silentclient.client.event.EventTarget;
import net.silentclient.client.event.impl.*;
import net.silentclient.client.gui.GuiError;
import net.silentclient.client.gui.UserTutorial;
import net.silentclient.client.gui.font.SilentFontRenderer;
import net.silentclient.client.gui.lite.GuiNews;
import net.silentclient.client.gui.lite.LiteMainMenu;
import net.silentclient.client.gui.lite.clickgui.ClickGUI;
import net.silentclient.client.gui.modmenu.ModMenu;
import net.silentclient.client.gui.silentmainmenu.MainMenuConcept;
import net.silentclient.client.gui.util.BackgroundPanorama;
import net.silentclient.client.keybinds.KeyBindManager;
import net.silentclient.client.mixin.SilentClientTweaker;
import net.silentclient.client.mixin.accessors.MinecraftAccessor;
import net.silentclient.client.mixin.ducks.AbstractClientPlayerExt;
import net.silentclient.client.mixin.ducks.MinecraftExt;
import net.silentclient.client.mods.ModInstances;
import net.silentclient.client.mods.SettingsManager;
import net.silentclient.client.mods.settings.CosmeticsMod;
import net.silentclient.client.mods.settings.FPSBoostMod;
import net.silentclient.client.mods.settings.GeneralMod;
import net.silentclient.client.mods.util.PingSource;
import net.silentclient.client.mods.util.Server;
import net.silentclient.client.mods.util.Utils;
import net.silentclient.client.nanovg.UI;
import net.silentclient.client.premium.PremiumCosmeticsGui;
import net.silentclient.client.premium.PremiumUtils;
import net.silentclient.client.utils.*;
import net.silentclient.client.utils.animations.AnimationHandler;
import net.silentclient.client.utils.animations.SneakHandler;
import net.silentclient.client.utils.culling.EntityCulling;
import net.silentclient.client.utils.types.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GLContext;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class Client {
    public static final Logger logger = LogManager.getLogger("SC");
    private final String version = "2.2.1";

    private static final Client INSTANCE = new Client();
    public static final Client getInstance() {
        return INSTANCE;
    }

    private BuildData buildData = new BuildData();
    private UserData userData = new UserData();
    public File configDir;
    private SettingsManager settingsManager;
    public ConfigManager configManager;
    private ModInstances modInstances;
    private Gson gson;
    private ScreenshotManager screenshotManager;
    private Cosmetics cosmetics = new Cosmetics();
    private PlayerResponse.Account account;
    private SCTextureManager textureManager;
    private SilentFontRenderer silentFontRenderer;
    private long lastMemoryDebug = System.currentTimeMillis();
    public int ping;
    private static final int PING_INTERVAL = 600;
    private int nextPing;
    private PingSource source = PingSource.AUTO;
    private boolean banerror = false;
    private ResourceLocation bindingTexture = new ResourceLocation("silentclient/binding.png");
    private ArrayList<ServerDataFeature> featuredServers = new ArrayList<ServerDataFeature>();
    private FriendsResponse friends;
    public int playersCount = 0;
    private CPSTracker cpsTracker;
    public static BackgroundPanorama backgroundPanorama;
    private KeyBindManager keyBindManager;
    private IMetadataSerializer iMetadataSerializer;
    private MouseCursorHandler mouseCursorHandler;
    private GlobalSettings globalSettings;
    private File globalSettingsFile;
    private AccountManager accountManager;
    public ServerData lastServerData;
    public TextUtils textUtils;
    private PlayerResponse.BanInfo banInfo;

    public static void memoryDebug(String paramString) {
        LogManager.getLogger().info("-- Start Memory Debug -- " + paramString);
        long l1 = Runtime.getRuntime().maxMemory();
        long l2 = Runtime.getRuntime().totalMemory();
        long l3 = Runtime.getRuntime().freeMemory();
        LogManager.getLogger().info("Max: " + l1 + " (" + (l1 / 1000000.0D) + "MB)");
        LogManager.getLogger().info("Total: " + l2 + " (" + (l2 / 1000000.0D) + "MB)");
        LogManager.getLogger().info("Free: " + l3 + " (" + (l3 / 1000000.0D) + "MB)");
        LogManager.getLogger().info("-- End Memory Debug -- " + paramString);
    }

    public void init() throws IOException {
        try {
            InputStream in = getClass().getResourceAsStream("/build_data.json");
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            StringBuffer content = new StringBuffer();
            String inputLine;
            while ((inputLine = reader.readLine()) != null) {
                content.append(inputLine);
            }
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();

            buildData = gson.fromJson(content.toString(), BuildData.class);
            in.close();
        } catch (Exception e1) {
            Client.logger.catching(e1);
        }
        try {
            InputStream in = new FileInputStream(new File(Minecraft.getMinecraft().mcDataDir, "silent_account.json"));
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            StringBuffer content = new StringBuffer();
            String inputLine;
            while ((inputLine = reader.readLine()) != null) {
                content.append(inputLine);
            }
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();

            userData = gson.fromJson(content.toString(), UserData.class);
            in.close();
            if(!ClientUtils.isDevelopment()) {
                try {
                    new File(Minecraft.getMinecraft().mcDataDir, "silent_account.json").delete();
                } catch (Exception err) {

                }
            }
        } catch (Exception err) {

        }
        logger.info("---------[ Silent Client Initialising ]---------");
        logger.info("MC Version: 1.8.9");
        logger.info("SC Version: " + getFullVersion());
        logger.info("Width: " + Minecraft.getMinecraft().displayWidth);
        logger.info("Height: " + Minecraft.getMinecraft().displayHeight);
        logger.info("Fullscreen: " + Minecraft.getMinecraft().isFullScreen());
        List<GarbageCollectorMXBean> gcMxBeans = ManagementFactory.getGarbageCollectorMXBeans();

        for (GarbageCollectorMXBean gcMxBean : gcMxBeans) {
            logger.info("GC Name: " + gcMxBean.getName());
            logger.info("GC Object Name: " + gcMxBean.getObjectName());
        }
        logger.info("-------------------------------------------------");
        memoryDebug("CLIENT_PRE_INIT");
        if(SilentClientTweaker.hasOptifine) {
            logger.info("INITIALISING > optifine-patch");
            OptifinePatch.init();
        }
        logger.info("INITIALISING > gson-builder");
        this.gson = (new GsonBuilder()).registerTypeAdapterFactory(new EnumAdapterFactory()).setPrettyPrinting()
                .enableComplexMapKeySerialization().create();
        logger.info("INITIALISING > silent-directory");
        configDir = new File(Minecraft.getMinecraft().mcDataDir, "SilentClient-Configs");
        if (!configDir.exists()) {
            configDir.mkdirs();
        }
        globalSettingsFile = new File(Minecraft.getMinecraft().mcDataDir, "silent_settings.json");
        if(!globalSettingsFile.exists()) {
            globalSettingsFile.createNewFile();
        }
        logger.info("INITIALISING > event-manager");
        EventManager.register(this);
        EventManager.register(SneakHandler.getInstance());
        EventManager.register(AnimationHandler.getInstance());
        memoryDebug("CLIENT_POST_INIT");
    }

    public void start() throws Throwable {
        memoryDebug("CLIENT_PRE_START");
        logger.info("---------[ Silent Client Starting ]--------------");
        try {
            logger.info("STARTING > registering-player");
            Players.register();
            logger.info("STARTING > sc-account");
            PlayerResponse acc = updateAccount();

            if(acc != null) {
                Client.getInstance().setAccount(acc.getAccount());
                if(ClientUtils.isDevelopment()) {
                    Session session = new Session(account.original_username, AccountManager.nameToUuid(account.original_username), "0", "legacy");
                    ((MinecraftExt) Minecraft.getMinecraft()).setSession(session);
                }
            }
            logger.info("STARTING > settings-manager");
            settingsManager = new SettingsManager();
            logger.info("STARTING > mod-instances");
            modInstances = new ModInstances();
            logger.info("STARTING > global-settings");
            globalSettings = new GlobalSettings();
            try {
                InputStream in = new FileInputStream(getGlobalSettingsFile());
                BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                StringBuffer content = new StringBuffer();
                String inputLine;
                while ((inputLine = reader.readLine()) != null) {
                    content.append(inputLine);
                }

                globalSettings = gson.fromJson(content.toString(), GlobalSettings.class);

                Client.getInstance().updateWindowTitle();
                in.close();
            } catch (Exception err) {
                Client.logger.catching(err);
            }

            if(globalSettings == null) {
                globalSettings = new GlobalSettings();
            }

            globalSettings.save();
            Client.getInstance().updateWindowTitle();
            if(!globalSettings.configsMigrated && new File(Minecraft.getMinecraft().mcDataDir, "SilentClient").exists() && new File(Minecraft.getMinecraft().mcDataDir, "SilentClient").isDirectory()) {
                logger.info("STARTING > migrating-configs");
                for(String file : new File(Minecraft.getMinecraft().mcDataDir, "SilentClient").list()) {
                    if(!new File(Minecraft.getMinecraft().mcDataDir, "SilentClient/" + file).isDirectory()) {
                        Client.logger.info("STARTING > migrating-configs > " + file);
                        FileUtils.copyFile(new File(Minecraft.getMinecraft().mcDataDir, "SilentClient/" + file), new File(Minecraft.getMinecraft().mcDataDir, "SilentClient-Configs/" + file));
                    }
                }
                globalSettings.configsMigrated = true;
                globalSettings.save();
                new File(Minecraft.getMinecraft().mcDataDir, "SilentClient").delete();
            }
            logger.info("STARTING > config-manager");
            configManager = new ConfigManager();
            if(OSUtil.isWindows()) {
                logger.info("STARTING > raw-mouse-input");
                Minecraft.getMinecraft().mouseHelper = new RawMouseHelper();
                RawInputHandler.init();
            }
            logger.info("STARTING > texture-manager");
            this.textureManager = new SCTextureManager(Minecraft.getMinecraft().getResourceManager());
            logger.info("STARTING > font-renderer");
            this.silentFontRenderer = new SilentFontRenderer();
            logger.info("STARTING > cps-tracker");
            this.cpsTracker = new CPSTracker();
            EventManager.register(cpsTracker);
            logger.info("STARTING > cosmetics");
            cosmetics.init();
            logger.info("STARTING > account-manager");
            accountManager = new AccountManager();
            accountManager.init();
            logger.info("STARTING > entity-culling");
            EventManager.register(new EntityCulling());
            try {
                EntityCulling.SUPPORT_NEW_GL = GLContext.getCapabilities().OpenGL33;
            } catch(Exception err) {
                Client.logger.catching(err);
                EntityCulling.SUPPORT_NEW_GL = false;
            }
            EntityCulling.renderManager = Minecraft.getMinecraft().getRenderManager();
            logger.info("STARTING > screenshot-manager");
            EventManager.register(this.screenshotManager = new ScreenshotManager());
            logger.info("STARTING > binding-textures");
            Minecraft.getMinecraft().getTextureManager().bindTexture(this.getBindingTexture());
            modInstances.getMods().forEach((mod) -> {
                if(mod.getIcon() != null) {
                    Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation(mod.getIcon()));
                }
            });
            logger.info("STARTING > ui");
            UI.init();
            logger.info("STARTING > servers");
            ArrayList<FeaturedServers.FeaturedServerInfo> servers = FeaturedServers.get();
            featuredServers.clear();
            if(servers != null) {
                servers.forEach(server -> {
                    featuredServers.add(new ServerDataFeature(server.getName(), server.getIp()));
                });
            }
            ServerList savedServerList = new ServerList(Minecraft.getMinecraft());
            savedServerList.loadServerList();
            ArrayList<FeaturedServers.FeaturedServerInfo> newSavedFeaturedServers = new ArrayList<>(globalSettings.getSavedFeaturedServers());
            globalSettings.getSavedFeaturedServers().forEach((savedServer) -> {
                if(FeaturedServers.findByIPServerDataFeature(savedServer.getIp(), featuredServers) == null) {
                    Client.logger.info("adding saved featured server to server list: " + savedServer.getIp());
                    savedServerList.addServerData(new ServerData(savedServer.getName(), savedServer.getIp(), false));
                    newSavedFeaturedServers.remove(savedServer);
                }
            });
            globalSettings.setSavedFeaturedServers(newSavedFeaturedServers);
            globalSettings.save();

            savedServerList.saveServerList();

            logger.info("STARTING > friends");
            this.updateFriendsList();

            if(Client.getInstance().getAccount() == null) {
                logger.info("STARTING > ERROR: " + "Authorization Error. Try restarting the game.");
                Minecraft.getMinecraft().displayGuiScreen(new GuiError("Authorization Error. Try restarting the game"));
                return;
            }

            if(banInfo != null && banInfo.banned && !banerror) {
                logger.info("STARTING > ERROR: " + "Account is banned");
                Minecraft.getMinecraft().displayGuiScreen(new GuiError("Your account is banned. Reason: " + banInfo.reason));
                banerror = true;
            }

            Client.logger.info("STARTING > mod-instances-post-init");
            modInstances.postInit();

            Client.logger.info("STARTING > fixing-mods");
            modInstances.getMods().forEach((mod) -> {
                mod.setEnabled(!mod.isEnabled());
                mod.setEnabled(!mod.isEnabled());
            });

            Client.logger.info("STARTING > launching-detector");
            Requests.post("https://api.silentclient.net/_next/launch_v2", new JSONObject().put("branch", getBuildData().getBranch()).toString());

            logger.info("STARTING > promo");
            String panelContent = Requests.get("https://assets.silentclient.net/client/promo.json");
            if(panelContent != null) {
                try {
                    PromoController.PromoResponse promoResponse = getGson().fromJson(panelContent, PromoController.PromoResponse.class);

                    PromoController.setResponse(promoResponse);
                } catch (Exception err) {
                    logger.catching(err);
                }
            }

            logger.info("STARTING > mouse-cursor-handler");
            this.mouseCursorHandler = new MouseCursorHandler();

            if(globalSettings.lite) {
                Minecraft.getMinecraft().displayGuiScreen(new GuiNews());
            } else {
                Minecraft.getMinecraft().displayGuiScreen(new MainMenuConcept());
            }

            if(!Client.getInstance().getAccount().getClaimedPremiumCosmetics()) {
                Client.logger.info("STARTING > premium-cosmetics");
                PremiumCosmeticsResponse premiumCosmetics = PremiumUtils.getPremiumCosmetics();

                if(premiumCosmetics != null) {
                    Minecraft.getMinecraft().displayGuiScreen(new PremiumCosmeticsGui(premiumCosmetics));
                }
            }

            Client.logger.info("STARTING > config-manager-post-init");
            configManager.postInit();

            if(!globalSettings.isResourcePacksFetched()) {
                Client.logger.info("STARTING > resource-packs-fetching");
                if(Minecraft.getMinecraft().getResourcePackRepository().getDirResourcepacks() != null && Minecraft.getMinecraft().getResourcePackRepository().getDirResourcepacks().listFiles() != null) {
                    for (File file : Minecraft.getMinecraft().getResourcePackRepository().getDirResourcepacks().listFiles()) {
                        globalSettings.addToUsedResourcePacks(file.getName());
                    }
                    globalSettings.setResourcePacksFetched(true);
                    globalSettings.save();
                }
            }

            if(!globalSettings.isDisplayedTutorial()) {
                Minecraft.getMinecraft().displayGuiScreen(new UserTutorial());
            }
        } catch(Exception err) {
            Client.logger.catching(err);
            logger.info("STARTING > ERROR: " + err.getMessage());
            throw err;
        }
        Client.logger.info("STARTING > text-utils");
        this.textUtils = new TextUtils(Minecraft.getMinecraft().fontRendererObj);

        logger.info("STARTING > skillissue");
        logger.info("-------------------------------------------------");
        memoryDebug("CLIENT_POST_INIT");
    }

    public void shutdown() {
        logger.info("---------[ Silent Client Stopping ]--------------");
        logger.info("STOPPING > silent-socket");
        Players.unregister();
        logger.info("-------------------------------------------------");
    }

    // utils

    public void updateWindowTitle() {
        Display.setTitle(String.format("Silent Client%s %s (1.8.9)", Client.getInstance().getGlobalSettings() != null && Client.getInstance().getGlobalSettings().isLite() ? " Lite" : "", Client.getInstance().getFullVersion()));
    }

    public void updateUserInformation() {
        if(Client.getInstance().getAccount() != null) {
            (new Thread("updateUserInformation") {
                public void run() {
                    PlayerResponse cosmetics = updateAccount();

                    if(cosmetics != null) {
                        Client.getInstance().setAccount(cosmetics.getAccount());

                        Client.getInstance().getCosmetics().setMyCapes(cosmetics.getAccount().getCosmetics().getCapes());
                        Client.getInstance().getCosmetics().setMyWings(cosmetics.getAccount().getCosmetics().getWings());
                        Client.getInstance().getCosmetics().setMyIcons(cosmetics.getAccount().getCosmetics().getIcons());
                        Client.getInstance().getCosmetics().setMyBandanas(cosmetics.getAccount().getCosmetics().getBandanas());
                        Client.getInstance().getCosmetics().setMyHats(cosmetics.getAccount().getCosmetics().getHats());
                        Client.getInstance().getCosmetics().setMyShields(cosmetics.getAccount().getCosmetics().getShields());
                        Client.getInstance().getCosmetics().setMyEmotes(cosmetics.getAccount().getCosmetics().getEmotes());
                        Client.getInstance().getSettingsManager().getSettingByClass(CosmeticsMod.class, "Cape Shoulders").setValBoolean(cosmetics.getAccount().getCapeShoulders());
                        Client.getInstance().getSettingsManager().getSettingByClass(CosmeticsMod.class, "Cape Type").setValString(cosmetics.getAccount().getCapeType().equals("dynamic_curved") ? "Dynamic Curved" : cosmetics.getAccount().getCapeType().equals("curved_rectangle") ? "Curved Rectangle" : "Rectangle");
                        if(Minecraft.getMinecraft().thePlayer != null) {
                            ((AbstractClientPlayerExt) Minecraft.getMinecraft().thePlayer).silent$setCapeType(cosmetics.getAccount().getCapeType());
                            ((AbstractClientPlayerExt) Minecraft.getMinecraft().thePlayer).silent$setShoulders(cosmetics.getAccount().getCapeShoulders());
                        }
                    }
                }
            }).start();
        }
    }

    public void updateFriendsList() {
        if(Client.getInstance().getAccount() != null) {
            (new Thread("updateFriendsList") {
                public void run() {
                    FriendsResponse friends = getFriendsAPI();

                    if(friends != null) {
                        Client.getInstance().setFriends(friends);
                    }
                }
            }).start();
        }
    }

    private FriendsResponse getFriendsAPI() {
        try {
            URL url = new URL("https://api.silentclient.net/friends");
            HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("User-Agent", "SilentClient");
            con.setRequestProperty("Authorization", "Bearer " + Client.getInstance().getUserData().getAccessToken());

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            Client.logger.info("Loaded friends: " + content.toString());
            in.close();
            con.disconnect();
            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();

            FriendsResponse response = gson.fromJson(content.toString(), FriendsResponse.class);
            return response;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public PlayerResponse updateAccount() {
        try {
            String content = Requests.post("https://api.silentclient.net/account/update", new JSONObject().put("server", Minecraft.getMinecraft().getCurrentServerData() != null ? Minecraft.getMinecraft().getCurrentServerData().serverIP : null).toString());

            GsonBuilder builder = new GsonBuilder();
            Gson gson = builder.create();
            PlayerResponse playerResponse = gson.fromJson(content, PlayerResponse.class);

            this.banInfo = playerResponse.banInfo;

            return playerResponse;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Events
    @EventTarget
    public void onTick(ClientTickEvent event) {
        long l = System.currentTimeMillis();
        if (l - 60000L > this.lastMemoryDebug) {
            this.lastMemoryDebug = l;
            if(getSettingsManager() != null && getSettingsManager().getSettingByClass(FPSBoostMod.class, "Do memory debug").getValBoolean()) {
                memoryDebug("Interval: " + l);
            }
            this.updateUserInformation();
        }
        if(banInfo != null) {
            if(banInfo.banned && !banerror) {
                if(Minecraft.getMinecraft().theWorld != null) {
                    Minecraft.getMinecraft().theWorld.sendQuittingDisconnectingPacket();
                    Minecraft.getMinecraft().loadWorld(null);
                }
                Minecraft.getMinecraft().displayGuiScreen(new GuiError("Your account is banned. Reason: " + banInfo.reason));
                banerror = true;
            }
        }
        if(Client.getInstance().getSettingsManager().getSettingByClass(GeneralMod.class, "Mod Menu Keybind").isKeyDown()) {
            Minecraft.getMinecraft().displayGuiScreen(Client.getInstance().getGlobalSettings().isLite() ? new ClickGUI() : new ModMenu());
        }

        if(source.resolve() != PingSource.MULTIPLAYER_SCREEN) {
            return;
        }

        if(Minecraft.getMinecraft().getCurrentServerData() != null && !Minecraft.getMinecraft().isIntegratedServerRunning()) {
            if(nextPing > 0) {
                nextPing--;
            }
            else if(nextPing > -1) {
                nextPing = -1;

                Utils.MAIN_EXECUTOR.submit(() -> {
                    try {
                        Utils.pingServer(Minecraft.getMinecraft().getCurrentServerData().serverIP, (newPing) -> {
                            if(newPing != -1) {
                                if(ping != 0) {
                                    ping = (ping * 3 + newPing) / 4;
                                }
                                else {
                                    ping = newPing;
                                }
                            }
                        });
                    }
                    catch(UnknownHostException error) {
                        Client.logger.fatal("[SC]: Could not ping server", error);
                    }

                    nextPing = PING_INTERVAL;
                });
            }
        }
    }

    @EventTarget
    public void onMouseClick(EventClickMouse event) {
        if(event.getButton() == 0) {
            ((MinecraftAccessor) Minecraft.getMinecraft()).setLeftClickCounter(0);
        }
    }

    @EventTarget
    public void onServerConnect(ConnectToServerEvent event) {
        ping = 0;
        playersCount = 0;
        nextPing = 10;
        this.updateUserInformation();
        Client.logger.info("Update Connection Server: " + event.getServerData().serverIP);
        Server.setHypixel(Server.checkIsHypixel());
        Server.setRuHypixel(Server.checkIsRuHypixel());
    }

    @EventTarget
    public void onServerDisconnect(ServerLeaveEvent event) {
        ping = 0;
        playersCount = 0;
        nextPing = 10;
        this.updateUserInformation();
        Client.logger.info("Update Connection Server: null");
        Server.setHypixel(Server.checkIsHypixel());
        Server.setRuHypixel(Server.checkIsRuHypixel());
    }

    @EventTarget
    public void onServerDisconnect(SingleplayerJoinEvent event) {
        ping = 0;
        playersCount = 0;
        nextPing = 10;
        this.updateUserInformation();
        Client.logger.info("Update Connection Server: null");
        Server.setHypixel(Server.checkIsHypixel());
        Server.setRuHypixel(Server.checkIsRuHypixel());
    }

    @EventTarget
    public void onClick(KeyEvent event) {
        EmotesMod.onClick(event);
    }

    @EventTarget
    public void onJoinToWorld(EntityJoinLevelEvent event) {
        if(event.getEntity() instanceof EntityPlayer) {
            WorldListener.onPlayerJoin((EntityPlayer) event.getEntity());
        }
    }

    // Instances
    public String getApiUrl() {
        return "http://localhost:" + getUserData().server_port;
    }

    public AccountManager getAccountManager() {
        return accountManager;
    }

    public FriendsResponse getFriends() {
        return friends;
    }

    public void setFriends(FriendsResponse friends) {
        this.friends = friends;
    }

    public UserData getUserData() {
        return userData;
    }

    public BuildData getBuildData() {
        return buildData;
    }

    public String getVersion() {
        return version;
    }

    public String getFullVersion() {
        return "v" + version + "-" + getBuildData().getCommit() + "-" + getBuildData().getBranch();
    }

    public Cosmetics getCosmetics() {
        return cosmetics;
    }

    public PlayerResponse.Account getAccount() {
        return account;
    }

    public void setAccount(PlayerResponse.Account account) {
        this.account = account;
    }

    public ResourceLocation getBindingTexture() {
        return bindingTexture;
    }

    public Gson getGson() {
        return gson;
    }

    public SCTextureManager getTextureManager() {
        return textureManager;
    }

    public SilentFontRenderer getSilentFontRenderer() {
        return silentFontRenderer;
    }

    public SettingsManager getSettingsManager() {
        return settingsManager;
    }

    public ModInstances getModInstances() {
        return modInstances;
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public CPSTracker getCPSTracker() {
        return cpsTracker;
    }

    public int getPing() {
        return ping;
    }

    public ArrayList<ServerDataFeature> getFeaturedServers() {
        return featuredServers;
    }

    public int getPlayersCount() {
        return playersCount;
    }

    public ScreenshotManager getScreenshotManager() {
        return screenshotManager;
    }

    public boolean isTest() {
        return getBuildData().getBranch().equals("TEST2");
    }

    public boolean isDebug() {
        return getBuildData().getBranch().equals("debug");
    }

    public int getScaleFactor() {
        return (new ScaledResolution(Minecraft.getMinecraft())).getScaleFactor();
    }

    public void setKeyBindManager(KeyBindManager keyBindManager) {
        this.keyBindManager = keyBindManager;
    }

    public KeyBindManager getKeyBindManager() {
        return keyBindManager;
    }

    public IMetadataSerializer getiMetadataSerializer() {
        return iMetadataSerializer;
    }

    public void setiMetadataSerializer(IMetadataSerializer iMetadataSerializer) {
        this.iMetadataSerializer = iMetadataSerializer;
    }

    public MouseCursorHandler getMouseCursorHandler() {
        return mouseCursorHandler;
    }

    public GlobalSettings getGlobalSettings() {
        return globalSettings;
    }

    public File getGlobalSettingsFile() {
        return globalSettingsFile;
    }

    public GuiScreen getMainMenu() {
        if(Client.getInstance().getGlobalSettings() == null) {
            return new MainMenuConcept();
        }
        return Client.getInstance().getGlobalSettings().isLite() ? new LiteMainMenu() : new MainMenuConcept();
    }
}
