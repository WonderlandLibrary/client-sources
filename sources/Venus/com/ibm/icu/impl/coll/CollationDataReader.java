/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl.coll;

import com.ibm.icu.impl.ICUBinary;
import com.ibm.icu.impl.Trie2_32;
import com.ibm.icu.impl.USerializedSet;
import com.ibm.icu.impl.coll.CollationData;
import com.ibm.icu.impl.coll.CollationFastLatin;
import com.ibm.icu.impl.coll.CollationSettings;
import com.ibm.icu.impl.coll.CollationTailoring;
import com.ibm.icu.text.UnicodeSet;
import com.ibm.icu.util.ICUException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.util.Arrays;

final class CollationDataReader {
    static final int IX_INDEXES_LENGTH = 0;
    static final int IX_OPTIONS = 1;
    static final int IX_RESERVED2 = 2;
    static final int IX_RESERVED3 = 3;
    static final int IX_JAMO_CE32S_START = 4;
    static final int IX_REORDER_CODES_OFFSET = 5;
    static final int IX_REORDER_TABLE_OFFSET = 6;
    static final int IX_TRIE_OFFSET = 7;
    static final int IX_RESERVED8_OFFSET = 8;
    static final int IX_CES_OFFSET = 9;
    static final int IX_RESERVED10_OFFSET = 10;
    static final int IX_CE32S_OFFSET = 11;
    static final int IX_ROOT_ELEMENTS_OFFSET = 12;
    static final int IX_CONTEXTS_OFFSET = 13;
    static final int IX_UNSAFE_BWD_OFFSET = 14;
    static final int IX_FAST_LATIN_TABLE_OFFSET = 15;
    static final int IX_SCRIPTS_OFFSET = 16;
    static final int IX_COMPRESSIBLE_BYTES_OFFSET = 17;
    static final int IX_RESERVED18_OFFSET = 18;
    static final int IX_TOTAL_SIZE = 19;
    private static final IsAcceptable IS_ACCEPTABLE;
    private static final int DATA_FORMAT = 1430482796;
    static final boolean $assertionsDisabled;

