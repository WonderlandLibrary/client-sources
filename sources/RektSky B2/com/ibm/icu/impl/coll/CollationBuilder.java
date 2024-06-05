package com.ibm.icu.impl.coll;

import com.ibm.icu.impl.*;
import java.text.*;
import com.ibm.icu.text.*;
import com.ibm.icu.util.*;

public final class CollationBuilder extends CollationRuleParser.Sink
{
    private static final boolean DEBUG = false;
    private static final UnicodeSet COMPOSITES;
    private static final int MAX_INDEX = 1048575;
    private static final int HAS_BEFORE2 = 64;
    private static final int HAS_BEFORE3 = 32;
    private static final int IS_TAILORED = 8;
    private Normalizer2 nfd;
    private Normalizer2 fcd;
    private Normalizer2Impl nfcImpl;
    private CollationTailoring base;
    private CollationData baseData;
    private CollationRootElements rootElements;
    private long variableTop;
    private CollationDataBuilder dataBuilder;
    private boolean fastLatinEnabled;
    private UnicodeSet optimizeSet;
    private long[] ces;
    private int cesLength;
    private UVector32 rootPrimaryIndexes;
    private UVector64 nodes;
    
    public CollationBuilder(final CollationTailoring b) {
        this.optimizeSet = new UnicodeSet();
        this.ces = new long[31];
        this.nfd = Normalizer2.getNFDInstance();
        this.fcd = Norm2AllModes.getFCDNormalizer2();
        this.nfcImpl = Norm2AllModes.getNFCInstance().impl;
        this.base = b;
        this.baseData = b.data;
        this.rootElements = new CollationRootElements(b.data.rootElements);
        this.variableTop = 0L;
        this.dataBuilder = new CollationDataBuilder();
        this.fastLatinEnabled = true;
        this.cesLength = 0;
        this.rootPrimaryIndexes = new UVector32();
        this.nodes = new UVector64();
        this.nfcImpl.ensureCanonIterData();
        this.dataBuilder.initForTailoring(this.baseData);
    }
    
    public CollationTailoring parseAndBuild(final String ruleString) throws ParseException {
        if (this.baseData.rootElements == null) {
            throw new UnsupportedOperationException("missing root elements data, tailoring not supported");
        }
        final CollationTailoring tailoring = new CollationTailoring(this.base.settings);
        final CollationRuleParser parser = new CollationRuleParser(this.baseData);
        this.variableTop = this.base.settings.readOnly().variableTop;
        parser.setSink(this);
        parser.setImporter(new BundleImporter());
        final CollationSettings ownedSettings = tailoring.settings.copyOnWrite();
        parser.parse(ruleString, ownedSettings);
        if (this.dataBuilder.hasMappings()) {
            this.makeTailoredCEs();
            this.closeOverComposites();
            this.finalizeCEs();
            this.optimizeSet.add(0, 127);
            this.optimizeSet.add(192, 255);
            this.optimizeSet.remove(44032, 55203);
            this.dataBuilder.optimize(this.optimizeSet);
            tailoring.ensureOwnedData();
            if (this.fastLatinEnabled) {
                this.dataBuilder.enableFastLatin();
            }
            this.dataBuilder.build(tailoring.ownedData);
            this.dataBuilder = null;
        }
        else {
            tailoring.data = this.baseData;
        }
        ownedSettings.fastLatinOptions = CollationFastLatin.getOptions(tailoring.data, ownedSettings, ownedSettings.fastLatinPrimaries);
        tailoring.setRules(ruleString);
        tailoring.setVersion(this.base.version, 0);
        return tailoring;
    }
    
    @Override
    void addReset(int strength, final CharSequence str) {
        assert str.length() != 0;
        if (str.charAt(0) == '\ufffe') {
            this.ces[0] = this.getSpecialResetPosition(str);
            this.cesLength = 1;
            assert (this.ces[0] & 0xC0C0L) == 0x0L;
        }
        else {
            final String nfdString = this.nfd.normalize(str);
            this.cesLength = this.dataBuilder.getCEs(nfdString, this.ces, 0);
            if (this.cesLength > 31) {
                throw new IllegalArgumentException("reset position maps to too many collation elements (more than 31)");
            }
        }
        if (strength == 15) {
            return;
        }
        assert 0 <= strength && strength <= 2;
        int index;
        long node;
        for (index = this.findOrInsertNodeForCEs(strength), node = this.nodes.elementAti(index); strengthFromNode(node) > strength; node = this.nodes.elementAti(index)) {
            index = previousIndexFromNode(node);
        }
        if (strengthFromNode(node) == strength && isTailoredNode(node)) {
            index = previousIndexFromNode(node);
        }
        else if (strength == 0) {
            long p = weight32FromNode(node);
            if (p == 0L) {
                throw new UnsupportedOperationException("reset primary-before ignorable not possible");
            }
            if (p <= this.rootElements.getFirstPrimary()) {
                throw new UnsupportedOperationException("reset primary-before first non-ignorable not supported");
            }
            if (p == 4278321664L) {
                throw new UnsupportedOperationException("reset primary-before [first trailing] not supported");
            }
            p = this.rootElements.getPrimaryBefore(p, this.baseData.isCompressiblePrimary(p));
            index = this.findOrInsertNodeForPrimary(p);
            while (true) {
                node = this.nodes.elementAti(index);
                final int nextIndex = nextIndexFromNode(node);
                if (nextIndex == 0) {
                    break;
                }
                index = nextIndex;
            }
        }
        else {
            index = this.findCommonNode(index, 1);
            if (strength >= 2) {
                index = this.findCommonNode(index, 2);
            }
            node = this.nodes.elementAti(index);
            if (strengthFromNode(node) == strength) {
                int weight16 = weight16FromNode(node);
                if (weight16 == 0) {
                    throw new UnsupportedOperationException((strength == 1) ? "reset secondary-before secondary ignorable not possible" : "reset tertiary-before completely ignorable not possible");
                }
                assert weight16 > 256;
                weight16 = this.getWeight16Before(index, node, strength);
                int i;
                final int previousIndex = i = previousIndexFromNode(node);
                int previousWeight16;
                while (true) {
                    node = this.nodes.elementAti(i);
                    final int previousStrength = strengthFromNode(node);
                    if (previousStrength < strength) {
                        assert i == previousIndex;
                        previousWeight16 = 1280;
                        break;
                    }
                    else {
                        if (previousStrength == strength && !isTailoredNode(node)) {
                            previousWeight16 = weight16FromNode(node);
                            break;
                        }
                        i = previousIndexFromNode(node);
                    }
                }
                if (previousWeight16 == weight16) {
                    index = previousIndex;
                }
                else {
                    node = (nodeFromWeight16(weight16) | nodeFromStrength(strength));
                    index = this.insertNodeBetween(previousIndex, index, node);
                }
            }
            else {
                final int weight16 = this.getWeight16Before(index, node, strength);
                index = this.findOrInsertWeakNode(index, weight16, strength);
            }
            strength = ceStrength(this.ces[this.cesLength - 1]);
        }
        this.ces[this.cesLength - 1] = tempCEFromIndexAndStrength(index, strength);
    }
    
