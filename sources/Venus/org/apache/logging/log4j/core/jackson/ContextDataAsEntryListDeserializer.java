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
import java.util.List;
import java.util.Map;
import org.apache.logging.log4j.core.impl.ContextDataFactory;
import org.apache.logging.log4j.core.jackson.MapEntry;
import org.apache.logging.log4j.util.StringMap;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class ContextDataAsEntryListDeserializer
extends StdDeserializer<StringMap> {
    private static final long serialVersionUID = 1L;

    ContextDataAsEntryListDeserializer() {
        super(Map.class);
    }

    @Override
    public StringMap deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        List list = (List)jsonParser.readValueAs(new TypeReference<List<MapEntry>>(this){
            final ContextDataAsEntryListDeserializer this$0;
            {
                this.this$0 = contextDataAsEntryListDeserializer;
            }
        });
        new ContextDataFactory();
        StringMap stringMap = ContextDataFactory.createContextData();
        for (MapEntry mapEntry : list) {
            stringMap.putValue(mapEntry.getKey(), mapEntry.getValue());
        }
        return stringMap;
    }

    @Override
    public Object deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        return this.deserialize(jsonParser, deserializationContext);
    }
}

