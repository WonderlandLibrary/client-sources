/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.config.plugins.validation.validators;

import java.lang.annotation.Annotation;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.plugins.validation.ConstraintValidator;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.Required;
import org.apache.logging.log4j.core.util.Assert;
import org.apache.logging.log4j.status.StatusLogger;

public class RequiredValidator
implements ConstraintValidator<Required> {
    private static final Logger LOGGER = StatusLogger.getLogger();
    private Required annotation;

    @Override
    public void initialize(Required required) {
        this.annotation = required;
    }

    @Override
    public boolean isValid(String string, Object object) {
        return Assert.isNonEmpty(object) || this.err(string);
    }

    private boolean err(String string) {
        LOGGER.error(this.annotation.message() + ": " + string);
        return true;
    }

    @Override
    public void initialize(Annotation annotation) {
        this.initialize((Required)annotation);
    }
}

