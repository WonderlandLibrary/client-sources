/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8;

import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.ClientboundPackets1_7;
import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.ServerboundPackets1_7;
import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.packets.EntityPackets;
import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.packets.InventoryPackets;
import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.packets.PlayerPackets;
import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.packets.ScoreboardPackets;
import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.packets.SpawnPackets;
import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.packets.WorldPackets;
import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.provider.CompressionHandlerProvider;
import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.storage.CompressionSendStorage;
import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.storage.EntityTracker;
import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.storage.GameProfileStorage;
import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.storage.PlayerAbilities;
import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.storage.PlayerPosition;
import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.storage.Scoreboard;
import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.storage.Windows;
import com.viaversion.viarewind.protocol.protocol1_7_6_10to1_8.storage.WorldBorder;
import com.viaversion.viarewind.utils.Ticker;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.platform.providers.ViaProviders;
import com.viaversion.viaversion.api.protocol.AbstractProtocol;
import com.viaversion.viaversion.api.protocol.packet.Direction;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.packet.State;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocols.protocol1_8.ClientboundPackets1_8;
import com.viaversion.viaversion.protocols.protocol1_8.ServerboundPackets1_8;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.storage.ClientWorld;

public class Protocol1_7_6_10To1_8
extends AbstractProtocol<ClientboundPackets1_8, ClientboundPackets1_7, ServerboundPackets1_8, ServerboundPackets1_7> {
    public Protocol1_7_6_10To1_8() {
        super(ClientboundPackets1_8.class, ClientboundPackets1_7.class, ServerboundPackets1_8.class, ServerboundPackets1_7.class);
    }

    @Override
    protected void registerPackets() {
        EntityPackets.register(this);
        InventoryPackets.register(this);
        PlayerPackets.register(this);
        ScoreboardPackets.register(this);
        SpawnPackets.register(this);
        WorldPackets.register(this);
        this.registerClientbound(ClientboundPackets1_8.KEEP_ALIVE, new PacketHandlers(){

            @Override
            public void register() {
                this.map((Type)Type.VAR_INT, Type.INT);
            }
        });
        this.cancelClientbound(ClientboundPackets1_8.SET_COMPRESSION);
        this.registerServerbound(ServerboundPackets1_7.KEEP_ALIVE, new PacketHandlers(){

            @Override
            public void register() {
                this.map((Type)Type.INT, Type.VAR_INT);
            }
        });
        this.registerClientbound(State.LOGIN, 1, 1, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.STRING);
                this.map(Type.BYTE_ARRAY_PRIMITIVE, Type.SHORT_BYTE_ARRAY);
                this.map(Type.BYTE_ARRAY_PRIMITIVE, Type.SHORT_BYTE_ARRAY);
            }
        });
        this.registerClientbound(State.LOGIN, 3, 3, new PacketHandlers(){

            @Override
            public void register() {
                this.handler(packetWrapper -> {
                    Via.getManager().getProviders().get(CompressionHandlerProvider.class).handleSetCompression(packetWrapper.user(), packetWrapper.read(Type.VAR_INT));
                    packetWrapper.cancel();
                });
            }
        });
        this.registerServerbound(State.LOGIN, 1, 1, new PacketHandlers(){

            @Override
            public void register() {
                this.map(Type.SHORT_BYTE_ARRAY, Type.BYTE_ARRAY_PRIMITIVE);
                this.map(Type.SHORT_BYTE_ARRAY, Type.BYTE_ARRAY_PRIMITIVE);
            }
        });
    }

    @Override
    public void transform(Direction direction, State state, PacketWrapper packetWrapper) throws Exception {
        Via.getManager().getProviders().get(CompressionHandlerProvider.class).handleTransform(packetWrapper.user());
        super.transform(direction, state, packetWrapper);
    }

    @Override
    public void init(UserConnection userConnection) {
        Ticker.init();
        userConnection.put(new Windows(userConnection));
        userConnection.put(new EntityTracker(userConnection));
        userConnection.put(new PlayerPosition(userConnection));
        userConnection.put(new GameProfileStorage(userConnection));
        userConnection.put(new Scoreboard(userConnection));
        userConnection.put(new CompressionSendStorage(userConnection));
        userConnection.put(new WorldBorder(userConnection));
        userConnection.put(new PlayerAbilities(userConnection));
        userConnection.put(new ClientWorld(userConnection));
    }

    @Override
    public void register(ViaProviders providers) {
        providers.register(CompressionHandlerProvider.class, new CompressionHandlerProvider());
    }
}

