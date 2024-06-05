package net.minecraft.client.particle;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.world.World;

public class Barrier extends EntityFX {
   private static final String __OBFID = "CL_00002615";

   public int getFXLayer() {
      return 1;
   }

   public void func_180434_a(WorldRenderer var1, Entity var2, float var3, float var4, float var5, float var6, float var7, float var8) {
      float var9 = this.particleIcon.getMinU();
      float var10 = this.particleIcon.getMaxU();
      float var11 = this.particleIcon.getMinV();
      float var12 = this.particleIcon.getMaxV();
      float var13 = (float)(this.prevPosX + (this.posX - this.prevPosX) * (double)var3 - interpPosX);
      float var14 = (float)(this.prevPosY + (this.posY - this.prevPosY) * (double)var3 - interpPosY);
      float var15 = (float)(this.prevPosZ + (this.posZ - this.prevPosZ) * (double)var3 - interpPosZ);
      var1.func_178986_b(this.particleRed, this.particleGreen, this.particleBlue);
      float var16 = 0.5F;
      var1.addVertexWithUV((double)(var13 - var4 * var16 - var7 * var16), (double)(var14 - var5 * var16), (double)(var15 - var6 * var16 - var8 * var16), (double)var10, (double)var12);
      var1.addVertexWithUV((double)(var13 - var4 * var16 + var7 * var16), (double)(var14 + var5 * var16), (double)(var15 - var6 * var16 + var8 * var16), (double)var10, (double)var11);
      var1.addVertexWithUV((double)(var13 + var4 * var16 + var7 * var16), (double)(var14 + var5 * var16), (double)(var15 + var6 * var16 + var8 * var16), (double)var9, (double)var11);
      var1.addVertexWithUV((double)(var13 + var4 * var16 - var7 * var16), (double)(var14 - var5 * var16), (double)(var15 + var6 * var16 - var8 * var16), (double)var9, (double)var12);
   }

   protected Barrier(World var1, double var2, double var4, double var6, Item var8) {
      super(var1, var2, var4, var6, 0.0D, 0.0D, 0.0D);
      this.func_180435_a(Minecraft.getMinecraft().getRenderItem().getItemModelMesher().getParticleIcon(var8));
      this.particleRed = this.particleGreen = this.particleBlue = 1.0F;
      this.motionX = this.motionY = this.motionZ = 0.0D;
      this.particleGravity = 0.0F;
      this.particleMaxAge = 80;
   }

   public static class Factory implements IParticleFactory {
      private static final String __OBFID = "CL_00002614";

      public EntityFX func_178902_a(int var1, World var2, double var3, double var5, double var7, double var9, double var11, double var13, int... var15) {
         return new Barrier(var2, var3, var5, var7, Item.getItemFromBlock(Blocks.barrier));
      }
   }
}
