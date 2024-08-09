/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl.coll;

import com.ibm.icu.impl.Norm2AllModes;
import com.ibm.icu.impl.Normalizer2Impl;
import com.ibm.icu.impl.Trie2;
import com.ibm.icu.impl.Trie2Writable;
import com.ibm.icu.impl.coll.Collation;
import com.ibm.icu.impl.coll.CollationData;
import com.ibm.icu.impl.coll.CollationFastLatinBuilder;
import com.ibm.icu.impl.coll.CollationIterator;
import com.ibm.icu.impl.coll.CollationSettings;
import com.ibm.icu.impl.coll.UVector32;
import com.ibm.icu.impl.coll.UVector64;
import com.ibm.icu.lang.UCharacter;
import com.ibm.icu.text.UnicodeSet;
import com.ibm.icu.text.UnicodeSetIterator;
import com.ibm.icu.util.CharsTrie;
import com.ibm.icu.util.CharsTrieBuilder;
import com.ibm.icu.util.StringTrieBuilder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

final class CollationDataBuilder {
    private static final int IS_BUILDER_JAMO_CE32 = 256;
    protected Normalizer2Impl nfcImpl;
    protected CollationData base;
    protected CollationSettings baseSettings;
    protected Trie2Writable trie;
    protected UVector32 ce32s;
    protected UVector64 ce64s;
    protected ArrayList<ConditionalCE32> conditionalCE32s;
    protected UnicodeSet contextChars = new UnicodeSet();
    protected StringBuilder contexts = new StringBuilder();
    protected UnicodeSet unsafeBackwardSet = new UnicodeSet();
    protected boolean modified;
    protected boolean fastLatinEnabled;
    protected CollationFastLatinBuilder fastLatinBuilder;
    protected DataBuilderCollationIterator collIter;
    static final boolean $assertionsDisabled = !CollationDataBuilder.class.desiredAssertionStatus();

    CollationDataBuilder() {
        this.nfcImpl = Norm2AllModes.getNFCInstance().impl;
        this.base = null;
        this.baseSettings = null;
        this.trie = null;
        this.ce32s = new UVector32();
        this.ce64s = new UVector64();
        this.conditionalCE32s = new ArrayList();
        this.modified = false;
        this.fastLatinEnabled = false;
        this.fastLatinBuilder = null;
        this.collIter = null;
        this.ce32s.addElement(0);
    }

    void initForTailoring(CollationData collationData) {
        int n;
        if (this.trie != null) {
            throw new IllegalStateException("attempt to reuse a CollationDataBuilder");
        }
        if (collationData == null) {
            throw new IllegalArgumentException("null CollationData");
        }
        this.base = collationData;
        this.trie = new Trie2Writable(192, -195323);
        for (n = 192; n <= 255; ++n) {
            this.trie.set(n, 192);
        }
        n = Collation.makeCE32FromTagAndIndex(12, 0);
        this.trie.setRange(44032, 55203, n, false);
        this.unsafeBackwardSet.addAll(collationData.unsafeBackwardSet);
    }

    boolean isCompressibleLeadByte(int n) {
        return this.base.isCompressibleLeadByte(n);
    }

    boolean isCompressiblePrimary(long l) {
        return this.isCompressibleLeadByte((int)l >>> 24);
    }

    boolean hasMappings() {
        return this.modified;
    }

    boolean isAssigned(int n) {
        return Collation.isAssignedCE32(this.trie.get(n));
    }

    void add(CharSequence charSequence, CharSequence charSequence2, long[] lArray, int n) {
        int n2 = this.encodeCEs(lArray, n);
        this.addCE32(charSequence, charSequence2, n2);
    }

    int encodeCEs(long[] lArray, int n) {
        if (n < 0 || n > 31) {
            throw new IllegalArgumentException("mapping to too many CEs");
        }
        if (!this.isMutable()) {
            throw new IllegalStateException("attempt to add mappings after build()");
        }
        if (n == 0) {
            return CollationDataBuilder.encodeOneCEAsCE32(0L);
        }
        if (n == 1) {
            return this.encodeOneCE(lArray[0]);
        }
        if (n == 2) {
            long l = lArray[0];
            long l2 = lArray[1];
            long l3 = l >>> 32;
            if ((l & 0xFFFFFFFFFF00FFL) == 0x5000000L && (l2 & 0xFFFFFFFF00FFFFFFL) == 1280L && l3 != 0L) {
                return (int)l3 | ((int)l & 0xFF00) << 8 | (int)l2 >> 16 & 0xFF00 | 0xC0 | 4;
            }
        }
        int[] nArray = new int[31];
        int n2 = 0;
        while (true) {
            if (n2 == n) {
                return this.encodeExpansion32(nArray, 0, n);
            }
            int n3 = CollationDataBuilder.encodeOneCEAsCE32(lArray[n2]);
            if (n3 == 1) break;
            nArray[n2] = n3;
            ++n2;
        }
        return this.encodeExpansion(lArray, 0, n);
    }

