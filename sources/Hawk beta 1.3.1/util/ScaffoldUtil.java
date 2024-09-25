package eze.util;

import net.minecraft.client.*;
import net.minecraft.block.*;
import net.minecraft.init.*;
import java.util.*;
import net.minecraft.util.*;
import net.minecraft.item.*;
import net.minecraft.entity.player.*;

public class ScaffoldUtil
{
    private static Minecraft mc;
    private static List<Block> blockBlacklist;
    
    static {
        ScaffoldUtil.mc = Minecraft.getMinecraft();
        ScaffoldUtil.blockBlacklist = Arrays.asList(Blocks.air, Blocks.water, Blocks.tnt, Blocks.chest, Blocks.flowing_water, Blocks.lava, Blocks.flowing_lava, Blocks.enchanting_table, Blocks.carpet, Blocks.chest, Blocks.torch, Blocks.anvil, Blocks.trapped_chest, Blocks.noteblock, Blocks.jukebox, Blocks.iron_ore, Blocks.lapis_ore, Blocks.wooden_pressure_plate, Blocks.stone_pressure_plate, Blocks.light_weighted_pressure_plate, Blocks.heavy_weighted_pressure_plate, Blocks.stone_button, Blocks.wooden_button, Blocks.lever, Blocks.enchanting_table);
    }
    
    public static BlockInfo getBlockData(final BlockPos var1) {
        if (!ScaffoldUtil.blockBlacklist.contains(ScaffoldUtil.mc.theWorld.getBlockState(var1.add(0, -1, 0)).getBlock())) {
            return new BlockInfo(var1.add(0, -1, 0), EnumFacing.UP);
        }
        if (!ScaffoldUtil.blockBlacklist.contains(ScaffoldUtil.mc.theWorld.getBlockState(var1.add(-1, 0, 0)).getBlock())) {
            return new BlockInfo(var1.add(-1, 0, 0), EnumFacing.EAST);
        }
        if (!ScaffoldUtil.blockBlacklist.contains(ScaffoldUtil.mc.theWorld.getBlockState(var1.add(1, 0, 0)).getBlock())) {
            return new BlockInfo(var1.add(1, 0, 0), EnumFacing.WEST);
        }
        if (!ScaffoldUtil.blockBlacklist.contains(ScaffoldUtil.mc.theWorld.getBlockState(var1.add(0, 0, -1)).getBlock())) {
            return new BlockInfo(var1.add(0, 0, -1), EnumFacing.SOUTH);
        }
        if (!ScaffoldUtil.blockBlacklist.contains(ScaffoldUtil.mc.theWorld.getBlockState(var1.add(0, 0, 1)).getBlock())) {
            return new BlockInfo(var1.add(0, 0, 1), EnumFacing.NORTH);
        }
        final BlockPos add = var1.add(-1, 0, 0);
        if (!ScaffoldUtil.blockBlacklist.contains(ScaffoldUtil.mc.theWorld.getBlockState(add.add(-1, 0, 0)).getBlock())) {
            return new BlockInfo(add.add(-1, 0, 0), EnumFacing.EAST);
        }
        if (!ScaffoldUtil.blockBlacklist.contains(ScaffoldUtil.mc.theWorld.getBlockState(add.add(1, 0, 0)).getBlock())) {
            return new BlockInfo(add.add(1, 0, 0), EnumFacing.WEST);
        }
        if (!ScaffoldUtil.blockBlacklist.contains(ScaffoldUtil.mc.theWorld.getBlockState(add.add(0, 0, -1)).getBlock())) {
            return new BlockInfo(add.add(0, 0, -1), EnumFacing.SOUTH);
        }
        if (!ScaffoldUtil.blockBlacklist.contains(ScaffoldUtil.mc.theWorld.getBlockState(add.add(0, 0, 1)).getBlock())) {
            return new BlockInfo(add.add(0, 0, 1), EnumFacing.NORTH);
        }
        final BlockPos add2 = var1.add(1, 0, 0);
        if (!ScaffoldUtil.blockBlacklist.contains(ScaffoldUtil.mc.theWorld.getBlockState(add2.add(-1, 0, 0)).getBlock())) {
            return new BlockInfo(add2.add(-1, 0, 0), EnumFacing.EAST);
        }
        if (!ScaffoldUtil.blockBlacklist.contains(ScaffoldUtil.mc.theWorld.getBlockState(add2.add(1, 0, 0)).getBlock())) {
            return new BlockInfo(add2.add(1, 0, 0), EnumFacing.WEST);
        }
        if (!ScaffoldUtil.blockBlacklist.contains(ScaffoldUtil.mc.theWorld.getBlockState(add2.add(0, 0, -1)).getBlock())) {
            return new BlockInfo(add2.add(0, 0, -1), EnumFacing.SOUTH);
        }
        if (!ScaffoldUtil.blockBlacklist.contains(ScaffoldUtil.mc.theWorld.getBlockState(add2.add(0, 0, 1)).getBlock())) {
            return new BlockInfo(add2.add(0, 0, 1), EnumFacing.NORTH);
        }
        final BlockPos add3 = var1.add(0, 0, -1);
        if (!ScaffoldUtil.blockBlacklist.contains(ScaffoldUtil.mc.theWorld.getBlockState(add3.add(-1, 0, 0)).getBlock())) {
            return new BlockInfo(add3.add(-1, 0, 0), EnumFacing.EAST);
        }
        if (!ScaffoldUtil.blockBlacklist.contains(ScaffoldUtil.mc.theWorld.getBlockState(add3.add(1, 0, 0)).getBlock())) {
            return new BlockInfo(add3.add(1, 0, 0), EnumFacing.WEST);
        }
        if (!ScaffoldUtil.blockBlacklist.contains(ScaffoldUtil.mc.theWorld.getBlockState(add3.add(0, 0, -1)).getBlock())) {
            return new BlockInfo(add3.add(0, 0, -1), EnumFacing.SOUTH);
        }
        if (!ScaffoldUtil.blockBlacklist.contains(ScaffoldUtil.mc.theWorld.getBlockState(add3.add(0, 0, 1)).getBlock())) {
            return new BlockInfo(add3.add(0, 0, 1), EnumFacing.NORTH);
        }
        final BlockPos add4 = var1.add(0, 0, 1);
        if (!ScaffoldUtil.blockBlacklist.contains(ScaffoldUtil.mc.theWorld.getBlockState(add4.add(-1, 0, 0)).getBlock())) {
            return new BlockInfo(add4.add(-1, 0, 0), EnumFacing.EAST);
        }
        if (!ScaffoldUtil.blockBlacklist.contains(ScaffoldUtil.mc.theWorld.getBlockState(add4.add(1, 0, 0)).getBlock())) {
            return new BlockInfo(add4.add(1, 0, 0), EnumFacing.WEST);
        }
        if (!ScaffoldUtil.blockBlacklist.contains(ScaffoldUtil.mc.theWorld.getBlockState(add4.add(0, 0, -1)).getBlock())) {
            return new BlockInfo(add4.add(0, 0, -1), EnumFacing.SOUTH);
        }
        if (!ScaffoldUtil.blockBlacklist.contains(ScaffoldUtil.mc.theWorld.getBlockState(add4.add(0, 0, 1)).getBlock())) {
            return new BlockInfo(add4.add(0, 0, 1), EnumFacing.NORTH);
        }
        return null;
    }
    
