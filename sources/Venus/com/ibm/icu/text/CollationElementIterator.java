/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.text;

import com.ibm.icu.impl.CharacterIteratorWrapper;
import com.ibm.icu.impl.coll.CollationData;
import com.ibm.icu.impl.coll.CollationIterator;
import com.ibm.icu.impl.coll.ContractionsAndExpansions;
import com.ibm.icu.impl.coll.FCDIterCollationIterator;
import com.ibm.icu.impl.coll.FCDUTF16CollationIterator;
import com.ibm.icu.impl.coll.IterCollationIterator;
import com.ibm.icu.impl.coll.UTF16CollationIterator;
import com.ibm.icu.impl.coll.UVector32;
import com.ibm.icu.text.RuleBasedCollator;
import com.ibm.icu.text.UCharacterIterator;
import java.text.CharacterIterator;
import java.util.HashMap;
import java.util.Map;

public final class CollationElementIterator {
    private CollationIterator iter_ = null;
    private RuleBasedCollator rbc_;
    private int otherHalf_;
    private byte dir_;
    private UVector32 offsets_;
    private String string_;
    public static final int NULLORDER = -1;
    public static final int IGNORABLE = 0;
    static final boolean $assertionsDisabled = !CollationElementIterator.class.desiredAssertionStatus();

    public static final int primaryOrder(int n) {
        return n >>> 16 & 0xFFFF;
    }

    public static final int secondaryOrder(int n) {
        return n >>> 8 & 0xFF;
    }

    public static final int tertiaryOrder(int n) {
        return n & 0xFF;
    }

    private static final int getFirstHalf(long l, int n) {
        return (int)l & 0xFFFF0000 | n >> 16 & 0xFF00 | n >> 8 & 0xFF;
    }

    private static final int getSecondHalf(long l, int n) {
        return (int)l << 16 | n >> 8 & 0xFF00 | n & 0x3F;
    }

    private static final boolean ceNeedsTwoParts(long l) {
        return (l & 0xFFFF00FF003FL) != 0L;
    }

    private CollationElementIterator(RuleBasedCollator ruleBasedCollator) {
        this.rbc_ = ruleBasedCollator;
        this.otherHalf_ = 0;
        this.dir_ = 0;
        this.offsets_ = null;
    }

    CollationElementIterator(String string, RuleBasedCollator ruleBasedCollator) {
        this(ruleBasedCollator);
        this.setText(string);
    }

    CollationElementIterator(CharacterIterator characterIterator, RuleBasedCollator ruleBasedCollator) {
        this(ruleBasedCollator);
        this.setText(characterIterator);
    }

    CollationElementIterator(UCharacterIterator uCharacterIterator, RuleBasedCollator ruleBasedCollator) {
        this(ruleBasedCollator);
        this.setText(uCharacterIterator);
    }

    public int getOffset() {
        if (this.dir_ < 0 && this.offsets_ != null && !this.offsets_.isEmpty()) {
            int n = this.iter_.getCEsLength();
            if (this.otherHalf_ != 0) {
                ++n;
            }
            if (!$assertionsDisabled && n >= this.offsets_.size()) {
                throw new AssertionError();
            }
            return this.offsets_.elementAti(n);
        }
        return this.iter_.getOffset();
    }

    public int next() {
        if (this.dir_ > 1) {
            if (this.otherHalf_ != 0) {
                int n = this.otherHalf_;
                this.otherHalf_ = 0;
                return n;
            }
        } else if (this.dir_ == 1) {
            this.dir_ = (byte)2;
        } else if (this.dir_ == 0) {
            this.dir_ = (byte)2;
        } else {
            throw new IllegalStateException("Illegal change of direction");
        }
        this.iter_.clearCEsIfNoneRemaining();
        long l = this.iter_.nextCE();
        if (l == 0x101000100L) {
            return 1;
        }
        long l2 = l >>> 32;
        int n = (int)l;
        int n2 = CollationElementIterator.getFirstHalf(l2, n);
        int n3 = CollationElementIterator.getSecondHalf(l2, n);
        if (n3 != 0) {
            this.otherHalf_ = n3 | 0xC0;
        }
        return n2;
    }

