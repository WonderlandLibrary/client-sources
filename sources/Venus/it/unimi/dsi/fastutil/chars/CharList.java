/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.chars.CharCollection;
import it.unimi.dsi.fastutil.chars.CharIterator;
import it.unimi.dsi.fastutil.chars.CharListIterator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public interface CharList
extends List<Character>,
Comparable<List<? extends Character>>,
CharCollection {
    @Override
    public CharListIterator iterator();

    public CharListIterator listIterator();

    public CharListIterator listIterator(int var1);

    public CharList subList(int var1, int var2);

    public void size(int var1);

    public void getElements(int var1, char[] var2, int var3, int var4);

    public void removeElements(int var1, int var2);

    public void addElements(int var1, char[] var2);

    public void addElements(int var1, char[] var2, int var3, int var4);

    @Override
    public boolean add(char var1);

    @Override
    public void add(int var1, char var2);

    @Override
    @Deprecated
    default public void add(int n, Character c) {
        this.add(n, c.charValue());
    }

    public boolean addAll(int var1, CharCollection var2);

    public boolean addAll(int var1, CharList var2);

    public boolean addAll(CharList var1);

    @Override
    public char set(int var1, char var2);

    public char getChar(int var1);

    public int indexOf(char var1);

    public int lastIndexOf(char var1);

    @Override
    @Deprecated
    default public boolean contains(Object object) {
        return CharCollection.super.contains(object);
    }

    @Override
    @Deprecated
    default public Character get(int n) {
        return Character.valueOf(this.getChar(n));
    }

    @Override
    @Deprecated
    default public int indexOf(Object object) {
        return this.indexOf(((Character)object).charValue());
    }

    @Override
    @Deprecated
    default public int lastIndexOf(Object object) {
        return this.lastIndexOf(((Character)object).charValue());
    }

    @Override
    @Deprecated
    default public boolean add(Character c) {
        return this.add(c.charValue());
    }

    public char removeChar(int var1);

    @Override
    @Deprecated
    default public boolean remove(Object object) {
        return CharCollection.super.remove(object);
    }

    @Override
    @Deprecated
    default public Character remove(int n) {
        return Character.valueOf(this.removeChar(n));
    }

    @Override
    @Deprecated
    default public Character set(int n, Character c) {
        return Character.valueOf(this.set(n, c.charValue()));
    }

    @Override
    default public List subList(int n, int n2) {
        return this.subList(n, n2);
    }

    @Override
    default public ListIterator listIterator(int n) {
        return this.listIterator(n);
    }

    @Override
    default public ListIterator listIterator() {
        return this.listIterator();
    }

    @Override
    @Deprecated
    default public Object remove(int n) {
        return this.remove(n);
    }

    @Override
    @Deprecated
    default public void add(int n, Object object) {
        this.add(n, (Character)object);
    }

    @Override
    @Deprecated
    default public Object set(int n, Object object) {
        return this.set(n, (Character)object);
    }

    @Override
    @Deprecated
    default public Object get(int n) {
        return this.get(n);
    }

    @Override
    @Deprecated
    default public boolean add(Object object) {
        return this.add((Character)object);
    }

    @Override
    default public Iterator iterator() {
        return this.iterator();
    }

    @Override
    default public CharIterator iterator() {
        return this.iterator();
    }
}

