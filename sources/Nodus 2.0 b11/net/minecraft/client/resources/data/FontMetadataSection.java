/*  1:   */ package net.minecraft.client.resources.data;
/*  2:   */ 
/*  3:   */ public class FontMetadataSection
/*  4:   */   implements IMetadataSection
/*  5:   */ {
/*  6:   */   private final float[] charWidths;
/*  7:   */   private final float[] charLefts;
/*  8:   */   private final float[] charSpacings;
/*  9:   */   private static final String __OBFID = "CL_00001108";
/* 10:   */   
/* 11:   */   public FontMetadataSection(float[] par1ArrayOfFloat, float[] par2ArrayOfFloat, float[] par3ArrayOfFloat)
/* 12:   */   {
/* 13:12 */     this.charWidths = par1ArrayOfFloat;
/* 14:13 */     this.charLefts = par2ArrayOfFloat;
/* 15:14 */     this.charSpacings = par3ArrayOfFloat;
/* 16:   */   }
/* 17:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.resources.data.FontMetadataSection
 * JD-Core Version:    0.7.0.1
 */