    private int getWeight16Before(int index, long node, final int level) {
        assert !isTailoredNode(node);
        int t;
        if (strengthFromNode(node) == 2) {
            t = weight16FromNode(node);
        }
        else {
            t = 1280;
        }
        while (strengthFromNode(node) > 1) {
            index = previousIndexFromNode(node);
            node = this.nodes.elementAti(index);
        }
        if (isTailoredNode(node)) {
            return 256;
        }
        int s;
        if (strengthFromNode(node) == 1) {
            s = weight16FromNode(node);
        }
        else {
            s = 1280;
        }
        while (strengthFromNode(node) > 0) {
            index = previousIndexFromNode(node);
            node = this.nodes.elementAti(index);
        }
        if (isTailoredNode(node)) {
            return 256;
        }
        final long p = weight32FromNode(node);
        int weight16;
        if (level == 1) {
            weight16 = this.rootElements.getSecondaryBefore(p, s);
        }
        else {
            weight16 = this.rootElements.getTertiaryBefore(p, s, t);
            assert (weight16 & 0xFFFFC0C0) == 0x0;
        }
        return weight16;
    }
    
    private long getSpecialResetPosition(final CharSequence str) {
        assert str.length() == 2;
        int strength = 0;
        boolean isBoundary = false;
        final CollationRuleParser.Position pos = CollationRuleParser.POSITION_VALUES[str.charAt(1) - '\u2800'];
        long ce = 0L;
        switch (pos) {
            case FIRST_TERTIARY_IGNORABLE: {
                return 0L;
            }
            case LAST_TERTIARY_IGNORABLE: {
                return 0L;
            }
            case FIRST_SECONDARY_IGNORABLE: {
                int index = this.findOrInsertNodeForRootCE(0L, 2);
                long node = this.nodes.elementAti(index);
                if ((index = nextIndexFromNode(node)) != 0) {
                    node = this.nodes.elementAti(index);
                    assert strengthFromNode(node) <= 2;
                    if (isTailoredNode(node) && strengthFromNode(node) == 2) {
                        return tempCEFromIndexAndStrength(index, 2);
                    }
                }
                return this.rootElements.getFirstTertiaryCE();
            }
            case LAST_SECONDARY_IGNORABLE: {
                ce = this.rootElements.getLastTertiaryCE();
                strength = 2;
                break;
            }
            case FIRST_PRIMARY_IGNORABLE: {
                int index = this.findOrInsertNodeForRootCE(0L, 1);
                long node = this.nodes.elementAti(index);
                while ((index = nextIndexFromNode(node)) != 0) {
                    node = this.nodes.elementAti(index);
                    strength = strengthFromNode(node);
                    if (strength < 1) {
                        break;
                    }
                    if (strength != 1) {
                        continue;
                    }
                    if (isTailoredNode(node)) {
                        if (nodeHasBefore3(node)) {
                            index = nextIndexFromNode(this.nodes.elementAti(nextIndexFromNode(node)));
                            assert isTailoredNode(this.nodes.elementAti(index));
                        }
                        return tempCEFromIndexAndStrength(index, 1);
                    }
                    break;
                }
                ce = this.rootElements.getFirstSecondaryCE();
                strength = 1;
                break;
            }
            case LAST_PRIMARY_IGNORABLE: {
                ce = this.rootElements.getLastSecondaryCE();
                strength = 1;
                break;
            }
            case FIRST_VARIABLE: {
                ce = this.rootElements.getFirstPrimaryCE();
                isBoundary = true;
                break;
            }
            case LAST_VARIABLE: {
                ce = this.rootElements.lastCEWithPrimaryBefore(this.variableTop + 1L);
                break;
            }
            case FIRST_REGULAR: {
                ce = this.rootElements.firstCEWithPrimaryAtLeast(this.variableTop + 1L);
                isBoundary = true;
                break;
            }
            case LAST_REGULAR: {
                ce = this.rootElements.firstCEWithPrimaryAtLeast(this.baseData.getFirstPrimaryForGroup(17));
                break;
            }
            case FIRST_IMPLICIT: {
                ce = this.baseData.getSingleCE(19968);
                break;
            }
            case LAST_IMPLICIT: {
                throw new UnsupportedOperationException("reset to [last implicit] not supported");
            }
            case FIRST_TRAILING: {
                ce = Collation.makeCE(4278321664L);
                isBoundary = true;
                break;
            }
            case LAST_TRAILING: {
                throw new IllegalArgumentException("LDML forbids tailoring to U+FFFF");
            }
            default: {
                assert false;
                return 0L;
            }
        }
        int index = this.findOrInsertNodeForRootCE(ce, strength);
        long node = this.nodes.elementAti(index);
        if ((pos.ordinal() & 0x1) == 0x0) {
            if (!nodeHasAnyBefore(node) && isBoundary) {
                if ((index = nextIndexFromNode(node)) != 0) {
                    node = this.nodes.elementAti(index);
                    assert isTailoredNode(node);
                    ce = tempCEFromIndexAndStrength(index, strength);
                }
                else {
                    assert strength == 0;
                    long p = ce >>> 32;
                    final int pIndex = this.rootElements.findPrimary(p);
                    final boolean isCompressible = this.baseData.isCompressiblePrimary(p);
                    p = this.rootElements.getPrimaryAfter(p, pIndex, isCompressible);
                    ce = Collation.makeCE(p);
                    index = this.findOrInsertNodeForRootCE(ce, 0);
                    node = this.nodes.elementAti(index);
                }
            }
            if (nodeHasAnyBefore(node)) {
                if (nodeHasBefore2(node)) {
                    index = nextIndexFromNode(this.nodes.elementAti(nextIndexFromNode(node)));
                    node = this.nodes.elementAti(index);
                }
                if (nodeHasBefore3(node)) {
                    index = nextIndexFromNode(this.nodes.elementAti(nextIndexFromNode(node)));
                }
                assert isTailoredNode(this.nodes.elementAti(index));
                ce = tempCEFromIndexAndStrength(index, strength);
            }
        }
        else {
            while (true) {
                final int nextIndex = nextIndexFromNode(node);
                if (nextIndex == 0) {
                    break;
                }
                final long nextNode = this.nodes.elementAti(nextIndex);
                if (strengthFromNode(nextNode) < strength) {
                    break;
                }
                index = nextIndex;
                node = nextNode;
            }
            if (isTailoredNode(node)) {
                ce = tempCEFromIndexAndStrength(index, strength);
            }
        }
        return ce;
    }
    
