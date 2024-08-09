package xyz.mathax.mathaxclient;

import xyz.mathax.mathaxclient.addons.AddonManager;
import xyz.mathax.mathaxclient.addons.MatHaxAddon;
import xyz.mathax.mathaxclient.eventbus.EventBus;
import xyz.mathax.mathaxclient.eventbus.EventHandler;
import xyz.mathax.mathaxclient.eventbus.EventPriority;
import xyz.mathax.mathaxclient.eventbus.IEventBus;
import xyz.mathax.mathaxclient.events.game.OpenScreenEvent;
import xyz.mathax.mathaxclient.events.mathax.KeyEvent;
import xyz.mathax.mathaxclient.events.mathax.MouseButtonEvent;
import xyz.mathax.mathaxclient.events.world.TickEvent;
import xyz.mathax.mathaxclient.gui.tabs.builtin.ModulesTab;
import xyz.mathax.mathaxclient.init.PostInit;
import xyz.mathax.mathaxclient.init.PreInit;
import xyz.mathax.mathaxclient.systems.modules.render.Zoom;
import xyz.mathax.mathaxclient.systems.themes.Themes;
import xyz.mathax.mathaxclient.gui.WidgetScreen;
import xyz.mathax.mathaxclient.gui.tabs.Tabs;
import xyz.mathax.mathaxclient.systems.Systems;
import xyz.mathax.mathaxclient.systems.config.Config;
import xyz.mathax.mathaxclient.systems.modules.Modules;
import xyz.mathax.mathaxclient.init.Init;
import xyz.mathax.mathaxclient.utils.Utils;
import xyz.mathax.mathaxclient.utils.input.KeyAction;
import xyz.mathax.mathaxclient.utils.input.KeyBinds;
import xyz.mathax.mathaxclient.utils.misc.MatHaxIdentifier;
import xyz.mathax.mathaxclient.utils.network.DiscordRPC;
import xyz.mathax.mathaxclient.utils.network.api.Api;
import xyz.mathax.mathaxclient.utils.network.irc.Irc;
import xyz.mathax.mathaxclient.utils.network.versions.Versions;
import xyz.mathax.mathaxclient.utils.window.Icon;
import xyz.mathax.mathaxclient.utils.window.Title;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.metadata.ModMetadata;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.util.Formatting;
import org.lwjgl.glfw.GLFW;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.lang.invoke.MethodHandles;
import java.util.*;

// TODO: Make all modules use sections for text rendering
// TODO: If i change how a setting works (from boolean to enum etc), the game will crash with old configs

public class MatHax implements ClientModInitializer {
    public static MinecraftClient mc;
    public static MatHax INSTANCE;
    public static MatHaxAddon ADDON;

    public static final String NAME = "MatHax";
    public static final String ID = NAME.toLowerCase(Locale.ROOT);
    public static final ModMetadata META = FabricLoader.getInstance().getModContainer(ID).get().getMetadata();

    public static final long DISCORD_RPC_ID = 878967665501306920L;

    public static final String URL = "https://" + ID + "client.xyz/";
    public static final String API_URL = URL + "api/";

    public static final File FOLDER = new File(FabricLoader.getInstance().getGameDir().toString(), NAME);
    public static final File VERSION_FOLDER = new File(FOLDER, Versions.getMinecraft());

    public static final IEventBus EVENT_BUS = new EventBus();

    public static final Logger LOG = LoggerFactory.getLogger(NAME);

    private boolean wasWidgetScreen, wasHudHiddenRoot;

    public static List<String> getSplashes() {
        String username = MinecraftClient.getInstance().getSession().getUsername();
        return Arrays.asList(
                // SPLASHES
                Formatting.RED + NAME + " on top!",
                Formatting.RED + "Matejko06" + Formatting.GRAY + " based god",
                Formatting.RED + NAME + "Client.xyz",
                Formatting.RED + NAME + "Client.xyz/Discord",
                Formatting.RED + Versions.getStylized(),
                Formatting.RED + Versions.getMinecraft(),

                // MEME SPLASHES
                Formatting.YELLOW + "cope",
                Formatting.YELLOW + "IntelliJ IDEa",
                Formatting.YELLOW + "I <3 nns",
                Formatting.YELLOW + "haha 69",
                Formatting.YELLOW + "420 XDDDDDD",
                Formatting.YELLOW + "ayy",
                Formatting.YELLOW + "too ez",
                Formatting.YELLOW + "owned",
                Formatting.YELLOW + "your mom :joy:",
                Formatting.YELLOW + "BOOM BOOM BOOM!",
                Formatting.YELLOW + "I <3 forks",
                Formatting.YELLOW + "based",
                Formatting.YELLOW + "Pog",
                Formatting.YELLOW + "Big Rat on top!",
                Formatting.YELLOW + "bigrat.monster",
                Formatting.YELLOW + "Hack on anarchyclef.eyezah.com",
                Formatting.YELLOW + "Hack on 2b2t.org",
                Formatting.YELLOW + "Better Than Wurst",
                Formatting.YELLOW + "Better Than OptiFine",
                Formatting.YELLOW + "Better Than Internet Explorer",
                Formatting.YELLOW + "Wish was better than Technoblade",
                Formatting.YELLOW + "Chad Water",
                Formatting.YELLOW + "L Bozo",

                // PERSONALIZED
                Formatting.YELLOW + "You're cool, " + Formatting.GRAY + username,
                Formatting.YELLOW + "Owning with " + Formatting.GRAY + username,
                Formatting.YELLOW + "Who is " + Formatting.GRAY + username + Formatting.YELLOW + "?",
                Formatting.YELLOW + "Watching hentai with " + Formatting.GRAY + username + Formatting.YELLOW + "!"
        );
    }

