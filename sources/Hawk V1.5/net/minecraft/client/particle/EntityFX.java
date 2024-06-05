package net.minecraft.client.particle;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityFX extends Entity {
   protected float particleAlpha;
   public static double interpPosX;
   protected float particleGreen;
   protected float particleRed;
   protected TextureAtlasSprite particleIcon;
   protected int particleAge;
   public static double interpPosZ;
   public static double interpPosY;
   protected float particleScale;
   protected int particleTextureIndexX;
   protected int particleMaxAge;
   protected float particleTextureJitterX;
   protected float particleGravity;
   private static final String __OBFID = "CL_00000914";
   protected float particleBlue;
   protected int particleTextureIndexY;
   protected float particleTextureJitterY;

   public float getRedColorF() {
      return this.particleRed;
   }

   public String toString() {
      return String.valueOf((new StringBuilder(String.valueOf(this.getClass().getSimpleName()))).append(", Pos (").append(this.posX).append(",").append(this.posY).append(",").append(this.posZ).append("), RGBA (").append(this.particleRed).append(",").append(this.particleGreen).append(",").append(this.particleBlue).append(",").append(this.particleAlpha).append("), Age ").append(this.particleAge));
   }

   public void func_180434_a(WorldRenderer var1, Entity var2, float var3, float var4, float var5, float var6, float var7, float var8) {
      float var9 = (float)this.particleTextureIndexX / 16.0F;
      float var10 = var9 + 0.0624375F;
      float var11 = (float)this.particleTextureIndexY / 16.0F;
      float var12 = var11 + 0.0624375F;
      float var13 = 0.1F * this.particleScale;
      if (this.particleIcon != null) {
         var9 = this.particleIcon.getMinU();
         var10 = this.particleIcon.getMaxU();
         var11 = this.particleIcon.getMinV();
         var12 = this.particleIcon.getMaxV();
      }

      float var14 = (float)(this.prevPosX + (this.posX - this.prevPosX) * (double)var3 - interpPosX);
      float var15 = (float)(this.prevPosY + (this.posY - this.prevPosY) * (double)var3 - interpPosY);
      float var16 = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * (double)var3 - interpPosZ);
      var1.func_178960_a(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha);
      var1.addVertexWithUV((double)(var14 - var4 * var13 - var7 * var13), (double)(var15 - var5 * var13), (double)(var16 - var6 * var13 - var8 * var13), (double)var10, (double)var12);
      var1.addVertexWithUV((double)(var14 - var4 * var13 + var7 * var13), (double)(var15 + var5 * var13), (double)(var16 - var6 * var13 + var8 * var13), (double)var10, (double)var11);
      var1.addVertexWithUV((double)(var14 + var4 * var13 + var7 * var13), (double)(var15 + var5 * var13), (double)(var16 + var6 * var13 + var8 * var13), (double)var9, (double)var11);
      var1.addVertexWithUV((double)(var14 + var4 * var13 - var7 * var13), (double)(var15 - var5 * var13), (double)(var16 + var6 * var13 - var8 * var13), (double)var9, (double)var12);
   }

   public int getFXLayer() {
      return 0;
   }

   public void setParticleTextureIndex(int var1) {
      if (this.getFXLayer() != 0) {
         throw new RuntimeException("Invalid call to Particle.setMiscTex");
      } else {
         this.particleTextureIndexX = var1 % 16;
         this.particleTextureIndexY = var1 / 16;
      }
   }

   public EntityFX multiplyVelocity(float var1) {
      this.motionX *= (double)var1;
      this.motionY = (this.motionY - 0.10000000149011612D) * (double)var1 + 0.10000000149011612D;
      this.motionZ *= (double)var1;
      return this;
   }

   public void readEntityFromNBT(NBTTagCompound var1) {
   }

   public void setRBGColorF(float var1, float var2, float var3) {
      this.particleRed = var1;
      this.particleGreen = var2;
      this.particleBlue = var3;
   }

   public EntityFX multipleParticleScaleBy(float var1) {
      this.setSize(0.2F * var1, 0.2F * var1);
      this.particleScale *= var1;
      return this;
   }

   protected EntityFX(World var1, double var2, double var4, double var6) {
      super(var1);
      this.particleAlpha = 1.0F;
      this.setSize(0.2F, 0.2F);
      this.setPosition(var2, var4, var6);
      this.lastTickPosX = var2;
      this.lastTickPosY = var4;
      this.lastTickPosZ = var6;
      this.particleRed = this.particleGreen = this.particleBlue = 1.0F;
      this.particleTextureJitterX = this.rand.nextFloat() * 3.0F;
      this.particleTextureJitterY = this.rand.nextFloat() * 3.0F;
      this.particleScale = (this.rand.nextFloat() * 0.5F + 0.5F) * 2.0F;
      this.particleMaxAge = (int)(4.0F / (this.rand.nextFloat() * 0.9F + 0.1F));
      this.particleAge = 0;
   }

   public void setAlphaF(float var1) {
      if (this.particleAlpha == 1.0F && var1 < 1.0F) {
         Minecraft.getMinecraft().effectRenderer.func_178928_b(this);
      } else if (this.particleAlpha < 1.0F && var1 == 1.0F) {
         Minecraft.getMinecraft().effectRenderer.func_178931_c(this);
      }

      this.particleAlpha = var1;
   }

   protected void entityInit() {
   }

   public boolean canAttackWithItem() {
      return false;
   }

   public EntityFX(World var1, double var2, double var4, double var6, double var8, double var10, double var12) {
      this(var1, var2, var4, var6);
      this.motionX = var8 + (Math.random() * 2.0D - 1.0D) * 0.4000000059604645D;
      this.motionY = var10 + (Math.random() * 2.0D - 1.0D) * 0.4000000059604645D;
      this.motionZ = var12 + (Math.random() * 2.0D - 1.0D) * 0.4000000059604645D;
      float var14 = (float)(Math.random() + Math.random() + 1.0D) * 0.15F;
      float var15 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
      this.motionX = this.motionX / (double)var15 * (double)var14 * 0.4000000059604645D;
      this.motionY = this.motionY / (double)var15 * (double)var14 * 0.4000000059604645D + 0.10000000149011612D;
      this.motionZ = this.motionZ / (double)var15 * (double)var14 * 0.4000000059604645D;
   }

   public void writeEntityToNBT(NBTTagCompound var1) {
   }

   public float getBlueColorF() {
      return this.particleBlue;
   }

   public void nextTextureIndexX() {
      ++this.particleTextureIndexX;
   }

   protected boolean canTriggerWalking() {
      return false;
   }

   public float getGreenColorF() {
      return this.particleGreen;
   }

   public void onUpdate() {
      this.prevPosX = this.posX;
      this.prevPosY = this.posY;
      this.prevPosZ = this.posZ;
      if (this.particleAge++ >= this.particleMaxAge) {
         this.setDead();
      }

      this.motionY -= 0.04D * (double)this.particleGravity;
      this.moveEntity(this.motionX, this.motionY, this.motionZ);
      this.motionX *= 0.9800000190734863D;
      this.motionY *= 0.9800000190734863D;
      this.motionZ *= 0.9800000190734863D;
      if (this.onGround) {
         this.motionX *= 0.699999988079071D;
         this.motionZ *= 0.699999988079071D;
      }

   }

   public void func_180435_a(TextureAtlasSprite var1) {
      int var2 = this.getFXLayer();
      if (var2 == 1) {
         this.particleIcon = var1;
      } else {
         throw new RuntimeException("Invalid call to Particle.setTex, use coordinate methods");
      }
   }

   public float func_174838_j() {
      return this.particleAlpha;
   }
}
