/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package joptsimple.util;

import java.util.Locale;
import java.util.regex.Pattern;
import joptsimple.ValueConversionException;
import joptsimple.ValueConverter;
import joptsimple.internal.Messages;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class RegexMatcher
implements ValueConverter<String> {
    private final Pattern pattern;

    public RegexMatcher(String string, int n) {
        this.pattern = Pattern.compile(string, n);
    }

    public static ValueConverter<String> regex(String string) {
        return new RegexMatcher(string, 0);
    }

    @Override
    public String convert(String string) {
        if (!this.pattern.matcher(string).matches()) {
            this.raiseValueConversionFailure(string);
        }
        return string;
    }

    @Override
    public Class<String> valueType() {
        return String.class;
    }

    @Override
    public String valuePattern() {
        return this.pattern.pattern();
    }

    private void raiseValueConversionFailure(String string) {
        String string2 = Messages.message(Locale.getDefault(), "joptsimple.ExceptionMessages", RegexMatcher.class, "message", string, this.pattern.pattern());
        throw new ValueConversionException(string2);
    }

    @Override
    public Object convert(String string) {
        return this.convert(string);
    }
}

