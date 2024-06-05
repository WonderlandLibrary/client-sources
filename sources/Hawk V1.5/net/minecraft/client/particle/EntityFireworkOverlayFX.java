package net.minecraft.client.particle;

import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityFireworkOverlayFX extends EntityFX {
   private static final String __OBFID = "CL_00000904";

   public void func_180434_a(WorldRenderer var1, Entity var2, float var3, float var4, float var5, float var6, float var7, float var8) {
      float var9 = 0.25F;
      float var10 = var9 + 0.25F;
      float var11 = 0.125F;
      float var12 = var11 + 0.25F;
      float var13 = 7.1F * MathHelper.sin(((float)this.particleAge + var3 - 1.0F) * 0.25F * 3.1415927F);
      this.particleAlpha = 0.6F - ((float)this.particleAge + var3 - 1.0F) * 0.25F * 0.5F;
      float var14 = (float)(this.prevPosX + (this.posX - this.prevPosX) * (double)var3 - interpPosX);
      float var15 = (float)(this.prevPosY + (this.posY - this.prevPosY) * (double)var3 - interpPosY);
      float var16 = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * (double)var3 - interpPosZ);
      var1.func_178960_a(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha);
      var1.addVertexWithUV((double)(var14 - var4 * var13 - var7 * var13), (double)(var15 - var5 * var13), (double)(var16 - var6 * var13 - var8 * var13), (double)var10, (double)var12);
      var1.addVertexWithUV((double)(var14 - var4 * var13 + var7 * var13), (double)(var15 + var5 * var13), (double)(var16 - var6 * var13 + var8 * var13), (double)var10, (double)var11);
      var1.addVertexWithUV((double)(var14 + var4 * var13 + var7 * var13), (double)(var15 + var5 * var13), (double)(var16 + var6 * var13 + var8 * var13), (double)var9, (double)var11);
      var1.addVertexWithUV((double)(var14 + var4 * var13 - var7 * var13), (double)(var15 - var5 * var13), (double)(var16 + var6 * var13 - var8 * var13), (double)var9, (double)var12);
   }

   protected EntityFireworkOverlayFX(World var1, double var2, double var4, double var6) {
      super(var1, var2, var4, var6);
      this.particleMaxAge = 4;
   }
}
