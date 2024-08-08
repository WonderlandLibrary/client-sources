package me.xatzdevelopments.modules.movement;

import me.xatzdevelopments.events.Event;
import me.xatzdevelopments.events.listeners.EventMotion;
import me.xatzdevelopments.events.listeners.EventUpdate;
import me.xatzdevelopments.events.listeners.EventUsingItem;
import me.xatzdevelopments.modules.Module;
import me.xatzdevelopments.settings.ModeSetting;
import me.xatzdevelopments.util.MoveUtils;
import me.xatzdevelopments.util.PlayerUtils;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

public class NoSlow extends Module{
	public ModeSetting mode = new ModeSetting("Mode", "NCP", "NCP", "Redesky");
	public NoSlow() {
		super("NoSlowDown", 0, Category.MOVEMENT, "Doesnt slow your movement");
		this.addSettings(mode);
	}
	
	public void onEvent(Event e) {
		
	        if (e instanceof EventMotion && this.mc.thePlayer.isBlocking() && this.mode.getMode() == "NCP" && MoveUtils.isMoving()) {
	            if (e.isPre()) {
	                this.mc.playerController.syncCurrentPlayItem();
	                this.mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
	            }
	            else {
	                this.mc.playerController.syncCurrentPlayItem();
	                this.mc.thePlayer.sendQueue.addToSendQueue(new C08PacketPlayerBlockPlacement(this.mc.thePlayer.getCurrentEquippedItem()));
	            }
	        }
	        if (e instanceof EventUsingItem && e.isPre() && (this.mode.getMode() == "NCP" || this.mode.getMode() == "Vanilla" || (this.mode.getMode() == "Cubecraft" && this.mc.thePlayer.isBlocking() && PlayerUtils.isHoldingSword()))) {
	            e.setCancelled(true);
	        }
		
		}
	}
