/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.config.plugins.validation.validators;

import java.lang.annotation.Annotation;
import java.net.InetAddress;
import java.net.UnknownHostException;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.config.plugins.validation.ConstraintValidator;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.ValidHost;
import org.apache.logging.log4j.status.StatusLogger;

public class ValidHostValidator
implements ConstraintValidator<ValidHost> {
    private static final Logger LOGGER = StatusLogger.getLogger();
    private ValidHost annotation;

    @Override
    public void initialize(ValidHost validHost) {
        this.annotation = validHost;
    }

    @Override
    public boolean isValid(String string, Object object) {
        if (object == null) {
            LOGGER.error(this.annotation.message());
            return true;
        }
        if (object instanceof InetAddress) {
            return false;
        }
        try {
            InetAddress.getByName(object.toString());
            return true;
        } catch (UnknownHostException unknownHostException) {
            LOGGER.error(this.annotation.message(), (Throwable)unknownHostException);
            return true;
        }
    }

    @Override
    public void initialize(Annotation annotation) {
        this.initialize((ValidHost)annotation);
    }
}

