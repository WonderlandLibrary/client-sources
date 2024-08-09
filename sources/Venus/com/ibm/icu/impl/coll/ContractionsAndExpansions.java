/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl.coll;

import com.ibm.icu.impl.Trie2;
import com.ibm.icu.impl.coll.Collation;
import com.ibm.icu.impl.coll.CollationData;
import com.ibm.icu.impl.coll.UTF16CollationIterator;
import com.ibm.icu.text.UnicodeSet;
import com.ibm.icu.util.CharsTrie;

public final class ContractionsAndExpansions {
    private CollationData data;
    private UnicodeSet contractions;
    private UnicodeSet expansions;
    private CESink sink;
    private boolean addPrefixes;
    private int checkTailored = 0;
    private UnicodeSet tailored = new UnicodeSet();
    private UnicodeSet ranges;
    private StringBuilder unreversedPrefix = new StringBuilder();
    private String suffix;
    private long[] ces = new long[31];
    static final boolean $assertionsDisabled = !ContractionsAndExpansions.class.desiredAssertionStatus();

    public ContractionsAndExpansions(UnicodeSet unicodeSet, UnicodeSet unicodeSet2, CESink cESink, boolean bl) {
        this.contractions = unicodeSet;
        this.expansions = unicodeSet2;
        this.sink = cESink;
        this.addPrefixes = bl;
    }

    public void forData(CollationData collationData) {
        if (collationData.base != null) {
            this.checkTailored = -1;
        }
        this.data = collationData;
        for (Trie2.Range range : this.data.trie) {
            if (range.leadSurrogate) break;
            this.enumCnERange(range.startCodePoint, range.endCodePoint, range.value, this);
        }
        if (collationData.base == null) {
            return;
        }
        this.tailored.freeze();
        this.checkTailored = 1;
        this.data = collationData.base;
        for (Trie2.Range range : this.data.trie) {
            if (range.leadSurrogate) break;
            this.enumCnERange(range.startCodePoint, range.endCodePoint, range.value, this);
        }
    }

    private void enumCnERange(int n, int n2, int n3, ContractionsAndExpansions contractionsAndExpansions) {
        if (contractionsAndExpansions.checkTailored != 0) {
            if (contractionsAndExpansions.checkTailored < 0) {
                if (n3 == 192) {
                    return;
                }
                contractionsAndExpansions.tailored.add(n, n2);
            } else if (n == n2) {
                if (contractionsAndExpansions.tailored.contains(n)) {
                    return;
                }
            } else if (contractionsAndExpansions.tailored.containsSome(n, n2)) {
                if (contractionsAndExpansions.ranges == null) {
                    contractionsAndExpansions.ranges = new UnicodeSet();
                }
                contractionsAndExpansions.ranges.set(n, n2).removeAll(contractionsAndExpansions.tailored);
                int n4 = contractionsAndExpansions.ranges.getRangeCount();
                for (int i = 0; i < n4; ++i) {
                    contractionsAndExpansions.handleCE32(contractionsAndExpansions.ranges.getRangeStart(i), contractionsAndExpansions.ranges.getRangeEnd(i), n3);
                }
            }
        }
        contractionsAndExpansions.handleCE32(n, n2, n3);
    }

    public void forCodePoint(CollationData collationData, int n) {
        int n2 = collationData.getCE32(n);
        if (n2 == 192) {
            collationData = collationData.base;
            n2 = collationData.getCE32(n);
        }
        this.data = collationData;
        this.handleCE32(n, n, n2);
    }

