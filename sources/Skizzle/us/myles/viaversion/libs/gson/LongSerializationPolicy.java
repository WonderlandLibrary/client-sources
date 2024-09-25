/*
 * Decompiled with CFR 0.150.
 */
package us.myles.viaversion.libs.gson;

import us.myles.viaversion.libs.gson.JsonElement;
import us.myles.viaversion.libs.gson.JsonPrimitive;

public enum LongSerializationPolicy {
    DEFAULT{

        @Override
        public JsonElement serialize(Long value) {
            return new JsonPrimitive(value);
        }
    }
    ,
    STRING{

        @Override
        public JsonElement serialize(Long value) {
            return new JsonPrimitive(String.valueOf(value));
        }
    };


    public abstract JsonElement serialize(Long var1);
}

