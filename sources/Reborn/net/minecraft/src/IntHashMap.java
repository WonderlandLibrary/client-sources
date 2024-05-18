package net.minecraft.src;

import java.util.*;

public class IntHashMap
{
    private transient IntHashMapEntry[] slots;
    private transient int count;
    private int threshold;
    private final float growFactor = 0.75f;
    private transient volatile int versionStamp;
    private Set keySet;
    
    public IntHashMap() {
        this.slots = new IntHashMapEntry[16];
        this.threshold = 12;
        this.keySet = new HashSet();
    }
    
    private static int computeHash(int par0) {
        par0 ^= (par0 >>> 20 ^ par0 >>> 12);
        return par0 ^ par0 >>> 7 ^ par0 >>> 4;
    }
    
    private static int getSlotIndex(final int par0, final int par1) {
        return par0 & par1 - 1;
    }
    
    public Object lookup(final int par1) {
        final int var2 = computeHash(par1);
        for (IntHashMapEntry var3 = this.slots[getSlotIndex(var2, this.slots.length)]; var3 != null; var3 = var3.nextEntry) {
            if (var3.hashEntry == par1) {
                return var3.valueEntry;
            }
        }
        return null;
    }
    
    public boolean containsItem(final int par1) {
        return this.lookupEntry(par1) != null;
    }
    
    final IntHashMapEntry lookupEntry(final int par1) {
        final int var2 = computeHash(par1);
        for (IntHashMapEntry var3 = this.slots[getSlotIndex(var2, this.slots.length)]; var3 != null; var3 = var3.nextEntry) {
            if (var3.hashEntry == par1) {
                return var3;
            }
        }
        return null;
    }
    
    public void addKey(final int par1, final Object par2Obj) {
        this.keySet.add(par1);
        final int var3 = computeHash(par1);
        final int var4 = getSlotIndex(var3, this.slots.length);
        for (IntHashMapEntry var5 = this.slots[var4]; var5 != null; var5 = var5.nextEntry) {
            if (var5.hashEntry == par1) {
                var5.valueEntry = par2Obj;
                return;
            }
        }
        ++this.versionStamp;
        this.insert(var3, par1, par2Obj, var4);
    }
    
    private void grow(final int par1) {
        final IntHashMapEntry[] var2 = this.slots;
        final int var3 = var2.length;
        if (var3 == 1073741824) {
            this.threshold = Integer.MAX_VALUE;
        }
        else {
            final IntHashMapEntry[] var4 = new IntHashMapEntry[par1];
            this.copyTo(var4);
            this.slots = var4;
            this.threshold = (int)(par1 * 0.75f);
        }
    }
    
    private void copyTo(final IntHashMapEntry[] par1ArrayOfIntHashMapEntry) {
        final IntHashMapEntry[] var2 = this.slots;
        final int var3 = par1ArrayOfIntHashMapEntry.length;
        for (int var4 = 0; var4 < var2.length; ++var4) {
            IntHashMapEntry var5 = var2[var4];
            if (var5 != null) {
                var2[var4] = null;
                IntHashMapEntry var6;
                do {
                    var6 = var5.nextEntry;
                    final int var7 = getSlotIndex(var5.slotHash, var3);
                    var5.nextEntry = par1ArrayOfIntHashMapEntry[var7];
                    par1ArrayOfIntHashMapEntry[var7] = var5;
                } while ((var5 = var6) != null);
            }
        }
    }
    
    public Object removeObject(final int par1) {
        this.keySet.remove(par1);
        final IntHashMapEntry var2 = this.removeEntry(par1);
        return (var2 == null) ? null : var2.valueEntry;
    }
    
    final IntHashMapEntry removeEntry(final int par1) {
        final int var2 = computeHash(par1);
        final int var3 = getSlotIndex(var2, this.slots.length);
        IntHashMapEntry var5;
        IntHashMapEntry var6;
        for (IntHashMapEntry var4 = var5 = this.slots[var3]; var5 != null; var5 = var6) {
            var6 = var5.nextEntry;
            if (var5.hashEntry == par1) {
                ++this.versionStamp;
                --this.count;
                if (var4 == var5) {
                    this.slots[var3] = var6;
                }
                else {
                    var4.nextEntry = var6;
                }
                return var5;
            }
            var4 = var5;
        }
        return var5;
    }
    
    public void clearMap() {
        ++this.versionStamp;
        final IntHashMapEntry[] var1 = this.slots;
        for (int var2 = 0; var2 < var1.length; ++var2) {
            var1[var2] = null;
        }
        this.count = 0;
    }
    
    private void insert(final int par1, final int par2, final Object par3Obj, final int par4) {
        final IntHashMapEntry var5 = this.slots[par4];
        this.slots[par4] = new IntHashMapEntry(par1, par2, par3Obj, var5);
        if (this.count++ >= this.threshold) {
            this.grow(2 * this.slots.length);
        }
    }
    
    public Set getKeySet() {
        return this.keySet;
    }
    
    static int getHash(final int par0) {
        return computeHash(par0);
    }
}
