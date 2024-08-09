/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.text;

import com.ibm.icu.impl.ICULocaleService;
import com.ibm.icu.impl.ICUResourceBundle;
import com.ibm.icu.impl.ICUService;
import com.ibm.icu.text.NumberFormat;
import com.ibm.icu.util.Currency;
import com.ibm.icu.util.ULocale;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.Set;

class NumberFormatServiceShim
extends NumberFormat.NumberFormatShim {
    private static ICULocaleService service = new NFService();

    NumberFormatServiceShim() {
    }

    @Override
    Locale[] getAvailableLocales() {
        if (service.isDefault()) {
            return ICUResourceBundle.getAvailableLocales();
        }
        return service.getAvailableLocales();
    }

    @Override
    ULocale[] getAvailableULocales() {
        if (service.isDefault()) {
            return ICUResourceBundle.getAvailableULocales();
        }
        return service.getAvailableULocales();
    }

    @Override
    Object registerFactory(NumberFormat.NumberFormatFactory numberFormatFactory) {
        return service.registerFactory(new NFFactory(numberFormatFactory));
    }

    @Override
    boolean unregister(Object object) {
        return service.unregisterFactory((ICUService.Factory)object);
    }

    @Override
    NumberFormat createInstance(ULocale uLocale, int n) {
        ULocale[] uLocaleArray = new ULocale[1];
        NumberFormat numberFormat = (NumberFormat)service.get(uLocale, n, uLocaleArray);
        if (numberFormat == null) {
            throw new MissingResourceException("Unable to construct NumberFormat", "", "");
        }
        numberFormat = (NumberFormat)numberFormat.clone();
        if (n == 1 || n == 5 || n == 6 || n == 7 || n == 8 || n == 9) {
            numberFormat.setCurrency(Currency.getInstance(uLocale));
        }
        ULocale uLocale2 = uLocaleArray[0];
        numberFormat.setLocale(uLocale2, uLocale2);
        return numberFormat;
    }

    private static class NFService
    extends ICULocaleService {
        NFService() {
            super("NumberFormat");
            class RBNumberFormatFactory
            extends ICULocaleService.ICUResourceBundleFactory {
                final NFService this$0;

                RBNumberFormatFactory(NFService nFService) {
                    this.this$0 = nFService;
                }

                @Override
                protected Object handleCreate(ULocale uLocale, int n, ICUService iCUService) {
                    return NumberFormat.createInstance(uLocale, n);
                }
            }
            this.registerFactory(new RBNumberFormatFactory(this));
            this.markDefault();
        }
    }

    private static final class NFFactory
    extends ICULocaleService.LocaleKeyFactory {
        private NumberFormat.NumberFormatFactory delegate;

        NFFactory(NumberFormat.NumberFormatFactory numberFormatFactory) {
            super(numberFormatFactory.visible());
            this.delegate = numberFormatFactory;
        }

        @Override
        public Object create(ICUService.Key key, ICUService iCUService) {
            if (!this.handlesKey(key) || !(key instanceof ICULocaleService.LocaleKey)) {
                return null;
            }
            ICULocaleService.LocaleKey localeKey = (ICULocaleService.LocaleKey)key;
            Object object = this.delegate.createFormat(localeKey.canonicalLocale(), localeKey.kind());
            if (object == null) {
                object = iCUService.getKey(key, null, this);
            }
            return object;
        }

        @Override
        protected Set<String> getSupportedIDs() {
            return this.delegate.getSupportedLocaleNames();
        }
    }
}

