/*   1:    */ package net.minecraft.nbt;
/*   2:    */ 
/*   3:    */ import java.io.DataInput;
/*   4:    */ import java.io.DataOutput;
/*   5:    */ import java.io.IOException;
/*   6:    */ import net.minecraft.util.MathHelper;
/*   7:    */ 
/*   8:    */ public class NBTTagDouble
/*   9:    */   extends NBTBase.NBTPrimitive
/*  10:    */ {
/*  11:    */   private double data;
/*  12:    */   private static final String __OBFID = "CL_00001218";
/*  13:    */   
/*  14:    */   NBTTagDouble() {}
/*  15:    */   
/*  16:    */   public NBTTagDouble(double p_i45130_1_)
/*  17:    */   {
/*  18: 18 */     this.data = p_i45130_1_;
/*  19:    */   }
/*  20:    */   
/*  21:    */   void write(DataOutput par1DataOutput)
/*  22:    */     throws IOException
/*  23:    */   {
/*  24: 26 */     par1DataOutput.writeDouble(this.data);
/*  25:    */   }
/*  26:    */   
/*  27:    */   void load(DataInput par1DataInput, int par2)
/*  28:    */     throws IOException
/*  29:    */   {
/*  30: 34 */     this.data = par1DataInput.readDouble();
/*  31:    */   }
/*  32:    */   
/*  33:    */   public byte getId()
/*  34:    */   {
/*  35: 42 */     return 6;
/*  36:    */   }
/*  37:    */   
/*  38:    */   public String toString()
/*  39:    */   {
/*  40: 47 */     return this.data + "d";
/*  41:    */   }
/*  42:    */   
/*  43:    */   public NBTBase copy()
/*  44:    */   {
/*  45: 55 */     return new NBTTagDouble(this.data);
/*  46:    */   }
/*  47:    */   
/*  48:    */   public boolean equals(Object par1Obj)
/*  49:    */   {
/*  50: 60 */     if (super.equals(par1Obj))
/*  51:    */     {
/*  52: 62 */       NBTTagDouble var2 = (NBTTagDouble)par1Obj;
/*  53: 63 */       return this.data == var2.data;
/*  54:    */     }
/*  55: 67 */     return false;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public int hashCode()
/*  59:    */   {
/*  60: 73 */     long var1 = Double.doubleToLongBits(this.data);
/*  61: 74 */     return super.hashCode() ^ (int)(var1 ^ var1 >>> 32);
/*  62:    */   }
/*  63:    */   
/*  64:    */   public long func_150291_c()
/*  65:    */   {
/*  66: 79 */     return Math.floor(this.data);
/*  67:    */   }
/*  68:    */   
/*  69:    */   public int func_150287_d()
/*  70:    */   {
/*  71: 84 */     return MathHelper.floor_double(this.data);
/*  72:    */   }
/*  73:    */   
/*  74:    */   public short func_150289_e()
/*  75:    */   {
/*  76: 89 */     return (short)(MathHelper.floor_double(this.data) & 0xFFFF);
/*  77:    */   }
/*  78:    */   
/*  79:    */   public byte func_150290_f()
/*  80:    */   {
/*  81: 94 */     return (byte)(MathHelper.floor_double(this.data) & 0xFF);
/*  82:    */   }
/*  83:    */   
/*  84:    */   public double func_150286_g()
/*  85:    */   {
/*  86: 99 */     return this.data;
/*  87:    */   }
/*  88:    */   
/*  89:    */   public float func_150288_h()
/*  90:    */   {
/*  91:104 */     return (float)this.data;
/*  92:    */   }
/*  93:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.nbt.NBTTagDouble
 * JD-Core Version:    0.7.0.1
 */