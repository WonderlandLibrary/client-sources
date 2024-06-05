package net.minecraft.tileentity;

public class TileEntityBeacon$BeamSegment {
   private final float[] colors;
   private int height;

   public TileEntityBeacon$BeamSegment(float[] var1) {
      this.colors = var1;
      this.height = 1;
   }

   protected void incrementHeight() {
      ++this.height;
   }

   public float[] getColors() {
      return this.colors;
   }

   public int getHeight() {
      return this.height;
   }
}
