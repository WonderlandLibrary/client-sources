/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.libs.kyori.adventure.text.format;

import com.viaversion.viaversion.libs.kyori.adventure.text.format.Style;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.StyleBuilderApplicable;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.TextDecorationAndState;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.TextDecorationAndStateImpl;
import com.viaversion.viaversion.libs.kyori.adventure.text.format.TextFormat;
import com.viaversion.viaversion.libs.kyori.adventure.util.Index;
import com.viaversion.viaversion.libs.kyori.adventure.util.TriState;
import java.util.Objects;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public enum TextDecoration implements StyleBuilderApplicable,
TextFormat
{
    OBFUSCATED("obfuscated"),
    BOLD("bold"),
    STRIKETHROUGH("strikethrough"),
    UNDERLINED("underlined"),
    ITALIC("italic");

    public static final Index<String, TextDecoration> NAMES;
    private final String name;

    private TextDecoration(String string2) {
        this.name = string2;
    }

    @Deprecated
    @NotNull
    public final TextDecorationAndState as(boolean bl) {
        return this.withState(bl);
    }

    @Deprecated
    @NotNull
    public final TextDecorationAndState as(@NotNull State state) {
        return this.withState(state);
    }

    @NotNull
    public final TextDecorationAndState withState(boolean bl) {
        return new TextDecorationAndStateImpl(this, State.byBoolean(bl));
    }

    @NotNull
    public final TextDecorationAndState withState(@NotNull State state) {
        return new TextDecorationAndStateImpl(this, state);
    }

    @NotNull
    public final TextDecorationAndState withState(@NotNull TriState triState) {
        return new TextDecorationAndStateImpl(this, State.byTriState(triState));
    }

    @Override
    public void styleApply(@NotNull Style.Builder builder) {
        builder.decorate(this);
    }

    @NotNull
    public String toString() {
        return this.name;
    }

    private static String lambda$static$0(TextDecoration textDecoration) {
        return textDecoration.name;
    }

    static {
        NAMES = Index.create(TextDecoration.class, TextDecoration::lambda$static$0);
    }

    public static enum State {
        NOT_SET("not_set"),
        FALSE("false"),
        TRUE("true");

        private final String name;

        private State(String string2) {
            this.name = string2;
        }

        public String toString() {
            return this.name;
        }

        @NotNull
        public static State byBoolean(boolean bl) {
            return bl ? TRUE : FALSE;
        }

        @NotNull
        public static State byBoolean(@Nullable Boolean bl) {
            return bl == null ? NOT_SET : State.byBoolean((boolean)bl);
        }

        @NotNull
        public static State byTriState(@NotNull TriState triState) {
            Objects.requireNonNull(triState);
            switch (1.$SwitchMap$net$kyori$adventure$util$TriState[triState.ordinal()]) {
                case 1: {
                    return TRUE;
                }
                case 2: {
                    return FALSE;
                }
                case 3: {
                    return NOT_SET;
                }
            }
            throw new IllegalArgumentException("Unable to turn TriState: " + (Object)((Object)triState) + " into a TextDecoration.State");
        }
    }
}

