package lunadevs.luna.module.player;

import org.lwjgl.input.Keyboard;

import lunadevs.luna.category.Category;
import lunadevs.luna.main.Luna;
import lunadevs.luna.module.Module;
import net.minecraft.network.play.client.C03PacketPlayer;

public class AACNoFall extends Module {

	private boolean active;

	public AACNoFall() {
		super("AACNoFall", Keyboard.KEY_NONE, Category.PLAYER, false);
	}

	@Override
	public void onUpdate() {
		if (!this.isEnabled) return;
	    if ((mc.thePlayer.fallDistance > 3.0F) && (!mc.thePlayer.onGround)) {
	    	mc.thePlayer.moveEntity(mc.thePlayer.motionX, mc.thePlayer.motionY, mc.thePlayer.motionZ);
	      }
			super.onUpdate();
	}

	
	@Override
	public void onEnable() {
		active=true;
	}
	@Override
	public void onDisable() {
		active=false;
		super.onDisable();
	}

	@Override
	public String getValue() {
		return null;
	}

}
