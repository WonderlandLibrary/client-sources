package net.minecraft.enchantment;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

public abstract class Enchantment {
   public EnumEnchantmentType type;
   public final int effectId;
   public static final Enchantment fireAspect = new EnchantmentFireAspect(20, new ResourceLocation("fire_aspect"), 2);
   public static final Enchantment aquaAffinity = new EnchantmentWaterWorker(6, new ResourceLocation("aqua_affinity"), 2);
   public static final Enchantment thorns = new EnchantmentThorns(7, new ResourceLocation("thorns"), 1);
   public static final Enchantment fireProtection = new EnchantmentProtection(1, new ResourceLocation("fire_protection"), 5, 1);
   public static final Enchantment field_180312_n = new EnchantmentDamage(18, new ResourceLocation("bane_of_arthropods"), 5, 2);
   public static final Enchantment power;
   public static final Enchantment field_180316_k = new EnchantmentWaterWalker(8, new ResourceLocation("depth_strider"), 2);
   public static final Enchantment field_180314_l = new EnchantmentDamage(16, new ResourceLocation("sharpness"), 10, 0);
   private static final Map field_180307_E = Maps.newHashMap();
   public static final Enchantment silkTouch;
   public static final Enchantment blastProtection = new EnchantmentProtection(3, new ResourceLocation("blast_protection"), 2, 3);
   public static final Enchantment field_180309_e = new EnchantmentProtection(2, new ResourceLocation("feather_falling"), 5, 2);
   public static final Enchantment[] enchantmentsList;
   public static final Enchantment flame;
   public static final Enchantment looting;
   private static final Enchantment[] field_180311_a = new Enchantment[256];
   public static final Enchantment field_180308_g = new EnchantmentProtection(4, new ResourceLocation("projectile_protection"), 5, 4);
   public static final Enchantment field_180315_m = new EnchantmentDamage(17, new ResourceLocation("smite"), 5, 1);
   public static final Enchantment luckOfTheSea;
   protected String name;
   public static final Enchantment lure;
   public static final Enchantment punch;
   public static final Enchantment unbreaking;
   private static final String __OBFID = "CL_00000105";
   public static final Enchantment efficiency;
   public static final Enchantment infinity;
   public static final Enchantment fortune;
   public static final Enchantment field_180313_o = new EnchantmentKnockback(19, new ResourceLocation("knockback"), 5);
   public static final Enchantment field_180310_c = new EnchantmentProtection(0, new ResourceLocation("protection"), 10, 0);
   private final int weight;
   public static final Enchantment field_180317_h = new EnchantmentOxygen(5, new ResourceLocation("respiration"), 2);

   public static String[] func_180304_c() {
      String[] var0 = new String[field_180307_E.size()];
      int var1 = 0;

      ResourceLocation var2;
      for(Iterator var3 = field_180307_E.keySet().iterator(); var3.hasNext(); var0[var1++] = var2.toString()) {
         var2 = (ResourceLocation)var3.next();
      }

      return var0;
   }

   public int getMaxEnchantability(int var1) {
      return this.getMinEnchantability(var1) + 5;
   }

   public boolean canApply(ItemStack var1) {
      return this.type.canEnchantItem(var1.getItem());
   }

   public void func_151367_b(EntityLivingBase var1, Entity var2, int var3) {
   }

   public float func_152376_a(int var1, EnumCreatureAttribute var2) {
      return 0.0F;
   }

   protected Enchantment(int var1, ResourceLocation var2, int var3, EnumEnchantmentType var4) {
      this.effectId = var1;
      this.weight = var3;
      this.type = var4;
      if (field_180311_a[var1] != null) {
         throw new IllegalArgumentException("Duplicate enchantment id!");
      } else {
         field_180311_a[var1] = this;
         field_180307_E.put(var2, this);
      }
   }

   public String getName() {
      return String.valueOf((new StringBuilder("enchantment.")).append(this.name));
   }

   public void func_151368_a(EntityLivingBase var1, Entity var2, int var3) {
   }

   public int getMinEnchantability(int var1) {
      return 1 + var1 * 10;
   }

   public boolean canApplyTogether(Enchantment var1) {
      return this != var1;
   }

   public String getTranslatedName(int var1) {
      String var2 = StatCollector.translateToLocal(this.getName());
      return String.valueOf((new StringBuilder(String.valueOf(var2))).append(" ").append(StatCollector.translateToLocal(String.valueOf((new StringBuilder("enchantment.level.")).append(var1)))));
   }

   public Enchantment setName(String var1) {
      this.name = var1;
      return this;
   }

   public int getWeight() {
      return this.weight;
   }

   public static Enchantment func_180305_b(String var0) {
      return (Enchantment)field_180307_E.get(new ResourceLocation(var0));
   }

   public int getMinLevel() {
      return 1;
   }

   public static Enchantment func_180306_c(int var0) {
      return var0 >= 0 && var0 < field_180311_a.length ? field_180311_a[var0] : null;
   }

   public int calcModifierDamage(int var1, DamageSource var2) {
      return 0;
   }

   static {
      looting = new EnchantmentLootBonus(21, new ResourceLocation("looting"), 2, EnumEnchantmentType.WEAPON);
      efficiency = new EnchantmentDigging(32, new ResourceLocation("efficiency"), 10);
      silkTouch = new EnchantmentUntouching(33, new ResourceLocation("silk_touch"), 1);
      unbreaking = new EnchantmentDurability(34, new ResourceLocation("unbreaking"), 5);
      fortune = new EnchantmentLootBonus(35, new ResourceLocation("fortune"), 2, EnumEnchantmentType.DIGGER);
      power = new EnchantmentArrowDamage(48, new ResourceLocation("power"), 10);
      punch = new EnchantmentArrowKnockback(49, new ResourceLocation("punch"), 2);
      flame = new EnchantmentArrowFire(50, new ResourceLocation("flame"), 2);
      infinity = new EnchantmentArrowInfinite(51, new ResourceLocation("infinity"), 1);
      luckOfTheSea = new EnchantmentLootBonus(61, new ResourceLocation("luck_of_the_sea"), 2, EnumEnchantmentType.FISHING_ROD);
      lure = new EnchantmentFishingSpeed(62, new ResourceLocation("lure"), 2, EnumEnchantmentType.FISHING_ROD);
      ArrayList var0 = Lists.newArrayList();
      Enchantment[] var1 = field_180311_a;
      int var2 = var1.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         Enchantment var4 = var1[var3];
         if (var4 != null) {
            var0.add(var4);
         }
      }

      enchantmentsList = (Enchantment[])var0.toArray(new Enchantment[var0.size()]);
   }

   public int getMaxLevel() {
      return 1;
   }
}
