/*  1:   */ package net.minecraft.world.chunk.storage;
/*  2:   */ 
/*  3:   */ public class NibbleArrayReader
/*  4:   */ {
/*  5:   */   public final byte[] data;
/*  6:   */   private final int depthBits;
/*  7:   */   private final int depthBitsPlusFour;
/*  8:   */   private static final String __OBFID = "CL_00000376";
/*  9:   */   
/* 10:   */   public NibbleArrayReader(byte[] par1ArrayOfByte, int par2)
/* 11:   */   {
/* 12:12 */     this.data = par1ArrayOfByte;
/* 13:13 */     this.depthBits = par2;
/* 14:14 */     this.depthBitsPlusFour = (par2 + 4);
/* 15:   */   }
/* 16:   */   
/* 17:   */   public int get(int par1, int par2, int par3)
/* 18:   */   {
/* 19:19 */     int var4 = par1 << this.depthBitsPlusFour | par3 << this.depthBits | par2;
/* 20:20 */     int var5 = var4 >> 1;
/* 21:21 */     int var6 = var4 & 0x1;
/* 22:22 */     return var6 == 0 ? this.data[var5] & 0xF : this.data[var5] >> 4 & 0xF;
/* 23:   */   }
/* 24:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.world.chunk.storage.NibbleArrayReader
 * JD-Core Version:    0.7.0.1
 */