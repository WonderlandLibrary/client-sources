/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.libs.kyori.adventure.text.serializer.json;

import com.viaversion.viaversion.libs.kyori.adventure.text.serializer.json.DummyJSONComponentSerializer;
import com.viaversion.viaversion.libs.kyori.adventure.text.serializer.json.JSONComponentSerializer;
import com.viaversion.viaversion.libs.kyori.adventure.util.Services;
import java.util.Optional;
import java.util.function.Supplier;

final class JSONComponentSerializerAccessor {
    private static final Optional<JSONComponentSerializer.Provider> SERVICE = Services.serviceWithFallback(JSONComponentSerializer.Provider.class);

    private JSONComponentSerializerAccessor() {
    }

    static /* synthetic */ Optional access$000() {
        return SERVICE;
    }

    static final class Instances {
        static final JSONComponentSerializer INSTANCE = JSONComponentSerializerAccessor.access$000().map(JSONComponentSerializer.Provider::instance).orElse(DummyJSONComponentSerializer.INSTANCE);
        static final Supplier<JSONComponentSerializer.Builder> BUILDER_SUPPLIER = JSONComponentSerializerAccessor.access$000().map(JSONComponentSerializer.Provider::builder).orElse(DummyJSONComponentSerializer.BuilderImpl::new);

        Instances() {
        }
    }
}

