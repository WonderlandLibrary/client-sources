package com.example.editme.modules.render;

import com.example.editme.events.RenderEntityModelEvent;
import com.example.editme.events.RenderEvent;
import com.example.editme.modules.Module;
import com.example.editme.settings.Setting;
import com.example.editme.util.client.MathUtil;
import com.example.editme.util.render.OutlineUtils;
import com.example.editme.util.setting.SettingsManager;
import java.awt.Color;
import java.util.Objects;
import java.util.function.Predicate;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.opengl.GL11;

@Module.Info(
   name = "ESP",
   category = Module.Category.RENDER
)
public class ESP extends Module {
   private Setting box = this.register(SettingsManager.b("Box", false));
   private Setting green = this.register(SettingsManager.integerBuilder("Green").withMinimum(1).withMaximum(255).withValue((int)0).build());
   @EventHandler
   private final Listener renderEntityModelEvent = new Listener(this::lambda$new$2, new Predicate[0]);
   private static final Frustum frustrum = new Frustum();
   private Setting rectangle = this.register(SettingsManager.b("Rectangle", false));
   private Setting outlineWidth = this.register(SettingsManager.integerBuilder("Outline Width").withMinimum(1).withMaximum(10).withValue((int)2).withVisibility(this::lambda$new$0).build());
   private Setting blue = this.register(SettingsManager.integerBuilder("Blue").withMinimum(1).withMaximum(255).withValue((int)255).build());
   private Setting outline = this.register(SettingsManager.b("Outline", false));
   private Setting red = this.register(SettingsManager.integerBuilder("Red").withMinimum(1).withMaximum(255).withValue((int)255).build());

