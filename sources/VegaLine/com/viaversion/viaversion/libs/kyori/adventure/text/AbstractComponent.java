/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.ApiStatus$ScheduledForRemoval
 *  org.jetbrains.annotations.Debug$Renderer
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 */
package com.viaversion.viaversion.libs.kyori.adventure.text;

import com.viaversion.viaversion.libs.kyori.adventure.text.Component;
import com.viaversion.viaversion.libs.kyori.adventure.text.ComponentLike;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.Style;
import com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty;
import com.viaversion.viaversion.libs.kyori.examination.string.StringExaminer;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.Debug;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Deprecated
@ApiStatus.ScheduledForRemoval(inVersion="5.0.0")
@Debug.Renderer(text="this.debuggerString()", childrenArray="this.children().toArray()", hasChildren="!this.children().isEmpty()")
public abstract class AbstractComponent
implements Component {
    protected final List<Component> children;
    protected final Style style;

    protected AbstractComponent(@NotNull List<? extends ComponentLike> children, @NotNull Style style) {
        this.children = ComponentLike.asComponents(children, IS_NOT_EMPTY);
        this.style = style;
    }

    @Override
    @NotNull
    public final List<Component> children() {
        return this.children;
    }

    @Override
    @NotNull
    public final Style style() {
        return this.style;
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof AbstractComponent)) {
            return false;
        }
        AbstractComponent that = (AbstractComponent)other;
        return Objects.equals(this.children, that.children) && Objects.equals(this.style, that.style);
    }

    public int hashCode() {
        int result = this.children.hashCode();
        result = 31 * result + this.style.hashCode();
        return result;
    }

    public abstract String toString();

    private String debuggerString() {
        Stream<ExaminableProperty> examinablePropertiesWithoutChildren = this.examinableProperties().filter(property -> !property.name().equals("children"));
        return (String)StringExaminer.simpleEscaping().examine(this.examinableName(), examinablePropertiesWithoutChildren);
    }
}

