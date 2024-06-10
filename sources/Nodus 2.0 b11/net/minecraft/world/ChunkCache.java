/*   1:    */ package net.minecraft.world;
/*   2:    */ 
/*   3:    */ import net.minecraft.block.Block;
/*   4:    */ import net.minecraft.block.material.Material;
/*   5:    */ import net.minecraft.init.Blocks;
/*   6:    */ import net.minecraft.tileentity.TileEntity;
/*   7:    */ import net.minecraft.util.Vec3Pool;
/*   8:    */ import net.minecraft.world.biome.BiomeGenBase;
/*   9:    */ import net.minecraft.world.chunk.Chunk;
/*  10:    */ 
/*  11:    */ public class ChunkCache
/*  12:    */   implements IBlockAccess
/*  13:    */ {
/*  14:    */   private int chunkX;
/*  15:    */   private int chunkZ;
/*  16:    */   private Chunk[][] chunkArray;
/*  17:    */   private boolean isEmpty;
/*  18:    */   private World worldObj;
/*  19:    */   private static final String __OBFID = "CL_00000155";
/*  20:    */   
/*  21:    */   public ChunkCache(World par1World, int par2, int par3, int par4, int par5, int par6, int par7, int par8)
/*  22:    */   {
/*  23: 26 */     this.worldObj = par1World;
/*  24: 27 */     this.chunkX = (par2 - par8 >> 4);
/*  25: 28 */     this.chunkZ = (par4 - par8 >> 4);
/*  26: 29 */     int var9 = par5 + par8 >> 4;
/*  27: 30 */     int var10 = par7 + par8 >> 4;
/*  28: 31 */     this.chunkArray = new Chunk[var9 - this.chunkX + 1][var10 - this.chunkZ + 1];
/*  29: 32 */     this.isEmpty = true;
/*  30: 37 */     for (int var11 = this.chunkX; var11 <= var9; var11++) {
/*  31: 39 */       for (int var12 = this.chunkZ; var12 <= var10; var12++)
/*  32:    */       {
/*  33: 41 */         Chunk var13 = par1World.getChunkFromChunkCoords(var11, var12);
/*  34: 43 */         if (var13 != null) {
/*  35: 45 */           this.chunkArray[(var11 - this.chunkX)][(var12 - this.chunkZ)] = var13;
/*  36:    */         }
/*  37:    */       }
/*  38:    */     }
/*  39: 50 */     for (var11 = par2 >> 4; var11 <= par5 >> 4; var11++) {
/*  40: 52 */       for (int var12 = par4 >> 4; var12 <= par7 >> 4; var12++)
/*  41:    */       {
/*  42: 54 */         Chunk var13 = this.chunkArray[(var11 - this.chunkX)][(var12 - this.chunkZ)];
/*  43: 56 */         if ((var13 != null) && (!var13.getAreLevelsEmpty(par3, par6))) {
/*  44: 58 */           this.isEmpty = false;
/*  45:    */         }
/*  46:    */       }
/*  47:    */     }
/*  48:    */   }
/*  49:    */   
/*  50:    */   public boolean extendedLevelsInChunkCache()
/*  51:    */   {
/*  52: 69 */     return this.isEmpty;
/*  53:    */   }
/*  54:    */   
/*  55:    */   public Block getBlock(int p_147439_1_, int p_147439_2_, int p_147439_3_)
/*  56:    */   {
/*  57: 74 */     Block var4 = Blocks.air;
/*  58: 76 */     if ((p_147439_2_ >= 0) && (p_147439_2_ < 256))
/*  59:    */     {
/*  60: 78 */       int var5 = (p_147439_1_ >> 4) - this.chunkX;
/*  61: 79 */       int var6 = (p_147439_3_ >> 4) - this.chunkZ;
/*  62: 81 */       if ((var5 >= 0) && (var5 < this.chunkArray.length) && (var6 >= 0) && (var6 < this.chunkArray[var5].length))
/*  63:    */       {
/*  64: 83 */         Chunk var7 = this.chunkArray[var5][var6];
/*  65: 85 */         if (var7 != null) {
/*  66: 87 */           var4 = var7.func_150810_a(p_147439_1_ & 0xF, p_147439_2_, p_147439_3_ & 0xF);
/*  67:    */         }
/*  68:    */       }
/*  69:    */     }
/*  70: 92 */     return var4;
/*  71:    */   }
/*  72:    */   
/*  73:    */   public TileEntity getTileEntity(int p_147438_1_, int p_147438_2_, int p_147438_3_)
/*  74:    */   {
/*  75: 97 */     int var4 = (p_147438_1_ >> 4) - this.chunkX;
/*  76: 98 */     int var5 = (p_147438_3_ >> 4) - this.chunkZ;
/*  77: 99 */     return this.chunkArray[var4][var5].func_150806_e(p_147438_1_ & 0xF, p_147438_2_, p_147438_3_ & 0xF);
/*  78:    */   }
/*  79:    */   
/*  80:    */   public int getLightBrightnessForSkyBlocks(int par1, int par2, int par3, int par4)
/*  81:    */   {
/*  82:107 */     int var5 = getSkyBlockTypeBrightness(EnumSkyBlock.Sky, par1, par2, par3);
/*  83:108 */     int var6 = getSkyBlockTypeBrightness(EnumSkyBlock.Block, par1, par2, par3);
/*  84:110 */     if (var6 < par4) {
/*  85:112 */       var6 = par4;
/*  86:    */     }
/*  87:115 */     return var5 << 20 | var6 << 4;
/*  88:    */   }
/*  89:    */   
/*  90:    */   public int getBlockMetadata(int par1, int par2, int par3)
/*  91:    */   {
/*  92:123 */     if (par2 < 0) {
/*  93:125 */       return 0;
/*  94:    */     }
/*  95:127 */     if (par2 >= 256) {
/*  96:129 */       return 0;
/*  97:    */     }
/*  98:133 */     int var4 = (par1 >> 4) - this.chunkX;
/*  99:134 */     int var5 = (par3 >> 4) - this.chunkZ;
/* 100:135 */     return this.chunkArray[var4][var5].getBlockMetadata(par1 & 0xF, par2, par3 & 0xF);
/* 101:    */   }
/* 102:    */   
/* 103:    */   public BiomeGenBase getBiomeGenForCoords(int par1, int par2)
/* 104:    */   {
/* 105:144 */     return this.worldObj.getBiomeGenForCoords(par1, par2);
/* 106:    */   }
/* 107:    */   
/* 108:    */   public Vec3Pool getWorldVec3Pool()
/* 109:    */   {
/* 110:152 */     return this.worldObj.getWorldVec3Pool();
/* 111:    */   }
/* 112:    */   
/* 113:    */   public boolean isAirBlock(int p_147437_1_, int p_147437_2_, int p_147437_3_)
/* 114:    */   {
/* 115:160 */     return getBlock(p_147437_1_, p_147437_2_, p_147437_3_).getMaterial() == Material.air;
/* 116:    */   }
/* 117:    */   
/* 118:    */   public int getSkyBlockTypeBrightness(EnumSkyBlock par1EnumSkyBlock, int par2, int par3, int par4)
/* 119:    */   {
/* 120:169 */     if (par3 < 0) {
/* 121:171 */       par3 = 0;
/* 122:    */     }
/* 123:174 */     if (par3 >= 256) {
/* 124:176 */       par3 = 255;
/* 125:    */     }
/* 126:179 */     if ((par3 >= 0) && (par3 < 256) && (par2 >= -30000000) && (par4 >= -30000000) && (par2 < 30000000) && (par4 <= 30000000))
/* 127:    */     {
/* 128:181 */       if ((par1EnumSkyBlock == EnumSkyBlock.Sky) && (this.worldObj.provider.hasNoSky)) {
/* 129:183 */         return 0;
/* 130:    */       }
/* 131:190 */       if (getBlock(par2, par3, par4).func_149710_n())
/* 132:    */       {
/* 133:192 */         int var5 = getSpecialBlockBrightness(par1EnumSkyBlock, par2, par3 + 1, par4);
/* 134:193 */         int var6 = getSpecialBlockBrightness(par1EnumSkyBlock, par2 + 1, par3, par4);
/* 135:194 */         int var7 = getSpecialBlockBrightness(par1EnumSkyBlock, par2 - 1, par3, par4);
/* 136:195 */         int var8 = getSpecialBlockBrightness(par1EnumSkyBlock, par2, par3, par4 + 1);
/* 137:196 */         int var9 = getSpecialBlockBrightness(par1EnumSkyBlock, par2, par3, par4 - 1);
/* 138:198 */         if (var6 > var5) {
/* 139:200 */           var5 = var6;
/* 140:    */         }
/* 141:203 */         if (var7 > var5) {
/* 142:205 */           var5 = var7;
/* 143:    */         }
/* 144:208 */         if (var8 > var5) {
/* 145:210 */           var5 = var8;
/* 146:    */         }
/* 147:213 */         if (var9 > var5) {
/* 148:215 */           var5 = var9;
/* 149:    */         }
/* 150:218 */         return var5;
/* 151:    */       }
/* 152:222 */       int var5 = (par2 >> 4) - this.chunkX;
/* 153:223 */       int var6 = (par4 >> 4) - this.chunkZ;
/* 154:224 */       return this.chunkArray[var5][var6].getSavedLightValue(par1EnumSkyBlock, par2 & 0xF, par3, par4 & 0xF);
/* 155:    */     }
/* 156:230 */     return par1EnumSkyBlock.defaultLightValue;
/* 157:    */   }
/* 158:    */   
/* 159:    */   public int getSpecialBlockBrightness(EnumSkyBlock par1EnumSkyBlock, int par2, int par3, int par4)
/* 160:    */   {
/* 161:239 */     if (par3 < 0) {
/* 162:241 */       par3 = 0;
/* 163:    */     }
/* 164:244 */     if (par3 >= 256) {
/* 165:246 */       par3 = 255;
/* 166:    */     }
/* 167:249 */     if ((par3 >= 0) && (par3 < 256) && (par2 >= -30000000) && (par4 >= -30000000) && (par2 < 30000000) && (par4 <= 30000000))
/* 168:    */     {
/* 169:251 */       int var5 = (par2 >> 4) - this.chunkX;
/* 170:252 */       int var6 = (par4 >> 4) - this.chunkZ;
/* 171:253 */       return this.chunkArray[var5][var6].getSavedLightValue(par1EnumSkyBlock, par2 & 0xF, par3, par4 & 0xF);
/* 172:    */     }
/* 173:257 */     return par1EnumSkyBlock.defaultLightValue;
/* 174:    */   }
/* 175:    */   
/* 176:    */   public int getHeight()
/* 177:    */   {
/* 178:266 */     return 256;
/* 179:    */   }
/* 180:    */   
/* 181:    */   public int isBlockProvidingPowerTo(int par1, int par2, int par3, int par4)
/* 182:    */   {
/* 183:274 */     return getBlock(par1, par2, par3).isProvidingStrongPower(this, par1, par2, par3, par4);
/* 184:    */   }
/* 185:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.world.ChunkCache
 * JD-Core Version:    0.7.0.1
 */