    @Override
    void addRelation(final int strength, final CharSequence prefix, final CharSequence str, final CharSequence extension) {
        String nfdPrefix;
        if (prefix.length() == 0) {
            nfdPrefix = "";
        }
        else {
            nfdPrefix = this.nfd.normalize(prefix);
        }
        final String nfdString = this.nfd.normalize(str);
        final int nfdLength = nfdString.length();
        if (nfdLength >= 2) {
            char c = nfdString.charAt(0);
            if (Normalizer2Impl.Hangul.isJamoL(c) || Normalizer2Impl.Hangul.isJamoV(c)) {
                throw new UnsupportedOperationException("contractions starting with conjoining Jamo L or V not supported");
            }
            c = nfdString.charAt(nfdLength - 1);
            if (Normalizer2Impl.Hangul.isJamoL(c) || (Normalizer2Impl.Hangul.isJamoV(c) && Normalizer2Impl.Hangul.isJamoL(nfdString.charAt(nfdLength - 2)))) {
                throw new UnsupportedOperationException("contractions ending with conjoining Jamo L or L+V not supported");
            }
        }
        if (strength != 15) {
            int index = this.findOrInsertNodeForCEs(strength);
            assert this.cesLength > 0;
            final long ce = this.ces[this.cesLength - 1];
            if (strength == 0 && !isTempCE(ce) && ce >>> 32 == 0L) {
                throw new UnsupportedOperationException("tailoring primary after ignorables not supported");
            }
            if (strength == 3 && ce == 0L) {
                throw new UnsupportedOperationException("tailoring quaternary after tertiary ignorables not supported");
            }
            index = this.insertTailoredNodeAfter(index, strength);
            int tempStrength = ceStrength(ce);
            if (strength < tempStrength) {
                tempStrength = strength;
            }
            this.ces[this.cesLength - 1] = tempCEFromIndexAndStrength(index, tempStrength);
        }
        this.setCaseBits(nfdString);
        final int cesLengthBeforeExtension = this.cesLength;
        if (extension.length() != 0) {
            final String nfdExtension = this.nfd.normalize(extension);
            this.cesLength = this.dataBuilder.getCEs(nfdExtension, this.ces, this.cesLength);
            if (this.cesLength > 31) {
                throw new IllegalArgumentException("extension string adds too many collation elements (more than 31 total)");
            }
        }
        int ce2 = -1;
        if ((!nfdPrefix.contentEquals(prefix) || !nfdString.contentEquals(str)) && !this.ignorePrefix(prefix) && !this.ignoreString(str)) {
            ce2 = this.addIfDifferent(prefix, str, this.ces, this.cesLength, ce2);
        }
        this.addWithClosure(nfdPrefix, nfdString, this.ces, this.cesLength, ce2);
        this.cesLength = cesLengthBeforeExtension;
    }
    
    private int findOrInsertNodeForCEs(final int strength) {
        assert 0 <= strength && strength <= 3;
        while (true) {
            while (this.cesLength != 0) {
                final long ce = this.ces[this.cesLength - 1];
                if (ceStrength(ce) <= strength) {
                    if (isTempCE(ce)) {
                        return indexFromTempCE(ce);
                    }
                    if ((int)(ce >>> 56) == 254) {
                        throw new UnsupportedOperationException("tailoring relative to an unassigned code point not supported");
                    }
                    return this.findOrInsertNodeForRootCE(ce, strength);
                }
                else {
                    --this.cesLength;
                }
            }
            final long[] ces = this.ces;
            final int n = 0;
            final long n2 = 0L;
            ces[n] = n2;
            final long ce = n2;
            this.cesLength = 1;
            continue;
        }
    }
    
