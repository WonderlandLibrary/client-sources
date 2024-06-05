package net;

import net.Dm;
import net.Du;
import net.Nj;
import net.OD;
import net.RT;
import net.aLp;
import net.aag;
import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

public class Og extends Nj {
   public String a() {
      return "bow";
   }

   public void a(EntityLivingBase var1, ModelBase var2, Dm var3) {
      OD.a();
      RT var5 = (RT)var2;
      Du var6 = (Du)var3;
      EntityPlayer var7 = (EntityPlayer)var1;
      float var8 = 0.0F;
      if(var7 != null) {
         var8 = (float)var7.getItemInUseDuration();
      }

      if(var8 > 15.0F) {
         var8 = 15.0F;
      }

      if(var8 < 10.0F) {
         ((aag)var5.bipedRightArm).F.a(0.0F, 0.3F);
         ((aag)var5.bipedLeftArm).F.a(0.0F, 0.3F);
         ((aag)var5.bipedBody).F.a(30.0F, 0.3F);
         ((aag)var5.bipedBody).F.c(0.0F, 0.3F);
         ((aag)var5.bipedRightArm).F.h(0.0F);
         ((aag)var5.bipedRightArm).F.c(-30.0F);
         ((aag)var5.bipedLeftArm).F.c(-30.0F);
         ((aag)var5.bipedLeftArm).F.e(80.0F);
         float var9 = var8 / 10.0F;
         var5.K.F.c(var9 * -50.0F);
         ((aag)var5.bipedHead).F.a(var5.S - 30.0F, 0.3F);
      }

      float var13 = 20.0F - (var8 - 10.0F) / 5.0F * 20.0F;
      ((aag)var5.bipedBody).F.a(var13, 0.3F);
      float var10 = (var8 - 10.0F) / 5.0F * -25.0F;
      ((aag)var5.bipedBody).F.c(var10 + var5.L, 0.3F);
      ((aag)var5.bipedRightArm).F.a(-90.0F - var13, 0.3F);
      ((aag)var5.bipedLeftArm).F.c(-30.0F);
      ((aag)var5.bipedLeftArm).F.e(80.0F);
      float var11 = var8 / 10.0F;
      var5.K.F.c(var11 * -30.0F);
      ((aag)var5.bipedRightArm).B.e(var10);
      float var12 = -90.0F + var5.S;
      var12 = aLp.b(var12, -120.0F);
      ((aag)var5.bipedLeftArm).B.a(var12, 0.3F);
      ((aag)var5.bipedRightArm).F.c(var5.S - 90.0F);
      ((aag)var5.bipedHead).F.g(-var10);
      ((aag)var5.bipedHead).B.a(-var13, 0.3F);
      ((aag)var5.bipedHead).F.f(var5.S);
   }
}
