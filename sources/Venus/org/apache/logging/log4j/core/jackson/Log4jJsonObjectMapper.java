/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.jackson;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.core.jackson.Log4jJsonModule;

public class Log4jJsonObjectMapper
extends ObjectMapper {
    private static final long serialVersionUID = 1L;

    public Log4jJsonObjectMapper() {
        this(false, true);
    }

    public Log4jJsonObjectMapper(boolean bl, boolean bl2) {
        this.registerModule(new Log4jJsonModule(bl, bl2));
        this.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
    }
}

