/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.gson.io;

import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import io.jsonwebtoken.lang.Supplier;
import java.lang.reflect.Type;

public final class GsonSupplierSerializer
implements JsonSerializer<Supplier<?>> {
    public static final GsonSupplierSerializer INSTANCE = new GsonSupplierSerializer();

    @Override
    public JsonElement serialize(Supplier<?> supplier, Type type, JsonSerializationContext jsonSerializationContext) {
        Object obj = supplier.get();
        return jsonSerializationContext.serialize(obj);
    }

    @Override
    public JsonElement serialize(Object object, Type type, JsonSerializationContext jsonSerializationContext) {
        return this.serialize((Supplier)object, type, jsonSerializationContext);
    }
}

