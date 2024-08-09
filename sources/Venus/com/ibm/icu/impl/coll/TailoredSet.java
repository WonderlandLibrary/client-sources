/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl.coll;

import com.ibm.icu.impl.Normalizer2Impl;
import com.ibm.icu.impl.Trie2;
import com.ibm.icu.impl.Utility;
import com.ibm.icu.impl.coll.Collation;
import com.ibm.icu.impl.coll.CollationData;
import com.ibm.icu.text.UnicodeSet;
import com.ibm.icu.util.CharsTrie;

public final class TailoredSet {
    private CollationData data;
    private CollationData baseData;
    private UnicodeSet tailored;
    private StringBuilder unreversedPrefix = new StringBuilder();
    private String suffix;
    static final boolean $assertionsDisabled = !TailoredSet.class.desiredAssertionStatus();

    public TailoredSet(UnicodeSet unicodeSet) {
        this.tailored = unicodeSet;
    }

    public void forData(CollationData collationData) {
        this.data = collationData;
        this.baseData = collationData.base;
        if (!$assertionsDisabled && this.baseData == null) {
            throw new AssertionError();
        }
        for (Trie2.Range range : this.data.trie) {
            if (range.leadSurrogate) break;
            this.enumTailoredRange(range.startCodePoint, range.endCodePoint, range.value, this);
        }
    }

    private void enumTailoredRange(int n, int n2, int n3, TailoredSet tailoredSet) {
        if (n3 == 192) {
            return;
        }
        tailoredSet.handleCE32(n, n2, n3);
    }

    private void handleCE32(int n, int n2, int n3) {
        if (!$assertionsDisabled && n3 == 192) {
            throw new AssertionError();
        }
        if (Collation.isSpecialCE32(n3) && (n3 = this.data.getIndirectCE32(n3)) == 192) {
            return;
        }
        do {
            int n4 = this.baseData.getFinalCE32(this.baseData.getCE32(n));
            if (Collation.isSelfContainedCE32(n3) && Collation.isSelfContainedCE32(n4)) {
                if (n3 == n4) continue;
                this.tailored.add(n);
                continue;
            }
            this.compare(n, n3, n4);
        } while (++n <= n2);
    }

