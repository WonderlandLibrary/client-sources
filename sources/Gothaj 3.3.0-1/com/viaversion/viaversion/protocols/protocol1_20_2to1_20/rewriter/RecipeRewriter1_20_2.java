package com.viaversion.viaversion.protocols.protocol1_20_2to1_20.rewriter;

import com.viaversion.viaversion.api.minecraft.item.Item;
import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.protocols.protocol1_19_4to1_19_3.rewriter.RecipeRewriter1_19_4;

public class RecipeRewriter1_20_2<C extends ClientboundPacketType> extends RecipeRewriter1_19_4<C> {
   public RecipeRewriter1_20_2(Protocol<C, ?, ?, ?> protocol) {
      super(protocol);
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
