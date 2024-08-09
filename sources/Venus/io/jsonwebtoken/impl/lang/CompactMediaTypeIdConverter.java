/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl.lang;

import io.jsonwebtoken.impl.lang.Converter;
import io.jsonwebtoken.lang.Assert;
import io.jsonwebtoken.lang.Strings;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class CompactMediaTypeIdConverter
implements Converter<String, Object> {
    public static final Converter<String, Object> INSTANCE = new CompactMediaTypeIdConverter();
    private static final char FORWARD_SLASH = '/';
    private static final String APP_MEDIA_TYPE_PREFIX = "application/";

    static String compactIfPossible(String string) {
        Assert.hasText(string, "Value cannot be null or empty.");
        if (Strings.startsWithIgnoreCase(string, APP_MEDIA_TYPE_PREFIX)) {
            for (int i = string.length() - 1; i >= 12; --i) {
                char c = string.charAt(i);
                if (c != '/') continue;
                return string;
            }
            return string.substring(12);
        }
        return string;
    }

    @Override
    public Object applyTo(String string) {
        return CompactMediaTypeIdConverter.compactIfPossible(string);
    }

    @Override
    public String applyFrom(Object object) {
        Assert.notNull(object, "Value cannot be null.");
        String string = Assert.isInstanceOf(String.class, object, "Value must be a string.");
        if (string.indexOf(47) < 0) {
            string = APP_MEDIA_TYPE_PREFIX + string;
        }
        return string;
    }

    @Override
    public Object applyFrom(Object object) {
        return this.applyFrom(object);
    }

    @Override
    public Object applyTo(Object object) {
        return this.applyTo((String)object);
    }
}

