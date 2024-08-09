/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viabackwards.protocol.protocol1_19_1to1_19_3.data;

import com.viaversion.viabackwards.api.data.VBMappingDataLoader;
import com.viaversion.viaversion.libs.fastutil.objects.Object2IntMap;
import com.viaversion.viaversion.libs.fastutil.objects.Object2IntOpenHashMap;
import com.viaversion.viaversion.libs.gson.JsonArray;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.protocols.protocol1_19_3to1_19_1.Protocol1_19_3To1_19_1;
import com.viaversion.viaversion.util.Key;

public final class BackwardsMappings
extends com.viaversion.viabackwards.api.data.BackwardsMappings {
    private final Object2IntMap<String> mappedSounds = new Object2IntOpenHashMap<String>();

    public BackwardsMappings() {
        super("1.19.3", "1.19", Protocol1_19_3To1_19_1.class);
        this.mappedSounds.defaultReturnValue(-1);
    }

    @Override
    protected void loadExtras(CompoundTag compoundTag) {
        super.loadExtras(compoundTag);
        JsonArray jsonArray = VBMappingDataLoader.loadData("sounds-1.19.json").getAsJsonArray("sounds");
        int n = 0;
        for (JsonElement jsonElement : jsonArray) {
            this.mappedSounds.put(jsonElement.getAsString(), n++);
        }
    }

    public int mappedSound(String string) {
        return this.mappedSounds.getInt(Key.stripMinecraftNamespace(string));
    }
}

