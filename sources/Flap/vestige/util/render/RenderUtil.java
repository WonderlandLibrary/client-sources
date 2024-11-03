package vestige.util.render;

import java.awt.Color;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import org.lwjgl.opengl.GL11;

public class RenderUtil {
   private static final Frustum frustrum = new Frustum();

   public static void prepareBoxRender(float lineWidth, double red, double green, double blue, double alpha) {
      GL11.glBlendFunc(770, 771);
      GL11.glEnable(3042);
      GL11.glLineWidth(lineWidth);
      GL11.glDisable(3553);
      GL11.glDisable(2929);
      GL11.glEnable(2848);
      GL11.glDepthMask(false);
      GL11.glColor4d(red, green, blue, alpha);
   }

   public static void BB(AxisAlignedBB bb, int color) {
      enable3D();
      color(color);
      drawBoundingBox(bb);
      disable3D();
   }

   public static void OutlinedBB(AxisAlignedBB bb, float width, int color) {
      enable3D();
      GL11.glLineWidth(width);
      color(color);
      drawOutlinedBoundingBox(bb);
      disable3D();
   }

   public static void drawOutlinedBoundingBox(AxisAlignedBB aa) {
      Tessellator tessellator = Tessellator.getInstance();
      WorldRenderer worldRenderer = tessellator.getWorldRenderer();
      worldRenderer.func_181668_a(3, DefaultVertexFormats.field_181705_e);
      worldRenderer.func_181662_b(aa.minX, aa.minY, aa.minZ).func_181675_d();
      worldRenderer.func_181662_b(aa.maxX, aa.minY, aa.minZ).func_181675_d();
      worldRenderer.func_181662_b(aa.maxX, aa.minY, aa.maxZ).func_181675_d();
      worldRenderer.func_181662_b(aa.minX, aa.minY, aa.maxZ).func_181675_d();
      worldRenderer.func_181662_b(aa.minX, aa.minY, aa.minZ).func_181675_d();
      tessellator.draw();
      worldRenderer.func_181668_a(3, DefaultVertexFormats.field_181705_e);
      worldRenderer.func_181662_b(aa.minX, aa.maxY, aa.minZ).func_181675_d();
      worldRenderer.func_181662_b(aa.maxX, aa.maxY, aa.minZ).func_181675_d();
      worldRenderer.func_181662_b(aa.maxX, aa.maxY, aa.maxZ).func_181675_d();
      worldRenderer.func_181662_b(aa.minX, aa.maxY, aa.maxZ).func_181675_d();
      worldRenderer.func_181662_b(aa.minX, aa.maxY, aa.minZ).func_181675_d();
      tessellator.draw();
      worldRenderer.func_181668_a(1, DefaultVertexFormats.field_181705_e);
      worldRenderer.func_181662_b(aa.minX, aa.minY, aa.minZ).func_181675_d();
      worldRenderer.func_181662_b(aa.minX, aa.maxY, aa.minZ).func_181675_d();
      worldRenderer.func_181662_b(aa.maxX, aa.minY, aa.minZ).func_181675_d();
      worldRenderer.func_181662_b(aa.maxX, aa.maxY, aa.minZ).func_181675_d();
      worldRenderer.func_181662_b(aa.maxX, aa.minY, aa.maxZ).func_181675_d();
      worldRenderer.func_181662_b(aa.maxX, aa.maxY, aa.maxZ).func_181675_d();
      worldRenderer.func_181662_b(aa.minX, aa.minY, aa.maxZ).func_181675_d();
      worldRenderer.func_181662_b(aa.minX, aa.maxY, aa.maxZ).func_181675_d();
      tessellator.draw();
   }

   public static void enable3D() {
      GL11.glEnable(3042);
      GL11.glBlendFunc(770, 771);
      GL11.glDisable(3553);
      GL11.glEnable(2848);
      GL11.glDisable(2929);
      GL11.glDepthMask(false);
   }

   public static void disable3D() {
      GL11.glDisable(2848);
      GL11.glEnable(3553);
      GL11.glEnable(2929);
      GL11.glDepthMask(true);
      GL11.glDisable(3042);
   }

   public static void color(int color) {
      GL11.glColor4f((float)(color >> 16 & 255) / 255.0F, (float)(color >> 8 & 255) / 255.0F, (float)(color & 255) / 255.0F, (float)(color >> 24 & 255) / 255.0F);
   }

