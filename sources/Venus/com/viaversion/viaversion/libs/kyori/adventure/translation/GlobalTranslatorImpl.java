/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.libs.kyori.adventure.translation;

import com.viaversion.viaversion.libs.kyori.adventure.key.Key;
import com.viaversion.viaversion.libs.kyori.adventure.text.Component;
import com.viaversion.viaversion.libs.kyori.adventure.text.TranslatableComponent;
import com.viaversion.viaversion.libs.kyori.adventure.text.renderer.TranslatableComponentRenderer;
import com.viaversion.viaversion.libs.kyori.adventure.translation.GlobalTranslator;
import com.viaversion.viaversion.libs.kyori.adventure.translation.Translator;
import com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty;
import java.text.MessageFormat;
import java.util.Collections;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

final class GlobalTranslatorImpl
implements GlobalTranslator {
    private static final Key NAME = Key.key("adventure", "global");
    static final GlobalTranslatorImpl INSTANCE = new GlobalTranslatorImpl();
    final TranslatableComponentRenderer<Locale> renderer = TranslatableComponentRenderer.usingTranslationSource(this);
    private final Set<Translator> sources = Collections.newSetFromMap(new ConcurrentHashMap());

    private GlobalTranslatorImpl() {
    }

    @Override
    @NotNull
    public Key name() {
        return NAME;
    }

    @Override
    @NotNull
    public Iterable<? extends Translator> sources() {
        return Collections.unmodifiableSet(this.sources);
    }

    @Override
    public boolean addSource(@NotNull Translator translator) {
        Objects.requireNonNull(translator, "source");
        if (translator == this) {
            throw new IllegalArgumentException("GlobalTranslationSource");
        }
        return this.sources.add(translator);
    }

    @Override
    public boolean removeSource(@NotNull Translator translator) {
        Objects.requireNonNull(translator, "source");
        return this.sources.remove(translator);
    }

    @Override
    @Nullable
    public MessageFormat translate(@NotNull String string, @NotNull Locale locale) {
        Objects.requireNonNull(string, "key");
        Objects.requireNonNull(locale, "locale");
        for (Translator translator : this.sources) {
            MessageFormat messageFormat = translator.translate(string, locale);
            if (messageFormat == null) continue;
            return messageFormat;
        }
        return null;
    }

    @Override
    @Nullable
    public Component translate(@NotNull TranslatableComponent translatableComponent, @NotNull Locale locale) {
        Objects.requireNonNull(translatableComponent, "component");
        Objects.requireNonNull(locale, "locale");
        for (Translator translator : this.sources) {
            Component component = translator.translate(translatableComponent, locale);
            if (component == null) continue;
            return component;
        }
        return null;
    }

    @Override
    @NotNull
    public Stream<? extends ExaminableProperty> examinableProperties() {
        return Stream.of(ExaminableProperty.of("sources", this.sources));
    }
}

