package me.swezedcode.client.module.modules.Player;

import org.lwjgl.input.Keyboard;

import com.darkmagician6.eventapi.EventListener;
import com.darkmagician6.eventapi.types.EventType;

import me.swezedcode.client.module.ModCategory;
import me.swezedcode.client.module.Module;
import me.swezedcode.client.utils.events.EventMotion;
import me.swezedcode.client.utils.events.EventReadPacket;
import net.minecraft.network.play.client.C0BPacketEntityAction;

public class Sneak extends Module {

	public Sneak() {
		super("Sneak", Keyboard.KEY_NONE, 0xFF, ModCategory.Player);
	}

	private boolean makeSneak;
	private boolean doBlock = false;

	public void onEnable() {
		this.mc.thePlayer.sendQueue.addToSendQueue(
				new C0BPacketEntityAction(this.mc.thePlayer, C0BPacketEntityAction.Action.START_SNEAKING));
	}

	public void onDisable() {
		this.mc.thePlayer.sendQueue.addToSendQueue(
				new C0BPacketEntityAction(this.mc.thePlayer, C0BPacketEntityAction.Action.STOP_SNEAKING));
	}

	@EventListener
	public void onPacket(EventReadPacket event) {
		if (((event.getPacket() instanceof C0BPacketEntityAction)) && (event.getState() == event.getState().PRE)) {
			if (this.doBlock) {
				event.setCancelled(true);
			} else if (this.makeSneak) {
				mc.thePlayer.sendQueue.addToSendQueue(
						new C0BPacketEntityAction(this.mc.thePlayer, C0BPacketEntityAction.Action.START_SNEAKING));
			} else {
				mc.thePlayer.sendQueue.addToSendQueue(
						new C0BPacketEntityAction(this.mc.thePlayer, C0BPacketEntityAction.Action.STOP_SNEAKING));
			}
		}
	}

	@EventListener
	public void onMotion(EventMotion event) {
		if (event.getType() == EventType.PRE) {
			this.doBlock = false;
			this.makeSneak = false;
			this.mc.thePlayer.sendQueue.addToSendQueue(
					new C0BPacketEntityAction(this.mc.thePlayer, C0BPacketEntityAction.Action.STOP_SNEAKING));
			this.doBlock = true;
		} else {
			this.doBlock = false;
			this.makeSneak = true;
			this.mc.thePlayer.sendQueue.addToSendQueue(
					new C0BPacketEntityAction(this.mc.thePlayer, C0BPacketEntityAction.Action.START_SNEAKING));
			this.doBlock = true;
		}
	}

}
