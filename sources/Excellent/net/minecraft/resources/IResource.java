package net.minecraft.resources;

import net.minecraft.resources.data.IMetadataSectionSerializer;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;
import java.io.Closeable;
import java.io.InputStream;

public interface IResource extends Closeable
{
    ResourceLocation getLocation();

    InputStream getInputStream();

    @Nullable
    <T> T getMetadata(IMetadataSectionSerializer<T> serializer);

    String getPackName();
}
