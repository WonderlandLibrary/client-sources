package net;

import cc.novoline.events.EventTarget;
import cc.novoline.events.events.MotionUpdateEvent$State;
import cc.novoline.events.events.Render3DEvent;
import cc.novoline.events.events.RenderEntityEvent;
import net.ML;
import net.a2R;
import net.aOE;
import net.asJ;
import net.rm;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;

public class agt {
   public static float a;
   public static Frustum b = new Frustum();

   @EventTarget
   public void a(Render3DEvent var1) {
      a = var1.getPartialTicks();
   }

   @EventTarget
   public void a(RenderEntityEvent var1) {
      String[] var2 = rm.b();
      if(var1.f() == MotionUpdateEvent$State.PRE) {
         if(!(var1.a() instanceof EntityPlayer)) {
            return;
         }

         if(var1.b() instanceof aOE) {
            return;
         }

         if(a2R.a(var1.a()).g) {
            asJ var3 = (asJ)var1.a();
            var1.setCancelled(true);
            ML.a(a2R.a(var3), var3, var1.c(), var1.e(), var1.d(), 0.0F, a);
         }
      }

   }

   public static boolean a(AxisAlignedBB var0) {
      rm.b();
      Entity var2 = Minecraft.getInstance().getRenderViewEntity();
      b.setPosition(var2.posX, var2.posY, var2.posZ);
      return b.isBoundingBoxInFrustum(var0);
   }
}
