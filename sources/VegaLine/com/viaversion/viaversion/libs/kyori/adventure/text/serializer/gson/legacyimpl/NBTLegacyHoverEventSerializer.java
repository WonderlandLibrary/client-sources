/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.ApiStatus$ScheduledForRemoval
 *  org.jetbrains.annotations.NotNull
 */
package com.viaversion.viaversion.libs.kyori.adventure.text.serializer.gson.legacyimpl;

import com.viaversion.viaversion.libs.kyori.adventure.text.serializer.gson.LegacyHoverEventSerializer;
import com.viaversion.viaversion.libs.kyori.adventure.text.serializer.gson.legacyimpl.NBTLegacyHoverEventSerializerImpl;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

@Deprecated
@ApiStatus.ScheduledForRemoval(inVersion="5.0.0")
public interface NBTLegacyHoverEventSerializer
extends LegacyHoverEventSerializer,
com.viaversion.viaversion.libs.kyori.adventure.text.serializer.json.legacyimpl.NBTLegacyHoverEventSerializer {
    @Deprecated
    @ApiStatus.ScheduledForRemoval(inVersion="5.0.0")
    @NotNull
    public static LegacyHoverEventSerializer get() {
        return NBTLegacyHoverEventSerializerImpl.INSTANCE;
    }
}

