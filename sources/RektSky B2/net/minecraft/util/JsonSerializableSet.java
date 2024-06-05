package net.minecraft.util;

import com.google.common.collect.*;
import com.google.gson.*;
import java.util.*;

public class JsonSerializableSet extends ForwardingSet<String> implements IJsonSerializable
{
    private final Set<String> underlyingSet;
    
    public JsonSerializableSet() {
        this.underlyingSet = (Set<String>)Sets.newHashSet();
    }
    
    @Override
    public void fromJson(final JsonElement json) {
        if (json.isJsonArray()) {
            for (final JsonElement jsonelement : json.getAsJsonArray()) {
                this.add(jsonelement.getAsString());
            }
        }
    }
    
    @Override
    public JsonElement getSerializableElement() {
        final JsonArray jsonarray = new JsonArray();
        for (final String s : this) {
            jsonarray.add(new JsonPrimitive(s));
        }
        return jsonarray;
    }
    
    @Override
    protected Set<String> delegate() {
        return this.underlyingSet;
    }
}
