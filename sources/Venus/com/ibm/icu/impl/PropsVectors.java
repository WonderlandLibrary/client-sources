/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl;

import com.ibm.icu.impl.IntTrie;
import com.ibm.icu.impl.IntTrieBuilder;
import com.ibm.icu.impl.PVecToTrieCompactHandler;
import com.ibm.icu.impl.Trie;
import com.ibm.icu.impl.TrieBuilder;
import java.util.Arrays;
import java.util.Comparator;

public class PropsVectors {
    private int[] v;
    private int columns;
    private int maxRows;
    private int rows;
    private int prevRow;
    private boolean isCompacted;
    public static final int FIRST_SPECIAL_CP = 0x110000;
    public static final int INITIAL_VALUE_CP = 0x110000;
    public static final int ERROR_VALUE_CP = 0x110001;
    public static final int MAX_CP = 0x110001;
    public static final int INITIAL_ROWS = 4096;
    public static final int MEDIUM_ROWS = 65536;
    public static final int MAX_ROWS = 0x110002;

    private boolean areElementsSame(int n, int[] nArray, int n2, int n3) {
        for (int i = 0; i < n3; ++i) {
            if (this.v[n + i] == nArray[n2 + i]) continue;
            return true;
        }
        return false;
    }

    private int findRow(int n) {
        int n2 = 0;
        n2 = this.prevRow * this.columns;
        if (n >= this.v[n2]) {
            if (n < this.v[n2 + 1]) {
                return n2;
            }
            if (n < this.v[(n2 += this.columns) + 1]) {
                ++this.prevRow;
                return n2;
            }
            if (n < this.v[(n2 += this.columns) + 1]) {
                this.prevRow += 2;
                return n2;
            }
            if (n - this.v[n2 + 1] < 10) {
                this.prevRow += 2;
                do {
                    ++this.prevRow;
                } while (n >= this.v[(n2 += this.columns) + 1]);
                return n2;
            }
        } else if (n < this.v[1]) {
            this.prevRow = 0;
            return 1;
        }
        int n3 = 0;
        int n4 = 0;
        int n5 = this.rows;
        while (n3 < n5 - 1) {
            n4 = (n3 + n5) / 2;
            n2 = this.columns * n4;
            if (n < this.v[n2]) {
                n5 = n4;
                continue;
            }
            if (n < this.v[n2 + 1]) {
                this.prevRow = n4;
                return n2;
            }
            n3 = n4;
        }
        this.prevRow = n3;
        n2 = n3 * this.columns;
        return n2;
    }

    public PropsVectors(int n) {
        if (n < 1) {
            throw new IllegalArgumentException("numOfColumns need to be no less than 1; but it is " + n);
        }
        this.columns = n + 2;
        this.v = new int[4096 * this.columns];
        this.maxRows = 4096;
        this.rows = 3;
        this.prevRow = 0;
        this.isCompacted = false;
        this.v[0] = 0;
        this.v[1] = 0x110000;
        int n2 = this.columns;
        for (int i = 0x110000; i <= 0x110001; ++i) {
            this.v[n2] = i;
            this.v[n2 + 1] = i + 1;
            n2 += this.columns;
        }
    }

