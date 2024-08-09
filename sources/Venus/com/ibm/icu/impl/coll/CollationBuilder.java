/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl.coll;

import com.ibm.icu.impl.Norm2AllModes;
import com.ibm.icu.impl.Normalizer2Impl;
import com.ibm.icu.impl.coll.Collation;
import com.ibm.icu.impl.coll.CollationData;
import com.ibm.icu.impl.coll.CollationDataBuilder;
import com.ibm.icu.impl.coll.CollationFastLatin;
import com.ibm.icu.impl.coll.CollationLoader;
import com.ibm.icu.impl.coll.CollationRootElements;
import com.ibm.icu.impl.coll.CollationRuleParser;
import com.ibm.icu.impl.coll.CollationSettings;
import com.ibm.icu.impl.coll.CollationTailoring;
import com.ibm.icu.impl.coll.CollationWeights;
import com.ibm.icu.impl.coll.UTF16CollationIterator;
import com.ibm.icu.impl.coll.UVector32;
import com.ibm.icu.impl.coll.UVector64;
import com.ibm.icu.text.CanonicalIterator;
import com.ibm.icu.text.Normalizer2;
import com.ibm.icu.text.UnicodeSet;
import com.ibm.icu.text.UnicodeSetIterator;
import com.ibm.icu.util.ULocale;
import java.text.ParseException;

