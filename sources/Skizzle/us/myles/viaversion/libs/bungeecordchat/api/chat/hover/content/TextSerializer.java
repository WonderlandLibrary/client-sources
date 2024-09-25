/*
 * Decompiled with CFR 0.150.
 */
package us.myles.viaversion.libs.bungeecordchat.api.chat.hover.content;

import java.lang.reflect.Type;
import us.myles.viaversion.libs.bungeecordchat.api.chat.BaseComponent;
import us.myles.viaversion.libs.bungeecordchat.api.chat.hover.content.Text;
import us.myles.viaversion.libs.gson.JsonDeserializationContext;
import us.myles.viaversion.libs.gson.JsonDeserializer;
import us.myles.viaversion.libs.gson.JsonElement;
import us.myles.viaversion.libs.gson.JsonParseException;
import us.myles.viaversion.libs.gson.JsonSerializationContext;
import us.myles.viaversion.libs.gson.JsonSerializer;

public class TextSerializer
implements JsonSerializer<Text>,
JsonDeserializer<Text> {
    @Override
    public Text deserialize(JsonElement element, Type type, JsonDeserializationContext context) throws JsonParseException {
        if (element.isJsonArray()) {
            return new Text((BaseComponent[])context.deserialize(element, (Type)((Object)BaseComponent[].class)));
        }
        if (element.isJsonPrimitive()) {
            return new Text(element.getAsJsonPrimitive().getAsString());
        }
        return new Text(new BaseComponent[]{(BaseComponent)context.deserialize(element, (Type)((Object)BaseComponent.class))});
    }

    @Override
    public JsonElement serialize(Text content, Type type, JsonSerializationContext context) {
        return context.serialize(content.getValue());
    }
}

