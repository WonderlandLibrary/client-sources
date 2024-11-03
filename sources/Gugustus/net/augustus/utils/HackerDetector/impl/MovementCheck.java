package net.augustus.utils.HackerDetector.impl;

import net.augustus.utils.ChatUtil;
import net.augustus.utils.HackerDetector.Check;
import net.minecraft.entity.player.EntityPlayer;

public class MovementCheck extends Check{
	
	public static final double MAX_XZ_DIFF = 0.65D;
	
	public MovementCheck() {
		super("Movement", true);
	}
	
	
	
	@Override
	public boolean runCheck(EntityPlayer player) {
		double xDiff = (player.lastTickPosX > player.posX) ? player.lastTickPosX - player.posX : player.posX - player.lastTickPosX;
		double zDiff = (player.lastTickPosZ > player.posZ) ? player.lastTickPosZ - player.posZ : player.posZ - player.lastTickPosZ;
		if(player.capabilities.isCreativeMode || player.capabilities.isFlying)
			return false;
		if(player.isPotionActive(1))
			return false;
		if(player.hurtTime > 0)
			return false;
		//teleport check's job ;)
		if(xDiff > 5 || zDiff > 5)
			return false;
		
		if(xDiff > MAX_XZ_DIFF)
			return true;
		if(zDiff > MAX_XZ_DIFF)
			return true;
		return false;
	}

}
