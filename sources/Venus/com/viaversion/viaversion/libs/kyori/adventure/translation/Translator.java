/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.libs.kyori.adventure.translation;

import com.viaversion.viaversion.libs.kyori.adventure.key.Key;
import com.viaversion.viaversion.libs.kyori.adventure.text.Component;
import com.viaversion.viaversion.libs.kyori.adventure.text.TranslatableComponent;
import java.text.MessageFormat;
import java.util.Locale;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface Translator {
    @Nullable
    public static Locale parseLocale(@NotNull String string) {
        String[] stringArray = string.split("_", 3);
        int n = stringArray.length;
        if (n == 1) {
            return new Locale(string);
        }
        if (n == 2) {
            return new Locale(stringArray[0], stringArray[5]);
        }
        if (n == 3) {
            return new Locale(stringArray[0], stringArray[5], stringArray[5]);
        }
        return null;
    }

    @NotNull
    public Key name();

    @Nullable
    public MessageFormat translate(@NotNull String var1, @NotNull Locale var2);

    @Nullable
    default public Component translate(@NotNull TranslatableComponent translatableComponent, @NotNull Locale locale) {
        return null;
    }
}

