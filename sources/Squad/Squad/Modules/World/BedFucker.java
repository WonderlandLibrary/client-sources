package Squad.Modules.World;

import com.darkmagician6.eventapi.EventTarget;

import Squad.Events.EventUpdate;
import Squad.Utils.TimeHelper;
import Squad.base.Module;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

public class BedFucker
extends Module
{
private int xPos;
private int yPos;
private int zPos;
private static int radius = 5;
TimeHelper time = new TimeHelper();

public BedFucker()
{
  super("BedFucker", 48, 16777215, Category.World);
}

@EventTarget
public void onUpdate(EventUpdate ev) {
	int playerX = (int) mc.thePlayer.posX;  
	int playerZ = (int) mc.thePlayer.posZ;
	int playerY = (int) mc.thePlayer.posY;
	//get players position
	for (int y = playerY -6; y <= playerY + 6; y++) {         //loop through xyz
		for (int x = playerX -6; x <= playerX + 6; x++) {        //loop through xyz
			for (int z = playerZ -6; z <= playerZ + 6; z++) {      //loop through xyz
				BlockPos pos = new BlockPos(x, y, z);  // create Blockpos to check the blocks material
				if (mc.theWorld.getBlockState(pos).getBlock() == Blocks.bed) {  //if blockmaterial equals bed then ---> destroy block
					if(time.hasReached(250)) {   //reducing autobans, because your mining the block very fast
					destroyBlock(pos);  //destroy Block
					time.setLastMS(); //reset timer
					}
				}
			}
		}
	}
}

private void destroyBlock(BlockPos blockPos) {  //hand over the blockpos which you want to destroy
	mc.thePlayer.sendQueue.addToSendQueue(new C0APacketAnimation()); //prevent a noswing flag, serversided swing, if you want to swing the hand clientsided just do mc.thePlayer.swingItem();
	mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.START_DESTROY_BLOCK, blockPos, EnumFacing.UP)); //start destroying the block at the specific blockpos
	mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK, blockPos, EnumFacing.UP)); //stop destroying the block at the specific blockpos
}

}