/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package com.viaversion.viaversion.libs.kyori.adventure.text;

import com.viaversion.viaversion.libs.kyori.adventure.text.AbstractComponent;
import com.viaversion.viaversion.libs.kyori.adventure.text.Component;
import com.viaversion.viaversion.libs.kyori.adventure.text.NBTComponent;
import com.viaversion.viaversion.libs.kyori.adventure.text.NBTComponentBuilder;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.Style;
import java.util.List;
import java.util.Objects;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

abstract class NBTComponentImpl<C extends NBTComponent<C, B>, B extends NBTComponentBuilder<C, B>>
extends AbstractComponent
implements NBTComponent<C, B> {
    static final boolean INTERPRET_DEFAULT = false;
    final String nbtPath;
    final boolean interpret;
    @Nullable
    final Component separator;

    NBTComponentImpl(@NotNull List<Component> children, @NotNull Style style, String nbtPath, boolean interpret, @Nullable Component separator) {
        super(children, style);
        this.nbtPath = nbtPath;
        this.interpret = interpret;
        this.separator = separator;
    }

    @Override
    @NotNull
    public String nbtPath() {
        return this.nbtPath;
    }

    @Override
    public boolean interpret() {
        return this.interpret;
    }

    @Override
    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof NBTComponent)) {
            return false;
        }
        if (!super.equals(other)) {
            return false;
        }
        NBTComponent that = (NBTComponent)other;
        return Objects.equals(this.nbtPath, that.nbtPath()) && this.interpret == that.interpret() && Objects.equals(this.separator, that.separator());
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + this.nbtPath.hashCode();
        result = 31 * result + Boolean.hashCode(this.interpret);
        result = 31 * result + Objects.hashCode(this.separator);
        return result;
    }
}

