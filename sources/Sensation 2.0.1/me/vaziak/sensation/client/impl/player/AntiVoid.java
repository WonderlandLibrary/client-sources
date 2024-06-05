package me.vaziak.sensation.client.impl.player;
import me.vaziak.sensation.Sensation;
import me.vaziak.sensation.client.api.Category;
import me.vaziak.sensation.client.api.Module;
import me.vaziak.sensation.client.api.event.annotations.Collect;
import me.vaziak.sensation.client.api.event.events.PlayerMoveEvent;
import me.vaziak.sensation.client.api.property.impl.StringsProperty;
import me.vaziak.sensation.utils.math.TimerUtil;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;

public class AntiVoid extends Module {

	/*
	 * 
	 * Credits to Zane/Ejaculation for packet antifall
	 * 
	 * 
	 * */
	private BlockPos lastSafe;
    private boolean shouldSave;
    private TimerUtil timer;

    private StringsProperty mode = new StringsProperty("Mode", "How cheat will function.", null,
            false, true, new String[]{"Packet", "Position", "Watchdog"});
    public AntiVoid() {
        super("Anti Void", Category.PLAYER);
        timer = new TimerUtil();
        registerValue(mode);
    }

    @Collect
    public void onMove(PlayerMoveEvent e) {
    	setMode(mode.getSelectedStrings().get(0));
        if(Sensation.instance.cheatManager.isModuleEnabled("Fly") || Sensation.instance.cheatManager.isModuleEnabled("Long Jump"))
            return;
        if (mc.thePlayer.onGround && mc.thePlayer.isCollidedVertically) {
        	lastSafe = new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ);
        }
        if(mc.thePlayer.fallDistance >= 2.75 && !isBlockUnder()) {
            if(!isBlockUnder()) {
                if (!shouldSave) {
                    shouldSave = true;
                }
                mc.thePlayer.fallDistance = 0; 
            }
        }
        if (shouldSave) {
        	
        	switch (mode.getSelectedStrings().get(0)) {
        		case "Packet":
            		mc.thePlayer.sendQueue.addToSendQueueNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 12.5, mc.thePlayer.posZ, true));
            	break;
            	

        		case "Position":
        			if (lastSafe != null) {
        				mc.thePlayer.sendQueue.addToSendQueueNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY - 900, mc.thePlayer.posZ, true));
            			mc.thePlayer.setPosition(lastSafe.getX(), lastSafe.getY(), lastSafe.getZ());
            			mc.thePlayer.setPositionAndUpdate(lastSafe.getX(), lastSafe.getY(), lastSafe.getZ());
        			}
        			break;

                case "Watchdog":
                    if (lastSafe != null) {
                    	mc.thePlayer.sendQueue.addToSendQueueNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, lastSafe.getY() + .0625 + 1.0E-4D, mc.thePlayer.posZ, true));

            			mc.thePlayer.setPositionAndUpdate(lastSafe.getX(), lastSafe.getY() + .0625, lastSafe.getZ());
            			timer.reset();
                    }
        		break;
        	}
        	shouldSave = false;
        }
    }

    private boolean isBlockUnder() {
        if(mc.thePlayer.posY < 0)
            return false;
        for(int off = 0; off < (int)mc.thePlayer.posY+2; off += 2){
            AxisAlignedBB bb = mc.thePlayer.getEntityBoundingBox().offset(0, -off, 0);
            if(!mc.theWorld.getCollidingBoundingBoxes(mc.thePlayer, bb).isEmpty()){
                return true;
            }
        }
        return false;
    }


}

