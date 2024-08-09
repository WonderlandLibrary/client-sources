/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.libs.kyori.adventure.translation;

import com.viaversion.viaversion.libs.kyori.adventure.internal.properties.AdventureProperties;
import com.viaversion.viaversion.libs.kyori.adventure.translation.Translator;
import java.util.Locale;
import java.util.function.Supplier;
import org.jetbrains.annotations.Nullable;

final class TranslationLocales {
    private static final Supplier<Locale> GLOBAL;

    private TranslationLocales() {
    }

    static Locale global() {
        return GLOBAL.get();
    }

    private static Locale lambda$static$1(Locale locale) {
        return locale;
    }

    private static Locale lambda$static$0() {
        return Locale.US;
    }

    static {
        @Nullable String string = AdventureProperties.DEFAULT_TRANSLATION_LOCALE.value();
        if (string == null || string.isEmpty()) {
            GLOBAL = TranslationLocales::lambda$static$0;
        } else if (string.equals("system")) {
            GLOBAL = Locale::getDefault;
        } else {
            Locale locale = Translator.parseLocale(string);
            GLOBAL = () -> TranslationLocales.lambda$static$1(locale);
        }
    }
}

