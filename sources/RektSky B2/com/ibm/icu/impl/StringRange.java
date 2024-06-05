package com.ibm.icu.impl;

import java.util.*;
import com.ibm.icu.util.*;
import com.ibm.icu.lang.*;

public class StringRange
{
    private static final boolean DEBUG = false;
    public static final Comparator<int[]> COMPARE_INT_ARRAYS;
    
    public static void compact(final Set<String> source, final Adder adder, final boolean shorterPairs, final boolean moreCompact) {
        if (!moreCompact) {
            String start = null;
            String end = null;
            int lastCp = 0;
            int prefixLen = 0;
            for (final String s : source) {
                if (start != null) {
                    if (s.regionMatches(0, start, 0, prefixLen)) {
                        final int currentCp = s.codePointAt(prefixLen);
                        if (currentCp == 1 + lastCp && s.length() == prefixLen + Character.charCount(currentCp)) {
                            end = s;
                            lastCp = currentCp;
                            continue;
                        }
                    }
                    adder.add(start, (end == null) ? null : (shorterPairs ? end.substring(prefixLen, end.length()) : end));
                }
                start = s;
                end = null;
                lastCp = s.codePointBefore(s.length());
                prefixLen = s.length() - Character.charCount(lastCp);
            }
            adder.add(start, (end == null) ? null : (shorterPairs ? end.substring(prefixLen, end.length()) : end));
        }
        else {
            final Relation<Integer, Ranges> lengthToArrays = Relation.of(new TreeMap<Integer, Set<Ranges>>(), TreeSet.class);
            for (final String s2 : source) {
                final Ranges item = new Ranges(s2);
                lengthToArrays.put(item.size(), item);
            }
            for (final Map.Entry<Integer, Set<Ranges>> entry : lengthToArrays.keyValuesSet()) {
                final LinkedList<Ranges> compacted = compact(entry.getKey(), entry.getValue());
                for (final Ranges ranges : compacted) {
                    adder.add(ranges.start(), ranges.end(shorterPairs));
                }
            }
        }
    }
    
    public static void compact(final Set<String> source, final Adder adder, final boolean shorterPairs) {
        compact(source, adder, shorterPairs, false);
    }
    
    private static LinkedList<Ranges> compact(final int size, final Set<Ranges> inputRanges) {
        final LinkedList<Ranges> ranges = new LinkedList<Ranges>(inputRanges);
        for (int i = size - 1; i >= 0; --i) {
            Ranges last = null;
            final Iterator<Ranges> it = ranges.iterator();
            while (it.hasNext()) {
                final Ranges item = it.next();
                if (last == null) {
                    last = item;
                }
                else if (last.merge(i, item)) {
                    it.remove();
                }
                else {
                    last = item;
                }
            }
        }
        return ranges;
    }
    
    public static Collection<String> expand(final String start, final String end, final boolean requireSameLength, final Collection<String> output) {
        if (start == null || end == null) {
            throw new ICUException("Range must have 2 valid strings");
        }
        final int[] startCps = CharSequences.codePoints(start);
        final int[] endCps = CharSequences.codePoints(end);
        final int startOffset = startCps.length - endCps.length;
        if (requireSameLength && startOffset != 0) {
            throw new ICUException("Range must have equal-length strings");
        }
        if (startOffset < 0) {
            throw new ICUException("Range must have start-length \u2265 end-length");
        }
        if (endCps.length == 0) {
            throw new ICUException("Range must have end-length > 0");
        }
        final StringBuilder builder = new StringBuilder();
        for (int i = 0; i < startOffset; ++i) {
            builder.appendCodePoint(startCps[i]);
        }
        add(0, startOffset, startCps, endCps, builder, output);
        return output;
    }
    
