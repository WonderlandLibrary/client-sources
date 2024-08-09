/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Range
 */
package com.viaversion.viaversion.libs.kyori.adventure.text.event;

import com.viaversion.viaversion.libs.kyori.adventure.internal.Internals;
import com.viaversion.viaversion.libs.kyori.adventure.key.Key;
import com.viaversion.viaversion.libs.kyori.adventure.key.Keyed;
import com.viaversion.viaversion.libs.kyori.adventure.nbt.api.BinaryTagHolder;
import com.viaversion.viaversion.libs.kyori.adventure.text.Component;
import com.viaversion.viaversion.libs.kyori.adventure.text.ComponentLike;
import com.viaversion.viaversion.libs.kyori.adventure.text.event.HoverEventSource;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.StyleBuilderApplicable;
import com.viaversion.viaversion.libs.kyori.adventure.text.renderer.ComponentRenderer;
import com.viaversion.viaversion.libs.kyori.adventure.util.Index;
import com.viaversion.viaversion.libs.kyori.examination.Examinable;
import com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty;
import java.util.Objects;
import java.util.UUID;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Range;

public final class HoverEvent<V>
implements Examinable,
HoverEventSource<V>,
StyleBuilderApplicable {
    private final Action<V> action;
    private final V value;

    @NotNull
    public static HoverEvent<Component> showText(@NotNull ComponentLike componentLike) {
        return HoverEvent.showText(componentLike.asComponent());
    }

    @NotNull
    public static HoverEvent<Component> showText(@NotNull Component component) {
        return new HoverEvent<Component>(Action.SHOW_TEXT, component);
    }

    @NotNull
    public static HoverEvent<ShowItem> showItem(@NotNull Key key, @Range(from=0L, to=0x7FFFFFFFL) int n) {
        return HoverEvent.showItem(key, n, null);
    }

    @NotNull
    public static HoverEvent<ShowItem> showItem(@NotNull Keyed keyed, @Range(from=0L, to=0x7FFFFFFFL) int n) {
        return HoverEvent.showItem(keyed, n, null);
    }

    @NotNull
    public static HoverEvent<ShowItem> showItem(@NotNull Key key, @Range(from=0L, to=0x7FFFFFFFL) int n, @Nullable BinaryTagHolder binaryTagHolder) {
        return HoverEvent.showItem(ShowItem.of(key, n, binaryTagHolder));
    }

    @NotNull
    public static HoverEvent<ShowItem> showItem(@NotNull Keyed keyed, @Range(from=0L, to=0x7FFFFFFFL) int n, @Nullable BinaryTagHolder binaryTagHolder) {
        return HoverEvent.showItem(ShowItem.of(keyed, n, binaryTagHolder));
    }

    @NotNull
    public static HoverEvent<ShowItem> showItem(@NotNull ShowItem showItem) {
        return new HoverEvent<ShowItem>(Action.SHOW_ITEM, showItem);
    }

    @NotNull
    public static HoverEvent<ShowEntity> showEntity(@NotNull Key key, @NotNull UUID uUID) {
        return HoverEvent.showEntity(key, uUID, null);
    }

    @NotNull
    public static HoverEvent<ShowEntity> showEntity(@NotNull Keyed keyed, @NotNull UUID uUID) {
        return HoverEvent.showEntity(keyed, uUID, null);
    }

    @NotNull
    public static HoverEvent<ShowEntity> showEntity(@NotNull Key key, @NotNull UUID uUID, @Nullable Component component) {
        return HoverEvent.showEntity(ShowEntity.of(key, uUID, component));
    }

    @NotNull
    public static HoverEvent<ShowEntity> showEntity(@NotNull Keyed keyed, @NotNull UUID uUID, @Nullable Component component) {
        return HoverEvent.showEntity(ShowEntity.of(keyed, uUID, component));
    }

    @NotNull
    public static HoverEvent<ShowEntity> showEntity(@NotNull ShowEntity showEntity) {
        return new HoverEvent<ShowEntity>(Action.SHOW_ENTITY, showEntity);
    }

    @NotNull
    public static <V> HoverEvent<V> hoverEvent(@NotNull Action<V> action, @NotNull V v) {
        return new HoverEvent<V>(action, v);
    }

    private HoverEvent(@NotNull Action<V> action, @NotNull V v) {
        this.action = Objects.requireNonNull(action, "action");
        this.value = Objects.requireNonNull(v, "value");
    }

    @NotNull
    public Action<V> action() {
        return this.action;
    }

    @NotNull
    public V value() {
        return this.value;
    }

    @NotNull
    public HoverEvent<V> value(@NotNull V v) {
        return new HoverEvent<V>(this.action, v);
    }

    @NotNull
    public <C> HoverEvent<V> withRenderedValue(@NotNull ComponentRenderer<C> componentRenderer, @NotNull C c) {
        V v = this.value;
        V v2 = Action.access$000(this.action).render(componentRenderer, c, v);
        if (v2 != v) {
            return new HoverEvent<V>(this.action, v2);
        }
        return this;
    }

    @Override
    @NotNull
    public HoverEvent<V> asHoverEvent() {
        return this;
    }

    @Override
    @NotNull
    public HoverEvent<V> asHoverEvent(@NotNull UnaryOperator<V> unaryOperator) {
        if (unaryOperator == UnaryOperator.identity()) {
            return this;
        }
        return new HoverEvent<V>(this.action, unaryOperator.apply(this.value));
    }

    @Override
    public void styleApply( @NotNull Style.Builder builder) {
        builder.hoverEvent((HoverEventSource)this);
    }

    public boolean equals(@Nullable Object object) {
        if (this == object) {
            return false;
        }
        if (object == null || this.getClass() != object.getClass()) {
            return true;
        }
        HoverEvent hoverEvent = (HoverEvent)object;
        return this.action == hoverEvent.action && this.value.equals(hoverEvent.value);
    }

    public int hashCode() {
        int n = this.action.hashCode();
        n = 31 * n + this.value.hashCode();
        return n;
    }

    @Override
    @NotNull
    public Stream<? extends ExaminableProperty> examinableProperties() {
        return Stream.of(ExaminableProperty.of("action", this.action), ExaminableProperty.of("value", this.value));
    }

    public String toString() {
        return Internals.toString(this);
    }

    public static final class Action<V> {
        public static final Action<Component> SHOW_TEXT = new Action<Component>("show_text", Component.class, true, new Renderer<Component>(){

            @Override
            @NotNull
            public <C> Component render(@NotNull ComponentRenderer<C> componentRenderer, @NotNull C c, @NotNull Component component) {
                return componentRenderer.render(component, c);
            }

            @Override
            @NotNull
            public Object render(@NotNull ComponentRenderer componentRenderer, @NotNull Object object, @NotNull Object object2) {
                return this.render(componentRenderer, object, (Component)object2);
            }
        });
        public static final Action<ShowItem> SHOW_ITEM = new Action<ShowItem>("show_item", ShowItem.class, true, new Renderer<ShowItem>(){

            @Override
            @NotNull
            public <C> ShowItem render(@NotNull ComponentRenderer<C> componentRenderer, @NotNull C c, @NotNull ShowItem showItem) {
                return showItem;
            }

            @Override
            @NotNull
            public Object render(@NotNull ComponentRenderer componentRenderer, @NotNull Object object, @NotNull Object object2) {
                return this.render(componentRenderer, object, (ShowItem)object2);
            }
        });
        public static final Action<ShowEntity> SHOW_ENTITY = new Action<ShowEntity>("show_entity", ShowEntity.class, true, new Renderer<ShowEntity>(){

            @Override
            @NotNull
            public <C> ShowEntity render(@NotNull ComponentRenderer<C> componentRenderer, @NotNull C c, @NotNull ShowEntity showEntity) {
                if (ShowEntity.access$100(showEntity) == null) {
                    return showEntity;
                }
                return showEntity.name(componentRenderer.render(ShowEntity.access$100(showEntity), c));
            }

            @Override
            @NotNull
            public Object render(@NotNull ComponentRenderer componentRenderer, @NotNull Object object, @NotNull Object object2) {
                return this.render(componentRenderer, object, (ShowEntity)object2);
            }
        });
        public static final Index<String, Action<?>> NAMES = Index.create(Action::lambda$static$0, SHOW_TEXT, SHOW_ITEM, SHOW_ENTITY);
        private final String name;
        private final Class<V> type;
        private final boolean readable;
        private final Renderer<V> renderer;

        Action(String string, Class<V> clazz, boolean bl, Renderer<V> renderer) {
            this.name = string;
            this.type = clazz;
            this.readable = bl;
            this.renderer = renderer;
        }

        @NotNull
        public Class<V> type() {
            return this.type;
        }

        public boolean readable() {
            return this.readable;
        }

        @NotNull
        public String toString() {
            return this.name;
        }

        private static String lambda$static$0(Action action) {
            return action.name;
        }

        static Renderer access$000(Action action) {
            return action.renderer;
        }

        @FunctionalInterface
        static interface Renderer<V> {
            @NotNull
            public <C> V render(@NotNull ComponentRenderer<C> var1, @NotNull C var2, @NotNull V var3);
        }
    }

    public static final class ShowEntity
    implements Examinable {
        private final Key type;
        private final UUID id;
        private final Component name;

        @NotNull
        public static ShowEntity of(@NotNull Key key, @NotNull UUID uUID) {
            return ShowEntity.of(key, uUID, null);
        }

        @NotNull
        public static ShowEntity of(@NotNull Keyed keyed, @NotNull UUID uUID) {
            return ShowEntity.of(keyed, uUID, null);
        }

        @NotNull
        public static ShowEntity of(@NotNull Key key, @NotNull UUID uUID, @Nullable Component component) {
            return new ShowEntity(Objects.requireNonNull(key, "type"), Objects.requireNonNull(uUID, "id"), component);
        }

        @NotNull
        public static ShowEntity of(@NotNull Keyed keyed, @NotNull UUID uUID, @Nullable Component component) {
            return new ShowEntity(Objects.requireNonNull(keyed, "type").key(), Objects.requireNonNull(uUID, "id"), component);
        }

        private ShowEntity(@NotNull Key key, @NotNull UUID uUID, @Nullable Component component) {
            this.type = key;
            this.id = uUID;
            this.name = component;
        }

        @NotNull
        public Key type() {
            return this.type;
        }

        @NotNull
        public ShowEntity type(@NotNull Key key) {
            if (Objects.requireNonNull(key, "type").equals(this.type)) {
                return this;
            }
            return new ShowEntity(key, this.id, this.name);
        }

        @NotNull
        public ShowEntity type(@NotNull Keyed keyed) {
            return this.type(Objects.requireNonNull(keyed, "type").key());
        }

        @NotNull
        public UUID id() {
            return this.id;
        }

        @NotNull
        public ShowEntity id(@NotNull UUID uUID) {
            if (Objects.requireNonNull(uUID).equals(this.id)) {
                return this;
            }
            return new ShowEntity(this.type, uUID, this.name);
        }

        @Nullable
        public Component name() {
            return this.name;
        }

        @NotNull
        public ShowEntity name(@Nullable Component component) {
            if (Objects.equals(component, this.name)) {
                return this;
            }
            return new ShowEntity(this.type, this.id, component);
        }

        public boolean equals(@Nullable Object object) {
            if (this == object) {
                return false;
            }
            if (object == null || this.getClass() != object.getClass()) {
                return true;
            }
            ShowEntity showEntity = (ShowEntity)object;
            return this.type.equals(showEntity.type) && this.id.equals(showEntity.id) && Objects.equals(this.name, showEntity.name);
        }

        public int hashCode() {
            int n = this.type.hashCode();
            n = 31 * n + this.id.hashCode();
            n = 31 * n + Objects.hashCode(this.name);
            return n;
        }

        @Override
        @NotNull
        public Stream<? extends ExaminableProperty> examinableProperties() {
            return Stream.of(ExaminableProperty.of("type", this.type), ExaminableProperty.of("id", this.id), ExaminableProperty.of("name", this.name));
        }

        public String toString() {
            return Internals.toString(this);
        }

        static Component access$100(ShowEntity showEntity) {
            return showEntity.name;
        }
    }

    public static final class ShowItem
    implements Examinable {
        private final Key item;
        private final int count;
        @Nullable
        private final BinaryTagHolder nbt;

        @NotNull
        public static ShowItem of(@NotNull Key key, @Range(from=0L, to=0x7FFFFFFFL) int n) {
            return ShowItem.of(key, n, null);
        }

        @NotNull
        public static ShowItem of(@NotNull Keyed keyed, @Range(from=0L, to=0x7FFFFFFFL) int n) {
            return ShowItem.of(keyed, n, null);
        }

        @NotNull
        public static ShowItem of(@NotNull Key key, @Range(from=0L, to=0x7FFFFFFFL) int n, @Nullable BinaryTagHolder binaryTagHolder) {
            return new ShowItem(Objects.requireNonNull(key, "item"), n, binaryTagHolder);
        }

        @NotNull
        public static ShowItem of(@NotNull Keyed keyed, @Range(from=0L, to=0x7FFFFFFFL) int n, @Nullable BinaryTagHolder binaryTagHolder) {
            return new ShowItem(Objects.requireNonNull(keyed, "item").key(), n, binaryTagHolder);
        }

        private ShowItem(@NotNull Key key, @Range(from=0L, to=0x7FFFFFFFL) int n, @Nullable BinaryTagHolder binaryTagHolder) {
            this.item = key;
            this.count = n;
            this.nbt = binaryTagHolder;
        }

        @NotNull
        public Key item() {
            return this.item;
        }

        @NotNull
        public ShowItem item(@NotNull Key key) {
            if (Objects.requireNonNull(key, "item").equals(this.item)) {
                return this;
            }
            return new ShowItem(key, this.count, this.nbt);
        }

        public @Range(from=0L, to=0x7FFFFFFFL) int count() {
            return this.count;
        }

        @NotNull
        public ShowItem count(@Range(from=0L, to=0x7FFFFFFFL) int n) {
            if (n == this.count) {
                return this;
            }
            return new ShowItem(this.item, n, this.nbt);
        }

        @Nullable
        public BinaryTagHolder nbt() {
            return this.nbt;
        }

        @NotNull
        public ShowItem nbt(@Nullable BinaryTagHolder binaryTagHolder) {
            if (Objects.equals(binaryTagHolder, this.nbt)) {
                return this;
            }
            return new ShowItem(this.item, this.count, binaryTagHolder);
        }

        public boolean equals(@Nullable Object object) {
            if (this == object) {
                return false;
            }
            if (object == null || this.getClass() != object.getClass()) {
                return true;
            }
            ShowItem showItem = (ShowItem)object;
            return this.item.equals(showItem.item) && this.count == showItem.count && Objects.equals(this.nbt, showItem.nbt);
        }

        public int hashCode() {
            int n = this.item.hashCode();
            n = 31 * n + Integer.hashCode(this.count);
            n = 31 * n + Objects.hashCode(this.nbt);
            return n;
        }

        @Override
        @NotNull
        public Stream<? extends ExaminableProperty> examinableProperties() {
            return Stream.of(ExaminableProperty.of("item", this.item), ExaminableProperty.of("count", this.count), ExaminableProperty.of("nbt", this.nbt));
        }

        public String toString() {
            return Internals.toString(this);
        }
    }
}

