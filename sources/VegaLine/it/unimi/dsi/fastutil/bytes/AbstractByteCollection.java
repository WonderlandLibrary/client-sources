/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.bytes;

import it.unimi.dsi.fastutil.bytes.ByteCollection;
import it.unimi.dsi.fastutil.bytes.ByteIterator;
import it.unimi.dsi.fastutil.bytes.ByteIterators;
import it.unimi.dsi.fastutil.objects.ObjectIterators;
import java.lang.reflect.Array;
import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Iterator;

public abstract class AbstractByteCollection
extends AbstractCollection<Byte>
implements ByteCollection {
    protected AbstractByteCollection() {
    }

    @Override
    public byte[] toArray(byte[] a) {
        return this.toByteArray(a);
    }

    @Override
    public byte[] toByteArray() {
        return this.toByteArray(null);
    }

    @Override
    public byte[] toByteArray(byte[] a) {
        if (a == null || a.length < this.size()) {
            a = new byte[this.size()];
        }
        ByteIterators.unwrap(this.iterator(), a);
        return a;
    }

    @Override
    public boolean addAll(ByteCollection c) {
        boolean retVal = false;
        ByteIterator i = c.iterator();
        int n = c.size();
        while (n-- != 0) {
            if (!this.add(i.nextByte())) continue;
            retVal = true;
        }
        return retVal;
    }

    @Override
    public boolean containsAll(ByteCollection c) {
        ByteIterator i = c.iterator();
        int n = c.size();
        while (n-- != 0) {
            if (this.contains(i.nextByte())) continue;
            return false;
        }
        return true;
    }

    @Override
    public boolean retainAll(ByteCollection c) {
        boolean retVal = false;
        int n = this.size();
        ByteIterator i = this.iterator();
        while (n-- != 0) {
            if (c.contains(i.nextByte())) continue;
            i.remove();
            retVal = true;
        }
        return retVal;
    }

    @Override
    public boolean removeAll(ByteCollection c) {
        boolean retVal = false;
        int n = c.size();
        ByteIterator i = c.iterator();
        while (n-- != 0) {
            if (!this.rem(i.nextByte())) continue;
            retVal = true;
        }
        return retVal;
    }

    @Override
    public Object[] toArray() {
        Object[] a = new Object[this.size()];
        ObjectIterators.unwrap(this.iterator(), a);
        return a;
    }

    @Override
    public <T> T[] toArray(T[] a) {
        int size = this.size();
        if (a.length < size) {
            a = (Object[])Array.newInstance(a.getClass().getComponentType(), size);
        }
        ObjectIterators.unwrap(this.iterator(), a);
        if (size < a.length) {
            a[size] = null;
        }
        return a;
    }

    @Override
    public boolean addAll(Collection<? extends Byte> c) {
        boolean retVal = false;
        Iterator<? extends Byte> i = c.iterator();
        int n = c.size();
        while (n-- != 0) {
            if (!this.add(i.next())) continue;
            retVal = true;
        }
        return retVal;
    }

    @Override
    public boolean add(byte k) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Deprecated
    public ByteIterator byteIterator() {
        return this.iterator();
    }

    @Override
    public abstract ByteIterator iterator();

    @Override
    public boolean remove(Object ok) {
        if (ok == null) {
            return false;
        }
        return this.rem((Byte)ok);
    }

    @Override
    public boolean add(Byte o) {
        return this.add((byte)o);
    }

    public boolean rem(Object o) {
        if (o == null) {
            return false;
        }
        return this.rem((Byte)o);
    }

    @Override
    public boolean contains(Object o) {
        if (o == null) {
            return false;
        }
        return this.contains((Byte)o);
    }

    @Override
    public boolean contains(byte k) {
        ByteIterator iterator = this.iterator();
        while (iterator.hasNext()) {
            if (k != iterator.nextByte()) continue;
            return true;
        }
        return false;
    }

    @Override
    public boolean rem(byte k) {
        ByteIterator iterator = this.iterator();
        while (iterator.hasNext()) {
            if (k != iterator.nextByte()) continue;
            iterator.remove();
            return true;
        }
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        int n = c.size();
        Iterator<?> i = c.iterator();
        while (n-- != 0) {
            if (this.contains(i.next())) continue;
            return false;
        }
        return true;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        boolean retVal = false;
        int n = this.size();
        ByteIterator i = this.iterator();
        while (n-- != 0) {
            if (c.contains(i.next())) continue;
            i.remove();
            retVal = true;
        }
        return retVal;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        boolean retVal = false;
        int n = c.size();
        Iterator<?> i = c.iterator();
        while (n-- != 0) {
            if (!this.remove(i.next())) continue;
            retVal = true;
        }
        return retVal;
    }

    @Override
    public boolean isEmpty() {
        return this.size() == 0;
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        ByteIterator i = this.iterator();
        int n = this.size();
        boolean first = true;
        s.append("{");
        while (n-- != 0) {
            if (first) {
                first = false;
            } else {
                s.append(", ");
            }
            byte k = i.nextByte();
            s.append(String.valueOf(k));
        }
        s.append("}");
        return s.toString();
    }
}