    private void handleCE32(int n, int n2, int n3) {
        while (true) {
            if ((n3 & 0xFF) < 192) {
                if (this.sink != null) {
                    this.sink.handleCE(Collation.ceFromSimpleCE32(n3));
                }
                return;
            }
            switch (Collation.tagFromCE32(n3)) {
                case 0: {
                    return;
                }
                case 3: 
                case 7: 
                case 13: {
                    throw new AssertionError((Object)String.format("Unexpected CE32 tag type %d for ce32=0x%08x", Collation.tagFromCE32(n3), n3));
                }
                case 1: {
                    if (this.sink != null) {
                        this.sink.handleCE(Collation.ceFromLongPrimaryCE32(n3));
                    }
                    return;
                }
                case 2: {
                    if (this.sink != null) {
                        this.sink.handleCE(Collation.ceFromLongSecondaryCE32(n3));
                    }
                    return;
                }
                case 4: {
                    if (this.sink != null) {
                        this.ces[0] = Collation.latinCE0FromCE32(n3);
                        this.ces[1] = Collation.latinCE1FromCE32(n3);
                        this.sink.handleExpansion(this.ces, 0, 2);
                    }
                    if (this.unreversedPrefix.length() == 0) {
                        this.addExpansions(n, n2);
                    }
                    return;
                }
                case 5: {
                    if (this.sink != null) {
                        int n4 = Collation.indexFromCE32(n3);
                        int n5 = Collation.lengthFromCE32(n3);
                        for (int i = 0; i < n5; ++i) {
                            this.ces[i] = Collation.ceFromCE32(this.data.ce32s[n4 + i]);
                        }
                        this.sink.handleExpansion(this.ces, 0, n5);
                    }
                    if (this.unreversedPrefix.length() == 0) {
                        this.addExpansions(n, n2);
                    }
                    return;
                }
                case 6: {
                    if (this.sink != null) {
                        int n6 = Collation.indexFromCE32(n3);
                        int n7 = Collation.lengthFromCE32(n3);
                        this.sink.handleExpansion(this.data.ces, n6, n7);
                    }
                    if (this.unreversedPrefix.length() == 0) {
                        this.addExpansions(n, n2);
                    }
                    return;
                }
                case 8: {
                    this.handlePrefixes(n, n2, n3);
                    return;
                }
                case 9: {
                    this.handleContractions(n, n2, n3);
                    return;
                }
                case 10: {
                    n3 = this.data.ce32s[Collation.indexFromCE32(n3)];
                    break;
                }
                case 11: {
                    if (!($assertionsDisabled || n == 0 && n2 == 0)) {
                        throw new AssertionError();
                    }
                    n3 = this.data.ce32s[0];
                    break;
                }
                case 12: {
                    if (this.sink != null) {
                        UTF16CollationIterator uTF16CollationIterator = new UTF16CollationIterator(this.data);
                        StringBuilder stringBuilder = new StringBuilder(1);
                        for (int i = n; i <= n2; ++i) {
                            stringBuilder.setLength(0);
                            stringBuilder.appendCodePoint(i);
                            uTF16CollationIterator.setText(false, stringBuilder, 1);
                            int n8 = uTF16CollationIterator.fetchCEs();
                            if (!($assertionsDisabled || n8 >= 2 && uTF16CollationIterator.getCE(n8 - 1) == 0x101000100L)) {
                                throw new AssertionError();
                            }
                            this.sink.handleExpansion(uTF16CollationIterator.getCEs(), 0, n8 - 1);
                        }
                    }
                    if (this.unreversedPrefix.length() == 0) {
                        this.addExpansions(n, n2);
                    }
                    return;
                }
                case 14: {
                    return;
                }
                case 15: {
                    return;
                }
            }
        }
    }

    private void handlePrefixes(int n, int n2, int n3) {
        int n4 = Collation.indexFromCE32(n3);
        n3 = this.data.getCE32FromContexts(n4);
        this.handleCE32(n, n2, n3);
        if (!this.addPrefixes) {
            return;
        }
        for (CharsTrie.Entry entry : new CharsTrie(this.data.contexts, n4 + 2)) {
            this.setPrefix(entry.chars);
            this.addStrings(n, n2, this.contractions);
            this.addStrings(n, n2, this.expansions);
            this.handleCE32(n, n2, entry.value);
        }
        this.resetPrefix();
    }

    void handleContractions(int n, int n2, int n3) {
        int n4 = Collation.indexFromCE32(n3);
        if ((n3 & 0x100) != 0) {
            if (!$assertionsDisabled && this.unreversedPrefix.length() == 0) {
                throw new AssertionError();
            }
        } else {
            n3 = this.data.getCE32FromContexts(n4);
            if (!$assertionsDisabled && Collation.isContractionCE32(n3)) {
                throw new AssertionError();
            }
            this.handleCE32(n, n2, n3);
        }
        for (CharsTrie.Entry entry : new CharsTrie(this.data.contexts, n4 + 2)) {
            this.suffix = entry.chars.toString();
            this.addStrings(n, n2, this.contractions);
            if (this.unreversedPrefix.length() != 0) {
                this.addStrings(n, n2, this.expansions);
            }
            this.handleCE32(n, n2, entry.value);
        }
        this.suffix = null;
    }

    void addExpansions(int n, int n2) {
        if (this.unreversedPrefix.length() == 0 && this.suffix == null) {
            if (this.expansions != null) {
                this.expansions.add(n, n2);
            }
        } else {
            this.addStrings(n, n2, this.expansions);
        }
    }

    void addStrings(int n, int n2, UnicodeSet unicodeSet) {
        if (unicodeSet == null) {
            return;
        }
        StringBuilder stringBuilder = new StringBuilder(this.unreversedPrefix);
        do {
            stringBuilder.appendCodePoint(n);
            if (this.suffix != null) {
                stringBuilder.append(this.suffix);
            }
            unicodeSet.add(stringBuilder);
            stringBuilder.setLength(this.unreversedPrefix.length());
        } while (++n <= n2);
    }

    private void setPrefix(CharSequence charSequence) {
        this.unreversedPrefix.setLength(0);
        this.unreversedPrefix.append(charSequence).reverse();
    }

    private void resetPrefix() {
        this.unreversedPrefix.setLength(0);
    }

    public static interface CESink {
        public void handleCE(long var1);

        public void handleExpansion(long[] var1, int var2, int var3);
    }
}

