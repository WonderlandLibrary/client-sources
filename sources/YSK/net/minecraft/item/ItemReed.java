package net.minecraft.item;

import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import net.minecraft.block.*;
import net.minecraft.block.properties.*;
import net.minecraft.entity.*;
import net.minecraft.block.state.*;

public class ItemReed extends Item
{
    private Block block;
    
    @Override
    public boolean onItemUse(final ItemStack itemStack, final EntityPlayer entityPlayer, final World world, BlockPos offset, EnumFacing up, final float n, final float n2, final float n3) {
        final IBlockState blockState = world.getBlockState(offset);
        final Block block = blockState.getBlock();
        if (block == Blocks.snow_layer && blockState.getValue((IProperty<Integer>)BlockSnow.LAYERS) < " ".length()) {
            up = EnumFacing.UP;
            "".length();
            if (!true) {
                throw null;
            }
        }
        else if (!block.isReplaceable(world, offset)) {
            offset = offset.offset(up);
        }
        if (!entityPlayer.canPlayerEdit(offset, up, itemStack)) {
            return "".length() != 0;
        }
        if (itemStack.stackSize == 0) {
            return "".length() != 0;
        }
        if (world.canBlockBePlaced(this.block, offset, "".length() != 0, up, null, itemStack) && world.setBlockState(offset, this.block.onBlockPlaced(world, offset, up, n, n2, n3, "".length(), entityPlayer), "   ".length())) {
            final IBlockState blockState2 = world.getBlockState(offset);
            if (blockState2.getBlock() == this.block) {
                ItemBlock.setTileEntityNBT(world, entityPlayer, offset, itemStack);
                blockState2.getBlock().onBlockPlacedBy(world, offset, blockState2, entityPlayer, itemStack);
            }
            world.playSoundEffect(offset.getX() + 0.5f, offset.getY() + 0.5f, offset.getZ() + 0.5f, this.block.stepSound.getPlaceSound(), (this.block.stepSound.getVolume() + 1.0f) / 2.0f, this.block.stepSound.getFrequency() * 0.8f);
            itemStack.stackSize -= " ".length();
            return " ".length() != 0;
        }
        return "".length() != 0;
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
            if (false == true) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public ItemReed(final Block block) {
        this.block = block;
    }
}
