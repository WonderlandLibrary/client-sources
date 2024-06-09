//package us.dev.direkt.module.internal.core.protocol.adapter.adapters;
//
//import com.google.common.collect.ImmutableMap;
//import io.netty.buffer.Unpooled;
//import net.minecraft.network.EnumConnectionState;
//import net.minecraft.network.Packet;
//import net.minecraft.network.PacketBuffer;
//import net.minecraft.network.play.INetHandlerPlayClient;
//import net.minecraft.network.play.server.*;
//import net.minecraft.scoreboard.Team;
//import net.minecraft.util.text.ITextComponent;
//import us.dev.api.factory.ClassFactory;
//import us.dev.direkt.event.internal.events.game.network.EventDecodePacket;
//import us.dev.direkt.event.internal.events.game.network.EventEncodePacket;
//import us.dev.direkt.module.internal.core.protocol.adapter.ProtocolAdapter;
//import us.dev.dvent.Listener;
//import us.dev.dvent.Link;
//
//import java.io.IOException;
//import java.util.Map;
//
///**
// * @author Foundry
// */
//public class AdapterVersion47 extends ProtocolAdapter {
//
//    private static final Map<Class<? extends Packet>, Class<? extends Packet>> clientboundPacketMap = new ImmutableMap.Builder<Class<? extends Packet>, Class<? extends Packet>>()
//            .put(SPacketScoreboardObjective.class, SPacketCustomPayload.class)
//            .put(SPacketSpawnExperienceOrb.class, SPacketJoinGame.class)
//            .put(SPacketSpawnObject.class, SPacketKeepAlive.class)
//            .put(SPacketTeams.class, SPacketServerDifficulty.class)
//            .put(SPacketSpawnPlayer.class, SPacketSpawnPosition.class)
//            .put(SPacketEntityMetadata.class, SPacketPlayerAbilities.class)
//            .put(SPacketUpdateTileEntity.class, SPacketHeldItemChange.class)
//            .put(SPacketSpawnPainting.class, SPacketEntityEquipment.class)
//            .put(SPacketCollectItem.class, SPacketUpdateEntityNBT.class)
//            .put(SPacketHeldItemChange.class, SPacketStatistics.class)
//            .put(SPacketCustomSound.class, SPacketEntityHeadLook.class)
//            .put(SPacketPlayerPosLook.class, SPacketCloseWindow.class)
//            .put(SPacketAnimation.class, SPacketUpdateHealth.class)
//            .put(SPacketDisplayObjective.class, SPacketPlayerListItem.class)
//            .put(SPacketRemoveEntityEffect.class, SPacketWindowProperty.class)
//            .put(SPacketUpdateHealth.class, new SPacketTeams() {
//                @Override
//                public void readPacketData(PacketBuffer buf) throws IOException {
//                    this.name = buf.readStringFromBuffer(16);
//                    this.action = buf.readByte();
//
//                    if (this.action == 0 || this.action == 2) {
//                        this.displayName = buf.readStringFromBuffer(32);
//                        this.prefix = buf.readStringFromBuffer(16);
//                        this.suffix = buf.readStringFromBuffer(16);
//                        this.friendlyFlags = buf.readByte();
//                        this.nameTagVisibility = buf.readStringFromBuffer(32);
//                        this.collisionRule = Team.CollisionRule.NEVER.name();
//                        this.color = buf.readByte();
//                    }
//
//                    if (this.action == 0 || this.action == 3 || this.action == 4) {
//                        int i = buf.readVarIntFromBuffer();
//
//                        for (int j = 0; j < i; ++j) {
//                            this.players.add(buf.readStringFromBuffer(40));
//                        }
//                    }
//                }
//            }.getClass())
//            .put(SPacketSpawnGlobalEntity.class, new SPacketChat() {
//                @Override
//                public void readPacketData(PacketBuffer buf) throws IOException {
//                    this.chatComponent = ITextComponent.Serializer.fromJsonLenient(buf.readStringFromBuffer(32767));
//                    this.type = buf.readByte();
//                }
//            }.getClass())
//            .put(SPacketEffect.class, new SPacketChunkData() {
//                @Override
//                public void readPacketData(PacketBuffer buf) throws IOException {
//                    this.chunkX = buf.readInt();
//                    this.chunkZ = buf.readInt();
//                    this.loadChunk = buf.readBoolean();
//                    this.availableSections = buf.readShort();
//                    this.buffer = buf.readByteArray();
//                }
//            }.getClass())
//            .put(SPacketEntity.S17PacketEntityLookMove.class, new Packet<INetHandlerPlayClient>() {
//                private int[] xPositions;
//                private int[] zPositions;
//                private int[] chunkSizes;
//                private byte[][] chunksData;
//                private boolean isOverworld;
//
//                @Override
//                public void writePacketData(PacketBuffer buf) throws IOException {
//                    throw new UnsupportedOperationException("This packet cannot have data written to it.");
//                }
//
//                @Override
//                public void processPacket(INetHandlerPlayClient handler) {
//                    for (int i = 0; i < chunksData.length; i++) {
//                        final PacketBuffer buf = new PacketBuffer(Unpooled.directBuffer(Integer.BYTES * 2 + 1 + Short.BYTES + chunksData[i].length));
//
//                        buf.writeInt(xPositions[i]);
//                        buf.writeInt(zPositions[i]);
//                        buf.writeBoolean(isOverworld);
//                        buf.writeShort((short)(chunkSizes[i] & 65535));
//                        buf.writeByteArray(chunksData[i]);
//
//                        final SPacketChunkData newPacket = new SPacketChunkData() {
//                            @Override
//                            public void readPacketData(PacketBuffer buf) throws IOException {
//                                this.chunkX = buf.readInt();
//                                this.chunkZ = buf.readInt();
//                                this.loadChunk = buf.readBoolean();
//                                this.availableSections = buf.readShort();
//                                this.buffer = buf.readByteArray();
//                            }
//                        };
//                        try {
//                            newPacket.readPacketData(buf);
//                            handler.handleChunkData(newPacket);
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
//
//                @Override
//                public void readPacketData(PacketBuffer buf) throws IOException {
//                    this.isOverworld = buf.readBoolean();
//                    int i = buf.readVarIntFromBuffer();
//                    this.xPositions = new int[i];
//                    this.zPositions = new int[i];
//                    this.chunkSizes = new int[i];
//                    this.chunksData = new byte[i][];
//
//                    for (int j = 0; j < i; ++j) {
//                        this.xPositions[j] = buf.readInt();
//                        this.zPositions[j] = buf.readInt();
//                        this.chunkSizes[j] = buf.readShort() & 65535;
//                        this.chunksData[j] = new byte[func_180737_a(Integer.bitCount(this.chunkSizes[j]), this.isOverworld, true)];
//                    }
//
//                    for (int k = 0; k < i; ++k) {
//                        buf.readBytes(this.chunksData[k]);
//                    }
//                }
//
//                private int func_180737_a(int p_180737_0_, boolean p_180737_1_, boolean p_180737_2_)
//                {
//                    int i = p_180737_0_ * 2 * 16 * 16 * 16;
//                    int j = p_180737_0_ * 16 * 16 * 16 / 2;
//                    int k = p_180737_1_ ? p_180737_0_ * 16 * 16 * 16 / 2 : 0;
//                    int l = p_180737_2_ ? 256 : 0;
//                    return i + j + k + l;
//                }
//
//            }.getClass())
//            .put(SPacketBlockBreakAnim.class, new SPacketPlayerPosLook() {
//                @Override
//                public void readPacketData(PacketBuffer buf) throws IOException {
//                    this.x = buf.readDouble();
//                    this.y = buf.readDouble();
//                    this.z = buf.readDouble();
//                    this.yaw = buf.readFloat();
//                    this.pitch = buf.readFloat();
//                    this.flags = SPacketPlayerPosLook.EnumFlags.unpack(buf.readUnsignedByte());
//                    this.teleportId = 0;
//                }
//            }.getClass())
//            .put(SPacketTimeUpdate.class, SPacketWorldBorder.class)
//            .put(SPacketSpawnMob.class, SPacketTimeUpdate.class)
//            .put(SPacketDestroyEntities.class, SPacketWindowItems.class)
//            .put(SPacketUseBed.class, SPacketSetSlot.class)
//            .put(SPacketRespawn.class, new SPacketUpdateSign() {
//                @Override
//                public void readPacketData(PacketBuffer buf) throws IOException {
//                    this.blockPos = buf.readBlockPos();
//                    this.lines = new ITextComponent[4];
//
//                    for (int i = 0; i < 4; ++i) {
////                        TODO: Make a legacy IChatComponent Deserializer
//                        this.lines[i] = ITextComponent.Serializer.fromJsonLenient(buf.readStringFromBuffer(32767));
//                    }
//                    buf.clear();
//                }
//            }.getClass())
//            .put(SPacketSoundEffect.class, SPacketPlayerListHeaderFooter.class)
//            .put(SPacketChunkData.class, SPacketEntityProperties.class)
//            .put(SPacketWorldBorder.class, SPacketUpdateTileEntity.class)
//            .build();
//
//    private static final Map<Class<? extends Packet>, OutboundPacketTuple> serverboundPacketMap = new ImmutableMap.Builder<Class<? extends Packet>, OutboundPacketTuple>()
//            .put(SPacketKeepAlive.class, new OutboundPacketTuple(0, SPacketKeepAlive.class))
//            .build();
//
//    public AdapterVersion47() {
//        super(47, "1.8.8");
//    }
//
//    @Listener
//    protected Link<EventDecodePacket> onDecodePacket = new Link<>(event -> {
//        if (event.getPacket() != null && event.getState() == EnumConnectionState.PLAY) {
//            final Class<? extends Packet> packetLookup = clientboundPacketMap.get(event.getPacket().getClass());
//            if (packetLookup != null) {
//                event.setPacket(ClassFactory.create(packetLookup));
//            }
//        }
//    });
//
//    @Listener
//    protected Link<EventEncodePacket> onEncodePacket = new Link<>(event -> {
//        if (event.getState() == EnumConnectionState.PLAY) {
//            final OutboundPacketTuple tupleLookup = serverboundPacketMap.get(event.getPacket().getClass());
//            if (tupleLookup != null) {
//                event.setPacketID(tupleLookup.getPacketID());
//                event.setPacket(ClassFactory.create(tupleLookup.getPacket()));
//            }
//        }
//    });
//
//    private static class OutboundPacketTuple {
//        private final int packetID;
//        private final Class<? extends Packet<INetHandlerPlayClient>> packet;
//
//        OutboundPacketTuple(int packetID, Class<? extends Packet<INetHandlerPlayClient>> packet) {
//            this.packetID = packetID;
//            this.packet = packet;
//        }
//
//        int getPacketID() {
//            return this.packetID;
//        }
//
//        Class<? extends Packet<INetHandlerPlayClient>> getPacket() {
//            return this.packet;
//        }
//    }
//}
