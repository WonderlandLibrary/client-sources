package com.ibm.icu.impl.coll;

import com.ibm.icu.impl.*;
import com.ibm.icu.text.*;
import com.ibm.icu.lang.*;
import com.ibm.icu.util.*;
import java.util.*;

final class CollationDataBuilder
{
    private static final int IS_BUILDER_JAMO_CE32 = 256;
    protected Normalizer2Impl nfcImpl;
    protected CollationData base;
    protected CollationSettings baseSettings;
    protected Trie2Writable trie;
    protected UVector32 ce32s;
    protected UVector64 ce64s;
    protected ArrayList<ConditionalCE32> conditionalCE32s;
    protected UnicodeSet contextChars;
    protected StringBuilder contexts;
    protected UnicodeSet unsafeBackwardSet;
    protected boolean modified;
    protected boolean fastLatinEnabled;
    protected CollationFastLatinBuilder fastLatinBuilder;
    protected DataBuilderCollationIterator collIter;
    static final /* synthetic */ boolean $assertionsDisabled;
    
    CollationDataBuilder() {
        this.contextChars = new UnicodeSet();
        this.contexts = new StringBuilder();
        this.unsafeBackwardSet = new UnicodeSet();
        this.nfcImpl = Norm2AllModes.getNFCInstance().impl;
        this.base = null;
        this.baseSettings = null;
        this.trie = null;
        this.ce32s = new UVector32();
        this.ce64s = new UVector64();
        this.conditionalCE32s = new ArrayList<ConditionalCE32>();
        this.modified = false;
        this.fastLatinEnabled = false;
        this.fastLatinBuilder = null;
        this.collIter = null;
        this.ce32s.addElement(0);
    }
    
    void initForTailoring(final CollationData b) {
        if (this.trie != null) {
            throw new IllegalStateException("attempt to reuse a CollationDataBuilder");
        }
        if (b == null) {
            throw new IllegalArgumentException("null CollationData");
        }
        this.base = b;
        this.trie = new Trie2Writable(192, -195323);
        for (int c = 192; c <= 255; ++c) {
            this.trie.set(c, 192);
        }
        final int hangulCE32 = Collation.makeCE32FromTagAndIndex(12, 0);
        this.trie.setRange(44032, 55203, hangulCE32, true);
        this.unsafeBackwardSet.addAll(b.unsafeBackwardSet);
    }
    
    boolean isCompressibleLeadByte(final int b) {
        return this.base.isCompressibleLeadByte(b);
    }
    
    boolean isCompressiblePrimary(final long p) {
        return this.isCompressibleLeadByte((int)p >>> 24);
    }
    
    boolean hasMappings() {
        return this.modified;
    }
    
    boolean isAssigned(final int c) {
        return Collation.isAssignedCE32(this.trie.get(c));
    }
    
    void add(final CharSequence prefix, final CharSequence s, final long[] ces, final int cesLength) {
        final int ce32 = this.encodeCEs(ces, cesLength);
        this.addCE32(prefix, s, ce32);
    }
    
    int encodeCEs(final long[] ces, final int cesLength) {
        if (cesLength < 0 || cesLength > 31) {
            throw new IllegalArgumentException("mapping to too many CEs");
        }
        if (!this.isMutable()) {
            throw new IllegalStateException("attempt to add mappings after build()");
        }
        if (cesLength == 0) {
            return encodeOneCEAsCE32(0L);
        }
        if (cesLength == 1) {
            return this.encodeOneCE(ces[0]);
        }
        if (cesLength == 2) {
            final long ce0 = ces[0];
            final long ce2 = ces[1];
            final long p0 = ce0 >>> 32;
            if ((ce0 & 0xFFFFFFFFFF00FFL) == 0x5000000L && (ce2 & 0xFFFFFFFF00FFFFFFL) == 0x500L && p0 != 0L) {
                return (int)p0 | ((int)ce0 & 0xFF00) << 8 | ((int)ce2 >> 16 & 0xFF00) | 0xC0 | 0x4;
            }
        }
        final int[] newCE32s = new int[31];
        for (int i = 0; i != cesLength; ++i) {
            final int ce3 = encodeOneCEAsCE32(ces[i]);
            if (ce3 == 1) {
                return this.encodeExpansion(ces, 0, cesLength);
            }
            newCE32s[i] = ce3;
        }
        return this.encodeExpansion32(newCE32s, 0, cesLength);
    }
    
