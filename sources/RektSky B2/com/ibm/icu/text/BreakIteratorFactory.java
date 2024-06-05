package com.ibm.icu.text;

import com.ibm.icu.util.*;
import java.text.*;
import java.util.*;
import com.ibm.icu.impl.*;
import java.io.*;
import java.nio.*;

final class BreakIteratorFactory extends BreakIterator.BreakIteratorServiceShim
{
    static final ICULocaleService service;
    private static final String[] KIND_NAMES;
    
    @Override
    public Object registerInstance(final BreakIterator iter, final ULocale locale, final int kind) {
        iter.setText(new StringCharacterIterator(""));
        return BreakIteratorFactory.service.registerObject(iter, locale, kind);
    }
    
    @Override
    public boolean unregister(final Object key) {
        return !BreakIteratorFactory.service.isDefault() && BreakIteratorFactory.service.unregisterFactory((ICUService.Factory)key);
    }
    
    @Override
    public Locale[] getAvailableLocales() {
        if (BreakIteratorFactory.service == null) {
            return ICUResourceBundle.getAvailableLocales();
        }
        return BreakIteratorFactory.service.getAvailableLocales();
    }
    
    @Override
    public ULocale[] getAvailableULocales() {
        if (BreakIteratorFactory.service == null) {
            return ICUResourceBundle.getAvailableULocales();
        }
        return BreakIteratorFactory.service.getAvailableULocales();
    }
    
    @Override
    public BreakIterator createBreakIterator(final ULocale locale, final int kind) {
        if (BreakIteratorFactory.service.isDefault()) {
            return createBreakInstance(locale, kind);
        }
        final ULocale[] actualLoc = { null };
        final BreakIterator iter = (BreakIterator)BreakIteratorFactory.service.get(locale, kind, actualLoc);
        iter.setLocale(actualLoc[0], actualLoc[0]);
        return iter;
    }
    
    private static BreakIterator createBreakInstance(final ULocale locale, final int kind) {
        RuleBasedBreakIterator iter = null;
        final ICUResourceBundle rb = ICUResourceBundle.getBundleInstance("com/ibm/icu/impl/data/icudt62b/brkitr", locale, ICUResourceBundle.OpenType.LOCALE_ROOT);
        ByteBuffer bytes = null;
        String typeKeyExt = null;
        if (kind == 2) {
            final String lbKeyValue = locale.getKeywordValue("lb");
            if (lbKeyValue != null && (lbKeyValue.equals("strict") || lbKeyValue.equals("normal") || lbKeyValue.equals("loose"))) {
                typeKeyExt = "_" + lbKeyValue;
            }
        }
        try {
            final String typeKey = (typeKeyExt == null) ? BreakIteratorFactory.KIND_NAMES[kind] : (BreakIteratorFactory.KIND_NAMES[kind] + typeKeyExt);
            final String brkfname = rb.getStringWithFallback("boundaries/" + typeKey);
            final String rulesFileName = "brkitr/" + brkfname;
            bytes = ICUBinary.getData(rulesFileName);
        }
        catch (Exception e) {
            throw new MissingResourceException(e.toString(), "", "");
        }
        try {
            iter = RuleBasedBreakIterator.getInstanceFromCompiledRules(bytes);
        }
        catch (IOException e2) {
            Assert.fail(e2);
        }
        final ULocale uloc = ULocale.forLocale(rb.getLocale());
        iter.setLocale(uloc, uloc);
        if (kind == 3) {
            final String ssKeyword = locale.getKeywordValue("ss");
            if (ssKeyword != null && ssKeyword.equals("standard")) {
                final ULocale base = new ULocale(locale.getBaseName());
                return FilteredBreakIteratorBuilder.getInstance(base).wrapIteratorWithFilter(iter);
            }
        }
        return iter;
    }
    
    static {
        service = new BFService();
        KIND_NAMES = new String[] { "grapheme", "word", "line", "sentence", "title" };
    }
    
    private static class BFService extends ICULocaleService
    {
        BFService() {
            super("BreakIterator");
            this.registerFactory(new RBBreakIteratorFactory());
            this.markDefault();
        }
        
        @Override
        public String validateFallbackLocale() {
            return "";
        }
    }
}
