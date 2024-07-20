/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Contract
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package com.viaversion.viaversion.libs.kyori.adventure.text;

import com.viaversion.viaversion.libs.kyori.adventure.internal.Internals;
import com.viaversion.viaversion.libs.kyori.adventure.text.Component;
import com.viaversion.viaversion.libs.kyori.adventure.text.ComponentLike;
import com.viaversion.viaversion.libs.kyori.adventure.text.JoinConfiguration;
import com.viaversion.viaversion.libs.kyori.adventure.text.TextComponent;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.Style;
import com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty;
import java.util.Iterator;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

final class JoinConfigurationImpl
implements JoinConfiguration {
    static final Function<ComponentLike, Component> DEFAULT_CONVERTOR = ComponentLike::asComponent;
    static final Predicate<ComponentLike> DEFAULT_PREDICATE = componentLike -> true;
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

    private JoinConfigurationImpl(@NotNull BuilderImpl builder) {
        this.prefix = ComponentLike.unbox(builder.prefix);
        this.suffix = ComponentLike.unbox(builder.suffix);
        this.separator = ComponentLike.unbox(builder.separator);
        this.lastSeparator = ComponentLike.unbox(builder.lastSeparator);
        this.lastSeparatorIfSerial = ComponentLike.unbox(builder.lastSeparatorIfSerial);
        this.convertor = builder.convertor;
        this.predicate = builder.predicate;
        this.rootStyle = builder.rootStyle;
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
        return new BuilderImpl(this);
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
    static Component join(@NotNull JoinConfiguration config, @NotNull Iterable<? extends ComponentLike> components) {
        TextComponent.Builder builder;
        Objects.requireNonNull(config, "config");
        Objects.requireNonNull(components, "components");
        Iterator<? extends ComponentLike> it = components.iterator();
        if (!it.hasNext()) {
            return JoinConfigurationImpl.singleElementJoin(config, null);
        }
        ComponentLike component = Objects.requireNonNull(it.next(), "Null elements in \"components\" are not allowed");
        int componentsSeen = 0;
        if (!it.hasNext()) {
            return JoinConfigurationImpl.singleElementJoin(config, component);
        }
        Component prefix = config.prefix();
        Component suffix = config.suffix();
        Function<ComponentLike, Component> convertor = config.convertor();
        Predicate<ComponentLike> predicate = config.predicate();
        Style rootStyle = config.parentStyle();
        boolean hasRootStyle = rootStyle != Style.empty();
        Component separator = config.separator();
        boolean hasSeparator = separator != null;
        TextComponent.Builder builder2 = builder = hasRootStyle ? (TextComponent.Builder)Component.text().style(rootStyle) : Component.text();
        if (prefix != null) {
            builder.append(prefix);
        }
        while (component != null) {
            if (!predicate.test(component)) {
                if (!it.hasNext()) break;
                component = it.next();
                continue;
            }
            builder.append(Objects.requireNonNull(convertor.apply(component), "Null output from \"convertor\" is not allowed"));
            ++componentsSeen;
            if (!it.hasNext()) {
                component = null;
                continue;
            }
            component = Objects.requireNonNull(it.next(), "Null elements in \"components\" are not allowed");
            if (it.hasNext()) {
                if (!hasSeparator) continue;
                builder.append(separator);
                continue;
            }
            Component lastSeparator = null;
            if (componentsSeen > 1) {
                lastSeparator = config.lastSeparatorIfSerial();
            }
            if (lastSeparator == null) {
                lastSeparator = config.lastSeparator();
            }
            if (lastSeparator == null) {
                lastSeparator = config.separator();
            }
            if (lastSeparator == null) continue;
            builder.append(lastSeparator);
        }
        if (suffix != null) {
            builder.append(suffix);
        }
        return builder.build();
    }

    @NotNull
    static Component singleElementJoin(@NotNull JoinConfiguration config, @Nullable ComponentLike component) {
        boolean hasRootStyle;
        Component prefix = config.prefix();
        Component suffix = config.suffix();
        Function<ComponentLike, Component> convertor = config.convertor();
        Predicate<ComponentLike> predicate = config.predicate();
        Style rootStyle = config.parentStyle();
        boolean bl = hasRootStyle = rootStyle != Style.empty();
        if (prefix == null && suffix == null) {
            Component result = component == null || !predicate.test(component) ? Component.empty() : convertor.apply(component);
            return hasRootStyle ? ((TextComponent.Builder)((TextComponent.Builder)Component.text().style(rootStyle)).append(result)).build() : result;
        }
        TextComponent.Builder builder = Component.text();
        if (prefix != null) {
            builder.append(prefix);
        }
        if (component != null && predicate.test(component)) {
            builder.append(convertor.apply(component));
        }
        if (suffix != null) {
            builder.append(suffix);
        }
        return hasRootStyle ? ((TextComponent.Builder)((TextComponent.Builder)Component.text().style(rootStyle)).append(builder)).build() : builder.build();
    }

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

        private BuilderImpl(@NotNull JoinConfigurationImpl joinConfig) {
            this.separator = joinConfig.separator;
            this.lastSeparator = joinConfig.lastSeparator;
            this.prefix = joinConfig.prefix;
            this.suffix = joinConfig.suffix;
            this.convertor = joinConfig.convertor;
            this.lastSeparatorIfSerial = joinConfig.lastSeparatorIfSerial;
            this.predicate = joinConfig.predicate;
            this.rootStyle = joinConfig.rootStyle;
        }

        @Override
        @NotNull
        public JoinConfiguration.Builder prefix(@Nullable ComponentLike prefix) {
            this.prefix = prefix;
            return this;
        }

        @Override
        @NotNull
        public JoinConfiguration.Builder suffix(@Nullable ComponentLike suffix) {
            this.suffix = suffix;
            return this;
        }

        @Override
        @NotNull
        public JoinConfiguration.Builder separator(@Nullable ComponentLike separator) {
            this.separator = separator;
            return this;
        }

        @Override
        @NotNull
        public JoinConfiguration.Builder lastSeparator(@Nullable ComponentLike lastSeparator) {
            this.lastSeparator = lastSeparator;
            return this;
        }

        @Override
        @NotNull
        public JoinConfiguration.Builder lastSeparatorIfSerial(@Nullable ComponentLike lastSeparatorIfSerial) {
            this.lastSeparatorIfSerial = lastSeparatorIfSerial;
            return this;
        }

        @Override
        @NotNull
        public JoinConfiguration.Builder convertor(@NotNull Function<ComponentLike, Component> convertor) {
            this.convertor = Objects.requireNonNull(convertor, "convertor");
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
        public JoinConfiguration.Builder parentStyle(@NotNull Style parentStyle) {
            this.rootStyle = Objects.requireNonNull(parentStyle, "rootStyle");
            return this;
        }

        @Override
        @NotNull
        public JoinConfiguration build() {
            return new JoinConfigurationImpl(this);
        }
    }
}

