package cc.novoline.modules.visual;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.BlockPos;

public class DMGParticle$Location {
   private double x;
   private double y;
   private double z;
   private float yaw;
   private float pitch;

   public DMGParticle$Location(double var1, double var3, double var5, float var7, float var8) {
      this.x = var1;
      this.y = var3;
      this.z = var5;
      this.yaw = var7;
      this.pitch = var8;
   }

   public DMGParticle$Location(double var1, double var3, double var5) {
      this.x = var1;
      this.y = var3;
      this.z = var5;
      this.yaw = 0.0F;
      this.pitch = 0.0F;
   }

   public DMGParticle$Location(BlockPos var1) {
      this.x = (double)var1.getX();
      this.y = (double)var1.getY();
      this.z = (double)var1.getZ();
      this.yaw = 0.0F;
      this.pitch = 0.0F;
   }

   public DMGParticle$Location(int var1, int var2, int var3) {
      this.x = (double)var1;
      this.y = (double)var2;
      this.z = (double)var3;
      this.yaw = 0.0F;
      this.pitch = 0.0F;
   }

   public DMGParticle$Location(EntityLivingBase var1) {
      this.x = var1.posX;
      this.y = var1.posY;
      this.z = var1.posZ;
      this.yaw = 0.0F;
      this.pitch = 0.0F;
   }

   public DMGParticle$Location b(int var1, int var2, int var3) {
      this.x += (double)var1;
      this.y += (double)var2;
      this.z += (double)var3;
      return this;
   }

   public DMGParticle$Location a(double var1, double var3, double var5) {
      this.x += var1;
      this.y += var3;
      this.z += var5;
      return this;
   }

   public DMGParticle$Location a(int var1, int var2, int var3) {
      this.x -= (double)var1;
      this.y -= (double)var2;
      this.z -= (double)var3;
      return this;
   }

   public DMGParticle$Location b(double var1, double var3, double var5) {
      this.x -= var1;
      this.y -= var3;
      this.z -= var5;
      return this;
   }

   public Block getBlock() {
      return Minecraft.getInstance().world.getBlockState(this.toBlockPos()).getBlock();
   }

   public double getX() {
      return this.x;
   }

   public DMGParticle$Location setX(double var1) {
      this.x = var1;
      return this;
   }

   public double getY() {
      return this.y;
   }

   public DMGParticle$Location setY(double var1) {
      this.y = var1;
      return this;
   }

   public double getZ() {
      return this.z;
   }

   public DMGParticle$Location setZ(double var1) {
      this.z = var1;
      return this;
   }

   public float getYaw() {
      return this.yaw;
   }

   public DMGParticle$Location setYaw(float var1) {
      this.yaw = var1;
      return this;
   }

   public float getPitch() {
      return this.pitch;
   }

   public DMGParticle$Location setPitch(float var1) {
      this.pitch = var1;
      return this;
   }

   public static DMGParticle$Location fromBlockPos(BlockPos var0) {
      return new DMGParticle$Location(var0.getX(), var0.getY(), var0.getZ());
   }

   public BlockPos toBlockPos() {
      return new BlockPos(this.getX(), this.getY(), this.getZ());
   }

   public double distanceTo(DMGParticle$Location var1) {
      double var2 = var1.x - this.x;
      double var4 = var1.z - this.z;
      double var6 = var1.y - this.y;
      return Math.sqrt(var2 * var2 + var6 * var6 + var4 * var4);
   }

   public double distanceToY(DMGParticle$Location var1) {
      double var2 = var1.y - this.y;
      return Math.sqrt(var2 * var2);
   }
}
