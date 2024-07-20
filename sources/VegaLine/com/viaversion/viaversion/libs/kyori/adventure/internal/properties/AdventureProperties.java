/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.ApiStatus$Internal
 *  org.jetbrains.annotations.ApiStatus$NonExtendable
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package com.viaversion.viaversion.libs.kyori.adventure.internal.properties;

import com.viaversion.viaversion.libs.kyori.adventure.internal.properties.AdventurePropertiesImpl;
import java.util.function.Function;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@ApiStatus.Internal
public final class AdventureProperties {
    public static final Property<Boolean> DEBUG = AdventureProperties.property("debug", Boolean::parseBoolean, false);
    public static final Property<String> DEFAULT_TRANSLATION_LOCALE = AdventureProperties.property("defaultTranslationLocale", Function.identity(), null);
    public static final Property<Boolean> SERVICE_LOAD_FAILURES_ARE_FATAL = AdventureProperties.property("serviceLoadFailuresAreFatal", Boolean::parseBoolean, Boolean.TRUE);
    public static final Property<Boolean> TEXT_WARN_WHEN_LEGACY_FORMATTING_DETECTED = AdventureProperties.property("text.warnWhenLegacyFormattingDetected", Boolean::parseBoolean, Boolean.FALSE);

    private AdventureProperties() {
    }

    @NotNull
    public static <T> Property<T> property(@NotNull String name, @NotNull Function<String, T> parser, @Nullable T defaultValue) {
        return AdventurePropertiesImpl.property(name, parser, defaultValue);
    }

    @ApiStatus.Internal
    @ApiStatus.NonExtendable
    public static interface Property<T> {
        @Nullable
        public T value();
    }
}