    private int findOrInsertNodeForRootCE(final long ce, final int strength) {
        assert (int)(ce >>> 56) != 254;
        assert (ce & 0xC0L) == 0x0L;
        int index = this.findOrInsertNodeForPrimary(ce >>> 32);
        if (strength >= 1) {
            final int lower32 = (int)ce;
            index = this.findOrInsertWeakNode(index, lower32 >>> 16, 1);
            if (strength >= 2) {
                index = this.findOrInsertWeakNode(index, lower32 & 0x3F3F, 2);
            }
        }
        return index;
    }
    
    private static final int binarySearchForRootPrimaryNode(final int[] rootPrimaryIndexes, final int length, final long[] nodes, final long p) {
        if (length == 0) {
            return -1;
        }
        int start = 0;
        int limit = length;
        while (true) {
            final int i = (int)((start + (long)limit) / 2L);
            final long node = nodes[rootPrimaryIndexes[i]];
            final long nodePrimary = node >>> 32;
            if (p == nodePrimary) {
                return i;
            }
            if (p < nodePrimary) {
                if (i == start) {
                    return ~start;
                }
                limit = i;
            }
            else {
                if (i == start) {
                    return ~(start + 1);
                }
                start = i;
            }
        }
    }
    
    private int findOrInsertNodeForPrimary(final long p) {
        final int rootIndex = binarySearchForRootPrimaryNode(this.rootPrimaryIndexes.getBuffer(), this.rootPrimaryIndexes.size(), this.nodes.getBuffer(), p);
        if (rootIndex >= 0) {
            return this.rootPrimaryIndexes.elementAti(rootIndex);
        }
        final int index = this.nodes.size();
        this.nodes.addElement(nodeFromWeight32(p));
        this.rootPrimaryIndexes.insertElementAt(index, ~rootIndex);
        return index;
    }
    
    private int findOrInsertWeakNode(int index, final int weight16, final int level) {
        assert 0 <= index && index < this.nodes.size();
        assert 1 <= level && level <= 2;
        if (weight16 == 1280) {
            return this.findCommonNode(index, level);
        }
        long node = this.nodes.elementAti(index);
        assert strengthFromNode(node) < level;
        if (weight16 != 0 && weight16 < 1280) {
            final int hasThisLevelBefore = (level == 1) ? 64 : 32;
            if ((node & (long)hasThisLevelBefore) == 0x0L) {
                long commonNode = nodeFromWeight16(1280) | nodeFromStrength(level);
                if (level == 1) {
                    commonNode |= (node & 0x20L);
                    node &= 0xFFFFFFFFFFFFFFDFL;
                }
                this.nodes.setElementAt(node | (long)hasThisLevelBefore, index);
                final int nextIndex = nextIndexFromNode(node);
                node = (nodeFromWeight16(weight16) | nodeFromStrength(level));
                index = this.insertNodeBetween(index, nextIndex, node);
                this.insertNodeBetween(index, nextIndex, commonNode);
                return index;
            }
        }
        int nextIndex2;
        while ((nextIndex2 = nextIndexFromNode(node)) != 0) {
            node = this.nodes.elementAti(nextIndex2);
            final int nextStrength = strengthFromNode(node);
            if (nextStrength <= level) {
                if (nextStrength < level) {
                    break;
                }
                if (!isTailoredNode(node)) {
                    final int nextWeight16 = weight16FromNode(node);
                    if (nextWeight16 == weight16) {
                        return nextIndex2;
                    }
                    if (nextWeight16 > weight16) {
                        break;
                    }
                }
            }
            index = nextIndex2;
        }
        node = (nodeFromWeight16(weight16) | nodeFromStrength(level));
        return this.insertNodeBetween(index, nextIndex2, node);
    }
    
    private int insertTailoredNodeAfter(int index, final int strength) {
        assert 0 <= index && index < this.nodes.size();
        if (strength >= 1) {
            index = this.findCommonNode(index, 1);
            if (strength >= 2) {
                index = this.findCommonNode(index, 2);
            }
        }
        long node = this.nodes.elementAti(index);
        int nextIndex;
        while ((nextIndex = nextIndexFromNode(node)) != 0) {
            node = this.nodes.elementAti(nextIndex);
            if (strengthFromNode(node) <= strength) {
                break;
            }
            index = nextIndex;
        }
        node = (0x8L | nodeFromStrength(strength));
        return this.insertNodeBetween(index, nextIndex, node);
    }
    
    private int insertNodeBetween(final int index, final int nextIndex, long node) {
        assert previousIndexFromNode(node) == 0;
        assert nextIndexFromNode(node) == 0;
        assert nextIndexFromNode(this.nodes.elementAti(index)) == nextIndex;
        final int newIndex = this.nodes.size();
        node |= (nodeFromPreviousIndex(index) | nodeFromNextIndex(nextIndex));
        this.nodes.addElement(node);
        node = this.nodes.elementAti(index);
        this.nodes.setElementAt(changeNodeNextIndex(node, newIndex), index);
        if (nextIndex != 0) {
            node = this.nodes.elementAti(nextIndex);
            this.nodes.setElementAt(changeNodePreviousIndex(node, newIndex), nextIndex);
        }
        return newIndex;
    }
    
