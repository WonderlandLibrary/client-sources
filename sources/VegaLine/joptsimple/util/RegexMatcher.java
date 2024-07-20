/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package joptsimple.util;

import java.util.Locale;
import java.util.regex.Pattern;
import joptsimple.ValueConversionException;
import joptsimple.ValueConverter;
import joptsimple.internal.Messages;

public class RegexMatcher
implements ValueConverter<String> {
    private final Pattern pattern;

    public RegexMatcher(String pattern, int flags) {
        this.pattern = Pattern.compile(pattern, flags);
    }

    public static ValueConverter<String> regex(String pattern) {
        return new RegexMatcher(pattern, 0);
    }

    @Override
    public String convert(String value) {
        if (!this.pattern.matcher(value).matches()) {
            this.raiseValueConversionFailure(value);
        }
        return value;
    }

    @Override
    public Class<String> valueType() {
        return String.class;
    }

    @Override
    public String valuePattern() {
        return this.pattern.pattern();
    }

    private void raiseValueConversionFailure(String value) {
        String message = Messages.message(Locale.getDefault(), "joptsimple.ExceptionMessages", RegexMatcher.class, "message", value, this.pattern.pattern());
        throw new ValueConversionException(message);
    }
}

