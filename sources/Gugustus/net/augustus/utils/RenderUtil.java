package net.augustus.utils;

import java.awt.Color;
import net.augustus.utils.interfaces.MC;
import net.augustus.utils.skid.lorious.BlurUtil;
import net.minecraft.block.Block;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import org.lwjgl.opengl.GL11;

public class RenderUtil implements MC {
   public static void drawRect(int left, int top, int right, int bottom, int color) {
      if (left < right) {
         int i = left;
         left = right;
         right = i;
      }

      if (top < bottom) {
         int j = top;
         top = bottom;
         bottom = j;
      }

      float f3 = (float)(color >> 24 & 0xFF) / 255.0F;
      float f = (float)(color >> 16 & 0xFF) / 255.0F;
      float f1 = (float)(color >> 8 & 0xFF) / 255.0F;
      float f2 = (float)(color & 0xFF) / 255.0F;
      Tessellator tessellator = Tessellator.getInstance();
      WorldRenderer worldrenderer = tessellator.getWorldRenderer();
      GlStateManager.enableBlend();
      GlStateManager.disableTexture2D();
      GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
      GlStateManager.color(f, f1, f2, f3);
      worldrenderer.begin(7, DefaultVertexFormats.POSITION);
      worldrenderer.pos((double)left, (double)bottom, 0.0).endVertex();
      worldrenderer.pos((double)right, (double)bottom, 0.0).endVertex();
      worldrenderer.pos((double)right, (double)top, 0.0).endVertex();
      worldrenderer.pos((double)left, (double)top, 0.0).endVertex();
      tessellator.draw();
      GlStateManager.enableTexture2D();
      GlStateManager.disableBlend();
   }

   public static void drawRect(double left, double top, double right, double bottom, int color) {
      if (left < right) {
         double i = left;
         left = right;
         right = i;
      }

      if (top < bottom) {
         double j = top;
         top = bottom;
         bottom = j;
      }

      float f3 = (float)(color >> 24 & 0xFF) / 255.0F;
      float f = (float)(color >> 16 & 0xFF) / 255.0F;
      float f1 = (float)(color >> 8 & 0xFF) / 255.0F;
      float f2 = (float)(color & 0xFF) / 255.0F;
      Tessellator tessellator = Tessellator.getInstance();
      WorldRenderer worldrenderer = tessellator.getWorldRenderer();
      GlStateManager.enableBlend();
      GlStateManager.disableTexture2D();
      GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
      GlStateManager.color(f, f1, f2, f3);
      worldrenderer.begin(7, DefaultVertexFormats.POSITION);
      worldrenderer.pos(left, bottom, 0.0).endVertex();
      worldrenderer.pos(right, bottom, 0.0).endVertex();
      worldrenderer.pos(right, top, 0.0).endVertex();
      worldrenderer.pos(left, top, 0.0).endVertex();
      tessellator.draw();
      GlStateManager.enableTexture2D();
      GlStateManager.disableBlend();
   }

   public static void drawFloatRect(float left, float top, float right, float bottom, int color) {
      if (left < right) {
         float i = left;
         left = right;
         right = i;
      }

      if (top < bottom) {
         float j = top;
         top = bottom;
         bottom = j;
      }

      float f3 = (float)(color >> 24 & 0xFF) / 255.0F;
      float f = (float)(color >> 16 & 0xFF) / 255.0F;
      float f1 = (float)(color >> 8 & 0xFF) / 255.0F;
      float f2 = (float)(color & 0xFF) / 255.0F;
      Tessellator tessellator = Tessellator.getInstance();
      WorldRenderer worldrenderer = tessellator.getWorldRenderer();
      GlStateManager.enableBlend();
      GlStateManager.disableTexture2D();
      GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
      GlStateManager.color(f, f1, f2, f3);
      worldrenderer.begin(7, DefaultVertexFormats.POSITION);
      worldrenderer.pos((double)left, (double)bottom, 0.0).endVertex();
      worldrenderer.pos((double)right, (double)bottom, 0.0).endVertex();
      worldrenderer.pos((double)right, (double)top, 0.0).endVertex();
      worldrenderer.pos((double)left, (double)top, 0.0).endVertex();
      tessellator.draw();
      GlStateManager.enableTexture2D();
      GlStateManager.disableBlend();
   }

   public static void drawCircle(double x, double y, double radius, int color) {
      GL11.glPushMatrix();
      color(color);
      GL11.glBegin(9);

      for(int i = 0; i < 360; ++i) {
         GL11.glVertex2d(x + Math.sin(Math.toRadians((double)i)) * radius, y + Math.cos(Math.toRadians((double)i)) * radius);
      }

      GL11.glEnd();
      GL11.glPopMatrix();
   }

   public static void drawColoredCircle(double x, double y, double radius, float brightness) {
      GL11.glPushMatrix();
      GL11.glLineWidth(3.5F);
      GL11.glEnable(2848);
      GL11.glShadeModel(7425);
      GL11.glBegin(3);

      for(int i = 0; i < 360; ++i) {
         color(Color.HSBtoRGB(1.0F, 0.0F, brightness));
         GL11.glVertex2d(x, y);
         color(Color.HSBtoRGB((float)i / 360.0F, 1.0F, brightness));
         GL11.glVertex2d(x + Math.sin(Math.toRadians((double)i)) * radius, y + Math.cos(Math.toRadians((double)i)) * radius);
      }

      GL11.glEnd();
      GL11.glShadeModel(7424);
      GL11.glDisable(2848);
      GL11.glPopMatrix();
   }

   public static void color(int argb) {
      float alpha = (float)(argb >> 24 & 0xFF) / 255.0F;
      float red = (float)(argb >> 16 & 0xFF) / 255.0F;
      float green = (float)(argb >> 8 & 0xFF) / 255.0F;
      float blue = (float)(argb & 0xFF) / 255.0F;
      GL11.glColor4f(red, green, blue, alpha);
   }

   public static void drawEntityServerESP(Entity entity, float red, float green, float blue, float alpha, float lineAlpha, float lineWidth) {
      double d0 = (double)entity.serverPosX / 32.0;
      double d1 = (double)entity.serverPosY / 32.0;
      double d2 = (double)entity.serverPosZ / 32.0;
      if (entity instanceof EntityLivingBase) {
         EntityLivingBase livingBase = (EntityLivingBase)entity;
         d0 = (double)livingBase.realPosX / 32.0;
         d1 = (double)livingBase.realPosY / 32.0;
         d2 = (double)livingBase.realPosZ / 32.0;
      }

      float x = (float)(d0 - mc.getRenderManager().getRenderPosX());
      float y = (float)(d1 - mc.getRenderManager().getRenderPosY());
      float z = (float)(d2 - mc.getRenderManager().getRenderPosZ());
      GL11.glColor4f(red, green, blue, alpha);
      otherDrawBoundingBox(entity, x, y, z, (double)(entity.width - 0.2F), (double)(entity.height + 0.1F));
      if (lineWidth > 0.0F) {
         GL11.glLineWidth(lineWidth);
         GL11.glColor4f(red, green, blue, lineAlpha);
         otherDrawOutlinedBoundingBox(entity, x, y, z, (double)(entity.width - 0.2F), (double)(entity.height + 0.1F));
      }

      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
   }