   private static void drawBox(AxisAlignedBB var0) {
      if (var0 != null) {
         GlStateManager.func_187447_r(7);
         GlStateManager.func_187435_e((float)var0.field_72340_a, (float)var0.field_72338_b, (float)var0.field_72334_f);
         GlStateManager.func_187435_e((float)var0.field_72336_d, (float)var0.field_72338_b, (float)var0.field_72334_f);
         GlStateManager.func_187435_e((float)var0.field_72336_d, (float)var0.field_72337_e, (float)var0.field_72334_f);
         GlStateManager.func_187435_e((float)var0.field_72340_a, (float)var0.field_72337_e, (float)var0.field_72334_f);
         GlStateManager.func_187437_J();
         GlStateManager.func_187447_r(7);
         GlStateManager.func_187435_e((float)var0.field_72336_d, (float)var0.field_72338_b, (float)var0.field_72334_f);
         GlStateManager.func_187435_e((float)var0.field_72340_a, (float)var0.field_72338_b, (float)var0.field_72334_f);
         GlStateManager.func_187435_e((float)var0.field_72340_a, (float)var0.field_72337_e, (float)var0.field_72334_f);
         GlStateManager.func_187435_e((float)var0.field_72336_d, (float)var0.field_72337_e, (float)var0.field_72334_f);
         GlStateManager.func_187437_J();
         GlStateManager.func_187447_r(7);
         GlStateManager.func_187435_e((float)var0.field_72340_a, (float)var0.field_72338_b, (float)var0.field_72339_c);
         GlStateManager.func_187435_e((float)var0.field_72340_a, (float)var0.field_72338_b, (float)var0.field_72334_f);
         GlStateManager.func_187435_e((float)var0.field_72340_a, (float)var0.field_72337_e, (float)var0.field_72334_f);
         GlStateManager.func_187435_e((float)var0.field_72340_a, (float)var0.field_72337_e, (float)var0.field_72339_c);
         GlStateManager.func_187437_J();
         GlStateManager.func_187447_r(7);
         GlStateManager.func_187435_e((float)var0.field_72340_a, (float)var0.field_72338_b, (float)var0.field_72334_f);
         GlStateManager.func_187435_e((float)var0.field_72340_a, (float)var0.field_72338_b, (float)var0.field_72339_c);
         GlStateManager.func_187435_e((float)var0.field_72340_a, (float)var0.field_72337_e, (float)var0.field_72339_c);
         GlStateManager.func_187435_e((float)var0.field_72340_a, (float)var0.field_72337_e, (float)var0.field_72334_f);
         GlStateManager.func_187437_J();
         GlStateManager.func_187447_r(7);
         GlStateManager.func_187435_e((float)var0.field_72336_d, (float)var0.field_72338_b, (float)var0.field_72334_f);
         GlStateManager.func_187435_e((float)var0.field_72336_d, (float)var0.field_72338_b, (float)var0.field_72339_c);
         GlStateManager.func_187435_e((float)var0.field_72336_d, (float)var0.field_72337_e, (float)var0.field_72339_c);
         GlStateManager.func_187435_e((float)var0.field_72336_d, (float)var0.field_72337_e, (float)var0.field_72334_f);
         GlStateManager.func_187437_J();
         GlStateManager.func_187447_r(7);
         GlStateManager.func_187435_e((float)var0.field_72336_d, (float)var0.field_72338_b, (float)var0.field_72339_c);
         GlStateManager.func_187435_e((float)var0.field_72336_d, (float)var0.field_72338_b, (float)var0.field_72334_f);
         GlStateManager.func_187435_e((float)var0.field_72336_d, (float)var0.field_72337_e, (float)var0.field_72334_f);
         GlStateManager.func_187435_e((float)var0.field_72336_d, (float)var0.field_72337_e, (float)var0.field_72339_c);
         GlStateManager.func_187437_J();
         GlStateManager.func_187447_r(7);
         GlStateManager.func_187435_e((float)var0.field_72340_a, (float)var0.field_72338_b, (float)var0.field_72339_c);
         GlStateManager.func_187435_e((float)var0.field_72336_d, (float)var0.field_72338_b, (float)var0.field_72339_c);
         GlStateManager.func_187435_e((float)var0.field_72336_d, (float)var0.field_72337_e, (float)var0.field_72339_c);
         GlStateManager.func_187435_e((float)var0.field_72340_a, (float)var0.field_72337_e, (float)var0.field_72339_c);
         GlStateManager.func_187437_J();
         GlStateManager.func_187447_r(7);
         GlStateManager.func_187435_e((float)var0.field_72336_d, (float)var0.field_72338_b, (float)var0.field_72339_c);
         GlStateManager.func_187435_e((float)var0.field_72340_a, (float)var0.field_72338_b, (float)var0.field_72339_c);
         GlStateManager.func_187435_e((float)var0.field_72340_a, (float)var0.field_72337_e, (float)var0.field_72339_c);
         GlStateManager.func_187435_e((float)var0.field_72336_d, (float)var0.field_72337_e, (float)var0.field_72339_c);
         GlStateManager.func_187437_J();
         GlStateManager.func_187447_r(7);
         GlStateManager.func_187435_e((float)var0.field_72340_a, (float)var0.field_72337_e, (float)var0.field_72339_c);
         GlStateManager.func_187435_e((float)var0.field_72336_d, (float)var0.field_72337_e, (float)var0.field_72339_c);
         GlStateManager.func_187435_e((float)var0.field_72336_d, (float)var0.field_72337_e, (float)var0.field_72334_f);
         GlStateManager.func_187435_e((float)var0.field_72340_a, (float)var0.field_72337_e, (float)var0.field_72334_f);
         GlStateManager.func_187437_J();
         GlStateManager.func_187447_r(7);
         GlStateManager.func_187435_e((float)var0.field_72336_d, (float)var0.field_72337_e, (float)var0.field_72339_c);
         GlStateManager.func_187435_e((float)var0.field_72340_a, (float)var0.field_72337_e, (float)var0.field_72339_c);
         GlStateManager.func_187435_e((float)var0.field_72340_a, (float)var0.field_72337_e, (float)var0.field_72334_f);
         GlStateManager.func_187435_e((float)var0.field_72336_d, (float)var0.field_72337_e, (float)var0.field_72334_f);
         GlStateManager.func_187437_J();
         GlStateManager.func_187447_r(7);
         GlStateManager.func_187435_e((float)var0.field_72340_a, (float)var0.field_72338_b, (float)var0.field_72339_c);
         GlStateManager.func_187435_e((float)var0.field_72336_d, (float)var0.field_72338_b, (float)var0.field_72339_c);
         GlStateManager.func_187435_e((float)var0.field_72336_d, (float)var0.field_72338_b, (float)var0.field_72334_f);
         GlStateManager.func_187435_e((float)var0.field_72340_a, (float)var0.field_72338_b, (float)var0.field_72334_f);
         GlStateManager.func_187437_J();
         GlStateManager.func_187447_r(7);
         GlStateManager.func_187435_e((float)var0.field_72336_d, (float)var0.field_72338_b, (float)var0.field_72339_c);
         GlStateManager.func_187435_e((float)var0.field_72340_a, (float)var0.field_72338_b, (float)var0.field_72339_c);
         GlStateManager.func_187435_e((float)var0.field_72340_a, (float)var0.field_72338_b, (float)var0.field_72334_f);
         GlStateManager.func_187435_e((float)var0.field_72336_d, (float)var0.field_72338_b, (float)var0.field_72334_f);
         GlStateManager.func_187437_J();
      }
   }

