/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.libs.kyori.adventure.text;

import com.viaversion.viaversion.libs.kyori.adventure.text.AbstractComponent;
import com.viaversion.viaversion.libs.kyori.adventure.text.AbstractComponentBuilder;
import com.viaversion.viaversion.libs.kyori.adventure.text.Component;
import com.viaversion.viaversion.libs.kyori.adventure.text.ComponentLike;
import com.viaversion.viaversion.libs.kyori.adventure.text.LegacyFormattingDetected;
import com.viaversion.viaversion.libs.kyori.adventure.text.TextComponent;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.Style;
import com.viaversion.viaversion.libs.kyori.adventure.util.Nag;
import com.viaversion.viaversion.libs.kyori.examination.ExaminableProperty;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.VisibleForTesting;

final class TextComponentImpl
extends AbstractComponent
implements TextComponent {
    private static final boolean WARN_WHEN_LEGACY_FORMATTING_DETECTED = Boolean.getBoolean(String.join((CharSequence)".", "net", "kyori", "adventure", "text", "warnWhenLegacyFormattingDetected"));
    @VisibleForTesting
    static final char SECTION_CHAR = '\u00a7';
    static final TextComponent EMPTY = TextComponentImpl.createDirect("");
    static final TextComponent NEWLINE = TextComponentImpl.createDirect("\n");
    static final TextComponent SPACE = TextComponentImpl.createDirect(" ");
    private final String content;

    @NotNull
    private static TextComponent createDirect(@NotNull String content) {
        return new TextComponentImpl(Collections.emptyList(), Style.empty(), content);
    }

    TextComponentImpl(@NotNull List<? extends ComponentLike> children, @NotNull Style style, @NotNull String content) {
        super(children, style);
        this.content = Objects.requireNonNull(content, "content");
        if (!WARN_WHEN_LEGACY_FORMATTING_DETECTED) return;
        LegacyFormattingDetected nag = this.warnWhenLegacyFormattingDetected();
        if (nag == null) return;
        Nag.print(nag);
    }

    @VisibleForTesting
    @Nullable
    final LegacyFormattingDetected warnWhenLegacyFormattingDetected() {
        if (this.content.indexOf(167) == -1) return null;
        return new LegacyFormattingDetected(this);
    }

    @Override
    @NotNull
    public String content() {
        return this.content;
    }

    @Override
    @NotNull
    public TextComponent content(@NotNull String content) {
        if (!Objects.equals(this.content, content)) return new TextComponentImpl(this.children, this.style, Objects.requireNonNull(content, "content"));
        return this;
    }

    @Override
    @NotNull
    public TextComponent children(@NotNull List<? extends ComponentLike> children) {
        return new TextComponentImpl(children, this.style, this.content);
    }

    @Override
    @NotNull
    public TextComponent style(@NotNull Style style) {
        return new TextComponentImpl(this.children, style, this.content);
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
        return 31 * result + this.content.hashCode();
    }

    @Override
    @NotNull
    protected Stream<? extends ExaminableProperty> examinablePropertiesWithoutChildren() {
        return Stream.concat(Stream.of(ExaminableProperty.of("content", this.content)), super.examinablePropertiesWithoutChildren());
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
            if (!this.isEmpty()) return new TextComponentImpl(this.children, this.buildStyle(), this.content);
            return Component.empty();
        }

        private boolean isEmpty() {
            if (!this.content.isEmpty()) return false;
            if (!this.children.isEmpty()) return false;
            if (this.hasStyle()) return false;
            return true;
        }
    }
}

