package net.minecraft.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class ModelBlaze extends ModelBase {
   private final ModelRenderer[] blazeSticks = new ModelRenderer[12];
   private final ModelRenderer blazeHead;

   public ModelBlaze() {
      for(int var1 = 0; var1 < this.blazeSticks.length; ++var1) {
         this.blazeSticks[var1] = new ModelRenderer(this, 0, 16);
         this.blazeSticks[var1].addBox(0.0F, 0.0F, 0.0F, 2, 8, 2);
      }

      this.blazeHead = new ModelRenderer(this, 0, 0);
      this.blazeHead.addBox(-4.0F, -4.0F, -4.0F, 8, 8, 8);
   }

   public void render(Entity var1, float var2, float var3, float var4, float var5, float var6, float var7) {
      this.setRotationAngles(var2, var3, var4, var5, var6, var7, var1);
      this.blazeHead.render(var7);

      for(ModelRenderer var11 : this.blazeSticks) {
         var11.render(var7);
      }

   }

   public void setRotationAngles(float var1, float var2, float var3, float var4, float var5, float var6, Entity var7) {
      float var8 = var3 * 3.1415927F * -0.1F;

      for(int var9 = 0; var9 < 4; ++var9) {
         this.blazeSticks[var9].rotationPointY = -2.0F + MathHelper.cos(((float)(var9 * 2) + var3) * 0.25F);
         this.blazeSticks[var9].rotationPointX = MathHelper.cos(var8) * 9.0F;
         this.blazeSticks[var9].rotationPointZ = MathHelper.sin(var8) * 9.0F;
         ++var8;
      }

      var8 = 0.7853982F + var3 * 3.1415927F * 0.03F;

      for(int var12 = 4; var12 < 8; ++var12) {
         this.blazeSticks[var12].rotationPointY = 2.0F + MathHelper.cos(((float)(var12 * 2) + var3) * 0.25F);
         this.blazeSticks[var12].rotationPointX = MathHelper.cos(var8) * 7.0F;
         this.blazeSticks[var12].rotationPointZ = MathHelper.sin(var8) * 7.0F;
         ++var8;
      }

      var8 = 0.47123894F + var3 * 3.1415927F * -0.05F;

      for(int var13 = 8; var13 < 12; ++var13) {
         this.blazeSticks[var13].rotationPointY = 11.0F + MathHelper.cos(((float)var13 * 1.5F + var3) * 0.5F);
         this.blazeSticks[var13].rotationPointX = MathHelper.cos(var8) * 5.0F;
         this.blazeSticks[var13].rotationPointZ = MathHelper.sin(var8) * 5.0F;
         ++var8;
      }

      this.blazeHead.rotateAngleY = var4 / 57.295776F;
      this.blazeHead.rotateAngleX = var5 / 57.295776F;
   }
}
