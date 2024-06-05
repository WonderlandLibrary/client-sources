package com.ibm.icu.impl.coll;

import com.ibm.icu.util.*;
import com.ibm.icu.text.*;
import com.ibm.icu.impl.*;
import java.util.*;
import java.nio.*;
import java.io.*;

final class CollationDataReader
{
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
    
    static void read(final CollationTailoring base, final ByteBuffer inBytes, final CollationTailoring tailoring) throws IOException {
        tailoring.version = ICUBinary.readHeader(inBytes, 1430482796, CollationDataReader.IS_ACCEPTABLE);
        if (base != null && base.getUCAVersion() != tailoring.getUCAVersion()) {
            throw new ICUException("Tailoring UCA version differs from base data UCA version");
        }
        final int inLength = inBytes.remaining();
        if (inLength < 8) {
            throw new ICUException("not enough bytes");
        }
        final int indexesLength = inBytes.getInt();
        if (indexesLength < 2 || inLength < indexesLength * 4) {
            throw new ICUException("not enough indexes");
        }
        final int[] inIndexes = new int[20];
        inIndexes[0] = indexesLength;
        for (int i = 1; i < indexesLength && i < inIndexes.length; ++i) {
            inIndexes[i] = inBytes.getInt();
        }
        for (int i = indexesLength; i < inIndexes.length; ++i) {
            inIndexes[i] = -1;
        }
        if (indexesLength > inIndexes.length) {
            ICUBinary.skipBytes(inBytes, (indexesLength - inIndexes.length) * 4);
        }
        int length;
        if (indexesLength > 19) {
            length = inIndexes[19];
        }
        else if (indexesLength > 5) {
            length = inIndexes[indexesLength - 1];
        }
        else {
            length = 0;
        }
        if (inLength < length) {
            throw new ICUException("not enough bytes");
        }
        final CollationData baseData = (base == null) ? null : base.data;
        int index = 5;
        int offset = inIndexes[index];
        length = inIndexes[index + 1] - offset;
        int reorderCodesLength;
        int[] reorderCodes;
        if (length >= 4) {
            if (baseData == null) {
                throw new ICUException("Collation base data must not reorder scripts");
            }
            int reorderRangesLength;
            for (reorderCodesLength = length / 4, reorderCodes = ICUBinary.getInts(inBytes, reorderCodesLength, length & 0x3), reorderRangesLength = 0; reorderRangesLength < reorderCodesLength && (reorderCodes[reorderCodesLength - reorderRangesLength - 1] & 0xFFFF0000) != 0x0; ++reorderRangesLength) {}
            assert reorderRangesLength < reorderCodesLength;
            reorderCodesLength -= reorderRangesLength;
        }
        else {
            reorderCodes = new int[0];
            reorderCodesLength = 0;
            ICUBinary.skipBytes(inBytes, length);
        }
        byte[] reorderTable = null;
        index = 6;
        offset = inIndexes[index];
        length = inIndexes[index + 1] - offset;
        if (length >= 256) {
            if (reorderCodesLength == 0) {
                throw new ICUException("Reordering table without reordering codes");
            }
            reorderTable = new byte[256];
            inBytes.get(reorderTable);
            length -= 256;
        }
        ICUBinary.skipBytes(inBytes, length);
        if (baseData != null && baseData.numericPrimary != ((long)inIndexes[1] & 0xFF000000L)) {
            throw new ICUException("Tailoring numeric primary weight differs from base data");
        }
        CollationData data = null;
        index = 7;
        offset = inIndexes[index];
        length = inIndexes[index + 1] - offset;
        if (length >= 8) {
            tailoring.ensureOwnedData();
            data = tailoring.ownedData;
            data.base = baseData;
            data.numericPrimary = ((long)inIndexes[1] & 0xFF000000L);
            final CollationData collationData = data;
            final Trie2_32 fromSerialized = Trie2_32.createFromSerialized(inBytes);
            tailoring.trie = fromSerialized;
            collationData.trie = fromSerialized;
            final int trieLength = data.trie.getSerializedLength();
            if (trieLength > length) {
                throw new ICUException("Not enough bytes for the mappings trie");
            }
            length -= trieLength;
        }
        else {
            if (baseData == null) {
                throw new ICUException("Missing collation data mappings");
            }
            tailoring.data = baseData;
        }
        ICUBinary.skipBytes(inBytes, length);
        index = 8;
        offset = inIndexes[index];
        length = inIndexes[index + 1] - offset;
        ICUBinary.skipBytes(inBytes, length);
        index = 9;
        offset = inIndexes[index];
        length = inIndexes[index + 1] - offset;
        if (length >= 8) {
            if (data == null) {
                throw new ICUException("Tailored ces without tailored trie");
            }
            data.ces = ICUBinary.getLongs(inBytes, length / 8, length & 0x7);
        }
        else {
            ICUBinary.skipBytes(inBytes, length);
        }
        index = 10;
        offset = inIndexes[index];
        length = inIndexes[index + 1] - offset;
        ICUBinary.skipBytes(inBytes, length);
        index = 11;
        offset = inIndexes[index];
        length = inIndexes[index + 1] - offset;
        if (length >= 4) {
            if (data == null) {
                throw new ICUException("Tailored ce32s without tailored trie");
            }
            data.ce32s = ICUBinary.getInts(inBytes, length / 4, length & 0x3);
        }
        else {
            ICUBinary.skipBytes(inBytes, length);
        }
        final int jamoCE32sStart = inIndexes[4];
        if (jamoCE32sStart >= 0) {
            if (data == null || data.ce32s == null) {
                throw new ICUException("JamoCE32sStart index into non-existent ce32s[]");
            }
            data.jamoCE32s = new int[67];
            System.arraycopy(data.ce32s, jamoCE32sStart, data.jamoCE32s, 0, 67);
        }
        else if (data != null) {
            if (baseData == null) {
                throw new ICUException("Missing Jamo CE32s for Hangul processing");
            }
            data.jamoCE32s = baseData.jamoCE32s;
        }
        index = 12;
        offset = inIndexes[index];
        length = inIndexes[index + 1] - offset;
        if (length >= 4) {
            final int rootElementsLength = length / 4;
            if (data == null) {
                throw new ICUException("Root elements but no mappings");
            }
            if (rootElementsLength <= 4) {
                throw new ICUException("Root elements array too short");
            }
            data.rootElements = new long[rootElementsLength];
            for (int j = 0; j < rootElementsLength; ++j) {
                data.rootElements[j] = ((long)inBytes.getInt() & 0xFFFFFFFFL);
            }
            final long commonSecTer = data.rootElements[3];
            if (commonSecTer != 83887360L) {
                throw new ICUException("Common sec/ter weights in base data differ from the hardcoded value");
            }
            final long secTerBoundaries = data.rootElements[4];
            if (secTerBoundaries >>> 24 < 69L) {
                throw new ICUException("[fixed last secondary common byte] is too low");
            }
            length &= 0x3;
        }
        ICUBinary.skipBytes(inBytes, length);
        index = 13;
        offset = inIndexes[index];
        length = inIndexes[index + 1] - offset;
        if (length >= 2) {
            if (data == null) {
                throw new ICUException("Tailored contexts without tailored trie");
            }
            data.contexts = ICUBinary.getString(inBytes, length / 2, length & 0x1);
        }
        else {
            ICUBinary.skipBytes(inBytes, length);
        }
        index = 14;
        offset = inIndexes[index];
        length = inIndexes[index + 1] - offset;
        if (length >= 2) {
            if (data == null) {
                throw new ICUException("Unsafe-backward-set but no mappings");
            }
            if (baseData == null) {
                tailoring.unsafeBackwardSet = new UnicodeSet(56320, 57343);
                data.nfcImpl.addLcccChars(tailoring.unsafeBackwardSet);
            }
            else {
                tailoring.unsafeBackwardSet = baseData.unsafeBackwardSet.cloneAsThawed();
            }
            final USerializedSet sset = new USerializedSet();
            final char[] unsafeData = ICUBinary.getChars(inBytes, length / 2, length & 0x1);
            length = 0;
            sset.getSet(unsafeData, 0);
            final int count = sset.countRanges();
            final int[] range = new int[2];
            for (int k = 0; k < count; ++k) {
                sset.getRange(k, range);
                tailoring.unsafeBackwardSet.add(range[0], range[1]);
            }
            for (int c = 65536, lead = 55296; lead < 56320; ++lead, c += 1024) {
                if (!tailoring.unsafeBackwardSet.containsNone(c, c + 1023)) {
                    tailoring.unsafeBackwardSet.add(lead);
                }
            }
            tailoring.unsafeBackwardSet.freeze();
            data.unsafeBackwardSet = tailoring.unsafeBackwardSet;
        }
        else if (data != null) {
            if (baseData == null) {
                throw new ICUException("Missing unsafe-backward-set");
            }
            data.unsafeBackwardSet = baseData.unsafeBackwardSet;
        }
        ICUBinary.skipBytes(inBytes, length);
        index = 15;
        offset = inIndexes[index];
        length = inIndexes[index + 1] - offset;
        if (data != null) {
            data.fastLatinTable = null;
            data.fastLatinTableHeader = null;
            if ((inIndexes[1] >> 16 & 0xFF) == 0x2) {
                if (length >= 2) {
                    final char header0 = inBytes.getChar();
                    final int headerLength = header0 & '\u00ff';
                    (data.fastLatinTableHeader = new char[headerLength])[0] = header0;
                    for (int l = 1; l < headerLength; ++l) {
                        data.fastLatinTableHeader[l] = inBytes.getChar();
                    }
                    final int tableLength = length / 2 - headerLength;
                    data.fastLatinTable = ICUBinary.getChars(inBytes, tableLength, length & 0x1);
                    length = 0;
                    if (header0 >> 8 != 2) {
                        throw new ICUException("Fast-Latin table version differs from version in data header");
                    }
                }
                else if (baseData != null) {
                    data.fastLatinTable = baseData.fastLatinTable;
                    data.fastLatinTableHeader = baseData.fastLatinTableHeader;
                }
            }
        }
        ICUBinary.skipBytes(inBytes, length);
        index = 16;
        offset = inIndexes[index];
        length = inIndexes[index + 1] - offset;
        if (length >= 2) {
            if (data == null) {
                throw new ICUException("Script order data but no mappings");
            }
            final int scriptsLength = length / 2;
            final CharBuffer inChars = inBytes.asCharBuffer();
            data.numScripts = inChars.get();
            final int scriptStartsLength = scriptsLength - (1 + data.numScripts + 16);
            if (scriptStartsLength <= 2) {
                throw new ICUException("Script order data too short");
            }
            inChars.get(data.scriptsIndex = new char[data.numScripts + 16]);
            inChars.get(data.scriptStarts = new char[scriptStartsLength]);
            if (data.scriptStarts[0] != '\0' || data.scriptStarts[1] != '\u0300' || data.scriptStarts[scriptStartsLength - 1] != '\uff00') {
                throw new ICUException("Script order data not valid");
            }
        }
        else if (data != null) {
            if (baseData != null) {
                data.numScripts = baseData.numScripts;
                data.scriptsIndex = baseData.scriptsIndex;
                data.scriptStarts = baseData.scriptStarts;
            }
        }
        ICUBinary.skipBytes(inBytes, length);
        index = 17;
        offset = inIndexes[index];
        length = inIndexes[index + 1] - offset;
        if (length >= 256) {
            if (data == null) {
                throw new ICUException("Data for compressible primary lead bytes but no mappings");
            }
            data.compressibleBytes = new boolean[256];
            for (int m = 0; m < 256; ++m) {
                data.compressibleBytes[m] = (inBytes.get() != 0);
            }
            length -= 256;
        }
        else if (data != null) {
            if (baseData == null) {
                throw new ICUException("Missing data for compressible primary lead bytes");
            }
            data.compressibleBytes = baseData.compressibleBytes;
        }
        ICUBinary.skipBytes(inBytes, length);
        index = 18;
        offset = inIndexes[index];
        length = inIndexes[index + 1] - offset;
        ICUBinary.skipBytes(inBytes, length);
        final CollationSettings ts = tailoring.settings.readOnly();
        final int options = inIndexes[1] & 0xFFFF;
        final char[] fastLatinPrimaries = new char[384];
        final int fastLatinOptions = CollationFastLatin.getOptions(tailoring.data, ts, fastLatinPrimaries);
        if (options == ts.options && ts.variableTop != 0L && Arrays.equals(reorderCodes, ts.reorderCodes) && fastLatinOptions == ts.fastLatinOptions && (fastLatinOptions < 0 || Arrays.equals(fastLatinPrimaries, ts.fastLatinPrimaries))) {
            return;
        }
        final CollationSettings settings = tailoring.settings.copyOnWrite();
        settings.options = options;
        settings.variableTop = tailoring.data.getLastPrimaryForGroup(4096 + settings.getMaxVariable());
        if (settings.variableTop == 0L) {
            throw new ICUException("The maxVariable could not be mapped to a variableTop");
        }
        if (reorderCodesLength != 0) {
            settings.aliasReordering(baseData, reorderCodes, reorderCodesLength, reorderTable);
        }
        settings.fastLatinOptions = CollationFastLatin.getOptions(tailoring.data, settings, settings.fastLatinPrimaries);
    }
    
    private CollationDataReader() {
    }
    
    static {
        IS_ACCEPTABLE = new IsAcceptable();
    }
    
    private static final class IsAcceptable implements ICUBinary.Authenticate
    {
        @Override
        public boolean isDataVersionAcceptable(final byte[] version) {
            return version[0] == 5;
        }
    }
}
