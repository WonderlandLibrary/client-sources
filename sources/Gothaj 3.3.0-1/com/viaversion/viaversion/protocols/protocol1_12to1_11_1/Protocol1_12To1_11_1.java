package com.viaversion.viaversion.protocols.protocol1_12to1_11_1;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.ClientWorld;
import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
import com.viaversion.viaversion.api.minecraft.chunks.DataPalette;
import com.viaversion.viaversion.api.minecraft.chunks.PaletteType;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_12;
import com.viaversion.viaversion.api.platform.providers.ViaProviders;
import com.viaversion.viaversion.api.protocol.AbstractProtocol;
import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.protocol.version.ProtocolVersion;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.chunk.ChunkType1_9_3;
import com.viaversion.viaversion.api.type.types.version.Types1_12;
import com.viaversion.viaversion.data.entity.EntityTrackerBase;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.IntTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
import com.viaversion.viaversion.protocols.protocol1_12to1_11_1.metadata.MetadataRewriter1_12To1_11_1;
import com.viaversion.viaversion.protocols.protocol1_12to1_11_1.packets.InventoryPackets;
import com.viaversion.viaversion.protocols.protocol1_12to1_11_1.providers.InventoryQuickMoveProvider;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ClientboundPackets1_13;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.Protocol1_13To1_12_2;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.ClientboundPackets1_9_3;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.ServerboundPackets1_9_3;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.Protocol1_9To1_8;
import com.viaversion.viaversion.rewriter.SoundRewriter;

public class Protocol1_12To1_11_1 extends AbstractProtocol<ClientboundPackets1_9_3, ClientboundPackets1_12, ServerboundPackets1_9_3, ServerboundPackets1_12> {
   private final MetadataRewriter1_12To1_11_1 metadataRewriter = new MetadataRewriter1_12To1_11_1(this);
   private final InventoryPackets itemRewriter = new InventoryPackets(this);

   public Protocol1_12To1_11_1() {
      super(ClientboundPackets1_9_3.class, ClientboundPackets1_12.class, ServerboundPackets1_9_3.class, ServerboundPackets1_12.class);
   }

