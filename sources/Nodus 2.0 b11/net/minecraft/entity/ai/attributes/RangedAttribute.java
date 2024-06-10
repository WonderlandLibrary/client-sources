/*  1:   */ package net.minecraft.entity.ai.attributes;
/*  2:   */ 
/*  3:   */ public class RangedAttribute
/*  4:   */   extends BaseAttribute
/*  5:   */ {
/*  6:   */   private final double minimumValue;
/*  7:   */   private final double maximumValue;
/*  8:   */   private String description;
/*  9:   */   private static final String __OBFID = "CL_00001568";
/* 10:   */   
/* 11:   */   public RangedAttribute(String par1Str, double par2, double par4, double par6)
/* 12:   */   {
/* 13:12 */     super(par1Str, par2);
/* 14:13 */     this.minimumValue = par4;
/* 15:14 */     this.maximumValue = par6;
/* 16:16 */     if (par4 > par6) {
/* 17:18 */       throw new IllegalArgumentException("Minimum value cannot be bigger than maximum value!");
/* 18:   */     }
/* 19:20 */     if (par2 < par4) {
/* 20:22 */       throw new IllegalArgumentException("Default value cannot be lower than minimum value!");
/* 21:   */     }
/* 22:24 */     if (par2 > par6) {
/* 23:26 */       throw new IllegalArgumentException("Default value cannot be bigger than maximum value!");
/* 24:   */     }
/* 25:   */   }
/* 26:   */   
/* 27:   */   public RangedAttribute setDescription(String par1Str)
/* 28:   */   {
/* 29:32 */     this.description = par1Str;
/* 30:33 */     return this;
/* 31:   */   }
/* 32:   */   
/* 33:   */   public String getDescription()
/* 34:   */   {
/* 35:38 */     return this.description;
/* 36:   */   }
/* 37:   */   
/* 38:   */   public double clampValue(double par1)
/* 39:   */   {
/* 40:43 */     if (par1 < this.minimumValue) {
/* 41:45 */       par1 = this.minimumValue;
/* 42:   */     }
/* 43:48 */     if (par1 > this.maximumValue) {
/* 44:50 */       par1 = this.maximumValue;
/* 45:   */     }
/* 46:53 */     return par1;
/* 47:   */   }
/* 48:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.entity.ai.attributes.RangedAttribute
 * JD-Core Version:    0.7.0.1
 */