/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.libs.gson;

import com.viaversion.viaversion.libs.gson.stream.JsonReader;
import java.io.IOException;

public interface ToNumberStrategy {
    public Number readNumber(JsonReader var1) throws IOException;
}

