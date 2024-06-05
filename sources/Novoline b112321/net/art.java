package net;

import com.mojang.authlib.GameProfile;
import java.util.Collection;
import java.util.Map;
import java.util.UUID;
import net.rF;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.IMerchant;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.scoreboard.Team;
import net.minecraft.stats.StatFileWriter;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.FoodStats;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MovementInput;
import net.minecraft.util.Vec3;
import net.minecraft.world.IInteractionObject;

public class art {
   private static String b;

   public static ItemStack U(EntityPlayerSP var0) {
      return var0.getHeldItem();
   }

   public static float b(EntityPlayerSP var0, Entity var1) {
      return var0.getDistanceToEntity(var1);
   }

   public static boolean e(EntityPlayerSP var0, Entity var1) {
      return var0.canEntityBeSeen(var1);
   }

   public static void f(EntityPlayerSP var0) {
      var0.swingItem();
   }

   public static void c(EntityPlayerSP var0, Entity var1) {
      var0.onEnchantmentCritical(var1);
   }

   public static void d(EntityPlayerSP var0, Entity var1) {
      var0.onCriticalHit(var1);
   }

   public static boolean E(EntityPlayerSP var0) {
      return var0.isMoving();
   }

   public static float w(EntityPlayerSP var0) {
      return var0.getHealth();
   }

   public static float S(EntityPlayerSP var0) {
      return var0.getEyeHeight();
   }

   public static Slot c(EntityPlayerSP var0, int var1) {
      return var0.getSlotFromPlayerContainer(var1);
   }

   public static double P(EntityPlayerSP var0) {
      return var0.getLastTickDistance();
   }

   public static void a(EntityPlayerSP var0, int var1, int var2) {
      var0.swap(var1, var2);
   }

   public static void b(EntityPlayerSP var0, int var1) {
      var0.drop(var1);
   }

   public static boolean k(EntityPlayerSP var0) {
      return var0.isUsingItem();
   }

   public static void b(EntityPlayerSP var0, IChatComponent var1) {
      var0.addChatComponentMessage(var1);
   }

   public static StatFileWriter C(EntityPlayerSP var0) {
      return var0.getStatFileWriter();
   }

   public static void a(EntityPlayerSP var0, double var1, double var3, double var5, float var7, float var8) {
      var0.setPositionAndRotation(var1, var3, var5, var7, var8);
   }

   public static GameProfile z(EntityPlayerSP var0) {
      return var0.getGameProfile();
   }

   public static UUID H(EntityPlayerSP var0) {
      return var0.getEntityUniqueID();
   }

   public static MovementInput K(EntityPlayerSP var0) {
      return var0.movementInput();
   }

   public static void D(EntityPlayerSP var0) {
      var0.closeScreen();
   }

   public static boolean G(EntityPlayerSP var0) {
      return var0.isEntityAlive();
   }

   public static boolean Z(EntityPlayerSP var0) {
      return var0.isEntityInsideOpaqueBlock();
   }

   public static boolean W(EntityPlayerSP var0) {
      return var0.isPlayerSleeping();
   }

   public static boolean ac(EntityPlayerSP var0) {
      return var0.isSpectator();
   }

   public static void p(EntityPlayerSP var0) {
      var0.sendHorseInventory();
   }

   public static void c(EntityPlayerSP var0, boolean var1) {
      var0.dropOneItem(var1);
   }

   public static void i(EntityPlayerSP var0) {
      var0.preparePlayerToSpawn();
   }

   public static void a(EntityPlayerSP var0, MovementInput var1) {
      var0.setMovementInput(var1);
   }

   public static int d(EntityPlayerSP var0) {
      return var0.getEntityID();
   }

   public static String o(EntityPlayerSP var0) {
      return var0.getClientBrand();
   }

   public static rF I(EntityPlayerSP var0) {
      return var0.k();
   }

   public static void a(EntityPlayerSP var0, String var1) {
      var0.setClientBrand(var1);
   }

   public static void d(EntityPlayerSP var0, int var1) {
      var0.setEntityId(var1);
   }

   public static boolean T(EntityPlayerSP var0) {
      return var0.hasReducedDebug();
   }

   public static void b(EntityPlayerSP var0, boolean var1) {
      var0.setReducedDebug(var1);
   }