public final class CollationBuilder
extends CollationRuleParser.Sink {
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
    private UnicodeSet optimizeSet = new UnicodeSet();
    private long[] ces = new long[31];
    private int cesLength;
    private UVector32 rootPrimaryIndexes;
    private UVector64 nodes;
    static final boolean $assertionsDisabled;

    public CollationBuilder(CollationTailoring collationTailoring) {
        this.nfd = Normalizer2.getNFDInstance();
        this.fcd = Norm2AllModes.getFCDNormalizer2();
        this.nfcImpl = Norm2AllModes.getNFCInstance().impl;
        this.base = collationTailoring;
        this.baseData = collationTailoring.data;
        this.rootElements = new CollationRootElements(collationTailoring.data.rootElements);
        this.variableTop = 0L;
        this.dataBuilder = new CollationDataBuilder();
        this.fastLatinEnabled = true;
        this.cesLength = 0;
        this.rootPrimaryIndexes = new UVector32();
        this.nodes = new UVector64();
        this.nfcImpl.ensureCanonIterData();
        this.dataBuilder.initForTailoring(this.baseData);
    }

    public CollationTailoring parseAndBuild(String string) throws ParseException {
        if (this.baseData.rootElements == null) {
            throw new UnsupportedOperationException("missing root elements data, tailoring not supported");
        }
        CollationTailoring collationTailoring = new CollationTailoring(this.base.settings);
        CollationRuleParser collationRuleParser = new CollationRuleParser(this.baseData);
        this.variableTop = this.base.settings.readOnly().variableTop;
        collationRuleParser.setSink(this);
        collationRuleParser.setImporter(new BundleImporter());
        CollationSettings collationSettings = collationTailoring.settings.copyOnWrite();
        collationRuleParser.parse(string, collationSettings);
        if (this.dataBuilder.hasMappings()) {
            this.makeTailoredCEs();
            this.closeOverComposites();
            this.finalizeCEs();
            this.optimizeSet.add(0, 127);
            this.optimizeSet.add(192, 255);
            this.optimizeSet.remove(44032, 55203);
            this.dataBuilder.optimize(this.optimizeSet);
            collationTailoring.ensureOwnedData();
            if (this.fastLatinEnabled) {
                this.dataBuilder.enableFastLatin();
            }
            this.dataBuilder.build(collationTailoring.ownedData);
            this.dataBuilder = null;
        } else {
            collationTailoring.data = this.baseData;
        }
        collationSettings.fastLatinOptions = CollationFastLatin.getOptions(collationTailoring.data, collationSettings, collationSettings.fastLatinPrimaries);
        collationTailoring.setRules(string);
        collationTailoring.setVersion(this.base.version, 0);
        return collationTailoring;
    }

    @Override
    void addReset(int n, CharSequence charSequence) {
        if (!$assertionsDisabled && charSequence.length() == 0) {
            throw new AssertionError();
        }
        if (charSequence.charAt(0) == '\ufffe') {
            this.ces[0] = this.getSpecialResetPosition(charSequence);
            this.cesLength = 1;
            if (!$assertionsDisabled && (this.ces[0] & 0xC0C0L) != 0L) {
                throw new AssertionError();
            }
        } else {
            String string = this.nfd.normalize(charSequence);
            this.cesLength = this.dataBuilder.getCEs(string, this.ces, 0);
            if (this.cesLength > 31) {
                throw new IllegalArgumentException("reset position maps to too many collation elements (more than 31)");
            }
        }
        if (n == 15) {
            return;
        }
        if (!($assertionsDisabled || 0 <= n && n <= 2)) {
            throw new AssertionError();
        }
        int n2 = this.findOrInsertNodeForCEs(n);
        long l = this.nodes.elementAti(n2);
        while (CollationBuilder.strengthFromNode(l) > n) {
            n2 = CollationBuilder.previousIndexFromNode(l);
            l = this.nodes.elementAti(n2);
        }
        if (CollationBuilder.strengthFromNode(l) == n && CollationBuilder.isTailoredNode(l)) {
            n2 = CollationBuilder.previousIndexFromNode(l);
        } else if (n == 0) {
            int n3;
            long l2 = CollationBuilder.weight32FromNode(l);
            if (l2 == 0L) {
                throw new UnsupportedOperationException("reset primary-before ignorable not possible");
            }
            if (l2 <= this.rootElements.getFirstPrimary()) {
                throw new UnsupportedOperationException("reset primary-before first non-ignorable not supported");
            }
            if (l2 == 0xFF020200L) {
                throw new UnsupportedOperationException("reset primary-before [first trailing] not supported");
            }
            l2 = this.rootElements.getPrimaryBefore(l2, this.baseData.isCompressiblePrimary(l2));
            n2 = this.findOrInsertNodeForPrimary(l2);
            while ((n3 = CollationBuilder.nextIndexFromNode(l = this.nodes.elementAti(n2))) != 0) {
                n2 = n3;
            }
        } else {
            n2 = this.findCommonNode(n2, 1);
            if (n >= 2) {
                n2 = this.findCommonNode(n2, 2);
            }
            if (CollationBuilder.strengthFromNode(l = this.nodes.elementAti(n2)) == n) {
                int n4;
                int n5;
                int n6 = CollationBuilder.weight16FromNode(l);
                if (n6 == 0) {
                    throw new UnsupportedOperationException(n == 1 ? "reset secondary-before secondary ignorable not possible" : "reset tertiary-before completely ignorable not possible");
                }
                if (!$assertionsDisabled && n6 <= 256) {
                    throw new AssertionError();
                }
                n6 = this.getWeight16Before(n2, l, n);
                int n7 = n5 = CollationBuilder.previousIndexFromNode(l);
                while (true) {
                    int n8;
                    if ((n8 = CollationBuilder.strengthFromNode(l = this.nodes.elementAti(n7))) < n) {
                        if (!$assertionsDisabled && n6 < 1280 && n7 != n5) {
                            throw new AssertionError();
                        }
                        n4 = 1280;
                        break;
                    }
                    if (n8 == n && !CollationBuilder.isTailoredNode(l)) {
                        n4 = CollationBuilder.weight16FromNode(l);
                        break;
                    }
                    n7 = CollationBuilder.previousIndexFromNode(l);
                }
                if (n4 == n6) {
                    n2 = n5;
                } else {
                    l = CollationBuilder.nodeFromWeight16(n6) | CollationBuilder.nodeFromStrength(n);
                    n2 = this.insertNodeBetween(n5, n2, l);
                }
            } else {
                int n9 = this.getWeight16Before(n2, l, n);
                n2 = this.findOrInsertWeakNode(n2, n9, n);
            }
            n = CollationBuilder.ceStrength(this.ces[this.cesLength - 1]);
        }
        this.ces[this.cesLength - 1] = CollationBuilder.tempCEFromIndexAndStrength(n2, n);
    }

    private int getWeight16Before(int n, long l, int n2) {
        int n3;
        if (!$assertionsDisabled && CollationBuilder.strengthFromNode(l) >= n2 && CollationBuilder.isTailoredNode(l)) {
            throw new AssertionError();
        }
        int n4 = CollationBuilder.strengthFromNode(l) == 2 ? CollationBuilder.weight16FromNode(l) : 1280;
        while (CollationBuilder.strengthFromNode(l) > 1) {
            n = CollationBuilder.previousIndexFromNode(l);
            l = this.nodes.elementAti(n);
        }
        if (CollationBuilder.isTailoredNode(l)) {
            return 1;
        }
        int n5 = CollationBuilder.strengthFromNode(l) == 1 ? CollationBuilder.weight16FromNode(l) : 1280;
        while (CollationBuilder.strengthFromNode(l) > 0) {
            n = CollationBuilder.previousIndexFromNode(l);
            l = this.nodes.elementAti(n);
        }
        if (CollationBuilder.isTailoredNode(l)) {
            return 1;
        }
        long l2 = CollationBuilder.weight32FromNode(l);
        if (n2 == 1) {
            n3 = this.rootElements.getSecondaryBefore(l2, n5);
        } else {
            n3 = this.rootElements.getTertiaryBefore(l2, n5, n4);
            if (!$assertionsDisabled && (n3 & 0xFFFFC0C0) != 0) {
                throw new AssertionError();
            }
        }
        return n3;
    }

    private long getSpecialResetPosition(CharSequence charSequence) {
        long l;
        int n;
        long l2;
        if (!$assertionsDisabled && charSequence.length() != 2) {
            throw new AssertionError();
        }
        int n2 = 0;
        boolean bl = false;
        CollationRuleParser.Position position = CollationRuleParser.POSITION_VALUES[charSequence.charAt(1) - 10240];
        switch (1.$SwitchMap$com$ibm$icu$impl$coll$CollationRuleParser$Position[position.ordinal()]) {
            case 1: {
                return 0L;
            }
            case 2: {
                return 0L;
            }
            case 3: {
                int n3 = this.findOrInsertNodeForRootCE(0L, 2);
                long l3 = this.nodes.elementAti(n3);
                n3 = CollationBuilder.nextIndexFromNode(l3);
                if (n3 != 0) {
                    l3 = this.nodes.elementAti(n3);
                    if (!$assertionsDisabled && CollationBuilder.strengthFromNode(l3) > 2) {
                        throw new AssertionError();
                    }
                    if (CollationBuilder.isTailoredNode(l3) && CollationBuilder.strengthFromNode(l3) == 2) {
                        return CollationBuilder.tempCEFromIndexAndStrength(n3, 2);
                    }
                }
                return this.rootElements.getFirstTertiaryCE();
            }
            case 4: {
                l2 = this.rootElements.getLastTertiaryCE();
                n2 = 2;
                break;
            }
            case 5: {
                n = this.findOrInsertNodeForRootCE(0L, 1);
                l = this.nodes.elementAti(n);
                while ((n = CollationBuilder.nextIndexFromNode(l)) != 0 && (n2 = CollationBuilder.strengthFromNode(l = this.nodes.elementAti(n))) >= 1) {
                    if (n2 != 1) continue;
                    if (!CollationBuilder.isTailoredNode(l)) break;
                    if (CollationBuilder.nodeHasBefore3(l)) {
                        n = CollationBuilder.nextIndexFromNode(this.nodes.elementAti(CollationBuilder.nextIndexFromNode(l)));
                        if (!$assertionsDisabled && !CollationBuilder.isTailoredNode(this.nodes.elementAti(n))) {
                            throw new AssertionError();
                        }
                    }
                    return CollationBuilder.tempCEFromIndexAndStrength(n, 1);
                }
                l2 = this.rootElements.getFirstSecondaryCE();
                n2 = 1;
                break;
            }
            case 6: {
                l2 = this.rootElements.getLastSecondaryCE();
                n2 = 1;
                break;
            }
            case 7: {
                l2 = this.rootElements.getFirstPrimaryCE();
                bl = true;
                break;
            }
            case 8: {
                l2 = this.rootElements.lastCEWithPrimaryBefore(this.variableTop + 1L);
                break;
            }
            case 9: {
                l2 = this.rootElements.firstCEWithPrimaryAtLeast(this.variableTop + 1L);
                bl = true;
                break;
            }
            case 10: {
                l2 = this.rootElements.firstCEWithPrimaryAtLeast(this.baseData.getFirstPrimaryForGroup(17));
                break;
            }
            case 11: {
                l2 = this.baseData.getSingleCE(19968);
                break;
            }
            case 12: {
                throw new UnsupportedOperationException("reset to [last implicit] not supported");
            }
            case 13: {
                l2 = Collation.makeCE(0xFF020200L);
                bl = true;
                break;
            }
            case 14: {
                throw new IllegalArgumentException("LDML forbids tailoring to U+FFFF");
            }
            default: {
                if (!$assertionsDisabled) {
                    throw new AssertionError();
                }
                return 0L;
            }
        }
        n = this.findOrInsertNodeForRootCE(l2, n2);
        l = this.nodes.elementAti(n);
        if ((position.ordinal() & 1) == 0) {
            if (!CollationBuilder.nodeHasAnyBefore(l) && bl) {
                n = CollationBuilder.nextIndexFromNode(l);
                if (n != 0) {
                    l = this.nodes.elementAti(n);
                    if (!$assertionsDisabled && !CollationBuilder.isTailoredNode(l)) {
                        throw new AssertionError();
                    }
                    l2 = CollationBuilder.tempCEFromIndexAndStrength(n, n2);
                } else {
                    if (!$assertionsDisabled && n2 != 0) {
                        throw new AssertionError();
                    }
                    long l4 = l2 >>> 32;
                    int n4 = this.rootElements.findPrimary(l4);
                    boolean bl2 = this.baseData.isCompressiblePrimary(l4);
                    l4 = this.rootElements.getPrimaryAfter(l4, n4, bl2);
                    l2 = Collation.makeCE(l4);
                    n = this.findOrInsertNodeForRootCE(l2, 0);
                    l = this.nodes.elementAti(n);
                }
            }
            if (CollationBuilder.nodeHasAnyBefore(l)) {
                if (CollationBuilder.nodeHasBefore2(l)) {
                    n = CollationBuilder.nextIndexFromNode(this.nodes.elementAti(CollationBuilder.nextIndexFromNode(l)));
                    l = this.nodes.elementAti(n);
                }
                if (CollationBuilder.nodeHasBefore3(l)) {
                    n = CollationBuilder.nextIndexFromNode(this.nodes.elementAti(CollationBuilder.nextIndexFromNode(l)));
                }
                if (!$assertionsDisabled && !CollationBuilder.isTailoredNode(this.nodes.elementAti(n))) {
                    throw new AssertionError();
                }
                l2 = CollationBuilder.tempCEFromIndexAndStrength(n, n2);
            }
        } else {
            long l5;
            int n5;
            while ((n5 = CollationBuilder.nextIndexFromNode(l)) != 0 && CollationBuilder.strengthFromNode(l5 = this.nodes.elementAti(n5)) >= n2) {
                n = n5;
                l = l5;
            }
            if (CollationBuilder.isTailoredNode(l)) {
                l2 = CollationBuilder.tempCEFromIndexAndStrength(n, n2);
            }
        }
        return l2;
    }

    @Override
    void addRelation(int n, CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3) {
        int n2;
        String string = charSequence.length() == 0 ? "" : this.nfd.normalize(charSequence);
        String string2 = this.nfd.normalize(charSequence2);
        int n3 = string2.length();
        if (n3 >= 2) {
            n2 = string2.charAt(0);
            if (Normalizer2Impl.Hangul.isJamoL(n2) || Normalizer2Impl.Hangul.isJamoV(n2)) {
                throw new UnsupportedOperationException("contractions starting with conjoining Jamo L or V not supported");
            }
            n2 = string2.charAt(n3 - 1);
            if (Normalizer2Impl.Hangul.isJamoL(n2) || Normalizer2Impl.Hangul.isJamoV(n2) && Normalizer2Impl.Hangul.isJamoL(string2.charAt(n3 - 2))) {
                throw new UnsupportedOperationException("contractions ending with conjoining Jamo L or L+V not supported");
            }
        }
        if (n != 15) {
            n2 = this.findOrInsertNodeForCEs(n);
            if (!$assertionsDisabled && this.cesLength <= 0) {
                throw new AssertionError();
            }
            long l = this.ces[this.cesLength - 1];
            if (n == 0 && !CollationBuilder.isTempCE(l) && l >>> 32 == 0L) {
                throw new UnsupportedOperationException("tailoring primary after ignorables not supported");
            }
            if (n == 3 && l == 0L) {
                throw new UnsupportedOperationException("tailoring quaternary after tertiary ignorables not supported");
            }
            n2 = this.insertTailoredNodeAfter(n2, n);
            int n4 = CollationBuilder.ceStrength(l);
            if (n < n4) {
                n4 = n;
            }
            this.ces[this.cesLength - 1] = CollationBuilder.tempCEFromIndexAndStrength(n2, n4);
        }
        this.setCaseBits(string2);
        n2 = this.cesLength;
        if (charSequence3.length() != 0) {
            String string3 = this.nfd.normalize(charSequence3);
            this.cesLength = this.dataBuilder.getCEs(string3, this.ces, this.cesLength);
            if (this.cesLength > 31) {
                throw new IllegalArgumentException("extension string adds too many collation elements (more than 31 total)");
            }
        }
        int n5 = -1;
        if (!(string.contentEquals(charSequence) && string2.contentEquals(charSequence2) || this.ignorePrefix(charSequence) || this.ignoreString(charSequence2))) {
            n5 = this.addIfDifferent(charSequence, charSequence2, this.ces, this.cesLength, n5);
        }
        this.addWithClosure(string, string2, this.ces, this.cesLength, n5);
        this.cesLength = n2;
    }

    private int findOrInsertNodeForCEs(int n) {
        long l;
        if (!($assertionsDisabled || 0 <= n && n <= 3)) {
            throw new AssertionError();
        }
        while (true) {
            if (this.cesLength == 0) {
                this.ces[0] = 0L;
                l = 0L;
                this.cesLength = 1;
                break;
            }
            l = this.ces[this.cesLength - 1];
            if (CollationBuilder.ceStrength(l) <= n) break;
            --this.cesLength;
        }
        if (CollationBuilder.isTempCE(l)) {
            return CollationBuilder.indexFromTempCE(l);
        }
        if ((int)(l >>> 56) == 254) {
            throw new UnsupportedOperationException("tailoring relative to an unassigned code point not supported");
        }
        return this.findOrInsertNodeForRootCE(l, n);
    }

    private int findOrInsertNodeForRootCE(long l, int n) {
        if (!$assertionsDisabled && (int)(l >>> 56) == 254) {
            throw new AssertionError();
        }
        if (!$assertionsDisabled && (l & 0xC0L) != 0L) {
            throw new AssertionError();
        }
        int n2 = this.findOrInsertNodeForPrimary(l >>> 32);
        if (n >= 1) {
            int n3 = (int)l;
            n2 = this.findOrInsertWeakNode(n2, n3 >>> 16, 1);
            if (n >= 2) {
                n2 = this.findOrInsertWeakNode(n2, n3 & 0x3F3F, 2);
            }
        }
        return n2;
    }

    private static final int binarySearchForRootPrimaryNode(int[] nArray, int n, long[] lArray, long l) {
        if (n == 0) {
            return 1;
        }
        int n2 = 0;
        int n3 = n;
        int n4;
        long l2;
        long l3;
        while (l != (l3 = (l2 = lArray[nArray[n4 = (int)(((long)n2 + (long)n3) / 2L)]]) >>> 32)) {
            if (l < l3) {
                if (n4 == n2) {
                    return ~n2;
                }
                n3 = n4;
                continue;
            }
            if (n4 == n2) {
                return ~(n2 + 1);
            }
            n2 = n4;
        }
        return n4;
    }

    private int findOrInsertNodeForPrimary(long l) {
        int n = CollationBuilder.binarySearchForRootPrimaryNode(this.rootPrimaryIndexes.getBuffer(), this.rootPrimaryIndexes.size(), this.nodes.getBuffer(), l);
        if (n >= 0) {
            return this.rootPrimaryIndexes.elementAti(n);
        }
        int n2 = this.nodes.size();
        this.nodes.addElement(CollationBuilder.nodeFromWeight32(l));
        this.rootPrimaryIndexes.insertElementAt(n2, ~n);
        return n2;
    }

    private int findOrInsertWeakNode(int n, int n2, int n3) {
        int n4;
        if (!($assertionsDisabled || 0 <= n && n < this.nodes.size())) {
            throw new AssertionError();
        }
        if (!($assertionsDisabled || 1 <= n3 && n3 <= 2)) {
            throw new AssertionError();
        }
        if (n2 == 1280) {
            return this.findCommonNode(n, n3);
        }
        long l = this.nodes.elementAti(n);
        if (!$assertionsDisabled && CollationBuilder.strengthFromNode(l) >= n3) {
            throw new AssertionError();
        }
        if (n2 != 0 && n2 < 1280) {
            int n5 = n4 = n3 == 1 ? 64 : 32;
            if ((l & (long)n4) == 0L) {
                long l2 = CollationBuilder.nodeFromWeight16(1280) | CollationBuilder.nodeFromStrength(n3);
                if (n3 == 1) {
                    l2 |= l & 0x20L;
                    l &= 0xFFFFFFFFFFFFFFDFL;
                }
                this.nodes.setElementAt(l | (long)n4, n);
                int n6 = CollationBuilder.nextIndexFromNode(l);
                l = CollationBuilder.nodeFromWeight16(n2) | CollationBuilder.nodeFromStrength(n3);
                n = this.insertNodeBetween(n, n6, l);
                this.insertNodeBetween(n, n6, l2);
                return n;
            }
        }
        while ((n4 = CollationBuilder.nextIndexFromNode(l)) != 0) {
            l = this.nodes.elementAti(n4);
            int n7 = CollationBuilder.strengthFromNode(l);
            if (n7 <= n3) {
                if (n7 < n3) break;
                if (!CollationBuilder.isTailoredNode(l)) {
                    int n8 = CollationBuilder.weight16FromNode(l);
                    if (n8 == n2) {
                        return n4;
                    }
                    if (n8 > n2) break;
                }
            }
            n = n4;
        }
        l = CollationBuilder.nodeFromWeight16(n2) | CollationBuilder.nodeFromStrength(n3);
        return this.insertNodeBetween(n, n4, l);
    }

    private int insertTailoredNodeAfter(int n, int n2) {
        int n3;
        if (!($assertionsDisabled || 0 <= n && n < this.nodes.size())) {
            throw new AssertionError();
        }
        if (n2 >= 1) {
            n = this.findCommonNode(n, 1);
            if (n2 >= 2) {
                n = this.findCommonNode(n, 2);
            }
        }
        long l = this.nodes.elementAti(n);
        while ((n3 = CollationBuilder.nextIndexFromNode(l)) != 0 && CollationBuilder.strengthFromNode(l = this.nodes.elementAti(n3)) > n2) {
            n = n3;
        }
        l = 8L | CollationBuilder.nodeFromStrength(n2);
        return this.insertNodeBetween(n, n3, l);
    }

    private int insertNodeBetween(int n, int n2, long l) {
        if (!$assertionsDisabled && CollationBuilder.previousIndexFromNode(l) != 0) {
            throw new AssertionError();
        }
        if (!$assertionsDisabled && CollationBuilder.nextIndexFromNode(l) != 0) {
            throw new AssertionError();
        }
        if (!$assertionsDisabled && CollationBuilder.nextIndexFromNode(this.nodes.elementAti(n)) != n2) {
            throw new AssertionError();
        }
        int n3 = this.nodes.size();
        this.nodes.addElement(l |= CollationBuilder.nodeFromPreviousIndex(n) | CollationBuilder.nodeFromNextIndex(n2));
        l = this.nodes.elementAti(n);
        this.nodes.setElementAt(CollationBuilder.changeNodeNextIndex(l, n3), n);
        if (n2 != 0) {
            l = this.nodes.elementAti(n2);
            this.nodes.setElementAt(CollationBuilder.changeNodePreviousIndex(l, n3), n2);
        }
        return n3;
    }

    private int findCommonNode(int n, int n2) {
        if (!($assertionsDisabled || 1 <= n2 && n2 <= 2)) {
            throw new AssertionError();
        }
        long l = this.nodes.elementAti(n);
        if (CollationBuilder.strengthFromNode(l) >= n2) {
            return n;
        }
        if (n2 == 1 ? !CollationBuilder.nodeHasBefore2(l) : !CollationBuilder.nodeHasBefore3(l)) {
            return n;
        }
        n = CollationBuilder.nextIndexFromNode(l);
        l = this.nodes.elementAti(n);
        if (!$assertionsDisabled && (CollationBuilder.isTailoredNode(l) || CollationBuilder.strengthFromNode(l) != n2 || CollationBuilder.weight16FromNode(l) >= 1280)) {
            throw new AssertionError();
        }
        do {
            n = CollationBuilder.nextIndexFromNode(l);
            l = this.nodes.elementAti(n);
            if (!$assertionsDisabled && CollationBuilder.strengthFromNode(l) < n2) {
                throw new AssertionError();
            }
        } while (CollationBuilder.isTailoredNode(l) || CollationBuilder.strengthFromNode(l) > n2 || CollationBuilder.weight16FromNode(l) < 1280);
        if (!$assertionsDisabled && CollationBuilder.weight16FromNode(l) != 1280) {
            throw new AssertionError();
        }
        return n;
    }

    private void setCaseBits(CharSequence charSequence) {
        int n;
        int n2 = 0;
        for (int i = 0; i < this.cesLength; ++i) {
            if (CollationBuilder.ceStrength(this.ces[i]) != 0) continue;
            ++n2;
        }
        if (!$assertionsDisabled && n2 > 31) {
            throw new AssertionError();
        }
        long l = 0L;
        if (n2 > 0) {
            CharSequence charSequence2 = charSequence;
            UTF16CollationIterator uTF16CollationIterator = new UTF16CollationIterator(this.baseData, false, charSequence2, 0);
            int n3 = uTF16CollationIterator.fetchCEs() - 1;
            if (!($assertionsDisabled || n3 >= 0 && uTF16CollationIterator.getCE(n3) == 0x101000100L)) {
                throw new AssertionError();
            }
            n = 0;
            int n4 = 0;
            for (int i = 0; i < n3; ++i) {
                long l2 = uTF16CollationIterator.getCE(i);
                if (l2 >>> 32 == 0L) continue;
                ++n4;
                int n5 = (int)l2 >> 14 & 3;
                if (!$assertionsDisabled && n5 != 0 && n5 != 2) {
                    throw new AssertionError();
                }
                if (n4 < n2) {
                    l |= (long)n5 << (n4 - 1) * 2;
                    continue;
                }
                if (n4 == n2) {
                    n = n5;
                    continue;
                }
                if (n5 == n) continue;
                n = 1;
                break;
            }
            if (n4 >= n2) {
                l |= (long)n << (n2 - 1) * 2;
            }
        }
        for (int i = 0; i < this.cesLength; ++i) {
            long l3 = this.ces[i] & 0xFFFFFFFFFFFF3FFFL;
            n = CollationBuilder.ceStrength(l3);
            if (n == 0) {
                l3 |= (l & 3L) << 14;
                l >>>= 2;
            } else if (n == 2) {
                l3 |= 0x8000L;
            }
            this.ces[i] = l3;
        }
    }

    @Override
    void suppressContractions(UnicodeSet unicodeSet) {
        this.dataBuilder.suppressContractions(unicodeSet);
    }

    @Override
    void optimize(UnicodeSet unicodeSet) {
        this.optimizeSet.addAll(unicodeSet);
    }

    private int addWithClosure(CharSequence charSequence, CharSequence charSequence2, long[] lArray, int n, int n2) {
        n2 = this.addIfDifferent(charSequence, charSequence2, lArray, n, n2);
        n2 = this.addOnlyClosure(charSequence, charSequence2, lArray, n, n2);
        this.addTailComposites(charSequence, charSequence2);
        return n2;
    }

    private int addOnlyClosure(CharSequence charSequence, CharSequence charSequence2, long[] lArray, int n, int n2) {
        if (charSequence.length() == 0) {
            String string;
            CanonicalIterator canonicalIterator = new CanonicalIterator(charSequence2.toString());
            String string2 = "";
            while ((string = canonicalIterator.next()) != null) {
                if (this.ignoreString(string) || string.contentEquals(charSequence2)) continue;
                n2 = this.addIfDifferent(string2, string, lArray, n, n2);
            }
        } else {
            String string;
            CanonicalIterator canonicalIterator = new CanonicalIterator(charSequence.toString());
            CanonicalIterator canonicalIterator2 = new CanonicalIterator(charSequence2.toString());
            while ((string = canonicalIterator.next()) != null) {
                String string3;
                if (this.ignorePrefix(string)) continue;
                boolean bl = string.contentEquals(charSequence);
                while ((string3 = canonicalIterator2.next()) != null) {
                    if (this.ignoreString(string3) || bl && string3.contentEquals(charSequence2)) continue;
                    n2 = this.addIfDifferent(string, string3, lArray, n, n2);
                }
                canonicalIterator2.reset();
            }
        }
        return n2;
    }

    private void addTailComposites(CharSequence charSequence, CharSequence charSequence2) {
        int n;
        int n2 = charSequence2.length();
        while (true) {
            if (n2 == 0) {
                return;
            }
            n = Character.codePointBefore(charSequence2, n2);
            if (this.nfd.getCombiningClass(n) == 0) break;
            n2 -= Character.charCount(n);
        }
        if (Normalizer2Impl.Hangul.isJamoL(n)) {
            return;
        }
        UnicodeSet unicodeSet = new UnicodeSet();
        if (!this.nfcImpl.getCanonStartSet(n, unicodeSet)) {
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        StringBuilder stringBuilder2 = new StringBuilder();
        long[] lArray = new long[31];
        UnicodeSetIterator unicodeSetIterator = new UnicodeSetIterator(unicodeSet);
        while (unicodeSetIterator.next()) {
            int n3;
            int n4;
            if (!$assertionsDisabled && unicodeSetIterator.codepoint == UnicodeSetIterator.IS_STRING) {
                throw new AssertionError();
            }
            int n5 = unicodeSetIterator.codepoint;
            String string = this.nfd.getDecomposition(n5);
            if (!this.mergeCompositeIntoString(charSequence2, n2, n5, string, stringBuilder, stringBuilder2) || (n4 = this.dataBuilder.getCEs(charSequence, stringBuilder, lArray, 0)) > 31 || (n3 = this.addIfDifferent(charSequence, stringBuilder2, lArray, n4, -1)) == -1) continue;
            this.addOnlyClosure(charSequence, stringBuilder, lArray, n4, n3);
        }
    }

    private boolean mergeCompositeIntoString(CharSequence charSequence, int n, int n2, CharSequence charSequence2, StringBuilder stringBuilder, StringBuilder stringBuilder2) {
        if (!$assertionsDisabled && Character.codePointBefore(charSequence, n) != Character.codePointAt(charSequence2, 0)) {
            throw new AssertionError();
        }
        int n3 = Character.offsetByCodePoints(charSequence2, 0, 1);
        if (n3 == charSequence2.length()) {
            return true;
        }
        if (this.equalSubSequences(charSequence, n, charSequence2, n3)) {
            return true;
        }
        stringBuilder.setLength(0);
        stringBuilder.append(charSequence, 0, n);
        stringBuilder2.setLength(0);
        stringBuilder2.append(charSequence, 0, n - n3).appendCodePoint(n2);
        int n4 = n;
        int n5 = n3;
        int n6 = -1;
        int n7 = 0;
        int n8 = 0;
        while (true) {
            if (n6 < 0) {
                if (n4 >= charSequence.length()) break;
                n6 = Character.codePointAt(charSequence, n4);
                n7 = this.nfd.getCombiningClass(n6);
                if (!$assertionsDisabled && n7 == 0) {
                    throw new AssertionError();
                }
            }
            if (n5 >= charSequence2.length()) break;
            int n9 = Character.codePointAt(charSequence2, n5);
            n8 = this.nfd.getCombiningClass(n9);
            if (n8 == 0) {
                return true;
            }
            if (n7 < n8) {
                return true;
            }
            if (n8 < n7) {
                stringBuilder.appendCodePoint(n9);
                n5 += Character.charCount(n9);
                continue;
            }
            if (n9 != n6) {
                return true;
            }
            stringBuilder.appendCodePoint(n9);
            n5 += Character.charCount(n9);
            n4 += Character.charCount(n9);
            n6 = -1;
        }
        if (n6 >= 0) {
            if (n7 < n8) {
                return true;
            }
            stringBuilder.append(charSequence, n4, charSequence.length());
            stringBuilder2.append(charSequence, n4, charSequence.length());
        } else if (n5 < charSequence2.length()) {
            stringBuilder.append(charSequence2, n5, charSequence2.length());
        }
        if (!$assertionsDisabled && !this.nfd.isNormalized(stringBuilder)) {
            throw new AssertionError();
        }
        if (!$assertionsDisabled && !this.fcd.isNormalized(stringBuilder2)) {
            throw new AssertionError();
        }
        if (!$assertionsDisabled && !this.nfd.normalize(stringBuilder2).equals(stringBuilder.toString())) {
            throw new AssertionError();
        }
        return false;
    }

    private boolean equalSubSequences(CharSequence charSequence, int n, CharSequence charSequence2, int n2) {
        int n3 = charSequence.length();
        if (n3 - n != charSequence2.length() - n2) {
            return true;
        }
        while (n < n3) {
            if (charSequence.charAt(n++) == charSequence2.charAt(n2++)) continue;
            return true;
        }
        return false;
    }

    private boolean ignorePrefix(CharSequence charSequence) {
        return !this.isFCD(charSequence);
    }

    private boolean ignoreString(CharSequence charSequence) {
        return !this.isFCD(charSequence) || Normalizer2Impl.Hangul.isHangul(charSequence.charAt(0));
    }

    private boolean isFCD(CharSequence charSequence) {
        return this.fcd.isNormalized(charSequence);
    }

    private void closeOverComposites() {
        String string = "";
        UnicodeSetIterator unicodeSetIterator = new UnicodeSetIterator(COMPOSITES);
        while (unicodeSetIterator.next()) {
            if (!$assertionsDisabled && unicodeSetIterator.codepoint == UnicodeSetIterator.IS_STRING) {
                throw new AssertionError();
            }
            String string2 = this.nfd.getDecomposition(unicodeSetIterator.codepoint);
            this.cesLength = this.dataBuilder.getCEs(string2, this.ces, 0);
            if (this.cesLength > 31) continue;
            String string3 = unicodeSetIterator.getString();
            this.addIfDifferent(string, string3, this.ces, this.cesLength, -1);
        }
    }

    private int addIfDifferent(CharSequence charSequence, CharSequence charSequence2, long[] lArray, int n, int n2) {
        long[] lArray2 = new long[31];
        int n3 = this.dataBuilder.getCEs(charSequence, charSequence2, lArray2, 0);
        if (!CollationBuilder.sameCEs(lArray, n, lArray2, n3)) {
            if (n2 == -1) {
                n2 = this.dataBuilder.encodeCEs(lArray, n);
            }
            this.dataBuilder.addCE32(charSequence, charSequence2, n2);
        }
        return n2;
    }

    private static boolean sameCEs(long[] lArray, int n, long[] lArray2, int n2) {
        if (n != n2) {
            return true;
        }
        if (!$assertionsDisabled && n > 31) {
            throw new AssertionError();
        }
        for (int i = 0; i < n; ++i) {
            if (lArray[i] == lArray2[i]) continue;
            return true;
        }
        return false;
    }

    private static final int alignWeightRight(int n) {
        if (n != 0) {
            while ((n & 0xFF) == 0) {
                n >>>= 8;
            }
        }
        return n;
    }

    private void makeTailoredCEs() {
        CollationWeights collationWeights = new CollationWeights();
        CollationWeights collationWeights2 = new CollationWeights();
        CollationWeights collationWeights3 = new CollationWeights();
        long[] lArray = this.nodes.getBuffer();
        for (int i = 0; i < this.rootPrimaryIndexes.size(); ++i) {
            int n;
            int n2 = this.rootPrimaryIndexes.elementAti(i);
            long l = lArray[n2];
            long l2 = CollationBuilder.weight32FromNode(l);
            int n3 = n = l2 == 0L ? 0 : 1280;
            int n4 = 0;
            boolean bl = false;
            boolean bl2 = false;
            boolean bl3 = false;
            int n5 = l2 == 0L ? 0 : this.rootElements.findPrimary(l2);
            int n6 = CollationBuilder.nextIndexFromNode(l);
            while (n6 != 0) {
                n2 = n6;
                l = lArray[n2];
                n6 = CollationBuilder.nextIndexFromNode(l);
                int n7 = CollationBuilder.strengthFromNode(l);
                if (n7 == 3) {
                    if (!$assertionsDisabled && !CollationBuilder.isTailoredNode(l)) {
                        throw new AssertionError();
                    }
                    if (n4 == 3) {
                        throw new UnsupportedOperationException("quaternary tailoring gap too small");
                    }
                    ++n4;
                } else {
                    int n8;
                    int n9;
                    if (n7 == 2) {
                        if (CollationBuilder.isTailoredNode(l)) {
                            if (!bl3) {
                                n9 = CollationBuilder.countTailoredNodes(lArray, n6, 2) + 1;
                                if (n3 == 0) {
                                    n3 = this.rootElements.getTertiaryBoundary() - 256;
                                    n8 = (int)this.rootElements.getFirstTertiaryCE() & 0x3F3F;
                                } else if (!bl && !bl2) {
                                    n8 = this.rootElements.getTertiaryAfter(n5, n, n3);
                                } else if (n3 == 256) {
                                    n8 = 1280;
                                } else {
                                    if (!$assertionsDisabled && n3 != 1280) {
                                        throw new AssertionError();
                                    }
                                    n8 = this.rootElements.getTertiaryBoundary();
                                }
                                if (!$assertionsDisabled && n8 != 16384 && (n8 & 0xFFFFC0C0) != 0) {
                                    throw new AssertionError();
                                }
                                collationWeights3.initForTertiary();
                                if (!collationWeights3.allocWeights(n3, n8, n9)) {
                                    throw new UnsupportedOperationException("tertiary tailoring gap too small");
                                }
                                bl3 = true;
                            }
                            n3 = (int)collationWeights3.nextWeight();
                            if (!$assertionsDisabled && n3 == -1) {
                                throw new AssertionError();
                            }
                        } else {
                            n3 = CollationBuilder.weight16FromNode(l);
                            bl3 = false;
                        }
                    } else {
                        if (n7 == 1) {
                            if (CollationBuilder.isTailoredNode(l)) {
                                if (!bl2) {
                                    n9 = CollationBuilder.countTailoredNodes(lArray, n6, 1) + 1;
                                    if (n == 0) {
                                        n = this.rootElements.getSecondaryBoundary() - 256;
                                        n8 = (int)(this.rootElements.getFirstSecondaryCE() >> 16);
                                    } else if (!bl) {
                                        n8 = this.rootElements.getSecondaryAfter(n5, n);
                                    } else if (n == 256) {
                                        n8 = 1280;
                                    } else {
                                        if (!$assertionsDisabled && n != 1280) {
                                            throw new AssertionError();
                                        }
                                        n8 = this.rootElements.getSecondaryBoundary();
                                    }
                                    if (n == 1280) {
                                        n = this.rootElements.getLastCommonSecondary();
                                    }
                                    collationWeights2.initForSecondary();
                                    if (!collationWeights2.allocWeights(n, n8, n9)) {
                                        throw new UnsupportedOperationException("secondary tailoring gap too small");
                                    }
                                    bl2 = true;
                                }
                                n = (int)collationWeights2.nextWeight();
                                if (!$assertionsDisabled && n == -1) {
                                    throw new AssertionError();
                                }
                            } else {
                                n = CollationBuilder.weight16FromNode(l);
                                bl2 = false;
                            }
                        } else {
                            if (!$assertionsDisabled && !CollationBuilder.isTailoredNode(l)) {
                                throw new AssertionError();
                            }
                            if (!bl) {
                                n9 = CollationBuilder.countTailoredNodes(lArray, n6, 0) + 1;
                                n8 = this.baseData.isCompressiblePrimary(l2);
                                long l3 = this.rootElements.getPrimaryAfter(l2, n5, n8 != 0);
                                collationWeights.initForPrimary(n8 != 0);
                                if (!collationWeights.allocWeights(l2, l3, n9)) {
                                    throw new UnsupportedOperationException("primary tailoring gap too small");
                                }
                                bl = true;
                            }
                            l2 = collationWeights.nextWeight();
                            if (!$assertionsDisabled && l2 == 0xFFFFFFFFL) {
                                throw new AssertionError();
                            }
                            n = 1280;
                            bl2 = false;
                        }
                        n3 = n == 0 ? 0 : 1280;
                        bl3 = false;
                    }
                    n4 = 0;
                }
                if (!CollationBuilder.isTailoredNode(l)) continue;
                lArray[n2] = Collation.makeCE(l2, n, n3, n4);
            }
        }
    }

    private static int countTailoredNodes(long[] lArray, int n, int n2) {
        long l;
        int n3 = 0;
        while (n != 0 && CollationBuilder.strengthFromNode(l = lArray[n]) >= n2) {
            if (CollationBuilder.strengthFromNode(l) == n2) {
                if (!CollationBuilder.isTailoredNode(l)) break;
                ++n3;
            }
            n = CollationBuilder.nextIndexFromNode(l);
        }
        return n3;
    }

    private void finalizeCEs() {
        CollationDataBuilder collationDataBuilder = new CollationDataBuilder();
        collationDataBuilder.initForTailoring(this.baseData);
        CEFinalizer cEFinalizer = new CEFinalizer(this.nodes.getBuffer());
        collationDataBuilder.copyFrom(this.dataBuilder, cEFinalizer);
        this.dataBuilder = collationDataBuilder;
    }

    private static long tempCEFromIndexAndStrength(int n, int n2) {
        return 4629700417037541376L + ((long)(n & 0xFE000) << 43) + ((long)(n & 0x1FC0) << 42) + (long)((n & 0x3F) << 24) + (long)(n2 << 8);
    }

    private static int indexFromTempCE(long l) {
        return (int)((l -= 4629700417037541376L) >> 43) & 0xFE000 | (int)(l >> 42) & 0x1FC0 | (int)(l >> 24) & 0x3F;
    }

    private static int strengthFromTempCE(long l) {
        return (int)l >> 8 & 3;
    }

    private static boolean isTempCE(long l) {
        int n = (int)l >>> 24;
        return 6 <= n && n <= 69;
    }

    private static int indexFromTempCE32(int n) {
        return (n -= 1077937696) >> 11 & 0xFE000 | n >> 10 & 0x1FC0 | n >> 8 & 0x3F;
    }

    private static boolean isTempCE32(int n) {
        return (n & 0xFF) >= 2 && 6 <= (n >> 8 & 0xFF) && (n >> 8 & 0xFF) <= 69;
    }

    private static int ceStrength(long l) {
        return CollationBuilder.isTempCE(l) ? CollationBuilder.strengthFromTempCE(l) : ((l & 0xFF00000000000000L) != 0L ? 0 : (((int)l & 0xFF000000) != 0 ? 1 : (l != 0L ? 2 : 15)));
    }

    private static long nodeFromWeight32(long l) {
        return l << 32;
    }

    private static long nodeFromWeight16(int n) {
        return (long)n << 48;
    }

    private static long nodeFromPreviousIndex(int n) {
        return (long)n << 28;
    }

    private static long nodeFromNextIndex(int n) {
        return n << 8;
    }

    private static long nodeFromStrength(int n) {
        return n;
    }

    private static long weight32FromNode(long l) {
        return l >>> 32;
    }

    private static int weight16FromNode(long l) {
        return (int)(l >> 48) & 0xFFFF;
    }

    private static int previousIndexFromNode(long l) {
        return (int)(l >> 28) & 0xFFFFF;
    }

    private static int nextIndexFromNode(long l) {
        return (int)l >> 8 & 0xFFFFF;
    }

    private static int strengthFromNode(long l) {
        return (int)l & 3;
    }

    private static boolean nodeHasBefore2(long l) {
        return (l & 0x40L) != 0L;
    }

    private static boolean nodeHasBefore3(long l) {
        return (l & 0x20L) != 0L;
    }

    private static boolean nodeHasAnyBefore(long l) {
        return (l & 0x60L) != 0L;
    }

    private static boolean isTailoredNode(long l) {
        return (l & 8L) != 0L;
    }

    private static long changeNodePreviousIndex(long l, int n) {
        return l & 0xFFFF00000FFFFFFFL | CollationBuilder.nodeFromPreviousIndex(n);
    }

    private static long changeNodeNextIndex(long l, int n) {
        return l & 0xFFFFFFFFF00000FFL | CollationBuilder.nodeFromNextIndex(n);
    }

    static boolean access$000(int n) {
        return CollationBuilder.isTempCE32(n);
    }

    static int access$100(int n) {
        return CollationBuilder.indexFromTempCE32(n);
    }

    static boolean access$200(long l) {
        return CollationBuilder.isTempCE(l);
    }

    static int access$300(long l) {
        return CollationBuilder.indexFromTempCE(l);
    }

    static {
        $assertionsDisabled = !CollationBuilder.class.desiredAssertionStatus();
        COMPOSITES = new UnicodeSet("[:NFD_QC=N:]");
        COMPOSITES.remove(44032, 55203);
    }

    private static final class CEFinalizer
    implements CollationDataBuilder.CEModifier {
        private long[] finalCEs;
        static final boolean $assertionsDisabled = !CollationBuilder.class.desiredAssertionStatus();

        CEFinalizer(long[] lArray) {
            this.finalCEs = lArray;
        }

        @Override
        public long modifyCE32(int n) {
            if (!$assertionsDisabled && Collation.isSpecialCE32(n)) {
                throw new AssertionError();
            }
            if (CollationBuilder.access$000(n)) {
                return this.finalCEs[CollationBuilder.access$100(n)] | (long)((n & 0xC0) << 8);
            }
            return 0x101000100L;
        }

        @Override
        public long modifyCE(long l) {
            if (CollationBuilder.access$200(l)) {
                return this.finalCEs[CollationBuilder.access$300(l)] | l & 0xC000L;
            }
            return 0x101000100L;
        }
    }

    private static final class BundleImporter
    implements CollationRuleParser.Importer {
        BundleImporter() {
        }

        @Override
        public String getRules(String string, String string2) {
            return CollationLoader.loadRules(new ULocale(string), string2);
        }
    }
}

