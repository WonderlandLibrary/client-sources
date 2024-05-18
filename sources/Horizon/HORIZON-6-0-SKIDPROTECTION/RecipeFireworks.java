package HORIZON-6-0-SKIDPROTECTION;

import java.util.ArrayList;
import com.google.common.collect.Lists;

public class RecipeFireworks implements IRecipe
{
    private ItemStack HorizonCode_Horizon_È;
    private static final String Â = "CL_00000083";
    
    @Override
    public boolean HorizonCode_Horizon_È(final InventoryCrafting p_77569_1_, final World worldIn) {
        this.HorizonCode_Horizon_È = null;
        int var3 = 0;
        int var4 = 0;
        int var5 = 0;
        int var6 = 0;
        int var7 = 0;
        int var8 = 0;
        for (int var9 = 0; var9 < p_77569_1_.áŒŠÆ(); ++var9) {
            final ItemStack var10 = p_77569_1_.á(var9);
            if (var10 != null) {
                if (var10.HorizonCode_Horizon_È() == Items.É) {
                    ++var4;
                }
                else if (var10.HorizonCode_Horizon_È() == Items.Ñ¢È) {
                    ++var6;
                }
                else if (var10.HorizonCode_Horizon_È() == Items.áŒŠÔ) {
                    ++var5;
                }
                else if (var10.HorizonCode_Horizon_È() == Items.ˆà¢) {
                    ++var3;
                }
                else if (var10.HorizonCode_Horizon_È() == Items.Ø­Ñ¢á€) {
                    ++var7;
                }
                else if (var10.HorizonCode_Horizon_È() == Items.áŒŠÆ) {
                    ++var7;
                }
                else if (var10.HorizonCode_Horizon_È() == Items.ÇŽØ) {
                    ++var8;
                }
                else if (var10.HorizonCode_Horizon_È() == Items.ÇŽÕ) {
                    ++var8;
                }
                else if (var10.HorizonCode_Horizon_È() == Items.Œáƒ) {
                    ++var8;
                }
                else {
                    if (var10.HorizonCode_Horizon_È() != Items.ˆ) {
                        return false;
                    }
                    ++var8;
                }
            }
        }
        var7 += var5 + var8;
        if (var4 > 3 || var3 > 1) {
            return false;
        }
        if (var4 >= 1 && var3 == 1 && var7 == 0) {
            this.HorizonCode_Horizon_È = new ItemStack(Items.ŠáŒŠà¢);
            if (var6 > 0) {
                final NBTTagCompound var11 = new NBTTagCompound();
                final NBTTagCompound var12 = new NBTTagCompound();
                final NBTTagList var13 = new NBTTagList();
                for (int var14 = 0; var14 < p_77569_1_.áŒŠÆ(); ++var14) {
                    final ItemStack var15 = p_77569_1_.á(var14);
                    if (var15 != null && var15.HorizonCode_Horizon_È() == Items.Ñ¢È && var15.£á() && var15.Å().Â("Explosion", 10)) {
                        var13.HorizonCode_Horizon_È(var15.Å().ˆÏ­("Explosion"));
                    }
                }
                var12.HorizonCode_Horizon_È("Explosions", var13);
                var12.HorizonCode_Horizon_È("Flight", (byte)var4);
                var11.HorizonCode_Horizon_È("Fireworks", var12);
                this.HorizonCode_Horizon_È.Ø­áŒŠá(var11);
            }
            return true;
        }
        if (var4 == 1 && var3 == 0 && var6 == 0 && var5 > 0 && var8 <= 1) {
            this.HorizonCode_Horizon_È = new ItemStack(Items.Ñ¢È);
            final NBTTagCompound var11 = new NBTTagCompound();
            final NBTTagCompound var12 = new NBTTagCompound();
            byte var16 = 0;
            final ArrayList var17 = Lists.newArrayList();
            for (int var18 = 0; var18 < p_77569_1_.áŒŠÆ(); ++var18) {
                final ItemStack var19 = p_77569_1_.á(var18);
                if (var19 != null) {
                    if (var19.HorizonCode_Horizon_È() == Items.áŒŠÔ) {
                        var17.add(ItemDye.à[var19.Ø() & 0xF]);
                    }
                    else if (var19.HorizonCode_Horizon_È() == Items.Ø­Ñ¢á€) {
                        var12.HorizonCode_Horizon_È("Flicker", true);
                    }
                    else if (var19.HorizonCode_Horizon_È() == Items.áŒŠÆ) {
                        var12.HorizonCode_Horizon_È("Trail", true);
                    }
                    else if (var19.HorizonCode_Horizon_È() == Items.ÇŽØ) {
                        var16 = 1;
                    }
                    else if (var19.HorizonCode_Horizon_È() == Items.ÇŽÕ) {
                        var16 = 4;
                    }
                    else if (var19.HorizonCode_Horizon_È() == Items.Œáƒ) {
                        var16 = 2;
                    }
                    else if (var19.HorizonCode_Horizon_È() == Items.ˆ) {
                        var16 = 3;
                    }
                }
            }
            final int[] var20 = new int[var17.size()];
            for (int var21 = 0; var21 < var20.length; ++var21) {
                var20[var21] = var17.get(var21);
            }
            var12.HorizonCode_Horizon_È("Colors", var20);
            var12.HorizonCode_Horizon_È("Type", var16);
            var11.HorizonCode_Horizon_È("Explosion", var12);
            this.HorizonCode_Horizon_È.Ø­áŒŠá(var11);
            return true;
        }
        if (var4 != 0 || var3 != 0 || var6 != 1 || var5 <= 0 || var5 != var7) {
            return false;
        }
        final ArrayList var22 = Lists.newArrayList();
        for (int var23 = 0; var23 < p_77569_1_.áŒŠÆ(); ++var23) {
            final ItemStack var24 = p_77569_1_.á(var23);
            if (var24 != null) {
                if (var24.HorizonCode_Horizon_È() == Items.áŒŠÔ) {
                    var22.add(ItemDye.à[var24.Ø() & 0xF]);
                }
                else if (var24.HorizonCode_Horizon_È() == Items.Ñ¢È) {
                    this.HorizonCode_Horizon_È = var24.áˆºÑ¢Õ();
                    this.HorizonCode_Horizon_È.Â = 1;
                }
            }
        }
        final int[] var25 = new int[var22.size()];
        for (int var26 = 0; var26 < var25.length; ++var26) {
            var25[var26] = var22.get(var26);
        }
        if (this.HorizonCode_Horizon_È == null || !this.HorizonCode_Horizon_È.£á()) {
            return false;
        }
        final NBTTagCompound var27 = this.HorizonCode_Horizon_È.Å().ˆÏ­("Explosion");
        if (var27 == null) {
            return false;
        }
        var27.HorizonCode_Horizon_È("FadeColors", var25);
        return true;
    }
    
    @Override
    public ItemStack HorizonCode_Horizon_È(final InventoryCrafting p_77572_1_) {
        return this.HorizonCode_Horizon_È.áˆºÑ¢Õ();
    }
    
    @Override
    public int HorizonCode_Horizon_È() {
        return 10;
    }
    
    @Override
    public ItemStack Â() {
        return this.HorizonCode_Horizon_È;
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
