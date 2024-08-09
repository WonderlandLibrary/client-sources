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
    public Map<String, String> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        List list = (List)jsonParser.readValueAs(new TypeReference<List<MapEntry>>(this){
            final ListOfMapEntryDeserializer this$0;
            {
                this.this$0 = listOfMapEntryDeserializer;
            }
        });
        HashMap<String, String> hashMap = new HashMap<String, String>(list.size());
        for (MapEntry mapEntry : list) {
            hashMap.put(mapEntry.getKey(), mapEntry.getValue());
        }
        return hashMap;
    }

    @Override
    public Object deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        return this.deserialize(jsonParser, deserializationContext);
    }
}

