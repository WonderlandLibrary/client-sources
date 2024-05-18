package net.minecraft.util;

import com.google.gson.*;

public interface IJsonSerializable
{
    JsonElement getSerializableElement();
    
    void fromJson(final JsonElement p0);
}
