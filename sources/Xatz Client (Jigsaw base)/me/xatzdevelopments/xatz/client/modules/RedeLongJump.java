package me.xatzdevelopments.xatz.client.modules;

import org.lwjgl.input.Keyboard;

import net.minecraft.potion.Potion;
import me.xatzdevelopments.xatz.client.module.state.Category;
import me.xatzdevelopments.xatz.module.Module;

public class RedeLongJump extends Module {

	public RedeLongJump() {
		super("RedeLongJump", Keyboard.KEY_NONE, Category.HIDDEN, "LongJump for redesky!");
	} // Combat
	public void onUpdate() {
	    if((mc.gameSettings.keyBindForward.isKeyDown() || mc.gameSettings.keyBindLeft.isKeyDown() || mc.gameSettings.keyBindRight.isKeyDown() || mc.gameSettings.keyBindBack.isKeyDown()) && mc.gameSettings.keyBindJump.isKeyDown()) {
            float dir = mc.thePlayer.rotationYaw + ((mc.thePlayer.moveForward < 0) ? 180 : 0) + ((mc.thePlayer.moveStrafing > 0) ? (-90F * ((mc.thePlayer.moveForward < 0) ? -.5F : ((mc.thePlayer.moveForward > 0) ? .4F : 1F))) : 0);
            float xDir = (float)Math.cos((dir + 90F) * Math.PI / 180);
            float zDir = (float)Math.sin((dir + 90F) * Math.PI / 180);
            if(mc.thePlayer.isCollidedVertically && (mc.gameSettings.keyBindForward.isKeyDown() || mc.gameSettings.keyBindLeft.isKeyDown() || mc.gameSettings.keyBindRight.isKeyDown() || mc.gameSettings.keyBindBack.isKeyDown()) && mc.gameSettings.keyBindJump.isKeyDown()) {
                mc.thePlayer.motionX = xDir * .29F;
                mc.thePlayer.motionZ = zDir * .29F;
             
            }
            if(mc.thePlayer.motionY == .33319999363422365 && (mc.gameSettings.keyBindForward.isKeyDown() || mc.gameSettings.keyBindLeft.isKeyDown() || mc.gameSettings.keyBindRight.isKeyDown() || mc.gameSettings.keyBindBack.isKeyDown())) {
                if (mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
                    mc.thePlayer.motionX = xDir * 1.34;
                    mc.thePlayer.motionZ = zDir * 1.34;
                } else {
                    mc.thePlayer.motionX = xDir * 1.0;
                    mc.thePlayer.motionZ = zDir * 1.0;
                    mc.thePlayer.motionY +=.09; 
                }
            }
        }
    }
	public void onEnable() {
		
	}
	public void onDisable() {
		
	}
}
