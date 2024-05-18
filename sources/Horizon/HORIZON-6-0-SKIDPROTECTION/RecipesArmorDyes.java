package HORIZON-6-0-SKIDPROTECTION;

import java.util.ArrayList;
import com.google.common.collect.Lists;

public class RecipesArmorDyes implements IRecipe
{
    private static final String HorizonCode_Horizon_È = "CL_00000079";
    
    @Override
    public boolean HorizonCode_Horizon_È(final InventoryCrafting p_77569_1_, final World worldIn) {
        ItemStack var3 = null;
        final ArrayList var4 = Lists.newArrayList();
        for (int var5 = 0; var5 < p_77569_1_.áŒŠÆ(); ++var5) {
            final ItemStack var6 = p_77569_1_.á(var5);
            if (var6 != null) {
                if (var6.HorizonCode_Horizon_È() instanceof ItemArmor) {
                    final ItemArmor var7 = (ItemArmor)var6.HorizonCode_Horizon_È();
                    if (var7.ˆà() != ItemArmor.HorizonCode_Horizon_È.HorizonCode_Horizon_È || var3 != null) {
                        return false;
                    }
                    var3 = var6;
                }
                else {
                    if (var6.HorizonCode_Horizon_È() != Items.áŒŠÔ) {
                        return false;
                    }
                    var4.add(var6);
                }
            }
        }
        return var3 != null && !var4.isEmpty();
    }
    
    @Override
    public ItemStack HorizonCode_Horizon_È(final InventoryCrafting p_77572_1_) {
        ItemStack var2 = null;
        final int[] var3 = new int[3];
        int var4 = 0;
        int var5 = 0;
        ItemArmor var6 = null;
        for (int var7 = 0; var7 < p_77572_1_.áŒŠÆ(); ++var7) {
            final ItemStack var8 = p_77572_1_.á(var7);
            if (var8 != null) {
                if (var8.HorizonCode_Horizon_È() instanceof ItemArmor) {
                    var6 = (ItemArmor)var8.HorizonCode_Horizon_È();
                    if (var6.ˆà() != ItemArmor.HorizonCode_Horizon_È.HorizonCode_Horizon_È || var2 != null) {
                        return null;
                    }
                    var2 = var8.áˆºÑ¢Õ();
                    var2.Â = 1;
                    if (var6.ÂµÈ(var8)) {
                        final int var9 = var6.á(var2);
                        final float var10 = (var9 >> 16 & 0xFF) / 255.0f;
                        final float var11 = (var9 >> 8 & 0xFF) / 255.0f;
                        final float var12 = (var9 & 0xFF) / 255.0f;
                        var4 += (int)(Math.max(var10, Math.max(var11, var12)) * 255.0f);
                        var3[0] += (int)(var10 * 255.0f);
                        var3[1] += (int)(var11 * 255.0f);
                        var3[2] += (int)(var12 * 255.0f);
                        ++var5;
                    }
                }
                else {
                    if (var8.HorizonCode_Horizon_È() != Items.áŒŠÔ) {
                        return null;
                    }
                    final float[] var13 = EntitySheep.HorizonCode_Horizon_È(EnumDyeColor.HorizonCode_Horizon_È(var8.Ø()));
                    final int var14 = (int)(var13[0] * 255.0f);
                    final int var15 = (int)(var13[1] * 255.0f);
                    final int var16 = (int)(var13[2] * 255.0f);
                    var4 += Math.max(var14, Math.max(var15, var16));
                    final int[] array = var3;
                    final int n = 0;
                    array[n] += var14;
                    final int[] array2 = var3;
                    final int n2 = 1;
                    array2[n2] += var15;
                    final int[] array3 = var3;
                    final int n3 = 2;
                    array3[n3] += var16;
                    ++var5;
                }
            }
        }
        if (var6 == null) {
            return null;
        }
        int var7 = var3[0] / var5;
        int var17 = var3[1] / var5;
        int var9 = var3[2] / var5;
        final float var10 = var4 / var5;
        final float var11 = Math.max(var7, Math.max(var17, var9));
        var7 = (int)(var7 * var10 / var11);
        var17 = (int)(var17 * var10 / var11);
        var9 = (int)(var9 * var10 / var11);
        int var16 = (var7 << 8) + var17;
        var16 = (var16 << 8) + var9;
        var6.Â(var2, var16);
        return var2;
    }
    
    @Override
    public int HorizonCode_Horizon_È() {
        return 10;
    }
    
    @Override
    public ItemStack Â() {
        return null;
    }
    
    @Override
    public ItemStack[] Â(final InventoryCrafting p_179532_1_) {
        final ItemStack[] var2 = new ItemStack[p_179532_1_.áŒŠÆ()];
        for (int var3 = 0; var3 < var2.length; ++var3) {
            final ItemStack var4 = p_179532_1_.á(var3);
            if (var4 != null && var4.HorizonCode_Horizon_È().ÂµÈ()) {
                var2[var3] = new ItemStack(var4.HorizonCode_Horizon_È().áˆºÑ¢Õ());
            }
        }
        return var2;
    }
}
