/*   1:    */ package net.minecraft.nbt;
/*   2:    */ 
/*   3:    */ import java.io.DataInput;
/*   4:    */ import java.io.DataOutput;
/*   5:    */ import java.io.IOException;
/*   6:    */ 
/*   7:    */ public class NBTTagLong
/*   8:    */   extends NBTBase.NBTPrimitive
/*   9:    */ {
/*  10:    */   private long data;
/*  11:    */   private static final String __OBFID = "CL_00001225";
/*  12:    */   
/*  13:    */   NBTTagLong() {}
/*  14:    */   
/*  15:    */   public NBTTagLong(long p_i45134_1_)
/*  16:    */   {
/*  17: 17 */     this.data = p_i45134_1_;
/*  18:    */   }
/*  19:    */   
/*  20:    */   void write(DataOutput par1DataOutput)
/*  21:    */     throws IOException
/*  22:    */   {
/*  23: 25 */     par1DataOutput.writeLong(this.data);
/*  24:    */   }
/*  25:    */   
/*  26:    */   void load(DataInput par1DataInput, int par2)
/*  27:    */     throws IOException
/*  28:    */   {
/*  29: 33 */     this.data = par1DataInput.readLong();
/*  30:    */   }
/*  31:    */   
/*  32:    */   public byte getId()
/*  33:    */   {
/*  34: 41 */     return 4;
/*  35:    */   }
/*  36:    */   
/*  37:    */   public String toString()
/*  38:    */   {
/*  39: 46 */     return this.data + "L";
/*  40:    */   }
/*  41:    */   
/*  42:    */   public NBTBase copy()
/*  43:    */   {
/*  44: 54 */     return new NBTTagLong(this.data);
/*  45:    */   }
/*  46:    */   
/*  47:    */   public boolean equals(Object par1Obj)
/*  48:    */   {
/*  49: 59 */     if (super.equals(par1Obj))
/*  50:    */     {
/*  51: 61 */       NBTTagLong var2 = (NBTTagLong)par1Obj;
/*  52: 62 */       return this.data == var2.data;
/*  53:    */     }
/*  54: 66 */     return false;
/*  55:    */   }
/*  56:    */   
/*  57:    */   public int hashCode()
/*  58:    */   {
/*  59: 72 */     return super.hashCode() ^ (int)(this.data ^ this.data >>> 32);
/*  60:    */   }
/*  61:    */   
/*  62:    */   public long func_150291_c()
/*  63:    */   {
/*  64: 77 */     return this.data;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public int func_150287_d()
/*  68:    */   {
/*  69: 82 */     return (int)(this.data & 0xFFFFFFFF);
/*  70:    */   }
/*  71:    */   
/*  72:    */   public short func_150289_e()
/*  73:    */   {
/*  74: 87 */     return (short)(int)(this.data & 0xFFFF);
/*  75:    */   }
/*  76:    */   
/*  77:    */   public byte func_150290_f()
/*  78:    */   {
/*  79: 92 */     return (byte)(int)(this.data & 0xFF);
/*  80:    */   }
/*  81:    */   
/*  82:    */   public double func_150286_g()
/*  83:    */   {
/*  84: 97 */     return this.data;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public float func_150288_h()
/*  88:    */   {
/*  89:102 */     return (float)this.data;
/*  90:    */   }
/*  91:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.nbt.NBTTagLong
 * JD-Core Version:    0.7.0.1
 */