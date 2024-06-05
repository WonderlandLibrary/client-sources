package Ascii4UwUWareClient.Module.Modules.Misc;

import Ascii4UwUWareClient.API.EventHandler;
import Ascii4UwUWareClient.API.Events.World.EventPreUpdate;
import Ascii4UwUWareClient.API.Value.Mode;
import Ascii4UwUWareClient.Module.Module;
import Ascii4UwUWareClient.Module.ModuleType;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

public class Fucker
extends Module {
	
	private int xPos;
    private int yPos;
    private int zPos;
    private static int radius = 5;
    private Mode<Enum> mode = new Mode("Mode", "Mode", (Enum[])Break.values(), (Enum)Break.Bed);
	
    public Fucker() {
        super("Fucker", new String[]{"fplace", "fc"}, ModuleType.Misc);
        this.addValues(this.mode);
    }

    @EventHandler
    private void onUpdate(EventPreUpdate event) {
    	 this.setSuffix(this.mode.getValue());
        int x = - radius;
        while (x < radius) {
            int y = radius;
            while (y > - radius) {
                int z = - radius;
                while (z < radius) {
                    this.xPos = (int)Minecraft.thePlayer.posX + x;
                    this.yPos = (int)Minecraft.thePlayer.posY + y;
                    this.zPos = (int)Minecraft.thePlayer.posZ + z;
                    BlockPos blockPos = new BlockPos(this.xPos, this.yPos, this.zPos);
                    Block block = Minecraft.theWorld.getBlockState(blockPos).getBlock();
                    if (block.getBlockState().getBlock() == Block.getBlockById(92) && this.mode.getValue() == Break.Cake) {
                        Minecraft.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.START_DESTROY_BLOCK, blockPos, EnumFacing.NORTH));
                        Minecraft.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK, blockPos, EnumFacing.NORTH));
                    } else if (block.getBlockState().getBlock() == Block.getBlockById(122) && this.mode.getValue() == Break.Egg) {
                        Minecraft.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.START_DESTROY_BLOCK, blockPos, EnumFacing.NORTH));
                        Minecraft.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK, blockPos, EnumFacing.NORTH));
                    } else if (block.getBlockState().getBlock() == Block.getBlockById(26) && this.mode.getValue() == Break.Bed) {
                        Minecraft.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.START_DESTROY_BLOCK, blockPos, EnumFacing.NORTH));
                        Minecraft.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK, blockPos, EnumFacing.NORTH));
                    }
                    ++z;
                }
                --y;
            }
            ++x;
        }
    }
    
    static enum Break {
    	Cake,
    	Egg,
    	Bed;
    }
}


