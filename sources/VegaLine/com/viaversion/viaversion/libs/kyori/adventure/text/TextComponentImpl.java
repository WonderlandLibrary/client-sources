/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
 *  org.jetbrains.annotations.VisibleForTesting
 */
package com.viaversion.viaversion.libs.kyori.adventure.text;

import com.viaversion.viaversion.libs.kyori.adventure.internal.Internals;
import com.viaversion.viaversion.libs.kyori.adventure.internal.properties.AdventureProperties;
import com.viaversion.viaversion.libs.kyori.adventure.text.AbstractComponent;
import com.viaversion.viaversion.libs.kyori.adventure.text.AbstractComponentBuilder;
import com.viaversion.viaversion.libs.kyori.adventure.text.Component;
import com.viaversion.viaversion.libs.kyori.adventure.text.ComponentLike;
import com.viaversion.viaversion.libs.kyori.adventure.text.LegacyFormattingDetected;
import com.viaversion.viaversion.libs.kyori.adventure.text.TextComponent;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.Style;
import com.viaversion.viaversion.libs.kyori.adventure.util.Nag;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.VisibleForTesting;

final class TextComponentImpl
extends AbstractComponent
implements TextComponent {
    private static final boolean WARN_WHEN_LEGACY_FORMATTING_DETECTED = Boolean.TRUE.equals(AdventureProperties.TEXT_WARN_WHEN_LEGACY_FORMATTING_DETECTED.value());
    @VisibleForTesting
    static final char SECTION_CHAR = '\u00a7';
    static final TextComponent EMPTY = TextComponentImpl.createDirect("");
    static final TextComponent NEWLINE = TextComponentImpl.createDirect("\n");
    static final TextComponent SPACE = TextComponentImpl.createDirect(" ");
    private final String content;

    static TextComponent create(@NotNull List<? extends ComponentLike> children, @NotNull Style style, @NotNull String content) {
        List<Component> filteredChildren = ComponentLike.asComponents(children, IS_NOT_EMPTY);
        if (filteredChildren.isEmpty() && style.isEmpty() && content.isEmpty()) {
            return Component.empty();
        }
        return new TextComponentImpl(filteredChildren, Objects.requireNonNull(style, "style"), Objects.requireNonNull(content, "content"));
    }

    @NotNull
    private static TextComponent createDirect(@NotNull String content) {
        return new TextComponentImpl(Collections.emptyList(), Style.empty(), content);
    }

    TextComponentImpl(@NotNull List<Component> children, @NotNull Style style, @NotNull String content) {
        super(children, style);
        LegacyFormattingDetected nag;
        this.content = content;
        if (WARN_WHEN_LEGACY_FORMATTING_DETECTED && (nag = this.warnWhenLegacyFormattingDetected()) != null) {
            Nag.print(nag);
        }
    }

    @VisibleForTesting
    @Nullable
    final LegacyFormattingDetected warnWhenLegacyFormattingDetected() {
        if (this.content.indexOf(167) != -1) {
            return new LegacyFormattingDetected(this);
        }
        return null;
    }

    @Override
    @NotNull
    public String content() {
        return this.content;
    }

    @Override
    @NotNull
    public TextComponent content(@NotNull String content) {
        if (Objects.equals(this.content, content)) {
            return this;
        }
        return TextComponentImpl.create(this.children, this.style, content);
    }

    @Override
    @NotNull
    public TextComponent children(@NotNull List<? extends ComponentLike> children) {
        return TextComponentImpl.create(children, this.style, this.content);
    }

    @Override
    @NotNull
    public TextComponent style(@NotNull Style style) {
        return TextComponentImpl.create(this.children, style, this.content);
    }

    @Override
    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof TextComponentImpl)) {
            return false;
        }
        if (!super.equals(other)) {
            return false;
        }
        TextComponentImpl that = (TextComponentImpl)other;
        return Objects.equals(this.content, that.content);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + this.content.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return Internals.toString(this);
    }

    @Override
    @NotNull
    public TextComponent.Builder toBuilder() {
        return new BuilderImpl(this);
    }

    static final class BuilderImpl
    extends AbstractComponentBuilder<TextComponent, TextComponent.Builder>
    implements TextComponent.Builder {
        private String content = "";

        BuilderImpl() {
        }

        BuilderImpl(@NotNull TextComponent component) {
            super(component);
            this.content = component.content();
        }

        @Override
        @NotNull
        public TextComponent.Builder content(@NotNull String content) {
            this.content = Objects.requireNonNull(content, "content");
            return this;
        }

        @Override
        @NotNull
        public String content() {
            return this.content;
        }

        @Override
        @NotNull
        public TextComponent build() {
            if (this.isEmpty()) {
                return Component.empty();
            }
            return TextComponentImpl.create(this.children, this.buildStyle(), this.content);
        }

        private boolean isEmpty() {
            return this.content.isEmpty() && this.children.isEmpty() && !this.hasStyle();
        }
    }
}

