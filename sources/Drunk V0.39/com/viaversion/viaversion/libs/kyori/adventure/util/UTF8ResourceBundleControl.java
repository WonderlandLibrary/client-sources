/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.libs.kyori.adventure.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.security.AccessController;
import java.security.PrivilegedActionException;
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
    public ResourceBundle newBundle(String baseName, Locale locale, String format, ClassLoader loader, boolean reload) throws IllegalAccessException, InstantiationException, IOException {
        InputStream is;
        if (!format.equals("java.properties")) return super.newBundle(baseName, locale, format, loader, reload);
        String bundle = this.toBundleName(baseName, locale);
        String resource = this.toResourceName(bundle, "properties");
        try {
            is = AccessController.doPrivileged(() -> {
                if (!reload) return loader.getResourceAsStream(resource);
                URL url = loader.getResource(resource);
                if (url == null) return null;
                URLConnection connection = url.openConnection();
                if (connection == null) return null;
                connection.setUseCaches(false);
                return connection.getInputStream();
            });
        }
        catch (PrivilegedActionException e) {
            throw (IOException)e.getException();
        }
        if (is == null) return null;
        try (InputStreamReader isr = new InputStreamReader(is, StandardCharsets.UTF_8);){
            PropertyResourceBundle propertyResourceBundle = new PropertyResourceBundle(isr);
            return propertyResourceBundle;
        }
    }
}

