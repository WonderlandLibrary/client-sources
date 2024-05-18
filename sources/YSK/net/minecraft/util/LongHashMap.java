package net.minecraft.util;

public class LongHashMap
{
    private final float percentUseable = 0.75f;
    private int mask;
    private transient int modCount;
    private int capacity;
    private transient int numHashElements;
    private static final String __OBFID;
    private static final String[] I;
    private transient Entry[] hashArray;
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I(")\u001e8Z]ZbV^TX", "jRgjm");
    }
    
    private static int hash(int n) {
        n = (n ^ n >>> (0x54 ^ 0x40) ^ n >>> (0x61 ^ 0x6D));
        return n ^ n >>> (0x24 ^ 0x23) ^ n >>> (0x3A ^ 0x3E);
    }
    
    final Entry removeKey(final long n) {
        final int hashIndex = getHashIndex(getHashedKey(n), this.mask);
        Entry entry2;
        Entry entry = entry2 = this.hashArray[hashIndex];
        "".length();
        if (4 <= 3) {
            throw null;
        }
        while (entry2 != null) {
            final Entry nextEntry = entry2.nextEntry;
            if (entry2.key == n) {
                this.modCount += " ".length();
                this.numHashElements -= " ".length();
                if (entry == entry2) {
                    this.hashArray[hashIndex] = nextEntry;
                    "".length();
                    if (4 < 0) {
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
    
    private void copyHashTableTo(final Entry[] array) {
        final Entry[] hashArray = this.hashArray;
        final int length = array.length;
        int i = "".length();
        "".length();
        if (false == true) {
            throw null;
        }
        while (i < hashArray.length) {
            Entry entry = hashArray[i];
            if (entry != null) {
                hashArray[i] = null;
                Entry nextEntry;
                do {
                    nextEntry = entry.nextEntry;
                    final int hashIndex = getHashIndex(entry.hash, length - " ".length());
                    entry.nextEntry = array[hashIndex];
                    array[hashIndex] = entry;
                } while ((entry = nextEntry) != null);
            }
            ++i;
        }
    }
    
    public boolean containsItem(final long n) {
        if (this.getEntry(n) != null) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public void add(final long n, final Object value) {
        final int hashedKey = getHashedKey(n);
        final int hashIndex = getHashIndex(hashedKey, this.mask);
        Entry nextEntry = this.hashArray[hashIndex];
        "".length();
        if (-1 < -1) {
            throw null;
        }
        while (nextEntry != null) {
            if (nextEntry.key == n) {
                nextEntry.value = value;
                return;
            }
            nextEntry = nextEntry.nextEntry;
        }
        this.modCount += " ".length();
        this.createKey(hashedKey, n, value, hashIndex);
    }
    
    public Object remove(final long n) {
        final Entry removeKey = this.removeKey(n);
        Object value;
        if (removeKey == null) {
            value = null;
            "".length();
            if (true != true) {
                throw null;
            }
        }
        else {
            value = removeKey.value;
        }
        return value;
    }
    
    public Object getValueByKey(final long n) {
        Entry nextEntry = this.hashArray[getHashIndex(getHashedKey(n), this.mask)];
        "".length();
        if (1 == 2) {
            throw null;
        }
        while (nextEntry != null) {
            if (nextEntry.key == n) {
                return nextEntry.value;
            }
            nextEntry = nextEntry.nextEntry;
        }
        return null;
    }
    
    private static int getHashIndex(final int n, final int n2) {
        return n & n2;
    }
    
    final Entry getEntry(final long n) {
        Entry nextEntry = this.hashArray[getHashIndex(getHashedKey(n), this.mask)];
        "".length();
        if (1 == 4) {
            throw null;
        }
        while (nextEntry != null) {
            if (nextEntry.key == n) {
                return nextEntry;
            }
            nextEntry = nextEntry.nextEntry;
        }
        return null;
    }
    
    private static int getHashedKey(final long n) {
        return (int)(n ^ n >>> (0x57 ^ 0x4C));
    }
    
    private void resizeTable(final int n) {
        if (this.hashArray.length == 268548133 + 255341582 + 18712160 + 531139949) {
            this.capacity = 951770728 + 1411152093 - 1233047924 + 1017608750;
            "".length();
            if (3 >= 4) {
                throw null;
            }
        }
        else {
            final Entry[] hashArray = new Entry[n];
            this.copyHashTableTo(hashArray);
            this.hashArray = hashArray;
            this.mask = this.hashArray.length - " ".length();
            final float n2 = n;
            this.getClass();
            this.capacity = (int)(n2 * 0.75f);
        }
    }
    
    public double getKeyDistribution() {
        int length = "".length();
        int i = "".length();
        "".length();
        if (4 != 4) {
            throw null;
        }
        while (i < this.hashArray.length) {
            if (this.hashArray[i] != null) {
                ++length;
            }
            ++i;
        }
        return 1.0 * length / this.numHashElements;
    }
    
    public LongHashMap() {
        this.hashArray = new Entry[2215 + 1181 - 2498 + 3198];
        this.capacity = 2983 + 1974 - 2451 + 566;
        this.mask = this.hashArray.length - " ".length();
    }
    
    public int getNumHashElements() {
        return this.numHashElements;
    }
    
    static {
        I();
        __OBFID = LongHashMap.I["".length()];
    }
    
    static int access$0(final long n) {
        return getHashedKey(n);
    }
    
    private void createKey(final int n, final long n2, final Object o, final int n3) {
        this.hashArray[n3] = new Entry(n, n2, o, this.hashArray[n3]);
        final int numHashElements = this.numHashElements;
        this.numHashElements = numHashElements + " ".length();
        if (numHashElements >= this.capacity) {
            this.resizeTable("  ".length() * this.hashArray.length);
        }
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
            if (3 < 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    static class Entry
    {
        private static final String[] I;
        Object value;
        Entry nextEntry;
        final int hash;
        private static final String __OBFID;
        final long key;
        
        @Override
        public final int hashCode() {
            return LongHashMap.access$0(this.key);
        }
        
        public final Object getValue() {
            return this.value;
        }
        
        @Override
        public final boolean equals(final Object o) {
            if (!(o instanceof Entry)) {
                return "".length() != 0;
            }
            final Entry entry = (Entry)o;
            final Long value = this.getKey();
            final Long value2 = entry.getKey();
            if (value == value2 || (value != null && value.equals(value2))) {
                final Object value3 = this.getValue();
                final Object value4 = entry.getValue();
                if (value3 == value4 || (value3 != null && value3.equals(value4))) {
                    return " ".length() != 0;
                }
            }
            return "".length() != 0;
        }
        
        private static void I() {
            (I = new String["  ".length()])["".length()] = I("G", "zuaNl");
            Entry.I[" ".length()] = I("\u0005\u0019>udvePqmu", "FUaET");
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
                if (0 >= 4) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        Entry(final int hash, final long key, final Object value, final Entry nextEntry) {
            this.value = value;
            this.nextEntry = nextEntry;
            this.key = key;
            this.hash = hash;
        }
        
        @Override
        public final String toString() {
            return String.valueOf(this.getKey()) + Entry.I["".length()] + this.getValue();
        }
        
        public final long getKey() {
            return this.key;
        }
        
        static {
            I();
            __OBFID = Entry.I[" ".length()];
        }
    }
}
