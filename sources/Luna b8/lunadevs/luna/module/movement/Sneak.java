package lunadevs.luna.module.movement;

import org.lwjgl.input.Keyboard;

import com.darkmagician6.eventapi.EventTarget;

import lunadevs.luna.category.Category;
import lunadevs.luna.events.PostTickEvent;
import lunadevs.luna.events.TickPreEvent;
import lunadevs.luna.module.Module;
import net.minecraft.network.play.client.C0BPacketEntityAction;

public class Sneak extends Module {

	public Sneak() {
		super("Sneak", Keyboard.KEY_NONE, Category.MOVEMENT, false);
	}

	public void onDisable() {
		mc.thePlayer.sendQueue
				.addToSendQueue(new C0BPacketEntityAction(mc.thePlayer, C0BPacketEntityAction.Action.STOP_SNEAKING));
	}

	@EventTarget
	public void preTick(TickPreEvent e) {
		if(!this.isEnabled) return;
		   mc.thePlayer.sendQueue.addToSendQueue(new C0BPacketEntityAction(mc.thePlayer, C0BPacketEntityAction.Action.STOP_SNEAKING));
	}
	
	@EventTarget
	public void postTick(PostTickEvent e) {
		if(!this.isEnabled) return;
		  mc.thePlayer.sendQueue.addToSendQueue(new C0BPacketEntityAction(mc.thePlayer, C0BPacketEntityAction.Action.START_SNEAKING));
	}
	
	public String getValue() {
		return null;
	}

}
