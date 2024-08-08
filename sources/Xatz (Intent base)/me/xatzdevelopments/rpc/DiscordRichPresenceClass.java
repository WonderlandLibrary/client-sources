package me.xatzdevelopments.rpc;

import club.minnced.discord.rpc.DiscordEventHandlers;
import club.minnced.discord.rpc.DiscordRPC;
import club.minnced.discord.rpc.DiscordRichPresence;
import club.minnced.discord.rpc.DiscordUser;
import me.xatzdevelopments.Xatz;
import me.xatzdevelopments.modules.player.DiscordRPCModule;

public class DiscordRichPresenceClass {

	public String appid;
	
	
	public static void start() {
	DiscordRPC lib = DiscordRPC.INSTANCE;
    String applicationId = appid();
    //System.out.println(appid());
    String steamId = "";
    DiscordEventHandlers handlers = new DiscordEventHandlers();
    handlers.ready = (user) -> printUsername(user);
    lib.Discord_Initialize(applicationId, handlers, true, steamId);
    DiscordRichPresence presence = new DiscordRichPresence();
    presence.startTimestamp = System.currentTimeMillis() / 1000; // epoch second
    presence.details = "Mining Monero..";
    presence.largeImageKey = "large";
    presence.largeImageText = "Playing " + Xatz.name;
    lib.Discord_UpdatePresence(presence);
    // in a worker thread
    new Thread(() -> {
        while (!Thread.currentThread().isInterrupted()) {
            lib.Discord_RunCallbacks();
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ignored) {}
        }
    }, "RPC-Callback-Handler").start();
}
	
	public static void stop() {
		DiscordRPC lib = DiscordRPC.INSTANCE;
		lib.Discord_Shutdown();
	}
	
	public static void printUsername(DiscordUser user) {
		System.out.println("Welcome " + user.username + "#" + user.discriminator + ".");
	}
	
	public static String appid() {
		switch(Xatz.getModuleByName("DiscordRPC").getModeSetting("Style").getMode().toString()) {
		case "Xatz":
			return "738835303959101572";
		case "BLC":
			return "738835303959101572";
		case "Lunar":
			return "739501420092457040";
		case "PvP Lounge":
			return "739522541583859834";
		case "LabyMod":
			return "739515586161147954";
		case "Hyperium":
			return "739516102249545759";
		case "CheatBreaker":
			return "739516906654138448";
		case "Cosmic Xatz":
			return "739516624217964746";
		case "Forge":
			return "739517851714584666";
		
	    }
		return null;
	}
	
}
