package net.minecraft.client.model;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityArmorStand;

public class ModelArmorStandArmor extends ModelBiped {
   private static final String __OBFID = "CL_00002632";

   public ModelArmorStandArmor() {
      this(0.0F);
   }

   public void setRotationAngles(float var1, float var2, float var3, float var4, float var5, float var6, Entity var7) {
      if (var7 instanceof EntityArmorStand) {
         EntityArmorStand var8 = (EntityArmorStand)var7;
         this.bipedHead.rotateAngleX = 0.017453292F * var8.getHeadRotation().func_179415_b();
         this.bipedHead.rotateAngleY = 0.017453292F * var8.getHeadRotation().func_179416_c();
         this.bipedHead.rotateAngleZ = 0.017453292F * var8.getHeadRotation().func_179413_d();
         this.bipedHead.setRotationPoint(0.0F, 1.0F, 0.0F);
         this.bipedBody.rotateAngleX = 0.017453292F * var8.getBodyRotation().func_179415_b();
         this.bipedBody.rotateAngleY = 0.017453292F * var8.getBodyRotation().func_179416_c();
         this.bipedBody.rotateAngleZ = 0.017453292F * var8.getBodyRotation().func_179413_d();
         this.bipedLeftArm.rotateAngleX = 0.017453292F * var8.getLeftArmRotation().func_179415_b();
         this.bipedLeftArm.rotateAngleY = 0.017453292F * var8.getLeftArmRotation().func_179416_c();
         this.bipedLeftArm.rotateAngleZ = 0.017453292F * var8.getLeftArmRotation().func_179413_d();
         this.bipedRightArm.rotateAngleX = 0.017453292F * var8.getRightArmRotation().func_179415_b();
         this.bipedRightArm.rotateAngleY = 0.017453292F * var8.getRightArmRotation().func_179416_c();
         this.bipedRightArm.rotateAngleZ = 0.017453292F * var8.getRightArmRotation().func_179413_d();
         this.bipedLeftLeg.rotateAngleX = 0.017453292F * var8.getLeftLegRotation().func_179415_b();
         this.bipedLeftLeg.rotateAngleY = 0.017453292F * var8.getLeftLegRotation().func_179416_c();
         this.bipedLeftLeg.rotateAngleZ = 0.017453292F * var8.getLeftLegRotation().func_179413_d();
         this.bipedLeftLeg.setRotationPoint(1.9F, 11.0F, 0.0F);
         this.bipedRightLeg.rotateAngleX = 0.017453292F * var8.getRightLegRotation().func_179415_b();
         this.bipedRightLeg.rotateAngleY = 0.017453292F * var8.getRightLegRotation().func_179416_c();
         this.bipedRightLeg.rotateAngleZ = 0.017453292F * var8.getRightLegRotation().func_179413_d();
         this.bipedRightLeg.setRotationPoint(-1.9F, 11.0F, 0.0F);
         func_178685_a(this.bipedHead, this.bipedHeadwear);
      }

   }

   protected ModelArmorStandArmor(float var1, int var2, int var3) {
      super(var1, 0.0F, var2, var3);
   }

   public ModelArmorStandArmor(float var1) {
      this(var1, 64, 32);
   }
}
