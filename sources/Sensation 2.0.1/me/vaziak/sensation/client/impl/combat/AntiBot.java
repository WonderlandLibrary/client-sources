package me.vaziak.sensation.client.impl.combat;

import java.util.ArrayList;

import me.vaziak.sensation.client.api.Category;
import me.vaziak.sensation.client.api.Module;
import me.vaziak.sensation.client.api.event.annotations.Collect;
import me.vaziak.sensation.client.api.event.events.PlayerUpdateEvent;
import me.vaziak.sensation.client.api.event.events.ProcessPacketEvent;
import me.vaziak.sensation.client.api.property.impl.StringsProperty;
import me.vaziak.sensation.utils.math.TimerUtil;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.server.S02PacketChat;
/**
 * Made by Jonathan H. (Niada)
 *
 * Sensation development - Since 8/25/19
 **/
public class AntiBot extends Module { 

	private TimerUtil botClearStopwatch;
	public ArrayList<EntityLivingBase> ignoredEntities;
	public ArrayList<EntityLivingBase> whitelistedEntity;
    public StringsProperty antiBot = new StringsProperty("Mode", "Ignore certain bots", false, true, new String[]{"Tablist", "NaN Health", "Range", "Watchdog", "PvPTemple", "Skidrizon"});

    public AntiBot() {
        super("Anti Bot", Category.COMBAT);
        registerValue(antiBot);
		botClearStopwatch = new TimerUtil();
		ignoredEntities = new ArrayList<>();
		whitelistedEntity = new ArrayList<>();
    }

    @Collect
    public void onPlayerUpdate(PlayerUpdateEvent playerUpdateEvent) {
    	setMode(antiBot.getSelectedStrings().get(0));
    }
    
    public void antibot() {
    	if (botClearStopwatch.hasPassed(20000)) {
			if (ignoredEntities.size() > 200) {
				ignoredEntities.clear();
			}
			if (whitelistedEntity.size() > 200) {
				whitelistedEntity.clear();
			}
			botClearStopwatch.reset();
		}
		mc.theWorld.getLoadedEntityList().forEach(entity -> {
			if (entity != mc.thePlayer && entity != null && entity instanceof EntityLivingBase) {
				if (antiBot.getValue().get("Watchdog") && onServer("hypixel")) {
					/* Hypixel antibot */
					if (entity instanceof EntityPlayer) {
						EntityPlayer watchdoggy = (EntityPlayer) entity;
						boolean watchdoge =  (watchdoggy.getName().contains("\247") || watchdoggy.getDisplayName().getFormattedText().startsWith("ยง") || watchdoggy.getDisplayName().getFormattedText().toLowerCase().contains("npc")  || !isInTablist(watchdoggy) && watchdoggy.isInvisible());
						if (watchdoge) {
							blacklist(watchdoggy);
						} 
						if (watchdoggy.hurtTime > 1) {
							if (ignoredEntities.contains(watchdoggy)) {
								ignoredEntities.remove(watchdoggy);
							}
						}
					}
				}
				
				/* Retard antibot */
				if (antiBot.getValue().get("Tablist")) {
					if (entity instanceof EntityPlayer) {
						EntityPlayer playerentity = (EntityPlayer) entity;
						if (!isInTablist(playerentity)) {
							blacklist(playerentity);
						}
					}
				}
				/* Mineplex antibot */
				if (antiBot.getValue().get("NaN Health")) {
					if (entity.ticksExisted < mc.thePlayer.ticksExisted && ((EntityLivingBase) entity).getHealth() < 20 && ((EntityLivingBase) entity).getHealth() > 0) {
						blacklist((EntityLivingBase) entity);
					}
					if (!String.valueOf(((EntityLivingBase) entity).getHealth()).contains("NaN") && entity instanceof EntityPlayer) {
						blacklist((EntityLivingBase) entity);
					}
				}

				/* Skidrizon antibot */
			if (antiBot.getValue().get("Skidrizon")) {
					if (mc.thePlayer.canEntityBeSeen(entity) && entity instanceof EntityPlayer) {
						EntityPlayer ent = (EntityPlayer)entity;
						
						double xDist = ent.posX - ent.prevPosX;
						double zDist = ent.posZ - ent.prevPosZ; 
						if (ent.maxDistanceFromLocalPlayer > 100 && Math.sqrt(xDist * xDist + zDist * zDist) > 1.1 || ent.isEntityInsideOpaqueBlock()) {
							blacklist(ent);
						} 
						
						if (ent.hasBeenOnGround && ent.ticksGroundSynced > 10 && Math.abs(ent.ticksGroundDesycned - ent.ticksGroundSynced) > 30) {
							whitelist(ent);
						}
					}
				}

				/* PvPtemple antibot */
				if (antiBot.getValue().get("PvPTemple")) {
					if (mc.thePlayer.canEntityBeSeen(entity) && entity instanceof EntityPlayer) {
						EntityPlayer ent = (EntityPlayer)entity;
						if (ent.maxDistanceFromLocalPlayer > 9.52901840209961 && ent.ticksGroundSynced > 1 && ent.hasBeenOnGround) {
							whitelist(ent);
						}
						
						if (ent.getDisplayName().getFormattedText().toLowerCase().contains("npc")) {
							blacklist(ent);
						}
					}
				}

				if (antiBot.getValue().get("Range")) {
					EntityLivingBase e = (EntityLivingBase) entity;
					if (!e.isInvisible() && mc.thePlayer.getDistanceToEntity(entity) >= 15 && e instanceof EntityPlayer) {
						whitelist(e);
						
					}
					if (e instanceof EntityPlayer) {
						if (e.isInvisible() && !isInTablist((EntityPlayer) e)) {
							blacklist(e);
						}
					}
				}
			}
		}); 
    }
    
    public void whitelist(EntityLivingBase e) {
		if (!whitelistedEntity.contains(e)) {
			whitelistedEntity.add(e);
		}
    }
    
    public void blacklist(EntityLivingBase e) {
		if (!ignoredEntities.contains(e)) {
			ignoredEntities.add(e);
		}
    }
    
	private boolean isInTablist(EntityPlayer player) {
		if (mc.isSingleplayer()) {
			return true;
		}
		for (Object o : mc.getNetHandler().getPlayerInfoMap()) {
			NetworkPlayerInfo playerInfo = (NetworkPlayerInfo) o;
			if (playerInfo.getGameProfile().getName().equalsIgnoreCase(player.getName())) {
				return true;
			}
		}
		return false;
	}


	/*Packet event*/
	@Collect
	public void onProcessPacket(ProcessPacketEvent event) {
		if (event.getPacket() instanceof S02PacketChat && onServer("hypixel")) {
			S02PacketChat chat = (S02PacketChat) event.getPacket();
			if (chat.getChatComponent().getUnformattedText().toLowerCase().contains("cages opened")) {
				ignoredEntities.clear();
				whitelistedEntity.clear();
			}
		}
	}

}
