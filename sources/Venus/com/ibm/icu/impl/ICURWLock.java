/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl;

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ICURWLock {
    private ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();
    private Stats stats = null;

    public synchronized Stats resetStats() {
        Stats stats = this.stats;
        this.stats = new Stats(null);
        return stats;
    }

    public synchronized Stats clearStats() {
        Stats stats = this.stats;
        this.stats = null;
        return stats;
    }

    public synchronized Stats getStats() {
        return this.stats == null ? null : new Stats(this.stats, null);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void acquireRead() {
        if (this.stats != null) {
            ICURWLock iCURWLock = this;
            synchronized (iCURWLock) {
                ++this.stats._rc;
                if (this.rwl.getReadLockCount() > 0) {
                    ++this.stats._mrc;
                }
                if (this.rwl.isWriteLocked()) {
                    ++this.stats._wrc;
                }
            }
        }
        this.rwl.readLock().lock();
    }

    public void releaseRead() {
        this.rwl.readLock().unlock();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public void acquireWrite() {
        if (this.stats != null) {
            ICURWLock iCURWLock = this;
            synchronized (iCURWLock) {
                ++this.stats._wc;
                if (this.rwl.getReadLockCount() > 0 || this.rwl.isWriteLocked()) {
                    ++this.stats._wwc;
                }
            }
        }
        this.rwl.writeLock().lock();
    }

    public void releaseWrite() {
        this.rwl.writeLock().unlock();
    }

    public static final class Stats {
        public int _rc;
        public int _mrc;
        public int _wrc;
        public int _wc;
        public int _wwc;

        private Stats() {
        }

        private Stats(int n, int n2, int n3, int n4, int n5) {
            this._rc = n;
            this._mrc = n2;
            this._wrc = n3;
            this._wc = n4;
            this._wwc = n5;
        }

        private Stats(Stats stats) {
            this(stats._rc, stats._mrc, stats._wrc, stats._wc, stats._wwc);
        }

        public String toString() {
            return " rc: " + this._rc + " mrc: " + this._mrc + " wrc: " + this._wrc + " wc: " + this._wc + " wwc: " + this._wwc;
        }

        Stats(1 var1_1) {
            this();
        }

        Stats(Stats stats, 1 var2_2) {
            this(stats);
        }
    }
}