   public static void drawEntityESP(Entity entity, float red, float green, float blue, float alpha, float lineAlpha, float lineWidth) {
      float x = (float)(
         entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * (double)mc.getTimer().renderPartialTicks - mc.getRenderManager().getRenderPosX()
      );
      float y = (float)(
         entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * (double)mc.getTimer().renderPartialTicks - mc.getRenderManager().getRenderPosY()
      );
      float z = (float)(
         entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * (double)mc.getTimer().renderPartialTicks - mc.getRenderManager().getRenderPosZ()
      );
      GL11.glColor4f(red, green, blue, alpha);
      otherDrawBoundingBox(entity, x, y, z, (double)(entity.width - 0.2F), (double)(entity.height + 0.1F));
      if (lineWidth > 0.0F) {
         GL11.glLineWidth(lineWidth);
         GL11.glColor4f(red, green, blue, lineAlpha);
         otherDrawOutlinedBoundingBox(entity, x, y, z, (double)(entity.width - 0.2F), (double)(entity.height + 0.1F));
      }

      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
   }

   public static void drawBlockESP(TileEntity tileEntity, float red, float green, float blue, float alpha, float lineAlpha, float lineWidth, boolean other) {
      GlStateManager.color(red, green, blue, alpha);
      AxisAlignedBB axisAlignedBB = tileEntity.getBlockType().getSelectedBoundingBox(mc.theWorld, tileEntity.getPos());
      if (tileEntity instanceof TileEntityChest) {
         TileEntityChest tileEntityChest = (TileEntityChest)tileEntity;
         if (tileEntityChest.adjacentChestXNeg != null) {
            axisAlignedBB = axisAlignedBB.union(
               tileEntityChest.adjacentChestXNeg.getBlockType().getSelectedBoundingBox(mc.theWorld, tileEntityChest.adjacentChestXNeg.getPos())
            );
         } else if (tileEntityChest.adjacentChestZNeg != null) {
            axisAlignedBB = axisAlignedBB.union(
               tileEntityChest.adjacentChestZNeg.getBlockType().getSelectedBoundingBox(mc.theWorld, tileEntityChest.adjacentChestZNeg.getPos())
            );
         } else if (tileEntityChest.adjacentChestXPos != null) {
            axisAlignedBB = axisAlignedBB.union(
               tileEntityChest.adjacentChestXPos.getBlockType().getSelectedBoundingBox(mc.theWorld, tileEntityChest.adjacentChestXPos.getPos())
            );
         } else if (tileEntityChest.adjacentChestZPos != null) {
            axisAlignedBB = axisAlignedBB.union(
               tileEntityChest.adjacentChestZPos.getBlockType().getSelectedBoundingBox(mc.theWorld, tileEntityChest.adjacentChestZPos.getPos())
            );
         }
      }

      axisAlignedBB = axisAlignedBB.offset(
         -mc.getRenderManager().getRenderPosX(), -mc.getRenderManager().getRenderPosY(), -mc.getRenderManager().getRenderPosZ()
      );
      if (other) {
         drawBoundingBox(axisAlignedBB);
      } else {
         drawBoundingBox(axisAlignedBB.expand(0.05, 0.05, 0.05));
      }

      if (lineWidth > 0.0F) {
         GL11.glLineWidth(lineWidth);
         GlStateManager.color(red, green, blue, lineAlpha);
         if (other) {
            drawOutlinedBoundingBox(axisAlignedBB);
         } else {
            drawOutlinedBoundingBox(axisAlignedBB.expand(0.05, 0.05, 0.05));
         }
      }

      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
   }

   public static void drawBlockESP(BlockPos blockPos, float red, float green, float blue, float alpha, float lineAlpha, float lineWidth) {
      GlStateManager.color(red, green, blue, alpha);
      float x = (float)((double)blockPos.getX() - mc.getRenderManager().getRenderPosX());
      float y = (float)((double)blockPos.getY() - mc.getRenderManager().getRenderPosY());
      float z = (float)((double)blockPos.getZ() - mc.getRenderManager().getRenderPosZ());
      Block block = mc.theWorld.getBlockState(blockPos).getBlock();
      drawBoundingBox(
         new AxisAlignedBB(
            (double)x,
            (double)y,
            (double)z,
            (double)x + block.getBlockBoundsMaxX(),
            (double)y + block.getBlockBoundsMaxY(),
            (double)z + block.getBlockBoundsMaxZ()
         )
      );
      if (lineWidth > 0.0F) {
         GL11.glLineWidth(lineWidth);
         GlStateManager.color(red, green, blue, lineAlpha);
         drawOutlinedBoundingBox(
            new AxisAlignedBB(
               (double)x,
               (double)y,
               (double)z,
               (double)x + block.getBlockBoundsMaxX(),
               (double)y + block.getBlockBoundsMaxY(),
               (double)z + block.getBlockBoundsMaxZ()
            )
         );
      }

      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
   }

   public static void drawTracers(EntityLivingBase entity, float red, float green, float blue, float alpha, float lineWidth, String s) {
      float x = (float)(
         entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * (double)mc.getTimer().renderPartialTicks - mc.getRenderManager().getRenderPosX()
      );
      float y = (float)(
         entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * (double)mc.getTimer().renderPartialTicks - mc.getRenderManager().getRenderPosY()
      );
      float z = (float)(
         entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * (double)mc.getTimer().renderPartialTicks - mc.getRenderManager().getRenderPosZ()
      );
      GL11.glLineWidth(lineWidth);
      GL11.glColor4f(red, green, blue, alpha);
      drawTracerLine(x, y, z, s);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
   }

   public static void drawItemBoxESP(EntityItem entityItem, float red, float green, float blue, float alpha) {
      float x = (float)(
         entityItem.lastTickPosX
            + (entityItem.posX - entityItem.lastTickPosX) * (double)mc.getTimer().renderPartialTicks
            - mc.getRenderManager().getRenderPosX()
      );
      float y = (float)(
         entityItem.lastTickPosY
            + (entityItem.posY - entityItem.lastTickPosY) * (double)mc.getTimer().renderPartialTicks
            - mc.getRenderManager().getRenderPosY()
      );
      float z = (float)(
         entityItem.lastTickPosZ
            + (entityItem.posZ - entityItem.lastTickPosZ) * (double)mc.getTimer().renderPartialTicks
            - mc.getRenderManager().getRenderPosZ()
      );
      GL11.glColor4f(red, green, blue, alpha);
      drawItemBock(new AxisAlignedBB((double)x - 0.25, (double)y + 0.05, (double)z - 0.25, (double)x + 0.25, (double)y + 0.65, (double)z + 0.253));
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
   }

