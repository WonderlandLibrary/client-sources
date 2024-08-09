/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.data;

import java.nio.file.Path;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.TagsProvider;
import net.minecraft.entity.EntityType;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

public class EntityTypeTagsProvider
extends TagsProvider<EntityType<?>> {
    public EntityTypeTagsProvider(DataGenerator dataGenerator) {
        super(dataGenerator, Registry.ENTITY_TYPE);
    }

    @Override
    protected void registerTags() {
        this.getOrCreateBuilder(EntityTypeTags.SKELETONS).add(EntityType.SKELETON, EntityType.STRAY, EntityType.WITHER_SKELETON);
        this.getOrCreateBuilder(EntityTypeTags.RAIDERS).add(EntityType.EVOKER, EntityType.PILLAGER, EntityType.RAVAGER, EntityType.VINDICATOR, EntityType.ILLUSIONER, EntityType.WITCH);
        this.getOrCreateBuilder(EntityTypeTags.BEEHIVE_INHABITORS).addItemEntry(EntityType.BEE);
        this.getOrCreateBuilder(EntityTypeTags.ARROWS).add(EntityType.ARROW, EntityType.SPECTRAL_ARROW);
        this.getOrCreateBuilder(EntityTypeTags.IMPACT_PROJECTILES).addTag(EntityTypeTags.ARROWS).add(EntityType.SNOWBALL, EntityType.FIREBALL, EntityType.SMALL_FIREBALL, EntityType.EGG, EntityType.TRIDENT, EntityType.DRAGON_FIREBALL, EntityType.WITHER_SKULL);
    }

    @Override
    protected Path makePath(ResourceLocation resourceLocation) {
        return this.generator.getOutputFolder().resolve("data/" + resourceLocation.getNamespace() + "/tags/entity_types/" + resourceLocation.getPath() + ".json");
    }

    @Override
    public String getName() {
        return "Entity Type Tags";
    }
}

