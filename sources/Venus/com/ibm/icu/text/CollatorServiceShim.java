/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.text;

import com.ibm.icu.impl.ICULocaleService;
import com.ibm.icu.impl.ICUResourceBundle;
import com.ibm.icu.impl.ICUService;
import com.ibm.icu.impl.coll.CollationLoader;
import com.ibm.icu.impl.coll.CollationTailoring;
import com.ibm.icu.text.Collator;
import com.ibm.icu.text.RuleBasedCollator;
import com.ibm.icu.util.ICUCloneNotSupportedException;
import com.ibm.icu.util.Output;
import com.ibm.icu.util.ULocale;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.Set;

final class CollatorServiceShim
extends Collator.ServiceShim {
    private static ICULocaleService service = new CService();

    CollatorServiceShim() {
    }

    @Override
    Collator getInstance(ULocale uLocale) {
        try {
            ULocale[] uLocaleArray = new ULocale[1];
            Collator collator = (Collator)service.get(uLocale, uLocaleArray);
            if (collator == null) {
                throw new MissingResourceException("Could not locate Collator data", "", "");
            }
            return (Collator)collator.clone();
        } catch (CloneNotSupportedException cloneNotSupportedException) {
            throw new ICUCloneNotSupportedException(cloneNotSupportedException);
        }
    }

    @Override
    Object registerInstance(Collator collator, ULocale uLocale) {
        collator.setLocale(uLocale, uLocale);
        return service.registerObject((Object)collator, uLocale);
    }

    @Override
    Object registerFactory(Collator.CollatorFactory collatorFactory) {
        class CFactory
        extends ICULocaleService.LocaleKeyFactory {
            Collator.CollatorFactory delegate;
            final CollatorServiceShim this$0;

            CFactory(CollatorServiceShim collatorServiceShim, Collator.CollatorFactory collatorFactory) {
                this.this$0 = collatorServiceShim;
                super(collatorFactory.visible());
                this.delegate = collatorFactory;
            }

            @Override
            public Object handleCreate(ULocale uLocale, int n, ICUService iCUService) {
                Collator collator = this.delegate.createCollator(uLocale);
                return collator;
            }

            @Override
            public String getDisplayName(String string, ULocale uLocale) {
                ULocale uLocale2 = new ULocale(string);
                return this.delegate.getDisplayName(uLocale2, uLocale);
            }

            @Override
            public Set<String> getSupportedIDs() {
                return this.delegate.getSupportedLocaleIDs();
            }
        }
        return service.registerFactory(new CFactory(this, collatorFactory));
    }

    @Override
    boolean unregister(Object object) {
        return service.unregisterFactory((ICUService.Factory)object);
    }

    @Override
    Locale[] getAvailableLocales() {
        Locale[] localeArray = service.isDefault() ? ICUResourceBundle.getAvailableLocales("com/ibm/icu/impl/data/icudt66b/coll", ICUResourceBundle.ICU_DATA_CLASS_LOADER) : service.getAvailableLocales();
        return localeArray;
    }

    @Override
    ULocale[] getAvailableULocales() {
        ULocale[] uLocaleArray = service.isDefault() ? ICUResourceBundle.getAvailableULocales("com/ibm/icu/impl/data/icudt66b/coll", ICUResourceBundle.ICU_DATA_CLASS_LOADER) : service.getAvailableULocales();
        return uLocaleArray;
    }

    @Override
    String getDisplayName(ULocale uLocale, ULocale uLocale2) {
        String string = uLocale.getName();
        return service.getDisplayName(string, uLocale2);
    }

    private static final Collator makeInstance(ULocale uLocale) {
        Output<ULocale> output = new Output<ULocale>(ULocale.ROOT);
        CollationTailoring collationTailoring = CollationLoader.loadTailoring(uLocale, output);
        return new RuleBasedCollator(collationTailoring, (ULocale)output.value);
    }

    static Collator access$000(ULocale uLocale) {
        return CollatorServiceShim.makeInstance(uLocale);
    }

    private static class CService
    extends ICULocaleService {
        CService() {
            super("Collator");
            class CollatorFactory
            extends ICULocaleService.ICUResourceBundleFactory {
                final CService this$0;

                CollatorFactory(CService cService) {
                    this.this$0 = cService;
                    super("com/ibm/icu/impl/data/icudt66b/coll");
                }

                @Override
                protected Object handleCreate(ULocale uLocale, int n, ICUService iCUService) {
                    return CollatorServiceShim.access$000(uLocale);
                }
            }
            this.registerFactory(new CollatorFactory(this));
            this.markDefault();
        }

        @Override
        public String validateFallbackLocale() {
            return "";
        }

        @Override
        protected Object handleDefault(ICUService.Key key, String[] stringArray) {
            if (stringArray != null) {
                stringArray[0] = "root";
            }
            try {
                return CollatorServiceShim.access$000(ULocale.ROOT);
            } catch (MissingResourceException missingResourceException) {
                return null;
            }
        }
    }
}

