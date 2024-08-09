/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.chars.AbstractCharCollection;
import it.unimi.dsi.fastutil.chars.CharCollection;
import it.unimi.dsi.fastutil.chars.CharIterator;
import it.unimi.dsi.fastutil.chars.CharList;
import it.unimi.dsi.fastutil.chars.CharListIterator;
import it.unimi.dsi.fastutil.chars.CharStack;
import java.io.Serializable;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public abstract class AbstractCharList
extends AbstractCharCollection
implements CharList,
CharStack {
    protected AbstractCharList() {
    }

    protected void ensureIndex(int n) {
        if (n < 0) {
            throw new IndexOutOfBoundsException("Index (" + n + ") is negative");
        }
        if (n > this.size()) {
            throw new IndexOutOfBoundsException("Index (" + n + ") is greater than list size (" + this.size() + ")");
        }
    }

    protected void ensureRestrictedIndex(int n) {
        if (n < 0) {
            throw new IndexOutOfBoundsException("Index (" + n + ") is negative");
        }
        if (n >= this.size()) {
            throw new IndexOutOfBoundsException("Index (" + n + ") is greater than or equal to list size (" + this.size() + ")");
        }
    }

    @Override
    public void add(int n, char c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean add(char c) {
        this.add(this.size(), c);
        return false;
    }

    @Override
    public char removeChar(int n) {
        throw new UnsupportedOperationException();
    }

    @Override
    public char set(int n, char c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(int n, Collection<? extends Character> collection) {
        this.ensureIndex(n);
        Iterator<? extends Character> iterator2 = collection.iterator();
        boolean bl = iterator2.hasNext();
        while (iterator2.hasNext()) {
            this.add(n++, iterator2.next().charValue());
        }
        return bl;
    }

    @Override
    public boolean addAll(Collection<? extends Character> collection) {
        return this.addAll(this.size(), collection);
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
    public CharListIterator listIterator(int n) {
        this.ensureIndex(n);
        return new CharListIterator(this, n){
            int pos;
            int last;
            final int val$index;
            final AbstractCharList this$0;
            {
                this.this$0 = abstractCharList;
                this.val$index = n;
                this.pos = this.val$index;
                this.last = -1;
            }

            @Override
            public boolean hasNext() {
                return this.pos < this.this$0.size();
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
            public int nextIndex() {
                return this.pos;
            }

            @Override
            public int previousIndex() {
                return this.pos - 1;
            }

            @Override
            public void add(char c) {
                this.this$0.add(this.pos++, c);
                this.last = -1;
            }

            @Override
            public void set(char c) {
                if (this.last == -1) {
                    throw new IllegalStateException();
                }
                this.this$0.set(this.last, c);
            }

            @Override
            public void remove() {
                if (this.last == -1) {
                    throw new IllegalStateException();
                }
                this.this$0.removeChar(this.last);
                if (this.last < this.pos) {
                    --this.pos;
                }
                this.last = -1;
            }
        };
    }

    @Override
    public boolean contains(char c) {
        return this.indexOf(c) >= 0;
    }

    @Override
    public int indexOf(char c) {
        CharListIterator charListIterator = this.listIterator();
        while (charListIterator.hasNext()) {
            char c2 = charListIterator.nextChar();
            if (c != c2) continue;
            return charListIterator.previousIndex();
        }
        return 1;
    }

    @Override
    public int lastIndexOf(char c) {
        CharListIterator charListIterator = this.listIterator(this.size());
        while (charListIterator.hasPrevious()) {
            char c2 = charListIterator.previousChar();
            if (c != c2) continue;
            return charListIterator.nextIndex();
        }
        return 1;
    }

    @Override
    public void size(int n) {
        int n2 = this.size();
        if (n > n2) {
            while (n2++ < n) {
                this.add('\u0001');
            }
        } else {
            while (n2-- != n) {
                this.removeChar(n2);
            }
        }
    }

    @Override
    public CharList subList(int n, int n2) {
        this.ensureIndex(n);
        this.ensureIndex(n2);
        if (n > n2) {
            throw new IndexOutOfBoundsException("Start index (" + n + ") is greater than end index (" + n2 + ")");
        }
        return new CharSubList(this, n, n2);
    }

    @Override
    public void removeElements(int n, int n2) {
        this.ensureIndex(n2);
        CharListIterator charListIterator = this.listIterator(n);
        int n3 = n2 - n;
        if (n3 < 0) {
            throw new IllegalArgumentException("Start index (" + n + ") is greater than end index (" + n2 + ")");
        }
        while (n3-- != 0) {
            charListIterator.nextChar();
            charListIterator.remove();
        }
    }

    @Override
    public void addElements(int n, char[] cArray, int n2, int n3) {
        this.ensureIndex(n);
        if (n2 < 0) {
            throw new ArrayIndexOutOfBoundsException("Offset (" + n2 + ") is negative");
        }
        if (n2 + n3 > cArray.length) {
            throw new ArrayIndexOutOfBoundsException("End index (" + (n2 + n3) + ") is greater than array length (" + cArray.length + ")");
        }
        while (n3-- != 0) {
            this.add(n++, cArray[n2++]);
        }
    }

    @Override
    public void addElements(int n, char[] cArray) {
        this.addElements(n, cArray, 0, cArray.length);
    }

    @Override
    public void getElements(int n, char[] cArray, int n2, int n3) {
        CharListIterator charListIterator = this.listIterator(n);
        if (n2 < 0) {
            throw new ArrayIndexOutOfBoundsException("Offset (" + n2 + ") is negative");
        }
        if (n2 + n3 > cArray.length) {
            throw new ArrayIndexOutOfBoundsException("End index (" + (n2 + n3) + ") is greater than array length (" + cArray.length + ")");
        }
        if (n + n3 > this.size()) {
            throw new IndexOutOfBoundsException("End index (" + (n + n3) + ") is greater than list size (" + this.size() + ")");
        }
        while (n3-- != 0) {
            cArray[n2++] = charListIterator.nextChar();
        }
    }

    @Override
    public void clear() {
        this.removeElements(0, this.size());
    }

    private boolean valEquals(Object object, Object object2) {
        return object == null ? object2 == null : object.equals(object2);
    }

    @Override
    public int hashCode() {
        CharListIterator charListIterator = this.iterator();
        int n = 1;
        int n2 = this.size();
        while (n2-- != 0) {
            char c = charListIterator.nextChar();
            n = 31 * n + c;
        }
        return n;
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return false;
        }
        if (!(object instanceof List)) {
            return true;
        }
        List list = (List)object;
        int n = this.size();
        if (n != list.size()) {
            return true;
        }
        if (list instanceof CharList) {
            CharListIterator charListIterator = this.listIterator();
            CharListIterator charListIterator2 = ((CharList)list).listIterator();
            while (n-- != 0) {
                if (charListIterator.nextChar() == charListIterator2.nextChar()) continue;
                return true;
            }
            return false;
        }
        CharListIterator charListIterator = this.listIterator();
        ListIterator listIterator2 = list.listIterator();
        while (n-- != 0) {
            if (this.valEquals(charListIterator.next(), listIterator2.next())) continue;
            return true;
        }
        return false;
    }

    @Override
    public int compareTo(List<? extends Character> list) {
        if (list == this) {
            return 1;
        }
        if (list instanceof CharList) {
            CharListIterator charListIterator = this.listIterator();
            CharListIterator charListIterator2 = ((CharList)list).listIterator();
            while (charListIterator.hasNext() && charListIterator2.hasNext()) {
                char c;
                char c2 = charListIterator.nextChar();
                int n = Character.compare(c2, c = charListIterator2.nextChar());
                if (n == 0) continue;
                return n;
            }
            return charListIterator2.hasNext() ? -1 : (charListIterator.hasNext() ? 1 : 0);
        }
        CharListIterator charListIterator = this.listIterator();
        ListIterator<? extends Character> listIterator2 = list.listIterator();
        while (charListIterator.hasNext() && listIterator2.hasNext()) {
            int n = ((Comparable)charListIterator.next()).compareTo(listIterator2.next());
            if (n == 0) continue;
            return n;
        }
        return listIterator2.hasNext() ? -1 : (charListIterator.hasNext() ? 1 : 0);
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
    public char peekChar(int n) {
        return this.getChar(this.size() - 1 - n);
    }

    @Override
    public boolean rem(char c) {
        int n = this.indexOf(c);
        if (n == -1) {
            return true;
        }
        this.removeChar(n);
        return false;
    }

    @Override
    public boolean addAll(int n, CharCollection charCollection) {
        this.ensureIndex(n);
        CharIterator charIterator = charCollection.iterator();
        boolean bl = charIterator.hasNext();
        while (charIterator.hasNext()) {
            this.add(n++, charIterator.nextChar());
        }
        return bl;
    }

    @Override
    public boolean addAll(int n, CharList charList) {
        return this.addAll(n, (CharCollection)charList);
    }

    @Override
    public boolean addAll(CharCollection charCollection) {
        return this.addAll(this.size(), charCollection);
    }

    @Override
    public boolean addAll(CharList charList) {
        return this.addAll(this.size(), charList);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        CharListIterator charListIterator = this.iterator();
        int n = this.size();
        boolean bl = true;
        stringBuilder.append("[");
        while (n-- != 0) {
            if (bl) {
                bl = false;
            } else {
                stringBuilder.append(", ");
            }
            char c = charListIterator.nextChar();
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
    public List subList(int n, int n2) {
        return this.subList(n, n2);
    }

    @Override
    public ListIterator listIterator(int n) {
        return this.listIterator(n);
    }

    @Override
    public ListIterator listIterator() {
        return this.listIterator();
    }

    @Override
    public int compareTo(Object object) {
        return this.compareTo((List)object);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class CharSubList
    extends AbstractCharList
    implements Serializable {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final CharList l;
        protected final int from;
        protected int to;
        static final boolean $assertionsDisabled = !AbstractCharList.class.desiredAssertionStatus();

        public CharSubList(CharList charList, int n, int n2) {
            this.l = charList;
            this.from = n;
            this.to = n2;
        }

        private boolean assertRange() {
            if (!$assertionsDisabled && this.from > this.l.size()) {
                throw new AssertionError();
            }
            if (!$assertionsDisabled && this.to > this.l.size()) {
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
        public void add(int n, char c) {
            this.ensureIndex(n);
            this.l.add(this.from + n, c);
            ++this.to;
            if (!$assertionsDisabled && !this.assertRange()) {
                throw new AssertionError();
            }
        }

        @Override
        public boolean addAll(int n, Collection<? extends Character> collection) {
            this.ensureIndex(n);
            this.to += collection.size();
            return this.l.addAll(this.from + n, collection);
        }

        @Override
        public char getChar(int n) {
            this.ensureRestrictedIndex(n);
            return this.l.getChar(this.from + n);
        }

        @Override
        public char removeChar(int n) {
            this.ensureRestrictedIndex(n);
            --this.to;
            return this.l.removeChar(this.from + n);
        }

        @Override
        public char set(int n, char c) {
            this.ensureRestrictedIndex(n);
            return this.l.set(this.from + n, c);
        }

        @Override
        public int size() {
            return this.to - this.from;
        }

        @Override
        public void getElements(int n, char[] cArray, int n2, int n3) {
            this.ensureIndex(n);
            if (n + n3 > this.size()) {
                throw new IndexOutOfBoundsException("End index (" + n + n3 + ") is greater than list size (" + this.size() + ")");
            }
            this.l.getElements(this.from + n, cArray, n2, n3);
        }

        @Override
        public void removeElements(int n, int n2) {
            this.ensureIndex(n);
            this.ensureIndex(n2);
            this.l.removeElements(this.from + n, this.from + n2);
            this.to -= n2 - n;
            if (!$assertionsDisabled && !this.assertRange()) {
                throw new AssertionError();
            }
        }

        @Override
        public void addElements(int n, char[] cArray, int n2, int n3) {
            this.ensureIndex(n);
            this.l.addElements(this.from + n, cArray, n2, n3);
            this.to += n3;
            if (!$assertionsDisabled && !this.assertRange()) {
                throw new AssertionError();
            }
        }

        @Override
        public CharListIterator listIterator(int n) {
            this.ensureIndex(n);
            return new CharListIterator(this, n){
                int pos;
                int last;
                static final boolean $assertionsDisabled = !AbstractCharList.class.desiredAssertionStatus();
                final int val$index;
                final CharSubList this$0;
                {
                    this.this$0 = charSubList;
                    this.val$index = n;
                    this.pos = this.val$index;
                    this.last = -1;
                }

                @Override
                public boolean hasNext() {
                    return this.pos < this.this$0.size();
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
                public int nextIndex() {
                    return this.pos;
                }

                @Override
                public int previousIndex() {
                    return this.pos - 1;
                }

                @Override
                public void add(char c) {
                    if (this.last == -1) {
                        throw new IllegalStateException();
                    }
                    this.this$0.add(this.pos++, c);
                    this.last = -1;
                    if (!$assertionsDisabled && !CharSubList.access$000(this.this$0)) {
                        throw new AssertionError();
                    }
                }

                @Override
                public void set(char c) {
                    if (this.last == -1) {
                        throw new IllegalStateException();
                    }
                    this.this$0.set(this.last, c);
                }

                @Override
                public void remove() {
                    if (this.last == -1) {
                        throw new IllegalStateException();
                    }
                    this.this$0.removeChar(this.last);
                    if (this.last < this.pos) {
                        --this.pos;
                    }
                    this.last = -1;
                    if (!$assertionsDisabled && !CharSubList.access$000(this.this$0)) {
                        throw new AssertionError();
                    }
                }
            };
        }

        @Override
        public CharList subList(int n, int n2) {
            this.ensureIndex(n);
            this.ensureIndex(n2);
            if (n > n2) {
                throw new IllegalArgumentException("Start index (" + n + ") is greater than end index (" + n2 + ")");
            }
            return new CharSubList(this, n, n2);
        }

        @Override
        public boolean rem(char c) {
            int n = this.indexOf(c);
            if (n == -1) {
                return true;
            }
            --this.to;
            this.l.removeChar(this.from + n);
            if (!$assertionsDisabled && !this.assertRange()) {
                throw new AssertionError();
            }
            return false;
        }

        @Override
        public boolean addAll(int n, CharCollection charCollection) {
            this.ensureIndex(n);
            return super.addAll(n, charCollection);
        }

        @Override
        public boolean addAll(int n, CharList charList) {
            this.ensureIndex(n);
            return super.addAll(n, charList);
        }

        @Override
        public List subList(int n, int n2) {
            return this.subList(n, n2);
        }

        @Override
        public ListIterator listIterator(int n) {
            return this.listIterator(n);
        }

        @Override
        public ListIterator listIterator() {
            return super.listIterator();
        }

        @Override
        public Iterator iterator() {
            return super.iterator();
        }

        @Override
        public int compareTo(Object object) {
            return super.compareTo((List)object);
        }

        @Override
        public CharIterator iterator() {
            return super.iterator();
        }

        static boolean access$000(CharSubList charSubList) {
            return charSubList.assertRange();
        }
    }
}

