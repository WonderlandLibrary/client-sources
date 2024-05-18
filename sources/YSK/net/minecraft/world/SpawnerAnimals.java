package net.minecraft.world;

import net.minecraft.entity.player.*;
import net.minecraft.world.biome.*;
import net.minecraft.entity.*;
import java.lang.reflect.*;
import net.minecraft.init.*;
import net.minecraft.block.*;
import net.minecraft.util.*;
import java.util.*;
import com.google.common.collect.*;
import net.minecraft.world.chunk.*;

public final class SpawnerAnimals
{
    private final Set<ChunkCoordIntPair> eligibleChunksForSpawning;
    private static final int MOB_COUNT_DIV;
    
    public int findChunksForSpawning(final WorldServer worldServer, final boolean b, final boolean b2, final boolean b3) {
        if (!b && !b2) {
            return "".length();
        }
        this.eligibleChunksForSpawning.clear();
        int length = "".length();
        final Iterator<EntityPlayer> iterator = worldServer.playerEntities.iterator();
        "".length();
        if (3 == -1) {
            throw null;
        }
        while (iterator.hasNext()) {
            final EntityPlayer entityPlayer = iterator.next();
            if (!entityPlayer.isSpectator()) {
                final int floor_double = MathHelper.floor_double(entityPlayer.posX / 16.0);
                final int floor_double2 = MathHelper.floor_double(entityPlayer.posZ / 16.0);
                final int n = 0x32 ^ 0x3A;
                int i = -n;
                "".length();
                if (0 == 3) {
                    throw null;
                }
                while (i <= n) {
                    int j = -n;
                    "".length();
                    if (1 == 4) {
                        throw null;
                    }
                    while (j <= n) {
                        int n2;
                        if (i != -n && i != n && j != -n && j != n) {
                            n2 = "".length();
                            "".length();
                            if (4 <= 2) {
                                throw null;
                            }
                        }
                        else {
                            n2 = " ".length();
                        }
                        final int n3 = n2;
                        final ChunkCoordIntPair chunkCoordIntPair = new ChunkCoordIntPair(i + floor_double, j + floor_double2);
                        if (!this.eligibleChunksForSpawning.contains(chunkCoordIntPair)) {
                            ++length;
                            if (n3 == 0 && worldServer.getWorldBorder().contains(chunkCoordIntPair)) {
                                this.eligibleChunksForSpawning.add(chunkCoordIntPair);
                            }
                        }
                        ++j;
                    }
                    ++i;
                }
            }
        }
        int length2 = "".length();
        final BlockPos spawnPoint = worldServer.getSpawnPoint();
        final EnumCreatureType[] values;
        final int length3 = (values = EnumCreatureType.values()).length;
        int k = "".length();
        "".length();
        if (2 < -1) {
            throw null;
        }
        while (k < length3) {
            final EnumCreatureType enumCreatureType = values[k];
            if ((!enumCreatureType.getPeacefulCreature() || b2) && (enumCreatureType.getPeacefulCreature() || b) && (!enumCreatureType.getAnimal() || b3) && worldServer.countEntities(enumCreatureType.getCreatureClass()) <= enumCreatureType.getMaxNumberOfCreature() * length / SpawnerAnimals.MOB_COUNT_DIV) {
                final Iterator<ChunkCoordIntPair> iterator2 = this.eligibleChunksForSpawning.iterator();
                "".length();
                if (1 <= 0) {
                    throw null;
                }
            Label_1003:
                while (true) {
                    while (iterator2.hasNext()) {
                        final ChunkCoordIntPair chunkCoordIntPair2 = iterator2.next();
                        final BlockPos randomChunkPosition = getRandomChunkPosition(worldServer, chunkCoordIntPair2.chunkXPos, chunkCoordIntPair2.chunkZPos);
                        final int x = randomChunkPosition.getX();
                        final int y = randomChunkPosition.getY();
                        final int z = randomChunkPosition.getZ();
                        if (!worldServer.getBlockState(randomChunkPosition).getBlock().isNormalCube()) {
                            int length4 = "".length();
                            int l = "".length();
                            "".length();
                            if (4 == 2) {
                                throw null;
                            }
                            while (l < "   ".length()) {
                                int n4 = x;
                                int n5 = y;
                                int n6 = z;
                                final int n7 = 0xAB ^ 0xAD;
                                BiomeGenBase.SpawnListEntry spawnListEntryForType = null;
                                IEntityLivingData onInitialSpawn = null;
                                int length5 = "".length();
                                "".length();
                                if (1 >= 2) {
                                    throw null;
                                }
                                while (length5 < (0xC ^ 0x8)) {
                                    n4 += worldServer.rand.nextInt(n7) - worldServer.rand.nextInt(n7);
                                    n5 += worldServer.rand.nextInt(" ".length()) - worldServer.rand.nextInt(" ".length());
                                    n6 += worldServer.rand.nextInt(n7) - worldServer.rand.nextInt(n7);
                                    final BlockPos blockPos = new BlockPos(n4, n5, n6);
                                    final float n8 = n4 + 0.5f;
                                    final float n9 = n6 + 0.5f;
                                    if (!worldServer.isAnyPlayerWithinRangeAt(n8, n5, n9, 24.0) && spawnPoint.distanceSq(n8, n5, n9) >= 576.0) {
                                        if (spawnListEntryForType == null) {
                                            spawnListEntryForType = worldServer.getSpawnListEntryForTypeAt(enumCreatureType, blockPos);
                                            if (spawnListEntryForType == null) {
                                                "".length();
                                                if (0 >= 4) {
                                                    throw null;
                                                }
                                                break;
                                            }
                                        }
                                        if (worldServer.canCreatureTypeSpawnHere(enumCreatureType, spawnListEntryForType, blockPos) && canCreatureTypeSpawnAtLocation(EntitySpawnPlacementRegistry.getPlacementForEntity(spawnListEntryForType.entityClass), worldServer, blockPos)) {
                                            EntityLiving entityLiving;
                                            try {
                                                final Class<? extends EntityLiving> entityClass = spawnListEntryForType.entityClass;
                                                final Class[] array = new Class[" ".length()];
                                                array["".length()] = World.class;
                                                final Constructor<? extends EntityLiving> constructor = entityClass.getConstructor((Class<?>[])array);
                                                final Object[] array2 = new Object[" ".length()];
                                                array2["".length()] = worldServer;
                                                entityLiving = (EntityLiving)constructor.newInstance(array2);
                                                "".length();
                                                if (2 != 2) {
                                                    throw null;
                                                }
                                            }
                                            catch (Exception ex) {
                                                ex.printStackTrace();
                                                return length2;
                                            }
                                            entityLiving.setLocationAndAngles(n8, n5, n9, worldServer.rand.nextFloat() * 360.0f, 0.0f);
                                            if (entityLiving.getCanSpawnHere() && entityLiving.isNotColliding()) {
                                                onInitialSpawn = entityLiving.onInitialSpawn(worldServer.getDifficultyForLocation(new BlockPos(entityLiving)), onInitialSpawn);
                                                if (entityLiving.isNotColliding()) {
                                                    ++length4;
                                                    worldServer.spawnEntityInWorld(entityLiving);
                                                }
                                                if (length4 >= entityLiving.getMaxSpawnedInChunk()) {
                                                    "".length();
                                                    if (3 != 3) {
                                                        throw null;
                                                    }
                                                    continue Label_1003;
                                                }
                                            }
                                            length2 += length4;
                                        }
                                    }
                                    ++length5;
                                }
                                ++l;
                            }
                        }
                    }
                    break;
                }
            }
            ++k;
        }
        return length2;
    }
    
