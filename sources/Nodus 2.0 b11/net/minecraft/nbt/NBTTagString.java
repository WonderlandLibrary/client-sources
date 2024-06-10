/*  1:   */ package net.minecraft.nbt;
/*  2:   */ 
/*  3:   */ import java.io.DataInput;
/*  4:   */ import java.io.DataOutput;
/*  5:   */ import java.io.IOException;
/*  6:   */ 
/*  7:   */ public class NBTTagString
/*  8:   */   extends NBTBase
/*  9:   */ {
/* 10:   */   private String data;
/* 11:   */   private static final String __OBFID = "CL_00001228";
/* 12:   */   
/* 13:   */   public NBTTagString()
/* 14:   */   {
/* 15:15 */     this.data = "";
/* 16:   */   }
/* 17:   */   
/* 18:   */   public NBTTagString(String par1Str)
/* 19:   */   {
/* 20:20 */     this.data = par1Str;
/* 21:22 */     if (par1Str == null) {
/* 22:24 */       throw new IllegalArgumentException("Empty string not allowed");
/* 23:   */     }
/* 24:   */   }
/* 25:   */   
/* 26:   */   void write(DataOutput par1DataOutput)
/* 27:   */     throws IOException
/* 28:   */   {
/* 29:33 */     par1DataOutput.writeUTF(this.data);
/* 30:   */   }
/* 31:   */   
/* 32:   */   void load(DataInput par1DataInput, int par2)
/* 33:   */     throws IOException
/* 34:   */   {
/* 35:41 */     this.data = par1DataInput.readUTF();
/* 36:   */   }
/* 37:   */   
/* 38:   */   public byte getId()
/* 39:   */   {
/* 40:49 */     return 8;
/* 41:   */   }
/* 42:   */   
/* 43:   */   public String toString()
/* 44:   */   {
/* 45:54 */     return "\"" + this.data + "\"";
/* 46:   */   }
/* 47:   */   
/* 48:   */   public NBTBase copy()
/* 49:   */   {
/* 50:62 */     return new NBTTagString(this.data);
/* 51:   */   }
/* 52:   */   
/* 53:   */   public boolean equals(Object par1Obj)
/* 54:   */   {
/* 55:67 */     if (!super.equals(par1Obj)) {
/* 56:69 */       return false;
/* 57:   */     }
/* 58:73 */     NBTTagString var2 = (NBTTagString)par1Obj;
/* 59:74 */     return ((this.data == null) && (var2.data == null)) || ((this.data != null) && (this.data.equals(var2.data)));
/* 60:   */   }
/* 61:   */   
/* 62:   */   public int hashCode()
/* 63:   */   {
/* 64:80 */     return super.hashCode() ^ this.data.hashCode();
/* 65:   */   }
/* 66:   */   
/* 67:   */   public String func_150285_a_()
/* 68:   */   {
/* 69:85 */     return this.data;
/* 70:   */   }
/* 71:   */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.nbt.NBTTagString
 * JD-Core Version:    0.7.0.1
 */