package net;

import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import net.UW;
import net.VN;
import net.a2V;
import net.a6_;
import net.a6d;
import net.aEu;
import net.adZ;
import net.ae9;
import net.agu;
import net.as0;
import net.au7;
import net.ava;
import net.axu;
import net.aye;
import net.minecraft.entity.player.EntityPlayer;

public class arn extends as0 {
   @VN("only-tar")
   aEu x = axu.g();

   public arn(UW var1) {
      super(var1, "Tracers", a2V.VISUALS);
      ae9.a(new adZ("TC_ONLY_TAR", "Targets Only", a6d.CHECKBOX, this, this.x));
   }

   @agu
   public void a(aye var1) {
      ava.e();
      Iterator var3 = ((List)this.w.theWorld.K().stream().filter(this::lambda$on3DRender$0).collect(Collectors.toList())).iterator();
      if(var3.hasNext()) {
         EntityPlayer var4 = (EntityPlayer)var3.next();
         if(var4.isEntityAlive() && var4 != this.w.thePlayer && !var4.isInvisible()) {
            double var5 = var4.lastTickPosX + (var4.posX - var4.lastTickPosX) * (double)var1.a() - this.w.getRenderManager().h;
            double var7 = var4.lastTickPosY + (var4.posY - var4.lastTickPosY) * (double)var1.a() - this.w.getRenderManager().g;
            double var9 = var4.lastTickPosZ + (var4.posZ - var4.lastTickPosZ) * (double)var1.a() - this.w.getRenderManager().m;
            boolean var11 = this.w.gameSettings.viewBobbing;
            a6_.g();
            this.w.gameSettings.viewBobbing = false;
            this.w.entityRenderer.setupCameraTransform(this.w.timer.renderPartialTicks, 2);
            this.w.gameSettings.viewBobbing = var11;
            boolean var12 = this.j.k().b(var4.getName(), au7.TARGET);
            double[] var13 = var12?new double[]{1.0D, 0.0D, 0.0D}:new double[]{1.0D, 1.0D, 1.0D};
            a6_.a(var4, var13, var5, var7 + (double)var4.getEyeHeight(), var9);
            a6_.j();
         }
      }

   }

   private boolean lambda$on3DRender$0(EntityPlayer var1) {
      int var2 = ava.e();
      return !((Boolean)this.x.a()).booleanValue() || this.j.k().b(var1.getName(), au7.TARGET);
   }
}
