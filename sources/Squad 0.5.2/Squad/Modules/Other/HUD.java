package Squad.Modules.Other;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

import com.darkmagician6.eventapi.EventTarget;

import Squad.Squad;
import Squad.Events.EventUpdate;
import Squad.base.Module;
import de.Hero.settings.Setting;

public class HUD extends Module{

	public HUD() {
		super("Hud", Keyboard.KEY_NONE, 0x88, Category.Render);
		// TODO Auto-generated constructor stub
	}
	
	
	
	public void setup(){
	 	ArrayList<String> options = new ArrayList<>();
	 		options.add("Nuke");
	 		options.add("China");
	 		options.add("Future");
	 		options.add("SQUAD");
	 		options.add("New");
	 		options.add("Ambien");
	 Squad.instance.setmgr.rSetting(new Setting("HUDMode", this, "China", options));
	}
	
	@EventTarget
	public void onUpdate(EventUpdate e){
	    setDisplayname("HUD §7NORECT");
		if(Squad.instance.setmgr.getSettingByName("HUDMode").getValString().equalsIgnoreCase("Nuke")){	
			GuiHUD.mode = 1;	
	}
		if(Squad.instance.setmgr.getSettingByName("HUDMode").getValString().equalsIgnoreCase("China")){	
		    setDisplayname("HUD §7China");
			GuiHUD.mode = 2;
			
		}
		if(Squad.instance.setmgr.getSettingByName("HUDMode").getValString().equalsIgnoreCase("Future")){	
		    setDisplayname("HUD §7Future");
			GuiHUD.mode = 3;
			
		}
		if(Squad.instance.setmgr.getSettingByName("HUDMode").getValString().equalsIgnoreCase("SQUAD")){	
		    setDisplayname("HUD §7SQUAD");
			GuiHUD.mode = 4;
			
		}
	if(Squad.instance.setmgr.getSettingByName("HUDMode").getValString().equalsIgnoreCase("New")){	
	    setDisplayname("HUD §7New");
		GuiHUD.mode = 5;
		
	}
	if(Squad.instance.setmgr.getSettingByName("HUDMode").getValString().equalsIgnoreCase("Ambien")){	
	    setDisplayname("HUD §7Ambien");
		GuiHUD.mode = 6;
		
	
	}
}
}
