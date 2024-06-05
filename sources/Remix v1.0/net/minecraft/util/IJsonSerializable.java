package net.minecraft.util;

import com.google.gson.*;

public interface IJsonSerializable
{
    void func_152753_a(final JsonElement p0);
    
    JsonElement getSerializableElement();
}