    public static boolean isEmpty(final ItemStack stack) {
        return stack == null;
    }
    
    public static Vec3d getVec3d(final BlockPos pos, final EnumFacing face) {
        double x = pos.getX() + 0.5026836562;
        double y = pos.getY() + 0.5026836562;
        double z = pos.getZ() + 0.5026836562;
        x += face.getFrontOffsetX() / 2.0;
        z += face.getFrontOffsetZ() / 2.0;
        y += face.getFrontOffsetY() / 2.0;
        if (face == EnumFacing.UP || face == EnumFacing.DOWN) {
            x += randomNumber(0.30526836562, -0.30526836562);
            z += randomNumber(0.30526836562, -0.30526836562);
        }
        else {
            y += randomNumber(0.30526836562, -0.30526836562);
        }
        if (face == EnumFacing.WEST || face == EnumFacing.EAST) {
            z += randomNumber(0.30526836562, -0.30526836562);
        }
        if (face == EnumFacing.SOUTH || face == EnumFacing.NORTH) {
            x += randomNumber(0.30526836562, -0.30526836562);
        }
        return new Vec3d(x, y, z);
    }
    
    public static double randomNumber(final double max, final double min) {
        return Math.random() * (max - min) + min;
    }
    
    public static boolean invCheck() {
        for (int i = 36; i < 45; ++i) {
            if (ScaffoldUtil.mc.thePlayer.inventoryContainer.getSlot(i).getHasStack() && isValid(ScaffoldUtil.mc.thePlayer.inventoryContainer.getSlot(i).getStack())) {
                return false;
            }
        }
        return true;
    }
    
    public static boolean isValid(final ItemStack item) {
        return !isEmpty(item) && !item.getUnlocalizedName().equalsIgnoreCase("tile.chest") && item.getItem() instanceof ItemBlock && !ScaffoldUtil.blockBlacklist.contains(((ItemBlock)item.getItem()).getBlock());
    }
    
    public static boolean contains(final Block block) {
        return ScaffoldUtil.blockBlacklist.contains(block);
    }
    
    public static int getBlockSlot() {
        for (int i = 36; i < 45; ++i) {
            final ItemStack stack = ScaffoldUtil.mc.thePlayer.inventoryContainer.getSlot(i).getStack();
            if (stack != null && stack.getItem() instanceof ItemBlock && !contains(((ItemBlock)stack.getItem()).getBlock())) {
                return i - 36;
            }
        }
        return -1;
    }
    
    public static void swap(final int slot, final int hotBarNumber) {
        ScaffoldUtil.mc.playerController.windowClick(ScaffoldUtil.mc.thePlayer.inventoryContainer.windowId, slot, hotBarNumber, 2, ScaffoldUtil.mc.thePlayer);
    }
}
