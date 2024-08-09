/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.libs.kyori.adventure.text;

import com.viaversion.viaversion.libs.kyori.adventure.internal.Internals;
import com.viaversion.viaversion.libs.kyori.adventure.text.Component;
import com.viaversion.viaversion.libs.kyori.adventure.text.ComponentLike;
import com.viaversion.viaversion.libs.kyori.adventure.text.JoinConfiguration;
import com.viaversion.viaversion.libs.kyori.adventure.text.TextComponent;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.Style;
import com.viaversion.viaversion.libs.kyori.adventure.util.Buildable;
import com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty;
import java.util.Iterator;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
final class JoinConfigurationImpl
implements JoinConfiguration {
    static final Function<ComponentLike, Component> DEFAULT_CONVERTOR = ComponentLike::asComponent;
    static final Predicate<ComponentLike> DEFAULT_PREDICATE = JoinConfigurationImpl::lambda$static$0;
    static final JoinConfigurationImpl NULL = new JoinConfigurationImpl();
    static final JoinConfiguration STANDARD_NEW_LINES = JoinConfiguration.separator(Component.newline());
    static final JoinConfiguration STANDARD_COMMA_SEPARATED = JoinConfiguration.separator(Component.text(","));
    static final JoinConfiguration STANDARD_COMMA_SPACE_SEPARATED = JoinConfiguration.separator(Component.text(", "));
    static final JoinConfiguration STANDARD_ARRAY_LIKE = (JoinConfiguration)JoinConfiguration.builder().separator(Component.text(", ")).prefix(Component.text("[")).suffix(Component.text("]")).build();
    private final Component prefix;
    private final Component suffix;
    private final Component separator;
    private final Component lastSeparator;
    private final Component lastSeparatorIfSerial;
    private final Function<ComponentLike, Component> convertor;
    private final Predicate<ComponentLike> predicate;
    private final Style rootStyle;

    private JoinConfigurationImpl() {
        this.prefix = null;
        this.suffix = null;
        this.separator = null;
        this.lastSeparator = null;
        this.lastSeparatorIfSerial = null;
        this.convertor = DEFAULT_CONVERTOR;
        this.predicate = DEFAULT_PREDICATE;
        this.rootStyle = Style.empty();
    }

    private JoinConfigurationImpl(@NotNull BuilderImpl builderImpl) {
        this.prefix = ComponentLike.unbox(BuilderImpl.access$000(builderImpl));
        this.suffix = ComponentLike.unbox(BuilderImpl.access$100(builderImpl));
        this.separator = ComponentLike.unbox(BuilderImpl.access$200(builderImpl));
        this.lastSeparator = ComponentLike.unbox(BuilderImpl.access$300(builderImpl));
        this.lastSeparatorIfSerial = ComponentLike.unbox(BuilderImpl.access$400(builderImpl));
        this.convertor = BuilderImpl.access$500(builderImpl);
        this.predicate = BuilderImpl.access$600(builderImpl);
        this.rootStyle = BuilderImpl.access$700(builderImpl);
    }

    @Override
    @Nullable
    public Component prefix() {
        return this.prefix;
    }

    @Override
    @Nullable
    public Component suffix() {
        return this.suffix;
    }

    @Override
    @Nullable
    public Component separator() {
        return this.separator;
    }

    @Override
    @Nullable
    public Component lastSeparator() {
        return this.lastSeparator;
    }

    @Override
    @Nullable
    public Component lastSeparatorIfSerial() {
        return this.lastSeparatorIfSerial;
    }

    @Override
    @NotNull
    public Function<ComponentLike, Component> convertor() {
        return this.convertor;
    }

    @Override
    @NotNull
    public Predicate<ComponentLike> predicate() {
        return this.predicate;
    }

    @Override
    @NotNull
    public Style parentStyle() {
        return this.rootStyle;
    }

    @Override
    public @NotNull JoinConfiguration.Builder toBuilder() {
        return new BuilderImpl(this, null);
    }

    @Override
    @NotNull
    public Stream<? extends ExaminableProperty> examinableProperties() {
        return Stream.of(ExaminableProperty.of("prefix", this.prefix), ExaminableProperty.of("suffix", this.suffix), ExaminableProperty.of("separator", this.separator), ExaminableProperty.of("lastSeparator", this.lastSeparator), ExaminableProperty.of("lastSeparatorIfSerial", this.lastSeparatorIfSerial), ExaminableProperty.of("convertor", this.convertor), ExaminableProperty.of("predicate", this.predicate), ExaminableProperty.of("rootStyle", this.rootStyle));
    }

    public String toString() {
        return Internals.toString(this);
    }

    @Contract(pure=true)
    @NotNull
    static Component join(@NotNull JoinConfiguration joinConfiguration, @NotNull Iterable<? extends ComponentLike> iterable) {
        TextComponent.Builder builder;
        Objects.requireNonNull(joinConfiguration, "config");
        Objects.requireNonNull(iterable, "components");
        Iterator<? extends ComponentLike> iterator2 = iterable.iterator();
        if (!iterator2.hasNext()) {
            return JoinConfigurationImpl.singleElementJoin(joinConfiguration, null);
        }
        ComponentLike componentLike = Objects.requireNonNull(iterator2.next(), "Null elements in \"components\" are not allowed");
        int n = 0;
        if (!iterator2.hasNext()) {
            return JoinConfigurationImpl.singleElementJoin(joinConfiguration, componentLike);
        }
        Component component = joinConfiguration.prefix();
        Component component2 = joinConfiguration.suffix();
        Function<ComponentLike, Component> function = joinConfiguration.convertor();
        Predicate<ComponentLike> predicate = joinConfiguration.predicate();
        Style style = joinConfiguration.parentStyle();
        boolean bl = style != Style.empty();
        Component component3 = joinConfiguration.separator();
        boolean bl2 = component3 != null;
        TextComponent.Builder builder2 = builder = bl ? (TextComponent.Builder)Component.text().style(style) : Component.text();
        if (component != null) {
            builder.append(component);
        }
        while (componentLike != null) {
            if (!predicate.test(componentLike)) {
                if (!iterator2.hasNext()) break;
                componentLike = iterator2.next();
                continue;
            }
            builder.append(Objects.requireNonNull(function.apply(componentLike), "Null output from \"convertor\" is not allowed"));
            ++n;
            if (!iterator2.hasNext()) {
                componentLike = null;
                continue;
            }
            componentLike = Objects.requireNonNull(iterator2.next(), "Null elements in \"components\" are not allowed");
            if (iterator2.hasNext()) {
                if (!bl2) continue;
                builder.append(component3);
                continue;
            }
            Component component4 = null;
            if (n > 1) {
                component4 = joinConfiguration.lastSeparatorIfSerial();
            }
            if (component4 == null) {
                component4 = joinConfiguration.lastSeparator();
            }
            if (component4 == null) {
                component4 = joinConfiguration.separator();
            }
            if (component4 == null) continue;
            builder.append(component4);
        }
        if (component2 != null) {
            builder.append(component2);
        }
        return builder.build();
    }

    @NotNull
    static Component singleElementJoin(@NotNull JoinConfiguration joinConfiguration, @Nullable ComponentLike componentLike) {
        boolean bl;
        Component component = joinConfiguration.prefix();
        Component component2 = joinConfiguration.suffix();
        Function<ComponentLike, Component> function = joinConfiguration.convertor();
        Predicate<ComponentLike> predicate = joinConfiguration.predicate();
        Style style = joinConfiguration.parentStyle();
        boolean bl2 = bl = style != Style.empty();
        if (component == null && component2 == null) {
            Component component3 = componentLike == null || !predicate.test(componentLike) ? Component.empty() : function.apply(componentLike);
            return bl ? ((TextComponent.Builder)((TextComponent.Builder)Component.text().style(style)).append(component3)).build() : component3;
        }
        TextComponent.Builder builder = Component.text();
        if (component != null) {
            builder.append(component);
        }
        if (componentLike != null && predicate.test(componentLike)) {
            builder.append(function.apply(componentLike));
        }
        if (component2 != null) {
            builder.append(component2);
        }
        return bl ? ((TextComponent.Builder)((TextComponent.Builder)Component.text().style(style)).append(builder)).build() : builder.build();
    }

    @Override
    public @NotNull Buildable.Builder toBuilder() {
        return this.toBuilder();
    }

    private static boolean lambda$static$0(ComponentLike componentLike) {
        return false;
    }

    static Component access$900(JoinConfigurationImpl joinConfigurationImpl) {
        return joinConfigurationImpl.separator;
    }

    static Component access$1000(JoinConfigurationImpl joinConfigurationImpl) {
        return joinConfigurationImpl.lastSeparator;
    }

    static Component access$1100(JoinConfigurationImpl joinConfigurationImpl) {
        return joinConfigurationImpl.prefix;
    }

    static Component access$1200(JoinConfigurationImpl joinConfigurationImpl) {
        return joinConfigurationImpl.suffix;
    }

    static Function access$1300(JoinConfigurationImpl joinConfigurationImpl) {
        return joinConfigurationImpl.convertor;
    }

    static Component access$1400(JoinConfigurationImpl joinConfigurationImpl) {
        return joinConfigurationImpl.lastSeparatorIfSerial;
    }

    static Predicate access$1500(JoinConfigurationImpl joinConfigurationImpl) {
        return joinConfigurationImpl.predicate;
    }

    static Style access$1600(JoinConfigurationImpl joinConfigurationImpl) {
        return joinConfigurationImpl.rootStyle;
    }

    JoinConfigurationImpl(BuilderImpl builderImpl, 1 var2_2) {
        this(builderImpl);
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    static final class BuilderImpl
    implements JoinConfiguration.Builder {
        private ComponentLike prefix;
        private ComponentLike suffix;
        private ComponentLike separator;
        private ComponentLike lastSeparator;
        private ComponentLike lastSeparatorIfSerial;
        private Function<ComponentLike, Component> convertor;
        private Predicate<ComponentLike> predicate;
        private Style rootStyle;

        BuilderImpl() {
            this(NULL);
        }

        private BuilderImpl(@NotNull JoinConfigurationImpl joinConfigurationImpl) {
            this.separator = JoinConfigurationImpl.access$900(joinConfigurationImpl);
            this.lastSeparator = JoinConfigurationImpl.access$1000(joinConfigurationImpl);
            this.prefix = JoinConfigurationImpl.access$1100(joinConfigurationImpl);
            this.suffix = JoinConfigurationImpl.access$1200(joinConfigurationImpl);
            this.convertor = JoinConfigurationImpl.access$1300(joinConfigurationImpl);
            this.lastSeparatorIfSerial = JoinConfigurationImpl.access$1400(joinConfigurationImpl);
            this.predicate = JoinConfigurationImpl.access$1500(joinConfigurationImpl);
            this.rootStyle = JoinConfigurationImpl.access$1600(joinConfigurationImpl);
        }

        @Override
        @NotNull
        public JoinConfiguration.Builder prefix(@Nullable ComponentLike componentLike) {
            this.prefix = componentLike;
            return this;
        }

        @Override
        @NotNull
        public JoinConfiguration.Builder suffix(@Nullable ComponentLike componentLike) {
            this.suffix = componentLike;
            return this;
        }

        @Override
        @NotNull
        public JoinConfiguration.Builder separator(@Nullable ComponentLike componentLike) {
            this.separator = componentLike;
            return this;
        }

        @Override
        @NotNull
        public JoinConfiguration.Builder lastSeparator(@Nullable ComponentLike componentLike) {
            this.lastSeparator = componentLike;
            return this;
        }

        @Override
        @NotNull
        public JoinConfiguration.Builder lastSeparatorIfSerial(@Nullable ComponentLike componentLike) {
            this.lastSeparatorIfSerial = componentLike;
            return this;
        }

        @Override
        @NotNull
        public JoinConfiguration.Builder convertor(@NotNull Function<ComponentLike, Component> function) {
            this.convertor = Objects.requireNonNull(function, "convertor");
            return this;
        }

        @Override
        @NotNull
        public JoinConfiguration.Builder predicate(@NotNull Predicate<ComponentLike> predicate) {
            this.predicate = Objects.requireNonNull(predicate, "predicate");
            return this;
        }

        @Override
        @NotNull
        public JoinConfiguration.Builder parentStyle(@NotNull Style style) {
            this.rootStyle = Objects.requireNonNull(style, "rootStyle");
            return this;
        }

        @Override
        @NotNull
        public JoinConfiguration build() {
            return new JoinConfigurationImpl(this, null);
        }

        @Override
        @NotNull
        public Object build() {
            return this.build();
        }

        static ComponentLike access$000(BuilderImpl builderImpl) {
            return builderImpl.prefix;
        }

        static ComponentLike access$100(BuilderImpl builderImpl) {
            return builderImpl.suffix;
        }

        static ComponentLike access$200(BuilderImpl builderImpl) {
            return builderImpl.separator;
        }

        static ComponentLike access$300(BuilderImpl builderImpl) {
            return builderImpl.lastSeparator;
        }

        static ComponentLike access$400(BuilderImpl builderImpl) {
            return builderImpl.lastSeparatorIfSerial;
        }

        static Function access$500(BuilderImpl builderImpl) {
            return builderImpl.convertor;
        }

        static Predicate access$600(BuilderImpl builderImpl) {
            return builderImpl.predicate;
        }

        static Style access$700(BuilderImpl builderImpl) {
            return builderImpl.rootStyle;
        }

        BuilderImpl(JoinConfigurationImpl joinConfigurationImpl, 1 var2_2) {
            this(joinConfigurationImpl);
        }
    }
}

