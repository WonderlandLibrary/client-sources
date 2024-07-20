/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.chars.CharCollection;
import it.unimi.dsi.fastutil.chars.CharIterator;
import it.unimi.dsi.fastutil.chars.CharIterators;
import it.unimi.dsi.fastutil.objects.ObjectIterators;
import java.lang.reflect.Array;
import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Iterator;

public abstract class AbstractCharCollection
extends AbstractCollection<Character>
implements CharCollection {
    protected AbstractCharCollection() {
    }

    @Override
    public char[] toArray(char[] a) {
        return this.toCharArray(a);
    }

    @Override
    public char[] toCharArray() {
        return this.toCharArray(null);
    }

    @Override
    public char[] toCharArray(char[] a) {
        if (a == null || a.length < this.size()) {
            a = new char[this.size()];
        }
        CharIterators.unwrap(this.iterator(), a);
        return a;
    }

    @Override
    public boolean addAll(CharCollection c) {
        boolean retVal = false;
        CharIterator i = c.iterator();
        int n = c.size();
        while (n-- != 0) {
            if (!this.add(i.nextChar())) continue;
            retVal = true;
        }
        return retVal;
    }

    @Override
    public boolean containsAll(CharCollection c) {
        CharIterator i = c.iterator();
        int n = c.size();
        while (n-- != 0) {
            if (this.contains(i.nextChar())) continue;
            return false;
        }
        return true;
    }

    @Override
    public boolean retainAll(CharCollection c) {
        boolean retVal = false;
        int n = this.size();
        CharIterator i = this.iterator();
        while (n-- != 0) {
            if (c.contains(i.nextChar())) continue;
            i.remove();
            retVal = true;
        }
        return retVal;
    }

    @Override
    public boolean removeAll(CharCollection c) {
        boolean retVal = false;
        int n = c.size();
        CharIterator i = c.iterator();
        while (n-- != 0) {
            if (!this.rem(i.nextChar())) continue;
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
    public boolean addAll(Collection<? extends Character> c) {
        boolean retVal = false;
        Iterator<? extends Character> i = c.iterator();
        int n = c.size();
        while (n-- != 0) {
            if (!this.add(i.next())) continue;
            retVal = true;
        }
        return retVal;
    }

    @Override
    public boolean add(char k) {
        throw new UnsupportedOperationException();
    }

    @Override
    @Deprecated
    public CharIterator charIterator() {
        return this.iterator();
    }

    @Override
    public abstract CharIterator iterator();

    @Override
    public boolean remove(Object ok) {
        if (ok == null) {
            return false;
        }
        return this.rem(((Character)ok).charValue());
    }

    @Override
    public boolean add(Character o) {
        return this.add(o.charValue());
    }

    public boolean rem(Object o) {
        if (o == null) {
            return false;
        }
        return this.rem(((Character)o).charValue());
    }

    @Override
    public boolean contains(Object o) {
        if (o == null) {
            return false;
        }
        return this.contains(((Character)o).charValue());
    }

    @Override
    public boolean contains(char k) {
        CharIterator iterator = this.iterator();
        while (iterator.hasNext()) {
            if (k != iterator.nextChar()) continue;
            return true;
        }
        return false;
    }

    @Override
    public boolean rem(char k) {
        CharIterator iterator = this.iterator();
        while (iterator.hasNext()) {
            if (k != iterator.nextChar()) continue;
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
        CharIterator i = this.iterator();
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
        CharIterator i = this.iterator();
        int n = this.size();
        boolean first = true;
        s.append("{");
        while (n-- != 0) {
            if (first) {
                first = false;
            } else {
                s.append(", ");
            }
            char k = i.nextChar();
            s.append(String.valueOf(k));
        }
        s.append("}");
        return s.toString();
    }
}

