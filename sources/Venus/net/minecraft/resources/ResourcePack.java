/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.resources;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import javax.annotation.Nullable;
import net.minecraft.resources.IResourcePack;
import net.minecraft.resources.ResourcePackType;
import net.minecraft.resources.data.IMetadataSectionSerializer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class ResourcePack
implements IResourcePack {
    private static final Logger LOGGER = LogManager.getLogger();
    public final File file;

    public ResourcePack(File file) {
        this.file = file;
    }

    private static String getFullPath(ResourcePackType resourcePackType, ResourceLocation resourceLocation) {
        return String.format("%s/%s/%s", resourcePackType.getDirectoryName(), resourceLocation.getNamespace(), resourceLocation.getPath());
    }

    protected static String getRelativeString(File file, File file2) {
        return file.toURI().relativize(file2.toURI()).getPath();
    }

    @Override
    public InputStream getResourceStream(ResourcePackType resourcePackType, ResourceLocation resourceLocation) throws IOException {
        return this.getInputStream(ResourcePack.getFullPath(resourcePackType, resourceLocation));
    }

    @Override
    public boolean resourceExists(ResourcePackType resourcePackType, ResourceLocation resourceLocation) {
        return this.resourceExists(ResourcePack.getFullPath(resourcePackType, resourceLocation));
    }

    protected abstract InputStream getInputStream(String var1) throws IOException;

    @Override
    public InputStream getRootResourceStream(String string) throws IOException {
        if (!string.contains("/") && !string.contains("\\")) {
            return this.getInputStream(string);
        }
        throw new IllegalArgumentException("Root resources can only be filenames, not paths (no / allowed!)");
    }

    protected abstract boolean resourceExists(String var1);

    protected void onIgnoreNonLowercaseNamespace(String string) {
        LOGGER.warn("ResourcePack: ignored non-lowercase namespace: {} in {}", (Object)string, (Object)this.file);
    }

    @Override
    @Nullable
    public <T> T getMetadata(IMetadataSectionSerializer<T> iMetadataSectionSerializer) throws IOException {
        T t;
        try (InputStream inputStream = this.getInputStream("pack.mcmeta");){
            t = ResourcePack.getResourceMetadata(iMetadataSectionSerializer, inputStream);
        }
        return t;
    }

    @Nullable
    public static <T> T getResourceMetadata(IMetadataSectionSerializer<T> iMetadataSectionSerializer, InputStream inputStream) {
        JsonObject jsonObject;
        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));){
            jsonObject = JSONUtils.fromJson(bufferedReader);
        } catch (JsonParseException | IOException exception) {
            LOGGER.error("Couldn't load {} metadata", (Object)iMetadataSectionSerializer.getSectionName(), (Object)exception);
            return null;
        }
        if (!jsonObject.has(iMetadataSectionSerializer.getSectionName())) {
            return null;
        }
        try {
            return iMetadataSectionSerializer.deserialize(JSONUtils.getJsonObject(jsonObject, iMetadataSectionSerializer.getSectionName()));
        } catch (JsonParseException jsonParseException) {
            LOGGER.error("Couldn't load {} metadata", (Object)iMetadataSectionSerializer.getSectionName(), (Object)jsonParseException);
            return null;
        }
    }

    @Override
    public String getName() {
        return this.file.getName();
    }
}

