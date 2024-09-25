package none.module.modules.player;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.lwjgl.input.Keyboard;

import com.mojang.realmsclient.gui.ChatFormatting;

import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.server.S06PacketUpdateHealth;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.HWID;
import none.Client;
import none.event.Event;
import none.event.RegisterEvent;
import none.event.events.EventPacket;
import none.event.events.EventPreMotionUpdate;
import none.module.Category;
import none.module.Module;
import none.notifications.Notification;
import none.notifications.NotificationType;
import none.utils.MoveUtils;
import none.utils.TimeHelper;
import none.valuesystem.ModeValue;

public class Nofall extends Module{

	public Nofall() {
		super("Nofall", "Nofall", Category.PLAYER, Keyboard.KEY_B);
	}
	
	private static String[] mode = {"Vanilla", "Packet", "Hypixel", "Damage", "FlagLag"};
	public static ModeValue modes = new ModeValue("Nofall-Mode", "Packet", mode);
	
	double fall;
	private List<Packet> packetList = new CopyOnWriteArrayList<>();
	TimeHelper timer = new TimeHelper();
	
	@Override
	protected void onEnable() {
		super.onEnable();
    	fall = 0;
    	packetList.clear();
    	if (mc.thePlayer == null) {
    		return;
    	}
    	
    	if (modes.getSelected().equalsIgnoreCase("Hypixel") && !HWID.isHWID()) {
			evc("Premium Only");
			Client.instance.notification.show(Client.notification("Premium", "You are not Premium", 3));
			setState(false);
			return;
		}
	}
	
	@Override
	protected void onDisable() {
		super.onDisable();
		fall = 0;
    	packetList.clear();
	}
	
