/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.chars.CharCollection;
import it.unimi.dsi.fastutil.chars.CharIterator;
import it.unimi.dsi.fastutil.chars.CharIterators;
import java.util.AbstractCollection;
import java.util.Iterator;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public abstract class AbstractCharCollection
extends AbstractCollection<Character>
implements CharCollection {
    protected AbstractCharCollection() {
    }

    @Override
    public abstract CharIterator iterator();

    @Override
    public boolean add(char c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean contains(char c) {
        CharIterator charIterator = this.iterator();
        while (charIterator.hasNext()) {
            if (c != charIterator.nextChar()) continue;
            return false;
        }
        return true;
    }

    @Override
    public boolean rem(char c) {
        CharIterator charIterator = this.iterator();
        while (charIterator.hasNext()) {
            if (c != charIterator.nextChar()) continue;
            charIterator.remove();
            return false;
        }
        return true;
    }

    @Override
    @Deprecated
    public boolean add(Character c) {
        return CharCollection.super.add(c);
    }

    @Override
    @Deprecated
    public boolean contains(Object object) {
        return CharCollection.super.contains(object);
    }

    @Override
    @Deprecated
    public boolean remove(Object object) {
        return CharCollection.super.remove(object);
    }

    @Override
    public char[] toArray(char[] cArray) {
        if (cArray == null || cArray.length < this.size()) {
            cArray = new char[this.size()];
        }
        CharIterators.unwrap(this.iterator(), cArray);
        return cArray;
    }

    @Override
    public char[] toCharArray() {
        return this.toArray((char[])null);
    }

    @Override
    @Deprecated
    public char[] toCharArray(char[] cArray) {
        return this.toArray(cArray);
    }

    @Override
    public boolean addAll(CharCollection charCollection) {
        boolean bl = false;
        CharIterator charIterator = charCollection.iterator();
        while (charIterator.hasNext()) {
            if (!this.add(charIterator.nextChar())) continue;
            bl = true;
        }
        return bl;
    }

    @Override
    public boolean containsAll(CharCollection charCollection) {
        CharIterator charIterator = charCollection.iterator();
        while (charIterator.hasNext()) {
            if (this.contains(charIterator.nextChar())) continue;
            return true;
        }
        return false;
    }

    @Override
    public boolean removeAll(CharCollection charCollection) {
        boolean bl = false;
        CharIterator charIterator = charCollection.iterator();
        while (charIterator.hasNext()) {
            if (!this.rem(charIterator.nextChar())) continue;
            bl = true;
        }
        return bl;
    }

    @Override
    public boolean retainAll(CharCollection charCollection) {
        boolean bl = false;
        CharIterator charIterator = this.iterator();
        while (charIterator.hasNext()) {
            if (charCollection.contains(charIterator.nextChar())) continue;
            charIterator.remove();
            bl = true;
        }
        return bl;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        CharIterator charIterator = this.iterator();
        int n = this.size();
        boolean bl = true;
        stringBuilder.append("{");
        while (n-- != 0) {
            if (bl) {
                bl = false;
            } else {
                stringBuilder.append(", ");
            }
            char c = charIterator.nextChar();
            stringBuilder.append(String.valueOf(c));
        }
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    @Override
    @Deprecated
    public boolean add(Object object) {
        return this.add((Character)object);
    }

    @Override
    public Iterator iterator() {
        return this.iterator();
    }
}

