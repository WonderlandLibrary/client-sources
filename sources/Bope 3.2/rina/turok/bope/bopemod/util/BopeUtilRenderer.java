package rina.turok.bope.bopemod.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.opengl.GL11;
import rina.turok.turok.draw.TurokRenderHelp;

public class BopeUtilRenderer {
   public static final Minecraft mc = Minecraft.getMinecraft();

   public static void EntityCSGOESP(Entity entities, int r, int g, int b, int a) {
      if (mc.world != null && mc.getRenderManager().options != null) {
         boolean is_third_person_view = mc.getRenderManager().options.thirdPersonView == 2;
         float view_yaw = mc.getRenderManager().playerViewY;
         TurokRenderHelp.prepare_gl(0.5F);
         GlStateManager.pushMatrix();
         Vec3d pos = BopeUtilEntity.get_interpolated_entity(entities, mc.getRenderPartialTicks());
         GlStateManager.translate(pos.x - mc.getRenderManager().renderPosX, pos.y - mc.getRenderManager().renderPosY, pos.z - mc.getRenderManager().renderPosZ);
         GlStateManager.glNormal3f(0.0F, 1.0F, 0.0F);
         GlStateManager.rotate(-view_yaw, 0.0F, 1.0F, 0.0F);
         GlStateManager.rotate((float)(is_third_person_view ? -1 : 1), 1.0F, 0.0F, 0.0F);
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.5F);
         GL11.glLineWidth(2.0F);
         GL11.glEnable(2848);
         if (entities instanceof EntityPlayer) {
            GL11.glColor4f((float)r / 255.0F, (float)g / 255.0F, (float)b / 255.0F, (float)a / 255.0F);
            GL11.glBegin(2);
            GL11.glVertex2d((double)(-entities.width), 0.0D);
            GL11.glVertex2d((double)(-entities.width), (double)(entities.height / 3.0F));
            GL11.glVertex2d((double)(-entities.width), 0.0D);
            GL11.glVertex2d((double)(-entities.width / 3.0F * 2.0F), 0.0D);
            GL11.glEnd();
            GL11.glBegin(2);
            GL11.glVertex2d((double)(-entities.width), (double)entities.height);
            GL11.glVertex2d((double)(-entities.width / 3.0F * 2.0F), (double)entities.height);
            GL11.glVertex2d((double)(-entities.width), (double)entities.height);
            GL11.glVertex2d((double)(-entities.width), (double)(entities.height / 3.0F * 2.0F));
            GL11.glEnd();
            GL11.glBegin(2);
            GL11.glVertex2d((double)entities.width, (double)entities.height);
            GL11.glVertex2d((double)(entities.width / 3.0F * 2.0F), (double)entities.height);
            GL11.glVertex2d((double)entities.width, (double)entities.height);
            GL11.glVertex2d((double)entities.width, (double)(entities.height / 3.0F * 2.0F));
            GL11.glEnd();
            GL11.glBegin(2);
            GL11.glVertex2d((double)entities.width, 0.0D);
            GL11.glVertex2d((double)(entities.width / 3.0F * 2.0F), 0.0D);
            GL11.glVertex2d((double)entities.width, 0.0D);
            GL11.glVertex2d((double)entities.width, (double)(entities.height / 3.0F));
            GL11.glEnd();
         }

         TurokRenderHelp.release_gl();
         GlStateManager.popMatrix();
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      }

   }

   public static void EntityRectESP(Entity entities, int r, int g, int b, int a) {
      if (mc.world != null && mc.getRenderManager().options != null) {
         boolean is_third_person_view = mc.getRenderManager().options.thirdPersonView == 2;
         float view_yaw = mc.getRenderManager().playerViewY;
         TurokRenderHelp.prepare_gl(0.5F);
         GlStateManager.pushMatrix();
         Vec3d pos = BopeUtilEntity.get_interpolated_entity(entities, mc.getRenderPartialTicks());
         GlStateManager.translate(pos.x - mc.getRenderManager().renderPosX, pos.y - mc.getRenderManager().renderPosY, pos.z - mc.getRenderManager().renderPosZ);
         GlStateManager.glNormal3f(0.0F, 1.0F, 0.0F);
         GlStateManager.rotate(-view_yaw, 0.0F, 1.0F, 0.0F);
         GlStateManager.rotate((float)(is_third_person_view ? -1 : 1), 1.0F, 0.0F, 0.0F);
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.5F);
         GL11.glLineWidth(2.0F);
         GL11.glEnable(2848);
         if (entities instanceof EntityPlayer) {
            GL11.glColor4f((float)r / 255.0F, (float)g / 255.0F, (float)b / 255.0F, (float)a / 255.0F);
            GL11.glEnable(2848);
            GL11.glBegin(2);
            GL11.glVertex2d((double)(-entities.width / 2.0F), 0.0D);
            GL11.glVertex2d((double)(-entities.width / 2.0F), (double)entities.height);
            GL11.glVertex2d((double)(entities.width / 2.0F), (double)entities.height);
            GL11.glVertex2d((double)(entities.width / 2.0F), 0.0D);
            GL11.glEnd();
         }

         TurokRenderHelp.release_gl();
         GlStateManager.popMatrix();
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      }

   }

   public static void EntityTracerESP(Entity entities, int r, int g, int b, int a) {
      if (mc.world != null && mc.getRenderManager().options != null) {
      }

   }
}
