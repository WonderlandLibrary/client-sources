/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package io.jsonwebtoken.gson.io;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.jsonwebtoken.gson.io.GsonSupplierSerializer;
import io.jsonwebtoken.io.AbstractSerializer;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.lang.Assert;
import io.jsonwebtoken.lang.Objects;
import io.jsonwebtoken.lang.Supplier;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;

public class GsonSerializer<T>
extends AbstractSerializer<T> {
    static final Gson DEFAULT_GSON = new GsonBuilder().registerTypeHierarchyAdapter(Supplier.class, GsonSupplierSerializer.INSTANCE).disableHtmlEscaping().create();
    protected final Gson gson;

    public GsonSerializer() {
        this(DEFAULT_GSON);
    }

    public GsonSerializer(Gson gson) {
        Assert.notNull(gson, "gson cannot be null.");
        this.gson = gson;
        String string = this.gson.toJson(TestSupplier.access$000());
        if (string.contains("value")) {
            String string2 = "Invalid Gson instance - it has not been registered with the necessary " + Supplier.class.getName() + " type adapter.  When using the GsonBuilder, ensure this " + "type adapter is registered by calling gsonBuilder.registerTypeHierarchyAdapter(" + Supplier.class.getName() + ".class, " + GsonSupplierSerializer.class.getName() + ".INSTANCE) before calling gsonBuilder.create()";
            throw new IllegalArgumentException(string2);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    protected void doSerialize(T t, OutputStream outputStream) {
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8);
        try {
            Object object = t;
            if (object instanceof byte[]) {
                object = Encoders.BASE64.encode((byte[])object);
            } else if (object instanceof char[]) {
                object = new String((char[])object);
            }
            this.writeValue(object, outputStreamWriter);
        } catch (Throwable throwable) {
            Objects.nullSafeClose(outputStreamWriter);
            throw throwable;
        }
        Objects.nullSafeClose(outputStreamWriter);
    }

    protected void writeValue(Object object, Writer writer) {
        this.gson.toJson(object, (Appendable)writer);
    }

    private static class TestSupplier<T>
    implements Supplier<T> {
        private static final TestSupplier<String> INSTANCE = new TestSupplier<String>("test");
        private final T value;

        private TestSupplier(T t) {
            this.value = t;
        }

        @Override
        public T get() {
            return this.value;
        }

        static TestSupplier access$000() {
            return INSTANCE;
        }
    }
}

