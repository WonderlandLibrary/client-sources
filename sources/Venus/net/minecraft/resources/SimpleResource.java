/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.resources;

import com.google.gson.JsonObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import javax.annotation.Nullable;
import net.minecraft.resources.IResource;
import net.minecraft.resources.data.IMetadataSectionSerializer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.io.IOUtils;

public class SimpleResource
implements IResource {
    private final String packName;
    private final ResourceLocation location;
    private final InputStream inputStream;
    private final InputStream metadataInputStream;
    private boolean wasMetadataRead;
    private JsonObject metadataJson;

    public SimpleResource(String string, ResourceLocation resourceLocation, InputStream inputStream, @Nullable InputStream inputStream2) {
        this.packName = string;
        this.location = resourceLocation;
        this.inputStream = inputStream;
        this.metadataInputStream = inputStream2;
    }

    @Override
    public ResourceLocation getLocation() {
        return this.location;
    }

    @Override
    public InputStream getInputStream() {
        return this.inputStream;
    }

    public boolean hasMetadata() {
        return this.metadataInputStream != null;
    }

    @Override
    @Nullable
    public <T> T getMetadata(IMetadataSectionSerializer<T> iMetadataSectionSerializer) {
        Object object;
        if (!this.hasMetadata()) {
            return null;
        }
        if (this.metadataJson == null && !this.wasMetadataRead) {
            this.wasMetadataRead = true;
            object = null;
            try {
                object = new BufferedReader(new InputStreamReader(this.metadataInputStream, StandardCharsets.UTF_8));
                this.metadataJson = JSONUtils.fromJson((Reader)object);
            } finally {
                IOUtils.closeQuietly((Reader)object);
            }
        }
        if (this.metadataJson == null) {
            return null;
        }
        object = iMetadataSectionSerializer.getSectionName();
        return this.metadataJson.has((String)object) ? (T)iMetadataSectionSerializer.deserialize(JSONUtils.getJsonObject(this.metadataJson, (String)object)) : null;
    }

    @Override
    public String getPackName() {
        return this.packName;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return false;
        }
        if (!(object instanceof SimpleResource)) {
            return true;
        }
        SimpleResource simpleResource = (SimpleResource)object;
        if (this.location != null ? !this.location.equals(simpleResource.location) : simpleResource.location != null) {
            return true;
        }
        return this.packName != null ? !this.packName.equals(simpleResource.packName) : simpleResource.packName != null;
    }

    public int hashCode() {
        int n = this.packName != null ? this.packName.hashCode() : 0;
        return 31 * n + (this.location != null ? this.location.hashCode() : 0);
    }

    @Override
    public void close() throws IOException {
        this.inputStream.close();
        if (this.metadataInputStream != null) {
            this.metadataInputStream.close();
        }
    }
}