   public static boolean b(EntityPlayerSP var0, Potion var1) {
      return var0.isPotionActive(var1);
   }

   public static PotionEffect c(EntityPlayerSP var0, Potion var1) {
      return var0.getActivePotionEffect(var1);
   }

   public static int a(EntityPlayerSP var0, Item var1) {
      return var0.getSlotByItem(var1);
   }

   public static double a(EntityPlayerSP var0, boolean var1, double var2) {
      return var0.a(var1, var2);
   }

   public static void a(EntityPlayerSP var0, boolean var1) {
      var0.setSprinting(var1);
   }

   public static double Y(EntityPlayerSP var0) {
      return var0.getBaseMotionY();
   }

   public static double u(EntityPlayerSP var0) {
      return var0.getBySprinting();
   }

   public static int a(EntityPlayerSP var0, Potion var1) {
      return var0.c(var1);
   }

   public static double d(EntityPlayerSP var0, double var1) {
      return var0.getBaseMotionY(var1);
   }

   public static boolean a(EntityPlayerSP var0, Block var1) {
      return var0.canHarvestBlock(var1);
   }

   public static boolean a(EntityPlayerSP var0, Material var1) {
      return var0.isInsideOfMaterial(var1);
   }

   public static float e(EntityPlayerSP var0) {
      return var0.getAbsorptionAmount();
   }

   public static boolean R(EntityPlayerSP var0) {
      return var0.N();
   }

   public static boolean M(EntityPlayerSP var0) {
      return var0.isInLiquid();
   }

   public static AxisAlignedBB O(EntityPlayerSP var0) {
      return var0.getEntityBoundingBox();
   }

   public static void a(EntityPlayerSP var0, BlockPos var1) {
      var0.updateTool(var1);
   }

   public static boolean e(EntityPlayerSP var0, int var1) {
      return var0.isPotionActive(var1);
   }

   public static void a(EntityPlayerSP var0, int var1) {
      var0.removePotionEffectClient(var1);
   }

   public static double a(EntityPlayerSP var0, Entity var1) {
      return var0.getDistance2D(var1);
   }

   public static double b(EntityPlayerSP var0, double var1, double var3) {
      return var0.getDistance(var1, var3);
   }

   public static void f(EntityPlayerSP var0, int var1) {
      var0.shiftClick(var1);
   }

   public static double e(EntityPlayerSP var0, boolean var1) {
      return var0.m(var1);
   }

   public static double a(EntityPlayerSP var0, double var1, double var3) {
      return var0.b(var1, var3);
   }

   public static boolean j(EntityPlayerSP var0) {
      return var0.isBlockUnder();
   }

   public static void a(EntityPlayerSP var0, float var1, float var2) {
      var0.setAngles(var1, var2);
   }

   public static float a(EntityPlayerSP var0, TileEntity var1) {
      return var0.a(var1);
   }

   public static EntityItem a(EntityPlayerSP var0, ItemStack var1, boolean var2) {
      return var0.dropPlayerItemWithRandomChoice(var1, var2);
   }

   public static void b(EntityPlayerSP var0, String var1) {
      var0.c(var1);
   }

   public static double t(EntityPlayerSP var0) {
      return var0.getBaseMoveSpeed();
   }

   public static void b(EntityPlayerSP var0) {
      var0.respawnPlayer();
   }

   public static String n(EntityPlayerSP var0) {
      return var0.getName();
   }

   public static ItemStack h(EntityPlayerSP var0) {
      return var0.getCurrentEquippedItem();
   }

   public static boolean m(EntityPlayerSP var0) {
      return var0.isBurning();
   }

   public static Map A(EntityPlayerSP var0) {
      return var0.getActivePotionsMap();
   }

   public static float e(EntityPlayerSP var0, float var1) {
      return var0.getSwingProgress(var1);
   }

   public static int g(EntityPlayerSP var0) {
      return var0.getItemInUseCount();
   }

   public static boolean ad(EntityPlayerSP var0) {
      return var0.isSneaking();
   }

   public static boolean L(EntityPlayerSP var0) {
      return var0.isInvisible();
   }

   public static float d(EntityPlayerSP var0, float var1) {
      return var0.getBrightness(var1);
   }

