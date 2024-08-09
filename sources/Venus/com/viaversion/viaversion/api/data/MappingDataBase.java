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

    public MappingDataBase(String string, String string2) {
        this.unmappedVersion = string;
        this.mappedVersion = string2;
    }

    @Override
    public void load() {
        Tag tag;
        if (Via.getManager().isDebug()) {
            this.getLogger().info("Loading " + this.unmappedVersion + " -> " + this.mappedVersion + " mappings...");
        }
        CompoundTag compoundTag = this.readNBTFile("mappings-" + this.unmappedVersion + "to" + this.mappedVersion + ".nbt");
        this.blockMappings = this.loadMappings(compoundTag, "blocks");
        this.blockStateMappings = this.loadMappings(compoundTag, "blockstates");
        this.blockEntityMappings = this.loadMappings(compoundTag, "blockentities");
        this.soundMappings = this.loadMappings(compoundTag, "sounds");
        this.statisticsMappings = this.loadMappings(compoundTag, "statistics");
        this.menuMappings = this.loadMappings(compoundTag, "menus");
        this.enchantmentMappings = this.loadMappings(compoundTag, "enchantments");
        this.paintingMappings = this.loadMappings(compoundTag, "paintings");
        this.itemMappings = this.loadBiMappings(compoundTag, "items");
        CompoundTag compoundTag2 = MappingDataLoader.loadNBT("identifiers-" + this.unmappedVersion + ".nbt", true);
        CompoundTag compoundTag3 = MappingDataLoader.loadNBT("identifiers-" + this.mappedVersion + ".nbt", true);
        if (compoundTag2 != null && compoundTag3 != null) {
            this.entityMappings = this.loadFullMappings(compoundTag, compoundTag2, compoundTag3, "entities");
            this.argumentTypeMappings = this.loadFullMappings(compoundTag, compoundTag2, compoundTag3, "argumenttypes");
            tag = (ListTag)compoundTag2.get("particles");
            ListTag listTag = (ListTag)compoundTag3.get("particles");
            if (tag != null && listTag != null) {
                Mappings mappings = this.loadMappings(compoundTag, "particles");
                if (mappings == null) {
                    mappings = new IdentityMappings(((ListTag)tag).size(), listTag.size());
                }
                List<String> list = ((ListTag)tag).getValue().stream().map(MappingDataBase::lambda$load$0).collect(Collectors.toList());
                List<String> list2 = listTag.getValue().stream().map(MappingDataBase::lambda$load$1).collect(Collectors.toList());
                this.particleMappings = new ParticleMappings(list, list2, mappings);
            }
        }
        if ((tag = (CompoundTag)compoundTag.get("tags")) != null) {
            this.tags = new EnumMap<RegistryType, List<TagData>>(RegistryType.class);
            this.loadTags(RegistryType.ITEM, (CompoundTag)tag);
            this.loadTags(RegistryType.BLOCK, (CompoundTag)tag);
        }
        this.loadExtras(compoundTag);
    }

    protected @Nullable CompoundTag readNBTFile(String string) {
        return MappingDataLoader.loadNBT(string);
    }

    protected @Nullable Mappings loadMappings(CompoundTag compoundTag, String string) {
        return MappingDataLoader.loadMappings(compoundTag, string);
    }

    protected @Nullable FullMappings loadFullMappings(CompoundTag compoundTag, CompoundTag compoundTag2, CompoundTag compoundTag3, String string) {
        return MappingDataLoader.loadFullMappings(compoundTag, compoundTag2, compoundTag3, string);
    }

    protected @Nullable BiMappings loadBiMappings(CompoundTag compoundTag, String string) {
        Mappings mappings = this.loadMappings(compoundTag, string);
        return mappings != null ? BiMappings.of(mappings) : null;
    }

    private void loadTags(RegistryType registryType, CompoundTag compoundTag) {
        CompoundTag compoundTag2 = (CompoundTag)compoundTag.get(registryType.resourceLocation());
        if (compoundTag2 == null) {
            return;
        }
        ArrayList<TagData> arrayList = new ArrayList<TagData>(this.tags.size());
        for (Map.Entry<String, Tag> entry : compoundTag2.entrySet()) {
            IntArrayTag intArrayTag = (IntArrayTag)entry.getValue();
            arrayList.add(new TagData(entry.getKey(), intArrayTag.getValue()));
        }
        this.tags.put(registryType, arrayList);
    }

    @Override
    public int getNewBlockStateId(int n) {
        return this.checkValidity(n, this.blockStateMappings.getNewId(n), "blockstate");
    }

    @Override
    public int getNewBlockId(int n) {
        return this.checkValidity(n, this.blockMappings.getNewId(n), "block");
    }

    @Override
    public int getNewItemId(int n) {
        return this.checkValidity(n, this.itemMappings.getNewId(n), "item");
    }

    @Override
    public int getOldItemId(int n) {
        return this.itemMappings.inverse().getNewIdOrDefault(n, 1);
    }

    @Override
    public int getNewParticleId(int n) {
        return this.checkValidity(n, this.particleMappings.getNewId(n), "particles");
    }

    @Override
    public @Nullable List<TagData> getTags(RegistryType registryType) {
        return this.tags != null ? this.tags.get((Object)registryType) : null;
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

    protected int checkValidity(int n, int n2, String string) {
        if (n2 == -1) {
            if (!Via.getConfig().isSuppressConversionWarnings()) {
                this.getLogger().warning(String.format("Missing %s %s for %s %s %d", this.mappedVersion, string, this.unmappedVersion, string, n));
            }
            return 1;
        }
        return n2;
    }

    protected void loadExtras(CompoundTag compoundTag) {
    }

    private static String lambda$load$1(Tag tag) {
        return (String)tag.getValue();
    }

    private static String lambda$load$0(Tag tag) {
        return (String)tag.getValue();
    }
}

