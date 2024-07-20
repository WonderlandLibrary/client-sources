/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.core.jackson.MapEntry;

public class ListOfMapEntryDeserializer
extends StdDeserializer<Map<String, String>> {
    private static final long serialVersionUID = 1L;

    ListOfMapEntryDeserializer() {
        super(Map.class);
    }

    @Override
    public Map<String, String> deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        List list = (List)jp.readValueAs(new TypeReference<List<MapEntry>>(){});
        HashMap<String, String> map = new HashMap<String, String>(list.size());
        for (MapEntry mapEntry : list) {
            map.put(mapEntry.getKey(), mapEntry.getValue());
        }
        return map;
    }
}

