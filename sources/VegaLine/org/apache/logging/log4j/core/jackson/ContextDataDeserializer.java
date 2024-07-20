/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import java.io.IOException;
import java.util.Map;
import org.apache.logging.log4j.core.impl.ContextDataFactory;
import org.apache.logging.log4j.util.StringMap;

public class ContextDataDeserializer
extends StdDeserializer<StringMap> {
    private static final long serialVersionUID = 1L;

    ContextDataDeserializer() {
        super(Map.class);
    }

    @Override
    public StringMap deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        StringMap contextData = ContextDataFactory.createContextData();
        while (jp.nextToken() != JsonToken.END_OBJECT) {
            String fieldName = jp.getCurrentName();
            jp.nextToken();
            contextData.putValue(fieldName, jp.getText());
        }
        return contextData;
    }
}

