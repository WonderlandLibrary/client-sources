package com.darkcart.xdolf.mods.player;

import org.lwjgl.input.Keyboard;

import com.darkcart.xdolf.Module;
import com.darkcart.xdolf.util.Category;
import com.darkcart.xdolf.util.Value;

/**
 * Credits to 0x22 for this.
 */

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.MovementInput;

public class EntitySpeed extends Module
{
	
	public static Value entitySpeed = new Value("Entity Speed");
	
	public EntitySpeed()
	{
		super("EntitySpeed", "Speedhack for entities.", Keyboard.KEYBOARD_SIZE, 0xFFFFFF, Category.PLAYER);
	}
	
	@Override
	public void onUpdate(EntityPlayerSP player) {
		if(isEnabled()) {
			if (player.ridingEntity != null) {
				MovementInput movementInput = player.movementInput;
				double forward = movementInput.moveForward;
				double strafe = movementInput.moveStrafe;
				float yaw = player.rotationYaw;
				if ((forward == 0.0D) && (strafe == 0.0D)) {
					player.ridingEntity.motionX = 0.0D;
			        player.ridingEntity.motionZ = 0.0D;
				}else{
					if (forward != 0.0D) {
						if (strafe > 0.0D) {
							yaw += (forward > 0.0D ? -45 : 45);
						}else if (strafe < 0.0D) {
							yaw += (forward > 0.0D ? 45 : -45);
						}
						strafe = 0.0D;
						if (forward > 0.0D) {
							forward = 1.0D;
						}else if (forward < 0.0D) {
							forward = -1.0D;
						}
					}
					player.ridingEntity.motionX = (forward * entitySpeed.getValue() * Math.cos(Math.toRadians(yaw + 90.0F)) + strafe * entitySpeed.getValue() * Math.sin(Math.toRadians(yaw + 90.0F)));
					player.ridingEntity.motionZ = (forward * entitySpeed.getValue() * Math.sin(Math.toRadians(yaw + 90.0F)) - strafe * entitySpeed.getValue() * Math.cos(Math.toRadians(yaw + 90.0F)));
				}
			}
		}
	}
}