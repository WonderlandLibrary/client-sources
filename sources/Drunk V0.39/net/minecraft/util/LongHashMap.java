/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.util;

public class LongHashMap {
    private transient Entry[] hashArray = new Entry[4096];
    private transient int numHashElements;
    private int mask = this.hashArray.length - 1;
    private int capacity = 3072;
    private final float percentUseable = 0.75f;
    private volatile transient int modCount;
    private static final String __OBFID = "CL_00001492";

    private static int getHashedKey(long originalKey) {
        return (int)(originalKey ^ originalKey >>> 27);
    }

    private static int hash(int integer) {
        integer = integer ^ integer >>> 20 ^ integer >>> 12;
        return integer ^ integer >>> 7 ^ integer >>> 4;
    }

    private static int getHashIndex(int p_76158_0_, int p_76158_1_) {
        return p_76158_0_ & p_76158_1_;
    }

    public int getNumHashElements() {
        return this.numHashElements;
    }

    public Object getValueByKey(long p_76164_1_) {
        int i = LongHashMap.getHashedKey(p_76164_1_);
        Entry longhashmap$entry = this.hashArray[LongHashMap.getHashIndex(i, this.mask)];
        while (longhashmap$entry != null) {
            if (longhashmap$entry.key == p_76164_1_) {
                return longhashmap$entry.value;
            }
            longhashmap$entry = longhashmap$entry.nextEntry;
        }
        return null;
    }

    public boolean containsItem(long p_76161_1_) {
        if (this.getEntry(p_76161_1_) == null) return false;
        return true;
    }

    final Entry getEntry(long p_76160_1_) {
        int i = LongHashMap.getHashedKey(p_76160_1_);
        Entry longhashmap$entry = this.hashArray[LongHashMap.getHashIndex(i, this.mask)];
        while (longhashmap$entry != null) {
            if (longhashmap$entry.key == p_76160_1_) {
                return longhashmap$entry;
            }
            longhashmap$entry = longhashmap$entry.nextEntry;
        }
        return null;
    }

    public void add(long p_76163_1_, Object p_76163_3_) {
        int i = LongHashMap.getHashedKey(p_76163_1_);
        int j = LongHashMap.getHashIndex(i, this.mask);
        Entry longhashmap$entry = this.hashArray[j];
        while (true) {
            if (longhashmap$entry == null) {
                ++this.modCount;
                this.createKey(i, p_76163_1_, p_76163_3_, j);
                return;
            }
            if (longhashmap$entry.key == p_76163_1_) {
                longhashmap$entry.value = p_76163_3_;
                return;
            }
            longhashmap$entry = longhashmap$entry.nextEntry;
        }
    }

    private void resizeTable(int p_76153_1_) {
        Entry[] alonghashmap$entry = this.hashArray;
        int i = alonghashmap$entry.length;
        if (i == 0x40000000) {
            this.capacity = Integer.MAX_VALUE;
            return;
        }
        Entry[] alonghashmap$entry1 = new Entry[p_76153_1_];
        this.copyHashTableTo(alonghashmap$entry1);
        this.hashArray = alonghashmap$entry1;
        this.mask = this.hashArray.length - 1;
        float f = p_76153_1_;
        this.getClass();
        this.capacity = (int)(f * 0.75f);
    }

    private void copyHashTableTo(Entry[] p_76154_1_) {
        Entry[] alonghashmap$entry = this.hashArray;
        int i = p_76154_1_.length;
        int j = 0;
        while (j < alonghashmap$entry.length) {
            Entry longhashmap$entry = alonghashmap$entry[j];
            if (longhashmap$entry != null) {
                Entry longhashmap$entry1;
                alonghashmap$entry[j] = null;
                do {
                    longhashmap$entry1 = longhashmap$entry.nextEntry;
                    int k = LongHashMap.getHashIndex(longhashmap$entry.hash, i - 1);
                    longhashmap$entry.nextEntry = p_76154_1_[k];
                    p_76154_1_[k] = longhashmap$entry;
                    longhashmap$entry = longhashmap$entry1;
                } while (longhashmap$entry1 != null);
            }
            ++j;
        }
    }

    public Object remove(long p_76159_1_) {
        Entry longhashmap$entry = this.removeKey(p_76159_1_);
        if (longhashmap$entry == null) {
            return null;
        }
        Object object = longhashmap$entry.value;
        return object;
    }

    final Entry removeKey(long p_76152_1_) {
        Entry longhashmap$entry2;
        Entry longhashmap$entry1;
        block3: {
            Entry longhashmap$entry;
            int i = LongHashMap.getHashedKey(p_76152_1_);
            int j = LongHashMap.getHashIndex(i, this.mask);
            longhashmap$entry1 = longhashmap$entry = this.hashArray[j];
            while (longhashmap$entry1 != null) {
                longhashmap$entry2 = longhashmap$entry1.nextEntry;
                if (longhashmap$entry1.key == p_76152_1_) {
                    ++this.modCount;
                    --this.numHashElements;
                    if (longhashmap$entry == longhashmap$entry1) {
                        this.hashArray[j] = longhashmap$entry2;
                        return longhashmap$entry1;
                    }
                    break block3;
                }
                longhashmap$entry = longhashmap$entry1;
                longhashmap$entry1 = longhashmap$entry2;
            }
            return longhashmap$entry1;
        }
        longhashmap$entry.nextEntry = longhashmap$entry2;
        return longhashmap$entry1;
    }

    private void createKey(int p_76156_1_, long p_76156_2_, Object p_76156_4_, int p_76156_5_) {
        Entry longhashmap$entry = this.hashArray[p_76156_5_];
        this.hashArray[p_76156_5_] = new Entry(p_76156_1_, p_76156_2_, p_76156_4_, longhashmap$entry);
        if (this.numHashElements++ < this.capacity) return;
        this.resizeTable(2 * this.hashArray.length);
    }

    public double getKeyDistribution() {
        int i = 0;
        int j = 0;
        while (j < this.hashArray.length) {
            if (this.hashArray[j] != null) {
                ++i;
            }
            ++j;
        }
        return 1.0 * (double)i / (double)this.numHashElements;
    }

    static class Entry {
        final long key;
        Object value;
        Entry nextEntry;
        final int hash;
        private static final String __OBFID = "CL_00001493";

        Entry(int p_i1553_1_, long p_i1553_2_, Object p_i1553_4_, Entry p_i1553_5_) {
            this.value = p_i1553_4_;
            this.nextEntry = p_i1553_5_;
            this.key = p_i1553_2_;
            this.hash = p_i1553_1_;
        }

        public final long getKey() {
            return this.key;
        }

        public final Object getValue() {
            return this.value;
        }

        public final boolean equals(Object p_equals_1_) {
            Object object1;
            Object object;
            Long olong1;
            if (!(p_equals_1_ instanceof Entry)) {
                return false;
            }
            Entry longhashmap$entry = (Entry)p_equals_1_;
            Long olong = this.getKey();
            if (olong != (olong1 = Long.valueOf(longhashmap$entry.getKey()))) {
                if (olong == null) return false;
                if (!olong.equals(olong1)) return false;
            }
            if ((object = this.getValue()) == (object1 = longhashmap$entry.getValue())) return true;
            if (object == null) return false;
            if (!object.equals(object1)) return false;
            return true;
        }

        public final int hashCode() {
            return LongHashMap.getHashedKey(this.key);
        }

        public final String toString() {
            return this.getKey() + "=" + this.getValue();
        }
    }
}

