package me.vaziak.sensation.client.impl.player;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import io.netty.buffer.Unpooled;
import me.vaziak.sensation.Sensation;
import me.vaziak.sensation.client.ModuleInstantiator;
import me.vaziak.sensation.client.api.Category;
import me.vaziak.sensation.client.api.Module;
import me.vaziak.sensation.client.api.event.annotations.Collect;
import me.vaziak.sensation.client.api.event.events.PlayerUpdateEvent;
import me.vaziak.sensation.client.api.event.events.ProcessPacketEvent;
import me.vaziak.sensation.client.api.event.events.SendPacketEvent;
import me.vaziak.sensation.client.api.property.impl.StringsProperty;
import me.vaziak.sensation.utils.client.ChatUtils;
import me.vaziak.sensation.utils.math.TimerUtil;
import net.minecraft.client.ClientBrandRetriever;
import net.minecraft.client.Minecraft;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.handshake.client.C00Handshake;
import net.minecraft.network.login.client.C00PacketLoginStart;
import net.minecraft.network.login.client.C01PacketEncryptionResponse;
import net.minecraft.network.play.client.C00PacketKeepAlive;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.network.play.client.C0CPacketInput;
import net.minecraft.network.play.client.C0DPacketCloseWindow;
import net.minecraft.network.play.client.C0EPacketClickWindow;
import net.minecraft.network.play.client.C0FPacketConfirmTransaction;
import net.minecraft.network.play.client.C17PacketCustomPayload;
import net.minecraft.network.play.server.S00PacketKeepAlive;
import net.minecraft.network.play.server.S01PacketJoinGame;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.network.status.client.C00PacketServerQuery;
import net.minecraft.util.AxisAlignedBB;

/**
 * Made by Spec el retardo ------------------------------- 9/28/2017 / 3:44 PM
 * aphrodite aka agway skid - Build 16 - r1.8.0
 **/
public class Disabler extends Module {
	private Deque<Packet> packetDeque = new LinkedList<>();
	private List<Packet> packetList = new CopyOnWriteArrayList<>();
	private long possiblePingTime;
	TimerUtil timer;
	public StringsProperty mode = new StringsProperty("Mode",
			"The fucking mode, most only disable move checks  ,holy mode only workks if you are on ground!", null,
			false, true, new String[] { "Verus", "Faithful", "Fiona", "Holy", "OldGuardian", "OldAGC", "Dev", "OldAntiVirus",
					"PvPTemple", "VerusOld", "Spark", "Faithful", "Faithful2", "AGCTest", "Border Patrol"});
	private Deque<Packet> packets = new LinkedList<>();
	private boolean cancelling;
	private int sentpackets, delay;
	private boolean disabling, done;
	private double posy;
	private double startposy;
	private TimerUtil stopwatch;
	private boolean disabled;

	public Disabler() {
		super("Disabler", Category.MISC);
		registerValue(mode);
		timer = new TimerUtil();
		stopwatch = new TimerUtil();
	}

	public void onEnable() {
		if (mc.theWorld == null || mc.thePlayer == null) return;
		packetDeque.clear();
		reset();
		posy = mc.thePlayer.posY;
		startposy = posy;
		sentpackets = 0;
		delay = 2;
		packets.clear();
		if (mode.getValue().get("OldAntiVirus")) {
			ChatUtils.log(
					"This only works so anti velocity will work. This does &cNOT &fwork with disabling other checks. Retoggle whenever you join another server.");
		}

		done = false;

		stopwatch.reset();
	}

	@Override
	public void onDisable() {
		if (mc.theWorld == null || mc.thePlayer == null) return;
		reset();
		stopwatch.reset();
		packets.clear();
	}

