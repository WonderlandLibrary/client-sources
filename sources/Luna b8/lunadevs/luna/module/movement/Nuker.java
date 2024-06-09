package lunadevs.luna.module.movement;

import javax.vecmath.Vector3f;

import org.lwjgl.input.Keyboard;

import lunadevs.luna.category.Category;
import lunadevs.luna.module.Module;
import lunadevs.luna.utils.RenderUtils;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C07PacketPlayerDigging.Action;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;


public class Nuker extends Module {

	public Nuker() {
		super("Nuker", Keyboard.KEY_NONE, Category.MOVEMENT, false);
	}
	    private int xPos;
	    private int yPos;
	    private int zPos;
	    private static int radius = 4;
	 
	    @Override
	    public void onUpdate() {
	        if(!this.isEnabled) return;
	        for(int x = -radius; x < radius; x++) {
	            for(int y = radius; y > -radius; y--) {
	                for(int z = -radius; z < radius; z++) {
	                    this.xPos = (int)mc.thePlayer.posX + x;
	                    this.yPos = (int)mc.thePlayer.posY + y;
	                    this.zPos = (int)mc.thePlayer.posZ + z;
	                   
	                        BlockPos blockPos = new BlockPos(this.xPos, this.yPos, this.zPos);
	                        Block block = mc.theWorld.getBlockState(blockPos).getBlock();
	                   
	                        if(block.getMaterial() == Material.air)
	                            continue;
	                       
	                        mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(Action.START_DESTROY_BLOCK, blockPos, EnumFacing.NORTH));
	                        mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(Action.STOP_DESTROY_BLOCK, blockPos, EnumFacing.NORTH));
	                }
	            }
	        }
	    }
	    
	    @Override
	    public void onRender() {
	       
	        if(!this.isEnabled)
	            return;
	                   
	        for(int x = -radius; x < radius; x++) {
	            for(int y = radius; y > -radius; y--) {
	                for(int z = -radius; z < radius; z++) {
	                    this.xPos = (int)mc.thePlayer.posX + x;
	                    this.yPos = (int)mc.thePlayer.posY + y;
	                    this.zPos = (int)mc.thePlayer.posZ + z;
	                   
	                        BlockPos blockPos = new BlockPos(this.xPos, this.yPos, this.zPos);
	                        Block block = mc.theWorld.getBlockState(blockPos).getBlock();
	                   
	                        if(block.getMaterial() == Material.air)
	                            continue;
	                       
	                        double renderX = this.xPos - mc.getRenderManager().renderPosX;
	                        double renderY = this.yPos - mc.getRenderManager().renderPosY;
	                        double renderZ = this.zPos - mc.getRenderManager().renderPosZ;
	                       
	                        RenderUtils.drawOutlinedBlockESP(renderX, renderY, renderZ, 0F, 1.50F, 1.30F, 1.50F, 2F);
	                }
	            }
	        }
	       
	        super.onRender();
	    }
	
	public String getValue(){
		return null;
	}
	

}
