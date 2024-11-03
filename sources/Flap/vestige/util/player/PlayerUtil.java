package vestige.util.player;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemAnvilBlock;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemFishingRod;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;
import org.lwjgl.input.Mouse;
import vestige.Flap;
import vestige.event.impl.MoveEvent;
import vestige.module.impl.combat.Autoclicker;
import vestige.util.IMinecraft;
import vestige.util.network.PacketUtil;

public class PlayerUtil implements IMinecraft {
   public static void ncpDamage() {
      for(int i = 0; i < 49; ++i) {
         PacketUtil.sendPacketNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 0.0625D, mc.thePlayer.posZ, false));
         PacketUtil.sendPacketNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, false));
      }

      PacketUtil.sendPacketNoEvent(new C03PacketPlayer(true));
   }

   public static boolean inFov(float fov, @NotNull BlockPos blockPos) {
      if (blockPos == null) {
         $$$reportNull$$$0(0);
      }

      return inFov(fov, (double)blockPos.getX(), (double)blockPos.getZ());
   }

   public static boolean inFov(float fov, @NotNull Entity entity) {
      if (entity == null) {
         $$$reportNull$$$0(1);
      }

      return inFov(fov, entity.posX, entity.posZ);
   }

   public static boolean inFov(float fov, @NotNull Entity self, @NotNull Entity target) {
      if (self == null) {
         $$$reportNull$$$0(2);
      }

      if (target == null) {
         $$$reportNull$$$0(3);
      }

      return inFov(self.rotationYaw, fov, target.posX, target.posZ);
   }

   public static boolean inFov(float fov, double n2, double n3) {
      fov *= 0.5F;
      double fovToPoint = getFov(n2, n3);
      if (fovToPoint > 0.0D) {
         return fovToPoint < (double)fov;
      } else {
         return fovToPoint > (double)(-fov);
      }
   }

   public static boolean inFov(float yaw, float fov, double n2, double n3) {
      fov *= 0.5F;
      double fovToPoint = getFov(yaw, n2, n3);
      if (fovToPoint > 0.0D) {
         return fovToPoint < (double)fov;
      } else {
         return fovToPoint > (double)(-fov);
      }
   }

   @Range(
      from = -180L,
      to = 180L
   )
   public static double getFov(double posX, double posZ) {
      return getFov(mc.thePlayer.rotationYaw, posX, posZ);
   }

   @Range(
      from = -180L,
      to = 180L
   )
   public static double getFov(float yaw, double posX, double posZ) {
      return MathHelper.wrapAngleTo180_double((double)((yaw - RotationsUtil.angle(posX, posZ)) % 360.0F));
   }

   public static boolean isOnSameTeam(EntityPlayer entity) {
      if (entity.getTeam() != null && mc.thePlayer.getTeam() != null) {
         return entity.getDisplayName().getFormattedText().charAt(1) == mc.thePlayer.getDisplayName().getFormattedText().charAt(1);
      } else {
         return false;
      }
   }

   public static boolean holdingWeapon() {
      ItemStack item = mc.thePlayer.getHeldItem();
      if (item == null) {
         return false;
      } else {
         Item getItem = item.getItem();
         return getItem instanceof ItemSword || getItem instanceof ItemAxe || getItem instanceof ItemFishingRod || getItem == Items.stick;
      }
   }

   public static boolean isLeftClicking() {
      if (((Autoclicker)Flap.instance.getModuleManager().getModule(Autoclicker.class)).isEnabled()) {
         return Mouse.isButtonDown(0);
      } else {
         return CPSCalculator.f() > 1 && System.currentTimeMillis() - CPSCalculator.LL < 300L;
      }
   }

   public static boolean nullCheck() {
      return mc.thePlayer != null && mc.theWorld != null;
   }

   public static double predictedMotion(double motion, int ticks) {
      if (ticks == 0) {
         return motion;
      } else {
         double predicted = motion;

         for(int i = 0; i < ticks; ++i) {
            predicted = (predicted - 0.08D) * 0.9800000190734863D;
         }

         return predicted;
      }
   }

   public static boolean isBlockBlacklisted(Item item) {
      return item instanceof ItemAnvilBlock || item.getUnlocalizedName().contains("sand") || item.getUnlocalizedName().contains("gravel") || item.getUnlocalizedName().contains("ladder") || item.getUnlocalizedName().contains("tnt") || item.getUnlocalizedName().contains("chest") || item.getUnlocalizedName().contains("web");
   }

   public static boolean isOverAir(double x, double y, double z) {
      return isAir(new BlockPos(x, y - 1.0D, z));
   }

   public static float getSpeed() {
      return (float)Math.hypot(mc.thePlayer.motionX, mc.thePlayer.motionZ);
   }

   public static double getSpeed(MoveEvent event) {
      return Math.hypot(event.getX(), event.getZ());
   }

   public static void resetMotion() {
      mc.thePlayer.motionX = mc.thePlayer.motionZ = 0.0D;
   }

   public static void resetMotion(boolean stopY) {
      mc.thePlayer.motionX = mc.thePlayer.motionZ = 0.0D;
      if (stopY) {
         mc.thePlayer.motionY = 0.0D;
      }

   }

   public static double getHorizontalSpeed() {
      return getHorizontalSpeed(mc.thePlayer);
   }

   public static double getHorizontalSpeed(Entity entity) {
      return Math.sqrt(entity.motionX * entity.motionX + entity.motionZ * entity.motionZ);
   }

   public static float getBindsDirection(float rotationYaw) {
      int moveForward = 0;
      if (GameSettings.isKeyDown(mc.gameSettings.keyBindForward)) {
         ++moveForward;
      }

      if (GameSettings.isKeyDown(mc.gameSettings.keyBindBack)) {
         --moveForward;
      }

      int moveStrafing = 0;
      if (GameSettings.isKeyDown(mc.gameSettings.keyBindRight)) {
         ++moveStrafing;
      }

      if (GameSettings.isKeyDown(mc.gameSettings.keyBindLeft)) {
         --moveStrafing;
      }

      boolean reversed = moveForward < 0;
      double strafingYaw = 90.0D * (moveForward > 0 ? 0.5D : (reversed ? -0.5D : 1.0D));
      if (reversed) {
         rotationYaw += 180.0F;
      }

      if (moveStrafing > 0) {
         rotationYaw = (float)((double)rotationYaw + strafingYaw);
      } else if (moveStrafing < 0) {
         rotationYaw = (float)((double)rotationYaw - strafingYaw);
      }

      return rotationYaw;
   }

   public static boolean isOverAir() {
      return isOverAir(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ);
   }

   public static boolean isAir(BlockPos blockPos) {
      Material material = getMaterial(blockPos);
      return material == Material.air;
   }

   public static Material getMaterial(BlockPos blockPos) {
      Block block = getBlock(blockPos);
      return block != null ? block.getMaterial() : null;
   }

   public Block getBlock(Vec3 vec3) {
      return getBlock(new BlockPos(vec3));
   }

   public static Block getBlock(BlockPos blockPos) {
      return mc.theWorld != null && blockPos != null ? mc.theWorld.getBlockState(blockPos).getBlock() : null;
   }

   // $FF: synthetic method
   private static void $$$reportNull$$$0(int var0) {
      Object[] var10001 = new Object[3];
      switch(var0) {
      case 0:
      default:
         var10001[0] = "blockPos";
         break;
      case 1:
         var10001[0] = "entity";
         break;
      case 2:
         var10001[0] = "self";
         break;
      case 3:
         var10001[0] = "target";
      }

      var10001[1] = "vestige/util/player/PlayerUtil";
      var10001[2] = "inFov";
      throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", var10001));
   }
}
