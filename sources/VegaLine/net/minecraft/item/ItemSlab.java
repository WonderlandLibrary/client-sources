/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.item;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.SoundType;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemSlab
extends ItemBlock {
    private final BlockSlab singleSlab;
    private final BlockSlab doubleSlab;

    public ItemSlab(Block block, BlockSlab singleSlab, BlockSlab doubleSlab) {
        super(block);
        this.singleSlab = singleSlab;
        this.doubleSlab = doubleSlab;
        this.setMaxDamage(0);
        this.setHasSubtypes(true);
    }

    @Override
    public int getMetadata(int damage) {
        return damage;
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return this.singleSlab.getUnlocalizedName(stack.getMetadata());
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer stack, World playerIn, BlockPos worldIn, EnumHand pos, EnumFacing hand, float facing, float hitX, float hitY) {
        ItemStack itemstack = stack.getHeldItem(pos);
        if (!itemstack.func_190926_b() && stack.canPlayerEdit(worldIn.offset(hand), hand, itemstack)) {
            Comparable<?> comparable = this.singleSlab.getTypeForItem(itemstack);
            IBlockState iblockstate = playerIn.getBlockState(worldIn);
            if (iblockstate.getBlock() == this.singleSlab) {
                IProperty<?> iproperty = this.singleSlab.getVariantProperty();
                Object comparable1 = iblockstate.getValue(iproperty);
                BlockSlab.EnumBlockHalf blockslab$enumblockhalf = iblockstate.getValue(BlockSlab.HALF);
                if ((hand == EnumFacing.UP && blockslab$enumblockhalf == BlockSlab.EnumBlockHalf.BOTTOM || hand == EnumFacing.DOWN && blockslab$enumblockhalf == BlockSlab.EnumBlockHalf.TOP) && comparable1 == comparable) {
                    IBlockState iblockstate1 = this.makeState(iproperty, (Comparable<?>)comparable1);
                    AxisAlignedBB axisalignedbb = iblockstate1.getCollisionBoundingBox(playerIn, worldIn);
                    if (axisalignedbb != Block.NULL_AABB && playerIn.checkNoEntityCollision(axisalignedbb.offset(worldIn)) && playerIn.setBlockState(worldIn, iblockstate1, 11)) {
                        SoundType soundtype = this.doubleSlab.getSoundType();
                        playerIn.playSound(stack, worldIn, soundtype.getPlaceSound(), SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0f) / 2.0f, soundtype.getPitch() * 0.8f);
                        itemstack.func_190918_g(1);
                        if (stack instanceof EntityPlayerMP) {
                            CriteriaTriggers.field_193137_x.func_193173_a((EntityPlayerMP)stack, worldIn, itemstack);
                        }
                    }
                    return EnumActionResult.SUCCESS;
                }
            }
            return this.tryPlace(stack, itemstack, playerIn, worldIn.offset(hand), comparable) ? EnumActionResult.SUCCESS : super.onItemUse(stack, playerIn, worldIn, pos, hand, facing, hitX, hitY);
        }
        return EnumActionResult.FAIL;
    }

    @Override
    public boolean canPlaceBlockOnSide(World worldIn, BlockPos pos, EnumFacing side, EntityPlayer player, ItemStack stack) {
        IBlockState iblockstate1;
        BlockPos blockpos = pos;
        IProperty<?> iproperty = this.singleSlab.getVariantProperty();
        Comparable<?> comparable = this.singleSlab.getTypeForItem(stack);
        IBlockState iblockstate = worldIn.getBlockState(pos);
        if (iblockstate.getBlock() == this.singleSlab) {
            boolean flag;
            boolean bl = flag = iblockstate.getValue(BlockSlab.HALF) == BlockSlab.EnumBlockHalf.TOP;
            if ((side == EnumFacing.UP && !flag || side == EnumFacing.DOWN && flag) && comparable == iblockstate.getValue(iproperty)) {
                return true;
            }
        }
        return (iblockstate1 = worldIn.getBlockState(pos = pos.offset(side))).getBlock() == this.singleSlab && comparable == iblockstate1.getValue(iproperty) ? true : super.canPlaceBlockOnSide(worldIn, blockpos, side, player, stack);
    }

    private boolean tryPlace(EntityPlayer player, ItemStack stack, World worldIn, BlockPos pos, Object itemSlabType) {
        Object comparable;
        IBlockState iblockstate = worldIn.getBlockState(pos);
        if (iblockstate.getBlock() == this.singleSlab && (comparable = iblockstate.getValue(this.singleSlab.getVariantProperty())) == itemSlabType) {
            IBlockState iblockstate1 = this.makeState(this.singleSlab.getVariantProperty(), (Comparable<?>)comparable);
            AxisAlignedBB axisalignedbb = iblockstate1.getCollisionBoundingBox(worldIn, pos);
            if (axisalignedbb != Block.NULL_AABB && worldIn.checkNoEntityCollision(axisalignedbb.offset(pos)) && worldIn.setBlockState(pos, iblockstate1, 11)) {
                SoundType soundtype = this.doubleSlab.getSoundType();
                worldIn.playSound(player, pos, soundtype.getPlaceSound(), SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0f) / 2.0f, soundtype.getPitch() * 0.8f);
                stack.func_190918_g(1);
            }
            return true;
        }
        return false;
    }

    protected <T extends Comparable<T>> IBlockState makeState(IProperty<T> p_185055_1_, Comparable<?> p_185055_2_) {
        return this.doubleSlab.getDefaultState().withProperty(p_185055_1_, p_185055_2_);
    }
}

