package net.minecraft.client.particle;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityFX extends Entity {
   protected int particleTextureIndexX;
   protected int particleTextureIndexY;
   protected float particleTextureJitterX;
   protected float particleTextureJitterY;
   protected int particleAge;
   protected int particleMaxAge;
   protected float particleScale;
   protected float particleGravity;
   protected float particleRed;
   protected float particleGreen;
   protected float particleBlue;
   protected float particleAlpha;
   protected TextureAtlasSprite particleIcon;
   public static double interpPosX;
   public static double interpPosY;
   public static double interpPosZ;

   protected EntityFX(World var1, double var2, double var4, double var6) {
      super(var1);
      this.particleAlpha = 1.0F;
      this.setSize(0.2F, 0.2F);
      this.setPosition(var2, var4, var6);
      this.lastTickPosX = this.prevPosX = var2;
      this.lastTickPosY = this.prevPosY = var4;
      this.lastTickPosZ = this.prevPosZ = var6;
      this.particleRed = this.particleGreen = this.particleBlue = 1.0F;
      this.particleTextureJitterX = this.rand.nextFloat() * 3.0F;
      this.particleTextureJitterY = this.rand.nextFloat() * 3.0F;
      this.particleScale = (this.rand.nextFloat() * 0.5F + 0.5F) * 2.0F;
      this.particleMaxAge = (int)(4.0F / (this.rand.nextFloat() * 0.9F + 0.1F));
      this.particleAge = 0;
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

   public EntityFX multiplyVelocity(float var1) {
      this.motionX *= (double)var1;
      this.motionY = (this.motionY - 0.10000000149011612D) * (double)var1 + 0.10000000149011612D;
      this.motionZ *= (double)var1;
      return this;
   }

   public EntityFX multipleParticleScaleBy(float var1) {
      this.setSize(0.2F * var1, 0.2F * var1);
      this.particleScale *= var1;
      return this;
   }

   public void setRBGColorF(float var1, float var2, float var3) {
      this.particleRed = var1;
      this.particleGreen = var2;
      this.particleBlue = var3;
   }

   public void setAlphaF(float var1) {
      if(this.particleAlpha == 1.0F && var1 < 1.0F) {
         Minecraft.getInstance().effectRenderer.moveToAlphaLayer(this);
      } else if(this.particleAlpha < 1.0F && var1 == 1.0F) {
         Minecraft.getInstance().effectRenderer.moveToNoAlphaLayer(this);
      }

      this.particleAlpha = var1;
   }

   public float getRedColorF() {
      return this.particleRed;
   }

   public float getGreenColorF() {
      return this.particleGreen;
   }

   public float getBlueColorF() {
      return this.particleBlue;
   }

   public float getAlpha() {
      return this.particleAlpha;
   }

   protected boolean canTriggerWalking() {
      return false;
   }

   protected void entityInit() {
   }

   public void onUpdate() {
      this.prevPosX = this.posX;
      this.prevPosY = this.posY;
      this.prevPosZ = this.posZ;
      if(this.particleAge++ >= this.particleMaxAge) {
         this.setDead();
      }

      this.motionY -= 0.04D * (double)this.particleGravity;
      this.moveEntity(this.motionX, this.motionY, this.motionZ);
      this.motionX *= 0.9800000190734863D;
      this.motionY *= 0.9800000190734863D;
      this.motionZ *= 0.9800000190734863D;
      if(this.onGround) {
         this.motionX *= 0.699999988079071D;
         this.motionZ *= 0.699999988079071D;
      }

   }

   public void renderParticle(WorldRenderer var1, Entity var2, float var3, float var4, float var5, float var6, float var7, float var8) {
      float var9 = (float)this.particleTextureIndexX / 16.0F;
      float var10 = var9 + 0.0624375F;
      float var11 = (float)this.particleTextureIndexY / 16.0F;
      float var12 = var11 + 0.0624375F;
      float var13 = 0.1F * this.particleScale;
      if(this.particleIcon != null) {
         var9 = this.particleIcon.getMinU();
         var10 = this.particleIcon.getMaxU();
         var11 = this.particleIcon.getMinV();
         var12 = this.particleIcon.getMaxV();
      }

      float var14 = (float)(this.prevPosX + (this.posX - this.prevPosX) * (double)var3 - interpPosX);
      float var15 = (float)(this.prevPosY + (this.posY - this.prevPosY) * (double)var3 - interpPosY);
      float var16 = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * (double)var3 - interpPosZ);
      int var17 = this.getBrightnessForRender(var3);
      int var18 = var17 >> 16 & '\uffff';
      int var19 = var17 & '\uffff';
      var1.pos((double)(var14 - var4 * var13 - var7 * var13), (double)(var15 - var5 * var13), (double)(var16 - var6 * var13 - var8 * var13)).tex((double)var10, (double)var12).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).lightmap(var18, var19).endVertex();
      var1.pos((double)(var14 - var4 * var13 + var7 * var13), (double)(var15 + var5 * var13), (double)(var16 - var6 * var13 + var8 * var13)).tex((double)var10, (double)var11).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).lightmap(var18, var19).endVertex();
      var1.pos((double)(var14 + var4 * var13 + var7 * var13), (double)(var15 + var5 * var13), (double)(var16 + var6 * var13 + var8 * var13)).tex((double)var9, (double)var11).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).lightmap(var18, var19).endVertex();
      var1.pos((double)(var14 + var4 * var13 - var7 * var13), (double)(var15 - var5 * var13), (double)(var16 + var6 * var13 - var8 * var13)).tex((double)var9, (double)var12).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).lightmap(var18, var19).endVertex();
   }

   public int getFXLayer() {
      return 0;
   }

   public void writeEntityToNBT(NBTTagCompound var1) {
   }

   public void readEntityFromNBT(NBTTagCompound var1) {
   }

   public void setParticleIcon(TextureAtlasSprite var1) {
      int var2 = this.getFXLayer();
      if(var2 == 1) {
         this.particleIcon = var1;
      } else {
         throw new RuntimeException("Invalid call to Particle.setTex, use coordinate methods");
      }
   }

   public void setParticleTextureIndex(int var1) {
      if(this.getFXLayer() != 0) {
         throw new RuntimeException("Invalid call to Particle.setMiscTex");
      } else {
         this.particleTextureIndexX = var1 % 16;
         this.particleTextureIndexY = var1 / 16;
      }
   }

   public void nextTextureIndexX() {
      ++this.particleTextureIndexX;
   }

   public boolean canAttackWithItem() {
      return false;
   }

   public String toString() {
      return this.getClass().getCanonicalName() + ", Pos (" + this.posX + "," + this.posY + "," + this.posZ + "), RGBA (" + this.particleRed + "," + this.particleGreen + "," + this.particleBlue + "," + this.particleAlpha + "), Age " + this.particleAge;
   }

   private static RuntimeException a(RuntimeException var0) {
      return var0;
   }
}