    private void compare(int n, int n2, int n3) {
        int n4;
        int n5;
        if (Collation.isPrefixCE32(n2)) {
            n5 = Collation.indexFromCE32(n2);
            n2 = this.data.getFinalCE32(this.data.getCE32FromContexts(n5));
            if (Collation.isPrefixCE32(n3)) {
                n4 = Collation.indexFromCE32(n3);
                n3 = this.baseData.getFinalCE32(this.baseData.getCE32FromContexts(n4));
                this.comparePrefixes(n, this.data.contexts, n5 + 2, this.baseData.contexts, n4 + 2);
            } else {
                this.addPrefixes(this.data, n, this.data.contexts, n5 + 2);
            }
        } else if (Collation.isPrefixCE32(n3)) {
            n5 = Collation.indexFromCE32(n3);
            n3 = this.baseData.getFinalCE32(this.baseData.getCE32FromContexts(n5));
            this.addPrefixes(this.baseData, n, this.baseData.contexts, n5 + 2);
        }
        if (Collation.isContractionCE32(n2)) {
            n5 = Collation.indexFromCE32(n2);
            n2 = (n2 & 0x100) != 0 ? 1 : this.data.getFinalCE32(this.data.getCE32FromContexts(n5));
            if (Collation.isContractionCE32(n3)) {
                n4 = Collation.indexFromCE32(n3);
                n3 = (n3 & 0x100) != 0 ? 1 : this.baseData.getFinalCE32(this.baseData.getCE32FromContexts(n4));
                this.compareContractions(n, this.data.contexts, n5 + 2, this.baseData.contexts, n4 + 2);
            } else {
                this.addContractions(n, this.data.contexts, n5 + 2);
            }
        } else if (Collation.isContractionCE32(n3)) {
            n5 = Collation.indexFromCE32(n3);
            n3 = this.baseData.getFinalCE32(this.baseData.getCE32FromContexts(n5));
            this.addContractions(n, this.baseData.contexts, n5 + 2);
        }
        if (Collation.isSpecialCE32(n2)) {
            n5 = Collation.tagFromCE32(n2);
            if (!$assertionsDisabled && n5 == 8) {
                throw new AssertionError();
            }
            if (!$assertionsDisabled && n5 == 9) {
                throw new AssertionError();
            }
            if (!$assertionsDisabled && n5 == 14) {
                throw new AssertionError();
            }
        } else {
            n5 = -1;
        }
        if (Collation.isSpecialCE32(n3)) {
            n4 = Collation.tagFromCE32(n3);
            if (!$assertionsDisabled && n4 == 8) {
                throw new AssertionError();
            }
            if (!$assertionsDisabled && n4 == 9) {
                throw new AssertionError();
            }
        } else {
            n4 = -1;
        }
        if (n4 == 14) {
            if (!Collation.isLongPrimaryCE32(n2)) {
                this.add(n);
                return;
            }
            long l = this.baseData.ces[Collation.indexFromCE32(n3)];
            long l2 = Collation.getThreeBytePrimaryForOffsetData(n, l);
            if (Collation.primaryFromLongPrimaryCE32(n2) != l2) {
                this.add(n);
                return;
            }
        }
        if (n5 != n4) {
            this.add(n);
            return;
        }
        if (n5 == 5) {
            int n6;
            int n7 = Collation.lengthFromCE32(n2);
            if (n7 != (n6 = Collation.lengthFromCE32(n3))) {
                this.add(n);
                return;
            }
            int n8 = Collation.indexFromCE32(n2);
            int n9 = Collation.indexFromCE32(n3);
            for (int i = 0; i < n7; ++i) {
                if (this.data.ce32s[n8 + i] == this.baseData.ce32s[n9 + i]) continue;
                this.add(n);
                break;
            }
        } else if (n5 == 6) {
            int n10;
            int n11 = Collation.lengthFromCE32(n2);
            if (n11 != (n10 = Collation.lengthFromCE32(n3))) {
                this.add(n);
                return;
            }
            int n12 = Collation.indexFromCE32(n2);
            int n13 = Collation.indexFromCE32(n3);
            for (int i = 0; i < n11; ++i) {
                if (this.data.ces[n12 + i] == this.baseData.ces[n13 + i]) continue;
                this.add(n);
                break;
            }
        } else if (n5 == 12) {
            StringBuilder stringBuilder = new StringBuilder();
            int n14 = Normalizer2Impl.Hangul.decompose(n, stringBuilder);
            if (this.tailored.contains(stringBuilder.charAt(0)) || this.tailored.contains(stringBuilder.charAt(1)) || n14 == 3 && this.tailored.contains(stringBuilder.charAt(2))) {
                this.add(n);
            }
        } else if (n2 != n3) {
            this.add(n);
        }
    }

    private void comparePrefixes(int n, CharSequence charSequence, int n2, CharSequence charSequence2, int n3) {
        CharsTrie.Iterator iterator2 = new CharsTrie(charSequence, n2).iterator();
        CharsTrie.Iterator iterator3 = new CharsTrie(charSequence2, n3).iterator();
        String string = null;
        String string2 = null;
        String string3 = "\uffff";
        CharsTrie.Entry entry = null;
        CharsTrie.Entry entry2 = null;
        while (true) {
            if (string == null) {
                if (iterator2.hasNext()) {
                    entry = iterator2.next();
                    string = entry.chars.toString();
                } else {
                    entry = null;
                    string = string3;
                }
            }
            if (string2 == null) {
                if (iterator3.hasNext()) {
                    entry2 = iterator3.next();
                    string2 = entry2.chars.toString();
                } else {
                    entry2 = null;
                    string2 = string3;
                }
            }
            if (Utility.sameObjects(string, string3) && Utility.sameObjects(string2, string3)) break;
            int n4 = string.compareTo(string2);
            if (n4 < 0) {
                if (!$assertionsDisabled && entry == null) {
                    throw new AssertionError();
                }
                this.addPrefix(this.data, string, n, entry.value);
                entry = null;
                string = null;
                continue;
            }
            if (n4 > 0) {
                if (!$assertionsDisabled && entry2 == null) {
                    throw new AssertionError();
                }
                this.addPrefix(this.baseData, string2, n, entry2.value);
                entry2 = null;
                string2 = null;
                continue;
            }
            this.setPrefix(string);
            if (!($assertionsDisabled || entry != null && entry2 != null)) {
                throw new AssertionError();
            }
            this.compare(n, entry.value, entry2.value);
            this.resetPrefix();
            entry2 = null;
            entry = null;
            string2 = null;
            string = null;
        }
    }

