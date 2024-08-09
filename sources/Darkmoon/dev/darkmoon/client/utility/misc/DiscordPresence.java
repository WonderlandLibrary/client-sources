package dev.darkmoon.client.utility.misc;

import dev.darkmoon.client.DarkMoon;
import dev.darkmoon.client.module.impl.combat.KillAura;
import dev.darkmoon.client.ui.menu.altmanager.GuiAltManager;
import dev.darkmoon.client.ui.menu.main.GuiMainMenuElement;
import discordrpc.DiscordEventHandlers;
import discordrpc.DiscordRPC;
import discordrpc.DiscordRichPresence;
import discordrpc.helpers.RPCButton;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.GuiWorldSelection;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.net.URLConnection;

import static dev.darkmoon.client.module.impl.util.DiscordRPC.discordRPC;
import static dev.darkmoon.client.utility.Utility.mc;

public class DiscordPresence {
    private static Thread rpcThread;
    private static final long lastTimeMillis = System.currentTimeMillis();
    public static String avatarUrl;
    public static BufferedImage avatar;
    public static String state;

    public static void startDiscord() {
        DiscordEventHandlers eventHandlers = new DiscordEventHandlers.Builder().ready((user) -> {
            if (user.avatar != null) {
                avatarUrl = "https://cdn.discordapp.com/avatars/" + user.userId + "/" + user.avatar;
                try {
                    URLConnection url = new URL(DiscordPresence.avatarUrl).openConnection();
                    url.setRequestProperty("User-Agent", "Mozilla/5.0");
                    avatar = ImageIO.read(url.getInputStream());
                } catch (Exception ignored) {}
            }
        }).build();
        DiscordRPC.INSTANCE.Discord_Initialize("1185544855343079524", eventHandlers, true, "");

        rpcThread = new Thread(() -> {
            while(true) {
                DiscordRPC.INSTANCE.Discord_RunCallbacks();
                updatePresence();

                try {
                    Thread.sleep(2000L);
                } catch (InterruptedException ignored) {}
            }

        });
        rpcThread.start();
    }

    public static void shutdownDiscord() {
        if (rpcThread != null) {
            rpcThread.interrupt();
            DiscordRPC.INSTANCE.Discord_Shutdown();
        }
    }

    private static void updatePresence() {
        DiscordRichPresence.Builder builder = new DiscordRichPresence.Builder();
        builder.setStartTimestamp(lastTimeMillis / 1000);
        if (mc.currentScreen instanceof GuiMainMenuElement) {
            state = "idling on mainmenu.";
        } else if (mc.currentScreen instanceof GuiMultiplayer) {
            state = "choosing server...";
        } else if (mc.isSingleplayer()) {
            state = "playing on localhost";
        } else if (mc.getCurrentServerData() != null) {
            state = "playing on " + mc.getCurrentServerData().serverIP + (DarkMoon.getInstance().getModuleManager().getModule(KillAura.class).isEnabled() ? ", killing players with aura." : "");
        } else if (mc.currentScreen instanceof GuiOptions) {
            state = "editing settings...";
        } else if (mc.currentScreen instanceof GuiAltManager) {
            state = "choosing account an alt manager.";
        } else if (mc.currentScreen instanceof GuiWorldSelection) {
            state = "choosing world in singleplayer.";
        }
        builder.setState("version: " + DarkMoon.VERSION);
        builder.setDetails(state);
        if (discordRPC.is("Dark")) {
            builder.setLargeImage("dark");
        } else if (discordRPC.is("White")) {
            builder.setLargeImage("white");
        }
        //  builder.setButtons(RPCButton.create("Discord", "https://discord.gg/JzggTUk4tS"));
        DiscordRPC.INSTANCE.Discord_UpdatePresence(builder.build());
    }
}