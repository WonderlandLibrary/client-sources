/*
 * Decompiled with CFR 0.150.
 */
package us.myles.viaversion.libs.bungeecordchat.chat;

import java.lang.reflect.Type;
import us.myles.viaversion.libs.bungeecordchat.api.chat.KeybindComponent;
import us.myles.viaversion.libs.bungeecordchat.chat.BaseComponentSerializer;
import us.myles.viaversion.libs.gson.JsonDeserializationContext;
import us.myles.viaversion.libs.gson.JsonDeserializer;
import us.myles.viaversion.libs.gson.JsonElement;
import us.myles.viaversion.libs.gson.JsonObject;
import us.myles.viaversion.libs.gson.JsonParseException;
import us.myles.viaversion.libs.gson.JsonSerializationContext;
import us.myles.viaversion.libs.gson.JsonSerializer;

public class KeybindComponentSerializer
extends BaseComponentSerializer
implements JsonSerializer<KeybindComponent>,
JsonDeserializer<KeybindComponent> {
    @Override
    public KeybindComponent deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject object = json.getAsJsonObject();
        if (!object.has("keybind")) {
            throw new JsonParseException("Could not parse JSON: missing 'keybind' property");
        }
        KeybindComponent component = new KeybindComponent();
        this.deserialize(object, component, context);
        component.setKeybind(object.get("keybind").getAsString());
        return component;
    }

    @Override
    public JsonElement serialize(KeybindComponent src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject object = new JsonObject();
        this.serialize(object, src, context);
        object.addProperty("keybind", src.getKeybind());
        return object;
    }
}

