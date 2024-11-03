package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.providers.blockentities;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.Position;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.NumberTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.providers.BlockEntityProvider;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.storage.BlockStorage;

public class SkullHandler implements BlockEntityProvider.BlockEntityHandler {
   private static final int SKULL_WALL_START = 5447;
   private static final int SKULL_END = 5566;

   @Override
   public int transform(UserConnection user, CompoundTag tag) {
      BlockStorage storage = user.get(BlockStorage.class);
      Position position = new Position((int)this.getLong(tag.get("x")), (short)((int)this.getLong(tag.get("y"))), (int)this.getLong(tag.get("z")));
      if (!storage.contains(position)) {
         Via.getPlatform().getLogger().warning("Received an head update packet, but there is no head! O_o " + tag);
         return -1;
      } else {
         int id = storage.get(position).getOriginal();
         if (id >= 5447 && id <= 5566) {
            Tag skullType = tag.get("SkullType");
            if (skullType instanceof NumberTag) {
               id += ((NumberTag)skullType).asInt() * 20;
            }

            Tag rot = tag.get("Rot");
            if (rot instanceof NumberTag) {
               id += ((NumberTag)rot).asInt();
            }

            return id;
         } else {
            Via.getPlatform().getLogger().warning("Why does this block have the skull block entity? " + tag);
            return -1;
         }
      }
   }

   private long getLong(NumberTag tag) {
      return tag.asLong();
   }
}
