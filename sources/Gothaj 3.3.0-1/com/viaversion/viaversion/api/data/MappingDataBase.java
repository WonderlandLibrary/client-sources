package com.viaversion.viaversion.api.data;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.minecraft.RegistryType;
import com.viaversion.viaversion.api.minecraft.TagData;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.IntArrayTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.ListTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import org.checkerframework.checker.nullness.qual.Nullable;

public class MappingDataBase implements MappingData {
   protected final String unmappedVersion;
   protected final String mappedVersion;
   protected BiMappings itemMappings;
   protected FullMappings argumentTypeMappings;
   protected FullMappings entityMappings;
   protected ParticleMappings particleMappings;
   protected Mappings blockMappings;
   protected Mappings blockStateMappings;
   protected Mappings blockEntityMappings;
   protected Mappings soundMappings;
   protected Mappings statisticsMappings;
   protected Mappings enchantmentMappings;
   protected Mappings paintingMappings;
   protected Mappings menuMappings;
   protected Map<RegistryType, List<TagData>> tags;

   public MappingDataBase(String unmappedVersion, String mappedVersion) {
      this.unmappedVersion = unmappedVersion;
      this.mappedVersion = mappedVersion;
   }

   @Override
   public void load() {
      if (Via.getManager().isDebug()) {
         this.getLogger().info("Loading " + this.unmappedVersion + " -> " + this.mappedVersion + " mappings...");
      }

      CompoundTag data = this.readNBTFile("mappings-" + this.unmappedVersion + "to" + this.mappedVersion + ".nbt");
      this.blockMappings = this.loadMappings(data, "blocks");
      this.blockStateMappings = this.loadMappings(data, "blockstates");
      this.blockEntityMappings = this.loadMappings(data, "blockentities");
      this.soundMappings = this.loadMappings(data, "sounds");
      this.statisticsMappings = this.loadMappings(data, "statistics");
      this.menuMappings = this.loadMappings(data, "menus");
      this.enchantmentMappings = this.loadMappings(data, "enchantments");
      this.paintingMappings = this.loadMappings(data, "paintings");
      this.itemMappings = this.loadBiMappings(data, "items");
      CompoundTag unmappedIdentifierData = MappingDataLoader.loadNBT("identifiers-" + this.unmappedVersion + ".nbt", true);
      CompoundTag mappedIdentifierData = MappingDataLoader.loadNBT("identifiers-" + this.mappedVersion + ".nbt", true);
      if (unmappedIdentifierData != null && mappedIdentifierData != null) {
         this.entityMappings = this.loadFullMappings(data, unmappedIdentifierData, mappedIdentifierData, "entities");
         this.argumentTypeMappings = this.loadFullMappings(data, unmappedIdentifierData, mappedIdentifierData, "argumenttypes");
         ListTag unmappedParticles = unmappedIdentifierData.get("particles");
         ListTag mappedParticles = mappedIdentifierData.get("particles");
         if (unmappedParticles != null && mappedParticles != null) {
            Mappings particleMappings = this.loadMappings(data, "particles");
            if (particleMappings == null) {
               particleMappings = new IdentityMappings(unmappedParticles.size(), mappedParticles.size());
            }

            List<String> identifiers = unmappedParticles.getValue().stream().map(t -> (String)t.getValue()).collect(Collectors.toList());
            List<String> mappedIdentifiers = mappedParticles.getValue().stream().map(t -> (String)t.getValue()).collect(Collectors.toList());
            this.particleMappings = new ParticleMappings(identifiers, mappedIdentifiers, particleMappings);
         }
      }

      CompoundTag tagsTag = data.get("tags");
      if (tagsTag != null) {
         this.tags = new EnumMap<>(RegistryType.class);
         this.loadTags(RegistryType.ITEM, tagsTag);
         this.loadTags(RegistryType.BLOCK, tagsTag);
      }

      this.loadExtras(data);
   }

