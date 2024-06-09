package HORIZON-6-0-SKIDPROTECTION;

import java.util.Random;

public class InventoryHelper
{
    private static final Random HorizonCode_Horizon_È;
    private static final String Â = "CL_00002262";
    
    static {
        HorizonCode_Horizon_È = new Random();
    }
    
    public static void HorizonCode_Horizon_È(final World worldIn, final BlockPos p_180175_1_, final IInventory p_180175_2_) {
        HorizonCode_Horizon_È(worldIn, p_180175_1_.HorizonCode_Horizon_È(), p_180175_1_.Â(), p_180175_1_.Ý(), p_180175_2_);
    }
    
    public static void HorizonCode_Horizon_È(final World worldIn, final Entity p_180176_1_, final IInventory p_180176_2_) {
        HorizonCode_Horizon_È(worldIn, p_180176_1_.ŒÏ, p_180176_1_.Çªà¢, p_180176_1_.Ê, p_180176_2_);
    }
    
    private static void HorizonCode_Horizon_È(final World worldIn, final double p_180174_1_, final double p_180174_3_, final double p_180174_5_, final IInventory p_180174_7_) {
        for (int var8 = 0; var8 < p_180174_7_.áŒŠÆ(); ++var8) {
            final ItemStack var9 = p_180174_7_.á(var8);
            if (var9 != null) {
                HorizonCode_Horizon_È(worldIn, p_180174_1_, p_180174_3_, p_180174_5_, var9);
            }
        }
    }
    
    private static void HorizonCode_Horizon_È(final World worldIn, final double p_180173_1_, final double p_180173_3_, final double p_180173_5_, final ItemStack p_180173_7_) {
        final float var8 = InventoryHelper.HorizonCode_Horizon_È.nextFloat() * 0.8f + 0.1f;
        final float var9 = InventoryHelper.HorizonCode_Horizon_È.nextFloat() * 0.8f + 0.1f;
        final float var10 = InventoryHelper.HorizonCode_Horizon_È.nextFloat() * 0.8f + 0.1f;
        while (p_180173_7_.Â > 0) {
            int var11 = InventoryHelper.HorizonCode_Horizon_È.nextInt(21) + 10;
            if (var11 > p_180173_7_.Â) {
                var11 = p_180173_7_.Â;
            }
            p_180173_7_.Â -= var11;
            final EntityItem var12 = new EntityItem(worldIn, p_180173_1_ + var8, p_180173_3_ + var9, p_180173_5_ + var10, new ItemStack(p_180173_7_.HorizonCode_Horizon_È(), var11, p_180173_7_.Ø()));
            if (p_180173_7_.£á()) {
                var12.Ø().Ø­áŒŠá((NBTTagCompound)p_180173_7_.Å().Â());
            }
            final float var13 = 0.05f;
            var12.ÇŽÉ = InventoryHelper.HorizonCode_Horizon_È.nextGaussian() * var13;
            var12.ˆá = InventoryHelper.HorizonCode_Horizon_È.nextGaussian() * var13 + 0.20000000298023224;
            var12.ÇŽÕ = InventoryHelper.HorizonCode_Horizon_È.nextGaussian() * var13;
            worldIn.HorizonCode_Horizon_È(var12);
        }
    }
}
