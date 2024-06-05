package net.minecraft.src;

import java.util.*;

public class ItemFireworkCharge extends Item
{
    private Icon theIcon;
    
    public ItemFireworkCharge(final int par1) {
        super(par1);
    }
    
    @Override
    public Icon getIconFromDamageForRenderPass(final int par1, final int par2) {
        return (par2 > 0) ? this.theIcon : super.getIconFromDamageForRenderPass(par1, par2);
    }
    
    @Override
    public int getColorFromItemStack(final ItemStack par1ItemStack, final int par2) {
        if (par2 != 1) {
            return super.getColorFromItemStack(par1ItemStack, par2);
        }
        final NBTBase var3 = func_92108_a(par1ItemStack, "Colors");
        if (var3 == null) {
            return 9079434;
        }
        final NBTTagIntArray var4 = (NBTTagIntArray)var3;
        if (var4.intArray.length == 1) {
            return var4.intArray[0];
        }
        int var5 = 0;
        int var6 = 0;
        int var7 = 0;
        for (final int var11 : var4.intArray) {
            var5 += (var11 & 0xFF0000) >> 16;
            var6 += (var11 & 0xFF00) >> 8;
            var7 += (var11 & 0xFF) >> 0;
        }
        var5 /= var4.intArray.length;
        var6 /= var4.intArray.length;
        var7 /= var4.intArray.length;
        return var5 << 16 | var6 << 8 | var7;
    }
    
    @Override
    public boolean requiresMultipleRenderPasses() {
        return true;
    }
    
    public static NBTBase func_92108_a(final ItemStack par0ItemStack, final String par1Str) {
        if (par0ItemStack.hasTagCompound()) {
            final NBTTagCompound var2 = par0ItemStack.getTagCompound().getCompoundTag("Explosion");
            if (var2 != null) {
                return var2.getTag(par1Str);
            }
        }
        return null;
    }
    
    @Override
    public void addInformation(final ItemStack par1ItemStack, final EntityPlayer par2EntityPlayer, final List par3List, final boolean par4) {
        if (par1ItemStack.hasTagCompound()) {
            final NBTTagCompound var5 = par1ItemStack.getTagCompound().getCompoundTag("Explosion");
            if (var5 != null) {
                func_92107_a(var5, par3List);
            }
        }
    }
    
    public static void func_92107_a(final NBTTagCompound par0NBTTagCompound, final List par1List) {
        final byte var2 = par0NBTTagCompound.getByte("Type");
        if (var2 >= 0 && var2 <= 4) {
            par1List.add(StatCollector.translateToLocal("item.fireworksCharge.type." + var2).trim());
        }
        else {
            par1List.add(StatCollector.translateToLocal("item.fireworksCharge.type").trim());
        }
        final int[] var3 = par0NBTTagCompound.getIntArray("Colors");
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
                for (int var11 = 0; var11 < 16; ++var11) {
                    if (var9 == ItemDye.dyeColors[var11]) {
                        var10 = true;
                        var5 = String.valueOf(var5) + StatCollector.translateToLocal("item.fireworksCharge." + ItemDye.dyeColorNames[var11]);
                        break;
                    }
                }
                if (!var10) {
                    var5 = String.valueOf(var5) + StatCollector.translateToLocal("item.fireworksCharge.customColor");
                }
            }
            par1List.add(var5);
        }
        final int[] var12 = par0NBTTagCompound.getIntArray("FadeColors");
        if (var12.length > 0) {
            boolean var13 = true;
            String var14 = String.valueOf(StatCollector.translateToLocal("item.fireworksCharge.fadeTo")) + " ";
            final int[] var15 = var12;
            for (int var8 = var12.length, var9 = 0; var9 < var8; ++var9) {
                final int var16 = var15[var9];
                if (!var13) {
                    var14 = String.valueOf(var14) + ", ";
                }
                var13 = false;
                boolean var17 = false;
                for (int var18 = 0; var18 < 16; ++var18) {
                    if (var16 == ItemDye.dyeColors[var18]) {
                        var17 = true;
                        var14 = String.valueOf(var14) + StatCollector.translateToLocal("item.fireworksCharge." + ItemDye.dyeColorNames[var18]);
                        break;
                    }
                }
                if (!var17) {
                    var14 = String.valueOf(var14) + StatCollector.translateToLocal("item.fireworksCharge.customColor");
                }
            }
            par1List.add(var14);
        }
        boolean var13 = par0NBTTagCompound.getBoolean("Trail");
        if (var13) {
            par1List.add(StatCollector.translateToLocal("item.fireworksCharge.trail"));
        }
        final boolean var19 = par0NBTTagCompound.getBoolean("Flicker");
        if (var19) {
            par1List.add(StatCollector.translateToLocal("item.fireworksCharge.flicker"));
        }
    }
    
    @Override
    public void registerIcons(final IconRegister par1IconRegister) {
        super.registerIcons(par1IconRegister);
        this.theIcon = par1IconRegister.registerIcon("fireworksCharge_overlay");
    }
}
