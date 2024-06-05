package net.minecraft.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelMinecart extends ModelBase {
   public ModelRenderer[] sideModels = new ModelRenderer[7];

   public ModelMinecart() {
      this.sideModels[0] = new ModelRenderer(this, 0, 10);
      this.sideModels[1] = new ModelRenderer(this, 0, 0);
      this.sideModels[2] = new ModelRenderer(this, 0, 0);
      this.sideModels[3] = new ModelRenderer(this, 0, 0);
      this.sideModels[4] = new ModelRenderer(this, 0, 0);
      this.sideModels[5] = new ModelRenderer(this, 44, 10);
      boolean var1 = true;
      boolean var2 = true;
      boolean var3 = true;
      boolean var4 = true;
      this.sideModels[0].addBox(-10.0F, -8.0F, -1.0F, 20, 16, 2, 0.0F);
      this.sideModels[0].setRotationPoint(0.0F, 4.0F, 0.0F);
      this.sideModels[5].addBox(-9.0F, -7.0F, -1.0F, 18, 14, 1, 0.0F);
      this.sideModels[5].setRotationPoint(0.0F, 4.0F, 0.0F);
      this.sideModels[1].addBox(-8.0F, -9.0F, -1.0F, 16, 8, 2, 0.0F);
      this.sideModels[1].setRotationPoint(-9.0F, 4.0F, 0.0F);
      this.sideModels[2].addBox(-8.0F, -9.0F, -1.0F, 16, 8, 2, 0.0F);
      this.sideModels[2].setRotationPoint(9.0F, 4.0F, 0.0F);
      this.sideModels[3].addBox(-8.0F, -9.0F, -1.0F, 16, 8, 2, 0.0F);
      this.sideModels[3].setRotationPoint(0.0F, 4.0F, -7.0F);
      this.sideModels[4].addBox(-8.0F, -9.0F, -1.0F, 16, 8, 2, 0.0F);
      this.sideModels[4].setRotationPoint(0.0F, 4.0F, 7.0F);
      this.sideModels[0].rotateAngleX = 1.5707964F;
      this.sideModels[1].rotateAngleY = 4.712389F;
      this.sideModels[2].rotateAngleY = 1.5707964F;
      this.sideModels[3].rotateAngleY = 3.1415927F;
      this.sideModels[5].rotateAngleX = -1.5707964F;
   }

   public void render(Entity var1, float var2, float var3, float var4, float var5, float var6, float var7) {
      this.sideModels[5].rotationPointY = 4.0F - var4;

      for(int var8 = 0; var8 < 6; ++var8) {
         this.sideModels[var8].render(var7);
      }

   }
}
