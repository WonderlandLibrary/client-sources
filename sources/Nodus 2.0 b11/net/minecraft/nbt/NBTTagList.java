/*   1:    */ package net.minecraft.nbt;
/*   2:    */ 
/*   3:    */ import java.io.DataInput;
/*   4:    */ import java.io.DataOutput;
/*   5:    */ import java.io.IOException;
/*   6:    */ import java.io.PrintStream;
/*   7:    */ import java.util.ArrayList;
/*   8:    */ import java.util.Iterator;
/*   9:    */ import java.util.List;
/*  10:    */ 
/*  11:    */ public class NBTTagList
/*  12:    */   extends NBTBase
/*  13:    */ {
/*  14: 13 */   private List tagList = new ArrayList();
/*  15: 18 */   private byte tagType = 0;
/*  16:    */   private static final String __OBFID = "CL_00001224";
/*  17:    */   
/*  18:    */   void write(DataOutput par1DataOutput)
/*  19:    */     throws IOException
/*  20:    */   {
/*  21: 26 */     if (!this.tagList.isEmpty()) {
/*  22: 28 */       this.tagType = ((NBTBase)this.tagList.get(0)).getId();
/*  23:    */     } else {
/*  24: 32 */       this.tagType = 0;
/*  25:    */     }
/*  26: 35 */     par1DataOutput.writeByte(this.tagType);
/*  27: 36 */     par1DataOutput.writeInt(this.tagList.size());
/*  28: 38 */     for (int var2 = 0; var2 < this.tagList.size(); var2++) {
/*  29: 40 */       ((NBTBase)this.tagList.get(var2)).write(par1DataOutput);
/*  30:    */     }
/*  31:    */   }
/*  32:    */   
/*  33:    */   void load(DataInput par1DataInput, int par2)
/*  34:    */     throws IOException
/*  35:    */   {
/*  36: 49 */     if (par2 > 512) {
/*  37: 51 */       throw new RuntimeException("Tried to read NBT tag with too high complexity, depth > 512");
/*  38:    */     }
/*  39: 55 */     this.tagType = par1DataInput.readByte();
/*  40: 56 */     int var3 = par1DataInput.readInt();
/*  41: 57 */     this.tagList = new ArrayList();
/*  42: 59 */     for (int var4 = 0; var4 < var3; var4++)
/*  43:    */     {
/*  44: 61 */       NBTBase var5 = NBTBase.func_150284_a(this.tagType);
/*  45: 62 */       var5.load(par1DataInput, par2 + 1);
/*  46: 63 */       this.tagList.add(var5);
/*  47:    */     }
/*  48:    */   }
/*  49:    */   
/*  50:    */   public byte getId()
/*  51:    */   {
/*  52: 73 */     return 9;
/*  53:    */   }
/*  54:    */   
/*  55:    */   public String toString()
/*  56:    */   {
/*  57: 78 */     String var1 = "[";
/*  58: 79 */     int var2 = 0;
/*  59: 81 */     for (Iterator var3 = this.tagList.iterator(); var3.hasNext(); var2++)
/*  60:    */     {
/*  61: 83 */       NBTBase var4 = (NBTBase)var3.next();
/*  62: 84 */       var1 = var1 + var2 + ':' + var4 + ',';
/*  63:    */     }
/*  64: 87 */     return var1 + "]";
/*  65:    */   }
/*  66:    */   
/*  67:    */   public void appendTag(NBTBase par1NBTBase)
/*  68:    */   {
/*  69: 96 */     if (this.tagType == 0)
/*  70:    */     {
/*  71: 98 */       this.tagType = par1NBTBase.getId();
/*  72:    */     }
/*  73:100 */     else if (this.tagType != par1NBTBase.getId())
/*  74:    */     {
/*  75:102 */       System.err.println("WARNING: Adding mismatching tag types to tag list");
/*  76:103 */       return;
/*  77:    */     }
/*  78:106 */     this.tagList.add(par1NBTBase);
/*  79:    */   }
/*  80:    */   
/*  81:    */   public void func_150304_a(int p_150304_1_, NBTBase p_150304_2_)
/*  82:    */   {
/*  83:111 */     if ((p_150304_1_ >= 0) && (p_150304_1_ < this.tagList.size()))
/*  84:    */     {
/*  85:113 */       if (this.tagType == 0)
/*  86:    */       {
/*  87:115 */         this.tagType = p_150304_2_.getId();
/*  88:    */       }
/*  89:117 */       else if (this.tagType != p_150304_2_.getId())
/*  90:    */       {
/*  91:119 */         System.err.println("WARNING: Adding mismatching tag types to tag list");
/*  92:120 */         return;
/*  93:    */       }
/*  94:123 */       this.tagList.set(p_150304_1_, p_150304_2_);
/*  95:    */     }
/*  96:    */     else
/*  97:    */     {
/*  98:127 */       System.err.println("WARNING: index out of bounds to set tag in tag list");
/*  99:    */     }
/* 100:    */   }
/* 101:    */   
/* 102:    */   public NBTBase removeTag(int par1)
/* 103:    */   {
/* 104:136 */     return (NBTBase)this.tagList.remove(par1);
/* 105:    */   }
/* 106:    */   
/* 107:    */   public NBTTagCompound getCompoundTagAt(int p_150305_1_)
/* 108:    */   {
/* 109:144 */     if ((p_150305_1_ >= 0) && (p_150305_1_ < this.tagList.size()))
/* 110:    */     {
/* 111:146 */       NBTBase var2 = (NBTBase)this.tagList.get(p_150305_1_);
/* 112:147 */       return var2.getId() == 10 ? (NBTTagCompound)var2 : new NBTTagCompound();
/* 113:    */     }
/* 114:151 */     return new NBTTagCompound();
/* 115:    */   }
/* 116:    */   
/* 117:    */   public int[] func_150306_c(int p_150306_1_)
/* 118:    */   {
/* 119:157 */     if ((p_150306_1_ >= 0) && (p_150306_1_ < this.tagList.size()))
/* 120:    */     {
/* 121:159 */       NBTBase var2 = (NBTBase)this.tagList.get(p_150306_1_);
/* 122:160 */       return var2.getId() == 11 ? ((NBTTagIntArray)var2).func_150302_c() : new int[0];
/* 123:    */     }
/* 124:164 */     return new int[0];
/* 125:    */   }
/* 126:    */   
/* 127:    */   public double func_150309_d(int p_150309_1_)
/* 128:    */   {
/* 129:170 */     if ((p_150309_1_ >= 0) && (p_150309_1_ < this.tagList.size()))
/* 130:    */     {
/* 131:172 */       NBTBase var2 = (NBTBase)this.tagList.get(p_150309_1_);
/* 132:173 */       return var2.getId() == 6 ? ((NBTTagDouble)var2).func_150286_g() : 0.0D;
/* 133:    */     }
/* 134:177 */     return 0.0D;
/* 135:    */   }
/* 136:    */   
/* 137:    */   public float func_150308_e(int p_150308_1_)
/* 138:    */   {
/* 139:183 */     if ((p_150308_1_ >= 0) && (p_150308_1_ < this.tagList.size()))
/* 140:    */     {
/* 141:185 */       NBTBase var2 = (NBTBase)this.tagList.get(p_150308_1_);
/* 142:186 */       return var2.getId() == 5 ? ((NBTTagFloat)var2).func_150288_h() : 0.0F;
/* 143:    */     }
/* 144:190 */     return 0.0F;
/* 145:    */   }
/* 146:    */   
/* 147:    */   public String getStringTagAt(int p_150307_1_)
/* 148:    */   {
/* 149:199 */     if ((p_150307_1_ >= 0) && (p_150307_1_ < this.tagList.size()))
/* 150:    */     {
/* 151:201 */       NBTBase var2 = (NBTBase)this.tagList.get(p_150307_1_);
/* 152:202 */       return var2.getId() == 8 ? var2.func_150285_a_() : var2.toString();
/* 153:    */     }
/* 154:206 */     return "";
/* 155:    */   }
/* 156:    */   
/* 157:    */   public int tagCount()
/* 158:    */   {
/* 159:215 */     return this.tagList.size();
/* 160:    */   }
/* 161:    */   
/* 162:    */   public NBTBase copy()
/* 163:    */   {
/* 164:223 */     NBTTagList var1 = new NBTTagList();
/* 165:224 */     var1.tagType = this.tagType;
/* 166:225 */     Iterator var2 = this.tagList.iterator();
/* 167:227 */     while (var2.hasNext())
/* 168:    */     {
/* 169:229 */       NBTBase var3 = (NBTBase)var2.next();
/* 170:230 */       NBTBase var4 = var3.copy();
/* 171:231 */       var1.tagList.add(var4);
/* 172:    */     }
/* 173:234 */     return var1;
/* 174:    */   }
/* 175:    */   
/* 176:    */   public boolean equals(Object par1Obj)
/* 177:    */   {
/* 178:239 */     if (super.equals(par1Obj))
/* 179:    */     {
/* 180:241 */       NBTTagList var2 = (NBTTagList)par1Obj;
/* 181:243 */       if (this.tagType == var2.tagType) {
/* 182:245 */         return this.tagList.equals(var2.tagList);
/* 183:    */       }
/* 184:    */     }
/* 185:249 */     return false;
/* 186:    */   }
/* 187:    */   
/* 188:    */   public int hashCode()
/* 189:    */   {
/* 190:254 */     return super.hashCode() ^ this.tagList.hashCode();
/* 191:    */   }
/* 192:    */   
/* 193:    */   public int func_150303_d()
/* 194:    */   {
/* 195:259 */     return this.tagType;
/* 196:    */   }
/* 197:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.nbt.NBTTagList
 * JD-Core Version:    0.7.0.1
 */