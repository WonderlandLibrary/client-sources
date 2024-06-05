package com.google.gson;

public enum LongSerializationPolicy
{
    DEFAULT(0) {
        @Override
        public JsonElement serialize(final Long value) {
            return new JsonPrimitive(value);
        }
    }, 
    STRING(1) {
        @Override
        public JsonElement serialize(final Long value) {
            return new JsonPrimitive(String.valueOf(value));
        }
    };
    
    public abstract JsonElement serialize(final Long p0);
}
