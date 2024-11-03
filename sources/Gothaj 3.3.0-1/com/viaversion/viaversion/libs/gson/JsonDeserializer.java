package com.viaversion.viaversion.libs.gson;

import java.lang.reflect.Type;

public interface JsonDeserializer<T> {
   T deserialize(JsonElement var1, Type var2, JsonDeserializationContext var3) throws JsonParseException;
}
