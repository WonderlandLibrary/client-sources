package net.minecraft.item;

import net.minecraft.creativetab.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.block.material.*;
import net.minecraft.init.*;
import net.minecraft.block.properties.*;
import net.minecraft.block.state.*;
import net.minecraft.block.*;
import net.minecraft.entity.*;

public class ItemHoe extends Item
{
    protected ToolMaterial theToolMaterial;
    
    public ItemHoe(final ToolMaterial p_i45343_1_) {
        this.theToolMaterial = p_i45343_1_;
        this.maxStackSize = 1;
        this.setMaxDamage(p_i45343_1_.getMaxUses());
        this.setCreativeTab(CreativeTabs.tabTools);
    }
    
    @Override
    public boolean onItemUse(final ItemStack stack, final EntityPlayer playerIn, final World worldIn, final BlockPos pos, final EnumFacing side, final float hitX, final float hitY, final float hitZ) {
        if (!playerIn.func_175151_a(pos.offset(side), side, stack)) {
            return false;
        }
        final IBlockState var9 = worldIn.getBlockState(pos);
        final Block var10 = var9.getBlock();
        if (side != EnumFacing.DOWN && worldIn.getBlockState(pos.offsetUp()).getBlock().getMaterial() == Material.air) {
            if (var10 == Blocks.grass) {
                return this.func_179232_a(stack, playerIn, worldIn, pos, Blocks.farmland.getDefaultState());
            }
            if (var10 == Blocks.dirt) {
                switch (SwitchDirtType.field_179590_a[((BlockDirt.DirtType)var9.getValue(BlockDirt.VARIANT)).ordinal()]) {
                    case 1: {
                        return this.func_179232_a(stack, playerIn, worldIn, pos, Blocks.farmland.getDefaultState());
                    }
                    case 2: {
                        return this.func_179232_a(stack, playerIn, worldIn, pos, Blocks.dirt.getDefaultState().withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.DIRT));
                    }
                }
            }
        }
        return false;
    }
    
    protected boolean func_179232_a(final ItemStack p_179232_1_, final EntityPlayer p_179232_2_, final World worldIn, final BlockPos p_179232_4_, final IBlockState p_179232_5_) {
        worldIn.playSoundEffect(p_179232_4_.getX() + 0.5f, p_179232_4_.getY() + 0.5f, p_179232_4_.getZ() + 0.5f, p_179232_5_.getBlock().stepSound.getStepSound(), (p_179232_5_.getBlock().stepSound.getVolume() + 1.0f) / 2.0f, p_179232_5_.getBlock().stepSound.getFrequency() * 0.8f);
        if (worldIn.isRemote) {
            return true;
        }
        worldIn.setBlockState(p_179232_4_, p_179232_5_);
        p_179232_1_.damageItem(1, p_179232_2_);
        return true;
    }
    
    @Override
    public boolean isFull3D() {
        return true;
    }
    
    public String getMaterialName() {
        return this.theToolMaterial.toString();
    }
    
    static final class SwitchDirtType
    {
        static final int[] field_179590_a;
        
        static {
            field_179590_a = new int[BlockDirt.DirtType.values().length];
            try {
                SwitchDirtType.field_179590_a[BlockDirt.DirtType.DIRT.ordinal()] = 1;
            }
            catch (NoSuchFieldError noSuchFieldError) {}
            try {
                SwitchDirtType.field_179590_a[BlockDirt.DirtType.COARSE_DIRT.ordinal()] = 2;
            }
            catch (NoSuchFieldError noSuchFieldError2) {}
        }
    }
}