	@Collect
	public void onPacketGet(ProcessPacketEvent event) {
		if (mc.theWorld == null || mc.thePlayer == null) return;
		if (event.getPacket() instanceof S01PacketJoinGame && mode.getValue().get("OldAGC")) {
			
            mc.thePlayer.sendQueue.addToSendQueueNoEvent(new C00PacketKeepAlive(10000));
		}
		if (event.getPacket() instanceof S02PacketChat && mode.getValue().get("OldAGC")) {
			S02PacketChat packet = (S02PacketChat) event.getPacket(); 
			if (packet.getChatComponent().getFormattedText().toString().contains("§r§6§m----------------------------------------§r")) {
				disabled = false;
			}
			if (packet.getChatComponent().getFormattedText().toString().contains("queue.")) {
				disabled = false;
			}
		}
		if (event.getPacket() instanceof S02PacketChat && mode.getValue().get("PvPTemple")) {
			S02PacketChat packet = (S02PacketChat) event.getPacket();
			if (packet.getChatComponent().getFormattedText().toString().equals("§r§1 §r§6 §r§2 §r§3 §r§9 §r§9 §r§1 §r§3 §r")) {
				disabled = false;
			}
		}
		if (event.getPacket() instanceof S01PacketJoinGame && mode.getValue().get("PvPTemple")) {
			disabled = false;
		}

		if (event.getPacket() instanceof S00PacketKeepAlive && mode.getValue().get("OldAGC")) {
			if (!disabled) {
				S00PacketKeepAlive packet = (S00PacketKeepAlive) event.getPacket();
				mc.thePlayer.sendQueue.addToSendQueueNoEvent(
						new C00PacketKeepAlive(((S00PacketKeepAlive) event.getPacket()).func_149134_c()));
	        	disabled = true;
			}
		}				 
			 
/*		if (event.getPacket() instanceof S00PacketKeepAlive && mode.getValue().get("Faithful")) {
			S00PacketKeepAlive packet = (S00PacketKeepAlive) event.getPacket();
			event.setCancelled(true);
			mc.thePlayer.sendQueue.addToSendQueueNoEvent(
					new C00PacketKeepAlive(((S00PacketKeepAlive) event.getPacket()).func_149134_c() - delay));
			delay++;
			if (delay >= 3) {
				delay = 2;
			}
		}*/
	}

