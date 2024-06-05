package net.minecraft.item;

import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.block.*;
import net.minecraft.block.properties.*;
import net.minecraft.block.state.*;

public class ItemSnow extends ItemBlock
{
    public ItemSnow(final Block p_i45781_1_) {
        super(p_i45781_1_);
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
    }
    
    @Override
    public boolean onItemUse(final ItemStack stack, final EntityPlayer playerIn, final World worldIn, BlockPos pos, final EnumFacing side, final float hitX, final float hitY, final float hitZ) {
        if (stack.stackSize == 0) {
            return false;
        }
        if (!playerIn.func_175151_a(pos, side, stack)) {
            return false;
        }
        IBlockState var9 = worldIn.getBlockState(pos);
        Block var10 = var9.getBlock();
        if (var10 != this.block && side != EnumFacing.UP) {
            pos = pos.offset(side);
            var9 = worldIn.getBlockState(pos);
            var10 = var9.getBlock();
        }
        if (var10 == this.block) {
            final int var11 = (int)var9.getValue(BlockSnow.LAYERS_PROP);
            if (var11 <= 7) {
                final IBlockState var12 = var9.withProperty(BlockSnow.LAYERS_PROP, var11 + 1);
                if (worldIn.checkNoEntityCollision(this.block.getCollisionBoundingBox(worldIn, pos, var12)) && worldIn.setBlockState(pos, var12, 2)) {
                    worldIn.playSoundEffect(pos.getX() + 0.5f, pos.getY() + 0.5f, pos.getZ() + 0.5f, this.block.stepSound.getPlaceSound(), (this.block.stepSound.getVolume() + 1.0f) / 2.0f, this.block.stepSound.getFrequency() * 0.8f);
                    --stack.stackSize;
                    return true;
                }
            }
        }
        return super.onItemUse(stack, playerIn, worldIn, pos, side, hitX, hitY, hitZ);
    }
    
    @Override
    public int getMetadata(final int damage) {
        return damage;
    }
}