   public static void drawItemBock(AxisAlignedBB a) {
      Tessellator tessellator = Tessellator.getInstance();
      WorldRenderer worldrenderer = tessellator.getWorldRenderer();
      worldrenderer.begin(7, DefaultVertexFormats.POSITION);
      worldrenderer.pos((double)((float)a.minX), (double)((float)a.minY), (double)((float)a.minZ)).endVertex();
      worldrenderer.pos((double)((float)a.minX), (double)((float)a.minY), (double)((float)a.maxZ)).endVertex();
      worldrenderer.pos((double)((float)a.minX), (double)((float)a.maxY), (double)((float)a.maxZ)).endVertex();
      worldrenderer.pos((double)((float)a.minX), (double)((float)a.maxY), (double)((float)a.minZ)).endVertex();
      worldrenderer.pos((double)((float)a.minX), (double)((float)a.minY), (double)((float)a.maxZ)).endVertex();
      worldrenderer.pos((double)((float)a.maxX), (double)((float)a.minY), (double)((float)a.maxZ)).endVertex();
      worldrenderer.pos((double)((float)a.maxX), (double)((float)a.maxY), (double)((float)a.maxZ)).endVertex();
      worldrenderer.pos((double)((float)a.minX), (double)((float)a.maxY), (double)((float)a.maxZ)).endVertex();
      worldrenderer.pos((double)((float)a.maxX), (double)((float)a.minY), (double)((float)a.maxZ)).endVertex();
      worldrenderer.pos((double)((float)a.maxX), (double)((float)a.minY), (double)((float)a.minZ)).endVertex();
      worldrenderer.pos((double)((float)a.maxX), (double)((float)a.maxY), (double)((float)a.minZ)).endVertex();
      worldrenderer.pos((double)((float)a.maxX), (double)((float)a.maxY), (double)((float)a.maxZ)).endVertex();
      worldrenderer.pos((double)((float)a.maxX), (double)((float)a.minY), (double)((float)a.minZ)).endVertex();
      worldrenderer.pos((double)((float)a.minX), (double)((float)a.minY), (double)((float)a.minZ)).endVertex();
      worldrenderer.pos((double)((float)a.minX), (double)((float)a.maxY), (double)((float)a.minZ)).endVertex();
      worldrenderer.pos((double)((float)a.maxX), (double)((float)a.maxY), (double)((float)a.minZ)).endVertex();
      worldrenderer.pos((double)((float)a.minX), (double)((float)a.minY), (double)((float)a.minZ)).endVertex();
      worldrenderer.pos((double)((float)a.minX), (double)((float)a.minY), (double)((float)a.maxZ)).endVertex();
      worldrenderer.pos((double)((float)a.maxX), (double)((float)a.minY), (double)((float)a.maxZ)).endVertex();
      worldrenderer.pos((double)((float)a.maxX), (double)((float)a.minY), (double)((float)a.minZ)).endVertex();
      worldrenderer.pos((double)((float)a.minX), (double)((float)a.maxY), (double)((float)a.minZ)).endVertex();
      worldrenderer.pos((double)((float)a.minX), (double)((float)a.maxY), (double)((float)a.maxZ)).endVertex();
      worldrenderer.pos((double)((float)a.maxX), (double)((float)a.maxY), (double)((float)a.maxZ)).endVertex();
      worldrenderer.pos((double)((float)a.maxX), (double)((float)a.maxY), (double)((float)a.minZ)).endVertex();
      worldrenderer.endVertex();
      tessellator.draw();
   }

   private static void drawTracerLine(float x, float y, float z, String s) {
      float py = s.equals("Feet") ? 0.0F : mc.thePlayer.getEyeHeight();
      Tessellator tessellator = Tessellator.getInstance();
      WorldRenderer worldrenderer = tessellator.getWorldRenderer();
      worldrenderer.begin(1, DefaultVertexFormats.POSITION);
      worldrenderer.pos(0.0, (double)py, 0.0).endVertex();
      worldrenderer.pos((double)x, (double)y, (double)z).endVertex();
      worldrenderer.endVertex();
      tessellator.draw();
   }

   private static void drawOutlinedBoundingBoxGL11(AxisAlignedBB a) {
      GL11.glBegin(1);
      GL11.glVertex3d(a.minX, a.minY, a.minZ);
      GL11.glVertex3d(a.minX, a.minY, a.maxZ);
      GL11.glVertex3d(a.minX, a.minY, a.maxZ);
      GL11.glVertex3d(a.maxX, a.minY, a.maxZ);
      GL11.glVertex3d(a.maxX, a.minY, a.maxZ);
      GL11.glVertex3d(a.maxX, a.minY, a.minZ);
      GL11.glVertex3d(a.maxX, a.minY, a.minZ);
      GL11.glVertex3d(a.minX, a.minY, a.minZ);
      GL11.glVertex3d(a.minX, a.maxY, a.minZ);
      GL11.glVertex3d(a.minX, a.maxY, a.maxZ);
      GL11.glVertex3d(a.minX, a.maxY, a.maxZ);
      GL11.glVertex3d(a.maxX, a.maxY, a.maxZ);
      GL11.glVertex3d(a.maxX, a.maxY, a.maxZ);
      GL11.glVertex3d(a.maxX, a.maxY, a.minZ);
      GL11.glVertex3d(a.maxX, a.maxY, a.minZ);
      GL11.glVertex3d(a.minX, a.maxY, a.minZ);
      GL11.glVertex3d(a.minX, a.minY, a.minZ);
      GL11.glVertex3d(a.minX, a.maxY, a.minZ);
      GL11.glVertex3d(a.maxX, a.minY, a.minZ);
      GL11.glVertex3d(a.maxX, a.maxY, a.minZ);
      GL11.glVertex3d(a.maxX, a.minY, a.maxZ);
      GL11.glVertex3d(a.maxX, a.maxY, a.maxZ);
      GL11.glVertex3d(a.minX, a.minY, a.maxZ);
      GL11.glVertex3d(a.minX, a.maxY, a.maxZ);
      GL11.glEnd();
   }

