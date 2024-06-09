package lunadevs.luna.module.EXAMPLES;

import org.lwjgl.input.Keyboard;

import com.zCore.Core.zCore;

import lunadevs.luna.category.Category;
import lunadevs.luna.main.Luna;
import lunadevs.luna.module.Module;
import net.minecraft.network.play.client.C03PacketPlayer;

public class ExampleMod extends Module {

	private boolean active;

	public ExampleMod() {
		super("ExampleMod", Keyboard.KEY_NONE, Category.PLAYER, false /** true = mode/mode(s) will be available if you put like "Test" in return null at GetValue. false = modes are deactivated.*/);
	}

	@Override
	public void onUpdate() {
		if (!this.isEnabled) return;
	    if(zCore.p().isCollidedHorizontally){ //Check if the player is collided with a block.
	    	zCore.p().stepHeight = 1.5F; //Vanilla step code.
	      }
			super.onUpdate();
	}

	
	@Override
	public void onEnable() {
		
		super.onDisable();
	}
	@Override
	public void onDisable() {
		zCore.p().stepHeight = 0.6F;
		super.onDisable();
	}

	@Override
	public String getValue() {
		return null; //\\Modes, Example: return "NCP"; //\\
	}

}
