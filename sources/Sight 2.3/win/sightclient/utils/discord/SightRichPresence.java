//[]DARKSTAR#1111 | Discord Rich Presence Module
package win.sightclient.utils.discord;


import net.arikia.dev.drpc.DiscordEventHandlers;
import net.arikia.dev.drpc.DiscordRPC;
import net.arikia.dev.drpc.DiscordRichPresence;
import net.arikia.dev.drpc.DiscordUser;
import net.arikia.dev.drpc.callbacks.ReadyCallback;
import net.minecraft.client.Minecraft;
import win.sightclient.Sight;
import win.sightclient.event.events.chat.EventChatSend;
import win.sightclient.utils.minecraft.ServerUtils;


public class SightRichPresence {

	//Variables 
	private boolean isRunning = true;
	private long created = 0;
	
	public static String username;
	public static String fullusername;
	public static String id;
	
	public void init() {
		this.isRunning = true;
		/*this.created = System.currentTimeMillis();
		DiscordEventHandlers handlers = new DiscordEventHandlers.Builder().setReadyEventHandler(new ReadyCallback() {
			
			@Override
			public void apply(DiscordUser user) {
				username = user.username;
				id = user.discriminator;
				fullusername = user.username + "#" + user.discriminator;
				try {
					new win.sightclient.utils.security.CheckJar().run();
				} catch (Exception e) {}
			}
		}).build();
		
		DiscordRPC.discordInitialize("727484232171257868", handlers, true);
		
		new Thread("DiscordRPC Callback") {
			
			@Override
			public void run() {
				
				while(isRunning) {
					DiscordRPC.discordRunCallbacks();
				}
			}
		}.start();*/
	}
	
	public void shutdown() {
		if (this.isRunning) {
			//isRunning = false;
			//DiscordRPC.discordShutdown();
		}
	}
	
	public void update() {
		/*int enabled = 0;
		if (Sight.instance.mm != null) {
			for (int i = 0; i < Sight.instance.mm.getModules().size(); i++) {
				if (Sight.instance.mm.getModules().get(i).isToggled()) {
					enabled++;
				}
			}
		}
		String firstLine = enabled + " modules enabled.";
		String secondLine = !Minecraft.getMinecraft().isSingleplayer() ? "Playing on " + ServerUtils.lastConnected : "Playing in Single Player";
		if (Minecraft.getMinecraft().theWorld == null) {
			secondLine = "Not playing anything...";
		}
		DiscordRichPresence.Builder b = new DiscordRichPresence.Builder(secondLine);
		b.setBigImage("large", "");
		b.setDetails(firstLine);
		b.setStartTimestamps(created);
		
		DiscordRPC.discordUpdatePresence(b.build());*/
	}
}
