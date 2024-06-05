package net;

import com.google.common.collect.Multimap;
import java.util.List;
import net.ih;
import net.minecraft.block.Block;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class adM {
   private static String[] b;

   public static Item a(Block var0) {
      return Item.getItemFromBlock(var0);
   }

   public static boolean d(Item var0) {
      return var0.getHasSubtypes();
   }

   public static int c(Item var0) {
      return Item.b(var0);
   }

   public static String e(Item var0) {
      return var0.getUnlocalizedName();
   }

   public static boolean h(Item var0) {
      return var0.hasContainerItem();
   }

   public static Item k(Item var0) {
      return var0.getContainerItem();
   }

   public static int a(Item var0, ItemStack var1, int var2) {
      return var0.getColorFromItemStack(var1, var2);
   }

   public static int b(Item var0) {
      return var0.getItemStackLimit();
   }

   public static int f(Item var0) {
      return var0.getMaxDamage();
   }

   public static int b(Item var0, ItemStack var1) {
      return var0.getMaxItemUseDuration(var1);
   }

   public static ModelBiped a(Item var0, EntityLivingBase var1, ItemStack var2, int var3) {
      return var0.a(var1, var2, var3);
   }

   public static boolean a(Item var0, ItemStack var1, ItemStack var2) {
      return var0.getIsRepairable(var1, var2);
   }

   public static boolean i(Item var0) {
      return var0.isDamageable();
   }

   public static boolean a(Item var0) {
      return var0.getShareTag();
   }

   public static Item a(int var0) {
      return Item.getItemById(var0);
   }

   public static Item a(String var0) {
      return Item.getByNameOrId(var0);
   }

   public static EnumAction c(Item var0, ItemStack var1) {
      return var0.getItemUseAction(var1);
   }

   public static boolean a(Item var0, ItemStack var1) {
      return var0.isPotionIngredient(var1);
   }

   public static String g(Item var0, ItemStack var1) {
      return var0.getPotionEffect(var1);
   }

   public static CreativeTabs l(Item var0) {
      return var0.getCreativeTab();
   }

   public static void a(Item var0, Item var1, CreativeTabs var2, List var3) {
      var0.getSubItems(var1, var2, var3);
   }

   public static String h(Item var0, ItemStack var1) {
      return var0.getItemStackDisplayName(var1);
   }

   public static boolean a(Item var0, ItemStack var1, EntityPlayer var2, World var3, BlockPos var4, EnumFacing var5, float var6, float var7, float var8) {
      String var9 = ih.b();
      return var0.onItemUse(var1, var2, var3, var4, var5, var6, var7, var8);
   }

   public static float a(Item var0, ItemStack var1, Block var2) {
      return var0.getStrVsBlock(var1, var2);
   }

   public static ItemStack a(Item var0, ItemStack var1, World var2, EntityPlayer var3) {
      return var0.onItemRightClick(var1, var2, var3);
   }

   public static ItemStack c(Item var0, ItemStack var1, World var2, EntityPlayer var3) {
      return var0.onItemUseFinish(var1, var2, var3);
   }

   public static void a(Item var0, NBTTagCompound var1) {
      var0.updateItemStackNBT(var1);
   }

   public static boolean a(Item var0, ItemStack var1, EntityLivingBase var2, EntityLivingBase var3) {
      return var0.hitEntity(var1, var2, var3);
   }

   public static boolean a(Item var0, ItemStack var1, World var2, Block var3, BlockPos var4, EntityLivingBase var5) {
      return var0.onBlockDestroyed(var1, var2, var3, var4, var5);
   }

   public static boolean a(Item var0, ItemStack var1, EntityPlayer var2, EntityLivingBase var3) {
      return var0.itemInteractionForEntity(var1, var2, var3);
   }

   public static String e(Item var0, ItemStack var1) {
      return var0.getUnlocalizedName(var1);
   }

   public static void a(Item var0, ItemStack var1, World var2, Entity var3, int var4, boolean var5) {
      var0.onUpdate(var1, var2, var3, var4, var5);
   }

   public static void b(Item var0, ItemStack var1, World var2, EntityPlayer var3) {
      var0.onCreated(var1, var2, var3);
   }

   public static void a(Item var0, ItemStack var1, World var2, EntityPlayer var3, int var4) {
      var0.onPlayerStoppedUsing(var1, var2, var3, var4);
   }

   public static void a(Item var0, ItemStack var1, EntityPlayer var2, List var3, boolean var4) {
      var0.addInformation(var1, var2, var3, var4);
   }

   public static boolean i(Item var0, ItemStack var1) {
      return var0.hasEffect(var1);
   }

   public static EnumRarity d(Item var0, ItemStack var1) {
      return var0.getRarity(var1);
   }

   public static boolean f(Item var0, ItemStack var1) {
      return var0.isItemTool(var1);
   }

   public static boolean j(Item var0) {
      return var0.canItemEditBlocks();
   }

   public static Multimap p(Item var0) {
      return var0.getItemAttributeModifiers();
   }

   public static void c() {
      Item.registerItems();
   }

   public static boolean o(Item var0) {
      return var0.isFull3D();
   }

   public static boolean g(Item var0) {
      return var0.shouldRotateAroundWhenRendering();
   }

   public static int n(Item var0) {
      return var0.getItemEnchantability();
   }

   public static boolean m(Item var0) {
      return var0.isMap();
   }

   public static void b(String[] var0) {
      b = var0;
   }

   public static String[] b() {
      return b;
   }

   static {
      if(b() != null) {
         b(new String[3]);
      }

   }
}
