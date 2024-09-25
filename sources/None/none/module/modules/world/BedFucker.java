package none.module.modules.world;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockBed;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import none.event.Event;
import none.event.RegisterEvent;
import none.event.events.Event3D;
import none.event.events.EventPreMotionUpdate;
import none.event.events.EventTick;
import none.module.Category;
import none.module.Module;
import none.utils.RenderingUtil;
import none.utils.TimeHelper;
import none.valuesystem.NumberValue;

public class BedFucker extends Module{
	
	public NumberValue<Integer> range = new NumberValue<>("Range", 3, 1, 6);
	public NumberValue<Integer> delay = new NumberValue<>("Delay", 100, 50, 300);
	
	public BedFucker() {
		super("BedFucker", "BedFucker", Category.WORLD, Keyboard.KEY_N);
	}
	
	@Override
	protected void onEnable() {
		super.onEnable();
	}
	
	@Override
	protected void onDisable() {
		super.onDisable();
		if(blockBreaking != null)
    		blockBreaking = null;
	}
	
	public static BlockPos blockBreaking = null;
    public TimeHelper timer = new TimeHelper();
    public List<BlockPos> beds = new ArrayList<>();

	@Override
	@RegisterEvent(events = {EventPreMotionUpdate.class, Event3D.class, EventTick.class})
	public void onEvent(Event event) {
		if (!isEnabled()) return;
		
		int reach = range.getObject();
		if (event instanceof EventTick) {
			if (blockBreaking != null && timer.hasTimeReached(delay.getObject())) {
                if (mc.playerController.blockHitDelay > 1) {
                    mc.playerController.blockHitDelay = 1;
                }
                EnumFacing direction = getClosestEnum(blockBreaking);
                if (direction != null) {
//                    mc.playerController.onPlayerDamageBlock(blockBreaking, direction);
//                    mc.thePlayer.swingItem();
                    mc.getConnection().sendPacket(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.START_DESTROY_BLOCK, blockBreaking, direction));
                    mc.thePlayer.swingItem();
					mc.getConnection().sendPacket(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK, blockBreaking, direction));
					mc.playerController.curBlockDamageMP = 10F;
                }else {
                	timer.setLastMS();
                }
            }
		}
		
		if (event instanceof EventPreMotionUpdate) {
			EventPreMotionUpdate em = (EventPreMotionUpdate) event;
            if (em.isPre()) {
                for (int y = -reach; y <= reach; y++) {
                    for (int x = -reach; x <= reach; x++) {
                        for (int z = -reach; z <= reach; z++) {
                            BlockPos pos1 = new BlockPos(mc.thePlayer.posX + x, mc.thePlayer.posY + y, mc.thePlayer.posZ + z);
                            if (getFacingDirection(pos1) != null && blockChecks(mc.theWorld.getBlockState(pos1).getBlock()) && mc.thePlayer.getDistance(pos1.getX(), pos1.getY(), pos1.getZ()) <= range.getObject()) {
                            	if(!beds.contains(pos1))
                            		beds.add(pos1);
                            }
                        }
                    }
                }
                BlockPos closest = null;
                if(!beds.isEmpty())
                	for(int i = 0; i < beds.size(); i++){
                		BlockPos bed = beds.get(i);
                		if(mc.thePlayer.getDistance(bed.getX(), bed.getY(), bed.getZ()) > range.getObject()
                			 || mc.theWorld.getBlockState(bed).getBlock() != Blocks.bed){
                			beds.remove(i);
                		}
                		if(closest == null && mc.thePlayer.getDistance(bed.getX(), bed.getY(), bed.getZ()) < range.getObject()) {
                			closest = bed;
                		}
                	}
                if(closest != null){
                	float[] rot = getRotations(closest, getClosestEnum(closest));
                    em.setYaw(rot[0]);
                    em.setPitch(rot[1]);
                    blockBreaking = closest;
                    return;
                }else {
                	blockBreaking = null;
                }
            } else if (em.isPost()) {
                
            }
        } else if (event instanceof Event3D){
        	if (blockBreaking != null) {
                drawESP(blockBreaking.getX(), blockBreaking.getY(), blockBreaking.getZ(), blockBreaking.getX() + 1, blockBreaking.getY() + 0.5625, blockBreaking.getZ() + 1);
            }
        }
	}
	
	public void drawESP(double x, double y, double z, double x2, double y2, double z2) {
		RenderManager RenderManager = Minecraft.getMinecraft().getRenderManager();
        double x3 = x - RenderManager.renderPosX;
        double y3 = y - RenderManager.renderPosY;
        double z3 = z - RenderManager.renderPosZ;
        double x4 = x2 - RenderManager.renderPosX;
        double y4 = y2 - RenderManager.renderPosY;
        double z4 = z2 - RenderManager.renderPosZ;
        Color color = Color.CYAN;
        drawFilledBBESP(new AxisAlignedBB(x3, y3, z3, x4, y4, z4), color);
    }

    public void drawFilledBBESP(AxisAlignedBB axisalignedbb, Color color) {
        GL11.glPushMatrix();
        float red = (float)color.getRed()/255;
        float green = (float)color.getGreen()/255;
        float blue = (float)color.getBlue()/255;
        float alpha = 0.3f;
        RenderingUtil.pre3D();
        GL11.glDepthMask(false);
        GL11.glColor4f(red, green, blue, alpha);
        RenderingUtil.drawBoundingBox(axisalignedbb);
        GL11.glDepthMask(true);
        GL11.glColor4f(0.0f, 0.0f, 0.0f, 1.0f);
        RenderingUtil.post3D();
        GL11.glPopMatrix();
    }
	
	private boolean blockChecks(Block block) {
        return block == Blocks.bed;
    }

	public float[] getRotations(BlockPos block, EnumFacing face){
		double x = block.getX() + 0.5 - mc.thePlayer.posX + (double)face.getFrontOffsetX()/2;
		double z = block.getZ() + 0.5 - mc.thePlayer.posZ + (double)face.getFrontOffsetZ()/2;
		double d1 = mc.thePlayer.posY + mc.thePlayer.getEyeHeight() -(block.getY() + 0.5);
		double d3 = MathHelper.sqrt_double(x * x + z * z);
		float yaw = (float)(Math.atan2(z, x) * 180.0D / Math.PI) - 90.0F;
		float pitch = (float)(Math.atan2(d1, d3) * 180.0D / Math.PI);
		if(yaw < 0.0F){
			yaw += 360f;
		}
		return  new float[]{yaw, pitch};
	}
	
	private EnumFacing getClosestEnum(BlockPos pos){
     	EnumFacing closestEnum = EnumFacing.UP;
    	float rotations = MathHelper.wrapAngleTo180_float(getRotations(pos, EnumFacing.UP)[0]);
    	if(rotations >= 45 && rotations <= 135){
    		closestEnum = EnumFacing.EAST;
    	}else if((rotations >= 135 && rotations <= 180) ||
    			(rotations <= -135 && rotations >= -180)){
    		closestEnum = EnumFacing.SOUTH;
    	}else if(rotations <= -45 && rotations >= -135){
    		closestEnum = EnumFacing.WEST;
    	}else if((rotations >= -45 && rotations <= 0) ||
    			(rotations <= 45 && rotations >= 0)){
    		closestEnum = EnumFacing.NORTH;
    	}
    	if (MathHelper.wrapAngleTo180_float(getRotations(pos, EnumFacing.UP)[1]) > 75 || 
    			MathHelper.wrapAngleTo180_float(getRotations(pos, EnumFacing.UP)[1]) < -75){
    		closestEnum = EnumFacing.UP;
    	}
    	return closestEnum;
	}
	
	private EnumFacing getFacingDirection(BlockPos pos) {
        EnumFacing direction = null;
        if (mc.thePlayer.posY > pos.getY()) {
            direction = EnumFacing.UP;
        } else if ((mc.theWorld.getBlockState(pos.add(0, -1, 0)).getBlock() instanceof BlockAir || mc.theWorld.getBlockState(pos.add(0, -1, 0)).getBlock().isFullCube()) && !(mc.theWorld.getBlockState(pos.add(0, -1, 0)).getBlock() instanceof BlockBed)) {
            direction = EnumFacing.DOWN;
        } else if ((mc.theWorld.getBlockState(pos.add(1, 0, 0)).getBlock() instanceof BlockAir || mc.theWorld.getBlockState(pos.add(1, 0, 0)).getBlock().isFullCube()) && !(mc.theWorld.getBlockState(pos.add(1, 0, 0)).getBlock() instanceof BlockBed)) {
            direction = EnumFacing.EAST;
        } else if ((mc.theWorld.getBlockState(pos.add(-1, 0, 0)).getBlock() instanceof BlockAir || mc.theWorld.getBlockState(pos.add(-1, 0, 0)).getBlock().isFullCube()) && !(mc.theWorld.getBlockState(pos.add(-1, 0, 0)).getBlock() instanceof BlockBed)) {
            direction = EnumFacing.WEST;
        } else if ((mc.theWorld.getBlockState(pos.add(0, 0, 1)).getBlock() instanceof BlockAir || mc.theWorld.getBlockState(pos.add(0, 0, 1)).getBlock().isFullCube()) && !(mc.theWorld.getBlockState(pos.add(0, 0, 1)).getBlock() instanceof BlockBed)) {
            direction = EnumFacing.SOUTH;
        } else if ((mc.theWorld.getBlockState(pos.add(0, 0, -1)).getBlock() instanceof BlockAir || mc.theWorld.getBlockState(pos.add(0, 0, -1)).getBlock().isFullCube()) && !(mc.theWorld.getBlockState(pos.add(0, 0, -1)).getBlock() instanceof BlockBed)) {
            direction = EnumFacing.NORTH;
        }
//        MovingObjectPosition rayResult = mc.theWorld.rayTraceBlocks(new Vec3(mc.thePlayer.posX, mc.thePlayer.posY + mc.thePlayer.getEyeHeight(), mc.thePlayer.posZ), new Vec3(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5));
//        if (rayResult != null && rayResult.getBlockPos() == pos) {
//            return rayResult.sideHit;
//        }
        return direction;
    }
}
