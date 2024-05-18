package me.swezedcode.client.module.modules.Fight;

import org.lwjgl.input.Keyboard;

import com.darkmagician6.eventapi.EventListener;
import com.darkmagician6.eventapi.types.EventType;

import me.swezedcode.client.module.ModCategory;
import me.swezedcode.client.module.Module;
import me.swezedcode.client.utils.ModuleUtils;
import me.swezedcode.client.utils.Wrapper;
import me.swezedcode.client.utils.block.BlockHelper;
import me.swezedcode.client.utils.events.EventMotion;
import me.swezedcode.client.utils.events.EventPostMotionUpdates;
import me.swezedcode.client.utils.events.EventPreMotionUpdates;
import me.swezedcode.client.utils.events.EventReadPacket;
import me.swezedcode.client.utils.events.EventSendPacket;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.server.S12PacketEntityVelocity;

public class Criticals extends Module {

	public Criticals() {
		super("Criticals", Keyboard.KEY_NONE, 0xFF, ModCategory.Fight);
		setDisplayName(getName() + " §7Packet");
	}

	@EventListener
	public void onCrit(EventSendPacket e) {
		if (isToggled() && canCrit()) {
			C02PacketUseEntity packet = (C02PacketUseEntity) e.getPacket();
			if (packet.getAction() == C02PacketUseEntity.Action.ATTACK && canCrit()
					&& e.getPacket() instanceof C02PacketUseEntity) {
				mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX,
						mc.thePlayer.posY + 0.0624, mc.thePlayer.posZ, false));
				mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX,
						mc.thePlayer.posY, mc.thePlayer.posZ, false));
				mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX,
						mc.thePlayer.posY + 0.012511, mc.thePlayer.posZ, false));
				mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX,
						mc.thePlayer.posY, mc.thePlayer.posZ, false));
			}
		}
	}

	private boolean canCrit() {
		return !this.mc.thePlayer.isInWater() && this.mc.thePlayer.onGround;
	}

}
