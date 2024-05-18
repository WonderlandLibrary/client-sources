/*    */ package net.minecraft.nbt;
/*    */ 
/*    */ import java.io.DataInput;
/*    */ import java.io.DataOutput;
/*    */ import java.io.IOException;
/*    */ import java.util.Arrays;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class NBTTagIntArray
/*    */   extends NBTBase
/*    */ {
/*    */   private int[] intArray;
/*    */   
/*    */   NBTTagIntArray() {}
/*    */   
/*    */   public NBTTagIntArray(int[] p_i45132_1_) {
/* 19 */     this.intArray = p_i45132_1_;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   void write(DataOutput output) throws IOException {
/* 27 */     output.writeInt(this.intArray.length);
/*    */     
/* 29 */     for (int i = 0; i < this.intArray.length; i++)
/*    */     {
/* 31 */       output.writeInt(this.intArray[i]);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   void read(DataInput input, int depth, NBTSizeTracker sizeTracker) throws IOException {
/* 37 */     sizeTracker.read(192L);
/* 38 */     int i = input.readInt();
/* 39 */     sizeTracker.read((32 * i));
/* 40 */     this.intArray = new int[i];
/*    */     
/* 42 */     for (int j = 0; j < i; j++)
/*    */     {
/* 44 */       this.intArray[j] = input.readInt();
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public byte getId() {
/* 53 */     return 11;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 58 */     String s = "["; byte b;
/*    */     int i, arrayOfInt[];
/* 60 */     for (i = (arrayOfInt = this.intArray).length, b = 0; b < i; ) { int j = arrayOfInt[b];
/*    */       
/* 62 */       s = String.valueOf(s) + j + ",";
/*    */       b++; }
/*    */     
/* 65 */     return String.valueOf(s) + "]";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public NBTBase copy() {
/* 73 */     int[] aint = new int[this.intArray.length];
/* 74 */     System.arraycopy(this.intArray, 0, aint, 0, this.intArray.length);
/* 75 */     return new NBTTagIntArray(aint);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object p_equals_1_) {
/* 80 */     return super.equals(p_equals_1_) ? Arrays.equals(this.intArray, ((NBTTagIntArray)p_equals_1_).intArray) : false;
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 85 */     return super.hashCode() ^ Arrays.hashCode(this.intArray);
/*    */   }
/*    */ 
/*    */   
/*    */   public int[] getIntArray() {
/* 90 */     return this.intArray;
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\nbt\NBTTagIntArray.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */