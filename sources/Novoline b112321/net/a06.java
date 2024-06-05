package net;

import com.mojang.authlib.GameProfile;
import java.util.Random;
import java.util.UUID;
import net.rF;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.command.server.CommandBlockLogic;
import net.minecraft.entity.Entity;
import net.minecraft.entity.IMerchant;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayer$EnumStatus;
import net.minecraft.entity.player.EnumPlayerModelParts;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryEnderChest;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.scoreboard.Team;
import net.minecraft.stats.StatBase;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.FoodStats;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.Vec3;
import net.minecraft.world.IInteractionObject;
import net.minecraft.world.World;
import net.minecraft.world.WorldSettings$GameType;

public class a06 {
   private static String b;

   public static ItemStack p(EntityPlayer var0) {
      return var0.getCurrentEquippedItem();
   }

   public static boolean b(EntityPlayer var0, Block var1) {
      return var0.canHarvestBlock(var1);
   }

   public static float a(EntityPlayer var0, Block var1) {
      return var0.getToolDigEfficiency(var1);
   }

   public static void a(EntityPlayer var0, StatBase var1) {
      var0.triggerAchievement(var1);
   }

   public static void c(EntityPlayer var0, float var1) {
      var0.addExhaustion(var1);
   }

   public static void a(EntityPlayer var0, IMerchant var1) {
      var0.displayVillagerTradeGui(var1);
   }

   public static InventoryEnderChest L(EntityPlayer var0) {
      return var0.getInventoryEnderChest();
   }

   public static void a(EntityPlayer var0, IInventory var1) {
      var0.displayGUIChest(var1);
   }

   public static boolean a(EntityPlayer var0, BlockPos var1, EnumFacing var2, ItemStack var3) {
      return var0.a(var1, var2, var3);
   }

   public static ItemStack r(EntityPlayer var0) {
      return var0.getHeldItem();
   }

   public static void a(EntityPlayer var0, ItemStack var1, int var2) {
      var0.setItemInUse(var1, var2);
   }

   public static Random E(EntityPlayer var0) {
      return var0.getRNG();
   }

   public static EntityItem a(EntityPlayer var0, ItemStack var1, boolean var2) {
      return var0.dropPlayerItemWithRandomChoice(var1, var2);
   }

   public static String n(EntityPlayer var0) {
      return var0.getName();
   }

   public static boolean f(EntityPlayer var0) {
      return var0.isSpectator();
   }

   public static int B(EntityPlayer var0) {
      return var0.getItemInUseDuration();
   }

   public static ItemStack t(EntityPlayer var0) {
      return var0.getItemInUse();
   }

   public static int c(EntityPlayer var0) {
      return var0.getItemInUseCount();
   }

   public static void a(EntityPlayer var0, ItemStack var1) {
      var0.displayGUIBook(var1);
   }

   public static void o(EntityPlayer var0) {
      var0.swingItem();
   }

   public static void a(EntityPlayer var0, IChatComponent var1) {
      var0.addChatMessage(var1);
   }

   public static EntityPlayer$EnumStatus a(EntityPlayer var0, BlockPos var1) {
      return var0.trySleep(var1);
   }

   public static boolean z(EntityPlayer var0) {
      return var0.isPlayerSleeping();
   }

   public static UUID a(GameProfile var0) {
      return EntityPlayer.getUUID(var0);
   }

   public static void a(EntityPlayer var0, IInteractionObject var1) {
      var0.displayGui(var1);
   }

   public static boolean a(EntityPlayer var0, EntityPlayer var1) {
      return var0.canAttackPlayer(var1);
   }

   public static void a(EntityPlayer var0, Entity var1, int var2) {
      var0.onItemPickup(var1, var2);
   }

   public static boolean b(EntityPlayer var0) {
      return var0.isRiding();
   }

   public static int K(EntityPlayer var0) {
      return var0.getXPSeed();
   }

   public static void b(EntityPlayer var0, int var1) {
      var0.removeExperienceLevel(var1);
   }

   public static double a(EntityPlayer var0, double var1, double var3, double var5) {
      return var0.getDistanceSq(var1, var3, var5);
   }

   public static float e(EntityPlayer var0) {
      return var0.getArmorVisibility();
   }

   public static IChatComponent l(EntityPlayer var0) {
      return var0.getDisplayName();
   }

   public static boolean v(EntityPlayer var0) {
      return var0.isEntityAlive();
   }

   public static float g(EntityPlayer var0) {
      return var0.getEyeHeight();
   }

   public static void a(EntityPlayer var0, int var1) {
      var0.addExperienceLevel(var1);
   }

   public static int q(EntityPlayer var0) {
      return var0.getEntityID();
   }

   public static GameProfile i(EntityPlayer var0) {
      return var0.getGameProfile();
   }

   public static Team m(EntityPlayer var0) {
      return var0.getTeam();
   }

   public static void a(EntityPlayer var0, PotionEffect var1) {
      var0.addPotionEffect(var1);
   }

   public static void c(EntityPlayer var0, int var1) {
      var0.addExperience(var1);
   }

   public static void k(EntityPlayer var0) {
      var0.clearActivePotions();
   }

   public static boolean M(EntityPlayer var0) {
      return var0.isInvisible();
   }

