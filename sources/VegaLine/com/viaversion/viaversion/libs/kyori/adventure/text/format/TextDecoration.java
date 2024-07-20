/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.NotNull
 *  org.jetbrains.annotations.Nullable
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

    private TextDecoration(String name) {
        this.name = name;
    }

    @Deprecated
    @NotNull
    public final TextDecorationAndState as(boolean state) {
        return this.withState(state);
    }

    @Deprecated
    @NotNull
    public final TextDecorationAndState as(@NotNull State state) {
        return this.withState(state);
    }

    @NotNull
    public final TextDecorationAndState withState(boolean state) {
        return new TextDecorationAndStateImpl(this, State.byBoolean(state));
    }

    @NotNull
    public final TextDecorationAndState withState(@NotNull State state) {
        return new TextDecorationAndStateImpl(this, state);
    }

    @NotNull
    public final TextDecorationAndState withState(@NotNull TriState state) {
        return new TextDecorationAndStateImpl(this, State.byTriState(state));
    }

    @Override
    public void styleApply(@NotNull Style.Builder style) {
        style.decorate(this);
    }

    @NotNull
    public String toString() {
        return this.name;
    }

    static {
        NAMES = Index.create(TextDecoration.class, constant -> constant.name);
    }

    public static enum State {
        NOT_SET("not_set"),
        FALSE("false"),
        TRUE("true");

        private final String name;

        private State(String name) {
            this.name = name;
        }

        public String toString() {
            return this.name;
        }

        @NotNull
        public static State byBoolean(boolean flag) {
            return flag ? TRUE : FALSE;
        }

        @NotNull
        public static State byBoolean(@Nullable Boolean flag) {
            return flag == null ? NOT_SET : State.byBoolean((boolean)flag);
        }

        @NotNull
        public static State byTriState(@NotNull TriState flag) {
            Objects.requireNonNull(flag);
            switch (flag) {
                case TRUE: {
                    return TRUE;
                }
                case FALSE: {
                    return FALSE;
                }
                case NOT_SET: {
                    return NOT_SET;
                }
            }
            throw new IllegalArgumentException("Unable to turn TriState: " + (Object)((Object)flag) + " into a TextDecoration.State");
        }
    }
}

