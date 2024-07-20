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
        public void accept(String key, Object value, JsonGenerator jsonGenerator) {
            try {
                jsonGenerator.writeStringField(key, String.valueOf(value));
            } catch (Exception ex) {
                throw new IllegalStateException("Problem with key " + key, ex);
            }
        }
    };

    protected ContextDataSerializer() {
        super(Map.class, false);
    }

    @Override
    public void serialize(ReadOnlyStringMap contextData, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonGenerationException {
        jgen.writeStartObject();
        contextData.forEach(WRITE_STRING_FIELD_INTO, jgen);
        jgen.writeEndObject();
    }
}

