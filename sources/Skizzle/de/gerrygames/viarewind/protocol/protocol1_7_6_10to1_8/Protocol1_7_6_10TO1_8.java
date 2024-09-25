/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  io.netty.channel.Channel
 *  io.netty.channel.ChannelHandler
 */
package de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8;

import de.gerrygames.viarewind.netty.EmptyChannelHandler;
import de.gerrygames.viarewind.netty.ForwardMessageToByteEncoder;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.packets.EntityPackets;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.packets.InventoryPackets;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.packets.PlayerPackets;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.packets.ScoreboardPackets;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.packets.SpawnPackets;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.packets.WorldPackets;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.storage.CompressionSendStorage;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.storage.EntityTracker;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.storage.GameProfileStorage;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.storage.PlayerAbilities;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.storage.PlayerPosition;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.storage.Scoreboard;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.storage.Windows;
import de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.storage.WorldBorder;
import de.gerrygames.viarewind.utils.Ticker;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import us.myles.ViaVersion.api.PacketWrapper;
import us.myles.ViaVersion.api.data.UserConnection;
import us.myles.ViaVersion.api.protocol.Protocol;
import us.myles.ViaVersion.api.remapper.PacketHandler;
import us.myles.ViaVersion.api.remapper.PacketRemapper;
import us.myles.ViaVersion.api.type.Type;
import us.myles.ViaVersion.api.type.types.CustomByteType;
import us.myles.ViaVersion.packets.Direction;
import us.myles.ViaVersion.packets.State;
import us.myles.ViaVersion.protocols.protocol1_9_3to1_9_1_2.storage.ClientWorld;
import us.myles.ViaVersion.protocols.protocol1_9to1_8.storage.ClientChunks;

public class Protocol1_7_6_10TO1_8
extends Protocol {
    @Override
    protected void registerPackets() {
        EntityPackets.register(this);
        InventoryPackets.register(this);
        PlayerPackets.register(this);
        ScoreboardPackets.register(this);
        SpawnPackets.register(this);
        WorldPackets.register(this);
        this.registerOutgoing(State.PLAY, 0, 0, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map((Type)Type.VAR_INT, Type.INT);
            }
        });
        this.registerOutgoing(State.PLAY, 70, -1, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper packetWrapper) throws Exception {
                        packetWrapper.cancel();
                    }
                });
            }
        });
        this.registerIncoming(State.PLAY, 0, 0, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.INT, Type.VAR_INT);
            }
        });
        this.registerOutgoing(State.LOGIN, 1, 1, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.map(Type.STRING);
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper packetWrapper) throws Exception {
                        int publicKeyLength = packetWrapper.read(Type.VAR_INT);
                        packetWrapper.write(Type.SHORT, (short)publicKeyLength);
                        packetWrapper.passthrough(new CustomByteType(publicKeyLength));
                        int verifyTokenLength = packetWrapper.read(Type.VAR_INT);
                        packetWrapper.write(Type.SHORT, (short)verifyTokenLength);
                        packetWrapper.passthrough(new CustomByteType(verifyTokenLength));
                    }
                });
            }
        });
        this.registerOutgoing(State.LOGIN, 3, 3, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper packetWrapper) throws Exception {
                        packetWrapper.cancel();
                        packetWrapper.user().get(CompressionSendStorage.class).setCompressionSend(true);
                    }
                });
            }
        });
        this.registerIncoming(State.LOGIN, 1, 1, new PacketRemapper(){

            @Override
            public void registerMap() {
                this.handler(new PacketHandler(){

                    @Override
                    public void handle(PacketWrapper packetWrapper) throws Exception {
                        short sharedSecretLength = packetWrapper.read(Type.SHORT);
                        packetWrapper.write(Type.VAR_INT, Integer.valueOf(sharedSecretLength));
                        packetWrapper.passthrough(new CustomByteType(Integer.valueOf(sharedSecretLength)));
                        short verifyTokenLength = packetWrapper.read(Type.SHORT);
                        packetWrapper.write(Type.VAR_INT, Integer.valueOf(verifyTokenLength));
                        packetWrapper.passthrough(new CustomByteType(Integer.valueOf(verifyTokenLength)));
                    }
                });
            }
        });
    }

    @Override
    public void transform(Direction direction, State state, PacketWrapper packetWrapper) throws Exception {
        CompressionSendStorage compressionSendStorage = packetWrapper.user().get(CompressionSendStorage.class);
        if (compressionSendStorage.isCompressionSend()) {
            Channel channel = packetWrapper.user().getChannel();
            if (channel.pipeline().get("compress") != null) {
                channel.pipeline().replace("decompress", "decompress", (ChannelHandler)new EmptyChannelHandler());
                channel.pipeline().replace("compress", "compress", (ChannelHandler)new ForwardMessageToByteEncoder());
            } else if (channel.pipeline().get("compression-encoder") != null) {
                channel.pipeline().replace("compression-decoder", "compression-decoder", (ChannelHandler)new EmptyChannelHandler());
                channel.pipeline().replace("compression-encoder", "compression-encoder", (ChannelHandler)new ForwardMessageToByteEncoder());
            }
            compressionSendStorage.setCompressionSend(false);
        }
        super.transform(direction, state, packetWrapper);
    }

    @Override
    public void init(UserConnection userConnection) {
        Ticker.init();
        userConnection.put(new Windows(userConnection));
        userConnection.put(new EntityTracker(userConnection));
        userConnection.put(new PlayerPosition(userConnection));
        userConnection.put(new GameProfileStorage(userConnection));
        userConnection.put(new ClientChunks(userConnection));
        userConnection.put(new Scoreboard(userConnection));
        userConnection.put(new CompressionSendStorage(userConnection));
        userConnection.put(new WorldBorder(userConnection));
        userConnection.put(new PlayerAbilities(userConnection));
        userConnection.put(new ClientWorld(userConnection));
    }
}