    void addCE32(final CharSequence prefix, final CharSequence s, final int ce32) {
        if (s.length() == 0) {
            throw new IllegalArgumentException("mapping from empty string");
        }
        if (!this.isMutable()) {
            throw new IllegalStateException("attempt to add mappings after build()");
        }
        final int c = Character.codePointAt(s, 0);
        final int cLength = Character.charCount(c);
        int oldCE32 = this.trie.get(c);
        final boolean hasContext = prefix.length() != 0 || s.length() > cLength;
        if (oldCE32 == 192) {
            final int baseCE32 = this.base.getFinalCE32(this.base.getCE32(c));
            if (hasContext || Collation.ce32HasContext(baseCE32)) {
                oldCE32 = this.copyFromBaseCE32(c, baseCE32, true);
                this.trie.set(c, oldCE32);
            }
        }
        if (!hasContext) {
            if (!isBuilderContextCE32(oldCE32)) {
                this.trie.set(c, ce32);
            }
            else {
                final ConditionalCE32 cond = this.getConditionalCE32ForCE32(oldCE32);
                cond.builtCE32 = 1;
                cond.ce32 = ce32;
            }
        }
        else {
            ConditionalCE32 cond;
            if (!isBuilderContextCE32(oldCE32)) {
                final int index = this.addConditionalCE32("\u0000", oldCE32);
                final int contextCE32 = makeBuilderContextCE32(index);
                this.trie.set(c, contextCE32);
                this.contextChars.add(c);
                cond = this.getConditionalCE32(index);
            }
            else {
                cond = this.getConditionalCE32ForCE32(oldCE32);
                cond.builtCE32 = 1;
            }
            final CharSequence suffix = s.subSequence(cLength, s.length());
            final String context = new StringBuilder().append((char)prefix.length()).append(prefix).append(suffix).toString();
            this.unsafeBackwardSet.addAll(suffix);
            while (true) {
                final int next = cond.next;
                if (next < 0) {
                    final int index2 = this.addConditionalCE32(context, ce32);
                    cond.next = index2;
                    break;
                }
                final ConditionalCE32 nextCond = this.getConditionalCE32(next);
                final int cmp = context.compareTo(nextCond.context);
                if (cmp < 0) {
                    final int index3 = this.addConditionalCE32(context, ce32);
                    cond.next = index3;
                    this.getConditionalCE32(index3).next = next;
                    break;
                }
                if (cmp == 0) {
                    nextCond.ce32 = ce32;
                    break;
                }
                cond = nextCond;
            }
        }
        this.modified = true;
    }
    
    void copyFrom(final CollationDataBuilder src, final CEModifier modifier) {
        if (!this.isMutable()) {
            throw new IllegalStateException("attempt to copyFrom() after build()");
        }
        final CopyHelper helper = new CopyHelper(src, this, modifier);
        final Iterator<Trie2.Range> trieIterator = src.trie.iterator();
        Trie2.Range range;
        while (trieIterator.hasNext() && !(range = trieIterator.next()).leadSurrogate) {
            enumRangeForCopy(range.startCodePoint, range.endCodePoint, range.value, helper);
        }
        this.modified |= src.modified;
    }
    
    void optimize(final UnicodeSet set) {
        if (set.isEmpty()) {
            return;
        }
        final UnicodeSetIterator iter = new UnicodeSetIterator(set);
        while (iter.next() && iter.codepoint != UnicodeSetIterator.IS_STRING) {
            final int c = iter.codepoint;
            int ce32 = this.trie.get(c);
            if (ce32 == 192) {
                ce32 = this.base.getFinalCE32(this.base.getCE32(c));
                ce32 = this.copyFromBaseCE32(c, ce32, true);
                this.trie.set(c, ce32);
            }
        }
        this.modified = true;
    }
    
    void suppressContractions(final UnicodeSet set) {
        if (set.isEmpty()) {
            return;
        }
        final UnicodeSetIterator iter = new UnicodeSetIterator(set);
        while (iter.next() && iter.codepoint != UnicodeSetIterator.IS_STRING) {
            final int c = iter.codepoint;
            int ce32 = this.trie.get(c);
            if (ce32 == 192) {
                ce32 = this.base.getFinalCE32(this.base.getCE32(c));
                if (!Collation.ce32HasContext(ce32)) {
                    continue;
                }
                ce32 = this.copyFromBaseCE32(c, ce32, false);
                this.trie.set(c, ce32);
            }
            else {
                if (!isBuilderContextCE32(ce32)) {
                    continue;
                }
                ce32 = this.getConditionalCE32ForCE32(ce32).ce32;
                this.trie.set(c, ce32);
                this.contextChars.remove(c);
            }
        }
        this.modified = true;
    }
    
    void enableFastLatin() {
        this.fastLatinEnabled = true;
    }
    
    void build(final CollationData data) {
        this.buildMappings(data);
        if (this.base != null) {
            data.numericPrimary = this.base.numericPrimary;
            data.compressibleBytes = this.base.compressibleBytes;
            data.numScripts = this.base.numScripts;
            data.scriptsIndex = this.base.scriptsIndex;
            data.scriptStarts = this.base.scriptStarts;
        }
        this.buildFastLatinTable(data);
    }
    
    int getCEs(final CharSequence s, final long[] ces, final int cesLength) {
        return this.getCEs(s, 0, ces, cesLength);
    }
    
    int getCEs(final CharSequence prefix, final CharSequence s, final long[] ces, final int cesLength) {
        final int prefixLength = prefix.length();
        if (prefixLength == 0) {
            return this.getCEs(s, 0, ces, cesLength);
        }
        return this.getCEs(new StringBuilder(prefix).append(s), prefixLength, ces, cesLength);
    }
    
    protected int getCE32FromOffsetCE32(final boolean fromBase, final int c, final int ce32) {
        final int i = Collation.indexFromCE32(ce32);
        final long dataCE = fromBase ? this.base.ces[i] : this.ce64s.elementAti(i);
        final long p = Collation.getThreeBytePrimaryForOffsetData(c, dataCE);
        return Collation.makeLongPrimaryCE32(p);
    }
    
