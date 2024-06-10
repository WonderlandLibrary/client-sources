/*  1:   */ package net.minecraft.world.chunk;
/*  2:   */ 
/*  3:   */ public class NibbleArray
/*  4:   */ {
/*  5:   */   public final byte[] data;
/*  6:   */   private final int depthBits;
/*  7:   */   private final int depthBitsPlusFour;
/*  8:   */   private static final String __OBFID = "CL_00000371";
/*  9:   */   
/* 10:   */   public NibbleArray(int par1, int par2)
/* 11:   */   {
/* 12:24 */     this.data = new byte[par1 >> 1];
/* 13:25 */     this.depthBits = par2;
/* 14:26 */     this.depthBitsPlusFour = (par2 + 4);
/* 15:   */   }
/* 16:   */   
/* 17:   */   public NibbleArray(byte[] par1ArrayOfByte, int par2)
/* 18:   */   {
/* 19:31 */     this.data = par1ArrayOfByte;
/* 20:32 */     this.depthBits = par2;
/* 21:33 */     this.depthBitsPlusFour = (par2 + 4);
/* 22:   */   }
/* 23:   */   
/* 24:   */   public int get(int par1, int par2, int par3)
/* 25:   */   {
/* 26:41 */     int var4 = par2 << this.depthBitsPlusFour | par3 << this.depthBits | par1;
/* 27:42 */     int var5 = var4 >> 1;
/* 28:43 */     int var6 = var4 & 0x1;
/* 29:44 */     return var6 == 0 ? this.data[var5] & 0xF : this.data[var5] >> 4 & 0xF;
/* 30:   */   }
/* 31:   */   
/* 32:   */   public void set(int par1, int par2, int par3, int par4)
/* 33:   */   {
/* 34:52 */     int var5 = par2 << this.depthBitsPlusFour | par3 << this.depthBits | par1;
/* 35:53 */     int var6 = var5 >> 1;
/* 36:54 */     int var7 = var5 & 0x1;
/* 37:56 */     if (var7 == 0) {
/* 38:58 */       this.data[var6] = ((byte)(this.data[var6] & 0xF0 | par4 & 0xF));
/* 39:   */     } else {
/* 40:62 */       this.data[var6] = ((byte)(this.data[var6] & 0xF | (par4 & 0xF) << 4));
/* 41:   */     }
/* 42:   */   }
/* 43:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.world.chunk.NibbleArray
 * JD-Core Version:    0.7.0.1
 */