package net;

import com.mojang.authlib.GameProfile;
import java.util.Collection;
import java.util.List;
import java.util.UUID;
import net.acE;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayer$EnumChatVisibility;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.client.C15PacketClientSettings;
import net.minecraft.scoreboard.Team;
import net.minecraft.stats.StatBase;
import net.minecraft.stats.StatisticsFile;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class oy {
   private static acE[] b;

   public static String t(EntityPlayerMP var0) {
      return var0.getPlayerIP();
   }

   public static String e(EntityPlayerMP var0) {
      return var0.getName();
   }

   public static long n(EntityPlayerMP var0) {
      return var0.getLastActiveTime();
   }

   public static WorldServer u(EntityPlayerMP var0) {
      return var0.getServerForPlayer();
   }

   public static void a(EntityPlayerMP var0, float var1, float var2, boolean var3, boolean var4) {
      var0.setEntityActionState(var1, var2, var3, var4);
   }

   public static void c(EntityPlayerMP var0) {
      var0.onUpdateEntity();
   }

   public static void b(EntityPlayerMP var0, double var1, double var3, double var5, float var7, float var8) {
      var0.setPositionAndRotation(var1, var3, var5, var7, var8);
   }

   public static boolean d(EntityPlayerMP var0) {
      return var0.isPlayerSleeping();
   }

   public static AxisAlignedBB f(EntityPlayerMP var0) {
      return var0.getEntityBoundingBox();
   }

   public static void A(EntityPlayerMP var0) {
      var0.jump();
   }

   public static void e(EntityPlayerMP var0, double var1, double var3, double var5) {
      var0.moveEntity(var1, var3, var5);
   }

   public static void b(EntityPlayerMP var0, double var1, double var3, double var5) {
      var0.addMovementStat(var1, var3, var5);
   }

   public static void a(EntityPlayerMP var0, double var1, boolean var3) {
      var0.handleFalling(var1, var3);
   }

   public static void g(EntityPlayerMP var0) {
      var0.markPlayerActive();
   }

   public static boolean b(EntityPlayerMP var0) {
      return var0.isSpectator();
   }

   public static void c(EntityPlayerMP var0, boolean var1) {
      var0.dropOneItem(var1);
   }

   public static void s(EntityPlayerMP var0) {
      var0.stopUsingItem();
   }

   public static double d(EntityPlayerMP var0, double var1, double var3, double var5) {
      return var0.getDistanceSq(var1, var3, var5);
   }

   public static void d(EntityPlayerMP var0, Entity var1) {
      var0.setSpectatingEntity(var1);
   }

   public static void b(EntityPlayerMP var0, Entity var1) {
      var0.mountEntity(var1);
   }

   public static void a(EntityPlayerMP var0, double var1, double var3, double var5, float var7, float var8) {
      var0.setLocationAndAngles(var1, var3, var5, var7, var8);
   }

   public static boolean y(EntityPlayerMP var0) {
      return var0.isEntityAlive();
   }

   public static void a(EntityPlayerMP var0, World var1) {
      var0.setWorld(var1);
   }

   public static void f(EntityPlayerMP var0, double var1, double var3, double var5) {
      var0.setPositionAndUpdate(var1, var3, var5);
   }

   public static IChatComponent q(EntityPlayerMP var0) {
      return var0.getDisplayName();
   }

   public static void i(EntityPlayerMP var0) {
      var0.mountEntityAndWakeUp();
   }

   public static EntityPlayer$EnumChatVisibility x(EntityPlayerMP var0) {
      return var0.getChatVisibility();
   }

   public static GameProfile w(EntityPlayerMP var0) {
      return var0.getGameProfile();
   }

   public static void J(EntityPlayerMP var0) {
      var0.swingItem();
   }

   public static void a(EntityPlayerMP var0, boolean var1) {
      var0.setSneaking(var1);
   }

   public static void b(EntityPlayerMP var0, boolean var1) {
      var0.setSprinting(var1);
   }

   public static void a(EntityPlayerMP var0, boolean var1, boolean var2, boolean var3) {
      var0.wakeUpPlayer(var1, var2, var3);
   }

   public static boolean c(EntityPlayerMP var0, Entity var1) {
      return var0.canEntityBeSeen(var1);
   }

   public static double h(EntityPlayerMP var0, Entity var1) {
      return var0.getDistanceSqToEntity(var1);
   }

   public static boolean f(EntityPlayerMP var0, Entity var1) {
      return var0.interactWith(var1);
   }

   public static void g(EntityPlayerMP var0, Entity var1) {
      var0.attackTargetEntityWithCurrentItem(var1);
   }

   public static float C(EntityPlayerMP var0) {
      return var0.getHealth();
   }

   public static StatisticsFile D(EntityPlayerMP var0) {
      return var0.getStatFile();
   }

   public static void a(EntityPlayerMP var0, StatBase var1) {
      var0.triggerAchievement(var1);
   }

   public static void E(EntityPlayerMP var0) {
      var0.closeContainer();
   }

   public static void a(EntityPlayerMP var0, Container var1, List var2) {
      var0.updateCraftingInventory(var1, var2);
   }

   public static void l(EntityPlayerMP var0) {
      var0.updateHeldItem();
   }

   public static EntityItem a(EntityPlayerMP var0, ItemStack var1, boolean var2) {
      return var0.dropPlayerItemWithRandomChoice(var1, var2);
   }

   public static void a(EntityPlayerMP var0, C15PacketClientSettings var1) {
      var0.handleClientSettings(var1);
   }

   public static void a(EntityPlayerMP var0, IChatComponent var1) {
      var0.addChatMessage(var1);
   }

   public static boolean a(EntityPlayerMP var0, int var1, String var2) {
      return var0.canCommandSenderUseCommand(var1, var2);
   }

   public static int p(EntityPlayerMP var0) {
      return var0.getEntityID();
   }

   public static void b(EntityPlayerMP var0, StatBase var1) {
      var0.func_175145_a(var1);
   }

   public static BlockPos r(EntityPlayerMP var0) {
      return var0.getPosition();
   }

   public static void a(EntityPlayerMP var0, BlockPos var1, boolean var2) {
      var0.setSpawnPoint(var1, var2);
   }

   public static void a(EntityPlayerMP var0, NBTTagCompound var1) {
      var0.writeToNBT(var1);
   }

   public static void v(EntityPlayerMP var0) {
      var0.sendPlayerAbilities();
   }

   public static boolean k(EntityPlayerMP var0) {
      return var0.isAllowEdit();
   }

   public static ItemStack a(EntityPlayerMP var0) {
      return var0.getCurrentEquippedItem();
   }

   public static ItemStack j(EntityPlayerMP var0) {
      return var0.getHeldItem();
   }

   public static boolean a(EntityPlayerMP var0, Block var1) {
      return var0.canHarvestBlock(var1);
   }

   public static void F(EntityPlayerMP var0) {
      var0.destroyCurrentEquippedItem();
   }

   public static void a(EntityPlayerMP var0, Container var1) {
      var0.sendContainerToPlayer(var1);
   }

   public static void a(EntityPlayerMP var0, Entity var1) {
      var0.removeEntity(var1);
   }

   public static void a(EntityPlayerMP var0, String var1, String var2) {
      var0.loadResourcePack(var1, var2);
   }

   public static Collection h(EntityPlayerMP var0) {
      return var0.getActivePotionEffects();
   }

   public static void m(EntityPlayerMP var0) {
      var0.addSelfToInternalCraftingInventory();
   }

   public static void b(EntityPlayerMP var0, NBTTagCompound var1) {
      var0.readFromNBT(var1);
   }

   public static UUID H(EntityPlayerMP var0) {
      return var0.getUniqueID();
   }

   public static BlockPos z(EntityPlayerMP var0) {
      return var0.getBedLocation();
   }

   public static boolean B(EntityPlayerMP var0) {
      return var0.isSpawnForced();
   }

   public static void a(EntityPlayerMP var0, EntityPlayer var1, boolean var2) {
      var0.clonePlayer(var1, var2);
   }

   public static void a(EntityPlayerMP var0, int var1) {
      var0.setEntityId(var1);
   }

   public static void e(EntityPlayerMP var0, Entity var1) {
      var0.func_174817_o(var1);
   }

   public static void c(EntityPlayerMP var0, double var1, double var3, double var5) {
      var0.setPosition(var1, var3, var5);
   }

   public static void a(EntityPlayerMP var0, float var1) {
      var0.setHealth(var1);
   }

   public static Team I(EntityPlayerMP var0) {
      return var0.getTeam();
   }

   public static void G(EntityPlayerMP var0) {
      var0.setPlayerHealthUpdated();
   }

   public static double a(EntityPlayerMP var0, double var1, double var3, double var5) {
      return var0.getDistance(var1, var3, var5);
   }

   public static IChatComponent o(EntityPlayerMP var0) {
      return var0.getTabListDisplayName();
   }

   public static void b(acE[] var0) {
      b = var0;
   }

   public static acE[] b() {
      return b;
   }

   static {
      if(b() == null) {
         b(new acE[2]);
      }

   }
}
