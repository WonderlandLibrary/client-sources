/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.BigList;
import it.unimi.dsi.fastutil.BigListIterator;
import it.unimi.dsi.fastutil.Size64;
import it.unimi.dsi.fastutil.chars.CharBigListIterator;
import it.unimi.dsi.fastutil.chars.CharCollection;
import it.unimi.dsi.fastutil.chars.CharIterator;
import java.util.Iterator;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public interface CharBigList
extends BigList<Character>,
CharCollection,
Size64,
Comparable<BigList<? extends Character>> {
    @Override
    public CharBigListIterator iterator();

    public CharBigListIterator listIterator();

    public CharBigListIterator listIterator(long var1);

    public CharBigList subList(long var1, long var3);

    public void getElements(long var1, char[][] var3, long var4, long var6);

    public void removeElements(long var1, long var3);

    public void addElements(long var1, char[][] var3);

    public void addElements(long var1, char[][] var3, long var4, long var6);

    @Override
    public void add(long var1, char var3);

    public boolean addAll(long var1, CharCollection var3);

    public boolean addAll(long var1, CharBigList var3);

    public boolean addAll(CharBigList var1);

    public char getChar(long var1);

    public char removeChar(long var1);

    @Override
    public char set(long var1, char var3);

    public long indexOf(char var1);

    public long lastIndexOf(char var1);

    @Override
    @Deprecated
    public void add(long var1, Character var3);

    @Override
    @Deprecated
    public Character get(long var1);

    @Override
    @Deprecated
    public long indexOf(Object var1);

    @Override
    @Deprecated
    public long lastIndexOf(Object var1);

    @Override
    @Deprecated
    public Character remove(long var1);

    @Override
    @Deprecated
    public Character set(long var1, Character var3);

    @Override
    default public BigList subList(long l, long l2) {
        return this.subList(l, l2);
    }

    @Override
    default public BigListIterator listIterator(long l) {
        return this.listIterator(l);
    }

    @Override
    default public BigListIterator listIterator() {
        return this.listIterator();
    }

    @Override
    @Deprecated
    default public void add(long l, Object object) {
        this.add(l, (Character)object);
    }

    @Override
    @Deprecated
    default public Object set(long l, Object object) {
        return this.set(l, (Character)object);
    }

    @Override
    @Deprecated
    default public Object remove(long l) {
        return this.remove(l);
    }

    @Override
    @Deprecated
    default public Object get(long l) {
        return this.get(l);
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

