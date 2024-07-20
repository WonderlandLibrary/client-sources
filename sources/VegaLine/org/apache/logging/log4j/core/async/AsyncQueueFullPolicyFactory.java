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
        String router = PropertiesUtil.getProperties().getStringProperty(PROPERTY_NAME_ASYNC_EVENT_ROUTER);
        if (router == null || PROPERTY_VALUE_DEFAULT_ASYNC_EVENT_ROUTER.equals(router) || DefaultAsyncQueueFullPolicy.class.getSimpleName().equals(router) || DefaultAsyncQueueFullPolicy.class.getName().equals(router)) {
            return new DefaultAsyncQueueFullPolicy();
        }
        if (PROPERTY_VALUE_DISCARDING_ASYNC_EVENT_ROUTER.equals(router) || DiscardingAsyncQueueFullPolicy.class.getSimpleName().equals(router) || DiscardingAsyncQueueFullPolicy.class.getName().equals(router)) {
            return AsyncQueueFullPolicyFactory.createDiscardingAsyncQueueFullPolicy();
        }
        return AsyncQueueFullPolicyFactory.createCustomRouter(router);
    }

    private static AsyncQueueFullPolicy createCustomRouter(String router) {
        try {
            Class<AsyncQueueFullPolicy> cls = LoaderUtil.loadClass(router).asSubclass(AsyncQueueFullPolicy.class);
            LOGGER.debug("Creating custom AsyncQueueFullPolicy '{}'", (Object)router);
            return cls.newInstance();
        } catch (Exception ex) {
            LOGGER.debug("Using DefaultAsyncQueueFullPolicy. Could not create custom AsyncQueueFullPolicy '{}': {}", (Object)router, (Object)ex.toString());
            return new DefaultAsyncQueueFullPolicy();
        }
    }

    private static AsyncQueueFullPolicy createDiscardingAsyncQueueFullPolicy() {
        PropertiesUtil util = PropertiesUtil.getProperties();
        String level = util.getStringProperty(PROPERTY_NAME_DISCARDING_THRESHOLD_LEVEL, Level.INFO.name());
        Level thresholdLevel = Level.toLevel(level, Level.INFO);
        LOGGER.debug("Creating custom DiscardingAsyncQueueFullPolicy(discardThreshold:{})", (Object)thresholdLevel);
        return new DiscardingAsyncQueueFullPolicy(thresholdLevel);
    }
}

