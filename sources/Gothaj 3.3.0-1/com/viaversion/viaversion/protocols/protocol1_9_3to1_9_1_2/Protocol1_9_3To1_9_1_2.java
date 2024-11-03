package com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.ClientWorld;
import com.viaversion.viaversion.api.minecraft.Position;
import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
import com.viaversion.viaversion.api.minecraft.chunks.ChunkSection;
import com.viaversion.viaversion.api.minecraft.chunks.DataPalette;
import com.viaversion.viaversion.api.minecraft.chunks.PaletteType;
import com.viaversion.viaversion.api.protocol.AbstractProtocol;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.protocol.remapper.ValueTransformer;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.chunk.ChunkType1_9_1;
import com.viaversion.viaversion.api.type.types.chunk.ChunkType1_9_3;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.IntTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.chunks.FakeTileEntity;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.ClientboundPackets1_9;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.ServerboundPackets1_9;
import java.util.List;

public class Protocol1_9_3To1_9_1_2 extends AbstractProtocol<ClientboundPackets1_9, ClientboundPackets1_9_3, ServerboundPackets1_9, ServerboundPackets1_9_3> {
   public static final ValueTransformer<Short, Short> ADJUST_PITCH = new ValueTransformer<Short, Short>(Type.UNSIGNED_BYTE, Type.UNSIGNED_BYTE) {
      public Short transform(PacketWrapper wrapper, Short inputValue) throws Exception {
         return (short)Math.round((float)inputValue.shortValue() / 63.5F * 63.0F);
      }
   };

   public Protocol1_9_3To1_9_1_2() {
      super(ClientboundPackets1_9.class, ClientboundPackets1_9_3.class, ServerboundPackets1_9.class, ServerboundPackets1_9_3.class);
   }

   @Override
   protected void registerPackets() {
      this.registerClientbound(ClientboundPackets1_9.UPDATE_SIGN, null, wrapper -> {
         Position position = wrapper.read(Type.POSITION1_8);
         JsonElement[] lines = new JsonElement[4];

         for (int i = 0; i < 4; i++) {
            lines[i] = wrapper.read(Type.COMPONENT);
         }

         wrapper.clearInputBuffer();
         wrapper.setPacketType(ClientboundPackets1_9_3.BLOCK_ENTITY_DATA);
         wrapper.write(Type.POSITION1_8, position);
         wrapper.write(Type.UNSIGNED_BYTE, (short)9);
         CompoundTag tag = new CompoundTag();
         tag.put("id", new StringTag("Sign"));
         tag.put("x", new IntTag(position.x()));
         tag.put("y", new IntTag(position.y()));
         tag.put("z", new IntTag(position.z()));

         for (int i = 0; i < lines.length; i++) {
            tag.put("Text" + (i + 1), new StringTag(lines[i].toString()));
         }

         wrapper.write(Type.NAMED_COMPOUND_TAG, tag);
      });
      this.registerClientbound(
         ClientboundPackets1_9.CHUNK_DATA,
         wrapper -> {
            ClientWorld clientWorld = wrapper.user().get(ClientWorld.class);
            Chunk chunk = wrapper.read(ChunkType1_9_1.forEnvironment(clientWorld.getEnvironment()));
            wrapper.write(ChunkType1_9_3.forEnvironment(clientWorld.getEnvironment()), chunk);
            List<CompoundTag> tags = chunk.getBlockEntities();

            for (int s = 0; s < chunk.getSections().length; s++) {
               ChunkSection section = chunk.getSections()[s];
               if (section != null) {
                  DataPalette blocks = section.palette(PaletteType.BLOCKS);

                  for (int idx = 0; idx < 4096; idx++) {
                     int id = blocks.idAt(idx) >> 4;
                     if (FakeTileEntity.isTileEntity(id)) {
                        tags.add(
                           FakeTileEntity.createTileEntity(
                              ChunkSection.xFromIndex(idx) + (chunk.getX() << 4),
                              ChunkSection.yFromIndex(idx) + (s << 4),
                              ChunkSection.zFromIndex(idx) + (chunk.getZ() << 4),
                              id
                           )
                        );
                     }
                  }
               }
            }
         }
      );
      this.registerClientbound(ClientboundPackets1_9.JOIN_GAME, new PacketHandlers() {
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
      this.registerClientbound(ClientboundPackets1_9.RESPAWN, new PacketHandlers() {
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
      this.registerClientbound(ClientboundPackets1_9.SOUND, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.VAR_INT);
            this.map(Type.VAR_INT);
            this.map(Type.INT);
            this.map(Type.INT);
            this.map(Type.INT);
            this.map(Type.FLOAT);
            this.map(Protocol1_9_3To1_9_1_2.ADJUST_PITCH);
         }
      });
   }

   @Override
   public void init(UserConnection user) {
      if (!user.has(ClientWorld.class)) {
         user.put(new ClientWorld(user));
      }
   }
}
