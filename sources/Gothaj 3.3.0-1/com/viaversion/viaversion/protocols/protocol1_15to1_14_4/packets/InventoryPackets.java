package com.viaversion.viaversion.protocols.protocol1_15to1_14_4.packets;

import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocols.protocol1_14_4to1_14_3.ClientboundPackets1_14_4;
import com.viaversion.viaversion.protocols.protocol1_14to1_13_2.ServerboundPackets1_14;
import com.viaversion.viaversion.protocols.protocol1_15to1_14_4.Protocol1_15To1_14_4;
import com.viaversion.viaversion.rewriter.ItemRewriter;
import com.viaversion.viaversion.rewriter.RecipeRewriter;

public class InventoryPackets extends ItemRewriter<ClientboundPackets1_14_4, ServerboundPackets1_14, Protocol1_15To1_14_4> {
   public InventoryPackets(Protocol1_15To1_14_4 protocol) {
      super(protocol, Type.ITEM1_13_2, Type.ITEM1_13_2_ARRAY);
   }

   @Override
   public void registerPackets() {
      this.registerSetCooldown(ClientboundPackets1_14_4.COOLDOWN);
      this.registerWindowItems(ClientboundPackets1_14_4.WINDOW_ITEMS, Type.ITEM1_13_2_SHORT_ARRAY);
      this.registerTradeList(ClientboundPackets1_14_4.TRADE_LIST);
      this.registerSetSlot(ClientboundPackets1_14_4.SET_SLOT, Type.ITEM1_13_2);
      this.registerEntityEquipment(ClientboundPackets1_14_4.ENTITY_EQUIPMENT, Type.ITEM1_13_2);
      this.registerAdvancements(ClientboundPackets1_14_4.ADVANCEMENTS, Type.ITEM1_13_2);
      new RecipeRewriter<>(this.protocol).register(ClientboundPackets1_14_4.DECLARE_RECIPES);
      this.registerClickWindow(ServerboundPackets1_14.CLICK_WINDOW, Type.ITEM1_13_2);
      this.registerCreativeInvAction(ServerboundPackets1_14.CREATIVE_INVENTORY_ACTION, Type.ITEM1_13_2);
   }
}
