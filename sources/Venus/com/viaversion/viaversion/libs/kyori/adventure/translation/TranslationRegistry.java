/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.libs.kyori.adventure.translation;

import com.viaversion.viaversion.libs.kyori.adventure.key.Key;
import com.viaversion.viaversion.libs.kyori.adventure.translation.TranslationRegistryImpl;
import com.viaversion.viaversion.libs.kyori.adventure.translation.Translator;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.MessageFormat;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.function.Function;
import java.util.regex.Pattern;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface TranslationRegistry
extends Translator {
    public static final Pattern SINGLE_QUOTE_PATTERN = Pattern.compile("'");

    @NotNull
    public static TranslationRegistry create(Key key) {
        return new TranslationRegistryImpl(Objects.requireNonNull(key, "name"));
    }

    public boolean contains(@NotNull String var1);

    @Override
    @Nullable
    public MessageFormat translate(@NotNull String var1, @NotNull Locale var2);

    public void defaultLocale(@NotNull Locale var1);

    public void register(@NotNull String var1, @NotNull Locale var2, @NotNull MessageFormat var3);

    default public void registerAll(@NotNull Locale locale, @NotNull Map<String, MessageFormat> map) {
        this.registerAll(locale, map.keySet(), map::get);
    }

    default public void registerAll(@NotNull Locale locale, @NotNull Path path, boolean bl) {
        try (BufferedReader bufferedReader = Files.newBufferedReader(path, StandardCharsets.UTF_8);){
            this.registerAll(locale, new PropertyResourceBundle(bufferedReader), bl);
        } catch (IOException iOException) {
            // empty catch block
        }
    }

    default public void registerAll(@NotNull Locale locale, @NotNull ResourceBundle resourceBundle, boolean bl) {
        this.registerAll(locale, resourceBundle.keySet(), arg_0 -> TranslationRegistry.lambda$registerAll$0(resourceBundle, bl, locale, arg_0));
    }

    default public void registerAll(@NotNull Locale locale, @NotNull Set<String> set, Function<String, MessageFormat> function) {
        IllegalArgumentException illegalArgumentException = null;
        int n = 0;
        for (String string : set) {
            try {
                this.register(string, locale, function.apply(string));
            } catch (IllegalArgumentException illegalArgumentException2) {
                if (illegalArgumentException == null) {
                    illegalArgumentException = illegalArgumentException2;
                }
                ++n;
            }
        }
        if (illegalArgumentException != null) {
            if (n == 1) {
                throw illegalArgumentException;
            }
            if (n > 1) {
                throw new IllegalArgumentException(String.format("Invalid key (and %d more)", n - 1), illegalArgumentException);
            }
        }
    }

    public void unregister(@NotNull String var1);

    private static MessageFormat lambda$registerAll$0(ResourceBundle resourceBundle, boolean bl, Locale locale, String string) {
        String string2 = resourceBundle.getString(string);
        return new MessageFormat(bl ? SINGLE_QUOTE_PATTERN.matcher(string2).replaceAll("''") : string2, locale);
    }
}

