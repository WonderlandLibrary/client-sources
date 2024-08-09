/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.netty.handler.codec.http.websocketx.extensions;

import java.util.Collections;
import java.util.Map;

public final class WebSocketExtensionData {
    private final String name;
    private final Map<String, String> parameters;

    public WebSocketExtensionData(String string, Map<String, String> map) {
        if (string == null) {
            throw new NullPointerException("name");
        }
        if (map == null) {
            throw new NullPointerException("parameters");
        }
        this.name = string;
        this.parameters = Collections.unmodifiableMap(map);
    }

    public String name() {
        return this.name;
    }

    public Map<String, String> parameters() {
        return this.parameters;
    }
}

