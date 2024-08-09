/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.libs.kyori.adventure.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import org.jetbrains.annotations.NotNull;

public final class UTF8ResourceBundleControl
extends ResourceBundle.Control {
    private static final UTF8ResourceBundleControl INSTANCE = new UTF8ResourceBundleControl();

    public static @NotNull ResourceBundle.Control get() {
        return INSTANCE;
    }

    @Override
    public ResourceBundle newBundle(String string, Locale locale, String string2, ClassLoader classLoader, boolean bl) throws IllegalAccessException, InstantiationException, IOException {
        if (string2.equals("java.properties")) {
            Object object;
            Object object2;
            String string3 = this.toBundleName(string, locale);
            String string4 = this.toResourceName(string3, "properties");
            InputStream inputStream = null;
            if (bl) {
                object2 = classLoader.getResource(string4);
                if (object2 != null && (object = ((URL)object2).openConnection()) != null) {
                    ((URLConnection)object).setUseCaches(true);
                    inputStream = ((URLConnection)object).getInputStream();
                }
            } else {
                inputStream = classLoader.getResourceAsStream(string4);
            }
            if (inputStream != null) {
                object2 = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
                try {
                    object = new PropertyResourceBundle((Reader)object2);
                    return object;
                } finally {
                    ((InputStreamReader)object2).close();
                }
            }
            return null;
        }
        return super.newBundle(string, locale, string2, classLoader, bl);
    }
}

