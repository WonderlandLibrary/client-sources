package net;

import net.Du;
import net.OD;
import net.RT;
import net.aLp;
import net.aag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemSword;
import org.lwjgl.util.vector.Vector3f;

public class bR {
   public static void a(EntityPlayer var0, RT var1, Du var2) {
      String[] var3 = OD.a();
      if(var2.d < 0.5F) {
         var1.R.a();
      }

      if(var0.getCurrentEquippedItem() != null && var0.getCurrentEquippedItem().getItem() instanceof ItemSword) {
         var1.R.a(var1);
      }

      float var4 = var2.d / 10.0F;
      float var5 = var4 * 3.0F;
      var5 = aLp.a(var5, 1.0F);
      if(!var0.isRiding()) {
         var1.A.c(30.0F, 0.7F);
      }

      Vector3f var6 = new Vector3f(0.0F, 0.0F, 0.0F);
      var6.x = 20.0F - var4 * 20.0F;
      var6.y = -40.0F * var4 + 50.0F * var4;
      ((aag)var1.bipedBody).F.a(var6, 0.9F);
      ((aag)var1.bipedHead).F.g(var1.L - 30.0F);
      ((aag)var1.bipedHead).F.f(var1.S);
      ((aag)var1.bipedHead).B.a(-var6.x, 0.9F);
      ((aag)var1.bipedHead).B.c(-var6.y, 0.9F);
      ((aag)var1.bipedRightArm).B.b(60.0F, 0.3F);
      ((aag)var1.bipedRightArm).F.a(-20.0F + var5 * 100.0F, 3.0F);
      ((aag)var1.bipedRightArm).F.c(0.0F, 0.3F);
      ((aag)var1.bipedRightArm).F.b(0.0F, 0.9F);
      ((aag)var1.bipedLeftArm).F.b(20.0F, 0.3F);
      ((aag)var1.bipedLeftArm).B.b(-80.0F, 0.3F);
      ((aag)var1.bipedLeftArm).F.c(0.0F, 0.3F);
      var1.D.F.a(-20.0F, 0.3F);
      var1.K.F.a(-60.0F, 0.3F);
      if(var2.f.x == 0.0F) {
         boolean var10000 = true;
      } else {
         boolean var8 = false;
      }

      if(var2.f.z == 0.0F) {
         boolean var10001 = true;
      } else {
         boolean var9 = false;
      }

      boolean var10 = false;
      var1.T.a(90.0F, 0.9F);
   }
}
