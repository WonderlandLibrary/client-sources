package net.minecraft.item;

import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.block.*;
import net.minecraft.block.state.*;
import net.minecraft.block.properties.*;
import net.minecraft.creativetab.*;

public class ItemDoor extends Item
{
    private Block block;
    
    @Override
    public boolean onItemUse(final ItemStack itemStack, final EntityPlayer entityPlayer, final World world, BlockPos offset, final EnumFacing enumFacing, final float n, final float n2, final float n3) {
        if (enumFacing != EnumFacing.UP) {
            return "".length() != 0;
        }
        if (!world.getBlockState(offset).getBlock().isReplaceable(world, offset)) {
            offset = offset.offset(enumFacing);
        }
        if (!entityPlayer.canPlayerEdit(offset, enumFacing, itemStack)) {
            return "".length() != 0;
        }
        if (!this.block.canPlaceBlockAt(world, offset)) {
            return "".length() != 0;
        }
        placeDoor(world, offset, EnumFacing.fromAngle(entityPlayer.rotationYaw), this.block);
        itemStack.stackSize -= " ".length();
        return " ".length() != 0;
    }
    
    public static void placeDoor(final World world, final BlockPos blockPos, final EnumFacing enumFacing, final Block block) {
        final BlockPos offset = blockPos.offset(enumFacing.rotateY());
        final BlockPos offset2 = blockPos.offset(enumFacing.rotateYCCW());
        int n;
        if (world.getBlockState(offset2).getBlock().isNormalCube()) {
            n = " ".length();
            "".length();
            if (-1 >= 0) {
                throw null;
            }
        }
        else {
            n = "".length();
        }
        int n2;
        if (world.getBlockState(offset2.up()).getBlock().isNormalCube()) {
            n2 = " ".length();
            "".length();
            if (0 == -1) {
                throw null;
            }
        }
        else {
            n2 = "".length();
        }
        final int n3 = n + n2;
        int n4;
        if (world.getBlockState(offset).getBlock().isNormalCube()) {
            n4 = " ".length();
            "".length();
            if (false == true) {
                throw null;
            }
        }
        else {
            n4 = "".length();
        }
        int n5;
        if (world.getBlockState(offset.up()).getBlock().isNormalCube()) {
            n5 = " ".length();
            "".length();
            if (-1 == 1) {
                throw null;
            }
        }
        else {
            n5 = "".length();
        }
        final int n6 = n4 + n5;
        int n7;
        if (world.getBlockState(offset2).getBlock() != block && world.getBlockState(offset2.up()).getBlock() != block) {
            n7 = "".length();
            "".length();
            if (-1 >= 4) {
                throw null;
            }
        }
        else {
            n7 = " ".length();
        }
        final int n8 = n7;
        int n9;
        if (world.getBlockState(offset).getBlock() != block && world.getBlockState(offset.up()).getBlock() != block) {
            n9 = "".length();
            "".length();
            if (0 < -1) {
                throw null;
            }
        }
        else {
            n9 = " ".length();
        }
        final int n10 = n9;
        int n11 = "".length();
        if ((n8 != 0 && n10 == 0) || n6 > n3) {
            n11 = " ".length();
        }
        final BlockPos up = blockPos.up();
        final IBlockState withProperty = block.getDefaultState().withProperty((IProperty<Comparable>)BlockDoor.FACING, enumFacing);
        final PropertyEnum<BlockDoor.EnumHingePosition> hinge = BlockDoor.HINGE;
        BlockDoor.EnumHingePosition enumHingePosition;
        if (n11 != 0) {
            enumHingePosition = BlockDoor.EnumHingePosition.RIGHT;
            "".length();
            if (4 <= -1) {
                throw null;
            }
        }
        else {
            enumHingePosition = BlockDoor.EnumHingePosition.LEFT;
        }
        final IBlockState withProperty2 = withProperty.withProperty((IProperty<Comparable>)hinge, enumHingePosition);
        world.setBlockState(blockPos, withProperty2.withProperty(BlockDoor.HALF, BlockDoor.EnumDoorHalf.LOWER), "  ".length());
        world.setBlockState(up, withProperty2.withProperty(BlockDoor.HALF, BlockDoor.EnumDoorHalf.UPPER), "  ".length());
        world.notifyNeighborsOfStateChange(blockPos, block);
        world.notifyNeighborsOfStateChange(up, block);
    }
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (2 >= 3) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public ItemDoor(final Block block) {
        this.block = block;
        this.setCreativeTab(CreativeTabs.tabRedstone);
    }
}
