package club.bluezenith;

import club.bluezenith.command.CommandManager;
import club.bluezenith.core.data.ClientResourceRepository;
import club.bluezenith.core.data.alt.AccountRepository;
import club.bluezenith.core.data.config.ConfigManager;
import club.bluezenith.core.data.config.DropdownSerializer;
import club.bluezenith.core.data.font.FontRepository;
import club.bluezenith.core.data.preferences.PreferencesSerializer;
import club.bluezenith.core.requests.RequestExecutor;
import club.bluezenith.core.user.ClientRank;
import club.bluezenith.core.user.ClientUser;
import club.bluezenith.events.Event;
import club.bluezenith.events.EventManager;
import club.bluezenith.events.Listener;
import club.bluezenith.events.impl.PacketEvent;
import club.bluezenith.events.listeners.HypixelStatMeter;
import club.bluezenith.events.listeners.PlaytimeMeter;
import club.bluezenith.module.Module;
import club.bluezenith.module.ModuleManager;
import club.bluezenith.module.modules.render.ClickGUI;
import club.bluezenith.ui.alt.rewrite.GuiNewAltManager;
import club.bluezenith.ui.clickgui.ClickGui;
import club.bluezenith.ui.draggables.DraggableRenderer;
import club.bluezenith.ui.draggables.IDraggableRenderer;
import club.bluezenith.ui.guis.mainmenu.GuiMainMenu;
import club.bluezenith.ui.notifications.NotificationPublisher;
import club.bluezenith.util.friends.FriendManager;
import club.bluezenith.util.render.GLShader;
import club.bluezenith.util.targets.TargetManager;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.main.Main;
import net.minecraft.entity.Entity;
import net.superblaubeere27.masxinlingvonta.annotation.Outsource;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import security.auth.AuthenticationInfo;
import security.auth.https.TrustedConnector;
import security.auth.hwid.Grabber;
import security.auth.loader.SimpleLoader;
import security.auth.methodlabels.client.Start;
import security.auth.methodlabels.grabber.Hash;
import security.auth.methodlabels.simpleloader.AddPrimaryParams;
import security.auth.methodlabels.simpleloader.SLComputeSelfDigest;
import security.auth.methodlabels.trustedconnector.IsDevEnvironment;
import security.auth.methodlabels.trustedconnector.MakeRequest;
import security.auth.methodlabels.trustedconnector.SetupTrustStore;
import viamcp.ViaMCP;

import java.io.File;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ScheduledExecutorService;
import java.util.function.Consumer;

import static club.bluezenith.module.modules.render.ClickGUI.oldDropdownUI;
import static club.bluezenith.util.client.FileUtil.clientFolder;
import static com.google.common.hash.Hashing.sha256;
import static java.lang.String.valueOf;
import static java.lang.System.exit;
import static java.nio.file.Files.readAllBytes;
import static java.util.Arrays.stream;
import static java.util.concurrent.Executors.newScheduledThreadPool;
import static net.minecraft.client.Minecraft.getMinecraft;
import static org.lwjgl.opengl.Display.setTitle;
import static security.auth.AuthenticationInfo.customMessage;

@SuppressWarnings("all")
public class BlueZenith {
    public static boolean isVirtueTheme = false;
    private static BlueZenith instance;
    public static final ScheduledExecutorService scheduledExecutorService = newScheduledThreadPool(1);
    public static String alert, name = "Blue Zenith", fancyName = "bluezenith.club", version = SimpleLoader.version;
    public static boolean expectedLagback, hasLoadedSuccessfully;
    public boolean ignoreModuleErrors = false;
    private ClientUser clientUser;
    private ClientResourceRepository resourceRepository;
    private FontRepository fontRepository;
    private AccountRepository accountRepository;
    private FriendManager friendManager;
    private TargetManager targetManager;
    private String currentServerIP;
    private ConfigManager configManager;
    private EventManager eventBus;
    private ModuleManager moduleManager;
    private HypixelStatMeter hypixelStatMeter;
    private PlaytimeMeter playtimeMeter;
    private CommandManager commandManager;
    private RequestExecutor requestExecutor;
    private NotificationPublisher notificationPublisher;
    private IDraggableRenderer draggableRenderer;
    private GuiScreen mainMenu;
    private GuiNewAltManager newAltManagerGUI;
    public GLShader backgroundShader;
    public Entity targetHudEntity;

    private List<Consumer<BlueZenith>> startupFinishListeners = new ArrayList<>();

    private BlueZenith() {
        if(instance != null)
            throw new IllegalStateException(this.getClass().getName() + " has already been instantiated!");
        hook();
    }

    @Outsource
    @Start
    public static void startEventManager() { //todo auth code
        instance = new BlueZenith();
        instance.eventBus = new EventManager();
    }

    public GuiNewAltManager getNewAltManagerGUI() {
        return newAltManagerGUI;
    }

