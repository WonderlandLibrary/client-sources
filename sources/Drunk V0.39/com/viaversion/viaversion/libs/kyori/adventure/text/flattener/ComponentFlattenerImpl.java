/*
 * Decompiled with CFR 0.152.
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
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

final class ComponentFlattenerImpl
implements ComponentFlattener {
    static final ComponentFlattener BASIC = (ComponentFlattener)new BuilderImpl().mapper(KeybindComponent.class, component -> component.keybind()).mapper(ScoreComponent.class, ScoreComponent::value).mapper(SelectorComponent.class, SelectorComponent::pattern).mapper(TextComponent.class, TextComponent::content).mapper(TranslatableComponent.class, TranslatableComponent::key).build();
    static final ComponentFlattener TEXT_ONLY = (ComponentFlattener)new BuilderImpl().mapper(TextComponent.class, TextComponent::content).build();
    private static final int MAX_DEPTH = 512;
    private final Map<Class<?>, Function<?, String>> flatteners;
    private final Map<Class<?>, BiConsumer<?, Consumer<Component>>> complexFlatteners;
    private final ConcurrentMap<Class<?>, Handler> propagatedFlatteners = new ConcurrentHashMap();
    private final Function<Component, String> unknownHandler;

    ComponentFlattenerImpl(Map<Class<?>, Function<?, String>> flatteners, Map<Class<?>, BiConsumer<?, Consumer<Component>>> complexFlatteners, @Nullable Function<Component, String> unknownHandler) {
        this.flatteners = Collections.unmodifiableMap(new HashMap(flatteners));
        this.complexFlatteners = Collections.unmodifiableMap(new HashMap(complexFlatteners));
        this.unknownHandler = unknownHandler;
    }

    @Override
    public void flatten(@NotNull Component input, @NotNull FlattenerListener listener) {
        this.flatten0(input, listener, 0);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void flatten0(@NotNull Component input, @NotNull FlattenerListener listener, int depth) {
        Objects.requireNonNull(input, "input");
        Objects.requireNonNull(listener, "listener");
        if (input == Component.empty()) {
            return;
        }
        if (depth > 512) {
            throw new IllegalStateException("Exceeded maximum depth of 512 while attempting to flatten components!");
        }
        @Nullable Handler flattener = this.flattener(input);
        Style inputStyle = input.style();
        listener.pushStyle(inputStyle);
        try {
            if (flattener != null) {
                flattener.handle(input, listener, depth + 1);
            }
            if (input.children().isEmpty()) return;
            Iterator<Component> iterator = input.children().iterator();
            while (iterator.hasNext()) {
                Component child = iterator.next();
                this.flatten0(child, listener, depth + 1);
            }
            return;
        }
        finally {
            listener.popStyle(inputStyle);
        }
    }

    @Nullable
    private <T extends Component> Handler flattener(T test) {
        Handler flattener = this.propagatedFlatteners.computeIfAbsent(test.getClass(), key -> {
            Map.Entry<Class<?>, BiConsumer<?, Consumer<Component>>> entry;
            @Nullable Function<?, String> value = this.flatteners.get(key);
            if (value != null) {
                return (component, listener, depth) -> listener.component((String)value.apply(component));
            }
            for (Map.Entry<Class<?>, Function<?, String>> entry2 : this.flatteners.entrySet()) {
                if (!entry2.getKey().isAssignableFrom((Class<?>)key)) continue;
                return (component, listener, depth) -> listener.component((String)((Function)entry2.getValue()).apply(component));
            }
            @Nullable BiConsumer<?, Consumer<Component>> complexValue = this.complexFlatteners.get(key);
            if (complexValue != null) {
                return (component, listener, depth) -> complexValue.accept(component, c -> this.flatten0((Component)c, listener, depth));
            }
            Iterator<Map.Entry<Class<?>, BiConsumer<?, Consumer<Component>>>> iterator = this.complexFlatteners.entrySet().iterator();
            do {
                if (!iterator.hasNext()) return Handler.NONE;
            } while (!(entry = iterator.next()).getKey().isAssignableFrom((Class<?>)key));
            return (component, listener, depth) -> ((BiConsumer)entry.getValue()).accept(component, c -> this.flatten0((Component)c, listener, depth));
        });
        if (flattener != Handler.NONE) return flattener;
        if (this.unknownHandler == null) {
            return null;
        }
        Handler handler = (component, listener, depth) -> this.unknownHandler.apply(component);
        return handler;
    }

    @Override
    public @NotNull ComponentFlattener.Builder toBuilder() {
        return new BuilderImpl(this.flatteners, this.complexFlatteners, this.unknownHandler);
    }

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

        BuilderImpl(Map<Class<?>, Function<?, String>> flatteners, Map<Class<?>, BiConsumer<?, Consumer<Component>>> complexFlatteners, @Nullable Function<Component, String> unknownHandler) {
            this.flatteners = new HashMap(flatteners);
            this.complexFlatteners = new HashMap(complexFlatteners);
            this.unknownHandler = unknownHandler;
        }

        @Override
        @NotNull
        public ComponentFlattener build() {
            return new ComponentFlattenerImpl(this.flatteners, this.complexFlatteners, this.unknownHandler);
        }

        @Override
        public <T extends Component> @NotNull ComponentFlattener.Builder mapper(@NotNull Class<T> type, @NotNull Function<T, String> converter) {
            this.validateNoneInHierarchy(Objects.requireNonNull(type, "type"));
            this.flatteners.put(type, Objects.requireNonNull(converter, "converter"));
            this.complexFlatteners.remove(type);
            return this;
        }

        @Override
        public <T extends Component> @NotNull ComponentFlattener.Builder complexMapper(@NotNull Class<T> type, @NotNull BiConsumer<T, Consumer<Component>> converter) {
            this.validateNoneInHierarchy(Objects.requireNonNull(type, "type"));
            this.complexFlatteners.put(type, Objects.requireNonNull(converter, "converter"));
            this.flatteners.remove(type);
            return this;
        }

        private void validateNoneInHierarchy(Class<? extends Component> beingRegistered) {
            for (Class<?> clazz : this.flatteners.keySet()) {
                BuilderImpl.testHierarchy(clazz, beingRegistered);
            }
            Iterator<Class<?>> iterator = this.complexFlatteners.keySet().iterator();
            while (iterator.hasNext()) {
                Class<?> clazz;
                clazz = iterator.next();
                BuilderImpl.testHierarchy(clazz, beingRegistered);
            }
        }

        private static void testHierarchy(Class<?> existing, Class<?> beingRegistered) {
            if (existing.equals(beingRegistered)) return;
            if (existing.isAssignableFrom(beingRegistered)) throw new IllegalArgumentException("Conflict detected between already registered type " + existing + " and newly registered type " + beingRegistered + "! Types in a component flattener must not share a common hierachy!");
            if (!beingRegistered.isAssignableFrom(existing)) return;
            throw new IllegalArgumentException("Conflict detected between already registered type " + existing + " and newly registered type " + beingRegistered + "! Types in a component flattener must not share a common hierachy!");
        }

        @Override
        public @NotNull ComponentFlattener.Builder unknownMapper(@Nullable Function<Component, String> converter) {
            this.unknownHandler = converter;
            return this;
        }
    }

    @FunctionalInterface
    static interface Handler {
        public static final Handler NONE = (input, listener, depth) -> {};

        public void handle(Component var1, FlattenerListener var2, int var3);
    }
}

