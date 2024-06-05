package net.minecraft.src;

import java.util.*;

public class ItemEnchantedBook extends Item
{
    public ItemEnchantedBook(final int par1) {
        super(par1);
    }
    
    @Override
    public boolean hasEffect(final ItemStack par1ItemStack) {
        return true;
    }
    
    @Override
    public boolean isItemTool(final ItemStack par1ItemStack) {
        return false;
    }
    
    @Override
    public EnumRarity getRarity(final ItemStack par1ItemStack) {
        return (this.func_92110_g(par1ItemStack).tagCount() > 0) ? EnumRarity.uncommon : super.getRarity(par1ItemStack);
    }
    
    public NBTTagList func_92110_g(final ItemStack par1ItemStack) {
        return (NBTTagList)((par1ItemStack.stackTagCompound != null && par1ItemStack.stackTagCompound.hasKey("StoredEnchantments")) ? par1ItemStack.stackTagCompound.getTag("StoredEnchantments") : new NBTTagList());
    }
    
    @Override
    public void addInformation(final ItemStack par1ItemStack, final EntityPlayer par2EntityPlayer, final List par3List, final boolean par4) {
        super.addInformation(par1ItemStack, par2EntityPlayer, par3List, par4);
        final NBTTagList var5 = this.func_92110_g(par1ItemStack);
        if (var5 != null) {
            for (int var6 = 0; var6 < var5.tagCount(); ++var6) {
                final short var7 = ((NBTTagCompound)var5.tagAt(var6)).getShort("id");
                final short var8 = ((NBTTagCompound)var5.tagAt(var6)).getShort("lvl");
                if (Enchantment.enchantmentsList[var7] != null) {
                    par3List.add(Enchantment.enchantmentsList[var7].getTranslatedName(var8));
                }
            }
        }
    }
    
    public void func_92115_a(final ItemStack par1ItemStack, final EnchantmentData par2EnchantmentData) {
        final NBTTagList var3 = this.func_92110_g(par1ItemStack);
        boolean var4 = true;
        for (int var5 = 0; var5 < var3.tagCount(); ++var5) {
            final NBTTagCompound var6 = (NBTTagCompound)var3.tagAt(var5);
            if (var6.getShort("id") == par2EnchantmentData.enchantmentobj.effectId) {
                if (var6.getShort("lvl") < par2EnchantmentData.enchantmentLevel) {
                    var6.setShort("lvl", (short)par2EnchantmentData.enchantmentLevel);
                }
                var4 = false;
                break;
            }
        }
        if (var4) {
            final NBTTagCompound var7 = new NBTTagCompound();
            var7.setShort("id", (short)par2EnchantmentData.enchantmentobj.effectId);
            var7.setShort("lvl", (short)par2EnchantmentData.enchantmentLevel);
            var3.appendTag(var7);
        }
        if (!par1ItemStack.hasTagCompound()) {
            par1ItemStack.setTagCompound(new NBTTagCompound());
        }
        par1ItemStack.getTagCompound().setTag("StoredEnchantments", var3);
    }
    
    public ItemStack func_92111_a(final EnchantmentData par1EnchantmentData) {
        final ItemStack var2 = new ItemStack(this);
        this.func_92115_a(var2, par1EnchantmentData);
        return var2;
    }
    
    public void func_92113_a(final Enchantment par1Enchantment, final List par2List) {
        for (int var3 = par1Enchantment.getMinLevel(); var3 <= par1Enchantment.getMaxLevel(); ++var3) {
            par2List.add(this.func_92111_a(new EnchantmentData(par1Enchantment, var3)));
        }
    }
    
    public ItemStack func_92109_a(final Random par1Random) {
        final Enchantment var2 = Enchantment.field_92090_c[par1Random.nextInt(Enchantment.field_92090_c.length)];
        final ItemStack var3 = new ItemStack(this.itemID, 1, 0);
        final int var4 = MathHelper.getRandomIntegerInRange(par1Random, var2.getMinLevel(), var2.getMaxLevel());
        this.func_92115_a(var3, new EnchantmentData(var2, var4));
        return var3;
    }
    
    public WeightedRandomChestContent func_92114_b(final Random par1Random) {
        return this.func_92112_a(par1Random, 1, 1, 1);
    }
    
    public WeightedRandomChestContent func_92112_a(final Random par1Random, final int par2, final int par3, final int par4) {
        final Enchantment var5 = Enchantment.field_92090_c[par1Random.nextInt(Enchantment.field_92090_c.length)];
        final ItemStack var6 = new ItemStack(this.itemID, 1, 0);
        final int var7 = MathHelper.getRandomIntegerInRange(par1Random, var5.getMinLevel(), var5.getMaxLevel());
        this.func_92115_a(var6, new EnchantmentData(var5, var7));
        return new WeightedRandomChestContent(var6, par2, par3, par4);
    }
}
