package net.minecraft.util;

import com.google.gson.*;
import java.util.*;
import com.google.common.collect.*;

public class JsonSerializableSet extends ForwardingSet<String> implements IJsonSerializable
{
    private final Set<String> underlyingSet;
    
    public void fromJson(final JsonElement jsonElement) {
        if (jsonElement.isJsonArray()) {
            final Iterator iterator = jsonElement.getAsJsonArray().iterator();
            "".length();
            if (0 >= 2) {
                throw null;
            }
            while (iterator.hasNext()) {
                this.add((Object)iterator.next().getAsString());
            }
        }
    }
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (-1 != -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public JsonElement getSerializableElement() {
        final JsonArray jsonArray = new JsonArray();
        final Iterator iterator = this.iterator();
        "".length();
        if (-1 != -1) {
            throw null;
        }
        while (iterator.hasNext()) {
            jsonArray.add((JsonElement)new JsonPrimitive((String)iterator.next()));
        }
        return (JsonElement)jsonArray;
    }
    
    protected Collection delegate() {
        return this.delegate();
    }
    
    public JsonSerializableSet() {
        this.underlyingSet = (Set<String>)Sets.newHashSet();
    }
    
    protected Set<String> delegate() {
        return this.underlyingSet;
    }
    
    protected Object delegate() {
        return this.delegate();
    }
}
