package me.vaziak.sensation.client.impl.movement;

import me.vaziak.sensation.client.api.event.annotations.Collect;
import me.vaziak.sensation.client.api.event.events.EventLiquidCollision;
import me.vaziak.sensation.client.api.event.events.PlayerUpdateEvent;
import me.vaziak.sensation.Sensation;
import me.vaziak.sensation.client.api.Category;
import me.vaziak.sensation.client.api.Module;
import me.vaziak.sensation.client.api.property.impl.DoubleProperty;
import me.vaziak.sensation.client.api.property.impl.StringsProperty;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockLiquid;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;

/**
 * @author Spec
 */
public class Jesus extends Module {
 
    public StringsProperty mode = new StringsProperty("Mode", "The fucking mode you autist what do you think", false, true, new String[]{"Bhop", "Solid", "FunkeMunky"});
    private DoubleProperty motionY = new DoubleProperty("Motion Y", "Funkemunky can't make a proper onground method, abuse the fact hes a retard to make this into a highjump", () -> mode.getValue().get("FunkeMunky"), 0.42, .15, 9.5, 1);

    private int ticks;

    public void onEnable() {
        ticks = 0;
    }

    public Jesus() {
        super("Jesus", Category.MOVEMENT);
        registerValue(mode, motionY);
    }

    @Collect
    public void onPlayerUpdate(PlayerUpdateEvent em) {
        setMode(mode.getSelectedStrings().get(0));
        if (mode.getValue().get("Solid")) {
        	if (!Sensation.instance.cheatManager.isModuleEnabled("Speed") && em.isPre() && isOnLiquid() && !isInLiquid() && !mc.thePlayer.isSneaking() && !mc.gameSettings.keyBindJump.isPressed()) {
        		if (ticks == 0 && isOnLiquid() && mc.thePlayer.isMoving()) {
                    mc.thePlayer.sendQueue.addToSendQueue(new C0BPacketEntityAction(mc.thePlayer, C0BPacketEntityAction.Action.STOP_SPRINTING));
                }
                mc.thePlayer.fallDistance = 0; 
            }  
            if (ticks == 1 && !isOnLiquid()) {
                ticks = 0;
            }
            if (isOnLiquid() || isInLiquid()) {
            }
            if (em.isPre() && !mc.thePlayer.isSneaking() && !mc.gameSettings.keyBindJump.isPressed()) {
            	if  (isOnLiquid() && !isInLiquid()) { 
            		mc.thePlayer.motionY -= .09; 
                	mc.thePlayer.setSpeed(.17);
            		
            	} else if (isInLiquid()) {  
                	mc.thePlayer.setSpeed(.12);
            		mc.thePlayer.motionY = 0.08f;
            	}
            }
            if (isOnLiquid() && mc.theWorld.getBlockState((new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 0.1, mc.thePlayer.posZ))).getBlock() == Blocks.lava && !mc.thePlayer.isMoving()) {
                em.setCancelled(true);
            }
            
        } else if (mode.getValue().get("FunkeMunky")) {
            if (em.isPre() && !mc.thePlayer.isSneaking() && !mc.gameSettings.keyBindJump.isPressed()) {
            	if ( isOnLiquid() && !isInLiquid()) {
            		mc.thePlayer.motionY -= .1;
            		ticks = 0;
            	} else if (isInLiquid()) {
            		ticks = 35;
            		mc.thePlayer.motionY = motionY.getValue();
            	}
            }
            
            if (ticks != 0) {
            	if (!mc.thePlayer.onGround) {  
            		mc.thePlayer.setSpeed(.19);
            		ticks -= 1; 
            	} else {
            		ticks = 0;
            	}
            }
        } else {
            if (em.isPre() && !mc.thePlayer.isSneaking() && !mc.gameSettings.keyBindJump.isPressed()) {
            	if  (isOnLiquid() && !isInLiquid()) {
            		mc.thePlayer.motionY -= .1;
            		ticks = 0;
            	} else if (isInLiquid()) {
            		ticks = 35;
            		mc.thePlayer.motionY = 0.42f;
            	}
            }
        }
    }

    private boolean shouldSetBoundingBox() {
        return (!mc.thePlayer.isSneaking()) && (mc.thePlayer.fallDistance < 4.0F) && !isInLiquid();
    }

    public boolean isOnLiquid() {
        AxisAlignedBB boundingBox = mc.thePlayer.getEntityBoundingBox();
        if (boundingBox == null) {
            return false;
        }
        boundingBox = boundingBox.contract(0.01D, 0.0D, 0.01D).offset(0.0D, -0.01D, 0.0D);
        boolean onLiquid = false;
        int y = (int) boundingBox.minY;
        for (int x = MathHelper.floor_double(boundingBox.minX); x < MathHelper
                .floor_double(boundingBox.maxX + 1.0D); x++) {
            for (int z = MathHelper.floor_double(boundingBox.minZ); z < MathHelper
                    .floor_double(boundingBox.maxZ + 1.0D); z++) {
                Block block = mc.theWorld.getBlockState((new BlockPos(x, y, z))).getBlock();
                if (block != Blocks.air) {
                    if (!(block instanceof BlockLiquid)) {
                        return false;
                    }
                    onLiquid = true;
                }
            }
        }
        return onLiquid;
    }


    public boolean isInLiquid() {
        if(mc.thePlayer == null) {
            return false;
        }
        for (int x = MathHelper.floor_double(mc.thePlayer.getEntityBoundingBox().minX); x < MathHelper.floor_double(mc.thePlayer.getEntityBoundingBox().maxX) + 1; x++) {
            for (int z = MathHelper.floor_double(mc.thePlayer.getEntityBoundingBox().minZ); z < MathHelper.floor_double(mc.thePlayer.getEntityBoundingBox().maxZ) + 1; z++) {
                BlockPos pos = new BlockPos(x, (int) mc.thePlayer.getEntityBoundingBox().minY, z);
                Block block = mc.theWorld.getBlockState(pos).getBlock();
                if ((block != null) && (!(block instanceof BlockAir))) {
                    return block instanceof BlockLiquid;
                }
            }
        }
        return false;
    }

    @Collect
    public void onPlayerUpdate(EventLiquidCollision event) {
    	if (mc.thePlayer == null || mc.theWorld == null) 
    		return;
    	if (mode.getValue().get("Solid")) { 
            if (event.getBounds().minY + .9 < mc.thePlayer.getEntityBoundingBox().minY) {
            	if (mc.thePlayer.ticksExisted % 2 != 0) {
            //		mc.timer.timerSpeed = 1.2f;
            	} else {

            		mc.timer.timerSpeed = 1.0f;
            	}
        		event.setCancelled(mc.thePlayer.ticksExisted % 2 == 0 && shouldSetBoundingBox()); 
            }
    	} else {
            if (event.getBounds().minY + .9 < mc.thePlayer.getEntityBoundingBox().minY) {
                    event.setCancelled(mode.getValue().get("FunkeMunky") || mode.getValue().get("Bhop") ? mc.thePlayer.ticksExisted % 2 == 0 && shouldSetBoundingBox() : shouldSetBoundingBox()); 
            }
    	}
    }
}