   public static double H(EntityPlayer var0) {
      return var0.getLastTickDistance();
   }

   public static boolean d(EntityPlayer var0) {
      return var0.isUsingItem();
   }

   public static boolean w(EntityPlayer var0) {
      return var0.isSneaking();
   }

   public static EntityItem a(EntityPlayer var0, ItemStack var1, boolean var2, boolean var3) {
      return var0.a(var1, var2, var3);
   }

   public static double a(EntityPlayer var0, Entity var1) {
      return var0.getDistanceSqToEntity(var1);
   }

   public static void a(EntityPlayer var0, boolean var1, boolean var2, boolean var3) {
      var0.wakeUpPlayer(var1, var2, var3);
   }

   public static boolean O(EntityPlayer var0) {
      return var0.isPlayerFullyAsleep();
   }

   public static ItemStack d(EntityPlayer var0, int var1) {
      return var0.getCurrentArmor(var1);
   }

   public static void a(EntityPlayer var0, int var1, ItemStack var2) {
      var0.setCurrentItemOrArmor(var1, var2);
   }

   public static void a(EntityPlayer var0, WorldSettings$GameType var1) {
      var0.setGameType(var1);
   }

   public static FoodStats A(EntityPlayer var0) {
      return var0.getFoodStats();
   }

   public static boolean x(EntityPlayer var0) {
      return var0.shouldHeal();
   }

   public static void b(EntityPlayer var0, float var1) {
      var0.heal(var1);
   }

   public static float F(EntityPlayer var0) {
      return var0.getHealth();
   }

   public static boolean a(EntityPlayer var0, DamageSource var1, float var2) {
      return var0.attackEntityFrom(var1, var2);
   }

   public static void C(EntityPlayer var0) {
      var0.destroyCurrentEquippedItem();
   }

   public static void a(EntityPlayer var0, StatBase var1, int var2) {
      var0.addStat(var1, var2);
   }

   public static void a(EntityPlayer var0, TileEntitySign var1) {
      var0.openEditSign(var1);
   }

   public static boolean a(EntityPlayer var0, EnumPlayerModelParts var1) {
      return var0.isWearing(var1);
   }

   public static float D(EntityPlayer var0) {
      return var0.getAbsorptionAmount();
   }

   public static void a(EntityPlayer var0, double var1, double var3, double var5, float var7, float var8) {
      var0.setPositionAndRotation(var1, var3, var5, var7, var8);
   }

   public static AxisAlignedBB j(EntityPlayer var0) {
      return var0.getEntityBoundingBox();
   }

   public static float J(EntityPlayer var0) {
      return var0.getMaxHealth();
   }

   public static Vec3 a(EntityPlayer var0, float var1) {
      return var0.getLook(var1);
   }

   public static boolean h(EntityPlayer var0) {
      return var0.isSprinting();
   }

   public static float I(EntityPlayer var0) {
      return var0.getRotationYawHead();
   }

   public static boolean a(EntityPlayer var0, boolean var1) {
      return var0.canEat(var1);
   }

   public static boolean G(EntityPlayer var0) {
      return var0.isAllowEdit();
   }

   public static BlockPos a(World var0, BlockPos var1, boolean var2) {
      return EntityPlayer.getBedSpawnLocation(var0, var1, var2);
   }

   public static UUID N(EntityPlayer var0) {
      return var0.getUniqueID();
   }

   public static World s(EntityPlayer var0) {
      return var0.getEntityWorld();
   }

   public static void a(EntityPlayer var0, CommandBlockLogic var1) {
      var0.openEditCommandBlock(var1);
   }

   public static void a(EntityPlayer var0, EntityHorse var1, IInventory var2) {
      var0.displayGUIHorse(var1, var2);
   }

   public static rF y(EntityPlayer var0) {
      return var0.k();
   }

   public static float d(EntityPlayer var0, float var1) {
      return var0.getSwingProgress(var1);
   }

   public static void a(EntityPlayer var0) {
      var0.onKillCommand();
   }

   public static void c(EntityPlayer var0, Entity var1) {
      var0.attackTargetEntityWithCurrentItem(var1);
   }

   public static boolean b(EntityPlayer var0, Entity var1) {
      return var0.interactWith(var1);
   }

   public static void P(EntityPlayer var0) {
      var0.stopUsingItem();
   }

   public static void a(EntityPlayer var0, NBTTagCompound var1) {
      var0.writeToNBT(var1);
   }

   public static void b(EntityPlayer var0, NBTTagCompound var1) {
      var0.readFromNBT(var1);
   }

   public static IAttributeInstance a(EntityPlayer var0, IAttribute var1) {
      return var0.getEntityAttribute(var1);
   }

   public static int u(EntityPlayer var0) {
      return var0.getTotalArmorValue();
   }

   public static boolean a(EntityPlayer var0, Potion var1) {
      return var0.isPotionActive(var1);
   }

   public static boolean a(EntityPlayer var0, Material var1) {
      return var0.isInsideOfMaterial(var1);
   }

   public static void b(String var0) {
      b = var0;
   }

   public static String b() {
      return b;
   }

   static {
      if(b() == null) {
         b("O8bczb");
      }

   }
}
