/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.libs.kyori.adventure.text.renderer;

import com.viaversion.viaversion.libs.kyori.adventure.text.BlockNBTComponent;
import com.viaversion.viaversion.libs.kyori.adventure.text.Component;
import com.viaversion.viaversion.libs.kyori.adventure.text.EntityNBTComponent;
import com.viaversion.viaversion.libs.kyori.adventure.text.KeybindComponent;
import com.viaversion.viaversion.libs.kyori.adventure.text.NBTComponent;
import com.viaversion.viaversion.libs.kyori.adventure.text.ScoreComponent;
import com.viaversion.viaversion.libs.kyori.adventure.text.SelectorComponent;
import com.viaversion.viaversion.libs.kyori.adventure.text.StorageNBTComponent;
import com.viaversion.viaversion.libs.kyori.adventure.text.TextComponent;
import com.viaversion.viaversion.libs.kyori.adventure.text.TranslatableComponent;
import com.viaversion.viaversion.libs.kyori.adventure.text.renderer.ComponentRenderer;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractComponentRenderer<C>
implements ComponentRenderer<C> {
    @Override
    @NotNull
    public Component render(@NotNull Component component, @NotNull C c) {
        if (component instanceof TextComponent) {
            return this.renderText((TextComponent)component, c);
        }
        if (component instanceof TranslatableComponent) {
            return this.renderTranslatable((TranslatableComponent)component, c);
        }
        if (component instanceof KeybindComponent) {
            return this.renderKeybind((KeybindComponent)component, c);
        }
        if (component instanceof ScoreComponent) {
            return this.renderScore((ScoreComponent)component, c);
        }
        if (component instanceof SelectorComponent) {
            return this.renderSelector((SelectorComponent)component, c);
        }
        if (component instanceof NBTComponent) {
            if (component instanceof BlockNBTComponent) {
                return this.renderBlockNbt((BlockNBTComponent)component, c);
            }
            if (component instanceof EntityNBTComponent) {
                return this.renderEntityNbt((EntityNBTComponent)component, c);
            }
            if (component instanceof StorageNBTComponent) {
                return this.renderStorageNbt((StorageNBTComponent)component, c);
            }
        }
        return component;
    }

    @NotNull
    protected abstract Component renderBlockNbt(@NotNull BlockNBTComponent var1, @NotNull C var2);

    @NotNull
    protected abstract Component renderEntityNbt(@NotNull EntityNBTComponent var1, @NotNull C var2);

    @NotNull
    protected abstract Component renderStorageNbt(@NotNull StorageNBTComponent var1, @NotNull C var2);

    @NotNull
    protected abstract Component renderKeybind(@NotNull KeybindComponent var1, @NotNull C var2);

    @NotNull
    protected abstract Component renderScore(@NotNull ScoreComponent var1, @NotNull C var2);

    @NotNull
    protected abstract Component renderSelector(@NotNull SelectorComponent var1, @NotNull C var2);

    @NotNull
    protected abstract Component renderText(@NotNull TextComponent var1, @NotNull C var2);

    @NotNull
    protected abstract Component renderTranslatable(@NotNull TranslatableComponent var1, @NotNull C var2);
}

