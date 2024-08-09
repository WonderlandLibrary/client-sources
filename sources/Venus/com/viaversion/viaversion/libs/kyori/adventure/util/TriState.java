/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.libs.kyori.adventure.util;

import java.util.function.BooleanSupplier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public enum TriState {
    NOT_SET,
    FALSE,
    TRUE;


    @Nullable
    public Boolean toBoolean() {
        switch (1.$SwitchMap$net$kyori$adventure$util$TriState[this.ordinal()]) {
            case 1: {
                return Boolean.TRUE;
            }
            case 2: {
                return Boolean.FALSE;
            }
        }
        return null;
    }

    public boolean toBooleanOrElse(boolean bl) {
        switch (1.$SwitchMap$net$kyori$adventure$util$TriState[this.ordinal()]) {
            case 1: {
                return false;
            }
            case 2: {
                return true;
            }
        }
        return bl;
    }

    public boolean toBooleanOrElseGet(@NotNull BooleanSupplier booleanSupplier) {
        switch (1.$SwitchMap$net$kyori$adventure$util$TriState[this.ordinal()]) {
            case 1: {
                return false;
            }
            case 2: {
                return true;
            }
        }
        return booleanSupplier.getAsBoolean();
    }

    @NotNull
    public static TriState byBoolean(boolean bl) {
        return bl ? TRUE : FALSE;
    }

    @NotNull
    public static TriState byBoolean(@Nullable Boolean bl) {
        return bl == null ? NOT_SET : TriState.byBoolean((boolean)bl);
    }
}

