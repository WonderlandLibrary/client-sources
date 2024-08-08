package me.xatzdevelopments.xatz.client.modules;

import me.xatzdevelopments.xatz.client.events.PreMotionEvent;
import me.xatzdevelopments.xatz.client.main.Xatz;
import me.xatzdevelopments.xatz.client.module.state.Category;
import me.xatzdevelopments.xatz.gui.Level;
import me.xatzdevelopments.xatz.gui.Notification;
import me.xatzdevelopments.xatz.module.Module;
import net.minecraft.network.play.client.C0CPacketInput;
import net.minecraft.network.play.client.C18PacketSpectate;

public class Disabler extends Module {

	public Disabler() {
		super("Disabler", 0, Category.EXPLOITS, "Disables ghostly.live movement checks!");
	}
	
	public void onEnable() {
		Xatz.getNotificationManager().addNotification(new Notification(Level.INFO, 
				"Please relog for the disabler to properly work"));
		super.onEnable();
	}
	
	public void onDisable() {
		super.onDisable();
	}
	
    @Override
    public void onUpdate() {
        mc.getNetHandler().addToSendQueue(new C0CPacketInput());
    } 
}
