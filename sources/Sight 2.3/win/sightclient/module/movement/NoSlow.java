package win.sightclient.module.movement;

import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import win.sightclient.event.Event;
import win.sightclient.event.events.client.EventUpdate;
import win.sightclient.event.events.player.EventSlow;
import win.sightclient.module.Category;
import win.sightclient.module.Module;
import win.sightclient.module.combat.Killaura;
import win.sightclient.module.settings.ModeSetting;
import win.sightclient.utils.minecraft.MoveUtils;

public class NoSlow extends Module {

	private ModeSetting hypixel = new ModeSetting("Mode", this, new String[] {"Hypixel", "Vanilla"});
	
	public NoSlow() {
		super("NoSlow", Category.MOVEMENT);
	}

	@Override
	public void onEvent(Event e) {
		if (e instanceof EventUpdate && hypixel.getValue().equalsIgnoreCase("Hypixel")) {
			EventUpdate eu = (EventUpdate)e;
            if (((mc.thePlayer.isBlocking() && Killaura.target == null) || (!Killaura.isAttackTick && Killaura.target != null && Killaura.canBlock)) && MoveUtils.isMoving()) {
            	if (eu.isPre()) {
            		mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, new BlockPos(-.1F, -.5769F, -.1F), EnumFacing.DOWN));
            	} else if (!eu.isPre()) {
            		mc.getNetHandler().addToSendQueue(new C08PacketPlayerBlockPlacement(mc.thePlayer.getHeldItem()));
            	}
            }
		} else if (e instanceof EventSlow) {
			e.setCancelled();
		}
	}
}