    private int findCommonNode(int index, final int strength) {
        assert 1 <= strength && strength <= 2;
        long node = this.nodes.elementAti(index);
        if (strengthFromNode(node) >= strength) {
            return index;
        }
        Label_0067: {
            if (strength == 1) {
                if (nodeHasBefore2(node)) {
                    break Label_0067;
                }
            }
            else if (nodeHasBefore3(node)) {
                break Label_0067;
            }
            return index;
        }
        index = nextIndexFromNode(node);
        node = this.nodes.elementAti(index);
        assert !isTailoredNode(node) && strengthFromNode(node) == strength && weight16FromNode(node) < 1280;
        do {
            index = nextIndexFromNode(node);
            node = this.nodes.elementAti(index);
            assert strengthFromNode(node) >= strength;
        } while (isTailoredNode(node) || strengthFromNode(node) > strength || weight16FromNode(node) < 1280);
        assert weight16FromNode(node) == 1280;
        return index;
    }
    
    private void setCaseBits(final CharSequence nfdString) {
        int numTailoredPrimaries = 0;
        for (int i = 0; i < this.cesLength; ++i) {
            if (ceStrength(this.ces[i]) == 0) {
                ++numTailoredPrimaries;
            }
        }
        assert numTailoredPrimaries <= 31;
        long cases = 0L;
        if (numTailoredPrimaries > 0) {
            final CharSequence s = nfdString;
            final UTF16CollationIterator baseCEs = new UTF16CollationIterator(this.baseData, false, s, 0);
            final int baseCEsLength = baseCEs.fetchCEs() - 1;
            assert baseCEsLength >= 0 && baseCEs.getCE(baseCEsLength) == 4311744768L;
            int lastCase = 0;
            int numBasePrimaries = 0;
            for (int j = 0; j < baseCEsLength; ++j) {
                final long ce = baseCEs.getCE(j);
                if (ce >>> 32 != 0L) {
                    ++numBasePrimaries;
                    final int c = (int)ce >> 14 & 0x3;
                    assert c == 2;
                    if (numBasePrimaries < numTailoredPrimaries) {
                        cases |= (long)c << (numBasePrimaries - 1) * 2;
                    }
                    else if (numBasePrimaries == numTailoredPrimaries) {
                        lastCase = c;
                    }
                    else if (c != lastCase) {
                        lastCase = 1;
                        break;
                    }
                }
            }
            if (numBasePrimaries >= numTailoredPrimaries) {
                cases |= (long)lastCase << (numTailoredPrimaries - 1) * 2;
            }
        }
        for (int k = 0; k < this.cesLength; ++k) {
            long ce2 = this.ces[k] & 0xFFFFFFFFFFFF3FFFL;
            final int strength = ceStrength(ce2);
            if (strength == 0) {
                ce2 |= (cases & 0x3L) << 14;
                cases >>>= 2;
            }
            else if (strength == 2) {
                ce2 |= 0x8000L;
            }
            this.ces[k] = ce2;
        }
    }
    
    @Override
    void suppressContractions(final UnicodeSet set) {
        this.dataBuilder.suppressContractions(set);
    }
    
    @Override
    void optimize(final UnicodeSet set) {
        this.optimizeSet.addAll(set);
    }
    
    private int addWithClosure(final CharSequence nfdPrefix, final CharSequence nfdString, final long[] newCEs, final int newCEsLength, int ce32) {
        ce32 = this.addIfDifferent(nfdPrefix, nfdString, newCEs, newCEsLength, ce32);
        ce32 = this.addOnlyClosure(nfdPrefix, nfdString, newCEs, newCEsLength, ce32);
        this.addTailComposites(nfdPrefix, nfdString);
        return ce32;
    }
    
    private int addOnlyClosure(final CharSequence nfdPrefix, final CharSequence nfdString, final long[] newCEs, final int newCEsLength, int ce32) {
        if (nfdPrefix.length() == 0) {
            final CanonicalIterator stringIter = new CanonicalIterator(nfdString.toString());
            final String prefix = "";
            while (true) {
                final String str = stringIter.next();
                if (str == null) {
                    break;
                }
                if (this.ignoreString(str)) {
                    continue;
                }
                if (str.contentEquals(nfdString)) {
                    continue;
                }
                ce32 = this.addIfDifferent(prefix, str, newCEs, newCEsLength, ce32);
            }
        }
        else {
            final CanonicalIterator prefixIter = new CanonicalIterator(nfdPrefix.toString());
            final CanonicalIterator stringIter2 = new CanonicalIterator(nfdString.toString());
            while (true) {
                final String prefix2 = prefixIter.next();
                if (prefix2 == null) {
                    break;
                }
                if (this.ignorePrefix(prefix2)) {
                    continue;
                }
                final boolean samePrefix = prefix2.contentEquals(nfdPrefix);
                while (true) {
                    final String str2 = stringIter2.next();
                    if (str2 == null) {
                        break;
                    }
                    if (this.ignoreString(str2)) {
                        continue;
                    }
                    if (samePrefix && str2.contentEquals(nfdString)) {
                        continue;
                    }
                    ce32 = this.addIfDifferent(prefix2, str2, newCEs, newCEsLength, ce32);
                }
                stringIter2.reset();
            }
        }
        return ce32;
    }
    