	@Collect
	public void onSendPacket(SendPacketEvent sendPacketEvent) {
		if (mc.theWorld == null || mc.thePlayer == null) return;

		ModuleInstantiator moduleInstantiator = Sensation.instance.cheatManager;

		if (sendPacketEvent.getPacket() instanceof C03PacketPlayer && mode.getValue().get("Spark")) {
			sendPacketEvent.setCancelled(mc.thePlayer.ticksExisted % 3 == 0);
		}

		if (mode.getValue().get("Border Patrol")) {
			if (sendPacketEvent.getPacket() instanceof C03PacketPlayer || sendPacketEvent.getPacket() instanceof C00PacketKeepAlive || sendPacketEvent.getPacket() instanceof C0FPacketConfirmTransaction) {
				if (timer.hasPassed(500)) {
					while (!packetDeque.isEmpty()) {
						mc.thePlayer.sendQueue.addToSendQueueNoEvent(packetDeque.poll());
					}
					timer.reset();
				} else {
					packetDeque.addLast(sendPacketEvent.getPacket());
					sendPacketEvent.setCancelled(true);
				}
			}
		}
		
		if (sendPacketEvent.getPacket() instanceof C00PacketKeepAlive && (mode.getValue().get("Verus") || mode.getValue().get("VerusOld"))) {
			sendPacketEvent.setCancelled(true);
		}

		if (sendPacketEvent.getPacket() instanceof C0FPacketConfirmTransaction && (mode.getValue().get("Verus") || mode.getValue().get("VerusOld"))) {
			packets.addLast(sendPacketEvent.getPacket());
			sendPacketEvent.setCancelled(true);

			if (stopwatch.hasPassed(5500) && !packets.isEmpty()) {
				mc.thePlayer.sendQueue.addToSendQueueNoEvent(packets.getFirst());
				packets.removeFirst();
				stopwatch.reset();
			}
		}

		if (mode.getValue().get("Faithful2")) {
			if (sendPacketEvent.getPacket() instanceof C00PacketKeepAlive) {
				C00PacketKeepAlive packetKeepAlive = (C00PacketKeepAlive) sendPacketEvent.getPacket();
				if (delay >= 15) {
					delay = 3;
				}
				packetKeepAlive.setKey(packetKeepAlive.getKey() - delay);
				delay++;
			}

			if (sendPacketEvent.getPacket() instanceof C0FPacketConfirmTransaction) {
				sendPacketEvent.setCancelled(true);
			}
		}
		if (mode.getValue().get("Faithful")) {
			if (mc.thePlayer.ticksExisted == 0) {
				mc.thePlayer.sendQueue.addToSendQueueNoEvent(new C00PacketKeepAlive());
			} else if (sendPacketEvent.getPacket() instanceof C00PacketKeepAlive) {
				sendPacketEvent.setCancelled(true);
			}
		}

		if (mode.getValue().get("OldAGC")) {
  
			if (sendPacketEvent.getPacket() instanceof C03PacketPlayer
					|| sendPacketEvent.getPacket() instanceof C03PacketPlayer.C06PacketPlayerPosLook
					|| sendPacketEvent.getPacket() instanceof C03PacketPlayer.C04PacketPlayerPosition
					|| sendPacketEvent.getPacket() instanceof C03PacketPlayer.C05PacketPlayerLook
					|| sendPacketEvent.getPacket() instanceof C0BPacketEntityAction
					|| sendPacketEvent.getPacket() instanceof C02PacketUseEntity
					|| sendPacketEvent.getPacket() instanceof C0APacketAnimation
					|| sendPacketEvent.getPacket() instanceof C08PacketPlayerBlockPlacement
					|| sendPacketEvent.getPacket() instanceof C07PacketPlayerDigging) {
				if (stopwatch.hasPassed(150)) {
					while (!packets.isEmpty()) {
						mc.thePlayer.sendQueue.addToSendQueueNoEvent(packets.poll());
					}
					stopwatch.reset();
				} else {
					packets.add(sendPacketEvent.getPacket());
					sendPacketEvent.setCancelled(true);
				}
			}
		}

		if (mode.getValue().get("PvPTemple")) {
			if (mc.thePlayer != null && mc.theWorld != null) {

				if (sendPacketEvent.getPacket() instanceof C00PacketKeepAlive)
					possiblePingTime = System.currentTimeMillis();

				if (sendPacketEvent.getPacket() instanceof C0FPacketConfirmTransaction
						|| sendPacketEvent.getPacket() instanceof C00PacketKeepAlive) {
					sendPacketEvent.setCancelled(true);
					packetList.add(sendPacketEvent.getPacket());
				}
				if (timer.hasPassed(1000 * 10)) {
					timer.reset();
					dispAllPackets();
				}
			} else if (mc.theWorld == null) {
				this.disabled = false;
			}
		}

		if (mode.getValue().get("Faithful")) {
			if (sendPacketEvent.getPacket() instanceof C00PacketKeepAlive) {
				if (delay >= 15) {
					delay = 3;
				}
				((C00PacketKeepAlive) sendPacketEvent.getPacket()).setKey(((C00PacketKeepAlive) sendPacketEvent.getPacket()).getKey() - delay);
				delay++;
			}
		}

		if (mode.getValue().get("Dev")) {
			if (sendPacketEvent.getPacket() instanceof C00PacketKeepAlive || sendPacketEvent.getPacket() instanceof C0FPacketConfirmTransaction) {
				if (stopwatch.hasPassed(2000)) {
					while (!packets.isEmpty()) {
						mc.thePlayer.sendQueue.addToSendQueueNoEvent(packets.poll());
					}
					stopwatch.reset();
				} else {
					packets.add(sendPacketEvent.getPacket());
					sendPacketEvent.setCancelled(true);
				}
			} 
		}

		if (sendPacketEvent.getPacket() instanceof C00PacketKeepAlive
				|| sendPacketEvent.getPacket() instanceof C17PacketCustomPayload) {

			if (mode.getValue().get("Verus")) {
				sendPacketEvent.setCancelled(true);
			}
		}
	}

	@Collect
	public void onPlayerUpdate(PlayerUpdateEvent playerUpdateEvent) {
		setMode(mode.getSelectedStrings().get(0));
		if (mode.getValue().get("PvPTemple") && !disabled) {
			mc.thePlayer.sendQueue.addToSendQueue(new C17PacketCustomPayload("Lunar-Client",
					(new PacketBuffer(Unpooled.buffer())).writeString(ClientBrandRetriever.getClientModName())));
			disabled = true;
		}
		if (mode.getValue().get("OldAntiVirus")) {
			if (!stopwatch.hasPassed(3000)) {
				mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer(false));
			}
		}
		if (mode.getValue().get("Dev")) {
			 
		}

