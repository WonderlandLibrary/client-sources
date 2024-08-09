/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.tags;

import java.util.List;
import net.minecraft.entity.EntityType;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ITagCollection;
import net.minecraft.tags.ITagCollectionSupplier;
import net.minecraft.tags.TagRegistry;
import net.minecraft.tags.TagRegistryManager;
import net.minecraft.util.ResourceLocation;

public final class EntityTypeTags {
    protected static final TagRegistry<EntityType<?>> tagCollection = TagRegistryManager.create(new ResourceLocation("entity_type"), ITagCollectionSupplier::getEntityTypeTags);
    public static final ITag.INamedTag<EntityType<?>> SKELETONS = EntityTypeTags.getTagById("skeletons");
    public static final ITag.INamedTag<EntityType<?>> RAIDERS = EntityTypeTags.getTagById("raiders");
    public static final ITag.INamedTag<EntityType<?>> BEEHIVE_INHABITORS = EntityTypeTags.getTagById("beehive_inhabitors");
    public static final ITag.INamedTag<EntityType<?>> ARROWS = EntityTypeTags.getTagById("arrows");
    public static final ITag.INamedTag<EntityType<?>> IMPACT_PROJECTILES = EntityTypeTags.getTagById("impact_projectiles");

    private static ITag.INamedTag<EntityType<?>> getTagById(String string) {
        return tagCollection.createTag(string);
    }

    public static ITagCollection<EntityType<?>> getCollection() {
        return tagCollection.getCollection();
    }

    public static List<? extends ITag.INamedTag<EntityType<?>>> getAllTags() {
        return tagCollection.getTags();
    }
}

