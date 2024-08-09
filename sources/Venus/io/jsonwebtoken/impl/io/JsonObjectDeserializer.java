/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl.io;

import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.impl.lang.Function;
import io.jsonwebtoken.io.DeserializationException;
import io.jsonwebtoken.io.Deserializer;
import io.jsonwebtoken.lang.Assert;
import java.io.Reader;
import java.util.Map;

public class JsonObjectDeserializer
implements Function<Reader, Map<String, ?>> {
    private static final String MALFORMED_ERROR = "Malformed %s JSON: %s";
    private static final String MALFORMED_COMPLEX_ERROR = "Malformed or excessively complex %s JSON. If experienced in a production environment, this could reflect a potential malicious %s, please investigate the source further. Cause: %s";
    private final Deserializer<?> deserializer;
    private final String name;

    public JsonObjectDeserializer(Deserializer<?> deserializer, String string) {
        this.deserializer = Assert.notNull(deserializer, "JSON Deserializer cannot be null.");
        this.name = Assert.hasText(string, "name cannot be null or empty.");
    }

    @Override
    public Map<String, ?> apply(Reader reader) {
        Assert.notNull(reader, "InputStream argument cannot be null.");
        try {
            Object obj = this.deserializer.deserialize(reader);
            if (obj == null) {
                String string = "Deserialized data resulted in a null value; cannot create Map<String,?>";
                throw new DeserializationException(string);
            }
            if (!(obj instanceof Map)) {
                String string = "Deserialized data is not a JSON Object; cannot create Map<String,?>";
                throw new DeserializationException(string);
            }
            return (Map)obj;
        } catch (StackOverflowError stackOverflowError) {
            String string = String.format(MALFORMED_COMPLEX_ERROR, this.name, this.name, stackOverflowError.getMessage());
            throw new DeserializationException(string, stackOverflowError);
        } catch (Throwable throwable) {
            throw this.malformed(throwable);
        }
    }

    protected RuntimeException malformed(Throwable throwable) {
        String string = String.format(MALFORMED_ERROR, this.name, throwable.getMessage());
        throw new MalformedJwtException(string, throwable);
    }

    @Override
    public Object apply(Object object) {
        return this.apply((Reader)object);
    }
}

