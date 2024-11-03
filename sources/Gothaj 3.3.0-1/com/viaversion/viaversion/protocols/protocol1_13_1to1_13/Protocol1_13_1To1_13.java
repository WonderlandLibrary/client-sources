package com.viaversion.viaversion.protocols.protocol1_13_1to1_13;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.data.MappingData;
import com.viaversion.viaversion.api.data.MappingDataBase;
import com.viaversion.viaversion.api.minecraft.ClientWorld;
import com.viaversion.viaversion.api.minecraft.RegistryType;
import com.viaversion.viaversion.api.minecraft.entities.EntityTypes1_13;
import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.AbstractProtocol;
import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.protocol.remapper.ValueTransformer;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.data.entity.EntityTrackerBase;
import com.viaversion.viaversion.protocols.protocol1_13_1to1_13.metadata.MetadataRewriter1_13_1To1_13;
import com.viaversion.viaversion.protocols.protocol1_13_1to1_13.packets.EntityPackets;
import com.viaversion.viaversion.protocols.protocol1_13_1to1_13.packets.InventoryPackets;
import com.viaversion.viaversion.protocols.protocol1_13_1to1_13.packets.WorldPackets;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ClientboundPackets1_13;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ServerboundPackets1_13;
import com.viaversion.viaversion.rewriter.StatisticsRewriter;
import com.viaversion.viaversion.rewriter.TagRewriter;

public class Protocol1_13_1To1_13 extends AbstractProtocol<ClientboundPackets1_13, ClientboundPackets1_13, ServerboundPackets1_13, ServerboundPackets1_13> {
   public static final MappingData MAPPINGS = new MappingDataBase("1.13", "1.13.2");
   private final MetadataRewriter1_13_1To1_13 entityRewriter = new MetadataRewriter1_13_1To1_13(this);
   private final InventoryPackets itemRewriter = new InventoryPackets(this);

   public Protocol1_13_1To1_13() {
      super(ClientboundPackets1_13.class, ClientboundPackets1_13.class, ServerboundPackets1_13.class, ServerboundPackets1_13.class);
   }

   @Override
   protected void registerPackets() {
      this.entityRewriter.register();
      this.itemRewriter.register();
      EntityPackets.register(this);
      WorldPackets.register(this);
      this.registerServerbound(ServerboundPackets1_13.TAB_COMPLETE, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.VAR_INT);
            this.map(Type.STRING, new ValueTransformer<String, String>(Type.STRING) {
               public String transform(PacketWrapper wrapper, String inputValue) {
                  return inputValue.startsWith("/") ? inputValue.substring(1) : inputValue;
               }
            });
         }
      });
      this.registerServerbound(ServerboundPackets1_13.EDIT_BOOK, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.ITEM1_13);
            this.map(Type.BOOLEAN);
            this.handler(wrapper -> {
               Item item = wrapper.get(Type.ITEM1_13, 0);
               Protocol1_13_1To1_13.this.itemRewriter.handleItemToServer(item);
            });
            this.handler(wrapper -> {
               int hand = wrapper.read(Type.VAR_INT);
               if (hand == 1) {
                  wrapper.cancel();
               }
            });
         }
      });
      this.registerClientbound(ClientboundPackets1_13.TAB_COMPLETE, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.VAR_INT);
            this.map(Type.VAR_INT);
            this.map(Type.VAR_INT);
            this.map(Type.VAR_INT);
            this.handler(wrapper -> {
               int start = wrapper.get(Type.VAR_INT, 1);
               wrapper.set(Type.VAR_INT, 1, start + 1);
               int count = wrapper.get(Type.VAR_INT, 3);

               for (int i = 0; i < count; i++) {
                  wrapper.passthrough(Type.STRING);
                  wrapper.passthrough(Type.OPTIONAL_COMPONENT);
               }
            });
         }
      });
      this.registerClientbound(ClientboundPackets1_13.BOSSBAR, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.UUID);
            this.map(Type.VAR_INT);
            this.handler(wrapper -> {
               int action = wrapper.get(Type.VAR_INT, 0);
               if (action == 0) {
                  wrapper.passthrough(Type.COMPONENT);
                  wrapper.passthrough(Type.FLOAT);
                  wrapper.passthrough(Type.VAR_INT);
                  wrapper.passthrough(Type.VAR_INT);
                  short flags = (short)wrapper.read(Type.BYTE).byteValue();
                  if ((flags & 2) != 0) {
                     flags = (short)(flags | 4);
                  }

                  wrapper.write(Type.UNSIGNED_BYTE, flags);
               }
            });
         }
      });
      new TagRewriter<>(this).register(ClientboundPackets1_13.TAGS, RegistryType.ITEM);
      new StatisticsRewriter<>(this).register(ClientboundPackets1_13.STATISTICS);
   }

   @Override
   public void init(UserConnection userConnection) {
      userConnection.addEntityTracker((Class<? extends Protocol>)this.getClass(), new EntityTrackerBase(userConnection, EntityTypes1_13.EntityType.PLAYER));
      if (!userConnection.has(ClientWorld.class)) {
         userConnection.put(new ClientWorld());
      }
   }

   @Override
   public MappingData getMappingData() {
      return MAPPINGS;
   }

   public MetadataRewriter1_13_1To1_13 getEntityRewriter() {
      return this.entityRewriter;
   }

   public InventoryPackets getItemRewriter() {
      return this.itemRewriter;
   }
}
