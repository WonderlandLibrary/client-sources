package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.providers.blockentities;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.Position;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.IntTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ListTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.NumberTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.providers.BlockEntityProvider;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.storage.BlockStorage;
import com.viaversion.viaversion.util.ComponentUtil;

public class BannerHandler implements BlockEntityProvider.BlockEntityHandler {
   private static final int WALL_BANNER_START = 7110;
   private static final int WALL_BANNER_STOP = 7173;
   private static final int BANNER_START = 6854;
   private static final int BANNER_STOP = 7109;

   @Override
   public int transform(UserConnection user, CompoundTag tag) {
      BlockStorage storage = user.get(BlockStorage.class);
      Position position = new Position((int)this.getLong(tag.get("x")), (short)((int)this.getLong(tag.get("y"))), (int)this.getLong(tag.get("z")));
      if (!storage.contains(position)) {
         Via.getPlatform().getLogger().warning("Received an banner color update packet, but there is no banner! O_o " + tag);
         return -1;
      } else {
         int blockId = storage.get(position).getOriginal();
         Tag base = tag.get("Base");
         int color = 0;
         if (base instanceof NumberTag) {
            color = ((NumberTag)base).asInt();
         }

         if (blockId >= 6854 && blockId <= 7109) {
            blockId += (15 - color) * 16;
         } else if (blockId >= 7110 && blockId <= 7173) {
            blockId += (15 - color) * 4;
         } else {
            Via.getPlatform().getLogger().warning("Why does this block have the banner block entity? :(" + tag);
         }

         Tag patterns = tag.get("Patterns");
         if (patterns instanceof ListTag) {
            for (Tag pattern : (ListTag)patterns) {
               if (pattern instanceof CompoundTag) {
                  Tag c = ((CompoundTag)pattern).get("Color");
                  if (c instanceof IntTag) {
                     ((IntTag)c).setValue(15 - (Integer)c.getValue());
                  }
               }
            }
         }

         Tag name = tag.get("CustomName");
         if (name instanceof StringTag) {
            ((StringTag)name).setValue(ComponentUtil.legacyToJsonString(((StringTag)name).getValue()));
         }

         return blockId;
      }
   }

   private long getLong(NumberTag tag) {
      return tag.asLong();
   }
}
