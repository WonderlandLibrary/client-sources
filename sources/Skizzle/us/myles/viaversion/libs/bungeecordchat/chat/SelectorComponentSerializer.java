/*
 * Decompiled with CFR 0.150.
 */
package us.myles.viaversion.libs.bungeecordchat.chat;

import java.lang.reflect.Type;
import us.myles.viaversion.libs.bungeecordchat.api.chat.SelectorComponent;
import us.myles.viaversion.libs.bungeecordchat.chat.BaseComponentSerializer;
import us.myles.viaversion.libs.gson.JsonDeserializationContext;
import us.myles.viaversion.libs.gson.JsonDeserializer;
import us.myles.viaversion.libs.gson.JsonElement;
import us.myles.viaversion.libs.gson.JsonObject;
import us.myles.viaversion.libs.gson.JsonParseException;
import us.myles.viaversion.libs.gson.JsonSerializationContext;
import us.myles.viaversion.libs.gson.JsonSerializer;

public class SelectorComponentSerializer
extends BaseComponentSerializer
implements JsonSerializer<SelectorComponent>,
JsonDeserializer<SelectorComponent> {
    @Override
    public SelectorComponent deserialize(JsonElement element, Type type, JsonDeserializationContext context) throws JsonParseException {
        JsonObject object = element.getAsJsonObject();
        if (!object.has("selector")) {
            throw new JsonParseException("Could not parse JSON: missing 'selector' property");
        }
        SelectorComponent component = new SelectorComponent(object.get("selector").getAsString());
        this.deserialize(object, component, context);
        return component;
    }

    @Override
    public JsonElement serialize(SelectorComponent component, Type type, JsonSerializationContext context) {
        JsonObject object = new JsonObject();
        this.serialize(object, component, context);
        object.addProperty("selector", component.getSelector());
        return object;
    }
}

