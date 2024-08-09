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

    public static String message(Locale locale, String string, Class<?> clazz, String string2, Object ... objectArray) {
        ResourceBundle resourceBundle = ResourceBundle.getBundle(string, locale);
        String string3 = resourceBundle.getString(clazz.getName() + '.' + string2);
        MessageFormat messageFormat = new MessageFormat(string3);
        messageFormat.setLocale(locale);
        return messageFormat.format(objectArray);
    }
}

