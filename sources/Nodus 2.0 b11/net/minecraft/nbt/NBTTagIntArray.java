/*  1:   */ package net.minecraft.nbt;
/*  2:   */ 
/*  3:   */ import java.io.DataInput;
/*  4:   */ import java.io.DataOutput;
/*  5:   */ import java.io.IOException;
/*  6:   */ import java.util.Arrays;
/*  7:   */ 
/*  8:   */ public class NBTTagIntArray
/*  9:   */   extends NBTBase
/* 10:   */ {
/* 11:   */   private int[] intArray;
/* 12:   */   private static final String __OBFID = "CL_00001221";
/* 13:   */   
/* 14:   */   NBTTagIntArray() {}
/* 15:   */   
/* 16:   */   public NBTTagIntArray(int[] p_i45132_1_)
/* 17:   */   {
/* 18:18 */     this.intArray = p_i45132_1_;
/* 19:   */   }
/* 20:   */   
/* 21:   */   void write(DataOutput par1DataOutput)
/* 22:   */     throws IOException
/* 23:   */   {
/* 24:26 */     par1DataOutput.writeInt(this.intArray.length);
/* 25:28 */     for (int var2 = 0; var2 < this.intArray.length; var2++) {
/* 26:30 */       par1DataOutput.writeInt(this.intArray[var2]);
/* 27:   */     }
/* 28:   */   }
/* 29:   */   
/* 30:   */   void load(DataInput par1DataInput, int par2)
/* 31:   */     throws IOException
/* 32:   */   {
/* 33:39 */     int var3 = par1DataInput.readInt();
/* 34:40 */     this.intArray = new int[var3];
/* 35:42 */     for (int var4 = 0; var4 < var3; var4++) {
/* 36:44 */       this.intArray[var4] = par1DataInput.readInt();
/* 37:   */     }
/* 38:   */   }
/* 39:   */   
/* 40:   */   public byte getId()
/* 41:   */   {
/* 42:53 */     return 11;
/* 43:   */   }
/* 44:   */   
/* 45:   */   public String toString()
/* 46:   */   {
/* 47:58 */     String var1 = "[";
/* 48:59 */     int[] var2 = this.intArray;
/* 49:60 */     int var3 = var2.length;
/* 50:62 */     for (int var4 = 0; var4 < var3; var4++)
/* 51:   */     {
/* 52:64 */       int var5 = var2[var4];
/* 53:65 */       var1 = var1 + var5 + ",";
/* 54:   */     }
/* 55:68 */     return var1 + "]";
/* 56:   */   }
/* 57:   */   
/* 58:   */   public NBTBase copy()
/* 59:   */   {
/* 60:76 */     int[] var1 = new int[this.intArray.length];
/* 61:77 */     System.arraycopy(this.intArray, 0, var1, 0, this.intArray.length);
/* 62:78 */     return new NBTTagIntArray(var1);
/* 63:   */   }
/* 64:   */   
/* 65:   */   public boolean equals(Object par1Obj)
/* 66:   */   {
/* 67:83 */     return super.equals(par1Obj) ? Arrays.equals(this.intArray, ((NBTTagIntArray)par1Obj).intArray) : false;
/* 68:   */   }
/* 69:   */   
/* 70:   */   public int hashCode()
/* 71:   */   {
/* 72:88 */     return super.hashCode() ^ Arrays.hashCode(this.intArray);
/* 73:   */   }
/* 74:   */   
/* 75:   */   public int[] func_150302_c()
/* 76:   */   {
/* 77:93 */     return this.intArray;
/* 78:   */   }
/* 79:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.nbt.NBTTagIntArray
 * JD-Core Version:    0.7.0.1
 */