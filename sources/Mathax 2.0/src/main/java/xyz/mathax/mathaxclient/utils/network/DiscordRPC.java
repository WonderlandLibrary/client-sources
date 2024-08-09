package xyz.mathax.mathaxclient.utils.network;

import meteordevelopment.discordipc.DiscordIPC;
import meteordevelopment.discordipc.RichPresence;
import net.minecraft.client.gui.screen.*;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import net.minecraft.client.gui.screen.option.*;
import net.minecraft.client.gui.screen.pack.PackScreen;
import net.minecraft.client.gui.screen.world.CreateWorldScreen;
import net.minecraft.client.gui.screen.world.EditGameRulesScreen;
import net.minecraft.client.gui.screen.world.EditWorldScreen;
import net.minecraft.client.gui.screen.world.SelectWorldScreen;
import net.minecraft.client.realms.gui.screen.RealmsScreen;
import net.minecraft.util.Pair;
import xyz.mathax.mathaxclient.MatHax;
import xyz.mathax.mathaxclient.eventbus.EventHandler;
import xyz.mathax.mathaxclient.events.world.TickEvent;
import xyz.mathax.mathaxclient.gui.WidgetScreen;
import xyz.mathax.mathaxclient.init.PreInit;
import xyz.mathax.mathaxclient.systems.config.Config;
import xyz.mathax.mathaxclient.systems.modules.Modules;
import xyz.mathax.mathaxclient.systems.modules.misc.NameProtect;
import xyz.mathax.mathaxclient.utils.Utils;
import xyz.mathax.mathaxclient.utils.network.versions.Versions;
import xyz.mathax.mathaxclient.utils.player.PlayerUtils;

import java.util.ArrayList;
import java.util.List;

import static xyz.mathax.mathaxclient.MatHax.mc;

public class DiscordRPC {
    public static final List<Pair<String, String>> customStates = new ArrayList<>();

    private static final RichPresence rpc = new RichPresence();

    private static int ticks;

    @PreInit
    public static void init() {
        MatHax.EVENT_BUS.subscribe(DiscordRPC.class);

        registerCustomState("com.terraformersmc.modmenu.gui", "Browsing mods");
        registerCustomState("me.jellysquid.mods.sodium.client", "Changing options");
        registerCustomState("de.maxhenkel.voicechat.gui", "Changing voice chat options");
    }

    public static void initRPC() {
        DiscordIPC.start(MatHax.DISCORD_RPC_ID, null);

        rpc.setStart(System.currentTimeMillis() / 1000L);

        ticks = 0;

        update();
    }

    @EventHandler
    private static void onTick(TickEvent.Post event) {
        if (Config.get().discordRPCSetting.get()) {
            if (!DiscordIPC.isConnected()) {
                initRPC();
            } else {
                update();
            }
        }
    }

    private static void update() {
        if (ticks >= Config.get().discordRPCUpdateDelaySetting.get()) {
            rpc.setDetails(getVersions() + (Config.get().discordRPCNameSetting.get() ? " | " + getUsername() : "") + (Config.get().discordRPCHealthSetting.get() && mc.world != null && mc.player != null ? " | " + getHealth() : ""));
            rpc.setLargeImage("logo", "MatHax " + getVersions());
            rpc.setState(getState());

            DiscordIPC.setActivity(rpc);

            ticks = 0;
        } else {
            ticks++;
        }
    }

    private static String getVersions() {
        return Versions.getStylized() + " - " + Versions.getMinecraft();
    }

    private static String getUsername() {
        return Modules.get().get(NameProtect.class).getName();
    }

    private static String getHealth() {
        String text = "";
        if (mc.world != null && mc.player != null) {
            if (mc.player.isDead()) {
                text = "Dead";
            } else if (mc.player.isCreative()) {
                text = "Creative Mode";
            } else if (mc.player.isSpectator()) {
                text = "Spectator Mode";
            } else {
                text = PlayerUtils.getTotalHealth(true) + " HP";
            }
        }

        return text;
    }

    private static String getState() {
        String state = null;
        if (mc.world != null && mc.player != null) {
            state = "Playing" + (Config.get().discordRPCWorldNameSetting.get() ? " on " + Utils.getWorldName() : "") + " (" + (mc.isInSingleplayer() ? "Singleplayer" : "Multiplayer") + ")";
        } else if (mc.getOverlay() instanceof SplashOverlay && mc.world == null && mc.player == null) {
            state = "Loading...";
        } else if (mc.currentScreen instanceof TitleScreen) {
            state = "Looking at title screen";
        } else if (mc.currentScreen instanceof SelectWorldScreen) {
            state = "Selecting world";
        } else if (mc.currentScreen instanceof CreateWorldScreen || mc.currentScreen instanceof EditGameRulesScreen) {
            state = "Creating world";
        } else if (mc.currentScreen instanceof EditWorldScreen) {
            state = "Editing world";
        } else if (mc.currentScreen instanceof LevelLoadingScreen) {
            state = "Loading world";
        } else if (mc.currentScreen instanceof MultiplayerScreen) {
            state = "Selecting server";
        } else if (mc.currentScreen instanceof AddServerScreen) {
            state = "Adding server";
        } else if (mc.currentScreen instanceof ConnectScreen || mc.currentScreen instanceof DirectConnectScreen) {
            state = "Connecting to server";
        } else if (mc.currentScreen instanceof WidgetScreen) {
            state = "Browsing MatHax's GUI";
        } else if (mc.currentScreen instanceof OptionsScreen || mc.currentScreen instanceof SkinOptionsScreen || mc.currentScreen instanceof SoundOptionsScreen || mc.currentScreen instanceof VideoOptionsScreen || mc.currentScreen instanceof ControlsOptionsScreen || mc.currentScreen instanceof LanguageOptionsScreen || mc.currentScreen instanceof ChatOptionsScreen || mc.currentScreen instanceof PackScreen || mc.currentScreen instanceof AccessibilityOptionsScreen) {
            state = "Changing options";
        } else if (mc.currentScreen instanceof CreditsScreen) {
            state = "Reading credits";
        } else if (mc.currentScreen instanceof RealmsScreen) {
            state = "Browsing Realms";
        } else {
            String className = mc.currentScreen.getClass().getName();
            boolean setState = false;
            for (var pair : customStates) {
                if (className.startsWith(pair.getLeft())) {
                    state = pair.getRight();
                    setState = true;
                    break;
                }
            }

            if (!setState) {
                state = "In main menu";
            }
        }

        return state;
    }

    public static void disableRPC() {
        rpc.setState("Shutting down...");

        DiscordIPC.setActivity(rpc);

        DiscordIPC.stop();
    }

    public static void registerCustomState(String packageName, String state) {
        for (var pair : customStates) {
            if (pair.getLeft().equals(packageName)) {
                pair.setRight(state);
                return;
            }
        }

        customStates.add(new Pair<>(packageName, state));
    }

    public static void unregisterCustomState(String packageName) {
        customStates.removeIf(pair -> pair.getLeft().equals(packageName));
    }
}
