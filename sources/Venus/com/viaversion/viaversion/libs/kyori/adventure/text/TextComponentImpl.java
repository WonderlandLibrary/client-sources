/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.VisibleForTesting
 */
package com.viaversion.viaversion.libs.kyori.adventure.text;

import com.viaversion.viaversion.libs.kyori.adventure.internal.Internals;
import com.viaversion.viaversion.libs.kyori.adventure.internal.properties.AdventureProperties;
import com.viaversion.viaversion.libs.kyori.adventure.text.AbstractComponent;
import com.viaversion.viaversion.libs.kyori.adventure.text.AbstractComponentBuilder;
import com.viaversion.viaversion.libs.kyori.adventure.text.BuildableComponent;
import com.viaversion.viaversion.libs.kyori.adventure.text.Component;
import com.viaversion.viaversion.libs.kyori.adventure.text.ComponentBuilder;
import com.viaversion.viaversion.libs.kyori.adventure.text.ComponentLike;
import com.viaversion.viaversion.libs.kyori.adventure.text.LegacyFormattingDetected;
import com.viaversion.viaversion.libs.kyori.adventure.text.TextComponent;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.Style;
import com.viaversion.viaversion.libs.kyori.adventure.util.Buildable;
import com.viaversion.viaversion.libs.kyori.adventure.util.Nag;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.VisibleForTesting;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
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

    static TextComponent create(@NotNull List<? extends ComponentLike> list, @NotNull Style style, @NotNull String string) {
        List<Component> list2 = ComponentLike.asComponents(list, IS_NOT_EMPTY);
        if (list2.isEmpty() && style.isEmpty() && string.isEmpty()) {
            return Component.empty();
        }
        return new TextComponentImpl(list2, Objects.requireNonNull(style, "style"), Objects.requireNonNull(string, "content"));
    }

    @NotNull
    private static TextComponent createDirect(@NotNull String string) {
        return new TextComponentImpl(Collections.emptyList(), Style.empty(), string);
    }

    TextComponentImpl(@NotNull List<Component> list, @NotNull Style style, @NotNull String string) {
        super(list, style);
        LegacyFormattingDetected legacyFormattingDetected;
        this.content = string;
        if (WARN_WHEN_LEGACY_FORMATTING_DETECTED && (legacyFormattingDetected = this.warnWhenLegacyFormattingDetected()) != null) {
            Nag.print(legacyFormattingDetected);
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
    public TextComponent content(@NotNull String string) {
        if (Objects.equals(this.content, string)) {
            return this;
        }
        return TextComponentImpl.create(this.children, this.style, string);
    }

    @Override
    @NotNull
    public TextComponent children(@NotNull List<? extends ComponentLike> list) {
        return TextComponentImpl.create(list, this.style, this.content);
    }

    @Override
    @NotNull
    public TextComponent style(@NotNull Style style) {
        return TextComponentImpl.create(this.children, style, this.content);
    }

    @Override
    public boolean equals(@Nullable Object object) {
        if (this == object) {
            return false;
        }
        if (!(object instanceof TextComponentImpl)) {
            return true;
        }
        if (!super.equals(object)) {
            return true;
        }
        TextComponentImpl textComponentImpl = (TextComponentImpl)object;
        return Objects.equals(this.content, textComponentImpl.content);
    }

    @Override
    public int hashCode() {
        int n = super.hashCode();
        n = 31 * n + this.content.hashCode();
        return n;
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

    @Override
    @NotNull
    public Component style(@NotNull Style style) {
        return this.style(style);
    }

    @Override
    @NotNull
    public Component children(@NotNull List list) {
        return this.children(list);
    }

    @Override
    @NotNull
    public ComponentBuilder toBuilder() {
        return this.toBuilder();
    }

    @Override
    @NotNull
    public Buildable.Builder toBuilder() {
        return this.toBuilder();
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    static final class BuilderImpl
    extends AbstractComponentBuilder<TextComponent, TextComponent.Builder>
    implements TextComponent.Builder {
        private String content = "";

        BuilderImpl() {
        }

        BuilderImpl(@NotNull TextComponent textComponent) {
            super(textComponent);
            this.content = textComponent.content();
        }

        @Override
        @NotNull
        public TextComponent.Builder content(@NotNull String string) {
            this.content = Objects.requireNonNull(string, "content");
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

        @Override
        @NotNull
        public BuildableComponent build() {
            return this.build();
        }

        @Override
        @NotNull
        public Object build() {
            return this.build();
        }
    }
}

