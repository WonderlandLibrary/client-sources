package net.augustus.utils.HackerDetector.impl;

import net.augustus.utils.HackerDetector.Check;
import net.augustus.utils.interfaces.MC;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

public class ReachCheck extends Check{
	
	public ReachCheck() {
		super("Reach", true);
	}
	
	
	
	@Override
	public boolean runCheck(EntityPlayer player) {
		int entities = 0;
		Entity closestEntity = null;
		for(Entity e : MC.mc.theWorld.loadedEntityList) {
			if(e != MC.mc.thePlayer) {
				if(e.getDistanceToEntity(player) < 6) {
					entities++;
					if(closestEntity == null)
						closestEntity = e;
					if(closestEntity.getDistanceToEntity(player) > e.getDistanceToEntity(player)) {
						closestEntity = e;
					}
				}
			}
		}
		if(entities > 3) {
			//prevent falses?
			return false;
		}
		if(player.swingProgress > 0 && ((EntityLivingBase)closestEntity).hurtTime > 0) {
			if(player.getDistanceToEntity(closestEntity) > 3.3) {
				return true;
			}
		}
		return false;
	}

}
