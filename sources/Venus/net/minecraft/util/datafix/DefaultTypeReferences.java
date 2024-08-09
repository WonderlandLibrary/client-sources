/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.datafix;

import com.mojang.datafixers.DSL;
import net.minecraft.util.datafix.TypeReferences;

public enum DefaultTypeReferences {
    LEVEL(TypeReferences.LEVEL),
    PLAYER(TypeReferences.PLAYER),
    CHUNK(TypeReferences.CHUNK),
    HOTBAR(TypeReferences.HOTBAR),
    OPTIONS(TypeReferences.OPTIONS),
    STRUCTURE(TypeReferences.STRUCTURE),
    STATS(TypeReferences.STATS),
    SAVED_DATA(TypeReferences.SAVED_DATA),
    ADVANCEMENTS(TypeReferences.ADVANCEMENTS),
    POI_CHUNK(TypeReferences.POI_CHUNK),
    WORLD_GEN_SETTINGS(TypeReferences.WORLD_GEN_SETTINGS);

    private final DSL.TypeReference reference;

    private DefaultTypeReferences(DSL.TypeReference typeReference) {
        this.reference = typeReference;
    }

    public DSL.TypeReference getTypeReference() {
        return this.reference;
    }
}