   private static void drawBoundingBoxGL11(AxisAlignedBB a) {
      GL11.glBegin(7);
      GL11.glVertex3d(a.minX, a.minY, a.minZ);
      GL11.glVertex3d(a.minX, a.minY, a.maxZ);
      GL11.glVertex3d(a.minX, a.maxY, a.maxZ);
      GL11.glVertex3d(a.minX, a.maxY, a.minZ);
      GL11.glVertex3d(a.minX, a.minY, a.maxZ);
      GL11.glVertex3d(a.maxX, a.minY, a.maxZ);
      GL11.glVertex3d(a.maxX, a.maxY, a.maxZ);
      GL11.glVertex3d(a.minX, a.maxY, a.maxZ);
      GL11.glVertex3d(a.maxX, a.minY, a.maxZ);
      GL11.glVertex3d(a.maxX, a.minY, a.minZ);
      GL11.glVertex3d(a.maxX, a.maxY, a.minZ);
      GL11.glVertex3d(a.maxX, a.maxY, a.maxZ);
      GL11.glVertex3d(a.maxX, a.minY, a.minZ);
      GL11.glVertex3d(a.minX, a.minY, a.minZ);
      GL11.glVertex3d(a.minX, a.maxY, a.minZ);
      GL11.glVertex3d(a.maxX, a.maxY, a.minZ);
      GL11.glVertex3d(a.minX, a.minY, a.minZ);
      GL11.glVertex3d(a.minX, a.minY, a.maxZ);
      GL11.glVertex3d(a.maxX, a.minY, a.maxZ);
      GL11.glVertex3d(a.maxX, a.minY, a.minZ);
      GL11.glVertex3d(a.minX, a.maxY, a.minZ);
      GL11.glVertex3d(a.minX, a.maxY, a.maxZ);
      GL11.glVertex3d(a.maxX, a.maxY, a.maxZ);
      GL11.glVertex3d(a.maxX, a.maxY, a.minZ);
      GL11.glEnd();
   }

   private static void otherDrawOutlinedBoundingBoxGL11(Entity entity, float x, float y, float z, double width, double height) {
      width *= 1.5;
      float yaw1 = MathHelper.wrapAngleTo180_float(entity.getRotationYawHead()) + 45.0F;
      float newYaw1;
      if (yaw1 < 0.0F) {
         newYaw1 = 0.0F;
         newYaw1 += 360.0F - Math.abs(yaw1);
      } else {
         newYaw1 = yaw1;
      }

      newYaw1 *= -1.0F;
      newYaw1 = (float)((double)newYaw1 * (Math.PI / 180.0));
      float yaw2 = MathHelper.wrapAngleTo180_float(entity.getRotationYawHead()) + 135.0F;
      float newYaw2;
      if (yaw2 < 0.0F) {
         newYaw2 = 0.0F;
         newYaw2 += 360.0F - Math.abs(yaw2);
      } else {
         newYaw2 = yaw2;
      }

      newYaw2 *= -1.0F;
      newYaw2 = (float)((double)newYaw2 * (Math.PI / 180.0));
      float yaw3 = MathHelper.wrapAngleTo180_float(entity.getRotationYawHead()) + 225.0F;
      float newYaw3;
      if (yaw3 < 0.0F) {
         newYaw3 = 0.0F;
         newYaw3 += 360.0F - Math.abs(yaw3);
      } else {
         newYaw3 = yaw3;
      }

      newYaw3 *= -1.0F;
      newYaw3 = (float)((double)newYaw3 * (Math.PI / 180.0));
      float yaw4 = MathHelper.wrapAngleTo180_float(entity.getRotationYawHead()) + 315.0F;
      float newYaw4;
      if (yaw4 < 0.0F) {
         newYaw4 = 0.0F;
         newYaw4 += 360.0F - Math.abs(yaw4);
      } else {
         newYaw4 = yaw4;
      }

      newYaw4 *= -1.0F;
      newYaw4 = (float)((double)newYaw4 * (Math.PI / 180.0));
      double x1 = Math.sin((double)newYaw1) * width + (double)x;
      double z1 = Math.cos((double)newYaw1) * width + (double)z;
      double x2 = Math.sin((double)newYaw2) * width + (double)x;
      double z2 = Math.cos((double)newYaw2) * width + (double)z;
      double x3 = Math.sin((double)newYaw3) * width + (double)x;
      double z3 = Math.cos((double)newYaw3) * width + (double)z;
      double x4 = Math.sin((double)newYaw4) * width + (double)x;
      double z4 = Math.cos((double)newYaw4) * width + (double)z;
      double y2 = (double)y + height;
      GL11.glBegin(1);
      GL11.glVertex3d(x1, (double)y, z1);
      GL11.glVertex3d(x2, (double)y, z2);
      GL11.glVertex3d(x2, (double)y, z2);
      GL11.glVertex3d(x3, (double)y, z3);
      GL11.glVertex3d(x3, (double)y, z3);
      GL11.glVertex3d(x4, (double)y, z4);
      GL11.glVertex3d(x4, (double)y, z4);
      GL11.glVertex3d(x1, (double)y, z1);
      GL11.glVertex3d(x1, y2, z1);
      GL11.glVertex3d(x2, y2, z2);
      GL11.glVertex3d(x2, y2, z2);
      GL11.glVertex3d(x3, y2, z3);
      GL11.glVertex3d(x3, y2, z3);
      GL11.glVertex3d(x4, y2, z4);
      GL11.glVertex3d(x4, y2, z4);
      GL11.glVertex3d(x1, y2, z1);
      GL11.glVertex3d(x1, (double)y, z1);
      GL11.glVertex3d(x1, y2, z1);
      GL11.glVertex3d(x2, (double)y, z2);
      GL11.glVertex3d(x2, y2, z2);
      GL11.glVertex3d(x3, (double)y, z3);
      GL11.glVertex3d(x3, y2, z3);
      GL11.glVertex3d(x4, (double)y, z4);
      GL11.glVertex3d(x4, y2, z4);
      GL11.glEnd();
   }

