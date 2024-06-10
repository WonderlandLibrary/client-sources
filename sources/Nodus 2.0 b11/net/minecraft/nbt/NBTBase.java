/*   1:    */ package net.minecraft.nbt;
/*   2:    */ 
/*   3:    */ import java.io.DataInput;
/*   4:    */ import java.io.DataOutput;
/*   5:    */ import java.io.IOException;
/*   6:    */ 
/*   7:    */ public abstract class NBTBase
/*   8:    */ {
/*   9:  9 */   public static final String[] NBTTypes = { "END", "BYTE", "SHORT", "INT", "LONG", "FLOAT", "DOUBLE", "BYTE[]", "STRING", "LIST", "COMPOUND", "INT[]" };
/*  10:    */   private static final String __OBFID = "CL_00001229";
/*  11:    */   
/*  12:    */   abstract void write(DataOutput paramDataOutput)
/*  13:    */     throws IOException;
/*  14:    */   
/*  15:    */   abstract void load(DataInput paramDataInput, int paramInt)
/*  16:    */     throws IOException;
/*  17:    */   
/*  18:    */   public abstract String toString();
/*  19:    */   
/*  20:    */   public abstract byte getId();
/*  21:    */   
/*  22:    */   protected static NBTBase func_150284_a(byte p_150284_0_)
/*  23:    */   {
/*  24: 31 */     switch (p_150284_0_)
/*  25:    */     {
/*  26:    */     case 0: 
/*  27: 34 */       return new NBTTagEnd();
/*  28:    */     case 1: 
/*  29: 37 */       return new NBTTagByte();
/*  30:    */     case 2: 
/*  31: 40 */       return new NBTTagShort();
/*  32:    */     case 3: 
/*  33: 43 */       return new NBTTagInt();
/*  34:    */     case 4: 
/*  35: 46 */       return new NBTTagLong();
/*  36:    */     case 5: 
/*  37: 49 */       return new NBTTagFloat();
/*  38:    */     case 6: 
/*  39: 52 */       return new NBTTagDouble();
/*  40:    */     case 7: 
/*  41: 55 */       return new NBTTagByteArray();
/*  42:    */     case 8: 
/*  43: 58 */       return new NBTTagString();
/*  44:    */     case 9: 
/*  45: 61 */       return new NBTTagList();
/*  46:    */     case 10: 
/*  47: 64 */       return new NBTTagCompound();
/*  48:    */     case 11: 
/*  49: 67 */       return new NBTTagIntArray();
/*  50:    */     }
/*  51: 70 */     return null;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public static String func_150283_g(int p_150283_0_)
/*  55:    */   {
/*  56: 76 */     switch (p_150283_0_)
/*  57:    */     {
/*  58:    */     case 0: 
/*  59: 79 */       return "TAG_End";
/*  60:    */     case 1: 
/*  61: 82 */       return "TAG_Byte";
/*  62:    */     case 2: 
/*  63: 85 */       return "TAG_Short";
/*  64:    */     case 3: 
/*  65: 88 */       return "TAG_Int";
/*  66:    */     case 4: 
/*  67: 91 */       return "TAG_Long";
/*  68:    */     case 5: 
/*  69: 94 */       return "TAG_Float";
/*  70:    */     case 6: 
/*  71: 97 */       return "TAG_Double";
/*  72:    */     case 7: 
/*  73:100 */       return "TAG_Byte_Array";
/*  74:    */     case 8: 
/*  75:103 */       return "TAG_String";
/*  76:    */     case 9: 
/*  77:106 */       return "TAG_List";
/*  78:    */     case 10: 
/*  79:109 */       return "TAG_Compound";
/*  80:    */     case 11: 
/*  81:112 */       return "TAG_Int_Array";
/*  82:    */     case 99: 
/*  83:115 */       return "Any Numeric Tag";
/*  84:    */     }
/*  85:118 */     return "UNKNOWN";
/*  86:    */   }
/*  87:    */   
/*  88:    */   public abstract NBTBase copy();
/*  89:    */   
/*  90:    */   public boolean equals(Object par1Obj)
/*  91:    */   {
/*  92:129 */     if (!(par1Obj instanceof NBTBase)) {
/*  93:131 */       return false;
/*  94:    */     }
/*  95:135 */     NBTBase var2 = (NBTBase)par1Obj;
/*  96:136 */     return getId() == var2.getId();
/*  97:    */   }
/*  98:    */   
/*  99:    */   public int hashCode()
/* 100:    */   {
/* 101:142 */     return getId();
/* 102:    */   }
/* 103:    */   
/* 104:    */   protected String func_150285_a_()
/* 105:    */   {
/* 106:147 */     return toString();
/* 107:    */   }
/* 108:    */   
/* 109:    */   public static abstract class NBTPrimitive
/* 110:    */     extends NBTBase
/* 111:    */   {
/* 112:    */     private static final String __OBFID = "CL_00001230";
/* 113:    */     
/* 114:    */     public abstract long func_150291_c();
/* 115:    */     
/* 116:    */     public abstract int func_150287_d();
/* 117:    */     
/* 118:    */     public abstract short func_150289_e();
/* 119:    */     
/* 120:    */     public abstract byte func_150290_f();
/* 121:    */     
/* 122:    */     public abstract double func_150286_g();
/* 123:    */     
/* 124:    */     public abstract float func_150288_h();
/* 125:    */   }
/* 126:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.nbt.NBTBase
 * JD-Core Version:    0.7.0.1
 */