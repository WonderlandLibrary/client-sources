package net.minecraft.enchantment;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import net.minecraft.enchantment.EnchantmentArrowDamage;
import net.minecraft.enchantment.EnchantmentArrowFire;
import net.minecraft.enchantment.EnchantmentArrowInfinite;
import net.minecraft.enchantment.EnchantmentArrowKnockback;
import net.minecraft.enchantment.EnchantmentDamage;
import net.minecraft.enchantment.EnchantmentDigging;
import net.minecraft.enchantment.EnchantmentDurability;
import net.minecraft.enchantment.EnchantmentFireAspect;
import net.minecraft.enchantment.EnchantmentFishingSpeed;
import net.minecraft.enchantment.EnchantmentKnockback;
import net.minecraft.enchantment.EnchantmentLootBonus;
import net.minecraft.enchantment.EnchantmentOxygen;
import net.minecraft.enchantment.EnchantmentProtection;
import net.minecraft.enchantment.EnchantmentThorns;
import net.minecraft.enchantment.EnchantmentUntouching;
import net.minecraft.enchantment.EnchantmentWaterWalker;
import net.minecraft.enchantment.EnchantmentWaterWorker;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

public abstract class Enchantment {
   private static final Enchantment[] enchantmentsList = new Enchantment[256];
   public static final Enchantment[] enchantmentsBookList;
   private static final Map locationEnchantments = Maps.newHashMap();
   public static final Enchantment protection = new EnchantmentProtection(0, new ResourceLocation("protection"), 10, 0);
   public static final Enchantment fireProtection = new EnchantmentProtection(1, new ResourceLocation("fire_protection"), 5, 1);
   public static final Enchantment featherFalling = new EnchantmentProtection(2, new ResourceLocation("feather_falling"), 5, 2);
   public static final Enchantment blastProtection = new EnchantmentProtection(3, new ResourceLocation("blast_protection"), 2, 3);
   public static final Enchantment projectileProtection = new EnchantmentProtection(4, new ResourceLocation("projectile_protection"), 5, 4);
   public static final Enchantment respiration = new EnchantmentOxygen(5, new ResourceLocation("respiration"), 2);
   public static final Enchantment aquaAffinity = new EnchantmentWaterWorker(6, new ResourceLocation("aqua_affinity"), 2);
   public static final Enchantment thorns = new EnchantmentThorns(7, new ResourceLocation("thorns"), 1);
   public static final Enchantment depthStrider = new EnchantmentWaterWalker(8, new ResourceLocation("depth_strider"), 2);
   public static final Enchantment sharpness = new EnchantmentDamage(16, new ResourceLocation("sharpness"), 10, 0);
   public static final Enchantment smite = new EnchantmentDamage(17, new ResourceLocation("smite"), 5, 1);
   public static final Enchantment baneOfArthropods = new EnchantmentDamage(18, new ResourceLocation("bane_of_arthropods"), 5, 2);
   public static final Enchantment knockback = new EnchantmentKnockback(19, new ResourceLocation("knockback"), 5);
   public static final Enchantment fireAspect = new EnchantmentFireAspect(20, new ResourceLocation("fire_aspect"), 2);
   public static final Enchantment looting = new EnchantmentLootBonus(21, new ResourceLocation("looting"), 2, EnumEnchantmentType.WEAPON);
   public static final Enchantment efficiency = new EnchantmentDigging(32, new ResourceLocation("efficiency"), 10);
   public static final Enchantment silkTouch = new EnchantmentUntouching(33, new ResourceLocation("silk_touch"), 1);
   public static final Enchantment unbreaking = new EnchantmentDurability(34, new ResourceLocation("unbreaking"), 5);
   public static final Enchantment fortune = new EnchantmentLootBonus(35, new ResourceLocation("fortune"), 2, EnumEnchantmentType.DIGGER);
   public static final Enchantment power = new EnchantmentArrowDamage(48, new ResourceLocation("power"), 10);
   public static final Enchantment punch = new EnchantmentArrowKnockback(49, new ResourceLocation("punch"), 2);
   public static final Enchantment flame = new EnchantmentArrowFire(50, new ResourceLocation("flame"), 2);
   public static final Enchantment infinity = new EnchantmentArrowInfinite(51, new ResourceLocation("infinity"), 1);
   public static final Enchantment luckOfTheSea = new EnchantmentLootBonus(61, new ResourceLocation("luck_of_the_sea"), 2, EnumEnchantmentType.FISHING_ROD);
   public static final Enchantment lure = new EnchantmentFishingSpeed(62, new ResourceLocation("lure"), 2, EnumEnchantmentType.FISHING_ROD);
   public final int effectId;
   private final int weight;
   public EnumEnchantmentType type;
   protected String name;

   public static Enchantment getEnchantmentById(int var0) {
      return var0 < enchantmentsList.length?enchantmentsList[var0]:null;
   }

   protected Enchantment(int var1, ResourceLocation var2, int var3, EnumEnchantmentType var4) {
      this.effectId = var1;
      this.weight = var3;
      this.type = var4;
      if(enchantmentsList[var1] != null) {
         throw new IllegalArgumentException("Duplicate enchantment id!");
      } else {
         enchantmentsList[var1] = this;
         locationEnchantments.put(var2, this);
      }
   }

   public static Enchantment getEnchantmentByLocation(String var0) {
      return (Enchantment)locationEnchantments.get(new ResourceLocation(var0));
   }

   public static Set func_181077_c() {
      return locationEnchantments.keySet();
   }

   public int getWeight() {
      return this.weight;
   }

   public int getMinLevel() {
      return 1;
   }

   public int getMaxLevel() {
      return 1;
   }

   public int getMinEnchantability(int var1) {
      return 1 + var1 * 10;
   }

   public int getMaxEnchantability(int var1) {
      return this.getMinEnchantability(var1) + 5;
   }

   public int calcModifierDamage(int var1, DamageSource var2) {
      return 0;
   }

   public float calcDamageByCreature(int var1, EnumCreatureAttribute var2) {
      return 0.0F;
   }

   public boolean canApplyTogether(Enchantment var1) {
      return this != var1;
   }

   public Enchantment setName(String var1) {
      this.name = var1;
      return this;
   }

   public String getName() {
      return "enchantment." + this.name;
   }

   public String getTranslatedName(int var1) {
      String var2 = StatCollector.translateToLocal(this.getName());
      return var2 + " " + StatCollector.translateToLocal("enchantment.level." + var1);
   }

   public boolean canApply(ItemStack var1) {
      return this.type.canEnchantItem(var1.getItem());
   }

   public void onEntityDamaged(EntityLivingBase var1, Entity var2, int var3) {
   }

   public void onUserHurt(EntityLivingBase var1, Entity var2, int var3) {
   }

   static {
      ArrayList var7 = Lists.newArrayList();

      for(Enchantment var11 : enchantmentsList) {
         var7.add(var11);
      }

      enchantmentsBookList = (Enchantment[])((Enchantment[])var7.toArray(new Enchantment[var7.size()]));
   }

   private static IllegalArgumentException a(IllegalArgumentException var0) {
      return var0;
   }
}
