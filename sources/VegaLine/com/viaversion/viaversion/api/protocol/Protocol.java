/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.api.protocol;

import com.google.common.annotations.Beta;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.data.MappingData;
import com.viaversion.viaversion.api.platform.providers.ViaProviders;
import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
import com.viaversion.viaversion.api.protocol.packet.Direction;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.packet.ServerboundPacketType;
import com.viaversion.viaversion.api.protocol.packet.State;
import com.viaversion.viaversion.api.protocol.packet.provider.PacketTypesProvider;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.rewriter.EntityRewriter;
import com.viaversion.viaversion.api.rewriter.ItemRewriter;
import org.checkerframework.checker.nullness.qual.Nullable;

public interface Protocol<CU extends ClientboundPacketType, CM extends ClientboundPacketType, SM extends ServerboundPacketType, SU extends ServerboundPacketType> {
    default public void registerServerbound(State state, int unmappedPacketId, int mappedPacketId) {
        this.registerServerbound(state, unmappedPacketId, mappedPacketId, (PacketHandler)null);
    }

    default public void registerServerbound(State state, int unmappedPacketId, int mappedPacketId, PacketHandler handler) {
        this.registerServerbound(state, unmappedPacketId, mappedPacketId, handler, false);
    }

    public void registerServerbound(State var1, int var2, int var3, PacketHandler var4, boolean var5);

    public void cancelServerbound(State var1, int var2);

    default public void registerClientbound(State state, int unmappedPacketId, int mappedPacketId) {
        this.registerClientbound(state, unmappedPacketId, mappedPacketId, (PacketHandler)null);
    }

    default public void registerClientbound(State state, int unmappedPacketId, int mappedPacketId, PacketHandler handler) {
        this.registerClientbound(state, unmappedPacketId, mappedPacketId, handler, false);
    }

    public void cancelClientbound(State var1, int var2);

    public void registerClientbound(State var1, int var2, int var3, PacketHandler var4, boolean var5);

    public void registerClientbound(CU var1, @Nullable PacketHandler var2);

    default public void registerClientbound(CU packetType, @Nullable CM mappedPacketType) {
        this.registerClientbound(packetType, mappedPacketType, (PacketHandler)null);
    }

    default public void registerClientbound(CU packetType, @Nullable CM mappedPacketType, @Nullable PacketHandler handler) {
        this.registerClientbound(packetType, mappedPacketType, handler, false);
    }

    public void registerClientbound(CU var1, @Nullable CM var2, @Nullable PacketHandler var3, boolean var4);

    public void cancelClientbound(CU var1);

    default public void registerServerbound(SU packetType, @Nullable SM mappedPacketType) {
        this.registerServerbound(packetType, mappedPacketType, (PacketHandler)null);
    }

    public void registerServerbound(SU var1, @Nullable PacketHandler var2);

    default public void registerServerbound(SU packetType, @Nullable SM mappedPacketType, @Nullable PacketHandler handler) {
        this.registerServerbound(packetType, mappedPacketType, handler, false);
    }

    public void registerServerbound(SU var1, @Nullable SM var2, @Nullable PacketHandler var3, boolean var4);

    public void cancelServerbound(SU var1);

    default public boolean hasRegisteredClientbound(CU packetType) {
        return this.hasRegisteredClientbound(packetType.state(), packetType.getId());
    }

    default public boolean hasRegisteredServerbound(SU packetType) {
        return this.hasRegisteredServerbound(packetType.state(), packetType.getId());
    }

    public boolean hasRegisteredClientbound(State var1, int var2);

    public boolean hasRegisteredServerbound(State var1, int var2);

    public void transform(Direction var1, State var2, PacketWrapper var3) throws Exception;

    @Beta
    public PacketTypesProvider<CU, CM, SM, SU> getPacketTypesProvider();

    public <T> @Nullable T get(Class<T> var1);

    public void put(Object var1);

    public void initialize();

    default public boolean hasMappingDataToLoad() {
        return this.getMappingData() != null;
    }

    public void loadMappingData();

    default public void register(ViaProviders providers) {
    }

    default public void init(UserConnection connection) {
    }

    default public @Nullable MappingData getMappingData() {
        return null;
    }

    default public @Nullable EntityRewriter<?> getEntityRewriter() {
        return null;
    }

    default public @Nullable ItemRewriter<?> getItemRewriter() {
        return null;
    }

    default public boolean isBaseProtocol() {
        return false;
    }

    @Deprecated
    default public void cancelServerbound(State state, int unmappedPacketId, int mappedPacketId) {
        this.cancelServerbound(state, unmappedPacketId);
    }

    @Deprecated
    default public void cancelClientbound(State state, int unmappedPacketId, int mappedPacketId) {
        this.cancelClientbound(state, unmappedPacketId);
    }

    @Deprecated
    default public void registerClientbound(State state, int unmappedPacketId, int mappedPacketId, PacketRemapper packetRemapper) {
        this.registerClientbound(state, unmappedPacketId, mappedPacketId, packetRemapper.asPacketHandler(), false);
    }

    @Deprecated
    default public void registerClientbound(State state, int unmappedPacketId, int mappedPacketId, PacketRemapper packetRemapper, boolean override) {
        this.registerClientbound(state, unmappedPacketId, mappedPacketId, packetRemapper.asPacketHandler(), override);
    }

    @Deprecated
    default public void registerClientbound(CU packetType, @Nullable PacketRemapper packetRemapper) {
        this.registerClientbound(packetType, (CM)packetRemapper.asPacketHandler());
    }

    @Deprecated
    default public void registerClientbound(CU packetType, @Nullable CM mappedPacketType, @Nullable PacketRemapper packetRemapper) {
        this.registerClientbound(packetType, mappedPacketType, packetRemapper.asPacketHandler(), false);
    }

    @Deprecated
    default public void registerClientbound(CU packetType, @Nullable CM mappedPacketType, @Nullable PacketRemapper packetRemapper, boolean override) {
        this.registerClientbound(packetType, mappedPacketType, packetRemapper.asPacketHandler(), override);
    }

    @Deprecated
    default public void registerServerbound(State state, int unmappedPacketId, int mappedPacketId, PacketRemapper packetRemapper) {
        this.registerServerbound(state, unmappedPacketId, mappedPacketId, packetRemapper.asPacketHandler(), false);
    }

    @Deprecated
    default public void registerServerbound(State state, int unmappedPacketId, int mappedPacketId, PacketRemapper packetRemapper, boolean override) {
        this.registerServerbound(state, unmappedPacketId, mappedPacketId, packetRemapper.asPacketHandler(), override);
    }

    @Deprecated
    default public void registerServerbound(SU packetType, @Nullable PacketRemapper packetRemapper) {
        this.registerServerbound(packetType, packetRemapper.asPacketHandler());
    }

    @Deprecated
    default public void registerServerbound(SU packetType, @Nullable SM mappedPacketType, @Nullable PacketRemapper packetRemapper) {
        this.registerServerbound(packetType, mappedPacketType, packetRemapper.asPacketHandler(), false);
    }

    @Deprecated
    default public void registerServerbound(SU packetType, @Nullable SM mappedPacketType, @Nullable PacketRemapper packetRemapper, boolean override) {
        this.registerServerbound(packetType, mappedPacketType, packetRemapper.asPacketHandler(), override);
    }
}

