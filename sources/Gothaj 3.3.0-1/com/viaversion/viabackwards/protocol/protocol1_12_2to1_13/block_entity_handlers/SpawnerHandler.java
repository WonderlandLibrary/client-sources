package com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.block_entity_handlers;

import com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.data.EntityNameRewrites;
import com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.providers.BackwardsBlockEntityProvider;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;

public class SpawnerHandler implements BackwardsBlockEntityProvider.BackwardsBlockEntityHandler {
   @Override
   public CompoundTag transform(UserConnection user, int blockId, CompoundTag tag) {
      Tag dataTag = tag.get("SpawnData");
      if (dataTag instanceof CompoundTag) {
         CompoundTag data = (CompoundTag)dataTag;
         Tag idTag = data.get("id");
         if (idTag instanceof StringTag) {
            StringTag s = (StringTag)idTag;
            s.setValue(EntityNameRewrites.rewrite(s.getValue()));
         }
      }

      return tag;
   }
}