    protected int addCE(final long ce) {
        final int length = this.ce64s.size();
        for (int i = 0; i < length; ++i) {
            if (ce == this.ce64s.elementAti(i)) {
                return i;
            }
        }
        this.ce64s.addElement(ce);
        return length;
    }
    
    protected int addCE32(final int ce32) {
        final int length = this.ce32s.size();
        for (int i = 0; i < length; ++i) {
            if (ce32 == this.ce32s.elementAti(i)) {
                return i;
            }
        }
        this.ce32s.addElement(ce32);
        return length;
    }
    
    protected int addConditionalCE32(final String context, final int ce32) {
        assert context.length() != 0;
        final int index = this.conditionalCE32s.size();
        if (index > 524287) {
            throw new IndexOutOfBoundsException("too many context-sensitive mappings");
        }
        final ConditionalCE32 cond = new ConditionalCE32(context, ce32);
        this.conditionalCE32s.add(cond);
        return index;
    }
    
    protected ConditionalCE32 getConditionalCE32(final int index) {
        return this.conditionalCE32s.get(index);
    }
    
    protected ConditionalCE32 getConditionalCE32ForCE32(final int ce32) {
        return this.getConditionalCE32(Collation.indexFromCE32(ce32));
    }
    
    protected static int makeBuilderContextCE32(final int index) {
        return Collation.makeCE32FromTagAndIndex(7, index);
    }
    
    protected static boolean isBuilderContextCE32(final int ce32) {
        return Collation.hasCE32Tag(ce32, 7);
    }
    
    protected static int encodeOneCEAsCE32(final long ce) {
        final long p = ce >>> 32;
        final int lower32 = (int)ce;
        final int t = lower32 & 0xFFFF;
        assert (t & 0xC000) != 0xC000;
        if ((ce & 0xFFFF00FF00FFL) == 0x0L) {
            return (int)p | lower32 >>> 16 | t >> 8;
        }
        if ((ce & 0xFFFFFFFFFFL) == 0x5000500L) {
            return Collation.makeLongPrimaryCE32(p);
        }
        if (p == 0L && (t & 0xFF) == 0x0) {
            return Collation.makeLongSecondaryCE32(lower32);
        }
        return 1;
    }
    
    protected int encodeOneCE(final long ce) {
        final int ce2 = encodeOneCEAsCE32(ce);
        if (ce2 != 1) {
            return ce2;
        }
        final int index = this.addCE(ce);
        if (index > 524287) {
            throw new IndexOutOfBoundsException("too many mappings");
        }
        return Collation.makeCE32FromTagIndexAndLength(6, index, 1);
    }
    
    protected int encodeExpansion(final long[] ces, final int start, final int length) {
        final long first = ces[start];
    Label_0107:
        for (int ce64sMax = this.ce64s.size() - length, i = 0; i <= ce64sMax; ++i) {
            if (first == this.ce64s.elementAti(i)) {
                if (i > 524287) {
                    throw new IndexOutOfBoundsException("too many mappings");
                }
                for (int j = 1; j != length; ++j) {
                    if (this.ce64s.elementAti(i + j) != ces[start + j]) {
                        continue Label_0107;
                    }
                }
                return Collation.makeCE32FromTagIndexAndLength(6, i, length);
            }
        }
        int i = this.ce64s.size();
        if (i > 524287) {
            throw new IndexOutOfBoundsException("too many mappings");
        }
        for (int j = 0; j < length; ++j) {
            this.ce64s.addElement(ces[start + j]);
        }
        return Collation.makeCE32FromTagIndexAndLength(6, i, length);
    }
    
    protected int encodeExpansion32(final int[] newCE32s, final int start, final int length) {
        final int first = newCE32s[start];
    Label_0104:
        for (int ce32sMax = this.ce32s.size() - length, i = 0; i <= ce32sMax; ++i) {
            if (first == this.ce32s.elementAti(i)) {
                if (i > 524287) {
                    throw new IndexOutOfBoundsException("too many mappings");
                }
                for (int j = 1; j != length; ++j) {
                    if (this.ce32s.elementAti(i + j) != newCE32s[start + j]) {
                        continue Label_0104;
                    }
                }
                return Collation.makeCE32FromTagIndexAndLength(5, i, length);
            }
        }
        int i = this.ce32s.size();
        if (i > 524287) {
            throw new IndexOutOfBoundsException("too many mappings");
        }
        for (int j = 0; j < length; ++j) {
            this.ce32s.addElement(newCE32s[start + j]);
        }
        return Collation.makeCE32FromTagIndexAndLength(5, i, length);
    }
    
