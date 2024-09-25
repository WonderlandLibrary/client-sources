/*
 * Decompiled with CFR 0.150.
 */
package us.myles.viaversion.libs.bungeecordchat.api.chat.hover.content;

import java.lang.reflect.Type;
import us.myles.viaversion.libs.bungeecordchat.api.chat.BaseComponent;
import us.myles.viaversion.libs.bungeecordchat.api.chat.hover.content.Entity;
import us.myles.viaversion.libs.gson.JsonDeserializationContext;
import us.myles.viaversion.libs.gson.JsonDeserializer;
import us.myles.viaversion.libs.gson.JsonElement;
import us.myles.viaversion.libs.gson.JsonObject;
import us.myles.viaversion.libs.gson.JsonParseException;
import us.myles.viaversion.libs.gson.JsonSerializationContext;
import us.myles.viaversion.libs.gson.JsonSerializer;

public class EntitySerializer
implements JsonSerializer<Entity>,
JsonDeserializer<Entity> {
    @Override
    public Entity deserialize(JsonElement element, Type type, JsonDeserializationContext context) throws JsonParseException {
        JsonObject value = element.getAsJsonObject();
        return new Entity(value.has("type") ? value.get("type").getAsString() : null, value.get("id").getAsString(), value.has("name") ? (BaseComponent)context.deserialize(value.get("name"), (Type)((Object)BaseComponent.class)) : null);
    }

    @Override
    public JsonElement serialize(Entity content, Type type, JsonSerializationContext context) {
        JsonObject object = new JsonObject();
        object.addProperty("type", content.getType() != null ? content.getType() : "minecraft:pig");
        object.addProperty("id", content.getId());
        if (content.getName() != null) {
            object.add("name", context.serialize(content.getName()));
        }
        return object;
    }
}

