/*   1:    */ package net.minecraft.world.chunk.storage;
/*   2:    */ 
/*   3:    */ import net.minecraft.block.Block;
/*   4:    */ import net.minecraft.init.Blocks;
/*   5:    */ import net.minecraft.world.chunk.NibbleArray;
/*   6:    */ 
/*   7:    */ public class ExtendedBlockStorage
/*   8:    */ {
/*   9:    */   private int yBase;
/*  10:    */   private int blockRefCount;
/*  11:    */   private int tickRefCount;
/*  12:    */   private byte[] blockLSBArray;
/*  13:    */   private NibbleArray blockMSBArray;
/*  14:    */   private NibbleArray blockMetadataArray;
/*  15:    */   private NibbleArray blocklightArray;
/*  16:    */   private NibbleArray skylightArray;
/*  17:    */   private static final String __OBFID = "CL_00000375";
/*  18:    */   
/*  19:    */   public ExtendedBlockStorage(int par1, boolean par2)
/*  20:    */   {
/*  21: 49 */     this.yBase = par1;
/*  22: 50 */     this.blockLSBArray = new byte[4096];
/*  23: 51 */     this.blockMetadataArray = new NibbleArray(this.blockLSBArray.length, 4);
/*  24: 52 */     this.blocklightArray = new NibbleArray(this.blockLSBArray.length, 4);
/*  25: 54 */     if (par2) {
/*  26: 56 */       this.skylightArray = new NibbleArray(this.blockLSBArray.length, 4);
/*  27:    */     }
/*  28:    */   }
/*  29:    */   
/*  30:    */   public Block func_150819_a(int p_150819_1_, int p_150819_2_, int p_150819_3_)
/*  31:    */   {
/*  32: 62 */     int var4 = this.blockLSBArray[(p_150819_2_ << 8 | p_150819_3_ << 4 | p_150819_1_)] & 0xFF;
/*  33: 64 */     if (this.blockMSBArray != null) {
/*  34: 66 */       var4 |= this.blockMSBArray.get(p_150819_1_, p_150819_2_, p_150819_3_) << 8;
/*  35:    */     }
/*  36: 69 */     return Block.getBlockById(var4);
/*  37:    */   }
/*  38:    */   
/*  39:    */   public void func_150818_a(int p_150818_1_, int p_150818_2_, int p_150818_3_, Block p_150818_4_)
/*  40:    */   {
/*  41: 74 */     int var5 = this.blockLSBArray[(p_150818_2_ << 8 | p_150818_3_ << 4 | p_150818_1_)] & 0xFF;
/*  42: 76 */     if (this.blockMSBArray != null) {
/*  43: 78 */       var5 |= this.blockMSBArray.get(p_150818_1_, p_150818_2_, p_150818_3_) << 8;
/*  44:    */     }
/*  45: 81 */     Block var6 = Block.getBlockById(var5);
/*  46: 83 */     if (var6 != Blocks.air)
/*  47:    */     {
/*  48: 85 */       this.blockRefCount -= 1;
/*  49: 87 */       if (var6.getTickRandomly()) {
/*  50: 89 */         this.tickRefCount -= 1;
/*  51:    */       }
/*  52:    */     }
/*  53: 93 */     if (p_150818_4_ != Blocks.air)
/*  54:    */     {
/*  55: 95 */       this.blockRefCount += 1;
/*  56: 97 */       if (p_150818_4_.getTickRandomly()) {
/*  57: 99 */         this.tickRefCount += 1;
/*  58:    */       }
/*  59:    */     }
/*  60:103 */     int var7 = Block.getIdFromBlock(p_150818_4_);
/*  61:104 */     this.blockLSBArray[(p_150818_2_ << 8 | p_150818_3_ << 4 | p_150818_1_)] = ((byte)(var7 & 0xFF));
/*  62:106 */     if (var7 > 255)
/*  63:    */     {
/*  64:108 */       if (this.blockMSBArray == null) {
/*  65:110 */         this.blockMSBArray = new NibbleArray(this.blockLSBArray.length, 4);
/*  66:    */       }
/*  67:113 */       this.blockMSBArray.set(p_150818_1_, p_150818_2_, p_150818_3_, (var7 & 0xF00) >> 8);
/*  68:    */     }
/*  69:115 */     else if (this.blockMSBArray != null)
/*  70:    */     {
/*  71:117 */       this.blockMSBArray.set(p_150818_1_, p_150818_2_, p_150818_3_, 0);
/*  72:    */     }
/*  73:    */   }
/*  74:    */   
/*  75:    */   public int getExtBlockMetadata(int par1, int par2, int par3)
/*  76:    */   {
/*  77:126 */     return this.blockMetadataArray.get(par1, par2, par3);
/*  78:    */   }
/*  79:    */   
/*  80:    */   public void setExtBlockMetadata(int par1, int par2, int par3, int par4)
/*  81:    */   {
/*  82:134 */     this.blockMetadataArray.set(par1, par2, par3, par4);
/*  83:    */   }
/*  84:    */   
/*  85:    */   public boolean isEmpty()
/*  86:    */   {
/*  87:142 */     return this.blockRefCount == 0;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public boolean getNeedsRandomTick()
/*  91:    */   {
/*  92:151 */     return this.tickRefCount > 0;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public int getYLocation()
/*  96:    */   {
/*  97:159 */     return this.yBase;
/*  98:    */   }
/*  99:    */   
/* 100:    */   public void setExtSkylightValue(int par1, int par2, int par3, int par4)
/* 101:    */   {
/* 102:167 */     this.skylightArray.set(par1, par2, par3, par4);
/* 103:    */   }
/* 104:    */   
/* 105:    */   public int getExtSkylightValue(int par1, int par2, int par3)
/* 106:    */   {
/* 107:175 */     return this.skylightArray.get(par1, par2, par3);
/* 108:    */   }
/* 109:    */   
/* 110:    */   public void setExtBlocklightValue(int par1, int par2, int par3, int par4)
/* 111:    */   {
/* 112:183 */     this.blocklightArray.set(par1, par2, par3, par4);
/* 113:    */   }
/* 114:    */   
/* 115:    */   public int getExtBlocklightValue(int par1, int par2, int par3)
/* 116:    */   {
/* 117:191 */     return this.blocklightArray.get(par1, par2, par3);
/* 118:    */   }
/* 119:    */   
/* 120:    */   public void removeInvalidBlocks()
/* 121:    */   {
/* 122:196 */     this.blockRefCount = 0;
/* 123:197 */     this.tickRefCount = 0;
/* 124:199 */     for (int var1 = 0; var1 < 16; var1++) {
/* 125:201 */       for (int var2 = 0; var2 < 16; var2++) {
/* 126:203 */         for (int var3 = 0; var3 < 16; var3++)
/* 127:    */         {
/* 128:205 */           Block var4 = func_150819_a(var1, var2, var3);
/* 129:207 */           if (var4 != Blocks.air)
/* 130:    */           {
/* 131:209 */             this.blockRefCount += 1;
/* 132:211 */             if (var4.getTickRandomly()) {
/* 133:213 */               this.tickRefCount += 1;
/* 134:    */             }
/* 135:    */           }
/* 136:    */         }
/* 137:    */       }
/* 138:    */     }
/* 139:    */   }
/* 140:    */   
/* 141:    */   public byte[] getBlockLSBArray()
/* 142:    */   {
/* 143:223 */     return this.blockLSBArray;
/* 144:    */   }
/* 145:    */   
/* 146:    */   public void clearMSBArray()
/* 147:    */   {
/* 148:228 */     this.blockMSBArray = null;
/* 149:    */   }
/* 150:    */   
/* 151:    */   public NibbleArray getBlockMSBArray()
/* 152:    */   {
/* 153:236 */     return this.blockMSBArray;
/* 154:    */   }
/* 155:    */   
/* 156:    */   public NibbleArray getMetadataArray()
/* 157:    */   {
/* 158:241 */     return this.blockMetadataArray;
/* 159:    */   }
/* 160:    */   
/* 161:    */   public NibbleArray getBlocklightArray()
/* 162:    */   {
/* 163:249 */     return this.blocklightArray;
/* 164:    */   }
/* 165:    */   
/* 166:    */   public NibbleArray getSkylightArray()
/* 167:    */   {
/* 168:257 */     return this.skylightArray;
/* 169:    */   }
/* 170:    */   
/* 171:    */   public void setBlockLSBArray(byte[] par1ArrayOfByte)
/* 172:    */   {
/* 173:265 */     this.blockLSBArray = par1ArrayOfByte;
/* 174:    */   }
/* 175:    */   
/* 176:    */   public void setBlockMSBArray(NibbleArray par1NibbleArray)
/* 177:    */   {
/* 178:273 */     this.blockMSBArray = par1NibbleArray;
/* 179:    */   }
/* 180:    */   
/* 181:    */   public void setBlockMetadataArray(NibbleArray par1NibbleArray)
/* 182:    */   {
/* 183:281 */     this.blockMetadataArray = par1NibbleArray;
/* 184:    */   }
/* 185:    */   
/* 186:    */   public void setBlocklightArray(NibbleArray par1NibbleArray)
/* 187:    */   {
/* 188:289 */     this.blocklightArray = par1NibbleArray;
/* 189:    */   }
/* 190:    */   
/* 191:    */   public void setSkylightArray(NibbleArray par1NibbleArray)
/* 192:    */   {
/* 193:297 */     this.skylightArray = par1NibbleArray;
/* 194:    */   }
/* 195:    */   
/* 196:    */   public NibbleArray createBlockMSBArray()
/* 197:    */   {
/* 198:306 */     this.blockMSBArray = new NibbleArray(this.blockLSBArray.length, 4);
/* 199:307 */     return this.blockMSBArray;
/* 200:    */   }
/* 201:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.world.chunk.storage.ExtendedBlockStorage
 * JD-Core Version:    0.7.0.1
 */