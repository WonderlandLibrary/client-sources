package net;

import java.util.List;
import java.util.Map;
import java.util.Random;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;

public class aez {
   private static int[] b;

   public static int a(int var0, ItemStack var1) {
      return EnchantmentHelper.getEnchantmentLevel(var0, var1);
   }

   public static boolean h(EntityLivingBase var0) {
      return EnchantmentHelper.getSilkTouchModifier(var0);
   }

   public static int e(EntityLivingBase var0) {
      return EnchantmentHelper.getFortuneModifier(var0);
   }

   public static void b(EntityLivingBase var0, Entity var1) {
      EnchantmentHelper.applyThornEnchantments(var0, var1);
   }

   public static void a(EntityLivingBase var0, Entity var1) {
      EnchantmentHelper.applyArthropodEnchantments(var0, var1);
   }

   public static int a(Random var0, int var1, int var2, ItemStack var3) {
      return EnchantmentHelper.calcItemStackEnchantability(var0, var1, var2, var3);
   }

   public static List a(Random var0, ItemStack var1, int var2) {
      return EnchantmentHelper.a(var0, var1, var2);
   }

   public static boolean b(EntityLivingBase var0) {
      return EnchantmentHelper.getAquaAffinityModifier(var0);
   }

   public static Map a(ItemStack var0) {
      return EnchantmentHelper.getEnchantments(var0);
   }

   public static void a(Map var0, ItemStack var1) {
      EnchantmentHelper.setEnchantments(var0, var1);
   }

   public static int a(Entity var0) {
      return EnchantmentHelper.getRespiration(var0);
   }

   public static int c(EntityLivingBase var0) {
      return EnchantmentHelper.getLootingModifier(var0);
   }

   public static int a(ItemStack[] var0, DamageSource var1) {
      return EnchantmentHelper.getEnchantmentModifierDamage(var0, var1);
   }

   public static int b(Entity var0) {
      return EnchantmentHelper.getDepthStriderModifier(var0);
   }

   public static int f(EntityLivingBase var0) {
      return EnchantmentHelper.getLureModifier(var0);
   }

   public static int i(EntityLivingBase var0) {
      return EnchantmentHelper.getLuckOfSeaModifier(var0);
   }

   public static int a(int var0, ItemStack[] var1) {
      return EnchantmentHelper.a(var0, var1);
   }

   public static ItemStack a(Enchantment var0, EntityLivingBase var1) {
      return EnchantmentHelper.getEnchantedItem(var0, var1);
   }

   public static int g(EntityLivingBase var0) {
      return EnchantmentHelper.getEfficiencyModifier(var0);
   }

   public static float a(ItemStack var0, EnumCreatureAttribute var1) {
      return EnchantmentHelper.func_152377_a(var0, var1);
   }

   public static int a(EntityLivingBase var0) {
      return EnchantmentHelper.getKnockbackModifier(var0);
   }

   public static int d(EntityLivingBase var0) {
      return EnchantmentHelper.getFireAspectModifier(var0);
   }

   public static ItemStack b(Random var0, ItemStack var1, int var2) {
      return EnchantmentHelper.addRandomEnchantment(var0, var1, var2);
   }

   public static void b(int[] var0) {
      b = var0;
   }

   public static int[] b() {
      return b;
   }

   static {
      if(b() != null) {
         b(new int[5]);
      }

   }
}