    void addCE32(CharSequence charSequence, CharSequence charSequence2, int n) {
        boolean bl;
        if (charSequence2.length() == 0) {
            throw new IllegalArgumentException("mapping from empty string");
        }
        if (!this.isMutable()) {
            throw new IllegalStateException("attempt to add mappings after build()");
        }
        int n2 = Character.codePointAt(charSequence2, 0);
        int n3 = Character.charCount(n2);
        int n4 = this.trie.get(n2);
        boolean bl2 = bl = charSequence.length() != 0 || charSequence2.length() > n3;
        if (n4 == 192) {
            int n5 = this.base.getFinalCE32(this.base.getCE32(n2));
            if (bl || Collation.ce32HasContext(n5)) {
                n4 = this.copyFromBaseCE32(n2, n5, false);
                this.trie.set(n2, n4);
            }
        }
        if (!bl) {
            if (!CollationDataBuilder.isBuilderContextCE32(n4)) {
                this.trie.set(n2, n);
            } else {
                ConditionalCE32 conditionalCE32 = this.getConditionalCE32ForCE32(n4);
                conditionalCE32.builtCE32 = 1;
                conditionalCE32.ce32 = n;
            }
        } else {
            ConditionalCE32 conditionalCE32;
            if (!CollationDataBuilder.isBuilderContextCE32(n4)) {
                int n6 = this.addConditionalCE32("\u0000", n4);
                int n7 = CollationDataBuilder.makeBuilderContextCE32(n6);
                this.trie.set(n2, n7);
                this.contextChars.add(n2);
                conditionalCE32 = this.getConditionalCE32(n6);
            } else {
                conditionalCE32 = this.getConditionalCE32ForCE32(n4);
                conditionalCE32.builtCE32 = 1;
            }
            CharSequence charSequence3 = charSequence2.subSequence(n3, charSequence2.length());
            String string = "" + (char)charSequence.length() + charSequence + charSequence3;
            this.unsafeBackwardSet.addAll(charSequence3);
            while (true) {
                int n8;
                if ((n8 = conditionalCE32.next) < 0) {
                    int n9;
                    conditionalCE32.next = n9 = this.addConditionalCE32(string, n);
                    break;
                }
                ConditionalCE32 conditionalCE322 = this.getConditionalCE32(n8);
                int n10 = string.compareTo(conditionalCE322.context);
                if (n10 < 0) {
                    int n11;
                    conditionalCE32.next = n11 = this.addConditionalCE32(string, n);
                    this.getConditionalCE32((int)n11).next = n8;
                    break;
                }
                if (n10 == 0) {
                    conditionalCE322.ce32 = n;
                    break;
                }
                conditionalCE32 = conditionalCE322;
            }
        }
        this.modified = true;
    }

    void copyFrom(CollationDataBuilder collationDataBuilder, CEModifier cEModifier) {
        if (!this.isMutable()) {
            throw new IllegalStateException("attempt to copyFrom() after build()");
        }
        CopyHelper copyHelper = new CopyHelper(collationDataBuilder, this, cEModifier);
        for (Trie2.Range range : collationDataBuilder.trie) {
            if (range.leadSurrogate) break;
            CollationDataBuilder.enumRangeForCopy(range.startCodePoint, range.endCodePoint, range.value, copyHelper);
        }
        this.modified |= collationDataBuilder.modified;
    }

    void optimize(UnicodeSet unicodeSet) {
        if (unicodeSet.isEmpty()) {
            return;
        }
        UnicodeSetIterator unicodeSetIterator = new UnicodeSetIterator(unicodeSet);
        while (unicodeSetIterator.next() && unicodeSetIterator.codepoint != UnicodeSetIterator.IS_STRING) {
            int n = unicodeSetIterator.codepoint;
            int n2 = this.trie.get(n);
            if (n2 != 192) continue;
            n2 = this.base.getFinalCE32(this.base.getCE32(n));
            n2 = this.copyFromBaseCE32(n, n2, false);
            this.trie.set(n, n2);
        }
        this.modified = true;
    }

    void suppressContractions(UnicodeSet unicodeSet) {
        if (unicodeSet.isEmpty()) {
            return;
        }
        UnicodeSetIterator unicodeSetIterator = new UnicodeSetIterator(unicodeSet);
        while (unicodeSetIterator.next() && unicodeSetIterator.codepoint != UnicodeSetIterator.IS_STRING) {
            int n = unicodeSetIterator.codepoint;
            int n2 = this.trie.get(n);
            if (n2 == 192) {
                n2 = this.base.getFinalCE32(this.base.getCE32(n));
                if (!Collation.ce32HasContext(n2)) continue;
                n2 = this.copyFromBaseCE32(n, n2, true);
                this.trie.set(n, n2);
                continue;
            }
            if (!CollationDataBuilder.isBuilderContextCE32(n2)) continue;
            n2 = this.getConditionalCE32ForCE32((int)n2).ce32;
            this.trie.set(n, n2);
            this.contextChars.remove(n);
        }
        this.modified = true;
    }

    void enableFastLatin() {
        this.fastLatinEnabled = true;
    }

    void build(CollationData collationData) {
        this.buildMappings(collationData);
        if (this.base != null) {
            collationData.numericPrimary = this.base.numericPrimary;
            collationData.compressibleBytes = this.base.compressibleBytes;
            collationData.numScripts = this.base.numScripts;
            collationData.scriptsIndex = this.base.scriptsIndex;
            collationData.scriptStarts = this.base.scriptStarts;
        }
        this.buildFastLatinTable(collationData);
    }

    int getCEs(CharSequence charSequence, long[] lArray, int n) {
        return this.getCEs(charSequence, 0, lArray, n);
    }

    int getCEs(CharSequence charSequence, CharSequence charSequence2, long[] lArray, int n) {
        int n2 = charSequence.length();
        if (n2 == 0) {
            return this.getCEs(charSequence2, 0, lArray, n);
        }
        return this.getCEs((CharSequence)new StringBuilder(charSequence).append(charSequence2), n2, lArray, n);
    }

