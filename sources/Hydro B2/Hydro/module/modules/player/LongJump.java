package Hydro.module.modules.player;

import org.lwjgl.input.Keyboard;

import Hydro.Client;
import Hydro.ClickGui.settings.Setting;
import Hydro.module.Category;
import Hydro.module.Module;
import Hydro.notification.Color;
import Hydro.notification.NotificationManager;
import Hydro.notification.Type;

public class LongJump extends Module {
	
	private boolean candisable = false;

	public LongJump() {
		super("LongJump", Keyboard.KEY_C, true, Category.PLAYER, "Jump very long distances");
		Client.instance.settingsManager.rSetting(new Setting("LongJumpTimer", "TimerBoost", this, 1, 1, 10, true));
	}
	
	@Override
	public void onUpdate() {
		mc.timer.timerSpeed = (float) Client.instance.settingsManager.getSettingByName("LongJumpTimer").getValDouble();
        if (mc.thePlayer.onGround && candisable) {
            toggle();
            return;
        }
        
        if(mc.thePlayer.posY < 30) {
        	toggle();
        }
        
        if (mc.thePlayer.onGround) {
            mc.thePlayer.jump();
            mc.thePlayer.speedInAir = 0.05F;
            candisable = true;
        }
        if (mc.thePlayer.fallDistance > -0x12e * -0x18 + -0x1f67 + 0x317) {
            if (mc.thePlayer.fallDistance < -0x19a3 * -0x1 + 0x810 + -0x10d9 * 0x2) {
                mc.thePlayer.speedInAir *= 0x237e + -0x1 * -0x1dd5 + -16722.15;
                mc.thePlayer.motionY *= 0x2525 + 0x7 * -0xb + -9431.25;
            } else if (mc.thePlayer.fallDistance < -0x1fba + 0x1da3 + 0x3 * 0xb3) {
                mc.thePlayer.speedInAir *= -0x166 * -0x5 + 0xb41 + -4670.25;
                mc.thePlayer.motionY *= 0x892 + 0x450 + -3297.15;
            } else {
                mc.thePlayer.speedInAir = -0x1a * 0xd6 + -0x25e2 + 15262.02F;
            }
        }
        if (mc.thePlayer.motionY > 0) {
            if (mc.thePlayer.motionY < 0.3) {
                mc.thePlayer.motionY *= 1.25;
                mc.thePlayer.speedInAir *= 1.21;
            }
        }
	}
	
	@Override
	public void onEnable() {
		mc.thePlayer.motionY = 0.521F;
        mc.thePlayer.speedInAir = 0.025236F;
        candisable = false;
        NotificationManager.getNotificationManager().createNotification("Warning", "It is advised to not enable LongJump for 5 seconds!", true, 5000, Type.WARNING, Color.YELLOW);
    }
	
	@Override
    public void onDisable() {
        mc.thePlayer.speedInAir = 0.02F;
        mc.timer.timerSpeed = 1;
        //mc.thePlayer.motionY = -0.4; //not needed and can possible cause flagging
    }

}
