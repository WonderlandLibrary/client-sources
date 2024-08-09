/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.libs.kyori.adventure.translation;

import com.viaversion.viaversion.libs.kyori.adventure.internal.Internals;
import com.viaversion.viaversion.libs.kyori.adventure.key.Key;
import com.viaversion.viaversion.libs.kyori.adventure.translation.TranslationLocales;
import com.viaversion.viaversion.libs.kyori.adventure.translation.TranslationRegistry;
import com.viaversion.viaversion.libs.kyori.examination.Examinable;
import com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty;
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

    TranslationRegistryImpl(Key key) {
        this.name = key;
    }

    @Override
    public void register(@NotNull String string, @NotNull Locale locale, @NotNull MessageFormat messageFormat) {
        this.translations.computeIfAbsent(string, this::lambda$register$0).register(locale, messageFormat);
    }

    @Override
    public void unregister(@NotNull String string) {
        this.translations.remove(string);
    }

    @Override
    @NotNull
    public Key name() {
        return this.name;
    }

    @Override
    public boolean contains(@NotNull String string) {
        return this.translations.containsKey(string);
    }

    @Override
    @Nullable
    public MessageFormat translate(@NotNull String string, @NotNull Locale locale) {
        Translation translation = this.translations.get(string);
        if (translation == null) {
            return null;
        }
        return translation.translate(locale);
    }

    @Override
    public void defaultLocale(@NotNull Locale locale) {
        this.defaultLocale = Objects.requireNonNull(locale, "defaultLocale");
    }

    @Override
    @NotNull
    public Stream<? extends ExaminableProperty> examinableProperties() {
        return Stream.of(ExaminableProperty.of("translations", this.translations));
    }

    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (!(object instanceof TranslationRegistryImpl)) {
            return true;
        }
        TranslationRegistryImpl translationRegistryImpl = (TranslationRegistryImpl)object;
        return this.name.equals(translationRegistryImpl.name) && this.translations.equals(translationRegistryImpl.translations) && this.defaultLocale.equals(translationRegistryImpl.defaultLocale);
    }

    public int hashCode() {
        return Objects.hash(this.name, this.translations, this.defaultLocale);
    }

    public String toString() {
        return Internals.toString(this);
    }

    private Translation lambda$register$0(String string) {
        return new Translation(this, string);
    }

    static Locale access$000(TranslationRegistryImpl translationRegistryImpl) {
        return translationRegistryImpl.defaultLocale;
    }

    final class Translation
    implements Examinable {
        private final String key;
        private final Map<Locale, MessageFormat> formats;
        final TranslationRegistryImpl this$0;

        Translation(@NotNull TranslationRegistryImpl translationRegistryImpl, String string) {
            this.this$0 = translationRegistryImpl;
            this.key = Objects.requireNonNull(string, "translation key");
            this.formats = new ConcurrentHashMap<Locale, MessageFormat>();
        }

        void register(@NotNull Locale locale, @NotNull MessageFormat messageFormat) {
            if (this.formats.putIfAbsent(Objects.requireNonNull(locale, "locale"), Objects.requireNonNull(messageFormat, "message format")) != null) {
                throw new IllegalArgumentException(String.format("Translation already exists: %s for %s", this.key, locale));
            }
        }

        @Nullable
        MessageFormat translate(@NotNull Locale locale) {
            MessageFormat messageFormat = this.formats.get(Objects.requireNonNull(locale, "locale"));
            if (messageFormat == null && (messageFormat = this.formats.get(new Locale(locale.getLanguage()))) == null && (messageFormat = this.formats.get(TranslationRegistryImpl.access$000(this.this$0))) == null) {
                messageFormat = this.formats.get(TranslationLocales.global());
            }
            return messageFormat;
        }

        @Override
        @NotNull
        public Stream<? extends ExaminableProperty> examinableProperties() {
            return Stream.of(ExaminableProperty.of("key", this.key), ExaminableProperty.of("formats", this.formats));
        }

        public boolean equals(Object object) {
            if (this == object) {
                return false;
            }
            if (!(object instanceof Translation)) {
                return true;
            }
            Translation translation = (Translation)object;
            return this.key.equals(translation.key) && this.formats.equals(translation.formats);
        }

        public int hashCode() {
            return Objects.hash(this.key, this.formats);
        }

        public String toString() {
            return Internals.toString(this);
        }
    }
}