    public int previous() {
        if (this.dir_ < 0) {
            if (this.otherHalf_ != 0) {
                int n = this.otherHalf_;
                this.otherHalf_ = 0;
                return n;
            }
        } else if (this.dir_ == 0) {
            this.iter_.resetToOffset(this.string_.length());
            this.dir_ = (byte)-1;
        } else if (this.dir_ == 1) {
            this.dir_ = (byte)-1;
        } else {
            throw new IllegalStateException("Illegal change of direction");
        }
        if (this.offsets_ == null) {
            this.offsets_ = new UVector32();
        }
        int n = this.iter_.getCEsLength() == 0 ? this.iter_.getOffset() : 0;
        long l = this.iter_.previousCE(this.offsets_);
        if (l == 0x101000100L) {
            return 1;
        }
        long l2 = l >>> 32;
        int n2 = (int)l;
        int n3 = CollationElementIterator.getFirstHalf(l2, n2);
        int n4 = CollationElementIterator.getSecondHalf(l2, n2);
        if (n4 != 0) {
            if (this.offsets_.isEmpty()) {
                this.offsets_.addElement(this.iter_.getOffset());
                this.offsets_.addElement(n);
            }
            this.otherHalf_ = n3;
            return n4 | 0xC0;
        }
        return n3;
    }

    public void reset() {
        this.iter_.resetToOffset(0);
        this.otherHalf_ = 0;
        this.dir_ = 0;
    }

    public void setOffset(int n) {
        if (0 < n && n < this.string_.length()) {
            int n2;
            int n3 = n;
            while (this.rbc_.isUnsafe(n2 = this.string_.charAt(n3)) && (!Character.isHighSurrogate((char)n2) || this.rbc_.isUnsafe(this.string_.codePointAt(n3))) && --n3 > 0) {
            }
            if (n3 < n) {
                n2 = n3;
                do {
                    this.iter_.resetToOffset(n2);
                    do {
                        this.iter_.nextCE();
                    } while ((n3 = this.iter_.getOffset()) == n2);
                    if (n3 > n) continue;
                    n2 = n3;
                } while (n3 < n);
                n = n2;
            }
        }
        this.iter_.resetToOffset(n);
        this.otherHalf_ = 0;
        this.dir_ = 1;
    }

    public void setText(String string) {
        this.string_ = string;
        boolean bl = this.rbc_.settings.readOnly().isNumeric();
        UTF16CollationIterator uTF16CollationIterator = this.rbc_.settings.readOnly().dontCheckFCD() ? new UTF16CollationIterator(this.rbc_.data, bl, this.string_, 0) : new FCDUTF16CollationIterator(this.rbc_.data, bl, this.string_, 0);
        this.iter_ = uTF16CollationIterator;
        this.otherHalf_ = 0;
        this.dir_ = 0;
    }

    public void setText(UCharacterIterator uCharacterIterator) {
        UCharacterIterator uCharacterIterator2;
        this.string_ = uCharacterIterator.getText();
        try {
            uCharacterIterator2 = (UCharacterIterator)uCharacterIterator.clone();
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            this.setText(uCharacterIterator.getText());
            return;
        }
        uCharacterIterator2.setToStart();
        boolean bl = this.rbc_.settings.readOnly().isNumeric();
        IterCollationIterator iterCollationIterator = this.rbc_.settings.readOnly().dontCheckFCD() ? new IterCollationIterator(this.rbc_.data, bl, uCharacterIterator2) : new FCDIterCollationIterator(this.rbc_.data, bl, uCharacterIterator2, 0);
        this.iter_ = iterCollationIterator;
        this.otherHalf_ = 0;
        this.dir_ = 0;
    }

