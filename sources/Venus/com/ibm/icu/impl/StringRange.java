/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl;

import com.ibm.icu.impl.Relation;
import com.ibm.icu.lang.CharSequences;
import com.ibm.icu.util.ICUException;
import java.util.AbstractSequentialList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class StringRange {
    private static final boolean DEBUG = false;
    public static final Comparator<int[]> COMPARE_INT_ARRAYS = new Comparator<int[]>(){

        @Override
        public int compare(int[] nArray, int[] nArray2) {
            int n = Math.min(nArray.length, nArray2.length);
            for (int i = 0; i < n; ++i) {
                int n2 = nArray[i] - nArray2[i];
                if (n2 == 0) continue;
                return n2;
            }
            return nArray.length - nArray2.length;
        }

        @Override
        public int compare(Object object, Object object2) {
            return this.compare((int[])object, (int[])object2);
        }
    };

    public static void compact(Set<String> set, Adder adder, boolean bl, boolean bl2) {
        if (!bl2) {
            String string = null;
            String string2 = null;
            boolean n = false;
            int n2 = 0;
            for (String string3 : set) {
                int object2;
                if (string != null) {
                    int n3;
                    if (string3.regionMatches(0, string, 0, n2) && (n3 = string3.codePointAt(n2)) == 1 + object2 && string3.length() == n2 + Character.charCount(n3)) {
                        string2 = string3;
                        object2 = n3;
                        continue;
                    }
                    adder.add(string, string2 == null ? null : (!bl ? string2 : string2.substring(n2, string2.length())));
                }
                string = string3;
                string2 = null;
                object2 = string3.codePointBefore(string3.length());
                n2 = string3.length() - Character.charCount(object2);
            }
            adder.add(string, string2 == null ? null : (!bl ? string2 : string2.substring(n2, string2.length())));
        } else {
            Object object;
            Relation<Integer, Ranges> relation = Relation.of(new TreeMap(), TreeSet.class);
            for (String string : set) {
                object = new Ranges(string);
                relation.put(((Ranges)object).size(), (Ranges)object);
            }
            for (Map.Entry entry : relation.keyValuesSet()) {
                object = StringRange.compact((Integer)entry.getKey(), (Set)entry.getValue());
                Iterator iterator2 = ((AbstractSequentialList)object).iterator();
                while (iterator2.hasNext()) {
                    Ranges ranges = (Ranges)iterator2.next();
                    adder.add(ranges.start(), ranges.end(bl));
                }
            }
        }
    }

    public static void compact(Set<String> set, Adder adder, boolean bl) {
        StringRange.compact(set, adder, bl, false);
    }

    private static LinkedList<Ranges> compact(int n, Set<Ranges> set) {
        LinkedList<Ranges> linkedList = new LinkedList<Ranges>(set);
        for (int i = n - 1; i >= 0; --i) {
            Ranges ranges = null;
            Iterator iterator2 = linkedList.iterator();
            while (iterator2.hasNext()) {
                Ranges ranges2 = (Ranges)iterator2.next();
                if (ranges == null) {
                    ranges = ranges2;
                    continue;
                }
                if (ranges.merge(i, ranges2)) {
                    iterator2.remove();
                    continue;
                }
                ranges = ranges2;
            }
        }
        return linkedList;
    }

    public static Collection<String> expand(String string, String string2, boolean bl, Collection<String> collection) {
        if (string == null || string2 == null) {
            throw new ICUException("Range must have 2 valid strings");
        }
        int[] nArray = CharSequences.codePoints(string);
        int[] nArray2 = CharSequences.codePoints(string2);
        int n = nArray.length - nArray2.length;
        if (bl && n != 0) {
            throw new ICUException("Range must have equal-length strings");
        }
        if (n < 0) {
            throw new ICUException("Range must have start-length \u2265 end-length");
        }
        if (nArray2.length == 0) {
            throw new ICUException("Range must have end-length > 0");
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < n; ++i) {
            stringBuilder.appendCodePoint(nArray[i]);
        }
        StringRange.add(0, n, nArray, nArray2, stringBuilder, collection);
        return collection;
    }

    private static void add(int n, int n2, int[] nArray, int[] nArray2, StringBuilder stringBuilder, Collection<String> collection) {
        int n3 = nArray[n + n2];
        int n4 = nArray2[n];
        if (n3 > n4) {
            throw new ICUException("Range must have x\u1d62 \u2264 y\u1d62 for each index i");
        }
        boolean bl = n == nArray2.length - 1;
        int n5 = stringBuilder.length();
        for (int i = n3; i <= n4; ++i) {
            stringBuilder.appendCodePoint(i);
            if (bl) {
                collection.add(stringBuilder.toString());
            } else {
                StringRange.add(n + 1, n2, nArray, nArray2, stringBuilder, collection);
            }
            stringBuilder.setLength(n5);
        }
    }

    static final class Ranges
    implements Comparable<Ranges> {
        private final Range[] ranges;

        public Ranges(String string) {
            int[] nArray = CharSequences.codePoints(string);
            this.ranges = new Range[nArray.length];
            for (int i = 0; i < nArray.length; ++i) {
                this.ranges[i] = new Range(nArray[i], nArray[i]);
            }
        }

        public boolean merge(int n, Ranges ranges) {
            for (int i = this.ranges.length - 1; i >= 0; --i) {
                if (!(i == n ? this.ranges[i].max != ranges.ranges[i].min - 1 : !this.ranges[i].equals(ranges.ranges[i]))) continue;
                return true;
            }
            this.ranges[n].max = ranges.ranges[n].max;
            return false;
        }

        public String start() {
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < this.ranges.length; ++i) {
                stringBuilder.appendCodePoint(this.ranges[i].min);
            }
            return stringBuilder.toString();
        }

        public String end(boolean bl) {
            int n;
            int n2 = this.firstDifference();
            if (n2 == this.ranges.length) {
                return null;
            }
            StringBuilder stringBuilder = new StringBuilder();
            int n3 = n = bl ? n2 : 0;
            while (n < this.ranges.length) {
                stringBuilder.appendCodePoint(this.ranges[n].max);
                ++n;
            }
            return stringBuilder.toString();
        }

        public int firstDifference() {
            for (int i = 0; i < this.ranges.length; ++i) {
                if (this.ranges[i].min == this.ranges[i].max) continue;
                return i;
            }
            return this.ranges.length;
        }

        public Integer size() {
            return this.ranges.length;
        }

        @Override
        public int compareTo(Ranges ranges) {
            int n = this.ranges.length - ranges.ranges.length;
            if (n != 0) {
                return n;
            }
            for (int i = 0; i < this.ranges.length; ++i) {
                n = this.ranges[i].compareTo(ranges.ranges[i]);
                if (n == 0) continue;
                return n;
            }
            return 1;
        }

        public String toString() {
            String string = this.start();
            String string2 = this.end(true);
            return string2 == null ? string : string + "~" + string2;
        }

        @Override
        public int compareTo(Object object) {
            return this.compareTo((Ranges)object);
        }
    }

    static final class Range
    implements Comparable<Range> {
        int min;
        int max;

        public Range(int n, int n2) {
            this.min = n;
            this.max = n2;
        }

        public boolean equals(Object object) {
            return this == object || object != null && object instanceof Range && this.compareTo((Range)object) == 0;
        }

        @Override
        public int compareTo(Range range) {
            int n = this.min - range.min;
            if (n != 0) {
                return n;
            }
            return this.max - range.max;
        }

        public int hashCode() {
            return this.min * 37 + this.max;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder().appendCodePoint(this.min);
            return this.min == this.max ? stringBuilder.toString() : stringBuilder.append('~').appendCodePoint(this.max).toString();
        }

        @Override
        public int compareTo(Object object) {
            return this.compareTo((Range)object);
        }
    }

    public static interface Adder {
        public void add(String var1, String var2);
    }
}

