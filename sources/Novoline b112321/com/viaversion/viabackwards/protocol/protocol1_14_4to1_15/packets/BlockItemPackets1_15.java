package com.viaversion.viabackwards.protocol.protocol1_14_4to1_15.packets;

import com.viaversion.viabackwards.api.BackwardsProtocol;
import com.viaversion.viabackwards.protocol.protocol1_14_4to1_15.Protocol1_14_4To1_15;
import com.viaversion.viabackwards.protocol.protocol1_14_4to1_15.packets.BlockItemPackets1_15$1;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.rewriter.BlockRewriter;
import net.Mo;
import net.aFA;
import net.aQU;
import net.ahW;
import net.amx;
import net.aoa;
import net.aod;
import net.aq3;
import net.aqq;
import net.h0;
import net.ku;
import net.y7;

public class BlockItemPackets1_15 extends aqq {
   public BlockItemPackets1_15(Protocol1_14_4To1_15 var1, ku var2) {
      super(var1, var2);
   }

   protected void registerPackets() {
      aQU var2 = new aQU(this.c, this::a, this::c);
      BlockRewriter var3 = new BlockRewriter(this.c, Type.POSITION1_14);
      (new amx(this.c, this::a)).a(Mo.DECLARE_RECIPES);
      ((Protocol1_14_4To1_15)this.c).a(ahW.EDIT_BOOK, new BlockItemPackets1_15$1(this));
      var2.a((y7)Mo.COOLDOWN);
      var2.b((y7)Mo.WINDOW_ITEMS, (Type)Type.FLAT_VAR_INT_ITEM_ARRAY);
      var2.a((y7)Mo.SET_SLOT, (Type)Type.FLAT_VAR_INT_ITEM);
      var2.e(Mo.TRADE_LIST, Type.FLAT_VAR_INT_ITEM);
      var2.f(Mo.ENTITY_EQUIPMENT, Type.FLAT_VAR_INT_ITEM);
      var2.c(Mo.ADVANCEMENTS, Type.FLAT_VAR_INT_ITEM);
      var2.a((h0)ahW.CLICK_WINDOW, (Type)Type.FLAT_VAR_INT_ITEM);
      var2.b((h0)ahW.CREATIVE_INVENTORY_ACTION, (Type)Type.FLAT_VAR_INT_ITEM);
      var3.b((y7)Mo.ACKNOWLEDGE_PLAYER_DIGGING);
      aq3.a();
      var3.c(Mo.BLOCK_ACTION);
      var3.d(Mo.BLOCK_CHANGE);
      var3.e(Mo.MULTI_BLOCK_CHANGE);
      ((Protocol1_14_4To1_15)this.c).a(Mo.CHUNK_DATA, new aod(this));
      var3.a(Mo.EFFECT, 1010, 2001);
      ((Protocol1_14_4To1_15)this.c).a(Mo.SPAWN_PARTICLE, new aoa(this));
   }

   static BackwardsProtocol b(BlockItemPackets1_15 var0) {
      return var0.c;
   }

   static BackwardsProtocol c(BlockItemPackets1_15 var0) {
      return var0.c;
   }

   static BackwardsProtocol a(BlockItemPackets1_15 var0) {
      return var0.c;
   }
}
