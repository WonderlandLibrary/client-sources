package exhibition.util.render;

import java.util.HashMap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.shader.Framebuffer;
import org.lwjgl.opengl.EXTFramebufferObject;
import org.lwjgl.opengl.GL11;

public final class Stencil {
   private static final Stencil INSTANCE = new Stencil();
   private final HashMap stencilFuncs = new HashMap();
   private int layers = 1;
   private boolean renderMask;

   public static Stencil getInstance() {
      return INSTANCE;
   }

   public void setRenderMask(boolean renderMask) {
      this.renderMask = renderMask;
   }

   public static void checkSetupFBO() {
      Framebuffer fbo = Minecraft.getMinecraft().getFramebuffer();
      if (fbo != null && fbo.depthBuffer > -1) {
         EXTFramebufferObject.glDeleteRenderbuffersEXT(fbo.depthBuffer);
         int stencil_depth_buffer_ID = EXTFramebufferObject.glGenRenderbuffersEXT();
         EXTFramebufferObject.glBindRenderbufferEXT(36161, stencil_depth_buffer_ID);
         EXTFramebufferObject.glRenderbufferStorageEXT(36161, 34041, Minecraft.getMinecraft().displayWidth, Minecraft.getMinecraft().displayHeight);
         EXTFramebufferObject.glFramebufferRenderbufferEXT(36160, 36128, 36161, stencil_depth_buffer_ID);
         EXTFramebufferObject.glFramebufferRenderbufferEXT(36160, 36096, 36161, stencil_depth_buffer_ID);
         fbo.depthBuffer = -1;
      }

   }

   public static void setupFBO(Framebuffer fbo) {
      EXTFramebufferObject.glDeleteRenderbuffersEXT(fbo.depthBuffer);
      int stencil_depth_buffer_ID = EXTFramebufferObject.glGenRenderbuffersEXT();
      EXTFramebufferObject.glBindRenderbufferEXT(36161, stencil_depth_buffer_ID);
      EXTFramebufferObject.glRenderbufferStorageEXT(36161, 34041, Minecraft.getMinecraft().displayWidth, Minecraft.getMinecraft().displayHeight);
      EXTFramebufferObject.glFramebufferRenderbufferEXT(36160, 36128, 36161, stencil_depth_buffer_ID);
      EXTFramebufferObject.glFramebufferRenderbufferEXT(36160, 36096, 36161, stencil_depth_buffer_ID);
   }

   public void startLayer() {
      if (this.layers == 1) {
         GL11.glClearStencil(0);
         GL11.glClear(1024);
      }

      GL11.glEnable(2960);
      ++this.layers;
      if (this.layers > this.getMaximumLayers()) {
         System.out.println("StencilUtil: Reached maximum amount of layers!");
         this.layers = 1;
      }

   }

   public void stopLayer() {
      if (this.layers == 1) {
         System.out.println("StencilUtil: No layers found!");
      } else {
         --this.layers;
         if (this.layers == 1) {
            GL11.glDisable(2960);
         } else {
            Stencil.StencilFunc lastStencilFunc = (Stencil.StencilFunc)this.stencilFuncs.remove(this.layers);
            if (lastStencilFunc != null) {
               lastStencilFunc.use();
            }
         }

      }
   }

   public void clear() {
      GL11.glClearStencil(0);
      GL11.glClear(1024);
      this.stencilFuncs.clear();
      this.layers = 1;
   }

   public void setBuffer() {
      this.setStencilFunc(new Stencil.StencilFunc(this, this.renderMask ? 519 : 512, this.layers, this.getMaximumLayers(), 7681, 7680, 7680));
   }

   public void setBuffer(boolean set) {
      this.setStencilFunc(new Stencil.StencilFunc(this, this.renderMask ? 519 : 512, set ? this.layers : this.layers - 1, this.getMaximumLayers(), 7681, 7681, 7681));
   }

   public void cropOutside() {
      this.setStencilFunc(new Stencil.StencilFunc(this, 517, this.layers, this.getMaximumLayers(), 7680, 7680, 7680));
   }

   public void cropInside() {
      this.setStencilFunc(new Stencil.StencilFunc(this, 514, this.layers, this.getMaximumLayers(), 7680, 7680, 7680));
   }

   public void setStencilFunc(Stencil.StencilFunc stencilFunc) {
      GL11.glStencilFunc(Stencil.StencilFunc.func_func, Stencil.StencilFunc.func_ref, Stencil.StencilFunc.func_mask);
      GL11.glStencilOp(Stencil.StencilFunc.op_fail, Stencil.StencilFunc.op_zfail, Stencil.StencilFunc.op_zpass);
      this.stencilFuncs.put(this.layers, stencilFunc);
   }

   public Stencil.StencilFunc getStencilFunc() {
      return (Stencil.StencilFunc)this.stencilFuncs.get(this.layers);
   }

   public int getLayer() {
      return this.layers;
   }

   public int getStencilBufferSize() {
      return GL11.glGetInteger(3415);
   }

   public int getMaximumLayers() {
      return (int)(Math.pow(2.0D, (double)this.getStencilBufferSize()) - 1.0D);
   }

   public void createCirlce(double x, double y, double radius) {
      GL11.glBegin(6);

      for(int i = 0; i <= 360; ++i) {
         double sin = Math.sin((double)i * 3.141592653589793D / 180.0D) * radius;
         double cos = Math.cos((double)i * 3.141592653589793D / 180.0D) * radius;
         GL11.glVertex2d(x + sin, y + cos);
      }

      GL11.glEnd();
   }

   public void createRect(double x, double y, double x2, double y2) {
      GL11.glBegin(7);
      GL11.glVertex2d(x, y2);
      GL11.glVertex2d(x2, y2);
      GL11.glVertex2d(x2, y);
      GL11.glVertex2d(x, y);
      GL11.glEnd();
   }

   public static class StencilFunc {
      public static int func_func;
      public static int func_ref;
      public static int func_mask;
      public static int op_fail;
      public static int op_zfail;
      public static int op_zpass;

      public StencilFunc(Stencil paramStencil, int func_func, int func_ref, int func_mask, int op_fail, int op_zfail, int op_zpass) {
         func_func = func_func;
         func_ref = func_ref;
         func_mask = func_mask;
         op_fail = op_fail;
         op_zfail = op_zfail;
         op_zpass = op_zpass;
      }

      public void use() {
         GL11.glStencilFunc(func_func, func_ref, func_mask);
         GL11.glStencilOp(op_fail, op_zfail, op_zpass);
      }
   }
}
