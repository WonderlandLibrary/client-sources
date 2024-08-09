/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.advancements.criterion;

import com.google.common.base.Joiner;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSyntaxException;
import javax.annotation.Nullable;
import net.minecraft.entity.EntityType;
import net.minecraft.tags.ITag;
import net.minecraft.tags.TagCollectionManager;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

public abstract class EntityTypePredicate {
    public static final EntityTypePredicate ANY = new EntityTypePredicate(){

        @Override
        public boolean test(EntityType<?> entityType) {
            return false;
        }

        @Override
        public JsonElement serialize() {
            return JsonNull.INSTANCE;
        }
    };
    private static final Joiner JOINER = Joiner.on(", ");

    public abstract boolean test(EntityType<?> var1);

    public abstract JsonElement serialize();

    public static EntityTypePredicate deserialize(@Nullable JsonElement jsonElement) {
        if (jsonElement != null && !jsonElement.isJsonNull()) {
            String string = JSONUtils.getString(jsonElement, "type");
            if (string.startsWith("#")) {
                ResourceLocation resourceLocation = new ResourceLocation(string.substring(1));
                return new TagPredicate(TagCollectionManager.getManager().getEntityTypeTags().getTagByID(resourceLocation));
            }
            ResourceLocation resourceLocation = new ResourceLocation(string);
            EntityType<?> entityType = Registry.ENTITY_TYPE.getOptional(resourceLocation).orElseThrow(() -> EntityTypePredicate.lambda$deserialize$0(resourceLocation));
            return new TypePredicate(entityType);
        }
        return ANY;
    }

    public static EntityTypePredicate fromType(EntityType<?> entityType) {
        return new TypePredicate(entityType);
    }

    public static EntityTypePredicate fromTag(ITag<EntityType<?>> iTag) {
        return new TagPredicate(iTag);
    }

    private static JsonSyntaxException lambda$deserialize$0(ResourceLocation resourceLocation) {
        return new JsonSyntaxException("Unknown entity type '" + resourceLocation + "', valid types are: " + JOINER.join(Registry.ENTITY_TYPE.keySet()));
    }

    static class TagPredicate
    extends EntityTypePredicate {
        private final ITag<EntityType<?>> tag;

        public TagPredicate(ITag<EntityType<?>> iTag) {
            this.tag = iTag;
        }

        @Override
        public boolean test(EntityType<?> entityType) {
            return this.tag.contains(entityType);
        }

        @Override
        public JsonElement serialize() {
            return new JsonPrimitive("#" + TagCollectionManager.getManager().getEntityTypeTags().getValidatedIdFromTag(this.tag));
        }
    }

    static class TypePredicate
    extends EntityTypePredicate {
        private final EntityType<?> type;

        public TypePredicate(EntityType<?> entityType) {
            this.type = entityType;
        }

        @Override
        public boolean test(EntityType<?> entityType) {
            return this.type == entityType;
        }

        @Override
        public JsonElement serialize() {
            return new JsonPrimitive(Registry.ENTITY_TYPE.getKey(this.type).toString());
        }
    }
}

