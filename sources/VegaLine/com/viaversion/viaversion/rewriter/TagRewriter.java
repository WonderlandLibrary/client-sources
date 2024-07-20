/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.rewriter;

import com.viaversion.viaversion.api.data.MappingData;
import com.viaversion.viaversion.api.minecraft.RegistryType;
import com.viaversion.viaversion.api.minecraft.TagData;
import com.viaversion.viaversion.api.minecraft.entities.EntityType;
import com.viaversion.viaversion.api.protocol.Protocol;
import com.viaversion.viaversion.api.protocol.packet.ClientboundPacketType;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.protocol.remapper.PacketHandler;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.libs.fastutil.ints.IntArrayList;
import com.viaversion.viaversion.rewriter.IdRewriteFunction;
import com.viaversion.viaversion.util.Key;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.checkerframework.checker.nullness.qual.Nullable;

public class TagRewriter<C extends ClientboundPacketType> {
    private static final int[] EMPTY_ARRAY = new int[0];
    private final Protocol<C, ?, ?, ?> protocol;
    private final Map<RegistryType, List<TagData>> newTags = new EnumMap<RegistryType, List<TagData>>(RegistryType.class);
    private final Map<RegistryType, Map<String, String>> toRename = new EnumMap<RegistryType, Map<String, String>>(RegistryType.class);
    private final Set<String> toRemove = new HashSet<String>();

    public TagRewriter(Protocol<C, ?, ?, ?> protocol) {
        this.protocol = protocol;
    }

    public void loadFromMappingData() {
        for (RegistryType type2 : RegistryType.getValues()) {
            List<TagData> tags = this.protocol.getMappingData().getTags(type2);
            if (tags == null) continue;
            this.getOrComputeNewTags(type2).addAll(tags);
        }
    }

    public void removeTags(String registryKey) {
        this.toRemove.add(registryKey);
    }

    public void renameTag(RegistryType type2, String registryKey, String renameTo) {
        this.toRename.computeIfAbsent(type2, t -> new HashMap()).put(registryKey, renameTo);
    }

    public void addEmptyTag(RegistryType tagType, String tagId) {
        this.getOrComputeNewTags(tagType).add(new TagData(tagId, EMPTY_ARRAY));
    }

    public void addEmptyTags(RegistryType tagType, String ... tagIds) {
        List<TagData> tagList = this.getOrComputeNewTags(tagType);
        for (String id : tagIds) {
            tagList.add(new TagData(id, EMPTY_ARRAY));
        }
    }

    public void addEntityTag(String tagId, EntityType ... entities) {
        int[] ids = new int[entities.length];
        for (int i = 0; i < entities.length; ++i) {
            ids[i] = entities[i].getId();
        }
        this.addTagRaw(RegistryType.ENTITY, tagId, ids);
    }

    public void addTag(RegistryType tagType, String tagId, int ... unmappedIds) {
        List<TagData> newTags = this.getOrComputeNewTags(tagType);
        IdRewriteFunction rewriteFunction = this.getRewriter(tagType);
        if (rewriteFunction != null) {
            for (int i = 0; i < unmappedIds.length; ++i) {
                int unmappedId = unmappedIds[i];
                unmappedIds[i] = rewriteFunction.rewrite(unmappedId);
            }
        }
        newTags.add(new TagData(tagId, unmappedIds));
    }

    public void addTagRaw(RegistryType tagType, String tagId, int ... ids) {
        this.getOrComputeNewTags(tagType).add(new TagData(tagId, ids));
    }

    public void register(C packetType, @Nullable RegistryType readUntilType) {
        this.protocol.registerClientbound(packetType, this.getHandler(readUntilType));
    }

    public void registerGeneric(C packetType) {
        this.protocol.registerClientbound(packetType, this.getGenericHandler());
    }

    public PacketHandler getHandler(@Nullable RegistryType readUntilType) {
        return wrapper -> {
            for (RegistryType type2 : RegistryType.getValues()) {
                this.handle(wrapper, this.getRewriter(type2), this.getNewTags(type2), this.toRename.get((Object)type2));
                if (type2 == readUntilType) break;
            }
        };
    }