    @Outsource
    @Start
    public static long start(int prime) {
        try {
            ViaMCP.getInstance().start();

            final RuntimeMXBean bean = ManagementFactory.getRuntimeMXBean();
            isVirtueTheme = bean.getInputArguments().contains("-Dlongpenis=true");
        } catch (Exception e) {
            e.printStackTrace();
        }

        //todo remove this line lol
        instance.clientUser = new ClientUser("angles", 001, ClientRank.DEVELOPER);

        instance.moduleManager = new ModuleManager();
        instance.commandManager = new CommandManager();
        instance.resourceRepository = new ClientResourceRepository(clientFolder);
        instance.fontRepository = new FontRepository(clientFolder);
        instance.friendManager = new FriendManager();
        instance.targetManager = new TargetManager();
        instance.configManager = new ConfigManager(instance.resourceRepository);
        instance.mainMenu = new GuiMainMenu();
        oldDropdownUI = new ClickGui();
        instance.newAltManagerGUI = new GuiNewAltManager();
        instance.notificationPublisher = new NotificationPublisher();
        instance.draggableRenderer = new DraggableRenderer();
        instance.getEventManager().register(instance);
        instance.requestExecutor = new RequestExecutor();
        instance.accountRepository = new AccountRepository();
        instance.getResourceRepository().hookHandlers(
                instance.configManager,
                new DropdownSerializer(),
                new PreferencesSerializer(),
                instance.accountRepository,
                getBlueZenith().newAltManagerGUI.ipTracker
        );

        setTitle(BlueZenith.fancyName + " | " + SimpleLoader.version);

        if(isVirtueTheme)
            setTitle("Virtue");

        instance.startupFinishListeners.forEach(listener -> listener.accept(instance));
        instance.startupFinishListeners = null;

        hasLoadedSuccessfully = true;
        return 0;
    }

    public static BlueZenith getBlueZenith() {
        return instance;
    }

    public void registerStartupTask(Consumer<BlueZenith> consumer) {
        if(!hasLoadedSuccessfully)
        this.startupFinishListeners.add(consumer);
    }

    public ClientUser getClientUser() {
        return clientUser;
    }

    public IDraggableRenderer getDraggableRenderer() {
        return this.draggableRenderer;
    }

    public NotificationPublisher getNotificationPublisher() {
        return this.notificationPublisher;
    }

    public ModuleManager getModuleManager() {
        return moduleManager;
    }

    public CommandManager getCommandManager() {
        return commandManager;
    }

    public HypixelStatMeter getHypixelStatMeter() { return hypixelStatMeter; }

    public RequestExecutor getRequestExecutor() { return requestExecutor; }
    
    public String getCurrentServerIP() {
        return currentServerIP;
    }
    
    public String version() {
        return version;
    }
    
    public String getName() {
        return name;
    }

    @Outsource
    public void onReload() {
        getEventManager().unregisterAll();
        this.moduleManager.getModules().clear();
        this.moduleManager = null;
        instance.moduleManager = new ModuleManager();
    }

    private void hook() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            if(!hasLoadedSuccessfully) {
                System.err.println("[" + name + "]: Seems like the client has crashed before startup. Not saving any data!");
                return;
            }
            moduleManager.getModule(ClickGUI.class).setState(false);
            getEventManager().unregisterAll();
            getResourceRepository().onShutdown();
        }));
    }

    public GuiScreen getMainMenu() {
        return mainMenu;
    }

    public ClientResourceRepository getResourceRepository() {
        return this.resourceRepository;
    }

    public AccountRepository getAccountRepository() {
        return this.accountRepository;
    }

    public FontRepository getFontRepository() { return this.fontRepository; }

    public FriendManager getFriendManager() { return this.friendManager; }

    public TargetManager getTargetManager() { return this.targetManager; }

    public ConfigManager getConfigManager() { return configManager; }

    public void setCurrentServerIP(String serverIP) {
        this.currentServerIP = serverIP;
    }

    public void setHypixelStatMeter(HypixelStatMeter statMeter) { this.hypixelStatMeter = statMeter; }

    public void setPlaytimeMeter(PlaytimeMeter meter) {
        this.playtimeMeter = meter;
    }

    public PlaytimeMeter getPlaytimeMeter() {
        return this.playtimeMeter;
    }

    public void postEvent(Event event) {
        eventBus.invoke(event);
    }

    public void register(Object listener) {
        getEventManager().register(listener);
    }

    public void unregister(Object listener) {
        getEventManager().unregister(listener);
    }

    public EventManager getEventManager() {
        return eventBus;
    }



    public static void info(String info) {
        log(info);
    }

    private static void info(String info, Object... objects) {
        log(String.format(info, objects));
    }

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    private static void log(String f) {
        System.out.println(String.format("[%s] #### %s %s #### » %s", dateFormat.format(new Date()), name, version, f));
    }

    @Outsource
    public Object getAccountEncryptionKey(long accessCode) { //this is pointless i know
        return "aSBsb3ZlIHdoZW4gaGFpcnkgbWVuIGNvdmVyIHRoZW1zZWx2ZXMgaW4gb2ls";
    }

    @Listener
    public void update(PacketEvent event) {
        Module.player = getMinecraft().thePlayer;
        Module.world = getMinecraft().theWorld;
    }
}