   @Override
   protected void registerPackets() {
      this.metadataRewriter.register();
      this.itemRewriter.register();
      this.registerClientbound(ClientboundPackets1_9_3.SPAWN_ENTITY, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.VAR_INT);
            this.map(Type.UUID);
            this.map(Type.BYTE);
            this.handler(Protocol1_12To1_11_1.this.metadataRewriter.objectTrackerHandler());
         }
      });
      this.registerClientbound(ClientboundPackets1_9_3.SPAWN_MOB, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.VAR_INT);
            this.map(Type.UUID);
            this.map(Type.VAR_INT);
            this.map(Type.DOUBLE);
            this.map(Type.DOUBLE);
            this.map(Type.DOUBLE);
            this.map(Type.BYTE);
            this.map(Type.BYTE);
            this.map(Type.BYTE);
            this.map(Type.SHORT);
            this.map(Type.SHORT);
            this.map(Type.SHORT);
            this.map(Types1_12.METADATA_LIST);
            this.handler(Protocol1_12To1_11_1.this.metadataRewriter.trackerAndRewriterHandler(Types1_12.METADATA_LIST));
         }
      });
      this.registerClientbound(ClientboundPackets1_9_3.CHAT_MESSAGE, wrapper -> {
         if (Via.getConfig().is1_12NBTArrayFix()) {
            try {
               JsonElement obj = Protocol1_9To1_8.FIX_JSON.transform(null, wrapper.passthrough(Type.COMPONENT).toString());
               TranslateRewriter.toClient(obj, wrapper.user());
               ChatItemRewriter.toClient(obj, wrapper.user());
               wrapper.set(Type.COMPONENT, 0, obj);
            } catch (Exception var2) {
               var2.printStackTrace();
            }
         }
      });
      this.registerClientbound(ClientboundPackets1_9_3.CHUNK_DATA, wrapper -> {
         ClientWorld clientWorld = wrapper.user().get(ClientWorld.class);
         ChunkType1_9_3 type = ChunkType1_9_3.forEnvironment(clientWorld.getEnvironment());
         Chunk chunk = wrapper.passthrough(type);

         for (int s = 0; s < chunk.getSections().length; s++) {
            ChunkSection section = chunk.getSections()[s];
            if (section != null) {
               DataPalette blocks = section.palette(PaletteType.BLOCKS);

               for (int idx = 0; idx < 4096; idx++) {
                  int id = blocks.idAt(idx) >> 4;
                  if (id == 26) {
                     CompoundTag tag = new CompoundTag();
                     tag.put("color", new IntTag(14));
                     tag.put("x", new IntTag(ChunkSection.xFromIndex(idx) + (chunk.getX() << 4)));
                     tag.put("y", new IntTag(ChunkSection.yFromIndex(idx) + (s << 4)));
                     tag.put("z", new IntTag(ChunkSection.zFromIndex(idx) + (chunk.getZ() << 4)));
                     tag.put("id", new StringTag("minecraft:bed"));
                     chunk.getBlockEntities().add(tag);
                  }
               }
            }
         }
      });
      this.metadataRewriter.registerRemoveEntities(ClientboundPackets1_9_3.DESTROY_ENTITIES);
      this.metadataRewriter.registerMetadataRewriter(ClientboundPackets1_9_3.ENTITY_METADATA, Types1_12.METADATA_LIST);
      this.registerClientbound(
         ClientboundPackets1_9_3.JOIN_GAME,
         new PacketHandlers() {
            @Override
            public void register() {
               this.map(Type.INT);
               this.map(Type.UNSIGNED_BYTE);
               this.map(Type.INT);
               this.handler(
                  wrapper -> {
                     UserConnection user = wrapper.user();
                     ClientWorld clientChunks = user.get(ClientWorld.class);
                     int dimensionId = wrapper.get(Type.INT, 1);
                     clientChunks.setEnvironment(dimensionId);
                     if (user.getProtocolInfo().getProtocolVersion() >= ProtocolVersion.v1_13.getVersion()) {
                        wrapper.create(ClientboundPackets1_13.DECLARE_RECIPES, packetWrapper -> packetWrapper.write(Type.VAR_INT, 0))
                           .scheduleSend(Protocol1_13To1_12_2.class);
                     }
                  }
               );
            }
         }
      );
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
      new SoundRewriter<>(this, this::getNewSoundId).registerSound(ClientboundPackets1_9_3.SOUND);
      this.cancelServerbound(ServerboundPackets1_12.PREPARE_CRAFTING_GRID);
      this.registerServerbound(ServerboundPackets1_12.CLIENT_SETTINGS, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.STRING);
            this.map(Type.BYTE);
            this.map(Type.VAR_INT);
            this.map(Type.BOOLEAN);
            this.map(Type.UNSIGNED_BYTE);
            this.map(Type.VAR_INT);
            this.handler(wrapper -> {
               String locale = wrapper.get(Type.STRING, 0);
               if (locale.length() > 7) {
                  wrapper.set(Type.STRING, 0, locale.substring(0, 7));
               }
            });
         }
      });
      this.cancelServerbound(ServerboundPackets1_12.RECIPE_BOOK_DATA);
      this.cancelServerbound(ServerboundPackets1_12.ADVANCEMENT_TAB);
   }

   private int getNewSoundId(int id) {
      int newId = id;
      if (id >= 26) {
         newId = id + 2;
      }

      if (id >= 70) {
         newId += 4;
      }

      if (id >= 74) {
         newId++;
      }

      if (id >= 143) {
         newId += 3;
      }

      if (id >= 185) {
         newId++;
      }

      if (id >= 263) {
         newId += 7;
      }

      if (id >= 301) {
         newId += 33;
      }

      if (id >= 317) {
         newId += 2;
      }

      if (id >= 491) {
         newId += 3;
      }

      return newId;
   }

   @Override
   public void register(ViaProviders providers) {
      providers.register(InventoryQuickMoveProvider.class, new InventoryQuickMoveProvider());
   }

   @Override
   public void init(UserConnection userConnection) {
      userConnection.addEntityTracker((Class<? extends Protocol>)this.getClass(), new EntityTrackerBase(userConnection, EntityTypes1_12.EntityType.PLAYER));
      if (!userConnection.has(ClientWorld.class)) {
         userConnection.put(new ClientWorld());
      }
   }

   public MetadataRewriter1_12To1_11_1 getEntityRewriter() {
      return this.metadataRewriter;
   }

   public InventoryPackets getItemRewriter() {
      return this.itemRewriter;
   }
}
