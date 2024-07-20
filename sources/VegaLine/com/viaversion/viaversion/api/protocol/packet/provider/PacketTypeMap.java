/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.api.protocol.packet.provider;

import com.viaversion.viaversion.api.protocol.packet.PacketType;
import com.viaversion.viaversion.api.protocol.packet.provider.PacketTypeArrayMap;
import com.viaversion.viaversion.api.protocol.packet.provider.PacketTypeMapMap;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.checkerframework.checker.nullness.qual.Nullable;

public interface PacketTypeMap<P extends PacketType> {
    public @Nullable P typeByName(String var1);

    public @Nullable P typeById(int var1);

    public Collection<P> types();

    public static <S extends PacketType, T extends S> PacketTypeMap<S> of(Class<T> enumClass) {
        if (!enumClass.isEnum()) {
            throw new IllegalArgumentException("Given class is not an enum");
        }
        PacketType[] types = (PacketType[])enumClass.getEnumConstants();
        HashMap<String, PacketType> byName = new HashMap<String, PacketType>(types.length);
        for (PacketType type2 : types) {
            byName.put(type2.getName(), type2);
        }
        return PacketTypeMap.of(byName, (PacketType[])types);
    }

    public static <T extends PacketType> PacketTypeMap<T> of(Map<String, T> packetsByName, Int2ObjectMap<T> packetsById) {
        return new PacketTypeMapMap<T>(packetsByName, packetsById);
    }

    public static <T extends PacketType> PacketTypeMap<T> of(Map<String, T> packetsByName, T[] packets) {
        return new PacketTypeArrayMap(packetsByName, packets);
    }
}

