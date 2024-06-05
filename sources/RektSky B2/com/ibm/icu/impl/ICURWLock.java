package com.ibm.icu.impl;

import java.util.concurrent.locks.*;

public class ICURWLock
{
    private ReentrantReadWriteLock rwl;
    private Stats stats;
    
    public ICURWLock() {
        this.rwl = new ReentrantReadWriteLock();
        this.stats = null;
    }
    
    public synchronized Stats resetStats() {
        final Stats result = this.stats;
        this.stats = new Stats();
        return result;
    }
    
    public synchronized Stats clearStats() {
        final Stats result = this.stats;
        this.stats = null;
        return result;
    }
    
    public synchronized Stats getStats() {
        return (this.stats == null) ? null : new Stats(this.stats);
    }
    
    public void acquireRead() {
        if (this.stats != null) {
            synchronized (this) {
                final Stats stats = this.stats;
                ++stats._rc;
                if (this.rwl.getReadLockCount() > 0) {
                    final Stats stats2 = this.stats;
                    ++stats2._mrc;
                }
                if (this.rwl.isWriteLocked()) {
                    final Stats stats3 = this.stats;
                    ++stats3._wrc;
                }
            }
        }
        this.rwl.readLock().lock();
    }
    
    public void releaseRead() {
        this.rwl.readLock().unlock();
    }
    
    public void acquireWrite() {
        if (this.stats != null) {
            synchronized (this) {
                final Stats stats = this.stats;
                ++stats._wc;
                if (this.rwl.getReadLockCount() > 0 || this.rwl.isWriteLocked()) {
                    final Stats stats2 = this.stats;
                    ++stats2._wwc;
                }
            }
        }
        this.rwl.writeLock().lock();
    }
    
    public void releaseWrite() {
        this.rwl.writeLock().unlock();
    }
    
    public static final class Stats
    {
        public int _rc;
        public int _mrc;
        public int _wrc;
        public int _wc;
        public int _wwc;
        
        private Stats() {
        }
        
        private Stats(final int rc, final int mrc, final int wrc, final int wc, final int wwc) {
            this._rc = rc;
            this._mrc = mrc;
            this._wrc = wrc;
            this._wc = wc;
            this._wwc = wwc;
        }
        
        private Stats(final Stats rhs) {
            this(rhs._rc, rhs._mrc, rhs._wrc, rhs._wc, rhs._wwc);
        }
        
        @Override
        public String toString() {
            return " rc: " + this._rc + " mrc: " + this._mrc + " wrc: " + this._wrc + " wc: " + this._wc + " wwc: " + this._wwc;
        }
    }
}
