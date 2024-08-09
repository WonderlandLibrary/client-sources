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

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
final class MutableThreadContextStackDeserializer
extends StdDeserializer<MutableThreadContextStack> {
    private static final long serialVersionUID = 1L;

    MutableThreadContextStackDeserializer() {
        super(MutableThreadContextStack.class);
    }

    @Override
    public MutableThreadContextStack deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        List list = (List)jsonParser.readValueAs(new TypeReference<List<String>>(this){
            final MutableThreadContextStackDeserializer this$0;
            {
                this.this$0 = mutableThreadContextStackDeserializer;
            }
        });
        return new MutableThreadContextStack(list);
    }

    @Override
    public Object deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        return this.deserialize(jsonParser, deserializationContext);
    }
}

