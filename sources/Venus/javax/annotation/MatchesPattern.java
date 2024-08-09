/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package javax.annotation;

import java.lang.annotation.Annotation;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.regex.Pattern;
import javax.annotation.RegEx;
import javax.annotation.meta.TypeQualifier;
import javax.annotation.meta.TypeQualifierValidator;
import javax.annotation.meta.When;

@Documented
@TypeQualifier(applicableTo=String.class)
@Retention(value=RetentionPolicy.RUNTIME)
public @interface MatchesPattern {
    @RegEx
    public String value();

    public int flags() default 0;

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    public static class Checker
    implements TypeQualifierValidator<MatchesPattern> {
        @Override
        public When forConstantValue(MatchesPattern matchesPattern, Object object) {
            Pattern pattern = Pattern.compile(matchesPattern.value(), matchesPattern.flags());
            if (pattern.matcher((String)object).matches()) {
                return When.ALWAYS;
            }
            return When.NEVER;
        }

        @Override
        public When forConstantValue(Annotation annotation, Object object) {
            return this.forConstantValue((MatchesPattern)annotation, object);
        }
    }
}