    protected int copyFromBaseCE32(final int c, int ce32, final boolean withContext) {
        if (!Collation.isSpecialCE32(ce32)) {
            return ce32;
        }
        switch (Collation.tagFromCE32(ce32)) {
            case 1:
            case 2:
            case 4: {
                break;
            }
            case 5: {
                final int index = Collation.indexFromCE32(ce32);
                final int length = Collation.lengthFromCE32(ce32);
                ce32 = this.encodeExpansion32(this.base.ce32s, index, length);
                break;
            }
            case 6: {
                final int index = Collation.indexFromCE32(ce32);
                final int length = Collation.lengthFromCE32(ce32);
                ce32 = this.encodeExpansion(this.base.ces, index, length);
                break;
            }
            case 8: {
                final int trieIndex = Collation.indexFromCE32(ce32);
                ce32 = this.base.getCE32FromContexts(trieIndex);
                if (!withContext) {
                    return this.copyFromBaseCE32(c, ce32, false);
                }
                final ConditionalCE32 head = new ConditionalCE32("", 0);
                final StringBuilder context = new StringBuilder("\u0000");
                int index2;
                if (Collation.isContractionCE32(ce32)) {
                    index2 = this.copyContractionsFromBaseCE32(context, c, ce32, head);
                }
                else {
                    ce32 = this.copyFromBaseCE32(c, ce32, true);
                    index2 = (head.next = this.addConditionalCE32(context.toString(), ce32));
                }
                ConditionalCE32 cond = this.getConditionalCE32(index2);
                final CharsTrie.Iterator prefixes = CharsTrie.iterator(this.base.contexts, trieIndex + 2, 0);
                while (prefixes.hasNext()) {
                    final CharsTrie.Entry entry = prefixes.next();
                    context.setLength(0);
                    context.append(entry.chars).reverse().insert(0, (char)entry.chars.length());
                    ce32 = entry.value;
                    if (Collation.isContractionCE32(ce32)) {
                        index2 = this.copyContractionsFromBaseCE32(context, c, ce32, cond);
                    }
                    else {
                        ce32 = this.copyFromBaseCE32(c, ce32, true);
                        index2 = (cond.next = this.addConditionalCE32(context.toString(), ce32));
                    }
                    cond = this.getConditionalCE32(index2);
                }
                ce32 = makeBuilderContextCE32(head.next);
                this.contextChars.add(c);
                break;
            }
            case 9: {
                if (!withContext) {
                    final int index = Collation.indexFromCE32(ce32);
                    ce32 = this.base.getCE32FromContexts(index);
                    return this.copyFromBaseCE32(c, ce32, false);
                }
                final ConditionalCE32 head2 = new ConditionalCE32("", 0);
                final StringBuilder context2 = new StringBuilder("\u0000");
                this.copyContractionsFromBaseCE32(context2, c, ce32, head2);
                ce32 = makeBuilderContextCE32(head2.next);
                this.contextChars.add(c);
                break;
            }
            case 12: {
                throw new UnsupportedOperationException("We forbid tailoring of Hangul syllables.");
            }
            case 14: {
                ce32 = this.getCE32FromOffsetCE32(true, c, ce32);
                break;
            }
            case 15: {
                ce32 = this.encodeOneCE(Collation.unassignedCEFromCodePoint(c));
                break;
            }
            default: {
                throw new AssertionError((Object)"copyFromBaseCE32(c, ce32, withContext) requires ce32 == base.getFinalCE32(ce32)");
            }
        }
        return ce32;
    }
    
    protected int copyContractionsFromBaseCE32(final StringBuilder context, final int c, int ce32, ConditionalCE32 cond) {
        final int trieIndex = Collation.indexFromCE32(ce32);
        int index;
        if ((ce32 & 0x100) != 0x0) {
            assert context.length() > 1;
            index = -1;
        }
        else {
            ce32 = this.base.getCE32FromContexts(trieIndex);
            assert !Collation.isContractionCE32(ce32);
            ce32 = this.copyFromBaseCE32(c, ce32, true);
            index = (cond.next = this.addConditionalCE32(context.toString(), ce32));
            cond = this.getConditionalCE32(index);
        }
        final int suffixStart = context.length();
        final CharsTrie.Iterator suffixes = CharsTrie.iterator(this.base.contexts, trieIndex + 2, 0);
        while (suffixes.hasNext()) {
            final CharsTrie.Entry entry = suffixes.next();
            context.append(entry.chars);
            ce32 = this.copyFromBaseCE32(c, entry.value, true);
            index = (cond.next = this.addConditionalCE32(context.toString(), ce32));
            cond = this.getConditionalCE32(index);
            context.setLength(suffixStart);
        }
        assert index >= 0;
        return index;
    }
    
    private static void enumRangeForCopy(final int start, final int end, final int value, final CopyHelper helper) {
        if (value != -1 && value != 192) {
            helper.copyRangeCE32(start, end, value);
        }
    }
    