    private static void add(final int endIndex, final int startOffset, final int[] starts, final int[] ends, final StringBuilder builder, final Collection<String> output) {
        final int start = starts[endIndex + startOffset];
        final int end = ends[endIndex];
        if (start > end) {
            throw new ICUException("Range must have x\u1d62 \u2264 y\u1d62 for each index i");
        }
        final boolean last = endIndex == ends.length - 1;
        final int startLen = builder.length();
        for (int i = start; i <= end; ++i) {
            builder.appendCodePoint(i);
            if (last) {
                output.add(builder.toString());
            }
            else {
                add(endIndex + 1, startOffset, starts, ends, builder, output);
            }
            builder.setLength(startLen);
        }
    }
    
    static {
        COMPARE_INT_ARRAYS = new Comparator<int[]>() {
            @Override
            public int compare(final int[] o1, final int[] o2) {
                for (int minIndex = Math.min(o1.length, o2.length), i = 0; i < minIndex; ++i) {
                    final int diff = o1[i] - o2[i];
                    if (diff != 0) {
                        return diff;
                    }
                }
                return o1.length - o2.length;
            }
        };
    }
    
    static final class Range implements Comparable<Range>
    {
        int min;
        int max;
        
        public Range(final int min, final int max) {
            this.min = min;
            this.max = max;
        }
        
        @Override
        public boolean equals(final Object obj) {
            return this == obj || (obj != null && obj instanceof Range && this.compareTo((Range)obj) == 0);
        }
        
        @Override
        public int compareTo(final Range that) {
            final int diff = this.min - that.min;
            if (diff != 0) {
                return diff;
            }
            return this.max - that.max;
        }
        
        @Override
        public int hashCode() {
            return this.min * 37 + this.max;
        }
        
        @Override
        public String toString() {
            final StringBuilder result = new StringBuilder().appendCodePoint(this.min);
            return (this.min == this.max) ? result.toString() : result.append('~').appendCodePoint(this.max).toString();
        }
    }
    
    static final class Ranges implements Comparable<Ranges>
    {
        private final Range[] ranges;
        
        public Ranges(final String s) {
            final int[] array = CharSequences.codePoints(s);
            this.ranges = new Range[array.length];
            for (int i = 0; i < array.length; ++i) {
                this.ranges[i] = new Range(array[i], array[i]);
            }
        }
        
        public boolean merge(final int pivot, final Ranges other) {
            for (int i = this.ranges.length - 1; i >= 0; --i) {
                if (i == pivot) {
                    if (this.ranges[i].max != other.ranges[i].min - 1) {
                        return false;
                    }
                }
                else if (!this.ranges[i].equals(other.ranges[i])) {
                    return false;
                }
            }
            this.ranges[pivot].max = other.ranges[pivot].max;
            return true;
        }
        
        public String start() {
            final StringBuilder result = new StringBuilder();
            for (int i = 0; i < this.ranges.length; ++i) {
                result.appendCodePoint(this.ranges[i].min);
            }
            return result.toString();
        }
        
        public String end(final boolean mostCompact) {
            final int firstDiff = this.firstDifference();
            if (firstDiff == this.ranges.length) {
                return null;
            }
            final StringBuilder result = new StringBuilder();
            for (int i = mostCompact ? firstDiff : 0; i < this.ranges.length; ++i) {
                result.appendCodePoint(this.ranges[i].max);
            }
            return result.toString();
        }
        
        public int firstDifference() {
            for (int i = 0; i < this.ranges.length; ++i) {
                if (this.ranges[i].min != this.ranges[i].max) {
                    return i;
                }
            }
            return this.ranges.length;
        }
        
        public Integer size() {
            return this.ranges.length;
        }
        
        @Override
        public int compareTo(final Ranges other) {
            int diff = this.ranges.length - other.ranges.length;
            if (diff != 0) {
                return diff;
            }
            for (int i = 0; i < this.ranges.length; ++i) {
                diff = this.ranges[i].compareTo(other.ranges[i]);
                if (diff != 0) {
                    return diff;
                }
            }
            return 0;
        }
        
        @Override
        public String toString() {
            final String start = this.start();
            final String end = this.end(false);
            return (end == null) ? start : (start + "~" + end);
        }
    }
    
    public interface Adder
    {
        void add(final String p0, final String p1);
    }
}
