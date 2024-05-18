package net.minecraft.item;

import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.enchantment.*;
import net.minecraft.init.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.entity.*;
import net.minecraft.stats.*;
import net.minecraft.creativetab.*;

public class ItemBow extends Item
{
    private static final String[] I;
    public static final String[] bowPullIconNameArray;
    
    @Override
    public int getMaxItemUseDuration(final ItemStack itemStack) {
        return 69547 + 44173 - 112885 + 71165;
    }
    
    @Override
    public int getItemEnchantability() {
        return " ".length();
    }
    
    @Override
    public void onPlayerStoppedUsing(final ItemStack itemStack, final World world, final EntityPlayer entityPlayer, final int n) {
        int n2;
        if (!entityPlayer.capabilities.isCreativeMode && EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, itemStack) <= 0) {
            n2 = "".length();
            "".length();
            if (4 <= 0) {
                throw null;
            }
        }
        else {
            n2 = " ".length();
        }
        final int n3 = n2;
        if (n3 != 0 || entityPlayer.inventory.hasItem(Items.arrow)) {
            final float n4 = (this.getMaxItemUseDuration(itemStack) - n) / 20.0f;
            float n5 = (n4 * n4 + n4 * 2.0f) / 3.0f;
            if (n5 < 0.1) {
                return;
            }
            if (n5 > 1.0f) {
                n5 = 1.0f;
            }
            final EntityArrow entityArrow = new EntityArrow(world, entityPlayer, n5 * 2.0f);
            if (n5 == 1.0f) {
                entityArrow.setIsCritical(" ".length() != 0);
            }
            final int enchantmentLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, itemStack);
            if (enchantmentLevel > 0) {
                entityArrow.setDamage(entityArrow.getDamage() + enchantmentLevel * 0.5 + 0.5);
            }
            final int enchantmentLevel2 = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, itemStack);
            if (enchantmentLevel2 > 0) {
                entityArrow.setKnockbackStrength(enchantmentLevel2);
            }
            if (EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, itemStack) > 0) {
                entityArrow.setFire(0xE2 ^ 0x86);
            }
            itemStack.damageItem(" ".length(), entityPlayer);
            world.playSoundAtEntity(entityPlayer, ItemBow.I["   ".length()], 1.0f, 1.0f / (ItemBow.itemRand.nextFloat() * 0.4f + 1.2f) + n5 * 0.5f);
            if (n3 != 0) {
                entityArrow.canBePickedUp = "  ".length();
                "".length();
                if (0 == 3) {
                    throw null;
                }
            }
            else {
                entityPlayer.inventory.consumeInventoryItem(Items.arrow);
            }
            entityPlayer.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);
            if (!world.isRemote) {
                world.spawnEntityInWorld(entityArrow);
            }
        }
    }
    
    static {
        I();
        final String[] bowPullIconNameArray2 = new String["   ".length()];
        bowPullIconNameArray2["".length()] = ItemBow.I["".length()];
        bowPullIconNameArray2[" ".length()] = ItemBow.I[" ".length()];
        bowPullIconNameArray2["  ".length()] = ItemBow.I["  ".length()];
        bowPullIconNameArray = bowPullIconNameArray2;
    }
    
    @Override
    public ItemStack onItemRightClick(final ItemStack itemStack, final World world, final EntityPlayer entityPlayer) {
        if (entityPlayer.capabilities.isCreativeMode || entityPlayer.inventory.hasItem(Items.arrow)) {
            entityPlayer.setItemInUse(itemStack, this.getMaxItemUseDuration(itemStack));
        }
        return itemStack;
    }
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (4 != 4) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public ItemStack onItemUseFinish(final ItemStack itemStack, final World world, final EntityPlayer entityPlayer) {
        return itemStack;
    }
    
    public ItemBow() {
        this.maxStackSize = " ".length();
        this.setMaxDamage(19 + 274 - 239 + 330);
        this.setCreativeTab(CreativeTabs.tabCombat);
    }
    
    private static void I() {
        (I = new String[0x8F ^ 0x8B])["".length()] = I("*7\u001d!\u00064%.}", "ZBqMo");
        ItemBow.I[" ".length()] = I("\u00128#\u0001-\f*\u0010\\", "bMOmD");
        ItemBow.I["  ".length()] = I("2\u0007\b?\r,\u0015;a", "BrdSd");
        ItemBow.I["   ".length()] = I("\u0003;\u001d&\u0007\u001ct\u0011-\u001f", "qZsBh");
    }
    
    @Override
    public EnumAction getItemUseAction(final ItemStack itemStack) {
        return EnumAction.BOW;
    }
}
