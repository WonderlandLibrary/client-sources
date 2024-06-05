package net.minecraft.client.particle;

import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntityFirework$OverlayFX extends EntityFX {
   protected EntityFirework$OverlayFX(World var1, double var2, double var4, double var6) {
      super(var1, var2, var4, var6);
      this.particleMaxAge = 4;
   }

   public void renderParticle(WorldRenderer var1, Entity var2, float var3, float var4, float var5, float var6, float var7, float var8) {
      float var9 = 7.1F * MathHelper.sin(((float)this.particleAge + var3 - 1.0F) * 0.25F * 3.1415927F);
      this.particleAlpha = 0.6F - ((float)this.particleAge + var3 - 1.0F) * 0.25F * 0.5F;
      float var10 = (float)(this.prevPosX + (this.posX - this.prevPosX) * (double)var3 - interpPosX);
      float var11 = (float)(this.prevPosY + (this.posY - this.prevPosY) * (double)var3 - interpPosY);
      float var12 = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * (double)var3 - interpPosZ);
      int var13 = this.getBrightnessForRender(var3);
      int var14 = var13 >> 16 & '\uffff';
      int var15 = var13 & '\uffff';
      var1.pos((double)(var10 - var4 * var9 - var7 * var9), (double)(var11 - var5 * var9), (double)(var12 - var6 * var9 - var8 * var9)).tex(0.5D, 0.375D).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).lightmap(var14, var15).endVertex();
      var1.pos((double)(var10 - var4 * var9 + var7 * var9), (double)(var11 + var5 * var9), (double)(var12 - var6 * var9 + var8 * var9)).tex(0.5D, 0.125D).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).lightmap(var14, var15).endVertex();
      var1.pos((double)(var10 + var4 * var9 + var7 * var9), (double)(var11 + var5 * var9), (double)(var12 + var6 * var9 + var8 * var9)).tex(0.25D, 0.125D).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).lightmap(var14, var15).endVertex();
      var1.pos((double)(var10 + var4 * var9 - var7 * var9), (double)(var11 - var5 * var9), (double)(var12 + var6 * var9 - var8 * var9)).tex(0.25D, 0.375D).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).lightmap(var14, var15).endVertex();
   }
}
