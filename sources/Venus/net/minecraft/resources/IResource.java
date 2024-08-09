/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.resources;

import java.io.Closeable;
import java.io.InputStream;
import javax.annotation.Nullable;
import net.minecraft.resources.data.IMetadataSectionSerializer;
import net.minecraft.util.ResourceLocation;

public interface IResource
extends Closeable {
    public ResourceLocation getLocation();

    public InputStream getInputStream();

    @Nullable
    public <T> T getMetadata(IMetadataSectionSerializer<T> var1);

    public String getPackName();
}

