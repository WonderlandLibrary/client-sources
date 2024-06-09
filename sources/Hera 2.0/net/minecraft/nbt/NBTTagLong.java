/*     */ package net.minecraft.nbt;
/*     */ 
/*     */ import java.io.DataInput;
/*     */ import java.io.DataOutput;
/*     */ import java.io.IOException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class NBTTagLong
/*     */   extends NBTBase.NBTPrimitive
/*     */ {
/*     */   private long data;
/*     */   
/*     */   NBTTagLong() {}
/*     */   
/*     */   public NBTTagLong(long data) {
/*  18 */     this.data = data;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void write(DataOutput output) throws IOException {
/*  26 */     output.writeLong(this.data);
/*     */   }
/*     */ 
/*     */   
/*     */   void read(DataInput input, int depth, NBTSizeTracker sizeTracker) throws IOException {
/*  31 */     sizeTracker.read(128L);
/*  32 */     this.data = input.readLong();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public byte getId() {
/*  40 */     return 4;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/*  45 */     return this.data + "L";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public NBTBase copy() {
/*  53 */     return new NBTTagLong(this.data);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object p_equals_1_) {
/*  58 */     if (super.equals(p_equals_1_)) {
/*     */       
/*  60 */       NBTTagLong nbttaglong = (NBTTagLong)p_equals_1_;
/*  61 */       return (this.data == nbttaglong.data);
/*     */     } 
/*     */ 
/*     */     
/*  65 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/*  71 */     return super.hashCode() ^ (int)(this.data ^ this.data >>> 32L);
/*     */   }
/*     */ 
/*     */   
/*     */   public long getLong() {
/*  76 */     return this.data;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getInt() {
/*  81 */     return (int)(this.data & 0xFFFFFFFFFFFFFFFFL);
/*     */   }
/*     */ 
/*     */   
/*     */   public short getShort() {
/*  86 */     return (short)(int)(this.data & 0xFFFFL);
/*     */   }
/*     */ 
/*     */   
/*     */   public byte getByte() {
/*  91 */     return (byte)(int)(this.data & 0xFFL);
/*     */   }
/*     */ 
/*     */   
/*     */   public double getDouble() {
/*  96 */     return this.data;
/*     */   }
/*     */ 
/*     */   
/*     */   public float getFloat() {
/* 101 */     return (float)this.data;
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\nbt\NBTTagLong.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */