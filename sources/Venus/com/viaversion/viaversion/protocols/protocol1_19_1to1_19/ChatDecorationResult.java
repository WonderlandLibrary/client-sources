/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.protocols.protocol1_19_1to1_19;

import com.viaversion.viaversion.libs.gson.JsonElement;

public final class ChatDecorationResult {
    private final JsonElement content;
    private final boolean overlay;

    public ChatDecorationResult(JsonElement jsonElement, boolean bl) {
        this.content = jsonElement;
        this.overlay = bl;
    }

    public JsonElement content() {
        return this.content;
    }

    public boolean overlay() {
        return this.overlay;
    }
}