   private static void otherDrawBoundingBoxGL11(Entity entity, float x, float y, float z, double width, double height) {
      width *= 1.5;
      float yaw1 = MathHelper.wrapAngleTo180_float(entity.getRotationYawHead()) + 45.0F;
      float newYaw1;
      if (yaw1 < 0.0F) {
         newYaw1 = 0.0F;
         newYaw1 += 360.0F - Math.abs(yaw1);
      } else {
         newYaw1 = yaw1;
      }

      newYaw1 *= -1.0F;
      newYaw1 = (float)((double)newYaw1 * (Math.PI / 180.0));
      float yaw2 = MathHelper.wrapAngleTo180_float(entity.getRotationYawHead()) + 135.0F;
      float newYaw2;
      if (yaw2 < 0.0F) {
         newYaw2 = 0.0F;
         newYaw2 += 360.0F - Math.abs(yaw2);
      } else {
         newYaw2 = yaw2;
      }

      newYaw2 *= -1.0F;
      newYaw2 = (float)((double)newYaw2 * (Math.PI / 180.0));
      float yaw3 = MathHelper.wrapAngleTo180_float(entity.getRotationYawHead()) + 225.0F;
      float newYaw3;
      if (yaw3 < 0.0F) {
         newYaw3 = 0.0F;
         newYaw3 += 360.0F - Math.abs(yaw3);
      } else {
         newYaw3 = yaw3;
      }

      newYaw3 *= -1.0F;
      newYaw3 = (float)((double)newYaw3 * (Math.PI / 180.0));
      float yaw4 = MathHelper.wrapAngleTo180_float(entity.getRotationYawHead()) + 315.0F;
      float newYaw4;
      if (yaw4 < 0.0F) {
         newYaw4 = 0.0F;
         newYaw4 += 360.0F - Math.abs(yaw4);
      } else {
         newYaw4 = yaw4;
      }

      newYaw4 *= -1.0F;
      newYaw4 = (float)((double)newYaw4 * (Math.PI / 180.0));
      double x1 = Math.sin((double)newYaw1) * width + (double)x;
      double z1 = Math.cos((double)newYaw1) * width + (double)z;
      double x2 = Math.sin((double)newYaw2) * width + (double)x;
      double z2 = Math.cos((double)newYaw2) * width + (double)z;
      double x3 = Math.sin((double)newYaw3) * width + (double)x;
      double z3 = Math.cos((double)newYaw3) * width + (double)z;
      double x4 = Math.sin((double)newYaw4) * width + (double)x;
      double z4 = Math.cos((double)newYaw4) * width + (double)z;
      double y2 = (double)y + height;
      GL11.glBegin(7);
      GL11.glVertex3d(x1, (double)y, z1);
      GL11.glVertex3d(x1, y2, z1);
      GL11.glVertex3d(x2, y2, z2);
      GL11.glVertex3d(x2, (double)y, z2);
      GL11.glVertex3d(x2, (double)y, z2);
      GL11.glVertex3d(x2, y2, z2);
      GL11.glVertex3d(x3, y2, z3);
      GL11.glVertex3d(x3, (double)y, z3);
      GL11.glVertex3d(x3, (double)y, z3);
      GL11.glVertex3d(x3, y2, z3);
      GL11.glVertex3d(x4, y2, z4);
      GL11.glVertex3d(x4, (double)y, z4);
      GL11.glVertex3d(x4, (double)y, z4);
      GL11.glVertex3d(x4, y2, z4);
      GL11.glVertex3d(x1, y2, z1);
      GL11.glVertex3d(x1, (double)y, z1);
      GL11.glVertex3d(x1, (double)y, z1);
      GL11.glVertex3d(x2, (double)y, z2);
      GL11.glVertex3d(x3, (double)y, z3);
      GL11.glVertex3d(x4, (double)y, z4);
      GL11.glVertex3d(x1, y2, z1);
      GL11.glVertex3d(x2, y2, z2);
      GL11.glVertex3d(x3, y2, z3);
      GL11.glVertex3d(x4, y2, z4);
      GL11.glEnd();
   }

   private static void drawOutlinedBoundingBox(AxisAlignedBB a) {
      Tessellator tessellator = Tessellator.getInstance();
      WorldRenderer worldrenderer = tessellator.getWorldRenderer();
      GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
      worldrenderer.begin(3, DefaultVertexFormats.POSITION);
      worldrenderer.pos((double)((float)a.minX), (double)((float)a.minY), (double)((float)a.minZ)).endVertex();
      worldrenderer.pos((double)((float)a.minX), (double)((float)a.minY), (double)((float)a.maxZ)).endVertex();
      worldrenderer.pos((double)((float)a.minX), (double)((float)a.maxY), (double)((float)a.maxZ)).endVertex();
      worldrenderer.pos((double)((float)a.minX), (double)((float)a.maxY), (double)((float)a.minZ)).endVertex();
      worldrenderer.pos((double)((float)a.minX), (double)((float)a.minY), (double)((float)a.minZ)).endVertex();
      worldrenderer.pos((double)((float)a.maxX), (double)((float)a.minY), (double)((float)a.minZ)).endVertex();
      worldrenderer.pos((double)((float)a.maxX), (double)((float)a.maxY), (double)((float)a.minZ)).endVertex();
      worldrenderer.pos((double)((float)a.maxX), (double)((float)a.maxY), (double)((float)a.maxZ)).endVertex();
      worldrenderer.pos((double)((float)a.maxX), (double)((float)a.minY), (double)((float)a.maxZ)).endVertex();
      worldrenderer.pos((double)((float)a.maxX), (double)((float)a.minY), (double)((float)a.minZ)).endVertex();
      worldrenderer.pos((double)((float)a.maxX), (double)((float)a.minY), (double)((float)a.maxZ)).endVertex();
      worldrenderer.pos((double)((float)a.minX), (double)((float)a.minY), (double)((float)a.maxZ)).endVertex();
      worldrenderer.pos((double)((float)a.minX), (double)((float)a.maxY), (double)((float)a.maxZ)).endVertex();
      worldrenderer.pos((double)((float)a.maxX), (double)((float)a.maxY), (double)((float)a.maxZ)).endVertex();
      worldrenderer.pos((double)((float)a.maxX), (double)((float)a.maxY), (double)((float)a.minZ)).endVertex();
      worldrenderer.pos((double)((float)a.minX), (double)((float)a.maxY), (double)((float)a.minZ)).endVertex();
      worldrenderer.endVertex();
      tessellator.draw();
   }

   public static void drawBoundingBox(AxisAlignedBB a) {
      Tessellator tessellator = Tessellator.getInstance();
      WorldRenderer worldrenderer = tessellator.getWorldRenderer();
      worldrenderer.begin(7, DefaultVertexFormats.POSITION);
      worldrenderer.pos((double)((float)a.minX), (double)((float)a.minY), (double)((float)a.minZ)).endVertex();
      worldrenderer.pos((double)((float)a.minX), (double)((float)a.minY), (double)((float)a.maxZ)).endVertex();
      worldrenderer.pos((double)((float)a.minX), (double)((float)a.maxY), (double)((float)a.maxZ)).endVertex();
      worldrenderer.pos((double)((float)a.minX), (double)((float)a.maxY), (double)((float)a.minZ)).endVertex();
      worldrenderer.pos((double)((float)a.minX), (double)((float)a.minY), (double)((float)a.maxZ)).endVertex();
      worldrenderer.pos((double)((float)a.maxX), (double)((float)a.minY), (double)((float)a.maxZ)).endVertex();
      worldrenderer.pos((double)((float)a.maxX), (double)((float)a.maxY), (double)((float)a.maxZ)).endVertex();
      worldrenderer.pos((double)((float)a.minX), (double)((float)a.maxY), (double)((float)a.maxZ)).endVertex();
      worldrenderer.pos((double)((float)a.maxX), (double)((float)a.minY), (double)((float)a.maxZ)).endVertex();
      worldrenderer.pos((double)((float)a.maxX), (double)((float)a.minY), (double)((float)a.minZ)).endVertex();
      worldrenderer.pos((double)((float)a.maxX), (double)((float)a.maxY), (double)((float)a.minZ)).endVertex();
      worldrenderer.pos((double)((float)a.maxX), (double)((float)a.maxY), (double)((float)a.maxZ)).endVertex();
      worldrenderer.pos((double)((float)a.maxX), (double)((float)a.minY), (double)((float)a.minZ)).endVertex();
      worldrenderer.pos((double)((float)a.minX), (double)((float)a.minY), (double)((float)a.minZ)).endVertex();
      worldrenderer.pos((double)((float)a.minX), (double)((float)a.maxY), (double)((float)a.minZ)).endVertex();
      worldrenderer.pos((double)((float)a.maxX), (double)((float)a.maxY), (double)((float)a.minZ)).endVertex();
      worldrenderer.pos((double)((float)a.minX), (double)((float)a.minY), (double)((float)a.minZ)).endVertex();
      worldrenderer.pos((double)((float)a.minX), (double)((float)a.minY), (double)((float)a.maxZ)).endVertex();
      worldrenderer.pos((double)((float)a.maxX), (double)((float)a.minY), (double)((float)a.maxZ)).endVertex();
      worldrenderer.pos((double)((float)a.maxX), (double)((float)a.minY), (double)((float)a.minZ)).endVertex();
      worldrenderer.pos((double)((float)a.minX), (double)((float)a.maxY), (double)((float)a.minZ)).endVertex();
      worldrenderer.pos((double)((float)a.minX), (double)((float)a.maxY), (double)((float)a.maxZ)).endVertex();
      worldrenderer.pos((double)((float)a.maxX), (double)((float)a.maxY), (double)((float)a.maxZ)).endVertex();
      worldrenderer.pos((double)((float)a.maxX), (double)((float)a.maxY), (double)((float)a.minZ)).endVertex();
      worldrenderer.endVertex();
      tessellator.draw();
   }

