package Squad.Modules.Movement;

import java.util.ArrayList;

import com.darkmagician6.eventapi.EventTarget;

import Squad.Squad;
import Squad.Events.EventUpdate;
import Squad.base.Category;
import Squad.base.Module;
import de.Hero.settings.Setting;
import net.minecraft.client.Minecraft;

public class WallSpeed
extends Module
{
public WallSpeed()
{
  super("WallSpeed", 0, 136, Category.Movement);
  
}
public void setup(){
 	ArrayList<String> options = new ArrayList<>();
	options.add("AACFlag");

 	 Squad.instance.setmgr.rSetting(new Setting("WallSpeedMods", this, "", options));

}
	

	@EventTarget
	private void onMove(EventUpdate event)
	{
		if(Squad.instance.setmgr.getSettingByName("WallSpeedMods").getValString().equalsIgnoreCase("AACFlag")){	
		    setDisplayname("WallSpeed §7ACCFlag");
		    if (mc.thePlayer.isCollidedHorizontally)
		    {
		     mc.thePlayer.motionZ *= 1.2D;
		     mc.thePlayer.motionX *= 1.2D;
		     mc.thePlayer.motionY *= 0D;

  }
		

}
}

}

