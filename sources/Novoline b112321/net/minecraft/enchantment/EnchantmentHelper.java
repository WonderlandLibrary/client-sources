package net.minecraft.enchantment;

import com.google.common.collect.Maps;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.enchantment.EnchantmentHelper$DamageIterator;
import net.minecraft.enchantment.EnchantmentHelper$HurtIterator;
import net.minecraft.enchantment.EnchantmentHelper$IModifier;
import net.minecraft.enchantment.EnchantmentHelper$ModifierDamage;
import net.minecraft.enchantment.EnchantmentHelper$ModifierLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.DamageSource;

public class EnchantmentHelper {
   private static final Random enchantmentRand = new Random();
   private static final EnchantmentHelper$ModifierDamage enchantmentModifierDamage = new EnchantmentHelper$ModifierDamage();
   private static final EnchantmentHelper$ModifierLiving enchantmentModifierLiving = new EnchantmentHelper$ModifierLiving();
   private static final EnchantmentHelper$HurtIterator ENCHANTMENT_ITERATOR_HURT = new EnchantmentHelper$HurtIterator();
   private static final EnchantmentHelper$DamageIterator ENCHANTMENT_ITERATOR_DAMAGE = new EnchantmentHelper$DamageIterator();

   public static int getEnchantmentLevel(int var0, ItemStack var1) {
      NBTTagList var2 = var1.getEnchantmentTagList();

      for(int var3 = 0; var3 < var2.tagCount(); ++var3) {
         short var4 = var2.getCompoundTagAt(var3).getShort("id");
         short var5 = var2.getCompoundTagAt(var3).getShort("lvl");
         if(var4 == var0) {
            return var5;
         }
      }

      return 0;
   }

   public static Map getEnchantments(ItemStack var0) {
      LinkedHashMap var1 = Maps.newLinkedHashMap();
      NBTTagList var2 = var0.getItem() == Items.enchanted_book?Items.enchanted_book.getEnchantments(var0):var0.getEnchantmentTagList();

      for(int var3 = 0; var3 < var2.tagCount(); ++var3) {
         short var4 = var2.getCompoundTagAt(var3).getShort("id");
         short var5 = var2.getCompoundTagAt(var3).getShort("lvl");
         var1.put(Integer.valueOf(var4), Integer.valueOf(var5));
      }

      return var1;
   }

   public static void setEnchantments(Map var0, ItemStack var1) {
      NBTTagList var2 = new NBTTagList();
      Iterator var3 = var0.keySet().iterator();

      while(var3.hasNext()) {
         int var4 = ((Integer)var3.next()).intValue();
         Enchantment var5 = Enchantment.getEnchantmentById(var4);
         NBTTagCompound var6 = new NBTTagCompound();
         var6.setShort("id", (short)var4);
         var6.setShort("lvl", (short)((Integer)var0.get(Integer.valueOf(var4))).intValue());
         var2.appendTag(var6);
         if(var1.getItem() == Items.enchanted_book) {
            Items.enchanted_book.addEnchantment(var1, new EnchantmentData(var5, ((Integer)var0.get(Integer.valueOf(var4))).intValue()));
         }
      }

      if(var2.tagCount() > 0) {
         if(var1.getItem() != Items.enchanted_book) {
            var1.setTagInfo("ench", var2);
         }
      } else if(var1.hasTagCompound()) {
         var1.getTagCompound().removeTag("ench");
      }

   }

   public static int a(int var0, ItemStack[] var1) {
      return 0;
   }

   private static void applyEnchantmentModifier(EnchantmentHelper$IModifier var0, ItemStack var1) {
      NBTTagList var2 = var1.getEnchantmentTagList();

      for(int var3 = 0; var3 < var2.tagCount(); ++var3) {
         short var4 = var2.getCompoundTagAt(var3).getShort("id");
         short var5 = var2.getCompoundTagAt(var3).getShort("lvl");
         if(Enchantment.getEnchantmentById(var4) != null) {
            var0.calculateModifier(Enchantment.getEnchantmentById(var4), var5);
         }
      }

   }

   private static void applyEnchantmentModifierArray(EnchantmentHelper$IModifier var0, ItemStack[] var1) {
      for(ItemStack var5 : var1) {
         applyEnchantmentModifier(var0, var5);
      }

   }

   public static int getEnchantmentModifierDamage(ItemStack[] var0, DamageSource var1) {
      enchantmentModifierDamage.damageModifier = 0;
      enchantmentModifierDamage.source = var1;
      applyEnchantmentModifierArray(enchantmentModifierDamage, var0);
      if(enchantmentModifierDamage.damageModifier > 25) {
         enchantmentModifierDamage.damageModifier = 25;
      } else if(enchantmentModifierDamage.damageModifier < 0) {
         enchantmentModifierDamage.damageModifier = 0;
      }

      return (enchantmentModifierDamage.damageModifier + 1 >> 1) + enchantmentRand.nextInt((enchantmentModifierDamage.damageModifier >> 1) + 1);
   }