   @Nullable
   protected CompoundTag readNBTFile(String name) {
      return MappingDataLoader.loadNBT(name);
   }

   @Nullable
   protected Mappings loadMappings(CompoundTag data, String key) {
      return MappingDataLoader.loadMappings(data, key);
   }

   @Nullable
   protected FullMappings loadFullMappings(CompoundTag data, CompoundTag unmappedIdentifiers, CompoundTag mappedIdentifiers, String key) {
      return MappingDataLoader.loadFullMappings(data, unmappedIdentifiers, mappedIdentifiers, key);
   }

   @Nullable
   protected BiMappings loadBiMappings(CompoundTag data, String key) {
      Mappings mappings = this.loadMappings(data, key);
      return mappings != null ? BiMappings.of(mappings) : null;
   }

   private void loadTags(RegistryType type, CompoundTag data) {
      CompoundTag tag = data.get(type.resourceLocation());
      if (tag != null) {
         List<TagData> tagsList = new ArrayList<>(this.tags.size());

         for (Entry<String, Tag> entry : tag.entrySet()) {
            IntArrayTag entries = (IntArrayTag)entry.getValue();
            tagsList.add(new TagData(entry.getKey(), entries.getValue()));
         }

         this.tags.put(type, tagsList);
      }
   }

   @Override
   public int getNewBlockStateId(int id) {
      return this.checkValidity(id, this.blockStateMappings.getNewId(id), "blockstate");
   }

   @Override
   public int getNewBlockId(int id) {
      return this.checkValidity(id, this.blockMappings.getNewId(id), "block");
   }

   @Override
   public int getNewItemId(int id) {
      return this.checkValidity(id, this.itemMappings.getNewId(id), "item");
   }

   @Override
   public int getOldItemId(int id) {
      return this.itemMappings.inverse().getNewIdOrDefault(id, 1);
   }

   @Override
   public int getNewParticleId(int id) {
      return this.checkValidity(id, this.particleMappings.getNewId(id), "particles");
   }

   @Nullable
   @Override
   public List<TagData> getTags(RegistryType type) {
      return this.tags != null ? this.tags.get(type) : null;
   }

   @Nullable
   @Override
   public BiMappings getItemMappings() {
      return this.itemMappings;
   }

   @Nullable
   @Override
   public ParticleMappings getParticleMappings() {
      return this.particleMappings;
   }

   @Nullable
   @Override
   public Mappings getBlockMappings() {
      return this.blockMappings;
   }

   @Nullable
   @Override
   public Mappings getBlockEntityMappings() {
      return this.blockEntityMappings;
   }

   @Nullable
   @Override
   public Mappings getBlockStateMappings() {
      return this.blockStateMappings;
   }

   @Nullable
   @Override
   public Mappings getSoundMappings() {
      return this.soundMappings;
   }

   @Nullable
   @Override
   public Mappings getStatisticsMappings() {
      return this.statisticsMappings;
   }

   @Nullable
   @Override
   public Mappings getMenuMappings() {
      return this.menuMappings;
   }

   @Nullable
   @Override
   public Mappings getEnchantmentMappings() {
      return this.enchantmentMappings;
   }

   @Nullable
   @Override
   public FullMappings getEntityMappings() {
      return this.entityMappings;
   }

   @Nullable
   @Override
   public FullMappings getArgumentTypeMappings() {
      return this.argumentTypeMappings;
   }

   @Nullable
   @Override
   public Mappings getPaintingMappings() {
      return this.paintingMappings;
   }

   protected Logger getLogger() {
      return Via.getPlatform().getLogger();
   }

   protected int checkValidity(int id, int mappedId, String type) {
      if (mappedId == -1) {
         if (!Via.getConfig().isSuppressConversionWarnings()) {
            this.getLogger().warning(String.format("Missing %s %s for %s %s %d", this.mappedVersion, type, this.unmappedVersion, type, id));
         }

         return 0;
      } else {
         return mappedId;
      }
   }

   protected void loadExtras(CompoundTag data) {
   }
}