    static void read(CollationTailoring collationTailoring, ByteBuffer byteBuffer, CollationTailoring collationTailoring2) throws IOException {
        int n;
        int n2;
        int[] nArray;
        int n3;
        int n4;
        int n5;
        collationTailoring2.version = ICUBinary.readHeader(byteBuffer, 1430482796, IS_ACCEPTABLE);
        if (collationTailoring != null && collationTailoring.getUCAVersion() != collationTailoring2.getUCAVersion()) {
            throw new ICUException("Tailoring UCA version differs from base data UCA version");
        }
        int n6 = byteBuffer.remaining();
        if (n6 < 8) {
            throw new ICUException("not enough bytes");
        }
        int n7 = byteBuffer.getInt();
        if (n7 < 2 || n6 < n7 * 4) {
            throw new ICUException("not enough indexes");
        }
        int[] nArray2 = new int[20];
        nArray2[0] = n7;
        for (n5 = 1; n5 < n7 && n5 < nArray2.length; ++n5) {
            nArray2[n5] = byteBuffer.getInt();
        }
        for (n5 = n7; n5 < nArray2.length; ++n5) {
            nArray2[n5] = -1;
        }
        if (n7 > nArray2.length) {
            ICUBinary.skipBytes(byteBuffer, (n7 - nArray2.length) * 4);
        }
        if (n6 < (n4 = n7 > 19 ? nArray2[19] : (n7 > 5 ? nArray2[n7 - 1] : 0))) {
            throw new ICUException("not enough bytes");
        }
        CollationData collationData = collationTailoring == null ? null : collationTailoring.data;
        n5 = 5;
        int n8 = nArray2[n5];
        n4 = nArray2[n5 + 1] - n8;
        if (n4 >= 4) {
            int n9;
            if (collationData == null) {
                throw new ICUException("Collation base data must not reorder scripts");
            }
            n3 = n4 / 4;
            nArray = ICUBinary.getInts(byteBuffer, n3, n4 & 3);
            for (n9 = 0; n9 < n3 && (nArray[n3 - n9 - 1] & 0xFFFF0000) != 0; ++n9) {
            }
            if (!$assertionsDisabled && n9 >= n3) {
                throw new AssertionError();
            }
            n3 -= n9;
        } else {
            nArray = new int[]{};
            n3 = 0;
            ICUBinary.skipBytes(byteBuffer, n4);
        }
        byte[] byArray = null;
        n5 = 6;
        n8 = nArray2[n5];
        n4 = nArray2[n5 + 1] - n8;
        if (n4 >= 256) {
            if (n3 == 0) {
                throw new ICUException("Reordering table without reordering codes");
            }
            byArray = new byte[256];
            byteBuffer.get(byArray);
            n4 -= 256;
        }
        ICUBinary.skipBytes(byteBuffer, n4);
        if (collationData != null && collationData.numericPrimary != ((long)nArray2[1] & 0xFF000000L)) {
            throw new ICUException("Tailoring numeric primary weight differs from base data");
        }
        CollationData collationData2 = null;
        n5 = 7;
        n8 = nArray2[n5];
        n4 = nArray2[n5 + 1] - n8;
        if (n4 >= 8) {
            collationTailoring2.ensureOwnedData();
            collationData2 = collationTailoring2.ownedData;
            collationData2.base = collationData;
            collationData2.numericPrimary = (long)nArray2[1] & 0xFF000000L;
            collationData2.trie = collationTailoring2.trie = Trie2_32.createFromSerialized(byteBuffer);
            n2 = collationData2.trie.getSerializedLength();
            if (n2 > n4) {
                throw new ICUException("Not enough bytes for the mappings trie");
            }
            n4 -= n2;
        } else if (collationData != null) {
            collationTailoring2.data = collationData;
        } else {
            throw new ICUException("Missing collation data mappings");
        }
        ICUBinary.skipBytes(byteBuffer, n4);
        n5 = 8;
        n8 = nArray2[n5];
        n4 = nArray2[n5 + 1] - n8;
        ICUBinary.skipBytes(byteBuffer, n4);
        n5 = 9;
        n8 = nArray2[n5];
        n4 = nArray2[n5 + 1] - n8;
        if (n4 >= 8) {
            if (collationData2 == null) {
                throw new ICUException("Tailored ces without tailored trie");
            }
            collationData2.ces = ICUBinary.getLongs(byteBuffer, n4 / 8, n4 & 7);
        } else {
            ICUBinary.skipBytes(byteBuffer, n4);
        }
        n5 = 10;
        n8 = nArray2[n5];
        n4 = nArray2[n5 + 1] - n8;
        ICUBinary.skipBytes(byteBuffer, n4);
        n5 = 11;
        n8 = nArray2[n5];
        n4 = nArray2[n5 + 1] - n8;
        if (n4 >= 4) {
            if (collationData2 == null) {
                throw new ICUException("Tailored ce32s without tailored trie");
            }
            collationData2.ce32s = ICUBinary.getInts(byteBuffer, n4 / 4, n4 & 3);
        } else {
            ICUBinary.skipBytes(byteBuffer, n4);
        }
        n2 = nArray2[4];
        if (n2 >= 0) {
            if (collationData2 == null || collationData2.ce32s == null) {
                throw new ICUException("JamoCE32sStart index into non-existent ce32s[]");
            }
            collationData2.jamoCE32s = new int[67];
            System.arraycopy(collationData2.ce32s, n2, collationData2.jamoCE32s, 0, 67);
        } else if (collationData2 != null) {
            if (collationData != null) {
                collationData2.jamoCE32s = collationData.jamoCE32s;
            } else {
                throw new ICUException("Missing Jamo CE32s for Hangul processing");
            }
        }
        n5 = 12;
        n8 = nArray2[n5];
        n4 = nArray2[n5 + 1] - n8;
        if (n4 >= 4) {
            int n10 = n4 / 4;
            if (collationData2 == null) {
                throw new ICUException("Root elements but no mappings");
            }
            if (n10 <= 4) {
                throw new ICUException("Root elements array too short");
            }
            collationData2.rootElements = new long[n10];
            for (int i = 0; i < n10; ++i) {
                collationData2.rootElements[i] = (long)byteBuffer.getInt() & 0xFFFFFFFFL;
            }
            long l = collationData2.rootElements[3];
            if (l != 0x5000500L) {
                throw new ICUException("Common sec/ter weights in base data differ from the hardcoded value");
            }
            long l2 = collationData2.rootElements[4];
            if (l2 >>> 24 < 69L) {
                throw new ICUException("[fixed last secondary common byte] is too low");
            }
            n4 &= 3;
        }
        ICUBinary.skipBytes(byteBuffer, n4);
        n5 = 13;
        n8 = nArray2[n5];
        n4 = nArray2[n5 + 1] - n8;
        if (n4 >= 2) {
            if (collationData2 == null) {
                throw new ICUException("Tailored contexts without tailored trie");
            }
            collationData2.contexts = ICUBinary.getString(byteBuffer, n4 / 2, n4 & 1);
        } else {
            ICUBinary.skipBytes(byteBuffer, n4);
        }
        n5 = 14;
        n8 = nArray2[n5];
        n4 = nArray2[n5 + 1] - n8;
        if (n4 >= 2) {
            int n11;
            if (collationData2 == null) {
                throw new ICUException("Unsafe-backward-set but no mappings");
            }
            if (collationData == null) {
                collationTailoring2.unsafeBackwardSet = new UnicodeSet(56320, 57343);
                collationData2.nfcImpl.addLcccChars(collationTailoring2.unsafeBackwardSet);
            } else {
                collationTailoring2.unsafeBackwardSet = collationData.unsafeBackwardSet.cloneAsThawed();
            }
            USerializedSet uSerializedSet = new USerializedSet();
            char[] cArray = ICUBinary.getChars(byteBuffer, n4 / 2, n4 & 1);
            n4 = 0;
            uSerializedSet.getSet(cArray, 1);
            n = uSerializedSet.countRanges();
            int[] nArray3 = new int[2];
            for (n11 = 0; n11 < n; ++n11) {
                uSerializedSet.getRange(n11, nArray3);
                collationTailoring2.unsafeBackwardSet.add(nArray3[0], nArray3[1]);
            }
            n11 = 65536;
            int n12 = 55296;
            while (n12 < 56320) {
                if (!collationTailoring2.unsafeBackwardSet.containsNone(n11, n11 + 1023)) {
                    collationTailoring2.unsafeBackwardSet.add(n12);
                }
                ++n12;
                n11 += 1024;
            }
            collationTailoring2.unsafeBackwardSet.freeze();
            collationData2.unsafeBackwardSet = collationTailoring2.unsafeBackwardSet;
        } else if (collationData2 != null) {
            if (collationData != null) {
                collationData2.unsafeBackwardSet = collationData.unsafeBackwardSet;
            } else {
                throw new ICUException("Missing unsafe-backward-set");
            }
        }
        ICUBinary.skipBytes(byteBuffer, n4);
        n5 = 15;
        n8 = nArray2[n5];
        n4 = nArray2[n5 + 1] - n8;
        if (collationData2 != null) {
            collationData2.fastLatinTable = null;
            collationData2.fastLatinTableHeader = null;
            if ((nArray2[1] >> 16 & 0xFF) == 2) {
                if (n4 >= 2) {
                    char c = byteBuffer.getChar();
                    int n13 = c & 0xFF;
                    collationData2.fastLatinTableHeader = new char[n13];
                    collationData2.fastLatinTableHeader[0] = c;
                    for (n = 1; n < n13; ++n) {
                        collationData2.fastLatinTableHeader[n] = byteBuffer.getChar();
                    }
                    n = n4 / 2 - n13;
                    collationData2.fastLatinTable = ICUBinary.getChars(byteBuffer, n, n4 & 1);
                    n4 = 0;
                    if (c >> 8 != 2) {
                        throw new ICUException("Fast-Latin table version differs from version in data header");
                    }
                } else if (collationData != null) {
                    collationData2.fastLatinTable = collationData.fastLatinTable;
                    collationData2.fastLatinTableHeader = collationData.fastLatinTableHeader;
                }
            }
        }
        ICUBinary.skipBytes(byteBuffer, n4);
        n5 = 16;
        n8 = nArray2[n5];
        n4 = nArray2[n5 + 1] - n8;
        if (n4 >= 2) {
            if (collationData2 == null) {
                throw new ICUException("Script order data but no mappings");
            }
            int n14 = n4 / 2;
            CharBuffer charBuffer = byteBuffer.asCharBuffer();
            collationData2.numScripts = charBuffer.get();
            n = n14 - (1 + collationData2.numScripts + 16);
            if (n <= 2) {
                throw new ICUException("Script order data too short");
            }
            collationData2.scriptsIndex = new char[collationData2.numScripts + 16];
            charBuffer.get(collationData2.scriptsIndex);
            collationData2.scriptStarts = new char[n];
            charBuffer.get(collationData2.scriptStarts);
            if (collationData2.scriptStarts[0] != '\u0000' || collationData2.scriptStarts[1] != '\u0300' || collationData2.scriptStarts[n - 1] != '\uff00') {
                throw new ICUException("Script order data not valid");
            }
        } else if (collationData2 != null && collationData != null) {
            collationData2.numScripts = collationData.numScripts;
            collationData2.scriptsIndex = collationData.scriptsIndex;
            collationData2.scriptStarts = collationData.scriptStarts;
        }
        ICUBinary.skipBytes(byteBuffer, n4);
        n5 = 17;
        n8 = nArray2[n5];
        n4 = nArray2[n5 + 1] - n8;
        if (n4 >= 256) {
            if (collationData2 == null) {
                throw new ICUException("Data for compressible primary lead bytes but no mappings");
            }
            collationData2.compressibleBytes = new boolean[256];
            for (int i = 0; i < 256; ++i) {
                collationData2.compressibleBytes[i] = byteBuffer.get() != 0;
            }
            n4 -= 256;
        } else if (collationData2 != null) {
            if (collationData != null) {
                collationData2.compressibleBytes = collationData.compressibleBytes;
            } else {
                throw new ICUException("Missing data for compressible primary lead bytes");
            }
        }
        ICUBinary.skipBytes(byteBuffer, n4);
        n5 = 18;
        n8 = nArray2[n5];
        n4 = nArray2[n5 + 1] - n8;
        ICUBinary.skipBytes(byteBuffer, n4);
        CollationSettings collationSettings = collationTailoring2.settings.readOnly();
        int n15 = nArray2[1] & 0xFFFF;
        char[] cArray = new char[384];
        int n16 = CollationFastLatin.getOptions(collationTailoring2.data, collationSettings, cArray);
        if (n15 == collationSettings.options && collationSettings.variableTop != 0L && Arrays.equals(nArray, collationSettings.reorderCodes) && n16 == collationSettings.fastLatinOptions && (n16 < 0 || Arrays.equals(cArray, collationSettings.fastLatinPrimaries))) {
            return;
        }
        CollationSettings collationSettings2 = collationTailoring2.settings.copyOnWrite();
        collationSettings2.options = n15;
        collationSettings2.variableTop = collationTailoring2.data.getLastPrimaryForGroup(4096 + collationSettings2.getMaxVariable());
        if (collationSettings2.variableTop == 0L) {
            throw new ICUException("The maxVariable could not be mapped to a variableTop");
        }
        if (n3 != 0) {
            collationSettings2.aliasReordering(collationData, nArray, n3, byArray);
        }
        collationSettings2.fastLatinOptions = CollationFastLatin.getOptions(collationTailoring2.data, collationSettings2, collationSettings2.fastLatinPrimaries);
    }

    private CollationDataReader() {
    }

    static {
        $assertionsDisabled = !CollationDataReader.class.desiredAssertionStatus();
        IS_ACCEPTABLE = new IsAcceptable(null);
    }

    private static final class IsAcceptable
    implements ICUBinary.Authenticate {
        private IsAcceptable() {
        }

        @Override
        public boolean isDataVersionAcceptable(byte[] byArray) {
            return byArray[0] == 5;
        }

        IsAcceptable(1 var1_1) {
            this();
        }
    }
}

