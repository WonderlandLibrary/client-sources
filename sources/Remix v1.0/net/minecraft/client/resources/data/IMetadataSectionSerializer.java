package net.minecraft.client.resources.data;

import com.google.gson.*;

public interface IMetadataSectionSerializer extends JsonDeserializer
{
    String getSectionName();
}
