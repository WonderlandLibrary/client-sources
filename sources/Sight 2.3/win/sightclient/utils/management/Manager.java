package win.sightclient.utils.management;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C02PacketUseEntity.Action;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import win.sightclient.Sight;
import win.sightclient.event.events.client.EventPacket;
import win.sightclient.event.events.player.EventAttack;
import win.sightclient.event.events.player.EventFlag;
import win.sightclient.utils.TimerUtils;

public class Manager {

	public static void onPacket(EventPacket ep) {
		if (Minecraft.getMinecraft().theWorld == null || Minecraft.getMinecraft().thePlayer == null) {
			lastAttacked = null;
		}
		if (ep.getPacket() instanceof C02PacketUseEntity) {
			C02PacketUseEntity packet = (C02PacketUseEntity)ep.getPacket();
			if (packet.getAction() == Action.ATTACK) {
				Entity ent = packet.getEntityFromWorld(Minecraft.getMinecraft().theWorld);
				if (ent instanceof EntityLivingBase) {
					EventAttack ea = new EventAttack((EntityLivingBase) ent);
					lastAttacked = ent;
					lastAttack = System.currentTimeMillis();
					ea.call();
				} else {
					ep.setCancelled();
				}
			}
		} else if (ep.getPacket() instanceof S08PacketPlayerPosLook) {
			EventFlag ef = new EventFlag();
			ef.call();
			if (ef.isCancelled() && Minecraft.getMinecraft().thePlayer != null && Minecraft.getMinecraft().theWorld != null) {
				ep.setCancelled();
				S08PacketPlayerPosLook packet = (S08PacketPlayerPosLook)ep.getPacket();
				Minecraft.getMinecraft().getNetHandler().addToSendQueue(new C03PacketPlayer.C06PacketPlayerPosLook(
						packet.getX(), packet.getY(), packet.getZ(), packet.getYaw(), packet.getPitch(), false));
			}
		}
	}
	
	public static Entity lastAttacked;
	public static long lastAttack = System.currentTimeMillis();
	
	private static TimerUtils timer = new TimerUtils();
	private static long lastSend = System.currentTimeMillis();
	
	public static void clientTick() {
		if (timer.hasReached(5000)) {
			if (System.currentTimeMillis() - lastSend > 5500) {
				Sight.instance.setStartTime(Sight.instance.getStartTime() + (System.currentTimeMillis() - lastSend) + 5500);
			}
			lastSend = System.currentTimeMillis();
			timer.reset();
		}
	}
	
	private static GAMEMODE gamemode;
	
	public static boolean isGameMode(GAMEMODE gm) {
		return gamemode.equals(gm);
	}
	
	public static void setGameMode(GAMEMODE gm) {
		gamemode = gm;
	}
	
	public enum GAMEMODE {
		DUELS,
		BEDWARS,
		SKYWARS,
		PIT,
		UNKNOWN
	}
}
