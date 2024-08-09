/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.appender.rewrite;

import java.util.HashMap;
import java.util.Map;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.rewrite.RewritePolicy;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.impl.Log4jLogEvent;
import org.apache.logging.log4j.core.util.KeyValuePair;
import org.apache.logging.log4j.message.MapMessage;
import org.apache.logging.log4j.message.Message;
import org.apache.logging.log4j.status.StatusLogger;

@Plugin(name="MapRewritePolicy", category="Core", elementType="rewritePolicy", printObject=true)
public final class MapRewritePolicy
implements RewritePolicy {
    protected static final Logger LOGGER = StatusLogger.getLogger();
    private final Map<String, String> map;
    private final Mode mode;

    private MapRewritePolicy(Map<String, String> map, Mode mode) {
        this.map = map;
        this.mode = mode;
    }

    @Override
    public LogEvent rewrite(LogEvent logEvent) {
        Map.Entry<String, String> entry2;
        Message message = logEvent.getMessage();
        if (message == null || !(message instanceof MapMessage)) {
            return logEvent;
        }
        HashMap<String, String> hashMap = new HashMap<String, String>(((MapMessage)message).getData());
        switch (1.$SwitchMap$org$apache$logging$log4j$core$appender$rewrite$MapRewritePolicy$Mode[this.mode.ordinal()]) {
            case 1: {
                hashMap.putAll(this.map);
                break;
            }
            default: {
                for (Map.Entry<String, String> entry2 : this.map.entrySet()) {
                    if (!hashMap.containsKey(entry2.getKey())) continue;
                    hashMap.put((String)entry2.getKey(), (String)entry2.getValue());
                }
            }
        }
        MapMessage mapMessage = ((MapMessage)message).newInstance(hashMap);
        entry2 = new Log4jLogEvent.Builder(logEvent).setMessage(mapMessage).build();
        return entry2;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("mode=").append((Object)this.mode);
        stringBuilder.append(" {");
        boolean bl = true;
        for (Map.Entry<String, String> entry : this.map.entrySet()) {
            if (!bl) {
                stringBuilder.append(", ");
            }
            stringBuilder.append(entry.getKey()).append('=').append(entry.getValue());
            bl = false;
        }
        stringBuilder.append('}');
        return stringBuilder.toString();
    }

    @PluginFactory
    public static MapRewritePolicy createPolicy(@PluginAttribute(value="mode") String string, @PluginElement(value="KeyValuePair") KeyValuePair[] keyValuePairArray) {
        Mode mode;
        Mode mode2 = mode = string == null ? (mode = Mode.Add) : Mode.valueOf(string);
        if (keyValuePairArray == null || keyValuePairArray.length == 0) {
            LOGGER.error("keys and values must be specified for the MapRewritePolicy");
            return null;
        }
        HashMap<String, String> hashMap = new HashMap<String, String>();
        for (KeyValuePair keyValuePair : keyValuePairArray) {
            String string2 = keyValuePair.getKey();
            if (string2 == null) {
                LOGGER.error("A null key is not valid in MapRewritePolicy");
                continue;
            }
            String string3 = keyValuePair.getValue();
            if (string3 == null) {
                LOGGER.error("A null value for key " + string2 + " is not allowed in MapRewritePolicy");
                continue;
            }
            hashMap.put(keyValuePair.getKey(), keyValuePair.getValue());
        }
        if (hashMap.isEmpty()) {
            LOGGER.error("MapRewritePolicy is not configured with any valid key value pairs");
            return null;
        }
        return new MapRewritePolicy(hashMap, mode);
    }

    static class 1 {
        static final int[] $SwitchMap$org$apache$logging$log4j$core$appender$rewrite$MapRewritePolicy$Mode = new int[Mode.values().length];

        static {
            try {
                1.$SwitchMap$org$apache$logging$log4j$core$appender$rewrite$MapRewritePolicy$Mode[Mode.Add.ordinal()] = 1;
            } catch (NoSuchFieldError noSuchFieldError) {
                // empty catch block
            }
        }
    }

    public static enum Mode {
        Add,
        Update;

    }
}

