package com.kilo.mod.all;

import net.minecraft.client.Minecraft;
import net.minecraft.potion.Potion;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovementInput;

import org.lwjgl.input.Keyboard;

import com.kilo.mod.Category;
import com.kilo.mod.Module;
import com.kilo.mod.toolbar.dropdown.Interactable;
import com.kilo.util.ClientUtils;
import com.kilo.util.Timer;
import com.kilo.util.Util;

public class Sprint extends Module {

	public Sprint(String finder, Category category, String name, String description, String warning) {
		super(finder, category, name, description, warning);
		
		addOption("Directional", "Sprint in all directions", Interactable.TYPE.CHECKBOX, true, null, false);
		addOption("Fast", "Bouncy speed bypass", Interactable.TYPE.CHECKBOX, true, null, false);
	}
	
	public void onPlayerMovePostUpdate() {
		if (mc.gameSettings.keyBindSneak.isKeyDown()) {
			return;
		}
		
		if (Util.makeBoolean(getOptionValue("directional"))) {
			if (mc.thePlayer.moveForward != 0 || mc.thePlayer.moveStrafing != 0) {
				mc.thePlayer.setSprinting(true);
			}
		} else {
			if (mc.thePlayer.moveForward > 0) {
				mc.thePlayer.setSprinting(true);
			}
		}
		
		if (Util.makeBoolean(getOptionValue("fast"))) {

			if (Math.abs(mc.thePlayer.moveForward)+Math.abs(mc.thePlayer.moveStrafing) > 0.1f) {
	            MovementInput movementInput = mc.thePlayer.movementInput;
	            float forward = movementInput.moveForward;
	            float strafe = movementInput.moveStrafe;
	            float yaw = Minecraft.getMinecraft().thePlayer.rotationYaw;
	             if (forward != 0.0f) {
	                if (strafe >= 1.0f) {
	                    yaw += ((forward > 0.0f) ? -45 : 45);
	                    strafe = 0.0f;
	                }
	                else if (strafe <= -1.0f) {
	                    yaw += ((forward > 0.0f) ? 45 : -45);
	                    strafe = 0.0f;
	                }
	                if (forward > 0.0f) {
	                    forward = 1.0f;
	                }
	                else if (forward < 0.0f) {
	                    forward = -1.0f;
	                }
	                double mx = Math.cos(Math.toRadians(yaw + 90.0f));
	                double mz = Math.sin(Math.toRadians(yaw + 90.0f));
	                double motionX = forward * 100.55 * mx + strafe * 100.55 * mz;
	                double motionZ = forward * 100.55 * mz - strafe * 100.55 * mx;
	            }
				if (mc.thePlayer.onGround && !mc.thePlayer.isInWater() && !mc.thePlayer.isInLava()) {
					mc.thePlayer.motionY = 0.21f;
		            mc.thePlayer.speedInAir = 0.021f;
					
					float dir = mc.thePlayer.rotationYaw;
					if (mc.thePlayer.moveForward < 0) {
						dir+= 180;
					}
					if (mc.thePlayer.moveStrafing > 0) {
						dir-= 90*(mc.thePlayer.moveForward>0?0.5f:mc.thePlayer.moveForward<0?-0.5f:1);
					}
					if (mc.thePlayer.moveStrafing < 0) {
						dir+= 90*(mc.thePlayer.moveForward>0?0.5f:mc.thePlayer.moveForward<0?-0.5f:1);
					}
		            float var1 = dir * 0.017453292F;
		            if (!mc.gameSettings.keyBindJump.isKeyDown()) {
						
						ClientUtils.player().motionX *= 1.7;
						ClientUtils.player().motionZ *= 1.7;
						ClientUtils.player().motionX /= 1.1;
						ClientUtils.player().motionZ /= 1.1;
						mc.thePlayer.motionY -= 0.02f;

		          }
				} else {
					if (mc.thePlayer.motionY < 0) {
						mc.timer.timerSpeed = 1.18f;
					} else {
						mc.timer.timerSpeed = 1f;
					}
				}
			} else {
				mc.timer.timerSpeed = 1f;
			}
		}
	}
	
	public void onPlayerPostUpdate() {
		if (mc.gameSettings.keyBindSneak.isKeyDown()) {
			mc.timer.timerSpeed = 1f;
			return;
		}
		
		if (Util.makeBoolean(getOptionValue("fast"))) {
			if (Math.abs(mc.thePlayer.moveForward)+Math.abs(mc.thePlayer.moveStrafing) > 0.1f) {
				if (!mc.thePlayer.onGround && !mc.gameSettings.keyBindJump.isKeyDown()) {
					mc.thePlayer.motionY = Math.min(mc.thePlayer.motionY, 0f);
				}
			}
		}
	}
	
	
}
