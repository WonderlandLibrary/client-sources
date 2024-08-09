/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.loot;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonSyntaxException;
import com.mojang.datafixers.util.Pair;
import java.lang.reflect.Type;
import java.util.function.Function;
import javax.annotation.Nullable;
import net.minecraft.loot.LootType;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;

public class LootTypesManager {
    public static <E, T extends LootType<E>> LootTypeRegistryWrapper<E, T> getLootTypeRegistryWrapper(Registry<T> registry, String string, String string2, Function<E, T> function) {
        return new LootTypeRegistryWrapper<E, T>(registry, string, string2, function);
    }

    public static class LootTypeRegistryWrapper<E, T extends LootType<E>> {
        private final Registry<T> registry;
        private final String id;
        private final String name;
        private final Function<E, T> typeFunction;
        @Nullable
        private Pair<T, ISerializer<? extends E>> typeSerializer;

        private LootTypeRegistryWrapper(Registry<T> registry, String string, String string2, Function<E, T> function) {
            this.registry = registry;
            this.id = string;
            this.name = string2;
            this.typeFunction = function;
        }

        public Object getSerializer() {
            return new Serializer<E, T>(this.registry, this.id, this.name, this.typeFunction, this.typeSerializer);
        }
    }

    static class Serializer<E, T extends LootType<E>>
    implements JsonDeserializer<E>,
    JsonSerializer<E> {
        private final Registry<T> registry;
        private final String id;
        private final String name;
        private final Function<E, T> typeFunction;
        @Nullable
        private final Pair<T, ISerializer<? extends E>> typeSerializer;

        private Serializer(Registry<T> registry, String string, String string2, Function<E, T> function, @Nullable Pair<T, ISerializer<? extends E>> pair) {
            this.registry = registry;
            this.id = string;
            this.name = string2;
            this.typeFunction = function;
            this.typeSerializer = pair;
        }

        @Override
        public E deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            if (jsonElement.isJsonObject()) {
                JsonObject jsonObject = JSONUtils.getJsonObject(jsonElement, this.id);
                ResourceLocation resourceLocation = new ResourceLocation(JSONUtils.getString(jsonObject, this.name));
                LootType lootType = (LootType)this.registry.getOrDefault(resourceLocation);
                if (lootType == null) {
                    throw new JsonSyntaxException("Unknown type '" + resourceLocation + "'");
                }
                return (E)lootType.getSerializer().deserialize(jsonObject, jsonDeserializationContext);
            }
            if (this.typeSerializer == null) {
                throw new UnsupportedOperationException("Object " + jsonElement + " can't be deserialized");
            }
            return this.typeSerializer.getSecond().deserialize(jsonElement, jsonDeserializationContext);
        }

        @Override
        public JsonElement serialize(E e, Type type, JsonSerializationContext jsonSerializationContext) {
            LootType lootType = (LootType)this.typeFunction.apply(e);
            if (this.typeSerializer != null && this.typeSerializer.getFirst() == lootType) {
                return this.typeSerializer.getSecond().serializer(e, jsonSerializationContext);
            }
            if (lootType == null) {
                throw new JsonSyntaxException("Unknown type: " + e);
            }
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty(this.name, this.registry.getKey(lootType).toString());
            lootType.getSerializer().serialize(jsonObject, e, jsonSerializationContext);
            return jsonObject;
        }
    }

    public static interface ISerializer<T> {
        public JsonElement serializer(T var1, JsonSerializationContext var2);

        public T deserialize(JsonElement var1, JsonDeserializationContext var2);
    }
}