    public void setValue(int n, int n2, int n3, int n4, int n5) {
        boolean bl;
        if (n < 0 || n > n2 || n2 > 0x110001 || n3 < 0 || n3 >= this.columns - 2) {
            throw new IllegalArgumentException();
        }
        if (this.isCompacted) {
            throw new IllegalStateException("Shouldn't be called aftercompact()!");
        }
        int n6 = n2 + 1;
        int n7 = this.findRow(n);
        int n8 = this.findRow(n2);
        boolean bl2 = n != this.v[n7] && (n4 &= n5) != (this.v[n7 + (n3 += 2)] & n5);
        boolean bl3 = bl = n6 != this.v[n8 + 1] && n4 != (this.v[n8 + n3] & n5);
        if (bl2 || bl) {
            int n9;
            int n10 = 0;
            if (bl2) {
                ++n10;
            }
            if (bl) {
                ++n10;
            }
            int n11 = 0;
            if (this.rows + n10 > this.maxRows) {
                if (this.maxRows < 65536) {
                    n11 = 65536;
                } else if (this.maxRows < 0x110002) {
                    n11 = 0x110002;
                } else {
                    throw new IndexOutOfBoundsException("MAX_ROWS exceeded! Increase it to a higher valuein the implementation");
                }
                int[] nArray = new int[n11 * this.columns];
                System.arraycopy(this.v, 0, nArray, 0, this.rows * this.columns);
                this.v = nArray;
                this.maxRows = n11;
            }
            if ((n9 = this.rows * this.columns - (n8 + this.columns)) > 0) {
                System.arraycopy(this.v, n8 + this.columns, this.v, n8 + (1 + n10) * this.columns, n9);
            }
            this.rows += n10;
            if (bl2) {
                n9 = n8 - n7 + this.columns;
                System.arraycopy(this.v, n7, this.v, n7 + this.columns, n9);
                n8 += this.columns;
                int n12 = n;
                this.v[n7 + this.columns] = n12;
                this.v[n7 + 1] = n12;
                n7 += this.columns;
            }
            if (bl) {
                System.arraycopy(this.v, n8, this.v, n8 + this.columns, this.columns);
                int n13 = n6;
                this.v[n8 + this.columns] = n13;
                this.v[n8 + 1] = n13;
            }
        }
        this.prevRow = n8 / this.columns;
        n7 += n3;
        n8 += n3;
        n5 ^= 0xFFFFFFFF;
        while (true) {
            this.v[n7] = this.v[n7] & n5 | n4;
            if (n7 == n8) break;
            n7 += this.columns;
        }
    }

    public int getValue(int n, int n2) {
        if (this.isCompacted || n < 0 || n > 0x110001 || n2 < 0 || n2 >= this.columns - 2) {
            return 1;
        }
        int n3 = this.findRow(n);
        return this.v[n3 + 2 + n2];
    }

    public int[] getRow(int n) {
        if (this.isCompacted) {
            throw new IllegalStateException("Illegal Invocation of the method after compact()");
        }
        if (n < 0 || n > this.rows) {
            throw new IllegalArgumentException("rowIndex out of bound!");
        }
        int[] nArray = new int[this.columns - 2];
        System.arraycopy(this.v, n * this.columns + 2, nArray, 0, this.columns - 2);
        return nArray;
    }

    public int getRowStart(int n) {
        if (this.isCompacted) {
            throw new IllegalStateException("Illegal Invocation of the method after compact()");
        }
        if (n < 0 || n > this.rows) {
            throw new IllegalArgumentException("rowIndex out of bound!");
        }
        return this.v[n * this.columns];
    }

    public int getRowEnd(int n) {
        if (this.isCompacted) {
            throw new IllegalStateException("Illegal Invocation of the method after compact()");
        }
        if (n < 0 || n > this.rows) {
            throw new IllegalArgumentException("rowIndex out of bound!");
        }
        return this.v[n * this.columns + 1] - 1;
    }

