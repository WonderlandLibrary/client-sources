package net.minecraft.item;

import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import net.minecraft.block.*;
import net.minecraft.block.properties.*;
import net.minecraft.block.state.*;
import net.minecraft.util.*;

public class ItemSnow extends ItemBlock
{
    public ItemSnow(final Block block) {
        super(block);
        this.setMaxDamage("".length());
        this.setHasSubtypes(" ".length() != 0);
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
            if (2 == 3) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public int getMetadata(final int n) {
        return n;
    }
    
    @Override
    public boolean onItemUse(final ItemStack itemStack, final EntityPlayer entityPlayer, final World world, final BlockPos blockPos, final EnumFacing enumFacing, final float n, final float n2, final float n3) {
        if (itemStack.stackSize == 0) {
            return "".length() != 0;
        }
        if (!entityPlayer.canPlayerEdit(blockPos, enumFacing, itemStack)) {
            return "".length() != 0;
        }
        IBlockState blockState = world.getBlockState(blockPos);
        Block block = blockState.getBlock();
        BlockPos offset = blockPos;
        if ((enumFacing != EnumFacing.UP || block != this.block) && !block.isReplaceable(world, blockPos)) {
            offset = blockPos.offset(enumFacing);
            blockState = world.getBlockState(offset);
            block = blockState.getBlock();
        }
        if (block == this.block) {
            final int intValue = blockState.getValue((IProperty<Integer>)BlockSnow.LAYERS);
            if (intValue <= (0xC4 ^ 0xC3)) {
                final IBlockState withProperty = blockState.withProperty((IProperty<Comparable>)BlockSnow.LAYERS, intValue + " ".length());
                final AxisAlignedBB collisionBoundingBox = this.block.getCollisionBoundingBox(world, offset, withProperty);
                if (collisionBoundingBox != null && world.checkNoEntityCollision(collisionBoundingBox) && world.setBlockState(offset, withProperty, "  ".length())) {
                    world.playSoundEffect(offset.getX() + 0.5f, offset.getY() + 0.5f, offset.getZ() + 0.5f, this.block.stepSound.getPlaceSound(), (this.block.stepSound.getVolume() + 1.0f) / 2.0f, this.block.stepSound.getFrequency() * 0.8f);
                    itemStack.stackSize -= " ".length();
                    return " ".length() != 0;
                }
            }
        }
        return super.onItemUse(itemStack, entityPlayer, world, offset, enumFacing, n, n2, n3);
    }
}
