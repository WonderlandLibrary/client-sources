/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.chunk.storage;

import javax.annotation.Nullable;

public class SuppressedExceptions<T extends Throwable> {
    @Nullable
    private T field_233001_a_;

    public void func_233003_a_(T t) {
        if (this.field_233001_a_ == null) {
            this.field_233001_a_ = t;
        } else {
            ((Throwable)this.field_233001_a_).addSuppressed((Throwable)t);
        }
    }

    public void func_233002_a_() throws T {
        if (this.field_233001_a_ != null) {
            throw this.field_233001_a_;
        }
    }
}