   public static void otherDrawOutlinedBoundingBox(Entity entity, float x, float y, float z, double width, double height) {
      width *= 1.5;
      float yaw1 = MathHelper.wrapAngleTo180_float(entity.getRotationYawHead()) + 45.0F;
      float newYaw1;
      if (yaw1 < 0.0F) {
         newYaw1 = 0.0F;
         newYaw1 += 360.0F - Math.abs(yaw1);
      } else {
         newYaw1 = yaw1;
      }

      newYaw1 *= -1.0F;
      newYaw1 = (float)((double)newYaw1 * (Math.PI / 180.0));
      float yaw2 = MathHelper.wrapAngleTo180_float(entity.getRotationYawHead()) + 135.0F;
      float newYaw2;
      if (yaw2 < 0.0F) {
         newYaw2 = 0.0F;
         newYaw2 += 360.0F - Math.abs(yaw2);
      } else {
         newYaw2 = yaw2;
      }

      newYaw2 *= -1.0F;
      newYaw2 = (float)((double)newYaw2 * (Math.PI / 180.0));
      float yaw3 = MathHelper.wrapAngleTo180_float(entity.getRotationYawHead()) + 225.0F;
      float newYaw3;
      if (yaw3 < 0.0F) {
         newYaw3 = 0.0F;
         newYaw3 += 360.0F - Math.abs(yaw3);
      } else {
         newYaw3 = yaw3;
      }

      newYaw3 *= -1.0F;
      newYaw3 = (float)((double)newYaw3 * (Math.PI / 180.0));
      float yaw4 = MathHelper.wrapAngleTo180_float(entity.getRotationYawHead()) + 315.0F;
      float newYaw4;
      if (yaw4 < 0.0F) {
         newYaw4 = 0.0F;
         newYaw4 += 360.0F - Math.abs(yaw4);
      } else {
         newYaw4 = yaw4;
      }

      newYaw4 *= -1.0F;
      newYaw4 = (float)((double)newYaw4 * (Math.PI / 180.0));
      float x1 = (float)(Math.sin((double)newYaw1) * width + (double)x);
      float z1 = (float)(Math.cos((double)newYaw1) * width + (double)z);
      float x2 = (float)(Math.sin((double)newYaw2) * width + (double)x);
      float z2 = (float)(Math.cos((double)newYaw2) * width + (double)z);
      float x3 = (float)(Math.sin((double)newYaw3) * width + (double)x);
      float z3 = (float)(Math.cos((double)newYaw3) * width + (double)z);
      float x4 = (float)(Math.sin((double)newYaw4) * width + (double)x);
      float z4 = (float)(Math.cos((double)newYaw4) * width + (double)z);
      float y2 = (float)((double)y + height);
      Tessellator tessellator = Tessellator.getInstance();
      WorldRenderer worldrenderer = tessellator.getWorldRenderer();
      worldrenderer.begin(3, DefaultVertexFormats.POSITION);
      worldrenderer.pos((double)x1, (double)y, (double)z1).endVertex();
      worldrenderer.pos((double)x1, (double)y2, (double)z1).endVertex();
      worldrenderer.pos((double)x2, (double)y2, (double)z2).endVertex();
      worldrenderer.pos((double)x2, (double)y, (double)z2).endVertex();
      worldrenderer.pos((double)x1, (double)y, (double)z1).endVertex();
      worldrenderer.pos((double)x4, (double)y, (double)z4).endVertex();
      worldrenderer.pos((double)x3, (double)y, (double)z3).endVertex();
      worldrenderer.pos((double)x3, (double)y2, (double)z3).endVertex();
      worldrenderer.pos((double)x4, (double)y2, (double)z4).endVertex();
      worldrenderer.pos((double)x4, (double)y, (double)z4).endVertex();
      worldrenderer.pos((double)x4, (double)y2, (double)z4).endVertex();
      worldrenderer.pos((double)x3, (double)y2, (double)z3).endVertex();
      worldrenderer.pos((double)x2, (double)y2, (double)z2).endVertex();
      worldrenderer.pos((double)x2, (double)y, (double)z2).endVertex();
      worldrenderer.pos((double)x3, (double)y, (double)z3).endVertex();
      worldrenderer.pos((double)x4, (double)y, (double)z4).endVertex();
      worldrenderer.pos((double)x4, (double)y2, (double)z4).endVertex();
      worldrenderer.pos((double)x1, (double)y2, (double)z1).endVertex();
      worldrenderer.pos((double)x1, (double)y, (double)z1).endVertex();
      worldrenderer.endVertex();
      tessellator.draw();
   }

