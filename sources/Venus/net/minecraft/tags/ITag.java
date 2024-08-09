/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.tags;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;
import net.minecraft.tags.ITagCollection;
import net.minecraft.tags.Tag;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;

public interface ITag<T> {
    public static <T> Codec<ITag<T>> getTagCodec(Supplier<ITagCollection<T>> supplier) {
        return ResourceLocation.CODEC.flatXmap(arg_0 -> ITag.lambda$getTagCodec$1(supplier, arg_0), arg_0 -> ITag.lambda$getTagCodec$3(supplier, arg_0));
    }

    public boolean contains(T var1);

    public List<T> getAllElements();

    default public T getRandomElement(Random random2) {
        List<T> list = this.getAllElements();
        return list.get(random2.nextInt(list.size()));
    }

    public static <T> ITag<T> getTagOf(Set<T> set) {
        return Tag.getTagFromContents(set);
    }

    private static DataResult lambda$getTagCodec$3(Supplier supplier, ITag iTag) {
        return Optional.ofNullable(((ITagCollection)supplier.get()).getDirectIdFromTag(iTag)).map(DataResult::success).orElseGet(() -> ITag.lambda$getTagCodec$2(iTag));
    }

    private static DataResult lambda$getTagCodec$2(ITag iTag) {
        return DataResult.error("Unknown tag: " + iTag);
    }

    private static DataResult lambda$getTagCodec$1(Supplier supplier, ResourceLocation resourceLocation) {
        return Optional.ofNullable(((ITagCollection)supplier.get()).get(resourceLocation)).map(DataResult::success).orElseGet(() -> ITag.lambda$getTagCodec$0(resourceLocation));
    }

    private static DataResult lambda$getTagCodec$0(ResourceLocation resourceLocation) {
        return DataResult.error("Unknown tag: " + resourceLocation);
    }

    public static class TagEntry
    implements ITagEntry {
        private final ResourceLocation id;

        public TagEntry(ResourceLocation resourceLocation) {
            this.id = resourceLocation;
        }

        @Override
        public <T> boolean matches(Function<ResourceLocation, ITag<T>> function, Function<ResourceLocation, T> function2, Consumer<T> consumer) {
            ITag<T> iTag = function.apply(this.id);
            if (iTag == null) {
                return true;
            }
            iTag.getAllElements().forEach(consumer);
            return false;
        }

        @Override
        public void addAdditionalData(JsonArray jsonArray) {
            jsonArray.add("#" + this.id);
        }

        public String toString() {
            return "#" + this.id;
        }
    }

    public static class Proxy {
        private final ITagEntry entry;
        private final String identifier;

        private Proxy(ITagEntry iTagEntry, String string) {
            this.entry = iTagEntry;
            this.identifier = string;
        }

        public ITagEntry getEntry() {
            return this.entry;
        }

        public String toString() {
            return this.entry.toString() + " (from " + this.identifier + ")";
        }
    }

    public static class OptionalTagEntry
    implements ITagEntry {
        private final ResourceLocation id;

        public OptionalTagEntry(ResourceLocation resourceLocation) {
            this.id = resourceLocation;
        }

        @Override
        public <T> boolean matches(Function<ResourceLocation, ITag<T>> function, Function<ResourceLocation, T> function2, Consumer<T> consumer) {
            ITag<T> iTag = function.apply(this.id);
            if (iTag != null) {
                iTag.getAllElements().forEach(consumer);
            }
            return false;
        }

        @Override
        public void addAdditionalData(JsonArray jsonArray) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("id", "#" + this.id);
            jsonObject.addProperty("required", false);
            jsonArray.add(jsonObject);
        }

