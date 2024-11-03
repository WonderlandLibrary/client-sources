package com.viaversion.viabackwards.protocol.protocol1_19_1to1_19_3.data;

import com.viaversion.viabackwards.api.data.VBMappingDataLoader;
import com.viaversion.viaversion.libs.fastutil.objects.Object2IntMap;
import com.viaversion.viaversion.libs.fastutil.objects.Object2IntOpenHashMap;
import com.viaversion.viaversion.libs.gson.JsonArray;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.protocols.protocol1_19_3to1_19_1.Protocol1_19_3To1_19_1;
import com.viaversion.viaversion.util.Key;

public final class BackwardsMappings extends com.viaversion.viabackwards.api.data.BackwardsMappings {
   private final Object2IntMap<String> mappedSounds = new Object2IntOpenHashMap<>();

   public BackwardsMappings() {
      super("1.19.3", "1.19", Protocol1_19_3To1_19_1.class);
      this.mappedSounds.defaultReturnValue(-1);
   }

   @Override
   protected void loadExtras(CompoundTag data) {
      super.loadExtras(data);
      JsonArray sounds = VBMappingDataLoader.loadData("sounds-1.19.json").getAsJsonArray("sounds");
      int i = 0;

      for (JsonElement sound : sounds) {
         this.mappedSounds.put(sound.getAsString(), i++);
      }
   }

   public int mappedSound(String sound) {
      return this.mappedSounds.getInt(Key.stripMinecraftNamespace(sound));
   }
}
