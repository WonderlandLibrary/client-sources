/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.tags;

import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tags.ITag;
import net.minecraft.tags.Tag;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.DefaultedRegistry;
import net.minecraft.util.registry.Registry;

public interface ITagCollection<T> {
    public Map<ResourceLocation, ITag<T>> getIDTagMap();

    @Nullable
    default public ITag<T> get(ResourceLocation resourceLocation) {
        return this.getIDTagMap().get(resourceLocation);
    }

    public ITag<T> getTagByID(ResourceLocation var1);

    @Nullable
    public ResourceLocation getDirectIdFromTag(ITag<T> var1);

    default public ResourceLocation getValidatedIdFromTag(ITag<T> iTag) {
        ResourceLocation resourceLocation = this.getDirectIdFromTag(iTag);
        if (resourceLocation == null) {
            throw new IllegalStateException("Unrecognized tag");
        }
        return resourceLocation;
    }

    default public Collection<ResourceLocation> getRegisteredTags() {
        return this.getIDTagMap().keySet();
    }

    default public Collection<ResourceLocation> getOwningTags(T t) {
        ArrayList<ResourceLocation> arrayList = Lists.newArrayList();
        for (Map.Entry<ResourceLocation, ITag<T>> entry : this.getIDTagMap().entrySet()) {
            if (!entry.getValue().contains(t)) continue;
            arrayList.add(entry.getKey());
        }
        return arrayList;
    }

    default public void writeTagCollectionToBuffer(PacketBuffer packetBuffer, DefaultedRegistry<T> defaultedRegistry) {
        Map<ResourceLocation, ITag<T>> map = this.getIDTagMap();
        packetBuffer.writeVarInt(map.size());
        for (Map.Entry<ResourceLocation, ITag<T>> entry : map.entrySet()) {
            packetBuffer.writeResourceLocation(entry.getKey());
            packetBuffer.writeVarInt(entry.getValue().getAllElements().size());
            for (T t : entry.getValue().getAllElements()) {
                packetBuffer.writeVarInt(defaultedRegistry.getId(t));
            }
        }
    }

    public static <T> ITagCollection<T> readTagCollectionFromBuffer(PacketBuffer packetBuffer, Registry<T> registry) {
        HashMap<ResourceLocation, ITag<T>> hashMap = Maps.newHashMap();
        int n = packetBuffer.readVarInt();
        for (int i = 0; i < n; ++i) {
            ResourceLocation resourceLocation = packetBuffer.readResourceLocation();
            int n2 = packetBuffer.readVarInt();
            ImmutableSet.Builder builder = ImmutableSet.builder();
            for (int j = 0; j < n2; ++j) {
                builder.add(registry.getByValue(packetBuffer.readVarInt()));
            }
            hashMap.put(resourceLocation, ITag.getTagOf(builder.build()));
        }
        return ITagCollection.getTagCollectionFromMap(hashMap);
    }

    public static <T> ITagCollection<T> getEmptyTagCollection() {
        return ITagCollection.getTagCollectionFromMap(ImmutableBiMap.of());
    }

    public static <T> ITagCollection<T> getTagCollectionFromMap(Map<ResourceLocation, ITag<T>> map) {
        ImmutableBiMap<ResourceLocation, ITag<T>> immutableBiMap = ImmutableBiMap.copyOf(map);
        return new ITagCollection<T>(immutableBiMap){
            private final ITag<T> emptyTag;
            final BiMap val$bimap;
            {
                this.val$bimap = biMap;
                this.emptyTag = Tag.getEmptyTag();
            }

            @Override
            public ITag<T> getTagByID(ResourceLocation resourceLocation) {
                return this.val$bimap.getOrDefault(resourceLocation, this.emptyTag);
            }

            @Override
            @Nullable
            public ResourceLocation getDirectIdFromTag(ITag<T> iTag) {
                return iTag instanceof ITag.INamedTag ? ((ITag.INamedTag)iTag).getName() : (ResourceLocation)this.val$bimap.inverse().get(iTag);
            }

            @Override
            public Map<ResourceLocation, ITag<T>> getIDTagMap() {
                return this.val$bimap;
            }
        };
    }
}