   public static boolean isInViewFrustrum(Entity entity) {
      return isInViewFrustrum(entity.getEntityBoundingBox()) || entity.ignoreFrustumCheck;
   }

   private static boolean isInViewFrustrum(AxisAlignedBB bb) {
      Entity current = StencilUtil.mc.getRenderViewEntity();
      frustrum.setPosition(current.posX, current.posY, current.posZ);
      return frustrum.isBoundingBoxInFrustum(bb);
   }

   public static void lineNoGl(double firstX, double firstY, double secondX, double secondY, Color color) {
      start();
      if (color != null) {
         color(color);
      }

      lineWidth(2.0D);
      GL11.glEnable(2848);
      begin(1);
      vertex(firstX, firstY);
      vertex(secondX, secondY);
      end();
      GL11.glDisable(2848);
      stop();
   }

   public static void gradient(double x, double y, double width, double height, boolean filled, Color color1, Color color2) {
      start();
      GL11.glShadeModel(7425);
      GlStateManager.enableAlpha();
      GL11.glAlphaFunc(516, 0.0F);
      if (color1 != null) {
         color(color1);
      }

      begin(filled ? 7 : 1);
      vertex(x, y);
      vertex(x + width, y);
      if (color2 != null) {
         color(color2);
      }

      vertex(x + width, y + height);
      vertex(x, y + height);
      if (!filled) {
         vertex(x, y);
         vertex(x, y + height);
         vertex(x + width, y);
         vertex(x + width, y + height);
      }

      end();
      GL11.glAlphaFunc(516, 0.1F);
      GlStateManager.disableAlpha();
      GL11.glShadeModel(7424);
      stop();
   }

   public static void gradient(double x, double y, double width, double height, Color color1, Color color2) {
      gradient(x, y, width, height, true, color1, color2);
   }

   public void gradientCentered(double x, double y, double width, double height, Color color1, Color color2) {
      x -= width / 2.0D;
      y -= height / 2.0D;
      gradient(x, y, width, height, true, color1, color2);
   }

   public static void gradientSideways(double x, double y, double width, double height, boolean filled, Color color1, Color color2) {
      start();
      GL11.glShadeModel(7425);
      GlStateManager.disableAlpha();
      if (color1 != null) {
         color(color1);
      }

      begin(filled ? 6 : 1);
      vertex(x, y);
      vertex(x, y + height);
      if (color2 != null) {
         color(color2);
      }

      vertex(x + width, y + height);
      vertex(x + width, y);
      end();
      GlStateManager.enableAlpha();
      GL11.glShadeModel(7424);
      stop();
   }

   public static void gradientSideways(double x, double y, double width, double height, Color color1, Color color2) {
      gradientSideways(x, y, width, height, true, color1, color2);
   }

   public void gradientSidewaysCentered(double x, double y, double width, double height, Color color1, Color color2) {
      x -= width / 2.0D;
      y -= height / 2.0D;
      gradientSideways(x, y, width, height, true, color1, color2);
   }

   public static void lineWidth(double width) {
      GL11.glLineWidth((float)width);
   }

   public static void color(double red, double green, double blue, double alpha) {
      GL11.glColor4d(red, green, blue, alpha);
   }

   public void color(double red, double green, double blue) {
      color(red, green, blue, 1.0D);
   }

   public static void color(Color color) {
      if (color == null) {
         color = Color.white;
      }

      color((double)((float)color.getRed() / 255.0F), (double)((float)color.getGreen() / 255.0F), (double)((float)color.getBlue() / 255.0F), (double)((float)color.getAlpha() / 255.0F));
   }

   public void color(Color color, int alpha) {
      if (color == null) {
         color = Color.white;
      }

      color((double)((float)color.getRed() / 255.0F), (double)((float)color.getGreen() / 255.0F), (double)((float)color.getBlue() / 255.0F), 0.5D);
   }

   public static void enable(int glTarget) {
      GL11.glEnable(glTarget);
   }

   public static void disable(int glTarget) {
      GL11.glDisable(glTarget);
   }

   public static void start() {
      GL11.glBlendFunc(770, 771);
      disable(3553);
      disable(2884);
      GlStateManager.disableAlpha();
      GlStateManager.disableDepth();
   }

   public static void stop() {
      GlStateManager.enableAlpha();
      GlStateManager.enableDepth();
      enable(2884);
      enable(3553);
      color(Color.white);
   }

   public static void begin(int glMode) {
      GL11.glBegin(glMode);
   }

