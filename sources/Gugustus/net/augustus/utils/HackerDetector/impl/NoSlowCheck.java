package net.augustus.utils.HackerDetector.impl;

import net.augustus.utils.ChatUtil;
import net.augustus.utils.HackerDetector.Check;
import net.minecraft.entity.player.EntityPlayer;

public class NoSlowCheck extends Check{
	
	public static double MAX_XZ_DIFF = 0.4D;
	//final
	public NoSlowCheck() {
		super("NoSlow", true);
	}
	
	
	
	@Override
	public boolean runCheck(EntityPlayer player) {
		MAX_XZ_DIFF = 0.4D;
		if(player.isUsingItem() || player.isBlocking()) {
			double xDiff = (player.lastTickPosX > player.posX) ? player.lastTickPosX - player.posX : player.posX - player.lastTickPosX;
			double zDiff = (player.lastTickPosZ > player.posZ) ? player.lastTickPosZ - player.posZ : player.posZ - player.lastTickPosZ;
			if(player.capabilities.isCreativeMode || player.capabilities.isFlying)
				return false;
			if(player.isPotionActive(1))
				return false;
//			if(player.hurtTime > 0)
//				return false;
			if(player.isSprinting() && player.moveForward > 0.91)
				return true;
			if(xDiff > MAX_XZ_DIFF)
				return true;
			if(zDiff > MAX_XZ_DIFF)
				return true;
		}
		return false;
	}

}