    public static boolean canCreatureTypeSpawnAtLocation(final EntityLiving.SpawnPlacementType spawnPlacementType, final World world, final BlockPos blockPos) {
        if (!world.getWorldBorder().contains(blockPos)) {
            return "".length() != 0;
        }
        final Block block = world.getBlockState(blockPos).getBlock();
        if (spawnPlacementType == EntityLiving.SpawnPlacementType.IN_WATER) {
            if (block.getMaterial().isLiquid() && world.getBlockState(blockPos.down()).getBlock().getMaterial().isLiquid() && !world.getBlockState(blockPos.up()).getBlock().isNormalCube()) {
                return " ".length() != 0;
            }
            return "".length() != 0;
        }
        else {
            final BlockPos down = blockPos.down();
            if (!World.doesBlockHaveSolidTopSurface(world, down)) {
                return "".length() != 0;
            }
            final Block block2 = world.getBlockState(down).getBlock();
            int n;
            if (block2 != Blocks.bedrock && block2 != Blocks.barrier) {
                n = " ".length();
                "".length();
                if (1 < 0) {
                    throw null;
                }
            }
            else {
                n = "".length();
            }
            if (n != 0 && !block.isNormalCube() && !block.getMaterial().isLiquid() && !world.getBlockState(blockPos.up()).getBlock().isNormalCube()) {
                return " ".length() != 0;
            }
            return "".length() != 0;
        }
    }
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (4 <= 0) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public static void performWorldGenSpawning(final World world, final BiomeGenBase biomeGenBase, final int n, final int n2, final int n3, final int n4, final Random random) {
        final List<BiomeGenBase.SpawnListEntry> spawnableList = biomeGenBase.getSpawnableList(EnumCreatureType.CREATURE);
        if (!spawnableList.isEmpty()) {
            "".length();
            if (2 >= 3) {
                throw null;
            }
            while (random.nextFloat() < biomeGenBase.getSpawningChance()) {
                final BiomeGenBase.SpawnListEntry spawnListEntry = WeightedRandom.getRandomItem(world.rand, spawnableList);
                final int n5 = spawnListEntry.minGroupCount + random.nextInt(" ".length() + spawnListEntry.maxGroupCount - spawnListEntry.minGroupCount);
                IEntityLivingData onInitialSpawn = null;
                int n6 = n + random.nextInt(n3);
                int n7 = n2 + random.nextInt(n4);
                final int n8 = n6;
                final int n9 = n7;
                int i = "".length();
                "".length();
                if (0 < 0) {
                    throw null;
                }
                while (i < n5) {
                    int n10 = "".length();
                    int length = "".length();
                    "".length();
                    if (3 >= 4) {
                        throw null;
                    }
                    while (n10 == 0 && length < (0x70 ^ 0x74)) {
                        final BlockPos topSolidOrLiquidBlock = world.getTopSolidOrLiquidBlock(new BlockPos(n6, "".length(), n7));
                        Label_0495: {
                            if (canCreatureTypeSpawnAtLocation(EntityLiving.SpawnPlacementType.ON_GROUND, world, topSolidOrLiquidBlock)) {
                                EntityLiving entityLiving;
                                try {
                                    final Class<? extends EntityLiving> entityClass = spawnListEntry.entityClass;
                                    final Class[] array = new Class[" ".length()];
                                    array["".length()] = World.class;
                                    final Constructor<? extends EntityLiving> constructor = entityClass.getConstructor((Class<?>[])array);
                                    final Object[] array2 = new Object[" ".length()];
                                    array2["".length()] = world;
                                    entityLiving = (EntityLiving)constructor.newInstance(array2);
                                    "".length();
                                    if (4 < 4) {
                                        throw null;
                                    }
                                }
                                catch (Exception ex) {
                                    ex.printStackTrace();
                                    "".length();
                                    if (2 >= 4) {
                                        throw null;
                                    }
                                    break Label_0495;
                                }
                                entityLiving.setLocationAndAngles(n6 + 0.5f, topSolidOrLiquidBlock.getY(), n7 + 0.5f, random.nextFloat() * 360.0f, 0.0f);
                                world.spawnEntityInWorld(entityLiving);
                                onInitialSpawn = entityLiving.onInitialSpawn(world.getDifficultyForLocation(new BlockPos(entityLiving)), onInitialSpawn);
                                n10 = " ".length();
                            }
                            n6 += random.nextInt(0xAA ^ 0xAF) - random.nextInt(0x11 ^ 0x14);
                            n7 += random.nextInt(0x50 ^ 0x55) - random.nextInt(0x6C ^ 0x69);
                            "".length();
                            if (2 != 2) {
                                throw null;
                            }
                            while (n6 < n || n6 >= n + n3 || n7 < n2 || n7 >= n2 + n3) {
                                n6 = n8 + random.nextInt(0x87 ^ 0x82) - random.nextInt(0x46 ^ 0x43);
                                n7 = n9 + random.nextInt(0xA6 ^ 0xA3) - random.nextInt(0x74 ^ 0x71);
                            }
                        }
                        ++length;
                    }
                    ++i;
                }
            }
        }
    }
    
