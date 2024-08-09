/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.util;

public class LockCounter {
    private int lockCount;

    public boolean lock() {
        ++this.lockCount;
        return this.lockCount == 1;
    }

    public boolean unlock() {
        if (this.lockCount <= 0) {
            return true;
        }
        --this.lockCount;
        return this.lockCount == 0;
    }

    public boolean isLocked() {
        return this.lockCount > 0;
    }

    public int getLockCount() {
        return this.lockCount;
    }

    public String toString() {
        return "lockCount: " + this.lockCount;
    }
}

