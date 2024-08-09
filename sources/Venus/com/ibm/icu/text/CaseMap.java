/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.text;

import com.ibm.icu.impl.CaseMapImpl;
import com.ibm.icu.impl.UCaseProps;
import com.ibm.icu.text.BreakIterator;
import com.ibm.icu.text.Edits;
import java.util.Locale;

public abstract class CaseMap {
    @Deprecated
    protected int internalOptions;

    private CaseMap(int n) {
        this.internalOptions = n;
    }

    private static int getCaseLocale(Locale locale) {
        if (locale == null) {
            locale = Locale.getDefault();
        }
        return UCaseProps.getCaseLocale(locale);
    }

    public static Lower toLower() {
        return Lower.access$000();
    }

    public static Upper toUpper() {
        return Upper.access$100();
    }

    public static Title toTitle() {
        return Title.access$200();
    }

    public static Fold fold() {
        return Fold.access$300();
    }

    public abstract CaseMap omitUnchangedText();

    CaseMap(int n, 1 var2_2) {
        this(n);
    }

    static int access$500(Locale locale) {
        return CaseMap.getCaseLocale(locale);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static final class Fold
    extends CaseMap {
        private static final Fold DEFAULT = new Fold(0);
        private static final Fold TURKIC = new Fold(1);
        private static final Fold OMIT_UNCHANGED = new Fold(16384);
        private static final Fold TURKIC_OMIT_UNCHANGED = new Fold(16385);

        private Fold(int n) {
            super(n, null);
        }

        @Override
        public Fold omitUnchangedText() {
            return (this.internalOptions & 1) == 0 ? OMIT_UNCHANGED : TURKIC_OMIT_UNCHANGED;
        }

        public Fold turkic() {
            return (this.internalOptions & 0x4000) == 0 ? TURKIC : TURKIC_OMIT_UNCHANGED;
        }

        public String apply(CharSequence charSequence) {
            return CaseMapImpl.fold(this.internalOptions, charSequence);
        }

        public <A extends Appendable> A apply(CharSequence charSequence, A a, Edits edits) {
            return CaseMapImpl.fold(this.internalOptions, charSequence, a, edits);
        }

        @Override
        public CaseMap omitUnchangedText() {
            return this.omitUnchangedText();
        }

        static Fold access$300() {
            return DEFAULT;
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static final class Title
    extends CaseMap {
        private static final Title DEFAULT = new Title(0);
        private static final Title OMIT_UNCHANGED = new Title(16384);

        private Title(int n) {
            super(n, null);
        }

        public Title wholeString() {
            return new Title(CaseMapImpl.addTitleIteratorOption(this.internalOptions, 32));
        }

        public Title sentences() {
            return new Title(CaseMapImpl.addTitleIteratorOption(this.internalOptions, 64));
        }

        @Override
        public Title omitUnchangedText() {
            if (this.internalOptions == 0 || this.internalOptions == 16384) {
                return OMIT_UNCHANGED;
            }
            return new Title(this.internalOptions | 0x4000);
        }

        public Title noLowercase() {
            return new Title(this.internalOptions | 0x100);
        }

        public Title noBreakAdjustment() {
            return new Title(CaseMapImpl.addTitleAdjustmentOption(this.internalOptions, 512));
        }

        public Title adjustToCased() {
            return new Title(CaseMapImpl.addTitleAdjustmentOption(this.internalOptions, 1024));
        }

        public String apply(Locale locale, BreakIterator breakIterator, CharSequence charSequence) {
            if (breakIterator == null && locale == null) {
                locale = Locale.getDefault();
            }
            breakIterator = CaseMapImpl.getTitleBreakIterator(locale, this.internalOptions, breakIterator);
            breakIterator.setText(charSequence);
            return CaseMapImpl.toTitle(CaseMap.access$500(locale), this.internalOptions, breakIterator, charSequence);
        }

        public <A extends Appendable> A apply(Locale locale, BreakIterator breakIterator, CharSequence charSequence, A a, Edits edits) {
            if (breakIterator == null && locale == null) {
                locale = Locale.getDefault();
            }
            breakIterator = CaseMapImpl.getTitleBreakIterator(locale, this.internalOptions, breakIterator);
            breakIterator.setText(charSequence);
            return CaseMapImpl.toTitle(CaseMap.access$500(locale), this.internalOptions, breakIterator, charSequence, a, edits);
        }

        @Override
        public CaseMap omitUnchangedText() {
            return this.omitUnchangedText();
        }

        static Title access$200() {
            return DEFAULT;
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static final class Upper
    extends CaseMap {
        private static final Upper DEFAULT = new Upper(0);
        private static final Upper OMIT_UNCHANGED = new Upper(16384);

        private Upper(int n) {
            super(n, null);
        }

        @Override
        public Upper omitUnchangedText() {
            return OMIT_UNCHANGED;
        }

        public String apply(Locale locale, CharSequence charSequence) {
            return CaseMapImpl.toUpper(CaseMap.access$500(locale), this.internalOptions, charSequence);
        }

        public <A extends Appendable> A apply(Locale locale, CharSequence charSequence, A a, Edits edits) {
            return CaseMapImpl.toUpper(CaseMap.access$500(locale), this.internalOptions, charSequence, a, edits);
        }

        @Override
        public CaseMap omitUnchangedText() {
            return this.omitUnchangedText();
        }

        static Upper access$100() {
            return DEFAULT;
        }
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static final class Lower
    extends CaseMap {
        private static final Lower DEFAULT = new Lower(0);
        private static final Lower OMIT_UNCHANGED = new Lower(16384);

        private Lower(int n) {
            super(n, null);
        }

        @Override
        public Lower omitUnchangedText() {
            return OMIT_UNCHANGED;
        }

        public String apply(Locale locale, CharSequence charSequence) {
            return CaseMapImpl.toLower(CaseMap.access$500(locale), this.internalOptions, charSequence);
        }

        public <A extends Appendable> A apply(Locale locale, CharSequence charSequence, A a, Edits edits) {
            return CaseMapImpl.toLower(CaseMap.access$500(locale), this.internalOptions, charSequence, a, edits);
        }

        @Override
        public CaseMap omitUnchangedText() {
            return this.omitUnchangedText();
        }

        static Lower access$000() {
            return DEFAULT;
        }
    }
}

