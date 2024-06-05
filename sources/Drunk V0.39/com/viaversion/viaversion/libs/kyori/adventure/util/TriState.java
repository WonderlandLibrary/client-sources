/*
 * Decompiled with CFR 0.152.
 */
package com.viaversion.viaversion.libs.kyori.adventure.util;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public enum TriState {
    NOT_SET,
    FALSE,
    TRUE;


    @NotNull
    public static TriState byBoolean(boolean value) {
        TriState triState;
        if (value) {
            triState = TRUE;
            return triState;
        }
        triState = FALSE;
        return triState;
    }

    @NotNull
    public static TriState byBoolean(@Nullable Boolean value) {
        TriState triState;
        if (value == null) {
            triState = NOT_SET;
            return triState;
        }
        triState = TriState.byBoolean((boolean)value);
        return triState;
    }
}

