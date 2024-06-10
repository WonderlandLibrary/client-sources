/*   1:    */ package net.minecraft.world;
/*   2:    */ 
/*   3:    */ import java.lang.reflect.Constructor;
/*   4:    */ import java.util.HashMap;
/*   5:    */ import java.util.Iterator;
/*   6:    */ import java.util.List;
/*   7:    */ import java.util.Random;
/*   8:    */ import java.util.Set;
/*   9:    */ import net.minecraft.block.Block;
/*  10:    */ import net.minecraft.block.material.Material;
/*  11:    */ import net.minecraft.entity.EntityLiving;
/*  12:    */ import net.minecraft.entity.EnumCreatureType;
/*  13:    */ import net.minecraft.entity.IEntityLivingData;
/*  14:    */ import net.minecraft.entity.player.EntityPlayer;
/*  15:    */ import net.minecraft.init.Blocks;
/*  16:    */ import net.minecraft.util.ChunkCoordinates;
/*  17:    */ import net.minecraft.util.MathHelper;
/*  18:    */ import net.minecraft.util.WeightedRandom;
/*  19:    */ import net.minecraft.world.biome.BiomeGenBase;
/*  20:    */ import net.minecraft.world.biome.BiomeGenBase.SpawnListEntry;
/*  21:    */ import net.minecraft.world.chunk.Chunk;
/*  22:    */ 
/*  23:    */ public final class SpawnerAnimals
/*  24:    */ {
/*  25: 23 */   private HashMap eligibleChunksForSpawning = new HashMap();
/*  26:    */   private static final String __OBFID = "CL_00000152";
/*  27:    */   
/*  28:    */   protected static ChunkPosition func_151350_a(World p_151350_0_, int p_151350_1_, int p_151350_2_)
/*  29:    */   {
/*  30: 28 */     Chunk var3 = p_151350_0_.getChunkFromChunkCoords(p_151350_1_, p_151350_2_);
/*  31: 29 */     int var4 = p_151350_1_ * 16 + p_151350_0_.rand.nextInt(16);
/*  32: 30 */     int var5 = p_151350_2_ * 16 + p_151350_0_.rand.nextInt(16);
/*  33: 31 */     int var6 = p_151350_0_.rand.nextInt(var3 == null ? p_151350_0_.getActualHeight() : var3.getTopFilledSegment() + 16 - 1);
/*  34: 32 */     return new ChunkPosition(var4, var6, var5);
/*  35:    */   }
/*  36:    */   
/*  37:    */   public int findChunksForSpawning(WorldServer par1WorldServer, boolean par2, boolean par3, boolean par4)
/*  38:    */   {
/*  39: 41 */     if ((!par2) && (!par3)) {
/*  40: 43 */       return 0;
/*  41:    */     }
/*  42: 47 */     this.eligibleChunksForSpawning.clear();
/*  43: 51 */     for (int var5 = 0; var5 < par1WorldServer.playerEntities.size(); var5++)
/*  44:    */     {
/*  45: 53 */       EntityPlayer var6 = (EntityPlayer)par1WorldServer.playerEntities.get(var5);
/*  46: 54 */       int var7 = MathHelper.floor_double(var6.posX / 16.0D);
/*  47: 55 */       int var8 = MathHelper.floor_double(var6.posZ / 16.0D);
/*  48: 56 */       byte var9 = 8;
/*  49: 58 */       for (int var10 = -var9; var10 <= var9; var10++) {
/*  50: 60 */         for (int var11 = -var9; var11 <= var9; var11++)
/*  51:    */         {
/*  52: 62 */           boolean var12 = (var10 == -var9) || (var10 == var9) || (var11 == -var9) || (var11 == var9);
/*  53: 63 */           ChunkCoordIntPair var13 = new ChunkCoordIntPair(var10 + var7, var11 + var8);
/*  54: 65 */           if (!var12) {
/*  55: 67 */             this.eligibleChunksForSpawning.put(var13, Boolean.valueOf(false));
/*  56: 69 */           } else if (!this.eligibleChunksForSpawning.containsKey(var13)) {
/*  57: 71 */             this.eligibleChunksForSpawning.put(var13, Boolean.valueOf(true));
/*  58:    */           }
/*  59:    */         }
/*  60:    */       }
/*  61:    */     }
/*  62: 77 */     var5 = 0;
/*  63: 78 */     ChunkCoordinates var34 = par1WorldServer.getSpawnPoint();
/*  64: 79 */     EnumCreatureType[] var35 = EnumCreatureType.values();
/*  65: 80 */     int var8 = var35.length;
/*  66: 82 */     for (int var36 = 0; var36 < var8; var36++)
/*  67:    */     {
/*  68: 84 */       EnumCreatureType var37 = var35[var36];
/*  69: 86 */       if (((!var37.getPeacefulCreature()) || (par3)) && ((var37.getPeacefulCreature()) || (par2)) && ((!var37.getAnimal()) || (par4)) && (par1WorldServer.countEntities(var37.getCreatureClass()) <= var37.getMaxNumberOfCreature() * this.eligibleChunksForSpawning.size() / 256))
/*  70:    */       {
/*  71: 88 */         Iterator var39 = this.eligibleChunksForSpawning.keySet().iterator();
/*  72:    */         label833:
/*  73: 91 */         while (var39.hasNext())
/*  74:    */         {
/*  75: 93 */           ChunkCoordIntPair var38 = (ChunkCoordIntPair)var39.next();
/*  76: 95 */           if (!((Boolean)this.eligibleChunksForSpawning.get(var38)).booleanValue())
/*  77:    */           {
/*  78: 97 */             ChunkPosition var40 = func_151350_a(par1WorldServer, var38.chunkXPos, var38.chunkZPos);
/*  79: 98 */             int var14 = var40.field_151329_a;
/*  80: 99 */             int var15 = var40.field_151327_b;
/*  81:100 */             int var16 = var40.field_151328_c;
/*  82:102 */             if ((!par1WorldServer.getBlock(var14, var15, var16).isNormalCube()) && (par1WorldServer.getBlock(var14, var15, var16).getMaterial() == var37.getCreatureMaterial()))
/*  83:    */             {
/*  84:104 */               int var17 = 0;
/*  85:105 */               int var18 = 0;
/*  86:107 */               while (var18 < 3)
/*  87:    */               {
/*  88:109 */                 int var19 = var14;
/*  89:110 */                 int var20 = var15;
/*  90:111 */                 int var21 = var16;
/*  91:112 */                 byte var22 = 6;
/*  92:113 */                 BiomeGenBase.SpawnListEntry var23 = null;
/*  93:114 */                 IEntityLivingData var24 = null;
/*  94:115 */                 int var25 = 0;
/*  95:119 */                 while (var25 < 4)
/*  96:    */                 {
/*  97:123 */                   var19 += par1WorldServer.rand.nextInt(var22) - par1WorldServer.rand.nextInt(var22);
/*  98:124 */                   var20 += par1WorldServer.rand.nextInt(1) - par1WorldServer.rand.nextInt(1);
/*  99:125 */                   var21 += par1WorldServer.rand.nextInt(var22) - par1WorldServer.rand.nextInt(var22);
/* 100:127 */                   if (canCreatureTypeSpawnAtLocation(var37, par1WorldServer, var19, var20, var21))
/* 101:    */                   {
/* 102:129 */                     float var26 = var19 + 0.5F;
/* 103:130 */                     float var27 = var20;
/* 104:131 */                     float var28 = var21 + 0.5F;
/* 105:133 */                     if (par1WorldServer.getClosestPlayer(var26, var27, var28, 24.0D) == null)
/* 106:    */                     {
/* 107:135 */                       float var29 = var26 - var34.posX;
/* 108:136 */                       float var30 = var27 - var34.posY;
/* 109:137 */                       float var31 = var28 - var34.posZ;
/* 110:138 */                       float var32 = var29 * var29 + var30 * var30 + var31 * var31;
/* 111:140 */                       if (var32 >= 576.0F)
/* 112:    */                       {
/* 113:142 */                         if (var23 == null)
/* 114:    */                         {
/* 115:144 */                           var23 = par1WorldServer.spawnRandomCreature(var37, var19, var20, var21);
/* 116:146 */                           if (var23 == null) {
/* 117:    */                             break;
/* 118:    */                           }
/* 119:    */                         }
/* 120:    */                         try
/* 121:    */                         {
/* 122:156 */                           var41 = (EntityLiving)var23.entityClass.getConstructor(new Class[] { World.class }).newInstance(new Object[] { par1WorldServer });
/* 123:    */                         }
/* 124:    */                         catch (Exception var33)
/* 125:    */                         {
/* 126:    */                           EntityLiving var41;
/* 127:160 */                           var33.printStackTrace();
/* 128:161 */                           return var5;
/* 129:    */                         }
/* 130:    */                         EntityLiving var41;
/* 131:164 */                         var41.setLocationAndAngles(var26, var27, var28, par1WorldServer.rand.nextFloat() * 360.0F, 0.0F);
/* 132:166 */                         if (var41.getCanSpawnHere())
/* 133:    */                         {
/* 134:168 */                           var17++;
/* 135:169 */                           par1WorldServer.spawnEntityInWorld(var41);
/* 136:170 */                           var24 = var41.onSpawnWithEgg(var24);
/* 137:172 */                           if (var17 >= var41.getMaxSpawnedInChunk()) {
/* 138:    */                             break label833;
/* 139:    */                           }
/* 140:    */                         }
/* 141:178 */                         var5 += var17;
/* 142:    */                       }
/* 143:    */                     }
/* 144:    */                   }
/* 145:183 */                   var25++;
/* 146:    */                 }
/* 147:188 */                 var18++;
/* 148:    */               }
/* 149:    */             }
/* 150:    */           }
/* 151:    */         }
/* 152:    */       }
/* 153:    */     }
/* 154:198 */     return var5;
/* 155:    */   }
/* 156:    */   
/* 157:    */   public static boolean canCreatureTypeSpawnAtLocation(EnumCreatureType par0EnumCreatureType, World par1World, int par2, int par3, int par4)
/* 158:    */   {
/* 159:207 */     if (par0EnumCreatureType.getCreatureMaterial() == Material.water) {
/* 160:209 */       return (par1World.getBlock(par2, par3, par4).getMaterial().isLiquid()) && (par1World.getBlock(par2, par3 - 1, par4).getMaterial().isLiquid()) && (!par1World.getBlock(par2, par3 + 1, par4).isNormalCube());
/* 161:    */     }
/* 162:211 */     if (!World.doesBlockHaveSolidTopSurface(par1World, par2, par3 - 1, par4)) {
/* 163:213 */       return false;
/* 164:    */     }
/* 165:217 */     Block var5 = par1World.getBlock(par2, par3 - 1, par4);
/* 166:218 */     return (var5 != Blocks.bedrock) && (!par1World.getBlock(par2, par3, par4).isNormalCube()) && (!par1World.getBlock(par2, par3, par4).getMaterial().isLiquid()) && (!par1World.getBlock(par2, par3 + 1, par4).isNormalCube());
/* 167:    */   }
/* 168:    */   
/* 169:    */   public static void performWorldGenSpawning(World par0World, BiomeGenBase par1BiomeGenBase, int par2, int par3, int par4, int par5, Random par6Random)
/* 170:    */   {
/* 171:227 */     List var7 = par1BiomeGenBase.getSpawnableList(EnumCreatureType.creature);
/* 172:229 */     if (!var7.isEmpty())
/* 173:    */     {
/* 174:    */       int var10;
/* 175:    */       int var15;
/* 176:231 */       for (; par6Random.nextFloat() < par1BiomeGenBase.getSpawningChance(); var15 < var10)
/* 177:    */       {
/* 178:233 */         BiomeGenBase.SpawnListEntry var8 = (BiomeGenBase.SpawnListEntry)WeightedRandom.getRandomItem(par0World.rand, var7);
/* 179:234 */         IEntityLivingData var9 = null;
/* 180:235 */         var10 = var8.minGroupCount + par6Random.nextInt(1 + var8.maxGroupCount - var8.minGroupCount);
/* 181:236 */         int var11 = par2 + par6Random.nextInt(par4);
/* 182:237 */         int var12 = par3 + par6Random.nextInt(par5);
/* 183:238 */         int var13 = var11;
/* 184:239 */         int var14 = var12;
/* 185:    */         
/* 186:241 */         var15 = 0; continue;
/* 187:    */         
/* 188:243 */         boolean var16 = false;
/* 189:245 */         for (int var17 = 0; (!var16) && (var17 < 4); var17++)
/* 190:    */         {
/* 191:247 */           int var18 = par0World.getTopSolidOrLiquidBlock(var11, var12);
/* 192:249 */           if (canCreatureTypeSpawnAtLocation(EnumCreatureType.creature, par0World, var11, var18, var12))
/* 193:    */           {
/* 194:251 */             float var19 = var11 + 0.5F;
/* 195:252 */             float var20 = var18;
/* 196:253 */             float var21 = var12 + 0.5F;
/* 197:    */             try
/* 198:    */             {
/* 199:258 */               var22 = (EntityLiving)var8.entityClass.getConstructor(new Class[] { World.class }).newInstance(new Object[] { par0World });
/* 200:    */             }
/* 201:    */             catch (Exception var24)
/* 202:    */             {
/* 203:    */               EntityLiving var22;
/* 204:262 */               var24.printStackTrace();
/* 205:263 */               continue;
/* 206:    */             }
/* 207:    */             EntityLiving var22;
/* 208:266 */             var22.setLocationAndAngles(var19, var20, var21, par6Random.nextFloat() * 360.0F, 0.0F);
/* 209:267 */             par0World.spawnEntityInWorld(var22);
/* 210:268 */             var9 = var22.onSpawnWithEgg(var9);
/* 211:269 */             var16 = true;
/* 212:    */           }
/* 213:272 */           var11 += par6Random.nextInt(5) - par6Random.nextInt(5);
/* 214:274 */           for (var12 += par6Random.nextInt(5) - par6Random.nextInt(5); (var11 < par2) || (var11 >= par2 + par4) || (var12 < par3) || (var12 >= par3 + par4); var12 = var14 + par6Random.nextInt(5) - par6Random.nextInt(5)) {
/* 215:276 */             var11 = var13 + par6Random.nextInt(5) - par6Random.nextInt(5);
/* 216:    */           }
/* 217:    */         }
/* 218:241 */         var15++;
/* 219:    */       }
/* 220:    */     }
/* 221:    */   }
/* 222:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.world.SpawnerAnimals
 * JD-Core Version:    0.7.0.1
 */