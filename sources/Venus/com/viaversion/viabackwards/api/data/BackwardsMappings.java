/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viabackwards.api.data;

import com.google.common.base.Preconditions;
import com.viaversion.viabackwards.ViaBackwards;
import com.viaversion.viabackwards.api.BackwardsProtocol;
import com.viaversion.viabackwards.api.data.ItemMappings;
import com.viaversion.viabackwards.api.data.MappedItem;
import com.viaversion.viabackwards.api.data.VBMappingDataLoader;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.data.BiMappings;
import com.viaversion.viaversion.api.data.MappingData;
import com.viaversion.viaversion.api.data.MappingDataBase;
import com.viaversion.viaversion.api.data.Mappings;
import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectOpenHashMap;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.NumberTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.StringTag;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.Tag;
import com.viaversion.viaversion.util.Key;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.logging.Logger;
import org.checkerframework.checker.nullness.qual.Nullable;

public class BackwardsMappings
extends MappingDataBase {
    private final Class<? extends Protocol<?, ?, ?, ?>> vvProtocolClass;
    protected Int2ObjectMap<MappedItem> backwardsItemMappings;
    private Map<String, String> backwardsSoundMappings;
    private Map<String, String> entityNames;

    public BackwardsMappings(String string, String string2) {
        this(string, string2, null);
    }

    public BackwardsMappings(String string, String string2, @Nullable Class<? extends Protocol<?, ?, ?, ?>> clazz) {
        super(string, string2);
        Preconditions.checkArgument(clazz == null || !clazz.isAssignableFrom(BackwardsProtocol.class));
        this.vvProtocolClass = clazz;
    }

    @Override
    protected void loadExtras(CompoundTag compoundTag) {
        Map.Entry<String, Tag> entry;
        Iterator<Map.Entry<String, Tag>> iterator2;
        CompoundTag compoundTag2;
        CompoundTag compoundTag3 = (CompoundTag)compoundTag.get("itemnames");
        if (compoundTag3 != null) {
            Preconditions.checkNotNull(this.itemMappings);
            this.backwardsItemMappings = new Int2ObjectOpenHashMap<MappedItem>(compoundTag3.size());
            compoundTag2 = (CompoundTag)compoundTag.get("itemdata");
            iterator2 = compoundTag3.entrySet().iterator();
            while (iterator2.hasNext()) {
                entry = iterator2.next();
                StringTag object2 = (StringTag)entry.getValue();
                int n = Integer.parseInt(entry.getKey());
                Integer n2 = null;
                if (compoundTag2 != null && compoundTag2.contains(entry.getKey())) {
                    CompoundTag compoundTag4 = (CompoundTag)compoundTag2.get(entry.getKey());
                    NumberTag numberTag = (NumberTag)compoundTag4.get("custom_model_data");
                    n2 = numberTag != null ? Integer.valueOf(numberTag.asInt()) : null;
                }
                this.backwardsItemMappings.put(n, new MappedItem(this.getNewItemId(n), object2.getValue(), n2));
            }
        }
        if ((compoundTag2 = (CompoundTag)compoundTag.get("entitynames")) != null) {
            this.entityNames = new HashMap<String, String>(compoundTag2.size());
            iterator2 = compoundTag2.entrySet().iterator();
            while (iterator2.hasNext()) {
                entry = iterator2.next();
                StringTag stringTag = (StringTag)entry.getValue();
                this.entityNames.put(entry.getKey(), stringTag.getValue());
            }
        }
        if ((iterator2 = (CompoundTag)compoundTag.get("soundnames")) != null) {
            this.backwardsSoundMappings = new HashMap<String, String>(((CompoundTag)((Object)iterator2)).size());
            for (Map.Entry entry2 : ((CompoundTag)((Object)iterator2)).entrySet()) {
                StringTag stringTag = (StringTag)entry2.getValue();
                this.backwardsSoundMappings.put((String)entry2.getKey(), stringTag.getValue());
            }
        }
    }

    @Override
    protected @Nullable BiMappings loadBiMappings(CompoundTag compoundTag, String string) {
        if (string.equals("items") && this.vvProtocolClass != null) {
            Mappings mappings = super.loadMappings(compoundTag, string);
            MappingData mappingData = Via.getManager().getProtocolManager().getProtocol(this.vvProtocolClass).getMappingData();
            if (mappingData != null && mappingData.getItemMappings() != null) {
                return ItemMappings.of(mappings, mappingData.getItemMappings());
            }
        }
        return super.loadBiMappings(compoundTag, string);
    }

    @Override
    public int getNewItemId(int n) {
        return this.itemMappings.getNewId(n);
    }

    @Override
    public int getNewBlockId(int n) {
        return this.blockMappings.getNewId(n);
    }

    @Override
    public int getOldItemId(int n) {
        return this.checkValidity(n, this.itemMappings.inverse().getNewId(n), "item");
    }

    public @Nullable MappedItem getMappedItem(int n) {
        return this.backwardsItemMappings != null ? (MappedItem)this.backwardsItemMappings.get(n) : null;
    }

    public @Nullable String getMappedNamedSound(String string) {
        if (this.backwardsSoundMappings == null) {
            return null;
        }
        return this.backwardsSoundMappings.get(Key.stripMinecraftNamespace(string));
    }

    public @Nullable String mappedEntityName(String string) {
        if (this.entityNames == null) {
            ViaBackwards.getPlatform().getLogger().severe("No entity mappings found when requesting them for " + string);
            new Exception().printStackTrace();
            return null;
        }
        return this.entityNames.get(string);
    }

    public @Nullable Int2ObjectMap<MappedItem> getBackwardsItemMappings() {
        return this.backwardsItemMappings;
    }

    public @Nullable Map<String, String> getBackwardsSoundMappings() {
        return this.backwardsSoundMappings;
    }

    public @Nullable Class<? extends Protocol<?, ?, ?, ?>> getViaVersionProtocolClass() {
        return this.vvProtocolClass;
    }

    @Override
    protected Logger getLogger() {
        return ViaBackwards.getPlatform().getLogger();
    }

    @Override
    protected @Nullable CompoundTag readNBTFile(String string) {
        return VBMappingDataLoader.loadNBTFromDir(string);
    }
}

