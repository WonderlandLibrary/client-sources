package me.xatzdevelopments.modules.player;

import org.lwjgl.input.Keyboard;

import me.xatzdevelopments.Xatz;
import me.xatzdevelopments.events.Event;
import me.xatzdevelopments.events.listeners.EventModeChanged;
import me.xatzdevelopments.events.listeners.EventRenderGUI;
import me.xatzdevelopments.events.listeners.EventUpdate;
import me.xatzdevelopments.modules.Module;
import me.xatzdevelopments.notifications.Notification;
import me.xatzdevelopments.notifications.NotificationManager;
import me.xatzdevelopments.notifications.NotificationType;
import me.xatzdevelopments.rpc.DiscordRichPresenceClass;
import me.xatzdevelopments.settings.ModeSetting;
import me.xatzdevelopments.util.ModulesUtils;
import me.xatzdevelopments.util.Timer;
import net.minecraft.network.play.client.C03PacketPlayer;

public class DiscordRPCModule extends Module {
	public Timer timer = new Timer();
	public ModeSetting discordstyle = new ModeSetting("Style", "Xatz", "Xatz", "BLC", "Lunar", "PvP Lounge", "LabyMod", "Hyperium", "CheatBreaker", "Cosmic Xatz", "Forge");
	public static String applicationid = "";
	public DiscordRPCModule() {
		super("DiscordRPC", Keyboard.KEY_NONE, Category.PLAYER, "Have a cool discord presence");
		this.addSettings(discordstyle);
	}
	
	public void onEnable() {
		DiscordRichPresenceClass.start();
	}
	
	public void onDisable() {
		DiscordRichPresenceClass.stop();
		
	}
	
	public void onEvent(Event e) {
		if(e instanceof EventModeChanged) {
			DiscordRichPresenceClass.stop();
			DiscordRichPresenceClass.start();
		}
	}


	
	
}
