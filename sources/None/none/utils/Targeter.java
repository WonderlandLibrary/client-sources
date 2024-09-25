package none.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import none.Client;
import none.event.events.EventChat;
import none.module.modules.combat.Antibot;

public class Targeter {
	private static Minecraft mc = Minecraft.getMinecraft();
	public static boolean player = false;
	public static boolean other = false;
	public static boolean invisible = false;
	public static GameType gameType = GameType.Normal;
	
	public static GameType getGameType() {
		return gameType;
	}

	public static boolean isPlayer() {
		return player;
	}

	public static boolean isOther() {
		return other;
	}

	public static boolean isInvisible() {
		return invisible;
	}

	public static void setGameType(GameType gameType) {
		Targeter.gameType = gameType;
	}

	public static void setPlayer(boolean player) {
		Targeter.player = player;
	}

	public static void setOther(boolean other) {
		Targeter.other = other;
	}

	public static void setInvisible(boolean invisible) {
		Targeter.invisible = invisible;
	}

	public static boolean isTarget(Entity entity) {
		
		if (entity instanceof EntityLivingBase && gameType == GameType.Normal) {
			EntityLivingBase entity2 = (EntityLivingBase) entity;
			if (entity2.getHealth() <= 0F) {
				return false;
			}
			
			if (other && !(entity instanceof EntityPlayer)) {
				if (!invisible && entity.isInvisible()) {
					return false;
				}
				return true;
			}
			
			if (player && entity instanceof EntityPlayer) {
				EntityPlayer player = (EntityPlayer) entity;
				if (Antibot.getInvalid().contains(player) || Client.instance.nameList.contains(player.getName()) || Client.instance.whiteList.contains(player.getName())) {
					return false;
				}
				
				if (!invisible && player.isInvisible()) {
					return false;
				}
				return true;
			}
			
			if (player && other && entity instanceof EntityLivingBase) {
				if (entity instanceof EntityPlayerSP) {
					return false;
				}
				if (entity instanceof EntityPlayer) {
					EntityPlayer player = (EntityPlayer) entity;
					if (Antibot.getInvalid().contains(player) || Client.instance.nameList.contains(player.getName()) || Client.instance.whiteList.contains(player.getName())) {
						return false;
					}
				}
				
				if (!invisible && entity.isInvisible()) {
					return false;
				}
				return true;
			}
		}else if (entity instanceof EntityLivingBase && GameType.VillagerDef == gameType) {
			return entity instanceof EntityZombie;
		}
		
		return false;
	}
	
	public enum GameType {
		Normal("Normal"),
		VillagerDef("VillagerDef");
		
		private String name;
		
		GameType(String name) {
	        this.name = name;
	    }

	    @Override
	    public String toString() {
	        return name;
	    }
	}
}
