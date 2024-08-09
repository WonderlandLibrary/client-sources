/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.config.plugins.validation;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import org.apache.logging.log4j.core.config.plugins.validation.Constraint;
import org.apache.logging.log4j.core.config.plugins.validation.ConstraintValidator;
import org.apache.logging.log4j.core.util.ReflectionUtil;

public final class ConstraintValidators {
    private ConstraintValidators() {
    }

    public static Collection<ConstraintValidator<?>> findValidators(Annotation ... annotationArray) {
        ArrayList arrayList = new ArrayList();
        for (Annotation annotation : annotationArray) {
            ConstraintValidator<? extends Annotation> constraintValidator;
            Class<? extends Annotation> clazz = annotation.annotationType();
            if (!clazz.isAnnotationPresent(Constraint.class) || (constraintValidator = ConstraintValidators.getValidator(annotation, clazz)) == null) continue;
            arrayList.add(constraintValidator);
        }
        return arrayList;
    }

    private static <A extends Annotation> ConstraintValidator<A> getValidator(A a, Class<? extends A> clazz) {
        Constraint constraint = clazz.getAnnotation(Constraint.class);
        Class<? extends ConstraintValidator<? extends Annotation>> clazz2 = constraint.value();
        if (clazz.equals(ConstraintValidators.getConstraintValidatorAnnotationType(clazz2))) {
            ConstraintValidator<? extends Annotation> constraintValidator = ReflectionUtil.instantiate(clazz2);
            constraintValidator.initialize(a);
            return constraintValidator;
        }
        return null;
    }

    private static Type getConstraintValidatorAnnotationType(Class<? extends ConstraintValidator<?>> clazz) {
        for (Type type : clazz.getGenericInterfaces()) {
            ParameterizedType parameterizedType;
            if (!(type instanceof ParameterizedType) || !ConstraintValidator.class.equals((Object)(parameterizedType = (ParameterizedType)type).getRawType())) continue;
            return parameterizedType.getActualTypeArguments()[0];
        }
        return Void.TYPE;
    }
}