    private void addTailComposites(final CharSequence nfdPrefix, final CharSequence nfdString) {
        int lastStarter;
        for (int indexAfterLastStarter = nfdString.length(); indexAfterLastStarter != 0; indexAfterLastStarter -= Character.charCount(lastStarter)) {
            lastStarter = Character.codePointBefore(nfdString, indexAfterLastStarter);
            if (this.nfd.getCombiningClass(lastStarter) == 0) {
                if (Normalizer2Impl.Hangul.isJamoL(lastStarter)) {
                    return;
                }
                final UnicodeSet composites = new UnicodeSet();
                if (!this.nfcImpl.getCanonStartSet(lastStarter, composites)) {
                    return;
                }
                final StringBuilder newNFDString = new StringBuilder();
                final StringBuilder newString = new StringBuilder();
                final long[] newCEs = new long[31];
                final UnicodeSetIterator iter = new UnicodeSetIterator(composites);
                while (iter.next()) {
                    assert iter.codepoint != UnicodeSetIterator.IS_STRING;
                    final int composite = iter.codepoint;
                    final String decomp = this.nfd.getDecomposition(composite);
                    if (!this.mergeCompositeIntoString(nfdString, indexAfterLastStarter, composite, decomp, newNFDString, newString)) {
                        continue;
                    }
                    final int newCEsLength = this.dataBuilder.getCEs(nfdPrefix, newNFDString, newCEs, 0);
                    if (newCEsLength > 31) {
                        continue;
                    }
                    final int ce32 = this.addIfDifferent(nfdPrefix, newString, newCEs, newCEsLength, -1);
                    if (ce32 == -1) {
                        continue;
                    }
                    this.addOnlyClosure(nfdPrefix, newNFDString, newCEs, newCEsLength, ce32);
                }
            }
            else {}
        }
    }
    
    private boolean mergeCompositeIntoString(final CharSequence nfdString, final int indexAfterLastStarter, final int composite, final CharSequence decomp, final StringBuilder newNFDString, final StringBuilder newString) {
        assert Character.codePointBefore(nfdString, indexAfterLastStarter) == Character.codePointAt(decomp, 0);
        final int lastStarterLength = Character.offsetByCodePoints(decomp, 0, 1);
        if (lastStarterLength == decomp.length()) {
            return false;
        }
        if (this.equalSubSequences(nfdString, indexAfterLastStarter, decomp, lastStarterLength)) {
            return false;
        }
        newNFDString.setLength(0);
        newNFDString.append(nfdString, 0, indexAfterLastStarter);
        newString.setLength(0);
        newString.append(nfdString, 0, indexAfterLastStarter - lastStarterLength).appendCodePoint(composite);
        int sourceIndex = indexAfterLastStarter;
        int decompIndex = lastStarterLength;
        int sourceChar = -1;
        int sourceCC = 0;
        int decompCC = 0;
        while (true) {
            if (sourceChar < 0) {
                if (sourceIndex >= nfdString.length()) {
                    break;
                }
                sourceChar = Character.codePointAt(nfdString, sourceIndex);
                sourceCC = this.nfd.getCombiningClass(sourceChar);
                assert sourceCC != 0;
            }
            if (decompIndex >= decomp.length()) {
                break;
            }
            final int decompChar = Character.codePointAt(decomp, decompIndex);
            decompCC = this.nfd.getCombiningClass(decompChar);
            if (decompCC == 0) {
                return false;
            }
            if (sourceCC < decompCC) {
                return false;
            }
            if (decompCC < sourceCC) {
                newNFDString.appendCodePoint(decompChar);
                decompIndex += Character.charCount(decompChar);
            }
            else {
                if (decompChar != sourceChar) {
                    return false;
                }
                newNFDString.appendCodePoint(decompChar);
                decompIndex += Character.charCount(decompChar);
                sourceIndex += Character.charCount(decompChar);
                sourceChar = -1;
            }
        }
        if (sourceChar >= 0) {
            if (sourceCC < decompCC) {
                return false;
            }
            newNFDString.append(nfdString, sourceIndex, nfdString.length());
            newString.append(nfdString, sourceIndex, nfdString.length());
        }
        else if (decompIndex < decomp.length()) {
            newNFDString.append(decomp, decompIndex, decomp.length());
        }
        assert this.nfd.isNormalized(newNFDString);
        assert this.fcd.isNormalized(newString);
        assert this.nfd.normalize(newString).equals(newNFDString.toString());
        return true;
    }
    
    private boolean equalSubSequences(final CharSequence left, int leftStart, final CharSequence right, int rightStart) {
        final int leftLength = left.length();
        if (leftLength - leftStart != right.length() - rightStart) {
            return false;
        }
        while (leftStart < leftLength) {
            if (left.charAt(leftStart++) != right.charAt(rightStart++)) {
                return false;
            }
        }
        return true;
    }
    
    private boolean ignorePrefix(final CharSequence s) {
        return !this.isFCD(s);
    }
    
    private boolean ignoreString(final CharSequence s) {
        return !this.isFCD(s) || Normalizer2Impl.Hangul.isHangul(s.charAt(0));
    }
    
    private boolean isFCD(final CharSequence s) {
        return this.fcd.isNormalized(s);
    }
    
    private void closeOverComposites() {
        final String prefix = "";
        final UnicodeSetIterator iter = new UnicodeSetIterator(CollationBuilder.COMPOSITES);
        while (iter.next()) {
            assert iter.codepoint != UnicodeSetIterator.IS_STRING;
            final String nfdString = this.nfd.getDecomposition(iter.codepoint);
            this.cesLength = this.dataBuilder.getCEs(nfdString, this.ces, 0);
            if (this.cesLength > 31) {
                continue;
            }
            final String composite = iter.getString();
            this.addIfDifferent(prefix, composite, this.ces, this.cesLength, -1);
        }
    }
    
