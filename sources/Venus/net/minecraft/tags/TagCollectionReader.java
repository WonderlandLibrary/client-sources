/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.tags;

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Function;
import java.util.stream.Collectors;
import net.minecraft.resources.IResource;
import net.minecraft.resources.IResourceManager;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ITagCollection;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TagCollectionReader<T> {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Gson GSON = new Gson();
    private static final int FILE_TYPE_LENGHT_VALUE = 5;
    private final Function<ResourceLocation, Optional<T>> idToTagFunction;
    private final String path;
    private final String tagType;

    public TagCollectionReader(Function<ResourceLocation, Optional<T>> function, String string, String string2) {
        this.idToTagFunction = function;
        this.path = string;
        this.tagType = string2;
    }

    public CompletableFuture<Map<ResourceLocation, ITag.Builder>> readTagsFromManager(IResourceManager iResourceManager, Executor executor) {
        return CompletableFuture.supplyAsync(() -> this.lambda$readTagsFromManager$2(iResourceManager), executor);
    }

    public ITagCollection<T> buildTagCollectionFromMap(Map<ResourceLocation, ITag.Builder> map) {
        HashMap hashMap = Maps.newHashMap();
        Function function = hashMap::get;
        Function<ResourceLocation, Object> function2 = this::lambda$buildTagCollectionFromMap$3;
        while (!map.isEmpty()) {
            boolean bl = false;
            Iterator<Map.Entry<ResourceLocation, ITag.Builder>> iterator2 = map.entrySet().iterator();
            while (iterator2.hasNext()) {
                Map.Entry<ResourceLocation, ITag.Builder> entry = iterator2.next();
                Optional<ITag<Object>> optional = entry.getValue().build(function, function2);
                if (!optional.isPresent()) continue;
                hashMap.put(entry.getKey(), optional.get());
                iterator2.remove();
                bl = true;
            }
            if (bl) continue;
            break;
        }
        map.forEach((arg_0, arg_1) -> this.lambda$buildTagCollectionFromMap$4(function, function2, arg_0, arg_1));
        return ITagCollection.getTagCollectionFromMap(hashMap);
    }

    private void lambda$buildTagCollectionFromMap$4(Function function, Function function2, ResourceLocation resourceLocation, ITag.Builder builder) {
        LOGGER.error("Couldn't load {} tag {} as it is missing following references: {}", (Object)this.tagType, (Object)resourceLocation, (Object)builder.getProxyTags(function, function2).map(Objects::toString).collect(Collectors.joining(",")));
    }

    private Object lambda$buildTagCollectionFromMap$3(ResourceLocation resourceLocation) {
        return this.idToTagFunction.apply(resourceLocation).orElse(null);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private Map lambda$readTagsFromManager$2(IResourceManager iResourceManager) {
        HashMap<ResourceLocation, ITag.Builder> hashMap = Maps.newHashMap();
        for (ResourceLocation resourceLocation : iResourceManager.getAllResourceLocations(this.path, TagCollectionReader::lambda$readTagsFromManager$0)) {
            String string = resourceLocation.getPath();
            ResourceLocation resourceLocation2 = new ResourceLocation(resourceLocation.getNamespace(), string.substring(this.path.length() + 1, string.length() - FILE_TYPE_LENGHT_VALUE));
            try {
                for (IResource iResource : iResourceManager.getAllResources(resourceLocation)) {
                    try {
                        InputStream inputStream = iResource.getInputStream();
                        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));){
                            JsonObject jsonObject = JSONUtils.fromJson(GSON, bufferedReader, JsonObject.class);
                            if (jsonObject == null) {
                                LOGGER.error("Couldn't load {} tag list {} from {} in data pack {} as it is empty or null", (Object)this.tagType, (Object)resourceLocation2, (Object)resourceLocation, (Object)iResource.getPackName());
                                continue;
                            }
                            hashMap.computeIfAbsent(resourceLocation2, TagCollectionReader::lambda$readTagsFromManager$1).deserialize(jsonObject, iResource.getPackName());
                        } finally {
                            if (inputStream == null) continue;
                            inputStream.close();
                        }
                    } catch (IOException | RuntimeException exception) {
                        LOGGER.error("Couldn't read {} tag list {} from {} in data pack {}", (Object)this.tagType, (Object)resourceLocation2, (Object)resourceLocation, (Object)iResource.getPackName(), (Object)exception);
                    } finally {
                        IOUtils.closeQuietly((Closeable)iResource);
                    }
                }
            } catch (IOException iOException) {
                LOGGER.error("Couldn't read {} tag list {} from {}", (Object)this.tagType, (Object)resourceLocation2, (Object)resourceLocation, (Object)iOException);
            }
        }
        return hashMap;
    }

    private static ITag.Builder lambda$readTagsFromManager$1(ResourceLocation resourceLocation) {
        return ITag.Builder.create();
    }

    private static boolean lambda$readTagsFromManager$0(String string) {
        return string.endsWith(".json");
    }
}

