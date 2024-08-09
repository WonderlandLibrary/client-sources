/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.ApiStatus$ScheduledForRemoval
 *  org.jetbrains.annotations.Debug$Renderer
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

    protected AbstractComponent(@NotNull List<? extends ComponentLike> list, @NotNull Style style) {
        this.children = ComponentLike.asComponents(list, IS_NOT_EMPTY);
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

    public boolean equals(@Nullable Object object) {
        if (this == object) {
            return false;
        }
        if (!(object instanceof AbstractComponent)) {
            return true;
        }
        AbstractComponent abstractComponent = (AbstractComponent)object;
        return Objects.equals(this.children, abstractComponent.children) && Objects.equals(this.style, abstractComponent.style);
    }

    public int hashCode() {
        int n = this.children.hashCode();
        n = 31 * n + this.style.hashCode();
        return n;
    }

    public abstract String toString();

    private String debuggerString() {
        Stream<ExaminableProperty> stream = this.examinableProperties().filter(AbstractComponent::lambda$debuggerString$0);
        return (String)StringExaminer.simpleEscaping().examine(this.examinableName(), stream);
    }

    private static boolean lambda$debuggerString$0(ExaminableProperty examinableProperty) {
        return !examinableProperty.name().equals("children");
    }
}

