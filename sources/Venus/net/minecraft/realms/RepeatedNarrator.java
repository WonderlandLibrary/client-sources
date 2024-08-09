/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.realms;

import com.google.common.util.concurrent.RateLimiter;
import java.time.Duration;
import java.util.concurrent.atomic.AtomicReference;
import net.minecraft.client.gui.chat.NarratorChatListener;
import net.minecraft.util.Util;
import net.minecraft.util.text.ChatType;
import net.minecraft.util.text.StringTextComponent;

public class RepeatedNarrator {
    private final float field_230729_a_;
    private final AtomicReference<Parameter> field_230730_b_ = new AtomicReference();

    public RepeatedNarrator(Duration duration) {
        this.field_230729_a_ = 1000.0f / (float)duration.toMillis();
    }

    public void func_231415_a_(String string) {
        Parameter parameter = this.field_230730_b_.updateAndGet(arg_0 -> this.lambda$func_231415_a_$0(string, arg_0));
        if (parameter.field_214463_b.tryAcquire(0)) {
            NarratorChatListener.INSTANCE.say(ChatType.SYSTEM, new StringTextComponent(string), Util.DUMMY_UUID);
        }
    }

    private Parameter lambda$func_231415_a_$0(String string, Parameter parameter) {
        return parameter != null && string.equals(parameter.field_214462_a) ? parameter : new Parameter(string, RateLimiter.create(this.field_230729_a_));
    }

    static class Parameter {
        private final String field_214462_a;
        private final RateLimiter field_214463_b;

        Parameter(String string, RateLimiter rateLimiter) {
            this.field_214462_a = string;
            this.field_214463_b = rateLimiter;
        }
    }
}

