/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.resources;

import com.google.common.collect.Maps;
import com.google.common.io.Files;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ResourceIndex {
    protected static final Logger LOGGER = LogManager.getLogger();
    private final Map<String, File> rootFiles = Maps.newHashMap();
    private final Map<ResourceLocation, File> namespaceFiles = Maps.newHashMap();

    protected ResourceIndex() {
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public ResourceIndex(File file, String string) {
        File file2 = new File(file, "objects");
        File file3 = new File(file, "indexes/" + string + ".json");
        BufferedReader bufferedReader = null;
        try {
            bufferedReader = Files.newReader(file3, StandardCharsets.UTF_8);
            JsonObject jsonObject = JSONUtils.fromJson(bufferedReader);
            JsonObject jsonObject2 = JSONUtils.getJsonObject(jsonObject, "objects", null);
            if (jsonObject2 != null) {
                for (Map.Entry<String, JsonElement> entry : jsonObject2.entrySet()) {
                    JsonObject jsonObject3 = (JsonObject)entry.getValue();
                    String string2 = entry.getKey();
                    String[] stringArray = string2.split("/", 2);
                    String string3 = JSONUtils.getString(jsonObject3, "hash");
                    File file4 = new File(file2, string3.substring(0, 2) + "/" + string3);
                    if (stringArray.length == 1) {
                        this.rootFiles.put(stringArray[0], file4);
                        continue;
                    }
                    this.namespaceFiles.put(new ResourceLocation(stringArray[0], stringArray[5]), file4);
                }
            }
        } catch (JsonParseException jsonParseException) {
            LOGGER.error("Unable to parse resource index file: {}", (Object)file3);
        } catch (FileNotFoundException fileNotFoundException) {
            LOGGER.error("Can't find the resource index file: {}", (Object)file3);
        } finally {
            IOUtils.closeQuietly(bufferedReader);
        }
    }

    @Nullable
    public File getFile(ResourceLocation resourceLocation) {
        return this.namespaceFiles.get(resourceLocation);
    }

    @Nullable
    public File getFile(String string) {
        return this.rootFiles.get(string);
    }

    public Collection<ResourceLocation> getFiles(String string, String string2, int n, Predicate<String> predicate) {
        return this.namespaceFiles.keySet().stream().filter(arg_0 -> ResourceIndex.lambda$getFiles$0(string2, string, predicate, arg_0)).collect(Collectors.toList());
    }

    private static boolean lambda$getFiles$0(String string, String string2, Predicate predicate, ResourceLocation resourceLocation) {
        String string3 = resourceLocation.getPath();
        return resourceLocation.getNamespace().equals(string) && !string3.endsWith(".mcmeta") && string3.startsWith(string2 + "/") && predicate.test(string3);
    }
}