    protected boolean getJamoCE32s(final int[] jamoCE32s) {
        boolean anyJamoAssigned = this.base == null;
        boolean needToCopyFromBase = false;
        for (int j = 0; j < 67; ++j) {
            final int jamo = jamoCpFromIndex(j);
            boolean fromBase = false;
            int ce32 = this.trie.get(jamo);
            anyJamoAssigned |= Collation.isAssignedCE32(ce32);
            if (ce32 == 192) {
                fromBase = true;
                ce32 = this.base.getCE32(jamo);
            }
            if (Collation.isSpecialCE32(ce32)) {
                switch (Collation.tagFromCE32(ce32)) {
                    case 5:
                    case 6:
                    case 8:
                    case 9: {
                        if (fromBase) {
                            ce32 = 192;
                            needToCopyFromBase = true;
                            break;
                        }
                        break;
                    }
                    case 15: {
                        assert fromBase;
                        ce32 = 192;
                        needToCopyFromBase = true;
                        break;
                    }
                    case 14: {
                        ce32 = this.getCE32FromOffsetCE32(fromBase, jamo, ce32);
                        break;
                    }
                    case 0:
                    case 3:
                    case 7:
                    case 10:
                    case 11:
                    case 12:
                    case 13: {
                        throw new AssertionError((Object)String.format("unexpected special tag in ce32=0x%08x", ce32));
                    }
                }
            }
            jamoCE32s[j] = ce32;
        }
        if (anyJamoAssigned && needToCopyFromBase) {
            for (int j = 0; j < 67; ++j) {
                if (jamoCE32s[j] == 192) {
                    final int jamo = jamoCpFromIndex(j);
                    jamoCE32s[j] = this.copyFromBaseCE32(jamo, this.base.getCE32(jamo), true);
                }
            }
        }
        return anyJamoAssigned;
    }
    
    protected void setDigitTags() {
        final UnicodeSet digits = new UnicodeSet("[:Nd:]");
        final UnicodeSetIterator iter = new UnicodeSetIterator(digits);
        while (iter.next()) {
            assert iter.codepoint != UnicodeSetIterator.IS_STRING;
            final int c = iter.codepoint;
            int ce32 = this.trie.get(c);
            if (ce32 == 192 || ce32 == -1) {
                continue;
            }
            final int index = this.addCE32(ce32);
            if (index > 524287) {
                throw new IndexOutOfBoundsException("too many mappings");
            }
            ce32 = Collation.makeCE32FromTagIndexAndLength(10, index, UCharacter.digit(c));
            this.trie.set(c, ce32);
        }
    }
    
    protected void setLeadSurrogates() {
        for (char lead = '\ud800'; lead < '\udc00'; ++lead) {
            int leadValue = -1;
            final Iterator<Trie2.Range> trieIterator = this.trie.iteratorForLeadSurrogate(lead);
            while (trieIterator.hasNext()) {
                final Trie2.Range range = trieIterator.next();
                int value = range.value;
                if (value == -1) {
                    value = 0;
                }
                else {
                    if (value != 192) {
                        leadValue = 512;
                        break;
                    }
                    value = 256;
                }
                if (leadValue < 0) {
                    leadValue = value;
                }
                else {
                    if (leadValue != value) {
                        leadValue = 512;
                        break;
                    }
                    continue;
                }
            }
            this.trie.setForLeadSurrogateCodeUnit(lead, Collation.makeCE32FromTagAndIndex(13, 0) | leadValue);
        }
    }
    
    protected void buildMappings(final CollationData data) {
        if (!this.isMutable()) {
            throw new IllegalStateException("attempt to build() after build()");
        }
        this.buildContexts();
        final int[] jamoCE32s = new int[67];
        int jamoIndex = -1;
        if (this.getJamoCE32s(jamoCE32s)) {
            jamoIndex = this.ce32s.size();
            for (int i = 0; i < 67; ++i) {
                this.ce32s.addElement(jamoCE32s[i]);
            }
            boolean isAnyJamoVTSpecial = false;
            for (int j = 19; j < 67; ++j) {
                if (Collation.isSpecialCE32(jamoCE32s[j])) {
                    isAnyJamoVTSpecial = true;
                    break;
                }
            }
            final int hangulCE32 = Collation.makeCE32FromTagAndIndex(12, 0);
            int c = 44032;
            for (int k = 0; k < 19; ++k) {
                int ce32 = hangulCE32;
                if (!isAnyJamoVTSpecial && !Collation.isSpecialCE32(jamoCE32s[k])) {
                    ce32 |= 0x100;
                }
                final int limit = c + 588;
                this.trie.setRange(c, limit - 1, ce32, true);
                c = limit;
            }
        }
        else {
            int limit2;
            for (int c2 = 44032; c2 < 55204; c2 = limit2) {
                final int ce33 = this.base.getCE32(c2);
                assert Collation.hasCE32Tag(ce33, 12);
                limit2 = c2 + 588;
                this.trie.setRange(c2, limit2 - 1, ce33, true);
            }
        }
        this.setDigitTags();
        this.setLeadSurrogates();
        this.ce32s.setElementAt(this.trie.get(0), 0);
        this.trie.set(0, Collation.makeCE32FromTagAndIndex(11, 0));
        data.trie = this.trie.toTrie2_32();
        int c2 = 65536;
        for (char lead = '\ud800'; lead < '\udc00'; ++lead, c2 += 1024) {
            if (this.unsafeBackwardSet.containsSome(c2, c2 + 1023)) {
                this.unsafeBackwardSet.add(lead);
            }
        }
        this.unsafeBackwardSet.freeze();
        data.ce32s = this.ce32s.getBuffer();
        data.ces = this.ce64s.getBuffer();
        data.contexts = this.contexts.toString();
        data.base = this.base;
        if (jamoIndex >= 0) {
            data.jamoCE32s = jamoCE32s;
        }
        else {
            data.jamoCE32s = this.base.jamoCE32s;
        }
        data.unsafeBackwardSet = this.unsafeBackwardSet;
    }
    
