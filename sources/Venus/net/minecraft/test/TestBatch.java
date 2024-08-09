/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.test;

import java.util.Collection;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.test.TestFunctionInfo;
import net.minecraft.world.server.ServerWorld;

public class TestBatch {
    private final String field_229460_a_;
    private final Collection<TestFunctionInfo> field_229461_b_;
    @Nullable
    private final Consumer<ServerWorld> field_229462_c_;

    public TestBatch(String string, Collection<TestFunctionInfo> collection, @Nullable Consumer<ServerWorld> consumer) {
        if (collection.isEmpty()) {
            throw new IllegalArgumentException("A GameTestBatch must include at least one TestFunction!");
        }
        this.field_229460_a_ = string;
        this.field_229461_b_ = collection;
        this.field_229462_c_ = consumer;
    }

    public String func_229463_a_() {
        return this.field_229460_a_;
    }

    public Collection<TestFunctionInfo> func_229465_b_() {
        return this.field_229461_b_;
    }

    public void func_229464_a_(ServerWorld serverWorld) {
        if (this.field_229462_c_ != null) {
            this.field_229462_c_.accept(serverWorld);
        }
    }
}