    static {
        MOB_COUNT_DIV = (int)Math.pow(17.0, 2.0);
    }
    
    public SpawnerAnimals() {
        this.eligibleChunksForSpawning = (Set<ChunkCoordIntPair>)Sets.newHashSet();
    }
    
    protected static BlockPos getRandomChunkPosition(final World world, final int n, final int n2) {
        final Chunk chunkFromChunkCoords = world.getChunkFromChunkCoords(n, n2);
        final int n3 = n * (0xD ^ 0x1D) + world.rand.nextInt(0x3D ^ 0x2D);
        final int n4 = n2 * (0x41 ^ 0x51) + world.rand.nextInt(0xA8 ^ 0xB8);
        final int func_154354_b = MathHelper.func_154354_b(chunkFromChunkCoords.getHeight(new BlockPos(n3, "".length(), n4)) + " ".length(), 0x30 ^ 0x20);
        final Random rand = world.rand;
        int n5;
        if (func_154354_b > 0) {
            n5 = func_154354_b;
            "".length();
            if (0 == 4) {
                throw null;
            }
        }
        else {
            n5 = chunkFromChunkCoords.getTopFilledSegment() + (0x3C ^ 0x2C) - " ".length();
        }
        return new BlockPos(n3, rand.nextInt(n5), n4);
    }
}
