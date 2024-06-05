package cc.novoline.utils;

import cc.novoline.Novoline;
import cc.novoline.modules.PlayerManager$EnumPlayerType;
import cc.novoline.modules.visual.ESP;
import cc.novoline.utils.PlayerUtils;
import cc.novoline.utils.Timer;
import java.awt.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import org.lwjgl.opengl.EXTFramebufferObject;
import org.lwjgl.opengl.GL11;

public final class OutlineUtils {
   private OutlineUtils() {
      throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
   }

   public static void renderOne() {
      checkSetupFBO();
      GL11.glPushAttrib(1048575);
      GL11.glDisable(3008);
      GL11.glDisable(3553);
      GL11.glDisable(2896);
      GL11.glEnable(3042);
      GL11.glBlendFunc(770, 771);
      GL11.glLineWidth(3.0F);
      GL11.glEnable(2848);
      GL11.glEnable(2960);
      GL11.glClear(1024);
      GL11.glClearStencil(15);
      GL11.glStencilFunc(512, 1, 15);
      GL11.glStencilOp(7681, 7681, 7681);
      GL11.glPolygonMode(1032, 6913);
   }

   public static void renderTwo() {
      GL11.glStencilFunc(512, 0, 15);
      GL11.glStencilOp(7681, 7681, 7681);
      GL11.glPolygonMode(1032, 6914);
   }

   public static void renderThree() {
      GL11.glStencilFunc(514, 1, 15);
      GL11.glStencilOp(7680, 7680, 7680);
      GL11.glPolygonMode(1032, 6913);
   }

   public static Color a(Color var0, int var1) {
      return new Color(var0.getRed(), var0.getGreen(), var0.getBlue(), var1);
   }

   public static void a(ESP var0, Entity var1) {
      label0: {
         String[] var2 = Timer.e();
         if(var1 instanceof EntityPlayer) {
            if(Novoline.getInstance().getPlayerManager().hasType(var1.getName(), PlayerManager$EnumPlayerType.TARGET)) {
               setColor(a(var0.b, ((Integer)var0.a().get()).intValue()));
            }

            if(Novoline.getInstance().getPlayerManager().hasType(var1.getName(), PlayerManager$EnumPlayerType.FRIEND)) {
               setColor(a(var0.c, ((Integer)var0.a().get()).intValue()));
            }

            if(var0.k().equals("Team")) {
               setColor(PlayerUtils.c(var1));
            }

            if(var0.k().equals("Static")) {
               a(var0.j()[0], var0.j()[1], var0.j()[2], (float)((Integer)var0.a().get()).intValue());
            }

            if(var0.k().equals("Rainbow")) {
               a(var0.f()[0], var0.f()[1], var0.f()[2], (float)((Integer)var0.a().get()).intValue());
            }

            if(!var0.k().equals("Astolfo")) {
               break label0;
            }

            a(var0.h()[0], var0.h()[1], var0.h()[2], (float)((Integer)var0.a().get()).intValue());
         }

         if(var0.k().equals("Static") || var0.k().equals("Team")) {
            a(var0.j()[0], var0.j()[1], var0.j()[2], (float)((Integer)var0.a().get()).intValue());
         }

         if(var0.k().equals("Rainbow")) {
            a(var0.f()[0], var0.f()[1], var0.f()[2], (float)((Integer)var0.a().get()).intValue());
         }

         if(var0.k().equals("Astolfo")) {
            a(var0.h()[0], var0.h()[1], var0.h()[2], (float)((Integer)var0.a().get()).intValue());
         }
      }

      GL11.glDepthMask(false);
      GL11.glDisable(2929);
      GL11.glEnable(10754);
      GL11.glPolygonOffset(1.0F, -2000000.0F);
      OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0F, 240.0F);
   }

   public static void renderFive() {
      GL11.glPolygonOffset(1.0F, 2000000.0F);
      GL11.glDisable(10754);
      GL11.glEnable(2929);
      GL11.glDepthMask(true);
      GL11.glDisable(2960);
      GL11.glDisable(2848);
      GL11.glHint(3154, 4352);
      GL11.glEnable(3042);
      GL11.glEnable(2896);
      GL11.glEnable(3553);
      GL11.glEnable(3008);
      GL11.glPopAttrib();
   }

   public static void setColor(Color var0) {
      GL11.glColor4d((double)((float)var0.getRed() / 255.0F), (double)((float)var0.getGreen() / 255.0F), (double)((float)var0.getBlue() / 255.0F), (double)((float)var0.getAlpha() / 255.0F));
   }

   public static void a(int var0, int var1, int var2, float var3) {
      GL11.glColor4d((double)(0.003921569F * (float)var0), (double)(0.003921569F * (float)var1), (double)(0.003921569F * (float)var2), (double)(0.003921569F * var3));
   }

   public static void checkSetupFBO() {
      Timer.e();
      Framebuffer var1 = Minecraft.getInstance().getFramebuffer();
      if(var1 != null && var1.depthBuffer > -1) {
         setupFBO(var1);
         var1.depthBuffer = -1;
      }

   }

   public static void setupFBO(Framebuffer var0) {
      EXTFramebufferObject.glDeleteRenderbuffersEXT(var0.depthBuffer);
      int var1 = EXTFramebufferObject.glGenRenderbuffersEXT();
      EXTFramebufferObject.glBindRenderbufferEXT('赁', var1);
      EXTFramebufferObject.glRenderbufferStorageEXT('赁', '蓹', Minecraft.getInstance().displayWidth, Minecraft.getInstance().displayHeight);
      EXTFramebufferObject.glFramebufferRenderbufferEXT('赀', '贠', '赁', var1);
      EXTFramebufferObject.glFramebufferRenderbufferEXT('赀', '贀', '赁', var1);
   }

   private static UnsupportedOperationException a(UnsupportedOperationException var0) {
      return var0;
   }
}
