package com.viaversion.viaversion.protocols.protocol1_18to1_17_1.packets;

import com.viaversion.viaversion.api.data.ParticleMappings;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandlers;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocols.protocol1_17_1to1_17.ClientboundPackets1_17_1;
import com.viaversion.viaversion.protocols.protocol1_17to1_16_4.ServerboundPackets1_17;
import com.viaversion.viaversion.protocols.protocol1_18to1_17_1.Protocol1_18To1_17_1;
import com.viaversion.viaversion.rewriter.ItemRewriter;
import com.viaversion.viaversion.rewriter.RecipeRewriter;

public final class InventoryPackets extends ItemRewriter<ClientboundPackets1_17_1, ServerboundPackets1_17, Protocol1_18To1_17_1> {
   public InventoryPackets(Protocol1_18To1_17_1 protocol) {
      super(protocol, Type.ITEM1_13_2, Type.ITEM1_13_2_ARRAY);
   }

   @Override
   public void registerPackets() {
      this.registerSetCooldown(ClientboundPackets1_17_1.COOLDOWN);
      this.registerWindowItems1_17_1(ClientboundPackets1_17_1.WINDOW_ITEMS);
      this.registerTradeList(ClientboundPackets1_17_1.TRADE_LIST);
      this.registerSetSlot1_17_1(ClientboundPackets1_17_1.SET_SLOT);
      this.registerAdvancements(ClientboundPackets1_17_1.ADVANCEMENTS, Type.ITEM1_13_2);
      this.registerEntityEquipmentArray(ClientboundPackets1_17_1.ENTITY_EQUIPMENT);
      this.protocol.registerClientbound(ClientboundPackets1_17_1.EFFECT, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.INT);
            this.map(Type.POSITION1_14);
            this.map(Type.INT);
            this.handler(wrapper -> {
               int id = wrapper.get(Type.INT, 0);
               int data = wrapper.get(Type.INT, 1);
               if (id == 1010) {
                  wrapper.set(Type.INT, 1, InventoryPackets.this.protocol.getMappingData().getNewItemId(data));
               }
            });
         }
      });
      this.protocol.registerClientbound(ClientboundPackets1_17_1.SPAWN_PARTICLE, new PacketHandlers() {
         @Override
         public void register() {
            this.map(Type.INT);
            this.map(Type.BOOLEAN);
            this.map(Type.DOUBLE);
            this.map(Type.DOUBLE);
            this.map(Type.DOUBLE);
            this.map(Type.FLOAT);
            this.map(Type.FLOAT);
            this.map(Type.FLOAT);
            this.map(Type.FLOAT);
            this.map(Type.INT);
            this.handler(wrapper -> {
               int id = wrapper.get(Type.INT, 0);
               if (id == 2) {
                  wrapper.set(Type.INT, 0, 3);
                  wrapper.write(Type.VAR_INT, 7754);
               } else if (id == 3) {
                  wrapper.write(Type.VAR_INT, 7786);
               } else {
                  ParticleMappings mappings = InventoryPackets.this.protocol.getMappingData().getParticleMappings();
                  if (mappings.isBlockParticle(id)) {
                     int data = wrapper.passthrough(Type.VAR_INT);
                     wrapper.set(Type.VAR_INT, 0, InventoryPackets.this.protocol.getMappingData().getNewBlockStateId(data));
                  } else if (mappings.isItemParticle(id)) {
                     InventoryPackets.this.handleItemToClient(wrapper.passthrough(Type.ITEM1_13_2));
                  }

                  int newId = InventoryPackets.this.protocol.getMappingData().getNewParticleId(id);
                  if (newId != id) {
                     wrapper.set(Type.INT, 0, newId);
                  }
               }
            });
         }
      });
      new RecipeRewriter<>(this.protocol).register(ClientboundPackets1_17_1.DECLARE_RECIPES);
      this.registerClickWindow1_17_1(ServerboundPackets1_17.CLICK_WINDOW);
      this.registerCreativeInvAction(ServerboundPackets1_17.CREATIVE_INVENTORY_ACTION, Type.ITEM1_13_2);
   }
}
