package net.minecraft.client.renderer;

public class Tessellator {
   public static final Tessellator instance = new Tessellator(2097152);
   private WorldVertexBufferUploader field_178182_b = new WorldVertexBufferUploader();
   private WorldRenderer worldRenderer;
   private static final String __OBFID = "CL_00000960";

   public int draw() {
      return this.field_178182_b.draw(this.worldRenderer, this.worldRenderer.draw());
   }

   public WorldRenderer getWorldRenderer() {
      return this.worldRenderer;
   }

   public static Tessellator getInstance() {
      return instance;
   }

   public Tessellator(int var1) {
      this.worldRenderer = new WorldRenderer(var1);
   }
}
