package me.travis.wurstplus;

import net.minecraft.client.multiplayer.ServerData;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraft.client.Minecraft;

import net.arikia.dev.drpc.DiscordEventHandlers;
import net.arikia.dev.drpc.DiscordRPC;
import net.arikia.dev.drpc.DiscordRichPresence;

public class DiscordPresence
{
    public static Minecraft mc = Minecraft.getMinecraft();
    private static final String APP_ID = "644559686904971305";
    private static DiscordRichPresence presence;
    private static boolean hasStarted;
    public static String details;
    public static String state;
    public static int players;
    public static int maxPlayers;
    public static ServerData svr;
    public static String[] popInfo;
    public static int players2;
    public static int maxPlayers2;
    
    public DiscordPresence() {
        super();
    }
    
    public static boolean start() {
        FMLLog.log.info("Starting Discord RPC");
        if (DiscordPresence.hasStarted) {
            return false;
        }
        
        DiscordPresence.hasStarted = true;
        final DiscordEventHandlers handlers = new DiscordEventHandlers();
        handlers.disconnected = ((var1, var2) -> System.out.println("Discord RPC disconnected, var1: " + String.valueOf(var1) + ", var2: " + var2));
        DiscordRPC.discordInitialize(APP_ID, handlers, true, "");
        DiscordPresence.presence.startTimestamp = System.currentTimeMillis() / 1000L;
        DiscordPresence.presence.details = "Vibin RN, NR";
        DiscordPresence.presence.state = "Wurst+ on top!";
        DiscordPresence.presence.largeImageKey = "pitbull";
        DiscordRPC.discordUpdatePresence(DiscordPresence.presence);
        new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    DiscordRPC.discordRunCallbacks();
                    details = "";
                    state = "";
                    players = 0;
                    maxPlayers = 0;
                    if (mc.isIntegratedServerRunning()) {
                        details = "Singleplayer";
                    }
                    else if (mc.getCurrentServerData() != null) {
                        svr = mc.getCurrentServerData();
                        if (!svr.serverIP.equals("")) {
                            details = "With The Fellas";
                            state = svr.serverIP;
                            if (svr.populationInfo != null) {
                                popInfo = svr.populationInfo.split("/");
                                if (popInfo.length > 2) {
                                    players2 = Integer.valueOf(popInfo[0]);
                                    maxPlayers2 = Integer.valueOf(popInfo[1]);
                                }
                            }
                            if (state.contains("2b2t.org")) {
                                try {
                                    if (wurstplusMod.lastChat.startsWith("Position in queue: ")) {
                                        state = state + " " + Integer.parseInt(wurstplusMod.lastChat.substring(19)) + " in queue";
                                    }
                                }
                                catch (Throwable e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                    else {
                        details = "Vibin RN, NR";
                        state = "Listening to pitbull";
                    }
                    if (!details.equals(DiscordPresence.presence.details) || !state.equals(DiscordPresence.presence.state)) {
                        DiscordPresence.presence.startTimestamp = System.currentTimeMillis() / 1000L;
                    }
                    DiscordPresence.presence.details = details;
                    DiscordPresence.presence.state = state;
                    DiscordRPC.discordUpdatePresence(DiscordPresence.presence);
                }
                catch (Exception e2) {
                    e2.printStackTrace();
                }
                try {
                    Thread.sleep(5000L);
                }
                catch (InterruptedException e3) {
                    e3.printStackTrace();
                }
            }
            return;
        }, "Discord-RPC-Callback-Handler").start();
        FMLLog.log.info("Discord RPC initialised succesfully");
        return true;
    }
    
    private static void lambdastart() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                DiscordRPC.discordRunCallbacks();
                String details = "";
                String state = "";
                int players = 0;
                int maxPlayers = 0;
                if (mc.isIntegratedServerRunning()) {
                    details = "On my Lonesome";
                }
                else if (mc.getCurrentServerData() != null) {
                    final ServerData svr = mc.getCurrentServerData();
                    if (!svr.serverIP.equals("")) {
                        details = "With The Fellas";
                        state = svr.serverIP;
                        if (svr.populationInfo != null) {
                            final String[] popInfo = svr.populationInfo.split("/");
                            if (popInfo.length > 2) {
                                players = Integer.valueOf(popInfo[0]);
                                maxPlayers = Integer.valueOf(popInfo[1]);
                            }
                        }
                        if (state.contains("2b2t.org")) {
                            try {
                                if (wurstplusMod.lastChat.startsWith("Queue simulator: ")) {
                                    state = state + " " + Integer.parseInt(wurstplusMod.lastChat.substring(19)) + " people infront";
                                }
                            }
                            catch (Throwable e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
                else {
                    details = "Vibin RN, NR";
                    state = "Listening to pitbull";
                }
                if (!details.equals(DiscordPresence.presence.details) || !state.equals(DiscordPresence.presence.state)) {
                    DiscordPresence.presence.startTimestamp = System.currentTimeMillis() / 1000L;
                }
                DiscordPresence.presence.details = details;
                DiscordPresence.presence.state = state;
                DiscordRPC.discordUpdatePresence(DiscordPresence.presence);
            }
            catch (Exception e2) {
                e2.printStackTrace();
            }
            try {
                Thread.sleep(5000L);
            }
            catch (InterruptedException e3) {
                e3.printStackTrace();
            }
        }
    }
    
    private static /* synthetic */ void lambdastart(final int var1, final String var2) {
        System.out.println("Discord RPC disconnected, var1: " + String.valueOf(var1) + ", var2: " + var2);
    }

    public static void shutdown() {
        DiscordRPC.discordShutdown();
    }
    
    static {
        DiscordPresence.presence = new DiscordRichPresence();
        DiscordPresence.hasStarted = false;
    }
}
