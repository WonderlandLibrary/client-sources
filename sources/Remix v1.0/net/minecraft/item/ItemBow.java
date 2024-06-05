package net.minecraft.item;

import net.minecraft.creativetab.*;
import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.enchantment.*;
import net.minecraft.init.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.entity.*;
import net.minecraft.stats.*;

public class ItemBow extends Item
{
    public static final String[] bowPullIconNameArray;
    
    public ItemBow() {
        this.maxStackSize = 1;
        this.setMaxDamage(384);
        this.setCreativeTab(CreativeTabs.tabCombat);
    }
    
    @Override
    public void onPlayerStoppedUsing(final ItemStack stack, final World worldIn, final EntityPlayer playerIn, final int timeLeft) {
        final boolean var5 = playerIn.capabilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, stack) > 0;
        if (var5 || playerIn.inventory.hasItem(Items.arrow)) {
            final int var6 = this.getMaxItemUseDuration(stack) - timeLeft;
            float var7 = var6 / 20.0f;
            var7 = (var7 * var7 + var7 * 2.0f) / 3.0f;
            if (var7 < 0.1) {
                return;
            }
            if (var7 > 1.0f) {
                var7 = 1.0f;
            }
            final EntityArrow var8 = new EntityArrow(worldIn, playerIn, var7 * 2.0f);
            if (var7 == 1.0f) {
                var8.setIsCritical(true);
            }
            final int var9 = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, stack);
            if (var9 > 0) {
                var8.setDamage(var8.getDamage() + var9 * 0.5 + 0.5);
            }
            final int var10 = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, stack);
            if (var10 > 0) {
                var8.setKnockbackStrength(var10);
            }
            if (EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, stack) > 0) {
                var8.setFire(100);
            }
            stack.damageItem(1, playerIn);
            worldIn.playSoundAtEntity(playerIn, "random.bow", 1.0f, 1.0f / (ItemBow.itemRand.nextFloat() * 0.4f + 1.2f) + var7 * 0.5f);
            if (var5) {
                var8.canBePickedUp = 2;
            }
            else {
                playerIn.inventory.consumeInventoryItem(Items.arrow);
            }
            playerIn.triggerAchievement(StatList.objectUseStats[Item.getIdFromItem(this)]);
            if (!worldIn.isRemote) {
                worldIn.spawnEntityInWorld(var8);
            }
        }
    }
    
    @Override
    public ItemStack onItemUseFinish(final ItemStack stack, final World worldIn, final EntityPlayer playerIn) {
        return stack;
    }
    
    @Override
    public int getMaxItemUseDuration(final ItemStack stack) {
        return 72000;
    }
    
    @Override
    public EnumAction getItemUseAction(final ItemStack stack) {
        return EnumAction.BOW;
    }
    
    @Override
    public ItemStack onItemRightClick(final ItemStack itemStackIn, final World worldIn, final EntityPlayer playerIn) {
        if (playerIn.capabilities.isCreativeMode || playerIn.inventory.hasItem(Items.arrow)) {
            playerIn.setItemInUse(itemStackIn, this.getMaxItemUseDuration(itemStackIn));
        }
        return itemStackIn;
    }
    
    @Override
    public int getItemEnchantability() {
        return 1;
    }
    
    static {
        bowPullIconNameArray = new String[] { "pulling_0", "pulling_1", "pulling_2" };
    }
}
