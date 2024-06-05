package net.minecraft.client.renderer;

import net.HN;
import net.minecraft.client.renderer.WorldRenderer;

public class Tessellator {
   private WorldRenderer worldRenderer;
   private HN b = new HN();
   private static final Tessellator instance = new Tessellator(2097152);

   public static Tessellator getInstance() {
      return instance;
   }

   public Tessellator(int var1) {
      this.worldRenderer = new WorldRenderer(var1);
   }

   public void draw() {
      this.worldRenderer.finishDrawing();
      this.b.func_181679_a(this.worldRenderer);
   }

   public WorldRenderer getWorldRenderer() {
      return this.worldRenderer;
   }
}
