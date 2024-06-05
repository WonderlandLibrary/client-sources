package net.minecraft.item;

import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.block.material.*;
import net.minecraft.block.*;
import net.minecraft.block.properties.*;
import net.minecraft.init.*;
import net.minecraft.stats.*;
import net.minecraft.util.*;
import net.minecraft.block.state.*;

public class ItemLilyPad extends ItemColored
{
    public ItemLilyPad(final Block p_i45357_1_) {
        super(p_i45357_1_, false);
    }
    
    @Override
    public ItemStack onItemRightClick(final ItemStack itemStackIn, final World worldIn, final EntityPlayer playerIn) {
        final MovingObjectPosition var4 = this.getMovingObjectPositionFromPlayer(worldIn, playerIn, true);
        if (var4 == null) {
            return itemStackIn;
        }
        if (var4.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
            final BlockPos var5 = var4.getBlockPos();
            if (!worldIn.isBlockModifiable(playerIn, var5)) {
                return itemStackIn;
            }
            if (!playerIn.func_175151_a(var5.offset(var4.sideHit), var4.sideHit, itemStackIn)) {
                return itemStackIn;
            }
            final BlockPos var6 = var5.offsetUp();
            final IBlockState var7 = worldIn.getBlockState(var5);
            if (var7.getBlock().getMaterial() == Material.water && (int)var7.getValue(BlockLiquid.LEVEL) == 0 && worldIn.isAirBlock(var6)) {
                worldIn.setBlockState(var6, Blocks.waterlily.getDefaultState());
                if (!playerIn.capabilities.isCreativeMode) {
                    --itemStackIn.stackSize;
                }
                playerIn.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);
            }
        }
        return itemStackIn;
    }
    
    @Override
    public int getColorFromItemStack(final ItemStack stack, final int renderPass) {
        return Blocks.waterlily.getRenderColor(Blocks.waterlily.getStateFromMeta(stack.getMetadata()));
    }
}
