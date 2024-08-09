/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.libs.kyori.adventure.text;

import com.viaversion.viaversion.libs.kyori.adventure.text.AbstractComponentBuilder;
import com.viaversion.viaversion.libs.kyori.adventure.text.Component;
import com.viaversion.viaversion.libs.kyori.adventure.text.ComponentLike;
import com.viaversion.viaversion.libs.kyori.adventure.text.NBTComponent;
import com.viaversion.viaversion.libs.kyori.adventure.text.NBTComponentBuilder;
import java.util.Objects;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

abstract class AbstractNBTComponentBuilder<C extends NBTComponent<C, B>, B extends NBTComponentBuilder<C, B>>
extends AbstractComponentBuilder<C, B>
implements NBTComponentBuilder<C, B> {
    @Nullable
    protected String nbtPath;
    protected boolean interpret = false;
    @Nullable
    protected Component separator;

    AbstractNBTComponentBuilder() {
    }

    AbstractNBTComponentBuilder(@NotNull C c) {
        super(c);
        this.nbtPath = c.nbtPath();
        this.interpret = c.interpret();
        this.separator = c.separator();
    }

    @Override
    @NotNull
    public B nbtPath(@NotNull String string) {
        this.nbtPath = Objects.requireNonNull(string, "nbtPath");
        return (B)this;
    }

    @Override
    @NotNull
    public B interpret(boolean bl) {
        this.interpret = bl;
        return (B)this;
    }

    @Override
    @NotNull
    public B separator(@Nullable ComponentLike componentLike) {
        this.separator = ComponentLike.unbox(componentLike);
        return (B)this;
    }
}

