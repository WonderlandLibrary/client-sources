/*  1:   */ package net.minecraft.block;
/*  2:   */ 
/*  3:   */ public class BlockEventData
/*  4:   */ {
/*  5:   */   private int coordX;
/*  6:   */   private int coordY;
/*  7:   */   private int coordZ;
/*  8:   */   private Block field_151344_d;
/*  9:   */   private int eventID;
/* 10:   */   private int eventParameter;
/* 11:   */   private static final String __OBFID = "CL_00000131";
/* 12:   */   
/* 13:   */   public BlockEventData(int p_i45362_1_, int p_i45362_2_, int p_i45362_3_, Block p_i45362_4_, int p_i45362_5_, int p_i45362_6_)
/* 14:   */   {
/* 15:17 */     this.coordX = p_i45362_1_;
/* 16:18 */     this.coordY = p_i45362_2_;
/* 17:19 */     this.coordZ = p_i45362_3_;
/* 18:20 */     this.eventID = p_i45362_5_;
/* 19:21 */     this.eventParameter = p_i45362_6_;
/* 20:22 */     this.field_151344_d = p_i45362_4_;
/* 21:   */   }
/* 22:   */   
/* 23:   */   public int func_151340_a()
/* 24:   */   {
/* 25:27 */     return this.coordX;
/* 26:   */   }
/* 27:   */   
/* 28:   */   public int func_151342_b()
/* 29:   */   {
/* 30:32 */     return this.coordY;
/* 31:   */   }
/* 32:   */   
/* 33:   */   public int func_151341_c()
/* 34:   */   {
/* 35:37 */     return this.coordZ;
/* 36:   */   }
/* 37:   */   
/* 38:   */   public int getEventID()
/* 39:   */   {
/* 40:45 */     return this.eventID;
/* 41:   */   }
/* 42:   */   
/* 43:   */   public int getEventParameter()
/* 44:   */   {
/* 45:50 */     return this.eventParameter;
/* 46:   */   }
/* 47:   */   
/* 48:   */   public Block getBlock()
/* 49:   */   {
/* 50:55 */     return this.field_151344_d;
/* 51:   */   }
/* 52:   */   
/* 53:   */   public boolean equals(Object par1Obj)
/* 54:   */   {
/* 55:60 */     if (!(par1Obj instanceof BlockEventData)) {
/* 56:62 */       return false;
/* 57:   */     }
/* 58:66 */     BlockEventData var2 = (BlockEventData)par1Obj;
/* 59:67 */     return (this.coordX == var2.coordX) && (this.coordY == var2.coordY) && (this.coordZ == var2.coordZ) && (this.eventID == var2.eventID) && (this.eventParameter == var2.eventParameter) && (this.field_151344_d == var2.field_151344_d);
/* 60:   */   }
/* 61:   */   
/* 62:   */   public String toString()
/* 63:   */   {
/* 64:73 */     return "TE(" + this.coordX + "," + this.coordY + "," + this.coordZ + ")," + this.eventID + "," + this.eventParameter + "," + this.field_151344_d;
/* 65:   */   }
/* 66:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.block.BlockEventData
 * JD-Core Version:    0.7.0.1
 */