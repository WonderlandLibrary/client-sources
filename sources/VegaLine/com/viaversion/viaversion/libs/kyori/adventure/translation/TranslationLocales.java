/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Nullable
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

    static {
        @Nullable String property = AdventureProperties.DEFAULT_TRANSLATION_LOCALE.value();
        if (property == null || property.isEmpty()) {
            GLOBAL = () -> Locale.US;
        } else if (property.equals("system")) {
            GLOBAL = Locale::getDefault;
        } else {
            Locale locale = Translator.parseLocale(property);
            GLOBAL = () -> locale;
        }
    }
}

