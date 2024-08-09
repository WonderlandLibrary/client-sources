/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viabackwards.api.data;

import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ChatRewriter;
import org.checkerframework.checker.nullness.qual.Nullable;

public class MappedItem {
    private final int id;
    private final String jsonName;
    private final Integer customModelData;

    public MappedItem(int n, String string) {
        this(n, string, null);
    }

    public MappedItem(int n, String string, @Nullable Integer n2) {
        this.id = n;
        this.jsonName = ChatRewriter.legacyTextToJsonString("\u00a7f" + string, true);
        this.customModelData = n2;
    }

    public int getId() {
        return this.id;
    }

    public String getJsonName() {
        return this.jsonName;
    }

    public @Nullable Integer customModelData() {
        return this.customModelData;
    }
}