   private void lambda$onWorldRender$1(RenderEvent var1, Entity var2) {
      if (var2 != null && var2.func_70089_S() && (isInViewFrustrum(var2.func_174813_aQ()) || var2.field_70158_ak) && var2 != mc.field_71439_g && var2 instanceof EntityPlayer) {
         Color var3 = new Color((Integer)this.red.getValue(), (Integer)this.green.getValue(), (Integer)this.blue.getValue());
         Vec3d var4 = MathUtil.interpolateEntity(var2, var1.getPartialTicks());
         double var5 = var4.field_72450_a - mc.func_175598_ae().field_78725_b;
         double var7 = var4.field_72448_b - mc.func_175598_ae().field_78726_c;
         double var9 = var4.field_72449_c - mc.func_175598_ae().field_78723_d;
         float var11 = 40.0F;
         if ((Boolean)this.rectangle.getValue()) {
            if (var2 instanceof EntityLivingBase) {
               EntityLivingBase var12 = (EntityLivingBase)var2;
               if (var12.field_70737_aN > 0) {
                  var11 += 10.0F;
               }
            }

            drawESP((new AxisAlignedBB(0.0D, 0.0D, 0.0D, (double)var2.field_70130_N, (double)var2.field_70131_O, (double)var2.field_70130_N)).func_72317_d(var5 - (double)(var2.field_70130_N / 2.0F), var7, var9 - (double)(var2.field_70130_N / 2.0F)), (float)var3.getRed(), (float)var3.getGreen(), (float)var3.getBlue(), var11);
         }

         if ((Boolean)this.box.getValue()) {
            drawESPOutline((new AxisAlignedBB(0.0D, 0.0D, 0.0D, (double)var2.field_70130_N, (double)var2.field_70131_O, (double)var2.field_70130_N)).func_72317_d(var5 - (double)(var2.field_70130_N / 2.0F), var7, var9 - (double)(var2.field_70130_N / 2.0F)), (float)var3.getRed(), (float)var3.getGreen(), (float)var3.getBlue(), 255.0F, 1.0F);
         }
      }

   }

   private void lambda$new$2(RenderEntityModelEvent var1) {
      if (mc.field_71441_e != null && mc.field_71439_g != null) {
         if (var1.entity instanceof EntityLivingBase && (Boolean)this.outline.getValue()) {
            Color var2 = new Color((Integer)this.red.getValue(), (Integer)this.green.getValue(), (Integer)this.blue.getValue());
            OutlineUtils.renderOne((float)(Integer)this.outlineWidth.getValue());
            var1.modelBase.func_78088_a(var1.entity, var1.limbSwing, var1.limbSwingAmount, var1.age, var1.headYaw, var1.headPitch, var1.scale);
            OutlineUtils.renderTwo();
            var1.modelBase.func_78088_a(var1.entity, var1.limbSwing, var1.limbSwingAmount, var1.age, var1.headYaw, var1.headPitch, var1.scale);
            OutlineUtils.renderThree();
            OutlineUtils.renderFour(var2);
            var1.modelBase.func_78088_a(var1.entity, var1.limbSwing, var1.limbSwingAmount, var1.age, var1.headYaw, var1.headPitch, var1.scale);
            OutlineUtils.renderFive();
         }

      }
   }

   public void onWorldRender(RenderEvent var1) {
      mc.field_71441_e.field_72996_f.forEach(this::lambda$onWorldRender$1);
   }

   private boolean lambda$new$0(Integer var1) {
      return (Boolean)this.outline.getValue();
   }

