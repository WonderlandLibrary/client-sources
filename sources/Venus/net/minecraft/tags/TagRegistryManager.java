/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.tags;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.ITagCollection;
import net.minecraft.tags.ITagCollectionSupplier;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagRegistry;
import net.minecraft.util.ResourceLocation;

public class TagRegistryManager {
    private static final Map<ResourceLocation, TagRegistry<?>> idToRegistryMap = Maps.newHashMap();

    public static <T> TagRegistry<T> create(ResourceLocation resourceLocation, Function<ITagCollectionSupplier, ITagCollection<T>> function) {
        TagRegistry<T> tagRegistry = new TagRegistry<T>(function);
        TagRegistry<T> tagRegistry2 = idToRegistryMap.putIfAbsent(resourceLocation, tagRegistry);
        if (tagRegistry2 != null) {
            throw new IllegalStateException("Duplicate entry for static tag collection: " + resourceLocation);
        }
        return tagRegistry;
    }

    public static void fetchTags(ITagCollectionSupplier iTagCollectionSupplier) {
        idToRegistryMap.values().forEach(arg_0 -> TagRegistryManager.lambda$fetchTags$0(iTagCollectionSupplier, arg_0));
    }

    public static void fetchTags() {
        idToRegistryMap.values().forEach(TagRegistry::fetchTags);
    }

    public static Multimap<ResourceLocation, ResourceLocation> validateTags(ITagCollectionSupplier iTagCollectionSupplier) {
        HashMultimap<ResourceLocation, ResourceLocation> hashMultimap = HashMultimap.create();
        idToRegistryMap.forEach((arg_0, arg_1) -> TagRegistryManager.lambda$validateTags$1(hashMultimap, iTagCollectionSupplier, arg_0, arg_1));
        return hashMultimap;
    }

    public static void checkHelperRegistrations() {
        TagRegistry[] tagRegistryArray = new TagRegistry[]{BlockTags.collection, ItemTags.collection, FluidTags.collection, EntityTypeTags.tagCollection};
        boolean bl = Stream.of(tagRegistryArray).anyMatch(TagRegistryManager::lambda$checkHelperRegistrations$2);
        if (bl) {
            throw new IllegalStateException("Missing helper registrations");
        }
    }

    private static boolean lambda$checkHelperRegistrations$2(TagRegistry tagRegistry) {
        return !idToRegistryMap.containsValue(tagRegistry);
    }

    private static void lambda$validateTags$1(Multimap multimap, ITagCollectionSupplier iTagCollectionSupplier, ResourceLocation resourceLocation, TagRegistry tagRegistry) {
        multimap.putAll(resourceLocation, tagRegistry.getTagIdsFromSupplier(iTagCollectionSupplier));
    }

    private static void lambda$fetchTags$0(ITagCollectionSupplier iTagCollectionSupplier, TagRegistry tagRegistry) {
        tagRegistry.fetchTags(iTagCollectionSupplier);
    }
}

