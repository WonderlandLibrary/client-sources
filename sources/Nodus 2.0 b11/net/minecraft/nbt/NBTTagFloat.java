/*   1:    */ package net.minecraft.nbt;
/*   2:    */ 
/*   3:    */ import java.io.DataInput;
/*   4:    */ import java.io.DataOutput;
/*   5:    */ import java.io.IOException;
/*   6:    */ import net.minecraft.util.MathHelper;
/*   7:    */ 
/*   8:    */ public class NBTTagFloat
/*   9:    */   extends NBTBase.NBTPrimitive
/*  10:    */ {
/*  11:    */   private float data;
/*  12:    */   private static final String __OBFID = "CL_00001220";
/*  13:    */   
/*  14:    */   NBTTagFloat() {}
/*  15:    */   
/*  16:    */   public NBTTagFloat(float p_i45131_1_)
/*  17:    */   {
/*  18: 18 */     this.data = p_i45131_1_;
/*  19:    */   }
/*  20:    */   
/*  21:    */   void write(DataOutput par1DataOutput)
/*  22:    */     throws IOException
/*  23:    */   {
/*  24: 26 */     par1DataOutput.writeFloat(this.data);
/*  25:    */   }
/*  26:    */   
/*  27:    */   void load(DataInput par1DataInput, int par2)
/*  28:    */     throws IOException
/*  29:    */   {
/*  30: 34 */     this.data = par1DataInput.readFloat();
/*  31:    */   }
/*  32:    */   
/*  33:    */   public byte getId()
/*  34:    */   {
/*  35: 42 */     return 5;
/*  36:    */   }
/*  37:    */   
/*  38:    */   public String toString()
/*  39:    */   {
/*  40: 47 */     return this.data + "f";
/*  41:    */   }
/*  42:    */   
/*  43:    */   public NBTBase copy()
/*  44:    */   {
/*  45: 55 */     return new NBTTagFloat(this.data);
/*  46:    */   }
/*  47:    */   
/*  48:    */   public boolean equals(Object par1Obj)
/*  49:    */   {
/*  50: 60 */     if (super.equals(par1Obj))
/*  51:    */     {
/*  52: 62 */       NBTTagFloat var2 = (NBTTagFloat)par1Obj;
/*  53: 63 */       return this.data == var2.data;
/*  54:    */     }
/*  55: 67 */     return false;
/*  56:    */   }
/*  57:    */   
/*  58:    */   public int hashCode()
/*  59:    */   {
/*  60: 73 */     return super.hashCode() ^ Float.floatToIntBits(this.data);
/*  61:    */   }
/*  62:    */   
/*  63:    */   public long func_150291_c()
/*  64:    */   {
/*  65: 78 */     return this.data;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public int func_150287_d()
/*  69:    */   {
/*  70: 83 */     return MathHelper.floor_float(this.data);
/*  71:    */   }
/*  72:    */   
/*  73:    */   public short func_150289_e()
/*  74:    */   {
/*  75: 88 */     return (short)(MathHelper.floor_float(this.data) & 0xFFFF);
/*  76:    */   }
/*  77:    */   
/*  78:    */   public byte func_150290_f()
/*  79:    */   {
/*  80: 93 */     return (byte)(MathHelper.floor_float(this.data) & 0xFF);
/*  81:    */   }
/*  82:    */   
/*  83:    */   public double func_150286_g()
/*  84:    */   {
/*  85: 98 */     return this.data;
/*  86:    */   }
/*  87:    */   
/*  88:    */   public float func_150288_h()
/*  89:    */   {
/*  90:103 */     return this.data;
/*  91:    */   }
/*  92:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.nbt.NBTTagFloat
 * JD-Core Version:    0.7.0.1
 */