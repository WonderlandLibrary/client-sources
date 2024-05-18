package Squad.Modules.Movement;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

import com.darkmagician6.eventapi.EventTarget;

import Squad.Squad;
import Squad.Events.EventUpdate;
import Squad.base.Module;
import de.Hero.settings.Setting;

public class HighJump extends Module{

	public HighJump() {
		super("HighJump", Keyboard.KEY_NONE, 0x88, Category.Movement);
		// TODO Auto-generated constructor stub
	}
	
	
	
	public void setup(){
	 	ArrayList<String> options = new ArrayList<>();
	 		options.add("HighAusserGomme");
	 		options.add("GommeIksde");

	 		
	 Squad.instance.setmgr.rSetting(new Setting("HJMODE", this, "HighAusserGomme", options));
	}
	
@EventTarget
	public void onUpdate(EventUpdate e){
	if(Squad.instance.setmgr.getSettingByName("HJMODE").getValString().equalsIgnoreCase("HighAusserGomme")){	
		setDisplayname("HighJump §7NoGomme");
		if(mc.thePlayer.onGround){
				mc.thePlayer.motionY = 10;
			}
		}

	if(Squad.instance.setmgr.getSettingByName("HJMODE").getValString().equalsIgnoreCase("GommeIksde")){	
		setDisplayname("HighJump §7Gomme");
		if(mc.thePlayer.onGround){
			mc.thePlayer.motionY = 1.5;
		}
		
	}
}
}
	

