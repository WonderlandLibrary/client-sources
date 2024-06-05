package net.minecraft.src;

import java.util.*;

public class EnchantmentHelper
{
    private static final Random enchantmentRand;
    private static final EnchantmentModifierDamage enchantmentModifierDamage;
    private static final EnchantmentModifierLiving enchantmentModifierLiving;
    
    static {
        enchantmentRand = new Random();
        enchantmentModifierDamage = new EnchantmentModifierDamage(null);
        enchantmentModifierLiving = new EnchantmentModifierLiving(null);
    }
    
    public static int getEnchantmentLevel(final int par0, final ItemStack par1ItemStack) {
        if (par1ItemStack == null) {
            return 0;
        }
        final NBTTagList var2 = par1ItemStack.getEnchantmentTagList();
        if (var2 == null) {
            return 0;
        }
        for (int var3 = 0; var3 < var2.tagCount(); ++var3) {
            final short var4 = ((NBTTagCompound)var2.tagAt(var3)).getShort("id");
            final short var5 = ((NBTTagCompound)var2.tagAt(var3)).getShort("lvl");
            if (var4 == par0) {
                return var5;
            }
        }
        return 0;
    }
    
    public static Map getEnchantments(final ItemStack par0ItemStack) {
        final LinkedHashMap var1 = new LinkedHashMap();
        final NBTTagList var2 = (par0ItemStack.itemID == Item.enchantedBook.itemID) ? Item.enchantedBook.func_92110_g(par0ItemStack) : par0ItemStack.getEnchantmentTagList();
        if (var2 != null) {
            for (int var3 = 0; var3 < var2.tagCount(); ++var3) {
                final short var4 = ((NBTTagCompound)var2.tagAt(var3)).getShort("id");
                final short var5 = ((NBTTagCompound)var2.tagAt(var3)).getShort("lvl");
                var1.put((int)var4, (int)var5);
            }
        }
        return var1;
    }
    
    public static void setEnchantments(final Map par0Map, final ItemStack par1ItemStack) {
        final NBTTagList var2 = new NBTTagList();
        for (final int var4 : par0Map.keySet()) {
            final NBTTagCompound var5 = new NBTTagCompound();
            var5.setShort("id", (short)var4);
            var5.setShort("lvl", (short)(int)par0Map.get(var4));
            var2.appendTag(var5);
            if (par1ItemStack.itemID == Item.enchantedBook.itemID) {
                Item.enchantedBook.func_92115_a(par1ItemStack, new EnchantmentData(var4, par0Map.get(var4)));
            }
        }
        if (var2.tagCount() > 0) {
            if (par1ItemStack.itemID != Item.enchantedBook.itemID) {
                par1ItemStack.setTagInfo("ench", var2);
            }
        }
        else if (par1ItemStack.hasTagCompound()) {
            par1ItemStack.getTagCompound().removeTag("ench");
        }
    }
    
    public static int getMaxEnchantmentLevel(final int par0, final ItemStack[] par1ArrayOfItemStack) {
        if (par1ArrayOfItemStack == null) {
            return 0;
        }
        int var2 = 0;
        for (final ItemStack var5 : par1ArrayOfItemStack) {
            final int var6 = getEnchantmentLevel(par0, var5);
            if (var6 > var2) {
                var2 = var6;
            }
        }
        return var2;
    }
    
    private static void applyEnchantmentModifier(final IEnchantmentModifier par0IEnchantmentModifier, final ItemStack par1ItemStack) {
        if (par1ItemStack != null) {
            final NBTTagList var2 = par1ItemStack.getEnchantmentTagList();
            if (var2 != null) {
                for (int var3 = 0; var3 < var2.tagCount(); ++var3) {
                    final short var4 = ((NBTTagCompound)var2.tagAt(var3)).getShort("id");
                    final short var5 = ((NBTTagCompound)var2.tagAt(var3)).getShort("lvl");
                    if (Enchantment.enchantmentsList[var4] != null) {
                        par0IEnchantmentModifier.calculateModifier(Enchantment.enchantmentsList[var4], var5);
                    }
                }
            }
        }
    }
    
