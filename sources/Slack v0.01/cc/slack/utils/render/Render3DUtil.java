package cc.slack.utils.render;

import cc.slack.utils.client.mc;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.AxisAlignedBB;
import org.lwjgl.opengl.GL11;

public final class Render3DUtil extends mc {
   private static final Map<String, Map<Integer, Boolean>> glCapMap = new HashMap();

   public static void drawAABB(AxisAlignedBB boundingBox) {
      GL11.glBlendFunc(770, 771);
      enableGlCap(3042);
      disableGlCap(3553, 2929);
      GL11.glDepthMask(false);
      GL11.glLineWidth(1.5F);
      enableGlCap(2848);
      drawSelectionBoundingBox(boundingBox);
      GlStateManager.resetColor();
      GL11.glDepthMask(true);
      resetCaps();
   }

   public static void drawSelectionBoundingBox(AxisAlignedBB boundingBox) {
      Tessellator tessellator = Tessellator.getInstance();
      WorldRenderer worldrenderer = tessellator.getWorldRenderer();
      worldrenderer.begin(3, DefaultVertexFormats.POSITION_TEX);
      worldrenderer.pos(boundingBox.minX, boundingBox.minY, boundingBox.minZ).endVertex();
      worldrenderer.pos(boundingBox.minX, boundingBox.minY, boundingBox.maxZ).endVertex();
      worldrenderer.pos(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ).endVertex();
      worldrenderer.pos(boundingBox.maxX, boundingBox.minY, boundingBox.minZ).endVertex();
      worldrenderer.pos(boundingBox.minX, boundingBox.minY, boundingBox.minZ).endVertex();
      worldrenderer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.minZ).endVertex();
      worldrenderer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ).endVertex();
      worldrenderer.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ).endVertex();
      worldrenderer.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ).endVertex();
      worldrenderer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.minZ).endVertex();
      worldrenderer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ).endVertex();
      worldrenderer.pos(boundingBox.minX, boundingBox.minY, boundingBox.maxZ).endVertex();
      worldrenderer.pos(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ).endVertex();
      worldrenderer.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ).endVertex();
      worldrenderer.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ).endVertex();
      worldrenderer.pos(boundingBox.maxX, boundingBox.minY, boundingBox.minZ).endVertex();
      tessellator.draw();
   }

   public static void enableGlCap(int cap, String scale) {
      setGlCap(cap, true, scale);
   }

   public static void enableGlCap(int cap) {
      enableGlCap(cap, "COMMON");
   }

   public static void disableGlCap(int... caps) {
      int[] var1 = caps;
      int var2 = caps.length;

      for(int var3 = 0; var3 < var2; ++var3) {
         int cap = var1[var3];
         setGlCap(cap, false, "COMMON");
      }

   }

   public static void setGlCap(int cap, boolean state, String scale) {
      if (!glCapMap.containsKey(scale)) {
         glCapMap.put(scale, new HashMap());
      }

      ((Map)glCapMap.get(scale)).put(cap, GL11.glGetBoolean(cap));
      setGlState(cap, state);
   }

   public static void setGlState(int cap, boolean state) {
      if (state) {
         GL11.glEnable(cap);
      } else {
         GL11.glDisable(cap);
      }

   }

   public static void glColor(int red, int green, int blue, int alpha) {
      GlStateManager.color((float)red / 255.0F, (float)green / 255.0F, (float)blue / 255.0F, (float)alpha / 255.0F);
   }

   public static void resetCaps(String scale) {
      if (glCapMap.containsKey(scale)) {
         Map<Integer, Boolean> map = (Map)glCapMap.get(scale);
         map.forEach(Render3DUtil::setGlState);
         map.clear();
      }
   }

   public static void resetCaps() {
      resetCaps("COMMON");
   }
}
