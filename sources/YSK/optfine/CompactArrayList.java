package optfine;

import java.util.*;

public class CompactArrayList
{
    private float loadFactor;
    private ArrayList list;
    private int countValid;
    private int initialCapacity;
    
    public void add(final int n, final Object o) {
        if (o != null) {
            this.countValid += " ".length();
        }
        this.list.add(n, o);
    }
    
    public int size() {
        return this.list.size();
    }
    
    public boolean add(final Object o) {
        if (o != null) {
            this.countValid += " ".length();
        }
        return this.list.add(o);
    }
    
    public boolean isEmpty() {
        return this.list.isEmpty();
    }
    
    public boolean contains(final Object o) {
        return this.list.contains(o);
    }
    
    public void clear() {
        this.list.clear();
        this.countValid = "".length();
    }
    
    public Object get(final int n) {
        return this.list.get(n);
    }
    
    public CompactArrayList(final int initialCapacity, final float loadFactor) {
        this.list = null;
        this.initialCapacity = "".length();
        this.loadFactor = 1.0f;
        this.countValid = "".length();
        this.list = new ArrayList(initialCapacity);
        this.initialCapacity = initialCapacity;
        this.loadFactor = loadFactor;
    }
    
    public Object remove(final int n) {
        final Object remove = this.list.remove(n);
        if (remove != null) {
            this.countValid -= " ".length();
        }
        return remove;
    }
    
    public CompactArrayList(final int n) {
        this(n, 0.75f);
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
            if (!true) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public void compact() {
        if (this.countValid <= 0 && this.list.size() <= 0) {
            this.clear();
            "".length();
            if (4 == 3) {
                throw null;
            }
        }
        else if (this.list.size() > this.initialCapacity && this.countValid * 1.0f / this.list.size() <= this.loadFactor) {
            int length = "".length();
            int i = "".length();
            "".length();
            if (false) {
                throw null;
            }
            while (i < this.list.size()) {
                final Object value = this.list.get(i);
                if (value != null) {
                    if (i != length) {
                        this.list.set(length, value);
                    }
                    ++length;
                }
                ++i;
            }
            int j = this.list.size() - " ".length();
            "".length();
            if (true != true) {
                throw null;
            }
            while (j >= length) {
                this.list.remove(j);
                --j;
            }
        }
    }
    
    public CompactArrayList() {
        this(0x30 ^ 0x3A, 0.75f);
    }
    
    public Object set(final int n, final Object o) {
        final Object set = this.list.set(n, o);
        if (o != set) {
            if (set == null) {
                this.countValid += " ".length();
            }
            if (o == null) {
                this.countValid -= " ".length();
            }
        }
        return set;
    }
    
    public int getCountValid() {
        return this.countValid;
    }
}
