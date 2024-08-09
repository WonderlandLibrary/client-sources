/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.appender.rewrite;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.rewrite.RewritePolicy;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.impl.Log4jLogEvent;
import org.apache.logging.log4j.core.util.KeyValuePair;

@Plugin(name="LoggerNameLevelRewritePolicy", category="Core", elementType="rewritePolicy", printObject=true)
public class LoggerNameLevelRewritePolicy
implements RewritePolicy {
    private final String loggerName;
    private final Map<Level, Level> map;

    @PluginFactory
    public static LoggerNameLevelRewritePolicy createPolicy(@PluginAttribute(value="logger") String string, @PluginElement(value="KeyValuePair") KeyValuePair[] keyValuePairArray) {
        HashMap<Level, Level> hashMap = new HashMap<Level, Level>(keyValuePairArray.length);
        for (KeyValuePair keyValuePair : keyValuePairArray) {
            hashMap.put(LoggerNameLevelRewritePolicy.getLevel(keyValuePair.getKey()), LoggerNameLevelRewritePolicy.getLevel(keyValuePair.getValue()));
        }
        return new LoggerNameLevelRewritePolicy(string, hashMap);
    }

    private static Level getLevel(String string) {
        return Level.getLevel(string.toUpperCase(Locale.ROOT));
    }

    private LoggerNameLevelRewritePolicy(String string, Map<Level, Level> map) {
        this.loggerName = string;
        this.map = map;
    }

    @Override
    public LogEvent rewrite(LogEvent logEvent) {
        if (!logEvent.getLoggerName().startsWith(this.loggerName)) {
            return logEvent;
        }
        Level level = logEvent.getLevel();
        Level level2 = this.map.get(level);
        if (level2 == null || level2 == level) {
            return logEvent;
        }
        Log4jLogEvent log4jLogEvent = new Log4jLogEvent.Builder(logEvent).setLevel(level2).build();
        return log4jLogEvent;
    }
}

