/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer;

import net.minecraft.util.math.BlockPos;

public class DestroyBlockProgress
implements Comparable<DestroyBlockProgress> {
    private final int miningPlayerEntId;
    private final BlockPos position;
    private int partialBlockProgress;
    private int createdAtCloudUpdateTick;

    public DestroyBlockProgress(int n, BlockPos blockPos) {
        this.miningPlayerEntId = n;
        this.position = blockPos;
    }

    public BlockPos getPosition() {
        return this.position;
    }

    public void setPartialBlockDamage(int n) {
        if (n > 10) {
            n = 10;
        }
        this.partialBlockProgress = n;
    }

    public int getPartialBlockDamage() {
        return this.partialBlockProgress;
    }

    public void setCloudUpdateTick(int n) {
        this.createdAtCloudUpdateTick = n;
    }

    public int getCreationCloudUpdateTick() {
        return this.createdAtCloudUpdateTick;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (object != null && this.getClass() == object.getClass()) {
            DestroyBlockProgress destroyBlockProgress = (DestroyBlockProgress)object;
            return this.miningPlayerEntId == destroyBlockProgress.miningPlayerEntId;
        }
        return true;
    }

    public int hashCode() {
        return Integer.hashCode(this.miningPlayerEntId);
    }

    @Override
    public int compareTo(DestroyBlockProgress destroyBlockProgress) {
        return this.partialBlockProgress != destroyBlockProgress.partialBlockProgress ? Integer.compare(this.partialBlockProgress, destroyBlockProgress.partialBlockProgress) : Integer.compare(this.miningPlayerEntId, destroyBlockProgress.miningPlayerEntId);
    }

    @Override
    public int compareTo(Object object) {
        return this.compareTo((DestroyBlockProgress)object);
    }
}

