/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.util;

import org.apache.logging.log4j.core.util.CachedClock;
import org.apache.logging.log4j.core.util.Clock;
import org.apache.logging.log4j.core.util.CoarseCachedClock;
import org.apache.logging.log4j.core.util.Loader;
import org.apache.logging.log4j.core.util.SystemClock;
import org.apache.logging.log4j.status.StatusLogger;
import org.apache.logging.log4j.util.PropertiesUtil;

public final class ClockFactory {
    public static final String PROPERTY_NAME = "log4j.Clock";
    private static final StatusLogger LOGGER = StatusLogger.getLogger();

    private ClockFactory() {
    }

    public static Clock getClock() {
        return ClockFactory.createClock();
    }

    private static Clock createClock() {
        String userRequest = PropertiesUtil.getProperties().getStringProperty(PROPERTY_NAME);
        if (userRequest == null || "SystemClock".equals(userRequest)) {
            LOGGER.trace("Using default SystemClock for timestamps.");
            return new SystemClock();
        }
        if (CachedClock.class.getName().equals(userRequest) || "CachedClock".equals(userRequest)) {
            LOGGER.trace("Using specified CachedClock for timestamps.");
            return CachedClock.instance();
        }
        if (CoarseCachedClock.class.getName().equals(userRequest) || "CoarseCachedClock".equals(userRequest)) {
            LOGGER.trace("Using specified CoarseCachedClock for timestamps.");
            return CoarseCachedClock.instance();
        }
        try {
            Clock result = Loader.newCheckedInstanceOf(userRequest, Clock.class);
            LOGGER.trace("Using {} for timestamps.", (Object)result.getClass().getName());
            return result;
        } catch (Exception e) {
            String fmt = "Could not create {}: {}, using default SystemClock for timestamps.";
            LOGGER.error("Could not create {}: {}, using default SystemClock for timestamps.", (Object)userRequest, (Object)e);
            return new SystemClock();
        }
    }
}

