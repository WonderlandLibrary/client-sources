/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.api.data;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.data.BiMappings;
import com.viaversion.viaversion.api.data.FullMappings;
import com.viaversion.viaversion.api.data.IdentityMappings;
import com.viaversion.viaversion.api.data.MappingData;
import com.viaversion.viaversion.api.data.MappingDataLoader;
import com.viaversion.viaversion.api.data.Mappings;
import com.viaversion.viaversion.api.data.ParticleMappings;
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
import java.util.logging.Logger;
import java.util.stream.Collectors;
import org.checkerframework.checker.nullness.qual.Nullable;

public class MappingDataBase
implements MappingData {
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
        CompoundTag tagsTag;
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
            ListTag unmappedParticles = (ListTag)unmappedIdentifierData.get("particles");
            ListTag mappedParticles = (ListTag)mappedIdentifierData.get("particles");
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
        if ((tagsTag = (CompoundTag)data.get("tags")) != null) {
            this.tags = new EnumMap<RegistryType, List<TagData>>(RegistryType.class);
            this.loadTags(RegistryType.ITEM, tagsTag);
            this.loadTags(RegistryType.BLOCK, tagsTag);
        }
        this.loadExtras(data);
    }

    protected @Nullable CompoundTag readNBTFile(String name) {
        return MappingDataLoader.loadNBT(name);
    }

    protected @Nullable Mappings loadMappings(CompoundTag data, String key) {
        return MappingDataLoader.loadMappings(data, key);
    }

    protected @Nullable FullMappings loadFullMappings(CompoundTag data, CompoundTag unmappedIdentifiers, CompoundTag mappedIdentifiers, String key) {
        return MappingDataLoader.loadFullMappings(data, unmappedIdentifiers, mappedIdentifiers, key);
    }

    protected @Nullable BiMappings loadBiMappings(CompoundTag data, String key) {
        Mappings mappings = this.loadMappings(data, key);
        return mappings != null ? BiMappings.of(mappings) : null;
    }

    private void loadTags(RegistryType type2, CompoundTag data) {
        CompoundTag tag = (CompoundTag)data.get(type2.resourceLocation());
        if (tag == null) {
            return;
        }
        ArrayList<TagData> tagsList = new ArrayList<TagData>(this.tags.size());
        for (Map.Entry<String, Tag> entry : tag.entrySet()) {
            IntArrayTag entries = (IntArrayTag)entry.getValue();
            tagsList.add(new TagData(entry.getKey(), entries.getValue()));
        }
        this.tags.put(type2, tagsList);
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

    @Override
    public @Nullable List<TagData> getTags(RegistryType type2) {
        return this.tags != null ? this.tags.get((Object)type2) : null;
    }

    @Override
    public @Nullable BiMappings getItemMappings() {
        return this.itemMappings;
    }

    @Override
    public @Nullable ParticleMappings getParticleMappings() {
        return this.particleMappings;
    }

    @Override
    public @Nullable Mappings getBlockMappings() {
        return this.blockMappings;
    }

    @Override
    public @Nullable Mappings getBlockEntityMappings() {
        return this.blockEntityMappings;
    }

    @Override
    public @Nullable Mappings getBlockStateMappings() {
        return this.blockStateMappings;
    }

    @Override
    public @Nullable Mappings getSoundMappings() {
        return this.soundMappings;
    }

    @Override
    public @Nullable Mappings getStatisticsMappings() {
        return this.statisticsMappings;
    }

    @Override
    public @Nullable Mappings getMenuMappings() {
        return this.menuMappings;
    }

    @Override
    public @Nullable Mappings getEnchantmentMappings() {
        return this.enchantmentMappings;
    }

    @Override
    public @Nullable FullMappings getEntityMappings() {
        return this.entityMappings;
    }

    @Override
    public @Nullable FullMappings getArgumentTypeMappings() {
        return this.argumentTypeMappings;
    }

    @Override
    public @Nullable Mappings getPaintingMappings() {
        return this.paintingMappings;
    }

    protected Logger getLogger() {
        return Via.getPlatform().getLogger();
    }

    protected int checkValidity(int id, int mappedId, String type2) {
        if (mappedId == -1) {
            if (!Via.getConfig().isSuppressConversionWarnings()) {
                this.getLogger().warning(String.format("Missing %s %s for %s %s %d", this.mappedVersion, type2, this.unmappedVersion, type2, id));
            }
            return 0;
        }
        return mappedId;
    }

    protected void loadExtras(CompoundTag data) {
    }
}

