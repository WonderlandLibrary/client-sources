/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.async;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.async.AsyncQueueFullPolicy;
import org.apache.logging.log4j.core.async.DefaultAsyncQueueFullPolicy;
import org.apache.logging.log4j.core.async.DiscardingAsyncQueueFullPolicy;
import org.apache.logging.log4j.status.StatusLogger;
import org.apache.logging.log4j.util.LoaderUtil;
import org.apache.logging.log4j.util.PropertiesUtil;

public class AsyncQueueFullPolicyFactory {
    static final String PROPERTY_NAME_ASYNC_EVENT_ROUTER = "log4j2.AsyncQueueFullPolicy";
    static final String PROPERTY_VALUE_DEFAULT_ASYNC_EVENT_ROUTER = "Default";
    static final String PROPERTY_VALUE_DISCARDING_ASYNC_EVENT_ROUTER = "Discard";
    static final String PROPERTY_NAME_DISCARDING_THRESHOLD_LEVEL = "log4j2.DiscardThreshold";
    private static final Logger LOGGER = StatusLogger.getLogger();

    public static AsyncQueueFullPolicy create() {
        String string = PropertiesUtil.getProperties().getStringProperty(PROPERTY_NAME_ASYNC_EVENT_ROUTER);
        if (string == null || PROPERTY_VALUE_DEFAULT_ASYNC_EVENT_ROUTER.equals(string) || DefaultAsyncQueueFullPolicy.class.getSimpleName().equals(string) || DefaultAsyncQueueFullPolicy.class.getName().equals(string)) {
            return new DefaultAsyncQueueFullPolicy();
        }
        if (PROPERTY_VALUE_DISCARDING_ASYNC_EVENT_ROUTER.equals(string) || DiscardingAsyncQueueFullPolicy.class.getSimpleName().equals(string) || DiscardingAsyncQueueFullPolicy.class.getName().equals(string)) {
            return AsyncQueueFullPolicyFactory.createDiscardingAsyncQueueFullPolicy();
        }
        return AsyncQueueFullPolicyFactory.createCustomRouter(string);
    }

    private static AsyncQueueFullPolicy createCustomRouter(String string) {
        try {
            Class<AsyncQueueFullPolicy> clazz = LoaderUtil.loadClass(string).asSubclass(AsyncQueueFullPolicy.class);
            LOGGER.debug("Creating custom AsyncQueueFullPolicy '{}'", (Object)string);
            return clazz.newInstance();
        } catch (Exception exception) {
            LOGGER.debug("Using DefaultAsyncQueueFullPolicy. Could not create custom AsyncQueueFullPolicy '{}': {}", (Object)string, (Object)exception.toString());
            return new DefaultAsyncQueueFullPolicy();
        }
    }

    private static AsyncQueueFullPolicy createDiscardingAsyncQueueFullPolicy() {
        PropertiesUtil propertiesUtil = PropertiesUtil.getProperties();
        String string = propertiesUtil.getStringProperty(PROPERTY_NAME_DISCARDING_THRESHOLD_LEVEL, Level.INFO.name());
        Level level = Level.toLevel(string, Level.INFO);
        LOGGER.debug("Creating custom DiscardingAsyncQueueFullPolicy(discardThreshold:{})", (Object)level);
        return new DiscardingAsyncQueueFullPolicy(level);
    }
}

