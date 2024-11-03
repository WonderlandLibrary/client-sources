package net.augustus.utils.HackerDetector.impl;

import net.augustus.utils.HackerDetector.Check;
import net.minecraft.entity.player.EntityPlayer;

public class TeleportCheck extends Check{
	
	public static final double MAX_XZ_DIFF = 5;
	
	public TeleportCheck() {
		super("Teleport", true);
	}
	
	
	
	@Override
	public boolean runCheck(EntityPlayer player) {
		double xDiff = (player.lastTickPosX > player.posX) ? player.lastTickPosX - player.posX : player.posX - player.lastTickPosX;
		double zDiff = (player.lastTickPosZ > player.posZ) ? player.lastTickPosZ - player.posZ : player.posZ - player.lastTickPosZ;
		if(player.capabilities.isCreativeMode)
			return false;
		if(xDiff > MAX_XZ_DIFF)
			return true;
		if(zDiff > MAX_XZ_DIFF)
			return true;
		return false;
	}

}
