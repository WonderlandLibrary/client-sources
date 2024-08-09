/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package it.unimi.dsi.fastutil.chars;

import it.unimi.dsi.fastutil.SafeMath;
import it.unimi.dsi.fastutil.chars.CharIterable;
import it.unimi.dsi.fastutil.chars.CharIterator;
import java.util.Collection;
import java.util.Iterator;
import java.util.Objects;
import java.util.function.IntPredicate;
import java.util.function.Predicate;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public interface CharCollection
extends Collection<Character>,
CharIterable {
    @Override
    public CharIterator iterator();

    @Override
    public boolean add(char var1);

    public boolean contains(char var1);

    public boolean rem(char var1);

    @Override
    @Deprecated
    default public boolean add(Character c) {
        return this.add(c.charValue());
    }

    @Override
    @Deprecated
    default public boolean contains(Object object) {
        if (object == null) {
            return true;
        }
        return this.contains(((Character)object).charValue());
    }

    @Override
    @Deprecated
    default public boolean remove(Object object) {
        if (object == null) {
            return true;
        }
        return this.rem(((Character)object).charValue());
    }

    public char[] toCharArray();

    @Deprecated
    public char[] toCharArray(char[] var1);

    public char[] toArray(char[] var1);

    public boolean addAll(CharCollection var1);

    public boolean containsAll(CharCollection var1);

    public boolean removeAll(CharCollection var1);

    @Override
    @Deprecated
    default public boolean removeIf(Predicate<? super Character> predicate) {
        return this.removeIf(arg_0 -> CharCollection.lambda$removeIf$0(predicate, arg_0));
    }

    default public boolean removeIf(IntPredicate intPredicate) {
        Objects.requireNonNull(intPredicate);
        boolean bl = false;
        CharIterator charIterator = this.iterator();
        while (charIterator.hasNext()) {
            if (!intPredicate.test(charIterator.nextChar())) continue;
            charIterator.remove();
            bl = true;
        }
        return bl;
    }

    public boolean retainAll(CharCollection var1);

    @Override
    @Deprecated
    default public boolean add(Object object) {
        return this.add((Character)object);
    }

    @Override
    default public Iterator iterator() {
        return this.iterator();
    }

    private static boolean lambda$removeIf$0(Predicate predicate, int n) {
        return predicate.test(Character.valueOf(SafeMath.safeIntToChar(n)));
    }
}

