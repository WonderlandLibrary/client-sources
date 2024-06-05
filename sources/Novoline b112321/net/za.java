package net;

import cc.novoline.utils.Timer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.shader.Framebuffer;
import org.lwjgl.opengl.EXTFramebufferObject;
import org.lwjgl.opengl.GL11;

public class za {
   public static void b(boolean var0) {
      a();
      Timer.e();
      GL11.glClearStencil(0);
      GL11.glClear(1024);
      GL11.glEnable(2960);
      GL11.glStencilFunc(518, 1, 1);
      GL11.glStencilOp(7680, 7680, 7681);
      if(!var0) {
         GlStateManager.colorMask(false, false, false, false);
      }

   }

   public static void a(boolean var0) {
      String[] var1 = Timer.e();
      GL11.glStencilFunc(var0?515:517, 1, 1);
      GL11.glStencilOp(7680, 7680, 7680);
      GlStateManager.colorMask(true, true, true, true);
      GlStateManager.enableBlend();
   }

   public static void b() {
      GL11.glDisable(2960);
      GlStateManager.enableBlend();
      GlStateManager.blendFunc(770, 771);
   }

   public static void a() {
      Timer.e();
      Framebuffer var1 = Minecraft.getInstance().getFramebuffer();
      if(var1 != null && var1.depthBuffer > -1) {
         a(var1);
         var1.depthBuffer = -1;
      }

   }

   public static void a(Framebuffer var0) {
      EXTFramebufferObject.glDeleteRenderbuffersEXT(var0.depthBuffer);
      int var1 = EXTFramebufferObject.glGenRenderbuffersEXT();
      EXTFramebufferObject.glBindRenderbufferEXT('赁', var1);
      EXTFramebufferObject.glRenderbufferStorageEXT('赁', '蓹', Minecraft.getInstance().displayWidth, Minecraft.getInstance().displayHeight);
      EXTFramebufferObject.glFramebufferRenderbufferEXT('赀', '贠', '赁', var1);
      EXTFramebufferObject.glFramebufferRenderbufferEXT('赀', '贀', '赁', var1);
   }
}
