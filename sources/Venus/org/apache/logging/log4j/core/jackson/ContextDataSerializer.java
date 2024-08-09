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
import org.apache.logging.log4j.util.ReadOnlyStringMap;
import org.apache.logging.log4j.util.TriConsumer;

public class ContextDataSerializer
extends StdSerializer<ReadOnlyStringMap> {
    private static final long serialVersionUID = 1L;
    private static final TriConsumer<String, Object, JsonGenerator> WRITE_STRING_FIELD_INTO = new TriConsumer<String, Object, JsonGenerator>(){

        @Override
        public void accept(String string, Object object, JsonGenerator jsonGenerator) {
            try {
                jsonGenerator.writeStringField(string, String.valueOf(object));
            } catch (Exception exception) {
                throw new IllegalStateException("Problem with key " + string, exception);
            }
        }

        @Override
        public void accept(Object object, Object object2, Object object3) {
            this.accept((String)object, object2, (JsonGenerator)object3);
        }
    };

    protected ContextDataSerializer() {
        super(Map.class, false);
    }

    @Override
    public void serialize(ReadOnlyStringMap readOnlyStringMap, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException, JsonGenerationException {
        jsonGenerator.writeStartObject();
        readOnlyStringMap.forEach(WRITE_STRING_FIELD_INTO, jsonGenerator);
        jsonGenerator.writeEndObject();
    }

    @Override
    public void serialize(Object object, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        this.serialize((ReadOnlyStringMap)object, jsonGenerator, serializerProvider);
    }
}

