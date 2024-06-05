package net;

import com.google.common.collect.Multimap;
import java.util.List;
import java.util.Random;
import net.acE;
import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.World;

public class ih {
   private static String b;

   public static ItemStack a(ItemStack var0, int var1) {
      return var0.splitStack(var1);
   }

   public static int s(ItemStack var0) {
      return var0.getMetadata();
   }

   public static void c(ItemStack var0, int var1) {
      var0.setItemDamage(var1);
   }

   public static boolean r(ItemStack var0) {
      return var0.hasTagCompound();
   }

   public static NBTTagCompound A(ItemStack var0) {
      return var0.getTagCompound();
   }

   public static boolean z(ItemStack var0) {
      return var0.hasDisplayName();
   }

   public static IChatComponent B(ItemStack var0) {
      return var0.getChatComponent();
   }

   public static Item o(ItemStack var0) {
      return var0.getItem();
   }

   public static ItemStack a(ItemStack var0, World var1, EntityPlayer var2) {
      return var0.useItemRightClick(var1, var2);
   }

   public static NBTTagCompound a(ItemStack var0, NBTTagCompound var1) {
      return var0.writeToNBT(var1);
   }

   public static ItemStack a(NBTTagCompound var0) {
      return ItemStack.loadItemStackFromNBT(var0);
   }

   public static void a(ItemStack var0, int var1, EntityLivingBase var2) {
      var0.damageItem(var1, var2);
   }

   public static ItemStack f(ItemStack var0) {
      return var0.copy();
   }

   public static NBTTagCompound a(ItemStack var0, String var1, boolean var2) {
      return var0.getSubCompound(var1, var2);
   }

   public static boolean e(ItemStack var0, ItemStack var1) {
      return var0.isItemEqual(var1);
   }

   public static int n(ItemStack var0) {
      return var0.getMaxItemUseDuration();
   }

   public static ItemStack q(ItemStack var0) {
      return ItemStack.d(var0);
   }

   public static boolean d(ItemStack var0, ItemStack var1) {
      return ItemStack.areItemStacksEqual(var0, var1);
   }

   public static void a(ItemStack var0, String var1, NBTBase var2) {
      var0.setTagInfo(var1, var2);
   }

   public static void a(ItemStack var0, Item var1) {
      var0.setItem(var1);
   }

   public static void b(ItemStack var0, NBTTagCompound var1) {
      var0.setTagCompound(var1);
   }

   public static boolean b(ItemStack var0) {
      return var0.hasEffect();
   }

   public static boolean g(ItemStack var0) {
      return var0.isItemDamaged();
   }

   public static int u(ItemStack var0) {
      return var0.getItemDamage();
   }

   public static int C(ItemStack var0) {
      return var0.getMaxDamage();
   }

   public static int j(ItemStack var0) {
      return var0.getMaxStackSize();
   }

   public static boolean a(ItemStack var0, ItemStack var1) {
      return ItemStack.areItemStackTagsEqual(var0, var1);
   }

   public static boolean c(ItemStack var0) {
      return var0.getHasSubtypes();
   }

   public static boolean x(ItemStack var0) {
      return var0.isStackable();
   }

   public static NBTTagList i(ItemStack var0) {
      return var0.getEnchantmentTagList();
   }

   public static String e(ItemStack var0) {
      return var0.getDisplayName();
   }

   public static boolean y(ItemStack var0) {
      return var0.isItemEnchantable();
   }

   public static void a(ItemStack var0, Enchantment var1, int var2) {
      var0.addEnchantment(var1, var2);
   }

   public static int p(ItemStack var0) {
      return var0.getRepairCost();
   }

   public static boolean d(ItemStack var0) {
      return var0.isItemStackDamageable();
   }

   public static void k(ItemStack var0) {
      var0.clearCustomName();
   }

   public static ItemStack a(ItemStack var0, String var1) {
      return var0.setStackDisplayName(var1);
   }

   public static void b(ItemStack var0, int var1) {
      var0.setRepairCost(var1);
   }

   public static Multimap m(ItemStack var0) {
      return var0.getAttributeModifiers();
   }

   public static EnumAction t(ItemStack var0) {
      return var0.getItemUseAction();
   }

   public static boolean c(ItemStack var0, ItemStack var1) {
      return ItemStack.areItemsEqual(var0, var1);
   }

   public static String l(ItemStack var0) {
      return var0.getUnlocalizedName();
   }

   public static boolean c(ItemStack var0, Block var1) {
      return var0.canDestroy(var1);
   }

   public static boolean a(ItemStack var0, Block var1) {
      return var0.canPlaceOn(var1);
   }

   public static void a(ItemStack var0, World var1, EntityPlayer var2, int var3) {
      var0.onCrafting(var1, var2, var3);
   }

   public static void b(ItemStack var0, World var1, EntityPlayer var2, int var3) {
      var0.onPlayerStoppedUsing(var1, var2, var3);
   }

   public static ItemStack b(ItemStack var0, World var1, EntityPlayer var2) {
      return var0.onItemUseFinish(var1, var2);
   }

   public static boolean a(ItemStack var0, EntityPlayer var1, EntityLivingBase var2) {
      return var0.interactWithEntity(var1, var2);
   }

   public static void a(ItemStack var0, EntityLivingBase var1, EntityPlayer var2) {
      var0.hitEntity(var1, var2);
   }

   public static boolean a(ItemStack var0) {
      return var0.canEditBlocks();
   }

   public static List a(ItemStack var0, EntityPlayer var1, boolean var2) {
      return var0.getTooltip(var1, var2);
   }

   public static EnumRarity v(ItemStack var0) {
      return var0.getRarity();
   }

   public static void a(ItemStack var0, World var1, Block var2, BlockPos var3, EntityPlayer var4) {
      var0.onBlockDestroyed(var1, var2, var3, var4);
   }

   public static boolean a(ItemStack var0, EntityPlayer var1, World var2, BlockPos var3, EnumFacing var4, float var5, float var6, float var7) {
      String var8 = b();
      boolean var10000 = var0.onItemUse(var1, var2, var3, var4, var5, var6, var7);
      if(acE.b() == null) {
         b("b7sxu");
      }

      return var10000;
   }

   public static void a(ItemStack var0, World var1, Entity var2, int var3, boolean var4) {
      var0.updateAnimation(var1, var2, var3, var4);
   }

   public static boolean d(ItemStack var0, Block var1) {
      return var0.canHarvestBlock(var1);
   }

   public static boolean D(ItemStack var0) {
      return var0.isItemEnchanted();
   }

   public static boolean b(ItemStack var0, ItemStack var1) {
      return var0.getIsItemStackEqual(var1);
   }

   public static boolean a(ItemStack var0, int var1, Random var2) {
      return var0.attemptDamageItem(var1, var2);
   }

   public static void a(ItemStack var0, EntityItemFrame var1) {
      var0.setItemFrame(var1);
   }

   public static boolean h(ItemStack var0) {
      return var0.isOnItemFrame();
   }

   public static EntityItemFrame w(ItemStack var0) {
      return var0.getItemFrame();
   }

   public static float b(ItemStack var0, Block var1) {
      return var0.getStrVsBlock(var1);
   }

   public static void b(String var0) {
      b = var0;
   }

   public static String b() {
      return b;
   }

   static {
      if(b() == null) {
         b("CyKTC");
      }

   }
}
