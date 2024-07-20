/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.config;

import org.apache.logging.log4j.core.config.AwaitCompletionReliabilityStrategy;
import org.apache.logging.log4j.core.config.AwaitUnconditionallyReliabilityStrategy;
import org.apache.logging.log4j.core.config.LockingReliabilityStrategy;
import org.apache.logging.log4j.core.config.LoggerConfig;
import org.apache.logging.log4j.core.config.ReliabilityStrategy;
import org.apache.logging.log4j.status.StatusLogger;
import org.apache.logging.log4j.util.LoaderUtil;
import org.apache.logging.log4j.util.PropertiesUtil;

public final class ReliabilityStrategyFactory {
    private ReliabilityStrategyFactory() {
    }

    public static ReliabilityStrategy getReliabilityStrategy(LoggerConfig loggerConfig) {
        String strategy = PropertiesUtil.getProperties().getStringProperty("log4j.ReliabilityStrategy", "AwaitCompletion");
        if ("AwaitCompletion".equals(strategy)) {
            return new AwaitCompletionReliabilityStrategy(loggerConfig);
        }
        if ("AwaitUnconditionally".equals(strategy)) {
            return new AwaitUnconditionallyReliabilityStrategy(loggerConfig);
        }
        if ("Locking".equals(strategy)) {
            return new LockingReliabilityStrategy(loggerConfig);
        }
        try {
            Class<ReliabilityStrategy> cls = LoaderUtil.loadClass(strategy).asSubclass(ReliabilityStrategy.class);
            return cls.getConstructor(LoggerConfig.class).newInstance(loggerConfig);
        } catch (Exception dynamicFailed) {
            StatusLogger.getLogger().warn("Could not create ReliabilityStrategy for '{}', using default AwaitCompletionReliabilityStrategy: {}", (Object)strategy, (Object)dynamicFailed);
            return new AwaitCompletionReliabilityStrategy(loggerConfig);
        }
    }
}

