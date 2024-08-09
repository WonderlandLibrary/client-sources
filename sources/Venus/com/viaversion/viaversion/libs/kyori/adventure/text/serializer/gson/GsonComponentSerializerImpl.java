/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.libs.kyori.adventure.text.serializer.gson;

import com.viaversion.viaversion.libs.gson.Gson;
import com.viaversion.viaversion.libs.gson.GsonBuilder;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.kyori.adventure.text.Component;
import com.viaversion.viaversion.libs.kyori.adventure.text.serializer.gson.ComponentSerializerImpl;
import com.viaversion.viaversion.libs.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import com.viaversion.viaversion.libs.kyori.adventure.text.serializer.gson.LegacyHoverEventSerializer;
import com.viaversion.viaversion.libs.kyori.adventure.text.serializer.gson.SerializerFactory;
import com.viaversion.viaversion.libs.kyori.adventure.util.Buildable;
import com.viaversion.viaversion.libs.kyori.adventure.util.Services;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
final class GsonComponentSerializerImpl
implements GsonComponentSerializer {
    private static final Optional<GsonComponentSerializer.Provider> SERVICE = Services.service(GsonComponentSerializer.Provider.class);
    static final Consumer<GsonComponentSerializer.Builder> BUILDER = SERVICE.map(GsonComponentSerializer.Provider::builder).orElseGet(GsonComponentSerializerImpl::lambda$static$1);
    private final Gson serializer;
    private final UnaryOperator<GsonBuilder> populator;
    private final boolean downsampleColor;
    @Nullable
    private final LegacyHoverEventSerializer legacyHoverSerializer;
    private final boolean emitLegacyHover;

    GsonComponentSerializerImpl(boolean bl, @Nullable LegacyHoverEventSerializer legacyHoverEventSerializer, boolean bl2) {
        this.downsampleColor = bl;
        this.legacyHoverSerializer = legacyHoverEventSerializer;
        this.emitLegacyHover = bl2;
        this.populator = arg_0 -> GsonComponentSerializerImpl.lambda$new$2(bl, legacyHoverEventSerializer, bl2, arg_0);
        this.serializer = ((GsonBuilder)this.populator.apply(new GsonBuilder().disableHtmlEscaping())).create();
    }

    @Override
    @NotNull
    public Gson serializer() {
        return this.serializer;
    }

    @Override
    @NotNull
    public UnaryOperator<GsonBuilder> populator() {
        return this.populator;
    }

    @Override
    @NotNull
    public Component deserialize(@NotNull String string) {
        Component component = this.serializer().fromJson(string, Component.class);
        if (component == null) {
            throw ComponentSerializerImpl.notSureHowToDeserialize(string);
        }
        return component;
    }

    @Override
    @Nullable
    public Component deserializeOr(@Nullable String string, @Nullable Component component) {
        if (string == null) {
            return component;
        }
        Component component2 = this.serializer().fromJson(string, Component.class);
        if (component2 == null) {
            return component;
        }
        return component2;
    }

    @Override
    @NotNull
    public String serialize(@NotNull Component component) {
        return this.serializer().toJson(component);
    }

    @Override
    @NotNull
    public Component deserializeFromTree(@NotNull JsonElement jsonElement) {
        Component component = this.serializer().fromJson(jsonElement, Component.class);
        if (component == null) {
            throw ComponentSerializerImpl.notSureHowToDeserialize(jsonElement);
        }
        return component;
    }

    @Override
    @NotNull
    public JsonElement serializeToTree(@NotNull Component component) {
        return this.serializer().toJsonTree(component);
    }

    @Override
    @NotNull
    public GsonComponentSerializer.Builder toBuilder() {
        return new BuilderImpl(this);
    }

    @Override
    @NotNull
    public Object serialize(@NotNull Component component) {
        return this.serialize(component);
    }

    @Override
    @Nullable
    public Component deserializeOr(@Nullable Object object, @Nullable Component component) {
        return this.deserializeOr((String)object, component);
    }

    @Override
    @NotNull
    public Component deserialize(@NotNull Object object) {
        return this.deserialize((String)object);
    }

    @Override
    @NotNull
    public Buildable.Builder toBuilder() {
        return this.toBuilder();
    }

    private static GsonBuilder lambda$new$2(boolean bl, LegacyHoverEventSerializer legacyHoverEventSerializer, boolean bl2, GsonBuilder gsonBuilder) {
        gsonBuilder.registerTypeAdapterFactory(new SerializerFactory(bl, legacyHoverEventSerializer, bl2));
        return gsonBuilder;
    }

    private static Consumer lambda$static$1() {
        return GsonComponentSerializerImpl::lambda$static$0;
    }

    private static void lambda$static$0(GsonComponentSerializer.Builder builder) {
    }

    static Optional access$000() {
        return SERVICE;
    }

    static boolean access$100(GsonComponentSerializerImpl gsonComponentSerializerImpl) {
        return gsonComponentSerializerImpl.downsampleColor;
    }

    static boolean access$200(GsonComponentSerializerImpl gsonComponentSerializerImpl) {
        return gsonComponentSerializerImpl.emitLegacyHover;
    }

    static LegacyHoverEventSerializer access$300(GsonComponentSerializerImpl gsonComponentSerializerImpl) {
        return gsonComponentSerializerImpl.legacyHoverSerializer;
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    static final class BuilderImpl
    implements GsonComponentSerializer.Builder {
        private boolean downsampleColor = false;
        @Nullable
        private LegacyHoverEventSerializer legacyHoverSerializer;
        private boolean emitLegacyHover = false;

        BuilderImpl() {
            BUILDER.accept(this);
        }

        BuilderImpl(GsonComponentSerializerImpl gsonComponentSerializerImpl) {
            this();
            this.downsampleColor = GsonComponentSerializerImpl.access$100(gsonComponentSerializerImpl);
            this.emitLegacyHover = GsonComponentSerializerImpl.access$200(gsonComponentSerializerImpl);
            this.legacyHoverSerializer = GsonComponentSerializerImpl.access$300(gsonComponentSerializerImpl);
        }

        @Override
        @NotNull
        public GsonComponentSerializer.Builder downsampleColors() {
            this.downsampleColor = true;
            return this;
        }

        @Override
        @NotNull
        public GsonComponentSerializer.Builder legacyHoverEventSerializer(@Nullable LegacyHoverEventSerializer legacyHoverEventSerializer) {
            this.legacyHoverSerializer = legacyHoverEventSerializer;
            return this;
        }

        @Override
        @NotNull
        public GsonComponentSerializer.Builder emitLegacyHoverEvent() {
            this.emitLegacyHover = true;
            return this;
        }

        @Override
        @NotNull
        public GsonComponentSerializer build() {
            if (this.legacyHoverSerializer == null) {
                return this.downsampleColor ? Instances.LEGACY_INSTANCE : Instances.INSTANCE;
            }
            return new GsonComponentSerializerImpl(this.downsampleColor, this.legacyHoverSerializer, this.emitLegacyHover);
        }

        @Override
        @NotNull
        public Object build() {
            return this.build();
        }
    }

    static final class Instances {
        static final GsonComponentSerializer INSTANCE = GsonComponentSerializerImpl.access$000().map(GsonComponentSerializer.Provider::gson).orElseGet(Instances::lambda$static$0);
        static final GsonComponentSerializer LEGACY_INSTANCE = GsonComponentSerializerImpl.access$000().map(GsonComponentSerializer.Provider::gsonLegacy).orElseGet(Instances::lambda$static$1);

        Instances() {
        }

        private static GsonComponentSerializer lambda$static$1() {
            return new GsonComponentSerializerImpl(true, null, true);
        }

        private static GsonComponentSerializer lambda$static$0() {
            return new GsonComponentSerializerImpl(false, null, false);
        }
    }
}

