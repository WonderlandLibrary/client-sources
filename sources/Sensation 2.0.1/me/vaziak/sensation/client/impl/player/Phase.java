package me.vaziak.sensation.client.impl.player;

import me.vaziak.sensation.Sensation;
import me.vaziak.sensation.client.api.Category;
import me.vaziak.sensation.client.api.Module;
import me.vaziak.sensation.client.api.event.annotations.Collect;
import me.vaziak.sensation.client.api.event.events.BlockPushEvent;
import me.vaziak.sensation.client.api.event.events.BoundingBoxEvent;
import me.vaziak.sensation.client.api.event.events.InsideBlockRenderEvent;
import me.vaziak.sensation.client.api.event.events.PlayerMoveEvent;
import me.vaziak.sensation.client.api.event.events.PlayerUpdateEvent;
import me.vaziak.sensation.client.api.event.events.ProcessPacketEvent;
import me.vaziak.sensation.client.api.event.events.RunTickEvent;
import me.vaziak.sensation.client.api.event.events.SendPacketEvent;
import me.vaziak.sensation.client.api.property.impl.StringsProperty;
import me.vaziak.sensation.client.impl.movement.Speed;
import me.vaziak.sensation.utils.math.MathUtils;
import me.vaziak.sensation.utils.math.TimerUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockHopper;
import net.minecraft.block.BlockLiquid;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;

public class Phase extends Module {
    private final StringsProperty prop_mode = new StringsProperty("Mode", "The shit phase mode, these all need improvement", false, false, new String[]{"Watchdog", "Vanilla", "VClip"});
    public static boolean phasing;
    private int delay;
    private int state;
    private int moveUnder;
    private TimerUtil timer;
	private int counter;
    public Phase() {
        super("Phase", Category.PLAYER);
        registerValue(prop_mode);
        timer = new TimerUtil();
    }
    
    public void onEnable() {
        phasing = false;
        delay = 0;
        timer.reset();
    }

    public void onDisable() {
    	counter = 0;
    	mc.timer.timerSpeed = 1.0f;
        timer.reset();
    }
    
    @Collect
    public void onInside(InsideBlockRenderEvent event) {
        event.setCancelled(true);
    }

    @Collect
    public void onBlockPush(BlockPushEvent e) {
    	e.setCancelled(true);
    }

    @Collect
    public void onProcessPacket(ProcessPacketEvent event) {
        if (event.getPacket() instanceof S02PacketChat) {
            S02PacketChat packet = (S02PacketChat) event.getPacket();
            if (packet.getChatComponent().getUnformattedText().contains("You cannot go past the border.")) {
                event.setCancelled(true);
            }
        }
        if (prop_mode.getValue().get("Vanilla") && event.getPacket() instanceof S08PacketPlayerPosLook && moveUnder == 2) {
            moveUnder = 1;
        }
        if (prop_mode.getValue().get("Vanilla") && event.getPacket() instanceof S08PacketPlayerPosLook && moveUnder == 69) {
            moveUnder = 1488;
        }
    }
    
    @Collect
    public void onBoundingBox(BoundingBoxEvent event) {  
        if (prop_mode.getValue().get("Watchdog") && isInsideBlock()) {
            event.setBoundingBox(null);
        }
        if (prop_mode.getValue().get("Vanilla")) {
            if (mc.thePlayer.isCollidedHorizontally && !isInsideBlock()) {
                double mx = -Math.sin(Math.toRadians(mc.thePlayer.rotationYaw));
                double mz = Math.cos(Math.toRadians(mc.thePlayer.rotationYaw));
                double x = mc.thePlayer.movementInput.moveForward * mx + mc.thePlayer.movementInput.moveStrafe * mz;
                double z = mc.thePlayer.movementInput.moveForward * mz - mc.thePlayer.movementInput.moveStrafe * mx;
                event.setBoundingBox(null);
                mc.thePlayer.setPosition(mc.thePlayer.posX + x,mc.thePlayer.posY,mc.thePlayer.posZ + z);
                moveUnder = 69;
            }
            if (isInsideBlock()) event.setBoundingBox(null);
        } 
    }

