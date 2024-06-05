package hawk.util;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.util.AxisAlignedBB;
import org.lwjgl.opengl.GL11;

public class RenderUtil {
   public static void drawBoundingBox(AxisAlignedBB var0, float var1, float var2, float var3, float var4) {
      byte var5 = 3;
      GL11.glEnable(3042);
      GL11.glBlendFunc(770, 771);
      GL11.glColor4d((double)var1, (double)var2, (double)var3, (double)var4);
      GL11.glDisable(3553);
      GL11.glEnable(2848);
      GL11.glDisable(2929);
      GL11.glDepthMask(false);
      GL11.glLineWidth(3.0F);
      GL11.glEnable(2848);
      Tessellator var6 = Tessellator.getInstance();
      WorldRenderer var7 = var6.getWorldRenderer();
      var7.startDrawing(var5);
      var7.addVertex(var0.minX, var0.minY, var0.minZ);
      var7.addVertex(var0.minX, var0.minY, var0.maxZ);
      var7.addVertex(var0.maxX, var0.minY, var0.maxZ);
      var7.addVertex(var0.maxX, var0.minY, var0.minZ);
      var7.addVertex(var0.minX, var0.minY, var0.minZ);
      var6.draw();
      var7.startDrawing(var5);
      var7.addVertex(var0.maxX, var0.maxY, var0.maxZ);
      var7.addVertex(var0.maxX, var0.maxY, var0.minZ);
      var7.addVertex(var0.minX, var0.maxY, var0.minZ);
      var7.addVertex(var0.minX, var0.maxY, var0.maxZ);
      var7.addVertex(var0.maxX, var0.maxY, var0.maxZ);
      var6.draw();
      var7.startDrawing(var5);
      var7.addVertex(var0.minX, var0.minY, var0.minZ);
      var7.addVertex(var0.minX, var0.maxY, var0.minZ);
      var7.addVertex(var0.maxX, var0.maxY, var0.minZ);
      var7.addVertex(var0.maxX, var0.minY, var0.minZ);
      var7.addVertex(var0.minX, var0.minY, var0.minZ);
      var6.draw();
      var7.startDrawing(var5);
      var7.addVertex(var0.minX, var0.minY, var0.maxZ);
      var7.addVertex(var0.minX, var0.maxY, var0.maxZ);
      var6.draw();
      var7.startDrawing(var5);
      var7.addVertex(var0.maxX, var0.minY, var0.maxZ);
      var7.addVertex(var0.maxX, var0.maxY, var0.maxZ);
      var6.draw();
      GL11.glDisable(2848);
      GL11.glEnable(3553);
      GL11.glEnable(2929);
      GL11.glDepthMask(true);
      GL11.glDisable(3042);
      GL11.glDisable(2848);
   }
}
