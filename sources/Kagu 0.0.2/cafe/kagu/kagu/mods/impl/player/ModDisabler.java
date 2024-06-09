/**
 * 
 */
package cafe.kagu.kagu.mods.impl.player;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ThreadLocalRandom;

import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.util.Vec3;
import org.apache.commons.lang3.RandomUtils;

import cafe.kagu.kagu.eventBus.EventHandler;
import cafe.kagu.kagu.eventBus.Handler;
import cafe.kagu.kagu.eventBus.impl.EventPacketReceive;
import cafe.kagu.kagu.eventBus.impl.EventPacketSend;
import cafe.kagu.kagu.eventBus.impl.EventPlayerUpdate;
import cafe.kagu.kagu.eventBus.impl.EventRender2D;
import cafe.kagu.kagu.eventBus.impl.EventTick;
import cafe.kagu.kagu.font.FontUtils;
import cafe.kagu.kagu.mods.Module;
import cafe.kagu.kagu.mods.ModuleManager;
import cafe.kagu.kagu.settings.impl.ModeSetting;
import cafe.kagu.kagu.utils.ChatUtils;
import cafe.kagu.kagu.utils.MovementUtils;
import cafe.kagu.kagu.utils.TimerUtil;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C00PacketKeepAlive;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.network.play.client.C0BPacketEntityAction.Action;
import net.minecraft.network.play.client.C0FPacketConfirmTransaction;
import net.minecraft.network.play.client.C03PacketPlayer.C04PacketPlayerPosition;
import net.minecraft.network.play.client.C03PacketPlayer.C05PacketPlayerLook;
import net.minecraft.network.play.client.C03PacketPlayer.C06PacketPlayerPosLook;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;

/**
 * @author lavaflowglow
 *
 */
public class ModDisabler extends Module {
	
	public ModDisabler() {
		super("Disabler", Category.PLAYER);
		setSettings(mode);
	}
	
	private ModeSetting mode = new ModeSetting("Mode", "S08 C04", "S08 C04", "C04 Connect", "Rapid Rotate",
			"Inverse Rapid Rotate", "Always Send Rotating", "Hypixel Strafe", "Basic Desync", "Vulcan", "Test");
	
	private boolean changeNextC06 = false;
	private float rapidRotation = 0;
	
	private boolean synced = false, s08C04 = false;
	private Queue<Packet<?>> pingPackets = new LinkedList<>();
	private TimerUtil c03Timer = new TimerUtil(), s08Timer = new TimerUtil();
	private int c0fsInQueue = 0;
	private int ticks = 0;
	
	@Override
	public void onEnable() {
		changeNextC06 = false;
		rapidRotation = 0;
		synced = false;
		pingPackets.clear();
		c03Timer.reset();
		c0fsInQueue = 0;
		ticks = 0;
		
		switch (mode.getMode()) {
			case "Hypixel Strafe":
			case "Basic Desync":{
				ChatUtils.addChatMessage("he-hewwo mista >~<, pwease welog. The disabler won't work unless you do ;-;");
			}break;
		}
		
	}
	
	@EventHandler
	private Handler<EventTick> onTick = e -> {
		if (!mode.is("Hypixel Strafe") && !mode.is("Vulcan"))
			setInfo(mode.getMode());
		EntityPlayerSP thePlayer = mc.thePlayer;
		
		// Clear ping packets if disconnected
		if (!mc.getNetHandler().isDoneLoadingTerrain()) {
			pingPackets.clear();
		}
		
		switch (mode.getMode()) {
			case "Always Send Rotating":{
				if (e.isPost())
					return;
				// Tricks mc into thinking that the player rotated and that it needs to send an update to the server
				thePlayer.setLastReportedYaw(mc.thePlayer.getLastReportedYaw() + 1);
				thePlayer.setLastReportedPitch(mc.thePlayer.getLastReportedPitch() + 1);
			}break;
			case "Hypixel Strafe":{
				if (s08C04) {
					thePlayer.setPosition(thePlayer.lastTickPosX, thePlayer.lastTickPosY, thePlayer.lastTickPosZ);
				}
			}break;
			case "Basic Desync":{
				if (thePlayer.ticksExisted == 0)
					synced = false;
				setInfo("Basic Desync");
			}break;
			case "Vulcan":{
				if (e.isPost())
					return;
				setInfo("Vulcan (" + pingPackets.size() + ")");
				thePlayer.setLastReportedYaw(mc.thePlayer.getLastReportedYaw() + 1);
				thePlayer.setLastReportedPitch(mc.thePlayer.getLastReportedPitch() + 1);
				thePlayer.setLastReportedPosX(thePlayer.getLastReportedPosX() + 1);
				thePlayer.setLastReportedPosY(thePlayer.getLastReportedPosY() + 1);
				thePlayer.setLastReportedPosZ(thePlayer.getLastReportedPosZ() + 1);
				if (pingPackets.size() > 50 || thePlayer.ticksExisted == 0 || thePlayer.posY < 0) {
					NetworkManager networkManager = mc.getNetHandler().getNetworkManager();
					Packet<?> p = null;
					while ((p = pingPackets.poll()) != null) {
						networkManager.sendPacketNoEvent(p);
					}
				}
			}break;
			case "Test":{
				
			}break;
		}
	};
	