   public static void end() {
      GL11.glEnd();
   }

   public static void vertex(double x, double y) {
      GL11.glVertex2d(x, y);
   }


   public static void glColor(int hex) {
      float alpha = (float)(hex >> 24 & 255) / 255.0F;
      float red = (float)(hex >> 16 & 255) / 255.0F;
      float green = (float)(hex >> 8 & 255) / 255.0F;
      float blue = (float)(hex & 255) / 255.0F;
      GL11.glColor4f(red, green, blue, alpha);
   }

   public static void prepareOutlineRender(float lineWidth, double r, double g, double b, double a) {
      GlStateManager.pushMatrix();
      GlStateManager.disableTexture2D();
      GlStateManager.enableBlend();
      GlStateManager.blendFunc(770, 771);
      GlStateManager.color((float)r, (float)g, (float)b, (float)a);
      GL11.glLineWidth(lineWidth);
      GL11.glBegin(1);
   }

   public static void stopOutlineRender() {
      GL11.glEnd();
      GlStateManager.enableTexture2D();
      GlStateManager.disableBlend();
      GlStateManager.popMatrix();
   }

   public static void renderEntityOutline(RenderManager renderManager, float partialTicks, Entity entity) {
      double x = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * (double)partialTicks;
      double y = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * (double)partialTicks;
      double z = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * (double)partialTicks;
      AxisAlignedBB boundingBox = entity.getEntityBoundingBox().offset(-x, -y, -z);
      drawBoundingBox(boundingBox);
   }

   public static void renderBlockBox(RenderManager rm, float partialTicks, BlockPos blockPos) {
      IBlockState blockState = StencilUtil.mc.theWorld.getBlockState(blockPos);
      AxisAlignedBB bb = blockState.getBlock().getSelectedBoundingBox(StencilUtil.mc.theWorld, blockPos);
      double posX = (double)blockPos.getX() - rm.renderPosX;
      double posY = (double)blockPos.getY() - rm.renderPosY;
      double posZ = (double)blockPos.getZ() - rm.renderPosZ;
      GL11.glPushMatrix();
      GL11.glEnable(3042);
      GL11.glDisable(3553);
      GL11.glDisable(2929);
      GL11.glDepthMask(false);
      GL11.glBlendFunc(770, 771);
      GL11.glColor4f(0.0F, 0.0F, 1.0F, 0.3F);
      GL11.glBegin(1);
      drawBoundingBox(new AxisAlignedBB(bb.minX - (double)blockPos.getX() + posX, bb.minY - (double)blockPos.getY() + posY, bb.minZ - (double)blockPos.getZ() + posZ, bb.maxX - (double)blockPos.getX() + posX, bb.maxY - (double)blockPos.getY() + posY, bb.maxZ - (double)blockPos.getZ() + posZ));
      GL11.glEnd();
      GL11.glEnable(2929);
      GL11.glDepthMask(true);
      GL11.glEnable(3553);
      GL11.glDisable(3042);
      GL11.glPopMatrix();
   }

   private static void drawBoundingBox(AxisAlignedBB box) {
      double x1 = box.minX;
      double x2 = box.maxX;
      double y1 = box.minY;
      double y2 = box.maxY;
      double z1 = box.minZ;
      double z2 = box.maxZ;
      GL11.glVertex3d(x1, y1, z1);
      GL11.glVertex3d(x2, y1, z1);
      GL11.glVertex3d(x2, y1, z1);
      GL11.glVertex3d(x2, y2, z1);
      GL11.glVertex3d(x2, y2, z1);
      GL11.glVertex3d(x1, y2, z1);
      GL11.glVertex3d(x1, y2, z1);
      GL11.glVertex3d(x1, y1, z1);
      GL11.glVertex3d(x1, y1, z2);
      GL11.glVertex3d(x2, y1, z2);
      GL11.glVertex3d(x2, y1, z2);
      GL11.glVertex3d(x2, y2, z2);
      GL11.glVertex3d(x2, y2, z2);
      GL11.glVertex3d(x1, y2, z2);
      GL11.glVertex3d(x1, y2, z2);
      GL11.glVertex3d(x1, y1, z2);
      GL11.glVertex3d(x1, y1, z1);
      GL11.glVertex3d(x1, y1, z2);
      GL11.glVertex3d(x2, y1, z1);
      GL11.glVertex3d(x2, y1, z2);
      GL11.glVertex3d(x2, y2, z1);
      GL11.glVertex3d(x2, y2, z2);
      GL11.glVertex3d(x1, y2, z1);
      GL11.glVertex3d(x1, y2, z2);
   }