    protected int getCE32FromOffsetCE32(boolean bl, int n, int n2) {
        int n3 = Collation.indexFromCE32(n2);
        long l = bl ? this.base.ces[n3] : this.ce64s.elementAti(n3);
        long l2 = Collation.getThreeBytePrimaryForOffsetData(n, l);
        return Collation.makeLongPrimaryCE32(l2);
    }

    protected int addCE(long l) {
        int n = this.ce64s.size();
        for (int i = 0; i < n; ++i) {
            if (l != this.ce64s.elementAti(i)) continue;
            return i;
        }
        this.ce64s.addElement(l);
        return n;
    }

    protected int addCE32(int n) {
        int n2 = this.ce32s.size();
        for (int i = 0; i < n2; ++i) {
            if (n != this.ce32s.elementAti(i)) continue;
            return i;
        }
        this.ce32s.addElement(n);
        return n2;
    }

    protected int addConditionalCE32(String string, int n) {
        if (!$assertionsDisabled && string.length() == 0) {
            throw new AssertionError();
        }
        int n2 = this.conditionalCE32s.size();
        if (n2 > 524287) {
            throw new IndexOutOfBoundsException("too many context-sensitive mappings");
        }
        ConditionalCE32 conditionalCE32 = new ConditionalCE32(string, n);
        this.conditionalCE32s.add(conditionalCE32);
        return n2;
    }

    protected ConditionalCE32 getConditionalCE32(int n) {
        return this.conditionalCE32s.get(n);
    }

    protected ConditionalCE32 getConditionalCE32ForCE32(int n) {
        return this.getConditionalCE32(Collation.indexFromCE32(n));
    }

    protected static int makeBuilderContextCE32(int n) {
        return Collation.makeCE32FromTagAndIndex(7, n);
    }

    protected static boolean isBuilderContextCE32(int n) {
        return Collation.hasCE32Tag(n, 7);
    }

    protected static int encodeOneCEAsCE32(long l) {
        long l2 = l >>> 32;
        int n = (int)l;
        int n2 = n & 0xFFFF;
        if (!$assertionsDisabled && (n2 & 0xC000) == 49152) {
            throw new AssertionError();
        }
        if ((l & 0xFFFF00FF00FFL) == 0L) {
            return (int)l2 | n >>> 16 | n2 >> 8;
        }
        if ((l & 0xFFFFFFFFFFL) == 0x5000500L) {
            return Collation.makeLongPrimaryCE32(l2);
        }
        if (l2 == 0L && (n2 & 0xFF) == 0) {
            return Collation.makeLongSecondaryCE32(n);
        }
        return 0;
    }

    protected int encodeOneCE(long l) {
        int n = CollationDataBuilder.encodeOneCEAsCE32(l);
        if (n != 1) {
            return n;
        }
        int n2 = this.addCE(l);
        if (n2 > 524287) {
            throw new IndexOutOfBoundsException("too many mappings");
        }
        return Collation.makeCE32FromTagIndexAndLength(6, n2, 1);
    }

    protected int encodeExpansion(long[] lArray, int n, int n2) {
        int n3;
        int n4;
        long l = lArray[n];
        int n5 = this.ce64s.size() - n2;
        block0: for (n4 = 0; n4 <= n5; ++n4) {
            if (l != this.ce64s.elementAti(n4)) continue;
            if (n4 > 524287) {
                throw new IndexOutOfBoundsException("too many mappings");
            }
            n3 = 1;
            while (n3 != n2) {
                if (this.ce64s.elementAti(n4 + n3) != lArray[n + n3]) continue block0;
                ++n3;
            }
            return Collation.makeCE32FromTagIndexAndLength(6, n4, n2);
        }
        n4 = this.ce64s.size();
        if (n4 > 524287) {
            throw new IndexOutOfBoundsException("too many mappings");
        }
        for (n3 = 0; n3 < n2; ++n3) {
            this.ce64s.addElement(lArray[n + n3]);
        }
        return Collation.makeCE32FromTagIndexAndLength(6, n4, n2);
    }

    protected int encodeExpansion32(int[] nArray, int n, int n2) {
        int n3;
        int n4;
        int n5 = nArray[n];
        int n6 = this.ce32s.size() - n2;
        block0: for (n4 = 0; n4 <= n6; ++n4) {
            if (n5 != this.ce32s.elementAti(n4)) continue;
            if (n4 > 524287) {
                throw new IndexOutOfBoundsException("too many mappings");
            }
            n3 = 1;
            while (n3 != n2) {
                if (this.ce32s.elementAti(n4 + n3) != nArray[n + n3]) continue block0;
                ++n3;
            }
            return Collation.makeCE32FromTagIndexAndLength(5, n4, n2);
        }
        n4 = this.ce32s.size();
        if (n4 > 524287) {
            throw new IndexOutOfBoundsException("too many mappings");
        }
        for (n3 = 0; n3 < n2; ++n3) {
            this.ce32s.addElement(nArray[n + n3]);
        }
        return Collation.makeCE32FromTagIndexAndLength(5, n4, n2);
    }

