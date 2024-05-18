package rina.turok.turok.draw;

import java.util.Arrays;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.math.BlockPos;
import org.lwjgl.opengl.GL11;

public class TurokRenderHelp extends Tessellator {
   public static TurokRenderHelp INSTANCE = new TurokRenderHelp();

   public TurokRenderHelp() {
      super(2097152);
   }

   public static void prepare(String mode_requested) {
      int mode = 0;
      if (mode_requested.equalsIgnoreCase("quads")) {
         mode = 7;
      } else if (mode_requested.equalsIgnoreCase("lines")) {
         mode = 1;
      }

      prepare_gl(1.0F);
      begin(mode);
   }

   public static void prepare(String mode_requested, float size) {
      int mode = 0;
      if (mode_requested.equalsIgnoreCase("quads")) {
         mode = 7;
      } else if (mode_requested.equalsIgnoreCase("lines")) {
         mode = 1;
      }

      prepare_gl(size);
      begin(mode);
   }

   public static void prepare_gl(float size) {
      GL11.glBlendFunc(770, 771);
      GlStateManager.tryBlendFuncSeparate(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA, SourceFactor.ONE, DestFactor.ZERO);
      GlStateManager.glLineWidth(size);
      GlStateManager.disableTexture2D();
      GlStateManager.depthMask(false);
      GlStateManager.enableBlend();
      GlStateManager.disableDepth();
      GlStateManager.disableLighting();
      GlStateManager.disableCull();
      GlStateManager.enableAlpha();
      GlStateManager.color(1.0F, 1.0F, 1.0F);
   }

   public static void begin(int mode) {
      INSTANCE.getBuffer().begin(mode, DefaultVertexFormats.POSITION_COLOR);
   }

   public static void release() {
      render();
      release_gl();
   }

   public static void render() {
      INSTANCE.draw();
   }

   public static void release_gl() {
      GlStateManager.enableCull();
      GlStateManager.depthMask(true);
      GlStateManager.enableTexture2D();
      GlStateManager.enableBlend();
      GlStateManager.enableDepth();
   }

   public static void draw_cube(BlockPos blockPos, int argb, String sides) {
      int a = argb >>> 24 & 255;
      int r = argb >>> 16 & 255;
      int g = argb >>> 8 & 255;
      int b = argb & 255;
      draw_cube(blockPos, r, g, b, a, sides);
   }

   public static void draw_cube(float x, float y, float z, int argb, String sides) {
      int a = argb >>> 24 & 255;
      int r = argb >>> 16 & 255;
      int g = argb >>> 8 & 255;
      int b = argb & 255;
      draw_cube(INSTANCE.getBuffer(), x, y, z, 1.0F, 1.0F, 1.0F, r, g, b, a, sides);
   }

   public static void draw_cube(BlockPos blockPos, int r, int g, int b, int a, String sides) {
      draw_cube(INSTANCE.getBuffer(), (float)blockPos.x, (float)blockPos.y, (float)blockPos.z, 1.0F, 1.0F, 1.0F, r, g, b, a, sides);
   }

   public static void draw_cube_line(BlockPos blockPos, int argb, String sides) {
      int a = argb >>> 24 & 255;
      int r = argb >>> 16 & 255;
      int g = argb >>> 8 & 255;
      int b = argb & 255;
      draw_cube_line(blockPos, r, g, b, a, sides);
   }

   public static void draw_cube_line(float x, float y, float z, int argb, String sides) {
      int a = argb >>> 24 & 255;
      int r = argb >>> 16 & 255;
      int g = argb >>> 8 & 255;
      int b = argb & 255;
      draw_cube_line(INSTANCE.getBuffer(), x, y, z, 1.0F, 1.0F, 1.0F, r, g, b, a, sides);
   }

   public static void draw_cube_line(BlockPos blockPos, int r, int g, int b, int a, String sides) {
      draw_cube_line(INSTANCE.getBuffer(), (float)blockPos.x, (float)blockPos.y, (float)blockPos.z, 1.0F, 1.0F, 1.0F, r, g, b, a, sides);
   }

