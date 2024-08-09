/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.libs.kyori.adventure.text.renderer;

import com.viaversion.viaversion.libs.kyori.adventure.text.BlockNBTComponent;
import com.viaversion.viaversion.libs.kyori.adventure.text.BuildableComponent;
import com.viaversion.viaversion.libs.kyori.adventure.text.Component;
import com.viaversion.viaversion.libs.kyori.adventure.text.ComponentBuilder;
import com.viaversion.viaversion.libs.kyori.adventure.text.EntityNBTComponent;
import com.viaversion.viaversion.libs.kyori.adventure.text.KeybindComponent;
import com.viaversion.viaversion.libs.kyori.adventure.text.NBTComponent;
import com.viaversion.viaversion.libs.kyori.adventure.text.NBTComponentBuilder;
import com.viaversion.viaversion.libs.kyori.adventure.text.ScoreComponent;
import com.viaversion.viaversion.libs.kyori.adventure.text.SelectorComponent;
import com.viaversion.viaversion.libs.kyori.adventure.text.StorageNBTComponent;
import com.viaversion.viaversion.libs.kyori.adventure.text.TextComponent;
import com.viaversion.viaversion.libs.kyori.adventure.text.TranslatableComponent;
import com.viaversion.viaversion.libs.kyori.adventure.text.event.HoverEvent;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.Style;
import com.viaversion.viaversion.libs.kyori.adventure.text.renderer.AbstractComponentRenderer;
import com.viaversion.viaversion.libs.kyori.adventure.translation.Translator;
import java.text.AttributedCharacterIterator;
import java.text.FieldPosition;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class TranslatableComponentRenderer<C>
extends AbstractComponentRenderer<C> {
    private static final Set<Style.Merge> MERGES = Style.Merge.merges(Style.Merge.COLOR, Style.Merge.DECORATIONS, Style.Merge.INSERTION, Style.Merge.FONT);

    @NotNull
    public static TranslatableComponentRenderer<Locale> usingTranslationSource(@NotNull Translator translator) {
        Objects.requireNonNull(translator, "source");
        return new TranslatableComponentRenderer<Locale>(translator){
            final Translator val$source;
            {
                this.val$source = translator;
            }

            @Override
            @Nullable
            protected MessageFormat translate(@NotNull String string, @NotNull Locale locale) {
                return this.val$source.translate(string, locale);
            }

            @Override
            @NotNull
            protected Component renderTranslatable(@NotNull TranslatableComponent translatableComponent, @NotNull Locale locale) {
                @Nullable Component component = this.val$source.translate(translatableComponent, locale);
                if (component != null) {
                    return component;
                }
                return super.renderTranslatable(translatableComponent, locale);
            }

            @Override
            @NotNull
            protected Component renderTranslatable(@NotNull TranslatableComponent translatableComponent, @NotNull Object object) {
                return this.renderTranslatable(translatableComponent, (Locale)object);
            }

            @Override
            @Nullable
            protected MessageFormat translate(@NotNull String string, @NotNull Object object) {
                return this.translate(string, (Locale)object);
            }
        };
    }

    @Nullable
    protected abstract MessageFormat translate(@NotNull String var1, @NotNull C var2);

    @Override
    @NotNull
    protected Component renderBlockNbt(@NotNull BlockNBTComponent blockNBTComponent, @NotNull C c) {
        BlockNBTComponent.Builder builder = this.nbt(c, Component.blockNBT(), blockNBTComponent).pos(blockNBTComponent.pos());
        return this.mergeStyleAndOptionallyDeepRender(blockNBTComponent, builder, c);
    }

    @Override
    @NotNull
    protected Component renderEntityNbt(@NotNull EntityNBTComponent entityNBTComponent, @NotNull C c) {
        EntityNBTComponent.Builder builder = this.nbt(c, Component.entityNBT(), entityNBTComponent).selector(entityNBTComponent.selector());
        return this.mergeStyleAndOptionallyDeepRender(entityNBTComponent, builder, c);
    }

    @Override
    @NotNull
    protected Component renderStorageNbt(@NotNull StorageNBTComponent storageNBTComponent, @NotNull C c) {
        StorageNBTComponent.Builder builder = this.nbt(c, Component.storageNBT(), storageNBTComponent).storage(storageNBTComponent.storage());
        return this.mergeStyleAndOptionallyDeepRender(storageNBTComponent, builder, c);
    }

    protected <O extends NBTComponent<O, B>, B extends NBTComponentBuilder<O, B>> B nbt(@NotNull C c, B b, O o) {
        b.nbtPath(o.nbtPath()).interpret(o.interpret());
        @Nullable Component component = o.separator();
        if (component != null) {
            b.separator(this.render(component, c));
        }
        return b;
    }

    @Override
    @NotNull
    protected Component renderKeybind(@NotNull KeybindComponent keybindComponent, @NotNull C c) {
        KeybindComponent.Builder builder = Component.keybind().keybind(keybindComponent.keybind());
        return this.mergeStyleAndOptionallyDeepRender(keybindComponent, builder, c);
    }

    @Override
    @NotNull
    protected Component renderScore(@NotNull ScoreComponent scoreComponent, @NotNull C c) {
        ScoreComponent.Builder builder = Component.score().name(scoreComponent.name()).objective(scoreComponent.objective()).value(scoreComponent.value());
        return this.mergeStyleAndOptionallyDeepRender(scoreComponent, builder, c);
    }

    @Override
    @NotNull
    protected Component renderSelector(@NotNull SelectorComponent selectorComponent, @NotNull C c) {
        SelectorComponent.Builder builder = Component.selector().pattern(selectorComponent.pattern());
        return this.mergeStyleAndOptionallyDeepRender(selectorComponent, builder, c);
    }

    @Override
    @NotNull
    protected Component renderText(@NotNull TextComponent textComponent, @NotNull C c) {
        TextComponent.Builder builder = Component.text().content(textComponent.content());
        return this.mergeStyleAndOptionallyDeepRender(textComponent, builder, c);
    }

    @Override
    @NotNull
    protected Component renderTranslatable(@NotNull TranslatableComponent translatableComponent, @NotNull C c) {
        @Nullable MessageFormat messageFormat = this.translate(translatableComponent.key(), c);
        if (messageFormat == null) {
            TranslatableComponent.Builder builder = Component.translatable().key(translatableComponent.key()).fallback(translatableComponent.fallback());
            if (!translatableComponent.args().isEmpty()) {
                ArrayList<Component> arrayList = new ArrayList<Component>(translatableComponent.args());
                int n = arrayList.size();
                for (int i = 0; i < n; ++i) {
                    arrayList.set(i, this.render((Component)arrayList.get(i), c));
                }
                builder.args(arrayList);
            }
            return this.mergeStyleAndOptionallyDeepRender(translatableComponent, builder, c);
        }
        List<Component> list = translatableComponent.args();
        TextComponent.Builder builder = Component.text();
        this.mergeStyle(translatableComponent, builder, c);
        if (list.isEmpty()) {
            builder.content(messageFormat.format(null, new StringBuffer(), null).toString());
            return this.optionallyRenderChildrenAppendAndBuild(translatableComponent.children(), builder, c);
        }
        Object[] objectArray = new Object[list.size()];
        StringBuffer stringBuffer = messageFormat.format(objectArray, new StringBuffer(), (FieldPosition)null);
        AttributedCharacterIterator attributedCharacterIterator = messageFormat.formatToCharacterIterator(objectArray);
        while (attributedCharacterIterator.getIndex() < attributedCharacterIterator.getEndIndex()) {
            int n = attributedCharacterIterator.getRunLimit();
            Integer n2 = (Integer)attributedCharacterIterator.getAttribute(MessageFormat.Field.ARGUMENT);
            if (n2 != null) {
                builder.append(this.render(list.get(n2), c));
            } else {
                builder.append((Component)Component.text(stringBuffer.substring(attributedCharacterIterator.getIndex(), n)));
            }
            attributedCharacterIterator.setIndex(n);
        }
        return this.optionallyRenderChildrenAppendAndBuild(translatableComponent.children(), builder, c);
    }

    protected <O extends BuildableComponent<O, B>, B extends ComponentBuilder<O, B>> O mergeStyleAndOptionallyDeepRender(Component component, B b, C c) {
        this.mergeStyle(component, b, c);
        return this.optionallyRenderChildrenAppendAndBuild(component.children(), b, c);
    }

    protected <O extends BuildableComponent<O, B>, B extends ComponentBuilder<O, B>> O optionallyRenderChildrenAppendAndBuild(List<Component> list, B b, C c) {
        if (!list.isEmpty()) {
            list.forEach(arg_0 -> this.lambda$optionallyRenderChildrenAppendAndBuild$0(b, c, arg_0));
        }
        return (O)b.build();
    }

    protected <B extends ComponentBuilder<?, ?>> void mergeStyle(Component component, B b, C c) {
        b.mergeStyle(component, MERGES);
        b.clickEvent(component.clickEvent());
        @Nullable HoverEvent<?> hoverEvent = component.hoverEvent();
        if (hoverEvent != null) {
            b.hoverEvent(hoverEvent.withRenderedValue(this, c));
        }
    }

    private void lambda$optionallyRenderChildrenAppendAndBuild$0(ComponentBuilder componentBuilder, Object object, Component component) {
        componentBuilder.append(this.render(component, object));
    }
}

