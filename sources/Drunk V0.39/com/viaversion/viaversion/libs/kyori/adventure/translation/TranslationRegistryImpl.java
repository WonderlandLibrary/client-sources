/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.libs.kyori.adventure.translation;

import com.viaversion.viaversion.libs.kyori.adventure.key.Key;
import com.viaversion.viaversion.libs.kyori.adventure.translation.TranslationLocales;
import com.viaversion.viaversion.libs.kyori.adventure.translation.TranslationRegistry;
import com.viaversion.viaversion.libs.kyori.examination.Examinable;
import com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty;
import com.viaversion.viaversion.libs.kyori.examination.string.StringExaminer;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

final class TranslationRegistryImpl
implements Examinable,
TranslationRegistry {
    private final Key name;
    private final Map<String, Translation> translations = new ConcurrentHashMap<String, Translation>();
    private Locale defaultLocale = Locale.US;

    TranslationRegistryImpl(Key name) {
        this.name = name;
    }

    @Override
    public void register(@NotNull String key, @NotNull Locale locale, @NotNull MessageFormat format) {
        this.translations.computeIfAbsent(key, x$0 -> new Translation((String)x$0)).register(locale, format);
    }

    @Override
    public void unregister(@NotNull String key) {
        this.translations.remove(key);
    }

    @Override
    @NotNull
    public Key name() {
        return this.name;
    }

    @Override
    public boolean contains(@NotNull String key) {
        return this.translations.containsKey(key);
    }

    @Override
    @Nullable
    public MessageFormat translate(@NotNull String key, @NotNull Locale locale) {
        Translation translation = this.translations.get(key);
        if (translation != null) return translation.translate(locale);
        return null;
    }

    @Override
    public void defaultLocale(@NotNull Locale defaultLocale) {
        this.defaultLocale = Objects.requireNonNull(defaultLocale, "defaultLocale");
    }

    @Override
    @NotNull
    public Stream<? extends ExaminableProperty> examinableProperties() {
        return Stream.of(ExaminableProperty.of("translations", this.translations));
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof TranslationRegistryImpl)) {
            return false;
        }
        TranslationRegistryImpl that = (TranslationRegistryImpl)other;
        if (!this.name.equals(that.name)) return false;
        if (!this.translations.equals(that.translations)) return false;
        if (!this.defaultLocale.equals(that.defaultLocale)) return false;
        return true;
    }

    public int hashCode() {
        return Objects.hash(this.name, this.translations, this.defaultLocale);
    }

    public String toString() {
        return this.examine(StringExaminer.simpleEscaping());
    }

    final class Translation
    implements Examinable {
        private final String key;
        private final Map<Locale, MessageFormat> formats;

        Translation(String key) {
            this.key = Objects.requireNonNull(key, "translation key");
            this.formats = new ConcurrentHashMap<Locale, MessageFormat>();
        }

        void register(@NotNull Locale locale, @NotNull MessageFormat format) {
            if (this.formats.putIfAbsent(Objects.requireNonNull(locale, "locale"), Objects.requireNonNull(format, "message format")) == null) return;
            throw new IllegalArgumentException(String.format("Translation already exists: %s for %s", this.key, locale));
        }

        @Nullable
        MessageFormat translate(@NotNull Locale locale) {
            MessageFormat format = this.formats.get(Objects.requireNonNull(locale, "locale"));
            if (format != null) return format;
            format = this.formats.get(new Locale(locale.getLanguage()));
            if (format != null) return format;
            format = this.formats.get(TranslationRegistryImpl.this.defaultLocale);
            if (format != null) return format;
            return this.formats.get(TranslationLocales.global());
        }

        @Override
        @NotNull
        public Stream<? extends ExaminableProperty> examinableProperties() {
            return Stream.of(ExaminableProperty.of("key", this.key), ExaminableProperty.of("formats", this.formats));
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof Translation)) {
                return false;
            }
            Translation that = (Translation)other;
            if (!this.key.equals(that.key)) return false;
            if (!this.formats.equals(that.formats)) return false;
            return true;
        }

        public int hashCode() {
            return Objects.hash(this.key, this.formats);
        }

        public String toString() {
            return this.examine(StringExaminer.simpleEscaping());
        }
    }
}