	@Override
	@RegisterEvent(events = {EventPreMotionUpdate.class, EventPacket.class})
	public void onEvent(Event event) {
		if (!isEnabled()) return;
		String currentNofall = modes.getSelected();
		
		setDisplayName(getName() + ChatFormatting.WHITE + " " + modes.getSelected());
		
		if (currentNofall.equalsIgnoreCase("FlagLag") && mc.isSingleplayer()) {
			Client.instance.notification.show(new Notification(NotificationType.INFO, getName(), " disabled by SinglePlayer.", 3));
			evc("Nofall disabled by SinglePlayer.");
			this.toggle();
			return;
		}
		
		if(mc.thePlayer.ticksExisted <= 1 && currentNofall.equalsIgnoreCase("FlagLag")){
			Client.instance.notification.show(new Notification(NotificationType.INFO, getName(), " disabled by respawn.", 3));
			evc("Nofall disabled by respawn.");
			this.toggle();
			return;
		}
		
		if (event instanceof EventPacket) {
			EventPacket ep = (EventPacket) event;
			Packet p = ep.getPacket();
			if (ep.isPre()) {
				if (!MoveUtils.isOnGround(0.001)) {
					if (currentNofall.equalsIgnoreCase("FlagLag") && mc.thePlayer.fallDistance > 3) {
	//						if (ep.isIncoming() && p instanceof S15PacketEntityRelMove) {
						//						ep.setCancelled(true);
						//					}
	
						//					if (ep.isIncoming() && p instanceof S18PacketEntityTeleport) {
						//						ep.setCancelled(true);
						//					}
	
						//					if (ep.isIncoming() && (p instanceof S00PacketKeepAlive)) {
						//						ep.setCancelled(true);
						//					}
	
						if (ep.isOutgoing() && (p instanceof C03PacketPlayer.C05PacketPlayerLook || p instanceof C03PacketPlayer.C06PacketPlayerPosLook || p instanceof C03PacketPlayer.C04PacketPlayerPosition)) {
							ep.setCancelled(true);
						}
	
	//						if (ep.isIncoming() && (p instanceof S39PacketPlayerAbilities)) {
	////							S39PacketPlayerAbilities in = (S39PacketPlayerAbilities) ep.getPacket();
	////							in.setInvulnerable(true);
	////							in.setAllowFlying(in.isAllowFlying());
	////							in.setFlying(in.isFlying());
	////							in.setCreativeMode(in.isCreativeMode());
	////							in.setFlySpeed(in.getFlySpeed());
	////							in.setWalkSpeed(in.getWalkSpeed());
	//							ep.setCancelled(true);
	//						}
							if (ep.isIncoming() && p instanceof S06PacketUpdateHealth) {
								ep.setCancelled(true);
							}
	
	//						if (ep.isOutgoing() && (p instanceof C13PacketPlayerAbilities)) {
	//							C13PacketPlayerAbilities in = (C13PacketPlayerAbilities) ep.getPacket();
	//							in.setInvulnerable(true);
	//							in.setAllowFlying(in.isAllowFlying());
	//							in.setFlying(in.isFlying());
	//							in.setCreativeMode(in.isCreativeMode());
	//							in.setFlySpeed(mc.thePlayer.capabilities.getFlySpeed());
	//							in.setWalkSpeed(mc.thePlayer.capabilities.getWalkSpeed());
	//							ep.setCancelled(true);
	//						}
	
						if (ep.isIncoming() && (p instanceof S08PacketPlayerPosLook)) {
							S08PacketPlayerPosLook in = (S08PacketPlayerPosLook) ep.getPacket();
							in.yaw = mc.thePlayer.rotationYaw;
							in.pitch = mc.thePlayer.rotationPitch;
													ep.setCancelled(true);
						}
	
						if (ep.isOutgoing() && (p instanceof C03PacketPlayer) && !(p instanceof C03PacketPlayer.C05PacketPlayerLook || p instanceof C03PacketPlayer.C06PacketPlayerPosLook || p instanceof C03PacketPlayer.C04PacketPlayerPosition)) {
//													C03PacketPlayer c03PacketPlayer = (C03PacketPlayer) ep.getPacket();
//							if (c03PacketPlayer.onGround = false) {
//								c03PacketPlayer.onGround = true;
//							}
							ep.setCancelled(true);
							mc.getConnection().getNetworkManager().sendPacketNoEvent(new C03PacketPlayer(true));
							//						mc.getNetHandler().getNetworkManager().sendPacketNoEvent(c03PacketPlayer);
						}
					}
				}else {
					if (currentNofall.equalsIgnoreCase("FlagLag")) {
//							if (ep.isIncoming() && p instanceof S06PacketUpdateHealth) {
//								ep.setCancelled(true);
//							}
						//
						//					if (ep.isOutgoing() && (p instanceof C13PacketPlayerAbilities)) {
						//						ep.setCancelled(true);
						//						C13PacketPlayerAbilities in = (C13PacketPlayerAbilities) ep.getPacket();
						//						in.setInvulnerable(true);
						//						in.setAllowFlying(in.isAllowFlying());
						//						in.setFlying(in.isFlying());
						//						in.setCreativeMode(in.isCreativeMode());
						//						in.setFlySpeed(mc.thePlayer.capabilities.getFlySpeed());
						//						in.setWalkSpeed(mc.thePlayer.capabilities.getWalkSpeed());
						//						mc.getNetHandler().getNetworkManager().sendPacketNoEvent(in);
						//					}
					}
				}
			}
		}
		
		if (event instanceof EventPreMotionUpdate) {
			EventPreMotionUpdate em = (EventPreMotionUpdate) event;
			
			if (em.isPre()) {

				if (mc.thePlayer.fallDistance > 5F) {
					if (!isBlockUnder()) {
						return;
					}
				}
				
				switch(currentNofall){
            	case"Vanilla":
            		if(!MoveUtils.isOnGround(0.001) && mc.thePlayer.motionY < 0){
            			em.setOnGround(true);
            		}
            		break;
            		
            	case"Packet":
               	 	if(!MoveUtils.isOnGround(0.0001) && mc.thePlayer.motionY < 0){
               	 		mc.thePlayer.connection.sendPacket(new C03PacketPlayer(true));  
               	 	}
            		break;
            	case"Hypixel":
            	
            		if(!MoveUtils.isOnGround(0.001)){
            			if(mc.thePlayer.motionY < -0.08)
            				fall -= mc.thePlayer.motionY;
            			if(fall > 2){
            				fall = 0;
            			
                			em.setOnGround(true);
                		}
            		}else
            			fall = 0;
            		break;
            	case"Cubecraft":
            		if(!MoveUtils.isOnGround(0.001)){
            			if(mc.thePlayer.fallDistance > 2.69){
                			em.setOnGround(true);
                			mc.thePlayer.fallDistance = 0;
                		}
                		if (!mc.theWorld.getCollidingBoundingBoxes(mc.thePlayer, mc.thePlayer.getEntityBoundingBox().offset(0.0D, mc.thePlayer.motionY, 0.0D)).isEmpty()) {  
                			if(!em.isOnGround() && mc.thePlayer.motionY < -0.6){
                				em.setOnGround(true);
                			}		 
                		}
            		}
            		break;
            	case"Damage":
            		if(mc.thePlayer.fallDistance > 3.5){
            			em.setOnGround(true);
            		}
            		break;
            	case"FlagLag":
            		if(!MoveUtils.isOnGround(0.001) && mc.thePlayer.fallDistance > 3){
            			event.setCancelled(true);
//            			em.setOnGround(true);
//            			mc.thePlayer.sendQueue.addToSendQueue(new C13PacketPlayerAbilities(mc.thePlayer.capabilities));
//            			mc.getConnection().getNetworkManager().sendPacketNoEvent(new C03PacketPlayer(true));
            		}else if (MoveUtils.isOnGround(0.001)) {
            			event.setCancelled(false);
            		}
            	}
			}
		}
	}
	
	private boolean isBlockUnder() {
    	if(mc.thePlayer.posY < 0)
    		return false;
    	for(int off = 0; off < (int)mc.thePlayer.posY+2; off += 2){
    		AxisAlignedBB bb = mc.thePlayer.boundingBox.offset(0, -off, 0);
    		if(!mc.theWorld.getCollidingBoundingBoxes(mc.thePlayer, bb).isEmpty()){
    			return true;
    		}
    	}
    	return false;
    }

}
