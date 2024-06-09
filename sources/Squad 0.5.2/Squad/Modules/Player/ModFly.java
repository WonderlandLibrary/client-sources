package Squad.Modules.Player;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

import com.darkmagician6.eventapi.EventTarget;

import Squad.Squad;
import Squad.Events.EventUpdate;
import Squad.base.Module;
import de.Hero.settings.Setting;
import net.minecraft.client.Minecraft;


public class ModFly extends Module{

	public ModFly() {
		super("Fly", Keyboard.KEY_NONE, 0xffffffff, Category.Player);
		setDisplayname("Fly");

	}
	private double speed;
	private double x;
	private double y;
	private double z;
	@Override
	 public void setup(){
	 	ArrayList<String> options = new ArrayList<>();
	 		options.add("Vanilla");
	 		options.add("Motion");
	 		options.add("CubeCraft");
	 		options.add("TestAC");
	 		options.add("AAC3.1.6");
	 		
	 Squad.instance.setmgr.rSetting(new Setting("FlyMode", this, "Vanilla", options));
	// Squad.instance.setmgr.rSetting(new Setting("MotionX", this, 0.0, 1, 10.0, false));
	 Squad.instance.setmgr.rSetting(new Setting("MotionY", this, 0.0, 0.1, 10.0, false));
//	 Squad.instance.setmgr.rSetting(new Setting("MotionZ", this, 0.0, 1, 10.0, false));
	 Squad.instance.setmgr.rSetting(new Setting("Speed", this, 0.0, 1, 10.0, false));
	}


	@Override
	public void onDisable(){
		mc.thePlayer.capabilities.isFlying = false;
		mc.timer.timerSpeed = 1F;

	}
	@EventTarget
	public void onUpdate(EventUpdate event){
		setDisplayname("Fly");
		if(Squad.instance.setmgr.getSettingByName("FlyMode").getValString().equalsIgnoreCase("Vanilla")){
			setDisplayname("Fly �7Vanilla");
			speed = ((double) 	Squad.instance.setmgr.getSettingByName("Speed").getValDouble());
			mc.thePlayer.capabilities.isFlying = true;
			mc.thePlayer.capabilities.setFlySpeed((float) speed / 10);

		}else{
			if(Squad.instance.setmgr.getSettingByName("FlyMode").getValString().equalsIgnoreCase("Motion")){
	
				//x = ((double) 	Squad.instance.setmgr.getSettingByName("MotionX").getValDouble());
				y = ((double) 	Squad.instance.setmgr.getSettingByName("MotionY").getValDouble());
			//	z = ((double) 	Squad.instance.setmgr.getSettingByName("MotionZ").getValDouble());
				setDisplayname("Fly �7Motion");
				mc.thePlayer.motionY *= y;
				mc.thePlayer.capabilities.setFlySpeed((float) speed / 10);
			}else{
				if(Squad.instance.setmgr.getSettingByName("FlyMode").getValString().equalsIgnoreCase("CubeCraft")){
					setDisplayname("Fly �7CubeCraft");
					mc.timer.timerSpeed = 0.4F;
					mc.thePlayer.capabilities.setFlySpeed(0.6F);
					mc.thePlayer.capabilities.isFlying = true;
				}else{
				if(Squad.instance.setmgr.getSettingByName("FlyMode").getValString().equalsIgnoreCase("AAC3.1.6")) {
					setDisplayname("Fly §7ACC 3.1.6");
					if (mc.gameSettings.keyBindForward.pressed) {
						mc.thePlayer.motionZ *= -7.634633;
						mc.thePlayer.motionY *= -1;
					}
				}
		}
	}
	
}
}
	}