    protected int copyFromBaseCE32(int n, int n2, boolean bl) {
        if (!Collation.isSpecialCE32(n2)) {
            return n2;
        }
        switch (Collation.tagFromCE32(n2)) {
            case 1: 
            case 2: 
            case 4: {
                break;
            }
            case 5: {
                int n3 = Collation.indexFromCE32(n2);
                int n4 = Collation.lengthFromCE32(n2);
                n2 = this.encodeExpansion32(this.base.ce32s, n3, n4);
                break;
            }
            case 6: {
                int n5 = Collation.indexFromCE32(n2);
                int n6 = Collation.lengthFromCE32(n2);
                n2 = this.encodeExpansion(this.base.ces, n5, n6);
                break;
            }
            case 8: {
                int n7;
                int n8 = Collation.indexFromCE32(n2);
                n2 = this.base.getCE32FromContexts(n8);
                if (!bl) {
                    return this.copyFromBaseCE32(n, n2, true);
                }
                ConditionalCE32 conditionalCE32 = new ConditionalCE32("", 0);
                StringBuilder stringBuilder = new StringBuilder("\u0000");
                if (Collation.isContractionCE32(n2)) {
                    n7 = this.copyContractionsFromBaseCE32(stringBuilder, n, n2, conditionalCE32);
                } else {
                    n2 = this.copyFromBaseCE32(n, n2, false);
                    conditionalCE32.next = n7 = this.addConditionalCE32(stringBuilder.toString(), n2);
                }
                ConditionalCE32 conditionalCE322 = this.getConditionalCE32(n7);
                CharsTrie.Iterator iterator2 = CharsTrie.iterator(this.base.contexts, n8 + 2, 0);
                while (iterator2.hasNext()) {
                    CharsTrie.Entry entry = iterator2.next();
                    stringBuilder.setLength(0);
                    stringBuilder.append(entry.chars).reverse().insert(0, (char)entry.chars.length());
                    n2 = entry.value;
                    if (Collation.isContractionCE32(n2)) {
                        n7 = this.copyContractionsFromBaseCE32(stringBuilder, n, n2, conditionalCE322);
                    } else {
                        n2 = this.copyFromBaseCE32(n, n2, false);
                        conditionalCE322.next = n7 = this.addConditionalCE32(stringBuilder.toString(), n2);
                    }
                    conditionalCE322 = this.getConditionalCE32(n7);
                }
                n2 = CollationDataBuilder.makeBuilderContextCE32(conditionalCE32.next);
                this.contextChars.add(n);
                break;
            }
            case 9: {
                if (!bl) {
                    int n9 = Collation.indexFromCE32(n2);
                    n2 = this.base.getCE32FromContexts(n9);
                    return this.copyFromBaseCE32(n, n2, true);
                }
                ConditionalCE32 conditionalCE32 = new ConditionalCE32("", 0);
                StringBuilder stringBuilder = new StringBuilder("\u0000");
                this.copyContractionsFromBaseCE32(stringBuilder, n, n2, conditionalCE32);
                n2 = CollationDataBuilder.makeBuilderContextCE32(conditionalCE32.next);
                this.contextChars.add(n);
                break;
            }
            case 12: {
                throw new UnsupportedOperationException("We forbid tailoring of Hangul syllables.");
            }
            case 14: {
                n2 = this.getCE32FromOffsetCE32(true, n, n2);
                break;
            }
            case 15: {
                n2 = this.encodeOneCE(Collation.unassignedCEFromCodePoint(n));
                break;
            }
            default: {
                throw new AssertionError((Object)"copyFromBaseCE32(c, ce32, withContext) requires ce32 == base.getFinalCE32(ce32)");
            }
        }
        return n2;
    }

    protected int copyContractionsFromBaseCE32(StringBuilder stringBuilder, int n, int n2, ConditionalCE32 conditionalCE32) {
        int n3;
        int n4 = Collation.indexFromCE32(n2);
        if ((n2 & 0x100) != 0) {
            if (!$assertionsDisabled && stringBuilder.length() <= 1) {
                throw new AssertionError();
            }
            n3 = -1;
        } else {
            n2 = this.base.getCE32FromContexts(n4);
            if (!$assertionsDisabled && Collation.isContractionCE32(n2)) {
                throw new AssertionError();
            }
            n2 = this.copyFromBaseCE32(n, n2, false);
            conditionalCE32.next = n3 = this.addConditionalCE32(stringBuilder.toString(), n2);
            conditionalCE32 = this.getConditionalCE32(n3);
        }
        int n5 = stringBuilder.length();
        CharsTrie.Iterator iterator2 = CharsTrie.iterator(this.base.contexts, n4 + 2, 0);
        while (iterator2.hasNext()) {
            CharsTrie.Entry entry = iterator2.next();
            stringBuilder.append(entry.chars);
            n2 = this.copyFromBaseCE32(n, entry.value, false);
            conditionalCE32.next = n3 = this.addConditionalCE32(stringBuilder.toString(), n2);
            conditionalCE32 = this.getConditionalCE32(n3);
            stringBuilder.setLength(n5);
        }
        if (!$assertionsDisabled && n3 < 0) {
            throw new AssertionError();
        }
        return n3;
    }

    private static void enumRangeForCopy(int n, int n2, int n3, CopyHelper copyHelper) {
        if (n3 != -1 && n3 != 192) {
            copyHelper.copyRangeCE32(n, n2, n3);
        }
    }

