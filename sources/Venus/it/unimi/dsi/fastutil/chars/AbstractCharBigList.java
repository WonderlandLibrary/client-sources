/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.BigList;
import it.unimi.dsi.fastutil.BigListIterator;
import it.unimi.dsi.fastutil.chars.AbstractCharCollection;
import it.unimi.dsi.fastutil.chars.CharBigArrays;
import it.unimi.dsi.fastutil.chars.CharBigList;
import it.unimi.dsi.fastutil.chars.CharBigListIterator;
import it.unimi.dsi.fastutil.chars.CharCollection;
import it.unimi.dsi.fastutil.chars.CharIterator;
import it.unimi.dsi.fastutil.chars.CharStack;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public abstract class AbstractCharBigList
extends AbstractCharCollection
implements CharBigList,
CharStack {
    protected AbstractCharBigList() {
    }

    protected void ensureIndex(long l) {
        if (l < 0L) {
            throw new IndexOutOfBoundsException("Index (" + l + ") is negative");
        }
        if (l > this.size64()) {
            throw new IndexOutOfBoundsException("Index (" + l + ") is greater than list size (" + this.size64() + ")");
        }
    }

    protected void ensureRestrictedIndex(long l) {
        if (l < 0L) {
            throw new IndexOutOfBoundsException("Index (" + l + ") is negative");
        }
        if (l >= this.size64()) {
            throw new IndexOutOfBoundsException("Index (" + l + ") is greater than or equal to list size (" + this.size64() + ")");
        }
    }

    @Override
    public void add(long l, char c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean add(char c) {
        this.add(this.size64(), c);
        return false;
    }

    @Override
    public char removeChar(long l) {
        throw new UnsupportedOperationException();
    }

    @Override
    public char set(long l, char c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(long l, Collection<? extends Character> collection) {
        this.ensureIndex(l);
        Iterator<? extends Character> iterator2 = collection.iterator();
        boolean bl = iterator2.hasNext();
        while (iterator2.hasNext()) {
            this.add(l++, iterator2.next());
        }
        return bl;
    }

    @Override
    public boolean addAll(Collection<? extends Character> collection) {
        return this.addAll(this.size64(), collection);
    }

    @Override
    public CharBigListIterator iterator() {
        return this.listIterator();
    }

    @Override
    public CharBigListIterator listIterator() {
        return this.listIterator(0L);
    }

    @Override
    public CharBigListIterator listIterator(long l) {
        this.ensureIndex(l);
        return new CharBigListIterator(this, l){
            long pos;
            long last;
            final long val$index;
            final AbstractCharBigList this$0;
            {
                this.this$0 = abstractCharBigList;
                this.val$index = l;
                this.pos = this.val$index;
                this.last = -1L;
            }

            @Override
            public boolean hasNext() {
                return this.pos < this.this$0.size64();
            }

            @Override
            public boolean hasPrevious() {
                return this.pos > 0L;
            }

            @Override
            public char nextChar() {
                if (!this.hasNext()) {
                    throw new NoSuchElementException();
                }
                this.last = this.pos++;
                return this.this$0.getChar(this.last);
            }

            @Override
            public char previousChar() {
                if (!this.hasPrevious()) {
                    throw new NoSuchElementException();
                }
                this.last = --this.pos;
                return this.this$0.getChar(this.pos);
            }

            @Override
            public long nextIndex() {
                return this.pos;
            }

            @Override
            public long previousIndex() {
                return this.pos - 1L;
            }

            @Override
            public void add(char c) {
                this.this$0.add(this.pos++, c);
                this.last = -1L;
            }

            @Override
            public void set(char c) {
                if (this.last == -1L) {
                    throw new IllegalStateException();
                }
                this.this$0.set(this.last, c);
            }

            @Override
            public void remove() {
                if (this.last == -1L) {
                    throw new IllegalStateException();
                }
                this.this$0.removeChar(this.last);
                if (this.last < this.pos) {
                    --this.pos;
                }
                this.last = -1L;
            }
        };
    }

    @Override
    public boolean contains(char c) {
        return this.indexOf(c) >= 0L;
    }

    @Override
    public long indexOf(char c) {
        CharBigListIterator charBigListIterator = this.listIterator();
        while (charBigListIterator.hasNext()) {
            char c2 = charBigListIterator.nextChar();
            if (c != c2) continue;
            return charBigListIterator.previousIndex();
        }
        return -1L;
    }

    @Override
    public long lastIndexOf(char c) {
        CharBigListIterator charBigListIterator = this.listIterator(this.size64());
        while (charBigListIterator.hasPrevious()) {
            char c2 = charBigListIterator.previousChar();
            if (c != c2) continue;
            return charBigListIterator.nextIndex();
        }
        return -1L;
    }

    @Override
    public void size(long l) {
        long l2 = this.size64();
        if (l > l2) {
            while (l2++ < l) {
                this.add('\u0001');
            }
        } else {
            while (l2-- != l) {
                this.remove(l2);
            }
        }
    }

    @Override
    public CharBigList subList(long l, long l2) {
        this.ensureIndex(l);
        this.ensureIndex(l2);
        if (l > l2) {
            throw new IndexOutOfBoundsException("Start index (" + l + ") is greater than end index (" + l2 + ")");
        }
        return new CharSubList(this, l, l2);
    }

    @Override
    public void removeElements(long l, long l2) {
        this.ensureIndex(l2);
        CharBigListIterator charBigListIterator = this.listIterator(l);
        long l3 = l2 - l;
        if (l3 < 0L) {
            throw new IllegalArgumentException("Start index (" + l + ") is greater than end index (" + l2 + ")");
        }
        while (l3-- != 0L) {
            charBigListIterator.nextChar();
            charBigListIterator.remove();
        }
    }

    @Override
    public void addElements(long l, char[][] cArray, long l2, long l3) {
        this.ensureIndex(l);
        CharBigArrays.ensureOffsetLength(cArray, l2, l3);
        while (l3-- != 0L) {
            this.add(l++, CharBigArrays.get(cArray, l2++));
        }
    }

    @Override
    public void addElements(long l, char[][] cArray) {
        this.addElements(l, cArray, 0L, CharBigArrays.length(cArray));
    }

    @Override
    public void getElements(long l, char[][] cArray, long l2, long l3) {
        CharBigListIterator charBigListIterator = this.listIterator(l);
        CharBigArrays.ensureOffsetLength(cArray, l2, l3);
        if (l + l3 > this.size64()) {
            throw new IndexOutOfBoundsException("End index (" + (l + l3) + ") is greater than list size (" + this.size64() + ")");
        }
        while (l3-- != 0L) {
            CharBigArrays.set(cArray, l2++, charBigListIterator.nextChar());
        }
    }

    @Override
    public void clear() {
        this.removeElements(0L, this.size64());
    }

    @Override
    @Deprecated
    public int size() {
        return (int)Math.min(Integer.MAX_VALUE, this.size64());
    }

    private boolean valEquals(Object object, Object object2) {
        return object == null ? object2 == null : object.equals(object2);
    }

    @Override
    public int hashCode() {
        CharBigListIterator charBigListIterator = this.iterator();
        int n = 1;
        long l = this.size64();
        while (l-- != 0L) {
            char c = charBigListIterator.nextChar();
            n = 31 * n + c;
        }
        return n;
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return false;
        }
        if (!(object instanceof BigList)) {
            return true;
        }
        BigList bigList = (BigList)object;
        long l = this.size64();
        if (l != bigList.size64()) {
            return true;
        }
        if (bigList instanceof CharBigList) {
            CharBigListIterator charBigListIterator = this.listIterator();
            CharBigListIterator charBigListIterator2 = ((CharBigList)bigList).listIterator();
            while (l-- != 0L) {
                if (charBigListIterator.nextChar() == charBigListIterator2.nextChar()) continue;
                return true;
            }
            return false;
        }
        CharBigListIterator charBigListIterator = this.listIterator();
        BigListIterator bigListIterator = bigList.listIterator();
        while (l-- != 0L) {
            if (this.valEquals(charBigListIterator.next(), bigListIterator.next())) continue;
            return true;
        }
        return false;
    }

    @Override
    public int compareTo(BigList<? extends Character> bigList) {
        if (bigList == this) {
            return 1;
        }
        if (bigList instanceof CharBigList) {
            CharBigListIterator charBigListIterator = this.listIterator();
            CharBigListIterator charBigListIterator2 = ((CharBigList)bigList).listIterator();
            while (charBigListIterator.hasNext() && charBigListIterator2.hasNext()) {
                char c;
                char c2 = charBigListIterator.nextChar();
                int n = Character.compare(c2, c = charBigListIterator2.nextChar());
                if (n == 0) continue;
                return n;
            }
            return charBigListIterator2.hasNext() ? -1 : (charBigListIterator.hasNext() ? 1 : 0);
        }
        CharBigListIterator charBigListIterator = this.listIterator();
        BigListIterator<? extends Character> bigListIterator = bigList.listIterator();
        while (charBigListIterator.hasNext() && bigListIterator.hasNext()) {
            int n = ((Comparable)charBigListIterator.next()).compareTo(bigListIterator.next());
            if (n == 0) continue;
            return n;
        }
        return bigListIterator.hasNext() ? -1 : (charBigListIterator.hasNext() ? 1 : 0);
    }

    @Override
    public void push(char c) {
        this.add(c);
    }

    @Override
    public char popChar() {
        if (this.isEmpty()) {
            throw new NoSuchElementException();
        }
        return this.removeChar(this.size64() - 1L);
    }

    @Override
    public char topChar() {
        if (this.isEmpty()) {
            throw new NoSuchElementException();
        }
        return this.getChar(this.size64() - 1L);
    }

    @Override
    public char peekChar(int n) {
        return this.getChar(this.size64() - 1L - (long)n);
    }

    @Override
    public boolean rem(char c) {
        long l = this.indexOf(c);
        if (l == -1L) {
            return true;
        }
        this.removeChar(l);
        return false;
    }

    @Override
    public boolean addAll(long l, CharCollection charCollection) {
        return this.addAll(l, (Collection<? extends Character>)charCollection);
    }

    @Override
    public boolean addAll(long l, CharBigList charBigList) {
        return this.addAll(l, (CharCollection)charBigList);
    }

    @Override
    public boolean addAll(CharCollection charCollection) {
        return this.addAll(this.size64(), charCollection);
    }

    @Override
    public boolean addAll(CharBigList charBigList) {
        return this.addAll(this.size64(), charBigList);
    }

    @Override
    @Deprecated
    public void add(long l, Character c) {
        this.add(l, c.charValue());
    }

    @Override
    @Deprecated
    public Character set(long l, Character c) {
        return Character.valueOf(this.set(l, c.charValue()));
    }

    @Override
    @Deprecated
    public Character get(long l) {
        return Character.valueOf(this.getChar(l));
    }

    @Override
    @Deprecated
    public long indexOf(Object object) {
        return this.indexOf(((Character)object).charValue());
    }

    @Override
    @Deprecated
    public long lastIndexOf(Object object) {
        return this.lastIndexOf(((Character)object).charValue());
    }

    @Override
    @Deprecated
    public Character remove(long l) {
        return Character.valueOf(this.removeChar(l));
    }

    @Override
    @Deprecated
    public void push(Character c) {
        this.push(c.charValue());
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
    public Character peek(int n) {
        return Character.valueOf(this.peekChar(n));
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        CharBigListIterator charBigListIterator = this.iterator();
        long l = this.size64();
        boolean bl = true;
        stringBuilder.append("[");
        while (l-- != 0L) {
            if (bl) {
                bl = false;
            } else {
                stringBuilder.append(", ");
            }
            char c = charBigListIterator.nextChar();
            stringBuilder.append(String.valueOf(c));
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    @Override
    public CharIterator iterator() {
        return this.iterator();
    }

    @Override
    public Iterator iterator() {
        return this.iterator();
    }

    @Override
    public BigList subList(long l, long l2) {
        return this.subList(l, l2);
    }

    @Override
    public BigListIterator listIterator(long l) {
        return this.listIterator(l);
    }

    @Override
    public BigListIterator listIterator() {
        return this.listIterator();
    }

    @Override
    @Deprecated
    public void add(long l, Object object) {
        this.add(l, (Character)object);
    }

    @Override
    @Deprecated
    public Object set(long l, Object object) {
        return this.set(l, (Character)object);
    }

    @Override
    @Deprecated
    public Object remove(long l) {
        return this.remove(l);
    }

    @Override
    @Deprecated
    public Object get(long l) {
        return this.get(l);
    }

    @Override
    public int compareTo(Object object) {
        return this.compareTo((BigList)object);
    }

    @Override
    @Deprecated
    public Object peek(int n) {
        return this.peek(n);
    }

    @Override
    @Deprecated
    public Object top() {
        return this.top();
    }

    @Override
    @Deprecated
    public Object pop() {
        return this.pop();
    }

    @Override
    @Deprecated
    public void push(Object object) {
        this.push((Character)object);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class CharSubList
    extends AbstractCharBigList
    implements Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final CharBigList l;
        protected final long from;
        protected long to;
        static final boolean $assertionsDisabled = !AbstractCharBigList.class.desiredAssertionStatus();

        public CharSubList(CharBigList charBigList, long l, long l2) {
            this.l = charBigList;
            this.from = l;
            this.to = l2;
        }

        private boolean assertRange() {
            if (!$assertionsDisabled && this.from > this.l.size64()) {
                throw new AssertionError();
            }
            if (!$assertionsDisabled && this.to > this.l.size64()) {
                throw new AssertionError();
            }
            if (!$assertionsDisabled && this.to < this.from) {
                throw new AssertionError();
            }
            return false;
        }

        @Override
        public boolean add(char c) {
            this.l.add(this.to, c);
            ++this.to;
            if (!$assertionsDisabled && !this.assertRange()) {
                throw new AssertionError();
            }
            return false;
        }

        @Override
        public void add(long l, char c) {
            this.ensureIndex(l);
            this.l.add(this.from + l, c);
            ++this.to;
            if (!$assertionsDisabled && !this.assertRange()) {
                throw new AssertionError();
            }
        }

        @Override
        public boolean addAll(long l, Collection<? extends Character> collection) {
            this.ensureIndex(l);
            this.to += (long)collection.size();
            return this.l.addAll(this.from + l, collection);
        }

        @Override
        public char getChar(long l) {
            this.ensureRestrictedIndex(l);
            return this.l.getChar(this.from + l);
        }

        @Override
        public char removeChar(long l) {
            this.ensureRestrictedIndex(l);
            --this.to;
            return this.l.removeChar(this.from + l);
        }

        @Override
        public char set(long l, char c) {
            this.ensureRestrictedIndex(l);
            return this.l.set(this.from + l, c);
        }

        @Override
        public long size64() {
            return this.to - this.from;
        }

        @Override
        public void getElements(long l, char[][] cArray, long l2, long l3) {
            this.ensureIndex(l);
            if (l + l3 > this.size64()) {
                throw new IndexOutOfBoundsException("End index (" + l + l3 + ") is greater than list size (" + this.size64() + ")");
            }
            this.l.getElements(this.from + l, cArray, l2, l3);
        }

        @Override
        public void removeElements(long l, long l2) {
            this.ensureIndex(l);
            this.ensureIndex(l2);
            this.l.removeElements(this.from + l, this.from + l2);
            this.to -= l2 - l;
            if (!$assertionsDisabled && !this.assertRange()) {
                throw new AssertionError();
            }
        }

        @Override
        public void addElements(long l, char[][] cArray, long l2, long l3) {
            this.ensureIndex(l);
            this.l.addElements(this.from + l, cArray, l2, l3);
            this.to += l3;
            if (!$assertionsDisabled && !this.assertRange()) {
                throw new AssertionError();
            }
        }

        @Override
        public CharBigListIterator listIterator(long l) {
            this.ensureIndex(l);
            return new CharBigListIterator(this, l){
                long pos;
                long last;
                static final boolean $assertionsDisabled = !AbstractCharBigList.class.desiredAssertionStatus();
                final long val$index;
                final CharSubList this$0;
                {
                    this.this$0 = charSubList;
                    this.val$index = l;
                    this.pos = this.val$index;
                    this.last = -1L;
                }

                @Override
                public boolean hasNext() {
                    return this.pos < this.this$0.size64();
                }

                @Override
                public boolean hasPrevious() {
                    return this.pos > 0L;
                }

                @Override
                public char nextChar() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    this.last = this.pos++;
                    return this.this$0.l.getChar(this.this$0.from + this.last);
                }

                @Override
                public char previousChar() {
                    if (!this.hasPrevious()) {
                        throw new NoSuchElementException();
                    }
                    this.last = --this.pos;
                    return this.this$0.l.getChar(this.this$0.from + this.pos);
                }

                @Override
                public long nextIndex() {
                    return this.pos;
                }

                @Override
                public long previousIndex() {
                    return this.pos - 1L;
                }

                @Override
                public void add(char c) {
                    if (this.last == -1L) {
                        throw new IllegalStateException();
                    }
                    this.this$0.add(this.pos++, c);
                    this.last = -1L;
                    if (!$assertionsDisabled && !CharSubList.access$000(this.this$0)) {
                        throw new AssertionError();
                    }
                }

                @Override
                public void set(char c) {
                    if (this.last == -1L) {
                        throw new IllegalStateException();
                    }
                    this.this$0.set(this.last, c);
                }

                @Override
                public void remove() {
                    if (this.last == -1L) {
                        throw new IllegalStateException();
                    }
                    this.this$0.removeChar(this.last);
                    if (this.last < this.pos) {
                        --this.pos;
                    }
                    this.last = -1L;
                    if (!$assertionsDisabled && !CharSubList.access$000(this.this$0)) {
                        throw new AssertionError();
                    }
                }
            };
        }

        @Override
        public CharBigList subList(long l, long l2) {
            this.ensureIndex(l);
            this.ensureIndex(l2);
            if (l > l2) {
                throw new IllegalArgumentException("Start index (" + l + ") is greater than end index (" + l2 + ")");
            }
            return new CharSubList(this, l, l2);
        }

        @Override
        public boolean rem(char c) {
            long l = this.indexOf(c);
            if (l == -1L) {
                return true;
            }
            --this.to;
            this.l.removeChar(this.from + l);
            if (!$assertionsDisabled && !this.assertRange()) {
                throw new AssertionError();
            }
            return false;
        }

        @Override
        public boolean addAll(long l, CharCollection charCollection) {
            this.ensureIndex(l);
            return super.addAll(l, charCollection);
        }

        @Override
        public boolean addAll(long l, CharBigList charBigList) {
            this.ensureIndex(l);
            return super.addAll(l, charBigList);
        }

        @Override
        public BigList subList(long l, long l2) {
            return this.subList(l, l2);
        }

        @Override
        public BigListIterator listIterator(long l) {
            return this.listIterator(l);
        }

        @Override
        public BigListIterator listIterator() {
            return super.listIterator();
        }

        @Override
        @Deprecated
        public void add(long l, Object object) {
            super.add(l, (Character)object);
        }

        @Override
        @Deprecated
        public Object set(long l, Object object) {
            return super.set(l, (Character)object);
        }

        @Override
        @Deprecated
        public Object remove(long l) {
            return super.remove(l);
        }

        @Override
        @Deprecated
        public Object get(long l) {
            return super.get(l);
        }

        @Override
        public Iterator iterator() {
            return super.iterator();
        }

        @Override
        public CharIterator iterator() {
            return super.iterator();
        }

        @Override
        public int compareTo(Object object) {
            return super.compareTo((BigList)object);
        }

        @Override
        @Deprecated
        public Object peek(int n) {
            return super.peek(n);
        }

        @Override
        @Deprecated
        public Object top() {
            return super.top();
        }

        @Override
        @Deprecated
        public Object pop() {
            return super.pop();
        }

        @Override
        @Deprecated
        public void push(Object object) {
            super.push((Character)object);
        }

        static boolean access$000(CharSubList charSubList) {
            return charSubList.assertRange();
        }
    }
}

