package zamorozka.modules.TRAFFIC;

import de.Hero.settings.Setting;
import zamorozka.main.Zamorozka;
import zamorozka.module.Category;
import zamorozka.module.Module;
import zamorozka.ui.MoveUtils;
import zamorozka.ui.MovementUtilis;
import zamorozka.ui.SpeedUtils;

public class VehicleFly extends Module{
	
	public static float speed = 3F;
	
    @Override
    public void setup() {
    	Zamorozka.settingsManager.rSetting(new Setting("Pig", this, true));
    	Zamorozka.settingsManager.rSetting(new Setting("Horse", this, true));
    	Zamorozka.settingsManager.rSetting(new Setting("Boat", this, true));
    }
    
	public VehicleFly(){
		super("VehicleFly", 0, Category.TRAFFIC);
	}
	
	public void onUpdate(){
		if(!this.getState()){
			return;
		}
		if(Zamorozka.settingsManager.getSettingByName("Pig").getValBoolean()){
			  if (Zamorozka.player().isRiding()) {
				  //Zamorozka.player().getRidingEntity().motionY = (mc.gameSettings.keyBindJump.pressed ? 0.5D : 0.0D);
			      float dir = mc.player.rotationYaw + ((mc.player.moveForward < 0) ? 180 : 0) + ((mc.player.moveStrafing > 0) ? (-90F * ((mc.player.moveForward < 0) ? -.5F : ((mc.player.moveForward > 0) ? .4F : 1F))) : 0);
			      float xDir = (float)Math.cos((dir + 90F) * Math.PI / 180);
			      float zDir = (float)Math.sin((dir + 90F) * Math.PI / 180);
				  mc.player.getLowestRidingEntity().motionY = (mc.gameSettings.keyBindJump.pressed ? 0.5D : 0.0D);
				  if(mc.gameSettings.keyBindForward.isKeyDown()){
				  mc.player.getLowestRidingEntity().motionX=xDir*1;
				  mc.player.getLowestRidingEntity().motionZ=zDir*1;
				  }
			  }
		}
		if(Zamorozka.settingsManager.getSettingByName("Horse").getValBoolean()){
			  if (Zamorozka.player().isRiding()) {
				  Zamorozka.player().getLowestRidingEntity().motionY = 
			    (mc.gameSettings.keyBindJump.pressed ? 1.5D : 0.0D);
				  MovementUtilis.setMotion(0.5);
			  }
		}
		if(Zamorozka.settingsManager.getSettingByName("Boat").getValBoolean()){
			if (Zamorozka.player().isRiding()) {
			      float dir = mc.player.rotationYaw + ((mc.player.moveForward < 0) ? 180 : 0) + ((mc.player.moveStrafing > 0) ? (-90F * ((mc.player.moveForward < 0) ? -.5F : ((mc.player.moveForward > 0) ? .4F : 1F))) : 0);
			      float xDir = (float)Math.cos((dir + 90F) * Math.PI / 180);
			      float zDir = (float)Math.sin((dir + 90F) * Math.PI / 180);
				  mc.player.getRidingEntity().motionY = (mc.gameSettings.keyBindJump.pressed ? 0.5D : 0.0D);
			}
		}
	}
}