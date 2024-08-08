package in.momin5.cookieclient.api.util.utils.client;

import club.minnced.discord.rpc.DiscordEventHandlers;
import club.minnced.discord.rpc.DiscordRPC;
import club.minnced.discord.rpc.DiscordRichPresence;
import in.momin5.cookieclient.CookieClient;
import net.minecraft.client.Minecraft;


public class DiscordPresence {

    static Minecraft mc = Minecraft.getMinecraft();

    private static String id = "794615111771488296";
    private static DiscordRichPresence rpc = new DiscordRichPresence();
    private static DiscordRPC lib = DiscordRPC.INSTANCE;
    private static String steamId = "";

    public static void onEnable(){
        DiscordEventHandlers handlers = new DiscordEventHandlers();
        handlers.ready = (user) -> System.out.println("RPC registered");
        lib.Discord_Initialize(id, handlers, true, steamId);
        rpc.startTimestamp = System.currentTimeMillis() / 1000L;
        rpc.details = CookieClient.MOD_NAME + " b" + CookieClient.MOD_VERSION;
        rpc.largeImageKey = "biscuit";
        rpc.largeImageText = "swag client by Momin";
        rpc.state = "";
        lib.Discord_UpdatePresence(rpc);

        new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    rpc.details = "In Main Menu";

                    if (mc.isIntegratedServerRunning()) {
                        rpc.state = "Playing Singleplayer";
                    } else if (mc.getCurrentServerData() != null) {
                        rpc.state = "Playing " + mc.getCurrentServerData().serverIP.toLowerCase();
                    }
                    rpc.details =  mc.player.getName();
                    // the ask to join button thingy, i tried recreating the c# code in java - momin5
                    rpc.partyId = "mypenisissmall";
                    rpc.partySize = 1;
                    rpc.partyMax = 2;
                    rpc.joinSecret = "MTI4NzM0OjFpMmhuZToxMjMxMjM= ";


                    lib.Discord_UpdatePresence(rpc);
                } catch (Exception penisExceptionLmao) { // i have autism - momin5
                    penisExceptionLmao.printStackTrace();
                }
                try {
                    Thread.sleep(2000L);
                } catch (InterruptedException e2) {
                    e2.printStackTrace();
                }
            }
        }, "RPC-Callback-Handler").start();
    }

    public static void onDisable() {
        lib.Discord_Shutdown();
    }

}