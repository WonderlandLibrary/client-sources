package me.vaziak.sensation.client.impl.misc;

import me.vaziak.sensation.client.api.event.annotations.Collect;
import me.vaziak.sensation.client.api.event.events.PlayerUpdateEvent;
import me.vaziak.sensation.client.api.Category;
import me.vaziak.sensation.client.api.Module;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

public class Nuker extends Module {
    public Nuker() {
        super("Nuker", Category.MISC);
        position = 0;
    }
    
    double position;

    @Collect
    public void onPlayerUpdate(PlayerUpdateEvent event) {
        BlockPos playerPos = new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY + position, mc.thePlayer.posZ);

        BlockData blockData = retriveBlockData(1, true, true, playerPos);
        if (position >= 3) {
        	position = !mc.gameSettings.keyBindSneak.isKeyDown() ? 0 : -1;
        } else {
        	position ++;
        }
        if (blockData == null)
            return;

        int slot = mc.thePlayer.inventory.currentItem;
        
        
        
        mc.playerController.onPlayerDamageBlock(blockData.position, blockData.face);

        mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.START_DESTROY_BLOCK, blockData.position, blockData.face));

    }

    private class BlockData {
        //Taken from ye old MCP - go search the shit
        public BlockPos position;
        public EnumFacing face;

        private BlockData(BlockPos position, EnumFacing face) {
            this.position = position;
            this.face = face;
        }
    }

    /*
     * Block Data getter with all your cardinal directions + upwards facing,
     *
     * This can be used to make things like:
     *  - Scaffolds
     *  - ChestAuras
     *  - AutoBuilders
     *  - AutoPots (I know some people used blockdata to get a spot to look at lol)
     *  - Bed Breakers/Nukers
     *  - Decimators/Nukers
     *  - Anything that requires you to get a SHITLOAD of surrounding blocks
     *
     *
     *  IF ANYONE MANAGES TO CONVERT THIS TO LESS THAN 400 LINES USING A FORLOOP OR A LAMBDA CONGRATS
     *
     *  PLEASE FUCKING DM ME LOL
     */
    private BlockData retriveBlockData(int i, boolean placeable, boolean godown, BlockPos pos) {
        EnumFacing up =  EnumFacing.UP;
        EnumFacing east = EnumFacing.EAST;
        EnumFacing west = EnumFacing.WEST;
        EnumFacing north = EnumFacing.NORTH;
        EnumFacing south = EnumFacing.SOUTH;

        if (isValidPosition(true,pos.add(0, -i, 0))) {
            //return new BlockData(pos.add(0, -i, 0), up);
        }
        if (isValidPosition(true,pos.add(0, 0, i))) {
            return new BlockData(pos.add(0, 0, i), north);
        }
        if (isValidPosition(true,pos.add(0, 0, -i))) {
            return new BlockData(pos.add(0, 0, -i), south);
        }
        if (isValidPosition(true,pos.add(-i, 0, 0))) {
            return new BlockData(pos.add(-i, 0, 0), east);
        }
        if (isValidPosition(true,pos.add(i, 0, 0))) {
            return new BlockData(pos.add(i, 0, 0), west);
        }
        if (isValidPosition(true,pos.add(-i, 0, 0).add(0, -i, 0))) {
            //return new BlockData(pos.add(-i, 0, 0).add(0, -i, 0), up);
        }
        if (isValidPosition(true,pos.add(-i, 0, 0).add(0, 0, i))) {
            return new BlockData(pos.add(-i, 0, 0).add(0, 0, i), north);
        }
        if (isValidPosition(true,pos.add(-i, 0, 0).add(0, 0, -i))) {
            return new BlockData(pos.add(-i, 0, 0).add(0, 0, -i), south);
        }
        if (isValidPosition(true,pos.add(-i, 0, 0).add(-i, 0, 0))) {
            return new BlockData(pos.add(-i, 0, 0).add(-i, 0, 0), east);
        }
        if (isValidPosition(true,pos.add(-i, 0, 0).add(i, 0, 0))) {
            return new BlockData(pos.add(-i, 0, 0).add(i, 0, 0), west);
        }
        if (isValidPosition(true,pos.add(i, 0, 0).add(0, -i, 0))) {
            //return new BlockData(pos.add(i, 0, 0).add(0, -i, 0), up);
        }
        if (isValidPosition(true,pos.add(i, 0, 0).add(0, 0, i))) {
            return new BlockData(pos.add(i, 0, 0).add(0, 0, i), north);
        }
        if (isValidPosition(true,pos.add(i, 0, 0).add(0, 0, -i))) {
            return new BlockData(pos.add(i, 0, 0).add(0, 0, -i), south);
        }
        if (isValidPosition(true,pos.add(i, 0, 0).add(-i, 0, 0))) {
            return new BlockData(pos.add(i, 0, 0).add(-i, 0, 0), east);
        }
        if (isValidPosition(true,pos.add(i, 0, 0).add(i, 0, 0))) {
            return new BlockData(pos.add(i, 0, 0).add(i, 0, 0), west);
        }
        if (isValidPosition(true,pos.add(0, 0, i).add(0, -i, 0))) {
            //return new BlockData(pos.add(0, 0, i).add(0, -i, 0), up);
        }
        if (isValidPosition(true,pos.add(0, 0, i).add(0, 0, i))) {
            return new BlockData(pos.add(0, 0, i).add(0, 0, i), north);
        }
        if (isValidPosition(true,pos.add(0, 0, i).add(0, 0, -i))) {
            return new BlockData(pos.add(0, 0, i).add(0, 0, -i), south);
        }
        if (isValidPosition(true,pos.add(0, 0, i).add(-i, 0, 0))) {
            return new BlockData(pos.add(0, 0, i).add(-i, 0, 0), east);
        }
        if (isValidPosition(true,pos.add(0, 0, i).add(i, 0, 0))) {
            return new BlockData(pos.add(0, 0, i).add(i, 0, 0), west);
        }
        if (isValidPosition(true,pos.add(0, 0, -i).add(0, -i, 0))) {
            //return new BlockData(pos.add(0, 0, -i).add(0, -i, 0), up);
        }
        if (isValidPosition(true,pos.add(0, 0, -i).add(0, 0, i))) {
            return new BlockData(pos.add(0, 0, -i).add(0, 0, i), north);
        }
        if (isValidPosition(true,pos.add(0, 0, -i).add(0, 0, -i))) {
            return new BlockData(pos.add(0, 0, -i).add(0, 0, -i), south);
        }
        if (isValidPosition(true,pos.add(0, 0, -i).add(-i, 0, 0))) {
            return new BlockData(pos.add(0, 0, -i).add(-i, 0, 0), east);
        }
        if (isValidPosition(true,pos.add(0, 0, -i).add(i, 0, 0))) {
            return new BlockData(pos.add(0, 0, -i).add(i, 0, 0), west);
        }
        if (isValidPosition(true,pos.add(-i, 0, 0).add(0, -i, 0))) {
            //return new BlockData(pos.add(-i, 0, 0).add(0, -i, 0), up);
        }
        if (isValidPosition(true,pos.add(-i, 0, 0).add(0, 0, i))) {
            return new BlockData(pos.add(-i, 0, 0).add(0, 0, i), north);
        }
        if (isValidPosition(true,pos.add(-i, 0, 0).add(0, 0, -i))) {
            return new BlockData(pos.add(-i, 0, 0).add(0, 0, -i), south);
        }
        if (isValidPosition(true,pos.add(-i, 0, 0).add(-i, 0, 0))) {
            return new BlockData(pos.add(-i, 0, 0).add(-i, 0, 0), east);
        }
        if (isValidPosition(true,pos.add(-i, 0, 0).add(i, 0, 0))) {
            return new BlockData(pos.add(-i, 0, 0).add(i, 0, 0), west);
        }
        if (isValidPosition(true,pos.add(i, 0, 0).add(0, -i, 0))) {
            //return new BlockData(pos.add(i, 0, 0).add(0, -i, 0), up);
        }
        if (isValidPosition(true,pos.add(i, 0, 0).add(0, 0, i))) {
            return new BlockData(pos.add(i, 0, 0).add(0, 0, i), north);
        }
        if (isValidPosition(true,pos.add(i, 0, 0).add(0, 0, -i))) {
            return new BlockData(pos.add(i, 0, 0).add(0, 0, -i), south);
        }
        if (isValidPosition(true,pos.add(i, 0, 0).add(-i, 0, 0))) {
            return new BlockData(pos.add(i, 0, 0).add(-i, 0, 0), east);
        }
        if (isValidPosition(true,pos.add(i, 0, 0).add(i, 0, 0))) {
            return new BlockData(pos.add(i, 0, 0).add(i, 0, 0), west);
        }
        if (isValidPosition(true,pos.add(0, 0, i).add(0, -i, 0))) {
            //return new BlockData(pos.add(0, 0, i).add(0, -i, 0), up);
        }
        if (isValidPosition(true,pos.add(0, 0, i).add(0, 0, i))) {
            return new BlockData(pos.add(0, 0, i).add(0, 0, i), north);
        }
        if (isValidPosition(true,pos.add(0, 0, i).add(0, 0, -i))) {
            return new BlockData(pos.add(0, 0, i).add(0, 0, -i), south);
        }
        if (isValidPosition(true,pos.add(0, 0, i).add(-i, 0, 0))) {
            return new BlockData(pos.add(0, 0, i).add(-i, 0, 0), east);
        }
        if (isValidPosition(true,pos.add(0, 0, i).add(i, 0, 0))) {
            return new BlockData(pos.add(0, 0, i).add(i, 0, 0), west);
        }
        if (isValidPosition(true,pos.add(0, 0, -i).add(0, -i, 0))) {
            //return new BlockData(pos.add(0, 0, -i).add(0, -i, 0), up);
        }
        if (isValidPosition(true,pos.add(0, 0, -i).add(0, 0, i))) {
            return new BlockData(pos.add(0, 0, -i).add(0, 0, i), north);
        }
        if (isValidPosition(true,pos.add(0, 0, -i).add(0, 0, -i))) {
            return new BlockData(pos.add(0, 0, -i).add(0, 0, -i), south);
        }
        if (isValidPosition(true,pos.add(0, 0, -i).add(-i, 0, 0))) {
            return new BlockData(pos.add(0, 0, -i).add(-i, 0, 0), east);
        }
        if (isValidPosition(true,pos.add(0, 0, -i).add(i, 0, 0))) {
            return new BlockData(pos.add(0, 0, -i).add(i, 0, 0), west);
        }
        if (isValidPosition(true,pos.add(0, -i, 0).add(0, -i, 0))) {
            //return new BlockData(pos.add(0, -i, 0).add(0, -i, 0), up);
        }
        if (isValidPosition(true,pos.add(0, -i, 0).add(0, 0, i))) {
            //return new BlockData(pos.add(0, -i, 0).add(0, 0, i), north);
        }
        if (isValidPosition(true,pos.add(0, -i, 0).add(0, 0, -i))) {
            //return new BlockData(pos.add(0, -i, 0).add(0, 0, -i), south);
        }
        if (isValidPosition(true,pos.add(0, -i, 0).add(-i, 0, 0))) {
            //return new BlockData(pos.add(0, -i, 0).add(-i, 0, 0), east);
        }
        if (isValidPosition(true,pos.add(0, -i, 0).add(i, 0, 0))) {
            //return new BlockData(pos.add(0, -i, 0).add(i, 0, 0), west);
        }
        return null;
    }

    public static boolean isValidPosition(boolean placeable, BlockPos pos) {
        Block block = Minecraft.getMinecraft().theWorld.getBlockState(pos).getBlock();
        return !(block instanceof BlockAir);
    }
}
