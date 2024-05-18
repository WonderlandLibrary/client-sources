package net.minecraft.util;

import java.lang.reflect.*;
import java.util.concurrent.locks.*;

public class ThreadSafeBoundList<T>
{
    private int field_152762_d;
    private int field_152763_e;
    private final T[] field_152759_a;
    private final ReadWriteLock field_152761_c;
    private final Class<? extends T> field_152760_b;
    
    public T[] func_152756_c() {
        final Object[] array = (Object[])Array.newInstance(this.field_152760_b, this.field_152762_d);
        this.field_152761_c.readLock().lock();
        int i = "".length();
        "".length();
        if (1 < 0) {
            throw null;
        }
        while (i < this.field_152762_d) {
            int n = (this.field_152763_e - this.field_152762_d + i) % this.func_152758_b();
            if (n < 0) {
                n += this.func_152758_b();
            }
            array[i] = this.field_152759_a[n];
            ++i;
        }
        this.field_152761_c.readLock().unlock();
        return (T[])array;
    }
    
    public int func_152758_b() {
        this.field_152761_c.readLock().lock();
        final int length = this.field_152759_a.length;
        this.field_152761_c.readLock().unlock();
        return length;
    }
    
    public T func_152757_a(final T t) {
        this.field_152761_c.writeLock().lock();
        this.field_152759_a[this.field_152763_e] = t;
        this.field_152763_e = (this.field_152763_e + " ".length()) % this.func_152758_b();
        if (this.field_152762_d < this.func_152758_b()) {
            this.field_152762_d += " ".length();
        }
        this.field_152761_c.writeLock().unlock();
        return t;
    }
    
    public ThreadSafeBoundList(final Class<? extends T> field_152760_b, final int n) {
        this.field_152761_c = new ReentrantReadWriteLock();
        this.field_152760_b = field_152760_b;
        this.field_152759_a = (Object[])Array.newInstance(field_152760_b, n);
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
            if (3 < 3) {
                throw null;
            }
        }
        return sb.toString();
    }
}
