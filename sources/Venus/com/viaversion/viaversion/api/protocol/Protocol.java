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
    default public void registerServerbound(State state, int n, int n2) {
        this.registerServerbound(state, n, n2, (PacketHandler)null);
    }

    default public void registerServerbound(State state, int n, int n2, PacketHandler packetHandler) {
        this.registerServerbound(state, n, n2, packetHandler, false);
    }

    public void registerServerbound(State var1, int var2, int var3, PacketHandler var4, boolean var5);

    public void cancelServerbound(State var1, int var2);

    default public void registerClientbound(State state, int n, int n2) {
        this.registerClientbound(state, n, n2, (PacketHandler)null);
    }

    default public void registerClientbound(State state, int n, int n2, PacketHandler packetHandler) {
        this.registerClientbound(state, n, n2, packetHandler, false);
    }

    public void cancelClientbound(State var1, int var2);

    public void registerClientbound(State var1, int var2, int var3, PacketHandler var4, boolean var5);

    public void registerClientbound(CU var1, @Nullable PacketHandler var2);

    default public void registerClientbound(CU CU, @Nullable CM CM) {
        this.registerClientbound(CU, CM, (PacketHandler)null);
    }

    default public void registerClientbound(CU CU, @Nullable CM CM, @Nullable PacketHandler packetHandler) {
        this.registerClientbound(CU, CM, packetHandler, false);
    }

    public void registerClientbound(CU var1, @Nullable CM var2, @Nullable PacketHandler var3, boolean var4);

    public void cancelClientbound(CU var1);

    default public void registerServerbound(SU SU, @Nullable SM SM2) {
        this.registerServerbound(SU, SM2, (PacketHandler)null);
    }

    public void registerServerbound(SU var1, @Nullable PacketHandler var2);

    default public void registerServerbound(SU SU, @Nullable SM SM2, @Nullable PacketHandler packetHandler) {
        this.registerServerbound(SU, SM2, packetHandler, false);
    }

    public void registerServerbound(SU var1, @Nullable SM var2, @Nullable PacketHandler var3, boolean var4);

    public void cancelServerbound(SU var1);

    default public boolean hasRegisteredClientbound(CU CU) {
        return this.hasRegisteredClientbound(CU.state(), CU.getId());
    }

    default public boolean hasRegisteredServerbound(SU SU) {
        return this.hasRegisteredServerbound(SU.state(), SU.getId());
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

    default public void register(ViaProviders viaProviders) {
    }

    default public void init(UserConnection userConnection) {
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
        return true;
    }

    @Deprecated
    default public void cancelServerbound(State state, int n, int n2) {
        this.cancelServerbound(state, n);
    }

    @Deprecated
    default public void cancelClientbound(State state, int n, int n2) {
        this.cancelClientbound(state, n);
    }

    @Deprecated
    default public void registerClientbound(State state, int n, int n2, PacketRemapper packetRemapper) {
        this.registerClientbound(state, n, n2, packetRemapper.asPacketHandler(), false);
    }

    @Deprecated
    default public void registerClientbound(State state, int n, int n2, PacketRemapper packetRemapper, boolean bl) {
        this.registerClientbound(state, n, n2, packetRemapper.asPacketHandler(), bl);
    }

    @Deprecated
    default public void registerClientbound(CU CU, @Nullable PacketRemapper packetRemapper) {
        this.registerClientbound(CU, (CM)packetRemapper.asPacketHandler());
    }

    @Deprecated
    default public void registerClientbound(CU CU, @Nullable CM CM, @Nullable PacketRemapper packetRemapper) {
        this.registerClientbound(CU, CM, packetRemapper.asPacketHandler(), false);
    }

    @Deprecated
    default public void registerClientbound(CU CU, @Nullable CM CM, @Nullable PacketRemapper packetRemapper, boolean bl) {
        this.registerClientbound(CU, CM, packetRemapper.asPacketHandler(), bl);
    }

    @Deprecated
    default public void registerServerbound(State state, int n, int n2, PacketRemapper packetRemapper) {
        this.registerServerbound(state, n, n2, packetRemapper.asPacketHandler(), false);
    }

    @Deprecated
    default public void registerServerbound(State state, int n, int n2, PacketRemapper packetRemapper, boolean bl) {
        this.registerServerbound(state, n, n2, packetRemapper.asPacketHandler(), bl);
    }

    @Deprecated
    default public void registerServerbound(SU SU, @Nullable PacketRemapper packetRemapper) {
        this.registerServerbound(SU, packetRemapper.asPacketHandler());
    }

    @Deprecated
    default public void registerServerbound(SU SU, @Nullable SM SM2, @Nullable PacketRemapper packetRemapper) {
        this.registerServerbound(SU, SM2, packetRemapper.asPacketHandler(), false);
    }

    @Deprecated
    default public void registerServerbound(SU SU, @Nullable SM SM2, @Nullable PacketRemapper packetRemapper, boolean bl) {
        this.registerServerbound(SU, SM2, packetRemapper.asPacketHandler(), bl);
    }
}

