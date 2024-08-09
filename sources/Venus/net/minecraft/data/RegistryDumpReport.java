/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.data;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.io.IOException;
import java.nio.file.Path;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DirectoryCache;
import net.minecraft.data.IDataProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.DefaultedRegistry;
import net.minecraft.util.registry.Registry;

public class RegistryDumpReport
implements IDataProvider {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private final DataGenerator generator;

    public RegistryDumpReport(DataGenerator dataGenerator) {
        this.generator = dataGenerator;
    }

    @Override
    public void act(DirectoryCache directoryCache) throws IOException {
        JsonObject jsonObject = new JsonObject();
        Registry.REGISTRY.keySet().forEach(arg_0 -> RegistryDumpReport.lambda$act$0(jsonObject, arg_0));
        Path path = this.generator.getOutputFolder().resolve("reports/registries.json");
        IDataProvider.save(GSON, directoryCache, jsonObject, path);
    }

    private static <T> JsonElement serialize(Registry<T> registry) {
        JsonObject jsonObject = new JsonObject();
        if (registry instanceof DefaultedRegistry) {
            ResourceLocation resourceLocation = ((DefaultedRegistry)registry).getDefaultKey();
            jsonObject.addProperty("default", resourceLocation.toString());
        }
        int n = Registry.REGISTRY.getId(registry);
        jsonObject.addProperty("protocol_id", n);
        JsonObject jsonObject2 = new JsonObject();
        for (ResourceLocation resourceLocation : registry.keySet()) {
            T t = registry.getOrDefault(resourceLocation);
            int n2 = registry.getId(t);
            JsonObject jsonObject3 = new JsonObject();
            jsonObject3.addProperty("protocol_id", n2);
            jsonObject2.add(resourceLocation.toString(), jsonObject3);
        }
        jsonObject.add("entries", jsonObject2);
        return jsonObject;
    }

    @Override
    public String getName() {
        return "Registry Dump";
    }

    private static void lambda$act$0(JsonObject jsonObject, ResourceLocation resourceLocation) {
        jsonObject.add(resourceLocation.toString(), RegistryDumpReport.serialize(Registry.REGISTRY.getOrDefault(resourceLocation)));
    }
}

