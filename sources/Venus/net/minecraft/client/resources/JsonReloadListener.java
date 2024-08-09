/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.resources;

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.client.resources.ReloadListener;
import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IResource;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class JsonReloadListener
extends ReloadListener<Map<ResourceLocation, JsonElement>> {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final int JSON_EXTENSION_LENGTH = 5;
    private final Gson gson;
    private final String folder;

    public JsonReloadListener(Gson gson, String string) {
        this.gson = gson;
        this.folder = string;
    }

    @Override
    protected Map<ResourceLocation, JsonElement> prepare(IResourceManager iResourceManager, IProfiler iProfiler) {
        HashMap<ResourceLocation, JsonElement> hashMap = Maps.newHashMap();
        int n = this.folder.length() + 1;
        for (ResourceLocation resourceLocation : iResourceManager.getAllResourceLocations(this.folder, JsonReloadListener::lambda$prepare$0)) {
            String string = resourceLocation.getPath();
            ResourceLocation resourceLocation2 = new ResourceLocation(resourceLocation.getNamespace(), string.substring(n, string.length() - JSON_EXTENSION_LENGTH));
            try {
                IResource iResource = iResourceManager.getResource(resourceLocation);
                try {
                    InputStream inputStream = iResource.getInputStream();
                    try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));){
                        JsonElement jsonElement = JSONUtils.fromJson(this.gson, bufferedReader, JsonElement.class);
                        if (jsonElement != null) {
                            JsonElement jsonElement2 = hashMap.put(resourceLocation2, jsonElement);
                            if (jsonElement2 == null) continue;
                            throw new IllegalStateException("Duplicate data file ignored with ID " + resourceLocation2);
                        }
                        LOGGER.error("Couldn't load data file {} from {} as it's null or empty", (Object)resourceLocation2, (Object)resourceLocation);
                    } finally {
                        if (inputStream == null) continue;
                        inputStream.close();
                    }
                } finally {
                    if (iResource == null) continue;
                    iResource.close();
                }
            } catch (JsonParseException | IOException | IllegalArgumentException exception) {
                LOGGER.error("Couldn't parse data file {} from {}", (Object)resourceLocation2, (Object)resourceLocation, (Object)exception);
            }
        }
        return hashMap;
    }

    @Override
    protected Object prepare(IResourceManager iResourceManager, IProfiler iProfiler) {
        return this.prepare(iResourceManager, iProfiler);
    }

    private static boolean lambda$prepare$0(String string) {
        return string.endsWith(".json");
    }
}

