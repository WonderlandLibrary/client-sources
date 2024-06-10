/*  1:   */ package net.minecraft.util;
/*  2:   */ 
/*  3:   */ public enum EnumFacing
/*  4:   */ {
/*  5: 5 */   DOWN(0, 1, 0, -1, 0),  UP(1, 0, 0, 1, 0),  NORTH(2, 3, 0, 0, -1),  SOUTH(3, 2, 0, 0, 1),  EAST(4, 5, -1, 0, 0),  WEST(5, 4, 1, 0, 0);
/*  6:   */   
/*  7:   */   private final int order_a;
/*  8:   */   private final int order_b;
/*  9:   */   private final int frontOffsetX;
/* 10:   */   private final int frontOffsetY;
/* 11:   */   private final int frontOffsetZ;
/* 12:   */   private static final EnumFacing[] faceList;
/* 13:   */   private static final String __OBFID = "CL_00001201";
/* 14:   */   
/* 15:   */   private EnumFacing(int par3, int par4, int par5, int par6, int par7)
/* 16:   */   {
/* 17:27 */     this.order_a = par3;
/* 18:28 */     this.order_b = par4;
/* 19:29 */     this.frontOffsetX = par5;
/* 20:30 */     this.frontOffsetY = par6;
/* 21:31 */     this.frontOffsetZ = par7;
/* 22:   */   }
/* 23:   */   
/* 24:   */   public int getFrontOffsetX()
/* 25:   */   {
/* 26:39 */     return this.frontOffsetX;
/* 27:   */   }
/* 28:   */   
/* 29:   */   public int getFrontOffsetY()
/* 30:   */   {
/* 31:44 */     return this.frontOffsetY;
/* 32:   */   }
/* 33:   */   
/* 34:   */   public int getFrontOffsetZ()
/* 35:   */   {
/* 36:52 */     return this.frontOffsetZ;
/* 37:   */   }
/* 38:   */   
/* 39:   */   public static EnumFacing getFront(int par0)
/* 40:   */   {
/* 41:60 */     return faceList[(par0 % faceList.length)];
/* 42:   */   }
/* 43:   */   
/* 44:   */   static
/* 45:   */   {
/* 46:22 */     faceList = new EnumFacing[6];
/* 47:   */     
/* 48:   */ 
/* 49:   */ 
/* 50:   */ 
/* 51:   */ 
/* 52:   */ 
/* 53:   */ 
/* 54:   */ 
/* 55:   */ 
/* 56:   */ 
/* 57:   */ 
/* 58:   */ 
/* 59:   */ 
/* 60:   */ 
/* 61:   */ 
/* 62:   */ 
/* 63:   */ 
/* 64:   */ 
/* 65:   */ 
/* 66:   */ 
/* 67:   */ 
/* 68:   */ 
/* 69:   */ 
/* 70:   */ 
/* 71:   */ 
/* 72:   */ 
/* 73:   */ 
/* 74:   */ 
/* 75:   */ 
/* 76:   */ 
/* 77:   */ 
/* 78:   */ 
/* 79:   */ 
/* 80:   */ 
/* 81:   */ 
/* 82:   */ 
/* 83:   */ 
/* 84:   */ 
/* 85:   */ 
/* 86:   */ 
/* 87:   */ 
/* 88:64 */     EnumFacing[] var0 = values();
/* 89:65 */     int var1 = var0.length;
/* 90:67 */     for (int var2 = 0; var2 < var1; var2++)
/* 91:   */     {
/* 92:69 */       EnumFacing var3 = var0[var2];
/* 93:70 */       faceList[var3.order_a] = var3;
/* 94:   */     }
/* 95:   */   }
/* 96:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.util.EnumFacing
 * JD-Core Version:    0.7.0.1
 */