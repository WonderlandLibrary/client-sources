package exhibition.util.render;

import java.util.HashMap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.Timer;
import org.lwjgl.opengl.EXTFramebufferObject;
import org.lwjgl.opengl.GL11;

public class GayShit {
   public static class Camera {
      private final Minecraft mc = Minecraft.getMinecraft();
      private Timer timer;
      private double posX;
      private double posY;
      private double posZ;
      private float rotationYaw;
      private float rotationPitch;

      public Camera(Entity entity) {
         if (this.timer == null) {
            this.timer = this.mc.timer;
         }

         this.posX = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * (double)this.timer.renderPartialTicks;
         this.posY = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * (double)this.timer.renderPartialTicks;
         this.posZ = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * (double)this.timer.renderPartialTicks;
         this.setRotationYaw(entity.rotationYaw);
         this.setRotationPitch(entity.rotationPitch);
         if (entity instanceof EntityPlayer && Minecraft.getMinecraft().gameSettings.viewBobbing && entity == Minecraft.getMinecraft().thePlayer) {
            EntityPlayer living1 = (EntityPlayer)entity;
            this.setRotationYaw(this.getRotationYaw() + living1.prevCameraYaw + (living1.cameraYaw - living1.prevCameraYaw) * this.timer.renderPartialTicks);
            this.setRotationPitch(this.getRotationPitch() + living1.prevCameraPitch + (living1.cameraPitch - living1.prevCameraPitch) * this.timer.renderPartialTicks);
         } else if (entity instanceof EntityLivingBase) {
            EntityLivingBase living2 = (EntityLivingBase)entity;
            this.setRotationYaw(living2.rotationYawHead);
         }

      }

      public Camera(Entity entity, double offsetX, double offsetY, double offsetZ, double offsetRotationYaw, double offsetRotationPitch) {
         this.posX = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * (double)this.timer.renderPartialTicks;
         this.posY = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * (double)this.timer.renderPartialTicks;
         this.posZ = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * (double)this.timer.renderPartialTicks;
         this.setRotationYaw(entity.rotationYaw);
         this.setRotationPitch(entity.rotationPitch);
         if (entity instanceof EntityPlayer && Minecraft.getMinecraft().gameSettings.viewBobbing && entity == Minecraft.getMinecraft().thePlayer) {
            EntityPlayer player = (EntityPlayer)entity;
            this.setRotationYaw(this.getRotationYaw() + player.prevCameraYaw + (player.cameraYaw - player.prevCameraYaw) * this.timer.renderPartialTicks);
            this.setRotationPitch(this.getRotationPitch() + player.prevCameraPitch + (player.cameraPitch - player.prevCameraPitch) * this.timer.renderPartialTicks);
         }

         this.posX += offsetX;
         this.posY += offsetY;
         this.posZ += offsetZ;
         this.rotationYaw += (float)offsetRotationYaw;
         this.rotationPitch += (float)offsetRotationPitch;
      }

      public Camera(double posX, double posY, double posZ, float rotationYaw, float rotationPitch) {
         this.setPosX(posX);
         this.posY = posY;
         this.posZ = posZ;
         this.setRotationYaw(rotationYaw);
         this.setRotationPitch(rotationPitch);
      }

      public double getPosX() {
         return this.posX;
      }

      public void setPosX(double posX) {
         this.posX = posX;
      }

      public double getPosY() {
         return this.posY;
      }

      public void setPosY(double posY) {
         this.posY = posY;
      }

      public double getPosZ() {
         return this.posZ;
      }

      public void setPosZ(double posZ) {
         this.posZ = posZ;
      }

      public float getRotationYaw() {
         return this.rotationYaw;
      }

      public void setRotationYaw(float rotationYaw) {
         this.rotationYaw = rotationYaw;
      }

      public float getRotationPitch() {
         return this.rotationPitch;
      }

      public void setRotationPitch(float rotationPitch) {
         this.rotationPitch = rotationPitch;
      }

      public static float[] getRotation(double posX1, double posY1, double posZ1, double posX2, double posY2, double posZ2) {
         float[] rotation = new float[2];
         double diffX = posX2 - posX1;
         double diffZ = posZ2 - posZ1;
         double diffY = posY2 - posY1;
         double dist = Math.sqrt(diffZ * diffZ + diffX * diffX);
         double pitch = -Math.toDegrees(Math.atan(diffY / dist));
         rotation[1] = (float)pitch;
         double yaw = 0.0D;
         if (diffZ >= 0.0D && diffX >= 0.0D) {
            yaw = Math.toDegrees(-Math.atan(diffX / diffZ));
         } else if (diffZ >= 0.0D && diffX <= 0.0D) {
            yaw = Math.toDegrees(-Math.atan(diffX / diffZ));
         } else if (diffZ <= 0.0D && diffX >= 0.0D) {
            yaw = -90.0D + Math.toDegrees(Math.atan(diffZ / diffX));
         } else if (diffZ <= 0.0D && diffX <= 0.0D) {
            yaw = 90.0D + Math.toDegrees(Math.atan(diffZ / diffX));
         }

         rotation[0] = (float)yaw;
         return rotation;
      }
   }

   public static final class Stencil {
      private static final GayShit.Stencil INSTANCE = new GayShit.Stencil();
      private final HashMap stencilFuncs = new HashMap();
      private int layers = 1;
      private boolean renderMask;

      public static GayShit.Stencil getInstance() {
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
               GayShit.Stencil.StencilFunc lastStencilFunc = (GayShit.Stencil.StencilFunc)this.stencilFuncs.remove(this.layers);
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
         this.setStencilFunc(new GayShit.Stencil.StencilFunc(this, this.renderMask ? 519 : 512, this.layers, this.getMaximumLayers(), 7681, 7680, 7680));
      }

      public void setBuffer(boolean set) {
         this.setStencilFunc(new GayShit.Stencil.StencilFunc(this, this.renderMask ? 519 : 512, set ? this.layers : this.layers - 1, this.getMaximumLayers(), 7681, 7681, 7681));
      }

      public void cropOutside() {
         this.setStencilFunc(new GayShit.Stencil.StencilFunc(this, 517, this.layers, this.getMaximumLayers(), 7680, 7680, 7680));
      }

      public void cropInside() {
         this.setStencilFunc(new GayShit.Stencil.StencilFunc(this, 514, this.layers, this.getMaximumLayers(), 7680, 7680, 7680));
      }

      public void setStencilFunc(GayShit.Stencil.StencilFunc stencilFunc) {
         GL11.glStencilFunc(GayShit.Stencil.StencilFunc.func_func, GayShit.Stencil.StencilFunc.func_ref, GayShit.Stencil.StencilFunc.func_mask);
         GL11.glStencilOp(GayShit.Stencil.StencilFunc.op_fail, GayShit.Stencil.StencilFunc.op_zfail, GayShit.Stencil.StencilFunc.op_zpass);
         this.stencilFuncs.put(this.layers, stencilFunc);
      }

      public GayShit.Stencil.StencilFunc getStencilFunc() {
         return (GayShit.Stencil.StencilFunc)this.stencilFuncs.get(this.layers);
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

         public StencilFunc(GayShit.Stencil paramStencil, int func_func, int func_ref, int func_mask, int op_fail, int op_zfail, int op_zpass) {
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
}
