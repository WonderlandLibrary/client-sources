package com.viaversion.viaversion.protocols.protocol1_20_3to1_20_2.rewriter;

import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocols.protocol1_19_4to1_19_3.rewriter.RecipeRewriter1_19_4;

public class RecipeRewriter1_20_3<C extends ClientboundPacketType> extends RecipeRewriter1_19_4<C> {
   public RecipeRewriter1_20_3(Protocol<C, ?, ?, ?> protocol) {
      super(protocol);
   }

   @Override
   public void handleCraftingShaped(PacketWrapper wrapper) throws Exception {
      wrapper.passthrough(Type.STRING);
      wrapper.passthrough(Type.VAR_INT);
      int ingredients = wrapper.passthrough(Type.VAR_INT) * wrapper.passthrough(Type.VAR_INT);

      for (int i = 0; i < ingredients; i++) {
         this.handleIngredient(wrapper);
      }

      this.rewrite(wrapper.passthrough(this.itemType()));
      wrapper.passthrough(Type.BOOLEAN);
   }

   @Override
   protected Type<Item> itemType() {
      return Type.ITEM1_20_2;
   }

   @Override
   protected Type<Item[]> itemArrayType() {
      return Type.ITEM1_20_2_ARRAY;
   }
}