    public PacketHandler getGenericHandler() {
        return wrapper -> {
            int length;
            int editedLength = length = wrapper.passthrough(Type.VAR_INT).intValue();
            for (int i = 0; i < length; ++i) {
                String registryKey = wrapper.read(Type.STRING);
                if (this.toRemove.contains(registryKey)) {
                    wrapper.set(Type.VAR_INT, 0, --editedLength);
                    int tagsSize = wrapper.read(Type.VAR_INT);
                    for (int j = 0; j < tagsSize; ++j) {
                        wrapper.read(Type.STRING);
                        wrapper.read(Type.VAR_INT_ARRAY_PRIMITIVE);
                    }
                    continue;
                }
                wrapper.write(Type.STRING, registryKey);
                registryKey = Key.stripMinecraftNamespace(registryKey);
                RegistryType type2 = RegistryType.getByKey(registryKey);
                if (type2 != null) {
                    this.handle(wrapper, this.getRewriter(type2), this.getNewTags(type2), this.toRename.get((Object)type2));
                    continue;
                }
                this.handle(wrapper, null, null, null);
            }
        };
    }

    public void handle(PacketWrapper wrapper, @Nullable IdRewriteFunction rewriteFunction, @Nullable List<TagData> newTags) throws Exception {
        this.handle(wrapper, rewriteFunction, newTags, null);
    }

    public void handle(PacketWrapper wrapper, @Nullable IdRewriteFunction rewriteFunction, @Nullable List<TagData> newTags, @Nullable Map<String, String> tagsToRename) throws Exception {
        int tagsSize = wrapper.read(Type.VAR_INT);
        wrapper.write(Type.VAR_INT, newTags != null ? tagsSize + newTags.size() : tagsSize);
        for (int i = 0; i < tagsSize; ++i) {
            String renamedKey;
            String key = wrapper.read(Type.STRING);
            if (tagsToRename != null && (renamedKey = tagsToRename.get(key)) != null) {
                key = renamedKey;
            }
            wrapper.write(Type.STRING, key);
            int[] ids = wrapper.read(Type.VAR_INT_ARRAY_PRIMITIVE);
            if (rewriteFunction != null) {
                IntArrayList idList = new IntArrayList(ids.length);
                for (int id : ids) {
                    int mappedId = rewriteFunction.rewrite(id);
                    if (mappedId == -1) continue;
                    idList.add(mappedId);
                }
                wrapper.write(Type.VAR_INT_ARRAY_PRIMITIVE, idList.toArray(EMPTY_ARRAY));
                continue;
            }
            wrapper.write(Type.VAR_INT_ARRAY_PRIMITIVE, ids);
        }
        if (newTags != null) {
            for (TagData tag : newTags) {
                wrapper.write(Type.STRING, tag.identifier());
                wrapper.write(Type.VAR_INT_ARRAY_PRIMITIVE, tag.entries());
            }
        }
    }

    public @Nullable List<TagData> getNewTags(RegistryType tagType) {
        return this.newTags.get((Object)tagType);
    }

    public List<TagData> getOrComputeNewTags(RegistryType tagType) {
        return this.newTags.computeIfAbsent(tagType, type2 -> new ArrayList());
    }

    public @Nullable IdRewriteFunction getRewriter(RegistryType tagType) {
        MappingData mappingData = this.protocol.getMappingData();
        switch (tagType) {
            case BLOCK: {
                return mappingData != null && mappingData.getBlockMappings() != null ? mappingData::getNewBlockId : null;
            }
            case ITEM: {
                return mappingData != null && mappingData.getItemMappings() != null ? mappingData::getNewItemId : null;
            }
            case ENTITY: {
                return this.protocol.getEntityRewriter() != null ? id -> this.protocol.getEntityRewriter().newEntityId(id) : null;
            }
        }
        return null;
    }
}

