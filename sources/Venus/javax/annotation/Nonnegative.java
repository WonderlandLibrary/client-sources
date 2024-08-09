/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package javax.annotation;

import java.lang.annotation.Annotation;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import javax.annotation.meta.TypeQualifier;
import javax.annotation.meta.TypeQualifierValidator;
import javax.annotation.meta.When;

@Documented
@TypeQualifier(applicableTo=Number.class)
@Retention(value=RetentionPolicy.RUNTIME)
public @interface Nonnegative {
    public When when() default When.ALWAYS;

    /*
     * This class specifies class file version 49.0 but uses Java 6 signatures.  Assumed Java 6.
     */
    public static class Checker
    implements TypeQualifierValidator<Nonnegative> {
        @Override
        public When forConstantValue(Nonnegative nonnegative, Object object) {
            boolean bl;
            if (!(object instanceof Number)) {
                return When.NEVER;
            }
            Number number = (Number)object;
            if (number instanceof Long) {
                bl = number.longValue() < 0L;
            } else if (number instanceof Double) {
                bl = number.doubleValue() < 0.0;
            } else if (number instanceof Float) {
                bl = number.floatValue() < 0.0f;
            } else {
                boolean bl2 = bl = number.intValue() < 0;
            }
            if (bl) {
                return When.NEVER;
            }
            return When.ALWAYS;
        }

        @Override
        public When forConstantValue(Annotation annotation, Object object) {
            return this.forConstantValue((Nonnegative)annotation, object);
        }
    }
}

