package Squad.Modules.Movement;

import javax.swing.text.JTextComponent.KeyBinding;

import com.darkmagician6.eventapi.EventManager;
import com.darkmagician6.eventapi.EventTarget;

import Squad.Squad;
import Squad.Events.EventUpdate;
import Squad.base.Module;
import net.java.games.input.Keyboard;
import net.minecraft.client.Minecraft;

public class ModuleSprint extends Module{

	public ModuleSprint() {
		super("Sprint", org.lwjgl.input.Keyboard.KEY_NONE, 0xffffffff, Category.Movement);

	}

@EventTarget

public void SetSprinting(EventUpdate event){
	setDisplayname("Sprint");
if(	 Squad.instance.moduleManager.getModuleByName("ScaffoldWalk").isEnabled()){
	if(Squad.instance.setmgr.getSettingByName("Sprint").getValBoolean()) {
mc.thePlayer.setSprinting(true);
	}else{
mc.thePlayer.setSprinting(false);	
	}
}else{
	if(mc.gameSettings.keyBindForward.pressed == true || mc.gameSettings.keyBindBack.pressed == true || mc.gameSettings.keyBindLeft.pressed == true|| mc.gameSettings.keyBindRight.pressed == true)
mc.thePlayer.setSprinting(true);
	
}
		
}	
}
