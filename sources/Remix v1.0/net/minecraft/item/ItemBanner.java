package net.minecraft.item;

import net.minecraft.init.*;
import net.minecraft.creativetab.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import net.minecraft.block.properties.*;
import net.minecraft.block.*;
import net.minecraft.tileentity.*;
import net.minecraft.util.*;
import java.util.*;
import net.minecraft.nbt.*;

public class ItemBanner extends ItemBlock
{
    public ItemBanner() {
        super(Blocks.standing_banner);
        this.maxStackSize = 16;
        this.setCreativeTab(CreativeTabs.tabDecorations);
        this.setHasSubtypes(true);
        this.setMaxDamage(0);
    }
    
    @Override
    public boolean onItemUse(final ItemStack stack, final EntityPlayer playerIn, final World worldIn, BlockPos pos, final EnumFacing side, final float hitX, final float hitY, final float hitZ) {
        if (side == EnumFacing.DOWN) {
            return false;
        }
        if (!worldIn.getBlockState(pos).getBlock().getMaterial().isSolid()) {
            return false;
        }
        pos = pos.offset(side);
        if (!playerIn.func_175151_a(pos, side, stack)) {
            return false;
        }
        if (!Blocks.standing_banner.canPlaceBlockAt(worldIn, pos)) {
            return false;
        }
        if (worldIn.isRemote) {
            return true;
        }
        if (side == EnumFacing.UP) {
            final int var9 = MathHelper.floor_double((playerIn.rotationYaw + 180.0f) * 16.0f / 360.0f + 0.5) & 0xF;
            worldIn.setBlockState(pos, Blocks.standing_banner.getDefaultState().withProperty(BlockStandingSign.ROTATION_PROP, var9), 3);
        }
        else {
            worldIn.setBlockState(pos, Blocks.wall_banner.getDefaultState().withProperty(BlockWallSign.field_176412_a, side), 3);
        }
        --stack.stackSize;
        final TileEntity var10 = worldIn.getTileEntity(pos);
        if (var10 instanceof TileEntityBanner) {
            ((TileEntityBanner)var10).setItemValues(stack);
        }
        return true;
    }
    
    @Override
    public String getItemStackDisplayName(final ItemStack stack) {
        String var2 = "item.banner.";
        final EnumDyeColor var3 = this.func_179225_h(stack);
        var2 = var2 + var3.func_176762_d() + ".name";
        return StatCollector.translateToLocal(var2);
    }
    
    @Override
    public void addInformation(final ItemStack stack, final EntityPlayer playerIn, final List tooltip, final boolean advanced) {
        final NBTTagCompound var5 = stack.getSubCompound("BlockEntityTag", false);
        if (var5 != null && var5.hasKey("Patterns")) {
            final NBTTagList var6 = var5.getTagList("Patterns", 10);
            for (int var7 = 0; var7 < var6.tagCount() && var7 < 6; ++var7) {
                final NBTTagCompound var8 = var6.getCompoundTagAt(var7);
                final EnumDyeColor var9 = EnumDyeColor.func_176766_a(var8.getInteger("Color"));
                final TileEntityBanner.EnumBannerPattern var10 = TileEntityBanner.EnumBannerPattern.func_177268_a(var8.getString("Pattern"));
                if (var10 != null) {
                    tooltip.add(StatCollector.translateToLocal("item.banner." + var10.func_177271_a() + "." + var9.func_176762_d()));
                }
            }
        }
    }
    
    @Override
    public int getColorFromItemStack(final ItemStack stack, final int renderPass) {
        if (renderPass == 0) {
            return 16777215;
        }
        final EnumDyeColor var3 = this.func_179225_h(stack);
        return var3.func_176768_e().colorValue;
    }
    
    @Override
    public void getSubItems(final Item itemIn, final CreativeTabs tab, final List subItems) {
        for (final EnumDyeColor var7 : EnumDyeColor.values()) {
            subItems.add(new ItemStack(itemIn, 1, var7.getDyeColorDamage()));
        }
    }
    
    @Override
    public CreativeTabs getCreativeTab() {
        return CreativeTabs.tabDecorations;
    }
    
    private EnumDyeColor func_179225_h(final ItemStack p_179225_1_) {
        final NBTTagCompound var2 = p_179225_1_.getSubCompound("BlockEntityTag", false);
        EnumDyeColor var3 = null;
        if (var2 != null && var2.hasKey("Base")) {
            var3 = EnumDyeColor.func_176766_a(var2.getInteger("Base"));
        }
        else {
            var3 = EnumDyeColor.func_176766_a(p_179225_1_.getMetadata());
        }
        return var3;
    }
}
