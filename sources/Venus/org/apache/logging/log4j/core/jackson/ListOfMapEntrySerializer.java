/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.jackson;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import org.apache.logging.log4j.core.jackson.MapEntry;

public class ListOfMapEntrySerializer
extends StdSerializer<Map<String, String>> {
    private static final long serialVersionUID = 1L;

    protected ListOfMapEntrySerializer() {
        super(Map.class, false);
    }

    @Override
    public void serialize(Map<String, String> map, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonGenerationException {
        Set<Map.Entry<String, String>> set = map.entrySet();
        MapEntry[] mapEntryArray = new MapEntry[set.size()];
        int n = 0;
        for (Map.Entry<String, String> entry : set) {
            mapEntryArray[n++] = new MapEntry(entry.getKey(), entry.getValue());
        }
        jsonGenerator.writeObject(mapEntryArray);
    }

    @Override
    public void serialize(Object object, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        this.serialize((Map)object, jsonGenerator, serializerProvider);
    }
}

