package xyz.vergoclient.modules.impl.movement;

import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import xyz.vergoclient.Vergo;
import xyz.vergoclient.event.Event;
import xyz.vergoclient.event.impl.EventSendPacket;
import xyz.vergoclient.event.impl.EventSlowdown;
import xyz.vergoclient.modules.Module;
import xyz.vergoclient.modules.OnEventInterface;
import xyz.vergoclient.util.main.MovementUtils;

public class NoSlow extends Module implements OnEventInterface {

	public NoSlow() {
		super("NoSlow", Category.MOVEMENT);
	}

	public static int ticks = 0;

	@Override
	public void onEvent(Event e) {

		if(Vergo.config.modScaffold.isEnabled()) {
			return;
		}

		if(MovementUtils.isMoving()) {

			if (mc.gameSettings.keyBindSprint.isPressed()) {
				mc.thePlayer.setSprinting(true);
			}

			if(e instanceof EventSlowdown) {
				e.setCanceled(true);
			}
		}

		if(Vergo.config.modScaffold.isEnabled()) {
			return;
		}
		if(e instanceof EventSendPacket) {
			EventSendPacket event = (EventSendPacket) e;
			if (mc.thePlayer.isBlocking()) {
				if (event.packet instanceof C08PacketPlayerBlockPlacement) {
					if (mc.thePlayer.ticksExisted % 3 == 0) {
						mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
					} else {
						mc.thePlayer.sendQueue.addToSendQueue(new C08PacketPlayerBlockPlacement(mc.thePlayer.getHeldItem()));
					}
				}
			}
		}
	}

}