package Squad.Modules.Player;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

import com.darkmagician6.eventapi.EventManager;
import com.darkmagician6.eventapi.EventTarget;

import Squad.Squad;
import Squad.Events.EventUpdate;
import Squad.Utils.MoveUtils;
import Squad.base.Module;
import de.Hero.settings.Setting;
import net.minecraft.client.Minecraft;

public class Velocity extends Module {

	public static int mode = 1;
	
	
	public Velocity() {
		super("Velocity", Keyboard.KEY_NONE, 0xFFFFFF, Category.Combat);

	}
	public static int timeVelocity  = 0;
	
	
	
	 public void setup(){
		 	ArrayList<String> options3 = new ArrayList<>();
		 		options3.add("AACPush");
		 		options3.add("AACBOOST");
		 		options3.add("NoVelocity");				
		 Squad.instance.setmgr.rSetting(new Setting("VelocityMode", this, "AACPush", options3));
		}
	
	
	 @EventTarget
	public void onUpdate(EventUpdate e){
			
			if(Squad.instance.setmgr.getSettingByName("VelocityMode").getValString().equalsIgnoreCase("AACPush")){
			setDisplayname("Velocity §7Push");		
			   if(timeVelocity !=0)
			   {
			    timeVelocity++;
			   }
			   
			   if(timeVelocity == 6)
			   {
			    MoveUtils.setSpeed(0.39F);
			    for(int i = 0; i < 5; i++)
			    {
			     timeVelocity = 0;
			    }
			   }
}
			if(Squad.instance.setmgr.getSettingByName("VelocityMode").getValString().equalsIgnoreCase("AACBOOST")){
				setDisplayname("Velocity §7Boost");	
				if(mc.thePlayer.hurtTime != 0){
					mc.thePlayer.motionZ *= 0.7D;
					mc.thePlayer.motionX *= 0.7D;
				}
			}
				}
				
			
			
	 
		public void onEnable(){
			EventManager.register(this);
		}
		
		public void onDisable(){
			EventManager.unregister(this);
		}
		
		  public void onVelocity()
		  {
		   if(Squad.instance.setmgr.getSettingByName("VelocityMode").getValString().equalsIgnoreCase("AACPush") && mc.thePlayer.hurtTime != 0){
		    timeVelocity = 1;
		   }
		  
		  if(Squad.instance.setmgr.getSettingByName("VelocityMode").getValString().equalsIgnoreCase("NoVelocity")){
				setDisplayname("NoVelocity");	
				if(mc.thePlayer.hurtTime != 0){
					mc.thePlayer.motionY = 0;
					mc.thePlayer.motionX = 0;
					mc.thePlayer.motionZ = 0;
				}

		

		

}
		  }
}