    private int addIfDifferent(final CharSequence prefix, final CharSequence str, final long[] newCEs, final int newCEsLength, int ce32) {
        final long[] oldCEs = new long[31];
        final int oldCEsLength = this.dataBuilder.getCEs(prefix, str, oldCEs, 0);
        if (!sameCEs(newCEs, newCEsLength, oldCEs, oldCEsLength)) {
            if (ce32 == -1) {
                ce32 = this.dataBuilder.encodeCEs(newCEs, newCEsLength);
            }
            this.dataBuilder.addCE32(prefix, str, ce32);
        }
        return ce32;
    }
    
    private static boolean sameCEs(final long[] ces1, final int ces1Length, final long[] ces2, final int ces2Length) {
        if (ces1Length != ces2Length) {
            return false;
        }
        assert ces1Length <= 31;
        for (int i = 0; i < ces1Length; ++i) {
            if (ces1[i] != ces2[i]) {
                return false;
            }
        }
        return true;
    }
    
    private static final int alignWeightRight(int w) {
        if (w != 0) {
            while ((w & 0xFF) == 0x0) {
                w >>>= 8;
            }
        }
        return w;
    }
    
    private void makeTailoredCEs() {
        final CollationWeights primaries = new CollationWeights();
        final CollationWeights secondaries = new CollationWeights();
        final CollationWeights tertiaries = new CollationWeights();
        final long[] nodesArray = this.nodes.getBuffer();
        for (int rpi = 0; rpi < this.rootPrimaryIndexes.size(); ++rpi) {
            int i = this.rootPrimaryIndexes.elementAti(rpi);
            long node = nodesArray[i];
            long p = weight32FromNode(node);
            int t;
            int s = t = ((p == 0L) ? 0 : 1280);
            int q = 0;
            boolean pIsTailored = false;
            boolean sIsTailored = false;
            boolean tIsTailored = false;
            final int pIndex = (p == 0L) ? 0 : this.rootElements.findPrimary(p);
            int nextIndex = nextIndexFromNode(node);
            while (nextIndex != 0) {
                i = nextIndex;
                node = nodesArray[i];
                nextIndex = nextIndexFromNode(node);
                final int strength = strengthFromNode(node);
                if (strength == 3) {
                    assert isTailoredNode(node);
                    if (q == 3) {
                        throw new UnsupportedOperationException("quaternary tailoring gap too small");
                    }
                    ++q;
                }
                else {
                    if (strength == 2) {
                        if (isTailoredNode(node)) {
                            if (!tIsTailored) {
                                final int tCount = countTailoredNodes(nodesArray, nextIndex, 2) + 1;
                                int tLimit;
                                if (t == 0) {
                                    t = this.rootElements.getTertiaryBoundary() - 256;
                                    tLimit = ((int)this.rootElements.getFirstTertiaryCE() & 0x3F3F);
                                }
                                else if (!pIsTailored && !sIsTailored) {
                                    tLimit = this.rootElements.getTertiaryAfter(pIndex, s, t);
                                }
                                else if (t == 256) {
                                    tLimit = 1280;
                                }
                                else {
                                    assert t == 1280;
                                    tLimit = this.rootElements.getTertiaryBoundary();
                                }
                                assert (tLimit & 0xFFFFC0C0) == 0x0;
                                tertiaries.initForTertiary();
                                if (!tertiaries.allocWeights(t, tLimit, tCount)) {
                                    throw new UnsupportedOperationException("tertiary tailoring gap too small");
                                }
                                tIsTailored = true;
                            }
                            t = (int)tertiaries.nextWeight();
                            assert t != -1;
                        }
                        else {
                            t = weight16FromNode(node);
                            tIsTailored = false;
                        }
                    }
                    else {
                        if (strength == 1) {
                            if (isTailoredNode(node)) {
                                if (!sIsTailored) {
                                    final int sCount = countTailoredNodes(nodesArray, nextIndex, 1) + 1;
                                    int sLimit;
                                    if (s == 0) {
                                        s = this.rootElements.getSecondaryBoundary() - 256;
                                        sLimit = (int)(this.rootElements.getFirstSecondaryCE() >> 16);
                                    }
                                    else if (!pIsTailored) {
                                        sLimit = this.rootElements.getSecondaryAfter(pIndex, s);
                                    }
                                    else if (s == 256) {
                                        sLimit = 1280;
                                    }
                                    else {
                                        assert s == 1280;
                                        sLimit = this.rootElements.getSecondaryBoundary();
                                    }
                                    if (s == 1280) {
                                        s = this.rootElements.getLastCommonSecondary();
                                    }
                                    secondaries.initForSecondary();
                                    if (!secondaries.allocWeights(s, sLimit, sCount)) {
                                        throw new UnsupportedOperationException("secondary tailoring gap too small");
                                    }
                                    sIsTailored = true;
                                }
                                s = (int)secondaries.nextWeight();
                                assert s != -1;
                            }
                            else {
                                s = weight16FromNode(node);
                                sIsTailored = false;
                            }
                        }
                        else {
                            assert isTailoredNode(node);
                            if (!pIsTailored) {
                                final int pCount = countTailoredNodes(nodesArray, nextIndex, 0) + 1;
                                final boolean isCompressible = this.baseData.isCompressiblePrimary(p);
                                final long pLimit = this.rootElements.getPrimaryAfter(p, pIndex, isCompressible);
                                primaries.initForPrimary(isCompressible);
                                if (!primaries.allocWeights(p, pLimit, pCount)) {
                                    throw new UnsupportedOperationException("primary tailoring gap too small");
                                }
                                pIsTailored = true;
                            }
                            p = primaries.nextWeight();
                            assert p != 4294967295L;
                            s = 1280;
                            sIsTailored = false;
                        }
                        t = ((s == 0) ? 0 : 1280);
                        tIsTailored = false;
                    }
                    q = 0;
                }
                if (isTailoredNode(node)) {
                    nodesArray[i] = Collation.makeCE(p, s, t, q);
                }
            }
        }
    }
    
