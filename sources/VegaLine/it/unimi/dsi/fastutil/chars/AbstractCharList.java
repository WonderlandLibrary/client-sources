/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.chars.AbstractCharCollection;
import it.unimi.dsi.fastutil.chars.AbstractCharListIterator;
import it.unimi.dsi.fastutil.chars.CharCollection;
import it.unimi.dsi.fastutil.chars.CharList;
import it.unimi.dsi.fastutil.chars.CharListIterator;
import it.unimi.dsi.fastutil.chars.CharStack;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public abstract class AbstractCharList
extends AbstractCharCollection
implements CharList,
CharStack {
    protected AbstractCharList() {
    }

    protected void ensureIndex(int index) {
        if (index < 0) {
            throw new IndexOutOfBoundsException("Index (" + index + ") is negative");
        }
        if (index > this.size()) {
            throw new IndexOutOfBoundsException("Index (" + index + ") is greater than list size (" + this.size() + ")");
        }
    }

    protected void ensureRestrictedIndex(int index) {
        if (index < 0) {
            throw new IndexOutOfBoundsException("Index (" + index + ") is negative");
        }
        if (index >= this.size()) {
            throw new IndexOutOfBoundsException("Index (" + index + ") is greater than or equal to list size (" + this.size() + ")");
        }
    }

    @Override
    public void add(int index, char k) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean add(char k) {
        this.add(this.size(), k);
        return true;
    }

    @Override
    public char removeChar(int i) {
        throw new UnsupportedOperationException();
    }

    @Override
    public char set(int index, char k) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(int index, Collection<? extends Character> c) {
        this.ensureIndex(index);
        int n = c.size();
        if (n == 0) {
            return false;
        }
        Iterator<? extends Character> i = c.iterator();
        while (n-- != 0) {
            this.add(index++, i.next());
        }
        return true;
    }

    @Override
    public boolean addAll(Collection<? extends Character> c) {
        return this.addAll(this.size(), c);
    }

    @Override
    @Deprecated
    public CharListIterator charListIterator() {
        return this.listIterator();
    }

    @Override
    @Deprecated
    public CharListIterator charListIterator(int index) {
        return this.listIterator(index);
    }

    @Override
    public CharListIterator iterator() {
        return this.listIterator();
    }

    @Override
    public CharListIterator listIterator() {
        return this.listIterator(0);
    }

    @Override
    public CharListIterator listIterator(final int index) {
        this.ensureIndex(index);
        return new AbstractCharListIterator(){
            int pos;
            int last;
            {
                this.pos = index;
                this.last = -1;
            }

            @Override
            public boolean hasNext() {
                return this.pos < AbstractCharList.this.size();
            }

            @Override
            public boolean hasPrevious() {
                return this.pos > 0;
            }

            @Override
            public char nextChar() {
                if (!this.hasNext()) {
                    throw new NoSuchElementException();
                }
                this.last = this.pos++;
                return AbstractCharList.this.getChar(this.last);
            }

            @Override
            public char previousChar() {
                if (!this.hasPrevious()) {
                    throw new NoSuchElementException();
                }
                this.last = --this.pos;
                return AbstractCharList.this.getChar(this.pos);
            }

            @Override
            public int nextIndex() {
                return this.pos;
            }

            @Override
            public int previousIndex() {
                return this.pos - 1;
            }

            @Override
            public void add(char k) {
                AbstractCharList.this.add(this.pos++, k);
                this.last = -1;
            }

            @Override
            public void set(char k) {
                if (this.last == -1) {
                    throw new IllegalStateException();
                }
                AbstractCharList.this.set(this.last, k);
            }

            @Override
            public void remove() {
                if (this.last == -1) {
                    throw new IllegalStateException();
                }
                AbstractCharList.this.removeChar(this.last);
                if (this.last < this.pos) {
                    --this.pos;
                }
                this.last = -1;
            }
        };
    }

    @Override
    public boolean contains(char k) {
        return this.indexOf(k) >= 0;
    }

    @Override
    public int indexOf(char k) {
        CharListIterator i = this.listIterator();
        while (i.hasNext()) {
            char e = i.nextChar();
            if (k != e) continue;
            return i.previousIndex();
        }
        return -1;
    }

    @Override
    public int lastIndexOf(char k) {
        CharListIterator i = this.listIterator(this.size());
        while (i.hasPrevious()) {
            char e = i.previousChar();
            if (k != e) continue;
            return i.nextIndex();
        }
        return -1;
    }

    @Override
    public void size(int size) {
        int i = this.size();
        if (size > i) {
            while (i++ < size) {
                this.add('\u0000');
            }
        } else {
            while (i-- != size) {
                this.remove(i);
            }
        }
    }

    @Override
    public CharList subList(int from, int to) {
        this.ensureIndex(from);
        this.ensureIndex(to);
        if (from > to) {
            throw new IndexOutOfBoundsException("Start index (" + from + ") is greater than end index (" + to + ")");
        }
        return new CharSubList(this, from, to);
    }

    @Override
    @Deprecated
    public CharList charSubList(int from, int to) {
        return this.subList(from, to);
    }

    @Override
    public void removeElements(int from, int to) {
        this.ensureIndex(to);
        CharListIterator i = this.listIterator(from);
        int n = to - from;
        if (n < 0) {
            throw new IllegalArgumentException("Start index (" + from + ") is greater than end index (" + to + ")");
        }
        while (n-- != 0) {
            i.nextChar();
            i.remove();
        }
    }

    @Override
    public void addElements(int index, char[] a, int offset, int length) {
        this.ensureIndex(index);
        if (offset < 0) {
            throw new ArrayIndexOutOfBoundsException("Offset (" + offset + ") is negative");
        }
        if (offset + length > a.length) {
            throw new ArrayIndexOutOfBoundsException("End index (" + (offset + length) + ") is greater than array length (" + a.length + ")");
        }
        while (length-- != 0) {
            this.add(index++, a[offset++]);
        }
    }

    @Override
    public void addElements(int index, char[] a) {
        this.addElements(index, a, 0, a.length);
    }

    @Override
    public void getElements(int from, char[] a, int offset, int length) {
        CharListIterator i = this.listIterator(from);
        if (offset < 0) {
            throw new ArrayIndexOutOfBoundsException("Offset (" + offset + ") is negative");
        }
        if (offset + length > a.length) {
            throw new ArrayIndexOutOfBoundsException("End index (" + (offset + length) + ") is greater than array length (" + a.length + ")");
        }
        if (from + length > this.size()) {
            throw new IndexOutOfBoundsException("End index (" + (from + length) + ") is greater than list size (" + this.size() + ")");
        }
        while (length-- != 0) {
            a[offset++] = i.nextChar();
        }
    }

    private boolean valEquals(Object a, Object b) {
        return a == null ? b == null : a.equals(b);
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof List)) {
            return false;
        }
        List l = (List)o;
        int s = this.size();
        if (s != l.size()) {
            return false;
        }
        if (l instanceof CharList) {
            CharListIterator i1 = this.listIterator();
            CharListIterator i2 = ((CharList)l).listIterator();
            while (s-- != 0) {
                if (i1.nextChar() == i2.nextChar()) continue;
                return false;
            }
            return true;
        }
        CharListIterator i1 = this.listIterator();
        ListIterator i2 = l.listIterator();
        while (s-- != 0) {
            if (this.valEquals(i1.next(), i2.next())) continue;
            return false;
        }
        return true;
    }

    @Override
    public int compareTo(List<? extends Character> l) {
        if (l == this) {
            return 0;
        }
        if (l instanceof CharList) {
            CharListIterator i1 = this.listIterator();
            CharListIterator i2 = ((CharList)l).listIterator();
            while (i1.hasNext() && i2.hasNext()) {
                char e2;
                char e1 = i1.nextChar();
                int r = Character.compare(e1, e2 = i2.nextChar());
                if (r == 0) continue;
                return r;
            }
            return i2.hasNext() ? -1 : (i1.hasNext() ? 1 : 0);
        }
        CharListIterator i1 = this.listIterator();
        ListIterator<? extends Character> i2 = l.listIterator();
        while (i1.hasNext() && i2.hasNext()) {
            int r = ((Comparable)i1.next()).compareTo(i2.next());
            if (r == 0) continue;
            return r;
        }
        return i2.hasNext() ? -1 : (i1.hasNext() ? 1 : 0);
    }

    @Override
    public int hashCode() {
        CharListIterator i = this.iterator();
        int h = 1;
        int s = this.size();
        while (s-- != 0) {
            char k = i.nextChar();
            h = 31 * h + k;
        }
        return h;
    }

    @Override
    public void push(char o) {
        this.add(o);
    }

    @Override
    public char popChar() {
        if (this.isEmpty()) {
            throw new NoSuchElementException();
        }
        return this.removeChar(this.size() - 1);
    }

    @Override
    public char topChar() {
        if (this.isEmpty()) {
            throw new NoSuchElementException();
        }
        return this.getChar(this.size() - 1);
    }

    @Override
    public char peekChar(int i) {
        return this.getChar(this.size() - 1 - i);
    }

    @Override
    public boolean rem(char k) {
        int index = this.indexOf(k);
        if (index == -1) {
            return false;
        }
        this.removeChar(index);
        return true;
    }

    @Override
    public boolean remove(Object o) {
        return this.rem(((Character)o).charValue());
    }

    @Override
    public boolean addAll(int index, CharCollection c) {
        return this.addAll(index, (Collection<? extends Character>)c);
    }

    @Override
    public boolean addAll(int index, CharList l) {
        return this.addAll(index, (CharCollection)l);
    }

    @Override
    public boolean addAll(CharCollection c) {
        return this.addAll(this.size(), c);
    }

    @Override
    public boolean addAll(CharList l) {
        return this.addAll(this.size(), l);
    }

    @Override
    public void add(int index, Character ok) {
        this.add(index, ok.charValue());
    }

    @Override
    @Deprecated
    public Character set(int index, Character ok) {
        return Character.valueOf(this.set(index, ok.charValue()));
    }

    @Override
    @Deprecated
    public Character get(int index) {
        return Character.valueOf(this.getChar(index));
    }

    @Override
    public int indexOf(Object ok) {
        return this.indexOf(((Character)ok).charValue());
    }

    @Override
    public int lastIndexOf(Object ok) {
        return this.lastIndexOf(((Character)ok).charValue());
    }

    @Override
    @Deprecated
    public Character remove(int index) {
        return Character.valueOf(this.removeChar(index));
    }

    @Override
    public void push(Character o) {
        this.push(o.charValue());
    }

    @Override
    @Deprecated
    public Character pop() {
        return Character.valueOf(this.popChar());
    }

    @Override
    @Deprecated
    public Character top() {
        return Character.valueOf(this.topChar());
    }

    @Override
    @Deprecated
    public Character peek(int i) {
        return Character.valueOf(this.peekChar(i));
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        CharListIterator i = this.iterator();
        int n = this.size();
        boolean first = true;
        s.append("[");
        while (n-- != 0) {
            if (first) {
                first = false;
            } else {
                s.append(", ");
            }
            char k = i.nextChar();
            s.append(String.valueOf(k));
        }
        s.append("]");
        return s.toString();
    }

    public static class CharSubList
    extends AbstractCharList
    implements Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final CharList l;
        protected final int from;
        protected int to;
        private static final boolean ASSERTS = false;

        public CharSubList(CharList l, int from, int to) {
            this.l = l;
            this.from = from;
            this.to = to;
        }

        private void assertRange() {
        }

        @Override
        public boolean add(char k) {
            this.l.add(this.to, k);
            ++this.to;
            return true;
        }

        @Override
        public void add(int index, char k) {
            this.ensureIndex(index);
            this.l.add(this.from + index, k);
            ++this.to;
        }

        @Override
        public boolean addAll(int index, Collection<? extends Character> c) {
            this.ensureIndex(index);
            this.to += c.size();
            return this.l.addAll(this.from + index, c);
        }

        @Override
        public char getChar(int index) {
            this.ensureRestrictedIndex(index);
            return this.l.getChar(this.from + index);
        }

        @Override
        public char removeChar(int index) {
            this.ensureRestrictedIndex(index);
            --this.to;
            return this.l.removeChar(this.from + index);
        }

        @Override
        public char set(int index, char k) {
            this.ensureRestrictedIndex(index);
            return this.l.set(this.from + index, k);
        }

        @Override
        public void clear() {
            this.removeElements(0, this.size());
        }

        @Override
        public int size() {
            return this.to - this.from;
        }

        @Override
        public void getElements(int from, char[] a, int offset, int length) {
            this.ensureIndex(from);
            if (from + length > this.size()) {
                throw new IndexOutOfBoundsException("End index (" + from + length + ") is greater than list size (" + this.size() + ")");
            }
            this.l.getElements(this.from + from, a, offset, length);
        }

        @Override
        public void removeElements(int from, int to) {
            this.ensureIndex(from);
            this.ensureIndex(to);
            this.l.removeElements(this.from + from, this.from + to);
            this.to -= to - from;
        }

        @Override
        public void addElements(int index, char[] a, int offset, int length) {
            this.ensureIndex(index);
            this.l.addElements(this.from + index, a, offset, length);
            this.to += length;
        }

        @Override
        public CharListIterator listIterator(final int index) {
            this.ensureIndex(index);
            return new AbstractCharListIterator(){
                int pos;
                int last;
                {
                    this.pos = index;
                    this.last = -1;
                }

                @Override
                public boolean hasNext() {
                    return this.pos < CharSubList.this.size();
                }

                @Override
                public boolean hasPrevious() {
                    return this.pos > 0;
                }

                @Override
                public char nextChar() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    this.last = this.pos++;
                    return CharSubList.this.l.getChar(CharSubList.this.from + this.last);
                }

                @Override
                public char previousChar() {
                    if (!this.hasPrevious()) {
                        throw new NoSuchElementException();
                    }
                    this.last = --this.pos;
                    return CharSubList.this.l.getChar(CharSubList.this.from + this.pos);
                }

                @Override
                public int nextIndex() {
                    return this.pos;
                }

                @Override
                public int previousIndex() {
                    return this.pos - 1;
                }

                @Override
                public void add(char k) {
                    if (this.last == -1) {
                        throw new IllegalStateException();
                    }
                    CharSubList.this.add(this.pos++, k);
                    this.last = -1;
                }

                @Override
                public void set(char k) {
                    if (this.last == -1) {
                        throw new IllegalStateException();
                    }
                    CharSubList.this.set(this.last, k);
                }

                @Override
                public void remove() {
                    if (this.last == -1) {
                        throw new IllegalStateException();
                    }
                    CharSubList.this.removeChar(this.last);
                    if (this.last < this.pos) {
                        --this.pos;
                    }
                    this.last = -1;
                }
            };
        }

        @Override
        public CharList subList(int from, int to) {
            this.ensureIndex(from);
            this.ensureIndex(to);
            if (from > to) {
                throw new IllegalArgumentException("Start index (" + from + ") is greater than end index (" + to + ")");
            }
            return new CharSubList(this, from, to);
        }

        @Override
        public boolean rem(char k) {
            int index = this.indexOf(k);
            if (index == -1) {
                return false;
            }
            --this.to;
            this.l.removeChar(this.from + index);
            return true;
        }

        @Override
        public boolean remove(Object o) {
            return this.rem(((Character)o).charValue());
        }

        @Override
        public boolean addAll(int index, CharCollection c) {
            this.ensureIndex(index);
            this.to += c.size();
            return this.l.addAll(this.from + index, c);
        }

        @Override
        public boolean addAll(int index, CharList l) {
            this.ensureIndex(index);
            this.to += l.size();
            return this.l.addAll(this.from + index, l);
        }
    }
}