    private static void applyEnchantmentModifierArray(final IEnchantmentModifier par0IEnchantmentModifier, final ItemStack[] par1ArrayOfItemStack) {
        for (final ItemStack var5 : par1ArrayOfItemStack) {
            applyEnchantmentModifier(par0IEnchantmentModifier, var5);
        }
    }
    
    public static int getEnchantmentModifierDamage(final ItemStack[] par0ArrayOfItemStack, final DamageSource par1DamageSource) {
        EnchantmentHelper.enchantmentModifierDamage.damageModifier = 0;
        EnchantmentHelper.enchantmentModifierDamage.source = par1DamageSource;
        applyEnchantmentModifierArray(EnchantmentHelper.enchantmentModifierDamage, par0ArrayOfItemStack);
        if (EnchantmentHelper.enchantmentModifierDamage.damageModifier > 25) {
            EnchantmentHelper.enchantmentModifierDamage.damageModifier = 25;
        }
        return (EnchantmentHelper.enchantmentModifierDamage.damageModifier + 1 >> 1) + EnchantmentHelper.enchantmentRand.nextInt((EnchantmentHelper.enchantmentModifierDamage.damageModifier >> 1) + 1);
    }
    
    public static int getEnchantmentModifierLiving(final EntityLiving par0EntityLiving, final EntityLiving par1EntityLiving) {
        EnchantmentHelper.enchantmentModifierLiving.livingModifier = 0;
        EnchantmentHelper.enchantmentModifierLiving.entityLiving = par1EntityLiving;
        applyEnchantmentModifier(EnchantmentHelper.enchantmentModifierLiving, par0EntityLiving.getHeldItem());
        return (EnchantmentHelper.enchantmentModifierLiving.livingModifier > 0) ? (1 + EnchantmentHelper.enchantmentRand.nextInt(EnchantmentHelper.enchantmentModifierLiving.livingModifier)) : 0;
    }
    
    public static int getKnockbackModifier(final EntityLiving par0EntityLiving, final EntityLiving par1EntityLiving) {
        return getEnchantmentLevel(Enchantment.knockback.effectId, par0EntityLiving.getHeldItem());
    }
    
    public static int getFireAspectModifier(final EntityLiving par0EntityLiving) {
        return getEnchantmentLevel(Enchantment.fireAspect.effectId, par0EntityLiving.getHeldItem());
    }
    
    public static int getRespiration(final EntityLiving par0EntityLiving) {
        return getMaxEnchantmentLevel(Enchantment.respiration.effectId, par0EntityLiving.getLastActiveItems());
    }
    
    public static int getEfficiencyModifier(final EntityLiving par0EntityLiving) {
        return getEnchantmentLevel(Enchantment.efficiency.effectId, par0EntityLiving.getHeldItem());
    }
    
    public static boolean getSilkTouchModifier(final EntityLiving par0EntityLiving) {
        return getEnchantmentLevel(Enchantment.silkTouch.effectId, par0EntityLiving.getHeldItem()) > 0;
    }
    
    public static int getFortuneModifier(final EntityLiving par0EntityLiving) {
        return getEnchantmentLevel(Enchantment.fortune.effectId, par0EntityLiving.getHeldItem());
    }
    
    public static int getLootingModifier(final EntityLiving par0EntityLiving) {
        return getEnchantmentLevel(Enchantment.looting.effectId, par0EntityLiving.getHeldItem());
    }
    
    public static boolean getAquaAffinityModifier(final EntityLiving par0EntityLiving) {
        return getMaxEnchantmentLevel(Enchantment.aquaAffinity.effectId, par0EntityLiving.getLastActiveItems()) > 0;
    }
    
    public static int func_92098_i(final EntityLiving par0EntityLiving) {
        return getMaxEnchantmentLevel(Enchantment.thorns.effectId, par0EntityLiving.getLastActiveItems());
    }
    
    public static ItemStack func_92099_a(final Enchantment par0Enchantment, final EntityLiving par1EntityLiving) {
        for (final ItemStack var5 : par1EntityLiving.getLastActiveItems()) {
            if (var5 != null && getEnchantmentLevel(par0Enchantment.effectId, var5) > 0) {
                return var5;
            }
        }
        return null;
    }
    
