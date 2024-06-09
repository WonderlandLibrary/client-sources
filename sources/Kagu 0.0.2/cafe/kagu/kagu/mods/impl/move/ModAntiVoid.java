/**
 * 
 */
package cafe.kagu.kagu.mods.impl.move;

import java.util.LinkedList;
import java.util.Queue;

import cafe.kagu.kagu.Kagu;
import cafe.kagu.kagu.eventBus.EventHandler;
import cafe.kagu.kagu.eventBus.Handler;
import cafe.kagu.kagu.eventBus.impl.EventChatSendMessage;
import cafe.kagu.kagu.eventBus.impl.EventCheatProcessTick;
import cafe.kagu.kagu.eventBus.impl.EventPacketReceive;
import cafe.kagu.kagu.eventBus.impl.EventPacketSend;
import cafe.kagu.kagu.eventBus.impl.EventPlayerUpdate;
import cafe.kagu.kagu.mods.Module;
import cafe.kagu.kagu.mods.ModuleManager;
import cafe.kagu.kagu.mods.impl.exploit.ModBlink;
import cafe.kagu.kagu.mods.impl.player.ModNoFall;
import cafe.kagu.kagu.mods.impl.visual.ModTargetHud;
import cafe.kagu.kagu.settings.impl.BooleanSetting;
import cafe.kagu.kagu.settings.impl.DoubleSetting;
import cafe.kagu.kagu.settings.impl.IntegerSetting;
import cafe.kagu.kagu.settings.impl.ModeSetting;
import cafe.kagu.kagu.utils.MovementUtils;
import cafe.kagu.kagu.utils.TimerUtil;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C00PacketKeepAlive;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C0FPacketConfirmTransaction;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;

/**
 * @author DistastefulBannock
 *
 */
public class ModAntiVoid extends Module {
	
	public ModAntiVoid() {
		super("AntiVoid", Category.MOVEMENT);
		setSettings(mode, fallDistanceSetting, cooldown, restoreMotion, resetCooldownOnCatch, resetCooldownOnS08, dontDropPingPackets, dontBlinkPingPackets);
	}
	
	// Settings
	private ModeSetting mode = new ModeSetting("Mode", "Blink", "Blink", "Hypixel Blink");
	private DoubleSetting fallDistanceSetting = new DoubleSetting("Fall Distance", 6, 2, 20, 0.5);
	private IntegerSetting cooldown = new IntegerSetting("Cooldown", 250, 0, 2500, 50);
	private BooleanSetting restoreMotion = new BooleanSetting("Restore Motion", true).setDependency(() -> mode.is("Blink"));
	private BooleanSetting resetCooldownOnCatch = new BooleanSetting("Reset Cooldown on Catch", true).setDependency(() -> mode.is("Blink") || mode.is("Hypixel Blink"));
	private BooleanSetting resetCooldownOnS08 = new BooleanSetting("Reset Cooldown on S08", true).setDependency(() -> mode.is("Blink") || mode.is("Hypixel Blink"));
	private BooleanSetting dontDropPingPackets = new BooleanSetting("Don't Drop Ping Packets", true).setDependency(() -> mode.is("Blink"));
	private BooleanSetting dontBlinkPingPackets = new BooleanSetting("Don't Blink Ping Packets", false).setDependency(() -> mode.is("Blink") || mode.is("Hypixel Blink"));
	
	// Vars
	private boolean isOverVoid = false;
	private double[] lastSafePosition = new double[6];
	private Queue<Packet<?>> blinkPackets = new LinkedList<>();
	private double fallDistance = 0;
	private TimerUtil cooldownTimer = new TimerUtil();
	private boolean hypixelHasFlagged = false;
	
	@Override
	public void onEnable() {
		isOverVoid = MovementUtils.isOverVoid();
		lastSafePosition = null;
		fallDistance = 0;
		blinkPackets.clear();
		hypixelHasFlagged = false;
	}
	
