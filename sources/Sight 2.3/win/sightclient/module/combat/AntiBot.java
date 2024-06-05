package win.sightclient.module.combat;

import java.util.ArrayList;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.server.S18PacketEntityTeleport;
import net.minecraft.util.MathHelper;
import win.sightclient.event.Event;
import win.sightclient.event.events.client.EventPacket;
import win.sightclient.event.events.client.EventUpdate;
import win.sightclient.module.Category;
import win.sightclient.module.Module;
import win.sightclient.module.settings.ModeSetting;
import win.sightclient.module.settings.Setting;
import win.sightclient.utils.management.Manager;
import win.sightclient.utils.management.Manager.GAMEMODE;
import win.sightclient.utils.minecraft.ServerUtils;

public class AntiBot extends Module {
	
	private static ArrayList<EntityPlayer> bots = new ArrayList<EntityPlayer>();
	private static ModeSetting mode;
	
	public AntiBot() {
		super("AntiBot", Category.COMBAT);
		mode = new ModeSetting("Mode", this, new String[] {"Hypixel, Mineplex"});
	}
	
	public void onEvent(Event e) {
		if (mc.theWorld == null || mc.thePlayer == null || this.mode.getValue().equalsIgnoreCase("Mineplex")) {
			this.bots.clear();
			return;
		}
		
		if (e instanceof EventPacket) {
			EventPacket ep = (EventPacket)e;
			if (ep.getPacket() instanceof S18PacketEntityTeleport && ServerUtils.isOnHypixel() && Manager.isGameMode(GAMEMODE.SKYWARS)) {
				S18PacketEntityTeleport packet = (S18PacketEntityTeleport)ep.getPacket();
				Entity entity = mc.theWorld.getEntityByID(packet.getEntityId());
				if (entity != null && entity != mc.thePlayer & entity instanceof EntityPlayer && entity.isInvisible()) {
					bots.add((EntityPlayer) entity);
				}
			}
		} else if (e instanceof EventUpdate) {
			EventUpdate eu = (EventUpdate)e;
			if (eu.isPre()) {
				for (Entity entity : mc.theWorld.loadedEntityList) {
					if ((entity instanceof EntityPlayer)) {
						EntityPlayer player = (EntityPlayer)entity; 
						if (player.getName().startsWith("�c") && !isInTablist(player) && player.isInvisible()) {
							if (!bots.contains(player)) {
								bots.add(player);
							}
						}
						if (player.isInvisible() && !bots.contains(player)) {
							float xDist = (float) (mc.thePlayer.posX - player.posX);
							float zDist = (float) (mc.thePlayer.posZ - player.posZ);
							double horizontalReaach = MathHelper.sqrt_float(xDist * xDist + zDist * zDist);
							if (horizontalReaach < .6) {
								double vert = mc.thePlayer.posY - player.posY;
								if (vert <= 5 && vert > 1) {
									if (mc.thePlayer.ticksExisted % 5 == 0) {
										bots.add(player);
									}
								}
							}
						}
						if (bots.contains(player) && player.hurtTime > 0 || player.fallDistance > 0) {
							bots.remove(player);
						}
					}
				}
				
				if (!bots.isEmpty() && mc.thePlayer.ticksExisted % 20 == 0) {
					for (int i = 0; i < bots.size(); i++) {
						if (bots.contains(bots.get(i))) {
							if (!mc.theWorld.playerEntities.contains(bots.get(i))) {
								bots.remove(bots.get(i));
							}
						}	
					}
				}
			}
		}
	}
	
	@Override
	public void onEnable() {
		super.onEnable();
		bots.clear();
	}
	
	@Override
	public void onDisable() {
		super.onDisable();
		this.bots.clear();
	}
	
	private boolean isInTablist(EntityLivingBase player){
		if (mc.isSingleplayer()) {
			return true;
		}
		return ServerUtils.getTabPlayerList().contains(player);
	}
	
	public static boolean isBot(EntityPlayer ep) {
		if (mode.getValue().equalsIgnoreCase("Mineplex")) {
			return ep.groundTicks < 20;
		}
		return bots.contains(ep);
	}
}