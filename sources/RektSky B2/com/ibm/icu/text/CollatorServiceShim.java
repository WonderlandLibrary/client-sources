package com.ibm.icu.text;

import java.util.*;
import com.ibm.icu.impl.*;
import com.ibm.icu.util.*;
import com.ibm.icu.impl.coll.*;

final class CollatorServiceShim extends Collator.ServiceShim
{
    private static ICULocaleService service;
    
    @Override
    Collator getInstance(final ULocale locale) {
        try {
            final ULocale[] actualLoc = { null };
            final Collator coll = (Collator)CollatorServiceShim.service.get(locale, actualLoc);
            if (coll == null) {
                throw new MissingResourceException("Could not locate Collator data", "", "");
            }
            return (Collator)coll.clone();
        }
        catch (CloneNotSupportedException e) {
            throw new ICUCloneNotSupportedException(e);
        }
    }
    
    @Override
    Object registerInstance(final Collator collator, final ULocale locale) {
        collator.setLocale(locale, locale);
        return CollatorServiceShim.service.registerObject(collator, locale);
    }
    
    @Override
    Object registerFactory(final Collator.CollatorFactory f) {
        class CFactory extends ICULocaleService.LocaleKeyFactory
        {
            Collator.CollatorFactory delegate = f;
            
            CFactory() {
                super(fctry.visible());
            }
            
            public Object handleCreate(final ULocale loc, final int kind, final ICUService srvc) {
                final Object coll = this.delegate.createCollator(loc);
                return coll;
            }
            
            @Override
            public String getDisplayName(final String id, final ULocale displayLocale) {
                final ULocale objectLocale = new ULocale(id);
                return this.delegate.getDisplayName(objectLocale, displayLocale);
            }
            
            public Set<String> getSupportedIDs() {
                return this.delegate.getSupportedLocaleIDs();
            }
        }
        return CollatorServiceShim.service.registerFactory(new CFactory());
    }
    
    @Override
    boolean unregister(final Object registryKey) {
        return CollatorServiceShim.service.unregisterFactory((ICUService.Factory)registryKey);
    }
    
    @Override
    Locale[] getAvailableLocales() {
        Locale[] result;
        if (CollatorServiceShim.service.isDefault()) {
            result = ICUResourceBundle.getAvailableLocales("com/ibm/icu/impl/data/icudt62b/coll", ICUResourceBundle.ICU_DATA_CLASS_LOADER);
        }
        else {
            result = CollatorServiceShim.service.getAvailableLocales();
        }
        return result;
    }
    
    @Override
    ULocale[] getAvailableULocales() {
        ULocale[] result;
        if (CollatorServiceShim.service.isDefault()) {
            result = ICUResourceBundle.getAvailableULocales("com/ibm/icu/impl/data/icudt62b/coll", ICUResourceBundle.ICU_DATA_CLASS_LOADER);
        }
        else {
            result = CollatorServiceShim.service.getAvailableULocales();
        }
        return result;
    }
    
    @Override
    String getDisplayName(final ULocale objectLocale, final ULocale displayLocale) {
        final String id = objectLocale.getName();
        return CollatorServiceShim.service.getDisplayName(id, displayLocale);
    }
    
    private static final Collator makeInstance(final ULocale desiredLocale) {
        final Output<ULocale> validLocale = new Output<ULocale>(ULocale.ROOT);
        final CollationTailoring t = CollationLoader.loadTailoring(desiredLocale, validLocale);
        return new RuleBasedCollator(t, validLocale.value);
    }
    
    static {
        CollatorServiceShim.service = new CService();
    }
    
    private static class CService extends ICULocaleService
    {
        CService() {
            super("Collator");
            this.registerFactory(new CollatorFactory());
            this.markDefault();
        }
        
        @Override
        public String validateFallbackLocale() {
            return "";
        }
        
        @Override
        protected Object handleDefault(final Key key, final String[] actualIDReturn) {
            if (actualIDReturn != null) {
                actualIDReturn[0] = "root";
            }
            try {
                return makeInstance(ULocale.ROOT);
            }
            catch (MissingResourceException e) {
                return null;
            }
        }
    }
}