    protected void clearContexts() {
        this.contexts.setLength(0);
        final UnicodeSetIterator iter = new UnicodeSetIterator(this.contextChars);
        while (iter.next()) {
            assert iter.codepoint != UnicodeSetIterator.IS_STRING;
            final int ce32 = this.trie.get(iter.codepoint);
            assert isBuilderContextCE32(ce32);
            this.getConditionalCE32ForCE32(ce32).builtCE32 = 1;
        }
    }
    
    protected void buildContexts() {
        this.contexts.setLength(0);
        final UnicodeSetIterator iter = new UnicodeSetIterator(this.contextChars);
        while (iter.next()) {
            assert iter.codepoint != UnicodeSetIterator.IS_STRING;
            final int c = iter.codepoint;
            int ce32 = this.trie.get(c);
            if (!isBuilderContextCE32(ce32)) {
                throw new AssertionError((Object)"Impossible: No context data for c in contextChars.");
            }
            final ConditionalCE32 cond = this.getConditionalCE32ForCE32(ce32);
            ce32 = this.buildContext(cond);
            this.trie.set(c, ce32);
        }
    }
    
    protected int buildContext(final ConditionalCE32 head) {
        assert !head.hasContext();
        assert head.next >= 0;
        final CharsTrieBuilder prefixBuilder = new CharsTrieBuilder();
        final CharsTrieBuilder contractionBuilder = new CharsTrieBuilder();
        for (ConditionalCE32 cond = head; CollationDataBuilder.$assertionsDisabled || cond == head || cond.hasContext(); cond = this.getConditionalCE32(cond.next)) {
            final int prefixLength = cond.prefixLength();
            final StringBuilder prefix = new StringBuilder().append(cond.context, 0, prefixLength + 1);
            final String prefixString = prefix.toString();
            final ConditionalCE32 firstCond = cond;
            ConditionalCE32 lastCond = cond;
            while (cond.next >= 0 && (cond = this.getConditionalCE32(cond.next)).context.startsWith(prefixString)) {
                lastCond = cond;
            }
            final int suffixStart = prefixLength + 1;
            int ce32;
            if (lastCond.context.length() == suffixStart) {
                assert firstCond == lastCond;
                ce32 = lastCond.ce32;
                cond = lastCond;
            }
            else {
                contractionBuilder.clear();
                int emptySuffixCE32 = 1;
                int flags = 0;
                if (firstCond.context.length() == suffixStart) {
                    emptySuffixCE32 = firstCond.ce32;
                    cond = this.getConditionalCE32(firstCond.next);
                }
                else {
                    flags |= 0x100;
                    cond = head;
                    while (true) {
                        final int length = cond.prefixLength();
                        if (length == prefixLength) {
                            break;
                        }
                        if (cond.defaultCE32 != 1 && (length == 0 || prefixString.regionMatches(prefix.length() - length, cond.context, 1, length))) {
                            emptySuffixCE32 = cond.defaultCE32;
                        }
                        cond = this.getConditionalCE32(cond.next);
                    }
                    cond = firstCond;
                }
                flags |= 0x200;
                while (true) {
                    final String suffix = cond.context.substring(suffixStart);
                    int fcd16 = this.nfcImpl.getFCD16(suffix.codePointAt(0));
                    if (fcd16 <= 255) {
                        flags &= 0xFFFFFDFF;
                    }
                    fcd16 = this.nfcImpl.getFCD16(suffix.codePointBefore(suffix.length()));
                    if (fcd16 > 255) {
                        flags |= 0x400;
                    }
                    contractionBuilder.add(suffix, cond.ce32);
                    if (cond == lastCond) {
                        break;
                    }
                    cond = this.getConditionalCE32(cond.next);
                }
                final int index = this.addContextTrie(emptySuffixCE32, contractionBuilder);
                if (index > 524287) {
                    throw new IndexOutOfBoundsException("too many context-sensitive mappings");
                }
                ce32 = (Collation.makeCE32FromTagAndIndex(9, index) | flags);
            }
            assert cond == lastCond;
            firstCond.defaultCE32 = ce32;
            if (prefixLength == 0) {
                if (cond.next < 0) {
                    return ce32;
                }
            }
            else {
                prefix.delete(0, 1);
                prefix.reverse();
                prefixBuilder.add(prefix, ce32);
                if (cond.next < 0) {
                    assert head.defaultCE32 != 1;
                    final int index2 = this.addContextTrie(head.defaultCE32, prefixBuilder);
                    if (index2 > 524287) {
                        throw new IndexOutOfBoundsException("too many context-sensitive mappings");
                    }
                    return Collation.makeCE32FromTagAndIndex(8, index2);
                }
            }
        }
        throw new AssertionError();
    }
    
    protected int addContextTrie(final int defaultCE32, final CharsTrieBuilder trieBuilder) {
        final StringBuilder context = new StringBuilder();
        context.append((char)(defaultCE32 >> 16)).append((char)defaultCE32);
        context.append(trieBuilder.buildCharSequence(StringTrieBuilder.Option.SMALL));
        int index = this.contexts.indexOf(context.toString());
        if (index < 0) {
            index = this.contexts.length();
            this.contexts.append((CharSequence)context);
        }
        return index;
    }
    