	@EventHandler
	private Handler<EventPlayerUpdate> onPlayerUpdate = e -> {
		if (e.isPost())
			return;
		
		// Vars
		EntityPlayerSP thePlayer = mc.thePlayer;
		boolean isOverVoid = this.isOverVoid;
		
		// Fall distance calculations
		if (MovementUtils.isTrueOnGround()) {
			fallDistance = 0;
		}
		else if (thePlayer.posY < thePlayer.lastTickPosY) {
			fallDistance += thePlayer.lastTickPosY - thePlayer.posY;
		}
		
		// Info
		if (lastSafePosition == null || thePlayer.isDead || thePlayer.capabilities.isFlying) {
			setInfo(mode.getMode(), "No Safe Spot");
		}else {
			setInfo(mode.getMode(), ModTargetHud.DECIMAL_FORMAT.format(fallDistance) + "/" + ModTargetHud.DECIMAL_FORMAT.format(fallDistanceSetting.getValue()));
		}
		
		// Lag back if needed
		if(isOverVoid && fallDistance >= fallDistanceSetting.getValue()) {
			switch (mode.getMode()) {
				case "Blink":
				case "Hypixel Blink":{
					if (thePlayer.ticksExisted == 0)
						lastSafePosition = null;
					if (lastSafePosition == null) {
						return;
					}
					if (resetCooldownOnCatch.isEnabled())
						cooldownTimer.reset();
					ModBlink modBlink = Kagu.getModuleManager().getModule(ModBlink.class);
					if (modBlink.isEnabled()) {
						modBlink.getSentPackets().removeAll(blinkPackets);
					}
					if (dontDropPingPackets.isEnabled() && mode.is("Blink")) {
						blinkPackets.iterator().forEachRemaining(p -> {
							if (p instanceof C00PacketKeepAlive || p instanceof C0FPacketConfirmTransaction)
								mc.getNetHandler().getNetworkManager().sendPacketNoEvent(p);
						});
					}
					if (mode.is("Hypixel Blink")) {
						hypixelHasFlagged = false;
						NetworkManager networkManager = mc.getNetHandler().getNetworkManager();
						blinkPackets.iterator().forEachRemaining(packet -> {
							if (!hypixelHasFlagged && packet instanceof C03PacketPlayer && ((C03PacketPlayer)packet).isMoving()) {
								C03PacketPlayer c03 = (C03PacketPlayer)packet;
								c03.setY(c03.getY() + 0.3);
								hypixelHasFlagged = true;
							}
							networkManager.sendPacketNoEvent(packet);
						});
						hypixelHasFlagged = false;
					}
					blinkPackets.clear();
					if (mode.is("Blink")) {
						thePlayer.setPosition(lastSafePosition[0], lastSafePosition[1], lastSafePosition[2]);
						if (restoreMotion.isEnabled())
							thePlayer.setMotion(lastSafePosition[3], lastSafePosition[4], lastSafePosition[5]);
						else
							thePlayer.setMotion(0, 0, 0);
					}
					lastSafePosition = null;
				}break;
			}
		}
		
		if (!isOverVoid) {
			NetworkManager networkManager = mc.getNetHandler().getNetworkManager();
			blinkPackets.iterator().forEachRemaining(networkManager::sendPacketNoEvent);
			blinkPackets.clear();
		}
		
	};
	
	@EventHandler
	private Handler<EventPacketSend> onPacketSend = e -> {
		if (e.isPost() || mc.isSingleplayer())
			return;
		boolean isOverVoid = this.isOverVoid;
		EntityPlayerSP thePlayer = mc.thePlayer;
		switch (mode.getMode()) {
			case "Blink":
			case "Hypixel Blink":{
				if (isOverVoid && lastSafePosition != null && !thePlayer.isDead && !thePlayer.capabilities.isFlying) {
					if (dontBlinkPingPackets.isEnabled() && (e.getPacket() instanceof C00PacketKeepAlive || e.getPacket() instanceof C0FPacketConfirmTransaction))
						return;
					if (blinkPackets.offer(e.getPacket()))
						e.cancel();
				}
				else if (isOverVoid) {
					NetworkManager networkManager = mc.getNetHandler().getNetworkManager();
					blinkPackets.iterator().forEachRemaining(networkManager::sendPacketNoEvent);
					blinkPackets.clear();
				}
			}break;
		}
	};
	
	@EventHandler
	private Handler<EventPacketReceive> onPacketReceive = e -> {
		if (e.isPost() || mc.isSingleplayer())
			return;
		switch (mode.getMode()) {
			case "Blink":
			case "Hypixel Blink":{
				if (resetCooldownOnS08.isEnabled() && e.getPacket() instanceof S08PacketPlayerPosLook)
					cooldownTimer.reset();
			}break;
		}
	};
	
	@EventHandler
	private Handler<EventCheatProcessTick> onCheatProcessTick = e -> {
		if (e.isPost())
			return;
		EntityPlayerSP thePlayer = mc.thePlayer;
		boolean cooldownBool = cooldownTimer.hasTimeElapsed(cooldown.getValue(), false);
		if (!cooldownBool) {
			lastSafePosition = null;
			return;
		}
		isOverVoid = MovementUtils.isOverVoid() && cooldownBool;
		if (!isOverVoid) {
			lastSafePosition = new double[6];
			lastSafePosition[0] = thePlayer.posX;
			lastSafePosition[1] = thePlayer.posY;
			lastSafePosition[2] = thePlayer.posZ;
			lastSafePosition[3] = thePlayer.motionX;
			lastSafePosition[4] = thePlayer.motionY;
			lastSafePosition[5] = thePlayer.motionZ;
		}
	};
	
}