   public static BufferBuilder get_buffer_build() {
      return INSTANCE.getBuffer();
   }

   public static void draw_cube(BufferBuilder buffer, float x, float y, float z, float w, float h, float d, int r, int g, int b, int a, String sides) {
      if (Arrays.asList(sides.split("-")).contains("down") || sides.equalsIgnoreCase("all")) {
         buffer.pos((double)(x + w), (double)y, (double)z).color(r, g, b, a).endVertex();
         buffer.pos((double)(x + w), (double)y, (double)(z + d)).color(r, g, b, a).endVertex();
         buffer.pos((double)x, (double)y, (double)(z + d)).color(r, g, b, a).endVertex();
         buffer.pos((double)x, (double)y, (double)z).color(r, g, b, a).endVertex();
      }

      if (Arrays.asList(sides.split("-")).contains("up") || sides.equalsIgnoreCase("all")) {
         buffer.pos((double)(x + w), (double)(y + h), (double)z).color(r, g, b, a).endVertex();
         buffer.pos((double)x, (double)(y + h), (double)z).color(r, g, b, a).endVertex();
         buffer.pos((double)x, (double)(y + h), (double)(z + d)).color(r, g, b, a).endVertex();
         buffer.pos((double)(x + w), (double)(y + h), (double)(z + d)).color(r, g, b, a).endVertex();
      }

      if (Arrays.asList(sides.split("-")).contains("north") || sides.equalsIgnoreCase("all")) {
         buffer.pos((double)(x + w), (double)y, (double)z).color(r, g, b, a).endVertex();
         buffer.pos((double)x, (double)y, (double)z).color(r, g, b, a).endVertex();
         buffer.pos((double)x, (double)(y + h), (double)z).color(r, g, b, a).endVertex();
         buffer.pos((double)(x + w), (double)(y + h), (double)z).color(r, g, b, a).endVertex();
      }

      if (Arrays.asList(sides.split("-")).contains("south") || sides.equalsIgnoreCase("all")) {
         buffer.pos((double)x, (double)y, (double)(z + d)).color(r, g, b, a).endVertex();
         buffer.pos((double)(x + w), (double)y, (double)(z + d)).color(r, g, b, a).endVertex();
         buffer.pos((double)(x + w), (double)(y + h), (double)(z + d)).color(r, g, b, a).endVertex();
         buffer.pos((double)x, (double)(y + h), (double)(z + d)).color(r, g, b, a).endVertex();
      }

      if (Arrays.asList(sides.split("-")).contains("south") || sides.equalsIgnoreCase("all")) {
         buffer.pos((double)x, (double)y, (double)z).color(r, g, b, a).endVertex();
         buffer.pos((double)x, (double)y, (double)(z + d)).color(r, g, b, a).endVertex();
         buffer.pos((double)x, (double)(y + h), (double)(z + d)).color(r, g, b, a).endVertex();
         buffer.pos((double)x, (double)(y + h), (double)z).color(r, g, b, a).endVertex();
      }

      if (Arrays.asList(sides.split("-")).contains("south") || sides.equalsIgnoreCase("all")) {
         buffer.pos((double)(x + w), (double)y, (double)(z + d)).color(r, g, b, a).endVertex();
         buffer.pos((double)(x + w), (double)y, (double)z).color(r, g, b, a).endVertex();
         buffer.pos((double)(x + w), (double)(y + h), (double)z).color(r, g, b, a).endVertex();
         buffer.pos((double)(x + w), (double)(y + h), (double)(z + d)).color(r, g, b, a).endVertex();
      }

   }

