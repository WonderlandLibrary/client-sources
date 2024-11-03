package net.augustus.utils.HackerDetector.impl;

import net.augustus.utils.HackerDetector.Check;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

public class VelocityCheck extends Check{
	
	public VelocityCheck() {
		super("Velocity/NoKB", true);
	}
	
	
	
	@Override
	public boolean runCheck(EntityPlayer player) {
		if(player.lastTickPosY != player.posY)
			return false;
		if(player.isBurning() || player.isInWater() || player.isEntityInsideOpaqueBlock())
			return false;
		double motionCap = 0;
		if(((EntityLivingBase)player).hurtTime > 8 && (((EntityLivingBase)player).motionX <= motionCap || ((EntityLivingBase)player).motionY <= motionCap || ((EntityLivingBase)player).motionZ <= motionCap)) {
			return true;
		}
		if(((EntityLivingBase)player).hurtTime > 0 && ((EntityLivingBase)player).isJumping) {
			return true;
		}
		return false;
	}

}
