package me.vaziak.sensation.client.impl.player;

import me.vaziak.sensation.client.api.Category;
import me.vaziak.sensation.client.api.Module;
import me.vaziak.sensation.client.api.event.annotations.Collect;
import me.vaziak.sensation.client.api.event.events.PlayerUpdateEvent;
import me.vaziak.sensation.client.api.event.events.ProcessPacketEvent;
import me.vaziak.sensation.client.api.event.events.SendPacketEvent;
import me.vaziak.sensation.client.api.property.impl.StringsProperty;
import me.vaziak.sensation.utils.client.ChatUtils;
import me.vaziak.sensation.utils.math.TimerUtil;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemBucketMilk;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C07PacketPlayerDigging.Action;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

public class FastUse extends Module {

    public StringsProperty prop_mode = new StringsProperty("Mode", "The fucking mode", null, false, true, new String[]{"Guardian", "AntiVirus", "NCP", "Falcon [TEST]"});
	private boolean stage, decreasing;
	TimerUtil timer;
	TimerUtil timer2;
	private int counter;
	private int waittime;
	private TimerUtil timerUtil;
    public FastUse() {
        super("FastUse", Category.PLAYER);
        registerValue(prop_mode);
        timer = new TimerUtil();
        timer2 = new TimerUtil();
        timerUtil = new TimerUtil();
    }

