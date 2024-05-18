package ooo.cpacket.ruby.module.misc;

import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C03PacketPlayer.C04PacketPlayerPosition;
import net.minecraft.network.play.client.C07PacketPlayerDigging.Action;
import net.minecraft.util.BlockPos;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import ooo.cpacket.ruby.api.event.EventImpl;
import ooo.cpacket.ruby.api.event.events.network.EventPacket;
import ooo.cpacket.ruby.api.event.events.player.EventMotionUpdate;
import ooo.cpacket.ruby.api.event.events.player.motion.State;
import ooo.cpacket.ruby.module.Module;

public class GodMode extends Module {

	public GodMode(String name, int key, Category category) {
		super(name, key, category);
		this.addModes("Guardian/Spigot", "Vanilla");
	}

	@Override
	public void onEnable() {

	}

	@Override
	public void onDisable() {

	}

	@EventImpl
	public void onUpdates(final EventMotionUpdate e) {
		if (this.isMode("Guardian/Spigot")) {
			if (e.getState() == State.PRE) {
				mc.thePlayer.sendQueue.getNetworkManager().sendPacket(
						new C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 9 - (mc.thePlayer.ticksExisted % 2 == 0 ? 0.0000001 : 0), mc.thePlayer.posZ, true));
			}
		}
		if (this.isMode("Vanilla")) {
			mc.thePlayer.sendQueue.addToSendQueue(
					new C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 99.999164, mc.thePlayer.posZ, true));
		}
	}

	@EventImpl
	public void onPacket(EventPacket e) {
		if (e.getPacket() instanceof C02PacketUseEntity
				|| (e.getPacket() instanceof C08PacketPlayerBlockPlacement
						&& ((C08PacketPlayerBlockPlacement) e.getPacket()).func_179724_a() != BlockPos.ORIGIN)
				|| (e.getPacket() instanceof C07PacketPlayerDigging
						&& ((C07PacketPlayerDigging) e.getPacket()).func_180762_c() != Action.DROP_ITEM
						&& ((C07PacketPlayerDigging) e.getPacket()).func_180762_c() != Action.DROP_ALL_ITEMS
						&& ((C07PacketPlayerDigging) e.getPacket()).func_180762_c() != Action.RELEASE_USE_ITEM
						&& ((C07PacketPlayerDigging) e.getPacket()).func_180762_c() != Action.ABORT_DESTROY_BLOCK)) {
			mc.thePlayer.sendQueue.getNetworkManager().sendPacket(
					new C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, true));
			mc.thePlayer.sendQueue.getNetworkManager().sendPacket(e.getPacket());
			e.setSkip(true);
		}
	}

}
