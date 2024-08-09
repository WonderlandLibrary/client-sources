/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken;

import java.util.Map;

public interface Header
extends Map<String, Object> {
    @Deprecated
    public static final String JWT_TYPE = "JWT";
    @Deprecated
    public static final String TYPE = "typ";
    @Deprecated
    public static final String CONTENT_TYPE = "cty";
    @Deprecated
    public static final String ALGORITHM = "alg";
    @Deprecated
    public static final String COMPRESSION_ALGORITHM = "zip";
    @Deprecated
    public static final String DEPRECATED_COMPRESSION_ALGORITHM = "calg";

    public String getType();

    public String getContentType();

    public String getAlgorithm();

    public String getCompressionAlgorithm();
}

