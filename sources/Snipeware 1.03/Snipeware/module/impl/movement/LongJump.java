package Snipeware.module.impl.movement;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.LinkedList;

import Snipeware.Client;
import Snipeware.api.annotations.Handler;
import Snipeware.events.packet.EventPacketReceive;
import Snipeware.events.packet.EventPacketSend;
import Snipeware.events.player.EventMotionUpdate;
import Snipeware.events.player.EventMove;
import Snipeware.gui.notification.Notifications;
import Snipeware.module.Module;
import Snipeware.util.other.Logger;
import Snipeware.util.other.TimeHelper;
import Snipeware.util.player.MovementUtils;
import Snipeware.value.impl.BooleanValue;
import Snipeware.value.impl.EnumValue;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemFireball;
import net.minecraft.item.ItemStack;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C07PacketPlayerDigging.Action;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

public class LongJump extends Module {

    public int stage, groundTicks;
    public double lastDistance;
    private boolean wasAir = false;
    public double movementSpeed;
	private EnumValue<Mode> mode = new EnumValue<>("Mode", Mode.Vanilla);
	private EnumValue<Watchdogmode> watchdogmode = new EnumValue<>("WatchdogMode", Watchdogmode.Normal);
	private BooleanValue AutoDisable = new BooleanValue("AutoDisable", true);
	private BooleanValue flagbackcheck = new BooleanValue("Flagback check", false);
	private final TimeHelper WatchdogTimer = new TimeHelper();
	private final TimeHelper WatchdogTimer2 = new TimeHelper();
	private ItemStack item;
	private boolean state;
	private ArrayList<Packet> packetList = new ArrayList<>();
	
    public LongJump() {
        super("LongJump", 0, ModuleCategory.MOVEMENT);
        addValues(mode, watchdogmode, flagbackcheck, AutoDisable);
    }

    
    private enum Mode {
		Vanilla, Redesky, Watchdog;
	}
    
    private enum Watchdogmode {
		Normal, Damage;
	}
    public double cameraY;
    
    @Override
    public void onEnable() {
    	WatchdogTimer2.reset();
    	packetList.clear();
    	state = false;
    	double cameraY = mc.thePlayer.chasingPosY;
    	WatchdogTimer.reset();
        lastDistance = movementSpeed = 0.0D;
        stage = groundTicks = 0;
        wasAir = false;
        switch (mode.getValue()) {
    	case Watchdog:{
    		if(watchdogmode.getValueAsString() == "Normal") {
    			if(mc.thePlayer.onGround) {
    				mc.thePlayer.motionY =+ 0.42f;
	    		}
    		}
    	}
        }
        super.onEnable();
    }

    @Override
    public void onDisable() {
    	 switch (mode.getValue()) {
	     	case Watchdog:{
	     		if(watchdogmode.getValueAsString() == "Damage") {
	     			try {
						for (Packet packets : packetList) {
							mc.getNetHandler().addToSendQueueNoEvent(packets);
						}
						packetList.clear();
					}
					catch (final ConcurrentModificationException e) {
						e.printStackTrace();
					}
	     		}
	     	}
     	}
        super.onDisable();
        boolean autodisable = false;
        wasAir = false;
        WatchdogTimer.reset();
        mc.timer.timerSpeed = 1.0f;
        mc.gameSettings.keyBindJump.pressed = false;
    }
    
    @Handler
	public void onSendPacket(final EventPacketSend event) {
    	if(mode.getValueAsString() == "Watchdog") {
    		if(watchdogmode.getValueAsString()== "Damage") {
    			final Packet p = event.getPacket();
    			if (p instanceof C03PacketPlayer) {
    				if(state) {
    					event.setCancelled(true);
	    				packetList.add(p);
    				}
    			}
    		}
    	}
	}