    protected boolean getJamoCE32s(int[] nArray) {
        int n;
        int n2;
        boolean bl = this.base == null;
        boolean bl2 = false;
        for (n2 = 0; n2 < 67; ++n2) {
            n = CollationDataBuilder.jamoCpFromIndex(n2);
            boolean bl3 = false;
            int n3 = this.trie.get(n);
            bl |= Collation.isAssignedCE32(n3);
            if (n3 == 192) {
                bl3 = true;
                n3 = this.base.getCE32(n);
            }
            if (Collation.isSpecialCE32(n3)) {
                switch (Collation.tagFromCE32(n3)) {
                    case 1: 
                    case 2: 
                    case 4: {
                        break;
                    }
                    case 5: 
                    case 6: 
                    case 8: 
                    case 9: {
                        if (!bl3) break;
                        n3 = 192;
                        bl2 = true;
                        break;
                    }
                    case 15: {
                        if (!$assertionsDisabled && !bl3) {
                            throw new AssertionError();
                        }
                        n3 = 192;
                        bl2 = true;
                        break;
                    }
                    case 14: {
                        n3 = this.getCE32FromOffsetCE32(bl3, n, n3);
                        break;
                    }
                    case 0: 
                    case 3: 
                    case 7: 
                    case 10: 
                    case 11: 
                    case 12: 
                    case 13: {
                        throw new AssertionError((Object)String.format("unexpected special tag in ce32=0x%08x", n3));
                    }
                }
            }
            nArray[n2] = n3;
        }
        if (bl && bl2) {
            for (n2 = 0; n2 < 67; ++n2) {
                if (nArray[n2] != 192) continue;
                n = CollationDataBuilder.jamoCpFromIndex(n2);
                nArray[n2] = this.copyFromBaseCE32(n, this.base.getCE32(n), false);
            }
        }
        return bl;
    }

    protected void setDigitTags() {
        UnicodeSet unicodeSet = new UnicodeSet("[:Nd:]");
        UnicodeSetIterator unicodeSetIterator = new UnicodeSetIterator(unicodeSet);
        while (unicodeSetIterator.next()) {
            if (!$assertionsDisabled && unicodeSetIterator.codepoint == UnicodeSetIterator.IS_STRING) {
                throw new AssertionError();
            }
            int n = unicodeSetIterator.codepoint;
            int n2 = this.trie.get(n);
            if (n2 == 192 || n2 == -1) continue;
            int n3 = this.addCE32(n2);
            if (n3 > 524287) {
                throw new IndexOutOfBoundsException("too many mappings");
            }
            n2 = Collation.makeCE32FromTagIndexAndLength(10, n3, UCharacter.digit(n));
            this.trie.set(n, n2);
        }
    }

    protected void setLeadSurrogates() {
        for (char c = '\ud800'; c < '\udc00'; c = (char)(c + '\u0001')) {
            int n = -1;
            Iterator<Trie2.Range> iterator2 = this.trie.iteratorForLeadSurrogate(c);
            while (iterator2.hasNext()) {
                Trie2.Range range = iterator2.next();
                int n2 = range.value;
                if (n2 == -1) {
                    n2 = 0;
                } else if (n2 == 192) {
                    n2 = 256;
                } else {
                    n = 512;
                    break;
                }
                if (n < 0) {
                    n = n2;
                    continue;
                }
                if (n == n2) continue;
                n = 512;
                break;
            }
            this.trie.setForLeadSurrogateCodeUnit(c, Collation.makeCE32FromTagAndIndex(13, 0) | n);
        }
    }

    protected void buildMappings(CollationData collationData) {
        int n;
        int n2;
        int n3;
        if (!this.isMutable()) {
            throw new IllegalStateException("attempt to build() after build()");
        }
        this.buildContexts();
        int[] nArray = new int[67];
        int n4 = -1;
        if (this.getJamoCE32s(nArray)) {
            n4 = this.ce32s.size();
            for (n3 = 0; n3 < 67; ++n3) {
                this.ce32s.addElement(nArray[n3]);
            }
            n3 = 0;
            for (n2 = 19; n2 < 67; ++n2) {
                if (!Collation.isSpecialCE32(nArray[n2])) continue;
                n3 = 1;
                break;
            }
            n2 = Collation.makeCE32FromTagAndIndex(12, 0);
            n = 44032;
            for (int i = 0; i < 19; ++i) {
                int n5 = n2;
                if (n3 == 0 && !Collation.isSpecialCE32(nArray[i])) {
                    n5 |= 0x100;
                }
                int n6 = n + 588;
                this.trie.setRange(n, n6 - 1, n5, false);
                n = n6;
            }
        } else {
            n3 = 44032;
            while (n3 < 55204) {
                n2 = this.base.getCE32(n3);
                if (!$assertionsDisabled && !Collation.hasCE32Tag(n2, 12)) {
                    throw new AssertionError();
                }
                n = n3 + 588;
                this.trie.setRange(n3, n - 1, n2, false);
                n3 = n;
            }
        }
        this.setDigitTags();
        this.setLeadSurrogates();
        this.ce32s.setElementAt(this.trie.get(0), 0);
        this.trie.set(0, Collation.makeCE32FromTagAndIndex(11, 0));
        collationData.trie = this.trie.toTrie2_32();
        n3 = 65536;
        n2 = 55296;
        while (n2 < 56320) {
            if (this.unsafeBackwardSet.containsSome(n3, n3 + 1023)) {
                this.unsafeBackwardSet.add(n2);
            }
            n2 = (char)(n2 + 1);
            n3 += 1024;
        }
        this.unsafeBackwardSet.freeze();
        collationData.ce32s = this.ce32s.getBuffer();
        collationData.ces = this.ce64s.getBuffer();
        collationData.contexts = this.contexts.toString();
        collationData.base = this.base;
        collationData.jamoCE32s = n4 >= 0 ? nArray : this.base.jamoCE32s;
        collationData.unsafeBackwardSet = this.unsafeBackwardSet;
    }

