package net.minecraft.item;

import net.minecraft.entity.item.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.entity.*;
import net.minecraft.block.state.*;
import net.minecraft.creativetab.*;
import net.minecraft.block.*;
import net.minecraft.dispenser.*;
import net.minecraft.block.material.*;

public class ItemMinecart extends Item
{
    private final EntityMinecart.EnumMinecartType minecartType;
    private static final IBehaviorDispenseItem dispenserMinecartBehavior;
    
    @Override
    public boolean onItemUse(final ItemStack itemStack, final EntityPlayer entityPlayer, final World world, final BlockPos blockPos, final EnumFacing enumFacing, final float n, final float n2, final float n3) {
        final IBlockState blockState = world.getBlockState(blockPos);
        if (BlockRailBase.isRailBlock(blockState)) {
            if (!world.isRemote) {
                BlockRailBase.EnumRailDirection north_SOUTH;
                if (blockState.getBlock() instanceof BlockRailBase) {
                    north_SOUTH = blockState.getValue(((BlockRailBase)blockState.getBlock()).getShapeProperty());
                    "".length();
                    if (3 == 1) {
                        throw null;
                    }
                }
                else {
                    north_SOUTH = BlockRailBase.EnumRailDirection.NORTH_SOUTH;
                }
                final BlockRailBase.EnumRailDirection enumRailDirection = north_SOUTH;
                double n4 = 0.0;
                if (enumRailDirection.isAscending()) {
                    n4 = 0.5;
                }
                final EntityMinecart func_180458_a = EntityMinecart.func_180458_a(world, blockPos.getX() + 0.5, blockPos.getY() + 0.0625 + n4, blockPos.getZ() + 0.5, this.minecartType);
                if (itemStack.hasDisplayName()) {
                    func_180458_a.setCustomNameTag(itemStack.getDisplayName());
                }
                world.spawnEntityInWorld(func_180458_a);
            }
            itemStack.stackSize -= " ".length();
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public ItemMinecart(final EntityMinecart.EnumMinecartType minecartType) {
        this.maxStackSize = " ".length();
        this.minecartType = minecartType;
        this.setCreativeTab(CreativeTabs.tabTransport);
        BlockDispenser.dispenseBehaviorRegistry.putObject(this, ItemMinecart.dispenserMinecartBehavior);
    }
    
    static {
        dispenserMinecartBehavior = new BehaviorDefaultDispenseItem() {
            private final BehaviorDefaultDispenseItem behaviourDefaultDispenseItem = new BehaviorDefaultDispenseItem();
            
            @Override
            protected void playDispenseSound(final IBlockSource blockSource) {
                blockSource.getWorld().playAuxSFX(249 + 929 - 234 + 56, blockSource.getBlockPos(), "".length());
            }
            
            public ItemStack dispenseStack(final IBlockSource blockSource, final ItemStack itemStack) {
                final EnumFacing facing = BlockDispenser.getFacing(blockSource.getBlockMetadata());
                final World world = blockSource.getWorld();
                final double n = blockSource.getX() + facing.getFrontOffsetX() * 1.125;
                final double n2 = Math.floor(blockSource.getY()) + facing.getFrontOffsetY();
                final double n3 = blockSource.getZ() + facing.getFrontOffsetZ() * 1.125;
                final BlockPos offset = blockSource.getBlockPos().offset(facing);
                final IBlockState blockState = world.getBlockState(offset);
                BlockRailBase.EnumRailDirection north_SOUTH;
                if (blockState.getBlock() instanceof BlockRailBase) {
                    north_SOUTH = blockState.getValue(((BlockRailBase)blockState.getBlock()).getShapeProperty());
                    "".length();
                    if (false) {
                        throw null;
                    }
                }
                else {
                    north_SOUTH = BlockRailBase.EnumRailDirection.NORTH_SOUTH;
                }
                final BlockRailBase.EnumRailDirection enumRailDirection = north_SOUTH;
                double n4;
                if (BlockRailBase.isRailBlock(blockState)) {
                    if (enumRailDirection.isAscending()) {
                        n4 = 0.6;
                        "".length();
                        if (3 != 3) {
                            throw null;
                        }
                    }
                    else {
                        n4 = 0.1;
                        "".length();
                        if (2 < -1) {
                            throw null;
                        }
                    }
                }
                else {
                    if (blockState.getBlock().getMaterial() != Material.air || !BlockRailBase.isRailBlock(world.getBlockState(offset.down()))) {
                        return this.behaviourDefaultDispenseItem.dispense(blockSource, itemStack);
                    }
                    final IBlockState blockState2 = world.getBlockState(offset.down());
                    BlockRailBase.EnumRailDirection north_SOUTH2;
                    if (blockState2.getBlock() instanceof BlockRailBase) {
                        north_SOUTH2 = blockState2.getValue(((BlockRailBase)blockState2.getBlock()).getShapeProperty());
                        "".length();
                        if (3 < 0) {
                            throw null;
                        }
                    }
                    else {
                        north_SOUTH2 = BlockRailBase.EnumRailDirection.NORTH_SOUTH;
                    }
                    final BlockRailBase.EnumRailDirection enumRailDirection2 = north_SOUTH2;
                    if (facing != EnumFacing.DOWN && enumRailDirection2.isAscending()) {
                        n4 = -0.4;
                        "".length();
                        if (1 <= -1) {
                            throw null;
                        }
                    }
                    else {
                        n4 = -0.9;
                    }
                }
                final EntityMinecart func_180458_a = EntityMinecart.func_180458_a(world, n, n2 + n4, n3, ItemMinecart.access$0((ItemMinecart)itemStack.getItem()));
                if (itemStack.hasDisplayName()) {
                    func_180458_a.setCustomNameTag(itemStack.getDisplayName());
                }
                world.spawnEntityInWorld(func_180458_a);
                itemStack.splitStack(" ".length());
                return itemStack;
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
                    if (2 <= 0) {
                        throw null;
                    }
                }
                return sb.toString();
            }
        };
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
            if (2 == 4) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    static EntityMinecart.EnumMinecartType access$0(final ItemMinecart itemMinecart) {
        return itemMinecart.minecartType;
    }
}
