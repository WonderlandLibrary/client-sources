package net.minecraft.src;

import java.util.*;

public final class SpawnerAnimals
{
    private static HashMap eligibleChunksForSpawning;
    protected static final Class[] nightSpawnEntities;
    
    static {
        SpawnerAnimals.eligibleChunksForSpawning = new HashMap();
        nightSpawnEntities = new Class[] { EntitySpider.class, EntityZombie.class, EntitySkeleton.class };
    }
    
    protected static ChunkPosition getRandomSpawningPointInChunk(final World par0World, final int par1, final int par2) {
        final Chunk var3 = par0World.getChunkFromChunkCoords(par1, par2);
        final int var4 = par1 * 16 + par0World.rand.nextInt(16);
        final int var5 = par2 * 16 + par0World.rand.nextInt(16);
        final int var6 = par0World.rand.nextInt((var3 == null) ? par0World.getActualHeight() : (var3.getTopFilledSegment() + 16 - 1));
        return new ChunkPosition(var4, var6, var5);
    }
    
    public static final int findChunksForSpawning(final WorldServer par0WorldServer, final boolean par1, final boolean par2, final boolean par3) {
        if (!par1 && !par2) {
            return 0;
        }
        SpawnerAnimals.eligibleChunksForSpawning.clear();
        for (int var4 = 0; var4 < par0WorldServer.playerEntities.size(); ++var4) {
            final EntityPlayer var5 = par0WorldServer.playerEntities.get(var4);
            final int var6 = MathHelper.floor_double(var5.posX / 16.0);
            final int var7 = MathHelper.floor_double(var5.posZ / 16.0);
            final byte var8 = 8;
            for (int var9 = -var8; var9 <= var8; ++var9) {
                for (int var10 = -var8; var10 <= var8; ++var10) {
                    final boolean var11 = var9 == -var8 || var9 == var8 || var10 == -var8 || var10 == var8;
                    final ChunkCoordIntPair var12 = new ChunkCoordIntPair(var9 + var6, var10 + var7);
                    if (!var11) {
                        SpawnerAnimals.eligibleChunksForSpawning.put(var12, false);
                    }
                    else if (!SpawnerAnimals.eligibleChunksForSpawning.containsKey(var12)) {
                        SpawnerAnimals.eligibleChunksForSpawning.put(var12, true);
                    }
                }
            }
        }
        int var4 = 0;
        final ChunkCoordinates var13 = par0WorldServer.getSpawnPoint();
        final EnumCreatureType[] var14 = EnumCreatureType.values();
        final int var7 = var14.length;
    Label_0819_Outer:
        for (final EnumCreatureType var16 : var14) {
            if ((!var16.getPeacefulCreature() || par2) && (var16.getPeacefulCreature() || par1) && (!var16.getAnimal() || par3) && par0WorldServer.countEntities(var16.getCreatureClass()) <= var16.getMaxNumberOfCreature() * SpawnerAnimals.eligibleChunksForSpawning.size() / 256) {
            Label_0819:
                while (true) {
                    for (final ChunkCoordIntPair var18 : SpawnerAnimals.eligibleChunksForSpawning.keySet()) {
                        if (!SpawnerAnimals.eligibleChunksForSpawning.get(var18)) {
                            final ChunkPosition var19 = getRandomSpawningPointInChunk(par0WorldServer, var18.chunkXPos, var18.chunkZPos);
                            final int var20 = var19.x;
                            final int var21 = var19.y;
                            final int var22 = var19.z;
                            if (par0WorldServer.isBlockNormalCube(var20, var21, var22) || par0WorldServer.getBlockMaterial(var20, var21, var22) != var16.getCreatureMaterial()) {
                                continue Label_0819_Outer;
                            }
                            int var23 = 0;
                            for (int var24 = 0; var24 < 3; ++var24) {
                                int var25 = var20;
                                int var26 = var21;
                                int var27 = var22;
                                final byte var28 = 6;
                                SpawnListEntry var29 = null;
                                for (int var30 = 0; var30 < 4; ++var30) {
                                    var25 += par0WorldServer.rand.nextInt(var28) - par0WorldServer.rand.nextInt(var28);
                                    var26 += par0WorldServer.rand.nextInt(1) - par0WorldServer.rand.nextInt(1);
                                    var27 += par0WorldServer.rand.nextInt(var28) - par0WorldServer.rand.nextInt(var28);
                                    if (canCreatureTypeSpawnAtLocation(var16, par0WorldServer, var25, var26, var27)) {
                                        final float var31 = var25 + 0.5f;
                                        final float var32 = var26;
                                        final float var33 = var27 + 0.5f;
                                        if (par0WorldServer.getClosestPlayer(var31, var32, var33, 24.0) == null) {
                                            final float var34 = var31 - var13.posX;
                                            final float var35 = var32 - var13.posY;
                                            final float var36 = var33 - var13.posZ;
                                            final float var37 = var34 * var34 + var35 * var35 + var36 * var36;
                                            if (var37 >= 576.0f) {
                                                if (var29 == null) {
                                                    var29 = par0WorldServer.spawnRandomCreature(var16, var25, var26, var27);
                                                    if (var29 == null) {
                                                        break;
                                                    }
                                                }
                                                EntityLiving var38;
                                                try {
                                                    var38 = var29.entityClass.getConstructor(World.class).newInstance(par0WorldServer);
                                                }
                                                catch (Exception var39) {
                                                    var39.printStackTrace();
                                                    return var4;
                                                }
                                                var38.setLocationAndAngles(var31, var32, var33, par0WorldServer.rand.nextFloat() * 360.0f, 0.0f);
                                                if (var38.getCanSpawnHere()) {
                                                    ++var23;
                                                    par0WorldServer.spawnEntityInWorld(var38);
                                                    creatureSpecificInit(var38, par0WorldServer, var31, var32, var33);
                                                    if (var23 >= var38.getMaxSpawnedInChunk()) {
                                                        continue Label_0819;
                                                    }
                                                }
                                                var4 += var23;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    break;
                }
            }
        }
        return var4;
    }
    
    public static boolean canCreatureTypeSpawnAtLocation(final EnumCreatureType par0EnumCreatureType, final World par1World, final int par2, final int par3, final int par4) {
        if (par0EnumCreatureType.getCreatureMaterial() == Material.water) {
            return par1World.getBlockMaterial(par2, par3, par4).isLiquid() && par1World.getBlockMaterial(par2, par3 - 1, par4).isLiquid() && !par1World.isBlockNormalCube(par2, par3 + 1, par4);
        }
        if (!par1World.doesBlockHaveSolidTopSurface(par2, par3 - 1, par4)) {
            return false;
        }
        final int var5 = par1World.getBlockId(par2, par3 - 1, par4);
        return var5 != Block.bedrock.blockID && !par1World.isBlockNormalCube(par2, par3, par4) && !par1World.getBlockMaterial(par2, par3, par4).isLiquid() && !par1World.isBlockNormalCube(par2, par3 + 1, par4);
    }
    
    private static void creatureSpecificInit(final EntityLiving par0EntityLiving, final World par1World, final float par2, final float par3, final float par4) {
        par0EntityLiving.initCreature();
    }
    
    public static void performWorldGenSpawning(final World par0World, final BiomeGenBase par1BiomeGenBase, final int par2, final int par3, final int par4, final int par5, final Random par6Random) {
        final List var7 = par1BiomeGenBase.getSpawnableList(EnumCreatureType.creature);
        if (!var7.isEmpty()) {
            while (par6Random.nextFloat() < par1BiomeGenBase.getSpawningChance()) {
                final SpawnListEntry var8 = (SpawnListEntry)WeightedRandom.getRandomItem(par0World.rand, var7);
                final int var9 = var8.minGroupCount + par6Random.nextInt(1 + var8.maxGroupCount - var8.minGroupCount);
                int var10 = par2 + par6Random.nextInt(par4);
                int var11 = par3 + par6Random.nextInt(par5);
                final int var12 = var10;
                final int var13 = var11;
                for (int var14 = 0; var14 < var9; ++var14) {
                    boolean var15 = false;
                    for (int var16 = 0; !var15 && var16 < 4; ++var16) {
                        final int var17 = par0World.getTopSolidOrLiquidBlock(var10, var11);
                        if (canCreatureTypeSpawnAtLocation(EnumCreatureType.creature, par0World, var10, var17, var11)) {
                            final float var18 = var10 + 0.5f;
                            final float var19 = var17;
                            final float var20 = var11 + 0.5f;
                            EntityLiving var21;
                            try {
                                var21 = var8.entityClass.getConstructor(World.class).newInstance(par0World);
                            }
                            catch (Exception var22) {
                                var22.printStackTrace();
                                continue;
                            }
                            var21.setLocationAndAngles(var18, var19, var20, par6Random.nextFloat() * 360.0f, 0.0f);
                            par0World.spawnEntityInWorld(var21);
                            creatureSpecificInit(var21, par0World, var18, var19, var20);
                            var15 = true;
                        }
                        for (var10 += par6Random.nextInt(5) - par6Random.nextInt(5), var11 += par6Random.nextInt(5) - par6Random.nextInt(5); var10 < par2 || var10 >= par2 + par4 || var11 < par3 || var11 >= par3 + par4; var10 = var12 + par6Random.nextInt(5) - par6Random.nextInt(5), var11 = var13 + par6Random.nextInt(5) - par6Random.nextInt(5)) {}
                    }
                }
            }
        }
    }
}
