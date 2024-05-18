//package us.dev.direkt.module.internal.core.protocol.adapter.adapters;
//
//import java.io.IOException;
//import java.util.Iterator;
//import java.util.Map;
//import java.util.concurrent.ConcurrentMap;
//
//import com.google.common.collect.Maps;
//
//import net.minecraft.entity.EntityList;
//import net.minecraft.entity.EntityLivingBase;
//import net.minecraft.nbt.NBTTagCompound;
//import net.minecraft.network.EnumConnectionState;
//import net.minecraft.network.EnumPacketDirection;
//import net.minecraft.network.PacketBuffer;
//import net.minecraft.network.play.client.CPacketPlayer;
//import net.minecraft.network.play.server.SPacketChunkData;
//import net.minecraft.network.play.server.SPacketDestroyEntities;
//import net.minecraft.network.play.server.SPacketEntity;
//import net.minecraft.network.play.server.SPacketEntityStatus;
//import net.minecraft.network.play.server.SPacketEntityTeleport;
//import net.minecraft.network.play.server.SPacketJoinGame;
//import net.minecraft.network.play.server.SPacketSpawnMob;
//import net.minecraft.network.play.server.SPacketTeams;
//import net.minecraft.network.status.client.CPacketPing;
//import net.minecraft.tileentity.TileEntity;
//import net.minecraft.util.math.BlockPos;
//import net.minecraft.world.EnumDifficulty;
//import net.minecraft.world.WorldSettings;
//import net.minecraft.world.WorldType;
//import us.dev.direkt.Wrapper;
//import us.dev.direkt.event.internal.events.game.network.EventDecodePacket;
//import us.dev.direkt.event.internal.events.game.world.EventWorldUpdate;
//import us.dev.direkt.module.internal.core.protocol.adapter.ProtocolAdapter;
//import us.dev.dvent.Link;
//import us.dev.dvent.Listener;
//
///**
// * @author Foundry
// */
//public class AdapterVersion210 extends ProtocolAdapter {
//    public AdapterVersion210() {
//        super(210, "1.10");
//    }
//
//    // TODO: new mobs need to be blocked from spawning or changed to something else
//    
//    
//    private ConcurrentMap<BlockPos, NBTTagCompound> postLoadNBTMap = Maps.newConcurrentMap();
//
//    @Listener
//    protected Link<EventDecodePacket> onDecodePacket = new Link<>(event -> {
//        Integer packetID;
//        if (event.getPacket() instanceof SPacketJoinGame) {
//            event.setPacket(new SPacketJoinGame() {
//                @Override
//                public void readPacketData(PacketBuffer buf) throws IOException {
//                    this.playerId = buf.readInt();
//                    int unsignedByte = buf.readUnsignedByte();
//                    this.hardcoreMode = (unsignedByte & 0x8) == 0x8;
//                    this.gameType = WorldSettings.GameType.getByID(unsignedByte & 0xFFFFFFF7);
//                    this.dimension = buf.readInt();
//                    this.difficulty = EnumDifficulty.getDifficultyEnum(buf.readUnsignedByte());
//                    this.maxPlayers = buf.readUnsignedByte();
//                    this.worldType = WorldType.parseWorldType(buf.readStringFromBuffer(16));
//
//                    if (this.worldType == null) {
//                        this.worldType = WorldType.DEFAULT;
//                    }
//
//                    this.reducedDebugInfo = buf.readBoolean();
//                }
//            });
//        } else if (event.getPacket() instanceof SPacketChunkData) {
//            event.setPacket(new SPacketChunkData() {
//
//                @Override
//                public void readPacketData(PacketBuffer buf) throws IOException {
//                    this.chunkX = buf.readInt();
//                    this.chunkZ = buf.readInt();
//                    this.loadChunk = buf.readBoolean();
//                    this.availableSections = buf.readVarIntFromBuffer();
//                    int i = buf.readVarIntFromBuffer();
//
//                    if (i > 2097152) {
//                        throw new RuntimeException("Chunk Packet trying to allocate too much memory on read.");
//                    } else {
//                        buf.readBytes(this.buffer = new byte[i]);
//                        final int i2 = buf.readVarIntFromBuffer();
//
//                        for (int j = 0; j < i2; ++j) {
//                            final NBTTagCompound tags = buf.readNBTTagCompoundFromBuffer();
//                            postLoadNBTMap.put(new BlockPos(tags.getInteger("x"), tags.getInteger("y"), tags.getInteger("z")), tags);
//                        }
//                    }
//                }
//            });
//        } else if ((packetID = EnumConnectionState.PLAY.getPacketId(EnumPacketDirection.CLIENTBOUND, event.getPacket())) != null && packetID >= 70) {
//            try {
//                event.setPacket(EnumConnectionState.PLAY.getPacket(EnumPacketDirection.CLIENTBOUND, packetID+1));
//            } catch (InstantiationException | IllegalAccessException e) {
//                e.printStackTrace();
//            }
//            /*
//             * Entities spawning crashes the client
//             */
//        } else if(event.getPacket() instanceof SPacketSpawnMob || event.getPacket() instanceof SPacketEntity || event.getPacket() instanceof SPacketEntityTeleport || event.getPacket() instanceof SPacketDestroyEntities) {
//        		event.setCancelled(true);
//        } 
//    });
//
//    @Listener
//    protected Link<EventWorldUpdate> onWorldUpdate = new Link<>(event -> {
//        for (Iterator<Map.Entry<BlockPos, NBTTagCompound>> it = postLoadNBTMap.entrySet().iterator(); it.hasNext();) {
//            final Map.Entry<BlockPos, NBTTagCompound> next = it.next();
//            final TileEntity entity = Wrapper.getWorld().getTileEntity(next.getKey());
//            if (entity != null) {
//                entity.readFromNBT(next.getValue());
//                it.remove();
//            }
//        }
//    });
//
//}
