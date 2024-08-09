/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.layout;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.config.plugins.PluginFactory;
import org.apache.logging.log4j.core.util.KeyValuePair;
import org.apache.logging.log4j.message.StructuredDataId;

@Plugin(name="LoggerFields", category="Core", printObject=true)
public final class LoggerFields {
    private final Map<String, String> map;
    private final String sdId;
    private final String enterpriseId;
    private final boolean discardIfAllFieldsAreEmpty;

    private LoggerFields(Map<String, String> map, String string, String string2, boolean bl) {
        this.sdId = string;
        this.enterpriseId = string2;
        this.map = Collections.unmodifiableMap(map);
        this.discardIfAllFieldsAreEmpty = bl;
    }

    public Map<String, String> getMap() {
        return this.map;
    }

    public String toString() {
        return this.map.toString();
    }

    @PluginFactory
    public static LoggerFields createLoggerFields(@PluginElement(value="LoggerFields") KeyValuePair[] keyValuePairArray, @PluginAttribute(value="sdId") String string, @PluginAttribute(value="enterpriseId") String string2, @PluginAttribute(value="discardIfAllFieldsAreEmpty") boolean bl) {
        HashMap<String, String> hashMap = new HashMap<String, String>();
        for (KeyValuePair keyValuePair : keyValuePairArray) {
            hashMap.put(keyValuePair.getKey(), keyValuePair.getValue());
        }
        return new LoggerFields(hashMap, string, string2, bl);
    }

    public StructuredDataId getSdId() {
        if (this.enterpriseId == null || this.sdId == null) {
            return null;
        }
        int n = Integer.parseInt(this.enterpriseId);
        return new StructuredDataId(this.sdId, n, null, null);
    }

    public boolean getDiscardIfAllFieldsAreEmpty() {
        return this.discardIfAllFieldsAreEmpty;
    }
}