    @Override
    public void onInitializeClient() {
        if (INSTANCE == null) {
            INSTANCE = this;
            return;
        }

        long start = Utils.getCurrentTimeMillis();

        // Global minecraft client accessor
        mc = MinecraftClient.getInstance();

        // Start
        LOG.info("Initializing {} {} for Minecraft {} {}...", NAME, Versions.getStylized(), mc.getVersionType(), Versions.getMinecraft());
        Title.setTitle("[Initializing] " + NAME + " " + Versions.getStylized() + " - Minecraft " + mc.getVersionType() + " " + Versions.getMinecraft(), true);
        Icon.setIcon(new MatHaxIdentifier("icons/64.png"), new MatHaxIdentifier("icons/128.png"));

        // Pre-load
        Systems.addPreLoadTask(() -> {
            Api.load();

            if (!VERSION_FOLDER.exists()) {
                VERSION_FOLDER.getParentFile().mkdirs();
                VERSION_FOLDER.mkdir();

                Modules.get().get(Zoom.class).keybind.set(true, GLFW.GLFW_KEY_C);
                Modules.get().get(Zoom.class).toggleOnBindRelease = true;
                Modules.get().get(Zoom.class).chatFeedback = false;
            }
        });

        // Register addons
        AddonManager.init();

        // Register event handlers
        EVENT_BUS.registerLambdaFactory(ADDON.getPackage() , (lookupInMethod, klass) -> (MethodHandles.Lookup) lookupInMethod.invoke(null, klass, MethodHandles.lookup()));
        AddonManager.ADDONS.forEach(addon -> {
            try {
                EVENT_BUS.registerLambdaFactory(addon.getPackage(), (lookupInMethod, klass) -> (MethodHandles.Lookup) lookupInMethod.invoke(null, klass, MethodHandles.lookup()));
            } catch (AbstractMethodError error) {
                throw new RuntimeException("Addon \"%s\" is too old and cannot be ran.".formatted(addon.name), error);
            }
        });

        // Register init classes
        Init.registerPackages(ADDON.getPackage());

        // Pre init
        Init.init(PreInit.class);

        // Subscribe
        EVENT_BUS.subscribe(this);

        // Initialize addons
        AddonManager.ADDONS.forEach(MatHaxAddon::onInitialize);

        // Sort modules after addons have added their own
        Modules.get().sortModules();

        // Load configs
        Systems.load();

        // Post init
        Init.init(PostInit.class);

        // Shutdown hook
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            DiscordRPC.disableRPC();
            //TODO: Leave API and IRC

            if (Irc.client != null) {
                Irc.leave();
            }

            Api.save();
            Systems.save();
        }));

        // End
        LOG.info("{} {} for Minecraft {} {} initialized in {} milliseconds!", NAME, Versions.getStylized(), mc.getVersionType(), Versions.getMinecraft(), Utils.getCurrentTimeMillis() - start);
        Title.setTitle(NAME + " " + Versions.getStylized() + " - Minecraft " + mc.getVersionType() + " " + Versions.getMinecraft(), true);
    }

    @EventHandler
    private void onTick(TickEvent.Post event) {
        if (mc.currentScreen == null && mc.getOverlay() == null && KeyBinds.OPEN_COMMANDS.wasPressed()) {
            mc.setScreen(new ChatScreen(Config.get().prefixSetting.get()));
        }
    }

    @EventHandler
    private void onKey(KeyEvent event) {
        if (event.action == KeyAction.Press && KeyBinds.OPEN_GUI.matchesKey(event.key, 0)) {
            openGui();
        }
    }

    @EventHandler
    private void onMouseButton(MouseButtonEvent event) {
        if (event.action == KeyAction.Press && KeyBinds.OPEN_GUI.matchesMouse(event.button)) {
            openGui();
        }
    }

    private void openGui() {
        if (Utils.canOpenGui()) {
            Tabs.get(ModulesTab.class).openScreen(Systems.get(Themes.class).getTheme());
        }
    }

    // Hide HUD

    @EventHandler(priority = EventPriority.LOWEST)
    private void onOpenScreen(OpenScreenEvent event) {
        boolean hideHud = Systems.get(Themes.class).getTheme().hideHud();

        if (hideHud) {
            if (!wasWidgetScreen) {
                wasHudHiddenRoot = mc.options.hudHidden;
            }

            if (event.screen instanceof WidgetScreen) {
                mc.options.hudHidden = true;
            } else if (!wasHudHiddenRoot) {
                mc.options.hudHidden = false;
            }
        }

        wasWidgetScreen = event.screen instanceof WidgetScreen;
    }
}
