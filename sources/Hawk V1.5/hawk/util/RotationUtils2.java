package hawk.util;

import java.util.Iterator;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.Container;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import org.lwjgl.input.Keyboard;

public class RotationUtils2 {
   public float hitDelay;
   public float blockDamage;
   public static Minecraft mc;

   public static float[] getRotationFromPosition(double var0, double var2, double var4) {
      Minecraft.getMinecraft();
      double var6 = var0 - mc.thePlayer.posX;
      Minecraft.getMinecraft();
      double var8 = var2 - mc.thePlayer.posZ;
      Minecraft.getMinecraft();
      double var10 = var4 - mc.thePlayer.posY - 0.6D;
      double var12 = (double)MathHelper.sqrt_double(var6 * var6 + var8 * var8);
      float var14 = (float)(Math.atan2(var8, var6) * 180.0D / 3.141592653589793D) - 90.0F;
      float var15 = (float)(-(Math.atan2(var10, var12) * 180.0D / 3.141592653589793D));
      return new float[]{var14, var15};
   }

   public float getDistanceToEntity(TileEntity var1) {
      float var2 = (float)(mc.thePlayer.posX - (double)var1.getPos().getX());
      float var3 = (float)(mc.thePlayer.posY - (double)var1.getPos().getY());
      float var4 = (float)(mc.thePlayer.posZ - (double)var1.getPos().getZ());
      return MathHelper.sqrt_float(var2 * var2 + var3 * var3 + var4 * var4);
   }

   public boolean isVisibleFOV(TileEntity var1, EntityPlayerSP var2, int var3) {
      return (Math.abs(this.getRotationsTileEntity(var1)[0] - var2.rotationYaw) % 360.0F > 180.0F ? 360.0F - Math.abs(this.getRotationsTileEntity(var1)[0] - var2.rotationYaw) % 360.0F : Math.abs(this.getRotationsTileEntity(var1)[0] - var2.rotationYaw) % 360.0F) <= (float)var3;
   }

