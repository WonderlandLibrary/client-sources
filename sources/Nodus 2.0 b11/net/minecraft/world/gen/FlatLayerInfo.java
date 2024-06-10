/*  1:   */ package net.minecraft.world.gen;
/*  2:   */ 
/*  3:   */ import net.minecraft.block.Block;
/*  4:   */ 
/*  5:   */ public class FlatLayerInfo
/*  6:   */ {
/*  7:   */   private Block field_151537_a;
/*  8:   */   private int layerCount;
/*  9:   */   private int layerFillBlockMeta;
/* 10:   */   private int layerMinimumY;
/* 11:   */   private static final String __OBFID = "CL_00000441";
/* 12:   */   
/* 13:   */   public FlatLayerInfo(int p_i45467_1_, Block p_i45467_2_)
/* 14:   */   {
/* 15:19 */     this.layerCount = 1;
/* 16:20 */     this.layerCount = p_i45467_1_;
/* 17:21 */     this.field_151537_a = p_i45467_2_;
/* 18:   */   }
/* 19:   */   
/* 20:   */   public FlatLayerInfo(int p_i45468_1_, Block p_i45468_2_, int p_i45468_3_)
/* 21:   */   {
/* 22:26 */     this(p_i45468_1_, p_i45468_2_);
/* 23:27 */     this.layerFillBlockMeta = p_i45468_3_;
/* 24:   */   }
/* 25:   */   
/* 26:   */   public int getLayerCount()
/* 27:   */   {
/* 28:35 */     return this.layerCount;
/* 29:   */   }
/* 30:   */   
/* 31:   */   public Block func_151536_b()
/* 32:   */   {
/* 33:40 */     return this.field_151537_a;
/* 34:   */   }
/* 35:   */   
/* 36:   */   public int getFillBlockMeta()
/* 37:   */   {
/* 38:48 */     return this.layerFillBlockMeta;
/* 39:   */   }
/* 40:   */   
/* 41:   */   public int getMinY()
/* 42:   */   {
/* 43:56 */     return this.layerMinimumY;
/* 44:   */   }
/* 45:   */   
/* 46:   */   public void setMinY(int par1)
/* 47:   */   {
/* 48:64 */     this.layerMinimumY = par1;
/* 49:   */   }
/* 50:   */   
/* 51:   */   public String toString()
/* 52:   */   {
/* 53:69 */     String var1 = Integer.toString(Block.getIdFromBlock(this.field_151537_a));
/* 54:71 */     if (this.layerCount > 1) {
/* 55:73 */       var1 = this.layerCount + "x" + var1;
/* 56:   */     }
/* 57:76 */     if (this.layerFillBlockMeta > 0) {
/* 58:78 */       var1 = var1 + ":" + this.layerFillBlockMeta;
/* 59:   */     }
/* 60:81 */     return var1;
/* 61:   */   }
/* 62:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.world.gen.FlatLayerInfo
 * JD-Core Version:    0.7.0.1
 */