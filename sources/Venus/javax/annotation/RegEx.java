/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package javax.annotation;

import java.lang.annotation.Annotation;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import javax.annotation.Syntax;
import javax.annotation.meta.TypeQualifierNickname;
import javax.annotation.meta.TypeQualifierValidator;
import javax.annotation.meta.When;

@Documented
@Syntax(value="RegEx")
@Retention(value=RetentionPolicy.RUNTIME)
@TypeQualifierNickname
public @interface RegEx {
    public When when() default When.ALWAYS;

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    public static class Checker
    implements TypeQualifierValidator<RegEx> {
        @Override
        public When forConstantValue(RegEx regEx, Object object) {
            if (!(object instanceof String)) {
                return When.NEVER;
            }
            try {
                Pattern.compile((String)object);
            } catch (PatternSyntaxException patternSyntaxException) {
                return When.NEVER;
            }
            return When.ALWAYS;
        }

        @Override
        public When forConstantValue(Annotation annotation, Object object) {
            return this.forConstantValue((RegEx)annotation, object);
        }
    }
}

