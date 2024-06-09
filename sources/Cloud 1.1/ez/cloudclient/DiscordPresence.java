package ez.cloudclient;

import club.minnced.discord.rpc.DiscordEventHandlers;
import club.minnced.discord.rpc.DiscordRichPresence;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;

public class DiscordPresence {

    private static final club.minnced.discord.rpc.DiscordRPC rpc;
    public static DiscordRichPresence presence;
    private static boolean connected;
    private static String details;
    private static String state;
    private static String gamemode;

    static {
        rpc = club.minnced.discord.rpc.DiscordRPC.INSTANCE;
        DiscordPresence.presence = new DiscordRichPresence();
        DiscordPresence.connected = false;
    }

    public static void start() {
        if (DiscordPresence.connected) return;
        DiscordPresence.connected = true;

        final DiscordEventHandlers handlers = new DiscordEventHandlers();
        DiscordPresence.rpc.Discord_Initialize(CloudClient.APP_ID, handlers, true, "");
        DiscordPresence.presence.startTimestamp = System.currentTimeMillis() / 1000L;

        /* update rpc normally */
        setRpcFromSettings();

        /* update rpc while thread isn't interrupted  */
        new Thread(DiscordPresence::setRpcFromSettingsNonInt, "Discord-RPC-Callback-Handler").start();
    }

    public static void end() {
        DiscordPresence.connected = false;
        DiscordPresence.rpc.Discord_Shutdown();
    }

    private static void setRpcFromSettingsNonInt() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                DiscordPresence.rpc.Discord_RunCallbacks();
                String separator = " | ";
                //First Line of RPC

                if (Minecraft.getMinecraft().world != null && Minecraft.getMinecraft().player != null) {
                    details = CloudClient.VERSION + separator + "Holding " + Minecraft.getMinecraft().player.getHeldItemMainhand().getCount() + "x " + Minecraft.getMinecraft().player.getHeldItemMainhand().getDisplayName();
                }

                //Second Line of RPC
                state = "Loading Minecraft";
                if (Minecraft.getMinecraft().isSingleplayer()) {
                    gamemode = "Singleplayer";
                }
                if (!Minecraft.getMinecraft().isSingleplayer()) {
                    gamemode = "Multiplayer";
                }
                if (Minecraft.getMinecraft().currentScreen instanceof GuiMainMenu) {
                    state = "In Main Menu";
                }
                if (Minecraft.getMinecraft().world != null && Minecraft.getMinecraft().player != null) {
                    if (Minecraft.getMinecraft().isSingleplayer()) {
                        state = "Playing " + gamemode;
                    }
                    if (!Minecraft.getMinecraft().isSingleplayer() && Minecraft.getMinecraft().getCurrentServerData() != null) {
                        state = "Playing " + gamemode;
                    }
                }

                DiscordPresence.presence.details = details;
                DiscordPresence.presence.state = state;
                DiscordPresence.rpc.Discord_UpdatePresence(DiscordPresence.presence);
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            try {
                Thread.sleep(4000L);
            } catch (InterruptedException e3) {
                e3.printStackTrace();
            }
        }
    }

    private static void setRpcFromSettings() {
        DiscordPresence.presence.details = details;
        DiscordPresence.presence.state = state;
        DiscordPresence.presence.largeImageKey = "cloud";
        DiscordPresence.presence.largeImageText = "Cloud Client on Top";
    }
}
