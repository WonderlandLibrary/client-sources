/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine;

import java.util.Comparator;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;

public class ChunkPosComparator
implements Comparator<ChunkPos> {
    private int chunkPosX;
    private int chunkPosZ;
    private double yawRad;
    private double pitchNorm;

    public ChunkPosComparator(int n, int n2, double d, double d2) {
        this.chunkPosX = n;
        this.chunkPosZ = n2;
        this.yawRad = d;
        this.pitchNorm = 1.0 - MathHelper.clamp(Math.abs(d2) / 1.5707963267948966, 0.0, 1.0);
    }

    @Override
    public int compare(ChunkPos chunkPos, ChunkPos chunkPos2) {
        int n = this.getDistSq(chunkPos);
        int n2 = this.getDistSq(chunkPos2);
        return n - n2;
    }

    private int getDistSq(ChunkPos chunkPos) {
        int n = chunkPos.x - this.chunkPosX;
        int n2 = chunkPos.z - this.chunkPosZ;
        int n3 = n * n + n2 * n2;
        double d = MathHelper.atan2(n2, n);
        double d2 = Math.abs(d - this.yawRad);
        if (d2 > Math.PI) {
            d2 = Math.PI * 2 - d2;
        }
        return (int)((double)n3 * 1000.0 * this.pitchNorm * d2 * d2);
    }

    @Override
    public int compare(Object object, Object object2) {
        return this.compare((ChunkPos)object, (ChunkPos)object2);
    }
}

