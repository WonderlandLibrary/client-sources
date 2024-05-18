package com.kilo.mod.util;

import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityFlying;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.scoreboard.Team;

import com.kilo.manager.HackFriendManager;
import com.kilo.mod.ModuleManager;
import com.kilo.mod.toolbar.dropdown.Interactable;
import com.kilo.util.Util;

public class CombatUtil {

	protected static final Minecraft mc = Minecraft.getMinecraft();
	
	public static EntityLivingBase getEntityNearest(float range, float fov, float exist, boolean aimCon, boolean animals, boolean monsters, boolean players, boolean walls, boolean teams) {
		EntityLivingBase entity = null;
		
		for(int i = 0; i < mc.theWorld.loadedEntityList.size(); i++) {
			Entity ent = (Entity)mc.theWorld.loadedEntityList.get(i);
			
			if (!(ent instanceof EntityLivingBase)) {
				continue;
			}
			
			EntityLivingBase e = (EntityLivingBase)ent;

			if (isDisabledEntity(e, range)) {
				continue;
			}
			
			if (!(e.ticksExisted > exist*20)) {
				continue;
			}
			
			if (e instanceof EntityAnimal){
				if (!animals) {
					continue;
				}
			} else if (e instanceof EntityMob || e instanceof EntityFlying || e instanceof EntitySlime) {
				if (!monsters) {
					continue;
				}
			} else if (e instanceof EntityPlayer) {
				if (!players) {
					continue;
				}
				if (HackFriendManager.getHackFriend(e.getCommandSenderName()) != null) {
					continue;
				}
				if (teams) {
					if (mc.theWorld.getScoreboard() != null) {
						Team other = mc.theWorld.getScoreboard().getPlayersTeam(e.getCommandSenderName());
						if (other != null && mc.theWorld.getScoreboard().getPlayersTeam(mc.thePlayer.getCommandSenderName()) != null && mc.theWorld.getScoreboard().getPlayersTeam(mc.thePlayer.getCommandSenderName()).isSameTeam(other)) {
							continue;
						}
					}
				}
			} else {
				continue;
			}
			
			float newDistance = mc.thePlayer.getDistanceToEntity((EntityLivingBase)e);
			float[] newRotations = CombatUtil.getRotationToEntity((EntityLivingBase)e);
			
			if (Math.abs(Util.angleDifference(mc.thePlayer.rotationYaw%360, newRotations[0])) > fov) {
				continue;
			}

			if (!Util.canSeeEntity(mc.thePlayer, (EntityLivingBase)e) && !walls) {
				continue;
			}
			
			if (entity == null) {
				entity = (EntityLivingBase)e;
				continue;
			}
			
			float oldDistance = mc.thePlayer.getDistanceToEntity(entity);
			
			if (!aimCon) {
				if (newDistance < oldDistance) {
					entity = (EntityLivingBase)e;
				}
				continue;
			}
			
			float[] oldRotations = CombatUtil.getRotationToEntity(entity);

			float oldYaw = Math.abs(Util.angleDifference(mc.thePlayer.rotationYaw%360, oldRotations[0]));
			float oldPitch = Math.abs(Util.angleDifference(mc.thePlayer.rotationPitch, oldRotations[1]));
			float newYaw = Math.abs(Util.angleDifference(mc.thePlayer.rotationYaw%360, newRotations[0]));
			float newPitch = Math.abs(Util.angleDifference(mc.thePlayer.rotationPitch, newRotations[1]));

			float oldAngle = (float)Math.sqrt(Math.pow(oldYaw, 2)+Math.pow(oldPitch, 2));
			float newAngle = (float)Math.sqrt(Math.pow(newYaw, 2)+Math.pow(newPitch, 2));
			
			if (newAngle < oldAngle) {
				entity = (EntityLivingBase)e;
				continue;
			}
		}
		
		return entity;
	}
	
