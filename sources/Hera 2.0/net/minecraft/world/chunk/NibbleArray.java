/*    */ package net.minecraft.world.chunk;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class NibbleArray
/*    */ {
/*    */   private final byte[] data;
/*    */   
/*    */   public NibbleArray() {
/* 13 */     this.data = new byte[2048];
/*    */   }
/*    */ 
/*    */   
/*    */   public NibbleArray(byte[] storageArray) {
/* 18 */     this.data = storageArray;
/*    */     
/* 20 */     if (storageArray.length != 2048)
/*    */     {
/* 22 */       throw new IllegalArgumentException("ChunkNibbleArrays should be 2048 bytes not: " + storageArray.length);
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int get(int x, int y, int z) {
/* 31 */     return getFromIndex(getCoordinateIndex(x, y, z));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void set(int x, int y, int z, int value) {
/* 39 */     setIndex(getCoordinateIndex(x, y, z), value);
/*    */   }
/*    */ 
/*    */   
/*    */   private int getCoordinateIndex(int x, int y, int z) {
/* 44 */     return y << 8 | z << 4 | x;
/*    */   }
/*    */ 
/*    */   
/*    */   public int getFromIndex(int index) {
/* 49 */     int i = getNibbleIndex(index);
/* 50 */     return isLowerNibble(index) ? (this.data[i] & 0xF) : (this.data[i] >> 4 & 0xF);
/*    */   }
/*    */ 
/*    */   
/*    */   public void setIndex(int index, int value) {
/* 55 */     int i = getNibbleIndex(index);
/*    */     
/* 57 */     if (isLowerNibble(index)) {
/*    */       
/* 59 */       this.data[i] = (byte)(this.data[i] & 0xF0 | value & 0xF);
/*    */     }
/*    */     else {
/*    */       
/* 63 */       this.data[i] = (byte)(this.data[i] & 0xF | (value & 0xF) << 4);
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   private boolean isLowerNibble(int index) {
/* 69 */     return ((index & 0x1) == 0);
/*    */   }
/*    */ 
/*    */   
/*    */   private int getNibbleIndex(int index) {
/* 74 */     return index >> 1;
/*    */   }
/*    */ 
/*    */   
/*    */   public byte[] getData() {
/* 79 */     return this.data;
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\world\chunk\NibbleArray.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */