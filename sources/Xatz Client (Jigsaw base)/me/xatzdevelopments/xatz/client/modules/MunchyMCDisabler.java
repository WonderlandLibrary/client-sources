package me.xatzdevelopments.xatz.client.modules;

import me.xatzdevelopments.xatz.client.darkmagician6.Cancellable;
import me.xatzdevelopments.xatz.client.events.PacEvent;
import me.xatzdevelopments.xatz.client.events.PreMotionEvent;
import me.xatzdevelopments.xatz.client.main.Xatz;
import me.xatzdevelopments.xatz.client.module.state.Category;
import me.xatzdevelopments.xatz.gui.Level;
import me.xatzdevelopments.xatz.gui.Notification;
import me.xatzdevelopments.xatz.module.Module;
import net.minecraft.network.play.client.C00PacketKeepAlive;
import net.minecraft.network.play.client.C0CPacketInput;
import net.minecraft.network.play.client.C18PacketSpectate;
import net.minecraft.network.play.server.S00PacketKeepAlive;

public class MunchyMCDisabler extends Module {

	public MunchyMCDisabler() {
		super("MunchyMCDisabler", 0, Category.HIDDEN, "MunchyMC and Skycade disabler"); // Exploits
	}
	
	public void onEnable() {
		super.onEnable();
	}
	
	public void onDisable() {
		super.onDisable();
	}
	
    @Override
    public void onUpdate() {
    	if (PacEvent.getPacket() instanceof S00PacketKeepAlive || PacEvent.getPacket() instanceof C00PacketKeepAlive) {
    		Cancellable.setCancelled(true);
        }
    } 
}
