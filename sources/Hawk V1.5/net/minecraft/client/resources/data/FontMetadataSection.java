package net.minecraft.client.resources.data;

public class FontMetadataSection implements IMetadataSection {
   private final float[] charWidths;
   private final float[] charSpacings;
   private static final String __OBFID = "CL_00001108";
   private final float[] charLefts;

   public FontMetadataSection(float[] var1, float[] var2, float[] var3) {
      this.charWidths = var1;
      this.charLefts = var2;
      this.charSpacings = var3;
   }
}
