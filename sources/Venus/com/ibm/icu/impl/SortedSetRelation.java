/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl;

import java.util.Iterator;
import java.util.SortedSet;
import java.util.TreeSet;

public class SortedSetRelation {
    public static final int A_NOT_B = 4;
    public static final int A_AND_B = 2;
    public static final int B_NOT_A = 1;
    public static final int ANY = 7;
    public static final int CONTAINS = 6;
    public static final int DISJOINT = 5;
    public static final int ISCONTAINED = 3;
    public static final int NO_B = 4;
    public static final int EQUALS = 2;
    public static final int NO_A = 1;
    public static final int NONE = 0;
    public static final int ADDALL = 7;
    public static final int A = 6;
    public static final int COMPLEMENTALL = 5;
    public static final int B = 3;
    public static final int REMOVEALL = 4;
    public static final int RETAINALL = 2;
    public static final int B_REMOVEALL = 1;

    public static <T> boolean hasRelation(SortedSet<T> sortedSet, int n, SortedSet<T> sortedSet2) {
        if (n < 0 || n > 7) {
            throw new IllegalArgumentException("Relation " + n + " out of range");
        }
        boolean bl = (n & 4) != 0;
        boolean bl2 = (n & 2) != 0;
        boolean bl3 = (n & 1) != 0;
        switch (n) {
            case 6: {
                if (sortedSet.size() >= sortedSet2.size()) break;
                return true;
            }
            case 3: {
                if (sortedSet.size() <= sortedSet2.size()) break;
                return true;
            }
            case 2: {
                if (sortedSet.size() == sortedSet2.size()) break;
                return true;
            }
        }
        if (sortedSet.size() == 0) {
            if (sortedSet2.size() == 0) {
                return false;
            }
            return bl3;
        }
        if (sortedSet2.size() == 0) {
            return bl;
        }
        Iterator iterator2 = sortedSet.iterator();
        Iterator iterator3 = sortedSet2.iterator();
        Object e = iterator2.next();
        Object e2 = iterator3.next();
        while (true) {
            int n2;
            if ((n2 = ((Comparable)e).compareTo(e2)) == 0) {
                if (!bl2) {
                    return true;
                }
                if (!iterator2.hasNext()) {
                    if (!iterator3.hasNext()) {
                        return false;
                    }
                    return bl3;
                }
                if (!iterator3.hasNext()) {
                    return bl;
                }
                e = iterator2.next();
                e2 = iterator3.next();
                continue;
            }
            if (n2 < 0) {
                if (!bl) {
                    return true;
                }
                if (!iterator2.hasNext()) {
                    return bl3;
                }
                e = iterator2.next();
                continue;
            }
            if (!bl3) {
                return true;
            }
            if (!iterator3.hasNext()) {
                return bl;
            }
            e2 = iterator3.next();
        }
    }

    public static <T> SortedSet<? extends T> doOperation(SortedSet<T> sortedSet, int n, SortedSet<T> sortedSet2) {
        switch (n) {
            case 7: {
                sortedSet.addAll(sortedSet2);
                return sortedSet;
            }
            case 6: {
                return sortedSet;
            }
            case 3: {
                sortedSet.clear();
                sortedSet.addAll(sortedSet2);
                return sortedSet;
            }
            case 4: {
                sortedSet.removeAll(sortedSet2);
                return sortedSet;
            }
            case 2: {
                sortedSet.retainAll(sortedSet2);
                return sortedSet;
            }
            case 5: {
                TreeSet<T> treeSet = new TreeSet<T>(sortedSet2);
                treeSet.removeAll(sortedSet);
                sortedSet.removeAll(sortedSet2);
                sortedSet.addAll(treeSet);
                return sortedSet;
            }
            case 1: {
                TreeSet<T> treeSet = new TreeSet<T>(sortedSet2);
                treeSet.removeAll(sortedSet);
                sortedSet.clear();
                sortedSet.addAll(treeSet);
                return sortedSet;
            }
            case 0: {
                sortedSet.clear();
                return sortedSet;
            }
        }
        throw new IllegalArgumentException("Relation " + n + " out of range");
    }
}

