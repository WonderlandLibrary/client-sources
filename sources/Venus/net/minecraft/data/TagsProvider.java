/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.data;

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.attribute.FileAttribute;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.data.IDataProvider;
import net.minecraft.tags.ITag;
import net.minecraft.tags.Tag;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class TagsProvider<T>
implements IDataProvider {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    protected final DataGenerator generator;
    protected final Registry<T> registry;
    private final Map<ResourceLocation, ITag.Builder> tagToBuilder = Maps.newLinkedHashMap();

    protected TagsProvider(DataGenerator dataGenerator, Registry<T> registry) {
        this.generator = dataGenerator;
        this.registry = registry;
    }

    protected abstract void registerTags();

    @Override
    public void act(DirectoryCache directoryCache) {
        this.tagToBuilder.clear();
        this.registerTags();
        Tag tag = Tag.getEmptyTag();
        Function<ResourceLocation, ITag> function = arg_0 -> this.lambda$act$0(tag, arg_0);
        Function<ResourceLocation, Object> function2 = this::lambda$act$1;
        this.tagToBuilder.forEach((arg_0, arg_1) -> this.lambda$act$2(function, function2, directoryCache, arg_0, arg_1));
    }

    protected abstract Path makePath(ResourceLocation var1);

    protected Builder<T> getOrCreateBuilder(ITag.INamedTag<T> iNamedTag) {
        ITag.Builder builder = this.createBuilderIfAbsent(iNamedTag);
        return new Builder<T>(builder, this.registry, "vanilla");
    }

    protected ITag.Builder createBuilderIfAbsent(ITag.INamedTag<T> iNamedTag) {
        return this.tagToBuilder.computeIfAbsent(iNamedTag.getName(), TagsProvider::lambda$createBuilderIfAbsent$3);
    }

    private static ITag.Builder lambda$createBuilderIfAbsent$3(ResourceLocation resourceLocation) {
        return new ITag.Builder();
    }

    private void lambda$act$2(Function function, Function function2, DirectoryCache directoryCache, ResourceLocation resourceLocation, ITag.Builder builder) {
        List list = builder.getProxyTags(function, function2).collect(Collectors.toList());
        if (!list.isEmpty()) {
            throw new IllegalArgumentException(String.format("Couldn't define tag %s as it is missing following references: %s", resourceLocation, list.stream().map(Objects::toString).collect(Collectors.joining(","))));
        }
        JsonObject jsonObject = builder.serialize();
        Path path = this.makePath(resourceLocation);
        try {
            String string = GSON.toJson(jsonObject);
            String string2 = HASH_FUNCTION.hashUnencodedChars(string).toString();
            if (!Objects.equals(directoryCache.getPreviousHash(path), string2) || !Files.exists(path, new LinkOption[0])) {
                Files.createDirectories(path.getParent(), new FileAttribute[0]);
                try (BufferedWriter bufferedWriter = Files.newBufferedWriter(path, new OpenOption[0]);){
                    bufferedWriter.write(string);
                }
            }
            directoryCache.recordHash(path, string2);
        } catch (IOException iOException) {
            LOGGER.error("Couldn't save tags to {}", (Object)path, (Object)iOException);
        }
    }

    private Object lambda$act$1(ResourceLocation resourceLocation) {
        return this.registry.getOptional(resourceLocation).orElse(null);
    }

    private ITag lambda$act$0(ITag iTag, ResourceLocation resourceLocation) {
        return this.tagToBuilder.containsKey(resourceLocation) ? iTag : null;
    }

    public static class Builder<T> {
        private final ITag.Builder builder;
        private final Registry<T> registry;
        private final String id;

        private Builder(ITag.Builder builder, Registry<T> registry, String string) {
            this.builder = builder;
            this.registry = registry;
            this.id = string;
        }

        public Builder<T> addItemEntry(T t) {
            this.builder.addItemEntry(this.registry.getKey(t), this.id);
            return this;
        }

        public Builder<T> addTag(ITag.INamedTag<T> iNamedTag) {
            this.builder.addTagEntry(iNamedTag.getName(), this.id);
            return this;
        }

        @SafeVarargs
        public final Builder<T> add(T ... TArray) {
            Stream.of(TArray).map(this.registry::getKey).forEach(this::lambda$add$0);
            return this;
        }

        private void lambda$add$0(ResourceLocation resourceLocation) {
            this.builder.addItemEntry(resourceLocation, this.id);
        }
    }
}