    public void compact(CompactHandler compactHandler) {
        int n;
        int n2;
        if (this.isCompacted) {
            return;
        }
        this.isCompacted = true;
        int n3 = this.columns - 2;
        Integer[] integerArray = new Integer[this.rows];
        for (n2 = 0; n2 < this.rows; ++n2) {
            integerArray[n2] = this.columns * n2;
        }
        Arrays.sort(integerArray, new Comparator<Integer>(this){
            final PropsVectors this$0;
            {
                this.this$0 = propsVectors;
            }

            @Override
            public int compare(Integer n, Integer n2) {
                int n3 = n;
                int n4 = n2;
                int n5 = PropsVectors.access$000(this.this$0);
                int n6 = 2;
                do {
                    if (PropsVectors.access$100(this.this$0)[n3 + n6] != PropsVectors.access$100(this.this$0)[n4 + n6]) {
                        return PropsVectors.access$100(this.this$0)[n3 + n6] < PropsVectors.access$100(this.this$0)[n4 + n6] ? -1 : 1;
                    }
                    if (++n6 != PropsVectors.access$000(this.this$0)) continue;
                    n6 = 0;
                } while (--n5 > 0);
                return 1;
            }

            @Override
            public int compare(Object object, Object object2) {
                return this.compare((Integer)object, (Integer)object2);
            }
        });
        n2 = -n3;
        for (int i = 0; i < this.rows; ++i) {
            n = this.v[integerArray[i]];
            if (n2 < 0 || !this.areElementsSame(integerArray[i] + 2, this.v, integerArray[i - 1] + 2, n3)) {
                n2 += n3;
            }
            if (n == 0x110000) {
                compactHandler.setRowIndexForInitialValue(n2);
                continue;
            }
            if (n != 0x110001) continue;
            compactHandler.setRowIndexForErrorValue(n2);
        }
        compactHandler.startRealValues(n2 += n3);
        int[] nArray = new int[n2];
        n2 = -n3;
        for (n = 0; n < this.rows; ++n) {
            int n4 = this.v[integerArray[n]];
            int n5 = this.v[integerArray[n] + 1];
            if (n2 < 0 || !this.areElementsSame(integerArray[n] + 2, nArray, n2, n3)) {
                System.arraycopy(this.v, integerArray[n] + 2, nArray, n2 += n3, n3);
            }
            if (n4 >= 0x110000) continue;
            compactHandler.setRowIndexForRange(n4, n5 - 1, n2);
        }
        this.v = nArray;
        this.rows = n2 / n3 + 1;
    }

    public int[] getCompactedArray() {
        if (!this.isCompacted) {
            throw new IllegalStateException("Illegal Invocation of the method before compact()");
        }
        return this.v;
    }

    public int getCompactedRows() {
        if (!this.isCompacted) {
            throw new IllegalStateException("Illegal Invocation of the method before compact()");
        }
        return this.rows;
    }

    public int getCompactedColumns() {
        if (!this.isCompacted) {
            throw new IllegalStateException("Illegal Invocation of the method before compact()");
        }
        return this.columns - 2;
    }

    public IntTrie compactToTrieWithRowIndexes() {
        PVecToTrieCompactHandler pVecToTrieCompactHandler = new PVecToTrieCompactHandler();
        this.compact(pVecToTrieCompactHandler);
        return pVecToTrieCompactHandler.builder.serialize(new DefaultGetFoldedValue(pVecToTrieCompactHandler.builder), new DefaultGetFoldingOffset(null));
    }

    static int access$000(PropsVectors propsVectors) {
        return propsVectors.columns;
    }

    static int[] access$100(PropsVectors propsVectors) {
        return propsVectors.v;
    }

    public static interface CompactHandler {
        public void setRowIndexForRange(int var1, int var2, int var3);

        public void setRowIndexForInitialValue(int var1);

        public void setRowIndexForErrorValue(int var1);

        public void startRealValues(int var1);
    }

    private static class DefaultGetFoldedValue
    implements TrieBuilder.DataManipulate {
        private IntTrieBuilder builder;

        public DefaultGetFoldedValue(IntTrieBuilder intTrieBuilder) {
            this.builder = intTrieBuilder;
        }

        @Override
        public int getFoldedValue(int n, int n2) {
            int n3 = this.builder.m_initialValue_;
            int n4 = n + 1024;
            while (n < n4) {
                boolean[] blArray = new boolean[1];
                int n5 = this.builder.getValue(n, blArray);
                if (blArray[0]) {
                    n += 32;
                    continue;
                }
                if (n5 != n3) {
                    return n2;
                }
                ++n;
            }
            return 1;
        }
    }

    private static class DefaultGetFoldingOffset
    implements Trie.DataManipulate {
        private DefaultGetFoldingOffset() {
        }

        @Override
        public int getFoldingOffset(int n) {
            return n;
        }

        DefaultGetFoldingOffset(1 var1_1) {
            this();
        }
    }
}