    public static int calcItemStackEnchantability(final Random par0Random, final int par1, int par2, final ItemStack par3ItemStack) {
        final Item var4 = par3ItemStack.getItem();
        final int var5 = var4.getItemEnchantability();
        if (var5 <= 0) {
            return 0;
        }
        if (par2 > 15) {
            par2 = 15;
        }
        final int var6 = par0Random.nextInt(8) + 1 + (par2 >> 1) + par0Random.nextInt(par2 + 1);
        return (par1 == 0) ? Math.max(var6 / 3, 1) : ((par1 == 1) ? (var6 * 2 / 3 + 1) : Math.max(var6, par2 * 2));
    }
    
    public static ItemStack addRandomEnchantment(final Random par0Random, final ItemStack par1ItemStack, final int par2) {
        final List var3 = buildEnchantmentList(par0Random, par1ItemStack, par2);
        final boolean var4 = par1ItemStack.itemID == Item.book.itemID;
        if (var4) {
            par1ItemStack.itemID = Item.enchantedBook.itemID;
        }
        if (var3 != null) {
            for (final EnchantmentData var6 : var3) {
                if (var4) {
                    Item.enchantedBook.func_92115_a(par1ItemStack, var6);
                }
                else {
                    par1ItemStack.addEnchantment(var6.enchantmentobj, var6.enchantmentLevel);
                }
            }
        }
        return par1ItemStack;
    }
    
    public static List buildEnchantmentList(final Random par0Random, final ItemStack par1ItemStack, final int par2) {
        final Item var3 = par1ItemStack.getItem();
        int var4 = var3.getItemEnchantability();
        if (var4 <= 0) {
            return null;
        }
        var4 /= 2;
        var4 = 1 + par0Random.nextInt((var4 >> 1) + 1) + par0Random.nextInt((var4 >> 1) + 1);
        final int var5 = var4 + par2;
        final float var6 = (par0Random.nextFloat() + par0Random.nextFloat() - 1.0f) * 0.15f;
        int var7 = (int)(var5 * (1.0f + var6) + 0.5f);
        if (var7 < 1) {
            var7 = 1;
        }
        ArrayList var8 = null;
        final Map var9 = mapEnchantmentData(var7, par1ItemStack);
        if (var9 != null && !var9.isEmpty()) {
            final EnchantmentData var10 = (EnchantmentData)WeightedRandom.getRandomItem(par0Random, var9.values());
            if (var10 != null) {
                var8 = new ArrayList();
                var8.add(var10);
                for (int var11 = var7; par0Random.nextInt(50) <= var11; var11 >>= 1) {
                    final Iterator var12 = var9.keySet().iterator();
                    while (var12.hasNext()) {
                        final Integer var13 = var12.next();
                        boolean var14 = true;
                        for (final EnchantmentData var16 : var8) {
                            if (var16.enchantmentobj.canApplyTogether(Enchantment.enchantmentsList[var13])) {
                                continue;
                            }
                            var14 = false;
                            break;
                        }
                        if (!var14) {
                            var12.remove();
                        }
                    }
                    if (!var9.isEmpty()) {
                        final EnchantmentData var17 = (EnchantmentData)WeightedRandom.getRandomItem(par0Random, var9.values());
                        var8.add(var17);
                    }
                }
            }
        }
        return var8;
    }
    
    public static Map mapEnchantmentData(final int par0, final ItemStack par1ItemStack) {
        final Item var2 = par1ItemStack.getItem();
        HashMap var3 = null;
        final boolean var4 = par1ItemStack.itemID == Item.book.itemID;
        for (final Enchantment var8 : Enchantment.enchantmentsList) {
            if (var8 != null && (var8.type.canEnchantItem(var2) || var4)) {
                for (int var9 = var8.getMinLevel(); var9 <= var8.getMaxLevel(); ++var9) {
                    if (par0 >= var8.getMinEnchantability(var9) && par0 <= var8.getMaxEnchantability(var9)) {
                        if (var3 == null) {
                            var3 = new HashMap();
                        }
                        var3.put(var8.effectId, new EnchantmentData(var8, var9));
                    }
                }
            }
        }
        return var3;
    }
}
