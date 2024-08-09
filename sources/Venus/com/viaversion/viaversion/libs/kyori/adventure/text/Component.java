/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.ApiStatus$NonExtendable
 *  org.jetbrains.annotations.ApiStatus$ScheduledForRemoval
 *  org.jetbrains.annotations.Unmodifiable
 */
package com.viaversion.viaversion.libs.kyori.adventure.text;

import com.viaversion.viaversion.libs.kyori.adventure.builder.AbstractBuilder;
import com.viaversion.viaversion.libs.kyori.adventure.key.Key;
import com.viaversion.viaversion.libs.kyori.adventure.text.BlockNBTComponent;
import com.viaversion.viaversion.libs.kyori.adventure.text.BlockNBTComponentImpl;
import com.viaversion.viaversion.libs.kyori.adventure.text.ComponentBuilder;
import com.viaversion.viaversion.libs.kyori.adventure.text.ComponentBuilderApplicable;
import com.viaversion.viaversion.libs.kyori.adventure.text.ComponentCompaction;
import com.viaversion.viaversion.libs.kyori.adventure.text.ComponentIterator;
import com.viaversion.viaversion.libs.kyori.adventure.text.ComponentIteratorFlag;
import com.viaversion.viaversion.libs.kyori.adventure.text.ComponentIteratorType;
import com.viaversion.viaversion.libs.kyori.adventure.text.ComponentLike;
import com.viaversion.viaversion.libs.kyori.adventure.text.EntityNBTComponent;
import com.viaversion.viaversion.libs.kyori.adventure.text.EntityNBTComponentImpl;
import com.viaversion.viaversion.libs.kyori.adventure.text.JoinConfiguration;
import com.viaversion.viaversion.libs.kyori.adventure.text.JoinConfigurationImpl;
import com.viaversion.viaversion.libs.kyori.adventure.text.KeybindComponent;
import com.viaversion.viaversion.libs.kyori.adventure.text.KeybindComponentImpl;
import com.viaversion.viaversion.libs.kyori.adventure.text.PatternReplacementResult;
import com.viaversion.viaversion.libs.kyori.adventure.text.ScoreComponent;
import com.viaversion.viaversion.libs.kyori.adventure.text.ScoreComponentImpl;
import com.viaversion.viaversion.libs.kyori.adventure.text.SelectorComponent;
import com.viaversion.viaversion.libs.kyori.adventure.text.SelectorComponentImpl;
import com.viaversion.viaversion.libs.kyori.adventure.text.StorageNBTComponent;
import com.viaversion.viaversion.libs.kyori.adventure.text.StorageNBTComponentImpl;
import com.viaversion.viaversion.libs.kyori.adventure.text.TextComponent;
import com.viaversion.viaversion.libs.kyori.adventure.text.TextComponentImpl;
import com.viaversion.viaversion.libs.kyori.adventure.text.TextReplacementConfig;
import com.viaversion.viaversion.libs.kyori.adventure.text.TextReplacementConfigImpl;
import com.viaversion.viaversion.libs.kyori.adventure.text.TextReplacementRenderer;
import com.viaversion.viaversion.libs.kyori.adventure.text.TranslatableComponent;
import com.viaversion.viaversion.libs.kyori.adventure.text.TranslatableComponentImpl;
import com.viaversion.viaversion.libs.kyori.adventure.text.event.ClickEvent;
import com.viaversion.viaversion.libs.kyori.adventure.text.event.HoverEvent;
import com.viaversion.viaversion.libs.kyori.adventure.text.event.HoverEventSource;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.Style;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.StyleBuilderApplicable;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.StyleGetter;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.StyleSetter;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.TextColor;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.TextDecoration;
import com.viaversion.viaversion.libs.kyori.adventure.translation.Translatable;
import com.viaversion.viaversion.libs.kyori.adventure.util.ForwardingIterator;
import com.viaversion.viaversion.libs.kyori.adventure.util.IntFunction2;
import com.viaversion.viaversion.libs.kyori.adventure.util.MonkeyBars;
import com.viaversion.viaversion.libs.kyori.examination.Examinable;
import com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;
import java.util.stream.Collector;
import java.util.stream.Stream;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
@ApiStatus.NonExtendable
public interface Component
extends ComponentBuilderApplicable,
ComponentLike,
Examinable,
HoverEventSource<Component>,
StyleGetter,
StyleSetter<Component> {
    public static final BiPredicate<? super Component, ? super Component> EQUALS = Objects::equals;
    public static final BiPredicate<? super Component, ? super Component> EQUALS_IDENTITY = Component::lambda$static$0;
    public static final Predicate<? super Component> IS_NOT_EMPTY = Component::lambda$static$1;

    @NotNull
    public static TextComponent empty() {
        return TextComponentImpl.EMPTY;
    }

    @NotNull
    public static TextComponent newline() {
        return TextComponentImpl.NEWLINE;
    }

    @NotNull
    public static TextComponent space() {
        return TextComponentImpl.SPACE;
    }

    @Deprecated
    @ApiStatus.ScheduledForRemoval(inVersion="5.0.0")
    @Contract(value="_, _ -> new", pure=true)
    @NotNull
    public static TextComponent join(@NotNull ComponentLike componentLike, @NotNull @NotNull ComponentLike @NotNull ... componentLikeArray) {
        return Component.join(componentLike, Arrays.asList(componentLikeArray));
    }

    @Deprecated
    @ApiStatus.ScheduledForRemoval(inVersion="5.0.0")
    @Contract(value="_, _ -> new", pure=true)
    @NotNull
    public static TextComponent join(@NotNull ComponentLike componentLike, Iterable<? extends ComponentLike> iterable) {
        Component component = Component.join(JoinConfiguration.separator(componentLike), iterable);
        if (component instanceof TextComponent) {
            return (TextComponent)component;
        }
        return (TextComponent)((TextComponent.Builder)Component.text().append(component)).build();
    }

    @Contract(pure=true)
    @NotNull
    public static Component join(@NotNull JoinConfiguration joinConfiguration, @NotNull @NotNull ComponentLike @NotNull ... componentLikeArray) {
        return Component.join(joinConfiguration, Arrays.asList(componentLikeArray));
    }

    @Contract(pure=true)
    @NotNull
    public static Component join(@NotNull JoinConfiguration joinConfiguration, @NotNull Iterable<? extends ComponentLike> iterable) {
        return JoinConfigurationImpl.join(joinConfiguration, iterable);
    }

    @NotNull
    public static Collector<Component, ? extends ComponentBuilder<?, ?>, Component> toComponent() {
        return Component.toComponent(Component.empty());
    }

    @NotNull
    public static Collector<Component, ? extends ComponentBuilder<?, ?>, Component> toComponent(@NotNull Component component) {
        return Collector.of(Component::text, (arg_0, arg_1) -> Component.lambda$toComponent$2(component, arg_0, arg_1), (arg_0, arg_1) -> Component.lambda$toComponent$3(component, arg_0, arg_1), ComponentBuilder::build, new Collector.Characteristics[0]);
    }

    @Contract(pure=true)
    public static @NotNull BlockNBTComponent.Builder blockNBT() {
        return new BlockNBTComponentImpl.BuilderImpl();
    }

    @Contract(value="_ -> new")
    @NotNull
    public static BlockNBTComponent blockNBT(@NotNull Consumer<? super BlockNBTComponent.Builder> consumer) {
        return (BlockNBTComponent)AbstractBuilder.configureAndBuild(Component.blockNBT(), consumer);
    }

    @Contract(value="_, _ -> new", pure=true)
    @NotNull
    public static BlockNBTComponent blockNBT(@NotNull String string, @NotNull BlockNBTComponent.Pos pos) {
        return Component.blockNBT(string, false, pos);
    }

    @Contract(value="_, _, _ -> new", pure=true)
    @NotNull
    public static BlockNBTComponent blockNBT(@NotNull String string, boolean bl, @NotNull BlockNBTComponent.Pos pos) {
        return Component.blockNBT(string, bl, null, pos);
    }

    @Contract(value="_, _, _, _ -> new", pure=true)
    @NotNull
    public static BlockNBTComponent blockNBT(@NotNull String string, boolean bl, @Nullable ComponentLike componentLike, @NotNull BlockNBTComponent.Pos pos) {
        return BlockNBTComponentImpl.create(Collections.emptyList(), Style.empty(), string, bl, componentLike, pos);
    }

    @Contract(pure=true)
    public static @NotNull EntityNBTComponent.Builder entityNBT() {
        return new EntityNBTComponentImpl.BuilderImpl();
    }

    @Contract(value="_ -> new")
    @NotNull
    public static EntityNBTComponent entityNBT(@NotNull Consumer<? super EntityNBTComponent.Builder> consumer) {
        return (EntityNBTComponent)AbstractBuilder.configureAndBuild(Component.entityNBT(), consumer);
    }

    @Contract(value="_, _ -> new")
    @NotNull
    public static EntityNBTComponent entityNBT(@NotNull String string, @NotNull String string2) {
        return (EntityNBTComponent)((EntityNBTComponent.Builder)Component.entityNBT().nbtPath(string)).selector(string2).build();
    }

    @Contract(pure=true)
    public static @NotNull KeybindComponent.Builder keybind() {
        return new KeybindComponentImpl.BuilderImpl();
    }

    @Contract(value="_ -> new")
    @NotNull
    public static KeybindComponent keybind(@NotNull Consumer<? super KeybindComponent.Builder> consumer) {
        return (KeybindComponent)AbstractBuilder.configureAndBuild(Component.keybind(), consumer);
    }

    @Contract(value="_ -> new", pure=true)
    @NotNull
    public static KeybindComponent keybind(@NotNull String string) {
        return Component.keybind(string, Style.empty());
    }

    @Contract(value="_ -> new", pure=true)
    @NotNull
    public static KeybindComponent keybind(@NotNull KeybindComponent.KeybindLike keybindLike) {
        return Component.keybind(Objects.requireNonNull(keybindLike, "keybind").asKeybind(), Style.empty());
    }

    @Contract(value="_, _ -> new", pure=true)
    @NotNull
    public static KeybindComponent keybind(@NotNull String string, @NotNull Style style) {
        return KeybindComponentImpl.create(Collections.emptyList(), Objects.requireNonNull(style, "style"), string);
    }

    @Contract(value="_, _ -> new", pure=true)
    @NotNull
    public static KeybindComponent keybind(@NotNull KeybindComponent.KeybindLike keybindLike, @NotNull Style style) {
        return KeybindComponentImpl.create(Collections.emptyList(), Objects.requireNonNull(style, "style"), Objects.requireNonNull(keybindLike, "keybind").asKeybind());
    }

    @Contract(value="_, _ -> new", pure=true)
    @NotNull
    public static KeybindComponent keybind(@NotNull String string, @Nullable TextColor textColor) {
        return Component.keybind(string, Style.style(textColor));
    }

    @Contract(value="_, _ -> new", pure=true)
    @NotNull
    public static KeybindComponent keybind(@NotNull KeybindComponent.KeybindLike keybindLike, @Nullable TextColor textColor) {
        return Component.keybind(Objects.requireNonNull(keybindLike, "keybind").asKeybind(), Style.style(textColor));
    }

    @Contract(value="_, _, _ -> new", pure=true)
    @NotNull
    public static KeybindComponent keybind(@NotNull String string, @Nullable TextColor textColor, TextDecoration @NotNull ... textDecorationArray) {
        return Component.keybind(string, Style.style(textColor, textDecorationArray));
    }

    @Contract(value="_, _, _ -> new", pure=true)
    @NotNull
    public static KeybindComponent keybind(@NotNull KeybindComponent.KeybindLike keybindLike, @Nullable TextColor textColor, TextDecoration @NotNull ... textDecorationArray) {
        return Component.keybind(Objects.requireNonNull(keybindLike, "keybind").asKeybind(), Style.style(textColor, textDecorationArray));
    }

    @Contract(value="_, _, _ -> new", pure=true)
    @NotNull
    public static KeybindComponent keybind(@NotNull String string, @Nullable TextColor textColor, @NotNull Set<TextDecoration> set) {
        return Component.keybind(string, Style.style(textColor, set));
    }

    @Contract(value="_, _, _ -> new", pure=true)
    @NotNull
    public static KeybindComponent keybind(@NotNull KeybindComponent.KeybindLike keybindLike, @Nullable TextColor textColor, @NotNull Set<TextDecoration> set) {
        return Component.keybind(Objects.requireNonNull(keybindLike, "keybind").asKeybind(), Style.style(textColor, set));
    }

    @Contract(pure=true)
    public static @NotNull ScoreComponent.Builder score() {
        return new ScoreComponentImpl.BuilderImpl();
    }

    @Contract(value="_ -> new")
    @NotNull
    public static ScoreComponent score(@NotNull Consumer<? super ScoreComponent.Builder> consumer) {
        return (ScoreComponent)AbstractBuilder.configureAndBuild(Component.score(), consumer);
    }

    @Contract(value="_, _ -> new", pure=true)
    @NotNull
    public static ScoreComponent score(@NotNull String string, @NotNull String string2) {
        return Component.score(string, string2, null);
    }

    @Deprecated
    @Contract(value="_, _, _ -> new", pure=true)
    @NotNull
    public static ScoreComponent score(@NotNull String string, @NotNull String string2, @Nullable String string3) {
        return ScoreComponentImpl.create(Collections.emptyList(), Style.empty(), string, string2, string3);
    }

    @Contract(pure=true)
    public static @NotNull SelectorComponent.Builder selector() {
        return new SelectorComponentImpl.BuilderImpl();
    }

    @Contract(value="_ -> new")
    @NotNull
    public static SelectorComponent selector(@NotNull Consumer<? super SelectorComponent.Builder> consumer) {
        return (SelectorComponent)AbstractBuilder.configureAndBuild(Component.selector(), consumer);
    }

    @Contract(value="_ -> new", pure=true)
    @NotNull
    public static SelectorComponent selector(@NotNull String string) {
        return Component.selector(string, null);
    }

    @Contract(value="_, _ -> new", pure=true)
    @NotNull
    public static SelectorComponent selector(@NotNull String string, @Nullable ComponentLike componentLike) {
        return SelectorComponentImpl.create(Collections.emptyList(), Style.empty(), string, componentLike);
    }

    @Contract(pure=true)
    public static @NotNull StorageNBTComponent.Builder storageNBT() {
        return new StorageNBTComponentImpl.BuilderImpl();
    }

    @Contract(value="_ -> new")
    @NotNull
    public static StorageNBTComponent storageNBT(@NotNull Consumer<? super StorageNBTComponent.Builder> consumer) {
        return (StorageNBTComponent)AbstractBuilder.configureAndBuild(Component.storageNBT(), consumer);
    }

    @Contract(value="_, _ -> new", pure=true)
    @NotNull
    public static StorageNBTComponent storageNBT(@NotNull String string, @NotNull Key key) {
        return Component.storageNBT(string, false, key);
    }

    @Contract(value="_, _, _ -> new", pure=true)
    @NotNull
    public static StorageNBTComponent storageNBT(@NotNull String string, boolean bl, @NotNull Key key) {
        return Component.storageNBT(string, bl, null, key);
    }

    @Contract(value="_, _, _, _ -> new", pure=true)
    @NotNull
    public static StorageNBTComponent storageNBT(@NotNull String string, boolean bl, @Nullable ComponentLike componentLike, @NotNull Key key) {
        return StorageNBTComponentImpl.create(Collections.emptyList(), Style.empty(), string, bl, componentLike, key);
    }

    @Contract(pure=true)
    public static @NotNull TextComponent.Builder text() {
        return new TextComponentImpl.BuilderImpl();
    }

    @NotNull
    public static TextComponent textOfChildren(@NotNull @NotNull ComponentLike @NotNull ... componentLikeArray) {
        if (componentLikeArray.length == 0) {
            return Component.empty();
        }
        return TextComponentImpl.create(Arrays.asList(componentLikeArray), Style.empty(), "");
    }

    @Contract(value="_ -> new")
    @NotNull
    public static TextComponent text(@NotNull Consumer<? super TextComponent.Builder> consumer) {
        return (TextComponent)AbstractBuilder.configureAndBuild(Component.text(), consumer);
    }

    @Contract(value="_ -> new", pure=true)
    @NotNull
    public static TextComponent text(@NotNull String string) {
        if (string.isEmpty()) {
            return Component.empty();
        }
        return Component.text(string, Style.empty());
    }

    @Contract(value="_, _ -> new", pure=true)
    @NotNull
    public static TextComponent text(@NotNull String string, @NotNull Style style) {
        return TextComponentImpl.create(Collections.emptyList(), Objects.requireNonNull(style, "style"), string);
    }

    @Contract(value="_, _ -> new", pure=true)
    @NotNull
    public static TextComponent text(@NotNull String string, @Nullable TextColor textColor) {
        return Component.text(string, Style.style(textColor));
    }

    @Contract(value="_, _, _ -> new", pure=true)
    @NotNull
    public static TextComponent text(@NotNull String string, @Nullable TextColor textColor, TextDecoration @NotNull ... textDecorationArray) {
        return Component.text(string, Style.style(textColor, textDecorationArray));
    }

    @Contract(value="_, _, _ -> new", pure=true)
    @NotNull
    public static TextComponent text(@NotNull String string, @Nullable TextColor textColor, @NotNull Set<TextDecoration> set) {
        return Component.text(string, Style.style(textColor, set));
    }

    @Contract(value="_ -> new", pure=true)
    @NotNull
    public static TextComponent text(boolean bl) {
        return Component.text(String.valueOf(bl));
    }

    @Contract(value="_, _ -> new", pure=true)
    @NotNull
    public static TextComponent text(boolean bl, @NotNull Style style) {
        return Component.text(String.valueOf(bl), style);
    }

    @Contract(value="_, _ -> new", pure=true)
    @NotNull
    public static TextComponent text(boolean bl, @Nullable TextColor textColor) {
        return Component.text(String.valueOf(bl), textColor);
    }

    @Contract(value="_, _, _ -> new", pure=true)
    @NotNull
    public static TextComponent text(boolean bl, @Nullable TextColor textColor, TextDecoration @NotNull ... textDecorationArray) {
        return Component.text(String.valueOf(bl), textColor, textDecorationArray);
    }

    @Contract(value="_, _, _ -> new", pure=true)
    @NotNull
    public static TextComponent text(boolean bl, @Nullable TextColor textColor, @NotNull Set<TextDecoration> set) {
        return Component.text(String.valueOf(bl), textColor, set);
    }

    @Contract(pure=true)
    @NotNull
    public static TextComponent text(char c) {
        if (c == '\n') {
            return Component.newline();
        }
        if (c == ' ') {
            return Component.space();
        }
        return Component.text(String.valueOf(c));
    }

    @Contract(value="_, _ -> new", pure=true)
    @NotNull
    public static TextComponent text(char c, @NotNull Style style) {
        return Component.text(String.valueOf(c), style);
    }

    @Contract(value="_, _ -> new", pure=true)
    @NotNull
    public static TextComponent text(char c, @Nullable TextColor textColor) {
        return Component.text(String.valueOf(c), textColor);
    }

    @Contract(value="_, _, _ -> new", pure=true)
    @NotNull
    public static TextComponent text(char c, @Nullable TextColor textColor, TextDecoration @NotNull ... textDecorationArray) {
        return Component.text(String.valueOf(c), textColor, textDecorationArray);
    }

    @Contract(value="_, _, _ -> new", pure=true)
    @NotNull
    public static TextComponent text(char c, @Nullable TextColor textColor, @NotNull Set<TextDecoration> set) {
        return Component.text(String.valueOf(c), textColor, set);
    }

    @Contract(value="_ -> new", pure=true)
    @NotNull
    public static TextComponent text(double d) {
        return Component.text(String.valueOf(d));
    }

    @Contract(value="_, _ -> new", pure=true)
    @NotNull
    public static TextComponent text(double d, @NotNull Style style) {
        return Component.text(String.valueOf(d), style);
    }

    @Contract(value="_, _ -> new", pure=true)
    @NotNull
    public static TextComponent text(double d, @Nullable TextColor textColor) {
        return Component.text(String.valueOf(d), textColor);
    }

    @Contract(value="_, _, _ -> new", pure=true)
    @NotNull
    public static TextComponent text(double d, @Nullable TextColor textColor, TextDecoration @NotNull ... textDecorationArray) {
        return Component.text(String.valueOf(d), textColor, textDecorationArray);
    }

    @Contract(value="_, _, _ -> new", pure=true)
    @NotNull
    public static TextComponent text(double d, @Nullable TextColor textColor, @NotNull Set<TextDecoration> set) {
        return Component.text(String.valueOf(d), textColor, set);
    }

    @Contract(value="_ -> new", pure=true)
    @NotNull
    public static TextComponent text(float f) {
        return Component.text(String.valueOf(f));
    }

    @Contract(value="_, _ -> new", pure=true)
    @NotNull
    public static TextComponent text(float f, @NotNull Style style) {
        return Component.text(String.valueOf(f), style);
    }

    @Contract(value="_, _ -> new", pure=true)
    @NotNull
    public static TextComponent text(float f, @Nullable TextColor textColor) {
        return Component.text(String.valueOf(f), textColor);
    }

    @Contract(value="_, _, _ -> new", pure=true)
    @NotNull
    public static TextComponent text(float f, @Nullable TextColor textColor, TextDecoration @NotNull ... textDecorationArray) {
        return Component.text(String.valueOf(f), textColor, textDecorationArray);
    }

    @Contract(value="_, _, _ -> new", pure=true)
    @NotNull
    public static TextComponent text(float f, @Nullable TextColor textColor, @NotNull Set<TextDecoration> set) {
        return Component.text(String.valueOf(f), textColor, set);
    }

    @Contract(value="_ -> new", pure=true)
    @NotNull
    public static TextComponent text(int n) {
        return Component.text(String.valueOf(n));
    }

    @Contract(value="_, _ -> new", pure=true)
    @NotNull
    public static TextComponent text(int n, @NotNull Style style) {
        return Component.text(String.valueOf(n), style);
    }

    @Contract(value="_, _ -> new", pure=true)
    @NotNull
    public static TextComponent text(int n, @Nullable TextColor textColor) {
        return Component.text(String.valueOf(n), textColor);
    }

    @Contract(value="_, _, _ -> new", pure=true)
    @NotNull
    public static TextComponent text(int n, @Nullable TextColor textColor, TextDecoration @NotNull ... textDecorationArray) {
        return Component.text(String.valueOf(n), textColor, textDecorationArray);
    }

    @Contract(value="_, _, _ -> new", pure=true)
    @NotNull
    public static TextComponent text(int n, @Nullable TextColor textColor, @NotNull Set<TextDecoration> set) {
        return Component.text(String.valueOf(n), textColor, set);
    }

    @Contract(value="_ -> new", pure=true)
    @NotNull
    public static TextComponent text(long l) {
        return Component.text(String.valueOf(l));
    }

    @Contract(value="_, _ -> new", pure=true)
    @NotNull
    public static TextComponent text(long l, @NotNull Style style) {
        return Component.text(String.valueOf(l), style);
    }

    @Contract(value="_, _ -> new", pure=true)
    @NotNull
    public static TextComponent text(long l, @Nullable TextColor textColor) {
        return Component.text(String.valueOf(l), textColor);
    }

    @Contract(value="_, _, _ -> new", pure=true)
    @NotNull
    public static TextComponent text(long l, @Nullable TextColor textColor, TextDecoration @NotNull ... textDecorationArray) {
        return Component.text(String.valueOf(l), textColor, textDecorationArray);
    }

    @Contract(value="_, _, _ -> new", pure=true)
    @NotNull
    public static TextComponent text(long l, @Nullable TextColor textColor, @NotNull Set<TextDecoration> set) {
        return Component.text(String.valueOf(l), textColor, set);
    }

    @Contract(pure=true)
    public static @NotNull TranslatableComponent.Builder translatable() {
        return new TranslatableComponentImpl.BuilderImpl();
    }

    @Contract(value="_ -> new")
    @NotNull
    public static TranslatableComponent translatable(@NotNull Consumer<? super TranslatableComponent.Builder> consumer) {
        return (TranslatableComponent)AbstractBuilder.configureAndBuild(Component.translatable(), consumer);
    }

    @Contract(value="_ -> new", pure=true)
    @NotNull
    public static TranslatableComponent translatable(@NotNull String string) {
        return Component.translatable(string, Style.empty());
    }

    @Contract(value="_ -> new", pure=true)
    @NotNull
    public static TranslatableComponent translatable(@NotNull Translatable translatable) {
        return Component.translatable(Objects.requireNonNull(translatable, "translatable").translationKey(), Style.empty());
    }

    @Contract(value="_, _ -> new", pure=true)
    @NotNull
    public static TranslatableComponent translatable(@NotNull String string, @Nullable String string2) {
        return Component.translatable(string, string2, Style.empty());
    }

    @Contract(value="_, _ -> new", pure=true)
    @NotNull
    public static TranslatableComponent translatable(@NotNull Translatable translatable, @Nullable String string) {
        return Component.translatable(Objects.requireNonNull(translatable, "translatable").translationKey(), string, Style.empty());
    }

    @Contract(value="_, _ -> new", pure=true)
    @NotNull
    public static TranslatableComponent translatable(@NotNull String string, @NotNull Style style) {
        return TranslatableComponentImpl.create(Collections.emptyList(), Objects.requireNonNull(style, "style"), string, null, Collections.emptyList());
    }

    @Contract(value="_, _ -> new", pure=true)
    @NotNull
    public static TranslatableComponent translatable(@NotNull Translatable translatable, @NotNull Style style) {
        return Component.translatable(Objects.requireNonNull(translatable, "translatable").translationKey(), style);
    }

    @Contract(value="_, _, _ -> new", pure=true)
    @NotNull
    public static TranslatableComponent translatable(@NotNull String string, @Nullable String string2, @NotNull Style style) {
        return TranslatableComponentImpl.create(Collections.emptyList(), Objects.requireNonNull(style, "style"), string, string2, Collections.emptyList());
    }

    @Contract(value="_, _, _ -> new", pure=true)
    @NotNull
    public static TranslatableComponent translatable(@NotNull Translatable translatable, @Nullable String string, @NotNull Style style) {
        return Component.translatable(Objects.requireNonNull(translatable, "translatable").translationKey(), string, style);
    }

    @Contract(value="_, _, _ -> new", pure=true)
    @NotNull
    public static TranslatableComponent translatable(@NotNull String string, @Nullable String string2, @NotNull StyleBuilderApplicable ... styleBuilderApplicableArray) {
        return Component.translatable(Objects.requireNonNull(string, "key"), string2, Style.style(styleBuilderApplicableArray));
    }

    @Contract(value="_, _, _ -> new", pure=true)
    @NotNull
    public static TranslatableComponent translatable(@NotNull Translatable translatable, @Nullable String string, @NotNull Iterable<StyleBuilderApplicable> iterable) {
        return Component.translatable(Objects.requireNonNull(translatable, "translatable").translationKey(), string, Style.style(iterable));
    }

    @Contract(value="_, _, _ -> new", pure=true)
    @NotNull
    public static TranslatableComponent translatable(@NotNull String string, @Nullable String string2, @NotNull @NotNull ComponentLike @NotNull ... componentLikeArray) {
        return Component.translatable(string, string2, Style.empty(), componentLikeArray);
    }

    @Contract(value="_, _, _ -> new", pure=true)
    @NotNull
    public static TranslatableComponent translatable(@NotNull Translatable translatable, @Nullable String string, @NotNull @NotNull ComponentLike @NotNull ... componentLikeArray) {
        return Component.translatable(Objects.requireNonNull(translatable, "translatable").translationKey(), string, componentLikeArray);
    }

    @Contract(value="_, _, _, _ -> new", pure=true)
    @NotNull
    public static TranslatableComponent translatable(@NotNull String string, @Nullable String string2, @NotNull Style style, @NotNull @NotNull ComponentLike @NotNull ... componentLikeArray) {
        return TranslatableComponentImpl.create(Collections.emptyList(), Objects.requireNonNull(style, "style"), string, string2, Objects.requireNonNull(componentLikeArray, "args"));
    }

    @Contract(value="_, _, _, _ -> new", pure=true)
    @NotNull
    public static TranslatableComponent translatable(@NotNull Translatable translatable, @Nullable String string, @NotNull Style style, @NotNull @NotNull ComponentLike @NotNull ... componentLikeArray) {
        return Component.translatable(Objects.requireNonNull(translatable, "translatable").translationKey(), string, style, componentLikeArray);
    }

    @Contract(value="_, _, _, _ -> new", pure=true)
    @NotNull
    public static TranslatableComponent translatable(@NotNull String string, @Nullable String string2, @NotNull Style style, @NotNull List<? extends ComponentLike> list) {
        return TranslatableComponentImpl.create(Collections.emptyList(), style, string, string2, Objects.requireNonNull(list, "args"));
    }

    @Contract(value="_, _, _, _ -> new", pure=true)
    @NotNull
    public static TranslatableComponent translatable(@NotNull Translatable translatable, @Nullable String string, @NotNull Style style, @NotNull List<? extends ComponentLike> list) {
        return Component.translatable(Objects.requireNonNull(translatable, "translatable").translationKey(), string, style, list);
    }

    @Contract(value="_, _, _, _ -> new", pure=true)
    @NotNull
    public static TranslatableComponent translatable(@NotNull String string, @Nullable String string2, @NotNull List<? extends ComponentLike> list, @NotNull Iterable<StyleBuilderApplicable> iterable) {
        return TranslatableComponentImpl.create(Collections.emptyList(), Style.style(iterable), string, string2, Objects.requireNonNull(list, "args"));
    }

    @Contract(value="_, _, _, _ -> new", pure=true)
    @NotNull
    public static TranslatableComponent translatable(@NotNull Translatable translatable, @Nullable String string, @NotNull List<? extends ComponentLike> list, @NotNull Iterable<StyleBuilderApplicable> iterable) {
        return Component.translatable(Objects.requireNonNull(translatable, "translatable").translationKey(), string, list, iterable);
    }

    @Contract(value="_, _, _, _ -> new", pure=true)
    @NotNull
    public static TranslatableComponent translatable(@NotNull String string, @Nullable String string2, @NotNull List<? extends ComponentLike> list, @NotNull StyleBuilderApplicable ... styleBuilderApplicableArray) {
        return TranslatableComponentImpl.create(Collections.emptyList(), Style.style(styleBuilderApplicableArray), string, string2, Objects.requireNonNull(list, "args"));
    }

    @Contract(value="_, _, _, _ -> new", pure=true)
    @NotNull
    public static TranslatableComponent translatable(@NotNull Translatable translatable, @Nullable String string, @NotNull List<? extends ComponentLike> list, @NotNull StyleBuilderApplicable ... styleBuilderApplicableArray) {
        return Component.translatable(Objects.requireNonNull(translatable, "translatable").translationKey(), string, list, styleBuilderApplicableArray);
    }

    @Contract(value="_, _ -> new", pure=true)
    @NotNull
    public static TranslatableComponent translatable(@NotNull String string, @Nullable TextColor textColor) {
        return Component.translatable(string, Style.style(textColor));
    }

    @Contract(value="_, _ -> new", pure=true)
    @NotNull
    public static TranslatableComponent translatable(@NotNull Translatable translatable, @Nullable TextColor textColor) {
        return Component.translatable(Objects.requireNonNull(translatable, "translatable").translationKey(), textColor);
    }

    @Contract(value="_, _, _ -> new", pure=true)
    @NotNull
    public static TranslatableComponent translatable(@NotNull String string, @Nullable TextColor textColor, TextDecoration @NotNull ... textDecorationArray) {
        return Component.translatable(string, Style.style(textColor, textDecorationArray));
    }

    @Contract(value="_, _, _ -> new", pure=true)
    @NotNull
    public static TranslatableComponent translatable(@NotNull Translatable translatable, @Nullable TextColor textColor, TextDecoration @NotNull ... textDecorationArray) {
        return Component.translatable(Objects.requireNonNull(translatable, "translatable").translationKey(), textColor, textDecorationArray);
    }

    @Contract(value="_, _, _ -> new", pure=true)
    @NotNull
    public static TranslatableComponent translatable(@NotNull String string, @Nullable TextColor textColor, @NotNull Set<TextDecoration> set) {
        return Component.translatable(string, Style.style(textColor, set));
    }

    @Contract(value="_, _, _ -> new", pure=true)
    @NotNull
    public static TranslatableComponent translatable(@NotNull Translatable translatable, @Nullable TextColor textColor, @NotNull Set<TextDecoration> set) {
        return Component.translatable(Objects.requireNonNull(translatable, "translatable").translationKey(), textColor, set);
    }

    @Contract(value="_, _ -> new", pure=true)
    @NotNull
    public static TranslatableComponent translatable(@NotNull String string, @NotNull @NotNull ComponentLike @NotNull ... componentLikeArray) {
        return Component.translatable(string, Style.empty(), componentLikeArray);
    }

    @Contract(value="_, _ -> new", pure=true)
    @NotNull
    public static TranslatableComponent translatable(@NotNull Translatable translatable, @NotNull @NotNull ComponentLike @NotNull ... componentLikeArray) {
        return Component.translatable(Objects.requireNonNull(translatable, "translatable").translationKey(), componentLikeArray);
    }

    @Contract(value="_, _, _ -> new", pure=true)
    @NotNull
    public static TranslatableComponent translatable(@NotNull String string, @NotNull Style style, @NotNull @NotNull ComponentLike @NotNull ... componentLikeArray) {
        return TranslatableComponentImpl.create(Collections.emptyList(), Objects.requireNonNull(style, "style"), string, null, Objects.requireNonNull(componentLikeArray, "args"));
    }

    @Contract(value="_, _, _ -> new", pure=true)
    @NotNull
    public static TranslatableComponent translatable(@NotNull Translatable translatable, @NotNull Style style, @NotNull @NotNull ComponentLike @NotNull ... componentLikeArray) {
        return Component.translatable(Objects.requireNonNull(translatable, "translatable").translationKey(), style, componentLikeArray);
    }

    @Contract(value="_, _, _ -> new", pure=true)
    @NotNull
    public static TranslatableComponent translatable(@NotNull String string, @Nullable TextColor textColor, @NotNull @NotNull ComponentLike @NotNull ... componentLikeArray) {
        return Component.translatable(string, Style.style(textColor), componentLikeArray);
    }

    @Contract(value="_, _, _ -> new", pure=true)
    @NotNull
    public static TranslatableComponent translatable(@NotNull Translatable translatable, @Nullable TextColor textColor, @NotNull @NotNull ComponentLike @NotNull ... componentLikeArray) {
        return Component.translatable(Objects.requireNonNull(translatable, "translatable").translationKey(), textColor, componentLikeArray);
    }

    @Contract(value="_, _, _, _ -> new", pure=true)
    @NotNull
    public static TranslatableComponent translatable(@NotNull String string, @Nullable TextColor textColor, @NotNull Set<TextDecoration> set, @NotNull @NotNull ComponentLike @NotNull ... componentLikeArray) {
        return Component.translatable(string, Style.style(textColor, set), componentLikeArray);
    }

    @Contract(value="_, _, _, _ -> new", pure=true)
    @NotNull
    public static TranslatableComponent translatable(@NotNull Translatable translatable, @Nullable TextColor textColor, @NotNull Set<TextDecoration> set, @NotNull @NotNull ComponentLike @NotNull ... componentLikeArray) {
        return Component.translatable(Objects.requireNonNull(translatable, "translatable").translationKey(), textColor, set, componentLikeArray);
    }

    @Contract(value="_, _ -> new", pure=true)
    @NotNull
    public static TranslatableComponent translatable(@NotNull String string, @NotNull List<? extends ComponentLike> list) {
        return TranslatableComponentImpl.create(Collections.emptyList(), Style.empty(), string, null, Objects.requireNonNull(list, "args"));
    }

    @Contract(value="_, _ -> new", pure=true)
    @NotNull
    public static TranslatableComponent translatable(@NotNull Translatable translatable, @NotNull List<? extends ComponentLike> list) {
        return Component.translatable(Objects.requireNonNull(translatable, "translatable").translationKey(), list);
    }

    @Contract(value="_, _, _ -> new", pure=true)
    @NotNull
    public static TranslatableComponent translatable(@NotNull String string, @NotNull Style style, @NotNull List<? extends ComponentLike> list) {
        return TranslatableComponentImpl.create(Collections.emptyList(), Objects.requireNonNull(style, "style"), string, null, Objects.requireNonNull(list, "args"));
    }

    @Contract(value="_, _, _ -> new", pure=true)
    @NotNull
    public static TranslatableComponent translatable(@NotNull Translatable translatable, @NotNull Style style, @NotNull List<? extends ComponentLike> list) {
        return Component.translatable(Objects.requireNonNull(translatable, "translatable").translationKey(), style, list);
    }

    @Contract(value="_, _, _ -> new", pure=true)
    public static TranslatableComponent translatable(@NotNull String string, @Nullable TextColor textColor, @NotNull List<? extends ComponentLike> list) {
        return Component.translatable(string, Style.style(textColor), list);
    }

    @Contract(value="_, _, _ -> new", pure=true)
    public static TranslatableComponent translatable(@NotNull Translatable translatable, @Nullable TextColor textColor, @NotNull List<? extends ComponentLike> list) {
        return Component.translatable(Objects.requireNonNull(translatable, "translatable").translationKey(), textColor, list);
    }

    @Contract(value="_, _, _, _ -> new", pure=true)
    @NotNull
    public static TranslatableComponent translatable(@NotNull String string, @Nullable TextColor textColor, @NotNull Set<TextDecoration> set, @NotNull List<? extends ComponentLike> list) {
        return Component.translatable(string, Style.style(textColor, set), list);
    }

    @Contract(value="_, _, _, _ -> new", pure=true)
    @NotNull
    public static TranslatableComponent translatable(@NotNull Translatable translatable, @Nullable TextColor textColor, @NotNull Set<TextDecoration> set, @NotNull List<? extends ComponentLike> list) {
        return Component.translatable(Objects.requireNonNull(translatable, "translatable").translationKey(), textColor, set, list);
    }

    public @Unmodifiable @NotNull List<Component> children();

    @Contract(pure=true)
    @NotNull
    public Component children(@NotNull List<? extends ComponentLike> var1);

    default public boolean contains(@NotNull Component component) {
        return this.contains(component, EQUALS_IDENTITY);
    }

    default public boolean contains(@NotNull Component component, @NotNull BiPredicate<? super Component, ? super Component> biPredicate) {
        if (biPredicate.test(this, component)) {
            return false;
        }
        for (Component component2 : this.children()) {
            if (!component2.contains(component, biPredicate)) continue;
            return false;
        }
        HoverEvent<?> hoverEvent = this.hoverEvent();
        if (hoverEvent != null) {
            Component component2;
            component2 = hoverEvent.value();
            Component component3 = null;
            if (component2 instanceof Component) {
                component3 = (Component)hoverEvent.value();
            } else if (component2 instanceof HoverEvent.ShowEntity) {
                component3 = ((HoverEvent.ShowEntity)((Object)component2)).name();
            }
            if (component3 != null) {
                if (biPredicate.test(component, component3)) {
                    return false;
                }
                for (Component component4 : component3.children()) {
                    if (!component4.contains(component, biPredicate)) continue;
                    return false;
                }
            }
        }
        return true;
    }

    @Deprecated
    @ApiStatus.ScheduledForRemoval(inVersion="5.0.0")
    default public void detectCycle(@NotNull Component component) {
        if (component.contains(this)) {
            throw new IllegalStateException("Component cycle detected between " + this + " and " + component);
        }
    }

    @Contract(pure=true)
    @NotNull
    default public Component append(@NotNull Component component) {
        return this.append((ComponentLike)component);
    }

    @NotNull
    default public Component append(@NotNull ComponentLike componentLike) {
        Objects.requireNonNull(componentLike, "like");
        Component component = componentLike.asComponent();
        Objects.requireNonNull(component, "component");
        if (component == Component.empty()) {
            return this;
        }
        List<Component> list = this.children();
        return this.children(MonkeyBars.addOne(list, component));
    }

    @Contract(pure=true)
    @NotNull
    default public Component append(@NotNull ComponentBuilder<?, ?> componentBuilder) {
        return this.append((Component)componentBuilder.build());
    }

    @Contract(pure=true)
    @NotNull
    default public Component appendNewline() {
        return this.append(Component.newline());
    }

    @Contract(pure=true)
    @NotNull
    default public Component appendSpace() {
        return this.append(Component.space());
    }

    @Contract(pure=true)
    @NotNull
    default public Component applyFallbackStyle(@NotNull Style style) {
        Objects.requireNonNull(style, "style");
        return this.style(this.style().merge(style, Style.Merge.Strategy.IF_ABSENT_ON_TARGET));
    }

    @Contract(pure=true)
    @NotNull
    default public Component applyFallbackStyle(@NotNull @NotNull StyleBuilderApplicable @NotNull ... styleBuilderApplicableArray) {
        return this.applyFallbackStyle(Style.style(styleBuilderApplicableArray));
    }

    @NotNull
    public Style style();

    @Contract(pure=true)
    @NotNull
    public Component style(@NotNull Style var1);

    @Contract(pure=true)
    @NotNull
    default public Component style(@NotNull Consumer<Style.Builder> consumer) {
        return this.style(this.style().edit(consumer));
    }

    @Contract(pure=true)
    @NotNull
    default public Component style(@NotNull Consumer<Style.Builder> consumer, @NotNull Style.Merge.Strategy strategy) {
        return this.style(this.style().edit(consumer, strategy));
    }

    @Contract(pure=true)
    @NotNull
    default public Component style(@NotNull Style.Builder builder) {
        return this.style(builder.build());
    }

    @Contract(pure=true)
    @NotNull
    default public Component mergeStyle(@NotNull Component component) {
        return this.mergeStyle(component, Style.Merge.all());
    }

    @Contract(pure=true)
    @NotNull
    default public Component mergeStyle(@NotNull Component component, @NotNull Style.Merge @NotNull ... mergeArray) {
        return this.mergeStyle(component, Style.Merge.merges(mergeArray));
    }

    @Contract(pure=true)
    @NotNull
    default public Component mergeStyle(@NotNull Component component, @NotNull Set<Style.Merge> set) {
        return this.style(this.style().merge(component.style(), set));
    }

    @Override
    @Nullable
    default public Key font() {
        return this.style().font();
    }

    @Override
    @NotNull
    default public Component font(@Nullable Key key) {
        return this.style(this.style().font(key));
    }

    @Override
    @Nullable
    default public TextColor color() {
        return this.style().color();
    }

    @Override
    @Contract(pure=true)
    @NotNull
    default public Component color(@Nullable TextColor textColor) {
        return this.style(this.style().color(textColor));
    }

    @Override
    @Contract(pure=true)
    @NotNull
    default public Component colorIfAbsent(@Nullable TextColor textColor) {
        if (this.color() == null) {
            return this.color(textColor);
        }
        return this;
    }

    @Override
    default public boolean hasDecoration(@NotNull TextDecoration textDecoration) {
        return StyleGetter.super.hasDecoration(textDecoration);
    }

    @Override
    @Contract(pure=true)
    @NotNull
    default public Component decorate(@NotNull TextDecoration textDecoration) {
        return (Component)StyleSetter.super.decorate(textDecoration);
    }

    @Override
    default public @NotNull TextDecoration.State decoration(@NotNull TextDecoration textDecoration) {
        return this.style().decoration(textDecoration);
    }

    @Override
    @Contract(pure=true)
    @NotNull
    default public Component decoration(@NotNull TextDecoration textDecoration, boolean bl) {
        return (Component)StyleSetter.super.decoration(textDecoration, bl);
    }

    @Override
    @Contract(pure=true)
    @NotNull
    default public Component decoration(@NotNull TextDecoration textDecoration, @NotNull TextDecoration.State state) {
        return this.style(this.style().decoration(textDecoration, state));
    }

    @Override
    @NotNull
    default public Component decorationIfAbsent(@NotNull TextDecoration textDecoration, @NotNull TextDecoration.State state) {
        Objects.requireNonNull(state, "state");
        @NotNull TextDecoration.State state2 = this.decoration(textDecoration);
        if (state2 == TextDecoration.State.NOT_SET) {
            return this.style(this.style().decoration(textDecoration, state));
        }
        return this;
    }

    @Override
    @NotNull
    default public Map<TextDecoration, TextDecoration.State> decorations() {
        return this.style().decorations();
    }

    @Override
    @Contract(pure=true)
    @NotNull
    default public Component decorations(@NotNull Map<TextDecoration, TextDecoration.State> map) {
        return this.style((Style)this.style().decorations((Map)map));
    }

    @Override
    @Nullable
    default public ClickEvent clickEvent() {
        return this.style().clickEvent();
    }

    @Override
    @Contract(pure=true)
    @NotNull
    default public Component clickEvent(@Nullable ClickEvent clickEvent) {
        return this.style(this.style().clickEvent(clickEvent));
    }

    @Override
    @Nullable
    default public HoverEvent<?> hoverEvent() {
        return this.style().hoverEvent();
    }

    @Override
    @Contract(pure=true)
    @NotNull
    default public Component hoverEvent(@Nullable HoverEventSource<?> hoverEventSource) {
        return this.style((Style)this.style().hoverEvent((HoverEventSource)hoverEventSource));
    }

    @Override
    @Nullable
    default public String insertion() {
        return this.style().insertion();
    }

    @Override
    @Contract(pure=true)
    @NotNull
    default public Component insertion(@Nullable String string) {
        return this.style(this.style().insertion(string));
    }

    default public boolean hasStyling() {
        return !this.style().isEmpty();
    }

    @Contract(pure=true)
    @NotNull
    default public Component replaceText(@NotNull Consumer<TextReplacementConfig.Builder> consumer) {
        Objects.requireNonNull(consumer, "configurer");
        return this.replaceText((TextReplacementConfig)AbstractBuilder.configureAndBuild(TextReplacementConfig.builder(), consumer));
    }

    @Contract(pure=true)
    @NotNull
    default public Component replaceText(@NotNull TextReplacementConfig textReplacementConfig) {
        Objects.requireNonNull(textReplacementConfig, "replacement");
        if (!(textReplacementConfig instanceof TextReplacementConfigImpl)) {
            throw new IllegalArgumentException("Provided replacement was a custom TextReplacementConfig implementation, which is not supported.");
        }
        return TextReplacementRenderer.INSTANCE.render(this, ((TextReplacementConfigImpl)textReplacementConfig).createState());
    }

    @NotNull
    default public Component compact() {
        return ComponentCompaction.compact(this, null);
    }

    @NotNull
    default public Iterable<Component> iterable(@NotNull ComponentIteratorType componentIteratorType, @NotNull ComponentIteratorFlag @Nullable ... componentIteratorFlagArray) {
        return this.iterable(componentIteratorType, componentIteratorFlagArray == null ? Collections.emptySet() : MonkeyBars.enumSet(ComponentIteratorFlag.class, (Enum[])componentIteratorFlagArray));
    }

    @NotNull
    default public Iterable<Component> iterable(@NotNull ComponentIteratorType componentIteratorType, @NotNull Set<ComponentIteratorFlag> set) {
        Objects.requireNonNull(componentIteratorType, "type");
        Objects.requireNonNull(set, "flags");
        return new ForwardingIterator<Component>(() -> this.lambda$iterable$4(componentIteratorType, set), () -> this.lambda$iterable$5(componentIteratorType, set));
    }

    @NotNull
    default public Iterator<Component> iterator(@NotNull ComponentIteratorType componentIteratorType, @NotNull ComponentIteratorFlag @Nullable ... componentIteratorFlagArray) {
        return this.iterator(componentIteratorType, componentIteratorFlagArray == null ? Collections.emptySet() : MonkeyBars.enumSet(ComponentIteratorFlag.class, (Enum[])componentIteratorFlagArray));
    }

    @NotNull
    default public Iterator<Component> iterator(@NotNull ComponentIteratorType componentIteratorType, @NotNull Set<ComponentIteratorFlag> set) {
        return new ComponentIterator(this, Objects.requireNonNull(componentIteratorType, "type"), Objects.requireNonNull(set, "flags"));
    }

    @NotNull
    default public Spliterator<Component> spliterator(@NotNull ComponentIteratorType componentIteratorType, @NotNull ComponentIteratorFlag @Nullable ... componentIteratorFlagArray) {
        return this.spliterator(componentIteratorType, componentIteratorFlagArray == null ? Collections.emptySet() : MonkeyBars.enumSet(ComponentIteratorFlag.class, (Enum[])componentIteratorFlagArray));
    }

    @NotNull
    default public Spliterator<Component> spliterator(@NotNull ComponentIteratorType componentIteratorType, @NotNull Set<ComponentIteratorFlag> set) {
        return Spliterators.spliteratorUnknownSize(this.iterator(componentIteratorType, set), 1296);
    }

    @Deprecated
    @ApiStatus.ScheduledForRemoval(inVersion="5.0.0")
    @Contract(pure=true)
    @NotNull
    default public Component replaceText(@NotNull String string, @Nullable ComponentLike componentLike) {
        return this.replaceText(arg_0 -> Component.lambda$replaceText$6(string, componentLike, arg_0));
    }

    @Deprecated
    @ApiStatus.ScheduledForRemoval(inVersion="5.0.0")
    @Contract(pure=true)
    @NotNull
    default public Component replaceText(@NotNull Pattern pattern, @NotNull Function<TextComponent.Builder, @Nullable ComponentLike> function) {
        return this.replaceText(arg_0 -> Component.lambda$replaceText$7(pattern, function, arg_0));
    }

    @Deprecated
    @ApiStatus.ScheduledForRemoval(inVersion="5.0.0")
    @Contract(pure=true)
    @NotNull
    default public Component replaceFirstText(@NotNull String string, @Nullable ComponentLike componentLike) {
        return this.replaceText(arg_0 -> Component.lambda$replaceFirstText$8(string, componentLike, arg_0));
    }

    @Deprecated
    @ApiStatus.ScheduledForRemoval(inVersion="5.0.0")
    @Contract(pure=true)
    @NotNull
    default public Component replaceFirstText(@NotNull Pattern pattern, @NotNull Function<TextComponent.Builder, @Nullable ComponentLike> function) {
        return this.replaceText(arg_0 -> Component.lambda$replaceFirstText$9(pattern, function, arg_0));
    }

    @Deprecated
    @ApiStatus.ScheduledForRemoval(inVersion="5.0.0")
    @Contract(pure=true)
    @NotNull
    default public Component replaceText(@NotNull String string, @Nullable ComponentLike componentLike, int n) {
        return this.replaceText(arg_0 -> Component.lambda$replaceText$10(string, n, componentLike, arg_0));
    }

    @Deprecated
    @ApiStatus.ScheduledForRemoval(inVersion="5.0.0")
    @Contract(pure=true)
    @NotNull
    default public Component replaceText(@NotNull Pattern pattern, @NotNull Function<TextComponent.Builder, @Nullable ComponentLike> function, int n) {
        return this.replaceText(arg_0 -> Component.lambda$replaceText$11(pattern, n, function, arg_0));
    }

    @Deprecated
    @ApiStatus.ScheduledForRemoval(inVersion="5.0.0")
    @Contract(pure=true)
    @NotNull
    default public Component replaceText(@NotNull String string, @Nullable ComponentLike componentLike, @NotNull IntFunction2<PatternReplacementResult> intFunction2) {
        return this.replaceText(arg_0 -> Component.lambda$replaceText$12(string, componentLike, intFunction2, arg_0));
    }

    @Deprecated
    @ApiStatus.ScheduledForRemoval(inVersion="5.0.0")
    @Contract(pure=true)
    @NotNull
    default public Component replaceText(@NotNull Pattern pattern, @NotNull Function<TextComponent.Builder, @Nullable ComponentLike> function, @NotNull IntFunction2<PatternReplacementResult> intFunction2) {
        return this.replaceText(arg_0 -> Component.lambda$replaceText$13(pattern, function, intFunction2, arg_0));
    }

    @Override
    default public void componentBuilderApply(@NotNull ComponentBuilder<?, ?> componentBuilder) {
        componentBuilder.append(this);
    }

    @Override
    @NotNull
    default public Component asComponent() {
        return this;
    }

    @Override
    @NotNull
    default public HoverEvent<Component> asHoverEvent(@NotNull UnaryOperator<Component> unaryOperator) {
        return HoverEvent.showText((Component)unaryOperator.apply(this));
    }

    @Override
    @NotNull
    default public Stream<? extends ExaminableProperty> examinableProperties() {
        return Stream.of(ExaminableProperty.of("style", this.style()), ExaminableProperty.of("children", this.children()));
    }

    @Override
    @Contract(pure=true)
    @NotNull
    default public StyleSetter insertion(@Nullable String string) {
        return this.insertion(string);
    }

    @Override
    @Contract(pure=true)
    @NotNull
    default public StyleSetter hoverEvent(@Nullable HoverEventSource hoverEventSource) {
        return this.hoverEvent(hoverEventSource);
    }

    @Override
    @Contract(pure=true)
    @NotNull
    default public StyleSetter clickEvent(@Nullable ClickEvent clickEvent) {
        return this.clickEvent(clickEvent);
    }

    @Override
    @Contract(pure=true)
    @NotNull
    default public StyleSetter decorations(@NotNull Map map) {
        return this.decorations(map);
    }

    @Override
    @NotNull
    default public StyleSetter decorationIfAbsent(@NotNull TextDecoration textDecoration, @NotNull TextDecoration.State state) {
        return this.decorationIfAbsent(textDecoration, state);
    }

    @Override
    @Contract(pure=true)
    @NotNull
    default public StyleSetter decoration(@NotNull TextDecoration textDecoration, @NotNull TextDecoration.State state) {
        return this.decoration(textDecoration, state);
    }

    @Override
    @Contract(pure=true)
    @NotNull
    default public StyleSetter decoration(@NotNull TextDecoration textDecoration, boolean bl) {
        return this.decoration(textDecoration, bl);
    }

    @Override
    @Contract(pure=true)
    @NotNull
    default public StyleSetter decorate(@NotNull TextDecoration textDecoration) {
        return this.decorate(textDecoration);
    }

    @Override
    @Contract(pure=true)
    @NotNull
    default public StyleSetter colorIfAbsent(@Nullable TextColor textColor) {
        return this.colorIfAbsent(textColor);
    }

    @Override
    @Contract(pure=true)
    @NotNull
    default public StyleSetter color(@Nullable TextColor textColor) {
        return this.color(textColor);
    }

    @Override
    @NotNull
    default public StyleSetter font(@Nullable Key key) {
        return this.font(key);
    }

    private static void lambda$replaceText$13(Pattern pattern, Function function, IntFunction2 intFunction2, TextReplacementConfig.Builder builder) {
        builder.match(pattern).replacement(function).condition(intFunction2);
    }

    private static void lambda$replaceText$12(String string, ComponentLike componentLike, IntFunction2 intFunction2, TextReplacementConfig.Builder builder) {
        builder.matchLiteral(string).replacement(componentLike).condition(intFunction2);
    }

    private static void lambda$replaceText$11(Pattern pattern, int n, Function function, TextReplacementConfig.Builder builder) {
        builder.match(pattern).times(n).replacement(function);
    }

    private static void lambda$replaceText$10(String string, int n, ComponentLike componentLike, TextReplacementConfig.Builder builder) {
        builder.matchLiteral(string).times(n).replacement(componentLike);
    }

    private static void lambda$replaceFirstText$9(Pattern pattern, Function function, TextReplacementConfig.Builder builder) {
        builder.match(pattern).once().replacement(function);
    }

    private static void lambda$replaceFirstText$8(String string, ComponentLike componentLike, TextReplacementConfig.Builder builder) {
        builder.matchLiteral(string).once().replacement(componentLike);
    }

    private static void lambda$replaceText$7(Pattern pattern, Function function, TextReplacementConfig.Builder builder) {
        builder.match(pattern).replacement(function);
    }

    private static void lambda$replaceText$6(String string, ComponentLike componentLike, TextReplacementConfig.Builder builder) {
        builder.matchLiteral(string).replacement(componentLike);
    }

    private Spliterator lambda$iterable$5(ComponentIteratorType componentIteratorType, Set set) {
        return this.spliterator(componentIteratorType, set);
    }

    private Iterator lambda$iterable$4(ComponentIteratorType componentIteratorType, Set set) {
        return this.iterator(componentIteratorType, set);
    }

    private static TextComponent.Builder lambda$toComponent$3(Component component, TextComponent.Builder builder, TextComponent.Builder builder2) {
        List<Component> list = builder.children();
        TextComponent.Builder builder3 = (TextComponent.Builder)Component.text().append(list);
        if (!list.isEmpty()) {
            builder3.append(component);
        }
        builder3.append(builder2.children());
        return builder3;
    }

    private static void lambda$toComponent$2(Component component, TextComponent.Builder builder, Component component2) {
        if (component != Component.empty() && !builder.children().isEmpty()) {
            builder.append(component);
        }
        builder.append(component2);
    }

    private static boolean lambda$static$1(Component component) {
        return component != Component.empty();
    }

    private static boolean lambda$static$0(Component component, Component component2) {
        return component == component2;
    }
}

