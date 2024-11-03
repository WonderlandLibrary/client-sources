package com.viaversion.viabackwards.protocol.protocol1_9_4to1_10.packets;

import com.viaversion.viabackwards.api.rewriters.LegacyBlockItemRewriter;
import com.viaversion.viabackwards.protocol.protocol1_9_4to1_10.Protocol1_9_4To1_10;
import com.viaversion.viaversion.api.minecraft.BlockChangeRecord;
import com.viaversion.viaversion.api.minecraft.ClientWorld;
import com.viaversion.viaversion.api.minecraft.chunks.Chunk;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.chunk.ChunkType1_9_3;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.ClientboundPackets1_9_3;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.ServerboundPackets1_9_3;

public class BlockItemPackets1_10 extends LegacyBlockItemRewriter<ClientboundPackets1_9_3, ServerboundPackets1_9_3, Protocol1_9_4To1_10> {
   public BlockItemPackets1_10(Protocol1_9_4To1_10 protocol) {
      super(protocol);
   }

   @Override
   protected void registerPackets() {
      this.registerSetSlot(ClientboundPackets1_9_3.SET_SLOT, Type.ITEM1_8);
      this.registerWindowItems(ClientboundPackets1_9_3.WINDOW_ITEMS, Type.ITEM1_8_SHORT_ARRAY);
      this.registerEntityEquipment(ClientboundPackets1_9_3.ENTITY_EQUIPMENT, Type.ITEM1_8);
      this.protocol.registerClientbound(ClientboundPackets1_9_3.PLUGIN_MESSAGE, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.STRING);
            this.handler(wrapper -> {
               if (wrapper.get(Type.STRING, 0).equalsIgnoreCase("MC|TrList")) {
                  wrapper.passthrough(Type.INT);
                  int size = wrapper.passthrough(Type.UNSIGNED_BYTE);

                  for (int i = 0; i < size; i++) {
                     wrapper.write(Type.ITEM1_8, BlockItemPackets1_10.this.handleItemToClient(wrapper.read(Type.ITEM1_8)));
                     wrapper.write(Type.ITEM1_8, BlockItemPackets1_10.this.handleItemToClient(wrapper.read(Type.ITEM1_8)));
                     boolean secondItem = wrapper.passthrough(Type.BOOLEAN);
                     if (secondItem) {
                        wrapper.write(Type.ITEM1_8, BlockItemPackets1_10.this.handleItemToClient(wrapper.read(Type.ITEM1_8)));
                     }

                     wrapper.passthrough(Type.BOOLEAN);
                     wrapper.passthrough(Type.INT);
                     wrapper.passthrough(Type.INT);
                  }
               }
            });
         }
      });
      this.registerClickWindow(ServerboundPackets1_9_3.CLICK_WINDOW, Type.ITEM1_8);
      this.registerCreativeInvAction(ServerboundPackets1_9_3.CREATIVE_INVENTORY_ACTION, Type.ITEM1_8);
      this.protocol.registerClientbound(ClientboundPackets1_9_3.CHUNK_DATA, wrapper -> {
         ClientWorld clientWorld = wrapper.user().get(ClientWorld.class);
         ChunkType1_9_3 type = ChunkType1_9_3.forEnvironment(clientWorld.getEnvironment());
         Chunk chunk = wrapper.passthrough(type);
         this.handleChunk(chunk);
      });
      this.protocol.registerClientbound(ClientboundPackets1_9_3.BLOCK_CHANGE, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.POSITION1_8);
            this.map(Type.VAR_INT);
            this.handler(wrapper -> {
               int idx = wrapper.get(Type.VAR_INT, 0);
               wrapper.set(Type.VAR_INT, 0, BlockItemPackets1_10.this.handleBlockID(idx));
            });
         }
      });
      this.protocol.registerClientbound(ClientboundPackets1_9_3.MULTI_BLOCK_CHANGE, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.INT);
            this.map(Type.INT);
            this.map(Type.BLOCK_CHANGE_RECORD_ARRAY);
            this.handler(wrapper -> {
               for (BlockChangeRecord record : wrapper.get(Type.BLOCK_CHANGE_RECORD_ARRAY, 0)) {
                  record.setBlockId(BlockItemPackets1_10.this.handleBlockID(record.getBlockId()));
               }
            });
         }
      });
      this.protocol.getEntityRewriter().filter().handler((event, meta) -> {
         if (meta.metaType().type().equals(Type.ITEM1_8)) {
            meta.setValue(this.handleItemToClient((Item)meta.getValue()));
         }
      });
      this.protocol.registerClientbound(ClientboundPackets1_9_3.SPAWN_PARTICLE, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.INT);
            this.map(Type.BOOLEAN);
            this.map(Type.FLOAT);
            this.map(Type.FLOAT);
            this.map(Type.FLOAT);
            this.map(Type.FLOAT);
            this.map(Type.FLOAT);
            this.map(Type.FLOAT);
            this.map(Type.FLOAT);
            this.map(Type.INT);
            this.handler(wrapper -> {
               int id = wrapper.get(Type.INT, 0);
               if (id == 46) {
                  wrapper.set(Type.INT, 0, 38);
               }
            });
         }
      });
   }
}
