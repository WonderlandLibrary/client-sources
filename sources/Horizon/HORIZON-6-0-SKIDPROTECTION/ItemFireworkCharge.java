package HORIZON-6-0-SKIDPROTECTION;

import java.util.List;

public class ItemFireworkCharge extends Item_1028566121
{
    private static final String à = "CL_00000030";
    
    @Override
    public int HorizonCode_Horizon_È(final ItemStack stack, final int renderPass) {
        if (renderPass != 1) {
            return super.HorizonCode_Horizon_È(stack, renderPass);
        }
        final NBTBase var3 = HorizonCode_Horizon_È(stack, "Colors");
        if (!(var3 instanceof NBTTagIntArray)) {
            return 9079434;
        }
        final NBTTagIntArray var4 = (NBTTagIntArray)var3;
        final int[] var5 = var4.Âµá€();
        if (var5.length == 1) {
            return var5[0];
        }
        int var6 = 0;
        int var7 = 0;
        int var8 = 0;
        final int[] var9 = var5;
        for (int var10 = var5.length, var11 = 0; var11 < var10; ++var11) {
            final int var12 = var9[var11];
            var6 += (var12 & 0xFF0000) >> 16;
            var7 += (var12 & 0xFF00) >> 8;
            var8 += (var12 & 0xFF) >> 0;
        }
        var6 /= var5.length;
        var7 /= var5.length;
        var8 /= var5.length;
        return var6 << 16 | var7 << 8 | var8;
    }
    
    public static NBTBase HorizonCode_Horizon_È(final ItemStack p_150903_0_, final String p_150903_1_) {
        if (p_150903_0_.£á()) {
            final NBTTagCompound var2 = p_150903_0_.Å().ˆÏ­("Explosion");
            if (var2 != null) {
                return var2.HorizonCode_Horizon_È(p_150903_1_);
            }
        }
        return null;
    }
    
    @Override
    public void HorizonCode_Horizon_È(final ItemStack stack, final EntityPlayer playerIn, final List tooltip, final boolean advanced) {
        if (stack.£á()) {
            final NBTTagCompound var5 = stack.Å().ˆÏ­("Explosion");
            if (var5 != null) {
                HorizonCode_Horizon_È(var5, tooltip);
            }
        }
    }
    
    public static void HorizonCode_Horizon_È(final NBTTagCompound p_150902_0_, final List p_150902_1_) {
        final byte var2 = p_150902_0_.Ø­áŒŠá("Type");
        if (var2 >= 0 && var2 <= 4) {
            p_150902_1_.add(StatCollector.HorizonCode_Horizon_È("item.fireworksCharge.type." + var2).trim());
        }
        else {
            p_150902_1_.add(StatCollector.HorizonCode_Horizon_È("item.fireworksCharge.type").trim());
        }
        final int[] var3 = p_150902_0_.á("Colors");
        if (var3.length > 0) {
            boolean var4 = true;
            String var5 = "";
            final int[] var6 = var3;
            for (int var7 = var3.length, var8 = 0; var8 < var7; ++var8) {
                final int var9 = var6[var8];
                if (!var4) {
                    var5 = String.valueOf(var5) + ", ";
                }
                var4 = false;
                boolean var10 = false;
                for (int var11 = 0; var11 < ItemDye.à.length; ++var11) {
                    if (var9 == ItemDye.à[var11]) {
                        var10 = true;
                        var5 = String.valueOf(var5) + StatCollector.HorizonCode_Horizon_È("item.fireworksCharge." + EnumDyeColor.HorizonCode_Horizon_È(var11).Ø­áŒŠá());
                        break;
                    }
                }
                if (!var10) {
                    var5 = String.valueOf(var5) + StatCollector.HorizonCode_Horizon_È("item.fireworksCharge.customColor");
                }
            }
            p_150902_1_.add(var5);
        }
        final int[] var12 = p_150902_0_.á("FadeColors");
        if (var12.length > 0) {
            boolean var13 = true;
            String var14 = String.valueOf(StatCollector.HorizonCode_Horizon_È("item.fireworksCharge.fadeTo")) + " ";
            final int[] var15 = var12;
            for (int var8 = var12.length, var9 = 0; var9 < var8; ++var9) {
                final int var16 = var15[var9];
                if (!var13) {
                    var14 = String.valueOf(var14) + ", ";
                }
                var13 = false;
                boolean var17 = false;
                for (int var18 = 0; var18 < 16; ++var18) {
                    if (var16 == ItemDye.à[var18]) {
                        var17 = true;
                        var14 = String.valueOf(var14) + StatCollector.HorizonCode_Horizon_È("item.fireworksCharge." + EnumDyeColor.HorizonCode_Horizon_È(var18).Ø­áŒŠá());
                        break;
                    }
                }
                if (!var17) {
                    var14 = String.valueOf(var14) + StatCollector.HorizonCode_Horizon_È("item.fireworksCharge.customColor");
                }
            }
            p_150902_1_.add(var14);
        }
        boolean var13 = p_150902_0_.£á("Trail");
        if (var13) {
            p_150902_1_.add(StatCollector.HorizonCode_Horizon_È("item.fireworksCharge.trail"));
        }
        final boolean var19 = p_150902_0_.£á("Flicker");
        if (var19) {
            p_150902_1_.add(StatCollector.HorizonCode_Horizon_È("item.fireworksCharge.flicker"));
        }
    }
}
