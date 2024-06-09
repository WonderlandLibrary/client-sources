package host.kix.uzi.module.modules.movement;

import com.darkmagician6.eventapi.SubscribeEvent;

import host.kix.uzi.events.BoundingBoxEvent;
import host.kix.uzi.events.UpdateEvent;
import host.kix.uzi.module.Module;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockLiquid;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;

/**
 * Created by myche on 4/13/2017.
 */
public class Dolphin extends Module {

    public Dolphin() {
        super("Dolphin", 0, Category.MOVEMENT);
    }

    @SubscribeEvent
    public void update(UpdateEvent e){
    	if(mc.gameSettings.keyBindSneak.getIsKeyPressed()) {
    		return;
    	}
    	//Add a check for if water is nearby
    	if(mc.thePlayer.moveForward > 0) {
    		mc.thePlayer.setSprinting(true);
    	}
    	mc.thePlayer.setJumping(true);
        if(isInLiquid()){
            mc.thePlayer.jump();
        }	
    }
    
    @SubscribeEvent
    public void onBoundingBox(BoundingBoxEvent e){
        if(((e.getBlock() instanceof BlockLiquid)) && e.getEntity() == mc.thePlayer && (!this.isInLiquid())
                && (mc.thePlayer.fallDistance < 3.0F) && (!mc.thePlayer.isSneaking())){
            e.setBoundingBox(AxisAlignedBB.fromBounds(e.getLocation().getX(), e
                    .getLocation().getY(), e.getLocation().getZ(), e
                    .getLocation().getX() + 1, e.getLocation().getY() + .9, e
                    .getLocation().getZ() + 1));
        }
        
    }
  

    public boolean isInLiquid() {
    	if(mc.thePlayer.isSprinting()) {
    		mc.thePlayer.motionX /= 1.025;
    		mc.thePlayer.motionZ /= 1.025;
    		mc.thePlayer.motionY /= 1.005f;
    	}
        if (mc.thePlayer == null) return false;
        boolean inLiquid = false;
        int y = (int) mc.thePlayer.boundingBox.minY;
        for (int x = MathHelper.floor_double(mc.thePlayer.boundingBox.minX); x < MathHelper.floor_double(mc.thePlayer.boundingBox.maxX) + .15; x++) {
            for (int z = MathHelper.floor_double(mc.thePlayer.boundingBox.minZ); z < MathHelper.floor_double(mc.thePlayer.boundingBox.maxZ) + .15; z++) {
                Block block = mc.theWorld.getBlockState(new BlockPos(x, y, z)).getBlock();
                if ((block != null) && (!(block instanceof BlockAir))) {
                    if (!(block instanceof BlockLiquid)) return false;
                    inLiquid = true;
                }
            }
        }
        return inLiquid;
    }

}
