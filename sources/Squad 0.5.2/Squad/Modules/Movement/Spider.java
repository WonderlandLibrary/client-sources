package Squad.Modules.Movement;

import java.util.ArrayList;

import com.darkmagician6.eventapi.EventTarget;

import Squad.Squad;
import Squad.Events.EventUpdate;
import Squad.Utils.TimeHelper;
import Squad.base.Category;
import Squad.base.Module;
import de.Hero.settings.Setting;
import net.minecraft.client.Minecraft;

public class Spider
extends Module
{
public Spider()
{
  super("Step", 0, 387, Category.Player);
}

TimeHelper Mafucka = new TimeHelper();

@EventTarget
public void onUpdate(EventUpdate e)
{
	if(Squad.instance.setmgr.getSettingByName("StepMode").getValString().equalsIgnoreCase("AAC3StepF")){	
  setDisplayname("Step §7AAC3");
    if (mc.thePlayer.isCollidedHorizontally) {
      if (mc.thePlayer.onGround) {
        mc.thePlayer.motionY += 0.10000000149011612D;
        mc.thePlayer.jump();
      }
      mc.thePlayer.onGround = true;
    }
  }

	if(Squad.instance.setmgr.getSettingByName("StepMode").getValString().equalsIgnoreCase("AAC3StepS")){
		  setDisplayname("Step §7AAC3Slow");
		if(mc.thePlayer.onGround && mc.thePlayer.isCollidedHorizontally){
			if(Mafucka.isDelayComplete(50l)){
				mc.thePlayer.motionY += 0.1D;
				mc.timer.timerSpeed= 1.02f;
				if(mc.thePlayer.onGround){
					mc.thePlayer.jump();
				}
				mc.thePlayer.onGround = true;
				Mafucka.reset();
			}else {
				mc.timer.timerSpeed = 1F;
				mc.thePlayer.stepHeight = 0.5f;
			}
		}
	}
	
}

public void onDisable()
{
	mc.thePlayer.stepHeight = 0.6F;
}



public void setup(){
	 	ArrayList<String> options = new ArrayList<>();
	 		options.add("AAC3StepF");
	 		options.add("AAC3StepS");
	 		
	 Squad.instance.setmgr.rSetting(new Setting("StepMode", this, "AAC3StepF", options));
	}

}
