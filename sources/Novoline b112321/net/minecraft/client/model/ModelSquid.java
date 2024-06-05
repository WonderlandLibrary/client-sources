package net.minecraft.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class ModelSquid extends ModelBase {
   ModelRenderer squidBody;
   ModelRenderer[] squidTentacles = new ModelRenderer[8];

   public ModelSquid() {
      boolean var1 = true;
      this.squidBody = new ModelRenderer(this, 0, 0);
      this.squidBody.addBox(-6.0F, -8.0F, -6.0F, 12, 16, 12);
      this.squidBody.rotationPointY += 8.0F;

      for(int var2 = 0; var2 < this.squidTentacles.length; ++var2) {
         this.squidTentacles[var2] = new ModelRenderer(this, 48, 0);
         double var3 = (double)var2 * 3.141592653589793D * 2.0D / (double)this.squidTentacles.length;
         float var5 = MathHelper.cos(var3) * 5.0F;
         float var6 = MathHelper.sin(var3) * 5.0F;
         this.squidTentacles[var2].addBox(-1.0F, 0.0F, -1.0F, 2, 18, 2);
         this.squidTentacles[var2].rotationPointX = var5;
         this.squidTentacles[var2].rotationPointZ = var6;
         this.squidTentacles[var2].rotationPointY = 15.0F;
         var3 = (double)var2 * 3.141592653589793D * -2.0D / (double)this.squidTentacles.length + 1.5707963267948966D;
         this.squidTentacles[var2].rotateAngleY = (float)var3;
      }

   }

   public void setRotationAngles(float var1, float var2, float var3, float var4, float var5, float var6, Entity var7) {
      for(ModelRenderer var11 : this.squidTentacles) {
         var11.rotateAngleX = var3;
      }

   }

   public void render(Entity var1, float var2, float var3, float var4, float var5, float var6, float var7) {
      this.setRotationAngles(var2, var3, var4, var5, var6, var7, var1);
      this.squidBody.render(var7);

      for(ModelRenderer var11 : this.squidTentacles) {
         var11.render(var7);
      }

   }
}
