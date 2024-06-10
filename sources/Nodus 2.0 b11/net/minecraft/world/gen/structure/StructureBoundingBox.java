/*   1:    */ package net.minecraft.world.gen.structure;
/*   2:    */ 
/*   3:    */ import net.minecraft.nbt.NBTTagIntArray;
/*   4:    */ 
/*   5:    */ public class StructureBoundingBox
/*   6:    */ {
/*   7:    */   public int minX;
/*   8:    */   public int minY;
/*   9:    */   public int minZ;
/*  10:    */   public int maxX;
/*  11:    */   public int maxY;
/*  12:    */   public int maxZ;
/*  13:    */   private static final String __OBFID = "CL_00000442";
/*  14:    */   
/*  15:    */   public StructureBoundingBox() {}
/*  16:    */   
/*  17:    */   public StructureBoundingBox(int[] par1ArrayOfInteger)
/*  18:    */   {
/*  19: 30 */     if (par1ArrayOfInteger.length == 6)
/*  20:    */     {
/*  21: 32 */       this.minX = par1ArrayOfInteger[0];
/*  22: 33 */       this.minY = par1ArrayOfInteger[1];
/*  23: 34 */       this.minZ = par1ArrayOfInteger[2];
/*  24: 35 */       this.maxX = par1ArrayOfInteger[3];
/*  25: 36 */       this.maxY = par1ArrayOfInteger[4];
/*  26: 37 */       this.maxZ = par1ArrayOfInteger[5];
/*  27:    */     }
/*  28:    */   }
/*  29:    */   
/*  30:    */   public static StructureBoundingBox getNewBoundingBox()
/*  31:    */   {
/*  32: 46 */     return new StructureBoundingBox(2147483647, 2147483647, 2147483647, -2147483648, -2147483648, -2147483648);
/*  33:    */   }
/*  34:    */   
/*  35:    */   public static StructureBoundingBox getComponentToAddBoundingBox(int par0, int par1, int par2, int par3, int par4, int par5, int par6, int par7, int par8, int par9)
/*  36:    */   {
/*  37: 54 */     switch (par9)
/*  38:    */     {
/*  39:    */     case 0: 
/*  40: 57 */       return new StructureBoundingBox(par0 + par3, par1 + par4, par2 + par5, par0 + par6 - 1 + par3, par1 + par7 - 1 + par4, par2 + par8 - 1 + par5);
/*  41:    */     case 1: 
/*  42: 60 */       return new StructureBoundingBox(par0 - par8 + 1 + par5, par1 + par4, par2 + par3, par0 + par5, par1 + par7 - 1 + par4, par2 + par6 - 1 + par3);
/*  43:    */     case 2: 
/*  44: 63 */       return new StructureBoundingBox(par0 + par3, par1 + par4, par2 - par8 + 1 + par5, par0 + par6 - 1 + par3, par1 + par7 - 1 + par4, par2 + par5);
/*  45:    */     case 3: 
/*  46: 66 */       return new StructureBoundingBox(par0 + par5, par1 + par4, par2 + par3, par0 + par8 - 1 + par5, par1 + par7 - 1 + par4, par2 + par6 - 1 + par3);
/*  47:    */     }
/*  48: 69 */     return new StructureBoundingBox(par0 + par3, par1 + par4, par2 + par5, par0 + par6 - 1 + par3, par1 + par7 - 1 + par4, par2 + par8 - 1 + par5);
/*  49:    */   }
/*  50:    */   
/*  51:    */   public StructureBoundingBox(StructureBoundingBox par1StructureBoundingBox)
/*  52:    */   {
/*  53: 75 */     this.minX = par1StructureBoundingBox.minX;
/*  54: 76 */     this.minY = par1StructureBoundingBox.minY;
/*  55: 77 */     this.minZ = par1StructureBoundingBox.minZ;
/*  56: 78 */     this.maxX = par1StructureBoundingBox.maxX;
/*  57: 79 */     this.maxY = par1StructureBoundingBox.maxY;
/*  58: 80 */     this.maxZ = par1StructureBoundingBox.maxZ;
/*  59:    */   }
/*  60:    */   
/*  61:    */   public StructureBoundingBox(int par1, int par2, int par3, int par4, int par5, int par6)
/*  62:    */   {
/*  63: 85 */     this.minX = par1;
/*  64: 86 */     this.minY = par2;
/*  65: 87 */     this.minZ = par3;
/*  66: 88 */     this.maxX = par4;
/*  67: 89 */     this.maxY = par5;
/*  68: 90 */     this.maxZ = par6;
/*  69:    */   }
/*  70:    */   
/*  71:    */   public StructureBoundingBox(int par1, int par2, int par3, int par4)
/*  72:    */   {
/*  73: 95 */     this.minX = par1;
/*  74: 96 */     this.minZ = par2;
/*  75: 97 */     this.maxX = par3;
/*  76: 98 */     this.maxZ = par4;
/*  77: 99 */     this.minY = 1;
/*  78:100 */     this.maxY = 512;
/*  79:    */   }
/*  80:    */   
/*  81:    */   public boolean intersectsWith(StructureBoundingBox par1StructureBoundingBox)
/*  82:    */   {
/*  83:108 */     return (this.maxX >= par1StructureBoundingBox.minX) && (this.minX <= par1StructureBoundingBox.maxX) && (this.maxZ >= par1StructureBoundingBox.minZ) && (this.minZ <= par1StructureBoundingBox.maxZ) && (this.maxY >= par1StructureBoundingBox.minY) && (this.minY <= par1StructureBoundingBox.maxY);
/*  84:    */   }
/*  85:    */   
/*  86:    */   public boolean intersectsWith(int par1, int par2, int par3, int par4)
/*  87:    */   {
/*  88:116 */     return (this.maxX >= par1) && (this.minX <= par3) && (this.maxZ >= par2) && (this.minZ <= par4);
/*  89:    */   }
/*  90:    */   
/*  91:    */   public void expandTo(StructureBoundingBox par1StructureBoundingBox)
/*  92:    */   {
/*  93:124 */     this.minX = Math.min(this.minX, par1StructureBoundingBox.minX);
/*  94:125 */     this.minY = Math.min(this.minY, par1StructureBoundingBox.minY);
/*  95:126 */     this.minZ = Math.min(this.minZ, par1StructureBoundingBox.minZ);
/*  96:127 */     this.maxX = Math.max(this.maxX, par1StructureBoundingBox.maxX);
/*  97:128 */     this.maxY = Math.max(this.maxY, par1StructureBoundingBox.maxY);
/*  98:129 */     this.maxZ = Math.max(this.maxZ, par1StructureBoundingBox.maxZ);
/*  99:    */   }
/* 100:    */   
/* 101:    */   public void offset(int par1, int par2, int par3)
/* 102:    */   {
/* 103:137 */     this.minX += par1;
/* 104:138 */     this.minY += par2;
/* 105:139 */     this.minZ += par3;
/* 106:140 */     this.maxX += par1;
/* 107:141 */     this.maxY += par2;
/* 108:142 */     this.maxZ += par3;
/* 109:    */   }
/* 110:    */   
/* 111:    */   public boolean isVecInside(int par1, int par2, int par3)
/* 112:    */   {
/* 113:150 */     return (par1 >= this.minX) && (par1 <= this.maxX) && (par3 >= this.minZ) && (par3 <= this.maxZ) && (par2 >= this.minY) && (par2 <= this.maxY);
/* 114:    */   }
/* 115:    */   
/* 116:    */   public int getXSize()
/* 117:    */   {
/* 118:158 */     return this.maxX - this.minX + 1;
/* 119:    */   }
/* 120:    */   
/* 121:    */   public int getYSize()
/* 122:    */   {
/* 123:166 */     return this.maxY - this.minY + 1;
/* 124:    */   }
/* 125:    */   
/* 126:    */   public int getZSize()
/* 127:    */   {
/* 128:174 */     return this.maxZ - this.minZ + 1;
/* 129:    */   }
/* 130:    */   
/* 131:    */   public int getCenterX()
/* 132:    */   {
/* 133:179 */     return this.minX + (this.maxX - this.minX + 1) / 2;
/* 134:    */   }
/* 135:    */   
/* 136:    */   public int getCenterY()
/* 137:    */   {
/* 138:184 */     return this.minY + (this.maxY - this.minY + 1) / 2;
/* 139:    */   }
/* 140:    */   
/* 141:    */   public int getCenterZ()
/* 142:    */   {
/* 143:189 */     return this.minZ + (this.maxZ - this.minZ + 1) / 2;
/* 144:    */   }
/* 145:    */   
/* 146:    */   public String toString()
/* 147:    */   {
/* 148:194 */     return "(" + this.minX + ", " + this.minY + ", " + this.minZ + "; " + this.maxX + ", " + this.maxY + ", " + this.maxZ + ")";
/* 149:    */   }
/* 150:    */   
/* 151:    */   public NBTTagIntArray func_151535_h()
/* 152:    */   {
/* 153:199 */     return new NBTTagIntArray(new int[] { this.minX, this.minY, this.minZ, this.maxX, this.maxY, this.maxZ });
/* 154:    */   }
/* 155:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.world.gen.structure.StructureBoundingBox
 * JD-Core Version:    0.7.0.1
 */