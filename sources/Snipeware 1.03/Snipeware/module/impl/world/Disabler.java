package Snipeware.module.impl.world;

import java.io.StringBufferInputStream;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.commons.lang3.RandomUtils;

import Snipeware.Client;
import Snipeware.api.annotations.Handler;
import Snipeware.events.Event;
import Snipeware.events.packet.EventPacketReceive;
import Snipeware.events.packet.EventPacketSend;
import Snipeware.events.player.EventMotionUpdate;
import Snipeware.events.player.EventMove;
import Snipeware.gui.notification.Notifications;
import Snipeware.module.Module;
import Snipeware.module.impl.movement.Flight;
import Snipeware.util.other.Logger;
import Snipeware.util.other.MathUtils;
import Snipeware.util.other.PlayerUtil;
import Snipeware.util.other.TimeHelper;
import Snipeware.value.impl.EnumValue;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.player.PlayerCapabilities;
import net.minecraft.network.Packet;
import net.minecraft.network.handshake.client.C00Handshake;
import net.minecraft.network.login.server.S00PacketDisconnect;
import net.minecraft.network.login.server.S02PacketLoginSuccess;
import net.minecraft.network.play.client.*;
import net.minecraft.network.play.client.C03PacketPlayer.C04PacketPlayerPosition;
import net.minecraft.network.play.client.C0BPacketEntityAction.Action;
import net.minecraft.network.play.server.S07PacketRespawn;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.network.play.server.S32PacketConfirmTransaction;
import net.minecraft.network.play.server.S39PacketPlayerAbilities;

public class Disabler extends Module {

	private EnumValue<Mode> mode = new EnumValue("Mode", Mode.Watchdog);

	private boolean flag;

	private TimeHelper timer = new TimeHelper();
	private TimeHelper Watchdog = new TimeHelper();
	private TimeHelper Watchdog2 = new TimeHelper();
	private TimeHelper WatchdogDisabler = new TimeHelper();
	private boolean shouldDisable;

	private final ArrayDeque<Packet> list = new ArrayDeque<>();
	private ArrayList<Packet> packets = new ArrayList<>();
	private int watchdogCounter, currentTrans;
	private double watchdogMovement;
	private boolean groundCheck, watchdogPacket;
	private boolean shouldBlink;
	private boolean shouldBlink2;
	private int packetcounter;
	public EntityLivingBase entity;
	
	public Disabler() {
		super("Disabler", 0, ModuleCategory.WORLD);
		addValues(mode);
	}

	private enum Mode {
		Mineplex, FakeLag, Watchdog, WatchdogStaff, Horizon, Verus, Ghostly, Test;
	}

	@Handler
	public void onMotionUpdate(final EventMotionUpdate event) {
		super.setSuffix(mode.getValueAsString());
		switch (mode.getValue()) {
			case FakeLag: {
				
					if (mc.thePlayer.ticksExisted < .5) {
						list.clear();
					}
					if (timer.reach(MathUtils.getRandomInRange(5300, 10500))) {
						while (list.size() > 0) {
							mc.getNetHandler().addToSendQueueNoEvent(list.removeLast());
							timer.reset();
						}
					}
				}
				break;
			
			case Ghostly: {
				break;
			}
			case Horizon: {
				break;
			}
			case Verus: {
				break;
			}
			case Watchdog: {

				break;
			}
			case Mineplex: {
				
				break;
			}
			case WatchdogStaff: {
				break;
			}
			case Test: {
				
				break;
			}
		}
	}

	@Handler
	public void onReceivePacket(final EventPacketReceive event) {
		boolean flagged = event.getPacket() instanceof S08PacketPlayerPosLook;
			switch (mode.getValue()) {
			case Ghostly: {
				break;
			}
			case Horizon: {
				break;
			}
			case Verus: {
				break;
			}
			case Watchdog: {
		
				break;
			}
			case Mineplex: {
		         Packet packet = event.getPacket();
	                if (packet instanceof C00PacketKeepAlive) {
	                	Logger.print("nigger");
	                    ((C00PacketKeepAlive) packet).key -= MathUtils.getRandomInRange(1000, 2147483647);
	                }
				break;
			}
			case FakeLag: {
				break;
			}
			case Test: {
			
				break;
			}
			}
	}

