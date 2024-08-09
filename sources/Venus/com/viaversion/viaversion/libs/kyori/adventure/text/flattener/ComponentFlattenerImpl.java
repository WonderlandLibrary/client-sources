/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.libs.kyori.adventure.text.flattener;

import com.viaversion.viaversion.libs.kyori.adventure.text.Component;
import com.viaversion.viaversion.libs.kyori.adventure.text.KeybindComponent;
import com.viaversion.viaversion.libs.kyori.adventure.text.ScoreComponent;
import com.viaversion.viaversion.libs.kyori.adventure.text.SelectorComponent;
import com.viaversion.viaversion.libs.kyori.adventure.text.TextComponent;
import com.viaversion.viaversion.libs.kyori.adventure.text.TranslatableComponent;
import com.viaversion.viaversion.libs.kyori.adventure.text.flattener.ComponentFlattener;
import com.viaversion.viaversion.libs.kyori.adventure.text.flattener.FlattenerListener;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.Style;
import com.viaversion.viaversion.libs.kyori.adventure.util.Buildable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
final class ComponentFlattenerImpl
implements ComponentFlattener {
    static final ComponentFlattener BASIC = (ComponentFlattener)new BuilderImpl().mapper(KeybindComponent.class, ComponentFlattenerImpl::lambda$static$0).mapper(ScoreComponent.class, ScoreComponent::value).mapper(SelectorComponent.class, SelectorComponent::pattern).mapper(TextComponent.class, TextComponent::content).mapper(TranslatableComponent.class, ComponentFlattenerImpl::lambda$static$1).build();
    static final ComponentFlattener TEXT_ONLY = (ComponentFlattener)new BuilderImpl().mapper(TextComponent.class, TextComponent::content).build();
    private static final int MAX_DEPTH = 512;
    private final Map<Class<?>, Function<?, String>> flatteners;
    private final Map<Class<?>, BiConsumer<?, Consumer<Component>>> complexFlatteners;
    private final ConcurrentMap<Class<?>, Handler> propagatedFlatteners = new ConcurrentHashMap();
    private final Function<Component, String> unknownHandler;

    ComponentFlattenerImpl(Map<Class<?>, Function<?, String>> map, Map<Class<?>, BiConsumer<?, Consumer<Component>>> map2, @Nullable Function<Component, String> function) {
        this.flatteners = Collections.unmodifiableMap(new HashMap(map));
        this.complexFlatteners = Collections.unmodifiableMap(new HashMap(map2));
        this.unknownHandler = function;
    }

    @Override
    public void flatten(@NotNull Component component, @NotNull FlattenerListener flattenerListener) {
        this.flatten0(component, flattenerListener, 0);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void flatten0(@NotNull Component component, @NotNull FlattenerListener flattenerListener, int n) {
        Objects.requireNonNull(component, "input");
        Objects.requireNonNull(flattenerListener, "listener");
        if (component == Component.empty()) {
            return;
        }
        if (n > 512) {
            throw new IllegalStateException("Exceeded maximum depth of 512 while attempting to flatten components!");
        }
        @Nullable Handler handler = this.flattener(component);
        Style style = component.style();
        flattenerListener.pushStyle(style);
        try {
            if (handler != null) {
                handler.handle(component, flattenerListener, n + 1);
            }
            if (!component.children().isEmpty()) {
                for (Component component2 : component.children()) {
                    this.flatten0(component2, flattenerListener, n + 1);
                }
            }
        } finally {
            flattenerListener.popStyle(style);
        }
    }

    @Nullable
    private <T extends Component> Handler flattener(T t) {
        Handler handler = this.propagatedFlatteners.computeIfAbsent(t.getClass(), this::lambda$flattener$8);
        if (handler == Handler.NONE) {
            return this.unknownHandler == null ? null : this::lambda$flattener$9;
        }
        return handler;
    }

    @Override
    public @NotNull ComponentFlattener.Builder toBuilder() {
        return new BuilderImpl(this.flatteners, this.complexFlatteners, this.unknownHandler);
    }

    @Override
    public @NotNull Buildable.Builder toBuilder() {
        return this.toBuilder();
    }

    private void lambda$flattener$9(Component component, FlattenerListener flattenerListener, int n) {
        flattenerListener.component(this.unknownHandler.apply(component));
    }

    private Handler lambda$flattener$8(Class clazz) {
        @Nullable Function<?, String> function = this.flatteners.get(clazz);
        if (function != null) {
            return (arg_0, arg_1, arg_2) -> ComponentFlattenerImpl.lambda$flattener$2(function, arg_0, arg_1, arg_2);
        }
        for (Map.Entry<Class<?>, Function<?, String>> entry : this.flatteners.entrySet()) {
            if (!entry.getKey().isAssignableFrom(clazz)) continue;
            return (arg_0, arg_1, arg_2) -> ComponentFlattenerImpl.lambda$flattener$3(entry, arg_0, arg_1, arg_2);
        }
        BiConsumer<?, Consumer<Component>> biConsumer = this.complexFlatteners.get(clazz);
        if (biConsumer != null) {
            return (arg_0, arg_1, arg_2) -> this.lambda$flattener$5(biConsumer, arg_0, arg_1, arg_2);
        }
        for (Map.Entry entry : this.complexFlatteners.entrySet()) {
            if (!((Class)entry.getKey()).isAssignableFrom(clazz)) continue;
            return (arg_0, arg_1, arg_2) -> this.lambda$flattener$7(entry, arg_0, arg_1, arg_2);
        }
        return Handler.NONE;
    }

    private void lambda$flattener$7(Map.Entry entry, Component component, FlattenerListener flattenerListener, int n) {
        ((BiConsumer)entry.getValue()).accept(component, arg_0 -> this.lambda$flattener$6(flattenerListener, n, arg_0));
    }

    private void lambda$flattener$6(FlattenerListener flattenerListener, int n, Component component) {
        this.flatten0(component, flattenerListener, n);
    }

    private void lambda$flattener$5(BiConsumer biConsumer, Component component, FlattenerListener flattenerListener, int n) {
        biConsumer.accept(component, arg_0 -> this.lambda$flattener$4(flattenerListener, n, arg_0));
    }

    private void lambda$flattener$4(FlattenerListener flattenerListener, int n, Component component) {
        this.flatten0(component, flattenerListener, n);
    }

    private static void lambda$flattener$3(Map.Entry entry, Component component, FlattenerListener flattenerListener, int n) {
        flattenerListener.component((String)((Function)entry.getValue()).apply(component));
    }

    private static void lambda$flattener$2(Function function, Component component, FlattenerListener flattenerListener, int n) {
        flattenerListener.component((String)function.apply(component));
    }

    private static String lambda$static$1(TranslatableComponent translatableComponent) {
        @Nullable String string = translatableComponent.fallback();
        return string != null ? string : translatableComponent.key();
    }

    private static String lambda$static$0(KeybindComponent keybindComponent) {
        return keybindComponent.keybind();
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    static final class BuilderImpl
    implements ComponentFlattener.Builder {
        private final Map<Class<?>, Function<?, String>> flatteners;
        private final Map<Class<?>, BiConsumer<?, Consumer<Component>>> complexFlatteners;
        @Nullable
        private Function<Component, String> unknownHandler;

        BuilderImpl() {
            this.flatteners = new HashMap();
            this.complexFlatteners = new HashMap();
        }

        BuilderImpl(Map<Class<?>, Function<?, String>> map, Map<Class<?>, BiConsumer<?, Consumer<Component>>> map2, @Nullable Function<Component, String> function) {
            this.flatteners = new HashMap(map);
            this.complexFlatteners = new HashMap(map2);
            this.unknownHandler = function;
        }

        @Override
        @NotNull
        public ComponentFlattener build() {
            return new ComponentFlattenerImpl(this.flatteners, this.complexFlatteners, this.unknownHandler);
        }

        @Override
        public <T extends Component> @NotNull ComponentFlattener.Builder mapper(@NotNull Class<T> clazz, @NotNull Function<T, String> function) {
            this.validateNoneInHierarchy(Objects.requireNonNull(clazz, "type"));
            this.flatteners.put(clazz, Objects.requireNonNull(function, "converter"));
            this.complexFlatteners.remove(clazz);
            return this;
        }

        @Override
        public <T extends Component> @NotNull ComponentFlattener.Builder complexMapper(@NotNull Class<T> clazz, @NotNull BiConsumer<T, Consumer<Component>> biConsumer) {
            this.validateNoneInHierarchy(Objects.requireNonNull(clazz, "type"));
            this.complexFlatteners.put(clazz, Objects.requireNonNull(biConsumer, "converter"));
            this.flatteners.remove(clazz);
            return this;
        }

        private void validateNoneInHierarchy(Class<? extends Component> clazz) {
            for (Class<?> clazz2 : this.flatteners.keySet()) {
                BuilderImpl.testHierarchy(clazz2, clazz);
            }
            for (Class<?> clazz2 : this.complexFlatteners.keySet()) {
                BuilderImpl.testHierarchy(clazz2, clazz);
            }
        }

        private static void testHierarchy(Class<?> clazz, Class<?> clazz2) {
            if (!clazz.equals(clazz2) && (clazz.isAssignableFrom(clazz2) || clazz2.isAssignableFrom(clazz))) {
                throw new IllegalArgumentException("Conflict detected between already registered type " + clazz + " and newly registered type " + clazz2 + "! Types in a component flattener must not share a common hierarchy!");
            }
        }

        @Override
        public @NotNull ComponentFlattener.Builder unknownMapper(@Nullable Function<Component, String> function) {
            this.unknownHandler = function;
            return this;
        }

        @Override
        @NotNull
        public Object build() {
            return this.build();
        }
    }

    @FunctionalInterface
    static interface Handler {
        public static final Handler NONE = Handler::lambda$static$0;

        public void handle(Component var1, FlattenerListener var2, int var3);

        private static void lambda$static$0(Component component, FlattenerListener flattenerListener, int n) {
        }
    }
}

