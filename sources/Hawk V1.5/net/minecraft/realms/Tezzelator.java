package net.minecraft.realms;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;

public class Tezzelator {
   private static final String __OBFID = "CL_00001855";
   public static final Tezzelator instance = new Tezzelator();
   public static Tessellator t = Tessellator.getInstance();

   public void normal(float var1, float var2, float var3) {
      t.getWorldRenderer().func_178980_d(var1, var2, var3);
   }

   public void noColor() {
      t.getWorldRenderer().markDirty();
   }

   public void color(int var1, int var2) {
      t.getWorldRenderer().func_178974_a(var1, var2);
   }

   public void begin(int var1) {
      t.getWorldRenderer().startDrawing(var1);
   }

   public void color(float var1, float var2, float var3, float var4) {
      t.getWorldRenderer().func_178960_a(var1, var2, var3, var4);
   }

   public void color(int var1, int var2, int var3, int var4) {
      t.getWorldRenderer().func_178961_b(var1, var2, var3, var4);
   }

   public void vertex(double var1, double var3, double var5) {
      t.getWorldRenderer().addVertex(var1, var3, var5);
   }

   public void begin() {
      t.getWorldRenderer().startDrawingQuads();
   }

   public WorldRenderer.State sortQuads(float var1, float var2, float var3) {
      return t.getWorldRenderer().getVertexState(var1, var2, var3);
   }

   public void color(int var1) {
      t.getWorldRenderer().func_178991_c(var1);
   }

   public void color(byte var1, byte var2, byte var3) {
      t.getWorldRenderer().func_178982_a(var1, var2, var3);
   }

   public void color(int var1, int var2, int var3) {
      t.getWorldRenderer().setPosition(var1, var2, var3);
   }

   public void vertexUV(double var1, double var3, double var5, double var7, double var9) {
      t.getWorldRenderer().addVertexWithUV(var1, var3, var5, var7, var9);
   }

   public void tex(double var1, double var3) {
      t.getWorldRenderer().setTextureUV(var1, var3);
   }

   public void offset(double var1, double var3, double var5) {
      t.getWorldRenderer().setTranslation(var1, var3, var5);
   }

   public int end() {
      return t.draw();
   }

   public void tex2(int var1) {
      t.getWorldRenderer().func_178963_b(var1);
   }

   public void restoreState(WorldRenderer.State var1) {
      t.getWorldRenderer().setVertexState(var1);
   }

   public void color(float var1, float var2, float var3) {
      t.getWorldRenderer().func_178986_b(var1, var2, var3);
   }
}