    private static int countTailoredNodes(final long[] nodesArray, int i, final int strength) {
        int count = 0;
        while (i != 0) {
            final long node = nodesArray[i];
            if (strengthFromNode(node) >= strength) {
                if (strengthFromNode(node) == strength) {
                    if (!isTailoredNode(node)) {
                        break;
                    }
                    ++count;
                }
                i = nextIndexFromNode(node);
                continue;
            }
            return count;
        }
        return count;
    }
    
    private void finalizeCEs() {
        final CollationDataBuilder newBuilder = new CollationDataBuilder();
        newBuilder.initForTailoring(this.baseData);
        final CEFinalizer finalizer = new CEFinalizer(this.nodes.getBuffer());
        newBuilder.copyFrom(this.dataBuilder, finalizer);
        this.dataBuilder = newBuilder;
    }
    
    private static long tempCEFromIndexAndStrength(final int index, final int strength) {
        return 4629700417037541376L + ((long)(index & 0xFE000) << 43) + ((long)(index & 0x1FC0) << 42) + ((index & 0x3F) << 24) + (strength << 8);
    }
    
    private static int indexFromTempCE(long tempCE) {
        tempCE -= 4629700417037541376L;
        return ((int)(tempCE >> 43) & 0xFE000) | ((int)(tempCE >> 42) & 0x1FC0) | ((int)(tempCE >> 24) & 0x3F);
    }
    
    private static int strengthFromTempCE(final long tempCE) {
        return (int)tempCE >> 8 & 0x3;
    }
    
    private static boolean isTempCE(final long ce) {
        final int sec = (int)ce >>> 24;
        return 6 <= sec && sec <= 69;
    }
    
    private static int indexFromTempCE32(int tempCE32) {
        tempCE32 -= 1077937696;
        return (tempCE32 >> 11 & 0xFE000) | (tempCE32 >> 10 & 0x1FC0) | (tempCE32 >> 8 & 0x3F);
    }
    
    private static boolean isTempCE32(final int ce32) {
        return (ce32 & 0xFF) >= 2 && 6 <= (ce32 >> 8 & 0xFF) && (ce32 >> 8 & 0xFF) <= 69;
    }
    
    private static int ceStrength(final long ce) {
        return isTempCE(ce) ? strengthFromTempCE(ce) : (((ce & 0xFF00000000000000L) != 0x0L) ? 0 : ((((int)ce & 0xFF000000) != 0x0) ? 1 : ((ce != 0L) ? 2 : 15)));
    }
    
    private static long nodeFromWeight32(final long weight32) {
        return weight32 << 32;
    }
    
    private static long nodeFromWeight16(final int weight16) {
        return (long)weight16 << 48;
    }
    
    private static long nodeFromPreviousIndex(final int previous) {
        return (long)previous << 28;
    }
    
    private static long nodeFromNextIndex(final int next) {
        return next << 8;
    }
    
    private static long nodeFromStrength(final int strength) {
        return strength;
    }
    
    private static long weight32FromNode(final long node) {
        return node >>> 32;
    }
    
    private static int weight16FromNode(final long node) {
        return (int)(node >> 48) & 0xFFFF;
    }
    
    private static int previousIndexFromNode(final long node) {
        return (int)(node >> 28) & 0xFFFFF;
    }
    
    private static int nextIndexFromNode(final long node) {
        return (int)node >> 8 & 0xFFFFF;
    }
    
    private static int strengthFromNode(final long node) {
        return (int)node & 0x3;
    }
    
    private static boolean nodeHasBefore2(final long node) {
        return (node & 0x40L) != 0x0L;
    }
    
    private static boolean nodeHasBefore3(final long node) {
        return (node & 0x20L) != 0x0L;
    }
    
    private static boolean nodeHasAnyBefore(final long node) {
        return (node & 0x60L) != 0x0L;
    }
    
    private static boolean isTailoredNode(final long node) {
        return (node & 0x8L) != 0x0L;
    }
    
    private static long changeNodePreviousIndex(final long node, final int previous) {
        return (node & 0xFFFF00000FFFFFFFL) | nodeFromPreviousIndex(previous);
    }
    
    private static long changeNodeNextIndex(final long node, final int next) {
        return (node & 0xFFFFFFFFF00000FFL) | nodeFromNextIndex(next);
    }
    
    static {
        (COMPOSITES = new UnicodeSet("[:NFD_QC=N:]")).remove(44032, 55203);
    }
    
    private static final class BundleImporter implements CollationRuleParser.Importer
    {
        BundleImporter() {
        }
        
        @Override
        public String getRules(final String localeID, final String collationType) {
            return CollationLoader.loadRules(new ULocale(localeID), collationType);
        }
    }
    
    private static final class CEFinalizer implements CollationDataBuilder.CEModifier
    {
        private long[] finalCEs;
        
        CEFinalizer(final long[] ces) {
            this.finalCEs = ces;
        }
        
        @Override
        public long modifyCE32(final int ce32) {
            assert !Collation.isSpecialCE32(ce32);
            if (isTempCE32(ce32)) {
                return this.finalCEs[indexFromTempCE32(ce32)] | (long)((ce32 & 0xC0) << 8);
            }
            return 4311744768L;
        }
        
        @Override
        public long modifyCE(final long ce) {
            if (isTempCE(ce)) {
                return this.finalCEs[indexFromTempCE(ce)] | (ce & 0xC000L);
            }
            return 4311744768L;
        }
    }
}
