package net;

import net.Du;
import net.OD;
import net.RT;
import net.aag;
import net.minecraft.entity.player.EntityPlayer;

public class a4u {
   public static void a(EntityPlayer var0, RT var1, Du var2) {
      String[] var3 = OD.a();
      if(var2.i()) {
         if(var2.f.x == 0.0F) {
            boolean var10000 = true;
         } else {
            boolean var4 = false;
         }

         if(var2.f.z == 0.0F) {
            boolean var10001 = true;
         } else {
            boolean var5 = false;
         }

         boolean var6 = false;
         if(var0.isSprinting()) {
            ((aag)var1.bipedBody).F.c(20.0F, 0.3F);
            ((aag)var1.bipedHead).F.g(var1.L - 20.0F);
            ((aag)var1.bipedHead).F.f(var1.S - 15.0F);
            ((aag)var1.bipedRightLeg).F.e(0.0F);
            ((aag)var1.bipedLeftLeg).F.e(0.0F);
            ((aag)var1.bipedRightArm).F.a(60.0F, 0.3F);
            var1.T.a(90.0F, 0.3F);
         }

      }
   }
}
