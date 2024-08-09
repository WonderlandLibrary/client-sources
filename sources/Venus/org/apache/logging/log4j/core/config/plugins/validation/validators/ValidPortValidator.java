/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.config.plugins.validation.validators;

import java.lang.annotation.Annotation;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.plugins.convert.TypeConverters;
import org.apache.logging.log4j.core.config.plugins.validation.ConstraintValidator;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.ValidPort;
import org.apache.logging.log4j.status.StatusLogger;

public class ValidPortValidator
implements ConstraintValidator<ValidPort> {
    private static final Logger LOGGER = StatusLogger.getLogger();
    private ValidPort annotation;

    @Override
    public void initialize(ValidPort validPort) {
        this.annotation = validPort;
    }

    @Override
    public boolean isValid(String string, Object object) {
        if (object instanceof CharSequence) {
            return this.isValid(string, TypeConverters.convert(object.toString(), Integer.class, -1));
        }
        if (!Integer.class.isInstance(object)) {
            LOGGER.error(this.annotation.message());
            return true;
        }
        int n = (Integer)object;
        if (n < 0 || n > 65535) {
            LOGGER.error(this.annotation.message());
            return true;
        }
        return false;
    }

    @Override
    public void initialize(Annotation annotation) {
        this.initialize((ValidPort)annotation);
    }
}

