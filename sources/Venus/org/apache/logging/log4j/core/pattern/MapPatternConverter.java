/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.pattern;

import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.pattern.ConverterKeys;
import org.apache.logging.log4j.core.pattern.LogEventPatternConverter;
import org.apache.logging.log4j.message.MapMessage;
import org.apache.logging.log4j.util.IndexedReadOnlyStringMap;

@Plugin(name="MapPatternConverter", category="Converter")
@ConverterKeys(value={"K", "map", "MAP"})
public final class MapPatternConverter
extends LogEventPatternConverter {
    private final String key;

    private MapPatternConverter(String[] stringArray) {
        super(stringArray != null && stringArray.length > 0 ? "MAP{" + stringArray[0] + '}' : "MAP", "map");
        this.key = stringArray != null && stringArray.length > 0 ? stringArray[0] : null;
    }

    public static MapPatternConverter newInstance(String[] stringArray) {
        return new MapPatternConverter(stringArray);
    }

    @Override
    public void format(LogEvent logEvent, StringBuilder stringBuilder) {
        if (!(logEvent.getMessage() instanceof MapMessage)) {
            return;
        }
        MapMessage mapMessage = (MapMessage)logEvent.getMessage();
        IndexedReadOnlyStringMap indexedReadOnlyStringMap = mapMessage.getIndexedReadOnlyStringMap();
        if (this.key == null) {
            if (indexedReadOnlyStringMap.isEmpty()) {
                stringBuilder.append("{}");
                return;
            }
            stringBuilder.append("{");
            for (int i = 0; i < indexedReadOnlyStringMap.size(); ++i) {
                if (i > 0) {
                    stringBuilder.append(", ");
                }
                stringBuilder.append(indexedReadOnlyStringMap.getKeyAt(i)).append('=').append(indexedReadOnlyStringMap.getValueAt(i));
            }
            stringBuilder.append('}');
        } else {
            String string = (String)indexedReadOnlyStringMap.getValue(this.key);
            if (string != null) {
                stringBuilder.append(string);
            }
        }
    }
}