   public void breakBlock(double var1, double var3, double var5) {
      mc.thePlayer.swingItem();
      mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.START_DESTROY_BLOCK, this.getBlockPos(var1, var3, var5), this.getEnumFacing((float)((int)var1), (float)((int)var3), (float)((int)var5))));
      mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK, this.getBlockPos(var1, var3, var5), this.getEnumFacing((float)((int)var1), (float)((int)var3), (float)((int)var5))));
   }

   public boolean canBlockBeSeen(int var1, int var2, int var3) {
      Vec3 var4 = new Vec3(mc.thePlayer.posX, mc.thePlayer.posY + (double)mc.thePlayer.getEyeHeight(), mc.thePlayer.posZ);
      Vec3 var5 = new Vec3((double)((float)var1 + 0.5F), (double)((float)var2 + 0.5F), (double)((float)var3 + 0.5F));
      return mc.theWorld.rayTraceBlocks(var4, var5) != null ? mc.theWorld.rayTraceBlocks(var4, var5).field_178784_b != null : null;
   }

   public TileEntityChest getBestEntity(double var1) {
      TileEntityChest var3 = null;
      double var4 = var1;
      Iterator var7 = mc.theWorld.loadedEntityList.iterator();

      while(var7.hasNext()) {
         Object var6 = var7.next();
         TileEntityChest var8 = (TileEntityChest)var6;
         if (this.getDistanceToEntity(var8) <= 6.0F) {
            double var9 = (double)this.getDistanceToEntity(var8);
            if (!(var9 > var4)) {
               var4 = var9;
               var3 = var8;
            }
         }
      }

      return var3;
   }

   public EnumFacing getEnumFacing(float var1, float var2, float var3) {
      return EnumFacing.func_176737_a(var1, var2, var3);
   }

   public float getDistanceToVec(double var1, double var3, double var5, double var7, double var9, double var11) {
      float var13 = (float)(var1 - var7);
      float var14 = (float)(var3 - var9);
      float var15 = (float)(var5 - var11);
      return MathHelper.sqrt_float(var13 * var13 + var14 * var14 + var15 * var15);
   }

   public void moveRight() {
      mc.gameSettings.keyBindRight.pressed = true;
   }

   public static float getNewAngle(float var0) {
      var0 %= 360.0F;
      if (var0 >= 180.0F) {
         var0 -= 360.0F;
      }

      if (var0 < -180.0F) {
         var0 += 360.0F;
      }

      return var0;
   }

   public void placeBlock(int var1, int var2, int var3) {
      mc.thePlayer.sendQueue.addToSendQueue(new C08PacketPlayerBlockPlacement(this.getBlockPos((double)var1, (double)var2, (double)var3), this.getEnumFacing((float)var1, (float)var2, (float)var3).getIndex(), mc.thePlayer.getHeldItem(), 0.0F, 0.0F, 0.0F));
   }

   public BlockPos getBlockPos(double var1, double var3, double var5) {
      BlockPos var7 = new BlockPos(var1, var3, var5);
      return var7;
   }

   public Block getBlock(double var1, double var3, double var5) {
      var1 = (double)MathHelper.floor_double(var1);
      var3 = (double)MathHelper.floor_double(var3);
      var5 = (double)MathHelper.floor_double(var5);
      return mc.theWorld.getChunkFromBlockCoords(new BlockPos(var1, var3, var5)).getBlock(new BlockPos(var1, var3, var5));
   }

   public void movePlayerToBlock(double var1, double var3, double var5, float var7) {
      faceBlock(var1 + 0.5D, var3 + 0.5D, var5 + 0.5D);
      this.moveForward();
      if (mc.thePlayer.onGround && mc.thePlayer.isCollidedHorizontally && !Keyboard.isKeyDown(mc.gameSettings.keyBindJump.getKeyCode()) && !mc.thePlayer.isInWater()) {
         mc.thePlayer.jump();
      }

      if (this.canReach(var1, var3, var5, var7)) {
         this.stopMoving();
      }

   }

   public static float[] getAverageRotations(List<EntityLivingBase> var0) {
      double var1 = 0.0D;
      double var3 = 0.0D;
      double var5 = 0.0D;

      Entity var7;
      for(Iterator var8 = var0.iterator(); var8.hasNext(); var5 += var7.posZ) {
         var7 = (Entity)var8.next();
         var1 += var7.posX;
         var3 += var7.boundingBox.maxY - 2.0D;
      }

      var1 /= (double)var0.size();
      var3 /= (double)var0.size();
      var5 /= (double)var0.size();
      return new float[]{getRotationFromPosition(var1, var5, var3)[0], getRotationFromPosition(var1, var5, var3)[1]};
   }

   public static float[] faceBlock(double var0, double var2, double var4) {
      double var6 = var0 - mc.thePlayer.posX;
      double var8 = var4 - mc.thePlayer.posZ;
      double var10 = var2 - mc.thePlayer.posY + (double)mc.thePlayer.getEyeHeight();
      double var12 = (double)MathHelper.sqrt_double(var6 * var6 + var8 * var8);
      float var14 = (float)(Math.atan2(var8, var6) * 180.0D / 3.141592653589793D) - 90.0F;
      float var15 = (float)(-(Math.atan2(var10, var12) * 180.0D / 3.141592653589793D));
      float var16 = mc.thePlayer.rotationYaw;
      float var17 = mc.thePlayer.rotationPitch;
      return new float[]{var16 + MathHelper.wrapAngleTo180_float(var14 - var16), var17 + MathHelper.wrapAngleTo180_float(var15 - var17)};
   }

   public void moveBack() {
      mc.gameSettings.keyBindBack.pressed = true;
   }

   public static float getTrajAngleSolutionLow(float var0, float var1, float var2) {
      float var3 = 0.006F;
      float var4 = var2 * var2 * var2 * var2 - 0.006F * (0.006F * var0 * var0 + 2.0F * var1 * var2 * var2);
      return (float)Math.toDegrees(Math.atan(((double)(var2 * var2) - Math.sqrt((double)var4)) / (double)(0.006F * var0)));
   }

   public boolean canReach(double var1, double var3, double var5, float var7) {
      double var8 = (double)this.getDistance(var1, var3, var5);
      return var8 < (double)var7 && var8 > (double)(-var7);
   }

   public boolean isContainerEmpty(Container var1) {
      int var2 = 0;

      for(int var3 = var1.inventorySlots.size() == 90 ? 54 : 27; var2 < var3; ++var2) {
         if (var1.getSlot(var2).getHasStack()) {
            return false;
         }
      }

      return true;
   }

   public void breakBlockLegit(int var1, int var2, int var3, int var4) {
      ++this.hitDelay;
      mc.thePlayer.swingItem();
      if (this.blockDamage == 0.0F) {
         mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.START_DESTROY_BLOCK, this.getBlockPos((double)var1, (double)var2, (double)var3), this.getEnumFacing((float)var1, (float)var2, (float)var3)));
      }

      if (this.hitDelay >= (float)var4) {
         this.blockDamage += this.getBlock((double)var1, (double)var2, (double)var3).getPlayerRelativeBlockHardness(mc.thePlayer, mc.theWorld, new BlockPos(var1, var2, var3));
         mc.theWorld.sendBlockBreakProgress(mc.thePlayer.getEntityId(), new BlockPos(var1, var2, var3), (int)(this.blockDamage * 10.0F) - 1);
         if (this.blockDamage >= (mc.playerController.isInCreativeMode() ? 0.0F : 1.0F)) {
            mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK, this.getBlockPos((double)var1, (double)var2, (double)var3), this.getEnumFacing((float)var1, (float)var2, (float)var3)));
            mc.playerController.func_178888_a(this.getBlockPos((double)var1, (double)var2, (double)var3), this.getEnumFacing((float)var1, (float)var2, (float)var3));
            this.blockDamage = 0.0F;
            this.hitDelay = 0.0F;
         }
      }

   }

   private Float[] getRotationsTileEntity(TileEntity var1) {
      double var2 = (double)var1.getPos().getX() - mc.thePlayer.posX;
      double var4 = (double)var1.getPos().getY() - mc.thePlayer.posZ;
      double var6 = (double)(var1.getPos().getZ() + 1) - mc.thePlayer.posY + (double)mc.thePlayer.getEyeHeight();
      double var8 = (double)MathHelper.sqrt_double(var2 * var2 + var4 * var4);
      float var10 = (float)Math.toDegrees(-Math.atan(var2 / var4));
      float var11 = (float)(-Math.toDegrees(Math.atan(var6 / var8)));
      if (var4 < 0.0D && var2 < 0.0D) {
         var10 = (float)(90.0D + Math.toDegrees(Math.atan(var4 / var2)));
      } else if (var4 < 0.0D && var2 > 0.0D) {
         var10 = (float)(-90.0D + Math.toDegrees(Math.atan(var4 / var2)));
      }

      return new Float[]{var10, var11};
   }

   public float getDistance(double var1, double var3, double var5) {
      float var7 = (float)(mc.thePlayer.posX - var1);
      float var8 = (float)(mc.thePlayer.posY - var3);
      float var9 = (float)(mc.thePlayer.posZ - var5);
      return MathHelper.sqrt_float(var7 * var7 + var8 * var8 + var9 * var9);
   }

   public boolean shouldHitBlock(int var1, int var2, int var3, double var4) {
      Block var6 = this.getBlock((double)var1, (double)var2, (double)var3);
      boolean var7 = !(var6 instanceof BlockLiquid);
      boolean var8 = this.canBlockBeSeen(var1, var2, var3);
      return var7 && var8;
   }

   public static float getDistanceBetweenAngles(float var0, float var1) {
      float var2 = Math.abs(var0 - var1) % 360.0F;
      if (var2 > 180.0F) {
         var2 = 360.0F - var2;
      }

      return var2;
   }

   public int getNextSlotInContainer(Container var1) {
      int var2 = 0;

      for(int var3 = var1.inventorySlots.size() == 90 ? 54 : 27; var2 < var3; ++var2) {
         if (var1.getInventory().get(var2) != null) {
            return var2;
         }
      }

      return -1;
   }

   public void moveForward() {
      mc.gameSettings.keyBindForward.pressed = true;
   }

   public void stopMoving() {
      mc.gameSettings.keyBindForward.pressed = false;
      mc.gameSettings.keyBindLeft.pressed = false;
      mc.gameSettings.keyBindRight.pressed = false;
      mc.gameSettings.keyBindBack.pressed = false;
   }

   public void moveLeft() {
      mc.gameSettings.keyBindLeft.pressed = true;
   }

   public static float[] getRotations(EntityLivingBase var0) {
      double var1 = var0.posX;
      double var3 = var0.posZ;
      double var5 = var0.posY + (double)(var0.getEyeHeight() / 2.0F) - 0.5D;
      return getRotationFromPosition(var1, var3, var5);
   }
}
