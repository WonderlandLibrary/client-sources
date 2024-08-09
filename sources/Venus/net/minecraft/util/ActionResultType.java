/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util;

public enum ActionResultType {
    SUCCESS,
    CONSUME,
    PASS,
    FAIL;


    public boolean isSuccessOrConsume() {
        return this == SUCCESS || this == CONSUME;
    }

    public boolean isSuccess() {
        return this == SUCCESS;
    }

    public static ActionResultType func_233537_a_(boolean bl) {
        return bl ? SUCCESS : CONSUME;
    }
}

