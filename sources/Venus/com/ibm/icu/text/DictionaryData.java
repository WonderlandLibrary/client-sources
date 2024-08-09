/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.text;

import com.ibm.icu.impl.Assert;
import com.ibm.icu.impl.ICUBinary;
import com.ibm.icu.impl.ICUResourceBundle;
import com.ibm.icu.text.BytesDictionaryMatcher;
import com.ibm.icu.text.CharsDictionaryMatcher;
import com.ibm.icu.text.DictionaryMatcher;
import com.ibm.icu.util.UResourceBundle;
import java.io.IOException;
import java.nio.ByteBuffer;

final class DictionaryData {
    public static final int TRIE_TYPE_BYTES = 0;
    public static final int TRIE_TYPE_UCHARS = 1;
    public static final int TRIE_TYPE_MASK = 7;
    public static final int TRIE_HAS_VALUES = 8;
    public static final int TRANSFORM_NONE = 0;
    public static final int TRANSFORM_TYPE_OFFSET = 0x1000000;
    public static final int TRANSFORM_TYPE_MASK = 0x7F000000;
    public static final int TRANSFORM_OFFSET_MASK = 0x1FFFFF;
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

    public static DictionaryMatcher loadDictionaryFor(String string) throws IOException {
        int n;
        int n2;
        ICUResourceBundle iCUResourceBundle = (ICUResourceBundle)UResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt66b/brkitr");
        String string2 = iCUResourceBundle.getStringWithFallback("dictionaries/" + string);
        string2 = "brkitr/" + string2;
        ByteBuffer byteBuffer = ICUBinary.getRequiredData(string2);
        ICUBinary.readHeader(byteBuffer, 1147757428, null);
        int[] nArray = new int[8];
        for (n2 = 0; n2 < 8; ++n2) {
            nArray[n2] = byteBuffer.getInt();
        }
        n2 = nArray[0];
        Assert.assrt(n2 >= 32);
        if (n2 > 32) {
            n = n2 - 32;
            ICUBinary.skipBytes(byteBuffer, n);
        }
        n = nArray[4] & 7;
        int n3 = nArray[3] - n2;
        DictionaryMatcher dictionaryMatcher = null;
        if (n == 0) {
            int n4 = nArray[5];
            byte[] byArray = new byte[n3];
            byteBuffer.get(byArray);
            dictionaryMatcher = new BytesDictionaryMatcher(byArray, n4);
        } else if (n == 1) {
            Assert.assrt(n3 % 2 == 0);
            String string3 = ICUBinary.getString(byteBuffer, n3 / 2, n3 & 1);
            dictionaryMatcher = new CharsDictionaryMatcher(string3);
        } else {
            dictionaryMatcher = null;
        }
        return dictionaryMatcher;
    }
}

