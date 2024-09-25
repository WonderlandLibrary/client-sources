package eze.modules.player;

import eze.modules.*;
import java.util.*;
import net.minecraft.block.*;
import eze.util.*;
import eze.events.*;
import net.minecraft.client.*;
import net.minecraft.util.*;
import net.minecraft.item.*;

public class ScaffoldNotWorking extends Module
{
    private static List<Block> invalid;
    private BlockData blockData;
    boolean placing;
    private int slot;
    Timer timer;
    
    public ScaffoldNotWorking() {
        super("Crash the game - Scaffold", 0, Category.PLAYER);
        this.timer = new Timer();
    }
    
    @Override
    public void onEvent(final Event event) {
        throw new Error("Unresolved compilation problem: \n\tThe method getBlockData(BlockPos, List) in the type ScaffoldNotWorking is not applicable for the arguments (BlockPos, ScaffoldNotWorking.BlockData)\n");
    }
    
    public static BlockData getBlockData(final BlockPos pos, final List list) {
        System.out.println(new StringBuilder().append(list).toString());
        return list.contains(Minecraft.getMinecraft().theWorld.getBlockState(pos.add(0, -1, 0)).getBlock()) ? (list.contains(Minecraft.getMinecraft().theWorld.getBlockState(pos.add(-1, 0, 0)).getBlock()) ? (list.contains(Minecraft.getMinecraft().theWorld.getBlockState(pos.add(1, 0, 0)).getBlock()) ? (list.contains(Minecraft.getMinecraft().theWorld.getBlockState(pos.add(0, 0, -1)).getBlock()) ? (list.contains(Minecraft.getMinecraft().theWorld.getBlockState(pos.add(0, 0, 1)).getBlock()) ? null : new BlockData(pos.add(0, 0, 1), EnumFacing.NORTH)) : new BlockData(pos.add(0, 0, -1), EnumFacing.SOUTH)) : new BlockData(pos.add(1, 0, 0), EnumFacing.WEST)) : new BlockData(pos.add(-1, 0, 0), EnumFacing.EAST)) : new BlockData(pos.add(0, -1, 0), EnumFacing.UP);
    }
    
    public static Block getBlock(final int x, final int y, final int z) {
        return Minecraft.getMinecraft().theWorld.getBlockState(new BlockPos(x, y, z)).getBlock();
    }
    
    private int getBlockSlot() {
        for (int i = 36; i < 45; ++i) {
            final ItemStack itemStack = this.mc.thePlayer.inventoryContainer.getSlot(i).getStack();
            if (itemStack != null && itemStack.getItem() instanceof ItemBlock) {
                return i - 36;
            }
        }
        return -1;
    }
    
    public static class BlockData
    {
        public BlockPos position;
        public EnumFacing face;
        
        public BlockData(final BlockPos position, final EnumFacing face) {
            this.position = position;
            this.face = face;
        }
    }
}