    @Collect
    public void onMove(PlayerMoveEvent event) {

        if (prop_mode.getValue().get("Watchdog")) {
        	if (mc.gameSettings.keyBindSneak.isKeyDown()) {
        		event.setMoveSpeed(.25);
 
        	}
            if (isInsideBlock()) {
            	event.setMoveSpeed(.29);
            }
        }
        if (prop_mode.getValue().get("Vanilla")) {

            if (isInsideBlock()) {
                if (mc.gameSettings.keyBindJump.isKeyDown()) {
                    event.setY(mc.thePlayer.motionY = 1);
                } else if (mc.gameSettings.keyBindSneak.isKeyDown()) {
                    event.setY(mc.thePlayer.motionY = -1);
                } else {
                    event.setY(mc.thePlayer.motionY = 0.0);
                }
            } 
        }
    }

    @Collect
    public void onPlayerUpdate(PlayerUpdateEvent event) {
    	setMode(prop_mode.getSelectedStrings().get(0));
        if (prop_mode.getValue().get("VClip")) {
        	if (mc.gameSettings.keyBindSneak.isKeyDown()) {
        		counter ++;
        		if (counter > 3) {
        			mc.thePlayer.setPositionAndUpdate(mc.thePlayer.posX, mc.thePlayer.posY - 3, mc.thePlayer.posZ);
        			counter = 0;
        		}
        	}  else if (mc.gameSettings.keyBindJump.isKeyDown()) {
	    		counter ++;
	    		if (counter > 3) {
	    			mc.thePlayer.setPositionAndUpdate(mc.thePlayer.posX, mc.thePlayer.posY + 3, mc.thePlayer.posZ);
	    			counter = 0;
	    		}
	        } else {
	        	counter = 0;
	        }
        }
    	if (prop_mode.getValue().get("Vanilla")) {
    		mc.thePlayer.setSpeed(mc.thePlayer.isMoving() ? .6 + getBaseMoveSpeed(): 0);
    		mc.thePlayer.onGround = true;
        	if (mc.gameSettings.keyBindJump.isKeyDown()) {
        		mc.thePlayer.motionY += .4;
        	} else if (mc.gameSettings.keyBindSneak.isKeyDown()) {
        		mc.thePlayer.motionY -= .4;
        	} else {
        		mc.thePlayer.motionY = 0;
        	}
    	}
    	if (prop_mode.getValue().get("Watchdog") && mc.thePlayer.isSneaking()) {
    		
    		event.setYaw(event.getYaw() + MathUtils.getRandomInRange(-.2f, .2f));
			mc.thePlayer.motionY = 0;
            double multiplier = 0.295;
            double mx = -Math.sin(Math.toRadians(mc.thePlayer.rotationYaw));
            double mz = Math.cos(Math.toRadians(mc.thePlayer.rotationYaw));
            double x = mc.thePlayer.movementInput.moveForward * multiplier * mx + mc.thePlayer.movementInput.moveStrafe * multiplier * mz;
            double z = mc.thePlayer.movementInput.moveForward * multiplier * mz - mc.thePlayer.movementInput.moveStrafe * multiplier * mx;
            if (mc.thePlayer.isCollidedHorizontally && !mc.thePlayer.isOnLadder()) {   
            	if (counter >= 0) {                
            		mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX + x, mc.thePlayer.posY, mc.thePlayer.posZ + z, false));

            		event.setYaw(event.getYaw() + MathUtils.getRandomInRange(-.2f, .2f));
                    multiplier = 0.45;
                	mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, 0 - counter, mc.thePlayer.posZ, false));
                	mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX + x, mc.thePlayer.posY, mc.thePlayer.posZ + z, false));
     
                	mc.thePlayer.setPosition(mc.thePlayer.posX + x, mc.thePlayer.posY, mc.thePlayer.posZ + z); 
                	counter += 1;
                	if (counter >= 1) {  
                		counter = -8; 
                	}
            	} else {  
            		counter+=1;
            	}
            }

    } 

        if (prop_mode.getValue().get("Vanilla")) {
        	mc.timer.timerSpeed = .3f;
        }
        if (prop_mode.getValue().get("Vanilla") && mc.gameSettings.keyBindSneak.isPressed() && !isInsideBlock()) {
            mc.thePlayer.sendQueue.getNetworkManager().sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY - 2.0, mc.thePlayer.posZ, true));
            moveUnder = 2;
        } 
    }
    
    @Collect
    public void onTick(RunTickEvent event) {
        if (mc.thePlayer == null) return;
        if (prop_mode.getValue().get("Vanilla")) {
            if (mc.thePlayer != null && moveUnder == 1) {
                mc.thePlayer.sendQueue.getNetworkManager().sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY - 2.0, mc.thePlayer.posZ, false));
                mc.thePlayer.sendQueue.getNetworkManager().sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(Double.NEGATIVE_INFINITY, mc.thePlayer.posY, Double.NEGATIVE_INFINITY, true));
                moveUnder = 0;
            }
            if (mc.thePlayer != null && moveUnder == 1488) {
                double mx = -Math.sin(Math.toRadians(mc.thePlayer.rotationYaw));
                double mz = Math.cos(Math.toRadians(mc.thePlayer.rotationYaw));
                double x = mc.thePlayer.movementInput.moveForward * mx + mc.thePlayer.movementInput.moveStrafe * mz;
                double z = mc.thePlayer.movementInput.moveForward * mz - mc.thePlayer.movementInput.moveStrafe * mx;
                mc.thePlayer.sendQueue.getNetworkManager().sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX + x, mc.thePlayer.posY, mc.thePlayer.posZ + z, false));
                mc.thePlayer.sendQueue.getNetworkManager().sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(Double.NEGATIVE_INFINITY, mc.thePlayer.posY, Double.NEGATIVE_INFINITY, true));
                moveUnder = 0;
            }
        } 
    }
    
    public static boolean isInsideBlock() {
        for (int x = MathHelper.floor_double(Minecraft.getMinecraft().thePlayer.getEntityBoundingBox().minX); x < MathHelper.floor_double(Minecraft.getMinecraft().thePlayer.getEntityBoundingBox().maxX) + 1; x++) {
            for (int y = MathHelper.floor_double(Minecraft.getMinecraft().thePlayer.getEntityBoundingBox().minY); y < MathHelper.floor_double(Minecraft.getMinecraft().thePlayer.getEntityBoundingBox().maxY) + 1; y++) {
                for (int z = MathHelper.floor_double(Minecraft.getMinecraft().thePlayer.getEntityBoundingBox().minZ); z < MathHelper.floor_double(Minecraft.getMinecraft().thePlayer.getEntityBoundingBox().maxZ) + 1; z++) {
                    Block block = Minecraft.getMinecraft().theWorld.getBlockState(new BlockPos(x, y, z)).getBlock();
                    if ((block != null) && (!(block instanceof BlockAir))) {
                        AxisAlignedBB boundingBox = block.getCollisionBoundingBox(Minecraft.getMinecraft().theWorld, new BlockPos(x, y, z), Minecraft.getMinecraft().theWorld.getBlockState(new BlockPos(x, y, z)));
                        if ((block instanceof BlockHopper)) {
                            boundingBox = new AxisAlignedBB(x, y, z, x + 1, y + 1, z + 1);
                        }
                        if ((boundingBox != null) && (Minecraft.getMinecraft().thePlayer.getEntityBoundingBox().intersectsWith(boundingBox))) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }
}