    @Collect
    public void onPlayerUpdate(PlayerUpdateEvent playerUpdateEvent) {
        setMode(prop_mode.getSelectedStrings().get(0));

		if (mc.thePlayer.getItemInUse() == null) return;

        if (prop_mode.getValue().get("Guardian")) {
	        if (!(mc.thePlayer.getItemInUse().getItem() instanceof ItemBow)) {
	        	if ((mc.thePlayer.getItemInUseDuration() <= 3) && (mc.gameSettings.keyBindUseItem.pressed) && (!(mc.thePlayer.getItemInUse().getItem() instanceof ItemBow)) && (!(mc.thePlayer.getItemInUse().getItem() instanceof ItemSword))) {
	        		mc.thePlayer.sendQueue.addToSendQueue(new C08PacketPlayerBlockPlacement(mc.thePlayer.getHeldItem()));
	        		for (int i = 0; i < 40; i++) {
	        			mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer(true));
	                    }
	        		mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, net.minecraft.util.BlockPos.ORIGIN, net.minecraft.util.EnumFacing.DOWN));
	        	}
	        } else {
	   	        if (mc.thePlayer.onGround && mc.thePlayer.getHeldItem() != null && mc.thePlayer.getHeldItem().getItem() instanceof ItemBow && mc.gameSettings.keyBindUseItem.pressed) {
	   	        	mc.thePlayer.sendQueue.addToSendQueue(new C08PacketPlayerBlockPlacement(mc.thePlayer.getHeldItem()));
	
	   	        	for (int i = 0; i < 40; i++) {
	   	        		mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer(true));
	                }
	   	        	mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
	            }
	        }
        }
        
        if (prop_mode.getValue().get("Falcon [TEST]") && mc.thePlayer.onGround) {
	        if (!(mc.thePlayer.getItemInUse().getItem() instanceof ItemBow)) {
	        	if ((mc.thePlayer.getItemInUseDuration() <= 3) && (mc.gameSettings.keyBindUseItem.pressed) && (!(mc.thePlayer.getItemInUse().getItem() instanceof ItemBow)) && (!(mc.thePlayer.getItemInUse().getItem() instanceof ItemSword))) {
	        		decreasing = true;
	        		mc.thePlayer.sendQueue.addToSendQueueNoEvent(new C08PacketPlayerBlockPlacement(mc.thePlayer.getHeldItem()));

	   	        	for (int i = 0; i < 30; i++) {


	   	        		mc.thePlayer.sendQueue.addToSendQueueNoEvent(new C03PacketPlayer(true));
	                }
	   	        	mc.thePlayer.sendQueue.addToSendQueueNoEvent(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, net.minecraft.util.BlockPos.ORIGIN, net.minecraft.util.EnumFacing.DOWN));
	        	}
	        } else {
	   	        if (mc.thePlayer.onGround && mc.thePlayer.getHeldItem() != null && mc.thePlayer.getHeldItem().getItem() instanceof ItemBow && mc.gameSettings.keyBindUseItem.pressed) {
	   	        	decreasing = true;
	   	        	mc.thePlayer.sendQueue.addToSendQueueNoEvent(new C08PacketPlayerBlockPlacement(mc.thePlayer.getHeldItem()));
	                double POSITIVE_INFINITY = Double.NEGATIVE_INFINITY;
	                double POSITIVE_INFINITY2 = Double.POSITIVE_INFINITY;
   	        		
	   	        	for (int i = 0; i < 35; i++) {
 
	   	        		mc.thePlayer.sendQueue.addToSendQueueNoEvent(new C03PacketPlayer(true));
	                }
	   	        	mc.thePlayer.sendQueue.addToSendQueueNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(POSITIVE_INFINITY2, mc.thePlayer.posY, POSITIVE_INFINITY2, true));
		            
	   	        	mc.thePlayer.sendQueue.addToSendQueueNoEvent(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
	            }
	        }
	}
        
        

		if (mc.thePlayer.getItemInUse().getItem() instanceof ItemFood && prop_mode.getValue().get("NCP")) {
			if (mc.thePlayer.isUsingItem()) {
				if (mc.timer.timerSpeed > 1.3) {
					decreasing = true;
				}
	
				if (mc.timer.timerSpeed < 1.0) {
					decreasing = false;
				}
	
				mc.timer.timerSpeed += (decreasing ? -.1 : .05);
				if (mc.thePlayer.getItemInUseCount() == 17) {
				mc.thePlayer.sendQueue.addToSendQueue(new C08PacketPlayerBlockPlacement(mc.thePlayer.getHeldItem()));
				
				for (int i = 0; i < 16; i++) {
					mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer(true));
				}
				mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, net.minecraft.util.BlockPos.ORIGIN, net.minecraft.util.EnumFacing.DOWN));
				}
			}
		}

        if (prop_mode.getValue().get("AntiVirus")) {
        	
	        if (mc.thePlayer.getItemInUse() == null) return;
	        if (!(mc.thePlayer.getItemInUse().getItem() instanceof ItemBow)) {
	        	if (mc.thePlayer.getHeldItem() != null
	                    && (mc.thePlayer.getHeldItem().getItem() instanceof ItemFood
	                            || mc.thePlayer.getHeldItem().getItem() instanceof ItemPotion
	                            || mc.thePlayer.getHeldItem().getItem() instanceof ItemBucketMilk)
	                    && mc.thePlayer.isEating() && mc.thePlayer.onGround
	                    && timer.hasPassed(75L)) {
	                mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C06PacketPlayerPosLook(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, mc.thePlayer.rotationYaw, mc.thePlayer.rotationPitch,mc.thePlayer.onGround));
	                timer.reset();
	            }
	        } else {
	        	if (timer.hasPassed(55) && stage) {
	        		for (int i1 = 0; i1 < 2; i1++) {
	        			mc.thePlayer.sendQueue.addToSendQueue(new C08PacketPlayerBlockPlacement(mc.thePlayer.inventory.getCurrentItem()));
	        			for (int loop = 0; loop < 40; loop++) {
	        				mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, true));
	        			}
	        			mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM,new BlockPos(0, 0, 0).ORIGIN, EnumFacing.DOWN));
	        		}
	        		timer.reset();
	        	}
	        	
	        	if (timer2.hasPassed(600L)) {
	        		if (stage) {
	        			stage = false;
	        		} else {
	        			stage = true;
	        		}
	        		timer2.reset();
	        	} 
	        }
        }
    }

    @Collect
    public void onProcessPacket(ProcessPacketEvent event) {
        if (event.getPacket() instanceof S08PacketPlayerPosLook) {
            S08PacketPlayerPosLook packet = (S08PacketPlayerPosLook) event.getPacket();
            packet.setYaw(mc.thePlayer.rotationYaw);
            packet.setPitch(mc.thePlayer.rotationPitch);
        }
    }
    
    @Collect
    public void onSendPacket(SendPacketEvent event) {
    	if (event.getPacket() instanceof C03PacketPlayer && decreasing == true) {
    		mc.thePlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ);
    		decreasing = false; 
    	}
    }

}