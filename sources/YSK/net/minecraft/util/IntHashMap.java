package net.minecraft.util;

public class IntHashMap<V>
{
    private transient int count;
    private final float growFactor = 0.75f;
    private int threshold;
    private transient Entry<V>[] slots;
    
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
            if (1 < 1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public boolean containsItem(final int n) {
        if (this.lookupEntry(n) != null) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public V removeObject(final int n) {
        final Entry<V> removeEntry = this.removeEntry(n);
        V valueEntry;
        if (removeEntry == null) {
            valueEntry = null;
            "".length();
            if (4 < 2) {
                throw null;
            }
        }
        else {
            valueEntry = removeEntry.valueEntry;
        }
        return valueEntry;
    }
    
    final Entry<V> lookupEntry(final int n) {
        Entry<V> nextEntry = this.slots[getSlotIndex(computeHash(n), this.slots.length)];
        "".length();
        if (1 < 0) {
            throw null;
        }
        while (nextEntry != null) {
            if (nextEntry.hashEntry == n) {
                return nextEntry;
            }
            nextEntry = nextEntry.nextEntry;
        }
        return null;
    }
    
    public void clearMap() {
        final Entry<V>[] slots = this.slots;
        int i = "".length();
        "".length();
        if (2 <= -1) {
            throw null;
        }
        while (i < slots.length) {
            slots[i] = null;
            ++i;
        }
        this.count = "".length();
    }
    
    private void grow(final int n) {
        if (this.slots.length == 602960661 + 390447089 - 707161066 + 787495140) {
            this.threshold = 113502826 + 2115585310 - 917163331 + 835558842;
            "".length();
            if (4 < 4) {
                throw null;
            }
        }
        else {
            final Entry[] slots = new Entry[n];
            this.copyTo(slots);
            this.slots = (Entry<V>[])slots;
            this.threshold = (int)(n * 0.75f);
        }
    }
    
    private static int getSlotIndex(final int n, final int n2) {
        return n & n2 - " ".length();
    }
    
    public IntHashMap() {
        this.slots = (Entry<V>[])new Entry[0x10 ^ 0x0];
        this.threshold = (0x42 ^ 0x4E);
    }
    
    public void addKey(final int n, final V valueEntry) {
        final int computeHash = computeHash(n);
        final int slotIndex = getSlotIndex(computeHash, this.slots.length);
        Entry<V> nextEntry = this.slots[slotIndex];
        "".length();
        if (2 < -1) {
            throw null;
        }
        while (nextEntry != null) {
            if (nextEntry.hashEntry == n) {
                nextEntry.valueEntry = valueEntry;
                return;
            }
            nextEntry = nextEntry.nextEntry;
        }
        this.insert(computeHash, n, valueEntry, slotIndex);
    }
    
    private void copyTo(final Entry<V>[] array) {
        final Entry<V>[] slots = this.slots;
        final int length = array.length;
        int i = "".length();
        "".length();
        if (-1 == 0) {
            throw null;
        }
        while (i < slots.length) {
            Entry<V> entry = slots[i];
            if (entry != null) {
                slots[i] = null;
                Entry<V> nextEntry;
                do {
                    nextEntry = entry.nextEntry;
                    final int slotIndex = getSlotIndex(entry.slotHash, length);
                    entry.nextEntry = array[slotIndex];
                    array[slotIndex] = entry;
                } while ((entry = nextEntry) != null);
            }
            ++i;
        }
    }
    
    public V lookup(final int n) {
        Entry<V> nextEntry = this.slots[getSlotIndex(computeHash(n), this.slots.length)];
        "".length();
        if (2 == 4) {
            throw null;
        }
        while (nextEntry != null) {
            if (nextEntry.hashEntry == n) {
                return nextEntry.valueEntry;
            }
            nextEntry = nextEntry.nextEntry;
        }
        return null;
    }
    
    private void insert(final int n, final int n2, final V v, final int n3) {
        this.slots[n3] = new Entry<V>(n, n2, v, this.slots[n3]);
        final int count = this.count;
        this.count = count + " ".length();
        if (count >= this.threshold) {
            this.grow("  ".length() * this.slots.length);
        }
    }
    
    final Entry<V> removeEntry(final int n) {
        final int slotIndex = getSlotIndex(computeHash(n), this.slots.length);
        Entry<V> entry2;
        Entry<V> entry = entry2 = this.slots[slotIndex];
        "".length();
        if (3 <= 1) {
            throw null;
        }
        while (entry2 != null) {
            final Entry<V> nextEntry = entry2.nextEntry;
            if (entry2.hashEntry == n) {
                this.count -= " ".length();
                if (entry == entry2) {
                    this.slots[slotIndex] = nextEntry;
                    "".length();
                    if (3 == 4) {
                        throw null;
                    }
                }
                else {
                    entry.nextEntry = nextEntry;
                }
                return entry2;
            }
            entry = entry2;
            entry2 = nextEntry;
        }
        return entry2;
    }
    
    private static int computeHash(int n) {
        n = (n ^ n >>> (0x9D ^ 0x89) ^ n >>> (0x38 ^ 0x34));
        return n ^ n >>> (0xBF ^ 0xB8) ^ n >>> (0x39 ^ 0x3D);
    }
    
    static int access$0(final int n) {
        return computeHash(n);
    }
    
    static class Entry<V>
    {
        final int slotHash;
        Entry<V> nextEntry;
        private static final String[] I;
        final int hashEntry;
        V valueEntry;
        
        public final V getValue() {
            return this.valueEntry;
        }
        
        @Override
        public final String toString() {
            return String.valueOf(this.getHash()) + Entry.I["".length()] + this.getValue();
        }
        
        @Override
        public final int hashCode() {
            return IntHashMap.access$0(this.hashEntry);
        }
        
        Entry(final int slotHash, final int hashEntry, final V valueEntry, final Entry<V> nextEntry) {
            this.valueEntry = valueEntry;
            this.nextEntry = nextEntry;
            this.hashEntry = hashEntry;
            this.slotHash = slotHash;
        }
        
        public final int getHash() {
            return this.hashEntry;
        }
        
        static {
            I();
        }
        
        private static void I() {
            (I = new String[" ".length()])["".length()] = I("o", "RaDpk");
        }
        
        @Override
        public final boolean equals(final Object o) {
            if (!(o instanceof Entry)) {
                return "".length() != 0;
            }
            final Entry entry = (Entry)o;
            final Integer value = this.getHash();
            final Integer value2 = entry.getHash();
            if (value == value2 || (value != null && value.equals(value2))) {
                final Object value3 = this.getValue();
                final Object value4 = entry.getValue();
                if (value3 == value4 || (value3 != null && value3.equals(value4))) {
                    return " ".length() != 0;
                }
            }
            return "".length() != 0;
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
                if (-1 >= 2) {
                    throw null;
                }
            }
            return sb.toString();
        }
    }
}
