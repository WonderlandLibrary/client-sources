/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.impl;

import io.jsonwebtoken.Header;
import io.jsonwebtoken.impl.ParameterMap;
import io.jsonwebtoken.impl.lang.CompactMediaTypeIdConverter;
import io.jsonwebtoken.impl.lang.Parameter;
import io.jsonwebtoken.impl.lang.Parameters;
import io.jsonwebtoken.lang.Registry;
import io.jsonwebtoken.lang.Strings;
import java.util.Map;

public class DefaultHeader
extends ParameterMap
implements Header {
    static final Parameter<String> TYPE = Parameters.string("typ", "Type");
    static final Parameter<String> CONTENT_TYPE = (Parameter)Parameters.builder(String.class).setId("cty").setName("Content Type").setConverter(CompactMediaTypeIdConverter.INSTANCE).build();
    static final Parameter<String> ALGORITHM = Parameters.string("alg", "Algorithm");
    static final Parameter<String> COMPRESSION_ALGORITHM = Parameters.string("zip", "Compression Algorithm");
    @Deprecated
    static final Parameter<String> DEPRECATED_COMPRESSION_ALGORITHM = Parameters.string("calg", "Deprecated Compression Algorithm");
    static final Registry<String, Parameter<?>> PARAMS = Parameters.registry(TYPE, CONTENT_TYPE, ALGORITHM, COMPRESSION_ALGORITHM, DEPRECATED_COMPRESSION_ALGORITHM);

    public DefaultHeader(Map<String, ?> map) {
        super(PARAMS, map);
    }

    protected DefaultHeader(Registry<String, Parameter<?>> registry, Map<String, ?> map) {
        super(registry, map);
    }

    @Override
    public String getName() {
        return "JWT header";
    }

    @Override
    public String getType() {
        return this.get(TYPE);
    }

    @Override
    public String getContentType() {
        return this.get(CONTENT_TYPE);
    }

    @Override
    public String getAlgorithm() {
        return this.get(ALGORITHM);
    }

    @Override
    public String getCompressionAlgorithm() {
        String string = this.get(COMPRESSION_ALGORITHM);
        if (!Strings.hasText(string)) {
            string = this.get(DEPRECATED_COMPRESSION_ALGORITHM);
        }
        return string;
    }
}

