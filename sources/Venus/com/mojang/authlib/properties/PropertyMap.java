/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.authlib.properties;

import com.google.common.collect.ForwardingMultimap;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.mojang.authlib.properties.Property;
import java.lang.reflect.Type;
import java.util.Map;

public class PropertyMap
extends ForwardingMultimap<String, Property> {
    private final Multimap<String, Property> properties = LinkedHashMultimap.create();

    @Override
    protected Multimap<String, Property> delegate() {
        return this.properties;
    }

    @Override
    protected Object delegate() {
        return this.delegate();
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class Serializer
    implements JsonSerializer<PropertyMap>,
    JsonDeserializer<PropertyMap> {
        @Override
        public PropertyMap deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            PropertyMap propertyMap;
            block5: {
                block4: {
                    propertyMap = new PropertyMap();
                    if (!(jsonElement instanceof JsonObject)) break block4;
                    JsonObject jsonObject = (JsonObject)jsonElement;
                    for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
                        if (!(entry.getValue() instanceof JsonArray)) continue;
                        for (JsonElement jsonElement2 : (JsonArray)entry.getValue()) {
                            propertyMap.put(entry.getKey(), new Property(entry.getKey(), jsonElement2.getAsString()));
                        }
                    }
                    break block5;
                }
                if (!(jsonElement instanceof JsonArray)) break block5;
                for (JsonElement jsonElement3 : (JsonArray)jsonElement) {
                    if (!(jsonElement3 instanceof JsonObject)) continue;
                    JsonObject jsonObject = (JsonObject)jsonElement3;
                    String string = jsonObject.getAsJsonPrimitive("name").getAsString();
                    String string2 = jsonObject.getAsJsonPrimitive("value").getAsString();
                    if (jsonObject.has("signature")) {
                        propertyMap.put(string, new Property(string, string2, jsonObject.getAsJsonPrimitive("signature").getAsString()));
                        continue;
                    }
                    propertyMap.put(string, new Property(string, string2));
                }
            }
            return propertyMap;
        }

        @Override
        public JsonElement serialize(PropertyMap propertyMap, Type type, JsonSerializationContext jsonSerializationContext) {
            JsonArray jsonArray = new JsonArray();
            for (Property property : propertyMap.values()) {
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("name", property.getName());
                jsonObject.addProperty("value", property.getValue());
                if (property.hasSignature()) {
                    jsonObject.addProperty("signature", property.getSignature());
                }
                jsonArray.add(jsonObject);
            }
            return jsonArray;
        }

        @Override
        public JsonElement serialize(Object object, Type type, JsonSerializationContext jsonSerializationContext) {
            return this.serialize((PropertyMap)object, type, jsonSerializationContext);
        }

        @Override
        public Object deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            return this.deserialize(jsonElement, type, jsonDeserializationContext);
        }
    }
}

