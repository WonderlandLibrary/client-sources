/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.util;

import com.ibm.icu.impl.ICULocaleService;
import com.ibm.icu.impl.ICUResourceBundle;
import com.ibm.icu.impl.ICUService;
import com.ibm.icu.util.Currency;
import com.ibm.icu.util.ULocale;
import java.util.Locale;

final class CurrencyServiceShim
extends Currency.ServiceShim {
    static final ICULocaleService service = new CFService();

    CurrencyServiceShim() {
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
    Currency createInstance(ULocale uLocale) {
        if (service.isDefault()) {
            return Currency.createCurrency(uLocale);
        }
        Currency currency = (Currency)service.get(uLocale);
        return currency;
    }

    @Override
    Object registerInstance(Currency currency, ULocale uLocale) {
        return service.registerObject((Object)currency, uLocale);
    }

    @Override
    boolean unregister(Object object) {
        return service.unregisterFactory((ICUService.Factory)object);
    }

    private static class CFService
    extends ICULocaleService {
        CFService() {
            super("Currency");
            class CurrencyFactory
            extends ICULocaleService.ICUResourceBundleFactory {
                final CFService this$0;

                CurrencyFactory(CFService cFService) {
                    this.this$0 = cFService;
                }

                @Override
                protected Object handleCreate(ULocale uLocale, int n, ICUService iCUService) {
                    return Currency.createCurrency(uLocale);
                }
            }
            this.registerFactory(new CurrencyFactory(this));
            this.markDefault();
        }
    }
}

