package net.minecraft.item;

import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.block.properties.*;
import net.minecraft.block.state.*;
import net.minecraft.block.*;

public class ItemSlab extends ItemBlock
{
    private final BlockSlab doubleSlab;
    private final BlockSlab singleSlab;
    
    @Override
    public int getMetadata(final int n) {
        return n;
    }
    
    @Override
    public boolean onItemUse(final ItemStack itemStack, final EntityPlayer entityPlayer, final World world, final BlockPos blockPos, final EnumFacing enumFacing, final float n, final float n2, final float n3) {
        if (itemStack.stackSize == 0) {
            return "".length() != 0;
        }
        if (!entityPlayer.canPlayerEdit(blockPos.offset(enumFacing), enumFacing, itemStack)) {
            return "".length() != 0;
        }
        final Object variant = this.singleSlab.getVariant(itemStack);
        final IBlockState blockState = world.getBlockState(blockPos);
        if (blockState.getBlock() == this.singleSlab) {
            final IProperty<?> variantProperty = this.singleSlab.getVariantProperty();
            final Comparable<T> value = blockState.getValue(variantProperty);
            final BlockSlab.EnumBlockHalf enumBlockHalf = blockState.getValue(BlockSlab.HALF);
            if (((enumFacing == EnumFacing.UP && enumBlockHalf == BlockSlab.EnumBlockHalf.BOTTOM) || (enumFacing == EnumFacing.DOWN && enumBlockHalf == BlockSlab.EnumBlockHalf.TOP)) && value == variant) {
                final IBlockState withProperty = this.doubleSlab.getDefaultState().withProperty(variantProperty, (Comparable<T>)value);
                if (world.checkNoEntityCollision(this.doubleSlab.getCollisionBoundingBox(world, blockPos, withProperty)) && world.setBlockState(blockPos, withProperty, "   ".length())) {
                    world.playSoundEffect(blockPos.getX() + 0.5f, blockPos.getY() + 0.5f, blockPos.getZ() + 0.5f, this.doubleSlab.stepSound.getPlaceSound(), (this.doubleSlab.stepSound.getVolume() + 1.0f) / 2.0f, this.doubleSlab.stepSound.getFrequency() * 0.8f);
                    itemStack.stackSize -= " ".length();
                }
                return " ".length() != 0;
            }
        }
        int n4;
        if (this.tryPlace(itemStack, world, blockPos.offset(enumFacing), variant)) {
            n4 = " ".length();
            "".length();
            if (1 < 1) {
                throw null;
            }
        }
        else {
            n4 = (super.onItemUse(itemStack, entityPlayer, world, blockPos, enumFacing, n, n2, n3) ? 1 : 0);
        }
        return n4 != 0;
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
            if (2 <= 1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public boolean canPlaceBlockOnSide(final World world, BlockPos offset, final EnumFacing enumFacing, final EntityPlayer entityPlayer, final ItemStack itemStack) {
        final BlockPos blockPos = offset;
        final IProperty<?> variantProperty = this.singleSlab.getVariantProperty();
        final Object variant = this.singleSlab.getVariant(itemStack);
        final IBlockState blockState = world.getBlockState(offset);
        if (blockState.getBlock() == this.singleSlab) {
            int n;
            if (blockState.getValue(BlockSlab.HALF) == BlockSlab.EnumBlockHalf.TOP) {
                n = " ".length();
                "".length();
                if (true != true) {
                    throw null;
                }
            }
            else {
                n = "".length();
            }
            final int n2 = n;
            if (((enumFacing == EnumFacing.UP && n2 == 0) || (enumFacing == EnumFacing.DOWN && n2 != 0)) && variant == blockState.getValue(variantProperty)) {
                return " ".length() != 0;
            }
        }
        offset = offset.offset(enumFacing);
        final IBlockState blockState2 = world.getBlockState(offset);
        int n3;
        if (blockState2.getBlock() == this.singleSlab && variant == blockState2.getValue(variantProperty)) {
            n3 = " ".length();
            "".length();
            if (3 < 0) {
                throw null;
            }
        }
        else {
            n3 = (super.canPlaceBlockOnSide(world, blockPos, enumFacing, entityPlayer, itemStack) ? 1 : 0);
        }
        return n3 != 0;
    }
    
    @Override
    public String getUnlocalizedName(final ItemStack itemStack) {
        return this.singleSlab.getUnlocalizedName(itemStack.getMetadata());
    }
    
    private boolean tryPlace(final ItemStack itemStack, final World world, final BlockPos blockPos, final Object o) {
        final IBlockState blockState = world.getBlockState(blockPos);
        if (blockState.getBlock() == this.singleSlab) {
            final Object value = blockState.getValue(this.singleSlab.getVariantProperty());
            if (value == o) {
                final IBlockState withProperty = this.doubleSlab.getDefaultState().withProperty(this.singleSlab.getVariantProperty(), value);
                if (world.checkNoEntityCollision(this.doubleSlab.getCollisionBoundingBox(world, blockPos, withProperty)) && world.setBlockState(blockPos, withProperty, "   ".length())) {
                    world.playSoundEffect(blockPos.getX() + 0.5f, blockPos.getY() + 0.5f, blockPos.getZ() + 0.5f, this.doubleSlab.stepSound.getPlaceSound(), (this.doubleSlab.stepSound.getVolume() + 1.0f) / 2.0f, this.doubleSlab.stepSound.getFrequency() * 0.8f);
                    itemStack.stackSize -= " ".length();
                }
                return " ".length() != 0;
            }
        }
        return "".length() != 0;
    }
    
    public ItemSlab(final Block block, final BlockSlab singleSlab, final BlockSlab doubleSlab) {
        super(block);
        this.singleSlab = singleSlab;
        this.doubleSlab = doubleSlab;
        this.setMaxDamage("".length());
        this.setHasSubtypes(" ".length() != 0);
    }
}
