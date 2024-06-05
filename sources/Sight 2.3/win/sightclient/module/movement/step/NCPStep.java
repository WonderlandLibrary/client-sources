package win.sightclient.module.movement.step;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.BlockPos;
import win.sightclient.Sight;
import win.sightclient.event.Event;
import win.sightclient.event.events.client.EventUpdate;
import win.sightclient.event.events.player.EventStep;
import win.sightclient.module.Module;
import win.sightclient.module.ModuleMode;
import win.sightclient.utils.TimerUtils;
import win.sightclient.utils.minecraft.MoveUtils;

public class NCPStep extends ModuleMode {

	public static long lastStep;
	private int ticks;
	private boolean stepping;
	
	private long lastFuck, lastPacket;
	private static Map<Float, float[]> offsets = new HashMap<>();
	
	private TimerUtils timer;
	
	public NCPStep(Module parent, TimerUtils timer) {
		super(parent);
        offsets.put(1.0F, new float[]{0.41999998688698f, 0.753f});
        offsets.put(1.5F, new float[]{0.41999998688698f, 0.75f, 1, 1.16f});
        offsets.put(2.0F, new float[]{0.41999998688698f, 0.78f, 0.63f, 0.51f, 0.9f, 1.21f, 1.45f, 1.43f});	
        this.timer = timer;
	}

	@Override
	public void onEvent(Event event) {
		if (event instanceof EventUpdate) {
			EventUpdate eu = (EventUpdate)event;
			if (eu.isPre()) {
	            if ((System.currentTimeMillis() - lastStep) > 230L) {
	                ticks = 0;
	            } else {
	                if (mc.thePlayer.ticksExisted % 2 == 0) ticks++;
	            }
			}
		} else if (event instanceof EventStep) {
			EventStep e = (EventStep)event;
			if (e.isPre()) {
	            if (Sight.instance.mm.isEnabled("Flight")) return;
	            if (Sight.instance.mm.isEnabled("Speed")) return;
	            if (mc.gameSettings.keyBindJump.isKeyDown()) return;
	            
		        if (mc.thePlayer != null && mc.theWorld != null && (System.currentTimeMillis() - lastFuck) > 20L) {
		
		            if (e.stepHeight != 0.6f || mc == null || mc.thePlayer == null)
		                return;
		
		            if (!mc.thePlayer.isCollidedHorizontally || mc.thePlayer.isInWater() || mc.thePlayer.isInLava() || mc.thePlayer.isOnLadder() || mc.thePlayer.checkBlockAbove(0.1f))
		                return;
		
		
		            float stepHeight;
		            if ((stepHeight = getNeededStepHeight()) > 2D)
		                return;
		
		            if ((stepHeight == 1 && mc.theWorld.getCollidingBoundingBoxes(mc.thePlayer,
		                    mc.thePlayer.boundingBox.offset(mc.thePlayer.motionX, 0.6, mc.thePlayer.motionZ)).isEmpty())
		                    || !mc.theWorld.getCollidingBoundingBoxes(mc.thePlayer, mc.thePlayer.boundingBox.offset(
		                    mc.thePlayer.motionX, stepHeight + 0.01, mc.thePlayer.motionZ)).isEmpty())
		                return;
		
		
		            double radius = 0.50;
		
		            double currentX = mc.thePlayer.posX, currentY = mc.thePlayer.posY, currentZ = mc.thePlayer.posZ;
		
		            boolean isInvalid = false;
		            String[] invalidBlocks = {"snow", "chest", "slab", "stair"};
		
		            for (double x = currentX - radius; x <= currentX + radius; x++) {
		                for (double y = currentY - radius; y <= currentY + radius; y++) {
		                    for (double z = currentZ - radius; z <= currentZ + radius; z++) {
		                        if (!isInvalid) {
		                            String blockName = getBlockAtPos(new BlockPos(x, y, z)).getUnlocalizedName().toLowerCase();
		                            for (String s : invalidBlocks) {
		                                if (blockName.contains(s.toLowerCase())) isInvalid = true;
		                            }
		                        }
		                    }
		                }
		            }
		
		
		            if (isInvalid)
		                return;
		
		            float needed = getNeededStepHeight();
		            e.stepHeight = stepHeight;
		
		
		            if ((System.currentTimeMillis() - lastPacket) > (needed > 1.00 ? 355L : 100L)) {
		                offsets.forEach((a, b) -> {
		                    if (a == needed) {
		                    	timer.reset();
		                        for (double ab : b) {
		                            mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + ab, mc.thePlayer.posZ, true));
		                        }
		                        lastPacket = System.currentTimeMillis();
		                    }
		                });
		            }
		
		            lastStep = System.currentTimeMillis();
		            stepping = true;
		            lastFuck = System.currentTimeMillis();
		        }
		        if ((System.currentTimeMillis() - lastStep) > 150L && stepping) {
		            stepping = false;
		            mc.timer.timerSpeed = 1f;
		        }
			}
		}
	}
	
    public Block getBlockAtPos(BlockPos pos) {
        IBlockState blockState = getBlockStateAtPos(pos);
        if (blockState == null)
            return null;
        return blockState.getBlock();
    }
    
    public IBlockState getBlockStateAtPos(BlockPos pos) {
        if (Minecraft.getMinecraft() == null || Minecraft.getMinecraft().theWorld == null)
            return null;
        return Minecraft.getMinecraft().theWorld.getBlockState(pos);
    }
	
    private boolean couldStep() {
        float yaw = MoveUtils.getDirection();
        double x = -Math.sin(yaw) * 0.4;
        double z = Math.cos(yaw) * 0.4;
        return mc.theWorld.getCollidingBoundingBoxes(mc.thePlayer, mc.thePlayer.boundingBox.offset(x, 1.001335979112147, z)).isEmpty();
    }
    
    public float getNeededStepHeight() {
        if (mc.theWorld.getCollidingBoundingBoxes(mc.thePlayer, mc.thePlayer.boundingBox.offset(mc.thePlayer.motionX, 1.1, mc.thePlayer.motionZ)).size() == 0)
            return 1.0F;
        
        if (mc.theWorld.getCollidingBoundingBoxes(mc.thePlayer, mc.thePlayer.boundingBox.offset(mc.thePlayer.motionX, 1.6, mc.thePlayer.motionZ)).size() == 0)
            return 1.5F;
        
        return (float) 2D;
    }
}