		if (mode.getValue().get("Verus")) {
			if (timer.hasPassed(6500)) {
				mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(2501, 6644, 1, true));
				timer.reset();
			}
		}
		if (mode.getValue().get("OldAGC")) {
			//mc.thePlayer.sendQueue.addToSendQueueNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX,
			//		mc.thePlayer.posY + .42, mc.thePlayer.posZ, mc.thePlayer.ticksExisted % 5 == 0));
			//mc.thePlayer.sendQueue.addToSendQueueNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX,
			//		mc.thePlayer.posY, mc.thePlayer.posZ, mc.thePlayer.ticksExisted % 5 == 0));
		}
		if (mode.getValue().get("Fiona")) {
			    mc.thePlayer.capabilities.allowFlying = true;
			    mc.thePlayer.sendPlayerAbilities();
			    mc.thePlayer.capabilities.allowFlying = false;
		}

		if (mode.getValue().get("OldGuardian")) {
			if (stopwatch.hasPassed(4500L) || mc.thePlayer.ticksExisted < 5) {
				playerUpdateEvent.setPosY(-1337);
				stopwatch.reset();
			}
		}

		if (mode.getValue().get("Holy")) {
			if (stopwatch.hasPassed(9500L) || mc.thePlayer.ticksExisted < 5) {
				mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C06PacketPlayerPosLook(mc.thePlayer.posX,
						mc.thePlayer.posY - 999, mc.thePlayer.posZ, mc.thePlayer.rotationYaw,
						mc.thePlayer.rotationPitch, mc.thePlayer.onGround));
				mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C06PacketPlayerPosLook(mc.thePlayer.posX,
						mc.thePlayer.posY - 999, mc.thePlayer.posZ, mc.thePlayer.rotationYaw,
						mc.thePlayer.rotationPitch, mc.thePlayer.onGround));
				stopwatch.reset();
			}
		}
	}

	private void reset() {
		dispAllPackets();
		packetList.clear();
		timer.reset();
	}

	private void dispAllPackets() {/*
		ChatUtils.debug("S: " + packetList.size());*/
		for (Packet packet : packetList) {
			packetList.remove(packet);
			mc.thePlayer.sendQueue.addToSendQueueNoEvent(packet);
		}
	}

	public void damagePlayer(double damage) {
		if ((Minecraft.getMinecraft().thePlayer != null) && (Minecraft.getMinecraft().getNetHandler() != null)) {
			for (int i = 0; i <= ((3 + damage) / 0.0626); i++) {
				Minecraft.getMinecraft().getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(
						Minecraft.getMinecraft().thePlayer.posX, Minecraft.getMinecraft().thePlayer.posY + 0.0626,
						Minecraft.getMinecraft().thePlayer.posZ, false));
				Minecraft.getMinecraft().getNetHandler()
						.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(
								Minecraft.getMinecraft().thePlayer.posX, Minecraft.getMinecraft().thePlayer.posY,
								Minecraft.getMinecraft().thePlayer.posZ, (i == ((3 + damage) / 0.0626))));
			}
		}
	}

	public double getGroundLevel() {
		for (int i = (int) Math.round(mc.thePlayer.posY); i > 0; --i) {
			AxisAlignedBB box = (AxisAlignedBB) mc.thePlayer.getEntityBoundingBox().addCoord(0, 0, 0);
			box.minY = i - 1;
			box.maxY = i;
			if (isColliding(box) && box.minY <= mc.thePlayer.posY) {
				return i;
			}
		}
		return 0;
	}

	private boolean isColliding(AxisAlignedBB box) {
		return mc.theWorld.checkBlockCollision(box);
	}

	public double fall() {
		double i = mc.thePlayer.posY;
		for (; i > getGroundLevel(); i -= 8.01) {
			if (i < getGroundLevel())
				i = getGroundLevel();
			mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX,
					i - 0.4255, mc.thePlayer.posZ, true));
		}
		return i;
	}
}