	public static EntityLivingBase getEntityNearestForAttack(float range, float fov, float exist, boolean animals, boolean monsters, boolean players, boolean others, boolean invis, boolean wall, boolean teams) {
		EntityLivingBase entity = null;
		
		for(int i = 0; i < mc.theWorld.loadedEntityList.size(); i++) {
			Entity ent = (Entity)mc.theWorld.loadedEntityList.get(i);
			
			if (!(ent instanceof EntityLivingBase)) {
				continue;
			}
			
			EntityLivingBase e = (EntityLivingBase)ent;
			
			if (e.isInvisible() || e.isInvisibleToPlayer(mc.thePlayer)) {
				if (invis) {
					continue;
				}
			}
			
			if (isDisabledEntity(e, range)) {
				continue;
			}
			
			if (e.deathTime > 0) {
				continue;
			}
			
			if (!(e.ticksExisted > exist*20)) {
				continue;
			}
			
			if (e instanceof EntityAnimal){
				if (!animals) {
					continue;
				}
			} else if (e instanceof EntityMob || e instanceof EntityFlying || e instanceof EntitySlime) {
				if (!monsters) {
					continue;
				}
			} else if (e instanceof EntityPlayer) {
				if (!players) {
					continue;
				}
				if (HackFriendManager.getHackFriend(e.getCommandSenderName()) != null) {
					continue;
				}
				if (teams) {
					Team other = mc.theWorld.getScoreboard().getPlayersTeam(e.getCommandSenderName());
					if (mc.theWorld.getScoreboard() != null && mc.theWorld.getScoreboard().getPlayersTeam(mc.thePlayer.getCommandSenderName()) != null && mc.theWorld.getScoreboard().getPlayersTeam(mc.thePlayer.getCommandSenderName()).isSameTeam(other)) {
						continue;
					}
				}
			} else {
				if (!others) {
					continue;
				}
			}
			
			float newDistance = mc.thePlayer.getDistanceToEntity((EntityLivingBase)e);
			float[] newRotations = CombatUtil.getRotationToEntity((EntityLivingBase)e);
			
			if (Math.abs(Util.angleDifference(mc.thePlayer.rotationYaw%360, newRotations[0])) > fov) {
				continue;
			}
			
			if (!wall && !Util.canSeeEntity(mc.thePlayer, (EntityLivingBase)e)) {
				continue;
			}
			
			if (entity == null) {
				entity = (EntityLivingBase)e;
				continue;
			}
			
			float oldDistance = mc.thePlayer.getDistanceToEntity(entity);
			float[] oldRotations = CombatUtil.getRotationToEntity(entity);
			
			float oldYaw = Math.abs(Util.angleDifference(mc.thePlayer.rotationYaw%360, oldRotations[0]));
			float oldPitch = Math.abs(Util.angleDifference(mc.thePlayer.rotationPitch, oldRotations[1]));
			float newYaw = Math.abs(Util.angleDifference(mc.thePlayer.rotationYaw%360, newRotations[0]));
			float newPitch = Math.abs(Util.angleDifference(mc.thePlayer.rotationPitch, newRotations[1]));
			
			float oldAngle = (float)Math.sqrt(Math.pow(oldYaw, 2)+Math.pow(oldPitch, 2));
			float newAngle = (float)Math.sqrt(Math.pow(newYaw, 2)+Math.pow(newPitch, 2));
			
			if (newAngle < oldAngle) {
				entity = (EntityLivingBase)e;
				continue;
			}
		}
		
		return entity;
	}
	
	public static boolean isDisabledEntity(EntityLivingBase e, float range) {
		if (!(e instanceof EntityLivingBase)) {
			return true;
		}

		EntityLivingBase elb = (EntityLivingBase)e;
		
		if (elb == mc.thePlayer) {
			return true;
		}
		
		if (elb.getCommandSenderName().equalsIgnoreCase(mc.thePlayer.getCommandSenderName())) {
			return true;
		}
		
		if (elb.getUniqueID().equals(mc.thePlayer.getUniqueID())) {
			return true;
		}
		
		if (mc.thePlayer.getDistanceToEntity(elb) > range) {
			return true;
		}
		
		return false;
	}
	
	public static boolean isDisabledEntity(EntityLivingBase e) {
		return isDisabledEntity(e, 1000);
	}
	
	public static float[] getRotationToEntity(EntityLivingBase entity) {
		double pX = mc.thePlayer.posX;
		double pY = mc.thePlayer.posY+mc.thePlayer.getEyeHeight();
		double pZ = mc.thePlayer.posZ;

		double eX = entity.posX;
		double eY = entity.posY+entity.getEyeHeight();
		double eZ = entity.posZ;
		
		double dX = pX-eX;
		double dY = pY-eY;
		double dZ = pZ-eZ;
		double dH = Math.sqrt(Math.pow(dX, 2)+Math.pow(dZ, 2));

		float yaw = 0;
		float pitch = 0;
		
		yaw = (float)(Math.toDegrees(Math.atan2(dZ, dX))+90);
		pitch = (float)(Math.toDegrees(Math.atan2(dH, dY)));
		
		return new float[] {yaw, 90-pitch};
	}
}
