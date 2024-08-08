package me.xatzdevelopments.modules.player;

import org.lwjgl.input.Keyboard;

import me.xatzdevelopments.Xatz;
import me.xatzdevelopments.events.Event;
import me.xatzdevelopments.events.listeners.EventRenderGUI;
import me.xatzdevelopments.events.listeners.EventUpdate;
import me.xatzdevelopments.modules.Module;
import me.xatzdevelopments.notifications.Notification;
import me.xatzdevelopments.notifications.NotificationManager;
import me.xatzdevelopments.notifications.NotificationType;
import me.xatzdevelopments.util.ModulesUtils;
import net.minecraft.network.play.client.C03PacketPlayer;

public class FastEat extends Module {
	
	public FastEat() {
		super("FastEat", Keyboard.KEY_NONE, Category.PLAYER, "Eat Items Fast");
	}
	
	public void onEvent(Event e) {
		if(e instanceof EventUpdate && e.isPre()) {
			
			
		}
		
		
	}


	
	
}
