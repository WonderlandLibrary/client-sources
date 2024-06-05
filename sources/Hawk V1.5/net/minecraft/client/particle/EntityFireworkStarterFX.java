package net.minecraft.client.particle;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemDye;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityFireworkStarterFX extends EntityFX {
   private final EffectRenderer theEffectRenderer;
   boolean twinkle;
   private static final String __OBFID = "CL_00000906";
   private NBTTagList fireworkExplosions;
   private int fireworkAge;

   private void createParticle(double var1, double var3, double var5, double var7, double var9, double var11, int[] var13, int[] var14, boolean var15, boolean var16) {
      EntityFireworkSparkFX var17 = new EntityFireworkSparkFX(this.worldObj, var1, var3, var5, var7, var9, var11, this.theEffectRenderer);
      var17.setAlphaF(0.99F);
      var17.setTrail(var15);
      var17.setTwinkle(var16);
      int var18 = this.rand.nextInt(var13.length);
      var17.setColour(var13[var18]);
      if (var14 != null && var14.length > 0) {
         var17.setFadeColour(var14[this.rand.nextInt(var14.length)]);
      }

      this.theEffectRenderer.addEffect(var17);
   }

   private boolean func_92037_i() {
      Minecraft var1 = Minecraft.getMinecraft();
      return var1 == null || var1.func_175606_aa() == null || var1.func_175606_aa().getDistanceSq(this.posX, this.posY, this.posZ) >= 256.0D;
   }

   private void createBall(double var1, int var3, int[] var4, int[] var5, boolean var6, boolean var7) {
      double var8 = this.posX;
      double var10 = this.posY;
      double var12 = this.posZ;

      for(int var14 = -var3; var14 <= var3; ++var14) {
         for(int var15 = -var3; var15 <= var3; ++var15) {
            for(int var16 = -var3; var16 <= var3; ++var16) {
               double var17 = (double)var15 + (this.rand.nextDouble() - this.rand.nextDouble()) * 0.5D;
               double var19 = (double)var14 + (this.rand.nextDouble() - this.rand.nextDouble()) * 0.5D;
               double var21 = (double)var16 + (this.rand.nextDouble() - this.rand.nextDouble()) * 0.5D;
               double var23 = (double)MathHelper.sqrt_double(var17 * var17 + var19 * var19 + var21 * var21) / var1 + this.rand.nextGaussian() * 0.05D;
               this.createParticle(var8, var10, var12, var17 / var23, var19 / var23, var21 / var23, var4, var5, var6, var7);
               if (var14 != -var3 && var14 != var3 && var15 != -var3 && var15 != var3) {
                  var16 += var3 * 2 - 1;
               }
            }
         }
      }

   }

   private void createShaped(double var1, double[][] var3, int[] var4, int[] var5, boolean var6, boolean var7, boolean var8) {
      double var9 = var3[0][0];
      double var11 = var3[0][1];
      this.createParticle(this.posX, this.posY, this.posZ, var9 * var1, var11 * var1, 0.0D, var4, var5, var6, var7);
      float var13 = this.rand.nextFloat() * 3.1415927F;
      double var14 = var8 ? 0.034D : 0.34D;

      for(int var16 = 0; var16 < 3; ++var16) {
         double var17 = (double)var13 + (double)((float)var16 * 3.1415927F) * var14;
         double var19 = var9;
         double var21 = var11;

         for(int var23 = 1; var23 < var3.length; ++var23) {
            double var24 = var3[var23][0];
            double var26 = var3[var23][1];

            for(double var28 = 0.25D; var28 <= 1.0D; var28 += 0.25D) {
               double var30 = (var19 + (var24 - var19) * var28) * var1;
               double var32 = (var21 + (var26 - var21) * var28) * var1;
               double var34 = var30 * Math.sin(var17);
               var30 *= Math.cos(var17);

               for(double var36 = -1.0D; var36 <= 1.0D; var36 += 2.0D) {
                  this.createParticle(this.posX, this.posY, this.posZ, var30 * var36, var32, var34 * var36, var4, var5, var6, var7);
               }
            }

            var19 = var24;
            var21 = var26;
         }
      }

   }

   public void onUpdate() {
      boolean var1;
      if (this.fireworkAge == 0 && this.fireworkExplosions != null) {
         var1 = this.func_92037_i();
         boolean var2 = false;
         if (this.fireworkExplosions.tagCount() >= 3) {
            var2 = true;
         } else {
            for(int var3 = 0; var3 < this.fireworkExplosions.tagCount(); ++var3) {
               NBTTagCompound var4 = this.fireworkExplosions.getCompoundTagAt(var3);
               if (var4.getByte("Type") == 1) {
                  var2 = true;
                  break;
               }
            }
         }

         String var15 = String.valueOf((new StringBuilder("fireworks.")).append(var2 ? "largeBlast" : "blast").append(var1 ? "_far" : ""));
         this.worldObj.playSound(this.posX, this.posY, this.posZ, var15, 20.0F, 0.95F + this.rand.nextFloat() * 0.1F, true);
      }

      if (this.fireworkAge % 2 == 0 && this.fireworkExplosions != null && this.fireworkAge / 2 < this.fireworkExplosions.tagCount()) {
         int var14 = this.fireworkAge / 2;
         NBTTagCompound var17 = this.fireworkExplosions.getCompoundTagAt(var14);
         byte var18 = var17.getByte("Type");
         boolean var5 = var17.getBoolean("Trail");
         boolean var6 = var17.getBoolean("Flicker");
         int[] var7 = var17.getIntArray("Colors");
         int[] var8 = var17.getIntArray("FadeColors");
         if (var7.length == 0) {
            var7 = new int[]{ItemDye.dyeColors[0]};
         }

         if (var18 == 1) {
            this.createBall(0.5D, 4, var7, var8, var5, var6);
         } else if (var18 == 2) {
            this.createShaped(0.5D, new double[][]{{0.0D, 1.0D}, {0.3455D, 0.309D}, {0.9511D, 0.309D}, {0.3795918367346939D, -0.12653061224489795D}, {0.6122448979591837D, -0.8040816326530612D}, {0.0D, -0.35918367346938773D}}, var7, var8, var5, var6, false);
         } else if (var18 == 3) {
            this.createShaped(0.5D, new double[][]{{0.0D, 0.2D}, {0.2D, 0.2D}, {0.2D, 0.6D}, {0.6D, 0.6D}, {0.6D, 0.2D}, {0.2D, 0.2D}, {0.2D, 0.0D}, {0.4D, 0.0D}, {0.4D, -0.6D}, {0.2D, -0.6D}, {0.2D, -0.4D}, {0.0D, -0.4D}}, var7, var8, var5, var6, true);
         } else if (var18 == 4) {
            this.createBurst(var7, var8, var5, var6);
         } else {
            this.createBall(0.25D, 2, var7, var8, var5, var6);
         }

         int var9 = var7[0];
         float var10 = (float)((var9 & 16711680) >> 16) / 255.0F;
         float var11 = (float)((var9 & '\uff00') >> 8) / 255.0F;
         float var12 = (float)((var9 & 255) >> 0) / 255.0F;
         EntityFireworkOverlayFX var13 = new EntityFireworkOverlayFX(this.worldObj, this.posX, this.posY, this.posZ);
         var13.setRBGColorF(var10, var11, var12);
         this.theEffectRenderer.addEffect(var13);
      }

      ++this.fireworkAge;
      if (this.fireworkAge > this.particleMaxAge) {
         if (this.twinkle) {
            var1 = this.func_92037_i();
            String var16 = String.valueOf((new StringBuilder("fireworks.")).append(var1 ? "twinkle_far" : "twinkle"));
            this.worldObj.playSound(this.posX, this.posY, this.posZ, var16, 20.0F, 0.9F + this.rand.nextFloat() * 0.15F, true);
         }

         this.setDead();
      }

   }

   public int getFXLayer() {
      return 0;
   }

   private void createBurst(int[] var1, int[] var2, boolean var3, boolean var4) {
      double var5 = this.rand.nextGaussian() * 0.05D;
      double var7 = this.rand.nextGaussian() * 0.05D;

      for(int var9 = 0; var9 < 70; ++var9) {
         double var10 = this.motionX * 0.5D + this.rand.nextGaussian() * 0.15D + var5;
         double var12 = this.motionZ * 0.5D + this.rand.nextGaussian() * 0.15D + var7;
         double var14 = this.motionY * 0.5D + this.rand.nextDouble() * 0.5D;
         this.createParticle(this.posX, this.posY, this.posZ, var10, var14, var12, var1, var2, var3, var4);
      }

   }

   public void func_180434_a(WorldRenderer var1, Entity var2, float var3, float var4, float var5, float var6, float var7, float var8) {
   }

   public EntityFireworkStarterFX(World var1, double var2, double var4, double var6, double var8, double var10, double var12, EffectRenderer var14, NBTTagCompound var15) {
      super(var1, var2, var4, var6, 0.0D, 0.0D, 0.0D);
      this.motionX = var8;
      this.motionY = var10;
      this.motionZ = var12;
      this.theEffectRenderer = var14;
      this.particleMaxAge = 8;
      if (var15 != null) {
         this.fireworkExplosions = var15.getTagList("Explosions", 10);
         if (this.fireworkExplosions.tagCount() == 0) {
            this.fireworkExplosions = null;
         } else {
            this.particleMaxAge = this.fireworkExplosions.tagCount() * 2 - 1;

            for(int var16 = 0; var16 < this.fireworkExplosions.tagCount(); ++var16) {
               NBTTagCompound var17 = this.fireworkExplosions.getCompoundTagAt(var16);
               if (var17.getBoolean("Flicker")) {
                  this.twinkle = true;
                  this.particleMaxAge += 15;
                  break;
               }
            }
         }
      }

   }
}
