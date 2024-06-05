package com.ibm.icu.text;

import java.util.*;
import com.ibm.icu.impl.*;

public abstract class CaseMap
{
    @Deprecated
    protected int internalOptions;
    
    private CaseMap(final int opt) {
        this.internalOptions = opt;
    }
    
    private static int getCaseLocale(Locale locale) {
        if (locale == null) {
            locale = Locale.getDefault();
        }
        return UCaseProps.getCaseLocale(locale);
    }
    
    public static Lower toLower() {
        return Lower.DEFAULT;
    }
    
    public static Upper toUpper() {
        return Upper.DEFAULT;
    }
    
    public static Title toTitle() {
        return Title.DEFAULT;
    }
    
    public static Fold fold() {
        return Fold.DEFAULT;
    }
    
    public abstract CaseMap omitUnchangedText();
    
    public static final class Lower extends CaseMap
    {
        private static final Lower DEFAULT;
        private static final Lower OMIT_UNCHANGED;
        
        private Lower(final int opt) {
            super(opt, null);
        }
        
        @Override
        public Lower omitUnchangedText() {
            return Lower.OMIT_UNCHANGED;
        }
        
        public String apply(final Locale locale, final CharSequence src) {
            return CaseMapImpl.toLower(getCaseLocale(locale), this.internalOptions, src);
        }
        
        public <A extends Appendable> A apply(final Locale locale, final CharSequence src, final A dest, final Edits edits) {
            return CaseMapImpl.toLower(getCaseLocale(locale), this.internalOptions, src, dest, edits);
        }
        
        static {
            DEFAULT = new Lower(0);
            OMIT_UNCHANGED = new Lower(16384);
        }
    }
    
    public static final class Upper extends CaseMap
    {
        private static final Upper DEFAULT;
        private static final Upper OMIT_UNCHANGED;
        
        private Upper(final int opt) {
            super(opt, null);
        }
        
        @Override
        public Upper omitUnchangedText() {
            return Upper.OMIT_UNCHANGED;
        }
        
        public String apply(final Locale locale, final CharSequence src) {
            return CaseMapImpl.toUpper(getCaseLocale(locale), this.internalOptions, src);
        }
        
        public <A extends Appendable> A apply(final Locale locale, final CharSequence src, final A dest, final Edits edits) {
            return CaseMapImpl.toUpper(getCaseLocale(locale), this.internalOptions, src, dest, edits);
        }
        
        static {
            DEFAULT = new Upper(0);
            OMIT_UNCHANGED = new Upper(16384);
        }
    }
    
    public static final class Title extends CaseMap
    {
        private static final Title DEFAULT;
        private static final Title OMIT_UNCHANGED;
        
        private Title(final int opt) {
            super(opt, null);
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
                return Title.OMIT_UNCHANGED;
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
        
        public String apply(Locale locale, BreakIterator iter, final CharSequence src) {
            if (iter == null && locale == null) {
                locale = Locale.getDefault();
            }
            iter = CaseMapImpl.getTitleBreakIterator(locale, this.internalOptions, iter);
            iter.setText(src);
            return CaseMapImpl.toTitle(getCaseLocale(locale), this.internalOptions, iter, src);
        }
        
        public <A extends Appendable> A apply(Locale locale, BreakIterator iter, final CharSequence src, final A dest, final Edits edits) {
            if (iter == null && locale == null) {
                locale = Locale.getDefault();
            }
            iter = CaseMapImpl.getTitleBreakIterator(locale, this.internalOptions, iter);
            iter.setText(src);
            return CaseMapImpl.toTitle(getCaseLocale(locale), this.internalOptions, iter, src, dest, edits);
        }
        
        static {
            DEFAULT = new Title(0);
            OMIT_UNCHANGED = new Title(16384);
        }
    }
    
    public static final class Fold extends CaseMap
    {
        private static final Fold DEFAULT;
        private static final Fold TURKIC;
        private static final Fold OMIT_UNCHANGED;
        private static final Fold TURKIC_OMIT_UNCHANGED;
        
        private Fold(final int opt) {
            super(opt, null);
        }
        
        @Override
        public Fold omitUnchangedText() {
            return ((this.internalOptions & 0x1) == 0x0) ? Fold.OMIT_UNCHANGED : Fold.TURKIC_OMIT_UNCHANGED;
        }
        
        public Fold turkic() {
            return ((this.internalOptions & 0x4000) == 0x0) ? Fold.TURKIC : Fold.TURKIC_OMIT_UNCHANGED;
        }
        
        public String apply(final CharSequence src) {
            return CaseMapImpl.fold(this.internalOptions, src);
        }
        
        public <A extends Appendable> A apply(final CharSequence src, final A dest, final Edits edits) {
            return CaseMapImpl.fold(this.internalOptions, src, dest, edits);
        }
        
        static {
            DEFAULT = new Fold(0);
            TURKIC = new Fold(1);
            OMIT_UNCHANGED = new Fold(16384);
            TURKIC_OMIT_UNCHANGED = new Fold(16385);
        }
    }
}
