/*  1:   */ package net.minecraft.client.renderer;
/*  2:   */ 
/*  3:   */ import net.minecraft.util.IIcon;
/*  4:   */ 
/*  5:   */ public class IconFlipped
/*  6:   */   implements IIcon
/*  7:   */ {
/*  8:   */   private final IIcon baseIcon;
/*  9:   */   private final boolean flipU;
/* 10:   */   private final boolean flipV;
/* 11:   */   private static final String __OBFID = "CL_00001511";
/* 12:   */   
/* 13:   */   public IconFlipped(IIcon par1Icon, boolean par2, boolean par3)
/* 14:   */   {
/* 15:14 */     this.baseIcon = par1Icon;
/* 16:15 */     this.flipU = par2;
/* 17:16 */     this.flipV = par3;
/* 18:   */   }
/* 19:   */   
/* 20:   */   public int getIconWidth()
/* 21:   */   {
/* 22:24 */     return this.baseIcon.getIconWidth();
/* 23:   */   }
/* 24:   */   
/* 25:   */   public int getIconHeight()
/* 26:   */   {
/* 27:32 */     return this.baseIcon.getIconHeight();
/* 28:   */   }
/* 29:   */   
/* 30:   */   public float getMinU()
/* 31:   */   {
/* 32:40 */     return this.flipU ? this.baseIcon.getMaxU() : this.baseIcon.getMinU();
/* 33:   */   }
/* 34:   */   
/* 35:   */   public float getMaxU()
/* 36:   */   {
/* 37:48 */     return this.flipU ? this.baseIcon.getMinU() : this.baseIcon.getMaxU();
/* 38:   */   }
/* 39:   */   
/* 40:   */   public float getInterpolatedU(double par1)
/* 41:   */   {
/* 42:56 */     float var3 = getMaxU() - getMinU();
/* 43:57 */     return getMinU() + var3 * ((float)par1 / 16.0F);
/* 44:   */   }
/* 45:   */   
/* 46:   */   public float getMinV()
/* 47:   */   {
/* 48:65 */     return this.flipV ? this.baseIcon.getMinV() : this.baseIcon.getMinV();
/* 49:   */   }
/* 50:   */   
/* 51:   */   public float getMaxV()
/* 52:   */   {
/* 53:73 */     return this.flipV ? this.baseIcon.getMinV() : this.baseIcon.getMaxV();
/* 54:   */   }
/* 55:   */   
/* 56:   */   public float getInterpolatedV(double par1)
/* 57:   */   {
/* 58:81 */     float var3 = getMaxV() - getMinV();
/* 59:82 */     return getMinV() + var3 * ((float)par1 / 16.0F);
/* 60:   */   }
/* 61:   */   
/* 62:   */   public String getIconName()
/* 63:   */   {
/* 64:87 */     return this.baseIcon.getIconName();
/* 65:   */   }
/* 66:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.renderer.IconFlipped
 * JD-Core Version:    0.7.0.1
 */