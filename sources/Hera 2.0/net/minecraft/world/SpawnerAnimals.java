/*     */ package net.minecraft.world;
/*     */ 
/*     */ import com.google.common.collect.Sets;
/*     */ import java.util.List;
/*     */ import java.util.Random;
/*     */ import java.util.Set;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.EntityLiving;
/*     */ import net.minecraft.entity.EntitySpawnPlacementRegistry;
/*     */ import net.minecraft.entity.EnumCreatureType;
/*     */ import net.minecraft.entity.IEntityLivingData;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.init.Blocks;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.WeightedRandom;
/*     */ import net.minecraft.world.biome.BiomeGenBase;
/*     */ import net.minecraft.world.chunk.Chunk;
/*     */ 
/*     */ public final class SpawnerAnimals {
/*  22 */   private static final int MOB_COUNT_DIV = (int)Math.pow(17.0D, 2.0D);
/*  23 */   private final Set<ChunkCoordIntPair> eligibleChunksForSpawning = Sets.newHashSet();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int findChunksForSpawning(WorldServer p_77192_1_, boolean spawnHostileMobs, boolean spawnPeacefulMobs, boolean p_77192_4_) {
/*  31 */     if (!spawnHostileMobs && !spawnPeacefulMobs)
/*     */     {
/*  33 */       return 0;
/*     */     }
/*     */ 
/*     */     
/*  37 */     this.eligibleChunksForSpawning.clear();
/*  38 */     int i = 0;
/*     */     
/*  40 */     for (EntityPlayer entityplayer : p_77192_1_.playerEntities) {
/*     */       
/*  42 */       if (!entityplayer.isSpectator()) {
/*     */         
/*  44 */         int m = MathHelper.floor_double(entityplayer.posX / 16.0D);
/*  45 */         int k = MathHelper.floor_double(entityplayer.posZ / 16.0D);
/*  46 */         int l = 8;
/*     */         
/*  48 */         for (int i1 = -l; i1 <= l; i1++) {
/*     */           
/*  50 */           for (int j1 = -l; j1 <= l; j1++) {
/*     */             
/*  52 */             boolean flag = !(i1 != -l && i1 != l && j1 != -l && j1 != l);
/*  53 */             ChunkCoordIntPair chunkcoordintpair = new ChunkCoordIntPair(i1 + m, j1 + k);
/*     */             
/*  55 */             if (!this.eligibleChunksForSpawning.contains(chunkcoordintpair)) {
/*     */               
/*  57 */               i++;
/*     */               
/*  59 */               if (!flag && p_77192_1_.getWorldBorder().contains(chunkcoordintpair))
/*     */               {
/*  61 */                 this.eligibleChunksForSpawning.add(chunkcoordintpair);
/*     */               }
/*     */             } 
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     } 
/*     */     
/*  69 */     int i4 = 0;
/*  70 */     BlockPos blockpos2 = p_77192_1_.getSpawnPoint(); byte b; int j;
/*     */     EnumCreatureType[] arrayOfEnumCreatureType;
/*  72 */     for (j = (arrayOfEnumCreatureType = EnumCreatureType.values()).length, b = 0; b < j; ) { EnumCreatureType enumcreaturetype = arrayOfEnumCreatureType[b];
/*     */       
/*  74 */       if ((!enumcreaturetype.getPeacefulCreature() || spawnPeacefulMobs) && (enumcreaturetype.getPeacefulCreature() || spawnHostileMobs) && (!enumcreaturetype.getAnimal() || p_77192_4_)) {
/*     */         
/*  76 */         int j4 = p_77192_1_.countEntities(enumcreaturetype.getCreatureClass());
/*  77 */         int k4 = enumcreaturetype.getMaxNumberOfCreature() * i / MOB_COUNT_DIV;
/*     */         
/*  79 */         if (j4 <= k4)
/*     */         {
/*     */ 
/*     */           
/*  83 */           for (ChunkCoordIntPair chunkcoordintpair1 : this.eligibleChunksForSpawning) {
/*     */             
/*  85 */             BlockPos blockpos = getRandomChunkPosition(p_77192_1_, chunkcoordintpair1.chunkXPos, chunkcoordintpair1.chunkZPos);
/*  86 */             int k1 = blockpos.getX();
/*  87 */             int l1 = blockpos.getY();
/*  88 */             int i2 = blockpos.getZ();
/*  89 */             Block block = p_77192_1_.getBlockState(blockpos).getBlock();
/*     */             
/*  91 */             if (!block.isNormalCube()) {
/*     */               
/*  93 */               int j2 = 0;
/*     */               int k2;
/*  95 */               label83: for (k2 = 0; k2 < 3; k2++) {
/*     */                 
/*  97 */                 int l2 = k1;
/*  98 */                 int i3 = l1;
/*  99 */                 int j3 = i2;
/* 100 */                 int k3 = 6;
/* 101 */                 BiomeGenBase.SpawnListEntry biomegenbase$spawnlistentry = null;
/* 102 */                 IEntityLivingData ientitylivingdata = null;
/*     */                 
/* 104 */                 for (int l3 = 0; l3 < 4; l3++) {
/*     */                   
/* 106 */                   l2 += p_77192_1_.rand.nextInt(k3) - p_77192_1_.rand.nextInt(k3);
/* 107 */                   i3 += p_77192_1_.rand.nextInt(1) - p_77192_1_.rand.nextInt(1);
/* 108 */                   j3 += p_77192_1_.rand.nextInt(k3) - p_77192_1_.rand.nextInt(k3);
/* 109 */                   BlockPos blockpos1 = new BlockPos(l2, i3, j3);
/* 110 */                   float f = l2 + 0.5F;
/* 111 */                   float f1 = j3 + 0.5F;
/*     */                   
/* 113 */                   if (!p_77192_1_.isAnyPlayerWithinRangeAt(f, i3, f1, 24.0D) && blockpos2.distanceSq(f, i3, f1) >= 576.0D) {
/*     */                     
/* 115 */                     if (biomegenbase$spawnlistentry == null) {
/*     */                       
/* 117 */                       biomegenbase$spawnlistentry = p_77192_1_.getSpawnListEntryForTypeAt(enumcreaturetype, blockpos1);
/*     */                       
/* 119 */                       if (biomegenbase$spawnlistentry == null) {
/*     */                         break;
/*     */                       }
/*     */                     } 
/*     */ 
/*     */                     
/* 125 */                     if (p_77192_1_.canCreatureTypeSpawnHere(enumcreaturetype, biomegenbase$spawnlistentry, blockpos1) && canCreatureTypeSpawnAtLocation(EntitySpawnPlacementRegistry.getPlacementForEntity(biomegenbase$spawnlistentry.entityClass), p_77192_1_, blockpos1)) {
/*     */                       EntityLiving entityliving;
/*     */ 
/*     */ 
/*     */                       
/*     */                       try {
/* 131 */                         entityliving = biomegenbase$spawnlistentry.entityClass.getConstructor(new Class[] { World.class }).newInstance(new Object[] { p_77192_1_ });
/*     */                       }
/* 133 */                       catch (Exception exception) {
/*     */                         
/* 135 */                         exception.printStackTrace();
/* 136 */                         return i4;
/*     */                       } 
/*     */                       
/* 139 */                       entityliving.setLocationAndAngles(f, i3, f1, p_77192_1_.rand.nextFloat() * 360.0F, 0.0F);
/*     */                       
/* 141 */                       if (entityliving.getCanSpawnHere() && entityliving.isNotColliding()) {
/*     */                         
/* 143 */                         ientitylivingdata = entityliving.onInitialSpawn(p_77192_1_.getDifficultyForLocation(new BlockPos((Entity)entityliving)), ientitylivingdata);
/*     */                         
/* 145 */                         if (entityliving.isNotColliding()) {
/*     */                           
/* 147 */                           j2++;
/* 148 */                           p_77192_1_.spawnEntityInWorld((Entity)entityliving);
/*     */                         } 
/*     */                         
/* 151 */                         if (j2 >= entityliving.getMaxSpawnedInChunk()) {
/*     */                           break label83;
/*     */                         }
/*     */                       } 
/*     */ 
/*     */                       
/* 157 */                       i4 += j2;
/*     */                     } 
/*     */                   } 
/*     */                 } 
/*     */               } 
/*     */             } 
/*     */           } 
/*     */         }
/*     */       } 
/*     */       b++; }
/*     */     
/* 168 */     return i4;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected static BlockPos getRandomChunkPosition(World worldIn, int x, int z) {
/* 174 */     Chunk chunk = worldIn.getChunkFromChunkCoords(x, z);
/* 175 */     int i = x * 16 + worldIn.rand.nextInt(16);
/* 176 */     int j = z * 16 + worldIn.rand.nextInt(16);
/* 177 */     int k = MathHelper.func_154354_b(chunk.getHeight(new BlockPos(i, 0, j)) + 1, 16);
/* 178 */     int l = worldIn.rand.nextInt((k > 0) ? k : (chunk.getTopFilledSegment() + 16 - 1));
/* 179 */     return new BlockPos(i, l, j);
/*     */   }
/*     */ 
/*     */   
/*     */   public static boolean canCreatureTypeSpawnAtLocation(EntityLiving.SpawnPlacementType p_180267_0_, World worldIn, BlockPos pos) {
/* 184 */     if (!worldIn.getWorldBorder().contains(pos))
/*     */     {
/* 186 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 190 */     Block block = worldIn.getBlockState(pos).getBlock();
/*     */     
/* 192 */     if (p_180267_0_ == EntityLiving.SpawnPlacementType.IN_WATER)
/*     */     {
/* 194 */       return (block.getMaterial().isLiquid() && worldIn.getBlockState(pos.down()).getBlock().getMaterial().isLiquid() && !worldIn.getBlockState(pos.up()).getBlock().isNormalCube());
/*     */     }
/*     */ 
/*     */     
/* 198 */     BlockPos blockpos = pos.down();
/*     */     
/* 200 */     if (!World.doesBlockHaveSolidTopSurface(worldIn, blockpos))
/*     */     {
/* 202 */       return false;
/*     */     }
/*     */ 
/*     */     
/* 206 */     Block block1 = worldIn.getBlockState(blockpos).getBlock();
/* 207 */     boolean flag = (block1 != Blocks.bedrock && block1 != Blocks.barrier);
/* 208 */     return (flag && !block.isNormalCube() && !block.getMaterial().isLiquid() && !worldIn.getBlockState(pos.up()).getBlock().isNormalCube());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void performWorldGenSpawning(World worldIn, BiomeGenBase p_77191_1_, int p_77191_2_, int p_77191_3_, int p_77191_4_, int p_77191_5_, Random p_77191_6_) {
/* 219 */     List<BiomeGenBase.SpawnListEntry> list = p_77191_1_.getSpawnableList(EnumCreatureType.CREATURE);
/*     */     
/* 221 */     if (!list.isEmpty())
/*     */     {
/* 223 */       while (p_77191_6_.nextFloat() < p_77191_1_.getSpawningChance()) {
/*     */         
/* 225 */         BiomeGenBase.SpawnListEntry biomegenbase$spawnlistentry = (BiomeGenBase.SpawnListEntry)WeightedRandom.getRandomItem(worldIn.rand, list);
/* 226 */         int i = biomegenbase$spawnlistentry.minGroupCount + p_77191_6_.nextInt(1 + biomegenbase$spawnlistentry.maxGroupCount - biomegenbase$spawnlistentry.minGroupCount);
/* 227 */         IEntityLivingData ientitylivingdata = null;
/* 228 */         int j = p_77191_2_ + p_77191_6_.nextInt(p_77191_4_);
/* 229 */         int k = p_77191_3_ + p_77191_6_.nextInt(p_77191_5_);
/* 230 */         int l = j;
/* 231 */         int i1 = k;
/*     */         
/* 233 */         for (int j1 = 0; j1 < i; j1++) {
/*     */           
/* 235 */           boolean flag = false;
/*     */           
/* 237 */           for (int k1 = 0; !flag && k1 < 4; k1++) {
/*     */             
/* 239 */             BlockPos blockpos = worldIn.getTopSolidOrLiquidBlock(new BlockPos(j, 0, k));
/*     */             
/* 241 */             if (canCreatureTypeSpawnAtLocation(EntityLiving.SpawnPlacementType.ON_GROUND, worldIn, blockpos)) {
/*     */               EntityLiving entityliving;
/*     */ 
/*     */ 
/*     */               
/*     */               try {
/* 247 */                 entityliving = biomegenbase$spawnlistentry.entityClass.getConstructor(new Class[] { World.class }).newInstance(new Object[] { worldIn });
/*     */               }
/* 249 */               catch (Exception exception) {
/*     */                 
/* 251 */                 exception.printStackTrace();
/*     */               } 
/*     */ 
/*     */               
/* 255 */               entityliving.setLocationAndAngles((j + 0.5F), blockpos.getY(), (k + 0.5F), p_77191_6_.nextFloat() * 360.0F, 0.0F);
/* 256 */               worldIn.spawnEntityInWorld((Entity)entityliving);
/* 257 */               ientitylivingdata = entityliving.onInitialSpawn(worldIn.getDifficultyForLocation(new BlockPos((Entity)entityliving)), ientitylivingdata);
/* 258 */               flag = true;
/*     */             } 
/*     */             
/* 261 */             j += p_77191_6_.nextInt(5) - p_77191_6_.nextInt(5);
/*     */             
/* 263 */             for (k += p_77191_6_.nextInt(5) - p_77191_6_.nextInt(5); j < p_77191_2_ || j >= p_77191_2_ + p_77191_4_ || k < p_77191_3_ || k >= p_77191_3_ + p_77191_4_; k = i1 + p_77191_6_.nextInt(5) - p_77191_6_.nextInt(5))
/*     */             {
/* 265 */               j = l + p_77191_6_.nextInt(5) - p_77191_6_.nextInt(5);
/*     */             }
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\world\SpawnerAnimals.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */