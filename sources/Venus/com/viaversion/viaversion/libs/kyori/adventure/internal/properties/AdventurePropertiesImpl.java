/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.VisibleForTesting
 */
package com.viaversion.viaversion.libs.kyori.adventure.internal.properties;

import com.viaversion.viaversion.libs.kyori.adventure.internal.properties.AdventureProperties;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.Properties;
import java.util.function.Function;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.VisibleForTesting;

final class AdventurePropertiesImpl {
    private static final String FILESYSTEM_DIRECTORY_NAME = "config";
    private static final String FILESYSTEM_FILE_NAME = "adventure.properties";
    private static final Properties PROPERTIES = new Properties();

    private static void print(Throwable throwable) {
        throwable.printStackTrace();
    }

    private AdventurePropertiesImpl() {
    }

    @VisibleForTesting
    @NotNull
    static String systemPropertyName(String string) {
        return String.join((CharSequence)".", "net", "kyori", "adventure", string);
    }

    static <T> @NotNull AdventureProperties.Property<T> property(@NotNull String string, @NotNull Function<String, T> function, @Nullable T t) {
        return new PropertyImpl<T>(string, function, t);
    }

    private static Path lambda$static$1() {
        return Paths.get(FILESYSTEM_DIRECTORY_NAME, FILESYSTEM_FILE_NAME);
    }

    private static Path lambda$static$0(String string) {
        return Paths.get(string, new String[0]);
    }

    static Properties access$000() {
        return PROPERTIES;
    }

    static {
        Path path = Optional.ofNullable(System.getProperty(AdventurePropertiesImpl.systemPropertyName(FILESYSTEM_DIRECTORY_NAME))).map(AdventurePropertiesImpl::lambda$static$0).orElseGet(AdventurePropertiesImpl::lambda$static$1);
        if (Files.isRegularFile(path, new LinkOption[0])) {
            try (InputStream inputStream = Files.newInputStream(path, new OpenOption[0]);){
                PROPERTIES.load(inputStream);
            } catch (IOException iOException) {
                AdventurePropertiesImpl.print(iOException);
            }
        }
    }

    private static final class PropertyImpl<T>
    implements AdventureProperties.Property<T> {
        private final String name;
        private final Function<String, T> parser;
        @Nullable
        private final T defaultValue;
        private boolean valueCalculated;
        @Nullable
        private T value;

        PropertyImpl(@NotNull String string, @NotNull Function<String, T> function, @Nullable T t) {
            this.name = string;
            this.parser = function;
            this.defaultValue = t;
        }

        @Override
        @Nullable
        public T value() {
            if (!this.valueCalculated) {
                String string = AdventurePropertiesImpl.systemPropertyName(this.name);
                String string2 = System.getProperty(string, AdventurePropertiesImpl.access$000().getProperty(this.name));
                if (string2 != null) {
                    this.value = this.parser.apply(string2);
                }
                if (this.value == null) {
                    this.value = this.defaultValue;
                }
                this.valueCalculated = true;
            }
            return this.value;
        }

        public boolean equals(@Nullable Object object) {
            return this == object;
        }

        public int hashCode() {
            return this.name.hashCode();
        }
    }
}