   public static void renderEntityBox(RenderManager rm, float partialTicks, Entity entity) {
      AxisAlignedBB bb = entity.getEntityBoundingBox();
      double posX = interpolate(entity.posX, entity.lastTickPosX, (double)partialTicks);
      double posY = interpolate(entity.posY, entity.lastTickPosY, (double)partialTicks);
      double posZ = interpolate(entity.posZ, entity.lastTickPosZ, (double)partialTicks);
      RenderGlobal.func_181561_a(new AxisAlignedBB(bb.minX - 0.05D - entity.posX + (posX - rm.renderPosX), bb.minY - 0.05D - entity.posY + (posY - rm.renderPosY), bb.minZ - 0.05D - entity.posZ + (posZ - rm.renderPosZ), bb.maxX + 0.05D - entity.posX + (posX - rm.renderPosX), bb.maxY + 0.1D - entity.posY + (posY - rm.renderPosY), bb.maxZ + 0.05D - entity.posZ + (posZ - rm.renderPosZ)));
   }

   public static void renderCustomPlayerBox(RenderManager rm, float partialTicks, double x, double y, double z) {
      renderCustomPlayerBox(rm, partialTicks, x, y, z, x, y, z);
   }

   public static void renderCustomPlayerBox(RenderManager rm, float partialTicks, double x, double y, double z, double lastX, double lastY, double lastZ) {
      AxisAlignedBB bb = new AxisAlignedBB(x - 0.3D, y, z - 0.3D, x + 0.3D, y + 1.8D, z + 0.3D);
      double posX = interpolate(x, lastX, (double)partialTicks);
      double posY = interpolate(y, lastY, (double)partialTicks);
      double posZ = interpolate(z, lastZ, (double)partialTicks);
      RenderGlobal.func_181561_a(new AxisAlignedBB(bb.minX - 0.05D - x + (posX - rm.renderPosX), bb.minY - 0.05D - y + (posY - rm.renderPosY), bb.minZ - 0.05D - z + (posZ - rm.renderPosZ), bb.maxX + 0.05D - x + (posX - rm.renderPosX), bb.maxY + 0.1D - y + (posY - rm.renderPosY), bb.maxZ + 0.05D - z + (posZ - rm.renderPosZ)));
   }

   public static void stopBoxRender() {
      GL11.glDisable(2848);
      GL11.glEnable(3553);
      GL11.glEnable(2929);
      GL11.glDepthMask(true);
      GL11.glDisable(3042);
      GL11.glColor4d(1.0D, 1.0D, 1.0D, 1.0D);
   }

   public static double interpolate(double current, double old, double scale) {
      return old + (current - old) * scale;
   }

   public static void renderBlock(BlockPos blockPos, int color, boolean outline, boolean shade) {
      renderBox((double)blockPos.getX(), (double)blockPos.getY(), (double)blockPos.getZ(), 1.0D, 1.0D, 1.0D, color, outline, shade);
   }

   public static void renderChest(BlockPos blockPos, int color, boolean outline, boolean shade) {
      renderBox((double)((float)blockPos.getX() + 0.0625F), (double)blockPos.getY(), (double)((float)blockPos.getZ() + 0.0625F), 0.875D, 0.875D, 0.875D, color, outline, shade);
   }

   public static void renderBlock(BlockPos blockPos, int color, double y2, boolean outline, boolean shade) {
      renderBox((double)blockPos.getX(), (double)blockPos.getY(), (double)blockPos.getZ(), 1.0D, y2, 1.0D, color, outline, shade);
   }

   public static void renderBlock2(BlockPos blockPos, int color, boolean outline, boolean shade) {
      renderBox2((double)blockPos.getX(), (double)blockPos.getY(), (double)blockPos.getZ(), 1.0D, 1.0D, 1.0D, color, outline, shade);
   }