   private static void drawESPOutline(AxisAlignedBB var0, float var1, float var2, float var3, float var4, float var5) {
      GL11.glPushMatrix();
      GL11.glEnable(3042);
      GL11.glBlendFunc(770, 771);
      GL11.glDisable(3553);
      GL11.glEnable(2848);
      GL11.glDisable(2929);
      GL11.glDepthMask(false);
      GL11.glLineWidth(var5);
      GL11.glColor4f(var1 / 255.0F, var2 / 255.0F, var3 / 255.0F, var4 / 255.0F);
      drawOutlinedBox(var0);
      GL11.glDisable(2848);
      GL11.glEnable(3553);
      GL11.glEnable(2929);
      GL11.glDepthMask(true);
      GL11.glDisable(3042);
      GL11.glPopMatrix();
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
   }

   private boolean isValid(EntityLivingBase var1) {
      return mc.field_71439_g != var1 && var1.func_145782_y() != -1488 && var1.func_70089_S();
   }

   public static boolean isInViewFrustrum(AxisAlignedBB var0) {
      Entity var1 = mc.func_175606_aa();
      frustrum.func_78547_a(((Entity)Objects.requireNonNull(var1)).field_70165_t, var1.field_70163_u, var1.field_70161_v);
      return frustrum.func_78546_a(var0);
   }

   private static void drawOutlinedBox(AxisAlignedBB var0) {
      GL11.glBegin(1);
      GL11.glVertex3d(var0.field_72340_a, var0.field_72338_b, var0.field_72339_c);
      GL11.glVertex3d(var0.field_72336_d, var0.field_72338_b, var0.field_72339_c);
      GL11.glVertex3d(var0.field_72336_d, var0.field_72338_b, var0.field_72339_c);
      GL11.glVertex3d(var0.field_72336_d, var0.field_72338_b, var0.field_72334_f);
      GL11.glVertex3d(var0.field_72336_d, var0.field_72338_b, var0.field_72334_f);
      GL11.glVertex3d(var0.field_72340_a, var0.field_72338_b, var0.field_72334_f);
      GL11.glVertex3d(var0.field_72340_a, var0.field_72338_b, var0.field_72334_f);
      GL11.glVertex3d(var0.field_72340_a, var0.field_72338_b, var0.field_72339_c);
      GL11.glVertex3d(var0.field_72340_a, var0.field_72338_b, var0.field_72339_c);
      GL11.glVertex3d(var0.field_72340_a, var0.field_72337_e, var0.field_72339_c);
      GL11.glVertex3d(var0.field_72336_d, var0.field_72338_b, var0.field_72339_c);
      GL11.glVertex3d(var0.field_72336_d, var0.field_72337_e, var0.field_72339_c);
      GL11.glVertex3d(var0.field_72336_d, var0.field_72338_b, var0.field_72334_f);
      GL11.glVertex3d(var0.field_72336_d, var0.field_72337_e, var0.field_72334_f);
      GL11.glVertex3d(var0.field_72340_a, var0.field_72338_b, var0.field_72334_f);
      GL11.glVertex3d(var0.field_72340_a, var0.field_72337_e, var0.field_72334_f);
      GL11.glVertex3d(var0.field_72340_a, var0.field_72337_e, var0.field_72339_c);
      GL11.glVertex3d(var0.field_72336_d, var0.field_72337_e, var0.field_72339_c);
      GL11.glVertex3d(var0.field_72336_d, var0.field_72337_e, var0.field_72339_c);
      GL11.glVertex3d(var0.field_72336_d, var0.field_72337_e, var0.field_72334_f);
      GL11.glVertex3d(var0.field_72336_d, var0.field_72337_e, var0.field_72334_f);
      GL11.glVertex3d(var0.field_72340_a, var0.field_72337_e, var0.field_72334_f);
      GL11.glVertex3d(var0.field_72340_a, var0.field_72337_e, var0.field_72334_f);
      GL11.glVertex3d(var0.field_72340_a, var0.field_72337_e, var0.field_72339_c);
      GL11.glEnd();
   }

   private static void drawESP(AxisAlignedBB var0, float var1, float var2, float var3, float var4) {
      GL11.glPushMatrix();
      GL11.glEnable(3042);
      GL11.glBlendFunc(770, 771);
      GL11.glDisable(3553);
      GL11.glEnable(2848);
      GL11.glDisable(2929);
      GL11.glDepthMask(false);
      GL11.glColor4f(var1 / 255.0F, var2 / 255.0F, var3 / 255.0F, var4 / 255.0F);
      drawBox(var0);
      GL11.glDisable(2848);
      GL11.glEnable(3553);
      GL11.glEnable(2929);
      GL11.glDepthMask(true);
      GL11.glDisable(3042);
      GL11.glPopMatrix();
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
   }
}
