package Squad.Modules.Movement;

import org.lwjgl.input.Keyboard;

import com.darkmagician6.eventapi.EventManager;
import com.darkmagician6.eventapi.EventTarget;

import Squad.Events.EventUpdate;
import Squad.base.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.potion.Potion;

public class ModSpeed extends Module{

	public ModSpeed() {
		super("Speed",Keyboard.KEY_NONE, 0xffffffff, Category.Movement);

	}



	@EventTarget
	private void onMove(EventUpdate event){
		setDisplayname("Speed");
		if(mc.gameSettings.keyBindForward.pressed || mc.gameSettings.keyBindBack.pressed || mc.gameSettings.keyBindLeft.pressed || mc.gameSettings.keyBindRight.pressed)
	if(mc.thePlayer.onGround){
		mc.thePlayer.jump();
		mc.timer.timerSpeed = 2F;
	}else{
		mc.timer.timerSpeed = 1.3F;
	}
		
	
	
	}	  
	
	public void onDisable() {
		mc.timer.timerSpeed = 1F;
	}
	}