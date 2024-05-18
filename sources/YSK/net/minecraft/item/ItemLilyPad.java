package net.minecraft.item;

import net.minecraft.init.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.block.material.*;
import net.minecraft.block.*;
import net.minecraft.block.properties.*;
import net.minecraft.stats.*;
import net.minecraft.util.*;
import net.minecraft.block.state.*;

public class ItemLilyPad extends ItemColored
{
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
            if (2 != 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public int getColorFromItemStack(final ItemStack itemStack, final int n) {
        return Blocks.waterlily.getRenderColor(Blocks.waterlily.getStateFromMeta(itemStack.getMetadata()));
    }
    
    public ItemLilyPad(final Block block) {
        super(block, "".length() != 0);
    }
    
    @Override
    public ItemStack onItemRightClick(final ItemStack itemStack, final World world, final EntityPlayer entityPlayer) {
        final MovingObjectPosition movingObjectPositionFromPlayer = this.getMovingObjectPositionFromPlayer(world, entityPlayer, " ".length() != 0);
        if (movingObjectPositionFromPlayer == null) {
            return itemStack;
        }
        if (movingObjectPositionFromPlayer.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
            final BlockPos blockPos = movingObjectPositionFromPlayer.getBlockPos();
            if (!world.isBlockModifiable(entityPlayer, blockPos)) {
                return itemStack;
            }
            if (!entityPlayer.canPlayerEdit(blockPos.offset(movingObjectPositionFromPlayer.sideHit), movingObjectPositionFromPlayer.sideHit, itemStack)) {
                return itemStack;
            }
            final BlockPos up = blockPos.up();
            final IBlockState blockState = world.getBlockState(blockPos);
            if (blockState.getBlock().getMaterial() == Material.water && blockState.getValue((IProperty<Integer>)BlockLiquid.LEVEL) == 0 && world.isAirBlock(up)) {
                world.setBlockState(up, Blocks.waterlily.getDefaultState());
                if (!entityPlayer.capabilities.isCreativeMode) {
                    itemStack.stackSize -= " ".length();
                }
                entityPlayer.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);
            }
        }
        return itemStack;
    }
}
