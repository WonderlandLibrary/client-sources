package com.viaversion.viaversion.protocols.protocol1_10to1_9_3;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.ClientWorld;
import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
import com.viaversion.viaversion.api.minecraft.chunks.PaletteType;
import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
import com.viaversion.viaversion.api.protocol.AbstractProtocol;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.protocol.remapper.ValueTransformer;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.chunk.ChunkType1_9_3;
import com.viaversion.viaversion.api.type.types.version.Types1_9;
import com.viaversion.viaversion.protocols.protocol1_10to1_9_3.packets.InventoryPackets;
import com.viaversion.viaversion.protocols.protocol1_10to1_9_3.storage.ResourcePackTracker;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.ClientboundPackets1_9_3;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.ServerboundPackets1_9_3;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Protocol1_10To1_9_3_4 extends AbstractProtocol<ClientboundPackets1_9_3, ClientboundPackets1_9_3, ServerboundPackets1_9_3, ServerboundPackets1_9_3> {
   public static final ValueTransformer<Short, Float> TO_NEW_PITCH = new ValueTransformer<Short, Float>(Type.FLOAT) {
      public Float transform(PacketWrapper wrapper, Short inputValue) throws Exception {
         return (float)inputValue.shortValue() / 63.0F;
      }
   };
   public static final ValueTransformer<List<Metadata>, List<Metadata>> TRANSFORM_METADATA = new ValueTransformer<List<Metadata>, List<Metadata>>(
      Types1_9.METADATA_LIST
   ) {
      public List<Metadata> transform(PacketWrapper wrapper, List<Metadata> inputValue) throws Exception {
         List<Metadata> metaList = new CopyOnWriteArrayList<>(inputValue);

         for (Metadata m : metaList) {
            if (m.id() >= 5) {
               m.setId(m.id() + 1);
            }
         }

         return metaList;
      }
   };
   private final InventoryPackets itemRewriter = new InventoryPackets(this);

   public Protocol1_10To1_9_3_4() {
      super(ClientboundPackets1_9_3.class, ClientboundPackets1_9_3.class, ServerboundPackets1_9_3.class, ServerboundPackets1_9_3.class);
   }

   @Override
   protected void registerPackets() {
      this.itemRewriter.register();
      this.registerClientbound(ClientboundPackets1_9_3.NAMED_SOUND, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.STRING);
            this.map(Type.VAR_INT);
            this.map(Type.INT);
            this.map(Type.INT);
            this.map(Type.INT);
            this.map(Type.FLOAT);
            this.map(Type.UNSIGNED_BYTE, Protocol1_10To1_9_3_4.TO_NEW_PITCH);
         }
      });
      this.registerClientbound(ClientboundPackets1_9_3.SOUND, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.VAR_INT);
            this.map(Type.VAR_INT);
            this.map(Type.INT);
            this.map(Type.INT);
            this.map(Type.INT);
            this.map(Type.FLOAT);
            this.map(Type.UNSIGNED_BYTE, Protocol1_10To1_9_3_4.TO_NEW_PITCH);
            this.handler(wrapper -> {
               int id = wrapper.get(Type.VAR_INT, 0);
               wrapper.set(Type.VAR_INT, 0, Protocol1_10To1_9_3_4.this.getNewSoundId(id));
            });
         }
      });
      this.registerClientbound(ClientboundPackets1_9_3.ENTITY_METADATA, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.VAR_INT);
            this.map(Types1_9.METADATA_LIST, Protocol1_10To1_9_3_4.TRANSFORM_METADATA);
         }
      });
      this.registerClientbound(ClientboundPackets1_9_3.SPAWN_MOB, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.VAR_INT);
            this.map(Type.UUID);
            this.map(Type.UNSIGNED_BYTE);
            this.map(Type.DOUBLE);
            this.map(Type.DOUBLE);
            this.map(Type.DOUBLE);
            this.map(Type.BYTE);
            this.map(Type.BYTE);
            this.map(Type.BYTE);
            this.map(Type.SHORT);
            this.map(Type.SHORT);
            this.map(Type.SHORT);
            this.map(Types1_9.METADATA_LIST, Protocol1_10To1_9_3_4.TRANSFORM_METADATA);
         }
      });
      this.registerClientbound(ClientboundPackets1_9_3.SPAWN_PLAYER, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.VAR_INT);
            this.map(Type.UUID);
            this.map(Type.DOUBLE);
            this.map(Type.DOUBLE);
            this.map(Type.DOUBLE);
            this.map(Type.BYTE);
            this.map(Type.BYTE);
            this.map(Types1_9.METADATA_LIST, Protocol1_10To1_9_3_4.TRANSFORM_METADATA);
         }
      });
      this.registerClientbound(ClientboundPackets1_9_3.JOIN_GAME, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.INT);
            this.map(Type.UNSIGNED_BYTE);
            this.map(Type.INT);
            this.handler(wrapper -> {
               ClientWorld clientWorld = wrapper.user().get(ClientWorld.class);
               int dimensionId = wrapper.get(Type.INT, 1);
               clientWorld.setEnvironment(dimensionId);
            });
         }
      });
      this.registerClientbound(ClientboundPackets1_9_3.RESPAWN, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.INT);
            this.handler(wrapper -> {
               ClientWorld clientWorld = wrapper.user().get(ClientWorld.class);
               int dimensionId = wrapper.get(Type.INT, 0);
               clientWorld.setEnvironment(dimensionId);
            });
         }
      });
      this.registerClientbound(ClientboundPackets1_9_3.CHUNK_DATA, wrapper -> {
         ClientWorld clientWorld = wrapper.user().get(ClientWorld.class);
         Chunk chunk = wrapper.passthrough(ChunkType1_9_3.forEnvironment(clientWorld.getEnvironment()));
         if (Via.getConfig().isReplacePistons()) {
            int replacementId = Via.getConfig().getPistonReplacementId();

            for (ChunkSection section : chunk.getSections()) {
               if (section != null) {
                  section.palette(PaletteType.BLOCKS).replaceId(36, replacementId);
               }
            }
         }
      });
      this.registerClientbound(ClientboundPackets1_9_3.RESOURCE_PACK, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.STRING);
            this.map(Type.STRING);
            this.handler(wrapper -> {
               ResourcePackTracker tracker = wrapper.user().get(ResourcePackTracker.class);
               tracker.setLastHash(wrapper.get(Type.STRING, 1));
            });
         }
      });
      this.registerServerbound(ServerboundPackets1_9_3.RESOURCE_PACK_STATUS, new PacketHandlers() {
         @Override
         public void register() {
            this.handler(wrapper -> {
               ResourcePackTracker tracker = wrapper.user().get(ResourcePackTracker.class);
               wrapper.write(Type.STRING, tracker.getLastHash());
               wrapper.write(Type.VAR_INT, wrapper.read(Type.VAR_INT));
            });
         }
      });
   }

   public int getNewSoundId(int id) {
      int newId = id;
      if (id >= 24) {
         newId = id + 1;
      }

      if (id >= 248) {
         newId += 4;
      }

      if (id >= 296) {
         newId += 6;
      }

      if (id >= 354) {
         newId += 4;
      }

      if (id >= 372) {
         newId += 4;
      }

      return newId;
   }

   @Override
   public void init(UserConnection userConnection) {
      userConnection.put(new ResourcePackTracker());
      if (!userConnection.has(ClientWorld.class)) {
         userConnection.put(new ClientWorld());
      }
   }

   public InventoryPackets getItemRewriter() {
      return this.itemRewriter;
   }
}