   public static void renderBox2(double x, double y, double z, double x2, double y2, double z2, int color, boolean outline, boolean shade) {
      double xPos = x - StencilUtil.mc.getRenderManager().viewerPosX;
      double yPos = y - StencilUtil.mc.getRenderManager().viewerPosY;
      double zPos = z - StencilUtil.mc.getRenderManager().viewerPosZ;
      GL11.glPushMatrix();
      GL11.glBlendFunc(770, 771);
      GL11.glEnable(3042);
      GL11.glLineWidth(1.0F);
      GL11.glDisable(3553);
      GL11.glDisable(2929);
      GL11.glDepthMask(false);
      float n8 = (float)(color >> 24 & 255) / 255.0F;
      float n9 = (float)(color >> 16 & 255) / 255.0F;
      float n10 = (float)(color >> 8 & 255) / 255.0F;
      float n11 = (float)(color & 255) / 255.0F;
      GL11.glColor4f(n9, n10, n11, n8);
      AxisAlignedBB axisAlignedBB = new AxisAlignedBB(xPos, yPos, zPos, xPos + x2, yPos + y2, zPos + z2);
      if (outline) {
         RenderGlobal.func_181561_a(axisAlignedBB);
      }

      if (shade) {
         drawBoundingBox(axisAlignedBB, n9, n10, n11);
      }

      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      GL11.glEnable(3553);
      GL11.glEnable(2929);
      GL11.glDepthMask(true);
      GL11.glDisable(3042);
      GL11.glPopMatrix();
   }

   public static void renderBox(double x, double y, double z, double x2, double y2, double z2, int color, boolean outline, boolean shade) {
      double xPos = x - StencilUtil.mc.getRenderManager().viewerPosX;
      double yPos = y - StencilUtil.mc.getRenderManager().viewerPosY;
      double zPos = z - StencilUtil.mc.getRenderManager().viewerPosZ;
      GL11.glPushMatrix();
      GL11.glBlendFunc(770, 771);
      GL11.glEnable(3042);
      GL11.glLineWidth(1.0F);
      GL11.glDisable(3553);
      GL11.glDisable(2929);
      GL11.glDepthMask(false);
      float n8 = (float)(color >> 24 & 255) / 255.0F;
      float n9 = (float)(color >> 16 & 255) / 255.0F;
      float n10 = (float)(color >> 8 & 255) / 255.0F;
      float n11 = (float)(color & 255) / 255.0F;
      GL11.glColor4f(n9, n10, n11, n8);
      AxisAlignedBB axisAlignedBB = new AxisAlignedBB(xPos, yPos, zPos, xPos + x2, yPos + y2, zPos + z2);
      if (outline) {
         RenderGlobal.func_181561_a(axisAlignedBB);
      }

      if (shade) {
         drawBoundingBox(axisAlignedBB, n9, n10, n11);
      }

      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      GL11.glEnable(3553);
      GL11.glEnable(2929);
      GL11.glDepthMask(true);
      GL11.glDisable(3042);
      GL11.glPopMatrix();
   }

   public static void drawBoundingBox(AxisAlignedBB abb, float r, float g, float b) {
      drawBoundingBox(abb, r, g, b, 0.25F);
   }

