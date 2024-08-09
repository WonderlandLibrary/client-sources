/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.render;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import net.minecraft.entity.Entity;
import net.minecraft.util.ClassInheritanceMultiMap;
import net.minecraft.util.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3i;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.chunk.ChunkSection;
import net.optifine.Config;

public class ChunkVisibility {
    public static final int MASK_FACINGS = 63;
    public static final Direction[][] enumFacingArrays = ChunkVisibility.makeEnumFacingArrays(false);
    public static final Direction[][] enumFacingOppositeArrays = ChunkVisibility.makeEnumFacingArrays(true);
    private static int counter = 0;
    private static int iMaxStatic = -1;
    private static int iMaxStaticFinal = 16;
    private static World worldLast = null;
    private static int pcxLast = Integer.MIN_VALUE;
    private static int pczLast = Integer.MIN_VALUE;

    public static int getMaxChunkY(World world, Entity entity2, int n) {
        int n2 = MathHelper.floor(entity2.getPosX()) >> 4;
        int n3 = MathHelper.floor(entity2.getPosY()) >> 4;
        int n4 = MathHelper.floor(entity2.getPosZ()) >> 4;
        n3 = Config.limit(n3, 0, 15);
        Chunk chunk = world.getChunk(n2, n4);
        int n5 = n2 - n;
        int n6 = n2 + n;
        int n7 = n4 - n;
        int n8 = n4 + n;
        if (world != worldLast || n2 != pcxLast || n4 != pczLast) {
            counter = 0;
            iMaxStaticFinal = 16;
            worldLast = world;
            pcxLast = n2;
            pczLast = n4;
        }
        if (counter == 0) {
            iMaxStatic = -1;
        }
        int n9 = iMaxStatic;
        switch (counter) {
            case 0: {
                n6 = n2;
                n8 = n4;
                break;
            }
            case 1: {
                n5 = n2;
                n8 = n4;
                break;
            }
            case 2: {
                n6 = n2;
                n7 = n4;
                break;
            }
            case 3: {
                n5 = n2;
                n7 = n4;
            }
        }
        for (int i = n5; i < n6; ++i) {
            block9: for (int j = n7; j < n8; ++j) {
                ClassInheritanceMultiMap<Entity>[] classInheritanceMultiMapArray;
                Chunk chunk2 = world.getChunk(i, j);
                if (chunk2.isEmpty()) continue;
                ChunkSection[] chunkSectionArray = chunk2.getSections();
                for (int k = chunkSectionArray.length - 1; k > n9; --k) {
                    ChunkSection chunkSection = chunkSectionArray[k];
                    if (chunkSection == null || chunkSection.isEmpty()) continue;
                    if (k <= n9) break;
                    n9 = k;
                    break;
                }
                try {
                    classInheritanceMultiMapArray = chunk2.getTileEntityMap();
                    if (!classInheritanceMultiMapArray.isEmpty()) {
                        for (ClassInheritanceMultiMap<Entity> classInheritanceMultiMap : classInheritanceMultiMapArray.keySet()) {
                            int n10 = ((Vector3i)((Object)classInheritanceMultiMap)).getY() >> 4;
                            if (n10 <= n9) continue;
                            n9 = n10;
                        }
                    }
                } catch (ConcurrentModificationException concurrentModificationException) {
                    // empty catch block
                }
                classInheritanceMultiMapArray = chunk2.getEntityLists();
                for (int k = classInheritanceMultiMapArray.length - 1; k > n9; --k) {
                    ClassInheritanceMultiMap<Entity> classInheritanceMultiMap;
                    classInheritanceMultiMap = classInheritanceMultiMapArray[k];
                    if (classInheritanceMultiMap.isEmpty() || chunk2 == chunk && k == n3 && classInheritanceMultiMap.size() == 1) continue;
                    if (k <= n9) continue block9;
                    n9 = k;
                    continue block9;
                }
            }
        }
        if (counter < 3) {
            iMaxStatic = n9;
            n9 = iMaxStaticFinal;
        } else {
            iMaxStaticFinal = n9;
            iMaxStatic = -1;
        }
        counter = (counter + 1) % 4;
        return n9 << 4;
    }

    public static boolean isFinished() {
        return counter == 0;
    }

    private static Direction[][] makeEnumFacingArrays(boolean bl) {
        int n = 64;
        Direction[][] directionArray = new Direction[n][];
        for (int i = 0; i < n; ++i) {
            ArrayList<Direction> arrayList = new ArrayList<Direction>();
            for (int j = 0; j < Direction.VALUES.length; ++j) {
                Direction direction = Direction.VALUES[j];
                Direction direction2 = bl ? direction.getOpposite() : direction;
                int n2 = 1 << direction2.ordinal();
                if ((i & n2) == 0) continue;
                arrayList.add(direction);
            }
            Direction[] directionArray2 = arrayList.toArray(new Direction[arrayList.size()]);
            directionArray[i] = directionArray2;
        }
        return directionArray;
    }

    public static Direction[] getFacingsNotOpposite(int n) {
        int n2 = ~n & 0x3F;
        return enumFacingOppositeArrays[n2];
    }

    public static void reset() {
        worldLast = null;
    }
}

