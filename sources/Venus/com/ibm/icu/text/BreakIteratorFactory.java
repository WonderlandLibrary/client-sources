/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.text;

import com.ibm.icu.impl.Assert;
import com.ibm.icu.impl.ICUBinary;
import com.ibm.icu.impl.ICULocaleService;
import com.ibm.icu.impl.ICUResourceBundle;
import com.ibm.icu.impl.ICUService;
import com.ibm.icu.text.BreakIterator;
import com.ibm.icu.text.FilteredBreakIteratorBuilder;
import com.ibm.icu.text.RuleBasedBreakIterator;
import com.ibm.icu.util.ULocale;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.text.StringCharacterIterator;
import java.util.Locale;
import java.util.MissingResourceException;

final class BreakIteratorFactory
extends BreakIterator.BreakIteratorServiceShim {
    static final ICULocaleService service = new BFService();
    private static final String[] KIND_NAMES = new String[]{"grapheme", "word", "line", "sentence", "title"};

    BreakIteratorFactory() {
    }

    @Override
    public Object registerInstance(BreakIterator breakIterator, ULocale uLocale, int n) {
        breakIterator.setText(new StringCharacterIterator(""));
        return service.registerObject((Object)breakIterator, uLocale, n);
    }

    @Override
    public boolean unregister(Object object) {
        if (service.isDefault()) {
            return true;
        }
        return service.unregisterFactory((ICUService.Factory)object);
    }

    @Override
    public Locale[] getAvailableLocales() {
        if (service == null) {
            return ICUResourceBundle.getAvailableLocales();
        }
        return service.getAvailableLocales();
    }

    @Override
    public ULocale[] getAvailableULocales() {
        if (service == null) {
            return ICUResourceBundle.getAvailableULocales();
        }
        return service.getAvailableULocales();
    }

    @Override
    public BreakIterator createBreakIterator(ULocale uLocale, int n) {
        if (service.isDefault()) {
            return BreakIteratorFactory.createBreakInstance(uLocale, n);
        }
        ULocale[] uLocaleArray = new ULocale[1];
        BreakIterator breakIterator = (BreakIterator)service.get(uLocale, n, uLocaleArray);
        breakIterator.setLocale(uLocaleArray[0], uLocaleArray[0]);
        return breakIterator;
    }

    private static BreakIterator createBreakInstance(ULocale uLocale, int n) {
        Object object;
        String string;
        Object object2;
        RuleBasedBreakIterator ruleBasedBreakIterator = null;
        ICUResourceBundle iCUResourceBundle = ICUResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt66b/brkitr", uLocale, ICUResourceBundle.OpenType.LOCALE_ROOT);
        ByteBuffer byteBuffer = null;
        String string2 = null;
        if (n == 2 && (object2 = uLocale.getKeywordValue("lb")) != null && (((String)object2).equals("strict") || ((String)object2).equals("normal") || ((String)object2).equals("loose"))) {
            string2 = "_" + (String)object2;
        }
        try {
            object2 = string2 == null ? KIND_NAMES[n] : KIND_NAMES[n] + string2;
            string = iCUResourceBundle.getStringWithFallback("boundaries/" + (String)object2);
            object = "brkitr/" + string;
            byteBuffer = ICUBinary.getData((String)object);
        } catch (Exception exception) {
            throw new MissingResourceException(exception.toString(), "", "");
        }
        try {
            ruleBasedBreakIterator = RuleBasedBreakIterator.getInstanceFromCompiledRules(byteBuffer);
        } catch (IOException iOException) {
            Assert.fail(iOException);
        }
        object2 = ULocale.forLocale(iCUResourceBundle.getLocale());
        ruleBasedBreakIterator.setLocale((ULocale)object2, (ULocale)object2);
        if (n == 3 && (string = uLocale.getKeywordValue("ss")) != null && string.equals("standard")) {
            object = new ULocale(uLocale.getBaseName());
            return FilteredBreakIteratorBuilder.getInstance((ULocale)object).wrapIteratorWithFilter(ruleBasedBreakIterator);
        }
        return ruleBasedBreakIterator;
    }

    static BreakIterator access$000(ULocale uLocale, int n) {
        return BreakIteratorFactory.createBreakInstance(uLocale, n);
    }

    private static class BFService
    extends ICULocaleService {
        BFService() {
            super("BreakIterator");
            class RBBreakIteratorFactory
            extends ICULocaleService.ICUResourceBundleFactory {
                final BFService this$0;

                RBBreakIteratorFactory(BFService bFService) {
                    this.this$0 = bFService;
                }

                @Override
                protected Object handleCreate(ULocale uLocale, int n, ICUService iCUService) {
                    return BreakIteratorFactory.access$000(uLocale, n);
                }
            }
            this.registerFactory(new RBBreakIteratorFactory(this));
            this.markDefault();
        }

        @Override
        public String validateFallbackLocale() {
            return "";
        }
    }
}

