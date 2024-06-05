/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.protocols.protocol1_18to1_17_1.data;

import com.viaversion.viaversion.api.data.MappingDataBase;
import com.viaversion.viaversion.libs.fastutil.objects.Object2IntMap;
import com.viaversion.viaversion.libs.fastutil.objects.Object2IntOpenHashMap;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.gson.JsonObject;
import java.util.Iterator;
import org.checkerframework.checker.nullness.qual.Nullable;

public final class MappingData
extends MappingDataBase {
    private final Object2IntMap<String> blockEntityIds = new Object2IntOpenHashMap<String>();

    public MappingData() {
        super("1.17", "1.18", true);
        this.blockEntityIds.defaultReturnValue(-1);
    }

    @Override
    protected void loadExtras(JsonObject oldMappings, JsonObject newMappings, @Nullable JsonObject diffMappings) {
        int i = 0;
        Iterator<JsonElement> iterator = newMappings.getAsJsonArray("blockentities").iterator();
        while (iterator.hasNext()) {
            JsonElement element = iterator.next();
            String id = element.getAsString();
            this.blockEntityIds.put(id, i++);
        }
    }

    public Object2IntMap<String> blockEntityIds() {
        return this.blockEntityIds;
    }
}

