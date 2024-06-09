package ooo.cpacket.ruby.module.move;

import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C07PacketPlayerDigging.Action;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import ooo.cpacket.ruby.api.event.EventImpl;
import ooo.cpacket.ruby.api.event.events.player.EventMotionUpdate;
import ooo.cpacket.ruby.api.event.events.player.motion.State;
import ooo.cpacket.ruby.module.Module;
import ooo.cpacket.ruby.module.attack.KillAura;

public class NoSlow extends Module {

	public NoSlow(String name, int key, Category category) {
		super(name, key, category);
	}

	@Override
	public void onEnable() {

	}

	@Override
	public void onDisable() {

	}

	@EventImpl
	public void onUpdate(EventMotionUpdate e) {
		if (mc.thePlayer.isBlocking() && KillAura.XD == null) {
			if (e.getState() == State.PRE) {
				
			}
			if (e.getState() == State.POST) {
				mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.UP));
			}
		}
	}

}
