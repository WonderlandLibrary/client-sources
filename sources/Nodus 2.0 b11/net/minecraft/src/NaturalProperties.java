/*  1:   */ package net.minecraft.src;
/*  2:   */ 
/*  3:   */ public class NaturalProperties
/*  4:   */ {
/*  5: 5 */   public int rotation = 1;
/*  6: 6 */   public boolean flip = false;
/*  7:   */   
/*  8:   */   public NaturalProperties(String type)
/*  9:   */   {
/* 10:10 */     if (type.equals("4"))
/* 11:   */     {
/* 12:12 */       this.rotation = 4;
/* 13:   */     }
/* 14:14 */     else if (type.equals("2"))
/* 15:   */     {
/* 16:16 */       this.rotation = 2;
/* 17:   */     }
/* 18:18 */     else if (type.equals("F"))
/* 19:   */     {
/* 20:20 */       this.flip = true;
/* 21:   */     }
/* 22:22 */     else if (type.equals("4F"))
/* 23:   */     {
/* 24:24 */       this.rotation = 4;
/* 25:25 */       this.flip = true;
/* 26:   */     }
/* 27:27 */     else if (type.equals("2F"))
/* 28:   */     {
/* 29:29 */       this.rotation = 2;
/* 30:30 */       this.flip = true;
/* 31:   */     }
/* 32:   */     else
/* 33:   */     {
/* 34:34 */       Config.warn("NaturalTextures: Unknown type: " + type);
/* 35:   */     }
/* 36:   */   }
/* 37:   */   
/* 38:   */   public boolean isValid()
/* 39:   */   {
/* 40:40 */     return (this.rotation != 2) && (this.rotation != 4) ? this.flip : true;
/* 41:   */   }
/* 42:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.src.NaturalProperties
 * JD-Core Version:    0.7.0.1
 */