    @Handler
    public void onMotionUpdate(final EventMotionUpdate event) {
    	if(mode.getValueAsString() == "Watchdog") {
    		if(watchdogmode.getValueAsString()== "Damage") {
    			for(int i = 0;i < 9;i++) {
	    			item = mc.thePlayer.inventory.getStackInSlot(i);
	    			if(item != null && item.getItem() instanceof ItemBow) {
						mc.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(i));
						
						mc.thePlayer.sendQueue.addToSendQueue(new C08PacketPlayerBlockPlacement(item));
						
					}
    			}
    			if(state == false) {
    				event.setPitch(-90);
    			}
    			
    			if(WatchdogTimer2.reach(160)) {
    				mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
    			
    			}
    			
    		
    			
    			if(WatchdogTimer2.reach(2200)) {
    				toggle();
    			}
    			
    			if(mc.thePlayer.hurtTime > 0) {
    				state = true;
    				mc.thePlayer.motionY = 0.3;
    			}
    			
    			if(state) {
    				
    			}
    			
    			if(state == false) {
    				mc.gameSettings.keyBindJump.pressed = false;
    				mc.gameSettings.keyBindForward.pressed = false;
    				mc.gameSettings.keyBindRight.pressed = false;
    				mc.gameSettings.keyBindLeft.pressed = false;
    				mc.gameSettings.keyBindBack.pressed = false;
    			}else {
    				mc.gameSettings.keyBindForward.pressed = true;
    			}
    		}
    	}
    }
    
    @Handler
	public void onReceivePacket(final EventPacketReceive event) {
		if (event.getPacket() instanceof S08PacketPlayerPosLook && flagbackcheck.isEnabled()) {
			toggle();
			Notifications.getManager().post("Disabled Modules", "LongJump was disabled to prevent flags/errors.");
		}
	}

    @Handler
    public void onMove(final EventMove event) {
    	switch (mode.getValue()) {
    	case Vanilla:{
    		setSuffix(mode.getValueAsString());
        if (stage == 1) {
            mc.thePlayer.cameraYaw = 0.03f;
            movementSpeed = 0;
            mc.timer.timerSpeed = 0.402371238f;
            
        } else if (stage == 2) {
            event.setY(mc.thePlayer.motionY = MovementUtils.getJumpBoostModifier(mc.thePlayer.getJumpUpwardsMotion()));
            
            movementSpeed = 1.9 * MovementUtils.getSpeed();
        } else if (stage == 3) {
            movementSpeed = 3.188 * MovementUtils.getSpeed();
        } else if (stage == 4) {
            movementSpeed *= 1.22;
        } else {
            if (stage < 15) {
                if (mc.thePlayer.motionY < 0) {
                    event.setY(mc.thePlayer.motionY *= .7225f);
                }
                movementSpeed = lastDistance - lastDistance / 159;
            } else {
            	movementSpeed = lastDistance - lastDistance / 159;
            }
        }
        MovementUtils.setSpeed(event, Math.max(movementSpeed, MovementUtils.getSpeed()));
        stage++;
    	break;
    	}
    	case Redesky:{

    		
    		setSuffix(mode.getValueAsString());
    	
    		
    		mc.thePlayer.setSprinting(true);
    		if(mc.thePlayer.isMoving2() && mc.thePlayer.onGround) {
    			MovementUtils.setMotion(MovementUtils.getSpeed() + 0.20);
    			
    			mc.gameSettings.keyBindJump.pressed = true;
    			
    			
    	
    		}else {
    			
    			mc.gameSettings.keyBindJump.pressed = false;
    		}

    		break;
    	}
    	case Watchdog:{
    		setSuffix(mode.getValueAsString());
    		if(watchdogmode.getValueAsString() == "Normal") {
	    		if(mc.thePlayer.isMoving2() && mc.thePlayer.onGround && wasAir == false) {
	    			MovementUtils.setMotion(MovementUtils.getSpeed() + 0.82);
	    		}
	    		if(!mc.thePlayer.onGround){
	    			if(WatchdogTimer.isDelayComplete(100)) {
	    				wasAir = true;
	    			}
	    			MovementUtils.setMotion(MovementUtils.getSpeed() + 0.15);
	    			if(WatchdogTimer.isDelayComplete(300)  && mc.thePlayer.motionY < 0.1f) {
		    			MovementUtils.setMotion(MovementUtils.getSpeed() + 0.13);
		    			if(!WatchdogTimer.isDelayComplete(500)) {
		    				mc.thePlayer.motionY += 0.082f;
		    			}else {
		    				MovementUtils.setMotion(MovementUtils.getSpeed() + 0.02);
		    			}
	    			}
	    		}
	    		
	    		if(mc.thePlayer.onGround && wasAir && AutoDisable.isEnabled()) {
	    			wasAir = false;
	    			toggle();
	    		}
    		}else if(watchdogmode.getValueAsString() == "Damage") {
    			if(state) {
    				MovementUtils.setSpeed(event, 0.3);
    			}
    		}
    		
    		break;
    		}
    	}
    }
}
