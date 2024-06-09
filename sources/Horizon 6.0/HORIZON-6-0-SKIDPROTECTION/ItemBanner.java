package HORIZON-6-0-SKIDPROTECTION;

import java.util.List;

public class ItemBanner extends ItemBlock
{
    private static final String Ø = "CL_00002181";
    
    public ItemBanner() {
        super(Blocks.Ï­áŠ);
        this.Ø­áŒŠá = 16;
        this.HorizonCode_Horizon_È(CreativeTabs.Ý);
        this.HorizonCode_Horizon_È(true);
        this.Ø­áŒŠá(0);
    }
    
    @Override
    public boolean HorizonCode_Horizon_È(final ItemStack stack, final EntityPlayer playerIn, final World worldIn, BlockPos pos, final EnumFacing side, final float hitX, final float hitY, final float hitZ) {
        if (side == EnumFacing.HorizonCode_Horizon_È) {
            return false;
        }
        if (!worldIn.Â(pos).Ý().Ó().Â()) {
            return false;
        }
        pos = pos.HorizonCode_Horizon_È(side);
        if (!playerIn.HorizonCode_Horizon_È(pos, side, stack)) {
            return false;
        }
        if (!Blocks.Ï­áŠ.Ø­áŒŠá(worldIn, pos)) {
            return false;
        }
        if (worldIn.ŠÄ) {
            return true;
        }
        if (side == EnumFacing.Â) {
            final int var9 = MathHelper.Ý((playerIn.É + 180.0f) * 16.0f / 360.0f + 0.5) & 0xF;
            worldIn.HorizonCode_Horizon_È(pos, Blocks.Ï­áŠ.¥à().HorizonCode_Horizon_È(BlockStandingSign.Õ, var9), 3);
        }
        else {
            worldIn.HorizonCode_Horizon_È(pos, Blocks.Ñ¢Ô.¥à().HorizonCode_Horizon_È(BlockWallSign.Õ, side), 3);
        }
        --stack.Â;
        final TileEntity var10 = worldIn.HorizonCode_Horizon_È(pos);
        if (var10 instanceof TileEntityBanner) {
            ((TileEntityBanner)var10).HorizonCode_Horizon_È(stack);
        }
        return true;
    }
    
    @Override
    public String à(final ItemStack stack) {
        String var2 = "item.banner.";
        final EnumDyeColor var3 = this.ÂµÈ(stack);
        var2 = String.valueOf(var2) + var3.Ø­áŒŠá() + ".name";
        return StatCollector.HorizonCode_Horizon_È(var2);
    }
    
    @Override
    public void HorizonCode_Horizon_È(final ItemStack stack, final EntityPlayer playerIn, final List tooltip, final boolean advanced) {
        final NBTTagCompound var5 = stack.HorizonCode_Horizon_È("BlockEntityTag", false);
        if (var5 != null && var5.Ý("Patterns")) {
            final NBTTagList var6 = var5.Ý("Patterns", 10);
            for (int var7 = 0; var7 < var6.Âµá€() && var7 < 6; ++var7) {
                final NBTTagCompound var8 = var6.Â(var7);
                final EnumDyeColor var9 = EnumDyeColor.HorizonCode_Horizon_È(var8.Ó("Color"));
                final TileEntityBanner.HorizonCode_Horizon_È var10 = TileEntityBanner.HorizonCode_Horizon_È.HorizonCode_Horizon_È(var8.áˆºÑ¢Õ("Pattern"));
                if (var10 != null) {
                    tooltip.add(StatCollector.HorizonCode_Horizon_È("item.banner." + var10.HorizonCode_Horizon_È() + "." + var9.Ø­áŒŠá()));
                }
            }
        }
    }
    
    @Override
    public int HorizonCode_Horizon_È(final ItemStack stack, final int renderPass) {
        if (renderPass == 0) {
            return 16777215;
        }
        final EnumDyeColor var3 = this.ÂµÈ(stack);
        return var3.Âµá€().à¢;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final Item_1028566121 itemIn, final CreativeTabs tab, final List subItems) {
        for (final EnumDyeColor var7 : EnumDyeColor.values()) {
            subItems.add(new ItemStack(itemIn, 1, var7.Ý()));
        }
    }
    
    @Override
    public CreativeTabs £á() {
        return CreativeTabs.Ý;
    }
    
    private EnumDyeColor ÂµÈ(final ItemStack p_179225_1_) {
        final NBTTagCompound var2 = p_179225_1_.HorizonCode_Horizon_È("BlockEntityTag", false);
        EnumDyeColor var3 = null;
        if (var2 != null && var2.Ý("Base")) {
            var3 = EnumDyeColor.HorizonCode_Horizon_È(var2.Ó("Base"));
        }
        else {
            var3 = EnumDyeColor.HorizonCode_Horizon_È(p_179225_1_.Ø());
        }
        return var3;
    }
}
