package lunadevs.luna.module.player;

import org.lwjgl.input.Keyboard;

import lunadevs.luna.category.Category;
import lunadevs.luna.main.Luna;
import lunadevs.luna.module.Module;
import net.minecraft.network.play.client.C03PacketPlayer;

public class NoFall extends Module {

	public NoFall() {
		super("NoFall", Keyboard.KEY_NONE, Category.PLAYER, false);
	}

	@Override
	public void onUpdate() {
		if (!this.isEnabled) return;
	    if ((mc.thePlayer.fallDistance > 2.0F) && (!mc.thePlayer.onGround)) {
	        mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer(true));
	      }
			super.onUpdate();
	}

	
	@Override
	public void onEnable() {
	}
	@Override
	public void onDisable() {
		super.onDisable();
	}

	@Override
	public String getValue() {
		return null;
	}

}
