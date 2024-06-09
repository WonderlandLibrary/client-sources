/**
 * 
 */
package cafe.kagu.kagu.mods.impl.player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

import cafe.kagu.kagu.eventBus.EventHandler;
import cafe.kagu.kagu.eventBus.Handler;
import cafe.kagu.kagu.eventBus.impl.EventCheatProcessTick;
import cafe.kagu.kagu.eventBus.impl.EventPacketReceive;
import cafe.kagu.kagu.eventBus.impl.EventTick;
import cafe.kagu.kagu.mods.Module;
import cafe.kagu.kagu.settings.impl.ModeSetting;
import cafe.kagu.kagu.utils.ChatUtils;
import cafe.kagu.kagu.utils.TimerUtil;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.play.server.S26PacketMapChunkBulk;

/**
 * @author lavaflowglow
 *
 */
public class ModAntiBot extends Module {
	
	public ModAntiBot() {
		super("AntiBot", Category.PLAYER);
		setSettings(mode);
	}
	
	private ModeSetting mode = new ModeSetting("Mode", "Hypixel", "Hypixel");
	
	private List<EntityPlayer> hypixelBots = new CopyOnWriteArrayList<EntityPlayer>();
	private List<EntityPlayer> hypixelWhitelistedPlayers = new CopyOnWriteArrayList<EntityPlayer>();
	
	@Override
	public void onDisable() {
		hypixelBots.clear();
		hypixelWhitelistedPlayers.clear();
	}
	
	@EventHandler
	private Handler<EventTick> onTick = e -> {
		if (e.isPost())
			return;
		switch (mode.getMode()) {
			case "Hypixel": {
				if (mc.thePlayer.ticksExisted == 0) {
					hypixelBots.clear();
					hypixelWhitelistedPlayers.clear();
				}
			}break;
		}
	};
	
	private TimerUtil cheatTickSaveResources = new TimerUtil();
	
	@EventHandler
	private Handler<EventCheatProcessTick> onCheatTick = e -> {
		if (e.isPost() || !cheatTickSaveResources.hasTimeElapsed(50, false))
			return;
		switch (mode.getMode()) {
			case "Hypixel": {
				List<Entity> loadedEntityList = new ArrayList<Entity>(mc.theWorld.loadedEntityList);
				for (Entity ent : loadedEntityList) {
					if (!(ent instanceof EntityPlayer) || ent == mc.thePlayer || hypixelWhitelistedPlayers.contains(ent))
						continue;
					
					EntityPlayer player = (EntityPlayer)ent;
					NetworkPlayerInfo npi = mc.getNetHandler().getPlayerInfo(player.getUniqueID());
					boolean isBot = npi == null || npi.getGameProfile() == null || npi.getResponseTime() > 1;
					if (isBot && !hypixelBots.contains(player))
						hypixelBots.add(player);
					if (!isBot && hypixelBots.contains(player)) {
						hypixelBots.remove(player);
						hypixelWhitelistedPlayers.add(player);
					}
					setInfo(hypixelBots.size() + " bots");
				}
			}
		}
		cheatTickSaveResources.reset();
	};
	
	/**
	 * @param player The player to check
	 * @return true if the player is a bot, otherwise false
	 */
	public boolean isBot(EntityPlayer player) {
		switch (mode.getMode()) {
			case "Hypixel":{
				return hypixelBots.contains(player);
			}
		}
		return false;
	}
	
}
