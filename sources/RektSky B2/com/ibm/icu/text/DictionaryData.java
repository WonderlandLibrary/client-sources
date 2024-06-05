package com.ibm.icu.text;

import com.ibm.icu.util.*;
import com.ibm.icu.impl.*;
import java.nio.*;
import java.io.*;

final class DictionaryData
{
    public static final int TRIE_TYPE_BYTES = 0;
    public static final int TRIE_TYPE_UCHARS = 1;
    public static final int TRIE_TYPE_MASK = 7;
    public static final int TRIE_HAS_VALUES = 8;
    public static final int TRANSFORM_NONE = 0;
    public static final int TRANSFORM_TYPE_OFFSET = 16777216;
    public static final int TRANSFORM_TYPE_MASK = 2130706432;
    public static final int TRANSFORM_OFFSET_MASK = 2097151;
    public static final int IX_STRING_TRIE_OFFSET = 0;
    public static final int IX_RESERVED1_OFFSET = 1;
    public static final int IX_RESERVED2_OFFSET = 2;
    public static final int IX_TOTAL_SIZE = 3;
    public static final int IX_TRIE_TYPE = 4;
    public static final int IX_TRANSFORM = 5;
    public static final int IX_RESERVED6 = 6;
    public static final int IX_RESERVED7 = 7;
    public static final int IX_COUNT = 8;
    private static final int DATA_FORMAT_ID = 1147757428;
    
    private DictionaryData() {
    }
    
    public static DictionaryMatcher loadDictionaryFor(final String dictType) throws IOException {
        final ICUResourceBundle rb = (ICUResourceBundle)UResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt62b/brkitr");
        String dictFileName = rb.getStringWithFallback("dictionaries/" + dictType);
        dictFileName = "brkitr/" + dictFileName;
        final ByteBuffer bytes = ICUBinary.getRequiredData(dictFileName);
        ICUBinary.readHeader(bytes, 1147757428, null);
        final int[] indexes = new int[8];
        for (int i = 0; i < 8; ++i) {
            indexes[i] = bytes.getInt();
        }
        final int offset = indexes[0];
        Assert.assrt(offset >= 32);
        if (offset > 32) {
            final int diff = offset - 32;
            ICUBinary.skipBytes(bytes, diff);
        }
        final int trieType = indexes[4] & 0x7;
        final int totalSize = indexes[3] - offset;
        DictionaryMatcher m = null;
        if (trieType == 0) {
            final int transform = indexes[5];
            final byte[] data = new byte[totalSize];
            bytes.get(data);
            m = new BytesDictionaryMatcher(data, transform);
        }
        else if (trieType == 1) {
            Assert.assrt(totalSize % 2 == 0);
            final String data2 = ICUBinary.getString(bytes, totalSize / 2, totalSize & 0x1);
            m = new CharsDictionaryMatcher(data2);
        }
        else {
            m = null;
        }
        return m;
    }
}
