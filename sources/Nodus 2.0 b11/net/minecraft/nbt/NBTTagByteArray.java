/*  1:   */ package net.minecraft.nbt;
/*  2:   */ 
/*  3:   */ import java.io.DataInput;
/*  4:   */ import java.io.DataOutput;
/*  5:   */ import java.io.IOException;
/*  6:   */ import java.util.Arrays;
/*  7:   */ 
/*  8:   */ public class NBTTagByteArray
/*  9:   */   extends NBTBase
/* 10:   */ {
/* 11:   */   private byte[] byteArray;
/* 12:   */   private static final String __OBFID = "CL_00001213";
/* 13:   */   
/* 14:   */   NBTTagByteArray() {}
/* 15:   */   
/* 16:   */   public NBTTagByteArray(byte[] p_i45128_1_)
/* 17:   */   {
/* 18:18 */     this.byteArray = p_i45128_1_;
/* 19:   */   }
/* 20:   */   
/* 21:   */   void write(DataOutput par1DataOutput)
/* 22:   */     throws IOException
/* 23:   */   {
/* 24:26 */     par1DataOutput.writeInt(this.byteArray.length);
/* 25:27 */     par1DataOutput.write(this.byteArray);
/* 26:   */   }
/* 27:   */   
/* 28:   */   void load(DataInput par1DataInput, int par2)
/* 29:   */     throws IOException
/* 30:   */   {
/* 31:35 */     int var3 = par1DataInput.readInt();
/* 32:36 */     this.byteArray = new byte[var3];
/* 33:37 */     par1DataInput.readFully(this.byteArray);
/* 34:   */   }
/* 35:   */   
/* 36:   */   public byte getId()
/* 37:   */   {
/* 38:45 */     return 7;
/* 39:   */   }
/* 40:   */   
/* 41:   */   public String toString()
/* 42:   */   {
/* 43:50 */     return "[" + this.byteArray.length + " bytes]";
/* 44:   */   }
/* 45:   */   
/* 46:   */   public NBTBase copy()
/* 47:   */   {
/* 48:58 */     byte[] var1 = new byte[this.byteArray.length];
/* 49:59 */     System.arraycopy(this.byteArray, 0, var1, 0, this.byteArray.length);
/* 50:60 */     return new NBTTagByteArray(var1);
/* 51:   */   }
/* 52:   */   
/* 53:   */   public boolean equals(Object par1Obj)
/* 54:   */   {
/* 55:65 */     return super.equals(par1Obj) ? Arrays.equals(this.byteArray, ((NBTTagByteArray)par1Obj).byteArray) : false;
/* 56:   */   }
/* 57:   */   
/* 58:   */   public int hashCode()
/* 59:   */   {
/* 60:70 */     return super.hashCode() ^ Arrays.hashCode(this.byteArray);
/* 61:   */   }
/* 62:   */   
/* 63:   */   public byte[] func_150292_c()
/* 64:   */   {
/* 65:75 */     return this.byteArray;
/* 66:   */   }
/* 67:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.nbt.NBTTagByteArray
 * JD-Core Version:    0.7.0.1
 */