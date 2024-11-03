package com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.block_entity_handlers;

import com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.Protocol1_12_2To1_13;
import com.viaversion.viabackwards.protocol.protocol1_12_2to1_13.providers.BackwardsBlockEntityProvider;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.data.MappingDataLoader;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.IntTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ListTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.blockconnections.ConnectionData;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;
import java.util.Map.Entry;

public class PistonHandler implements BackwardsBlockEntityProvider.BackwardsBlockEntityHandler {
   private final Map<String, Integer> pistonIds = new HashMap<>();

   public PistonHandler() {
      if (Via.getConfig().isServersideBlockConnections()) {
         Map<String, Integer> keyToId = ConnectionData.getKeyToId();

         for (Entry<String, Integer> entry : keyToId.entrySet()) {
            if (entry.getKey().contains("piston")) {
               this.addEntries(entry.getKey(), entry.getValue());
            }
         }
      } else {
         ListTag blockStates = MappingDataLoader.loadNBT("blockstates-1.13.nbt").get("blockstates");

         for (int id = 0; id < blockStates.size(); id++) {
            StringTag state = blockStates.get(id);
            String key = state.getValue();
            if (key.contains("piston")) {
               this.addEntries(key, id);
            }
         }
      }
   }

   private void addEntries(String data, int id) {
      id = Protocol1_12_2To1_13.MAPPINGS.getNewBlockStateId(id);
      this.pistonIds.put(data, id);
      String substring = data.substring(10);
      if (substring.startsWith("piston") || substring.startsWith("sticky_piston")) {
         String[] split = data.substring(0, data.length() - 1).split("\\[");
         String[] properties = split[1].split(",");
         data = split[0] + "[" + properties[1] + "," + properties[0] + "]";
         this.pistonIds.put(data, id);
      }
   }

   @Override
   public CompoundTag transform(UserConnection user, int blockId, CompoundTag tag) {
      CompoundTag blockState = tag.get("blockState");
      if (blockState == null) {
         return tag;
      } else {
         String dataFromTag = this.getDataFromTag(blockState);
         if (dataFromTag == null) {
            return tag;
         } else {
            Integer id = this.pistonIds.get(dataFromTag);
            if (id == null) {
               return tag;
            } else {
               tag.put("blockId", new IntTag(id >> 4));
               tag.put("blockData", new IntTag(id & 15));
               return tag;
            }
         }
      }
   }

   private String getDataFromTag(CompoundTag tag) {
      StringTag name = tag.get("Name");
      if (name == null) {
         return null;
      } else {
         CompoundTag properties = tag.get("Properties");
         if (properties == null) {
            return name.getValue();
         } else {
            StringJoiner joiner = new StringJoiner(",", name.getValue() + "[", "]");

            for (Entry<String, Tag> entry : properties) {
               if (entry.getValue() instanceof StringTag) {
                  joiner.add(entry.getKey() + "=" + ((StringTag)entry.getValue()).getValue());
               }
            }

            return joiner.toString();
         }
      }
   }
}