   public static void otherDrawBoundingBox(Entity entity, float x, float y, float z, double width, double height) {
      width *= 1.5;
      float yaw1 = MathHelper.wrapAngleTo180_float(entity.getRotationYawHead()) + 45.0F;
      float newYaw1;
      if (yaw1 < 0.0F) {
         newYaw1 = 0.0F;
         newYaw1 += 360.0F - Math.abs(yaw1);
      } else {
         newYaw1 = yaw1;
      }

      newYaw1 *= -1.0F;
      newYaw1 = (float)((double)newYaw1 * (Math.PI / 180.0));
      float yaw2 = MathHelper.wrapAngleTo180_float(entity.getRotationYawHead()) + 135.0F;
      float newYaw2;
      if (yaw2 < 0.0F) {
         newYaw2 = 0.0F;
         newYaw2 += 360.0F - Math.abs(yaw2);
      } else {
         newYaw2 = yaw2;
      }

      newYaw2 *= -1.0F;
      newYaw2 = (float)((double)newYaw2 * (Math.PI / 180.0));
      float yaw3 = MathHelper.wrapAngleTo180_float(entity.getRotationYawHead()) + 225.0F;
      float newYaw3;
      if (yaw3 < 0.0F) {
         newYaw3 = 0.0F;
         newYaw3 += 360.0F - Math.abs(yaw3);
      } else {
         newYaw3 = yaw3;
      }

      newYaw3 *= -1.0F;
      newYaw3 = (float)((double)newYaw3 * (Math.PI / 180.0));
      float yaw4 = MathHelper.wrapAngleTo180_float(entity.getRotationYawHead()) + 315.0F;
      float newYaw4;
      if (yaw4 < 0.0F) {
         newYaw4 = 0.0F;
         newYaw4 += 360.0F - Math.abs(yaw4);
      } else {
         newYaw4 = yaw4;
      }

      newYaw4 *= -1.0F;
      newYaw4 = (float)((double)newYaw4 * (Math.PI / 180.0));
      float x1 = (float)(Math.sin((double)newYaw1) * width + (double)x);
      float z1 = (float)(Math.cos((double)newYaw1) * width + (double)z);
      float x2 = (float)(Math.sin((double)newYaw2) * width + (double)x);
      float z2 = (float)(Math.cos((double)newYaw2) * width + (double)z);
      float x3 = (float)(Math.sin((double)newYaw3) * width + (double)x);
      float z3 = (float)(Math.cos((double)newYaw3) * width + (double)z);
      float x4 = (float)(Math.sin((double)newYaw4) * width + (double)x);
      float z4 = (float)(Math.cos((double)newYaw4) * width + (double)z);
      float y2 = (float)((double)y + height);
      Tessellator tessellator = Tessellator.getInstance();
      WorldRenderer worldrenderer = tessellator.getWorldRenderer();
      worldrenderer.begin(7, DefaultVertexFormats.POSITION);
      worldrenderer.pos((double)x1, (double)y, (double)z1).endVertex();
      worldrenderer.pos((double)x1, (double)y2, (double)z1).endVertex();
      worldrenderer.pos((double)x2, (double)y2, (double)z2).endVertex();
      worldrenderer.pos((double)x2, (double)y, (double)z2).endVertex();
      worldrenderer.pos((double)x2, (double)y, (double)z2).endVertex();
      worldrenderer.pos((double)x2, (double)y2, (double)z2).endVertex();
      worldrenderer.pos((double)x3, (double)y2, (double)z3).endVertex();
      worldrenderer.pos((double)x3, (double)y, (double)z3).endVertex();
      worldrenderer.pos((double)x3, (double)y, (double)z3).endVertex();
      worldrenderer.pos((double)x3, (double)y2, (double)z3).endVertex();
      worldrenderer.pos((double)x4, (double)y2, (double)z4).endVertex();
      worldrenderer.pos((double)x4, (double)y, (double)z4).endVertex();
      worldrenderer.pos((double)x4, (double)y, (double)z4).endVertex();
      worldrenderer.pos((double)x4, (double)y2, (double)z4).endVertex();
      worldrenderer.pos((double)x1, (double)y2, (double)z1).endVertex();
      worldrenderer.pos((double)x1, (double)y, (double)z1).endVertex();
      worldrenderer.pos((double)x1, (double)y, (double)z1).endVertex();
      worldrenderer.pos((double)x2, (double)y, (double)z2).endVertex();
      worldrenderer.pos((double)x3, (double)y, (double)z3).endVertex();
      worldrenderer.pos((double)x4, (double)y, (double)z4).endVertex();
      worldrenderer.pos((double)x1, (double)y2, (double)z1).endVertex();
      worldrenderer.pos((double)x2, (double)y2, (double)z2).endVertex();
      worldrenderer.pos((double)x3, (double)y2, (double)z3).endVertex();
      worldrenderer.pos((double)x4, (double)y2, (double)z4).endVertex();
      worldrenderer.endVertex();
      tessellator.draw();
   }

   public static void drawCornerESP(EntityLivingBase entity, float red, float green, float blue) {
      float x = (float)(
         entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * (double)mc.getTimer().renderPartialTicks - mc.getRenderManager().getRenderPosX()
      );
      float y = (float)(
         entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * (double)mc.getTimer().renderPartialTicks - mc.getRenderManager().getRenderPosY()
      );
      float z = (float)(
         entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * (double)mc.getTimer().renderPartialTicks - mc.getRenderManager().getRenderPosZ()
      );
      GlStateManager.pushMatrix();
      GlStateManager.translate(x, y + entity.height / 2.0F, z);
      GlStateManager.rotate(-mc.getRenderManager().playerViewY, 0.0F, 1.0F, 0.0F);
      GlStateManager.scale(-0.098, -0.098, 0.098);
      float width = (float)(26.6 * (double)entity.width / 2.0);
      float height = entity instanceof EntityPlayer ? 12.0F : (float)(11.98 * (double)(entity.height / 2.0F));
      GlStateManager.color(red, green, blue);
      draw3DRect(width, height - 1.0F, width - 4.0F, height);
      draw3DRect(-width, height - 1.0F, -width + 4.0F, height);
      draw3DRect(-width, height, -width + 1.0F, height - 4.0F);
      draw3DRect(width, height, width - 1.0F, height - 4.0F);
      draw3DRect(width, -height, width - 4.0F, -height + 1.0F);
      draw3DRect(-width, -height, -width + 4.0F, -height + 1.0F);
      draw3DRect(-width, -height + 1.0F, -width + 1.0F, -height + 4.0F);
      draw3DRect(width, -height + 1.0F, width - 1.0F, -height + 4.0F);
      GlStateManager.color(0.0F, 0.0F, 0.0F);
      draw3DRect(width, height, width - 4.0F, height + 0.2F);
      draw3DRect(-width, height, -width + 4.0F, height + 0.2F);
      draw3DRect(-width - 0.2F, height + 0.2F, -width, height - 4.0F);
      draw3DRect(width + 0.2F, height + 0.2F, width, height - 4.0F);
      draw3DRect(width + 0.2F, -height, width - 4.0F, -height - 0.2F);
      draw3DRect(-width - 0.2F, -height, -width + 4.0F, -height - 0.2F);
      draw3DRect(-width - 0.2F, -height, -width, -height + 4.0F);
      draw3DRect(width + 0.2F, -height, width, -height + 4.0F);
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      GlStateManager.popMatrix();
   }

