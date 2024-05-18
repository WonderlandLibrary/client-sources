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

public class Sprint extends Module {

	public Sprint(String name, int key, Category category) {
		super(name, key, category);
		this.addBool("Multi", true);
	}

	@Override
	public void onEnable() {

	}

	@Override
	public void onDisable() {

	}

	@EventImpl
	public void onUpdate(EventMotionUpdate e) {
		if (mc.thePlayer.getFoodStats().getFoodLevel() > 9) {
			if ((mc.thePlayer.moveForward != 0 || this.getBool("Multi")) && mc.thePlayer.isMoving()) {
				mc.thePlayer.setSprinting(true);
			}
		}
	}

}
