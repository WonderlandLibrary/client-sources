/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util;

import net.minecraft.util.ActionResultType;

public class ActionResult<T> {
    private final ActionResultType type;
    private final T result;

    public ActionResult(ActionResultType actionResultType, T t) {
        this.type = actionResultType;
        this.result = t;
    }

    public ActionResultType getType() {
        return this.type;
    }

    public T getResult() {
        return this.result;
    }

    public static <T> ActionResult<T> resultSuccess(T t) {
        return new ActionResult<T>(ActionResultType.SUCCESS, t);
    }

    public static <T> ActionResult<T> resultConsume(T t) {
        return new ActionResult<T>(ActionResultType.CONSUME, t);
    }

    public static <T> ActionResult<T> resultPass(T t) {
        return new ActionResult<T>(ActionResultType.PASS, t);
    }

    public static <T> ActionResult<T> resultFail(T t) {
        return new ActionResult<T>(ActionResultType.FAIL, t);
    }

    public static <T> ActionResult<T> func_233538_a_(T t, boolean bl) {
        return bl ? ActionResult.resultSuccess(t) : ActionResult.resultConsume(t);
    }
}

