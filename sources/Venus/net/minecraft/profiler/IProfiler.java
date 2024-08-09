/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.profiler;

import java.util.function.Supplier;
import net.minecraft.profiler.EmptyProfiler;

public interface IProfiler {
    public void startTick();

    public void endTick();

    public void startSection(String var1);

    public void startSection(Supplier<String> var1);

    public void endSection();

    public void endStartSection(String var1);

    public void endStartSection(Supplier<String> var1);

    public void func_230035_c_(String var1);

    public void func_230036_c_(Supplier<String> var1);

    public static IProfiler func_233513_a_(IProfiler iProfiler, IProfiler iProfiler2) {
        if (iProfiler == EmptyProfiler.INSTANCE) {
            return iProfiler2;
        }
        return iProfiler2 == EmptyProfiler.INSTANCE ? iProfiler : new IProfiler(){
            final IProfiler val$p_233513_0_;
            final IProfiler val$p_233513_1_;
            {
                this.val$p_233513_0_ = iProfiler;
                this.val$p_233513_1_ = iProfiler2;
            }

            @Override
            public void startTick() {
                this.val$p_233513_0_.startTick();
                this.val$p_233513_1_.startTick();
            }

            @Override
            public void endTick() {
                this.val$p_233513_0_.endTick();
                this.val$p_233513_1_.endTick();
            }

            @Override
            public void startSection(String string) {
                this.val$p_233513_0_.startSection(string);
                this.val$p_233513_1_.startSection(string);
            }

            @Override
            public void startSection(Supplier<String> supplier) {
                this.val$p_233513_0_.startSection(supplier);
                this.val$p_233513_1_.startSection(supplier);
            }

            @Override
            public void endSection() {
                this.val$p_233513_0_.endSection();
                this.val$p_233513_1_.endSection();
            }

            @Override
            public void endStartSection(String string) {
                this.val$p_233513_0_.endStartSection(string);
                this.val$p_233513_1_.endStartSection(string);
            }

            @Override
            public void endStartSection(Supplier<String> supplier) {
                this.val$p_233513_0_.endStartSection(supplier);
                this.val$p_233513_1_.endStartSection(supplier);
            }

            @Override
            public void func_230035_c_(String string) {
                this.val$p_233513_0_.func_230035_c_(string);
                this.val$p_233513_1_.func_230035_c_(string);
            }

            @Override
            public void func_230036_c_(Supplier<String> supplier) {
                this.val$p_233513_0_.func_230036_c_(supplier);
                this.val$p_233513_1_.func_230036_c_(supplier);
            }
        };
    }
}

