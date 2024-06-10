/*  1:   */ package net.minecraft.client.resources;
/*  2:   */ 
/*  3:   */ public class Language
/*  4:   */   implements Comparable
/*  5:   */ {
/*  6:   */   private final String languageCode;
/*  7:   */   private final String region;
/*  8:   */   private final String name;
/*  9:   */   private final boolean bidirectional;
/* 10:   */   private static final String __OBFID = "CL_00001095";
/* 11:   */   
/* 12:   */   public Language(String par1Str, String par2Str, String par3Str, boolean par4)
/* 13:   */   {
/* 14:13 */     this.languageCode = par1Str;
/* 15:14 */     this.region = par2Str;
/* 16:15 */     this.name = par3Str;
/* 17:16 */     this.bidirectional = par4;
/* 18:   */   }
/* 19:   */   
/* 20:   */   public String getLanguageCode()
/* 21:   */   {
/* 22:21 */     return this.languageCode;
/* 23:   */   }
/* 24:   */   
/* 25:   */   public boolean isBidirectional()
/* 26:   */   {
/* 27:26 */     return this.bidirectional;
/* 28:   */   }
/* 29:   */   
/* 30:   */   public String toString()
/* 31:   */   {
/* 32:31 */     return String.format("%s (%s)", new Object[] { this.name, this.region });
/* 33:   */   }
/* 34:   */   
/* 35:   */   public boolean equals(Object par1Obj)
/* 36:   */   {
/* 37:36 */     return !(par1Obj instanceof Language) ? false : this == par1Obj ? true : this.languageCode.equals(((Language)par1Obj).languageCode);
/* 38:   */   }
/* 39:   */   
/* 40:   */   public int hashCode()
/* 41:   */   {
/* 42:41 */     return this.languageCode.hashCode();
/* 43:   */   }
/* 44:   */   
/* 45:   */   public int compareTo(Language par1Language)
/* 46:   */   {
/* 47:46 */     return this.languageCode.compareTo(par1Language.languageCode);
/* 48:   */   }
/* 49:   */   
/* 50:   */   public int compareTo(Object par1Obj)
/* 51:   */   {
/* 52:51 */     return compareTo((Language)par1Obj);
/* 53:   */   }
/* 54:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.resources.Language
 * JD-Core Version:    0.7.0.1
 */