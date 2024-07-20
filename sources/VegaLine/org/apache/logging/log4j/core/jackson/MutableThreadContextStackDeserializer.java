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
import org.apache.logging.log4j.spi.MutableThreadContextStack;

final class MutableThreadContextStackDeserializer
extends StdDeserializer<MutableThreadContextStack> {
    private static final long serialVersionUID = 1L;

    MutableThreadContextStackDeserializer() {
        super(MutableThreadContextStack.class);
    }

    @Override
    public MutableThreadContextStack deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        List list = (List)jp.readValueAs(new TypeReference<List<String>>(){});
        return new MutableThreadContextStack(list);
    }
}

