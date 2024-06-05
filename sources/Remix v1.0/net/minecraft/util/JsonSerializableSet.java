package net.minecraft.util;

import com.google.common.collect.*;
import com.google.gson.*;
import java.util.*;

public class JsonSerializableSet extends ForwardingSet implements IJsonSerializable
{
    private final Set underlyingSet;
    
    public JsonSerializableSet() {
        this.underlyingSet = Sets.newHashSet();
    }
    
    public void func_152753_a(final JsonElement p_152753_1_) {
        if (p_152753_1_.isJsonArray()) {
            for (final JsonElement var3 : p_152753_1_.getAsJsonArray()) {
                this.add((Object)var3.getAsString());
            }
        }
    }
    
    public JsonElement getSerializableElement() {
        final JsonArray var1 = new JsonArray();
        for (final String var3 : this) {
            var1.add((JsonElement)new JsonPrimitive(var3));
        }
        return (JsonElement)var1;
    }
    
    protected Set delegate() {
        return this.underlyingSet;
    }
}