    private void compareContractions(int n, CharSequence charSequence, int n2, CharSequence charSequence2, int n3) {
        CharsTrie.Iterator iterator2 = new CharsTrie(charSequence, n2).iterator();
        CharsTrie.Iterator iterator3 = new CharsTrie(charSequence2, n3).iterator();
        String string = null;
        String string2 = null;
        String string3 = "\uffff\uffff";
        CharsTrie.Entry entry = null;
        CharsTrie.Entry entry2 = null;
        while (true) {
            if (string == null) {
                if (iterator2.hasNext()) {
                    entry = iterator2.next();
                    string = entry.chars.toString();
                } else {
                    entry = null;
                    string = string3;
                }
            }
            if (string2 == null) {
                if (iterator3.hasNext()) {
                    entry2 = iterator3.next();
                    string2 = entry2.chars.toString();
                } else {
                    entry2 = null;
                    string2 = string3;
                }
            }
            if (Utility.sameObjects(string, string3) && Utility.sameObjects(string2, string3)) break;
            int n4 = string.compareTo(string2);
            if (n4 < 0) {
                this.addSuffix(n, string);
                entry = null;
                string = null;
                continue;
            }
            if (n4 > 0) {
                this.addSuffix(n, string2);
                entry2 = null;
                string2 = null;
                continue;
            }
            this.suffix = string;
            this.compare(n, entry.value, entry2.value);
            this.suffix = null;
            entry2 = null;
            entry = null;
            string2 = null;
            string = null;
        }
    }

    private void addPrefixes(CollationData collationData, int n, CharSequence charSequence, int n2) {
        for (CharsTrie.Entry entry : new CharsTrie(charSequence, n2)) {
            this.addPrefix(collationData, entry.chars, n, entry.value);
        }
    }

    private void addPrefix(CollationData collationData, CharSequence charSequence, int n, int n2) {
        this.setPrefix(charSequence);
        n2 = collationData.getFinalCE32(n2);
        if (Collation.isContractionCE32(n2)) {
            int n3 = Collation.indexFromCE32(n2);
            this.addContractions(n, collationData.contexts, n3 + 2);
        }
        this.tailored.add(new StringBuilder(this.unreversedPrefix.appendCodePoint(n)));
        this.resetPrefix();
    }

    private void addContractions(int n, CharSequence charSequence, int n2) {
        for (CharsTrie.Entry entry : new CharsTrie(charSequence, n2)) {
            this.addSuffix(n, entry.chars);
        }
    }

    private void addSuffix(int n, CharSequence charSequence) {
        this.tailored.add(new StringBuilder(this.unreversedPrefix).appendCodePoint(n).append(charSequence));
    }

    private void add(int n) {
        if (this.unreversedPrefix.length() == 0 && this.suffix == null) {
            this.tailored.add(n);
        } else {
            StringBuilder stringBuilder = new StringBuilder(this.unreversedPrefix);
            stringBuilder.appendCodePoint(n);
            if (this.suffix != null) {
                stringBuilder.append(this.suffix);
            }
            this.tailored.add(stringBuilder);
        }
    }

    private void setPrefix(CharSequence charSequence) {
        this.unreversedPrefix.setLength(0);
        this.unreversedPrefix.append(charSequence).reverse();
    }

    private void resetPrefix() {
        this.unreversedPrefix.setLength(0);
    }
}

