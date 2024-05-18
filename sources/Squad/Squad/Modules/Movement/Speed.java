package Squad.Modules.Movement;

import java.util.ArrayList;

import com.darkmagician6.eventapi.EventTarget;

import Squad.Squad;
import Squad.Events.EventUpdate;
import Squad.base.Module;
import Squad.base.ModuleManager;
import de.Hero.settings.Setting;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.C03PacketPlayer;

public class Speed
extends Module
{
private float timer;

public Speed()
{
  super("Speed", 0, -1, Category.Movement);
}
private int motionDelay;

public void setup(){
 	ArrayList<String> options = new ArrayList<>();
 		options.add("AACREWI");
 		options.add("TIMER");
 		options.add("AAC3");
 		options.add("AAC3New");
 		options.add("AAC 3.2.1");
 		options.add("Hypixel");
 		options.add("Hypixelground");
 		options.add("BhopAAC3.2.1");
 		options.add("Bhop");
 		options.add("AACBhop");
 		options.add("NCPBhop");
 Squad.instance.setmgr.rSetting(new Setting("SpeedMode", this, "AACREWI", options));

Squad.instance.setmgr.rSetting(new Setting("SprintSpeed", this, false));
}

public void onDisable(){
	  mc.timer.timerSpeed = 1.0F;
	}

@EventTarget
private void onMove(EventUpdate event)
{
	
	if(Squad.setmgr.getSettingByName("SprintSpeed").getValBoolean()){
		mc.thePlayer.setSprinting(true);
	}else{
		mc.thePlayer.setSprinting(false);
	}
	if(Squad.instance.setmgr.getSettingByName("SpeedMode").getValString().equalsIgnoreCase("AACREWI")){	
    setDisplayname("Speed §7AACNCP-Hop");
    if ((mc.gameSettings.keyBindForward.pressed) || (mc.gameSettings.keyBindBack.pressed) || (mc.gameSettings.keyBindLeft.pressed) || (mc.gameSettings.keyBindRight.pressed)) {
      if (Minecraft.getMinecraft().thePlayer.onGround)
      {
        Minecraft.getMinecraft().thePlayer.jump();
        mc.timer.timerSpeed = 2.0F;
      }
      else
      {
        mc.timer.timerSpeed = 1.3F;
      }
    }
  }
	if(Squad.instance.setmgr.getSettingByName("SpeedMode").getValString().equalsIgnoreCase("TIMER")){	
  
    setDisplayname("Speed §7TIMER");
    mc.timer.timerSpeed = 3.0F;
  }
	if(Squad.instance.setmgr.getSettingByName("SpeedMode").getValString().equalsIgnoreCase("BhopAAC3.2.1")){	
    setDisplayname("Speed §7BhopAAC3.2.1");
    Minecraft.getMinecraft().thePlayer.setSprinting(true);
    if (Minecraft.getMinecraft().thePlayer.onGround)
    {
      Minecraft.getMinecraft().thePlayer.jump();
      mc.timer.timerSpeed = 1.17F;
      Minecraft.getMinecraft().thePlayer.motionY = 0.4D;
      Minecraft.getMinecraft().thePlayer.motionX *= 0.06f;
      Minecraft.getMinecraft().thePlayer.motionZ *= 0.06f;
      Minecraft.getMinecraft().thePlayer.jumpMovementFactor = 3.0F;
      Minecraft.getMinecraft().thePlayer.moveStrafing = 2.0F;
    }
  }
	if(Squad.instance.setmgr.getSettingByName("SpeedMode").getValString().equalsIgnoreCase("AAC3New")){	
	    setDisplayname("Speed §7AAC3New");
	    if (Minecraft.getMinecraft().thePlayer.onGround)  {
	    	mc.thePlayer.jump();
	    	mc.timer.timerSpeed = 4F;
	    }else{
	    	mc.timer.timerSpeed = 1F;
	    }
	  }
	
	if(Squad.instance.setmgr.getSettingByName("SpeedMode").getValString().equalsIgnoreCase("Hypixel")){	
	    setDisplayname("Speed §7Hypixel");
	        if (Minecraft.getMinecraft().thePlayer.onGround)
	        {
	          Minecraft.getMinecraft().thePlayer.jump();
	          mc.timer.timerSpeed = 2.0F;
	        }
	        else
	        {
	          mc.timer.timerSpeed = 1.3F;
	        }
	      }
	if(Squad.instance.setmgr.getSettingByName("SpeedMode").getValString().equalsIgnoreCase("AAC 3.2.1") &&  ((mc.gameSettings.keyBindForward.pressed) || (mc.gameSettings.keyBindBack.pressed) || (mc.gameSettings.keyBindLeft.pressed) || (mc.gameSettings.keyBindRight.pressed))){
		setDisplayname("Speed §7AAC3.2.1");
	    {
	      if (mc.thePlayer.isInWater()) {
	        return;
	      }
	      if (mc.thePlayer.onGround)
	      {
	        this.motionDelay += 1;
	        this.motionDelay %= 1;
	        if (this.motionDelay == 0)
	        {
	          mc.thePlayer.jump();
	          mc.thePlayer.motionY -= 0.009999999776482582D;
	          mc.thePlayer.motionX *= 1.0049999952316284D;
	          mc.thePlayer.motionZ *= 1.0049999952316284D;
	        }
	      }
	      mc.thePlayer.speedInAir = 0.0206F;
	    }
	}
	if(Squad.instance.setmgr.getSettingByName("SpeedMode").getValString().equalsIgnoreCase("HypixelGround") &&  ((mc.gameSettings.keyBindForward.pressed) || (mc.gameSettings.keyBindBack.pressed) || (mc.gameSettings.keyBindLeft.pressed) || (mc.gameSettings.keyBindRight.pressed))){
		setDisplayname("Speed §7HypixelGround");
		{
			  if (mc.thePlayer.isInWater()) {
			        return;
		}
			  if (mc.thePlayer.onGround){
				  mc.thePlayer.jump();
		          mc.thePlayer.motionY -= 3D;
		          mc.thePlayer.motionX *= 0.7D;
		          mc.thePlayer.motionZ *= 1.3D; 
			  }

}

		if(Squad.instance.setmgr.getSettingByName("SpeedMode").getValString().equalsIgnoreCase("AACBhop")){	
	    setDisplayname("Speed §7AAC3.2.0");
	    {
	    	  if (mc.thePlayer.isInWater()) {
			        return;
	    }
	    	  if(mc.thePlayer.onGround)
	    	  mc.thePlayer.jump();
			  mc.thePlayer.motionY = 0.4;
			  mc.thePlayer.motionZ *= 0.4;
			  mc.thePlayer.motionX *= 0.4;
			  mc.thePlayer.setSprinting(true);
	
	    	  
}

		}
	}

	if(Squad.instance.setmgr.getSettingByName("SpeedMode").getValString().equalsIgnoreCase("Bhop")){	
	    setDisplayname("Speed §7Bhop");
	    if (mc.thePlayer.isInWater()) {
	        return;
	    }
	    if(mc.thePlayer.onGround){
		 mc.thePlayer.jump();
         mc.thePlayer.motionY = 0.4D;
         mc.thePlayer.motionX *= 1.00000555555D;
         mc.thePlayer.motionZ *= 1.00000555555D;
}
} 
	if(Squad.instance.setmgr.getSettingByName("SpeedMode").getValString().equalsIgnoreCase("AACBhop")){	
    setDisplayname("Speed §7AACBhop");
    if (Minecraft.getMinecraft().thePlayer.onGround)
    {
      Minecraft.getMinecraft().thePlayer.jump();
      mc.timer.timerSpeed = 1.17F;
      Minecraft.getMinecraft().thePlayer.motionY = 0.4D;
      Minecraft.getMinecraft().thePlayer.motionX *= 0.06f;
      Minecraft.getMinecraft().thePlayer.motionZ *= 0.06f;
      Minecraft.getMinecraft().thePlayer.jumpMovementFactor = 3.0F;
      Minecraft.getMinecraft().thePlayer.moveStrafing = 2.0F;			    
}
}
	 if(Squad.instance.setmgr.getSettingByName("SpeedMode").getValString().equalsIgnoreCase("NCPBhop")){	
		    setDisplayname("Speed §7NCPBhop");
		    if (Minecraft.getMinecraft().thePlayer.onGround){
			      Minecraft.getMinecraft().thePlayer.jump();
		          Minecraft.getMinecraft().thePlayer.motionY = 0.4D;
			      Minecraft.getMinecraft().thePlayer.motionX *= 0.6f;
			      Minecraft.getMinecraft().thePlayer.motionZ *= 0.6f;
			      Minecraft.getMinecraft().thePlayer.jumpMovementFactor = 3.0F;
			      Minecraft.getMinecraft().thePlayer.moveStrafing = 2.0F;
		    	
		    }
}
}
}


