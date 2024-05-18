package Squad.Modules.World;

import org.lwjgl.input.Keyboard;

import com.darkmagician6.eventapi.EventTarget;

import Squad.Squad;
import Squad.Events.EventUpdate;
import Squad.base.Module;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.BlockPos;

public class TowerRewi extends Module{

	
	public TowerRewi() {
		super("Tower", Keyboard.KEY_NONE, 0x99, Category.Other);
		// TODO Auto-generated constructor stub
	}

	double groundy = 0;
	
	@EventTarget
	public void onUpdate(EventUpdate e){
    	if(Squad.instance.setmgr.getSettingByName("TowerMode").getValString().equalsIgnoreCase("RewiTower")){
    	if(!Minecraft.getMinecraft().thePlayer.isSneaking())
    		return;
    	BlockPos at = new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 2, mc.thePlayer.posZ);
		ItemStack is = mc.thePlayer.getCurrentEquippedItem();
                
                // check for block in hand
		if (is == null || !(is.getItem() instanceof ItemBlock)) {
			return;
		}

                // check for block under player
		if (mc.theWorld.getBlockState(at).getBlock() == Blocks.air)
			if (mc.thePlayer.onGround)
				mc.thePlayer.jump();
			else
				return;

                // set silent player look
		Minecraft.getMinecraft().thePlayer.cameraPitch = 90F;
		Minecraft.getMinecraft().thePlayer.cameraYaw = 180F;

                // jump if onground
		if (mc.thePlayer.onGround) {
			groundy = mc.thePlayer.posY;
			jump();
		}


                // place block if possible & jump
		if (mc.thePlayer.posY > groundy + 0.9) {
			mc.thePlayer.setPosition(mc.thePlayer.posX, (int) (mc.thePlayer.posY), mc.thePlayer.posZ);
			placeBlock();
			jump();
			groundy = mc.thePlayer.posY;
		}
		super.onUpdate();
	}
    	}
	
	public void placeBlock() {

		BlockPos at = new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY - 2, mc.thePlayer.posZ);
		ItemStack is = mc.thePlayer.getCurrentEquippedItem();

                // check for block to place at
		if (mc.theWorld.getBlockState(at.add(0, 1, 0)).getBlock() != Blocks.air) {
			return;
		}

                // play arm animation
		mc.thePlayer.swingItem();

                // place block for client
		IBlockState ibl = ((ItemBlock) is.getItem()).getBlock().getDefaultState();
		mc.theWorld.setBlockState(at.add(0, 1, 0), ibl);

                // place block for server
		C08PacketPlayerBlockPlacement bl = new C08PacketPlayerBlockPlacement(at, 1,
				mc.thePlayer.getCurrentEquippedItem(), 0, 0, 0);
		mc.thePlayer.sendQueue.addToSendQueue(bl);
	}

	public void jump() {
		mc.thePlayer.motionY = 0.42f;
	}
}
	
	