    protected void clearContexts() {
        this.contexts.setLength(0);
        UnicodeSetIterator unicodeSetIterator = new UnicodeSetIterator(this.contextChars);
        while (unicodeSetIterator.next()) {
            if (!$assertionsDisabled && unicodeSetIterator.codepoint == UnicodeSetIterator.IS_STRING) {
                throw new AssertionError();
            }
            int n = this.trie.get(unicodeSetIterator.codepoint);
            if (!$assertionsDisabled && !CollationDataBuilder.isBuilderContextCE32(n)) {
                throw new AssertionError();
            }
            this.getConditionalCE32ForCE32((int)n).builtCE32 = 1;
        }
    }

    protected void buildContexts() {
        this.contexts.setLength(0);
        UnicodeSetIterator unicodeSetIterator = new UnicodeSetIterator(this.contextChars);
        while (unicodeSetIterator.next()) {
            if (!$assertionsDisabled && unicodeSetIterator.codepoint == UnicodeSetIterator.IS_STRING) {
                throw new AssertionError();
            }
            int n = unicodeSetIterator.codepoint;
            int n2 = this.trie.get(n);
            if (!CollationDataBuilder.isBuilderContextCE32(n2)) {
                throw new AssertionError((Object)"Impossible: No context data for c in contextChars.");
            }
            ConditionalCE32 conditionalCE32 = this.getConditionalCE32ForCE32(n2);
            n2 = this.buildContext(conditionalCE32);
            this.trie.set(n, n2);
        }
    }

    protected int buildContext(ConditionalCE32 conditionalCE32) {
        if (!$assertionsDisabled && conditionalCE32.hasContext()) {
            throw new AssertionError();
        }
        if (!$assertionsDisabled && conditionalCE32.next < 0) {
            throw new AssertionError();
        }
        CharsTrieBuilder charsTrieBuilder = new CharsTrieBuilder();
        CharsTrieBuilder charsTrieBuilder2 = new CharsTrieBuilder();
        ConditionalCE32 conditionalCE322 = conditionalCE32;
        while (true) {
            int n;
            if (!$assertionsDisabled && conditionalCE322 != conditionalCE32 && !conditionalCE322.hasContext()) {
                throw new AssertionError();
            }
            int n2 = conditionalCE322.prefixLength();
            StringBuilder stringBuilder = new StringBuilder().append(conditionalCE322.context, 0, n2 + 1);
            String string = stringBuilder.toString();
            ConditionalCE32 conditionalCE323 = conditionalCE322;
            ConditionalCE32 conditionalCE324 = conditionalCE322;
            while (conditionalCE322.next >= 0) {
                conditionalCE322 = this.getConditionalCE32(conditionalCE322.next);
                if (!conditionalCE322.context.startsWith(string)) break;
                conditionalCE324 = conditionalCE322;
            }
            int n3 = n2 + 1;
            if (conditionalCE324.context.length() == n3) {
                if (!$assertionsDisabled && conditionalCE323 != conditionalCE324) {
                    throw new AssertionError();
                }
                n = conditionalCE324.ce32;
                conditionalCE322 = conditionalCE324;
            } else {
                int n4;
                charsTrieBuilder2.clear();
                int n5 = 1;
                int n6 = 0;
                if (conditionalCE323.context.length() == n3) {
                    n5 = conditionalCE323.ce32;
                    conditionalCE322 = this.getConditionalCE32(conditionalCE323.next);
                } else {
                    n6 |= 0x100;
                    conditionalCE322 = conditionalCE32;
                    while ((n4 = conditionalCE322.prefixLength()) != n2) {
                        if (conditionalCE322.defaultCE32 != 1 && (n4 == 0 || string.regionMatches(stringBuilder.length() - n4, conditionalCE322.context, 1, n4))) {
                            n5 = conditionalCE322.defaultCE32;
                        }
                        conditionalCE322 = this.getConditionalCE32(conditionalCE322.next);
                    }
                    conditionalCE322 = conditionalCE323;
                }
                n6 |= 0x200;
                while (true) {
                    String string2;
                    int n7;
                    if ((n7 = this.nfcImpl.getFCD16((string2 = conditionalCE322.context.substring(n3)).codePointAt(0))) <= 255) {
                        n6 &= 0xFFFFFDFF;
                    }
                    if ((n7 = this.nfcImpl.getFCD16(string2.codePointBefore(string2.length()))) > 255) {
                        n6 |= 0x400;
                    }
                    charsTrieBuilder2.add(string2, conditionalCE322.ce32);
                    if (conditionalCE322 == conditionalCE324) break;
                    conditionalCE322 = this.getConditionalCE32(conditionalCE322.next);
                }
                n4 = this.addContextTrie(n5, charsTrieBuilder2);
                if (n4 > 524287) {
                    throw new IndexOutOfBoundsException("too many context-sensitive mappings");
                }
                n = Collation.makeCE32FromTagAndIndex(9, n4) | n6;
            }
            if (!$assertionsDisabled && conditionalCE322 != conditionalCE324) {
                throw new AssertionError();
            }
            conditionalCE323.defaultCE32 = n;
            if (n2 == 0) {
                if (conditionalCE322.next < 0) {
                    return n;
                }
            } else {
                stringBuilder.delete(0, 1);
                stringBuilder.reverse();
                charsTrieBuilder.add(stringBuilder, n);
                if (conditionalCE322.next < 0) break;
            }
            conditionalCE322 = this.getConditionalCE32(conditionalCE322.next);
        }
        if (!$assertionsDisabled && conditionalCE32.defaultCE32 == 1) {
            throw new AssertionError();
        }
        int n = this.addContextTrie(conditionalCE32.defaultCE32, charsTrieBuilder);
        if (n > 524287) {
            throw new IndexOutOfBoundsException("too many context-sensitive mappings");
        }
        return Collation.makeCE32FromTagAndIndex(8, n);
    }

