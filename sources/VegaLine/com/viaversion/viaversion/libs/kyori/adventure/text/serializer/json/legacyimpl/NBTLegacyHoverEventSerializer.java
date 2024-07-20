/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 */
package com.viaversion.viaversion.libs.kyori.adventure.text.serializer.json.legacyimpl;

import com.viaversion.viaversion.libs.kyori.adventure.text.serializer.json.LegacyHoverEventSerializer;
import com.viaversion.viaversion.libs.kyori.adventure.text.serializer.json.legacyimpl.NBTLegacyHoverEventSerializerImpl;
import org.jetbrains.annotations.NotNull;

public interface NBTLegacyHoverEventSerializer
extends LegacyHoverEventSerializer {
    @NotNull
    public static LegacyHoverEventSerializer get() {
        return NBTLegacyHoverEventSerializerImpl.INSTANCE;
    }
}

