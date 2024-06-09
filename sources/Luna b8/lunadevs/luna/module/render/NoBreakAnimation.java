package lunadevs.luna.module.render;

import org.lwjgl.input.Keyboard;

import com.darkmagician6.eventapi.EventTarget;

import lunadevs.luna.category.Category;
import lunadevs.luna.events.DamageBlockEvent;
import lunadevs.luna.module.Module;
import net.minecraft.network.play.client.C07PacketPlayerDigging;

public class NoBreakAnimation extends Module{

	public NoBreakAnimation() {
		super("NoBreakAnimation", Keyboard.KEY_NONE, Category.RENDER, false);
	}
	
	@EventTarget
	public void damageBlock(DamageBlockEvent e) {
		if(!this.isEnabled) return;
	      mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.ABORT_DESTROY_BLOCK, e.getBlockPos(), e.getEnumFacing()));
	}
	
	public String getValue() {
		return null;
	}
	
}