    protected void buildFastLatinTable(final CollationData data) {
        if (!this.fastLatinEnabled) {
            return;
        }
        this.fastLatinBuilder = new CollationFastLatinBuilder();
        if (this.fastLatinBuilder.forData(data)) {
            char[] header = this.fastLatinBuilder.getHeader();
            char[] table = this.fastLatinBuilder.getTable();
            if (this.base != null && Arrays.equals(header, this.base.fastLatinTableHeader) && Arrays.equals(table, this.base.fastLatinTable)) {
                this.fastLatinBuilder = null;
                header = this.base.fastLatinTableHeader;
                table = this.base.fastLatinTable;
            }
            data.fastLatinTableHeader = header;
            data.fastLatinTable = table;
        }
        else {
            this.fastLatinBuilder = null;
        }
    }
    
    protected int getCEs(final CharSequence s, final int start, final long[] ces, final int cesLength) {
        if (this.collIter == null) {
            this.collIter = new DataBuilderCollationIterator(this, new CollationData(this.nfcImpl));
            if (this.collIter == null) {
                return 0;
            }
        }
        return this.collIter.fetchCEs(s, start, ces, cesLength);
    }
    
    protected static int jamoCpFromIndex(int i) {
        if (i < 19) {
            return 4352 + i;
        }
        i -= 19;
        if (i < 21) {
            return 4449 + i;
        }
        i -= 21;
        return 4520 + i;
    }
    
    protected final boolean isMutable() {
        return this.trie != null && this.unsafeBackwardSet != null && !this.unsafeBackwardSet.isFrozen();
    }
    
    private static final class ConditionalCE32
    {
        String context;
        int ce32;
        int defaultCE32;
        int builtCE32;
        int next;
        
