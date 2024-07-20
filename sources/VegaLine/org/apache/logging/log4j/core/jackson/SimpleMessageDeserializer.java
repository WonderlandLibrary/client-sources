/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdScalarDeserializer;
import java.io.IOException;
import org.apache.logging.log4j.message.SimpleMessage;

public final class SimpleMessageDeserializer
extends StdScalarDeserializer<SimpleMessage> {
    private static final long serialVersionUID = 1L;

    SimpleMessageDeserializer() {
        super(SimpleMessage.class);
    }

    @Override
    public SimpleMessage deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        return new SimpleMessage(jp.getValueAsString());
    }
}

