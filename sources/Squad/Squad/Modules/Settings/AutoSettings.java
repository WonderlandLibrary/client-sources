package Squad.Modules.Settings;

import java.util.ArrayList;

import com.darkmagician6.eventapi.EventTarget;

import Squad.Squad;
import Squad.Events.EventUpdate;
import Squad.base.Module;
import Squad.commands.Command;
import de.Hero.settings.Setting;

public class AutoSettings extends Module{

	public AutoSettings() {
		super("AutoSettings", 0, 136, Category.Settings);
		// TODO Auto-generated constructor stub
	}
	public void setup(){
	 	ArrayList<String> options = new ArrayList<>();
	 	options.add("AAC");
	 	options.add("Rewi");
	 	options.add("Gomme");
		options.add("Hypixel");
		options.add("MineCheat");
		options.add("CubeCraft");
	 	 Squad.instance.setmgr.rSetting(new Setting("AutoSettings", this, "", options));
	 	Squad.instance.setmgr.rSetting(new Setting("AutoCheck", this, false));
	}
	@EventTarget
	private void onMove(EventUpdate event)
	{
		if(Squad.instance.setmgr.getSettingByName("AutoSettings").getValString().equalsIgnoreCase("Rewi")){	
		    setDisplayname("AutoSettings §7Rewi");
}
		if(Squad.instance.setmgr.getSettingByName("AutoSettings").getValString().equalsIgnoreCase("Gomme")){
		    setDisplayname("AutoSettings §7Gomme");
}
		if(Squad.instance.setmgr.getSettingByName("AutoSettings").getValString().equalsIgnoreCase("Hypixel")){	
		    setDisplayname("AutoSettings §7Hypixel");
	}
		if(Squad.instance.setmgr.getSettingByName("AutoSettings").getValString().equalsIgnoreCase("CubeCraft")){	
		    setDisplayname("AutoSettings §7CubeCraft");
}
		if(Squad.instance.setmgr.getSettingByName("AutoSettings").getValString().equalsIgnoreCase("MineCheat")){	
		    setDisplayname("AutoSettings §7MineCheat");
}
		if(Squad.instance.setmgr.getSettingByName("AutoSettings").getValString().equalsIgnoreCase("AAC")){	
		    setDisplayname("AutoSettings §7AAC");
		    
}
		if(Squad.instance.setmgr.getSettingByName("AutoCheck").getValBoolean()) {		
}
}
}

