package com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.block_entity_handlers;

import com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.providers.BackwardsBlockEntityProvider;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ByteTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;

public class SkullHandler implements BackwardsBlockEntityProvider.BackwardsBlockEntityHandler {
   private static final int SKULL_START = 5447;

   @Override
   public CompoundTag transform(UserConnection user, int blockId, CompoundTag tag) {
      int diff = blockId - 5447;
      int pos = diff % 20;
      byte type = (byte)((int)Math.floor((double)((float)diff / 20.0F)));
      tag.put("SkullType", new ByteTag(type));
      if (pos < 4) {
         return tag;
      } else {
         tag.put("Rot", new ByteTag((byte)(pos - 4 & 0xFF)));
         return tag;
      }
   }
}