   public static void draw_cube_line(BufferBuilder buffer, float x, float y, float z, float w, float h, float d, int r, int g, int b, int a, String sides) {
      if (Arrays.asList(sides.split("-")).contains("downwest") || sides.equalsIgnoreCase("all")) {
         buffer.pos((double)x, (double)y, (double)z).color(r, g, b, a).endVertex();
         buffer.pos((double)x, (double)y, (double)(z + d)).color(r, g, b, a).endVertex();
      }

      if (Arrays.asList(sides.split("-")).contains("upwest") || sides.equalsIgnoreCase("all")) {
         buffer.pos((double)x, (double)(y + h), (double)z).color(r, g, b, a).endVertex();
         buffer.pos((double)x, (double)(y + h), (double)(z + d)).color(r, g, b, a).endVertex();
      }

      if (Arrays.asList(sides.split("-")).contains("downeast") || sides.equalsIgnoreCase("all")) {
         buffer.pos((double)(x + w), (double)y, (double)z).color(r, g, b, a).endVertex();
         buffer.pos((double)(x + w), (double)y, (double)(z + d)).color(r, g, b, a).endVertex();
      }

      if (Arrays.asList(sides.split("-")).contains("upeast") || sides.equalsIgnoreCase("all")) {
         buffer.pos((double)(x + w), (double)(y + h), (double)z).color(r, g, b, a).endVertex();
         buffer.pos((double)(x + w), (double)(y + h), (double)(z + d)).color(r, g, b, a).endVertex();
      }

      if (Arrays.asList(sides.split("-")).contains("downnorth") || sides.equalsIgnoreCase("all")) {
         buffer.pos((double)x, (double)y, (double)z).color(r, g, b, a).endVertex();
         buffer.pos((double)(x + w), (double)y, (double)z).color(r, g, b, a).endVertex();
      }

      if (Arrays.asList(sides.split("-")).contains("upnorth") || sides.equalsIgnoreCase("all")) {
         buffer.pos((double)x, (double)(y + h), (double)z).color(r, g, b, a).endVertex();
         buffer.pos((double)(x + w), (double)(y + h), (double)z).color(r, g, b, a).endVertex();
      }

      if (Arrays.asList(sides.split("-")).contains("downsouth") || sides.equalsIgnoreCase("all")) {
         buffer.pos((double)x, (double)y, (double)(z + d)).color(r, g, b, a).endVertex();
         buffer.pos((double)(x + w), (double)y, (double)(z + d)).color(r, g, b, a).endVertex();
      }

      if (Arrays.asList(sides.split("-")).contains("upsouth") || sides.equalsIgnoreCase("all")) {
         buffer.pos((double)x, (double)(y + h), (double)(z + d)).color(r, g, b, a).endVertex();
         buffer.pos((double)(x + w), (double)(y + h), (double)(z + d)).color(r, g, b, a).endVertex();
      }

      if (Arrays.asList(sides.split("-")).contains("nortwest") || sides.equalsIgnoreCase("all")) {
         buffer.pos((double)x, (double)y, (double)z).color(r, g, b, a).endVertex();
         buffer.pos((double)x, (double)(y + h), (double)z).color(r, g, b, a).endVertex();
      }

      if (Arrays.asList(sides.split("-")).contains("norteast") || sides.equalsIgnoreCase("all")) {
         buffer.pos((double)(x + w), (double)y, (double)z).color(r, g, b, a).endVertex();
         buffer.pos((double)(x + w), (double)(y + h), (double)z).color(r, g, b, a).endVertex();
      }

      if (Arrays.asList(sides.split("-")).contains("southweast") || sides.equalsIgnoreCase("all")) {
         buffer.pos((double)x, (double)y, (double)(z + d)).color(r, g, b, a).endVertex();
         buffer.pos((double)x, (double)(y + h), (double)(z + d)).color(r, g, b, a).endVertex();
      }

      if (Arrays.asList(sides.split("-")).contains("southeast") || sides.equalsIgnoreCase("all")) {
         buffer.pos((double)(x + w), (double)y, (double)(z + d)).color(r, g, b, a).endVertex();
         buffer.pos((double)(x + w), (double)(y + h), (double)(z + d)).color(r, g, b, a).endVertex();
      }

   }
}