	@Handler
	public void onSendPacket(final EventPacketSend event) {
		boolean playerpacket = event.getPacket() instanceof C03PacketPlayer;
		switch (mode.getValue()) {
			case Ghostly: {
				if (event.getPacket() instanceof C0FPacketConfirmTransaction) {
					event.setCancelled(true);
				}
				if (mc.thePlayer.ticksExisted % 3 == 0) {
					mc.getNetHandler().addToSendQueueNoEvent(new C18PacketSpectate(mc.thePlayer.getUniqueID()));
					mc.getNetHandler().addToSendQueueNoEvent(new C0CPacketInput(mc.thePlayer.movementInput.moveStrafe, mc.thePlayer.movementInput.moveForward, mc.thePlayer.movementInput.jump, mc.thePlayer.movementInput.sneak));
				}
				break;
			}
			case Horizon: {
				break;
			}
			case Verus: {
				break;
			}
			case Watchdog: {
				
				if(Watchdog.isDelayComplete(MathUtils.getRandomInRange(300, 500))) {
					shouldBlink = true;
				}
				if(Watchdog.isDelayComplete(800 * mc.timer.timerSpeed)) {
					shouldBlink = false;
					Watchdog.reset();
				}
				if(Watchdog2.isDelayComplete(MathUtils.getRandomInRange(490,500))) {
					shouldBlink2 = true;
				}
				if(Watchdog2.isDelayComplete(MathUtils.getRandomInRange(900,905))) {
					shouldBlink2 = false;
					packetcounter = 0;
					Watchdog2.reset();
				}
				
				if(shouldBlink = true) {
					if (event.getPacket() instanceof C00PacketKeepAlive && mc.timer.timerSpeed > 1.1f){
						event.setCancelled(true);
					}
				}
				if(mc.thePlayer.hurtResistantTime > 1) {
					if(packetcounter > 3) {
						shouldBlink2 = false;
					}
				}else {
					if(packetcounter > 1) {
						shouldBlink2 = false;
					}
				}
				
				
				if(shouldBlink2 = true) {
					if (event.getPacket() instanceof C03PacketPlayer.C06PacketPlayerPosLook){
						packetcounter++;
						if(mc.timer.timerSpeed > 1.1f) {
						event.setCancelled(true);
						}
					}
				}
				
				break;
			}
			case WatchdogStaff: {
				if(event.getPacket() instanceof C03PacketPlayer.C04PacketPlayerPosition) {
		            float yaw = mc.thePlayer.rotationYaw;
		            float pitch = mc.thePlayer.rotationPitch;
		            double offsetX = 0;
		            double offsetZ = 0;
					if(mc.thePlayer.motionX > 0) {
						offsetX = mc.thePlayer.motionX / 2;
						yaw = -90;
					}
					if(mc.thePlayer.motionX < 0) {
						offsetX = mc.thePlayer.motionX / 2;
						yaw = 90;
					}
					if(mc.thePlayer.motionZ > 0) {
						offsetZ = mc.thePlayer.motionZ / 2;
						yaw = 0;
					}
					if(mc.thePlayer.motionZ < 0) {
						offsetZ = mc.thePlayer.motionZ / 2;
						yaw = -170;
					}
					event.setPacket(new C03PacketPlayer.C06PacketPlayerPosLook(mc.thePlayer.posX + offsetX, mc.thePlayer.posY, mc.thePlayer.posZ + offsetZ, yaw, pitch, mc.thePlayer.onGround));
				}
				
				break;
			}
			case Mineplex: {
				
				break;
			}
			case FakeLag: {
					if (event.getPacket() instanceof C0FPacketConfirmTransaction) {
						final C0FPacketConfirmTransaction pp = (C0FPacketConfirmTransaction) event.getPacket();
						if (pp.getUid() < 0) {
							list.push(pp);
							event.setCancelled(true);
						}
					}
					if (event.getPacket() instanceof C00PacketKeepAlive){
						final C00PacketKeepAlive keepAlive = (C00PacketKeepAlive) event.getPacket();
						list.push(keepAlive);
						event.setCancelled(true);
					}
				
				break;
			}
			case Test: {
				Packet<?> packet  = event.getPacket();
				if(packet instanceof C03PacketPlayer.C04PacketPlayerPosition) {
    				C03PacketPlayer.C04PacketPlayerPosition c04 = (C03PacketPlayer.C04PacketPlayerPosition) event.getPacket();
    				if(mc.thePlayer.motionX > 0.1f && !mc.thePlayer.onGround) {
    					double value = mc.thePlayer.motionX ;
    					System.out.println(value);
    					c04.setX(mc.thePlayer.posX - value);
    				}
    			}
			
				break;
			}
		}
	}

	@Handler
	public void onMove(final EventMove event) {
		switch (mode.getValue()) {
		case Watchdog:
			break;
		case FakeLag:
			break;
		case Ghostly:
			break;
		case Horizon:
			break;
		case Verus:
			break;
		default:
			break;
		}
	}

	@Override
	public void onEnable() {
		super.onEnable();
		packets.clear();
		Watchdog.reset();
		WatchdogDisabler.reset();
		currentTrans = 0;
		if (mc.thePlayer == null)
			return;
		flag = false;
		switch (mode.getValue()) {
		case Ghostly:
			break;
		case Horizon:
			break;
		case Verus:
			break;
		case Watchdog:
			break;
		case FakeLag:
			timer.reset();
			break;
		case Test:
			break;
		}
	}

	@Override
	public void onDisable() {
		super.onDisable();
	}
}