package exhibition.util.render.para;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import org.lwjgl.opengl.GL11;

public class Circle extends Render {
   public static void drawBase(double x1, double y1, double x2, double y2, double start, double end, double inc, int color, int code, float width) {
      pre();
      double x3 = x1;
      x1 = Math.min(x1, x2);
      x2 = Math.max(x2, x3);
      double y3 = y1;
      y1 = Math.min(y1, y2);
      y2 = Math.max(y2, y3);
      if (width > 0.0F || code != 2 && code != 3) {
         width = Math.min(width, 10.0F);
         Tessellator tes = Tessellator.getInstance();
         WorldRenderer buf = tes.getWorldRenderer();
         float f = (float)(color >> 24 & 255) / 255.0F;
         float f1 = (float)(color >> 16 & 255) / 255.0F;
         float f2 = (float)(color >> 8 & 255) / 255.0F;
         float f3 = (float)(color & 255) / 255.0F;
         GlStateManager.enableAlpha();
         GlStateManager.enableBlend();
         GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
         RenderHelper.disableStandardItemLighting();
         GlStateManager.disableTextures();
         float line = GL11.glGetFloat(2849);
         if (width > 0.0F) {
            GL11.glLineWidth(width);
         }

         int shade = GL11.glGetInteger(2900);
         GlStateManager.shadeModel(7425);
         double w = x2 - x1;
         double h = y2 - y1;
         double cx = x1 + w / 2.0D;
         double cy = y1 + h / 2.0D;
         if (inc == 0.0D) {
            inc = Math.max(360.0D / Math.max(w, h), 1.0D);
         }

         buf.setVertexFormat(DefaultVertexFormats.field_176599_b);
         if (start != end % 360.0D && code != 3) {
            GL11.glColor4f(f1, f2, f3, f);
            buf.addVertex(x1 + w / 2.0D, y1 + h / 2.0D, 0.0D);
         }

         for(double i = Math.max(start, end); i >= Math.min(start, end); i -= inc) {
            double j = Math.toRadians(i);
            double _x = cx + Math.cos(j) * (w / 2.0D);
            double _y = cy + Math.sin(j) * (h / 2.0D);
            GL11.glColor4f(f1, f2, f3, f);
            buf.addVertex(_x, _y, 0.0D);
         }

         tes.draw();
         GlStateManager.shadeModel(shade);
         GL11.glLineWidth(line);
         post();
      }
   }

   public static void draw(double x1, double y1, double x2, double y2, double start, double end, double inc, int color) {
      drawBase(x1, y1, x2, y2, start, end, inc, color, 9, 0.0F);
   }

   public static void draw(double x1, double y1, double x2, double y2, int color) {
      drawBase(x1, y1, x2, y2, 0.0D, 360.0D, 0.0D, color, 9, 0.0F);
   }

   public static void draw(double x, double y, double r, double start, double end, double inc, int color) {
      drawBase(x - r, y - r, x + r, y + r, start, end, inc, color, 9, 0.0F);
   }

   public static void draw(double x, double y, double r, int color) {
      drawBase(x - r, y - r, x + r, y + r, 0.0D, 360.0D, 0.0D, color, 9, 0.0F);
   }

   public static void drawOutline(double x1, double y1, double x2, double y2, double start, double end, double inc, int color, float width, boolean connected) {
      drawBase(x1, y1, x2, y2, start, end, inc, color, connected ? 2 : 3, width);
   }

   public static void drawOutline(double x, double y, double r, double start, double end, double inc, int color, float width, boolean connected) {
      drawBase(x - r, y - r, x + r, y + r, start, end, inc, color, connected ? 2 : 3, width);
   }

   public static void drawOutline(double x1, double y1, double x2, double y2, int color, float width) {
      drawBase(x1, y1, x2, y2, 0.0D, 360.0D, 0.0D, color, 2, width);
   }

   public static void drawOutline(double x, double y, double r, int color, float width) {
      drawBase(x - r, y - r, x + r, y + r, 0.0D, 360.0D, 0.0D, color, 2, width);
   }
}