    protected int addContextTrie(int n, CharsTrieBuilder charsTrieBuilder) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append((char)(n >> 16)).append((char)n);
        stringBuilder.append(charsTrieBuilder.buildCharSequence(StringTrieBuilder.Option.SMALL));
        int n2 = this.contexts.indexOf(stringBuilder.toString());
        if (n2 < 0) {
            n2 = this.contexts.length();
            this.contexts.append((CharSequence)stringBuilder);
        }
        return n2;
    }

    protected void buildFastLatinTable(CollationData collationData) {
        if (!this.fastLatinEnabled) {
            return;
        }
        this.fastLatinBuilder = new CollationFastLatinBuilder();
        if (this.fastLatinBuilder.forData(collationData)) {
            char[] cArray = this.fastLatinBuilder.getHeader();
            char[] cArray2 = this.fastLatinBuilder.getTable();
            if (this.base != null && Arrays.equals(cArray, this.base.fastLatinTableHeader) && Arrays.equals(cArray2, this.base.fastLatinTable)) {
                this.fastLatinBuilder = null;
                cArray = this.base.fastLatinTableHeader;
                cArray2 = this.base.fastLatinTable;
            }
            collationData.fastLatinTableHeader = cArray;
            collationData.fastLatinTable = cArray2;
        } else {
            this.fastLatinBuilder = null;
        }
    }

    protected int getCEs(CharSequence charSequence, int n, long[] lArray, int n2) {
        if (this.collIter == null) {
            this.collIter = new DataBuilderCollationIterator(this, new CollationData(this.nfcImpl));
            if (this.collIter == null) {
                return 1;
            }
        }
        return this.collIter.fetchCEs(charSequence, n, lArray, n2);
    }

    protected static int jamoCpFromIndex(int n) {
        if (n < 19) {
            return 4352 + n;
        }
        if ((n -= 19) < 21) {
            return 4449 + n;
        }
        return 4520 + (n -= 21);
    }

    protected final boolean isMutable() {
        return this.trie != null && this.unsafeBackwardSet != null && !this.unsafeBackwardSet.isFrozen();
    }

    private static final class DataBuilderCollationIterator
    extends CollationIterator {
        protected final CollationDataBuilder builder;
        protected final CollationData builderData;
        protected final int[] jamoCE32s = new int[67];
        protected CharSequence s;
        protected int pos;
        static final boolean $assertionsDisabled = !CollationDataBuilder.class.desiredAssertionStatus();

        DataBuilderCollationIterator(CollationDataBuilder collationDataBuilder, CollationData collationData) {
            super(collationData, false);
            this.builder = collationDataBuilder;
            this.builderData = collationData;
            this.builderData.base = this.builder.base;
            for (int i = 0; i < 67; ++i) {
                int n = CollationDataBuilder.jamoCpFromIndex(i);
                this.jamoCE32s[i] = Collation.makeCE32FromTagAndIndex(7, n) | 0x100;
            }
            this.builderData.jamoCE32s = this.jamoCE32s;
        }

        int fetchCEs(CharSequence charSequence, int n, long[] lArray, int n2) {
            this.builderData.ce32s = this.builder.ce32s.getBuffer();
            this.builderData.ces = this.builder.ce64s.getBuffer();
            this.builderData.contexts = this.builder.contexts.toString();
            this.reset();
            this.s = charSequence;
            this.pos = n;
            while (this.pos < this.s.length()) {
                CollationData collationData;
                this.clearCEs();
                int n3 = Character.codePointAt(this.s, this.pos);
                this.pos += Character.charCount(n3);
                int n4 = this.builder.trie.get(n3);
                if (n4 == 192) {
                    collationData = this.builder.base;
                    n4 = this.builder.base.getCE32(n3);
                } else {
                    collationData = this.builderData;
                }
                this.appendCEsFromCE32(collationData, n3, n4, false);
                for (int i = 0; i < this.getCEsLength(); ++i) {
                    long l = this.getCE(i);
                    if (l == 0L) continue;
                    if (n2 < 31) {
                        lArray[n2] = l;
                    }
                    ++n2;
                }
            }
            return n2;
        }

        @Override
        public void resetToOffset(int n) {
            this.reset();
            this.pos = n;
        }

        @Override
        public int getOffset() {
            return this.pos;
        }

        @Override
        public int nextCodePoint() {
            if (this.pos == this.s.length()) {
                return 1;
            }
            int n = Character.codePointAt(this.s, this.pos);
            this.pos += Character.charCount(n);
            return n;
        }

        @Override
        public int previousCodePoint() {
            if (this.pos == 0) {
                return 1;
            }
            int n = Character.codePointBefore(this.s, this.pos);
            this.pos -= Character.charCount(n);
            return n;
        }

        @Override
        protected void forwardNumCodePoints(int n) {
            this.pos = Character.offsetByCodePoints(this.s, this.pos, n);
        }

        @Override
        protected void backwardNumCodePoints(int n) {
            this.pos = Character.offsetByCodePoints(this.s, this.pos, -n);
        }

        @Override
        protected int getDataCE32(int n) {
            return this.builder.trie.get(n);
        }

        @Override
        protected int getCE32FromBuilderData(int n) {
            if (!$assertionsDisabled && !Collation.hasCE32Tag(n, 7)) {
                throw new AssertionError();
            }
            if ((n & 0x100) != 0) {
                int n2 = Collation.indexFromCE32(n);
                return this.builder.trie.get(n2);
            }
            ConditionalCE32 conditionalCE32 = this.builder.getConditionalCE32ForCE32(n);
            if (conditionalCE32.builtCE32 == 1) {
                try {
                    conditionalCE32.builtCE32 = this.builder.buildContext(conditionalCE32);
                } catch (IndexOutOfBoundsException indexOutOfBoundsException) {
                    this.builder.clearContexts();
                    conditionalCE32.builtCE32 = this.builder.buildContext(conditionalCE32);
                }
                this.builderData.contexts = this.builder.contexts.toString();
            }
            return conditionalCE32.builtCE32;
        }
    }

    private static final class CopyHelper {
        CollationDataBuilder src;
        CollationDataBuilder dest;
        CEModifier modifier;
        long[] modifiedCEs = new long[31];
        static final boolean $assertionsDisabled = !CollationDataBuilder.class.desiredAssertionStatus();

        CopyHelper(CollationDataBuilder collationDataBuilder, CollationDataBuilder collationDataBuilder2, CEModifier cEModifier) {
            this.src = collationDataBuilder;
            this.dest = collationDataBuilder2;
            this.modifier = cEModifier;
        }

        void copyRangeCE32(int n, int n2, int n3) {
            n3 = this.copyCE32(n3);
            this.dest.trie.setRange(n, n2, n3, false);
            if (CollationDataBuilder.isBuilderContextCE32(n3)) {
                this.dest.contextChars.add(n, n2);
            }
        }

        int copyCE32(int n) {
            if (!Collation.isSpecialCE32(n)) {
                long l = this.modifier.modifyCE32(n);
                if (l != 0x101000100L) {
                    n = this.dest.encodeOneCE(l);
                }
            } else {
                int n2 = Collation.tagFromCE32(n);
                if (n2 == 5) {
                    int[] nArray = this.src.ce32s.getBuffer();
                    int n3 = Collation.indexFromCE32(n);
                    int n4 = Collation.lengthFromCE32(n);
                    boolean bl = false;
                    for (int i = 0; i < n4; ++i) {
                        long l;
                        n = nArray[n3 + i];
                        if (Collation.isSpecialCE32(n) || (l = this.modifier.modifyCE32(n)) == 0x101000100L) {
                            if (!bl) continue;
                            this.modifiedCEs[i] = Collation.ceFromCE32(n);
                            continue;
                        }
                        if (!bl) {
                            for (int j = 0; j < i; ++j) {
                                this.modifiedCEs[j] = Collation.ceFromCE32(nArray[n3 + j]);
                            }
                            bl = true;
                        }
                        this.modifiedCEs[i] = l;
                    }
                    n = bl ? this.dest.encodeCEs(this.modifiedCEs, n4) : this.dest.encodeExpansion32(nArray, n3, n4);
                } else if (n2 == 6) {
                    long[] lArray = this.src.ce64s.getBuffer();
                    int n5 = Collation.indexFromCE32(n);
                    int n6 = Collation.lengthFromCE32(n);
                    boolean bl = false;
                    for (int i = 0; i < n6; ++i) {
                        long l = lArray[n5 + i];
                        long l2 = this.modifier.modifyCE(l);
                        if (l2 == 0x101000100L) {
                            if (!bl) continue;
                            this.modifiedCEs[i] = l;
                            continue;
                        }
                        if (!bl) {
                            for (int j = 0; j < i; ++j) {
                                this.modifiedCEs[j] = lArray[n5 + j];
                            }
                            bl = true;
                        }
                        this.modifiedCEs[i] = l2;
                    }
                    n = bl ? this.dest.encodeCEs(this.modifiedCEs, n6) : this.dest.encodeExpansion(lArray, n5, n6);
                } else if (n2 == 7) {
                    ConditionalCE32 conditionalCE32 = this.src.getConditionalCE32ForCE32(n);
                    if (!$assertionsDisabled && conditionalCE32.hasContext()) {
                        throw new AssertionError();
                    }
                    int n7 = this.dest.addConditionalCE32(conditionalCE32.context, this.copyCE32(conditionalCE32.ce32));
                    n = CollationDataBuilder.makeBuilderContextCE32(n7);
                    while (conditionalCE32.next >= 0) {
                        conditionalCE32 = this.src.getConditionalCE32(conditionalCE32.next);
                        ConditionalCE32 conditionalCE322 = this.dest.getConditionalCE32(n7);
                        n7 = this.dest.addConditionalCE32(conditionalCE32.context, this.copyCE32(conditionalCE32.ce32));
                        int n8 = conditionalCE32.prefixLength() + 1;
                        this.dest.unsafeBackwardSet.addAll(conditionalCE32.context.substring(n8));
                        conditionalCE322.next = n7;
                    }
                } else if (!$assertionsDisabled && n2 != 1 && n2 != 2 && n2 != 4 && n2 != 12) {
                    throw new AssertionError();
                }
            }
            return n;
        }
    }

    private static final class ConditionalCE32 {
        String context;
        int ce32;
        int defaultCE32;
        int builtCE32;
        int next;

        ConditionalCE32(String string, int n) {
            this.context = string;
            this.ce32 = n;
            this.defaultCE32 = 1;
            this.builtCE32 = 1;
            this.next = -1;
        }

        boolean hasContext() {
            return this.context.length() > 1;
        }

        int prefixLength() {
            return this.context.charAt(0);
        }
    }

    static interface CEModifier {
        public long modifyCE32(int var1);

        public long modifyCE(long var1);
    }
}

