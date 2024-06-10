/*  1:   */ package net.minecraft.client.resources.data;
/*  2:   */ 
/*  3:   */ public class PackMetadataSection
/*  4:   */   implements IMetadataSection
/*  5:   */ {
/*  6:   */   private final String packDescription;
/*  7:   */   private final int packFormat;
/*  8:   */   private static final String __OBFID = "CL_00001112";
/*  9:   */   
/* 10:   */   public PackMetadataSection(String par1Str, int par2)
/* 11:   */   {
/* 12:11 */     this.packDescription = par1Str;
/* 13:12 */     this.packFormat = par2;
/* 14:   */   }
/* 15:   */   
/* 16:   */   public String getPackDescription()
/* 17:   */   {
/* 18:17 */     return this.packDescription;
/* 19:   */   }
/* 20:   */   
/* 21:   */   public int getPackFormat()
/* 22:   */   {
/* 23:22 */     return this.packFormat;
/* 24:   */   }
/* 25:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.resources.data.PackMetadataSection
 * JD-Core Version:    0.7.0.1
 */