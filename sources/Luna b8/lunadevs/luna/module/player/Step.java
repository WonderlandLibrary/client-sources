package lunadevs.luna.module.player;

import org.lwjgl.input.Keyboard;

import lunadevs.luna.category.Category;
import lunadevs.luna.main.Luna;
import lunadevs.luna.module.Module;
import net.minecraft.network.play.client.C03PacketPlayer;

public class Step extends Module {

	private boolean active;

	public Step() {
		super("AACNoFall", Keyboard.KEY_NONE, Category.PLAYER, true);
	}

	@Override
	public void onUpdate() {
		if (!this.isEnabled) return;
	    if ((mc.thePlayer.isCollidedHorizontally)){
	    	mc.thePlayer.onGround=true;
	    	mc.gameSettings.keyBindJump.pressed=true;
	    	mc.gameSettings.keyBindJump.pressed=false;
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
		return "AAC 3.1.6";
	}

}
