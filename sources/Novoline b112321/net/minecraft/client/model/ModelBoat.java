package net.minecraft.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelBoat extends ModelBase {
   public ModelRenderer[] boatSides = new ModelRenderer[5];

   public ModelBoat() {
      this.boatSides[0] = new ModelRenderer(this, 0, 8);
      this.boatSides[1] = new ModelRenderer(this, 0, 0);
      this.boatSides[2] = new ModelRenderer(this, 0, 0);
      this.boatSides[3] = new ModelRenderer(this, 0, 0);
      this.boatSides[4] = new ModelRenderer(this, 0, 0);
      boolean var1 = true;
      boolean var2 = true;
      boolean var3 = true;
      boolean var4 = true;
      this.boatSides[0].addBox(-12.0F, -8.0F, -3.0F, 24, 16, 4, 0.0F);
      this.boatSides[0].setRotationPoint(0.0F, 4.0F, 0.0F);
      this.boatSides[1].addBox(-10.0F, -7.0F, -1.0F, 20, 6, 2, 0.0F);
      this.boatSides[1].setRotationPoint(-11.0F, 4.0F, 0.0F);
      this.boatSides[2].addBox(-10.0F, -7.0F, -1.0F, 20, 6, 2, 0.0F);
      this.boatSides[2].setRotationPoint(11.0F, 4.0F, 0.0F);
      this.boatSides[3].addBox(-10.0F, -7.0F, -1.0F, 20, 6, 2, 0.0F);
      this.boatSides[3].setRotationPoint(0.0F, 4.0F, -9.0F);
      this.boatSides[4].addBox(-10.0F, -7.0F, -1.0F, 20, 6, 2, 0.0F);
      this.boatSides[4].setRotationPoint(0.0F, 4.0F, 9.0F);
      this.boatSides[0].rotateAngleX = 1.5707964F;
      this.boatSides[1].rotateAngleY = 4.712389F;
      this.boatSides[2].rotateAngleY = 1.5707964F;
      this.boatSides[3].rotateAngleY = 3.1415927F;
   }

   public void render(Entity var1, float var2, float var3, float var4, float var5, float var6, float var7) {
      for(int var8 = 0; var8 < 5; ++var8) {
         this.boatSides[var8].render(var7);
      }

   }
}
