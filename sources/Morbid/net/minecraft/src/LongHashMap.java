package net.minecraft.src;

public class LongHashMap
{
    private transient LongHashMapEntry[] hashArray;
    private transient int numHashElements;
    private int capacity;
    private final float percentUseable;
    private transient volatile int modCount;
    
    public LongHashMap() {
        this.hashArray = new LongHashMapEntry[1024];
        this.capacity = (int)(0.75f * this.hashArray.length);
        this.percentUseable = 0.75f;
    }
    
    private static int getHashedKey(final long par0) {
        return (int)(par0 ^ par0 >>> 27);
    }
    
    private static int hash(int par0) {
        par0 ^= (par0 >>> 20 ^ par0 >>> 12);
        return par0 ^ par0 >>> 7 ^ par0 >>> 4;
    }
    
    private static int getHashIndex(final int par0, final int par1) {
        return par0 & par1 - 1;
    }
    
    public int getNumHashElements() {
        return this.numHashElements;
    }
    
    public Object getValueByKey(final long par1) {
        final int var3 = getHashedKey(par1);
        for (LongHashMapEntry var4 = this.hashArray[getHashIndex(var3, this.hashArray.length)]; var4 != null; var4 = var4.nextEntry) {
            if (var4.key == par1) {
                return var4.value;
            }
        }
        return null;
    }
    
    public boolean containsItem(final long par1) {
        return this.getEntry(par1) != null;
    }
    
    final LongHashMapEntry getEntry(final long par1) {
        final int var3 = getHashedKey(par1);
        for (LongHashMapEntry var4 = this.hashArray[getHashIndex(var3, this.hashArray.length)]; var4 != null; var4 = var4.nextEntry) {
            if (var4.key == par1) {
                return var4;
            }
        }
        return null;
    }
    
    public void add(final long par1, final Object par3Obj) {
        final int var4 = getHashedKey(par1);
        final int var5 = getHashIndex(var4, this.hashArray.length);
        for (LongHashMapEntry var6 = this.hashArray[var5]; var6 != null; var6 = var6.nextEntry) {
            if (var6.key == par1) {
                var6.value = par3Obj;
                return;
            }
        }
        ++this.modCount;
        this.createKey(var4, par1, par3Obj, var5);
    }
    
    private void resizeTable(final int par1) {
        final LongHashMapEntry[] var2 = this.hashArray;
        final int var3 = var2.length;
        if (var3 == 1073741824) {
            this.capacity = Integer.MAX_VALUE;
        }
        else {
            final LongHashMapEntry[] var4 = new LongHashMapEntry[par1];
            this.copyHashTableTo(var4);
            this.hashArray = var4;
            final float var5 = par1;
            this.getClass();
            this.capacity = (int)(var5 * 0.75f);
        }
    }
    
    private void copyHashTableTo(final LongHashMapEntry[] par1ArrayOfLongHashMapEntry) {
        final LongHashMapEntry[] var2 = this.hashArray;
        final int var3 = par1ArrayOfLongHashMapEntry.length;
        for (int var4 = 0; var4 < var2.length; ++var4) {
            LongHashMapEntry var5 = var2[var4];
            if (var5 != null) {
                var2[var4] = null;
                LongHashMapEntry var6;
                do {
                    var6 = var5.nextEntry;
                    final int var7 = getHashIndex(var5.hash, var3);
                    var5.nextEntry = par1ArrayOfLongHashMapEntry[var7];
                    par1ArrayOfLongHashMapEntry[var7] = var5;
                } while ((var5 = var6) != null);
            }
        }
    }
    
    public Object remove(final long par1) {
        final LongHashMapEntry var3 = this.removeKey(par1);
        return (var3 == null) ? null : var3.value;
    }
    
    final LongHashMapEntry removeKey(final long par1) {
        final int var3 = getHashedKey(par1);
        final int var4 = getHashIndex(var3, this.hashArray.length);
        LongHashMapEntry var6;
        LongHashMapEntry var7;
        for (LongHashMapEntry var5 = var6 = this.hashArray[var4]; var6 != null; var6 = var7) {
            var7 = var6.nextEntry;
            if (var6.key == par1) {
                ++this.modCount;
                --this.numHashElements;
                if (var5 == var6) {
                    this.hashArray[var4] = var7;
                }
                else {
                    var5.nextEntry = var7;
                }
                return var6;
            }
            var5 = var6;
        }
        return var6;
    }
    
    private void createKey(final int par1, final long par2, final Object par4Obj, final int par5) {
        final LongHashMapEntry var6 = this.hashArray[par5];
        this.hashArray[par5] = new LongHashMapEntry(par1, par2, par4Obj, var6);
        if (this.numHashElements++ >= this.capacity) {
            this.resizeTable(2 * this.hashArray.length);
        }
    }
    
    static int getHashCode(final long par0) {
        return getHashedKey(par0);
    }
    
    public double getKeyDistribution() {
        int var1 = 0;
        for (int var2 = 0; var2 < this.hashArray.length; ++var2) {
            if (this.hashArray[var2] != null) {
                ++var1;
            }
        }
        return 1.0 * var1 / this.numHashElements;
    }
}
