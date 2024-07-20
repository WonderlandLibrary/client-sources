/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package joptsimple.internal;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

public class Messages {
    private Messages() {
        throw new UnsupportedOperationException();
    }

    public static String message(Locale locale, String bundleName, Class<?> type2, String key, Object ... args) {
        ResourceBundle bundle = ResourceBundle.getBundle(bundleName, locale);
        String template = bundle.getString(type2.getName() + '.' + key);
        MessageFormat format = new MessageFormat(template);
        format.setLocale(locale);
        return format.format(args);
    }
}

