/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.util;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import it.unimi.dsi.fastutil.Swapper;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntComparator;
import it.unimi.dsi.fastutil.ints.IntList;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SuffixArray<T> {
    private static final boolean DEBUG_PRINT_COMPARISONS = Boolean.parseBoolean(System.getProperty("SuffixArray.printComparisons", "false"));
    private static final boolean DEBUG_PRINT_ARRAY = Boolean.parseBoolean(System.getProperty("SuffixArray.printArray", "false"));
    private static final Logger LOGGER = LogManager.getLogger();
    protected final List<T> list = Lists.newArrayList();
    private final IntList chars = new IntArrayList();
    private final IntList wordStarts = new IntArrayList();
    private IntList suffixToT = new IntArrayList();
    private IntList offsets = new IntArrayList();
    private int maxStringLength;

    public void add(T t, String string) {
        this.maxStringLength = Math.max(this.maxStringLength, string.length());
        int n = this.list.size();
        this.list.add(t);
        this.wordStarts.add(this.chars.size());
        for (int i = 0; i < string.length(); ++i) {
            this.suffixToT.add(n);
            this.offsets.add(i);
            this.chars.add(string.charAt(i));
        }
        this.suffixToT.add(n);
        this.offsets.add(string.length());
        this.chars.add(-1);
    }

    public void generate() {
        int n;
        int n2 = this.chars.size();
        int[] nArray = new int[n2];
        int[] nArray2 = new int[n2];
        int[] nArray3 = new int[n2];
        int[] nArray4 = new int[n2];
        IntComparator intComparator = new IntComparator(){
            final int[] val$aint1;
            final int[] val$aint2;
            final SuffixArray this$0;
            {
                this.this$0 = suffixArray;
                this.val$aint1 = nArray;
                this.val$aint2 = nArray2;
            }

            @Override
            public int compare(int n, int n2) {
                return this.val$aint1[n] == this.val$aint1[n2] ? Integer.compare(this.val$aint2[n], this.val$aint2[n2]) : Integer.compare(this.val$aint1[n], this.val$aint1[n2]);
            }

            @Override
            public int compare(Integer n, Integer n2) {
                return this.compare((int)n, (int)n2);
            }

            @Override
            public int compare(Object object, Object object2) {
                return this.compare((Integer)object, (Integer)object2);
            }
        };
        Swapper swapper = (arg_0, arg_1) -> SuffixArray.lambda$generate$0(nArray2, nArray3, nArray4, arg_0, arg_1);
        for (n = 0; n < n2; ++n) {
            nArray[n] = this.chars.getInt(n);
        }
        n = 1;
        int n3 = Math.min(n2, this.maxStringLength);
        while (n * 2 < n3) {
            int n4 = 0;
            while (n4 < n2) {
                nArray2[n4] = nArray[n4];
                nArray3[n4] = n4 + n < n2 ? nArray[n4 + n] : -2;
                nArray4[n4] = n4++;
            }
            it.unimi.dsi.fastutil.Arrays.quickSort(0, n2, intComparator, swapper);
            for (n4 = 0; n4 < n2; ++n4) {
                nArray[nArray4[n4]] = n4 > 0 && nArray2[n4] == nArray2[n4 - 1] && nArray3[n4] == nArray3[n4 - 1] ? nArray[nArray4[n4 - 1]] : n4;
            }
            n *= 2;
        }
        IntList intList = this.suffixToT;
        IntList intList2 = this.offsets;
        this.suffixToT = new IntArrayList(intList.size());
        this.offsets = new IntArrayList(intList2.size());
        for (int i = 0; i < n2; ++i) {
            int n5 = nArray4[i];
            this.suffixToT.add(intList.getInt(n5));
            this.offsets.add(intList2.getInt(n5));
        }
        if (DEBUG_PRINT_ARRAY) {
            this.printArray();
        }
    }

    private void printArray() {
        for (int i = 0; i < this.suffixToT.size(); ++i) {
            LOGGER.debug("{} {}", (Object)i, (Object)this.getString(i));
        }
        LOGGER.debug("");
    }

    private String getString(int n) {
        int n2 = this.offsets.getInt(n);
        int n3 = this.wordStarts.getInt(this.suffixToT.getInt(n));
        StringBuilder stringBuilder = new StringBuilder();
        int n4 = 0;
        while (n3 + n4 < this.chars.size()) {
            int n5;
            if (n4 == n2) {
                stringBuilder.append('^');
            }
            if ((n5 = this.chars.get(n3 + n4).intValue()) == -1) break;
            stringBuilder.append((char)n5);
            ++n4;
        }
        return stringBuilder.toString();
    }

    private int compare(String string, int n) {
        int n2 = this.wordStarts.getInt(this.suffixToT.getInt(n));
        int n3 = this.offsets.getInt(n);
        for (int i = 0; i < string.length(); ++i) {
            char c;
            int n4 = this.chars.getInt(n2 + n3 + i);
            if (n4 == -1) {
                return 0;
            }
            char c2 = string.charAt(i);
            if (c2 < (c = (char)n4)) {
                return 1;
            }
            if (c2 <= c) continue;
            return 0;
        }
        return 1;
    }

    public List<T> search(String string) {
        int n;
        int n2;
        int n3 = this.suffixToT.size();
        int n4 = 0;
        int n5 = n3;
        while (n4 < n5) {
            n2 = n4 + (n5 - n4) / 2;
            n = this.compare(string, n2);
            if (DEBUG_PRINT_COMPARISONS) {
                LOGGER.debug("comparing lower \"{}\" with {} \"{}\": {}", (Object)string, (Object)n2, (Object)this.getString(n2), (Object)n);
            }
            if (n > 0) {
                n4 = n2 + 1;
                continue;
            }
            n5 = n2;
        }
        if (n4 >= 0 && n4 < n3) {
            n2 = n4;
            n5 = n3;
            while (n4 < n5) {
                n = n4 + (n5 - n4) / 2;
                int n6 = this.compare(string, n);
                if (DEBUG_PRINT_COMPARISONS) {
                    LOGGER.debug("comparing upper \"{}\" with {} \"{}\": {}", (Object)string, (Object)n, (Object)this.getString(n), (Object)n6);
                }
                if (n6 >= 0) {
                    n4 = n + 1;
                    continue;
                }
                n5 = n;
            }
            n = n4;
            IntOpenHashSet intOpenHashSet = new IntOpenHashSet();
            for (int i = n2; i < n; ++i) {
                intOpenHashSet.add(this.suffixToT.getInt(i));
            }
            int[] nArray = intOpenHashSet.toIntArray();
            Arrays.sort(nArray);
            LinkedHashSet<T> linkedHashSet = Sets.newLinkedHashSet();
            for (int n7 : nArray) {
                linkedHashSet.add(this.list.get(n7));
            }
            return Lists.newArrayList(linkedHashSet);
        }
        return Collections.emptyList();
    }

    private static void lambda$generate$0(int[] nArray, int[] nArray2, int[] nArray3, int n, int n2) {
        if (n != n2) {
            int n3 = nArray[n];
            nArray[n] = nArray[n2];
            nArray[n2] = n3;
            n3 = nArray2[n];
            nArray2[n] = nArray2[n2];
            nArray2[n2] = n3;
            n3 = nArray3[n];
            nArray3[n] = nArray3[n2];
            nArray3[n2] = n3;
        }
    }
}

