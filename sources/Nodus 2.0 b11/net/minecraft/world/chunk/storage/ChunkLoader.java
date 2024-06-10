/*   1:    */ package net.minecraft.world.chunk.storage;
/*   2:    */ 
/*   3:    */ import net.minecraft.nbt.NBTTagCompound;
/*   4:    */ import net.minecraft.nbt.NBTTagList;
/*   5:    */ import net.minecraft.world.biome.BiomeGenBase;
/*   6:    */ import net.minecraft.world.biome.WorldChunkManager;
/*   7:    */ import net.minecraft.world.chunk.NibbleArray;
/*   8:    */ 
/*   9:    */ public class ChunkLoader
/*  10:    */ {
/*  11:    */   private static final String __OBFID = "CL_00000379";
/*  12:    */   
/*  13:    */   public static AnvilConverterData load(NBTTagCompound par0NBTTagCompound)
/*  14:    */   {
/*  15: 14 */     int var1 = par0NBTTagCompound.getInteger("xPos");
/*  16: 15 */     int var2 = par0NBTTagCompound.getInteger("zPos");
/*  17: 16 */     AnvilConverterData var3 = new AnvilConverterData(var1, var2);
/*  18: 17 */     var3.blocks = par0NBTTagCompound.getByteArray("Blocks");
/*  19: 18 */     var3.data = new NibbleArrayReader(par0NBTTagCompound.getByteArray("Data"), 7);
/*  20: 19 */     var3.skyLight = new NibbleArrayReader(par0NBTTagCompound.getByteArray("SkyLight"), 7);
/*  21: 20 */     var3.blockLight = new NibbleArrayReader(par0NBTTagCompound.getByteArray("BlockLight"), 7);
/*  22: 21 */     var3.heightmap = par0NBTTagCompound.getByteArray("HeightMap");
/*  23: 22 */     var3.terrainPopulated = par0NBTTagCompound.getBoolean("TerrainPopulated");
/*  24: 23 */     var3.entities = par0NBTTagCompound.getTagList("Entities", 10);
/*  25: 24 */     var3.field_151564_i = par0NBTTagCompound.getTagList("TileEntities", 10);
/*  26: 25 */     var3.field_151563_j = par0NBTTagCompound.getTagList("TileTicks", 10);
/*  27:    */     try
/*  28:    */     {
/*  29: 29 */       var3.lastUpdated = par0NBTTagCompound.getLong("LastUpdate");
/*  30:    */     }
/*  31:    */     catch (ClassCastException var5)
/*  32:    */     {
/*  33: 33 */       var3.lastUpdated = par0NBTTagCompound.getInteger("LastUpdate");
/*  34:    */     }
/*  35: 36 */     return var3;
/*  36:    */   }
/*  37:    */   
/*  38:    */   public static void convertToAnvilFormat(AnvilConverterData par0AnvilConverterData, NBTTagCompound par1NBTTagCompound, WorldChunkManager par2WorldChunkManager)
/*  39:    */   {
/*  40: 41 */     par1NBTTagCompound.setInteger("xPos", par0AnvilConverterData.x);
/*  41: 42 */     par1NBTTagCompound.setInteger("zPos", par0AnvilConverterData.z);
/*  42: 43 */     par1NBTTagCompound.setLong("LastUpdate", par0AnvilConverterData.lastUpdated);
/*  43: 44 */     int[] var3 = new int[par0AnvilConverterData.heightmap.length];
/*  44: 46 */     for (int var4 = 0; var4 < par0AnvilConverterData.heightmap.length; var4++) {
/*  45: 48 */       var3[var4] = par0AnvilConverterData.heightmap[var4];
/*  46:    */     }
/*  47: 51 */     par1NBTTagCompound.setIntArray("HeightMap", var3);
/*  48: 52 */     par1NBTTagCompound.setBoolean("TerrainPopulated", par0AnvilConverterData.terrainPopulated);
/*  49: 53 */     NBTTagList var16 = new NBTTagList();
/*  50: 56 */     for (int var5 = 0; var5 < 8; var5++)
/*  51:    */     {
/*  52: 58 */       boolean var6 = true;
/*  53: 60 */       for (int var7 = 0; (var7 < 16) && (var6); var7++)
/*  54:    */       {
/*  55: 62 */         int var8 = 0;
/*  56: 64 */         while ((var8 < 16) && (var6))
/*  57:    */         {
/*  58: 66 */           int var9 = 0;
/*  59: 70 */           while (var9 < 16)
/*  60:    */           {
/*  61: 72 */             int var10 = var7 << 11 | var9 << 7 | var8 + (var5 << 4);
/*  62: 73 */             byte var11 = par0AnvilConverterData.blocks[var10];
/*  63: 75 */             if (var11 == 0) {
/*  64: 77 */               var9++;
/*  65:    */             } else {
/*  66: 81 */               var6 = false;
/*  67:    */             }
/*  68:    */           }
/*  69: 84 */           var8++;
/*  70:    */         }
/*  71:    */       }
/*  72: 90 */       if (!var6)
/*  73:    */       {
/*  74: 92 */         byte[] var19 = new byte[4096];
/*  75: 93 */         NibbleArray var20 = new NibbleArray(var19.length, 4);
/*  76: 94 */         NibbleArray var21 = new NibbleArray(var19.length, 4);
/*  77: 95 */         NibbleArray var23 = new NibbleArray(var19.length, 4);
/*  78: 97 */         for (int var22 = 0; var22 < 16; var22++) {
/*  79: 99 */           for (int var12 = 0; var12 < 16; var12++) {
/*  80:101 */             for (int var13 = 0; var13 < 16; var13++)
/*  81:    */             {
/*  82:103 */               int var14 = var22 << 11 | var13 << 7 | var12 + (var5 << 4);
/*  83:104 */               byte var15 = par0AnvilConverterData.blocks[var14];
/*  84:105 */               var19[(var12 << 8 | var13 << 4 | var22)] = ((byte)(var15 & 0xFF));
/*  85:106 */               var20.set(var22, var12, var13, par0AnvilConverterData.data.get(var22, var12 + (var5 << 4), var13));
/*  86:107 */               var21.set(var22, var12, var13, par0AnvilConverterData.skyLight.get(var22, var12 + (var5 << 4), var13));
/*  87:108 */               var23.set(var22, var12, var13, par0AnvilConverterData.blockLight.get(var22, var12 + (var5 << 4), var13));
/*  88:    */             }
/*  89:    */           }
/*  90:    */         }
/*  91:113 */         NBTTagCompound var24 = new NBTTagCompound();
/*  92:114 */         var24.setByte("Y", (byte)(var5 & 0xFF));
/*  93:115 */         var24.setByteArray("Blocks", var19);
/*  94:116 */         var24.setByteArray("Data", var20.data);
/*  95:117 */         var24.setByteArray("SkyLight", var21.data);
/*  96:118 */         var24.setByteArray("BlockLight", var23.data);
/*  97:119 */         var16.appendTag(var24);
/*  98:    */       }
/*  99:    */     }
/* 100:123 */     par1NBTTagCompound.setTag("Sections", var16);
/* 101:124 */     byte[] var17 = new byte[256];
/* 102:126 */     for (int var18 = 0; var18 < 16; var18++) {
/* 103:128 */       for (int var7 = 0; var7 < 16; var7++) {
/* 104:130 */         var17[(var7 << 4 | var18)] = ((byte)(par2WorldChunkManager.getBiomeGenAt(par0AnvilConverterData.x << 4 | var18, par0AnvilConverterData.z << 4 | var7).biomeID & 0xFF));
/* 105:    */       }
/* 106:    */     }
/* 107:134 */     par1NBTTagCompound.setByteArray("Biomes", var17);
/* 108:135 */     par1NBTTagCompound.setTag("Entities", par0AnvilConverterData.entities);
/* 109:136 */     par1NBTTagCompound.setTag("TileEntities", par0AnvilConverterData.field_151564_i);
/* 110:138 */     if (par0AnvilConverterData.field_151563_j != null) {
/* 111:140 */       par1NBTTagCompound.setTag("TileTicks", par0AnvilConverterData.field_151563_j);
/* 112:    */     }
/* 113:    */   }
/* 114:    */   
/* 115:    */   public static class AnvilConverterData
/* 116:    */   {
/* 117:    */     public long lastUpdated;
/* 118:    */     public boolean terrainPopulated;
/* 119:    */     public byte[] heightmap;
/* 120:    */     public NibbleArrayReader blockLight;
/* 121:    */     public NibbleArrayReader skyLight;
/* 122:    */     public NibbleArrayReader data;
/* 123:    */     public byte[] blocks;
/* 124:    */     public NBTTagList entities;
/* 125:    */     public NBTTagList field_151564_i;
/* 126:    */     public NBTTagList field_151563_j;
/* 127:    */     public final int x;
/* 128:    */     public final int z;
/* 129:    */     private static final String __OBFID = "CL_00000380";
/* 130:    */     
/* 131:    */     public AnvilConverterData(int par1, int par2)
/* 132:    */     {
/* 133:162 */       this.x = par1;
/* 134:163 */       this.z = par2;
/* 135:    */     }
/* 136:    */   }
/* 137:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.world.chunk.storage.ChunkLoader
 * JD-Core Version:    0.7.0.1
 */