/*
 * Decompiled with CFR 0.150.
 */
package us.myles.viaversion.libs.bungeecordchat.chat;

import java.lang.reflect.Type;
import java.util.Arrays;
import us.myles.viaversion.libs.bungeecordchat.api.chat.BaseComponent;
import us.myles.viaversion.libs.bungeecordchat.api.chat.TranslatableComponent;
import us.myles.viaversion.libs.bungeecordchat.chat.BaseComponentSerializer;
import us.myles.viaversion.libs.gson.JsonDeserializationContext;
import us.myles.viaversion.libs.gson.JsonDeserializer;
import us.myles.viaversion.libs.gson.JsonElement;
import us.myles.viaversion.libs.gson.JsonObject;
import us.myles.viaversion.libs.gson.JsonParseException;
import us.myles.viaversion.libs.gson.JsonSerializationContext;
import us.myles.viaversion.libs.gson.JsonSerializer;

public class TranslatableComponentSerializer
extends BaseComponentSerializer
implements JsonSerializer<TranslatableComponent>,
JsonDeserializer<TranslatableComponent> {
    @Override
    public TranslatableComponent deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        TranslatableComponent component = new TranslatableComponent();
        JsonObject object = json.getAsJsonObject();
        this.deserialize(object, component, context);
        if (!object.has("translate")) {
            throw new JsonParseException("Could not parse JSON: missing 'translate' property");
        }
        component.setTranslate(object.get("translate").getAsString());
        if (object.has("with")) {
            component.setWith(Arrays.asList((Object[])context.deserialize(object.get("with"), (Type)((Object)BaseComponent[].class))));
        }
        return component;
    }

    @Override
    public JsonElement serialize(TranslatableComponent src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject object = new JsonObject();
        this.serialize(object, src, context);
        object.addProperty("translate", src.getTranslate());
        if (src.getWith() != null) {
            object.add("with", context.serialize(src.getWith()));
        }
        return object;
    }
}