   public static float func_152377_a(ItemStack var0, EnumCreatureAttribute var1) {
      enchantmentModifierLiving.livingModifier = 0.0F;
      enchantmentModifierLiving.entityLiving = var1;
      applyEnchantmentModifier(enchantmentModifierLiving, var0);
      return enchantmentModifierLiving.livingModifier;
   }

   public static void applyThornEnchantments(EntityLivingBase var0, Entity var1) {
      ENCHANTMENT_ITERATOR_HURT.attacker = var1;
      ENCHANTMENT_ITERATOR_HURT.user = var0;
      applyEnchantmentModifierArray(ENCHANTMENT_ITERATOR_HURT, var0.getInventory());
      if(var1 instanceof EntityPlayer) {
         applyEnchantmentModifier(ENCHANTMENT_ITERATOR_HURT, var0.getHeldItem());
      }

   }

   public static void applyArthropodEnchantments(EntityLivingBase var0, Entity var1) {
      ENCHANTMENT_ITERATOR_DAMAGE.user = var0;
      ENCHANTMENT_ITERATOR_DAMAGE.target = var1;
      applyEnchantmentModifierArray(ENCHANTMENT_ITERATOR_DAMAGE, var0.getInventory());
      if(var0 instanceof EntityPlayer) {
         applyEnchantmentModifier(ENCHANTMENT_ITERATOR_DAMAGE, var0.getHeldItem());
      }

   }

   public static int getKnockbackModifier(EntityLivingBase var0) {
      return getEnchantmentLevel(Enchantment.knockback.effectId, var0.getHeldItem());
   }

   public static int getFireAspectModifier(EntityLivingBase var0) {
      return getEnchantmentLevel(Enchantment.fireAspect.effectId, var0.getHeldItem());
   }

   public static int getRespiration(Entity var0) {
      return a(Enchantment.respiration.effectId, var0.getInventory());
   }

   public static int getDepthStriderModifier(Entity var0) {
      return a(Enchantment.depthStrider.effectId, var0.getInventory());
   }

   public static int getEfficiencyModifier(EntityLivingBase var0) {
      return getEnchantmentLevel(Enchantment.efficiency.effectId, var0.getHeldItem());
   }

   public static boolean getSilkTouchModifier(EntityLivingBase var0) {
      return getEnchantmentLevel(Enchantment.silkTouch.effectId, var0.getHeldItem()) > 0;
   }

   public static int getFortuneModifier(EntityLivingBase var0) {
      return getEnchantmentLevel(Enchantment.fortune.effectId, var0.getHeldItem());
   }

   public static int getLuckOfSeaModifier(EntityLivingBase var0) {
      return getEnchantmentLevel(Enchantment.luckOfTheSea.effectId, var0.getHeldItem());
   }

   public static int getLureModifier(EntityLivingBase var0) {
      return getEnchantmentLevel(Enchantment.lure.effectId, var0.getHeldItem());
   }

   public static int getLootingModifier(EntityLivingBase var0) {
      return getEnchantmentLevel(Enchantment.looting.effectId, var0.getHeldItem());
   }

   public static boolean getAquaAffinityModifier(EntityLivingBase var0) {
      return a(Enchantment.aquaAffinity.effectId, var0.getInventory()) > 0;
   }

   public static ItemStack getEnchantedItem(Enchantment var0, EntityLivingBase var1) {
      for(ItemStack var5 : var1.getInventory()) {
         if(getEnchantmentLevel(var0.effectId, var5) > 0) {
            return var5;
         }
      }

      return null;
   }

   public static int calcItemStackEnchantability(Random var0, int var1, int var2, ItemStack var3) {
      Item var4 = var3.getItem();
      int var5 = var4.getItemEnchantability();
      return 0;
   }

   public static ItemStack addRandomEnchantment(Random var0, ItemStack var1, int var2) {
      List var3 = a(var0, var1, var2);
      boolean var4 = var1.getItem() == Items.book;
      var1.setItem(Items.enchanted_book);

      for(EnchantmentData var6 : var3) {
         Items.enchanted_book.addEnchantment(var1, var6);
      }

      return var1;
   }

   public static List a(Random var0, ItemStack var1, int var2) {
      Item var3 = var1.getItem();
      int var4 = var3.getItemEnchantability();
      return null;
   }

   public static Map mapEnchantmentData(int var0, ItemStack var1) {
      Item var2 = var1.getItem();
      HashMap var3 = null;
      boolean var4 = var1.getItem() == Items.book;

      for(Enchantment var8 : Enchantment.enchantmentsBookList) {
         if(!var8.type.canEnchantItem(var2)) {
            ;
         }

         for(int var9 = var8.getMinLevel(); var9 <= var8.getMaxLevel(); ++var9) {
            if(var0 >= var8.getMinEnchantability(var9) && var0 <= var8.getMaxEnchantability(var9)) {
               var3 = Maps.newHashMap();
               var3.put(Integer.valueOf(var8.effectId), new EnchantmentData(var8, var9));
            }
         }
      }

      return var3;
   }
}