   public static void drawBoundingBox(AxisAlignedBB abb, float r, float g, float b, float a) {
      /*Tessellator ts = Tessellator.getInstance();
      WorldRenderer vb = ts.getWorldRenderer();
      vb.begin(7, DefaultVertexFormats.POSITION_COLOR);
      vb.pos(abb.minX, abb.minY, abb.minZ).color(r, g, b, a).endVertex();
      vb.pos(abb.minX, abb.maxY, abb.minZ).color(r, g, b, a).endVertex();
      vb.pos(abb.maxX, abb.minY, abb.minZ).color(r, g, b, a).endVertex();
      vb.pos(abb.maxX, abb.maxY, abb.minZ).color(r, g, b, a).endVertex();
      vb.pos(abb.maxX, abb.minY, abb.maxZ).color(r, g, b, a).endVertex();
      vb.pos(abb.maxX, abb.maxY, abb.maxZ).color(r, g, b, a).endVertex();
      vb.pos(abb.minX, abb.minY, abb.maxZ).color(r, g, b, a).endVertex();
      vb.pos(abb.minX, abb.maxY, abb.maxZ).color(r, g, b, a).endVertex();
      ts.draw();
      vb.begin(7, DefaultVertexFormats.POSITION_COLOR);
      vb.pos(abb.maxX, abb.maxY, abb.minZ).color(r, g, b, a).endVertex();
      vb.pos(abb.maxX, abb.minY, abb.minZ).color(r, g, b, a).endVertex();
      vb.pos(abb.minX, abb.maxY, abb.minZ).color(r, g, b, a).endVertex();
      vb.pos(abb.minX, abb.minY, abb.minZ).color(r, g, b, a).endVertex();
      vb.pos(abb.minX, abb.maxY, abb.maxZ).color(r, g, b, a).endVertex();
      vb.pos(abb.minX, abb.minY, abb.maxZ).color(r, g, b, a).endVertex();
      vb.pos(abb.maxX, abb.maxY, abb.maxZ).color(r, g, b, a).endVertex();
      vb.pos(abb.maxX, abb.minY, abb.maxZ).color(r, g, b, a).endVertex();
      ts.draw();
      vb.begin(7, DefaultVertexFormats.POSITION_COLOR);
      vb.pos(abb.minX, abb.maxY, abb.minZ).color(r, g, b, a).endVertex();
      vb.pos(abb.maxX, abb.maxY, abb.minZ).color(r, g, b, a).endVertex();
      vb.pos(abb.maxX, abb.maxY, abb.maxZ).color(r, g, b, a).endVertex();
      vb.pos(abb.minX, abb.maxY, abb.maxZ).color(r, g, b, a).endVertex();
      vb.pos(abb.minX, abb.maxY, abb.minZ).color(r, g, b, a).endVertex();
      vb.pos(abb.minX, abb.maxY, abb.maxZ).color(r, g, b, a).endVertex();
      vb.pos(abb.maxX, abb.maxY, abb.maxZ).color(r, g, b, a).endVertex();
      vb.pos(abb.maxX, abb.maxY, abb.minZ).color(r, g, b, a).endVertex();
      ts.draw();
      vb.begin(7, DefaultVertexFormats.POSITION_COLOR);
      vb.pos(abb.minX, abb.minY, abb.minZ).color(r, g, b, a).endVertex();
      vb.pos(abb.maxX, abb.minY, abb.minZ).color(r, g, b, a).endVertex();
      vb.pos(abb.maxX, abb.minY, abb.maxZ).color(r, g, b, a).endVertex();
      vb.pos(abb.minX, abb.minY, abb.maxZ).color(r, g, b, a).endVertex();
      vb.pos(abb.minX, abb.minY, abb.minZ).color(r, g, b, a).endVertex();
      vb.pos(abb.minX, abb.minY, abb.maxZ).color(r, g, b, a).endVertex();
      vb.pos(abb.maxX, abb.minY, abb.maxZ).color(r, g, b, a).endVertex();
      vb.pos(abb.maxX, abb.minY, abb.minZ).color(r, g, b, a).endVertex();
      ts.draw();
      vb.begin(7, DefaultVertexFormats.POSITION_COLOR);
      vb.pos(abb.minX, abb.minY, abb.minZ).color(r, g, b, a).endVertex();
      vb.pos(abb.minX, abb.maxY, abb.minZ).color(r, g, b, a).endVertex();
      vb.pos(abb.minX, abb.minY, abb.maxZ).color(r, g, b, a).endVertex();
      vb.pos(abb.minX, abb.maxY, abb.maxZ).color(r, g, b, a).endVertex();
      vb.pos(abb.maxX, abb.minY, abb.maxZ).color(r, g, b, a).endVertex();
      vb.pos(abb.maxX, abb.maxY, abb.maxZ).color(r, g, b, a).endVertex();
      vb.pos(abb.maxX, abb.minY, abb.minZ).color(r, g, b, a).endVertex();
      vb.pos(abb.maxX, abb.maxY, abb.minZ).color(r, g, b, a).endVertex();
      ts.draw();
      vb.begin(7, DefaultVertexFormats.POSITION_COLOR);
      vb.pos(abb.minX, abb.maxY, abb.maxZ).color(r, g, b, a).endVertex();
      vb.pos(abb.minX, abb.minY, abb.maxZ).color(r, g, b, a).endVertex();
      vb.pos(abb.minX, abb.maxY, abb.minZ).color(r, g, b, a).endVertex();
      vb.pos(abb.minX, abb.minY, abb.minZ).color(r, g, b, a).endVertex();
      vb.pos(abb.maxX, abb.maxY, abb.minZ).color(r, g, b, a).endVertex();
      vb.pos(abb.maxX, abb.minY, abb.minZ).color(r, g, b, a).endVertex();
      vb.pos(abb.maxX, abb.maxY, abb.maxZ).color(r, g, b, a).endVertex();
      vb.pos(abb.maxX, abb.minY, abb.maxZ).color(r, g, b, a).endVertex();
      ts.draw();*/
   }
}