    public void setText(CharacterIterator characterIterator) {
        CharacterIteratorWrapper characterIteratorWrapper = new CharacterIteratorWrapper(characterIterator);
        characterIteratorWrapper.setToStart();
        this.string_ = characterIteratorWrapper.getText();
        boolean bl = this.rbc_.settings.readOnly().isNumeric();
        IterCollationIterator iterCollationIterator = this.rbc_.settings.readOnly().dontCheckFCD() ? new IterCollationIterator(this.rbc_.data, bl, characterIteratorWrapper) : new FCDIterCollationIterator(this.rbc_.data, bl, characterIteratorWrapper, 0);
        this.iter_ = iterCollationIterator;
        this.otherHalf_ = 0;
        this.dir_ = 0;
    }

    static final Map<Integer, Integer> computeMaxExpansions(CollationData collationData) {
        HashMap<Integer, Integer> hashMap = new HashMap<Integer, Integer>();
        MaxExpSink maxExpSink = new MaxExpSink(hashMap);
        new ContractionsAndExpansions(null, null, maxExpSink, true).forData(collationData);
        return hashMap;
    }

    public int getMaxExpansion(int n) {
        return CollationElementIterator.getMaxExpansion(this.rbc_.tailoring.maxExpansions, n);
    }

    static int getMaxExpansion(Map<Integer, Integer> map, int n) {
        Integer n2;
        if (n == 0) {
            return 0;
        }
        if (map != null && (n2 = map.get(n)) != null) {
            return n2;
        }
        if ((n & 0xC0) == 192) {
            return 1;
        }
        return 0;
    }

    private byte normalizeDir() {
        return this.dir_ == 1 ? (byte)0 : this.dir_;
    }

    public boolean equals(Object object) {
        if (object == this) {
            return false;
        }
        if (object instanceof CollationElementIterator) {
            CollationElementIterator collationElementIterator = (CollationElementIterator)object;
            return this.rbc_.equals(collationElementIterator.rbc_) && this.otherHalf_ == collationElementIterator.otherHalf_ && this.normalizeDir() == collationElementIterator.normalizeDir() && this.string_.equals(collationElementIterator.string_) && this.iter_.equals(collationElementIterator.iter_);
        }
        return true;
    }

    public int hashCode() {
        if (!$assertionsDisabled) {
            throw new AssertionError((Object)"hashCode not designed");
        }
        return 1;
    }

    @Deprecated
    public RuleBasedCollator getRuleBasedCollator() {
        return this.rbc_;
    }

    static boolean access$000(long l) {
        return CollationElementIterator.ceNeedsTwoParts(l);
    }

    static int access$100(long l, int n) {
        return CollationElementIterator.getSecondHalf(l, n);
    }

    static int access$200(long l, int n) {
        return CollationElementIterator.getFirstHalf(l, n);
    }

    private static final class MaxExpSink
    implements ContractionsAndExpansions.CESink {
        private Map<Integer, Integer> maxExpansions;
        static final boolean $assertionsDisabled = !CollationElementIterator.class.desiredAssertionStatus();

        MaxExpSink(Map<Integer, Integer> map) {
            this.maxExpansions = map;
        }

        @Override
        public void handleCE(long l) {
        }

        @Override
        public void handleExpansion(long[] lArray, int n, int n2) {
            Integer n3;
            if (n2 <= 1) {
                return;
            }
            int n4 = 0;
            for (int i = 0; i < n2; ++i) {
                n4 += CollationElementIterator.access$000(lArray[n + i]) ? 2 : 1;
            }
            long l = lArray[n + n2 - 1];
            long l2 = l >>> 32;
            int n5 = (int)l;
            int n6 = CollationElementIterator.access$100(l2, n5);
            if (n6 == 0) {
                n6 = CollationElementIterator.access$200(l2, n5);
                if (!$assertionsDisabled && n6 == 0) {
                    throw new AssertionError();
                }
            } else {
                n6 |= 0xC0;
            }
            if ((n3 = this.maxExpansions.get(n6)) == null || n4 > n3) {
                this.maxExpansions.put(n6, n4);
            }
        }
    }
}