        public String toString() {
            return "#" + this.id + "?";
        }
    }

    public static class OptionalItemEntry
    implements ITagEntry {
        private final ResourceLocation id;

        public OptionalItemEntry(ResourceLocation resourceLocation) {
            this.id = resourceLocation;
        }

        @Override
        public <T> boolean matches(Function<ResourceLocation, ITag<T>> function, Function<ResourceLocation, T> function2, Consumer<T> consumer) {
            T t = function2.apply(this.id);
            if (t != null) {
                consumer.accept(t);
            }
            return false;
        }

        @Override
        public void addAdditionalData(JsonArray jsonArray) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("id", this.id.toString());
            jsonObject.addProperty("required", false);
            jsonArray.add(jsonObject);
        }

        public String toString() {
            return this.id.toString() + "?";
        }
    }

    public static class ItemEntry
    implements ITagEntry {
        private final ResourceLocation identifier;

        public ItemEntry(ResourceLocation resourceLocation) {
            this.identifier = resourceLocation;
        }

        @Override
        public <T> boolean matches(Function<ResourceLocation, ITag<T>> function, Function<ResourceLocation, T> function2, Consumer<T> consumer) {
            T t = function2.apply(this.identifier);
            if (t == null) {
                return true;
            }
            consumer.accept(t);
            return false;
        }

        @Override
        public void addAdditionalData(JsonArray jsonArray) {
            jsonArray.add(this.identifier.toString());
        }

        public String toString() {
            return this.identifier.toString();
        }
    }

    public static interface ITagEntry {
        public <T> boolean matches(Function<ResourceLocation, ITag<T>> var1, Function<ResourceLocation, T> var2, Consumer<T> var3);

        public void addAdditionalData(JsonArray var1);
    }

    public static interface INamedTag<T>
    extends ITag<T> {
        public ResourceLocation getName();
    }

    public static class Builder {
        private final List<Proxy> proxyTags = Lists.newArrayList();

        public static Builder create() {
            return new Builder();
        }

        public Builder addProxyTag(Proxy proxy) {
            this.proxyTags.add(proxy);
            return this;
        }

        public Builder addTag(ITagEntry iTagEntry, String string) {
            return this.addProxyTag(new Proxy(iTagEntry, string));
        }

        public Builder addItemEntry(ResourceLocation resourceLocation, String string) {
            return this.addTag(new ItemEntry(resourceLocation), string);
        }

        public Builder addTagEntry(ResourceLocation resourceLocation, String string) {
            return this.addTag(new TagEntry(resourceLocation), string);
        }

        public <T> Optional<ITag<T>> build(Function<ResourceLocation, ITag<T>> function, Function<ResourceLocation, T> function2) {
            ImmutableSet.Builder builder = ImmutableSet.builder();
            for (Proxy proxy : this.proxyTags) {
                if (proxy.getEntry().matches(function, function2, builder::add)) continue;
                return Optional.empty();
            }
            return Optional.of(ITag.getTagOf(builder.build()));
        }

        public Stream<Proxy> getProxyStream() {
            return this.proxyTags.stream();
        }

        public <T> Stream<Proxy> getProxyTags(Function<ResourceLocation, ITag<T>> function, Function<ResourceLocation, T> function2) {
            return this.getProxyStream().filter(arg_0 -> Builder.lambda$getProxyTags$1(function, function2, arg_0));
        }

        public Builder deserialize(JsonObject jsonObject, String string) {
            JsonArray jsonArray = JSONUtils.getJsonArray(jsonObject, "values");
            ArrayList<ITagEntry> arrayList = Lists.newArrayList();
            for (JsonElement jsonElement : jsonArray) {
                arrayList.add(Builder.deserializeTagEntry(jsonElement));
            }
            if (JSONUtils.getBoolean(jsonObject, "replace", false)) {
                this.proxyTags.clear();
            }
            arrayList.forEach(arg_0 -> this.lambda$deserialize$2(string, arg_0));
            return this;
        }

        private static ITagEntry deserializeTagEntry(JsonElement jsonElement) {
            boolean bl;
            String string;
            Object object;
            if (jsonElement.isJsonObject()) {
                object = jsonElement.getAsJsonObject();
                string = JSONUtils.getString((JsonObject)object, "id");
                bl = JSONUtils.getBoolean((JsonObject)object, "required", true);
            } else {
                string = JSONUtils.getString(jsonElement, "id");
                bl = true;
            }
            if (string.startsWith("#")) {
                object = new ResourceLocation(string.substring(1));
                return bl ? new TagEntry((ResourceLocation)object) : new OptionalTagEntry((ResourceLocation)object);
            }
            object = new ResourceLocation(string);
            return bl ? new ItemEntry((ResourceLocation)object) : new OptionalItemEntry((ResourceLocation)object);
        }

        public JsonObject serialize() {
            JsonObject jsonObject = new JsonObject();
            JsonArray jsonArray = new JsonArray();
            for (Proxy proxy : this.proxyTags) {
                proxy.getEntry().addAdditionalData(jsonArray);
            }
            jsonObject.addProperty("replace", false);
            jsonObject.add("values", jsonArray);
            return jsonObject;
        }

        private void lambda$deserialize$2(String string, ITagEntry iTagEntry) {
            this.proxyTags.add(new Proxy(iTagEntry, string));
        }

        private static boolean lambda$getProxyTags$1(Function function, Function function2, Proxy proxy) {
            return !proxy.getEntry().matches(function, function2, Builder::lambda$getProxyTags$0);
        }

        private static void lambda$getProxyTags$0(Object object) {
        }
    }
}

