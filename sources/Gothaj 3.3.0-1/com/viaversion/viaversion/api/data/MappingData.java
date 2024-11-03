package com.viaversion.viaversion.api.data;

import com.viaversion.viaversion.api.minecraft.RegistryType;
import com.viaversion.viaversion.api.minecraft.TagData;
import java.util.List;
import org.checkerframework.checker.nullness.qual.Nullable;

public interface MappingData {
   void load();

   int getNewBlockStateId(int var1);

   int getNewBlockId(int var1);

   int getNewItemId(int var1);

   int getOldItemId(int var1);

   int getNewParticleId(int var1);

   @Nullable
   List<TagData> getTags(RegistryType var1);

   @Nullable
   BiMappings getItemMappings();

   @Nullable
   ParticleMappings getParticleMappings();

   @Nullable
   Mappings getBlockMappings();

   @Nullable
   Mappings getBlockEntityMappings();

   @Nullable
   Mappings getBlockStateMappings();

   @Nullable
   Mappings getSoundMappings();

   @Nullable
   Mappings getStatisticsMappings();

   @Nullable
   Mappings getMenuMappings();

   @Nullable
   Mappings getEnchantmentMappings();

   @Nullable
   FullMappings getEntityMappings();

   @Nullable
   FullMappings getArgumentTypeMappings();

   @Nullable
   Mappings getPaintingMappings();
}