   public static float N(EntityPlayerSP var0) {
      return var0.getCollisionBorderSize();
   }

   public static Team Q(EntityPlayerSP var0) {
      return var0.getTeam();
   }

   public static boolean b(EntityPlayerSP var0, double var1) {
      return var0.isOnGround(var1);
   }

   public static boolean a(EntityPlayerSP var0, double var1) {
      return var0.c(var1);
   }

   public static double d(EntityPlayerSP var0, boolean var1) {
      return var0.l(var1);
   }

   public static float a(EntityPlayerSP var0, float var1) {
      return var0.o(var1);
   }

   public static void c(EntityPlayerSP var0, double var1, double var3, double var5) {
      var0.setPosition(var1, var3, var5);
   }

   public static void c(EntityPlayerSP var0, double var1) {
      var0.setSpeed(var1);
   }

   public static boolean l(EntityPlayerSP var0) {
      return var0.isInsideBlock();
   }

   public static boolean x(EntityPlayerSP var0) {
      return var0.isOnLadder();
   }

   public static void a(EntityPlayerSP var0, BlockPos var1, boolean var2) {
      var0.setSpawnPoint(var1, var2);
   }

   public static void b(EntityPlayerSP var0, float var1) {
      var0.setPlayerSPHealth(var1);
   }

   public static FoodStats af(EntityPlayerSP var0) {
      return var0.getFoodStats();
   }

   public static void a(EntityPlayerSP var0, float var1, int var2, int var3) {
      var0.setXPStats(var1, var2, var3);
   }

   public static void a(EntityPlayerSP var0, IInventory var1) {
      var0.displayGUIChest(var1);
   }

   public static void a(EntityPlayerSP var0, IMerchant var1) {
      var0.displayVillagerTradeGui(var1);
   }

   public static void a(EntityPlayerSP var0, EntityHorse var1, IInventory var2) {
      var0.displayGUIHorse(var1, var2);
   }

   public static void a(EntityPlayerSP var0, IInteractionObject var1) {
      var0.displayGui(var1);
   }

   public static void a(EntityPlayerSP var0, TileEntitySign var1) {
      var0.openEditSign(var1);
   }

   public static void a(EntityPlayerSP var0, IChatComponent var1) {
      var0.addChatMessage(var1);
   }

   public static void X(EntityPlayerSP var0) {
      var0.closeScreenAndDropStack();
   }

   public static double a(EntityPlayerSP var0, double var1, double var3, double var5) {
      return var0.getDistance(var1, var3, var5);
   }

   public static void b(EntityPlayerSP var0, double var1, double var3, double var5) {
      var0.setPositionAndUpdate(var1, var3, var5);
   }

   public static IChatComponent J(EntityPlayerSP var0) {
      return var0.getDisplayName();
   }

   public static void ab(EntityPlayerSP var0) {
      var0.resetLastTickDistance();
   }

   public static int aa(EntityPlayerSP var0) {
      return var0.getScore();
   }

   public static Collection c(EntityPlayerSP var0) {
      return var0.getActivePotionEffects();
   }

   public static Vec3 c(EntityPlayerSP var0, float var1) {
      return var0.getPositionEyes(var1);
   }

   public static UUID y(EntityPlayerSP var0) {
      return var0.getUniqueID();
   }

   public static boolean q(EntityPlayerSP var0) {
      return var0.isAllowEdit();
   }

   public static void s(EntityPlayerSP var0) {
      var0.destroyCurrentEquippedItem();
   }

   public static boolean ae(EntityPlayerSP var0) {
      return var0.isRiding();
   }

   public static int V(EntityPlayerSP var0) {
      return var0.getSleepTimer();
   }

   public static boolean B(EntityPlayerSP var0) {
      return var0.isRidingHorse();
   }

   public static float r(EntityPlayerSP var0) {
      return var0.getHorseJumpPower();
   }

   public static int a(EntityPlayerSP var0) {
      return var0.xpBarCap();
   }

   public static int F(EntityPlayerSP var0) {
      return var0.getAir();
   }

   public static boolean v(EntityPlayerSP var0) {
      return var0.isBlocking();
   }

   public static void b(String var0) {
      b = var0;
   }

   public static String b() {
      return b;
   }

   static {
      if(b() == null) {
         b("HxLYgc");
      }

   }
}
