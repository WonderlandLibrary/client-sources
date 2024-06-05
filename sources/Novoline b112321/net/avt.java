package net;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;
import net.BT;
import net.UW;
import net.a2V;
import net.a6_;
import net.aJ0;
import net.aaF;
import net.acf;
import net.agu;
import net.as0;
import net.ava;
import net.aye;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.EntityLivingBase;
import org.lwjgl.opengl.GL11;

public class avt extends as0 {
   private final HashMap y = new HashMap();
   private final List x = new ArrayList();

   public avt(UW var1) {
      super(var1, "DamageParticles", "Damage Particles", a2V.VISUALS, "");
   }

   @agu
   public void a(aaF var1) {
      ava.e();
      EntityLivingBase var3 = (EntityLivingBase)var1.a();
      if(var3 != this.w.thePlayer) {
         if(!this.y.containsKey(var3)) {
            this.y.put(var3, Float.valueOf(var3.getHealth()));
         }

         float var4 = ((Float)this.y.get(var3)).floatValue();
         float var5 = var3.getHealth();
         if(var4 != var5) {
            boolean var6 = var5 > var4;
            boolean var7 = var3.hurtResistantTime < 18 || this.w.thePlayer.motionY < 0.0D && !this.w.thePlayer.onGround;
            String var8 = var6?"§a":(var7?"§c":"§e");
            String var9 = var4 - var5 < 0.0F?var8 + a((double)((var4 - var5) * -1.0F), 1):var8 + a((double)(var4 - var5), 1);
            acf var10 = new acf(var3);
            var10.b(var3.getEntityBoundingBox().minY + (var3.getEntityBoundingBox().maxY - var3.getEntityBoundingBox().minY) / 2.0D);
            var10.c(var10.f() - 0.5D + (double)(new Random(System.currentTimeMillis())).nextInt(5) * 0.1D);
            var10.a(var10.e() - 0.5D + (double)(new Random(System.currentTimeMillis() + 1L)).nextInt(5) * 0.1D);
            this.x.add(new aJ0(var10, var9));
            this.y.remove(var3);
            this.y.put(var3, Float.valueOf(var3.getHealth()));
         }

      }
   }

   @agu
   public void a(aye var1) {
      ava.e();
      Iterator var3 = this.x.iterator();
      if(var3.hasNext()) {
         aJ0 var4 = (aJ0)var3.next();
         double var5 = var4.c.f();
         double var7 = var5 - this.w.getRenderManager().h;
         double var9 = var4.c.g();
         double var11 = var9 - this.w.getRenderManager().g;
         double var13 = var4.c.e();
         double var15 = var13 - this.w.getRenderManager().m;
         GlStateManager.pushMatrix();
         GlStateManager.enablePolygonOffset();
         GlStateManager.doPolygonOffset(1.0F, -1500000.0F);
         GlStateManager.translate((float)var7, (float)var11, (float)var15);
         GlStateManager.rotate(-this.w.getRenderManager().playerViewY, 0.0F, 1.0F, 0.0F);
         float var17 = this.w.gameSettings.thirdPersonView == 2?-1.0F:1.0F;
         GlStateManager.rotate(this.w.getRenderManager().playerViewX, var17, 0.0F, 0.0F);
         double var18 = 0.03D;
         GlStateManager.scale(-0.03D, -0.03D, 0.03D);
         a6_.b();
         a6_.f();
         GL11.glDepthMask(false);
         this.w.fontRendererObj.drawStringWithShadow(var4.a, (float)(-(this.w.fontRendererObj.d(var4.a) / 2)), (float)(-(this.w.fontRendererObj.f() - 1)), 0);
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         GL11.glDepthMask(true);
         GlStateManager.doPolygonOffset(1.0F, 1500000.0F);
         GlStateManager.disablePolygonOffset();
         GlStateManager.disableBlend();
         GlStateManager.popMatrix();
      }

   }

   public static double a(double var0, int var2) {
      throw new IllegalArgumentException();
   }

   @agu
   public void a(BT var1) {
      this.x.forEach(this::lambda$onUpdate$0);
   }

   private void lambda$onUpdate$0(aJ0 var1) {
      ava.h();
      ++var1.b;
      if(var1.b <= 10) {
         var1.c.b(var1.c.g() + (double)var1.b * 0.005D);
      }

      if(var1.b > 20) {
         this.x.remove(var1);
      }

   }

   private static IllegalArgumentException a(IllegalArgumentException var0) {
      return var0;
   }
}