        ConditionalCE32(final String ct, final int ce) {
            this.context = ct;
            this.ce32 = ce;
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
    
    private static final class CopyHelper
    {
        CollationDataBuilder src;
        CollationDataBuilder dest;
        CEModifier modifier;
        long[] modifiedCEs;
        
        CopyHelper(final CollationDataBuilder s, final CollationDataBuilder d, final CEModifier m) {
            this.modifiedCEs = new long[31];
            this.src = s;
            this.dest = d;
            this.modifier = m;
        }
        
        void copyRangeCE32(final int start, final int end, int ce32) {
            ce32 = this.copyCE32(ce32);
            this.dest.trie.setRange(start, end, ce32, true);
            if (CollationDataBuilder.isBuilderContextCE32(ce32)) {
                this.dest.contextChars.add(start, end);
            }
        }
        
        int copyCE32(int ce32) {
            if (!Collation.isSpecialCE32(ce32)) {
                final long ce33 = this.modifier.modifyCE32(ce32);
                if (ce33 != 4311744768L) {
                    ce32 = this.dest.encodeOneCE(ce33);
                }
            }
            else {
                final int tag = Collation.tagFromCE32(ce32);
                if (tag == 5) {
                    final int[] srcCE32s = this.src.ce32s.getBuffer();
                    final int srcIndex = Collation.indexFromCE32(ce32);
                    final int length = Collation.lengthFromCE32(ce32);
                    boolean isModified = false;
                    for (int i = 0; i < length; ++i) {
                        ce32 = srcCE32s[srcIndex + i];
                        final long ce34;
                        if (Collation.isSpecialCE32(ce32) || (ce34 = this.modifier.modifyCE32(ce32)) == 4311744768L) {
                            if (isModified) {
                                this.modifiedCEs[i] = Collation.ceFromCE32(ce32);
                            }
                        }
                        else {
                            if (!isModified) {
                                for (int j = 0; j < i; ++j) {
                                    this.modifiedCEs[j] = Collation.ceFromCE32(srcCE32s[srcIndex + j]);
                                }
                                isModified = true;
                            }
                            this.modifiedCEs[i] = ce34;
                        }
                    }
                    if (isModified) {
                        ce32 = this.dest.encodeCEs(this.modifiedCEs, length);
                    }
                    else {
                        ce32 = this.dest.encodeExpansion32(srcCE32s, srcIndex, length);
                    }
                }
                else if (tag == 6) {
                    final long[] srcCEs = this.src.ce64s.getBuffer();
                    final int srcIndex = Collation.indexFromCE32(ce32);
                    final int length = Collation.lengthFromCE32(ce32);
                    boolean isModified = false;
                    for (int i = 0; i < length; ++i) {
                        final long srcCE = srcCEs[srcIndex + i];
                        final long ce35 = this.modifier.modifyCE(srcCE);
                        if (ce35 == 4311744768L) {
                            if (isModified) {
                                this.modifiedCEs[i] = srcCE;
                            }
                        }
                        else {
                            if (!isModified) {
                                for (int k = 0; k < i; ++k) {
                                    this.modifiedCEs[k] = srcCEs[srcIndex + k];
                                }
                                isModified = true;
                            }
                            this.modifiedCEs[i] = ce35;
                        }
                    }
                    if (isModified) {
                        ce32 = this.dest.encodeCEs(this.modifiedCEs, length);
                    }
                    else {
                        ce32 = this.dest.encodeExpansion(srcCEs, srcIndex, length);
                    }
                }
                else if (tag == 7) {
                    ConditionalCE32 cond = this.src.getConditionalCE32ForCE32(ce32);
                    assert !cond.hasContext();
                    int destIndex = this.dest.addConditionalCE32(cond.context, this.copyCE32(cond.ce32));
                    ce32 = CollationDataBuilder.makeBuilderContextCE32(destIndex);
                    while (cond.next >= 0) {
                        cond = this.src.getConditionalCE32(cond.next);
                        final ConditionalCE32 prevDestCond = this.dest.getConditionalCE32(destIndex);
                        destIndex = this.dest.addConditionalCE32(cond.context, this.copyCE32(cond.ce32));
                        final int suffixStart = cond.prefixLength() + 1;
                        this.dest.unsafeBackwardSet.addAll(cond.context.substring(suffixStart));
                        prevDestCond.next = destIndex;
                    }
                }
                else {
                    assert tag == 12;
                }
            }
            return ce32;
        }
    }
    
    private static final class DataBuilderCollationIterator extends CollationIterator
    {
        protected final CollationDataBuilder builder;
        protected final CollationData builderData;
        protected final int[] jamoCE32s;
        protected CharSequence s;
        protected int pos;
        
        DataBuilderCollationIterator(final CollationDataBuilder b, final CollationData newData) {
            super(newData, false);
            this.jamoCE32s = new int[67];
            this.builder = b;
            this.builderData = newData;
            this.builderData.base = this.builder.base;
            for (int j = 0; j < 67; ++j) {
                final int jamo = CollationDataBuilder.jamoCpFromIndex(j);
                this.jamoCE32s[j] = (Collation.makeCE32FromTagAndIndex(7, jamo) | 0x100);
            }
            this.builderData.jamoCE32s = this.jamoCE32s;
        }
        
        int fetchCEs(final CharSequence str, final int start, final long[] ces, int cesLength) {
            this.builderData.ce32s = this.builder.ce32s.getBuffer();
            this.builderData.ces = this.builder.ce64s.getBuffer();
            this.builderData.contexts = this.builder.contexts.toString();
            this.reset();
            this.s = str;
            this.pos = start;
            while (this.pos < this.s.length()) {
                this.clearCEs();
                final int c = Character.codePointAt(this.s, this.pos);
                this.pos += Character.charCount(c);
                int ce32 = this.builder.trie.get(c);
                CollationData d;
                if (ce32 == 192) {
                    d = this.builder.base;
                    ce32 = this.builder.base.getCE32(c);
                }
                else {
                    d = this.builderData;
                }
                this.appendCEsFromCE32(d, c, ce32, true);
                for (int i = 0; i < this.getCEsLength(); ++i) {
                    final long ce33 = this.getCE(i);
                    if (ce33 != 0L) {
                        if (cesLength < 31) {
                            ces[cesLength] = ce33;
                        }
                        ++cesLength;
                    }
                }
            }
            return cesLength;
        }
        
        @Override
        public void resetToOffset(final int newOffset) {
            this.reset();
            this.pos = newOffset;
        }
        
        @Override
        public int getOffset() {
            return this.pos;
        }
        
        @Override
        public int nextCodePoint() {
            if (this.pos == this.s.length()) {
                return -1;
            }
            final int c = Character.codePointAt(this.s, this.pos);
            this.pos += Character.charCount(c);
            return c;
        }
        
        @Override
        public int previousCodePoint() {
            if (this.pos == 0) {
                return -1;
            }
            final int c = Character.codePointBefore(this.s, this.pos);
            this.pos -= Character.charCount(c);
            return c;
        }
        
        @Override
        protected void forwardNumCodePoints(final int num) {
            this.pos = Character.offsetByCodePoints(this.s, this.pos, num);
        }
        
        @Override
        protected void backwardNumCodePoints(final int num) {
            this.pos = Character.offsetByCodePoints(this.s, this.pos, -num);
        }
        
        @Override
        protected int getDataCE32(final int c) {
            return this.builder.trie.get(c);
        }
        
        @Override
        protected int getCE32FromBuilderData(final int ce32) {
            assert Collation.hasCE32Tag(ce32, 7);
            if ((ce32 & 0x100) != 0x0) {
                final int jamo = Collation.indexFromCE32(ce32);
                return this.builder.trie.get(jamo);
            }
            final ConditionalCE32 cond = this.builder.getConditionalCE32ForCE32(ce32);
            if (cond.builtCE32 == 1) {
                try {
                    cond.builtCE32 = this.builder.buildContext(cond);
                }
                catch (IndexOutOfBoundsException e) {
                    this.builder.clearContexts();
                    cond.builtCE32 = this.builder.buildContext(cond);
                }
                this.builderData.contexts = this.builder.contexts.toString();
            }
            return cond.builtCE32;
        }
    }
    
    interface CEModifier
    {
        long modifyCE32(final int p0);
        
        long modifyCE(final long p0);
    }
}
