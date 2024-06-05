package cc.novoline.modules.visual;

import cc.novoline.events.EventTarget;
import cc.novoline.events.events.LivingUpdateEvent;
import cc.novoline.events.events.PlayerUpdateEvent;
import cc.novoline.events.events.Render3DEvent;
import cc.novoline.modules.AbstractModule;
import cc.novoline.modules.EnumModuleType;
import cc.novoline.modules.ModuleManager;
import cc.novoline.modules.visual.DMGParticle$Location;
import cc.novoline.modules.visual.DMGParticle$Particles;
import cc.novoline.modules.visual.HUD;
import cc.novoline.utils.RenderUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.EntityLivingBase;
import org.lwjgl.opengl.GL11;

public class DMGParticle extends AbstractModule {
   private final HashMap healthMap = new HashMap();
   private final List particles = new ArrayList();

   public DMGParticle(ModuleManager var1) {
      super(var1, "DamageParticles", "Damage Particles", EnumModuleType.VISUALS, "");
   }

   @EventTarget
   public void onLivingUpdate(LivingUpdateEvent var1) {
      HUD.e();
      EntityLivingBase var3 = (EntityLivingBase)var1.getEntity();
      if(var3 != this.mc.player) {
         if(!this.healthMap.containsKey(var3)) {
            this.healthMap.put(var3, Float.valueOf(var3.getHealth()));
         }

         float var4 = ((Float)this.healthMap.get(var3)).floatValue();
         float var5 = var3.getHealth();
         if(var4 != var5) {
            boolean var6 = var5 > var4;
            boolean var7 = var3.hurtResistantTime < 18 || this.mc.player.motionY < 0.0D && !this.mc.player.onGround;
            String var8 = var6?"§a":(var7?"§c":"§e");
            String var9 = var4 - var5 < 0.0F?var8 + a((double)((var4 - var5) * -1.0F), 1):var8 + a((double)(var4 - var5), 1);
            DMGParticle$Location var10 = new DMGParticle$Location(var3);
            var10.setY(var3.getEntityBoundingBox().minY + (var3.getEntityBoundingBox().maxY - var3.getEntityBoundingBox().minY) / 2.0D);
            var10.setX(var10.getX() - 0.5D + (double)(new Random(System.currentTimeMillis())).nextInt(5) * 0.1D);
            var10.setZ(var10.getZ() - 0.5D + (double)(new Random(System.currentTimeMillis() + 1L)).nextInt(5) * 0.1D);
            this.particles.add(new DMGParticle$Particles(var10, var9));
            this.healthMap.remove(var3);
            this.healthMap.put(var3, Float.valueOf(var3.getHealth()));
         }

      }
   }

   @EventTarget
   public void onRender(Render3DEvent var1) {
      HUD.e();
      Iterator var3 = this.particles.iterator();
      if(var3.hasNext()) {
         DMGParticle$Particles var4 = (DMGParticle$Particles)var3.next();
         double var5 = var4.location.getX();
         double var7 = var5 - this.mc.getRenderManager().renderPosX;
         double var9 = var4.location.getY();
         double var11 = var9 - this.mc.getRenderManager().renderPosY;
         double var13 = var4.location.getZ();
         double var15 = var13 - this.mc.getRenderManager().renderPosZ;
         GlStateManager.pushMatrix();
         GlStateManager.enablePolygonOffset();
         GlStateManager.doPolygonOffset(1.0F, -1500000.0F);
         GlStateManager.translate((float)var7, (float)var11, (float)var15);
         GlStateManager.rotate(-this.mc.getRenderManager().playerViewY, 0.0F, 1.0F, 0.0F);
         float var17 = this.mc.gameSettings.thirdPersonView == 2?-1.0F:1.0F;
         GlStateManager.rotate(this.mc.getRenderManager().playerViewX, var17, 0.0F, 0.0F);
         double var18 = 0.03D;
         GlStateManager.scale(-0.03D, -0.03D, 0.03D);
         RenderUtils.enableGL2D();
         RenderUtils.disableGL2D();
         GL11.glDepthMask(false);
         this.mc.fontRendererObj.drawStringWithShadow(var4.text, (float)(-(this.mc.fontRendererObj.d(var4.text) / 2)), (float)(-(this.mc.fontRendererObj.getHeight() - 1)), 0);
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

   @EventTarget
   public void onUpdate(PlayerUpdateEvent var1) {
      this.particles.forEach(this::lambda$onUpdate$0);
   }

   private void lambda$onUpdate$0(DMGParticle$Particles var1) {
      HUD.h();
      ++var1.ticks;
      if(var1.ticks <= 10) {
         var1.location.setY(var1.location.getY() + (double)var1.ticks * 0.005D);
      }

      if(var1.ticks > 20) {
         this.particles.remove(var1);
      }

   }

   private static IllegalArgumentException a(IllegalArgumentException var0) {
      return var0;
   }
}
