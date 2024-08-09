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

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public final class SimpleMessageDeserializer
extends StdScalarDeserializer<SimpleMessage> {
    private static final long serialVersionUID = 1L;

    SimpleMessageDeserializer() {
        super(SimpleMessage.class);
    }

    @Override
    public SimpleMessage deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        return new SimpleMessage(jsonParser.getValueAsString());
    }

    @Override
    public Object deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        return this.deserialize(jsonParser, deserializationContext);
    }
}

