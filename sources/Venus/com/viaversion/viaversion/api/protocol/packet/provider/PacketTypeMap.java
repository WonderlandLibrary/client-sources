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

public interface PacketTypeMap<P> {
    public @Nullable P typeByName(String var1);

    public @Nullable P typeById(int var1);

    public Collection<P> types();

    public static <S extends PacketType, T extends S> PacketTypeMap<S> of(Class<T> clazz) {
        if (!clazz.isEnum()) {
            throw new IllegalArgumentException("Given class is not an enum");
        }
        PacketType[] packetTypeArray = (PacketType[])clazz.getEnumConstants();
        HashMap<String, PacketType> hashMap = new HashMap<String, PacketType>(packetTypeArray.length);
        for (PacketType packetType : packetTypeArray) {
            hashMap.put(packetType.getName(), packetType);
        }
        return PacketTypeMap.of(hashMap, packetTypeArray);
    }

    public static <T> PacketTypeMap<T> of(Map<String, T> map, Int2ObjectMap<T> int2ObjectMap) {
        return new PacketTypeMapMap<T>(map, int2ObjectMap);
    }

    public static <T> PacketTypeMap<T> of(Map<String, T> map, T[] TArray) {
        return new PacketTypeArrayMap<T>(map, TArray);
    }
}

