package net.minecraft.server.management;

import java.lang.reflect.*;
import com.google.gson.*;

class Serializer implements JsonDeserializer, JsonSerializer
{
    private Serializer() {
    }
    
    Serializer(final UserList this$0, final Object p_i1141_2_) {
        this(this$0);
    }
    
    public JsonElement serializeEntry(final UserListEntry p_152751_1_, final Type p_152751_2_, final JsonSerializationContext p_152751_3_) {
        final JsonObject var4 = new JsonObject();
        p_152751_1_.onSerialization(var4);
        return (JsonElement)var4;
    }
    
    public UserListEntry deserializeEntry(final JsonElement p_152750_1_, final Type p_152750_2_, final JsonDeserializationContext p_152750_3_) {
        if (p_152750_1_.isJsonObject()) {
            final JsonObject var4 = p_152750_1_.getAsJsonObject();
            final UserListEntry var5 = UserList.this.createEntry(var4);
            return var5;
        }
        return null;
    }
    
    public JsonElement serialize(final Object p_serialize_1_, final Type p_serialize_2_, final JsonSerializationContext p_serialize_3_) {
        return this.serializeEntry((UserListEntry)p_serialize_1_, p_serialize_2_, p_serialize_3_);
    }
    
    public Object deserialize(final JsonElement p_deserialize_1_, final Type p_deserialize_2_, final JsonDeserializationContext p_deserialize_3_) {
        return this.deserializeEntry(p_deserialize_1_, p_deserialize_2_, p_deserialize_3_);
    }
}