	@EventHandler
	private Handler<EventPlayerUpdate> onPlayerUpdate = e -> {
		EntityPlayerSP thePlayer = mc.thePlayer;
		switch (mode.getMode()) {
			case "Hypixel Strafe":{
				if (!(thePlayer.isUsingItem() || thePlayer.isBlocking()) || thePlayer.ticksExisted % 3 != 0)
					return;
				
			}break;
			case "Test":{
//				e.setOnGround(thePlayer.ticksExisted % 2 == 0);
			}break;
		}
	};
	
	@EventHandler
	private Handler<EventPacketReceive> onPacketReceive = e -> {
		if (e.isPost())
			return;
		
		EntityPlayerSP thePlayer = mc.thePlayer;
		switch (mode.getMode()) {
			case "S08 C04":{
				if (e.getPacket() instanceof S08PacketPlayerPosLook)
					changeNextC06 = true;
			}break;
			case "Hypixel Strafe":{
				if (e.getPacket() instanceof S08PacketPlayerPosLook) {
					S08PacketPlayerPosLook s08PacketPlayerPosLook = (S08PacketPlayerPosLook)e.getPacket();
					if (!s08C04 && thePlayer.ticksExisted > 60) {
						s08Timer.reset();
						s08C04 = true;
					}
					if (s08C04) {
						if (!s08Timer.hasTimeElapsed(3000, false)) {
							thePlayer.setPosition(s08PacketPlayerPosLook.getX(), s08PacketPlayerPosLook.getY(), s08PacketPlayerPosLook.getZ());
							mc.getNetHandler().getNetworkManager().sendPacketNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(thePlayer.posX, thePlayer.posY, thePlayer.posZ, false));
							e.cancel();
						}else if (s08Timer.hasTimeElapsed(9000, false)) {
							s08C04 = false;
						}
					}
				}
			}break;
		}
	};
	
	@EventHandler
	private Handler<EventPacketSend> onPacketSend = e -> {
		if (e.isPost())
			return;
		
		EntityPlayerSP thePlayer = mc.thePlayer;
		
		switch (mode.getMode()) {
			case "S08 C04":{
				if (!changeNextC06 || !(e.getPacket() instanceof C06PacketPlayerPosLook))
					break;
				C06PacketPlayerPosLook c06 = (C06PacketPlayerPosLook)e.getPacket();
				e.setPacket(new C04PacketPlayerPosition(c06.getPositionX(), c06.getPositionY(), c06.getPositionZ(), c06.isOnGround()));
			}break;
			case "C04 Connect":{
				if (thePlayer.ticksExisted > 0 || !(e.getPacket() instanceof C06PacketPlayerPosLook))
					break;
				C06PacketPlayerPosLook c06 = (C06PacketPlayerPosLook)e.getPacket();
				e.setPacket(new C04PacketPlayerPosition(c06.getPositionX(), c06.getPositionY(), c06.getPositionZ(), c06.isOnGround()));
			}break;
			case "Rapid Rotate":
			case "Inverse Rapid Rotate":{
				if (e.getPacket() instanceof C03PacketPlayer) {
					C03PacketPlayer c03 = (C03PacketPlayer)e.getPacket();
					if (c03.isRotating())
						return;
					// If c03 is c04
					if (c03 instanceof C04PacketPlayerPosition) {
						C04PacketPlayerPosition c04 = (C04PacketPlayerPosition)c03;
						e.setPacket(new C06PacketPlayerPosLook(c04.getPositionX(), c04.getPositionY(), c04.getPositionZ(), mc.thePlayer.getLastReportedYaw(), mc.thePlayer.getLastReportedPitch(), c03.isOnGround()));
					}
					
					// Else could only be normal c03 because c05 & c06 both have rotating set to true
					else {
						e.setPacket(new C05PacketPlayerLook(mc.thePlayer.getLastReportedYaw(), mc.thePlayer.getLastReportedPitch(), c03.isOnGround()));
					}
				}
			}break;
			case "Hypixel Strafe":{
				if (e.getPacket() instanceof C0FPacketConfirmTransaction) {
					C0FPacketConfirmTransaction c0f = (C0FPacketConfirmTransaction)e.getPacket();
					if (c0f.getUid() > 0 || c0f.getWindowId() != 0)
						return;
//					ChatUtils.addChatMessage(c0f.getUid());
//					if (pingPackets.offer(c0f)) {
//						c0fsInQueue++;
//						e.cancel();
//					}
				}
				else if (e.getPacket() instanceof C00PacketKeepAlive) {
//					if (synced)
//						if (pingPackets.offer(e.getPacket()))
//							e.cancel();
				}
				else if (e.getPacket() instanceof C03PacketPlayer) {
//					if (s08C04)
//						e.cancel();
					if (thePlayer.ticksExisted < 60) {
						e.cancel();
					}
//					if (!synced && thePlayer.ticksExisted < 60)
//						e.cancel();
				}
			}break;
			case "Basic Desync":{
				if (e.getPacket() instanceof C03PacketPlayer) {
					if (!synced && thePlayer.ticksExisted <= 60) {
						e.cancel();
					}else if (!synced) {
						synced = true;
					}
				}
			}break;
			case "Vulcan":{
				if (e.getPacket() instanceof C0BPacketEntityAction) {
					// Cancel start and stop sprint packets
					C0BPacketEntityAction c0b = (C0BPacketEntityAction)e.getPacket();
					if (c0b.getAction() == net.minecraft.network.play.client.C0BPacketEntityAction.Action.START_SPRINTING
							|| c0b.getAction() == net.minecraft.network.play.client.C0BPacketEntityAction.Action.STOP_SPRINTING)
						e.cancel();
				}
				else if (e.getPacket() instanceof C0FPacketConfirmTransaction || e.getPacket() instanceof C00PacketKeepAlive) {
					if (pingPackets.offer(e.getPacket()))
						e.cancel();
				}
				else if (e.getPacket() instanceof C03PacketPlayer) {
					
					// Disables killaura strafe checks
					C03PacketPlayer c03PacketPlayer = (C03PacketPlayer)e.getPacket();
					if (c03PacketPlayer.isOnGround()) {
						ticks = 0;
					}
					ticks++;
					if (c03PacketPlayer.isMoving() && ticks <= 2) {
						c03PacketPlayer.setX(c03PacketPlayer.getPositionX() + (Math.random() / 100) * (ThreadLocalRandom.current().nextBoolean() ? 1 : -1));
						c03PacketPlayer.setZ(c03PacketPlayer.getPositionZ() + (Math.random() / 100) * (ThreadLocalRandom.current().nextBoolean() ? 1 : -1));
					}
					
				}
			}break;
			case "Test":{
				if (thePlayer == null)
					break;
				if (e.getPacket() instanceof C0BPacketEntityAction)
					e.setCanceled(true);

				if (e.getPacket() instanceof C02PacketUseEntity){
					((C02PacketUseEntity) e.getPacket()).setHitVec(new Vec3(0, 0, 0));
				}
//				if (e.getPacket() instanceof C0FPacketConfirmTransaction || e.getPacket() instanceof C00PacketKeepAlive) {
//					if (pingPackets.offer(e.getPacket()))
//						e.cancel();
//					mc.getNetHandler().getNetworkManager().sendPacketNoEvent(e.getPacket());
//					mc.getNetHandler().getNetworkManager().sendPacketNoEvent(e.getPacket());
//					mc.getNetHandler().getNetworkManager().sendPacketNoEvent(e.getPacket());
//					new Timer().schedule(new TimerTask() {
//						
//						@Override
//						public void run() {
//							mc.getNetHandler().getNetworkManager().sendPacketNoEvent(e.getPacket());
//						}
//					}, 150);
//				}
//				else if (e.getPacket() instanceof C03PacketPlayer) {
//					while (pingPackets.peek() != null && pingPackets.peek() instanceof C00PacketKeepAlive) {
//						mc.getNetHandler().getNetworkManager().sendPacketNoEvent(pingPackets.poll());
//					}
//					if (pingPackets.peek() != null)
//						mc.getNetHandler().getNetworkManager().sendPacketNoEvent(pingPackets.poll());
//					ChatUtils.addChatMessage(pingPackets.size());
//				}
			}break;
		}
	};
	
	@EventHandler
	private Handler<EventRender2D> onRender2D = e -> {
		if (e.isPost())
			return;
		ScaledResolution sr = new ScaledResolution(mc);
		EntityPlayerSP thePlayer = mc.thePlayer;
		switch (mode.getMode()) {
			case "Hypixel Strafe":{
				if (s08C04 || thePlayer.ticksExisted < 60)
					FontUtils.ROBOTO_REGULAR_10.drawCenteredString("Bribing watchdog with sexual favors...", sr.getScaledWidth() / 2, sr.getScaledHeight() * 0.75, 0xffff0000);
			}break;
			case "Basic Desync":{
				if (thePlayer.ticksExisted > 60 || synced)
					return;
				FontUtils.ROBOTO_REGULAR_10.drawCenteredString("Desyncing (" + mc.thePlayer.ticksExisted + "/60)...", sr.getScaledWidth() / 2, sr.getScaledHeight() * 0.75, 0xffff0000);
			}break;
		}
	};
	
	/**
	 * @return the mode
	 */
	public ModeSetting getMode() {
		return mode;
	}
	
	/**
	 * @return the rapidRotation
	 */
	public float getRapidRotation() {
		if (mode.is("Inverse Rapid Rotate"))
			rapidRotation--;
		else
			rapidRotation++;
		return rapidRotation;
	}
	
}
