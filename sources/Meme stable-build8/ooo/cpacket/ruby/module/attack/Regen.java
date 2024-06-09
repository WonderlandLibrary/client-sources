package ooo.cpacket.ruby.module.attack;

import net.minecraft.network.play.client.C03PacketPlayer.C04PacketPlayerPosition;
import ooo.cpacket.ruby.api.event.EventImpl;
import ooo.cpacket.ruby.api.event.events.player.EventMotionUpdate;
import ooo.cpacket.ruby.module.Module;

public class Regen extends Module {

	public Regen(String name, int key, Category category) {
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
		if (!mc.thePlayer.isDead && mc.thePlayer.onGround && mc.thePlayer.getHealth() < 16 && mc.thePlayer.getFoodStats().getFoodLevel() > 1) {
			for (int niggerkiller = 0; niggerkiller < 20; niggerkiller++) {
				mc.thePlayer.sendQueue.addToSendQueue(
						new C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, true));
				
				mc.thePlayer.sendQueue.addToSendQueue(
						new C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 15, mc.thePlayer.posZ, true));
			}
		}
	}
	
}
