package net.futureclient.client;

import java.util.Iterator;
import java.util.HashMap;
import com.google.gson.JsonParseException;
import com.google.gson.JsonDeserializationContext;
import java.lang.reflect.Type;
import com.google.gson.JsonElement;
import java.util.Map;
import com.google.gson.JsonDeserializer;

private static class Ri implements JsonDeserializer<Map<String, String>> {
    private Ri() {
        super();
    }
    
    public Ri(final Fh fh) {
        this();
    }
    
    @Override
    public Object deserialize(final JsonElement jsonElement, final Type type, final JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        return this.M(jsonElement, type, jsonDeserializationContext);
    }
    
    public Map<String, String> M(final JsonElement jsonElement, final Type type, final JsonDeserializationContext jsonDeserializationContext) {
        final HashMap<String, String> hashMap = new HashMap<String, String>();
        final Iterator<JsonElement> iterator = jsonElement.getAsJsonArray().iterator();
        while (iterator.hasNext()) {
            final Iterator<Map.Entry<String, JsonElement>> iterator2;
            if ((iterator2 = iterator.next().getAsJsonObject().entrySet().iterator()).hasNext()) {
                final Map.Entry<String, JsonElement> entry = iterator2.next();
                hashMap.put(entry.getKey(), entry.getValue().getAsString());
            }
        }
        return hashMap;
    }
}