   public static void drawFake2DESP(EntityLivingBase entity, float red, float green, float blue) {
      float x = (float)(
         entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * (double)mc.getTimer().renderPartialTicks - mc.getRenderManager().getRenderPosX()
      );
      float y = (float)(
         entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * (double)mc.getTimer().renderPartialTicks - mc.getRenderManager().getRenderPosY()
      );
      float z = (float)(
         entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * (double)mc.getTimer().renderPartialTicks - mc.getRenderManager().getRenderPosZ()
      );
      GlStateManager.pushMatrix();
      GlStateManager.translate(x, y + entity.height / 2.0F, z);
      GlStateManager.rotate(-mc.getRenderManager().playerViewY, 0.0F, 1.0F, 0.0F);
      GlStateManager.scale(-0.1, -0.1, 0.1);
      GlStateManager.color(red, green, blue);
      float width = (float)(23.3 * (double)entity.width / 2.0);
      float height = entity instanceof EntityPlayer ? 12.0F : (float)(11.98 * (double)(entity.height / 2.0F));
      draw3DRect(width, height, -width, height + 0.4F);
      draw3DRect(width, -height, -width, -height + 0.4F);
      draw3DRect(width, -height + 0.4F, width - 0.4F, height + 0.4F);
      draw3DRect(-width, -height + 0.4F, -width + 0.4F, height + 0.4F);
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      GlStateManager.popMatrix();
   }

   public static void drawCornerESP(TileEntity tileEntity, float red, float green, float blue) {
      double tx = 0.0;
      double tz = 0.0;
      if (tileEntity instanceof TileEntityChest) {
         TileEntityChest tileEntityChest = (TileEntityChest)tileEntity;
         if (tileEntityChest.adjacentChestXNeg != null) {
            tx -= 0.5;
         } else if (tileEntityChest.adjacentChestZNeg != null) {
            tz -= 0.5;
         }
      }

      float x = (float)((double)tileEntity.getPos().getX() + tx - mc.getRenderManager().getRenderPosX());
      float y = (float)((double)tileEntity.getPos().getY() - mc.getRenderManager().getRenderPosY());
      float z = (float)((double)tileEntity.getPos().getZ() + tz - mc.getRenderManager().getRenderPosZ());
      GlStateManager.pushMatrix();
      GlStateManager.translate((double)x + 0.5, (double)y + 0.47, (double)z + 0.5);
      GlStateManager.rotate(-mc.getRenderManager().playerViewY, 0.0F, 1.0F, 0.0F);
      GlStateManager.rotate(mc.getRenderManager().playerViewX, 1.0F, 0.0F, 0.0F);
      GlStateManager.scale(-0.098, -0.098, 0.098);
      float width = tx == 0.0 && tz == 0.0 ? 8.0F : 14.0F;
      float height = 7.5F;
      GlStateManager.color(red, green, blue);
      draw3DRect(width, height - 1.0F, width - 4.0F, height);
      draw3DRect(-width, height - 1.0F, -width + 4.0F, height);
      draw3DRect(-width, height, -width + 1.0F, height - 4.0F);
      draw3DRect(width, height, width - 1.0F, height - 4.0F);
      draw3DRect(width, -height, width - 4.0F, -height + 1.0F);
      draw3DRect(-width, -height, -width + 4.0F, -height + 1.0F);
      draw3DRect(-width, -height + 1.0F, -width + 1.0F, -height + 4.0F);
      draw3DRect(width, -height + 1.0F, width - 1.0F, -height + 4.0F);
      GlStateManager.color(0.0F, 0.0F, 0.0F);
      draw3DRect(width, height, width - 4.0F, height + 0.2F);
      draw3DRect(-width, height, -width + 4.0F, height + 0.2F);
      draw3DRect(-width - 0.2F, height + 0.2F, -width, height - 4.0F);
      draw3DRect(width + 0.2F, height + 0.2F, width, height - 4.0F);
      draw3DRect(width + 0.2F, -height, width - 4.0F, -height - 0.2F);
      draw3DRect(-width - 0.2F, -height, -width + 4.0F, -height - 0.2F);
      draw3DRect(-width - 0.2F, -height, -width, -height + 4.0F);
      draw3DRect(width + 0.2F, -height, width, -height + 4.0F);
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      GlStateManager.popMatrix();
   }

   public static void drawFake2DESP(TileEntity tileEntity, float red, float green, float blue) {
      double tx = 0.0;
      double tz = 0.0;
      if (tileEntity instanceof TileEntityChest) {
         TileEntityChest tileEntityChest = (TileEntityChest)tileEntity;
         if (tileEntityChest.adjacentChestXNeg != null) {
            tx -= 0.5;
         } else if (tileEntityChest.adjacentChestZNeg != null) {
            tz -= 0.5;
         }
      }

      float x = (float)((double)tileEntity.getPos().getX() + tx - mc.getRenderManager().getRenderPosX());
      float y = (float)((double)tileEntity.getPos().getY() - mc.getRenderManager().getRenderPosY());
      float z = (float)((double)tileEntity.getPos().getZ() + tz - mc.getRenderManager().getRenderPosZ());
      GlStateManager.pushMatrix();
      GlStateManager.translate((double)x + 0.5, (double)y + 0.47, (double)z + 0.5);
      GlStateManager.rotate(-mc.getRenderManager().playerViewY, 0.0F, 1.0F, 0.0F);
      GlStateManager.rotate(mc.getRenderManager().playerViewX, 1.0F, 0.0F, 0.0F);
      GlStateManager.scale(-0.1, -0.1, 0.1);
      GlStateManager.color(red, green, blue);
      float width = tx == 0.0 && tz == 0.0 ? 8.0F : 14.0F;
      float height = 7.5F;
      draw3DRect(width, height, -width, height + 0.4F);
      draw3DRect(width, -height, -width, -height + 0.4F);
      draw3DRect(width, -height + 0.4F, width - 0.4F, height + 0.4F);
      draw3DRect(-width, -height + 0.4F, -width + 0.4F, height + 0.4F);
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      GlStateManager.popMatrix();
   }

   public static void draw3DRect(float x1, float y1, float x2, float y2) {
      GL11.glBegin(7);
      GL11.glVertex2d((double)x2, (double)y1);
      GL11.glVertex2d((double)x1, (double)y1);
      GL11.glVertex2d((double)x1, (double)y2);
      GL11.glVertex2d((double)x2, (double)y2);
      GL11.glEnd();
   }

   public static void setScissorBox(float x1, float y1, float x2, float y2) {
      ScaledResolution sr = new ScaledResolution(mc);
      float scaleFactor = (float)sr.getScaleFactor();
      GL11.glScissor(
         (int)(x1 * scaleFactor),
         (int)((sr.getScaledHeight_double() - (double)y2) * (double)scaleFactor),
         (int)((x2 - x1) * scaleFactor),
         (int)((y2 - y1) * scaleFactor)
      );
   }
}
