package net;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.particle.EntityFirework$OverlayFX;
import net.minecraft.client.particle.EntityFirework$SparkFX;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemDye;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class aFd extends EntityFX {
   private int ay;
   private final EffectRenderer ax;
   private NBTTagList aw;
   boolean av;

   public aFd(World var1, double var2, double var4, double var6, double var8, double var10, double var12, EffectRenderer var14, NBTTagCompound var15) {
      super(var1, var2, var4, var6, 0.0D, 0.0D, 0.0D);
      this.motionX = var8;
      this.motionY = var10;
      this.motionZ = var12;
      this.ax = var14;
      this.particleMaxAge = 8;
      this.aw = var15.getTagList("Explosions", 10);
      if(this.aw.tagCount() == 0) {
         this.aw = null;
      } else {
         this.particleMaxAge = this.aw.tagCount() * 2 - 1;

         for(int var16 = 0; var16 < this.aw.tagCount(); ++var16) {
            NBTTagCompound var17 = this.aw.getCompoundTagAt(var16);
            if(var17.getBoolean("Flicker")) {
               this.av = true;
               this.particleMaxAge += 15;
               break;
            }
         }
      }

   }

   public void renderParticle(WorldRenderer var1, Entity var2, float var3, float var4, float var5, float var6, float var7, float var8) {
   }

   public void onUpdate() {
      if(this.ay == 0 && this.aw != null) {
         boolean var1 = this.a();
         boolean var2 = false;
         if(this.aw.tagCount() >= 3) {
            var2 = true;
         } else {
            for(int var3 = 0; var3 < this.aw.tagCount(); ++var3) {
               NBTTagCompound var4 = this.aw.getCompoundTagAt(var3);
               if(var4.getByte("Type") == 1) {
                  var2 = true;
                  break;
               }
            }
         }

         String var19 = "fireworks." + "largeBlast" + "_far";
         this.worldObj.playSound(this.posX, this.posY, this.posZ, var19, 20.0F, 0.95F + this.rand.nextFloat() * 0.1F, true);
      }

      if(this.ay % 2 == 0 && this.aw != null && this.ay / 2 < this.aw.tagCount()) {
         int var13 = this.ay / 2;
         NBTTagCompound var17 = this.aw.getCompoundTagAt(var13);
         byte var20 = var17.getByte("Type");
         boolean var21 = var17.getBoolean("Trail");
         boolean var5 = var17.getBoolean("Flicker");
         int[] var6 = var17.getIntArray("Colors");
         int[] var7 = var17.getIntArray("FadeColors");
         if(var6.length == 0) {
            var6 = new int[]{ItemDye.dyeColors[0]};
         }

         if(var20 == 1) {
            this.a(0.5D, 4, var6, var7, var21, var5);
         } else if(var20 == 2) {
            this.a(0.5D, new double[][]{{0.0D, 1.0D}, {0.3455D, 0.309D}, {0.9511D, 0.309D}, {0.3795918367346939D, -0.12653061224489795D}, {0.6122448979591837D, -0.8040816326530612D}, {0.0D, -0.35918367346938773D}}, var6, var7, var21, var5, false);
         } else if(var20 == 3) {
            this.a(0.5D, new double[][]{{0.0D, 0.2D}, {0.2D, 0.2D}, {0.2D, 0.6D}, {0.6D, 0.6D}, {0.6D, 0.2D}, {0.2D, 0.2D}, {0.2D, 0.0D}, {0.4D, 0.0D}, {0.4D, -0.6D}, {0.2D, -0.6D}, {0.2D, -0.4D}, {0.0D, -0.4D}}, var6, var7, var21, var5, true);
         } else if(var20 == 4) {
            this.a(var6, var7, var21, var5);
         } else {
            this.a(0.25D, 2, var6, var7, var21, var5);
         }

         int var8 = var6[0];
         float var9 = (float)((var8 & 16711680) >> 16) / 255.0F;
         float var10 = (float)((var8 & '\uff00') >> 8) / 255.0F;
         float var11 = (float)(var8 & 255) / 255.0F;
         EntityFirework$OverlayFX var12 = new EntityFirework$OverlayFX(this.worldObj, this.posX, this.posY, this.posZ);
         var12.setRBGColorF(var9, var10, var11);
         this.ax.addEffect(var12);
      }

      ++this.ay;
      if(this.ay > this.particleMaxAge) {
         if(this.av) {
            boolean var14 = this.a();
            String var18 = "fireworks." + "twinkle_far";
            this.worldObj.playSound(this.posX, this.posY, this.posZ, var18, 20.0F, 0.9F + this.rand.nextFloat() * 0.15F, true);
         }

         this.setDead();
      }

   }

   private boolean a() {
      Minecraft var1 = Minecraft.getInstance();
      return var1.getRenderViewEntity() == null || var1.getRenderViewEntity().getDistanceSq(this.posX, this.posY, this.posZ) >= 256.0D;
   }

   private void a(double var1, double var3, double var5, double var7, double var9, double var11, int[] var13, int[] var14, boolean var15, boolean var16) {
      EntityFirework$SparkFX var17 = new EntityFirework$SparkFX(this.worldObj, var1, var3, var5, var7, var9, var11, this.ax);
      var17.setAlphaF(0.99F);
      var17.setTrail(var15);
      var17.setTwinkle(var16);
      int var18 = this.rand.nextInt(var13.length);
      var17.setColour(var13[var18]);
      if(var14.length > 0) {
         var17.setFadeColour(var14[this.rand.nextInt(var14.length)]);
      }

      this.ax.addEffect(var17);
   }

   private void a(double var1, int var3, int[] var4, int[] var5, boolean var6, boolean var7) {
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
               this.a(var8, var10, var12, var17 / var23, var19 / var23, var21 / var23, var4, var5, var6, var7);
               if(var14 != -var3 && var14 != var3 && var15 != -var3 && var15 != var3) {
                  var16 += var3 * 2 - 1;
               }
            }
         }
      }

   }

   private void a(double var1, double[][] var3, int[] var4, int[] var5, boolean var6, boolean var7, boolean var8) {
      double var9 = var3[0][0];
      double var11 = var3[0][1];
      this.a(this.posX, this.posY, this.posZ, var9 * var1, var11 * var1, 0.0D, var4, var5, var6, var7);
      float var13 = this.rand.nextFloat() * 3.1415927F;
      double var14 = 0.034D;

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
               double var34 = var30 * (double)MathHelper.sin(var17);
               var30 = var30 * (double)MathHelper.cos(var17);

               for(double var36 = -1.0D; var36 <= 1.0D; var36 += 2.0D) {
                  this.a(this.posX, this.posY, this.posZ, var30 * var36, var32, var34 * var36, var4, var5, var6, var7);
               }
            }

            var19 = var24;
            var21 = var26;
         }
      }

   }

   private void a(int[] var1, int[] var2, boolean var3, boolean var4) {
      double var5 = this.rand.nextGaussian() * 0.05D;
      double var7 = this.rand.nextGaussian() * 0.05D;

      for(int var9 = 0; var9 < 70; ++var9) {
         double var10 = this.motionX * 0.5D + this.rand.nextGaussian() * 0.15D + var5;
         double var12 = this.motionZ * 0.5D + this.rand.nextGaussian() * 0.15D + var7;
         double var14 = this.motionY * 0.5D + this.rand.nextDouble() * 0.5D;
         this.a(this.posX, this.posY, this.posZ, var10, var14, var12, var1, var2, var3, var4);
      }

   }

   public int getFXLayer() {
      return 0;
   }
}
