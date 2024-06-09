package lunadevs.luna.module.player;

import org.lwjgl.input.Keyboard;

import lunadevs.luna.category.Category;
import lunadevs.luna.events.Event;
import lunadevs.luna.main.Luna;
import lunadevs.luna.module.Module;
import net.minecraft.client.Minecraft;

public class SkipLadder extends Module{

	public SkipLadder() {
		super("SkipLadder", Keyboard.KEY_NONE, Category.PLAYER, true);
	}
	@Override
	public void onUpdate() {
		if (!this.isEnabled) return;
		if(mc.thePlayer.isOnLadder()){
			
		mc.thePlayer.setSprinting(true);
		mc.thePlayer.onGround=true;
		}
		super.onUpdate();
	}

	@Override
	public void onDisable() {
		mc.gameSettings.keyBindSprint.pressed = false;
		mc.gameSettings.keyBindSneak.pressed=false;
		super.onDisable();
	}
	
	@Override
	public void onEnable() {
		super.onEnable();
	}
	
	@Override
	public String getValue() {
		return "AAC 3.1.6";
	}

}
